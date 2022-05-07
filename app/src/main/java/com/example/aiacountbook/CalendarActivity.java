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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    //private TextView title_year_month;     // 년/월 텍스트뷰
    private ActionBar ab;                   // 앱바
    private GridAdapter gridAdapter;        // 그리드뷰 어댑터
    private ArrayList<String> dayList;      // 일 저장할 리스트
    private GridView gridView;              // 그리드 뷰
    private Calendar calendar;              // 캘린더 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        
        // 앱바
        ab = getSupportActionBar() ;
        ab.setTitle("ActionBar Title by setTitle()");
//
//        // 뷰페이저와 어댑터 연결
//        ViewPager2 vpPager = findViewById(R.id.vpPager);
//        FragmentStateAdapter adapter = new PagerAdapter(this);
//        vpPager.setAdapter(adapter);
//
//
//
//        //플로팅 버튼 클릭 시 영수증 등록 액티비티 실행
//        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // 액티비티 열기
//                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
//                startActivity(intent);
//
////                // 토스트 메세지 전송
////                Toast.makeText(CalendarActivity.this, "클릭",
////                        Toast.LENGTH_SHORT).show();
//            }
//        });

        // 일수 표시할 그리드 뷰
        gridView = (GridView)findViewById(R.id.gridview);

        // 오늘 날짜 가져옴
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        // 년,월,일 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        //ab.setTitle(curYearFormat.format(date) + "년 " + curMonthFormat.format(date) + "월");
//
//        // gridview 요일 표시
//        dayList = new ArrayList<String>();
//
//        // 캘린더 객체 생성
//        calendar = Calendar.getInstance();
//
//        // 이번 달 1일 무슨 요일인지 판단 calendar.set(Year,Month,Day)
//        calendar.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
//        // 1:일, 2:월, ....
//        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
//        //1일 - 요일 매칭 시키기 위해 공백 add
//        for (int i = 1; i < dayNum; i++) {
//            dayList.add("");
//        }
//        setCalendarDate(calendar.get(Calendar.MONTH) + 1);
//
//        gridAdapter = new GridAdapter(getApplicationContext(), dayList);
//        gridView.setAdapter(gridAdapter);

    }

    /**
     * 해당 월에 표시할 일 수 구함
     *
     * @param month
     */
    private void setCalendarDate(int month) {
        calendar.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }

    }


    // 그리드 뷰 어댑터
    private class GridAdapter extends BaseAdapter {

        private final List<String> list;

        private final LayoutInflater inflater;

        /**
         * 생성자
         *
         * @param context
         * @param list
         */
        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_item_day, parent, false);
                holder = new ViewHolder();

                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));

            //해당 날짜 텍스트 컬러,배경 변경
            calendar = Calendar.getInstance();
            //오늘 day 가져옴
            Integer today = calendar.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.pink));
            }
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tvItemGridView;
    }
}