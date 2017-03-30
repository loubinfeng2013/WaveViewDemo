package com.loubinfeng.www.waveviewdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.animation.ValueAnimator.INFINITE;

/**
 * Created by loubinfeng on 2017/3/30.
 */

public class WaveView extends View implements ValueAnimator.AnimatorUpdateListener{

    //用来填充波浪的画笔
    private Paint mPaint;
    //波浪曲线的路径
    private Path mPath;
    //控件的宽
    private int mViewWidth;
    //控件的高
    private int mViewHeight;
    //一个波浪的宽度,一起一伏为一个波浪
    private int mWaveWidth;
    //一共需要绘制4个波浪
    private int mWaveCount = 4;
    //绘制bezier曲线的控制点的高度
    private float mFlagHeight;
    //原点的纵向位置
    private float mStartY;
    private float mStartMaxY;
    private float mStartMinY;
    //偏移量
    private int offset;
    //控制offset值动画
    private ValueAnimator mValueAnimator;
    private Handler mHandler = new Handler();
    private int mProgress = 0;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#35000000"));
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mWaveWidth = w / 2 ;
        mFlagHeight = h / 12;
        mStartMinY = mViewHeight - mFlagHeight * 2;
        mStartMaxY = mFlagHeight * 2;
        mStartY = mStartMinY;
        mValueAnimator = ValueAnimator.ofInt(-mWaveWidth,0);
        mValueAnimator.setDuration(800);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(INFINITE);
        mValueAnimator.addUpdateListener(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mValueAnimator.start();
            }
        },200);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null){
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWaveWidth,mStartY);
        for (int i = 0 ; i < mWaveCount ; i++){
            mPath.quadTo(-mWaveWidth+mWaveWidth/4+i*mWaveWidth+offset,mStartY + mFlagHeight,-mWaveWidth+mWaveWidth/2+i*mWaveWidth+offset,mStartY);
            mPath.quadTo(-mWaveWidth+mWaveWidth*3/4+i*mWaveWidth+offset,mStartY-mFlagHeight,-mWaveWidth+mWaveWidth+i*mWaveWidth+offset,mStartY);
        }
        mPath.lineTo(mViewWidth,mViewHeight);
        mPath.lineTo(0,mViewHeight);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        offset = (Integer) valueAnimator.getAnimatedValue();
        invalidate();
    }

    /**
     * 设置进度
     */
    public void setProgress(int progress){
        if (progress < 0 || progress > 100)
            return;
        mProgress = progress;
        mStartY = mStartMinY - (float) progress / 100 * Math.abs(mStartMaxY - mStartMinY);
        invalidate();
    }
}
