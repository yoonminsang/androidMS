package org.techtown.minsang.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.techtown.minsang.Calendar_Edit_Box;
import org.techtown.minsang.Calendar_GridAdapter;
import org.techtown.minsang.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private TextView tx_date;
    private Button bt_day_previous;
    private Button bt_day_later;
    private Calendar_GridAdapter gridAdapter;
    private ArrayList<String> dayList;
    private GridView gridview;
    private Calendar calendar;
    public static int a;
    public static int b;
    private String sDate;
    //    public static String nowdate;
    public static TextView tx_calendar_point;
    public static int point = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        tx_date = (TextView) v.findViewById(R.id.tx_day);
        bt_day_previous = (Button) v.findViewById(R.id.bt_day_previous);
        bt_day_later = (Button) v.findViewById(R.id.bt_day_later);
        gridview = (GridView) v.findViewById(R.id.gridview_calendar);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat nowYear = new SimpleDateFormat("yyyy", Locale.KOREAN);
        SimpleDateFormat nowMonth = new SimpleDateFormat("MM", Locale.KOREAN);
//        SimpleDateFormat nowDay = new SimpleDateFormat("dd", Locale.KOREAN);
//        nowdate = Integer.parseInt(nowYear.format(date)) + "-" + Integer.parseInt(nowMonth.format(date)) + "-" + Integer.parseInt(nowDay.format(date));
        a = Integer.parseInt(nowYear.format(date));
        b = Integer.parseInt(nowMonth.format(date));

        dayList = new ArrayList<String>();

        calendar = Calendar.getInstance();
        getCalendar();

        gridAdapter = new Calendar_GridAdapter(dayList, getActivity().getApplicationContext());
        gridview.setAdapter(gridAdapter);

        tx_calendar_point = (TextView) v.findViewById(R.id.tx_calendar_point);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                point = 0;
                Intent intent = new Intent(getActivity().getBaseContext(), Calendar_Edit_Box.class);
                sDate = a + "-" + b + "-" + gridAdapter.getItem(position);
                intent.putExtra("date", sDate);
                startActivity(intent);
            }
        });

        bt_day_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = 0;
                b--;
                calendar.set(a - 0, b - 1, 1);
                getCalendar();
                gridAdapter.notifyDataSetChanged();
            }
        });

        bt_day_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = 0;
                b++;
                if (b - 1 <= 0) {
                    tx_date.setText(a + "." + (b - 1));
                }
                calendar.set(a - 0, b - 1, 1);
                getCalendar();
                gridAdapter.notifyDataSetChanged();
            }
        });
        return v;
    }

    private void getCalendar() {
        if (b <= 0) {
            a = a - 1;
            b = b + 12;
        } else if (b <= 12) ;
        else if (b <= 24) {
            a = a + 1;
            b = b - 12;
        }
        tx_date.setText(a + "년 " + b + "월");
        if (b == 1) {
            bt_day_previous.setText((a - 1) + "년 " + (b - 1 + 12) + "월");
            bt_day_later.setText(a + "년 " + (b + 1) + "월");
        } else if (b == 12) {
            bt_day_previous.setText((a) + "년 " + (b - 1) + "월");
            bt_day_later.setText((a + 1) + "년 " + (b + 1 - 12) + "월");
        } else {
            bt_day_previous.setText((a) + "년 " + (b - 1) + "월");
            bt_day_later.setText((a) + "년 " + (b + 1) + "월");
        }
        dayList.clear();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");

        // 1일 나타내기
        calendar.set(Calendar.DATE, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < dayOfWeek; i++) {
            dayList.add("");
        }

        // 1일부터 마지막일까지 나타내기
        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= dayOfMonth; i++) {
            dayList.add("" + i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        point = 0;
        gridAdapter.notifyDataSetChanged();
    }
}
