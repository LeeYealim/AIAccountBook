package com.example.aiacountbook;

// 캘린더에서 day 한 칸에 필요한 데이터 정의

public class Item {
    int idx;
    String date;   // text
    String title;    // text
    String price;

    Item(int aIdx, String aDate, String aTitle, String aPrice) {
        idx = aIdx;
        date = aDate;
        title = aTitle;
        price = aPrice;
    }

//    int mIcon; // image resource
//    String nName; // text
//    String nAge;  // text
//
//    DayItem(int aIcon, String aName, String aAge) {
//        mIcon = aIcon;
//        nName = aName;
//        nAge = aAge;
//    }
}