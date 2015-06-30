

import java.awt.BasicStroke; import java.awt.Color; import java.awt.Graphics2D;
import java.util.ArrayList;

public class Building 
{
	public static final ArrayList<Color> COLORS = new ArrayList<Color>();
	static //initialize the possible building colors 
	{
		COLORS.add(Color.black);
		COLORS.add(new Color(64, 64, 64)); //dark gray
		COLORS.add(new Color(136, 45, 23)); //sienna
		COLORS.add(new Color(25, 25, 112)); //midnight blue
		COLORS.add(Color.white);
		COLORS.add(new Color(192, 192, 192)); //light gray
	}
	public static final Color COLOR_WINDOW = Color.yellow;
	public static final int WIDTH_MIN = 70, WIDTH_MAX = 120; //min and max possible width of a building, in pixels
	public static final int HEIGHT_MIN = 100, HEIGHT_MAX = 400; //min and max possible height of a building, in pixels
	//the width of the sky border of each building
	public static final int SKY_STROKE_WIDTH = 3;
	//windows are square with that size, and there are that many pixels in the gap between adjacent windows
	public static final int WINDOW_SIZE = 6, WINDOW_GAP = 12; 
	
	
	//private data
	private static RandomTools random = new RandomTools();
	/*
	 * This is the index of the color of the most recently constructed building in the COLORS arraylist.
	 * every time a new building is constructed, this value is incremented, so that the building colors are rotated through.
	 */
	private static int colorIndex = 0;
	/*
	 * The leftX coordinate is stored with double precision because when the building is translated left during a frame, it may move by a fraction
	 * of a pixel, and precision greater than whole numbers is necessary. 
	 */
	private double leftX;
	//In contrast, the bottomY coordinate is stored as an integer because it should be an integer.
	private int bottomY;
	private Color color; //the building's color
	private int width, height;
	
	
	
	/**
	 * Construct a building, with the coordinate of the lower left of the building at the given point.
	 * The building's color, width, and height are randomly chosen.
	 */
	public Building (double leftX, int bottomY)
	{
		this.leftX = leftX;
		this.bottomY = bottomY;
		width = random.randRange(WIDTH_MIN, WIDTH_MAX);
		height = random.randRange(HEIGHT_MIN, HEIGHT_MAX);
		
		//get the next color
		color = COLORS.get(colorIndex);
		colorIndex++;
		if (colorIndex >= COLORS.size())
		{
			colorIndex = 0;
		}
	}
	
	/**
	 * Render the building on the provided Graphics. All drawing operations will be performed, even if the building is offscreen.
	 */
	public void render (Graphics2D g)
	{
		//round both x and y coordinates of the building's lower left x and y to integers.
		int leftX = (int) Math.round(this.leftX); //the x coordinate of the left edge of the building 
		int rightX = leftX + width;
		int topY = bottomY - height;
		
		//draw the building rectangle. (rectangle drawing begins at top left corner)
		g.setColor(color);
		g.fillRect(leftX, topY, width, height);
		
		//draw the sky borders
		g.setColor(Painter.COLOR_SKY);
		g.setStroke(new BasicStroke(SKY_STROKE_WIDTH));
		g.drawLine(leftX, bottomY, leftX, topY);
		g.drawLine(leftX, topY, rightX, topY);
		g.drawLine(rightX, topY, rightX, bottomY);		
		
		//draw windows
		g.setColor(COLOR_WINDOW);		
		/*
		 * the number of windows that fit in a row on the building. 
		 * each window takes up WINDOW_SIZE + WINDOW_GAP, so the number of windows that will fit in a row of size "width" is
		 * the floor of width / (WINDOW_SIZE + WINDOW_GAP).
		 */
		int numWindows = width / (WINDOW_SIZE + WINDOW_GAP);
		/*
		 * numWindows windows will be spaced out with WINDOW_GAP between adjacent windows and centered on the building.
		 * The distance between the left edge of the leftmost window and the right edge of the rightmost window is:
		 * rowWidth = numWindows * WINDOW_SIZE + (numWindows - 1) * WINDOW_GAP
		 * The distance between the left edge of the building and the left edge of the leftmost window is: 
		 * the floor of (width - rowWidth) / 2
		 * Add this distance to the x-coordinate of the left edge of the building to get the x-coordinate of the left edge of the leftmost window
		 */
		int windowLeftX = leftX + (width - (numWindows * WINDOW_SIZE + (numWindows - 1) * WINDOW_GAP)) / 2;
		//iterate from left to right through the possible x coordinates for the left edge of the window
		for (int i = 0; i < numWindows; i++) 
		{
			//iterate from top to bottom through the possible y coordinates for the top edge of the window
			for (int windowTopY = topY + WINDOW_GAP; bottomY - windowTopY >= 2*WINDOW_SIZE + 3*WINDOW_GAP/2 ; windowTopY += WINDOW_SIZE + WINDOW_GAP)
			{
				g.fillRect(windowLeftX, windowTopY, WINDOW_SIZE, WINDOW_SIZE);
			}
			
			//this is the x-coordinate of the left edge of the window to the right of the current window
			windowLeftX += WINDOW_SIZE + WINDOW_GAP;
		}
	}	
	
	//animation methods
	/**
	 * Translates the building LEFT by the given distance.
	 */
	public void translateLeft (double distance)
	{
		leftX -= distance;
	}
	
	/**
	 * Returns if the building is so far LEFT that it cannot be seen on the screen; true if offscreen, false if onscreen.
	 */
	public boolean isOffscreenLeft ()
	{
		return leftX + width < 0;
	}
}