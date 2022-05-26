package com.example.aiacountbook.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aiacountbook.GridItem;
import com.example.aiacountbook.R;
import com.example.aiacountbook.database.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class GridAdapter2 extends BaseAdapter {
    private Context mContext;
    private int mResource;
    public  ArrayList<GridItem> mItems;
    public int year;       // 실제 년
    public int month;      // 실제 월(0부터 시작x)
    public int dayNum;

    private DBHelper mDbHelper;

    public GridAdapter2(Context context, int resource, int year, int month) {
        mContext = context;
        //mItems = items;
        mResource = resource;
        this.year = year;
        this.month = month;

        mDbHelper = new DBHelper(context);

        mItems = new ArrayList<GridItem>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, 1);
        // 이번 달 1일 무슨 요일인지 판단 calendar.set(Year,Month,Day)
        // 1:일, 2:월, ....
        dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        ArrayList<GridItem> list = new ArrayList<GridItem>();
        for (int i = 1; i < dayNum; i++) {
            list.add(new GridItem("",  "", ""));
        }
        for (int i = 1; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1; i++) {

            String s_month = (this.month < 10) ? "0"+this.month : ""+this.month;
            String s_day = (i < 10) ? "0"+i : ""+i;
            String date = year + "-" + s_month + "-" + s_day;

            //Log.d("yelim", "date : "+date);
            Cursor cursor =  mDbHelper.getAllAccountWhereDateBySQL(date);
            if (cursor.moveToNext()) {
                //Log.d("yelim","결과 : " + cursor.getString(0) + " " + cursor.getInt(1) +" " +cursor.getInt(2));
                list.add(new GridItem(""+i,  ""+cursor.getInt(1), ""+cursor.getInt(2)));
            }
            else{
                list.add(new GridItem(""+i,  "", ""));
            }
        }

        mItems = list;
    }

    // MyAdapter 클래스가 관리하는 항목의 총 개수를 반환
    @Override
    public int getCount() {
        return mItems.size();
    }

    // MyAdapter 클래스가 관리하는 항목의 중에서 position 위치의 항목을 반환
    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    // 항목 id를 항목의 위치로 간주함
    @Override
    public long getItemId(int position) {
        return position;
    }

    // position 위치의 항목에 해당되는 항목뷰를 반환하는 것이 목적임
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d("yelim","GridAdapter > getView() position : "+ position);

        if (convertView == null) { // 해당 항목 뷰가 이전에 생성된 적이 없는 경우
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 항목 뷰를 정의한 xml 리소스(여기서는 mResource 값)으로부터 항목 뷰 객체를 메모리로 로드
            convertView = inflater.inflate(mResource, parent,false);
        }

        TextView date = (TextView) convertView.findViewById(R.id.textview_day);
        date.setText(mItems.get(position).day);

        TextView count = (TextView) convertView.findViewById(R.id.textview_count);
        count.setText(mItems.get(position).count);

        TextView total = (TextView) convertView.findViewById(R.id.textview_total);
        total.setText(mItems.get(position).total);

        return convertView;
    }
}