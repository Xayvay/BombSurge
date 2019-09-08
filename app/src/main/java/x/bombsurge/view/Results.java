package x.bombsurge.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import x.bombsurge.Globals;
import x.bombsurge.R;
import x.bombsurge.controller.GameActivity;
import x.bombsurge.controller.MainActivity;

/**
 * Created by X on 9/29/2015.
 */
public class Results implements View.OnClickListener
{

     SharedPreferences bestScore;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        View main = findViewById(R.id.main);
        main.setOnClickListener(this);
        View replay = findViewById(R.id.replay);
        replay.setOnClickListener(this);
        TextView tv1 = (TextView)findViewById(R.id.finalscore);
        TextView pr = (TextView)findViewById(R.id.personalBest);
        bestScore = getSharedPreferences("Personal Best", 0);
        SharedPreferences.Editor editor = bestScore.edit();
        editor.putInt("PB", GameActivity.personalBest);
        editor.commit();
        GameActivity.timer.cancel();
      //0 is the default value

        pr.setText(Globals.PB_DISPLAY + GameActivity.personalBest);
        tv1.setText(Globals.RESULTS_DISPLAY + GameActivity.score);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        return super.onCreateOptionsMenu(menu);

    }


    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.main:

                MainActivity.gameSong.pause();

                if(MainActivity.musicSet) {
                    MainActivity.intro.start();
                }
                finish();
                break;

            case R.id.replay:
            {

                if(MainActivity.musicSet) {
                    MainActivity.gameSong.seekTo(0);
                    MainActivity.gameSong.start();
                    MainActivity.gameSong.setLooping(true);
                }
                startActivity(new Intent(this, GameActivity.class));
                finish();
                break;
            }

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(MainActivity.isQuit)
            finish();
    }

    @Override
    public void onBackPressed() {
    }

}