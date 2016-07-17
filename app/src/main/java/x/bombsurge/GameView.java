package x.bombsurge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{


    class BombThread extends Thread
	{
        //TODO: Make the array list maps that take in Bombs and characters. If the valumultie of the map equals something due something.
		public ArrayList<Bomb> bombs;
        public ArrayList<Mini> minis;
        public ArrayList<TNT> tnts;
        public ArrayList<Cloud> clouds;
        public ArrayList<Mushroom> mushrooms;
        public ArrayList<Clocker> clocks;
        public ArrayList<Bomb> hearts;
        public ArrayList<Bomb> nukes;
        public ArrayList<Upgrades> weapons;
        public ArrayList<Upgrades> surge;
        public ArrayList<Upgrades> multi;
        public ArrayList<Laser> shots;
		public Bitmap bomber,combo,combosurge,combox,background,clock,gun,laser,flames,heart,nuke,tnt,mini,wing,blast,acid,wizard,bolt,snowman,plus1,boom,cloud,mushroom,exploded,lightbolt;
		public Paint white;
        public int gameWidth,mix,wep,explodingX;
        public int dropHearts = 1359,dropNukes =2005,dropMultis = 593,dropWeapons = 807, dropSurge = 400;

        public long laserTime = System.currentTimeMillis();
        Random random = new Random();





		public SurfaceHolder mSurfaceHolder;
		public Context mContext;
		public Handler mHandler;
		public GameActivity mActivity;

		public long frameRate;
		public boolean loading;

		public BombThread(SurfaceHolder sHolder, Context context, Handler handler)
		{

			mSurfaceHolder = sHolder;
			mHandler = handler;
			mContext = context;
			mActivity = (GameActivity) context;
            GameActivity.running = true;
			bombs = new ArrayList<>();
            shots = new ArrayList<>();
            surge = new ArrayList<>();
            clocks = new ArrayList<>();
            hearts = new ArrayList<>();
            nukes = new ArrayList<>();
            tnts = new ArrayList<>();
            multi = new ArrayList<>();
            weapons = new ArrayList<>();
            minis = new ArrayList<>();
            plus1 = BitmapFactory.decodeResource(getResources(), R.drawable.plus1);
            combo = BitmapFactory.decodeResource(getResources(), R.drawable.combo);
            combosurge = BitmapFactory.decodeResource(getResources(), R.drawable.combosurge);
            combox = BitmapFactory.decodeResource(getResources(), R.drawable.combox2);
            lightbolt = BitmapFactory.decodeResource(getResources(), R.drawable.lightningbolt);
            bomber = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
            laser = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
            blast = BitmapFactory.decodeResource(getResources(), R.drawable.beam);
            flames = BitmapFactory.decodeResource(getResources(), R.drawable.fireball);
            acid= BitmapFactory.decodeResource(getResources(), R.drawable.acidball);
            cloud = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);
            mushroom = BitmapFactory.decodeResource(getResources(), R.drawable.mushroomcloud);
            wing = BitmapFactory.decodeResource(getResources(), R.drawable.wing);
            exploded = BitmapFactory.decodeResource(getResources(), R.drawable.secondexplosion);
            gun = BitmapFactory.decodeResource(getResources(), R.drawable.gun);
            wizard = BitmapFactory.decodeResource(getResources(), R.drawable.wizard);
            clock = BitmapFactory.decodeResource(getResources(), R.drawable.clock);
            heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
            mini = BitmapFactory.decodeResource(getResources(), R.drawable.mini);
            nuke = BitmapFactory.decodeResource(getResources(), R.drawable.nuke);
            tnt = BitmapFactory.decodeResource(getResources(), R.drawable.tnt);
            bolt = BitmapFactory.decodeResource(getResources(), R.drawable.bolt);
            snowman = BitmapFactory.decodeResource(getResources(), R.drawable.snowman);




            background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            white = new Paint();
            GameActivity.dimblue = new Paint();
            GameActivity.dimblue.setStyle(Paint.Style.FILL);
            GameActivity.dimblue.setColor(Color.BLUE);
            GameActivity.dimblue.setAlpha(0);
            GameActivity.dimyellow = new Paint();
            GameActivity.dimyellow.setStyle(Paint.Style.FILL);
            GameActivity.dimyellow.setColor(Color.YELLOW);
            GameActivity.dimyellow.setAlpha(0);

			white.setStyle(Paint.Style.FILL);
			white.setColor(Color.WHITE);




			GameActivity.running = true;


			// This equates to 26 frames per second.
			frameRate = (long) (1000 / 26);
			loading = true;
		}

        public void createBombs(){

            Random rand = new Random();
            int x = rand.nextInt(gameWidth - 150);
            if(x<=110){
                x = x+110;
            }
            Bomb p = null;


                 p = new Bomb(x, 0);


            if(bombs.size() <= 8) {
                bombs.add(p);
            }
        }


        public void createClocks(){

            Random rand = new Random();
            int x = rand.nextInt(gameWidth - 150);
            if(x<=110){
                x = x+110;
            }
            Clocker p = null;


            p = new Clocker(x, 0);

            if(clocks.size() < 2) {
                clocks.add(p);
            }

        }
        public void createHearts(){

            Random rand = new Random();
            int x = rand.nextInt(gameWidth - 170);
            if(x<=110){
                x = x+110;
            }
            Bomb p = null;


            p = new Bomb(x, 0);

             if(GameActivity.score/dropHearts == GameActivity.heartdrop) {
                 hearts.add(p);
                 GameActivity.heartdrop++;
                 dropHearts = dropHearts + 100;
             }

        }
        public void createMinis(){

            Random rand = new Random();
            int x = rand.nextInt(gameWidth - 150);
            if(x<=110){
                x = x+110;
            }
            Mini p = null;


            p = new Mini(x, 0);

            if(GameActivity.score > 1250 && minis.size() < 2) {
                minis.add(p);
                GameActivity.minidrop++;
            }

        }
        public void createNukes(){

            Random rand = new Random();
            int x = rand.nextInt(gameWidth - 150);
            if(x<=110){
                x = x+110;
            }
            Bomb p = null;


            p = new Bomb(x, 0);

            if(GameActivity.score/dropNukes == GameActivity.nukedrop) {
                nukes.add(p);
                GameActivity.nukedrop++;
                dropNukes = dropNukes + 100;
            }

        }
        public void createMulti(){

            Random rand = new Random();
            int x = rand.nextInt(gameWidth - 150);
            if(x<=110){
                x = x+110;
            }
            Upgrades p = null;

            p = new Upgrades(x, 0);

            if(GameActivity.score/dropMultis == GameActivity.multidrop) {
                multi.add(p);
                GameActivity.multidrop++;
                dropMultis = dropMultis + 103;
            }

        }
        public void createWeapons(){

            Random rand = new Random();
            int x = rand.nextInt(gameWidth - 150);
            if(x<=110){
                x = x+110;
            }
            Upgrades p;

            p = new Upgrades(x, 0);
//TODO: this should be at 190
            if(GameActivity.score/dropWeapons == GameActivity.weapondrop) {
                weapons.add(p);
                GameActivity.weapondrop++;
                dropWeapons = dropWeapons + 113;
            }

        }
        public void createTNT(){

        Random rand = new Random();
        int x = rand.nextInt(gameWidth - 150);
            if(x<=110){
                x = x+110;
            }
        TNT p;


        p = new TNT(x, 0);

        if(GameActivity.score/290 == GameActivity.tntdrop) {
            tnts.add(p);
            GameActivity.tntdrop++;
        }

    }

        public void createSurge(){

            Random rand = new Random();
            int x = rand.nextInt(gameWidth - 150);
            if(x<=110){
                x = x+110;
            }
            Upgrades p;


            p = new Upgrades(x, 0);

            if(GameActivity.score/dropSurge == GameActivity.surgedrop) {
                GameActivity.surger = rand.nextInt(2);
                surge.add(p);
                GameActivity.surgedrop++;
                dropSurge = dropSurge + 121;
            }

        }
        public void shootLaser(int x){

            Laser l = null;
            l = new Laser(x,GameActivity.displayHeight);

            if(shots.size() <=10) {
                shots.add(l);

                if (mix == 1) {
                    Laser k = null;
                    k = new Laser(x + 110, GameActivity.displayHeight);
                    shots.add(k);
                }

                if (mix >= 2) {
                    //TODO: fix
                    Laser d = null;
                    d = new Laser(x + 110, GameActivity.displayHeight);
                    shots.add(d);

                    Laser v = null;
                    v = new Laser(x + 220, GameActivity.displayHeight);
                    shots.add(v);

                }
            }
            }


		@Override
		public void run()
		{

                while (GameActivity.running) {
                    if(GameActivity.notDone) {
                    Canvas c = null;
                    try {
                        c = mSurfaceHolder.lockCanvas();

                        synchronized (mSurfaceHolder) {
                            long start = System.currentTimeMillis();

                                doDraw(c);

                            long diff = System.currentTimeMillis() - start;

                            if (diff < frameRate)
                                Thread.sleep(frameRate - diff);
                        }
                    } catch (InterruptedException e) {
                    } finally {
                        if (c != null) {
                            mSurfaceHolder.unlockCanvasAndPost(c);
                        }
                    }
                }
            }
		}

		protected void doDraw(Canvas canvas) {
            gameWidth = canvas.getWidth();
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(canvas.getWidth() / 14);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), white);
            background = background.createScaledBitmap(background, canvas.getWidth(), canvas.getHeight(), true);
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawBitmap(heart, GameActivity.displayWidth / 14, GameActivity.displayHeight / 18, null);
            canvas.drawText("x" + GameActivity.lives, GameActivity.displayWidth / 8, GameActivity.displayHeight / 13, paint);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), GameActivity.dimblue);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), GameActivity.dimyellow);
            if(GameActivity.comboI1 && GameActivity.comboTrigger < 3) {
                canvas.drawBitmap(combo, GameActivity.displayWidth / 5, GameActivity.displayHeight / 18, null);
                GameActivity.comboI2 = true;
        }

            if(GameActivity.comboI2 && GameActivity.combo2trig == 2){
                GameActivity.comboI1 = false;
                canvas.drawBitmap(combox, GameActivity.displayWidth / 5, GameActivity.displayHeight / 18, null);
            }

            if(GameActivity.comboTrigger>= 3){
                GameActivity.comboI1 = false;
                GameActivity.comboI2 = false;
                canvas.drawBitmap(combosurge, GameActivity.displayWidth / 5, GameActivity.displayHeight / 18, null);
            }

