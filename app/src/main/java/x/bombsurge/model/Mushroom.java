package x.bombsurge.model;

public class Mushroom
{
	float posX;
	float posY;
    public static float rate;


	public Mushroom(float x, float y)
	{
		posX = x;
		posY = y;
        rate = 0;

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
