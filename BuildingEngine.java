

import java.awt.Graphics2D;
import java.util.ArrayList; import java.util.Random;

public class BuildingEngine 
{
	//The skyline translates left at this many pixels per second
	public static final double TRANSLATE_RATE = 100;
	//The distance between the left edge of adjacent buildings is a random integer between GEN_INCREMENT_MIN and GEN_INCREMENT_MAX.
	public static final int GEN_INCREMENT_MIN = 20, GEN_INCREMENT_MAX = 50; 
	
	private static RandomTools random = new RandomTools();
	private ArrayList<Building> buildings; //the buildings that this BuildingEngine controls
	private double genPosition; //the x coordinate of the left edge of the most recently generated building
	
	public BuildingEngine ()
	{
		buildings = new ArrayList<Building>(80);
		genPosition = 0;
		generate();
	}
	
	/**
	 * Calculates the distance the building setup travels in the given number of seconds. Shifts all buildings to the left, 
	 * removes them if they are left-offscreen, and generate new buildings on the very right of the screen to fill the new space.
	 */
	public void update (double secs)
	{
		//d = rt
		double translateDist = TRANSLATE_RATE * secs;		

		//iterate through each index in the arraylist of buildings
		int index = 0;
		Building building;
		while (index < buildings.size())
		{
			building = buildings.get(index);
			building.translateLeft(translateDist);
			//if the building is offscreen, remove it from the list of buildings
			if (building.isOffscreenLeft())
			{
				buildings.remove(index);
			}
			//else, move on to the next building
			else
			{				
				index++;
			}
		}
		
		//the current generation position has also shifted left by the translation distance.
		genPosition -= translateDist;
		//generate new buildings
		generate();
	}
	
	/**
	 * Generates buildings from genPosition up to the right edge of the GamePanel. 
	 * If genPosition is greater than GamePanel.WIDTH, no buildings will be generated. 
	 */
	public void generate ()
	{
		Building newBuilding;
		while (genPosition <= GamePanel.WIDTH)
		{
			newBuilding = new Building(genPosition, Painter.BASE_Y);
			/*
			 * add building to random index in the building arraylist.
			 * the possible indices of the new building are from 0 to buildings.size(),
			 * so the call to nextInt must be for integers must be from 0 to buildings.size() + 1
			 */
			buildings.add(random.nextInt(buildings.size() + 1), newBuilding);
			
			//increment genPosition
			genPosition += random.randRange(GEN_INCREMENT_MIN, GEN_INCREMENT_MAX);
		}
	}
	
	/**
	 * Draws each Building in the buildings ArrayList. They are drawn in the order of lowest to highest index.
	 */
	public void render (Graphics2D g)
	{
		for (int i = 0; i < buildings.size(); i++)
		{
			buildings.get(i).render(g);
		}
	}
}
