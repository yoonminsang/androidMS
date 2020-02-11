package org.techtown.minsang;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Profile_Information_Activity extends AppCompatActivity {

    ArrayList<InformationData> arrayList;
    int pos = Login.loginpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__information_);

        TextView tx_information_name = (TextView) findViewById(R.id.tx_information_name);
        TextView tx_information_birth = (TextView) findViewById(R.id.tx_information_birth);
        TextView tx_information_gender = (TextView) findViewById(R.id.tx_information_gender);
        TextView tx_information_id = (TextView) findViewById(R.id.tx_information_id);
        Button bt_information_change = (Button) findViewById(R.id.bt_information_change);
        Button bt_information_logout = (Button) findViewById(R.id.bt_information_logout);
        Button bt_information_secession = (Button) findViewById(R.id.bt_information_secession);

        loadData();
        tx_information_name.setText(arrayList.get(pos).getName());
        tx_information_birth.setText(arrayList.get(pos).getYear() + "-" + arrayList.get(pos).getMonth() + "-" + arrayList.get(pos).getDay());
        tx_information_gender.setText(arrayList.get(pos).getGender());
        tx_information_id.setText(arrayList.get(pos).getId());

        bt_information_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PasswordChange.class);
                intent.putExtra("password",arrayList.get(pos).getPassword());
                startActivityForResult(intent, 1);
            }
        });

        bt_information_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Profile_Information_Activity.this);
                dialog.setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sh = getSharedPreferences("autoLogin", 0);
                                SharedPreferences.Editor autoLogin = sh.edit();
                                autoLogin.clear();
                                autoLogin.apply();
                                Intent intent = new Intent(Profile_Information_Activity.this, Login.class);
                                startActivity(intent);
                                Toast.makeText(Profile_Information_Activity.this, "로그아웃.", Toast.LENGTH_SHORT).show();
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Profile_Information_Activity.this, "로그아웃 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        });

        bt_information_secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Profile_Information_Activity.this);
                dialog.setTitle("계정삭제")
                        .setMessage("계정을 정말로 삭제하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sh = getSharedPreferences("autoLogin", 0);
                                SharedPreferences.Editor autoLogin = sh.edit();
                                autoLogin.clear();
                                autoLogin.apply();
                                arrayList.remove(pos);
                                saveData();
                                Toast.makeText(Profile_Information_Activity.this, "계정삭제 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Profile_Information_Activity.this, Login.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Profile_Information_Activity.this, "계정을 삭제하지 않았습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String changepassword = data.getStringExtra("changepassword");
            arrayList.get(pos).setPassword(changepassword);
            saveData();
        }
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

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("signup", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("min", json);
        editor.apply();
    }
}
