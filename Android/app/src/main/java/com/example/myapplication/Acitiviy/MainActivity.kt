package com.example.myapplication.Acitiviy

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.*
import com.example.myapplication.Interface.CallBack
import com.example.myapplication.fragment.BlankFragment
import com.example.myapplication.fragment.BlankFragment2
import com.example.myapplication.fragment.BlankFragment3
import com.example.myapplication.fragment.BlankFragment4
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), CallBack {

    var mListContent:ListView?=null;
    var dip2:Float?=0f;
    private var mViewPager: ViewPager?=null;
    var number1:Float?=null;
    var mDataSource:ArrayList<String>?=arrayListOf<String>();
    var home:LinearLayout?=null;
    var vedio:LinearLayout?=null;
    var message:LinearLayout?=null;
    var person:LinearLayout?=null;
    var manager:FragmentManager?=null;
    var transaction: FragmentTransaction?=null;

    var fragment: BlankFragment?=null;
    var fragment2: BlankFragment2?=null;
    var fragment3: BlankFragment3?=null;
    var fragment4: BlankFragment4?=null;
    var mythread: myThread?=null;
    var menuItem:MenuItem?=null;
    var mHandler:Handler?=null;
    var mHandler1:Handler?=null;
    var data:String="";
    var aboolean:Boolean?=false;
    var aboolean1:Boolean?=false;
    var swipeRefresh: SwipeRefreshLayout?=null;

    val instance by lazy { this } //这里使用了委托，表示只有使用到instance才会执行该段代码
    override fun onStart() {
        super.onStart()



    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        swipeRefresh=findViewById(R.id.swipeRefreshMain);
//        swipeRefresh?.isRefreshing=true;
//
//        mHandler1?.postDelayed(Runnable { run {
//            Thread(Runnable {
//                kotlin.run {
////                    mHandler1.sendEmptyMessage(2);
//                }
//            }).start();
//
//            swipeRefresh?.isRefreshing=false;
//
//        } },1500);

        fragment= BlankFragment();
        fragment2= BlankFragment2();
        fragment3= BlankFragment3();
        fragment4= BlankFragment4();
        mythread=myThread(fragment!!);
        mythread?.start();
        mHandler= Handler(Looper.getMainLooper());
        framgment();
        initButton();
        buttonColor(0);
        var dip1:Float=480f;
        dip2=dip1/this.resources.displayMetrics.densityDpi;
        Log.i("tag","tagmsg*****************************"+data);
        fragment?.setCallListener(this);
    }

//    inner class DownLoadTask( name: String) : Thread(name) {
//        override fun run() {
//            super.run()
//            FuelManager.instance.request(Method.GET,"http://192.168.1.3:8080/web", listOf(Pair("fff","fff")))
//                .responseString(){request,response,result ->
//                    print(request.request);
//                    when(result){
//                    is Result.Success ->{
//                        data= result.getAs<String>()!!;
//                        Log.i("tag","tagdata"+data)
//                    }
//                    is Result.Failure ->{
//
//                    }
//                } }
////            Log.i("tag","tag&&&&&&&&&&&&&&&&"+data);
////            val url:URL= URL("http://192.168.1.3:8080/web");
////            var connection:HttpURLConnection=url.openConnection() as HttpURLConnection;
////            connection.requestMethod="GET"
////            connection.useCaches=false;
////            connection.connectTimeout=1000;
////            connection.readTimeout=1000;
////
////            val inStream=connection.inputStream;
////            val sreader=InputStreamReader(inStream);
////            val reader=BufferedReader(sreader);
////            val sb=StringBuffer();
////            while (true){
////                val line=reader.readLine();
////                Log.v("tag","msg-1"+line);
////                if(line==null||line=="")break;
////                sb.append(line);
////            }
////            Log.v("tag","msg"+sb);
////            connection.disconnect();
//        }
//    }

    inner class myThread:Thread{
        var tool:Int=0;
        var fragment: BlankFragment?=null;
        constructor(fragment: BlankFragment) : super(){
            this.fragment=fragment;
        }
        override fun run() {
            super.run()
            while (true){
                    if(aboolean==true){
                        break;
                    }else{
                        if(aboolean1==false){
                            var message:Message= Message();
                            fragment?.myAdapter?.tool_Value= fragment?.myAdapter?.tool_Value!! +1;
                            fragment?.myAdapter?.mHandler?.sendMessage(message);
                            Thread.sleep(4500);
//                            Log.i("tag","tag"+aboolean+".....");
                        }else{
                            Log.i("tag","tag"+aboolean1+".....");
                        }
                        Log.i("tag","tag"+aboolean1+".....");
                    }
            }
        }
    }
    fun framgment(){
        manager=supportFragmentManager;
        transaction=manager?.beginTransaction();
        transaction?.replace(R.id.fragment, fragment!!);
        transaction?.commit();
    }
    fun framgment1(fragmentId:Int,blankFramgent:Fragment ){
        transaction=manager?.beginTransaction();
        transaction?.replace(fragmentId,blankFramgent);
        transaction?.addToBackStack(null);
        transaction?.commit();
    }
    fun initButton(){
        home=findViewById(R.id.home) as LinearLayout;
        home!!.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                buttonColor(0);
                framgment1(R.id.fragment,fragment!!);
                menuItem?.setTitle("首页");
            }
        })
        vedio=findViewById(R.id.vedio) as LinearLayout;
        vedio!!.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                buttonColor(1);
                framgment1(R.id.fragment,fragment2!!);
                menuItem?.setTitle("视频");
            }
        })
        message=findViewById(R.id.message) as LinearLayout;
        message!!.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                buttonColor(2);
                framgment1(R.id.fragment,fragment3!!);
                menuItem?.setTitle("消息");
            }
        })
        person=findViewById(R.id.person) as LinearLayout;
        person!!.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                buttonColor(3);
                framgment1(R.id.fragment,fragment4!!);
                menuItem?.setTitle("个人");
            }
        })
    }
    fun buttonColor(selectInt: Int){
        home?.setBackgroundColor(ContextCompat.getColor(instance, R.color.white));
        vedio?.setBackgroundColor(ContextCompat.getColor(instance, R.color.white));
        message?.setBackgroundColor(ContextCompat.getColor(instance, R.color.white));
        person?.setBackgroundColor(ContextCompat.getColor(instance, R.color.white));

        when(selectInt){
            0 ->home?.setBackgroundColor(ContextCompat.getColor(instance, R.color.gray_600));
            1 ->vedio?.setBackgroundColor(ContextCompat.getColor(instance, R.color.gray_600));
            2 ->message?.setBackgroundColor(ContextCompat.getColor(instance, R.color.gray_600));
            3 ->person?.setBackgroundColor(ContextCompat.getColor(instance, R.color.gray_600));
        }
    }

    inner class SectionPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 3;
        }
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> BlankFragment();
                1 -> BlankFragment();
                else -> BlankFragment();
            }
        }
    }

    var metrics:DisplayMetrics?=null;
    var wM:WindowManager?=null;
    var rs:Resources?=null;
    @SuppressLint("ResourceType")
    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        var menu1: Unit =menuInflater.inflate(R.xml.action_menu,menu);
        menuItem=menu.findItem(R.id.plus);
        menuItem?.setTitle("首页");
        return true;
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun getMetric():Float{
         rs=resources;
        var dip1:Float=480f;
        var dip2:Float=dip1/this.resources.displayMetrics.densityDpi;

         metrics= rs?.displayMetrics;
        Log.i("tag","tag"+metrics+"..."+dip2);
        Log.i("tag","tag"+metrics?.widthPixels);

//        var metric= metrics?.widthPixels?.div(dip2);

//        metric1:Float?= metrics?.widthPixels?.times(dip2);
          var ab:Float?=metrics?.widthPixels?.toFloat();
          var metrics1:Float= ab!!;
        return metrics1!!;
      }

    override fun onRestart() {
        super.onRestart()
        Log.v("tag","重制一下。。。。。。。。。。Resume");
        aboolean=false;
        fragment?.setCallListener(this);
        mythread=myThread(fragment!!);
        mythread?.start();
    }

    override fun onResume() {
        super.onResume()
    }
     override fun setThreadBack(info: Boolean) {
            this.aboolean=info;
    }

    override fun setThreadBack1(info1: Boolean) {
            this.aboolean1=info1;
    }

    override fun onDestroy() {
        super.onDestroy()
        aboolean=true;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        aboolean=true;
        Log.i("tag","返回值。。。。。。。。。。。。。。。。。。。。。。。。。。。。")
        Log.i("tag","aboolean="+aboolean)
    }

}