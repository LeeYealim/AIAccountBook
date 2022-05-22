package com.example.aiacountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiacountbook.adapter.PagerAdapter;
import com.example.aiacountbook.application.AiApplication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private String ActionBarTitle;
    ViewPager2 vpPager;
    PagerAdapter adapter;
    int year;
    int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        //플로팅 버튼 클릭 시 영수증 등록 액티비티 실행
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("yelim", ""+vpPager.getCurrentItem()); // ------------------------- 잠깐 테스트용

//                // 액티비티 열기
//                //Intent intent = new Intent(getApplicationContext(), ImageTestActivity.class);
//                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
//                startActivity(intent);

            }
        });


        // 현재 날짜 캘린더 객체 생성
        Calendar calendar = Calendar.getInstance();

        // 이번 달 1일 무슨 요일인지 판단 calendar.set(Year,Month,Day)
        //calendar.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        calendar.set(Calendar.DATE, 1);     // 캘린더 객체 일을 1일로 변경

        // 전역 변수에 현재 년, 월 설정
        //((AiApplication) getApplication()).setYearMonth(""+calendar.get(Calendar.YEAR),""+(calendar.get(Calendar.MONTH)+1));

        // 1:일, 2:월, ....
        year = calendar.get(Calendar.YEAR);
        month = (calendar.get(Calendar.MONTH)+1);
        ((AiApplication) getApplication()).setYearMonth(""+year, ""+month);
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("yelim", "year:" + year + " month:"+(month+1) + " 요일:"+dayNum);

        // 앱바 설정
        ActionBarTitle =  ((AiApplication) getApplication()).getStrYearMonth();
        getSupportActionBar().setTitle(ActionBarTitle);

        // 뷰페이저 포지션 설정
        int idx = year*12 + month - 2000*12;      // 달은 -1 적게 나옴
        Log.d("yelim","------------------------------------ 초기 포지션(year*12+month(0부터)-2000*12) : " + idx);

        // 뷰 페이저랑 프래그먼트 어댑터 연결
        vpPager = findViewById(R.id.vpPager);
        adapter = new PagerAdapter(this);
        vpPager.setAdapter(adapter);

        // 뷰페이저 페이지 설정
        //vpPager.setCurrentItem(idx);      // 원래 이게 맞는데 -11~10정도 오류남..
        vpPager.setCurrentItem(idx);
        //vpPager.setCurrentItem(3);

        // 뷰 페이저 페이지 변경 이벤트
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("yelim", "!!! 뷰페이저 셀렉티드 : " + position);
                // 앱바 설정
                int year = 2000 + position/12;
                int month = position%12+1;
                ((AiApplication) getApplication()).setYearMonth(""+year, ""+month);
                ActionBarTitle =  year+"년 "+month+"월";
                getSupportActionBar().setTitle(ActionBarTitle);
            }
        });

//        // 뷰페이저 페이지 설정 여기에 하면 계속 자동으로 스와이프 되는 오류 남 !!!!!!!
//        //vpPager.setCurrentItem(idx);      // 원래 이게 맞는데 -11정도 오류남..
//        vpPager.setCurrentItem(idx+11);
    }

    // 메뉴 --------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    // 메뉴 선택 시 액션
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("yelim","onOptionsItemSelected() 클릭....");
        //startActivity(new Intent(this,ListActivity.class));
        //인텐트 선언 및 정의
        Intent intent =new Intent(this, ListActivity.class);
        // 입력한 input값을 intent로 전달한다.
        intent.putExtra("title", ActionBarTitle);
        // 액티비티 이동
        startActivity(intent);
        return true;
    }

}