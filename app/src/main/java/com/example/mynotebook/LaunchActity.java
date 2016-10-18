package com.example.mynotebook;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This activity is a launcher.
 * @author Zheng Zhang
 * Created by Zheng Zhang on 2016/3/17.
 */
public class LaunchActity extends AppCompatActivity {
    private final int DELAY = 800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_actity);
        //set launcher action, delay 800ms
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(LaunchActity.this,
                        MainActivity.class);
                LaunchActity.this.startActivity(mainIntent);
                LaunchActity.this.finish();
            }
        }, DELAY);
    }
}
