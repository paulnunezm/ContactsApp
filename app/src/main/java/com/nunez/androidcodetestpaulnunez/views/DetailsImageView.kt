package com.nunez.androidcodetestpaulnunez.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class DetailsImageView @JvmOverloads constructor(
                       context: Context,
                       attrs: AttributeSet? = null,
                       defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    private var mAspectRatio = 1.5f


    fun setAspectRatio(aspectRatio: Float = 1.5f) {
        mAspectRatio = aspectRatio
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = measuredWidth
        val height = (measuredWidth / mAspectRatio)
        setMeasuredDimension(measuredWidth, height.toInt())
    }
}