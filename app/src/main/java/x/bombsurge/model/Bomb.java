package x.bombsurge.model;

import x.bombsurge.controller.GameActivity;

public class Bomb
{
	private float posX;
	private float posY;
	private float rate;

	public Bomb(float x, float y)
	{
		posX = x;
		posY = y;
		rate = GameActivity.rateInc;
	}

	public void tick()
	{
		posY += rate;

        if(GameActivity.score > 200   && (posY < 200|| posY < 600 && posY > 400 ||
           posY < 1000 && posY > 800 || posY < 1400 && posY > 1200||
           posY < 1800 && posY > 1600 || posY < 2200 && posY > 2000 ||
           posY < 2600 && posY > 2400))  {
            posX += rate/2;
        } else if (GameActivity.score > 200) {
            posX -= rate/2;
        }
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
