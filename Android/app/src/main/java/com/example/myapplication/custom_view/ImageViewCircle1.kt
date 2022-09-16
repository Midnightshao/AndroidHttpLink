package com.example.myapplication.custom_view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.R

class ImageViewCircle1 :View{

    private var mkWidth:Int = 0
    private var mkHeight:Int = 0

    private var mNumber:Int=2;
    private var mBitmap:Bitmap?=null;
    private var mResources: Resources?=null;

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        mResources =context?.resources;
        mBitmap=mResources?.getDrawable(R.mipmap.wenhao,null)?.toBitmap();
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){

    }
    private val mPaint3 by lazy{
        Paint().also {
            it.color= Color.BLACK
            it.style= Paint.Style.FILL
        }
    }
    private fun number(number:Int){
        this.mNumber=number;
    }
    fun mColorBackground(color:Int){
        mPaint3?.color=Color.GRAY;
        mPaint3?.style=Paint.Style.FILL
        invalidate();
    }
    fun mColorBackground1(color:Int){
        mPaint3?.color=Color.parseColor("#c4c4c4");
        mPaint3?.style=Paint.Style.FILL
        invalidate();
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var minimumWidth = getSuggestedMinimumWidth();
        var minimumHeight = getSuggestedMinimumHeight();
        mkWidth=measureWh(widthMeasureSpec);
        mkHeight=measureHe(heightMeasureSpec);
        setMeasuredDimension(mkWidth,mkHeight);
    }
    private  fun  measureWh(widthMeasureSpec:Int):Int{
        var result:Int?=0;
        var specMode:Int?=MeasureSpec.getMode(widthMeasureSpec);
        var specSize:Int?=MeasureSpec.getSize(widthMeasureSpec);

        if(specMode==MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result =200;
            if(specMode == MeasureSpec.AT_MOST){
                result=Math.min(specSize!!,result);
            }
        }
        return result!!;
    }
    private  fun  measureHe(widthMeasureSpec:Int):Int{
        var result:Int?=0;
        var specMode:Int?=MeasureSpec.getMode(widthMeasureSpec);
        var specSize:Int?=MeasureSpec.getSize(widthMeasureSpec);

        if(specMode==MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result =200;
            if(specMode == MeasureSpec.AT_MOST){
                result=Math.min(specSize!!,result);
            }
        }
        return result!!;
    }
    public fun setBitmap(){

    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("tag","tag...................."+mkHeight);
        var sc=canvas?.saveLayer(0f,0f, mkWidth.toFloat(), mkHeight.toFloat(),mPaint3, Canvas.ALL_SAVE_FLAG);
        canvas?.drawCircle((mkWidth/2).toFloat(), (mkHeight/2).toFloat(),(mkWidth/2).toFloat(),mPaint3)
        mPaint3.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        mBitmap?.let {
            canvas?.drawBitmap(
                it, null,
                RectF((0).toFloat(),(10).toFloat(),(mkWidth).toFloat(),(mkHeight).toFloat()-10),mPaint3)
        };
        mPaint3.setXfermode(null);
        canvas?.restoreToCount(sc!!);
    }
     fun setBackgroundResource(bitmap: Bitmap) {
        mBitmap=bitmap;
        postInvalidate();
    }
}