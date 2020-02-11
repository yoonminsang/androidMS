package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExerciseRoutine_5x5_Activity extends AppCompatActivity {

    Button bt_ex_5x5_1;
    Button bt_ex_5x5_2;
    Button bt_ex_5x5_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_routine_5x5_);

        bt_ex_5x5_1 = (Button) findViewById(R.id.bt_ex_5x5_1);
        bt_ex_5x5_2 = (Button) findViewById(R.id.bt_ex_5x5_2);
        bt_ex_5x5_3 = (Button) findViewById(R.id.bt_ex_5x5_3);

        bt_ex_5x5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseRoutine_5x5_1_Activity.class);
                startActivity(intent);
            }
        });

        bt_ex_5x5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseRoutine_5x5_2_Activity.class);
                startActivity(intent);
            }
        });

        bt_ex_5x5_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseRoutine_5x5_3_Activity.class);
                startActivity(intent);
            }
        });
    }
}
