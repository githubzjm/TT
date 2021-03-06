package com.example.tt.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class myHttp {

    private static String host = "http://www.zjmpage.com:8000";
    public static String getHTTPReq(String router) {
        try {
            // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
            String getURL = host + router;
            URL getUrl = new URL(getURL);
            // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
            // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到服务器
            connection.connect();
            // 取得输入流，并使用Reader读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
            System.out.println("=============================");
            System.out.println("Contents of get request");
            System.out.println("=============================");
            String line;
            String respString = "";
            while ((line = reader.readLine()) != null) {
//             lines = new String(lines.getBytes(), "utf-8");
//            System.out.println(lines);
                respString += line+'\n';
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            System.out.println("=============================");
            System.out.println("Contents of get request end");
            System.out.println("=============================");
            return respString.trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String postHTTPReq(String router, String content) {
        try {
            // Post请求的url，与get不同的是不需要带参数
            URL postUrl = new URL(host+router);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            // Output to the connection. Default is false, set to true because post method must write something to the connection
            // 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true
            connection.setDoOutput(true);
            // Read from the connection. Default is true.
            connection.setDoInput(true);
            // Set the post method. Default is GET
            connection.setRequestMethod("POST");
            // Post cannot use caches
            // Post 请求不能使用缓存
            connection.setUseCaches(false);

            // This method takes effects to every instances of this class.URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
            // connection.setFollowRedirects(true);
            // This methods only takes effacts to this instance.URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            connection.setInstanceFollowRedirects(true);

            // Set the content type to urlencoded,because we will write some URL-encoded content to the connection. Settings above must be set before connect!
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成
            // 要注意的是connection.getOutputStream会隐含的进行connect
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            // The URL-encoded contend
            // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
//        String content = "name=" + URLEncoder.encode("张三", "utf-8");
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
            out.write(content.getBytes());
            out.flush();
            out.close();// flush and close
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
            String line = "";
            String respString = "";
            System.out.println("=============================");
            System.out.println("Contents of post request");
            System.out.println("=============================");
            while ((line = reader.readLine()) != null) {
                // line = new String(line.getBytes(), "utf-8");
//            System.out.println(line);
                respString += line+'\n';
            }
            reader.close();
            connection.disconnect();
            System.out.println("=============================");
            System.out.println("Contents of post request ends");
            System.out.println("=============================");
            return respString.trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static JSONArray getJsonArray(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            return jsonArray;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static JSONObject getJsonObject(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            return jsonObject;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

public static ArrayList<String> httpThread(final String[] reqs){
        final ArrayList<String> respStrArray = new ArrayList<String>();
        // 网络请求不能在UI线程内进行
        // 若有大量并发请求考虑用线程池管理
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < reqs.length; i = i+2){
                        String router = reqs[i];
                        String content = reqs[i+1];
                        if(content == ""){
                            // get
                            respStrArray.add(getHTTPReq(router));
                        }else{
                            // post
                            respStrArray.add(postHTTPReq(router, content));
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        // 线程同步
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        return respStrArray;
    }
}
