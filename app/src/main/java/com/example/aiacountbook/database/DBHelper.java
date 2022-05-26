package com.example.aiacountbook.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, AccountContract.DB_NAME, null, AccountContract.DATABASE_VERSION);
    }

    // ** onCreate(), onUpgrade()는 우리가 호출하지x
    // 안드로이드 프레임워크에서 호출함

    // onCreate()는 DB가 처음 만들어질 때 수행됨
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(AccountContract.Accounts.CREATE_TABLE);
    }
    
    // 기존 테이블을 지우고 다시 생성하도록 함
    // 데이터베이스의 버전이 변경되었을 때 호출됨 : UserContract.DATABASE_VERSION 1->2
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(AccountContract.Accounts.DELETE_TABLE);
        onCreate(db);
    }

    public void insertAccountBySQL(String date, String place, String price) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s) VALUES (NULL, '%s', '%s', %s)",
                    AccountContract.Accounts.TABLE_NAME,
                    AccountContract.Accounts._ID,
                    AccountContract.Accounts.KEY_DATE,
                    AccountContract.Accounts.KEY_PLACE,
                    AccountContract.Accounts.KEY_PRICE,
                    date,
                    place,
                    price);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    public Cursor getAllAccountBySQL() {
        String sql = "Select * FROM " + AccountContract.Accounts.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }

    public Cursor getAllAccountBySQL2(String yearmonth) {
        //String sql = "Select "+AccountContract.Accounts._ID+"," + AccountContract.Accounts.KEY_DATE+"," + AccountContract.Accounts.KEY_PLACE + "," + AccountContract.Accounts.KEY_PRICE + " FROM " + AccountContract.Accounts.TABLE_NAME + " Where " + AccountContract.Accounts.KEY_PRICE + " = 12000 ";


        String sql = "Select "+AccountContract.Accounts._ID+"," + AccountContract.Accounts.KEY_DATE+"," + "Count(date)" + "," + " Sum(price) "
                + " FROM " + AccountContract.Accounts.TABLE_NAME
                + " Where " + AccountContract.Accounts.KEY_DATE + " = '"+yearmonth+"' "
                + " Group By " + AccountContract.Accounts.KEY_DATE;

        return getReadableDatabase().rawQuery(sql,null);
    }

        public void deleteUserBySQL(String _id) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = %s",
                    AccountContract.Accounts.TABLE_NAME,
                    AccountContract.Accounts._ID,
                    _id);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
        }
    }

}
