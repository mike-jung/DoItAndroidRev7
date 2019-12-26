package org.techtown.samplelifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        //Toast.makeText(this, "onCreate 호출됨", Snackbar.LENGTH_LONG).show();
        println("onCreate 호출됨");
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Toast.makeText(this, "onStart 호출됨", Snackbar.LENGTH_LONG).show();
        println("onStart 호출됨");
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Toast.makeText(this, "onStop 호출됨", Snackbar.LENGTH_LONG).show();
        println("onStop 호출됨");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Toast.makeText(this, "onDestroy 호출됨", Snackbar.LENGTH_LONG).show();
        println("onDestroy 호출됨");
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Toast.makeText(this, "onPause 호출됨", Snackbar.LENGTH_LONG).show();
        println("onPause 호출됨");

        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Toast.makeText(this, "onResume 호출됨", Snackbar.LENGTH_LONG).show();
        println("onResume 호출됨");

        restoreState();
    }

    public void println(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        Log.d("Main", data);
    }

    protected void restoreState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if ((pref != null) && (pref.contains("name"))) {
            String name = pref.getString("name", "");
            nameInput.setText(name);
        }
    }

    protected void saveState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", nameInput.getText().toString());
        editor.commit();
    }

    protected void clearState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
