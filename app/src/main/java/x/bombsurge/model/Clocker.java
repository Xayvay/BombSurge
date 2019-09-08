package x.bombsurge.model;

import x.bombsurge.controller.GameActivity;

public class Clocker
{
	float posX;
	float posY;
	public static float rate;

	public Clocker(float x, float y)
	{
		posX = x;
		posY = y;
		rate = GameActivity.rateInc + 2;
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
