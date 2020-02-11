package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Profile_Point_Activity extends AppCompatActivity {
    private int a;
    private int b;
    ArrayList<CalendarData> arrayList;

    private Spinner spinner_point_year;
    private Spinner spinner_point_month;
    ArrayList<String> arrayList_year;
    ArrayList<String> arrayList_month;
    ArrayAdapter yearAdapter;
    ArrayAdapter monthAdapter;
    private String year;
    private String month;
    private TextView tx_point_date;
    private TextView tx_point_lookup_point;
    private int lookupPoint = 0;
    private int allPoint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__point_);

        TextView tx_point_now = (TextView) findViewById(R.id.tx_point_now);
        TextView tx_point_last = (TextView) findViewById(R.id.tx_point_last);
        TextView tx_point_all = (TextView) findViewById(R.id.tx_point_all);
        tx_point_date = (TextView) findViewById(R.id.tx_point_date);
        tx_point_lookup_point = (TextView) findViewById(R.id.tx_point_lookup_point);

        int nowPoint = 0;
        int lastPoint = 0;

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat nowYear = new SimpleDateFormat("yyyy", Locale.KOREAN);
        SimpleDateFormat nowMonth = new SimpleDateFormat("MM", Locale.KOREAN);
        a = Integer.parseInt(nowYear.format(date));
        b = Integer.parseInt(nowMonth.format(date));

        for (int i = 1; i <= 31; i++) {
            SharedPreferences sharedPreferences = getSharedPreferences("calendar", 0);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(a + "-" + b + "-" + i + Login.loginpos + "", null);
            Type type = new TypeToken<ArrayList<CalendarData>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
            if (arrayList == null) {
                arrayList=new ArrayList<>();
            }
            if(arrayList.size()>0){
                nowPoint++;
            }
        }
        tx_point_now.setText(nowPoint + "");

        if (b == 1) {
            a--;
            b = 12;
        } else {
            b--;
        }

        for (int i = 1; i <= 31; i++) {
            SharedPreferences sharedPreferences = getSharedPreferences("calendar", 0);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(a + "-" + b + "-" + i + Login.loginpos, null);
            Type type = new TypeToken<ArrayList<CalendarData>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            if(arrayList.size()>0){
                lastPoint++;
            }
        }
        tx_point_last.setText(lastPoint + "");


        arrayList_year = new ArrayList<>();
        arrayList_year.add("연도");
        for (int i = 2020; i <= 2050; i++) {
            arrayList_year.add(i + "");
        }

        yearAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList_year);

        spinner_point_year = (Spinner) findViewById(R.id.spinner_point_year);
        spinner_point_year.setAdapter(yearAdapter);
        spinner_point_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) spinner_point_year.getItemAtPosition(position);
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

        spinner_point_month = (Spinner) findViewById(R.id.spinner_point_month);
        spinner_point_month.setAdapter(monthAdapter);
        spinner_point_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = (String) spinner_point_month.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        for (int i = 2020; i <= 2050; i++) {
            for (int j = 1; j <= 12; j++) {
                for (int k = 1; k <= 31; k++) {
                    SharedPreferences sharedPreferences = getSharedPreferences("calendar", 0);
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString(i + "-" + j + "-" + k + Login.loginpos, null);
                    Type type = new TypeToken<ArrayList<CalendarData>>() {
                    }.getType();
                    arrayList = gson.fromJson(json, type);
                    if (arrayList == null) {
                        arrayList=new ArrayList<>();
                    }
                    if(arrayList.size()>0){
                        allPoint++;
                    }
                }
            }
        }
        tx_point_all.setText(allPoint + "");


        Button bt_point_lookup = (Button) findViewById(R.id.bt_point_lookup);
        bt_point_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year.equals(arrayList_year.get(0)) || month.equals(arrayList_month.get(0))) {
                    Toast.makeText(getApplicationContext(),
                            "입력되지 않은 정보가 있습니다.", Toast.LENGTH_LONG).show();
                } else {
                    tx_point_date.setText(year + "-" + month + " 운동 횟수");
                    lookupPoint = 0;
                    for (int i = 1; i <= 31; i++) {
                        SharedPreferences sharedPreferences = getSharedPreferences("calendar", 0);
                        Gson gson = new Gson();
                        String json = sharedPreferences.getString(year + "-" + month + "-" + i + Login.loginpos, null);
                        Type type = new TypeToken<ArrayList<CalendarData>>() {
                        }.getType();
                        arrayList = gson.fromJson(json, type);
                        if (arrayList != null) {
                            lookupPoint++;
                        }
                    }
                    tx_point_lookup_point.setText(lookupPoint + "");
                }
            }
        });
    }
}