//            canvas.drawRect(300,canvas.getHeight()- 300,canvas.getWidth()/2,canvas.getHeight()-200,GameActivity.photonCharge1);



            drawEnv(canvas);
            explosionType();
            createBombs();
            removeLaser();
            createMulti();
            createWeapons();
            createClocks();
            createHearts();
            createNukes();
            createMinis();
            createTNT();
            createSurge();
            multiExplosion(canvas);
            weaponExplosion(canvas);
            bombExplosion(canvas);
            clockExplosion(canvas);
            heartExplosion(canvas);
            nukeExplosion(canvas);
            miniExplosion(canvas);
            surgeExplosion(canvas);
            tntExplosion(canvas);


        }

        public void drawEnv(Canvas canvas) {
            //Draw



            for (int i = 0; i < bombs.size() - 1; i++) {
                canvas.drawBitmap(bomber, bombs.get(i).getX(), bombs.get(i).getY(), null);
                bombs.get(i).tick();
            }
            for (int x = 0; x < shots.size(); x++) {
                if (wep == 0) {
                    canvas.drawBitmap(laser, shots.get(x).getX(), shots.get(x).getY(), null);
                    shots.get(x).pulse();
                } else if (wep == 1 ) {
                    canvas.drawBitmap(wing, shots.get(x).getX(), shots.get(x).getY(), null);
                    shots.get(x).pulse();
                } else if (wep == 2 ) {
                    canvas.drawBitmap(acid, shots.get(x).getX(), shots.get(x).getY(), null);
                    shots.get(x).pulse();
                } else if (wep == 3 ) {
                    canvas.drawBitmap(flames, shots.get(x).getX(), shots.get(x).getY(), null);
                    shots.get(x).pulse();
                } else {
                    canvas.drawBitmap(laser, shots.get(x).getX(), shots.get(x).getY(), null);
                    shots.get(x).pulse();
                }
            }


            for (int i = 0; i < clocks.size() - 1; i++) {
                canvas.drawBitmap(clock, clocks.get(i).getX(), clocks.get(i).getY(), null);
                clocks.get(i).tick();
            }

            for (int i = 0; i < hearts.size() - 1; i++) {
                canvas.drawBitmap(heart, hearts.get(i).getX(), hearts.get(i).getY(), null);
                hearts.get(i).tick();
            }


            for (int x = 0; x < multi.size() - 1; x++) {


                canvas.drawBitmap(wizard, multi.get(x).getX(), multi.get(x).getY(), null);
                multi.get(x).tick();

            }


            for (int i = 0; i < weapons.size() - 1; i++) {
                canvas.drawBitmap(gun, weapons.get(i).getX(), weapons.get(i).getY(), null);
                weapons.get(i).tick();
            }

            for (int i = 0; i < minis.size() - 1; i++) {
                canvas.drawBitmap(mini, minis.get(i).getX(), minis.get(i).getY(), null);
                minis.get(i).tick();
            }

            for (int i = 0; i < nukes.size() - 1; i++) {
                canvas.drawBitmap(nuke, nukes.get(i).getX(), nukes.get(i).getY(), null);
                nukes.get(i).tick();
            }

            for (int i = 0; i < tnts.size() - 1; i++) {
                canvas.drawBitmap(tnt, tnts.get(i).getX(), tnts.get(i).getY(), null);
                tnts.get(i).tick();
            }

            for (int x = 0; x < surge.size() - 1; x++){
                if (GameActivity.surger == 0) {
                    canvas.drawBitmap(bolt, surge.get(x).getX(), surge.get(x).getY(), null);
                    surge.get(x).tick();
                } else if (GameActivity.surger == 1) {
                    canvas.drawBitmap(snowman, surge.get(x).getX(), surge.get(x).getY(), null);
                    surge.get(x).tick();
                }
            }


        }

        public void bombExplosion(Canvas canvas){
            Random rand = new Random();
            int a = rand.nextInt(getWidth());
            int b = rand.nextInt(getHeight());
        for (int i = 0; i < bombs.size()-2;i++)
        {

            if(bombs.get(i).getY()> canvas.getHeight()){
                GameActivity.lives--;
                bombs.remove(i);

            }
            if (bombs.size()== 0){
                continue;
            }
            else {
                for (int x = 0; x  < shots.size();x++)
                {
                    if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                            (int) shots.get(x).getY(), bomber, (int)bombs.get(i).getX(),
                            (int) bombs.get(i).getY())
                            ) {

                        GameActivity.score = GameActivity.score+GameActivity.incScore;
                        GameActivity.scoreCounter++;
                        GameActivity.explosion.start();

                        canvas.drawBitmap(boom, (int) bombs.get(i).getX(), (int) bombs.get(i).getY(), null);

                        if(GameActivity.lightning == true){

                         canvas.drawBitmap(lightbolt, (int) bombs.get(i).getX(), 0, null);

                        }

                        bombs.remove(i);
                        shots.remove(x);

                        if(wep == 3){
                            canvas.drawBitmap(exploded,a,b, null);
                       }

                    }

                }
            }
        }
    }

        public void multiExplosion(Canvas canvas){
            for (int i = 0; i < multi.size()-1;i++)
            {

                if(multi.get(i).getY()> canvas.getHeight()){
                    multi.remove(i);
                }
                if (multi.size()== 0){
                    continue;
                }
                else {
                    for (int x = 0; x  < shots.size();x++)
                    {
                        if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                                (int) shots.get(x).getY(), wizard, (int)multi.get(i).getX(),
                                (int) multi.get(i).getY())
                                ) {

                            multi.remove(i);
                            shots.remove(x);
                            GameActivity.item.start();
                            canvas.drawBitmap(boom, (int) shots.get(i).getX(), (int) shots.get(i).getY(), null);
                            mix = random.nextInt(3);

                        }

                    }
                }
            }
        }

        public void clockExplosion(Canvas canvas){
        for (int i = 0; i < clocks.size()-1;i++)
        {

            if(clocks.get(i).getY()> canvas.getHeight()){
                GameActivity.lives--;
                clocks.remove(i);
            }
            if (clocks.size()== 0){
                continue;
            }
            else {
                for (int x = 0; x  < shots.size();x++)
                {
                    if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                            (int) shots.get(x).getY(), clock, (int)clocks.get(i).getX(),
                            (int) clocks.get(i).getY())
                            ) {

                        GameActivity.clockHit = true;
                        GameActivity.score = GameActivity.score+GameActivity.incScore;
                        GameActivity.scoreCounter++;
                        clocks.remove(i);
                        GameActivity.explosion.start();
                        canvas.drawBitmap(boom,(int)shots.get(i).getX(),(int) shots.get(i).getY(), null);
                        shots.remove(x);

                    }

                }
            }
        }
    }

        public void weaponExplosion(Canvas canvas){
            for (int i = 0; i < weapons.size()-1;i++)
            {

                if(weapons.get(i).getY()> canvas.getHeight()){
                    weapons.remove(i);
                }
                if (weapons.size()== 0){
                    continue;
                }
                else {
                    for (int x = 0; x  < shots.size();x++)
                    {
                        if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                                (int) shots.get(x).getY(), gun, (int)weapons.get(i).getX(),
                                (int) weapons.get(i).getY())
                                ) {

                            wep = random.nextInt(4);
                            weapons.remove(i);
                            GameActivity.item.start();
                            canvas.drawBitmap(boom,(int)shots.get(i).getX(),(int) shots.get(i).getY(), null);
                            shots.remove(x);

                        }

                    }
                }
            }
        }

        public void heartExplosion(Canvas canvas){
            for (int i = 0; i < hearts.size()-1;i++)
            {

                if(hearts.get(i).getY()> canvas.getHeight()){
                    GameActivity.lives--;
                    hearts.remove(i);
                }
                if (hearts.size()== 0){
                    continue;
                }
                else {
                    for (int x = 0; x  < shots.size();x++)
                    {
                        if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                                (int) shots.get(x).getY(), heart, (int)hearts.get(i).getX(),
                                (int) hearts.get(i).getY())
                                ) {
                            GameActivity.lives++;
                            GameActivity.score = GameActivity.score+GameActivity.incScore;
                            GameActivity.scoreCounter++;
                            hearts.remove(i);
                            GameActivity.lifeup.start();
                            canvas.drawBitmap(boom,(int)shots.get(i).getX(),(int) shots.get(i).getY(), null);
                            shots.remove(x);

                        }

                    }
                }
            }
        }
        public void tntExplosion(Canvas canvas){
            for (int i = 0; i < tnts.size()-1;i++)
            {

                if(tnts.get(i).getY()> canvas.getHeight()){
                    GameActivity.lives = GameActivity.lives - 2;
                    tnts.remove(i);
                }
                if (tnts.size()== 0){
                    continue;
                }
                else {
                    for (int x = 0; x  < shots.size();x++)
                    {
                        if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                                (int) shots.get(x).getY(), heart, (int)tnts.get(i).getX(),
                                (int) tnts.get(i).getY())
                                ) {

                            GameActivity.score = GameActivity.score+(GameActivity.incScore*2);
                            GameActivity.scoreCounter++;
                            tnts.remove(i);
                            GameActivity.explosion.start();
                            canvas.drawBitmap(boom,(int)shots.get(i).getX(),(int) shots.get(i).getY(), null);
                            shots.remove(x);

                        }

                    }
                }
            }
        }

        public void surgeExplosion(Canvas canvas){
            for (int i = 0; i < surge.size()-1;i++)
            {

                if(surge.get(i).getY()> canvas.getHeight()){
                    GameActivity.lives = GameActivity.lives - 1;
                    surge.remove(i);
                }
                if (surge.size()== 0){
                    continue;
                }
                else {
                    for (int x = 0; x  < shots.size();x++)
                    {
                        if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                                (int) shots.get(x).getY(), snowman, (int)surge.get(i).getX(),
                                (int) surge.get(i).getY())
                                ) {

                            GameActivity.score = GameActivity.score + GameActivity.incScore;
                            GameActivity.scoreCounter++;
                            surge.remove(i);
                            shots.remove(x);
                            GameActivity.item.start();
                            canvas.drawBitmap(boom,(int)shots.get(i).getX(),(int) shots.get(i).getY(), null);
                            GameActivity.surgeHit = true;


                        }

                    }
                }
            }
        }

        public void miniExplosion(Canvas canvas){
            for (int i = 0; i < minis.size()-1;i++)
            {

                if(minis.get(i).getY()> canvas.getHeight()){
                    GameActivity.lives--;
                    minis.remove(i);
                }
                if (minis.size()== 0){
                    continue;
                }
                else {
                    for (int x = 0; x  < shots.size();x++)
                    {
                        if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                                (int) shots.get(x).getY(), heart, (int)minis.get(i).getX(),
                                (int) minis.get(i).getY())
                                ) {

                            GameActivity.score = GameActivity.score + (GameActivity.incScore*3);
                            GameActivity.scoreCounter++;
                            minis.remove(i);
                            GameActivity.explosion.start();
                            canvas.drawBitmap(boom, (int) shots.get(i).getX(), (int) shots.get(i).getY(), null);
                            shots.remove(x);

                        }

                    }
                }
            }
        }
        public void nukeExplosion(Canvas canvas){
            for (int i = 0; i < nukes.size()-1;i++)
            {

                if(nukes.get(i).getY()> canvas.getHeight()){
                    GameActivity.lives = 0;
                    nukes.remove(i);
                }
                if (nukes.size()== 0){
                    continue;
                }
                else {
                    for (int x = 0; x  < shots.size();x++)
                    {
                        if ( isCollisionDetected(laser, (int) shots.get(x).getX(),
                                (int) shots.get(x).getY(), heart, (int)nukes.get(i).getX(),
                                (int) nukes.get(i).getY())
                                ) {
                            GameActivity.explosion.start();
                            GameActivity.score = GameActivity.score + (GameActivity.incScore*5);
                            GameActivity.scoreCounter++;
                            canvas.drawBitmap(boom,(int)nukes.get(i).getX(),(int) nukes.get(i).getY(), null);
                            nukes.remove(i);
                            tnts.clear();
                            surge.clear();
                            multi.clear();
                            weapons.clear();
                            clocks.clear();
                            bombs.clear();
                            minis.clear();


                        }

                    }
                }
            }
        }

        public void explosionType(){

            if(wep == 1){
                boom = BitmapFactory.decodeResource(getResources(), R.drawable.bats);
            }else if (wep == 2){
                boom = BitmapFactory.decodeResource(getResources(), R.drawable.gas);
            }else if (wep == 3){
                boom = BitmapFactory.decodeResource(getResources(), R.drawable.fireexplosion);
            }else{
                boom = BitmapFactory.decodeResource(getResources(), R.drawable.boom);
            }
        }

        public void removeLaser() {

            for (int x = 0; x < shots.size() - 1; x++) {
                if(shots.get(x).getY()< 0){

                    shots.remove(x);
                }
            }
        }


        public  boolean isCollisionDetected(Bitmap bitmap1, int x1, int y1,
                                                  Bitmap bitmap2, int x2, int y2) {

            Rect bounds1 = new Rect(x1, y1, x1+bitmap1.getWidth(), y1+bitmap1.getHeight());
            Rect bounds2 = new Rect(x2, y2, x2+bitmap2.getWidth(), y2+bitmap2.getHeight());

            if (Rect.intersects(bounds1, bounds2)) {
                Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
                for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                    for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                        int bitmap1Pixel = bitmap1.getPixel(i-x1, j-y1);
                        int bitmap2Pixel = bitmap2.getPixel(i-x2, j-y2);
                        if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private  Rect getCollisionBounds(Rect rect1, Rect rect2) {
            int left =  Math.max(rect1.left, rect2.left);
            int top =  Math.max(rect1.top, rect2.top);
            int right = Math.min(rect1.right, rect2.right);
            int bottom =  Math.min(rect1.bottom, rect2.bottom);
            return new Rect(left, top, right, bottom);
        }

        private boolean isFilled(int pixel) {
            return pixel != Color.TRANSPARENT;
        }
		        public boolean onTouchEvent(MotionEvent event)
        {
            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;

            boolean handled = false;

            int xTouch;
            int actionIndex = event.getActionIndex();

            switch (event.getAction() & event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // it's the first pointer, so clear all existing pointers data

                    xTouch = (int) event.getX(0);


                    if(GameActivity.reset){
                        GameActivity.resetTimer();
                    }
                    if(GameActivity.notDone) {

                        shootLaser(xTouch);

                    }




                    break;

                case MotionEvent.ACTION_POINTER_DOWN:



                    break;
                case MotionEvent.ACTION_MOVE:

                    break;

                case MotionEvent.ACTION_HOVER_MOVE:


                    break;

                case MotionEvent.ACTION_UP:


                    break;

                case MotionEvent.ACTION_POINTER_UP:

                    break;

                case MotionEvent.ACTION_CANCEL:

                    break;

                default:
                    // do nothing
                    break;
            }


            return true;

        }


		public void setRunning(boolean bRun)
		{
			GameActivity.running = bRun;
		}
		
		public boolean getRunning()
		{
			return GameActivity.running;
		}
	}


	/** The thread that actually draws the animation */
	private BombThread eThread;
	
	public GameView(Context context)
	{
		super(context);
		
		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		// create thread only; it's started in surfaceCreated()
		eThread = new BombThread(holder, context, new Handler()
		{
			@Override
			public void handleMessage(Message m)
			{

			}
		});
		
		setFocusable(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return eThread.onTouchEvent(event);
	}

	public BombThread getThread()
	{
		return eThread;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder)
	{
		if (eThread.getState() == Thread.State.TERMINATED)
		{
			eThread = new BombThread(getHolder(), getContext(), getHandler());
			eThread.start();
		}
		else
		{
			eThread.start();
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean retry = true;
		eThread.setRunning(false);
		
		while (retry)
		{
			try
			{
				eThread.join();
				retry = false;
			}
			catch (InterruptedException e)
			{
			}
		}
	}


}
