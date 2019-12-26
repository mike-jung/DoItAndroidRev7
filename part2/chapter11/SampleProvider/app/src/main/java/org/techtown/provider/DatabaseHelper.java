package org.techtown.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "person";
    public static final String PERSON_ID = "_id";
    public static final String PERSON_NAME = "name";
    public static final String PERSON_AGE = "age";
    public static final String PERSON_MOBILE = "mobile";

    public static final String[] ALL_COLUMNS = {PERSON_ID, PERSON_NAME,PERSON_AGE,PERSON_MOBILE};

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PERSON_NAME + " TEXT, " +
                    PERSON_AGE + " INTEGER, " +
                    PERSON_MOBILE + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

}
