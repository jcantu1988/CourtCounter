package com.jucantu.courtcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final int ON_FIRE_STREAK = 3;
    private int scoreTeamA = 0;
    private int streakTeamA = 0;

    private int scoreTeamB = 0;
    private int streakTeamB = 0;

    private StringBuilder bodyMessage = new StringBuilder("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayForTeamA(scoreTeamA);
    }

    /**
     * Increases the score for Team A by 3.
     */
    public void plusThreeTeamA(View view){
        scoreTeamA += 3;

        streakTeamA++;
        if(streakTeamA == ON_FIRE_STREAK){
            ImageView onFireImageTeamA = (ImageView) findViewById(R.id.team_a_on_fire);
            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
            onFireImageTeamA.startAnimation(animation1);
            //onFireImageTeamA.setVisibility(View.VISIBLE);
        }

        displayForTeamA(scoreTeamA);
        updateBodyMessage();
        resetOnFireTeamForTeamB();
    }

    /**
     * Increases the score for Team A by 2.
     */
    public void plusTwoTeamA(View view){
        scoreTeamA += 2;
        displayForTeamA(scoreTeamA);
        updateBodyMessage();
        resetOnFireTeamForTeamB();
    }

    /**
     * Increases the score for Team A by 1.
     */
    public void freeThrowTeamA(View view){
        scoreTeamA += 1;
        displayForTeamA(scoreTeamA);
        updateBodyMessage();
        resetOnFireTeamForTeamB();
    }
    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Removes the "On Fire" image for Team B.
     */
    public void resetOnFireTeamForTeamB(){
        if(streakTeamB >= ON_FIRE_STREAK){
            ImageView onFireImageTeamB = (ImageView) findViewById(R.id.team_b_on_fire);
           // onFireImageTeamB.setVisibility(View.INVISIBLE);
           // onFireImageTeamB.clearAnimation();

            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
            onFireImageTeamB.startAnimation(animation1);
        }
        streakTeamB = 0;
    }

    /**
     * Increases the score for Team B by 3.
     */
    public void plusThreeTeamB(View view){
        scoreTeamB += 3;

        streakTeamB++;
        if(streakTeamB == ON_FIRE_STREAK){
            ImageView onFireImageTeamB = (ImageView) findViewById(R.id.team_b_on_fire);
            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
            onFireImageTeamB.startAnimation(animation1);
            //onFireImageTeamB.setVisibility(View.VISIBLE);
        }

        displayForTeamB(scoreTeamB);
        updateBodyMessage();
        resetOnFireTeamForTeamA();
    }

    /**
     * Increases the score for Team B by 2.
     */
    public void plusTwoTeamB(View view){
        scoreTeamB += 2;
        displayForTeamB(scoreTeamB);
        updateBodyMessage();
        resetOnFireTeamForTeamA();
    }

    /**
     * Increases the score for Team B by 1.
     */
    public void freeThrowTeamB(View view){
        scoreTeamB += 1;
        displayForTeamB(scoreTeamB);
        updateBodyMessage();
        resetOnFireTeamForTeamA();
    }
    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Removes the "On Fire" image for Team A.
     */
    public void resetOnFireTeamForTeamA(){
        if(streakTeamA >= ON_FIRE_STREAK){
            ImageView onFireImageTeamA = (ImageView) findViewById(R.id.team_a_on_fire);
            //onFireImageTeamA.setVisibility(View.INVISIBLE);
            //onFireImageTeamA.clearAnimation();


            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
            onFireImageTeamA.startAnimation(animation1);
        }
        streakTeamA = 0;
    }

    /**
     * Sets the scores for teamA and teamB to 0.
     */
    public void resetScores(View view){
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        resetOnFireTeamForTeamA();
        resetOnFireTeamForTeamB();

    }

    public void updateBodyMessage(){
        Calendar c = Calendar.getInstance();
        Date date =  c.getTime();
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss.SSS");
        String dateString = sdfDate.format(date);
        String timeString = sdfTime .format(date);

        bodyMessage.append("Score on " + dateString + " at " + timeString + "\n Team A:" + scoreTeamA + "\t Team B:" + scoreTeamB +"\n\n");
    }

    public void sendEmail(View view){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"example@domain.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Court Counter Score");
        i.putExtra(Intent.EXTRA_TEXT   , bodyMessage.toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
