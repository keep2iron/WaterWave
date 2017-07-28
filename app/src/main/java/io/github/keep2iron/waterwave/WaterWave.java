package io.github.keep2iron.waterwave;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * @author keep2iron <a href="http://keep2iron.github.io">Contract me.</a>
 * @version 1.0
 * @since 2017/07/28 10:33
 */
public class WaterWave {
    private final int w;
    private final int h;

    float A = 15.0f;             //振幅
    float W = 1.0f;             //角速度
    float Q = 0.0f;             //初相
    private final Paint mWaterWavePaint;
    float K = 0.0f;                     //偏距

    private Path mPath = new Path();

    /**
     * @param w     自定义控件的宽度
     * @param h     自定义控件的高度
     */
    public WaterWave(int w,int h,int color) {
        this.w  = w;
        this.h  = h;

        mWaterWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWaterWavePaint.setDither(true);
        mWaterWavePaint.setColor(color);
        mWaterWavePaint.setStyle(Paint.Style.FILL);
        mWaterWavePaint.setStrokeWidth(5);
    }

    public void computePath() {
        Path mWavePath1 = new Path();
        Path mWavePath2 = new Path();

        K = h * 2 / 3.f;

        int span = 5;

        float x1, x2, y1, y2 = 0;
        mWavePath1.moveTo(0, 0);
        mWavePath2.moveTo(-w, 0);
        for (int i = 0; i <= w; i += span) {
            x1 = i;
            x2 = i + span;
            y1 = (float) computeWavePoint(i, w);
            y2 = (float) computeWavePoint(i + span, w);

            mWavePath1.lineTo(x1, y1);
            mWavePath1.lineTo(x2, y2);

            mWavePath2.lineTo(x1 - w, y1);
            mWavePath2.lineTo(x2 - w, y2);
        }
        mWavePath1.lineTo(w, y2);
        mWavePath1.lineTo(w, 0);
        mWavePath1.close();

        mWavePath2.lineTo(0, y2);
        mWavePath2.lineTo(0, 0);
        mWavePath2.close();

        mPath.addPath(mWavePath1);
        mPath.addPath(mWavePath2);
    }

    /**
     * y = Asin(wx + q) + k
     * <p>
     * A是振幅，振幅越大，Y轴的振幅就越大
     * w角速度，控制正弦周期(单位角度内震动的次数)
     * q初相，反应在坐标系上的左右移动
     * k偏距，图像在坐标系上的上下移动
     *
     * @param x 弧度
     * @param w 宽度
     */
    private double computeWavePoint(int x, int w) {
        double radians = Math.toRadians(360 * (x  + Q) * 1.0f / w);

        return -A * Math.sin(W * radians) + K;
    }

    /**
     * 计算当x为多少时,y轴的值
     *
     * @param x
     * @return
     */
    public double computeY(int x){
        double radians = Math.toRadians(360 * (x  + Q) * 1.0f / w);

        return -A * Math.sin(W * radians) + K;
    }

    public Path buildPath(){
        return mPath;
    }

    public Paint getWaterWavePaint() {
        return mWaterWavePaint;
    }

    public void setA(float a) {
        A = a;
    }

    public void setW(float w) {
        W = w;
    }

    public void setQ(float q) {
        Q = q;
    }
}