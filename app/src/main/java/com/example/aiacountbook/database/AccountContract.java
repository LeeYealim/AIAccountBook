package com.example.aiacountbook.database;


import android.provider.BaseColumns;

public final class AccountContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private AccountContract() {}

    /* Inner class that defines the table contents */
    public static class Accounts implements BaseColumns {
        public static final String TABLE_NAME="Account";
        public static final String KEY_DATE = "date";
        public static final String KEY_PLACE = "place";
        public static final String KEY_PRICE = "price";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_DATE + TEXT_TYPE + COMMA_SEP +
                KEY_PLACE + TEXT_TYPE + COMMA_SEP +
                KEY_PRICE + INTEGER_TYPE +  " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
