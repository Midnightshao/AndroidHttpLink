package com.example.myapplication

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.*
import androidx.core.graphics.drawable.toBitmap
import javax.security.auth.callback.Callback

class ClockTimeExplain: View{


    //    保存图片的宽高
    private var ykWidth:Float = 0f
    private var ykHeight:Float = 0f
    private var animatorSet = AnimatorSet()
    private var  mSurfaceHolder:SurfaceHolder?=null;
    private var mBitmap:Bitmap?=null;
    private var mBitmap1:Bitmap?=null;
    private val mResources: Resources?=resources;
    private var mBoolean:Boolean?=false;
    private var mBoolean1:Boolean?=false;

    private var topF:Float=(1.0f/100.0f).toFloat()
    private var leftF:Float=11.0f/100.0f

    private var rightF:Float=(16.0f/100.0f)
    private var bottonF:Float=11.0f/100.0f


    private var TopF1:Float=0f;
    private var leftF1:Float=0f

    private var rightF1:Float=0f
    private var bottonF1:Float=0f



    private var backgroundText:String?="";
    private var backgroundText1:String?="";



    private val mPaint3 by lazy{
        Paint().also {
            it.color= Color.BLACK
            it.style= Paint.Style.FILL
            it.alpha=80;
        }
    }
    private val mPaint by lazy{
        Paint().also {
            it.color= Color.WHITE
            it.style= Paint.Style.FILL
            it.textSize= ykHeight/28;
        }
    }
    private val mPaint1 by lazy {
        Paint().also {
            it.color= Color.BLACK
            it.style= Paint.Style.FILL
            it.strokeWidth=3.0f;
        }
    }

    var backgound1:Int?=0;
    constructor(context: Context?) : super(context) {

    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        mBitmap=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
        mBitmap1=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        var dip1:Float=480f;
        var dip2:Float=dip1/this.resources.displayMetrics.densityDpi;

        ykWidth = this.resources.displayMetrics.widthPixels.toFloat()/dip2;
        ykHeight = this.resources.displayMetrics.heightPixels.toFloat()/dip2;


        Log.i("tag","ykWidthf"+ykWidth+"ykHight"+ykHeight+"top=" + topF);
    }



    private var wenziX:Float?=ykWidth *19.0f/100.0f;
    private var wenziY:Float?=ykHeight *7.0f/100.0f;

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(0f, 0f, ykWidth!!.toFloat(),
            (ykHeight)!!.toFloat(),20f,20f,mPaint3);

        canvas?.drawText(backgroundText!!,ykWidth *1/100,ykHeight *7/100,mPaint);

        if(mBoolean==true){
            Log.i("tag","left="+left+"ykWidth"+rightF)
            canvas?.drawBitmap(mBitmap!!,null,RectF((ykWidth*(leftF.toFloat())).toFloat(),(ykHeight*       (topF)   ).toFloat(),(ykWidth*   (rightF.toFloat())      ).toFloat(),(ykHeight*bottonF).toFloat()),mPaint);
        }
        canvas?.drawText(backgroundText1!!, wenziX!!, wenziY!!,mPaint);
        if(mBoolean1==true){
            canvas?.drawBitmap(mBitmap1!!,null, RectF( ykWidth*  leftF1.toFloat(),ykHeight*TopF1.toFloat(),ykWidth*rightF1.toFloat(),ykHeight*bottonF1.toFloat()),mPaint);
        }
    }




    var surce:SurfaceView?=null;
    fun wenzi(){
        var animatorString:String="长按设置键";
        backgroundText1="";
        mBoolean1=false;

        invalidate()
        val  animator =ValueAnimator.ofInt(0,animatorString.length+1).also {
                    it?.duration=1000;
                    it.repeatCount=0;
                    it.addUpdateListener {
                        if((it.animatedValue as Int <=animatorString.length)as Boolean){
                            backgroundText=animatorString.substring(0, it.animatedValue as Int);
                            invalidate()
                            Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                        }
                        if(it.animatedValue == animatorString.length+1){
                            mBitmap=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
                            topF=(1.0f/100.0f).toFloat()
                            leftF=11.0f/100.0f

                            rightF=(16.0f/100.0f)
                            bottonF=11.0f/100.0f

                            mBoolean=true;
                            invalidate()
                        }
                    }
                }
                   mBoolean=false;
                   animator.start();

                    mBoolean1=false;
                    if(animator.isPaused){
                        animator.end();
                    }

                if (animatorSet.isPaused){
                    animatorSet.end();
                }
    }
    fun wenzi1(){
        mBoolean=false;
        mBoolean1=false;
        var animatorString:String="按向上 向下 调整时间";
        val  animator2 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString.length)as Boolean){
                    backgroundText=animatorString.substring(0, it.animatedValue as Int);

                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap=mResources?.getDrawable(R.mipmap.downup,null)?.toBitmap();
                    leftF=(20.0f/100.0f).toFloat();
                    topF= (3.0f/100.0f).toFloat();
                    rightF=(36.0f/100.0f).toFloat();
                    bottonF=(10.0f/100.0f).toFloat();
                    mBoolean=true;
                    invalidate();
                }
            }
        }




        var animatorString1:String="短按设置键切换";
        val  animator3 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString1.length)as Boolean){

                    backgroundText1=animatorString1.substring(0, it.animatedValue as Int);
                    wenziX=ykWidth *2.0f/100.0f
                    wenziY=ykHeight *15.0f/100.0f;
                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap1=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
                    leftF1=(15.0f/100.0f).toFloat();
                    TopF1= (12.0f/100.0f).toFloat();
                    rightF1=(21.0f/100.0f).toFloat();
                    bottonF1=(22.0f/100.0f).toFloat();

                    mBoolean1=true;
                    invalidate();
                }
            }
        }


        animatorSet.playSequentially(animator2,animator3);
        animatorSet.start();
