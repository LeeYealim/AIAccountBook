package com.example.aiacountbook;

// 그리드뷰(캘린더)에 사용되는 객체 Item 클래스

public class CalendarItem {
    public String date;
    public String count;
    public String total;

    public CalendarItem( String aDate, String aCount, String aTotal) {
        date = aDate;
        count = aCount;
        total = aTotal;
    }
}
