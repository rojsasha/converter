package com.example.alex.laboratoryone.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "DB_NAME";
    private final static int DB_VERSION = 2;
    private final static String TABLE_LENGHT = "TABLE_LENGHT";
    private final static String TABLE_MASS = "TABLE_MASS";
    private final static String ID = "_id";
    private final static String ED_ONE = "ED_ONE";
    private final static String SPINNER_ONE = "SPINNER_ONE";
    private final static String ED_TWO = "ED_TWO";
    private final static String SPINNER_TWO = "SPINNER_TWO";

    private String TABLE_NAME = "";

    private final static String CREATE_TABLE_MASS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_MASS + "(" +
            ID + " INTEGER_PRIMARY_KEY, " +
            ED_ONE + " TEXT, " +
            SPINNER_ONE + " INTEGER, " +
            ED_TWO + " TEXT, " +
            SPINNER_TWO + " INTEGER " +
            ");";
    private final static String CREATE_TABLE_LENGHT = "CREATE TABLE IF NOT EXISTS " +
            TABLE_LENGHT + "(" +
            ID + " INTEGER_PRIMARY_KEY, " +
            ED_ONE + " TEXT, " +
            SPINNER_ONE + " INTEGER, " +
            ED_TWO + " TEXT, " +
            SPINNER_TWO + " INTEGER " +
            ");";

    public SQLiteHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MASS);
        sqLiteDatabase.execSQL(CREATE_TABLE_LENGHT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LENGHT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MASS);
        onCreate(sqLiteDatabase);
    }

    public void saveData(SpinerModel model, int activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (activity == 1) {
            TABLE_NAME = TABLE_LENGHT;
        } else {
            TABLE_NAME = TABLE_MASS;
        }
        long dbDelete = db.delete(TABLE_NAME, null, null);
        Log.d("ROW IS deleted", "ROW deleted" + dbDelete + TABLE_NAME);

        cv.put(ED_ONE, model.getEdOne());
        cv.put(SPINNER_ONE, model.getSpinerOne());
        cv.put(ED_TWO, model.getEdTwo());
        cv.put(SPINNER_TWO, model.getSpinerTwo());

        long row = db.insert(TABLE_NAME, null, cv);
        Log.d("ROW IS", "ROW add" + row);
        db.close();
    }

    public SpinerModel getData(int activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (activity == 1) {
            TABLE_NAME = TABLE_LENGHT;
        } else {
            TABLE_NAME = TABLE_MASS;
        }
        SpinerModel model = new SpinerModel();
        Cursor cursor = db.query(TABLE_NAME, null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int indexEdOne = cursor.getColumnIndex(ED_ONE);
            int indexSpinerOne = cursor.getColumnIndex(SPINNER_ONE);
            int indexEdTwo = cursor.getColumnIndex(ED_TWO);
            int indexSpinerTwo = cursor.getColumnIndex(SPINNER_TWO);
            do {
                model.setEdOne(cursor.getString(indexEdOne));
                model.setSpinerOne(Integer.parseInt(cursor.getString(indexSpinerOne)));
                model.setEdTwo(cursor.getString(indexEdTwo));
                model.setSpinerTwo(Integer.parseInt(cursor.getString(indexSpinerTwo)));
            } while (cursor.moveToNext());
            Log.d("ROW IS", "getiing");
        } else {
            Log.d("ROW IS", "not getting");
        }
        cursor.close();
        db.close();
        return model;
    }
}
