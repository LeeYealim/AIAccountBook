package com.example.aiacountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    static ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // 데이터 원본 준비
        ArrayList<DayItem> data = new ArrayList<DayItem>();
        data.add(new DayItem(1, "롯데리아", "1"));
        data.add(new DayItem(2, "하나로 마트", "2"));


        //어댑터 생성
        adapter = new ListAdapter(this, R.layout.list_item, data);

        //어댑터 연결
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked,
                                    int position, long id) {
                //   String name = (String) ((TextView)vClicked.findViewById(R.id.textItem1)).getText();
                String name = ((DayItem)adapter.getItem(position)).nName;
                Toast.makeText(ListActivity.this, name + " selected",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


}