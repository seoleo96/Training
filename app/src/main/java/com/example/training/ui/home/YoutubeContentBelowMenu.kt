package com.example.training.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class YoutubeContentBelowMenu(
    context: Context,
    attributeSet: AttributeSet,
) : View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = 2f
        paint.style = Paint.Style.STROKE
        textPaint.apply {
            textSize = 24f
            color = Color.BLACK
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(100F, 200F, 500f, 100f, 60f, 60f, paint)
        canvas.drawText("String", 250f, 158f, textPaint)
    }
}