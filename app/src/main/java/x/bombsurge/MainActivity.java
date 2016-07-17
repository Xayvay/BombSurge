package x.bombsurge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
//import com.google.android.gms.games.Games;



public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener, GoogleApiClient.ConnectionCallbacks {
    SharedPreferences bestScore, isActive;
    public static MediaPlayer gameSong;
    public static MediaPlayer intro;
    public static MediaPlayer blaster;
    public static MediaPlayer laser;
    public static boolean isQuit = false;
    public static boolean musicSet;
    public boolean signingoff;
    public static boolean gameAudioSet = false;




    private static final String TAG = "MainActivity";

    private static final String KEY_IN_RESOLUTION = "is_in_resolution";

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;


    private GoogleApiClient mGoogleApiClient;

    boolean mExplicitSignOut = false;
    boolean mInSignInFlow = false; // set to true when you're in the middle of the
    // sign in flow, to know you should not attempt
    // to connect in onStart()
    /**
     * Determines if the client is in a resolution state, and
     * waiting for resolution intent to return.
     */
    private boolean mIsInResolution;
    private TextView mStatusTextView;
    protected static final int REQUEST_CODE_RESOLUTION = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                .build();

        if (savedInstanceState != null) {
            mIsInResolution = savedInstanceState.getBoolean(KEY_IN_RESOLUTION, false);
        }

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        //constants
        GameActivity.score = 0;
        bestScore = getSharedPreferences("Personal Best", 0);
        isActive = getSharedPreferences("stillActive", 0);
        GameActivity.personalBest = bestScore.getInt("PB", 0);
        GameActivity.gameActive = isActive.getBoolean("game", false);
        TextView mainPB = (TextView)findViewById(R.id.mainPB);
        mainPB.setText(Globals.PB_DISPLAY + GameActivity.personalBest);
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //buttons setup
        View btnNewGame = findViewById(R.id.buttonNewGame);
        btnNewGame.setOnClickListener(this);
        View btnHelp = findViewById(R.id.buttonHelp);
        btnHelp.setOnClickListener(this);
        View btnExit = findViewById(R.id.buttonExit);
        btnExit.setOnClickListener(this);
        View toggleMusic = findViewById(R.id.music);
        toggleMusic.setOnClickListener(this);
        View toggleAudio = findViewById(R.id.gameaudio);
        toggleAudio.setOnClickListener(this);
        View btnleaderboard = findViewById(R.id.leaderboards);
        btnleaderboard.setOnClickListener(this);




        //sound
        intro = MediaPlayer.create(getApplicationContext(), R.raw.intro);
        intro.setVolume(0.2f, 0.2f);
        gameSong = MediaPlayer.create(getApplicationContext(), R.raw.bombsong);
        gameSong.setVolume(0.2f, 0.2f);
        blaster = MediaPlayer.create(getApplicationContext(), R.raw.phaser);
        laser = MediaPlayer.create(getApplicationContext(), R.raw.lasershot);
        musicSet = true;
        intro.start();
        intro.setLooping(true);
        gameSong.start();
        gameSong.pause();





        //Ad request
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    protected void onStart() {
        super.onStart();
        View resume = findViewById(R.id.resume);
        resume.setOnClickListener(this);
        if(GameActivity.gameActive == true){
            resume.setVisibility(View.VISIBLE);
        }
        if (!mInSignInFlow && !mExplicitSignOut) {
            // auto sign in
            mGoogleApiClient.connect();
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    // Optionally, add additional APIs and scopes if required.
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mGoogleApiClient.connect();

    }

    /**
     * Called when activity gets invisible. Connection to Play Services needs to
     * be disconnected as soon as an activity is invisible.
     */
    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private void quitApplication(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.exit)
                .setMessage("SO YOU WANT TO LEAVE ?!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isQuit = true;
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonNewGame:
                startActivity(new Intent(this, GameActivity.class));
                intro.pause();
                if(musicSet) {
                    gameSong.seekTo(0);
                    gameSong.start();
                    gameSong.setLooping(true);
                }
                if(gameAudioSet){

                }else {
                    blaster.start();
                }
                break;
            case R.id.buttonHelp:
                startActivity(new Intent(this, Help.class));
                intro.pause();
                if(gameAudioSet){

                }else {
                    blaster.start();
                }
                break;

            case R.id.buttonExit:

                quitApplication();
                stopService(new Intent(this, MyPlaybackService.class));
            break;

            case R.id.music:


                if(musicSet) {
                    musicSet = false;
                    intro.pause();
                }else{
                    musicSet = true;
                    intro.start();
                }

            break;

            case R.id.gameaudio:

                if(gameAudioSet) {
                    gameAudioSet = false;
                }else{
                    gameAudioSet = true;
                }


                break;

            case R.id.sign_in_button:
                signIn();
                break;

            case R.id.sign_out_button:

                    signOut();

                break;
            case R.id.leaderboards:
                if(mGoogleApiClient.isConnected()) {
                   checkleaderboards();
                } else {
                    mGoogleApiClient.connect();
                }


                break;

        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IN_RESOLUTION, mIsInResolution);
    }


    private void retryConnecting() {
        mIsInResolution = false;
        if (!mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }



    /**
     * Called when {@code mGoogleApiClient} is connected.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "GoogleApiClient connected");
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            // signed in. Show the "sign out" button and explanation.
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            // not signed in. Show the "sign in" button and explanation.
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

        }

//        MyGameProgress mGameProgress = ....;
//
//        when user finishes level:
//        mGameProgress.addScore(userScore);
//        mGameProgress.addAchievement(fooAchievement);
//        mGameProgress.addAchievement(barAchievement);
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//            mGameProgress.save(mGoogleApiClient);
//        }

//        if (mGameProgress != null) {
//            mGameProgress.save(mGoogleApiClient);
//        }
    }

    /**
     * Called when {@code mGoogleApiClient} connection is suspended.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "GoogleApiClient connection suspended");
        mGoogleApiClient.connect();
        retryConnecting();
    }


    /**
     * Called when {@code mGoogleApiClient} is trying to connect but failed.
     * Handle {@code result.getResolution()} if there is a resolution
     * available.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            // Show a localized error dialog.
            GooglePlayServicesUtil.getErrorDialog(
                    result.getErrorCode(), this, 0, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            retryConnecting();
                        }
                    }).show();
            return;
        }
        // If there is an existing resolution error being displayed or a resolution
        // activity has started before, do nothing and wait for resolution
        // progress to be completed.
        if (mIsInResolution) {
            return;
        }
        mIsInResolution = true;
        try {
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
            retryConnecting();
        }

        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }
        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
//            if (BaseGameUtils.resolveConnectionFailure(this,
//                                mGoogleApiClient, result,
//                                RC_SIGN_IN, R.string.signin_other_error)) return;
//            mResolvingConnectionFailure = false;
        }

    }





    @Override
    protected void onRestart() {
        super.onRestart();

        if(MainActivity.isQuit) {
            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
            System.exit(0);
        }
    }


    private void signIn(){

        // start the asynchronous sign in flow
        mSignInClicked = true;
        mGoogleApiClient.connect();

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);



}

    private void signOut(){

        if(mGoogleApiClient.isConnected()) {
//            Games.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();

        }

        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        findViewById(R.id.sign_out_button).setVisibility(View.GONE);


    }

    //Leaderboards
    public void checkleaderboards(){
        TextView mainPB = (TextView)findViewById(R.id.mainPB);
        mainPB.setText(Globals.PB_DISPLAY + GameActivity.personalBest);
        Games.Leaderboards.submitScore(mGoogleApiClient, Globals.LEADERBOARD_ID, GameActivity.personalBest);
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                Globals.LEADERBOARD_ID), Globals.REQUEST_LEADERBOARD);
    }

    @Override
    public void onBackPressed() {
    }

}
