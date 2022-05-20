package com.example.aiacountbook.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.aiacountbook.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class GetRequest extends AsyncTask<String, Void, String> {
    protected Activity activity;
    protected URL url;
    final static String TAG = "AndroidAPITest";
    String urlStr;

    // 생성자
    public GetRequest(Activity activity, String urlStr) {
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
    protected String doInBackground(String... strings) {
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
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);

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
        Log.d("yelim","onPostExecute()...");
        Log.d("yelim",result);

        JSONObject jObject;
        JSONArray jArray = null;
        try {
            jObject = new JSONObject(result);
            jArray = jObject.getJSONArray("list");
            Log.d("yelim",jArray.toString());
            Log.d("yelim",jArray.getJSONObject(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ArrayList<Item> list = jsonToItemList(jArray);

    }

    private ArrayList<Item> jsonToItemList(JSONArray jsonList) throws JSONException {
        Log.d("yelim","jsonToItemList() 호출 ...");

        ArrayList<Item> list = new ArrayList<Item>();

        for(int i=0; i<jsonList.length(); i++){
            JSONObject obj = jsonList.getJSONObject(i);
        }

        JSONObject j = jsonList.getJSONObject(0);

        return null;
    }
}