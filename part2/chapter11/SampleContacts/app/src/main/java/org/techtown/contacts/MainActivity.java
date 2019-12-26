package org.techtown.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseContacts();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }

    public void chooseContacts() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                try {
                    Uri contactsUri = data.getData();
                    String id = contactsUri.getLastPathSegment();

                    getContacts(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getContacts(String id) {
        Cursor cursor = null;
        String name = "";

        try {
            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=?",
                    new String[] { id },
                    null);

            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                println("Name : " + name);

                // 모든 칼럼 이름 확인
                String columns[] = cursor.getColumnNames();
                for (String column : columns) {
                    int index = cursor.getColumnIndex(column);
                    String columnOutput = ("#" + index + " -> [" + column + "] " + cursor.getString(index));
                    println(columnOutput);
                }

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void println(String data) {
        textView.append(data + "\n");
    }
}
