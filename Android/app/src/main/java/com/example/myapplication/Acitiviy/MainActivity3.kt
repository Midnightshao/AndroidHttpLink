package com.example.myapplication.Acitiviy

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.example.myapplication.BrokenLineChart
import com.example.myapplication.ClockTime
import com.example.myapplication.ClockTimeExplain
import com.example.myapplication.R
import java.text.SimpleDateFormat

class MainActivity3 : AppCompatActivity() {

    val instance by lazy { this };

    var button1: Button?=null;
    var button2: Button?=null;
    var button3: Button?=null;
    var isStart:Boolean ?= true;


    var start : ClockTime?=null;
    var start1: ClockTime?=null;
    var explain: ClockTimeExplain?=null;

    var relativeLayout:RelativeLayout?=null;
    var sDateFormat: SimpleDateFormat?=null;

    var xwidth:Float?=0.0f;
    var yHeight:Float?=0.0f;

    var xwidth1:Int?=0;
    var yHeight1:Int?=0;

    var time:Long?=0;
    var ControlTime:Int?=5;

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    var lineChart: BrokenLineChart?=null;
    @RequiresApi(Build.VERSION_CODES.R)

    var titleMaohao:Boolean?=true;
    var minute:Boolean?=false;
    var second:Boolean?=false;


    var ControlTime1:Boolean?=false;

    var year:Boolean?=false;
    var month:Boolean?=false;
    var date:Boolean?=false;
    var date1:Boolean?=false;
    var alarm:Boolean?=false;
    var alarm1:Boolean?=false;
    var alarmClock:Boolean?=false;
    var alarmClock1:Boolean?=false;
    var alarmMinute:Boolean?=false;
    var alarmMinute1:Boolean?=false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        button1=findViewById(R.id.buttonAnimal) as Button;
        relativeLayout=findViewById(R.id.relative);
        button2=findViewById(R.id.button_up) as Button;
        button3=findViewById(R.id.button_down) as Button;

        start  =findViewById(R.id.view);
        start1  =findViewById(R.id.view);

        explain=findViewById(R.id.clickExplain);

        var xwidth: Double = (this.resources.displayMetrics.widthPixels).toDouble();
        var yHeight: Double =this.resources.displayMetrics.heightPixels.toDouble();



        var messageMaohao:Message= Message();
            messageMaohao.what=3;
            mHandler.sendMessage(messageMaohao);

