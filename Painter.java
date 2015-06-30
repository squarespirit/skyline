

import java.awt.Graphics2D; import java.awt.Color;

public class Painter {	
	
	public static final Color COLOR_SKY = new Color(135, 206, 250);
	public static final Color COLOR_BASE = new Color(128, 128, 128);
	public static final Color COLOR_SUN = Color.yellow;
	//The thickness of the base
	public static final int BASE_THICKNESS = 50;
	public static final int BASE_Y = GamePanel.HEIGHT - BASE_THICKNESS;
	//The location of the CENTER of the sun, and the sun's radius.
	public static final int SUN_Y = 75, SUN_X = 75, SUN_RADIUS = 24;
	
	private BuildingEngine engine;

	public Painter ()
	{
		engine = new BuildingEngine();
	}
	
	public void update (double secs)
	{
		engine.update(secs);
	}
	
	public void render (Graphics2D g)
	{	
		//Draw sky
		g.setColor(COLOR_SKY);
		g.fillRect(0, 0, GamePanel.WIDTH, BASE_Y);
				
		//Draw sun
		g.setColor(COLOR_SUN);
		g.fillOval(SUN_X - SUN_RADIUS, SUN_Y - SUN_RADIUS, SUN_RADIUS * 2, SUN_RADIUS * 2);
		
		engine.render(g);
		
		//Draw base aka sidewalk
		g.setColor(COLOR_BASE);
		g.fillRect(0, BASE_Y, GamePanel.WIDTH, BASE_THICKNESS);
	}

}