//        if (animatorSet.isPaused){
//            animatorSet.end();
//        }else{
//            animatorSet.start()
//        }
    }
    fun wenzi2(){
        mBoolean=false;
        mBoolean1=false;
        var animatorString:String="向上 向下 12h/24h小时";
        val  animator2 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString.length)as Boolean){
                    backgroundText=animatorString.substring(0, it.animatedValue as Int);

                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap=mResources?.getDrawable(R.mipmap.downup,null)?.toBitmap();
                    leftF=(22.0f/100.0f).toFloat();
                    topF= (3.0f/100.0f).toFloat();
                    rightF=(38.0f/100.0f).toFloat();
                    bottonF=(10.0f/100.0f).toFloat();
                    mBoolean=true;
                    invalidate();
                }
            }
        }

        var animatorString1:String="短按设置键切换";
        val  animator3 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString1.length)as Boolean){

                    backgroundText1=animatorString1.substring(0, it.animatedValue as Int);
                    wenziX=ykWidth *2.0f/100.0f
                    wenziY=ykHeight *15.0f/100.0f;
                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap1=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
                    leftF1=(15.0f/100.0f).toFloat();
                    TopF1= (12.0f/100.0f).toFloat();
                    rightF1=(21.0f/100.0f).toFloat();
                    bottonF1=(22.0f/100.0f).toFloat();

                    mBoolean1=true;
                    invalidate();
                }
            }
        }

        animatorSet.playSequentially(animator2,animator3);
        animatorSet.start();
//        if (animatorSet.isPaused){
//            animatorSet.cancel();
//        }else{
//            animatorSet.start()
//        }
    }
    fun wenzi3(){
        mBoolean=false;
        mBoolean1=false;
        var animatorString:String="向上 向下 调整年份";
        val  animator2 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString.length)as Boolean){
                    backgroundText=animatorString.substring(0, it.animatedValue as Int);

                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap=mResources?.getDrawable(R.mipmap.downup,null)?.toBitmap();
                    leftF=(22.0f/100.0f).toFloat();
                    topF= (3.0f/100.0f).toFloat();
                    rightF=(38.0f/100.0f).toFloat();
                    bottonF=(10.0f/100.0f).toFloat();
                    mBoolean=true;
                    invalidate();
                }
            }
        }

        var animatorString1:String="短按设置键切换";
        val  animator3 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString1.length)as Boolean){

                    backgroundText1=animatorString1.substring(0, it.animatedValue as Int);
                    wenziX=ykWidth *2.0f/100.0f
                    wenziY=ykHeight *15.0f/100.0f;
                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap1=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
                    leftF1=(15.0f/100.0f).toFloat();
                    TopF1= (12.0f/100.0f).toFloat();
                    rightF1=(21.0f/100.0f).toFloat();
                    bottonF1=(22.0f/100.0f).toFloat();

                    mBoolean1=true;
                    invalidate();
                }
            }
        }

        animatorSet.playSequentially(animator2,animator3);
        animatorSet.start();
//        if (animatorSet.isPaused){
//            animatorSet.cancel();
//        }else{
//            animatorSet.start()
//        }
    }

    fun wenziMonth(){
        mBoolean=false;
        mBoolean1=false;
        var animatorString:String="向上 向下 调整月份";
        val  animator2 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString.length)as Boolean){
                    backgroundText=animatorString.substring(0, it.animatedValue as Int);

                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap=mResources?.getDrawable(R.mipmap.downup,null)?.toBitmap();
                    leftF=(22.0f/100.0f).toFloat();
                    topF= (3.0f/100.0f).toFloat();
                    rightF=(38.0f/100.0f).toFloat();
                    bottonF=(10.0f/100.0f).toFloat();
                    mBoolean=true;
                    invalidate();
                }
            }
        }

        var animatorString1:String="短按设置键切换";
        val  animator3 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString1.length)as Boolean){

                    backgroundText1=animatorString1.substring(0, it.animatedValue as Int);
                    wenziX=ykWidth *2.0f/100.0f
                    wenziY=ykHeight *15.0f/100.0f;
                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap1=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
                    leftF1=(15.0f/100.0f).toFloat();
                    TopF1= (12.0f/100.0f).toFloat();
                    rightF1=(21.0f/100.0f).toFloat();
                    bottonF1=(22.0f/100.0f).toFloat();

                    mBoolean1=true;
                    invalidate();
                }
            }
        }

        animatorSet.playSequentially(animator2,animator3);
        animatorSet.start();
