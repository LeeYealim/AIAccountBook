package com.example.aiacountbook;

//public class ListAdapter {
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<Item> mItems = new ArrayList<Item>();

    public ListAdapter(Context context, int resource, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
        mResource = resource;
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

        if (convertView == null) { // 해당 항목 뷰가 이전에 생성된 적이 없는 경우
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 항목 뷰를 정의한 xml 리소스(여기서는 mResource 값)으로부터 항목 뷰 객체를 메모리로 로드
            convertView = inflater.inflate(mResource, parent,false);
        }


        TextView date = (TextView) convertView.findViewById(R.id.list_date);
        date.setText(mItems.get(position).date);

        TextView title = (TextView) convertView.findViewById(R.id.list_title);
        title.setText(mItems.get(position).place);

        TextView price = (TextView) convertView.findViewById(R.id.list_price);
        price.setText(mItems.get(position).price);

        return convertView;
    }
}