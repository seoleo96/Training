package com.example.training.ui.home

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.LinearInterpolator

class CircularProgressBar(
    context: Context, attrs: AttributeSet
) : View(context, attrs) {

    private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectF = RectF(300f, 300f, 600f, 600f)

    init {
        outerPaint.color = Color.BLACK
        outerPaint.style = Paint.Style.FILL_AND_STROKE
        outerPaint.strokeWidth = 12f
        innerPaint.color = Color.BLUE
        innerPaint.style = Paint.Style.FILL_AND_STROKE
        innerPaint.strokeWidth = 12f
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawCircle(100f, 270f, 100f, outerPaint)

        canvas.drawArc(rectF, 0F, mAnimatedValue.toFloat(), true, outerPaint)
        canvas.drawCircle(450f, 450f, 120f, innerPaint)

        startAnimate()
    }

    private var valueAnimator : ValueAnimator? = null
    private var mAnimatedValue = 0

    private fun startAnimate(){
        if(valueAnimator == null){
            valueAnimator = ValueAnimator.ofInt(0, 720).apply {
                duration = DURATION
                interpolator = LinearInterpolator()
                repeatCount = -1
                addUpdateListener {
                    val value = it.animatedValue as Int
                    Log.d(TAG, "startAnimate: it.animatedValue ${it.animatedValue}", )

                    if(value > 0 && mAnimatedValue != value){
                        mAnimatedValue = value
                        Log.e(TAG, "startAnimate: mAnimatedValue", )
                        if (value == 360){
                            outerPaint.color = Color.BLACK
                        }
                        invalidate()
                    }
                }
            }
            valueAnimator?.start()
        }
    }

    companion object {
        private const val TAG = "CircularProgressBar"
        private const val DURATION = 10000L
    }
}