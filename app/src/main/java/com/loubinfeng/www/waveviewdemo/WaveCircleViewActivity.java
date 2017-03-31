package com.loubinfeng.www.waveviewdemo;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.loubinfeng.www.waveviewdemo.view.WaveCircleView;

public class WaveCircleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_circle_view);
        final WaveCircleView waveView = (WaveCircleView)findViewById(R.id.waveCircleView);
        ValueAnimator animator = ValueAnimator.ofInt(0,100);
        animator.setDuration(5000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                waveView.setProgress(value);
            }
        });
        animator.start();
    }
}
