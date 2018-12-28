package com.zhaojiabao.android.customview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zhaojiabao (zhaojiabao@huami.com)
 */
public class SpiderView extends View {
    private static final int POLYGON_COUNT = 6;

    private Paint mPaint;
    private Paint mShadowPaint;
    private int mCenterX;
    private int mCenterY;
    /**
     * 每根网格线顶点之间距离
     */
    private int mPolygonLength;
    /**
     * 网格线Path
     */
    private Path mPolygonPath;
    private Path mFrameworkPath;
    private Path mShadowPath;
    private int[] mShadow;


    public SpiderView(Context context) {


        this(context, null);
    }

    public SpiderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1.5f);
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setColor(Color.parseColor("#99000000"));
        mPolygonPath = new Path();
        mShadow = new int[]{6, 6, 6, 6, 6, 6};
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = mCenterY = Math.min(w, h) / 2;
        mPolygonLength = (int) (Math.min(w, h) * 0.9 / 2 / 6);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        drawPolygon(canvas);
        drawFramework(canvas);
        drawShadow(canvas);
    }

    private void drawPolygon(Canvas canvas) {
        for (int i = 1; i <= POLYGON_COUNT; i++) {
            mPolygonPath.reset();
            int sexAngleRadius = mPolygonLength * i;
            float height = (float) (sexAngleRadius * Math.sqrt(3) / 2);
            mPolygonPath.moveTo(mCenterX - sexAngleRadius, mCenterY);
            mPolygonPath.lineTo(mCenterX - sexAngleRadius / 2, mCenterY - height);
            mPolygonPath.lineTo(mCenterX + sexAngleRadius / 2, mCenterY - height);
            mPolygonPath.lineTo(mCenterX + sexAngleRadius, mCenterY);
            mPolygonPath.lineTo(mCenterX + sexAngleRadius / 2, mCenterY + height);
            mPolygonPath.lineTo(mCenterX - sexAngleRadius / 2, mCenterY + height);
            mPolygonPath.close();
            canvas.drawPath(mPolygonPath, mPaint);
        }
    }

    private void drawFramework(Canvas canvas) {
        if (mFrameworkPath == null) {
            mFrameworkPath = new Path();
            mFrameworkPath.moveTo(mCenterX - mPolygonLength * 6, mCenterY);
            mFrameworkPath.lineTo(mCenterX + mPolygonLength * 6, mCenterY);

            mFrameworkPath.moveTo(mCenterX - mPolygonLength * 3, (float) (mCenterY - mPolygonLength * 3 * Math.sqrt(3)));
            mFrameworkPath.lineTo(mCenterX + mPolygonLength * 3, (float) (mCenterY + mPolygonLength * 3 * Math.sqrt(3)));

            mFrameworkPath.moveTo(mCenterX + mPolygonLength * 3, mCenterY - (float) (mPolygonLength * 3 * Math.sqrt(3)));
            mFrameworkPath.lineTo(mCenterX - mPolygonLength * 3, mCenterY + (float) (mPolygonLength * 3 * Math.sqrt(3)));
        }
        canvas.drawPath(mFrameworkPath, mPaint);
    }

    private void drawShadow(Canvas canvas) {
        if (mShadowPath == null) {
            mShadowPath = new Path();
            mShadowPath.moveTo(mCenterX - mPolygonLength * mShadow[0], mCenterY);
            mShadowPath.lineTo(mCenterX - mPolygonLength * mShadow[1] / 2, mCenterY - (float) (mPolygonLength * mShadow[1] * Math.sqrt(3) / 2));
            mShadowPath.lineTo(mCenterX + mPolygonLength * mShadow[2] / 2, mCenterY - (float) (mPolygonLength * mShadow[2] * Math.sqrt(3) / 2));
            mShadowPath.lineTo(mCenterX + mPolygonLength * mShadow[3], mCenterY);
            mShadowPath.lineTo(mCenterX + mPolygonLength * mShadow[4] / 2, mCenterY + (float) (mPolygonLength * mShadow[4] * Math.sqrt(3) / 2));
            mShadowPath.lineTo(mCenterX - mPolygonLength * mShadow[5] / 2, mCenterY + (float) (mPolygonLength * mShadow[5] * Math.sqrt(3) / 2));
            mShadowPath.close();
        }
        canvas.drawPath(mShadowPath, mShadowPaint);
    }
}
