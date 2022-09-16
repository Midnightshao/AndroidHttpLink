package com.example.myapplication.Acitiviy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.Tool.UUIIDsharedPreferences
import com.example.myapplication.network.IpAddress
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.result.getAs
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.requests.download
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONObject


class MainActivity4 : AppCompatActivity() {
    var initRegister: TextView? = null;
    var editText: EditText? = null;
    var editTextP: EditText? = null;
    var buttonApp: Button? = null;
    val instance by lazy { this }
    var uuiiDshared: UUIIDsharedPreferences = UUIIDsharedPreferences();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        initButton();
        initOnClickListener();
    }

    fun initButton() {
        initRegister = findViewById(R.id.register);
        editText = findViewById(R.id.editTextTextUsername);
        editTextP = findViewById(R.id.editTextTextPassword);
        buttonApp = findViewById(R.id.loginApp);
    }

    fun initOnClickListener() {
        buttonApp?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                Toast.makeText(instance,"登陆",Toast.LENGTH_SHORT).show();
                var list =
                    listOf(Pair("neckname", editText?.text), Pair("password", editTextP?.text));
                Thread(Runnable {
                    internatURL(
                        IpAddress().IpAddress + "/login",
                        list as List<Pair<String, Object>>
                    );
                }).start();
            }
        })

        initRegister?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var intent: Intent = Intent();
                intent.setClass(instance, MainActivity5::class.java);
                instance.startActivity(intent);
            }
        })

    }

    fun internatURL(url: String, list: List<Pair<String, Object>>) {
        FuelManager.instance.request(Method.POST, url, list)
            .responseString() { request, response, result ->
                print(request.request);
                when (result) {
                    is Result.Success -> {
                        var data = result.getAs<String>()!!;
                        var message: Message = Message();
//                        var jsonArray:JSONArray= JSONArray(data);
                        var jsonObject: JSONObject = JSONObject(data);
                        var msg = jsonObject.getString("data");
                        var enroll:Boolean=jsonObject.getBoolean("enroll");
                        var token:String=jsonObject.getString("token");
                        uuiiDshared.Uuid1SharedPreferences(instance,"idCache");
                        if(enroll){
                            uuiiDshared.putString("token",token);
                            message.what = 0;

                        }else{
                            message.what = 1;
                        }
                        message.obj = msg;
                        mHandler.sendMessage(message);
                        Log.i("tag", "tagLogin" + data);
                    }
                    is Result.Failure -> {

                    }
                }
            }
    }


    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                        var text=msg.obj.toString();
                        Toast.makeText(instance, text, Toast.LENGTH_SHORT).show();
                        finish();
                }
                1 ->{
                    var text=msg.obj.toString();
                    Toast.makeText(instance, text, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}