package org.techtown.sampledialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage();
            }
        });
    }

    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("안내");
        builder.setMessage("종료하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                String message = "예 버튼이 눌렸습니다. ";
                textView.setText(message);
            }
        });
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                String message = "취소 버튼이 눌렸습니다. ";
                textView.setText(message);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                String message = "아니오 버튼이 눌렸습니다. ";
                textView.setText(message);
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
    }

}
