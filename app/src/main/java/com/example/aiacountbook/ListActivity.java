package com.example.aiacountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    static ListAdapter adapter;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        // 액티비티 생성 시 전달받은 데이터(년,월) 뽑음
        Intent intent = getIntent(); 
        String title = intent.getStringExtra("title") + " 목록";
        
        // 앱바 설정
        ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(true);     // 위로 버튼 활성화
        ab.setTitle(title);                     // 앱바 타이틀 설정

        // 데이터 원본 준비
        ArrayList<DayItem> data = new ArrayList<DayItem>();
        data.add(new DayItem("2022-05-05", "롯데리아", "10000"));
        data.add(new DayItem("2022-05-05", "BBQ 치킨", "20000"));

        //어댑터 생성
        adapter = new ListAdapter(this, R.layout.list_item, data);

        //어댑터 연결
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked,
                                    int position, long id) {
                //   String name = (String) ((TextView)vClicked.findViewById(R.id.textItem1)).getText();
                String title = ((DayItem)adapter.getItem(position)).title;
                Toast.makeText(ListActivity.this, title + " selected",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

}