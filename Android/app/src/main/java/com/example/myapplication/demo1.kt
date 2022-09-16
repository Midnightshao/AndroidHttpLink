package com.example.myapplication

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import java.util.*

//class demo1 {
//    companion object{
//        var lock:Boolean?=true;
//        var mythread:MyThread=demo1().MyThread();

//
//        @InternalCoroutinesApi
//        @JvmStatic
//        fun main(args: Array<String>) {

//            var abc:Int=1;
//            var cef:Float;
//            var mThread= Thread(object :Thread(){
//
//                override fun run() {
//                    while (true){
//                        if ( lock==true) {
//                                println("abcdefffajiwoefjoweajf");
//                                Thread.sleep(1000);
//                        }else{
//
//                        }
//                    }
//                }
//            });
//            mythread.start();
//            var mThread2=MyThread(){
//
//            };


//            var mThread1= Thread(object :Thread(){
//                override fun run() {
//                    while (true){
//                        println("提问")
//                        var scan:Scanner= Scanner(System.`in`);
//                        var str:String =scan.nextLine();
//                            if(str.equals("stop")){
//                                println(",,,,,,,,,,,onlock")
//                                mythread.sychronizedMethod();
//                                print("...............生存与否"+mythread.isAlive+"boolean="+ mythread.suspended);
//                            }else if (str.equals("start")){
//                                println(",,,,,,,,,,,lock")
//                                mythread.sychronizedmyresume();
//                                println("...............生存与否"+mythread.isAlive);
//
//
//                            }
//                    }
//                }
//            });
//            mThread1.start();
//            while (true){
//                var scan:Scanner= Scanner(System.`in`);
//                var str:String =scan.nextLine();
//                if(str.equals("stop")){
//                    println(",,,,,,,,,,,onlock")
//                    mythread.sychronizedMethod();
//                    print("...............生存与否"+mythread.isAlive+"boolean="+ mythread.suspended);
//                }else if (str.equals("start")){
//                    println(",,,,,,,,,,,lock")
//                    mythread.sychronizedmyresume();
//                    println("...............生存与否"+mythread.isAlive);
//
//                }
//            }
//
//
//
//        }
//    }
//     public inner class MyThread: Thread {
//
//        var object1:Object=Object();
//        var suspended:Boolean?=true;
//        var stopped:Boolean?=null;
//        constructor(){
//            suspended=true;
//            stopped=false;
//
//        }
//
//        @InternalCoroutinesApi
//        override fun run() {
//            while (true){
//                synchronized(object1){
//                    while (suspended == false){
//                        object1.notify()
//                    }
//                    println("xxxxxxxxxxxxxxx");
//                    sleep(1000);
//                }
//            }
//        }
//
//        @Synchronized fun sychronizedMethod(){
//            suspended=false;
//        }
//
//        @Synchronized fun sychronizedmyresume(){
//            suspended=true;
//        }
//    }

//}