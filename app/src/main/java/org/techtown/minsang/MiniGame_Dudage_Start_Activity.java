package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MiniGame_Dudage_Start_Activity extends AppCompatActivity {
    TextView tx_game_dudage_time;
    TextView tx_game_dudage_score;
    private Thread thread = null;

    ImageView[] img_array = new ImageView[9];
    int[] imageID = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5,
            R.id.imageView6, R.id.imageView7, R.id.imageView8, R.id.imageView9};

    final String TAG_ON = "on"; //태그용
    final String TAG_OFF = "off";
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game__dudage__start_);

        tx_game_dudage_time = (TextView) findViewById(R.id.tx_game_dudage_time);
        tx_game_dudage_score = (TextView) findViewById(R.id.tx_game_dudage_score);

        for (int i = 0; i < img_array.length; i++) {
            img_array[i] = (ImageView) findViewById(imageID[i]);
            img_array[i].setImageResource(R.drawable.dudage_down);
            img_array[i].setTag(TAG_OFF);

            img_array[i].setOnClickListener(new View.OnClickListener() { //두더지이미지에 온클릭리스너
                @Override
                public void onClick(View v) {
                    if (((ImageView) v).getTag().toString().equals(TAG_ON)) {
                        tx_game_dudage_score.setText(++score + "마리");
//                        ((ImageView) v).setImageResource(R.drawable.dudage_die);

//                        try {
//                            Thread.sleep(300);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }

//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                finish();
//                            }
//                        }, 300);


                        ((ImageView) v).setImageResource(R.drawable.dudage_down);
                        v.setTag(TAG_OFF);
                    } else {
                        if (score <= 0) {
                            score = 0;
                            tx_game_dudage_score.setText(score + "마리");
                        } else {
                            tx_game_dudage_score.setText(--score + "마리");
                        }
                    }
                }
            });
        }
        tx_game_dudage_time.setText("60초");
        tx_game_dudage_score.setText("0마리");
        thread = new Thread(new timeCheck());
        thread.start();
        for (int i = 0; i < img_array.length; i++) {
            new Thread(new DThread(i)).start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        thread.interrupt();
    }

    Handler onHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            img_array[msg.arg1].setImageResource(R.drawable.dudage_up);
            img_array[msg.arg1].setTag(TAG_ON); //올라오면 ON태그 달아줌
        }
    };

    Handler offHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            img_array[msg.arg1].setImageResource(R.drawable.dudage_down);
            img_array[msg.arg1].setTag(TAG_OFF); //내려오면 OFF태그 달아줌
        }
    };

    public class DThread implements Runnable { //두더지를 올라갔다 내려갔다 해줌
        int index = 0; //두더지 번호

        DThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int offtime = new Random().nextInt(5000) + 500;
                    Thread.sleep(offtime); //두더지가 내려가있는 시간
                    Message msg1 = new Message();
                    msg1.arg1 = index;
                    onHandler.sendMessage(msg1);

                    int ontime = new Random().nextInt(1000) + 500;
                    Thread.sleep(ontime); //두더지가 올라가있는 시간
                    Message msg2 = new Message();
                    msg2.arg1 = index;
                    offHandler.sendMessage(msg2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tx_game_dudage_time.setText(msg.arg1 + "초");
        }
    };

    public class timeCheck implements Runnable {
        final int MAXTIME = 60;

        @Override
        public void run() {
            for (int i = MAXTIME; i >= 0; i--) {
                Message msg = new Message();
                msg.arg1 = i;
                handler.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            Intent intent = new Intent(MiniGame_Dudage_Start_Activity.this, MiniGame_Dudage_Record_Activity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }
}