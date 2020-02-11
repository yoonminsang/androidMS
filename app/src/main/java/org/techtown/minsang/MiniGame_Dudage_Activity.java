package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MiniGame_Dudage_Activity extends AppCompatActivity {
    Button bt_game_dudage_start;
    Button bt_game_dudage_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game__dudage);

        bt_game_dudage_start = (Button) findViewById(R.id.bt_game_dudage_start);
        bt_game_dudage_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MiniGame_Dudage_Start_Activity.class);
                startActivity(intent);
            }
        });

        bt_game_dudage_record = (Button) findViewById(R.id.bt_game_dudage_record);
        bt_game_dudage_record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MiniGame_Dudage_Record_Activity.class);
                startActivity(intent);
            }
        });
    }
}
