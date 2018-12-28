package com.zhaojiabao.android.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * @author zhaojiabao (zhaojiabao@huami.com)
 */
public class TextLocationView extends View {
    private Paint mPaint;

    public TextLocationView(Context context) {
        this(context, null);
    }

    public TextLocationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextLocationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(sp2px(context, 35));
        mPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawLinesOfFontMetrics(canvas);
        drawCenterText(canvas);
    }

    /**
     * 画baseline, top, ascent, descent, bottom等线条。
     */
    private void drawLinesOfFontMetrics(Canvas canvas) {
        String text = "Android Programmer";
        int baseLine = 400;
        Paint.FontMetrics metrics = mPaint.getFontMetrics();

        //drawText
        canvas.drawText(text, 0, baseLine, mPaint);

        float textWidth = mPaint.measureText(text);

        //draw baseLine
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(0, baseLine, textWidth, baseLine, mPaint);

        //draw top
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(0, baseLine + metrics.top, textWidth, baseLine + metrics.top, mPaint);

        //draw ascent
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(0, baseLine + metrics.ascent, textWidth, baseLine + metrics.ascent, mPaint);

        //draw descent
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(0, baseLine + metrics.descent, textWidth, baseLine + metrics.descent, mPaint);

        //draw bottom
        mPaint.setColor(Color.RED);
        canvas.drawLine(0, baseLine + metrics.bottom, textWidth, baseLine + metrics.bottom, mPaint);
    }

    private void drawCenterText(Canvas canvas) {
        String text = "Android Phone";
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextAlign(Paint.Align.CENTER);

        RectF rectF = new RectF(100, 500, 600, 900);
        canvas.drawRect(rectF, mPaint);
        PointF pointF = getCenterTextPosInRect(mPaint, rectF);
        canvas.drawText(text, pointF.x, pointF.y, mPaint);
    }

    /**
     * 获得在rectF中居中的文字原点位置
     *
     * @param paint 画笔，需要确保TextAlign为CENTER
     */
    private PointF getCenterTextPosInRect(Paint paint, RectF rectF) {
        PointF pointF = new PointF();
        Paint.FontMetrics metrics = paint.getFontMetrics();
        pointF.x = (rectF.left + rectF.right) / 2;
        float textHeight = metrics.bottom - metrics.top;
        pointF.y = (rectF.height() - textHeight) / 2 - metrics.top + rectF.top;
        return pointF;
    }

    public static float sp2px(Context context, float sp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    @SuppressWarnings("unused")
    public static float dp2px(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
