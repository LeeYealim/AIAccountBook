package com.example.aiacountbook.api;

// PutRequest 와 코드 유사하지만 보기 편하려고 따로 정의함


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class PostRequest extends AsyncTask<JSONObject, Void, String> {
    protected Activity activity;
    protected URL url;
    final static String TAG = "AndroidAPITest";
    String urlStr;

    // 생성자
    public PostRequest(Activity activity, String urlStr) {
        this.activity = activity;
        this.urlStr = urlStr;
    }

    // execute() 호출 시 가장 먼저 1. onPreExecute() 호출
    @Override
    protected void onPreExecute() {
        try {
            Log.e(TAG, urlStr);
            url = new URL(urlStr);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

    // 그 다음 2. doInBackground() 호출
    @Override
    protected String doInBackground(JSONObject... postDataParams) {

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(10000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            String str = postDataParams[0].toString();

            Log.d("yelim","postDataParams[0].toString();");
            Log.d("yelim",str);
            Log.e("params", "Post String = " + str);

            writer.write(str);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

            } else {
                return new String("Server Error : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 리퀘스트 결과를 처리할 수 있는 3. onPostExecute() 호출
    @Override
    protected void onPostExecute(String result) {
        // result 가 응답임
        Log.d("yelim","onPostExecute()...");
        Log.d("yelim",result);
    }

}