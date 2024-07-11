package com.example.courseconnectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AvailableCoursesDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AvailableCourses.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "courses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COURSE_NAME = "course_name";
    public static final String COLUMN_COURSE_COST = "course_cost";
    public static final String COLUMN_BRANCH = "branch";
    public static final String COLUMN_STARTING_DATE = "starting_date";

    public AvailableCoursesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COURSE_NAME + " TEXT, " +
                COLUMN_COURSE_COST + " INTEGER, " +
                COLUMN_BRANCH + " TEXT, " +
                COLUMN_STARTING_DATE + " TEXT)";
        db.execSQL(CREATE_TABLE);

        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        insertCourse(db, "Programming", 30000, "Colombo", "2024/07/10");
        insertCourse(db, "Hardware", 50000, "Galle", "2024/08/20");
        insertCourse(db, "Software", 45000, "Colombo", "2024/07/20");
        insertCourse(db, "Computer Science", 40000, "Jaffna", "2024/09/15");
    }

    private void insertCourse(SQLiteDatabase db, String courseName, int courseCost, String branch, String startingDate) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, courseName);
        values.put(COLUMN_COURSE_COST, courseCost);
        values.put(COLUMN_BRANCH, branch);
        values.put(COLUMN_STARTING_DATE, startingDate);
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }


    public Cursor getCourseById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }


}
