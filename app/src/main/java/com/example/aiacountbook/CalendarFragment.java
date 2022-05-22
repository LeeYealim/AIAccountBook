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


        // 그리드뷰 어댑터 자체를 주석처리하고 GET API 호출 후에 할까...
        GridAdapter adapter = new GridAdapter(getActivity(), R.layout.calendar_item_day, year, month);

        // 여기서 API 호출할까... adapter 내의 mItems 배열 업데이트 하면 될 것 같은데..


        //어댑터 연결
        GridView gridView = (GridView)view.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);

        return view;
    }
}