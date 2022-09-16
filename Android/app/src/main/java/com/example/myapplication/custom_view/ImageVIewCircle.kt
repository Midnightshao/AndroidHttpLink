package com.example.myapplication.custom_view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.R

class ImageVIewCircle : View {

    private var ykWidth:Float = 0f
    private var ykHeight:Float = 0f
    private var mBitmap:Bitmap?=null;
    private val rect1:Rect?=null;
    private var mResources: Resources?=null;
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        mResources =context?.resources;
        mBitmap=mResources?.getDrawable(R.mipmap.wenhao,null)?.toBitmap();
    }

    private var mkWidth:Int = 0
    private var mkHeight:Int = 0
    private var specMode:Int=0;

    private val mPaint3 by lazy{
        Paint().also {
            it.flags=Paint.ANTI_ALIAS_FLAG;
            it.style= Paint.Style.FILL
            it.isDither=true;
            it.isFilterBitmap=true;
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var minimumWidth = getSuggestedMinimumWidth();
        var minimumHeight = getSuggestedMinimumHeight();
        mkWidth=measureWh(widthMeasureSpec);
        mkHeight=measureHe(heightMeasureSpec);
        setMeasuredDimension(mkWidth,mkHeight);
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

    var kBitmap: Bitmap?=null;
    var wRedio:Float?=40f;
    var hRedio:Float?=40f;
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("tag","tag...................."+mkHeight);
        var sc=canvas?.saveLayer(0f,0f, mkWidth.toFloat(), mkHeight.toFloat(),mPaint3, Canvas.ALL_SAVE_FLAG);
        canvas?.drawCircle((mkWidth/2).toFloat(), (mkHeight/2).toFloat(),(mkWidth/2).toFloat(),mPaint3)
        mPaint3.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas?.drawBitmap(mBitmap!!, null,
            RectF((0).toFloat(),(40).toFloat(),(mkWidth).toFloat(),(mkHeight).toFloat()-40),mPaint3);
        mPaint3.setXfermode(null);
        canvas?.restoreToCount(sc!!);
    }

}