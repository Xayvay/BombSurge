package x.bombsurge;

public class Cloud
{
	float posX;
	float posY;
	public static float rate;

	public Cloud(float x, float y)
	{
		posX = x;
		posY = y;
		rate = 10;
	}

	public void tick() {
        posX += rate;

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
