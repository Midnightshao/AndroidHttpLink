package com.example.myapplication

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.*
import android.os.Build
import android.service.controls.Control
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import kotlin.math.roundToInt

class ClockTime : View{
    private var assets1: AssetManager?=null;
    private val mResources: Resources ?=resources;
    private var mBitmap: Bitmap?=null;

    private var cBitmap: Bitmap?=null;

    private var mSrcRect:Rect?=null;
    private var mDrcRect:Rect?=null;

    private var cSrcRect:Rect?=null;

    private var ykX:Int?=null;
    private var ykY:Float?=null;
    private var minutes:Int?=22;
    private var seconds:Int?=15;
    private var months:Int?=11;
    private var dates:Int?=20;
    private var years:Int?=2020;

    private var alarm_minutes:Int?=20;
    private var alarm_seconds:Int?=20;

    private var aBitmap:Bitmap?=null;
    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet){
        assets1=context.assets;
        mBitmap=mResources?.getDrawable(R.mipmap.img_clock,null)?.toBitmap();
        cBitmap=mResources?.getDrawable(R.mipmap.naoling_clock,null)?.toBitmap();



        mSrcRect = Rect(0,0, ykWidth.toInt()/2, ykHeight.toInt()/2);
        mDrcRect =Rect (0,0,ykWidth.toInt()/2,ykHeight.toInt()/2);


        var minuteD= minutes?.div(10);
        minute= (minutes?.rem(10)).toString();
        minute_decade=minuteD.toString()

        var second_decade= seconds?.div(10);
        second= (seconds?.rem(10)).toString();
        secode_decade=second_decade.toString()

        month=months.toString();
        date=dates.toString();

    }


    private val mPaint1 by lazy{
        Paint().also {
            it.color= 0xff95999a.toInt();
            it.style= Paint.Style.FILL
//            it.textSize= ykWidth/4;
        }
    }
    private val mPaint2 by lazy{
        Paint().also {
            it.color= Color.GRAY
            it.style= Paint.Style.FILL
        }
    }


    private val mPaint3 by lazy{
        Paint().also {
            it.color= Color.BLACK
            it.style= Paint.Style.FILL

            var tf=Typeface.createFromAsset(assets1,"digital-7-mono-3.ttf");
            it.setTypeface(tf);
            it.textSize= (ykHeight/4.5).toFloat();
//            it.textSize= 40F;


        }
    }

    private val mPaint4 by lazy {
        Paint().also {
            it.color = Color.BLACK
            it.style = Paint.Style.STROKE
            it.strokeWidth = 5f
            it.strokeCap = Paint.Cap.ROUND;
        }
    }
    private val mPaint5 by lazy {
        Paint().also {
            it.color= Color.BLACK
            it.style= Paint.Style.FILL

            var tf=Typeface.createFromAsset(assets1,"digital-7-mono-3.ttf");
            it.setTypeface(tf);
            it.textSize= (ykWidth/20).toFloat();


        }
    }
    private val mPaint6 by lazy {
        Paint().also {
            it.color= Color.BLACK
            it.style= Paint.Style.FILL

            var tf=Typeface.createFromAsset(assets1,"digital-7-4.ttf");
            it.setTypeface(tf);
            it.textSize= (ykWidth/45).toFloat();


        }
    }
    private val mPaint7 by lazy {
        Paint().also {
            it.color= Color.BLACK
            it.style= Paint.Style.FILL

            var tf=Typeface.createFromAsset(assets1,"digital-7-4.ttf");
            it.setTypeface(tf);
            it.textSize= (ykWidth/60).toFloat();
        }
    }
    private val mPaint8 by lazy {
        Paint().also {
            it.color= Color.BLACK
            it.style= Paint.Style.FILL
            it.strokeWidth=3.0f;
        }
    }

    //    线
    private var line1X:Float = 0f
    private var line1Y:Float = 0f
    private var line2X:Float = 0f
    private var line2Y:Float = 0f

    //    保存图片的宽高
    private var ykWidth:Float = 0f
    private var ykHeight:Float = 0f
    //    第一次图层的y坐标
    private var firstX:Float = 0f
    //    第二次图片的x 坐标
    private var seondX:Float = 0f
    private var seconY:Float = 0f
    //    第二个图层两端缩进
    private var secondStartX:Float = 0f
    private var secondEndX:Float = 0f
    //    保存图集
    private var animatorSet = AnimatorSet()
    //    圆角矩形的rect
    private var oval3 = RectF(0f,0f,0f,0f)
    //    圆角矩形的圆角
    private var round:Float = 3f
    //    圆形的半径
    private var circleRadious:Float = 0f

    var scale:Float= 0F;
    var wM:WindowManager?=null;
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        scale=this.resources.displayMetrics.density;
        wM=context.getSystemService(Context.WINDOW_SERVICE) as WindowManager;


        var dip1:Float=480f;
        var dip2:Float=dip1/this.resources.displayMetrics.densityDpi;

        ykWidth = this.resources.displayMetrics.widthPixels.toFloat()/dip2;
        ykHeight = this.resources.displayMetrics.heightPixels.toFloat()/dip2;

        Log.v("tag","tag1111"+"$$$$$$$$$$$"+scale);
        Log.v("tag","tag1111"+"$$$$$$$$$$$"+this.resources.displayMetrics.densityDpi);
        Log.v("tag","tag1111"+ykWidth);
        Log.v("tag","tag1111"+ykHeight);
        firstX = height.toFloat()