//        if (animatorSet.isPaused){
//            animatorSet.cancel();
//        }else{
//            animatorSet.start()
//        }
    }

    fun wenziDate(){
        mBoolean=false;
        mBoolean1=false;
        var animatorString:String="向上 向下 调整日期";
        val  animator2 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString.length)as Boolean){
                    backgroundText=animatorString.substring(0, it.animatedValue as Int);

                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap=mResources?.getDrawable(R.mipmap.downup,null)?.toBitmap();
                    leftF=(22.0f/100.0f).toFloat();
                    topF= (3.0f/100.0f).toFloat();
                    rightF=(38.0f/100.0f).toFloat();
                    bottonF=(10.0f/100.0f).toFloat();
                    mBoolean=true;
                    invalidate();
                }
            }
        }

        var animatorString1:String="短按设置键切换";
        val  animator3 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString1.length)as Boolean){

                    backgroundText1=animatorString1.substring(0, it.animatedValue as Int);
                    wenziX=ykWidth *2.0f/100.0f
                    wenziY=ykHeight *15.0f/100.0f;
                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap1=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
                    leftF1=(15.0f/100.0f).toFloat();
                    TopF1= (12.0f/100.0f).toFloat();
                    rightF1=(21.0f/100.0f).toFloat();
                    bottonF1=(22.0f/100.0f).toFloat();

                    mBoolean1=true;
                    invalidate();
                }
            }
        }

        animatorSet.playSequentially(animator2,animator3);
        animatorSet.start();
//        if (animatorSet.isPaused){
//            animatorSet.cancel();
//        }else{
//            animatorSet.start()
//        }
    }


    fun wenziAlarm(){
        mBoolean=false;
        mBoolean1=false;
        var animatorString:String="向上 向下 on/-- 开启闹钟";
        val  animator2 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString.length)as Boolean){
                    backgroundText=animatorString.substring(0, it.animatedValue as Int);

                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap=mResources?.getDrawable(R.mipmap.downup,null)?.toBitmap();
                    leftF=(24.0f/100.0f).toFloat();
                    topF= (3.0f/100.0f).toFloat();
                    rightF=(40.0f/100.0f).toFloat();
                    bottonF=(10.0f/100.0f).toFloat();
                    mBoolean=true;
                    invalidate();
                }
            }
        }

        var animatorString1:String="短按设置键切换";
        val  animator3 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString1.length)as Boolean){

                    backgroundText1=animatorString1.substring(0, it.animatedValue as Int);
                    wenziX=ykWidth *2.0f/100.0f
                    wenziY=ykHeight *15.0f/100.0f;
                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap1=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
                    leftF1=(15.0f/100.0f).toFloat();
                    TopF1= (12.0f/100.0f).toFloat();
                    rightF1=(21.0f/100.0f).toFloat();
                    bottonF1=(22.0f/100.0f).toFloat();

                    mBoolean1=true;
                    invalidate();
                }
            }
        }

        animatorSet.playSequentially(animator2,animator3);
        animatorSet.start();
//        if (animatorSet.isPaused){
//            animatorSet.end();
//        }else{
//            animatorSet.start()
//        }
    }
    fun wenziAlarmTime(){
        mBoolean=false;
        mBoolean1=false;
        var animatorString:String="向上 向下 调整闹钟时间";
        val  animator2 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString.length)as Boolean){
                    backgroundText=animatorString.substring(0, it.animatedValue as Int);
                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap=mResources?.getDrawable(R.mipmap.downup,null)?.toBitmap();
                    leftF=(22.0f/100.0f).toFloat();
                    topF= (3.0f/100.0f).toFloat();
                    rightF=(38.0f/100.0f).toFloat();
                    bottonF=(10.0f/100.0f).toFloat();
                    mBoolean=true;
                    invalidate();
                }
            }
        }

        var animatorString1:String="短按设置键切换";
        val  animator3 =ValueAnimator.ofInt(0,animatorString.length+1).also {
            it?.duration=500;
            it.repeatCount=0;
            it.addUpdateListener {
                if((it.animatedValue as Int <=animatorString1.length)as Boolean){

                    backgroundText1=animatorString1.substring(0, it.animatedValue as Int);
                    wenziX=ykWidth *2.0f/100.0f
                    wenziY=ykHeight *15.0f/100.0f;
                    invalidate();
                    Log.v("tag","tag,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+it.animatedValue);
                }
                if(it.animatedValue == animatorString.length+1){
                    mBitmap1=mResources?.getDrawable(R.mipmap.anniu,null)?.toBitmap();
                    leftF1=(15.0f/100.0f).toFloat();
                    TopF1= (12.0f/100.0f).toFloat();
                    rightF1=(21.0f/100.0f).toFloat();
                    bottonF1=(22.0f/100.0f).toFloat();

                    mBoolean1=true;
                    invalidate();
                }
            }
        }

        animatorSet.playSequentially(animator2,animator3);
        animatorSet.start();
    }
}

