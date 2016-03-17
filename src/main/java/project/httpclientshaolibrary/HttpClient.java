package project.httpclientshaolibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanghaoshao on 16/3/17.
 */
public class HttpClient {

    private List<Map<String,Object>> list;
    public HttpClient(){
        list= new ArrayList<Map<String, Object>>();
    }

    public void addHeader(String key,String value){
        Map<String,Object> map=new HashMap<String,Object>();

        map.put(key,value);

        list.add(map);
    }
    @Deprecated
    public void setHeader(HttpURLConnection connection){

        for(int i=0;i<list.size();i++){

            HashMap<String,Object> map= (HashMap) list.get(i);

            for(Map.Entry<String, Object> entry : map.entrySet()){

                connection.setRequestProperty(entry.getKey(), entry.getValue().toString());

            }
        }
    }
    public String PostHttpClient(String url,Map<String,Object> map){

        URL localURL;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        InputStreamReader inputStreamReader=null;
        BufferedReader reader=null;
        InputStream inputStream=null;
        try {
            //用户地址
            localURL = new URL(url);
            //开启连接
            URLConnection connection=localURL.openConnection();
//            获取HttpURLConnection连接
            HttpURLConnection httpURLConnection=(HttpURLConnection)connection;
            //输出流，需要从电脑中输出的
            httpURLConnection.setDoOutput(true);
            //输入流
            httpURLConnection.setDoInput(true);
            //不允许缓存
            httpURLConnection.setUseCaches(false);
            //POST提交
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");

//            添加请求头
            setHeader(httpURLConnection);

            connection.setConnectTimeout(3000);

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.connect();

            for(Map.Entry<String, Object> entry : map.entrySet()){

                String name=entry.getKey();

                String value= String.valueOf(entry.getValue());

                String st=name+"="+value+"&";

                httpURLConnection.getOutputStream().write(st.getBytes());
            }


            if(httpURLConnection.getResponseCode() >=300){
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if(httpURLConnection.getResponseCode()==200){
                inputStream=httpURLConnection.getInputStream();

                inputStreamReader=new InputStreamReader(inputStream);

                reader=new BufferedReader(inputStreamReader);

                while ((tempLine = reader.readLine())!=null){
                    resultBuffer.append(tempLine);
                }
            }else {
                throw  new Exception("http连接失败");
            }

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBuffer.toString();
    }
    public String GetHttpClient(String url){

        URL localURL;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        InputStreamReader inputStreamReader=null;
        BufferedReader reader=null;
        InputStream inputStream=null;
        try {
            //用户地址
            localURL = new URL(url);
            //开启连接

            URLConnection connection=localURL.openConnection();
//            获取HttpURLConnection连接
            HttpURLConnection httpURLConnection=(HttpURLConnection)connection;
            //输出流，需要从电脑中输出的
            httpURLConnection.setDoOutput(false);
            //输入流
            httpURLConnection.setDoInput(true);
            //不允许缓存
            httpURLConnection.setUseCaches(false);
            //POST提交
            httpURLConnection.setRequestMethod("GET");


            connection.setRequestProperty("accept", "*/*");
            //长链接
            connection.setRequestProperty("connection", "Keep-Alive");

            connection.setConnectTimeout(3000);
//            添加请求头
            setHeader(httpURLConnection);

            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            try {
                httpURLConnection.connect();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("sssssssseee33333");
            }

            if(httpURLConnection.getResponseCode() >200){

                throw new Exception("连接没有成功 " + httpURLConnection.getResponseCode());
            }

            if(httpURLConnection.HTTP_OK==200){
                inputStream=httpURLConnection.getInputStream();

                inputStreamReader=new InputStreamReader(inputStream);

                reader=new BufferedReader(inputStreamReader);

                while ((tempLine = reader.readLine())!=null){
                    resultBuffer.append(tempLine);
                }
            }else {
                throw  new Exception("http连接失败");
            }
            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (MalformedURLException e) {
            System.out.println("ssssssssss");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBuffer.toString();
    }

}
