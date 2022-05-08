package com.tinyellow.testcompose.util;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.ColorInt;

import com.github.ihsg.patternlocker.CellBean;
import com.github.ihsg.patternlocker.ILockerLinkedLineView;

import java.util.List;

/**
 * Created by 小黄 on 2019/3/10.
 */

public class LockerLinkedLineView implements ILockerLinkedLineView {

    @ColorInt
    private int normalColor = 0;
    @ColorInt
    private int  errorColor = 0;
    private float lineWidth = dp2px(3);
    private Paint paint = new Paint();

    private boolean isShowTrack = true;

    public LockerLinkedLineView() {
        this.paint.setStyle(Paint.Style.STROKE);
    }

    public int getNormalColor() {
        return normalColor;
    }

    public LockerLinkedLineView setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public int getErrorColor() {
        return errorColor;
    }

    public LockerLinkedLineView setErrorColor(int errorColor) {
        this.errorColor = errorColor;
        return this;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public LockerLinkedLineView setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public LockerLinkedLineView setShowTrack(boolean showTrack) {
        isShowTrack = showTrack;
        return this;
    }

    @Override
    public void draw(Canvas canvas, List<Integer> hitIndexList, List<CellBean> cellBeanList, float endX, float endY, boolean isError) {
        if (hitIndexList.isEmpty() || cellBeanList.isEmpty()) {
            return ;
        }

        int saveCount = canvas.save();
        Path path = new Path();
        boolean first = true;

//        hitIndexList.forEach {
//            if (0 <= it && it < cellBeanList.size) {
//                val c = cellBeanList[it]
//                if (first) {
//                    path.moveTo(c.x, c.y)
//                    first = false
//                } else {
//                    path.lineTo(c.x, c.y)
//                }
//            }
//        }

        for(int it:hitIndexList){
            if (0 <= it && it < cellBeanList.size()) {
                CellBean c = cellBeanList.get(it);
                if (first) {
                    path.moveTo(c.getX(), c.getY());
                    first = false;
                } else {
//                    path.addCircle(c.getX(), c.getY(),c.getRadius(),Path.Direction.CW);
                    path.lineTo(c.getX(), c.getY());
                }
            }
        }

        if ((endX != 0f || endY != 0f) && hitIndexList.size() < 9) {
            path.lineTo(endX, endY);
        }

        this.paint.setColor(getColor(isError));
        this.paint.setStrokeWidth(getLineWidth());
        canvas.drawPath(path, this.paint);

        canvas.restoreToCount(saveCount);
    }


    @ColorInt
    private int getColor(boolean isError){
        if(!isShowTrack){
            return Color.TRANSPARENT;
        }
        return isError ? this.getErrorColor() : this.getNormalColor();
    }

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
