package com.example.aiacountbook.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aiacountbook.AiApplication;
import com.example.aiacountbook.Item;
import com.example.aiacountbook.ListActivity;
import com.example.aiacountbook.ListAdapter;
import com.example.aiacountbook.R;

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
    String type;

    // 생성자
    public GetRequest(Activity activity, String urlStr, String type) {
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
        ArrayList<Item> list = null;

        // 목록 화면에서의 GET 요청일 경우
        if(type.equals("list")){
            // 데이터 얻기
            try {
                jObject = new JSONObject(result);
                jArray = jObject.getJSONArray("list");
                //Log.d("yelim",jArray.toString());
                //Log.d("yelim",jArray.getJSONObject(0).toString());

                list = jsonToItemList(jArray);
                //Log.d("yelim","만들어진 Item 배열");
                //Log.d("yelim",list.get(0).place);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //어댑터 생성
            ListAdapter adapter = new ListAdapter(activity, R.layout.list_item, list);

            //어댑터 연결
            ListView listView = (ListView)activity.findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View vClicked,
                                        int position, long id) {

                    String place = ((Item)adapter.getItem(position)).place;
                    Toast.makeText(activity, place + " selected",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Json 배열을 Item 객체 배열로 바꾸는 메소드
    private ArrayList<Item> jsonToItemList(JSONArray jsonList) throws JSONException {
        Log.d("yelim","jsonToItemList() 호출 ...");

        ArrayList<Item> list = new ArrayList<Item>();

        for(int i=0; i<jsonList.length(); i++){
            JSONObject obj = jsonList.getJSONObject(i);
            Item item = new Item(obj.getInt("idx"),obj.getString("date"),obj.getString("place"),obj.getString("price"));
            list.add(item);
        }

        return list;
    }
}