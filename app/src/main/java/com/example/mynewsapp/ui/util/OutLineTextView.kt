package com.example.mynewsapp.ui.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.mynewsapp.R

class OutLineTextView : AppCompatTextView{
    private var outlineColor:Int = Color.WHITE
    private var outlineWidth:Float = 4f
    constructor(context: Context?):super(context!!){}
    constructor(context: Context, attrs:AttributeSet?):super(context,attrs){
        initView(context,attrs)
    }
    constructor(context:Context, attrs: AttributeSet?,defStyleAttr:Int):super(context, attrs, defStyleAttr){
        initView(context,attrs)
    }
    private fun initView(context: Context, attrs: AttributeSet?){
        val type = context.obtainStyledAttributes(attrs,R.styleable.OutLineTextView)
        outlineWidth = type.getFloat(R.styleable.OutLineTextView_outline_strokeWidth,outlineWidth)
        outlineColor = type.getColor(R.styleable.OutLineTextView_outline_color,outlineColor)
        type.recycle()
    }
    override fun onDraw(canvas: Canvas){
        val defColor = textColors
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = outlineWidth
        setTextColor(outlineColor)
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        setTextColor(defColor)
        super.onDraw(canvas)
    }
}