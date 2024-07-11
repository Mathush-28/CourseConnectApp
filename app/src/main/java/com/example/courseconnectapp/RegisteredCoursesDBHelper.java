package com.example.courseconnectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegisteredCoursesDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RegisteredCourses.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "registered_courses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COURSE_NAME = "course_name";
    public static final String COLUMN_COURSE_COST = "course_cost";
    public static final String COLUMN_BRANCH = "branch";
    public static final String COLUMN_STARTING_DATE = "starting_date";

    public RegisteredCoursesDBHelper(Context context) {
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long registerCourse(String courseName, int courseCost, String branch, String startingDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, courseName);
        values.put(COLUMN_COURSE_COST, courseCost);
        values.put(COLUMN_BRANCH, branch);
        values.put(COLUMN_STARTING_DATE, startingDate);
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllRegisteredCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getRegisteredCourseById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public int deleteCourseById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
