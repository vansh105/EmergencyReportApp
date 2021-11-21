package com.example.wsafe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactDbHelper extends SQLiteOpenHelper {
    public ContactDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public static final String DB_NAME="contacts.db";
    public static final int DB_VERSION=1;
    public static String CREATE_TABLE="CREATE TABLE "+ContactData.ContactEntry.TABLE_NAME+"("+ ContactData.ContactEntry.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ ContactData.ContactEntry.COLUMN_CONTACT_NAME+" TEXT NOT NULL,"+ ContactData.ContactEntry.COLUMN_CONTACT_NUMBER+" TEXT"+");";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
