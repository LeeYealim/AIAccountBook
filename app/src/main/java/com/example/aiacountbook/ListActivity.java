package com.example.aiacountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.aiacountbook.Adapter.ListAdapter;
import com.example.aiacountbook.Application.AiApplication;
import com.example.aiacountbook.api.GetRequest;

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
        
        // 현재 달의 지출 내역 리스트 GET 요청 및 리스트 뷰 출력
        String yearmonth = ((AiApplication) getApplication()).getHyphenYearMonth();
        String uri = "https://e866-110-14-126-182.ngrok.io/accounts/"+yearmonth;
        new GetRequest(ListActivity.this, uri, "list").execute();

        //setListView();

    }

    private void setListView(){
        // 데이터 원본 준비
        ArrayList<Item> data = new ArrayList<Item>();
        data.add(new Item(1, "2022-05-05", "롯데리아", "10000"));
        data.add(new Item(2, "2022-05-05", "BBQ 치킨", "20000"));

        //어댑터 생성
        adapter = new ListAdapter(this, R.layout.list_item, data);

        //어댑터 연결
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        
//        // 리스트뷰 아이템 클릭 이벤트가 왜 안먹히지
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View vClicked,
//                                    int position, long id) {
//
//                Log.d("yelim","리스트뷰 아이템 클릭");
//
//                //   String name = (String) ((TextView)vClicked.findViewById(R.id.textItem1)).getText();
//                String title = ((Item)adapter.getItem(position)).place;
//                Toast.makeText(ListActivity.this, title + " selected",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}