package com.example.aiacountbook.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aiacountbook.CalendarFragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

// FragmentStateAdapter를 상속받도록 해야 함
public class PagerAdapter extends FragmentStateAdapter {

    // 생성자도 그냥 하면 안되고
    // 오른쪽 마우스 > Generate > Constructure > 프래그먼트 액티비티를 파라미터로 받는 생성자 선택

    // 그냥 강의자료 복붙해서 대체

    // 뷰 개수 최댓값으로 설정
    private static int NUM_ITEMS = 12000;     //4000*12;      // 뭔가 페이지 많아서 렉걸리는 것 같은데 1900~3000년까지로 설정함

    public PagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {
        Log.d("yelim","페이저어댑터 createFragment() : " + position);

        // 프래그먼트 생성 시, position 정보 넘김
        // position으로 년, 월 계산할 수 있음
        CalendarFragment cf = new CalendarFragment().newInstance(position);
        return cf;
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
