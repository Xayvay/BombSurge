package x.bombsurge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity
{
    private GameView gView;
    public static MediaPlayer comboup,combovoice,combocharge,thunder,strike,explosion,item,lifeup,combodown,gameover;
    public static boolean running,notDone,gameActive,dropClock,clockHit,wizardAttack,multiHit,weaponHit,boltHit,snowmanHit,surgeHit,lightning,comboStart,reset,comboI1,comboI2,gameOver;
    DisplayMetrics metrics = new DisplayMetrics();
    public static Paint dimblue,dimyellow;
    public static boolean go = false;
    public static int surger,displayWidth,displayHeight,score,lives,personalBest,scoreCounter,
         laserRate,heartdrop,nukedrop,tntdrop,minidrop,multidrop,weapondrop,surgedrop,cloudmove;
    public static int timeSurge = 6;
    public static long sec;
    public static float rateInc,speedInc;
    public static int incScore,comboTrigger,combo2trig;
    public static int timeInc,timePlus;
    public static CountDownTimer timer,combocombo;
    public static String hms;
    public static MenuItem clock,points;
    private static Context mContext;
    public static Activity gActivity;




	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        ActionBar actionBar = getSupportActionBar();
        //actionBar.show();
        //actionBar.setDisplayShowTitleEnabled(false);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        gActivity = this;
        comboI1 = false;
        comboI2 = false;
        gameOver = false;
        reset = false;
        comboStart = true;
        displayWidth = metrics.widthPixels;
        gView = new GameView(this);
        setContentView(gView);
        gameActive = true;
        mContext = this;
        combo2trig = 0;
        combovoice = MediaPlayer.create(getApplicationContext(), R.raw.combovoice);
        comboup = MediaPlayer.create(getApplicationContext(), R.raw.comboup);
        thunder = MediaPlayer.create(getApplicationContext(), R.raw.thunder);
        strike = MediaPlayer.create(getApplicationContext(), R.raw.lightning);
        explosion = MediaPlayer.create(getApplicationContext(), R.raw.explosion);
        item = MediaPlayer.create(getApplicationContext(), R.raw.itemup);
        lifeup = MediaPlayer.create(getApplicationContext(), R.raw.oneup);
        combocharge = MediaPlayer.create(getApplicationContext(), R.raw.combocharge);
        combodown = MediaPlayer.create(getApplicationContext(), R.raw.combodown);
        gameover = MediaPlayer.create(getApplicationContext(), R.raw.gameover);
        lightning = false;
        lives = 3;
        comboTrigger = 0;
        incScore =1;
        surgedrop = 1;
        weapondrop = 1;
        heartdrop = 1;
        nukedrop = 1;
        tntdrop = 1;
        minidrop = 1;
        multidrop = 1;
        cloudmove = 1;
        timePlus = 6000;
        laserRate = 100;
        scoreCounter = 0;
        displayHeight = metrics.heightPixels;
        timeInc = 15;
        score = 0;
        dropClock = false;
        weaponHit = false;
        clockHit = false;
        notDone = true;
        sec = 20000;
        go = false;
        wizardAttack = false;
        multiHit = false;

        speedDetermination();

        if(MainActivity.gameAudioSet) {
            comboup.setVolume(0.0f,0.0f);
            combovoice.setVolume(0.0f, 0.0f);
            thunder.setVolume(0.00f, 0.00f);
            strike.setVolume(0.05f, 0.05f);
            explosion.setVolume(0.0f, 0.0f);
            item.setVolume(0.0f, 0.0f);
            lifeup.setVolume(0.0f, 0.0f);
            combocharge.setVolume(0.0f, 0.0f);
            combodown.setVolume(0.0f, 0.0f);
            gameover.setVolume(0.0f, 0.0f);

        }else{
            comboup.setVolume(0.5f,0.5f);
            combovoice.setVolume(1.0f, 1.0f);
            thunder.setVolume(1.05f, 1.05f);
            strike.setVolume(1.05f, 1.05f);
            explosion.setVolume(0.1f, 0.1f);
            item.setVolume(1.0f, 1.0f);
            lifeup.setVolume(1.0f, 1.0f);
            combocharge.setVolume(1.0f, 1.0f);
            combodown.setVolume(0.5f, 0.5f);
            gameover.setVolume(1.0f, 1.0f);
        }

    }


    public static void comboTimer (){

       combocombo = new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {

                if(comboTrigger == 3){
                    combocharge.start();
                    comboTrigger++;
                }
            }

            public void onFinish() {
                if(notDone) {
                    combo();

                    comboStart = true;
                }


            }
        }.start();
    }


    public  static void startTimer (long time){

        reset = false;


     timer = new CountDownTimer(time, 1000) {


        public void onTick(long millis) {
            sec = millis;
            hms = (TimeUnit.MILLISECONDS.toHours(millis)) + ":" + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))) + ":" + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            points.setTitle("" + score);
            clock.setTitle(hms);

            if (lives <= 0 ) {
                timer.cancel();
                timer.onFinish();
            }

            if (score / timeInc == 1) {
                timeInc = timeInc + timeInc;
                rateInc = rateInc + 1.1f;
                timePlus = 8000;
                    reset = true;


            }

            if (clockHit) {
                timePlus = 2000;
                clockHit = false;
                    reset = true;

            }


            if (surgeHit) {
                surgeTimer();
            }

            if(comboStart){
                comboStart=false;
                comboTimer();


            }
        }

        public void onFinish() {
                timer.cancel();
                notDone = false;
                clock.setTitle("Times Up!");
                gameActive = false;
                gameover.start();
                if (score > personalBest) {
                    personalBest = score;
                }
            if(gameOver == false) {
                gameOver = true;
                endGameTimer();
            }
            }

    }.start();
}

    public static void surgeTimer (){
    if(surger >= 1){
        snowmanHit = true;
        dimblue.setAlpha(50);
        rateInc = rateInc - timeSurge;

    } else{
        boltHit = true;
        dimyellow.setAlpha(50);
        rateInc = GameActivity.rateInc + timeSurge;
    }

        surgeHit = false;
    CountDownTimer surgtimer = new CountDownTimer(11000, 1000) {

        public void onTick(long millisUntilFinished) {

        }

        public void onFinish() {
            if(boltHit == true){
                GameActivity.rateInc = GameActivity.rateInc - timeSurge;
            } else {
                GameActivity.rateInc = GameActivity.rateInc + timeSurge;
            }

            boltHit = false;
            snowmanHit = false;
            dimblue.setAlpha(0);
            dimyellow.setAlpha(0);

        }
    }.start();
}

    public static void endGameTimer (){

        CountDownTimer eg = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                sec = millisUntilFinished;
            }
            public void onFinish() {

                MainActivity.gameSong.pause();
                timer.cancel();
                callResults();
                gActivity.finish();
                thunder.pause();
                strike.pause();

            }
        }.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game, menu);
        points = menu.findItem(R.id.score);
        points.setTitle("Score: " + score);
        clock = menu.findItem(R.id.counter);
        startTimer(sec);
        return super.onCreateOptionsMenu(menu);


    }
