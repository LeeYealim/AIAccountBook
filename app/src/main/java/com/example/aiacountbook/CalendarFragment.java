package com.example.aiacountbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.aiacountbook.adapter.GridAdapter;
import com.example.aiacountbook.api.GetRequest;
import com.example.aiacountbook.api.GridGetRequest;
import com.example.aiacountbook.application.AiApplication;

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

        int month = position%12 + 1;
        int year = (2000+position/12);
        String str_month = (month<10) ? "0"+month : ""+month;
        
        // 그리드뷰 어댑터 생성
        GridAdapter adapter = new GridAdapter(getActivity(), R.layout.calendar_item_day, year, month);
        //어댑터 연결 -- 로딩이 길어서 먼저 연결함... 일수라도 보이게...
        GridView gridView = (GridView)view.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);

        String uri = "https://ba0c-110-14-126-182.ngrok.io/calendars/"+year+"-"+str_month;
        Log.d("yelim","uri    :     "+uri);
        new GridGetRequest(getActivity(), uri, "calendar", adapter, year, month).execute();
        
        

        return view;
    }
}