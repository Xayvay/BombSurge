package x.bombsurge.model;

import x.bombsurge.controller.GameActivity;

public class Mini
{
	private float posX;
	private float posY;
	public static float rate;

	public Mini(float x, float y)
	{
		posX = x;
		posY = y;
		rate = GameActivity.rateInc + 10;
	}

	public void tick()
	{
		posY += rate;
	}

	public float getX()
	{
		return posX;
	}

	public float getY()
	{
		return posY;
	}

}
