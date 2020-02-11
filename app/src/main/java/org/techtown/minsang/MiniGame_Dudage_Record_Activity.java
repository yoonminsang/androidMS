package org.techtown.minsang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MiniGame_Dudage_Record_Activity extends AppCompatActivity {
    TextView tx_game_now;
    TextView tx_game_dudage1;
    TextView tx_game_dudage2;
    TextView tx_game_dudage3;
    TextView tx_game_dudage4;
    TextView tx_game_dudage5;
    Button bt_game_restart;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game__dudage__record_);
        SharedPreferences spf = getSharedPreferences("spfScore", MODE_PRIVATE);

        tx_game_now = (TextView) findViewById(R.id.tx_game_dudage_now);
        tx_game_dudage1 = (TextView) findViewById(R.id.tx_game_dudage_1);
        tx_game_dudage2 = (TextView) findViewById(R.id.tx_game_dudage_2);
        tx_game_dudage3 = (TextView) findViewById(R.id.tx_game_dudage_3);
        tx_game_dudage4 = (TextView) findViewById(R.id.tx_game_dudage_4);
        tx_game_dudage5 = (TextView) findViewById(R.id.tx_game_dudage_5);

        int record1 = spf.getInt("record1", 0);
        tx_game_dudage1.setText(record1+"마리");
        int record2 = spf.getInt("record2", 0);
        tx_game_dudage2.setText(record2+"마리");
        int record3 = spf.getInt("record3", 0);
        tx_game_dudage3.setText(record3+"마리");
        int record4 = spf.getInt("record4", 0);
        tx_game_dudage4.setText(record4+"마리");
        int record5 = spf.getInt("record5", 0);
        tx_game_dudage5.setText(record5+"마리");
        Log.e("리코","더"+record1+record2+record3+record4+record5);

        score = getIntent().getIntExtra("score", -1);
        tx_game_now.setText(score+"마리");

        SharedPreferences.Editor editor = spf.edit();
        if (record1 < score) { //내점수가 저번 점수보다 크면
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("신기록 달성!!").setMessage("축하합니다.");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            editor.putInt("record2", record1).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage2.setText(record1+"마리");
            editor.putInt("record3", record2).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage3.setText(record2+"마리");
            editor.putInt("record4", record3).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage4.setText(record3+"마리");
            editor.putInt("record5", record4).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage5.setText(record4+"마리");
            editor.putInt("record1", score).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage1.setText(spf.getInt("record1", 0)+"마리");
        } else if(record2<score){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("2등!!").setMessage("축하합니다.");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            editor.putInt("record3", record2).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage3.setText(record2+"마리");
            editor.putInt("record4", record3).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage4.setText(record3+"마리");
            editor.putInt("record5", record4).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage5.setText(record4+"마리");
            editor.putInt("record2", score).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage2.setText(spf.getInt("record2", 0)+"마리");
        }else if(record3<score){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("3등!!").setMessage("축하합니다.");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            editor.putInt("record4", record3).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage4.setText(record3+"마리");
            editor.putInt("record5", record4).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage5.setText(record4+"마리");
            editor.putInt("record3", score).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage3.setText(spf.getInt("record3", 0)+"마리");
        }else if(record4<score){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("4등!!").setMessage("축하합니다.");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            editor.putInt("record5", record4).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage5.setText(record4+"마리");
            editor.putInt("record4", score).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage4.setText(spf.getInt("record4", 0)+"마리");
        }else if(record5<score){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("5등!!").setMessage("축하합니다.");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            editor.putInt("record5", score).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage5.setText(spf.getInt("record5", 0)+"마리");
        }else{
            editor.putInt("record1", record1).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage1.setText(record1+"마리");
            editor.putInt("record2", record2).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage2.setText(record2+"마리");
            editor.putInt("record3", record3).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage3.setText(record3+"마리");
            editor.putInt("record4", record4).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage4.setText(record4+"마리");
            editor.putInt("record5", record5).apply(); //반영의 commit(). 현재상태 저장
            tx_game_dudage5.setText(record5+"마리");
            if(score==-1){
                tx_game_now.setText("");
            }
        }
        Log.e("리코","더"+record1+record2+record3+record4+record5);

        bt_game_restart = (Button) findViewById(R.id.bt_game_dudage_restart);
        bt_game_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiniGame_Dudage_Record_Activity.this, MiniGame_Dudage_Start_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
