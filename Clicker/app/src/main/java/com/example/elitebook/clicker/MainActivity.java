package com.example.elitebook.clicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView countDownTimer;
    private TextView totalClicks;
    private Button clickButton;
    private boolean firstClick;
    private boolean timerDone = false;
    private boolean canRestart = false;
    private int timesClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownTimer = (TextView) findViewById(R.id.countDown);
        totalClicks = (TextView) findViewById(R.id.totalTimesClicked);

        clickButton = (Button)(findViewById(R.id.pressThis));

        firstClick = true;

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstClick) {
                    runTimer();
                    firstClick = false;
                    timesClicked++;
                    String clicks = getText(R.string.clicks).toString().concat(""+timesClicked);
                    totalClicks.setText(clicks);
                }
                if (!timerDone) {
                    timesClicked++;
                    String clicks = getText(R.string.clicks).toString().concat(" "+timesClicked);
                    totalClicks.setText(clicks);
                }
                if(canRestart){

                    Intent thisIntent = getIntent();
                    finish();
                    startActivity(thisIntent);

                }
            }
        });
    }

    private void runTimer() {

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownTimer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countDownTimer.setText("Finished !");
                timerDone = true;

                Runnable r = new Runnable() {
                    @Override
                    public void run(){

                        String restartText = getText(R.string.restartButton).toString();
                        clickButton.setText(restartText);
                        canRestart = true;
                    }
                };

                Handler h = new Handler();
                h.postDelayed(r, 1500);
            }
        }.start();
    }
}
