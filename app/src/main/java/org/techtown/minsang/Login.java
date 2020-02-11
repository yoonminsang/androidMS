package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    ArrayList<InformationData> arrayList;
    private BackPressedForFinish backPressedForFinish;
    public static int loginpos;
    private SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sh = getSharedPreferences("autoLogin", 0);
        String autoId = sh.getString("autoId", null);
        String autoPassword = sh.getString("autoPassword", null);
        int autoPosition = sh.getInt("autoPosition", 100);

        if (autoId != null && autoPassword != null) {
            Toast.makeText(getApplicationContext(),
                    "자동 로그인 되었습니다.", Toast.LENGTH_LONG).show();
            loginpos = autoPosition;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        backPressedForFinish = new BackPressedForFinish(this);

        loadData();

        Button bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roop:
                while (true) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        EditText ed_inputId = (EditText) findViewById(R.id.ed_inputId);
                        EditText ed_inputPwd = (EditText) findViewById(R.id.ed_inputPwd);
                        if (arrayList.get(i).getId().equals(ed_inputId.getText().toString())
                                && arrayList.get(i).getPassword().equals(ed_inputPwd.getText().toString())) {
                            loginpos = i;
                            CheckBox ck_auto_login = (CheckBox) findViewById(R.id.ck_auto_login);
                            sh = getSharedPreferences("autoLogin", 0);
                            SharedPreferences.Editor autoLogin = sh.edit();
                            if (ck_auto_login.isChecked()) {
                                autoLogin.putString("autoId", arrayList.get(i).getId());
                                autoLogin.putString("autoPassword", arrayList.get(i).getPassword());
                                autoLogin.putInt("autoPosition", i);
                                autoLogin.apply();
                                Toast.makeText(getApplicationContext(),
                                        "자동 로그인 되었습니다.", Toast.LENGTH_LONG).show();
                            } else {
                                autoLogin.clear();
                                autoLogin.apply();
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            break roop;
                        }
                    }
                    Toast.makeText(getApplicationContext(),
                            "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_LONG).show();
                    break roop;
                }
            }
        });

        Button bt_signup = (Button) findViewById(R.id.bt_signup);
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        Button bt_find = (Button) findViewById(R.id.bt_find);
        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Find.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("signup", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("min", null);
        Type type = new TypeToken<ArrayList<InformationData>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
    }


    @Override
    public void onBackPressed() {
        backPressedForFinish.onBackPressed();
    }
}
