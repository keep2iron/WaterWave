package io.github.keep2iron.waterwave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author keep2iron <a href="http://keep2iron.github.io">Contract me.</a>
 * @version 1.0
 * @since 2017/07/26 17:13
 */
public class WaterWaveView extends View {

    int animValue;
    private PaintFlagsDrawFilter canvasFilter;

    private WaterWave mWaterWave1;
    private WaterWave mWaterWave2;

    public WaterWaveView(Context context) {
        this(context, null);
    }

    public WaterWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WaterWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        canvasFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    ValueAnimator valueAnimator;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            valueAnimator = ValueAnimator.ofInt(0, getWidth());
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(5000)
                    .setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animValue = (int) animation.getAnimatedValue();

                    if(mOnWaveListener != null)
                        mOnWaveListener.onWave((int) mWaterWave1.computeY(animValue));

                    invalidate();
                }
            });
            valueAnimator.start();
        }
    };

    public void startWave() {
        post(mRunnable);
    }

    public void stopWave() {
        if (valueAnimator != null)
            valueAnimator.cancel();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWaterWave1 = new WaterWave(w, h, Color.parseColor("#FF3366"));
        mWaterWave1.computePath();

        mWaterWave2 = new WaterWave(w, h, Color.parseColor("#FF6699"));
        mWaterWave2.setQ(w * 1.0f / 3);
        mWaterWave2.computePath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        canvas.setDrawFilter(canvasFilter);
        canvas.translate(animValue, 0);

        canvas.drawPath(mWaterWave2.buildPath(), mWaterWave2.getWaterWavePaint());
        canvas.drawPath(mWaterWave1.buildPath(), mWaterWave1.getWaterWavePaint());

        canvas.restore();
    }

    OnWaveListener mOnWaveListener;

    public void setOnWaveListener(OnWaveListener onWaveListener) {
        mOnWaveListener = onWaveListener;
    }

    public interface OnWaveListener {
        void onWave(int y);
    }
}
