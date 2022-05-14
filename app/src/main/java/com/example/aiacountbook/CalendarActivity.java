package com.example.aiacountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

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
    private String ActionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


//        // 뷰페이저와 어댑터 연결
//        ViewPager2 vpPager = findViewById(R.id.vpPager);
//        FragmentStateAdapter adapter = new PagerAdapter(this);
//        vpPager.setAdapter(adapter);
//
//        // Attach the page change listener inside the activity
//        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                Toast.makeText(CalendarActivity.this,
//                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
//
//            }
//        });


        //플로팅 버튼 클릭 시 영수증 등록 액티비티 실행
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 액티비티 열기
                //Intent intent = new Intent(getApplicationContext(), ImageTestActivity.class);
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);

//                // 토스트 메세지 전송
//                Toast.makeText(CalendarActivity.this, "클릭",
//                        Toast.LENGTH_SHORT).show();
            }
        });




        // 일수 표시할 그리드 뷰
        gridView = (GridView)findViewById(R.id.gridview);

        // 오늘 날짜 가져옴
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        // 년,월,일 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        
        // 앱바 설정
        ActionBarTitle = curYearFormat.format(date) + "년 " + curMonthFormat.format(date) + "월";
        ab = getSupportActionBar() ;
        ab.setTitle(ActionBarTitle);


        // gridview 요일 표시
        dayList = new ArrayList<String>();

        // 캘린더 객체 생성
        calendar = Calendar.getInstance();

        // 이번 달 1일 무슨 요일인지 판단 calendar.set(Year,Month,Day)
        calendar.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        // 1:일, 2:월, ....
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }
        setCalendarDate(calendar.get(Calendar.MONTH) + 1);

        gridAdapter = new GridAdapter(getApplicationContext(), dayList, null);
        gridView.setAdapter(gridAdapter);

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
//        switch (item.getItemId()) {
//            case R.id.quick_action1:
//                Toast.makeText(getApplicationContext(), "action_quick", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_settings:
//                Toast.makeText(getApplicationContext(), "action_settings", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_subactivity:
//                startActivity(new Intent(this,SubActivity.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        //Toast.makeText(getApplicationContext(), "메뉴 클릭", Toast.LENGTH_SHORT).show();


        //startActivity(new Intent(this,ListActivity.class));
        //인텐트 선언 및 정의
        Intent intent =new Intent(this,ListActivity.class);
        // 입력한 input값을 intent로 전달한다.
        intent.putExtra("title", ActionBarTitle);
        // 액티비티 이동
        startActivity(intent);

        return true;
    }
    // 메뉴 --------------------------------------------------





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
    // 여기에서 일수 칸에 데이터 셋팅
    private class GridAdapter extends BaseAdapter {

        private final List<String> dayList;
        private final LayoutInflater inflater;
        private final List<String> valList;

        /**
         * 생성자
         *  @param context
         * @param list
         * @param list2
         */
        public GridAdapter(Context context, List<String> list, List<String> list2) {
            this.dayList = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.valList = list2;
        }

        @Override
        public int getCount() {
            return dayList.size();
        }

        @Override
        public String getItem(int position) {
            return dayList.get(position);
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
                holder.tv_count = (TextView)convertView.findViewById(R.id.day_count);
                holder.tv_price = (TextView)convertView.findViewById(R.id.day_price);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));
            
            // 임의로 값 집어 넣음
            if(getItem(position).equals("5")){
                holder.tv_count.setText("2건");
                holder.tv_price.setText("30000원");
            }

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
        TextView tv_count;
        TextView tv_price;
    }
}