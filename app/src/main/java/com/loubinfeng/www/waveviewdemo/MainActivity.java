package com.loubinfeng.www.waveviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleRectWaveView(View view){
        startActivity(new Intent(this,WaveViewActivity.class));
    }

    public void handleCircleWaveView(View view){
        startActivity(new Intent(this,WaveCircleViewActivity.class));
    }
}
