package com.example.myapplication.Tool

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.Acitiviy.MainActivity5

class UUIIDsharedPreferences {

    private var SharedUuid: SharedPreferences?=null;
    private var mEditor:SharedPreferences.Editor?=null;

    public fun  Uuid1SharedPreferences(context: Context, name:String){
        SharedUuid = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    public fun putString(name:String,value:String){
        mEditor=SharedUuid?.edit();
        mEditor?.putString(name,value)?.apply();
        mEditor?.commit();
    }
    public fun getString(name: String, value: String, context: Context):String{
        SharedUuid = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        var share:String= SharedUuid?.getString(value,"")!!;
        return share;
    }


}