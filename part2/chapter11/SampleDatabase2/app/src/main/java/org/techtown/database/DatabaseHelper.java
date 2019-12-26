package org.techtown.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String NAME = "employee.db";
    public static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        println("onCreate 호출됨");

        String sql = "create table if not exists emp("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " mobile text)";

        db.execSQL(sql);
    }

    public void onOpen(SQLiteDatabase db) {
        println("onOpen 호출됨");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        println("onUpgrade 호출됨 : " + oldVersion + " -> " + newVersion);

        if (newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS emp");
        }
    }

    public void println(String data) {
        Log.d("DatabaseHelper", data);
    }
}
