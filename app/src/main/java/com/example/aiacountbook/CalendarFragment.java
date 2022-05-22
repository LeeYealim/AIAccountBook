package com.example.aiacountbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.aiacountbook.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private int position;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position selected position in the ListView.
     * @return A new instance of fragment DetailsFragment.
     */
    // 포지션 정보를 넘겨 받음
    public static CalendarFragment newInstance(int position) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);    // 포지션 값 넘겨 받음
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        int year = 2000 + position/12;
        int month = position%12 + 1;
        //Log.d("yelim","생성중인 뷰의 포지션/년/월 : "+position+"/"+year+"/"+month);
//
//        // 그리드뷰에 표시할 그리드뷰 어댑터 생성해야 함
//        // 일단 그리드 뷰 생성하고 나서 GET 리퀘스트 호출 후에 변경사항 공지하는 게 좋을 듯
//
//        calendar = Calendar.getInstance();
//        calendar.set(year, month, 1);
//        // 이번 달 1일 무슨 요일인지 판단 calendar.set(Year,Month,Day)
//        // 1:일, 2:월, ....
//        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
//        //1일 - 요일 매칭 시키기 위해 공백 add
//        ArrayList<GridItem> list = new ArrayList<GridItem>();
//        for (int i = 1; i < dayNum; i++) {
//            list.add(new GridItem(""+position,  ""+year, ""+month));
//        }
//        for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
//            list.add(new GridItem("" + (i + 1),  "", ""));
//        }

        GridAdapter adapter = new GridAdapter(getActivity(), R.layout.calendar_item_day, year, month);

        //어댑터 연결
        GridView gridView = (GridView)view.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);

        return view;
    }
}