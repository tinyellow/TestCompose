package com.tinyellow.testcompose.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import com.github.ihsg.patternlocker.CellBean
import com.github.ihsg.patternlocker.IHitCellView

class CustomIndicatorHitCellView (private val hitColor:Int = Color.GRAY,
                                  private val hitBgColor:Int = Color.GRAY,
                                  private val errorColor:Int = Color.RED) : IHitCellView {

    private val paint: Paint by lazy {
        val paint = Paint()
        paint.isDither = true
        paint.isAntiAlias = true
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        return@lazy paint
    }

    init {
        this.paint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas, cellBean: CellBean, isError: Boolean) {
        val saveCount = canvas.save()

        this.paint.color = hitBgColor
        canvas.drawCircle(cellBean.x, cellBean.y, cellBean.radius, this.paint)
        this.paint.color = this.getColor(isError)
        canvas.drawCircle(cellBean.x, cellBean.y, cellBean.radius*2/5, this.paint)
        canvas.restoreToCount(saveCount)
    }

    @ColorInt
    private fun getColor(isError: Boolean): Int {
        return if (isError) errorColor else hitColor
    }

}