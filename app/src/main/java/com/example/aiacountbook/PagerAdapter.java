package com.example.aiacountbook;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;




// FragmentStateAdapter 는 두 개의 메소드 재정의 필요

public class PagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=3;

    public PagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {

//        switch (position) {
//            case 0:
//                FirstFragment first = new FirstFragment();
//                return first;
//            case 1:
//                SecondFragment second = new SecondFragment();
//                return second;
//            case 2:
//                ThirdFragment third = new ThirdFragment();
//                return third;
//            default:
//                return null;
//        }
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}