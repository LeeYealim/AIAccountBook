package com.example.aiacountbook.api;

// PutRequest 와 코드 유사하지만 보기 편하려고 따로 정의함


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aiacountbook.R;

import org.json.JSONException;
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
    String type;

    // 생성자 - type에 따라 결과 처리 부분 다름
    public PostRequest(Activity activity, String urlStr, String type) {
        this.activity = activity;
        this.urlStr = urlStr;
        this.type = type;
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

        if(type.equals("recognition")){
            Log.d("yelim","recognition 결과 처리 ... ");

            JSONObject jObject = null;
            String date = "", place = "", price = "";
            try {
                jObject = new JSONObject(result);
                date = jObject.getString("date");
                place = jObject.getString("place");
                price = jObject.getString("price");
                Log.d("yelim", "파싱 값 : " + date +" "+place + " "+price);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            EditText edit_date = (EditText)activity.findViewById(R.id.edit_date);
            edit_date.setText(date);

            EditText edit_place = (EditText)activity.findViewById(R.id.edit_place);
            edit_place.setText(place);

            EditText edit_price = (EditText)activity.findViewById(R.id.edit_price);
            edit_price.setText(price);
        }
        else if(type.equals("insert")){
            Log.d("yelim","insert 결과 처리 ... ");

            JSONObject jObject = null;
            String api_result = "";
            try {
                jObject = new JSONObject(result);
                api_result = jObject.getString("result");
                Log.d("yelim", "result 파싱 값 : " + api_result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(api_result.equals("SUCCESS")){
                Toast.makeText(activity, "저장되었습니다.",
                        Toast.LENGTH_SHORT).show();
                activity.finish();
            }
            else{
                Toast.makeText(activity, "오류가 발생했습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.d("yelim","else 결과 처리 ... ");

        }
    }
}