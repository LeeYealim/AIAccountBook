package com.example.aiacountbook.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.example.aiacountbook.R;
import com.example.aiacountbook.adapter.GridAdapter;

public class GridGetRequest extends GetRequest{
    int year;
    int month;
    GridAdapter adapter;
    
    // GridAdapter 레퍼런스 전달받아서 내부 리스트 mItems 업데이트
    public GridGetRequest(Activity activity, String urlStr, String type,GridAdapter adapter, int year, int month) {
        super(activity, urlStr, type);
        this.year = year;
        this.month = month;
        this.adapter = adapter;     // 그리드뷰 어댑터 넘겨받아서 배열 설정 다시 할 것임
    }
    
    // 결과를 처리해주는 메소드만 재정의
    @Override
    protected void onPostExecute(String result){
        if(type.equals("calendar")){
            Log.d("yelim", "GridGetRequest > onPostExecute > calendar");
            Log.d("yelim", result);




//            // 여기서 하니까 달력 로딩 너무 오래 걸림
//            GridAdapter adapter = new GridAdapter(activity, R.layout.calendar_item_day, year, month);
//            //어댑터 연결
//            GridView gridView = (GridView)view.findViewById(R.id.gridview);
//            gridView.setAdapter(adapter);

        }
    }
}
