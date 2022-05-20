package com.example.aiacountbook.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.aiacountbook.adapter.ListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class DeleteRequest extends AsyncTask<String, Void, String> {
    protected Activity activity;
    protected URL url;
    final static String TAG = "AndroidAPITest";
    String urlStr;
    ListAdapter adapter;    // 리스트뷰 어댑터
    int position;           // 리스트뷰 항목 position  - 배열 갱신에 사용

    // 생성자
    public DeleteRequest(Activity activity, String urlStr, ListAdapter adapter, int position) {
        this.activity = activity;
        this.urlStr = urlStr;
        this.adapter = adapter;
        this.position = position;
    }

    // execute() 호출 시 가장 먼저 1. onPreExecute() 호출
    @Override
    protected void onPreExecute() {
        Log.d("yelim", "onPreExecute()...");
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
    protected String doInBackground(String... strings) {
        Log.d("yelim", "doInBackground()...");

        StringBuffer output = new StringBuffer();

        try {
            if (url == null) {
                Log.e(TAG, "Error: URL is null ");
                return null;
            }
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn == null) {
                Log.e(TAG, "HttpsURLConnection Error");
                return null;
            }
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("DELETE");
            //conn.setDoInput(true);
            //conn.setDoOutput(false);

            int resCode = conn.getResponseCode();

            if (resCode != HttpsURLConnection.HTTP_OK) {
                Log.e(TAG, "HttpsURLConnection ResponseCode: " + resCode);
                conn.disconnect();
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                output.append(line);
            }

            reader.close();
            conn.disconnect();

        } catch (IOException ex) {
            Log.e(TAG, "Exception in processing response.", ex);
            ex.printStackTrace();
        }

        return output.toString();
    }

    // 리퀘스트 결과를 처리할 수 있는 3. onPostExecute() 호출
    @Override
    protected void onPostExecute(String result) {
        Log.d("yelim", "onPostExecute()...");

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
            Toast.makeText(activity, "삭제되었습니다.",
                    Toast.LENGTH_SHORT).show();

            // 리스트뷰 어댑터 배열 항목 삭제 후, 리스트뷰 갱신
            adapter.mItems.remove(position);
            adapter.notifyDataSetChanged();

        }
        else{
            Toast.makeText(activity, "오류가 발생했습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

}