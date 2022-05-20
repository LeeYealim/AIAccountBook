package com.example.aiacountbook;

// 캘린더에서 day 한 칸에 필요한 데이터 정의

public class Item {
    public int idx;
    public String date;   // text
    public String place;    // text
    public String price;

    public Item(int aIdx, String aDate, String aPlace, String aPrice) {
        idx = aIdx;
        date = aDate;
        place = aPlace;
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
