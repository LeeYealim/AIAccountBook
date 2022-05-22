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

        // 앱바 설정
        ActionBarTitle =  calendar.get(Calendar.YEAR)+"년 "+(calendar.get(Calendar.MONTH)+1+"월");
        getSupportActionBar().setTitle(ActionBarTitle);

        // 1:일, 2:월, ....
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("yelim","요일 : " + dayNum);
        Log.d("yelim", "year = " + calendar.get(Calendar.YEAR));
        Log.d("yelim", "month = " + (calendar.get(Calendar.MONTH)+1));     // 달은 -1 적게 나옴

        // 뷰페이저 포지션 설정
        int idx = calendar.get(Calendar.YEAR)*12 + calendar.get(Calendar.MONTH) - 2000*12;      // 달은 -1 적게 나옴
        Log.d("yelim","------------------------------------n : " + idx);


        // 뷰 페이저랑 프래그먼트 어댑터 연결
        vpPager = findViewById(R.id.vpPager);
        adapter = new PagerAdapter(this);
        vpPager.setAdapter(adapter);

        // 뷰페이저 페이지 설정
        //vpPager.setCurrentItem(idx);      // 원래 이게 맞는데 -11정도 오류남..
        vpPager.setCurrentItem(idx+11);

        // 뷰 페이저 페이지 변경 이벤트
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("yelim", "!!! 뷰페이저 셀렉티드 : " + position);
                // 앱바 설정
                int year = 2000 + position/12;
                int month = position%12+1;
                ActionBarTitle =  year+"년 "+month+"월";
                getSupportActionBar().setTitle(ActionBarTitle);
            }
        });


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

        //startActivity(new Intent(this,ListActivity.class));
        //인텐트 선언 및 정의
        Intent intent =new Intent(this, ListActivity.class);
        // 입력한 input값을 intent로 전달한다.
        intent.putExtra("title", ActionBarTitle);
        // 액티비티 이동
        startActivity(intent);

        return true;
    }
    // 메뉴 --------------------------------------------------




//
//    /**
//     * 해당 월에 표시할 일 수 구함
//     *
//     * @param month
//     */
//    private void setCalendarDate(int month) {
//        calendar.set(Calendar.MONTH, month - 1);
//
//        for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
//            dayList.add("" + (i + 1));
//        }
//
//    }
//
//
//    // 그리드 뷰 어댑터
//    // 여기에서 일수 칸에 데이터 셋팅
//    private class GridAdapter extends BaseAdapter {
//
//        private final List<String> dayList;
//        private final LayoutInflater inflater;
//        private final List<String> valList;
//
//        /**
//         * 생성자
//         *  @param context
//         * @param list
//         * @param list2
//         */
//        public GridAdapter(Context context, List<String> list, List<String> list2) {
//            this.dayList = list;
//            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            this.valList = list2;
//        }
//
//        @Override
//        public int getCount() {
//            return dayList.size();
//        }
//
//        @Override
//        public String getItem(int position) {
//            return dayList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            ViewHolder holder = null;
//
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.calendar_item_day, parent, false);
//                holder = new ViewHolder();
//
//                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.textview_day);
//                holder.tv_count = (TextView)convertView.findViewById(R.id.textview_count);
//                holder.tv_price = (TextView)convertView.findViewById(R.id.textview_total);
//
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder)convertView.getTag();
//            }
//            holder.tvItemGridView.setText("" + getItem(position));
//
//            // 임의로 값 집어 넣음
//            if(getItem(position).equals("5")){
//                holder.tv_count.setText("2건");
//                holder.tv_price.setText("30000원");
//            }
//
//            //해당 날짜 텍스트 컬러,배경 변경
//            calendar = Calendar.getInstance();
//            //오늘 day 가져옴
//            Integer today = calendar.get(Calendar.DAY_OF_MONTH);
//            String sToday = String.valueOf(today);
//            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
//                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.pink));
//            }
//            return convertView;
//        }
//    }
//
//    public static class ViewHolder {
//        TextView tvItemGridView;
//        TextView tv_count;
//        TextView tv_price;
//    }
}