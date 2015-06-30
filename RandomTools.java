

import java.util.Random;

public class RandomTools extends Random
{	
	/**
	 * Returns a random integer between min and max, inclusive.
	 */
	public int randRange (int min, int max)
	{
		return nextInt(max - min + 1) + min;
	}
	
	/**
	 * Returns a random integer between 0 and max, inclusive.
	 */
	public int randRange (int max)
	{
		return nextInt(max + 1);
	}
	
	/**
	 * Returns a random double in the interval [min, max).
	 */
	public double randRange (double min, double max)
	{
		return nextDouble() * (max - min) + min;
	}
	
	
}
