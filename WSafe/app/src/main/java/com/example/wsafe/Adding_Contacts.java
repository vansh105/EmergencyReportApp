package com.example.wsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wsafe.data.ContactData;
import com.example.wsafe.data.ContactDbHelper;

public class Adding_Contacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_contacts);
        ContactDbHelper helper=new ContactDbHelper(getApplicationContext(),ContactDbHelper.DB_NAME,null,ContactDbHelper.DB_VERSION);
        SQLiteDatabase db=helper.getWritableDatabase();
        EditText name=findViewById(R.id.editTextTextPersonName);
        EditText phone=findViewById(R.id.editTextPhone);
        Button save=findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values=new ContentValues();
                values.put(ContactData.ContactEntry.COLUMN_CONTACT_NAME,name.getText().toString());
                values.put(ContactData.ContactEntry.COLUMN_CONTACT_NUMBER,phone.getText().toString());
               long check= db.insert(ContactData.ContactEntry.TABLE_NAME,null,values);
                if (check==-1){
                    Toast.makeText(getApplicationContext(), "Data could not be inserted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Number saved successfully", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}