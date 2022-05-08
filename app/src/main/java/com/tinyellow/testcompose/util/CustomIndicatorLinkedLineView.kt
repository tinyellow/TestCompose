package com.tinyellow.testcompose.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.annotation.ColorInt
import com.github.ihsg.patternlocker.CellBean
import com.github.ihsg.patternlocker.IIndicatorLinkedLineView

class CustomIndicatorLinkedLineView (private val lineWidth: Int = 0,
                                     private val lineColor:Int = Color.TRANSPARENT,
                                     private val errorColor:Int = Color.RED) : IIndicatorLinkedLineView {

    private val paint: Paint by lazy {
        val paint = Paint()
        paint.isDither = true
        paint.isAntiAlias = true
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        return@lazy paint
    }

    init {
        this.paint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas, hitIndexList: List<Int>, cellBeanList: List<CellBean>, isError: Boolean) {
        if (hitIndexList.isEmpty() || cellBeanList.isEmpty()) {
            return
        }

        val saveCount = canvas.save()
        val path = Path()
        var first = true

        hitIndexList.forEach {
            if (0 <= it && it < cellBeanList.size) {
                val c = cellBeanList[it]
                if (first) {
                    path.moveTo(c.x, c.y)
                    first = false
                } else {
                    path.lineTo(c.x, c.y)
                }
            }
        }

        this.paint.color = this.getColor(isError)
        this.paint.strokeWidth = lineWidth.toFloat()
        canvas.drawPath(path, this.paint)
        canvas.restoreToCount(saveCount)
    }

    @ColorInt
    private fun getColor(isError: Boolean): Int {
        return if (isError) errorColor else lineColor
    }
}