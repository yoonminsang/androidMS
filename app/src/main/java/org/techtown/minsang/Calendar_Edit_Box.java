package org.techtown.minsang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Calendar_Edit_Box extends AppCompatActivity {
    private ArrayList<CalendarData> arrayList;
    private Calendar_RecyclerAdapter recyclerAdapter;
    private CalendarData calendarData;
    private TextView tx_date;
    private int pst;
    public static final int REQUEST_CODE_CALENDAR_EDIT_BOX = 102;
    private CalendarData2 calendarData2;
    private ArrayList<CalendarData2> arrayList2;
    private ArrayList<CalendarData> loadarray;
    //    String name;
    String date;
    static public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_edit_box);

        date = getIntent().getStringExtra("date");
        tx_date = findViewById(R.id.tx_date);
        tx_date.setText(date);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.calendar_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        loadData();
        for (int i = 0; i < loadarray.size(); i++) {
            String loadname = loadarray.get(i).getName();
            ArrayList<CalendarData2> loadarray2 = new ArrayList<>();
            loadarray2 = loadarray.get(i).getArrayList();
            for (int j = 0; j < loadarray2.size(); j++) {
                String a = loadarray2.get(j).getSets();
                String b = loadarray2.get(j).getWeight();
                String c = loadarray2.get(j).getRaps();
                CalendarData2 calendarData2 = new CalendarData2(a, b, c);
                arrayList2.add(calendarData2);
            }
            CalendarData calendarData = new CalendarData(loadname, loadarray2);
            arrayList.add(calendarData);
        }

        recyclerAdapter = new Calendar_RecyclerAdapter(arrayList, this);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                calendarData = arrayList.get(position);
                pst = position;
                Intent intent = new Intent(getBaseContext(), Calendar_Edit_Box_Log.class);
                intent.putExtra("name", calendarData.getName());
                intent.putExtra("data", calendarData.getArrayList());
                startActivityForResult(intent, REQUEST_CODE_CALENDAR_EDIT_BOX);
            }

            @Override
            public void onLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Calendar_Edit_Box.this);
                builder.setTitle("운동 기록 삭제").setMessage("정말로 삭제하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        arrayList.remove(position);
                        recyclerAdapter.notifyDataSetChanged();
                        saveData();
                        Toast.makeText(getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }));

        Button bt_calendar_add = (Button) findViewById(R.id.bt_calendar_add);
        bt_calendar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = "성공하자민상아";
                String strSets = "";
                String strWeight = "";
                String strRaps = "";
                calendarData2 = new CalendarData2(strSets, strWeight, strRaps);
                arrayList2 = new ArrayList<>();
                arrayList2.add(calendarData2);
                calendarData = new CalendarData(strName, arrayList2);
//                arrayList.add(0, calendarData); //첫 줄에 삽입
                arrayList.add(calendarData);
                pst = arrayList.size() - 1;
                Intent intent = new Intent(getBaseContext(), Calendar_Edit_Box_Log.class);
                startActivityForResult(intent, REQUEST_CODE_CALENDAR_EDIT_BOX);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getName().equals("성공하자민상아")) {
                arrayList.remove(i);
            }
        }
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("backname");
            ArrayList<CalendarData2> getarray = new ArrayList<>();
            getarray = data.getParcelableArrayListExtra("backdata");
            for (int num = 0; num < Calendar_Edit_Box_Log.arraylist2size; num++) {
                CalendarData2 calendarData2 = new CalendarData2(getarray.get(num).getSets(), getarray.get(num).getWeight(), getarray.get(num).getRaps());
                if (num == 0) {
                    arrayList2.set(0, calendarData2);
                } else {
                    arrayList2.add(calendarData2);
                }
            }
            calendarData = new CalendarData(name, getarray);
            arrayList.set(pst, calendarData);
//            arrayList.add(calendarData);
            saveData();
            recyclerAdapter.notifyDataSetChanged();
        }
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Calendar_Edit_Box.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Calendar_Edit_Box.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    private void saveData() {
        sharedPreferences = getSharedPreferences("calendar", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(date + Login.loginpos, json);
        editor.apply();
    }

    private void loadData() {
        sharedPreferences = getSharedPreferences("calendar", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(date + Login.loginpos, null);
        Type type = new TypeToken<ArrayList<CalendarData>>() {
        }.getType();
        loadarray = gson.fromJson(json, type);
        if (loadarray == null) {
            loadarray = new ArrayList<>();
        }
    }
}


