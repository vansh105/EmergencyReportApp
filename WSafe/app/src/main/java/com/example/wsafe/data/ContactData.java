package com.example.wsafe.data;

import android.provider.BaseColumns;

public  final class ContactData {
    private ContactData(){}
    public static abstract class ContactEntry implements BaseColumns{
        public static final String TABLE_NAME="contacts";
        public static final String ID="_id";
        public static final String COLUMN_CONTACT_NAME="name";
        public static final String COLUMN_CONTACT_NUMBER="number";

    }
}
