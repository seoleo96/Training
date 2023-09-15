package com.example.training.ui.home

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.CycleInterpolator
import androidx.core.animation.doOnEnd


class PreLoader(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val circleRadius = 20F
    private var firstAnimatedValue = 0
    private var secondAnimatedValue = 0
    private var thirdAnimatedValue = 0
    private var firstAnimator: ValueAnimator? = null
    private var secondAnimator: ValueAnimator? = null
    private var thirdAnimator: ValueAnimator? = null
    private var startQueue = StartQueue.First

    init {
        paint.color = Color.BLUE
        paint.strokeWidth = 2f
        paint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas) {

        Log.e(TAG, "onDraw: startQueue $startQueue firstAnimatedValue $firstAnimatedValue")
        val centerWidht = width / 2F
        val centerHeight = height / 2F
        val firstCircleCenter = centerWidht - 60
        val thirdCircleCenter = centerWidht + 60

        val firstRadius = circleRadius + if (startQueue == StartQueue.First) firstAnimatedValue else 0
        val secondRadius = circleRadius + if (startQueue == StartQueue.Second) secondAnimatedValue else 0
        val thirdRadius = circleRadius + if (startQueue == StartQueue.Third) thirdAnimatedValue else 0

        canvas.drawCircle(firstCircleCenter, centerHeight, firstRadius, paint)
        canvas.drawCircle(centerWidht, centerHeight, secondRadius, paint)
        canvas.drawCircle(thirdCircleCenter, centerHeight, thirdRadius, paint)

        if (firstAnimator == null) {
            startFirstValueAnimator()
        }
    }

    private fun startFirstValueAnimator() {
        if (firstAnimator == null) {
            firstAnimator = ValueAnimator.ofInt(0, 9).apply {
                duration = DURATION
                interpolator = CycleInterpolator(1f)
                addUpdateListener {
                    val animatedValue = it.animatedValue as Int
                    if (animatedValue > 0 && firstAnimatedValue != animatedValue) {
                        startQueue = StartQueue.First
                        firstAnimatedValue = animatedValue
                        invalidate()
                    }

                }
                doOnEnd {
                    startSecondValueAnimator()
                }
            }
        }
        firstAnimator?.start()

    }

    private fun startSecondValueAnimator() {
        if (secondAnimator == null) {
            secondAnimator = ValueAnimator.ofInt(0, 9).apply {
                duration = DURATION
                interpolator = CycleInterpolator(1f)
                addUpdateListener {
                    val animatedValue = it.animatedValue as Int
                    if (animatedValue > 0 && secondAnimatedValue != animatedValue) {
                        startQueue = StartQueue.Second
                        secondAnimatedValue = animatedValue
                        invalidate()
                    }
                }
                doOnEnd {
                    startThirdValueAnimator()
                }
            }
        }
        secondAnimator?.start()

    }

    private fun startThirdValueAnimator() {
        if (thirdAnimator == null) {
            thirdAnimator = ValueAnimator.ofInt(0, 9).apply {
                duration = DURATION
                interpolator = CycleInterpolator(1f)
                addUpdateListener {
                    val animatedValue = it.animatedValue as Int
                    if (animatedValue > 0 && thirdAnimatedValue != animatedValue) {
                        startQueue = StartQueue.Third
                        thirdAnimatedValue = animatedValue
                        invalidate()
                    }
                }
                doOnEnd {
                    startFirstValueAnimator()
                }
            }
        }
        thirdAnimator?.start()

    }

    enum class StartQueue {
        First, Second, Third
    }

    companion object {
        private const val TAG = "PreLoader"
        private const val DURATION = 700L
    }
}