//        oval3 = RectF(0f, 0f, ykWidth, ykHeight) // 设置个新的长方形
        seconY = height.toFloat()
//        线
        line1X = ykWidth/2 - 60f
        line1Y = ykHeight/2 - 30f
        line2X = ykWidth/2 - 20f
        line2Y = ykHeight/2 + 30f
        Log.i("tag","ykWidthf"+ykWidth+"ykHight"+ykHeight);
    }


    public fun getxWidth():Float{
        return  width.toFloat();
    }
    public fun getxHeight():Float{
        return  height.toFloat();
    }


    private  var shijian:Int=1;
    private var xkWidthXRect: Float = (ykWidth*1.07/4).toFloat();
    private var ykHeightYRect:Float =(ykHeight*0.9/5).toFloat();
    private var xkWidthRectF: Float=(ykWidth*1.15/2).toFloat();
    private var ykheightRectF: Float=(ykHeight*1.71/2).toFloat();



    private var maohao:String?=":";
    private var minute:String?="1";
    private var minute_decade:String?="2";

    private var second:String?="";
    private var secode_decade:String?="";
    private var month:String?="";
    private var date:String?="";
    private var alarmP:String?="";


    private var bitmapBoolean:Boolean?=false;

    private var Rect1:RectF?=RectF((ykWidth*2/100).toFloat(),(ykHeight*50/100).toFloat(),(ykWidth*2/100).toFloat()+100,(ykHeight*50/100).toFloat()+100);
    private var Rect2:Rect?=Rect(-140, -140, 660, 660);
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.drawBitmap(mBitmap!!,null,RectF((ykWidth/5).toFloat(),(ykHeight/30).toFloat(), (ykWidth*4/5).toFloat(),(ykHeight*29/30).toFloat()),mPaint3);

        canvas?.drawRoundRect(0f, 0f, ykWidth.toFloat(),
            (ykHeight).toFloat(),20f,20f,mPaint1)
        canvas?.drawText(minute_decade!!, (ykWidth*3.5/100).toFloat(), (ykHeight*30/100).toFloat(),mPaint3);
        canvas?.drawText(minute!!, (ykWidth*8.5/100).toFloat(), (ykHeight*30/100).toFloat(),mPaint3);
        canvas?.drawText(maohao!!, (ykWidth*12/100).toFloat(), (ykHeight*30/100).toFloat(),mPaint3);

        canvas?.drawText(secode_decade!!, (ykWidth*16/100).toFloat(), (ykHeight*30/100).toFloat(),mPaint3);
        canvas?.drawText(second!!, (ykWidth*21/100).toFloat(), (ykHeight*30/100).toFloat(),mPaint3);

        //线
        canvas?.drawLine((ykWidth*3/100).toFloat(),(ykHeight*36/100).toFloat(),(ykWidth*25/100).toFloat(), (ykHeight*36/100).toFloat(),mPaint4);
        canvas?.drawLine((ykWidth*15/100).toFloat(),(ykHeight*36/100).toFloat(),(ykWidth*15/100).toFloat(), (ykHeight*54/100).toFloat(),mPaint4);



        if(bitmapBoolean==true){
            canvas?.drawBitmap(cBitmap!!,null,RectF((ykWidth*2/100).toFloat(),(ykHeight*37/100).toFloat(),(ykWidth*5/100).toFloat(),(ykHeight*42/100).toFloat()),mPaint8);
        }
        canvas?.drawText(alarmP!!,(ykWidth*5/100).toFloat(),(ykHeight*41.5/100).toFloat(),mPaint6)

        //月份
        canvas?.drawText(month!!, (ykWidth*2/100).toFloat(), (ykHeight*50/100).toFloat(),mPaint5);
        canvas?.drawText("月", (ykWidth*6.5/100).toFloat(), (ykHeight*50/100).toFloat(),mPaint6);

        canvas?.drawText(date!!, (ykWidth*8.5/100).toFloat(), (ykHeight*50/100).toFloat(),mPaint5);
        canvas?.drawText("日", ((ykWidth*12.8/100.toFloat()).toFloat()), (ykHeight*50/100).toFloat(),mPaint6);

