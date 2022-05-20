package com.example.aiacountbook;

// 전역변수를 사용하기 위한 클래스

import android.app.Application;

public class AiApplication extends Application {
    private String year;
    private String month;

    public String getHyphenYearMonth() {
        return year+"-"+month;
    }

    public String getStrYearMonth() {
        return year+"년 "+month+"월";
    }

    public void setYearMonth(String year, String month) {
        this.year = year;
        this.month = month;
    }

    public String getYear(){
        return year;
    }

    public void setYear(String year){
        this.year = year;
    }

    public String getMonth(){
        return month;
    }

    public void setMonth(String month){
        this.month = month;
    }

}
