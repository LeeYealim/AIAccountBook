package com.example.aiacountbook;

// 캘린더에서 day 한 칸에 필요한 데이터 정의

public class dayItem {
    int day;        // day(일)
    String nName;   // text
    String nAge;    // text

    dayItem(int aIcon, String aName, String aAge) {
        day = aIcon;
        nName = aName;
        nAge = aAge;
    }
}
