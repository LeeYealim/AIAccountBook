package com.example.aiacountbook;

// 리스트뷰에 사용되는 객체 Item 클래스

public class ListItem {
    public int idx;
    public String date;   // text
    public String place;    // text
    public String price;

    public ListItem(int aIdx, String aDate, String aPlace, String aPrice) {
        idx = aIdx;
        date = aDate;
        place = aPlace;
        price = aPrice;
    }
}
