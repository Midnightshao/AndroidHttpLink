package com.example.myapplication.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.Acitiviy.MainActivity2
import com.example.myapplication.Interface.CallBack
import com.example.myapplication.R
import com.example.myapplication.custom_view.ImageVIewTool
import com.example.myapplication.custom_view.ImageViewCircle1
import com.example.myapplication.network.IpAddress
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.requests.download
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import kotlinx.android.synthetic.main.activity_main3.view.*
import kotlinx.android.synthetic.main.fragment_item.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mListVisiablePosition:Int?=null;
    private var mFirstVisiablePosition:Int?=0;
    public var tool_INT:Int?=0;
    var mListContent:ListView?=null;
    var dip2:Float?=0f;
    var number1:Float?=null;
    var mDataSource:ArrayList<Map<String,String>>?=arrayListOf<Map<String,String>>();
    private var mViewPager: ViewPager?=null;
    val instance by lazy { this } //这里使用了委托，表示只有使用到instance才会执行该段代码
    var myAdapter:MyAdapter?=null;
    var swipeRefresh:SwipeRefreshLayout?=null;
    var mHandler2: Handler =Handler(Looper.getMainLooper());
//    public lateinit var listen:(position:Boolean) ->Unit;
    private  var setListener:CallBack?=null;
    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.i("tag","tag.............fragment");
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    fun setCallListener(e:CallBack){
        this.setListener=e;
    }
    override fun onStart() {
        super.onStart()
        Log.i("tag","tag........+网络")
        swipeRefresh?.isRefreshing=true;
        mHandler2.postDelayed(Runnable { run {
            Thread(Runnable {
                kotlin.run {
                    mHandler1.sendEmptyMessage(2);
                }
            }).start();

            swipeRefresh?.isRefreshing=false;

        } },1500)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onRefresh();
        var dip1:Float=480f;
        dip2=dip1/this.resources.displayMetrics.densityDpi;
        initListView();
        getMetric();
    }
    fun onRefresh(){
        swipeRefresh=this.view?.findViewById(R.id.swipeRefresh);
        swipeRefresh?.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light
                                              ,android.R.color.holo_orange_light,android.R.color.holo_red_light);
        swipeRefresh?.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                mHandler2.postDelayed(Runnable { kotlin.run {
                    Toast.makeText(instance.context,"toast.makeText",Toast.LENGTH_SHORT).show();
                    swipeRefresh?.isRefreshing=false;
                } },2000);
            }
        })
    }

    inner class DownLoadTask( name: String) : Thread(name) {
        override fun run() {
            super.run()
            mDataSource?.clear();
            Log.i("tag","tagshuju"+mDataSource.toString())
//            Log.i("tag","tag___________________");
            FuelManager.instance.request(Method.GET,IpAddress().IpAddress+"/web", listOf(Pair("fff","fff")))
                .responseString(){request,response,result ->
                    print(request.request);
                    when(result){
                        is Result.Success ->{
                            var data= result.getAs<String>()!!;
                            Log.i("tag","tagdata"+data)
                            var jsonArray:JSONArray= JSONArray(data);
                            for (i in 0 until jsonArray.length()){
                                var object1: JSONObject=jsonArray.getJSONObject(i);
                                   var title:String=object1.getString("title");
                                   var picture:String=object1.getString("picture");
                                   var nameMap:Map<String,String>?=HashMap<String,String>();
                                       nameMap = mapOf<String, String>(Pair("title",title),Pair("picture",picture));
                                   mDataSource?.add(nameMap)
                                   Log.i("tag","mDataSource"+mDataSource.toString());
                                   var message:Message= Message();
                                       message.what=0;
                                   mHandler1.sendMessage(message);
                                   mHandler1.sendEmptyMessage(1);
                                   Log.i("tag","tagshuju"+mDataSource.toString())
                            }
//                            var jsonObject:JSONObject=JSONObject();
//                            var nameMap:Map<String,String>?=HashMap<String,String>();
//                            nameMap = mapOf<String, String>(Pair("picture","fwaefwaef"));
//                            nameMap = mapOf<String, String>(Pair("name","fwaefwaef"));
//                            mDataSource?.add("好天气。今天明天");0
                        }
                        is Result.Failure ->{

                        }
                    } }
//            Log.i("tag","tag&&&&&&&&&&&&&&&&"+data);
//            val url:URL= URL("http://192.168.1.3:8080/web");
//            var connection:HttpURLConnection=url.openConnection() as HttpURLConnection;
//            connection.requestMethod="GET"
//            connection.useCaches=false;
//            connection.connectTimeout=1000;
//            connection.readTimeout=1000;
//
//            val inStream=connection.inputStream;
//            val sreader=InputStreamReader(inStream);
//            val reader=BufferedReader(sreader);
//            val sb=StringBuffer();
//            while (true){
//                val line=reader.readLine();
//                Log.v("tag","msg-1"+line);
//                if(line==null||line=="")break;
//                sb.append(line);
//            }
//            Log.v("tag","msg"+sb);
//            connection.disconnect();
        }
    }



    @RequiresApi(Build.VERSION_CODES.R)
    fun initListView(){
        number1=getMetric();
        mListContent=view?.findViewById(R.id.list_item) as ListView;
//        mDataSource?.clear();
        DownLoadTask("tagTask").start();
        mListContent?.dividerHeight=0;
        myAdapter=MyAdapter(mDataSource, instance.requireContext(),this,number1!!,dip2!!,metrics);
        mListContent?.adapter=myAdapter;
        mListContent?.setOnScrollListener(object : AbsListView.OnScrollListener,
                View.OnScrollChangeListener {
                override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
//                    Log.i("tag","tag...");
                }
                override fun onScroll(
                    view: AbsListView?,
                    firstVisibleItem: Int,
                    visibleItemCount: Int,
                    totalItemCount: Int
                ) {
                    mListContent!!.post(Runnable(){
                        kotlin.run {
                            mFirstVisiablePosition= mListContent?.firstVisiblePosition!!;

                        }
                    })
//                    Log.i("tag","tag"+"消失。。。。。。。"+mFirstVisiablePosition);
                    if(mFirstVisiablePosition!! !=null && mFirstVisiablePosition!!>0){
//                        myThread?.sychronizedMethod();
                    }
                }

                override fun onScrollChange(
                    v: View?,
                    scrollX: Int,
                    scrollY: Int,
                    oldScrollX: Int,
                    oldScrollY: Int
                ) {
                }
            })
    }
    //更新数据
    var mHandler1: Handler =object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when (msg.what){
                0 -> UpdateAdapter();
                1 -> UpdatePicture();
//                2 ->;
//                2 -> myAdapter?.notifyDataSetChanged();
            }
        }
    }
    inner class ImageThread:Thread{
        var url:String?=null;
        var imageVIewTool:ImageVIewTool?=null;
        constructor(url:String,imageVIewTool: ImageVIewTool){
            this.url=url;
            this.imageVIewTool=imageVIewTool;
        }
        override fun run() {

            Log.i("tag","url--"+url);
            var url: URL = URL(url);
            // 根据URL 实例， 获取HttpURLConnection 实例

                var httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                if(httpURLConnection.doInput){
                // 设置读取 和 连接 time out 时间
                httpURLConnection.readTimeout = 5000
                httpURLConnection.connectTimeout = 5000
                // 获取图片输入流
                var inputStream = httpURLConnection.inputStream
                // 获取网络响应结果
                var responseCode=httpURLConnection.responseCode;
                var bitmap=BitmapFactory.decodeStream(inputStream);
                if(responseCode==HttpURLConnection.HTTP_OK){
                    Log.i("tag","url--"+"-----------------------选择");
                    this.imageVIewTool?.setBitMapResource(bitmap);
                }
            }
        }
    }

    fun UpdateAdapter(){
        myAdapter?.setUpdate(mDataSource);
        myAdapter?.notifyDataSetChanged();
    }
    fun UpdatePicture(){
        Log.w("Progress","Picture-----------------------------------");
//
//        FuelManager.instance.download("http://192.168.1.3:8080/img/bigpicture.jpg",Method.GET)
//            .fileDestination { response, request ->  File.createTempFile("temp","png") }.progress { readBytes, totalBytes ->
//                    val progress= readBytes.toFloat()/totalBytes.toFloat();
//                    Log.w("Progress","PPPPPPPPpppppppppppppppppppppppppppppppppp"+progress.toString()+progress);
//            }.responseString();
//        Fuel.download("http://192.168.1.3:8080/img/bigpicture.jpg",Method.GET)
//            .fileDestination { response, request ->  File.createTempFile("temp","png") }.progress { readBytes, totalBytes ->
//                val progress= readBytes.toFloat()/totalBytes.toFloat();
//                Log.w("Progress","PPPPPPPPpppppppppppppppppppppppppppppppppp"+progress.toString()+progress);
//            }.responseString();
          Fuel.download("http://192.168.1.3:8080/img/bigpicture.jpg").progress { readBytes, totalBytes ->
             var file:File= File.createTempFile("temp",".tmp")
            val progress= readBytes.toFloat()/totalBytes.toFloat();
              Log.w("tag","picture...................."+totalBytes);
          }.response { req, res, result -> }
//        FuelManager.instance.request(Method.GET,"http://192.168.1.3:8080/img/bigpicture.jpg", listOf(Pair("fff","fff")))
//            .requestProgress { readBytes, totalBytes ->
//                Log.i("tag","存储");
//                var file:File= File("picture",".png");
//                var fos:OutputStream = file.outputStream();
//                fos.write(totalBytes.toInt())
//                fos.close();
////                fos.write(totalBytes.toByte());
//            }
    }



    class MyAdapter(ds:List<Map<String,String>>?, var context: Context,fragment:BlankFragment ,number:Float, dip:Float,metrics: DisplayMetrics?): BaseAdapter(){
        var mDataSource: List<Map<String,String>>? = null
        var mInflater: LayoutInflater? = null
        var number2:Float?=null;
        var intent: Intent?=null;
        var floot:Float?=2.0f;
        var dip1:Float?=1f;
        var scale:Float=2.2f;
//        var DataList:Int=0;
        var metrics1:DisplayMetrics?=null;
        var viewPager:ViewPager?=null;
        var tool_Value:Int=0;
        var fragment:BlankFragment?=null;

        private var FIRIST_ITEM:Int=0;
        private var OTHER_ITEM:Int =1;
        init {
            mDataSource=ds;
            mInflater=LayoutInflater.from(context);
            number2=number;
            intent= Intent(context, MainActivity2::class.java);
            metrics1=metrics;
            this.fragment=fragment;
        }
        public fun setUpdate(mDataSource: List<Map<String,String>>? ){
            this.mDataSource=mDataSource;
        }

        override fun getCount(): Int {
            val num = mDataSource!!.size/ floot!!;
            return (num.roundToInt()+1)
        }

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemViewType(position: Int): Int {
            if(position==0) {
                return FIRIST_ITEM;
            }
            return -1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong();
        }



        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var type=getItemViewType(position);
            var viewHode1:ViewHode1?=null;
            var viewHold:ViewHold?=null;
            var view:View?=null;
            if(convertView==null){
                if(type==FIRIST_ITEM){
                    view= LayoutInflater.from(context).inflate(R.layout.fragment_item,parent,false);
                    viewHode1=ViewHode1();
                    viewHode1?.viewPager=view.findViewById(R.id.viewPager);
                    viewHode1?.relative=view.findViewById(R.id.relative1) as RelativeLayout;

                    viewHode1?.viewPager?.adapter=fragment?.SectionsPagerAdapters(viewHode1?.relative!!);

//-----
                    view?.tag=viewHode1;
                }else{
                    view =LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
                    viewHold=ViewHold();
                    viewHold.textContent=view?.findViewById(R.id.textView) as ImageVIewTool;
                    viewHold.textClock=view?.findViewById(R.id.textView1)  as ImageVIewTool;
                    viewHold.TextView=view?.findViewById(R.id.imageText) as TextView;
                    viewHold.TextView1=view?.findViewById(R.id.imageText1) as TextView;
                    viewHold.linearLayout=view?.findViewById(R.id.LinearLayout) as LinearLayout;
                    viewHold.linearLayout1=view?.findViewById(R.id.LinearLayout1) as LinearLayout;
                    view?.tag=viewHold;
                }
            }else{
                    Log.i("tag","first_item Type="+type);
                    if(type ==FIRIST_ITEM){
                        Log.i("tag","first_item Type="+"First_item"+ convertView.tag);
                        Log.i("tag","first_item Type="+"First_ViewHode1");
                        viewHode1=convertView.tag as ViewHode1;
                        view=convertView;
                    }else{
                        viewHold= convertView.tag as ViewHold;
                        view=convertView;
                    }
            }
            if(view?.tag==viewHode1){
                viewHode1=view?.tag as ViewHode1;
                viewPager=viewHode1.viewPager;

                viewPager?.setOnTouchListener(object :View.OnClickListener, View.OnTouchListener {
                    override fun onClick(v: View?) {
                        TODO("Not yet implemented")
                    }

                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        if(event?.action==MotionEvent.ACTION_DOWN){
                            fragment?.setListener?.setThreadBack1(true)
                        }else if(event?.action ==MotionEvent.ACTION_UP){
                            fragment?.setListener?.setThreadBack1(false)
                        }else if(event?.action ==MotionEvent.ACTION_MOVE){
                            fragment?.setListener?.setThreadBack1(true)
                        }else if(event?.action ==MotionEvent.ACTION_CANCEL){
                            fragment?.setListener?.setThreadBack1(false)
                        }
                        return false;
                    }
                });
            }
            if(view?.tag==viewHold){
                var width1=metrics1?.widthPixels;
//                viewHold?.textContent?.setImageResource(R.mipmap.good_picture);
                    viewHold?.linearLayout?.also {
                    it.layoutParams.width= (width1!! /2.2f).toInt();
                    it.layoutParams.height=(width1!! /2.2f).toInt();
                }
                viewHold?.textContent?.also {
                    it.layoutParams.width= (width1!! /2.2f).toInt();
                    it.layoutParams.height=(width1!! /2.8f).toInt();
                }
                var map=mDataSource?.get(position*(floot?.toInt()!!)-2) as Map;
                    var title=map.get("title");
                    var piture=map.get("picture");
                var imageThread = BlankFragment().ImageThread(IpAddress().IpAddress+"/img/"+piture,viewHold?.textContent as ImageVIewTool);
                    imageThread.start();
//                viewHold?.TextView?.text=mDataSource?.get(position*(floot?.toInt()!!)-2);
                  viewHold?.TextView?.text=title;
            if((position*2)+1-2< mDataSource?.size!!){
                viewHold?.linearLayout1?.also {
                    it.layoutParams.width=(width1!! /2.2f).toInt();
                    it.layoutParams.height=(width1!! /2.2f).toInt();
                }
                viewHold?.textClock?.also {
                    it.layoutParams.width= (width1!! /2.2f).toInt();
                    it.layoutParams.height=(width1!! /2.8f).toInt();
                }
                var map=mDataSource?.get((position*floot?.toInt()!!)   +1-2);
                var title=map?.get("title");
                var piture=map?.get("picture");
                var imageThread1 = BlankFragment().ImageThread(IpAddress().IpAddress+"/img/"+piture,viewHold?.textClock as ImageVIewTool);
                    imageThread1.start();
                viewHold?.TextView1?.text=title;
                viewHold?.textClock?.visibility=View.VISIBLE;
                viewHold?.TextView1?.visibility=View.VISIBLE;
            }else{
                viewHold?.textClock?.visibility=View.GONE;
                viewHold?.linearLayout1?.setBackgroundColor(Color.TRANSPARENT);
                viewHold?.linearLayout1?.also {
                    it.layoutParams.width=(width1!! /2.2f).toInt();
                    it.layoutParams.height=(width1!! /2.2f).toInt();
                }
                viewHold?.TextView1?.visibility=View.GONE;
            }
              viewHold?.linearLayout?.setOnClickListener(object :View.OnClickListener{
                  override fun onClick(v: View?) {
                      fragment?.setListener?.setThreadBack(true)
                      intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      context.startActivity(intent)
                  }
              })
                viewHold?.linearLayout?.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                        fragment?.setListener?.setThreadBack(true)
                        intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent)
                    }
                })
        }
            return  view!!;
        }
        fun setView(parent: ViewGroup?,view:View?,viewHode1:ViewHode1?){

        }
        fun setOtherView(parent: ViewGroup?,view:View,viewHold:ViewHold){

        }


        var mHandler: Handler =object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                Log.i("tag","tag+...."+msg.obj)
                viewPager?.setCurrentItem(tool_Value);
            }
        }
        inner class ViewHold  :View.OnClickListener{
            var textContent: ImageVIewTool?=null;
            var textClock: ImageVIewTool?=null;

            var TextView :TextView?=null;
            var TextView1 :TextView?=null;

            var linearLayout:LinearLayout?=null;
            var linearLayout1:LinearLayout?=null;

            override fun onClick(v: View?) {

            }
        }

        inner class ViewHode1:View.OnClickListener{
            var viewPager:ViewPager?=null;
            var relative:RelativeLayout?=null;

            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        }
    }
    inner class SectionsPagerAdapters: PagerAdapter {
//        var width1=metrics1?.widthPixels;
        var mImageList:ArrayList<Int>?= arrayListOf();
        var mCircle1List:ArrayList<ImageViewCircle1>?= arrayListOf();

        var relativeLayout:RelativeLayout?=null;
        var parent:ViewGroup?=null;
        //            }
        constructor(relativeLayout: RelativeLayout){
            mImageList?.add(R.mipmap.bigpicture);
            mImageList?.add(R.mipmap.chuyinweilai);
            mImageList?.add(R.mipmap.ludyzhuansheng);
            this.relativeLayout=relativeLayout;
            for (i in 0 until 3){
                var imageView1:ImageViewCircle1=ImageViewCircle1(context);
                var paramPt:RelativeLayout.LayoutParams=RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                ImageCircle1(imageView1,paramPt,relativeLayout,i+1);
                mCircle1List?.add(imageView1);
            }
        };
        fun ImageCircle1(imageView: ImageViewCircle1, paramPt:RelativeLayout.LayoutParams, relativeLayout: RelativeLayout, number: Int){
            paramPt.width=15;
            paramPt.height=15;
            paramPt.rightMargin=28*number;
            paramPt.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            relativeLayout.addView(imageView,paramPt);
        }


        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view==`object`;
        }

        override fun getCount(): Int {
            return  Int.MAX_VALUE;
        }
        //            ----
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            myAdapter?.tool_Value=position;
            var imageView:ImageView=ImageView(context);
            container.addView(imageView)
            var position1=position% 3;
            imageView.setTag(position1);
            imageView.setBackgroundResource(mImageList?.get(position1)!!);
            Log.i("tag", "tagpoint########$position1");
            for (i in 0 until 3){
                if(i==imageView.getTag()){
                    Log.i("tag","tag"+"point"+i);
                    var imageCircle:ImageViewCircle1= mCircle1List?.get(i)!!;
                    imageCircle?.mColorBackground1(R.color.white)
                }else{
                    var imageCircle:ImageViewCircle1= mCircle1List?.get(i)!!;
                    imageCircle?.mColorBackground(R.color.black);
                }
            }
            return imageView;
        }
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?);
        }
    }
//    inner class MyThread: Thread {
//        var object1:Object=Object();
//        var MAX_VALUE:Int= Int.MAX_VALUE;
//        var suspended:Boolean?=true;
//        var stopped:Boolean?=null;
//        constructor(){
//            suspended=true;
//            stopped=false;
//        }
//        @InternalCoroutinesApi
//        override fun run() {
//            while (true){
//                synchronized(this){
////                    if(suspended==false){
////                        this.object1.wait();
////                    }
//                    if(tool!! >MAX_VALUE) {
//                        tool = 0;
//                    }
//                    var mMessage:Message=Message();
//                    mMessage?.obj=tool;
//                    myAdapter?.mHandler?.sendMessage(mMessage);
//                    tool= tool!! +1;
//                    Log.i("tag","tag....."+tool)
//                    Log.i("tag","tag,,,,,,,,,,,,,,@@@@@@@@@@@@@@@@"+tool)
////
//                    Thread.sleep(1000);
//                }
//            }
//        }
//

    var metrics: DisplayMetrics?=null;
    var wM: WindowManager?=null;
    var rs: Resources?=null;
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
    override fun onDestroy() {
        super.onDestroy()
        Log.i("tag","销毁！！！！");
    }
}