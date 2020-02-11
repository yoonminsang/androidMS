package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordChange extends AppCompatActivity {

    private String password;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private EditText ed_password_now;
    private EditText ed_password_change;
    private EditText ed_password_check;
    private Button bt_password_check;
    private Button bt_password_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        password = getIntent().getStringExtra("password");

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        ed_password_now = (EditText) findViewById(R.id.ed_password_now);
        ed_password_change = (EditText) findViewById(R.id.ed_password_change);
        ed_password_check = (EditText) findViewById(R.id.ed_password_check);
        bt_password_check = (Button) findViewById(R.id.bt_password_check);
        bt_password_change = (Button) findViewById(R.id.bt_password_change);

        textView2.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        ed_password_change.setVisibility(View.INVISIBLE);
        ed_password_check.setVisibility(View.INVISIBLE);
        bt_password_change.setVisibility(View.INVISIBLE);

        bt_password_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.equals(ed_password_now.getText().toString())) {
                    Toast.makeText(PasswordChange.this, "비밀번호가 확인되었습니다. 새로운 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    textView2.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.VISIBLE);
                    ed_password_change.setVisibility(View.VISIBLE);
                    ed_password_check.setVisibility(View.VISIBLE);
                    bt_password_change.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(PasswordChange.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_password_change.getText().toString().equals(ed_password_check.getText().toString())) {
                    String changepassword = ed_password_change.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("changepassword", changepassword);
                    setResult(RESULT_OK, intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "변경되었습니다. ", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
