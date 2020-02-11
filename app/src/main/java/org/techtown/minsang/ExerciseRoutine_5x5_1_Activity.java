package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExerciseRoutine_5x5_1_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_routine_5x5_1_);

        Button bt_5x5_1_go = (Button) findViewById(R.id.bt_5x5_1_go);
        bt_5x5_1_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat nowYear = new SimpleDateFormat("yyyy", Locale.KOREAN);
                SimpleDateFormat nowMonth = new SimpleDateFormat("MM", Locale.KOREAN);
                SimpleDateFormat nowDay = new SimpleDateFormat("dd", Locale.KOREAN);
                String nowDate = Integer.parseInt(nowYear.format(date)) + "-" + Integer.parseInt(nowMonth.format(date)) + "-" + Integer.parseInt(nowDay.format(date));

                Intent intent = new Intent(getApplicationContext(), Calendar_Edit_Box.class);
                intent.putExtra("date", nowDate);
                startActivity(intent);
            }
        });
    }
}