        button1?.setOnLongClickListener (object:View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {

                if(ControlTime1==true){
                    return false;
                }
                explain?.wenzi1();
                ControlTime=5;
                ControlTime1=true;
                object:Thread(){
                    override fun run() {
                        titleMaohao=false;
                        minute=true;
                        start?.titleMaohao(true);
                        while (minute as Boolean){
                            start1?.minute(false);
                        }
                    }
                }.start();

                object :Thread(){
                    override fun run() {
                        while (ControlTime1==true){
                            ControlTime= ControlTime?.minus(1);
                            Thread.sleep(1000);
                            if(ControlTime!!<1){
                                ControlTime1=false;
                                start1?.second(true);
                                start1?.minute(true);
                                titleMaohao=true;
                                AllBoolean();
                                start?.dates(true)
                                start1?.months(true);
                                var messageMaohao:Message= Message();
                                messageMaohao.what=3;
                                mHandler.sendMessage(messageMaohao);

                                mHandler1.sendEmptyMessage(0);
                            }
                        }
                    }
                }.start();
                return true;
            }
        });
        button1?.setOnClickListener ( object:View.OnClickListener {
            override fun onClick(v: View?) {
                ControlTime=5;
                if(minute==true){
                    minute=false;
                    second=true;
                    start1?.minute(true);
                    object :Thread(){
                        override fun run() {
                            while (second as Boolean){
                                start1?.second(second==false);
                            }
                        }
                    }.start();
                }
                else if(second==true){
                    second=false;
                    year=true;
                    explain?.wenzi2();
                    object :Thread(){
                        override fun run() {
                            start1?.hours();
                        }
                    }.start();
                }
                else if(year==true){
                    month=true;
                    explain?.wenzi3();
                    object :Thread(){
                        override fun run() {
                            start1?.years();
                        }
                    }.start();
                    year=false;
                } else if(month==true){
                        year=false;
                        date=true;
                        month=false;
                        explain?.wenziMonth();
                        object :Thread(){
                            override fun run() {
                                while (date as Boolean){
                                    start1?.months(false);
                                }
                            }
                        }.start();
                }else if(date==true){
                    date=false;
                    date1=true;
                    alarm=true;
                    explain?.wenziDate();
                    start1?.months(true);
                    object :Thread(){
                        override fun run() {
                            while (date1 as Boolean){
                                start1?.dates(false);
                            }
                        }
                    }.start();
                }else if(alarm==true){
                    explain?.wenziAlarm();
                    date1=false;
                    alarm=false;
                    start1?.dates(true);
                    start1?.alarms(alarm1 == true);
                    alarmClock=true;

                }else if(alarmClock==true){
                    explain?.wenziAlarmTime();
                    alarmClock=false;
                    alarmClock1=true;
                    alarmMinute=true;
                    object :Thread(){
                        override fun run() {
                            while (alarmClock1 as Boolean){
                                start1?.alarm1_clock(false);
                            }
                        }
                    }.start();
                }else if(alarmMinute==true){
                    alarmClock1=false;
                    alarmMinute=false;
                    alarmMinute1=true;
                    start1?.alarm1_clock(true);
                    object :Thread(){
                        override fun run() {
                            while (alarmMinute1 as Boolean){
                                start1?.alarm2_clock(false);
                            }
                        }
                    }.start();
                }
            }
        });

        var heightbutton2:Float=9.00f;
        button2?.also {
        }
        button3?.also{
        };
        button2?.setOnClickListener(View.OnClickListener {
            object : Thread() {
                override fun run() {
                    var message:Message= Message();
                    message.what=2;
                    mHandler.sendMessage(message);
                }
            }.start()
        })
        button3?.setOnClickListener(View.OnClickListener {
            object : Thread() {
                override fun run() {
                    var message:Message= Message();
                    message.what=1;
                    mHandler.sendMessage(message);
                }
            }.start();
        })
        mHandler1.sendEmptyMessage(0);
    }
    private val mHandler: Handler=object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg?.what){
                1-> UpTime();
                2-> DownTime();
                3-> Maohao();
                4-> word();
                else -> { // 注意这个块
                    print("x is neither 1 nor 2")
                }
            }
        }
    }
    private val mHandler1: Handler=object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            word();
        }
    }



    private fun AllBoolean(){
        minute=false;
        second=false;
        year=false;
        month=false;
        date=false;
        date1=false;
        alarm=false;
        alarm1=false;
        alarmClock=false;
        alarmClock1=false;
        alarmMinute=false;
        alarmMinute1=false;

    }
    var tagText:Int?=0;
    private fun word(){
        explain?.wenzi();
    }

    private fun Maohao(){
        object :Thread(){
            override fun run() {
                while (titleMaohao==true){
                    start?.titleMaohao(false);
                }
            }
        }.start();
    }
    private  fun UpTime(){
        ControlTime=5;
        if(minute==true){
            start1?.ControlTimeUp();
        }else if(second==true){
            start1?.ControlTimeSecondUp();
        }else if(month==true){
            start?.ControlTimeYearUp();
        }else if(date==true){
            start?.ControlTimeMonthUp();
        }else if(date1==true){
            start?.ControlTimeDateUp();
        }else if(alarmClock==true){
            alarm1=!alarm1!!;
            start?.alarms(alarm1!!);
        }else if(alarmClock1==true){
            start?.ControlTimeAlarmUp();
        }else if(alarmMinute1==true){
            start?.ControlTimeMinuteUp();
        }
    }
    private  fun DownTime(){
        ControlTime=5;
        if(minute==true){
            start1?.ControlTimeDown();
        }else if(second==true){
            start1?.ControlTimeSecondDown();
        } else if(month==true){
            start?.ControlTimeYearDown();
        } else if(date==true){
            start?.ControlTimeMonthDown();
        } else if(date1==true){
            start?.ControlTimeDateDown();
        }else if(alarmClock==true){
            alarm1=!alarm1!!;
            start?.alarms(alarm1!!);
        }else if(alarmClock1==true){
            start?.ControlTimeAlarmDown();
        }else if(alarmMinute1==true){
            start?.ControTimeMinuteDown();
        }
    }
}