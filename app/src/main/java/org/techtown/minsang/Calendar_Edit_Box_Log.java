package org.techtown.minsang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Calendar_Edit_Box_Log extends AppCompatActivity {

    private EditText ed_log_name;
    private EditText ed_time;
    private Button bt_start;
    private Button bt_resume;
    private Button bt_cancle;
    private Thread thread = null;
    private String a;
    private Boolean isRunning = true;
    private int time;
    private ArrayList<CalendarData2> arrayList2;
    private Calendar_RecyclerAdapter2 recyclerAdapter2;
    private CalendarData2 calendarData2;
    private int sets = 0;
    public static int arraylist2size;
    private InputMethodManager imm;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_edit_box_log);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.calendar_RecyclerView2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ed_log_name = (EditText) findViewById(R.id.ed_log_name);
        ed_log_name.setText(getIntent().getStringExtra("name"));

        ArrayList<CalendarData2> array = new ArrayList<>();
        array = getIntent().getParcelableArrayListExtra("data");
        arrayList2 = new ArrayList<>();
        if (array == null) ;
        else {
            for (int i = 0; i < array.size(); i++) {
                calendarData2 = new CalendarData2(array.get(i).getSets(), array.get(i).getWeight(), array.get(i).getRaps());
                arrayList2.add(calendarData2);
            }
        }

        recyclerAdapter2 = new Calendar_RecyclerAdapter2(arrayList2, this);
        recyclerView.setAdapter(recyclerAdapter2);

        Button bt_calendar_log_plus = (Button) findViewById(R.id.bt_calendar_log_plus);
        bt_calendar_log_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList2 == null) {
                    sets = 0;
                } else if (sets != arrayList2.size()) {
                    sets = arrayList2.size();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Calendar_Edit_Box_Log.this);
                View view = LayoutInflater.from(Calendar_Edit_Box_Log.this)
                        .inflate(R.layout.item_calendar_alert, null, false);
                builder.setView(view);
                final Button bt_calendar_alert_save = (Button) view.findViewById(R.id.bt_calendar_alert_save);
                final TextView tx_calendar_sets = (TextView) view.findViewById(R.id.tx_calendar_alert_sets);
                final EditText ed_calendar_alert_weight = (EditText) view.findViewById(R.id.ed_calendar_alert_weight);
                ed_calendar_alert_weight.setInputType(InputType.TYPE_CLASS_NUMBER);
                final EditText ed_calendar_alert_raps = (EditText) view.findViewById(R.id.ed_calendar_alert_raps);
                ed_calendar_alert_raps.setInputType(InputType.TYPE_CLASS_NUMBER);
                sets++;
                tx_calendar_sets.setText(sets + "셋트");
                final AlertDialog dialog = builder.create();
                bt_calendar_alert_save.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String strSets = sets + "셋트";
                        String strWeight = ed_calendar_alert_weight.getText().toString();
                        String strRaps = ed_calendar_alert_raps.getText().toString();
                        CalendarData2 calendarData2 = new CalendarData2(strSets, strWeight, strRaps);
//                arrayList2.add(0, calendarData2); //첫 줄에 삽입
                        arrayList2.add(calendarData2); //마지막 줄에 삽입
                        recyclerAdapter2.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Button bt_calendar_log_minus = (Button) findViewById(R.id.bt_calendar_log_minus);
        bt_calendar_log_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList2.remove(arrayList2.size()-1);
                recyclerAdapter2.notifyDataSetChanged();
            }
        });


        Button bt_log_save = (Button) findViewById(R.id.bt_log_save);
        bt_log_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("backname", ed_log_name.getText().toString());
                intent.putParcelableArrayListExtra("backdata", arrayList2);
                arraylist2size = arrayList2.size();
                setResult(RESULT_OK, intent);
                finish();
                Toast.makeText(getApplicationContext(), "저장되었습니다. ", Toast.LENGTH_SHORT).show();
            }
        });

        bt_resume = (Button) findViewById(R.id.bt_resume);
        bt_cancle = (Button) findViewById(R.id.bt_cancle);
        bt_resume.setVisibility(View.INVISIBLE);
        bt_cancle.setVisibility(View.INVISIBLE);
        ed_time = (EditText) findViewById(R.id.ed_time);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread = new Thread(new timeCheck());
                thread.start();
                a = ed_time.getText().toString();
                bt_start.setVisibility(View.INVISIBLE);
                bt_resume.setVisibility(View.VISIBLE);
                bt_cancle.setVisibility(View.VISIBLE);
                ed_time.setFocusable(false);
                ed_time.setClickable(false);
                imm.hideSoftInputFromWindow(ed_time.getWindowToken(), 0);
            }
        });

        bt_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = !isRunning;
                if (isRunning) {
                    thread = new Thread(new timeCheck());
                    thread.start();
                    bt_resume.setText("일시정지");
                    ed_time.setFocusable(false);
                    ed_time.setClickable(false);
                    imm.hideSoftInputFromWindow(ed_time.getWindowToken(), 0);
                } else {
                    thread.interrupt();
                    bt_resume.setText("계속");
                    ed_time.setFocusableInTouchMode(true);
                    ed_time.setFocusable(true);
                    ed_time.setClickable(true);
                    imm.showSoftInput(ed_time, 0);
                }
            }
        });

        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.interrupt();
                bt_resume.setVisibility(View.INVISIBLE);
                bt_cancle.setVisibility(View.INVISIBLE);
                bt_start.setVisibility(View.VISIBLE);
                isRunning = true;
                ed_time.setText(a);
                ed_time.setFocusableInTouchMode(true);
                ed_time.setFocusable(true);
                ed_time.setClickable(true);
                imm.showSoftInput(ed_time, 0);
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ed_time.setText(msg.arg1 + "");
        }
    };

    public class timeCheck implements Runnable {
        EditText ed_time = (EditText) findViewById(R.id.ed_time);
        final int MAXTIME = Integer.parseInt(ed_time.getText().toString());

        @Override
        public void run() {
            while (true) {
                for (time = MAXTIME; time >= 0; time--) {
                    Message msg = new Message();
                    msg.arg1 = time;
                    handler.sendMessage(msg);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (time == 0) {
                                ed_time.setFocusableInTouchMode(true);
                                ed_time.setFocusable(true);
                                ed_time.setClickable(true);
                                imm.showSoftInput(ed_time, 0);
                                bt_resume.setVisibility(View.INVISIBLE);
                                bt_cancle.setVisibility(View.INVISIBLE);
                                bt_start.setVisibility(View.VISIBLE);
                                thread.interrupt();
                                ed_time.setText(a);
                                startSound();
                            }
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }

    public void startSound() {
//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Uri notification = Uri.parse("android.resource://패키지이름/raw/파일명");
        Uri notification = Uri.parse("android.resource://org.techtown.minsang/raw/alarm");
        android.media.Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }
}
