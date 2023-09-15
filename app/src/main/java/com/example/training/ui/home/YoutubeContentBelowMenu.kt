package com.example.training.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.example.training.R
import kotlin.properties.Delegates

class YoutubeContentBelowMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttrs, defStyleRes) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textBounds = Rect()
    private var textToDraw = ""
    private var backgroundColor  = 0

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = 2f
        paint.style = Paint.Style.FILL
        textPaint.apply {
            textSize = 24f
            color = Color.BLACK
        }
        strokePaint.apply {
            color = Color.BLACK
            strokeWidth = 4f
            style = Paint.Style.STROKE
        }
        context.withStyledAttributes(attrs, R.styleable.YoutubeContentBelowMenu) {
             backgroundColor = getColor(
                R.styleable.YoutubeContentBelowMenu_android_background,
                ContextCompat.getColor(context, R.color.black)
            )
            val text = getString(R.styleable.YoutubeContentBelowMenu_android_text)
            Log.e(TAG, "withStyledAttributes: $text")
            textToDraw = "$text"
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(textToDraw, 0, textToDraw.length, textBounds)
        val textWidth = textBounds.width()
        val textHeight = textBounds.height()
        val measuredWidth = resolveSize(textWidth + paddingLeft + paddingRight + 240, widthMeasureSpec)
        val measuredHeight = resolveSize(textHeight + paddingLeft + paddingRight + 240, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.GRAY

        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            60f,
            60f,
            paint
        )

        canvas.drawRoundRect(
            2f,
            2f,
            width.toFloat() - 2f,
            height.toFloat()-2f,
            60f -2f,
            60f -2f,
            strokePaint
        )

        Log.e(TAG, "onDraw: textToDraw $textToDraw")
        Log.e(TAG, "onDraw: width $width")
        Log.e(TAG, "onDraw: height $height")
        Log.e(TAG, "onDraw: textToDraw ${textBounds.width()}")
        canvas.drawText(textToDraw, width.toFloat()/4, height.toFloat()/2 + textBounds.height() / 2, textPaint)
    }

    companion object {
        private const val TAG = "YoutubeContentBelowMenu"
    }
}