//TODO:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainMenu:

                notDone = false;
                timer.cancel();
                endPlay();
                return true;
            case R.id.play:
                notDone = true;
                startTimer(sec);
                MainActivity.gameSong.start();
                comboTimer();
                return true;
            case R.id.pause:
                notDone = false;
                timer.cancel();
                MainActivity.gameSong.pause();
                combocombo.cancel();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void endPlay(){
        new AlertDialog.Builder(this)
                .setTitle("Return to Main Menu")
                .setMessage("Are you sure you want to go back?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        gameActive = false;
                        running = false;
                        MainActivity.gameSong.pause();

                        if(MainActivity.musicSet) {
                            MainActivity.intro.start();

                        }
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                     notDone = true;
                     timer.start();
                    }
                })
                .show();
    }


    @Override
    protected void onPause()
    {
        super.onPause();

        notDone = false;
        MainActivity.gameSong.pause();

        timer.cancel();

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(MainActivity.musicSet){

        }

    }

	@Override
	protected void onStop()
	{
		try {
			gView.getThread().setRunning(false);
			gView = null;
		}
		catch (NullPointerException e) {}

        running = false;
        super.onStop();
	}

    public static void resetTimer(){
        timer.cancel();
        sec = sec + timePlus;
        startTimer(sec);
    }
    public static void callResults(){
        Intent result = new Intent(mContext, Results.class);
        mContext.startActivity(result);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(MainActivity.isQuit)
            finish();
    }

    public static void combo () {

        if(scoreCounter >= 11 && comboTrigger <= 3) {
            incScore++;
            comboTrigger++;
            comboup.start();
            comboI1 = true;
            combovoice.start();
            scoreCounter = 0;
            thunder.seekTo(0);
            thunder.start();
            thunder.setLooping(true);
            combo2trig++;
        }else if (scoreCounter >= 11 && comboTrigger >= 3 ){
            lightning = true;
            strike.seekTo(0);
            strike.start();
            strike.setLooping(true);
            scoreCounter = 0;
            comboI2 = false;
            comboI1=false;

        }else if (scoreCounter < 11 && comboTrigger >= 3){
            combodown.start();
            incScore = 1;
            scoreCounter = 0;
            comboTrigger = 0;
            lightning=false;
            comboI2 = false;
            comboI1=false;
            combo2trig = 0;
            thunder.pause();
            strike.pause();
        }else if (scoreCounter < 11 && comboTrigger > 0){
            combodown.start();
            incScore = 1;
            scoreCounter = 0;
            comboTrigger = 0;
            combo2trig = 0;
            lightning=false;
            comboI2 = false;
            comboI1=false;
            thunder.pause();
            strike.pause();
        }else{
            comboI2 = false;
            comboI1=false;
            incScore = 1;
            scoreCounter = 0;
            comboTrigger = 0;
            combo2trig = 0;
            lightning=false;
            thunder.pause();
            strike.pause();

        }


    }

    public static void speedDetermination(){
        if(displayHeight<1000){
            rateInc = 0.4f;
            speedInc = 0.2f;
        }else{
            rateInc = 1.3f;
            speedInc = 0.9f;
        }
    }


    @Override
    public void onBackPressed() {
    }

}