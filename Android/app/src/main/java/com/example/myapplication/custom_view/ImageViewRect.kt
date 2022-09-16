package com.example.myapplication.custom_view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.R

class ImageViewRect :View{


    //    保存图片的宽高
    private var ykWidth:Float = 0f
    private var ykHeight:Float = 0f
    private var mBitmap: Bitmap?=null;
    private val rect1: Rect?=null;
    private var mResources: Resources?=null;
    constructor(context: Context?) : super(context){}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        mResources =context?.resources;
        mBitmap=mResources?.getDrawable(R.mipmap.click,null)?.toBitmap();
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private val mPaint by lazy {

        Paint().also {
            it.color= Color.TRANSPARENT
            it.style= Paint.Style.FILL

        }

    }

    private val mPaint3 by lazy{
        Paint().also {
            it.flags= Paint.ANTI_ALIAS_FLAG;
            it.style= Paint.Style.FILL
            it.isDither=true;
            it.isFilterBitmap=true;
        }
    }

    private var mkWidth:Int = 0
    private var mkHeight:Int = 0
    private var specMode:Int=0;

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
        var specMode:Int?= View.MeasureSpec.getMode(widthMeasureSpec);
        var specSize:Int?= View.MeasureSpec.getSize(widthMeasureSpec);

        if(specMode== View.MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result =200;
            if(specMode == View.MeasureSpec.AT_MOST){
                result=Math.min(specSize!!,result);
            }
        }
        return result!!;
    }
    private  fun  measureWh(widthMeasureSpec:Int):Int{
        var result:Int?=0;
        var specMode:Int?= View.MeasureSpec.getMode(widthMeasureSpec);
        var specSize:Int?= View.MeasureSpec.getSize(widthMeasureSpec);

        if(specMode== View.MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result =200;
            if(specMode == View.MeasureSpec.AT_MOST){
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
        canvas?.drawRoundRect(0f,0f,mkWidth.toFloat(),mkHeight.toFloat(),wRedio!!,hRedio!!,mPaint3);
        mPaint3.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas?.drawBitmap(mBitmap!!, null,
            RectF((0).toFloat(),(0).toFloat(),(mkWidth).toFloat(),(mkHeight).toFloat()),mPaint3);
        mPaint3.setXfermode(null);
        canvas?.restoreToCount(sc!!);
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        mResources =context?.resources;
        mBitmap=mResources?.getDrawable(resid,null)?.toBitmap();
        postInvalidate();
    }
    fun setImageResource(rsid:Int){
        mResources =context?.resources;
        mBitmap=mResources?.getDrawable(rsid,null)?.toBitmap();
        postInvalidate();
    }

}
