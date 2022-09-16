package com.example.myapplication.Acitiviy

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.annotation.RequiresApi
import com.example.myapplication.R

class MainActivity2 : AppCompatActivity() {
    var  ab:String?="https://f.video.weibocdn.com/qk4AyzM8lx07Khpwb0U001041200rN3f0E010.mp4?label=mp4_720p&template=1280x720.25.0&trans_finger=721584770189073627c6ee9d880087b3&media_id=4603361211908124&tp=8x8A3El:YTkl0eM8&us=0&ori=1&bf=4&ot=h&lp=15zbw9ViQOokhB8wSlgJ71&ps=mZ6WB&uid=8fr4il&ab=3915-g1,966-g1,3601-g0,3601-g0,3601-g0,1493-g0,1192-g0,1258-g0&Expires=1625958073&ssig=Xlvz%2BcnRUT&KID=unistore,video"
    var  bc:String?="https://f.video.weibocdn.com/yrl2KG1alx07KOTtwtrW01041200IrC10E010.mp4?label=mp4_720p&template=1280x720.25.0&trans_finger=721584770189073627c6ee9d880087b3&media_id=4610968123277324&tp=8x8A3El:YTkl0eM8&us=0&ori=1&bf=4&ot=h&ps=3lckmu&uid=5Bm3J8&ab=3915-g1,966-g1,1493-g0,1192-g0,1258-g0&Expires=1625959541&ssig=PfUqPoK%2Fht&KID=unistore,video";
    var button:Button?=null;

    var button1:Button?=null;
    var intent1:Intent?=null;
    var webView:WebView?=null;
    var videoView: VideoView?=null;
    var wM: WindowManager?=null;
    var rs: Resources?=null;

    var  webViewClient :WebViewClient?=null;
    var webSettings:WebSettings?=null;
    var metrics: DisplayMetrics?=null;

    val instance by lazy { this } //这里使用了委托，表示只有使用到instance才会执行该段代码

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        getMetric()
        webView=findViewById(R.id.webView) as WebView;
        webSettings=webView?.settings;
        webSettings?.javaScriptEnabled=true;
        webSettings?.setAppCacheEnabled(true)
        webSettings?.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings?.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings?.setAppCachePath(cacheDir.path)
        webSettings?.setUseWideViewPort(true);
        webSettings?.setLoadWithOverviewMode(true);
        webSettings?.setDomStorageEnabled(true);

        webView?.loadUrl("https://weibo.com/tv/show/1034:4610968123277324?from=old_pc_videoshow");
        webViewClient =object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                try {
                    if (url?.startsWith("https://") == false) {//过滤
                        intent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        return true;
                    }
                }catch (e:Exception){
                    return false;
                }
                if (url != null) {
                    webView?.loadUrl(url)
                };
                    return true
            }
        }
        webView!!.webViewClient= webViewClient as WebViewClient;
        var width:Int = metrics?.widthPixels!!;
        var height:Int= metrics?.heightPixels!!;

        Log.i("tag","tag111"+width);
        Log.i("tag","tag111"+height);

        webView!!.layoutParams.also {
            it.width= width!!;
            it.height= (height!! /2.3f).toInt()
        }

//        videoView=findViewById(R.id.videoView) as VideoView;
//
//        videoView!!.setVideoURI(Uri.parse(bc));
//        videoView!!.start();
        button=findViewById(R.id.button123) as Button;
        button?.text="返回";

        button1=findViewById(R.id.buttonPanel) as Button;
        var intent:Intent= Intent(this, MainActivity3::class.java);
//        button1?.setOnClickListener {
//            this.startActivity(intent);
//        }
//        button?.setOnClickListener {
//            finish();
//        }
        button1?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
            instance.startActivity(intent);

            }
        })
        button?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                instance.finish();
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun getMetric():Float{
        rs=resources;
        var dip1:Float=480f;
        var dip2:Float=dip1/this.resources.displayMetrics.densityDpi;

        metrics= rs?.displayMetrics;
        Log.i("tag","tag"+metrics+"..."+dip2);
        Log.i("tag","tag"+metrics?.widthPixels);

        var ab:Float?=metrics?.widthPixels?.toFloat();
        var metrics1:Float= ab!!;
        return metrics1!!;
    }

}