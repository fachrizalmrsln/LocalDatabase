package com.example.zul.localdatabase.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.zul.localdatabase.Model.DataModel;

public class DataHelper {

    private static final String TABLE_1 = DatabaseContract.TABLE_NAME;

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DataHelper(Context context) {
        this.context = context;
    }

    public DataHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public long insertData(DataModel model) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.COLUMN_ID, model.getId());
        values.put(DatabaseContract.COLUMN_WORD, model.getEnglishWord());
        values.put(DatabaseContract.COLUMN_TRANSLATE, model.getEnglishTranslate());

        return sqLiteDatabase.insert(TABLE_1, null, values);
    }

    public void updateData(DataModel model) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.COLUMN_WORD, model.getEnglishWord());
        values.put(DatabaseContract.COLUMN_TRANSLATE, model.getEnglishTranslate());

        sqLiteDatabase.update(TABLE_1, values,
                DatabaseContract._ID + " = '" + model.getId() + "'", null);
    }

    public Cursor getAllData() {
        return sqLiteDatabase.rawQuery("SELECT * FROM " +
                        TABLE_1 + " ORDER BY " +
                        DatabaseContract._ID + " ASC",
                null
        );
    }

    public Cursor getData(String query) {
        return sqLiteDatabase.rawQuery("SELECT * FROM " +
                        TABLE_1 + " WHERE " +
                        DatabaseContract.COLUMN_WORD + " LIKE '%" +
                        query.trim() + "%'",
                null);
    }

    public void deleteAllData() {
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_1);
    }

    public void deleteData(int id) {
        sqLiteDatabase.delete(TABLE_1,
                DatabaseContract._ID + " = '" + id + "'", null);
    }

}
