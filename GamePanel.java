

import java.awt.Graphics; import java.awt.Graphics2D;
import java.awt.Image; import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * The GamePanel contains the game canvas and graphics object where the game is actually displayed.
 */
public class GamePanel extends JPanel 
{	
	public static final int WIDTH = 1300, HEIGHT = 500;
	public static final int FRAMERATE = 60; //a frames-per-second target
	
	private boolean terminate;
	
	private Image bufferImage;
	private Graphics bufferGraphics;
	//the current Graphics context, as opposed to the buffer
	private Graphics currentGraphics;
	
	//the painter that draws on this GamePanel
	private Painter painter;
	
	/**
	 * Initialize the GamePanel 
	 */
	public GamePanel ()
	{
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void run ()
	{
		terminate = false;
		
		//the target time for each frame, in nanoseconds
		long targetTime = Math.round(1000000000.0 / FRAMERATE);
		long startTime; //initial value of timer in ns, so timeTaken is System.nanoTime() - startTime
		long timeTaken; //The amount of time that the previous frame took in ns, NOT including time slept
		long sleepTime; //If timeTaken < targetTime, this is the time taken to sleep
		long sleepMillis; int sleepNanos; 
		
		//the amount of time the previous frame took in ns, INCLUDING time slept
		//The first frame is assumed to take targetTime ns, as an initial value
		long previousTime = targetTime; 
		
		//initialize the painter
		painter = new Painter();
		
		//each iteration of this while loop runs one frame of the game
		while (!terminate)
		{			
			//get start time
			startTime = System.nanoTime();	
			
			//update and render
			update(previousTime);			
			render();
			
			timeTaken = System.nanoTime() - startTime;			
			//if the frame took less than targetTime nanoseconds, sleep for the time difference
			if (timeTaken < targetTime)
			{
				try
				{
					sleepTime = targetTime - timeTaken; //split sleepTime into milliseconds and nanoseconds.
					sleepMillis = sleepTime / 1000000;
					sleepNanos = (int) sleepTime % 1000000;
					Thread.sleep(sleepMillis, sleepNanos);
				}
				catch (InterruptedException e) {}
			}
			
			//how long did all this take? Get difference in nanoTime counter and startTime at the end.
			//if the above if loop ran, hopefully previousTime is very close to targetTime
			previousTime = System.nanoTime() - startTime;
		}
	}
	
	/**
	 * Converts nanoseconds to seconds and updates the Painter for the given amount of time.
	 */
	public void update (long ns)
	{
		painter.update(ns / 1000000000.0);
	}
	
	/**
	 * Renders to the buffer graphics object, and then copies the buffer graphics to the current graphics context.
	 */
	public void render ()
	{
		//the painter paints on the buffer graphics
		bufferImage = createImage(WIDTH, HEIGHT);
		bufferGraphics = bufferImage.getGraphics();
		painter.render((Graphics2D) bufferGraphics);		
		
		//draw the buffer image on the current graphics
		currentGraphics = this.getGraphics();
		currentGraphics.drawImage(bufferImage, 0, 0, null);
	}
	
	public void terminateGame ()
	{
		terminate = true;
	}
	
}
