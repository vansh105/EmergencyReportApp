package com.example.wsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wsafe.data.ContactData;
import com.example.wsafe.data.ContactDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public StringBuilder Main_message = new StringBuilder("HELP ME!! IT'S AN EMERGENCY");
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton report = findViewById(R.id.emergency_button);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMSMessage();
            }
        });
    }

    protected void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            ContactDbHelper helper = new ContactDbHelper(getApplicationContext(), ContactDbHelper.DB_NAME, null, ContactDbHelper.DB_VERSION);
            SQLiteDatabase db = helper.getReadableDatabase();
            String[] projection = {ContactData.ContactEntry.COLUMN_CONTACT_NUMBER};
            Cursor cursor = db.query(ContactData.ContactEntry.TABLE_NAME, projection, null, null, null, null, null);
            SmsManager smsManager = SmsManager.getDefault();
            int num_column_index = cursor.getColumnIndex(ContactData.ContactEntry.COLUMN_CONTACT_NUMBER);
            try {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    String phoneNo = "+91" + cursor.getString(num_column_index);
                    smsManager.sendTextMessage(phoneNo, null, Main_message.toString(), null, null);
                }
            } finally {
                cursor.close();
            }
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_new_contact:
                Intent intent = new Intent(getApplicationContext(), Adding_Contacts.class);
                startActivity(intent);
                return true;
            case R.id.action_edit_SOS_message:
                Intent intent1 = new Intent(getApplicationContext(), Edit_Message.class);
                startActivity(intent1);
                String recieved = intent1.getStringExtra("main_message");
                Main_message.delete(0, Main_message.length());
                Main_message.append(recieved);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}