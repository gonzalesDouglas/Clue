package com.dgonzales.douglas.clue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Splashscreen extends AppCompatActivity {
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();

    }

    private void startApp() {
        startActivity(new Intent(Splashscreen.this,loginuser.class));

    }

    private void doWork() {
        for (int progress=0; progress<50; progress+=10) {
            try {
                Thread.sleep(1000);
                mProgressBar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
