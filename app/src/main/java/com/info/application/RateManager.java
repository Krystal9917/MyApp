package com.info.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RateManager {
    private DBHelper dbHelper;
    private String TBName;

    public RateManager(Context context) {
        dbHelper = new DBHelper(context);
        TBName = DBHelper.TB_NAME;
    }

    public void add(RateItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("CURNAME", item.getCurName());
        values.put("CURRATE", item.getCurRate());

        db.insert(TBName, null, values);
        db.close();
    }

    public RateItem select(String curName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBName, null, "CURNAME=?", new String[]{curName}, null, null, null);

        RateItem rateItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            rateItem = new RateItem();
            rateItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem.setCurRate(cursor.getFloat(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }
}
