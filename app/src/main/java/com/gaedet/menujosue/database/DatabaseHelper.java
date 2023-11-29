package com.gaedet.menujosue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_FOOD = "food";
    public static final String TABLE_EXTRAS = "extras";
    public static final String TABLE_BEVERAGES = "beverages";
    public static final String TABLE_SHOPPING_CART = "shopping_cart";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_ICON = "product_icon";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOTAL = "total";
    public  static  final String IS_FAVORITE = "isFavorite";

    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_ICON + " INTEGER, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            IS_FAVORITE + " INTEGER DEFAULT 0" +
            ");";

    private static final String CREATE_TABLE_EXTRAS = "CREATE TABLE " + TABLE_EXTRAS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_ICON + " INTEGER, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            IS_FAVORITE + " INTEGER DEFAULT 0" +
            ");";

    private static final String CREATE_TABLE_BEVERAGES = "CREATE TABLE " + TABLE_BEVERAGES + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_ICON + " INTEGER, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            IS_FAVORITE + " INTEGER DEFAULT 0" +
            ");";


    private static final String CREATE_TABLE_SHOPPING_CART = "CREATE TABLE " + TABLE_SHOPPING_CART + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PRODUCT_ID + " INTEGER, " +
            COLUMN_PRODUCT_NAME + " TEXT, " +
            COLUMN_PRODUCT_ICON + " INTEGER, " +
            COLUMN_PRODUCT_PRICE + " REAL, " +
            COLUMN_QUANTITY + " REAL, " +
            COLUMN_TOTAL + " REAL" +
            ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_EXTRAS);
        db.execSQL(CREATE_TABLE_BEVERAGES);
        db.execSQL(CREATE_TABLE_SHOPPING_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades here if needed
    }
}