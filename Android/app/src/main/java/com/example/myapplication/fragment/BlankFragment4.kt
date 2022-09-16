package com.example.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.Acitiviy.MainActivity4
import com.example.myapplication.Interface.CallBack
import com.example.myapplication.R
import com.example.myapplication.Tool.UUIIDsharedPreferences
import com.example.myapplication.network.IpAddress
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment4.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment4 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var textView:TextView?=null;
    private var linear:LinearLayout?=null;
    private  var setListener: CallBack?=null;
    var swipeRefresh: SwipeRefreshLayout?=null;
    var uuiiDshared: UUIIDsharedPreferences = UUIIDsharedPreferences();

    val instance1 by lazy { this }


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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank4, container, false)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        var uuiiDshared: UUIIDsharedPreferences = UUIIDsharedPreferences();
        mHandler2.postDelayed(Runnable { run {
            Thread(Runnable {
                kotlin.run {
                    var token=uuiiDshared.getString("idCache","token", instance1.requireContext());
                    Log.i("tag","token + save"+token)
                    var ipAddress=IpAddress();
                    FuelManager.instance.request(Method.POST, ipAddress.IpAddress+"/personal",listOf(Pair("token",token)))
                        .responseString(){request,response,result ->
                            print(request.request);
                            when(result){
                                is Result.Success -> {
                                    var data=result.getAs<String>();
                                    Log.i("tag","tagNickName.............."+data+",,,,,,,,,,GGGGGGGGGGGGGGGGGGGGGGGGGGg");
                                    var jsonObject:JSONObject=JSONObject(data);
                                    var  nickName = jsonObject.getString("token");
                                    Log.i("tag","tagNickName.............."+nickName);
                                    var msg:Message= Message();
                                    msg.obj=nickName;
                                    mHandler3.handleMessage(msg);
                                }
                            } }
                }
            }).start();
            swipeRefresh?.isRefreshing=false;
        } },500)

    }


    var mHandler2:Handler=Handler();
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        textView=this.view?.findViewById(R.id.username) as TextView;
        linear  =this.view?.findViewById(R.id.LinearLayout);
        swipeRefresh=this.view?.findViewById(R.id.swipeRefreshMain1);
        swipeRefresh?.isRefreshing=true;
        uuiiDshared.Uuid1SharedPreferences(instance1.requireContext(),"idCache");

        mHandler2.postDelayed(Runnable { run {
            Thread(Runnable {
                kotlin.run {
                    var token=uuiiDshared.getString("idCache","token", instance1.requireContext());
                    Log.i("tag","token + save"+token)
                    var ipAddress=IpAddress();
                    FuelManager.instance.request(Method.POST, ipAddress.IpAddress+"/personal",listOf(Pair("token",token)))
                        .responseString(){request,response,result ->
                            print(request.request);
                            when(result){
                                is Result.Success -> {
                                    var data=result.getAs<String>();
                                    Log.i("tag","tagNickName.............."+data+",,,,,,,,,,GGGGGGGGGGGGGGGGGGGGGGGGGGg");
                                    var jsonObject:JSONObject=JSONObject(data);
                                    var  nickName = jsonObject.getString("token");
                                    Log.i("tag","tagNickName.............."+nickName);
                                    var msg:Message= Message();
                                    msg.obj=nickName;
                                    mHandler3.handleMessage(msg);
                                }
                            } }
                }
            }).start();
            swipeRefresh?.isRefreshing=false;
        } },500)






        swipeRefresh?.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                mHandler2.postDelayed(Runnable { kotlin.run {
                    Toast.makeText(instance1.context,"关闭",Toast.LENGTH_SHORT).show();
                    swipeRefresh?.isRefreshing=false;
                } },1000);
            }
        })

        linear?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var intent:Intent=Intent();
                intent= Intent(context, MainActivity4::class.java);
                intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity?.startActivityForResult(intent,1);
            }
        })
        textView?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var intent:Intent=Intent();
                intent= Intent(context, MainActivity4::class.java);
                intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity?.startActivityForResult(intent,1);
            }
        })
    }
    var mHandler3: Handler =object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            var msg1=msg.obj;
            textView?.text=msg1.toString();
//            Toast.makeText(instance1.requireContext(),"昵称"+msg1.toString(),Toast.LENGTH_SHORT).show();
            Log.i("tag","msg..........................................."+msg1);
        }
    }

}

