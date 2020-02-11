package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    private Spinner spinner_year;
    private Spinner spinner_month;
    private Spinner spinner_day;
    private Spinner spinner_gender;
    private Spinner spinner_question;
    ArrayList<String> arrayList_year;
    ArrayList<String> arrayList_month;
    ArrayList<String> arrayList_day;
    ArrayList<String> arrayList_gender;
    ArrayList<String> arrayList_question;
    ArrayAdapter yearAdapter;
    ArrayAdapter monthAdapter;
    ArrayAdapter dayAdapter;
    ArrayAdapter genderAdapter;
    ArrayAdapter questionAdapter;
    private String year;
    private String month;
    private String day;
    private String gender;
    private String question;
    ArrayList<InformationData> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loadData();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat nowYear = new SimpleDateFormat("yyyy", Locale.KOREAN);
        int nowyear = Integer.parseInt(nowYear.format(date));
        arrayList_year = new ArrayList<>();
        arrayList_year.add("연도");
        for (int i = nowyear - 100; i <= nowyear; i++) {
            arrayList_year.add(i + "");
        }

        yearAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList_year);

        spinner_year = (Spinner) findViewById(R.id.spinner_year);
        spinner_year.setAdapter(yearAdapter);
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) spinner_year.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        arrayList_month = new ArrayList<>();
        arrayList_month.add("월");
        for (int i = 1; i <= 12; i++) {
            arrayList_month.add(i + "");
        }
        monthAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList_month);

        spinner_month = (Spinner) findViewById(R.id.spinner_month);
        spinner_month.setAdapter(monthAdapter);
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = (String) spinner_month.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        arrayList_day = new ArrayList<>();
        arrayList_day.add("일");
        for (int i = 1; i <= 31; i++) {
            arrayList_day.add(i + "");
        }
        dayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList_day);

        spinner_day = (Spinner) findViewById(R.id.spinner_day);
        spinner_day.setAdapter(dayAdapter);
        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = (String) spinner_day.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        arrayList_gender = new ArrayList<>();
        arrayList_gender.add("성별을 선택하세요.");
        arrayList_gender.add("남자");
        arrayList_gender.add("여자");

        genderAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList_gender);

        spinner_gender = (Spinner) findViewById(R.id.spinner_gender);
        spinner_gender.setAdapter(genderAdapter);
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = (String) spinner_gender.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        arrayList_question = new ArrayList<>();
        arrayList_question.add("질문을 선택하세요.");
        arrayList_question.add("내가 나온 초등학교는?");
        arrayList_question.add("내가 나온 중학교는?");
        arrayList_question.add("내가 나온 고등학교는?");
        arrayList_question.add("우리 아빠 이름은?");
        arrayList_question.add("우리 엄마 이름은?");

        questionAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList_question);

        spinner_question = (Spinner) findViewById(R.id.spinner_question);
        spinner_question.setAdapter(questionAdapter);
        spinner_question.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question = (String) spinner_question.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button bt_signup_confirm = (Button) findViewById(R.id.bt_signup_confirm);
        bt_signup_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed_signup_name = (EditText) findViewById(R.id.ed_signup_name);
                String name = ed_signup_name.getText().toString();
                EditText ed_signup_id = (EditText) findViewById(R.id.ed_signup_id);
                String id = ed_signup_id.getText().toString();
                EditText ed_signup_password = (EditText) findViewById(R.id.ed_signup_password);
                String password = ed_signup_password.getText().toString();
                EditText ed_signup_password_confirm = (EditText) findViewById(R.id.ed_signup_password_confirm);
                String password_confirm = ed_signup_password_confirm.getText().toString();
                EditText ed_signup_answer = (EditText) findViewById(R.id.ed_signup_answer);
                String answer = ed_signup_answer.getText().toString();

                roop:
                while (true) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getId().equals(name)) {
                            Toast.makeText(getApplicationContext(),
                                    "아이디가 이미 존재합니다.", Toast.LENGTH_LONG).show();
                            break roop;
                        }
                    }
                    if (!password.equals(password_confirm)) {
                        Toast.makeText(getApplicationContext(),
                                "비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                        break roop;
                    } else if (year.equals(arrayList_year.get(0)) || gender.equals(arrayList_gender.get(0))
                            || question.equals(arrayList_question.get(0)) || name.equals("") || id.equals("")
                            || password.equals("") || answer.equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "입력하지 않은 정보가 있습니다.", Toast.LENGTH_LONG).show();
                        break roop;
                    } else {
                        InformationData informationData = new InformationData(name, year, month, day, gender, id, password, question, answer);
                        arrayList.add(informationData);
                        saveData();
                        Toast.makeText(getApplicationContext(),
                                "회원가입 완료.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                        break roop;
                    }
                }
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("signup", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("min", json);
        editor.apply();
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
}
