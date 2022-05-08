package com.tinyellow.testcompose.util;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.ColorInt;

import com.github.ihsg.patternlocker.CellBean;
import com.github.ihsg.patternlocker.INormalCellView;

import org.jetbrains.annotations.NotNull;

public class NormalCellView implements INormalCellView {

    @ColorInt
    private int hitColor= 0;

    private Paint paint = new Paint();

    public NormalCellView() {
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(@NotNull Canvas canvas, @NotNull CellBean cellBean) {
        int saveCount = canvas.save();

        this.paint.setColor(getHitColor());
        canvas.drawCircle(cellBean.getX(), cellBean.getY(), cellBean.getRadius() / 3f, this.paint);
        canvas.restoreToCount(saveCount);
    }

    public int getHitColor() {
        return hitColor;
    }

    public NormalCellView setHitColor(int hitColor) {
        this.hitColor = hitColor;
        return this;
    }
}
