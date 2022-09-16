package com.example.myapplication.Acitiviy

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.R
import com.example.myapplication.Tool.UUIIDsharedPreferences
import com.example.myapplication.custom_view.ImageViewCircle1
import com.example.myapplication.network.IpAddress
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.util.encodeBase64ToString
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import org.json.JSONObject
import java.io.InputStream

class MainActivity5 : AppCompatActivity() {

//    private var dialogView:View?=null;
    private var editText:EditText?=null;
    private var relativet  : RelativeLayout?=null;
    private var relativet1 : RelativeLayout?=null;
    private var relativet2 : RelativeLayout?=null;
    private var relativet3 : RelativeLayout?=null;
    private var imageCircle:ImageViewCircle1?=null;
    private var buttonPut  :Button?=null;
    private var costomizeDalog:AlertDialog.Builder?=null;
    private val instance by lazy { this }
    private var button: Button?=null;
    private var imageTink:ImageView?=null;
    private var imageTelephone:ImageView?=null;
    private var imageNeckName:ImageView?=null;
    private var ImageOK:Int=1;
    private var nicheng:TextView?=null;


    private var nickName:String="";
    private var telephone:String="";
    private var password:String="";

    var uuiiDshared:UUIIDsharedPreferences= UUIIDsharedPreferences();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        button=findViewById(R.id.button1) as Button;
        nicheng=findViewById(R.id.nicheng) as TextView;
        relativet=findViewById(R.id.relativet) as RelativeLayout;
        relativet1=findViewById(R.id.relativet1) as RelativeLayout;
        relativet2=findViewById(R.id.relativet2) as RelativeLayout;
        relativet3=findViewById(R.id.relativet3) as RelativeLayout;
        buttonPut=findViewById(R.id.button1) as Button;
        imageCircle=findViewById(R.id.ImageHead) as ImageViewCircle1;
        var mBitmap=this.resources?.getDrawable(R.mipmap.ic_launcher_round,null)?.toBitmap();
        imageCircle?.setBackgroundResource(mBitmap!!);
        imageTink=findViewById(R.id.tick) as ImageView;
        imageTelephone=findViewById(R.id.tickT) as ImageView;
        imageNeckName=findViewById(R.id.tickN) as ImageView;
        initListener();
    }
    fun initListener(){
        //图片
        relativet?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var intent:Intent=Intent();
                    intent.action=Intent.ACTION_GET_CONTENT;
                    intent.type="image/*";
                    Toast.makeText(instance,"图片",Toast.LENGTH_SHORT).show();
            }
        })
        //昵称
        relativet1?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                dialogListener(R.layout.activity_main5_dialog,"昵称");
                costomizeDalog?.create()?.show()
            }
        })
        relativet2?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                dialogListener(R.layout.activity_main6_dialog,"手机号");
                costomizeDalog?.create()?.show()
            }
        })
        relativet3?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                dialogListener(R.layout.activity_main7_dialog,"密码修改");
                costomizeDalog?.create()?.show()
            }
        })


        buttonPut?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var code=nickName.encodeBase64ToString();
                FuelManager.instance.request(Method.POST,IpAddress().IpAddress+"/register",
                    listOf(Pair("telephone",telephone),Pair("neckname",nickName), Pair("password",password)),
                ).responseString(){request,response,result ->
                    when(result){
                          is Result.Failure ->{

                          }
                          is Result.Success ->{
                                var data=result.getAs<String>();
                                var jsonObject:JSONObject= JSONObject(data);
                                var str=jsonObject.get("data");
                                var uuid=jsonObject.get("UUID");
                                var token=jsonObject.get("token");
                                    uuiiDshared.Uuid1SharedPreferences(instance,"idCache");
                                    uuiiDshared.putString("uuid", uuid.toString());
                                    uuiiDshared.putString("token",token.toString());
                              System.out.println(jsonObject.toString()+"..................");
                              var msg=Message();
                                msg.what=1;
                                msg.obj=str;
                                mHandler.handleMessage(msg);
                          }
                        }
                    }
            }
        })
    }

    var mHandler:Handler = object:Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                1->
                    Toast.makeText(instance,msg.obj.toString(),Toast.LENGTH_SHORT).show();
            }
            Log.i("tag","保存1"+uuiiDshared.getString("idCache","uuid",instance));
            finish();
        }
    };
    fun dialogListener(activity_dialog:Int,title:String){
        var dialogView=LayoutInflater.from(instance).inflate(activity_dialog,null);
        costomizeDalog   =AlertDialog.Builder(this);
        costomizeDalog?.setTitle(title);
        if(title.equals("手机号")){
            var telephone0=dialogView.findViewById(R.id.telephone) as EditText;
            costomizeDalog?.setView(dialogView);
            costomizeDalog?.setPositiveButton("确定",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if(!telephone0.text.toString().equals("")){
                        telephone= telephone0.text.toString();
                        imageTelephone?.visibility=View.VISIBLE;
                    }else{
                        imageTelephone?.visibility=View.GONE;
                    }
                    dialog?.cancel();
                }
            });
        }else if(title.equals("昵称")){
            var name=dialogView.findViewById(R.id.name) as EditText;
            costomizeDalog?.setView(dialogView);
            costomizeDalog?.setPositiveButton("确定",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if(name.text.toString().equals("")){
                        imageNeckName?.visibility=View.GONE;
                    }else{
                        nickName=name.text.toString();
                        nicheng?.setText(nickName);
                        imageNeckName?.visibility=View.VISIBLE;
                    }
                    dialog?.cancel();
                }
            });
        }else if(title.equals("密码修改")){
            var password0=dialogView.findViewById(R.id.password2) as EditText;
            var password1=dialogView.findViewById(R.id.password1) as EditText;
            costomizeDalog?.setView(dialogView);
            costomizeDalog?.setPositiveButton("确定",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    if(password0.text.toString().equals("") || password1.text.toString().equals("")){
                        Toast.makeText(instance,"密码不能为空",Toast.LENGTH_SHORT).show();
                        imageTink?.visibility=View.GONE;
                    }else{
                        if(password0.text.toString().equals(password1.text.toString())){
                            password=password0.text.toString();
                            imageTink?.visibility=View.VISIBLE;
                        }else {
                            password0.setText("");
                            password1.setText("");
                            Toast.makeText(instance,"两次密码不同",Toast.LENGTH_SHORT).show();
                            imageTink?.visibility=View.GONE;
                        }
                    }
                }
            });
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var uri:Uri=data?.data!!;
        var input:InputStream = contentResolver.openInputStream(uri)!!;
        var bitmap:Bitmap=BitmapFactory.decodeStream(input);
        imageCircle?.setBackgroundResource(bitmap);
    }
}