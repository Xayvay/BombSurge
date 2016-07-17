package x.bombsurge;

public class TNT
{
	float posX;
	float posY;
	public static float rate;

	public TNT(float x, float y)
	{
		posX = x;
		posY = y;
		rate = GameActivity.rateInc + 6;
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
