package com.example.aiacountbook.api;

import android.app.Activity;

import com.example.aiacountbook.adapter.GridAdapter;

public class GridGetRequest extends GetRequest{
    GridAdapter adapter;
    
    // GridAdapter 레퍼런스 전달받아서 내부 리스트 mItems 업데이트
    public GridGetRequest(Activity activity, String urlStr, String type, GridAdapter adapter) {
        super(activity, urlStr, type);
        this.adapter = adapter;
    }
    
    // 결과를 처리해주는 메소드만 재정의
    @Override
    protected void onPostExecute(String result){
        if(type.equals("calendar")){

        }
    }
}