//        //温度
        canvas?.drawText("34", (ykWidth*17/100.toFloat()), (ykHeight*50/100).toFloat(),mPaint5);
        canvas?.drawText("最高", (ykWidth*16/100.toFloat()), (ykHeight*41/100).toFloat(),mPaint6);

        canvas?.drawText("温度", (ykWidth*22/100.toFloat()), (ykHeight*41/100).toFloat(),mPaint6);
    }
     var  maohaoB:Boolean?=false;
    @Synchronized fun titleMaohao(maohaoA:Boolean){
        if(maohaoA==true){
            maohao=":"
            maohaoB=false;
            invalidate()
        }else{
            if(maohaoB==false){
                maohao=""
                maohaoB=true;
                Thread.sleep(500)
            }else{
                maohao=":"
                maohaoB=false;
                Thread.sleep(500)
            }
            invalidate();
        }
    }


    var  minuteB:Boolean?=false;
     fun minute(minuteA:Boolean){
         if(minuteA==true){
             var minuteD= minutes?.div(10);
             minute= (minutes?.rem(10)).toString();
             minute_decade=minuteD.toString()
             minuteB=false;
             invalidate()
         }else{
             if(minuteB==false){
                 minute=""
                 minute_decade=""
                 minuteB=true;
                 Thread.sleep(500)
             }else{
                 var minuteD= minutes?.div(10);
                 minute= (minutes?.rem(10)).toString();
                 minute_decade=minuteD.toString()
                 minuteB=false;
                 Thread.sleep(500)
             }
         }
        invalidate()
    }
    var  secondB:Boolean?=false;
    fun second(secondA:Boolean){
        if(secondA==true){
            var minuteD= seconds?.div(10);
            second= (seconds?.rem(10)).toString();
            secode_decade=minuteD.toString()
        }else{
            if(secondB==false){
                second=""
                secode_decade=""
                secondB=true;
                Thread.sleep( 500)
            }else{
                var minuteD= seconds?.div(10);
                second= (seconds?.rem(10)).toString();
                secode_decade=minuteD.toString()
                secondB=false;
                Thread.sleep(500)
            }
        }
        invalidate();
    }


    var monthB:Boolean?=false;
    fun months(monthA:Boolean){
        if(monthA==true){
                if(months!! <10){
                    month=" "+months.toString();
                }else{
                    month=months.toString();
                }
                invalidate()
        }else{
            if(monthB==false){
                month="";
                monthB=true;
                Thread.sleep(500);
            }else{
                if(months!!<10){
                    month=" "+months.toString();
                }else{
                    month=months.toString();
                }
                monthB=false;
                Thread.sleep(500);
            }
            invalidate()
        }
    }
    var dateB:Boolean?=false;
    fun dates(dateA:Boolean){
        if(dateA==true){
            if(dates!!<10){
                date=" "+dates.toString();
            }else{
                date=dates.toString();
            }
               invalidate()
        }else{
            if(dateB==false){
                date="";
                dateB=true;
                Thread.sleep(500);
            }else{
                if(dates!!<10){
                    date=" "+dates.toString();
                }else{
                    date=dates.toString();
                }
                dateB=false;
                Thread.sleep(500);

            }
              invalidate();
        }
    }

    fun hours(){
        minute="2";
        minute_decade="1";
        second="";
        secode_decade="H"
        maohao=""
        invalidate()
    }
    fun years(){
        var year1:Int? = years!! /1000;
        var year2:Int?=years!!%100/10;
        var year3:Int?=years!!%10;

        minute_decade=year1.toString();
        minute="0";
        secode_decade=year2.toString();
        second=year3.toString();
        invalidate()
    }
    fun alarms(alarm:Boolean){
        if(alarm==true){
            minute_decade="o";
            minute="n"
            maohao=":"
            secode_decade="A"
            second="1";
            alarmP="闹铃";
            bitmapBoolean=true;
        }else{
            minute_decade="-";
            minute="-"
            maohao=":"
            alarmP="";
            secode_decade="A"
            second="1"
            bitmapBoolean=false;
        }
        invalidate();
    }

    var clock1:Boolean?=false;
    fun alarm1_clock(clock:Boolean){

        var minuteD= alarm_seconds?.div(10);
        second= (alarm_seconds?.rem(10)).toString();
        secode_decade=minuteD.toString();

        if(clock==true){
            var minuteD= alarm_minutes?.div(10);
                minute= (alarm_minutes?.rem(10)).toString();
                minute_decade=minuteD.toString()
                invalidate()
        }else{
            var minuteD= alarm_seconds?.div(10);
            second= (alarm_seconds?.rem(10)).toString();
            secode_decade=minuteD.toString();
            if(clock1==false){
                minute=""
                minute_decade=""
                clock1=true;
                Thread.sleep( 500)
            }else{
                var minuteD= alarm_minutes?.div(10);
                minute= (alarm_minutes?.rem(10)).toString();
                minute_decade=minuteD.toString()
                clock1=false;
                Thread.sleep(500)
            }
        }
        invalidate();
    }

    var clock2:Boolean?=false;
    fun alarm2_clock(clock:Boolean){
        if(clock==true){
            var minuteD= alarm_seconds?.div(10);
            second= (alarm_seconds?.rem(10)).toString();
            secode_decade=minuteD.toString()
            invalidate()
        }else{
            if(clock2==false){
                second=""
                secode_decade=""
                clock2=true;
                Thread.sleep( 500)
            }else{
                var minuteD= alarm_seconds?.div(10);
                second= (alarm_seconds?.rem(10)).toString();
                secode_decade=minuteD.toString()
                clock2=false;
                Thread.sleep(500)
            }
        }
        invalidate();
    }



    fun ControlTimeUp(){
        if(minutes!!<23){
            minutes= minutes?.plus(1);
        }else{
            minutes=0;
        }
    }
    fun ControlTimeDown(){
        if(minutes!!>0){
            minutes= minutes?.minus(1);
        }else{
            minutes= 23;
        }
    }
    fun ControlTimeSecondUp(){
        if(seconds!! < 60){
            seconds= seconds?.plus(1);
        }else{
           seconds=0;
        }
    }
    fun ControlTimeSecondDown(){
        if(seconds!!>0){
            seconds= seconds?.minus(1);
        }else{
            seconds=59;
        }
    }
    fun ControlTimeYearUp(){
        if(years!!<2100){
            years= years?.plus(1);
        }else{
            years=2000;
        }
        years();
    }
    fun ControlTimeYearDown(){
        if(years!!>2000){
            years= years?.minus(1);
        }else{
            years=2100;
        }
        years();
    }

    fun ControlTimeMonthUp(){
        if(months!!<12){
            months= months?.plus(1);
        }else{
            months=1;
        }
//        years();
    }
    fun ControlTimeMonthDown(){
        if(months!!>0){
            months= months?.minus(1);
        }else{
            months=12;
        }
    }
    fun ControlTimeDateUp(){
        if(dates!!<31){
            dates= dates?.plus(1);
        }else{
            dates=1;
        }
    }
    fun ControlTimeDateDown(){
        if(dates!!>0){
            dates= dates?.minus(1);
        }else{
            dates=31;
        }
    }
    fun ControlTimeAlarmUp(){
        if(alarm_minutes!!<23){
            alarm_minutes=alarm_minutes?.plus(1);
        }else{
            alarm_minutes=0;
        }
    }
    fun ControlTimeAlarmDown(){
        if(alarm_minutes!!>=0){
            alarm_minutes=alarm_minutes?.minus(1);
        }else{
            alarm_minutes=23;
        }
    }
    fun ControlTimeMinuteUp(){
        if(alarm_seconds!!<59){
            alarm_seconds=alarm_seconds?.plus(1);
        }else{
            alarm_seconds=0;
        }
    }
    fun ControTimeMinuteDown(){
        if(alarm_seconds!!>0){
            alarm_seconds=alarm_seconds?.minus(1);
        }else{
            alarm_seconds=59;
        }
    }

}