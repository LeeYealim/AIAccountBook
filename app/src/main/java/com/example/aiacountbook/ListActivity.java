package com.example.aiacountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aiacountbook.adapter.ListAdapter;
import com.example.aiacountbook.application.AiApplication;
import com.example.aiacountbook.api.GetRequest;
import com.example.aiacountbook.database.DBHelper;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    static ListAdapter adapter;
    private ActionBar ab;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        // 액티비티 생성 시 전달받은 데이터(년,월) 뽑음
        Intent intent = getIntent();
        String title = intent.getStringExtra("title") + " 목록";

        // 앱바 설정
        ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(false);     // 위로 버튼 비활성화
        ab.setTitle(title);                     // 앱바 타이틀 설정

        mDbHelper = new DBHelper(this);

        // 현재 달의 지출 내역 리스트 GET 요청 및 리스트 뷰 출력
        String yearmonth = ((AiApplication) getApplication()).getHyphenYearMonth();
        Log.d("yelim","리스트 액티비티 년월 : "+yearmonth);
        
        // DB에서 데이터 select 하여 보여줌
        viewAllToListView();

//        // HTTP API 호출
//        String uri = "https://ba0c-110-14-126-182.ngrok.io/accounts/"+yearmonth;
//        new GetRequest(ListActivity.this, uri, "list").execute();
    }

    private void viewAllToListView(){

        ArrayList<ListItem> list = new ArrayList<ListItem>();

        Cursor cursor = mDbHelper.getAllAccountBySQL();
        while (cursor.moveToNext()) {
            ListItem item = new ListItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2),""+cursor.getInt(3));
            list.add(item);
        }
        
        //어댑터 생성
        ListAdapter adapter = new ListAdapter(ListActivity.this, R.layout.list_item, list);

        //어댑터 연결
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked,
                                    int position, long id) {

                String place = ((ListItem)adapter.getItem(position)).place;
                Toast.makeText(ListActivity.this, place + " selected",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}