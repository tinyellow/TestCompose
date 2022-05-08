package com.tinyellow.testcompose.util;

import static com.tinyellow.testcompose.util.LockerLinkedLineView.dp2px;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.ColorInt;

import com.github.ihsg.patternlocker.CellBean;
import com.github.ihsg.patternlocker.IHitCellView;

/**
 * Created by 小黄 on 2019/3/10.
 */

public class HitCellView implements IHitCellView {

    @ColorInt
    private int hitColor= 0;
    @ColorInt
    private int errorColor = 0;
    @ColorInt
    private int centerColor = 0;

    private Paint paint = new Paint();

    private Path path = new Path();

    private int lineWidth = dp2px(3);

    public HitCellView() {
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas, CellBean cellBean, boolean isError) {
        int saveCount = canvas.save();

        this.paint.setColor(getColor(isError)& 0x43FFFFFF);
        canvas.drawCircle(cellBean.getX(), cellBean.getY(), cellBean.getRadius(), this.paint);

//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(lineWidth);
//        this.paint.setColor(getColor(isError));
//        canvas.drawCircle(cellBean.getX(), cellBean.getY(), cellBean.getRadius()-lineWidth/2, this.paint);

        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(getCenterColor(isError));
        canvas.drawCircle(cellBean.getX(), cellBean.getY(), cellBean.getRadius() / 3f, this.paint);

        canvas.restoreToCount(saveCount);
    }

    @ColorInt
    private int getColor(boolean isError){
        return isError ? this.getErrorColor() : this.getHitColor();
    }

    private int getErrorColor() {
        return errorColor;
    }

    private int getHitColor() {
        return hitColor;
    }

    public HitCellView setHitColor(int hitColor) {
        this.hitColor = hitColor;
        return this;
    }

    public HitCellView setErrorColor(int errorColor){
        this.errorColor = errorColor;
        return this;
    }

    public HitCellView setCenterColor(int centerColor){
        this.centerColor = centerColor;
        return this;
    }

    public int getCenterColor() {
        return centerColor;
    }

    @ColorInt
    private int getCenterColor(boolean isError){
        return isError ? this.getErrorColor() : this.getCenterColor();
    }

}
