package org.techtown.minsang;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.techtown.minsang.Fragment.CalendarFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.List;

public class Calendar_GridAdapter extends BaseAdapter {

    Calendar calendar;
    List<String> list;
    LayoutInflater inflater;
    Context context;
    String date;
    ArrayList<CalendarData> arrayList;
    public static int point;

    public Calendar_GridAdapter(List<String> list, Context context) {
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        calendar = Calendar.getInstance();
        Calendar_ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
            viewHolder = new Calendar_ViewHolder();
            viewHolder.tx_Calendar_ViewHolder = (TextView) convertView.findViewById(R.id.tx_item_calendar_gridview);
            viewHolder.im_Calendar_ViewHolder = (ImageView) convertView.findViewById(R.id.iv_calendar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Calendar_ViewHolder) convertView.getTag();
        }

        //여기 포지션은 전체포지션이고 getitem포지션은 날짜있는거부터 시작이야.
        viewHolder.tx_Calendar_ViewHolder.setText("" + getItem(position));
        calendar = Calendar.getInstance();
        Integer today = calendar.get(Calendar.DAY_OF_MONTH);
        String sToday = String.valueOf(today);

        if (position % 7 == 0) {
            viewHolder.tx_Calendar_ViewHolder.setTextColor(Color.RED);
        } else if (position % 7 == 6) {
            viewHolder.tx_Calendar_ViewHolder.setTextColor(Color.BLUE);
        } else {
            viewHolder.tx_Calendar_ViewHolder.setTextColor(Color.BLACK);
        }

        if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
            viewHolder.tx_Calendar_ViewHolder.setTextColor(Color.MAGENTA);
        }

        if (getItem(position).equals("") || getItem(position).equals("월") || getItem(position).equals("화")
                || getItem(position).equals("수") || getItem(position).equals("목")
                || getItem(position).equals("금") || getItem(position).equals("토") || getItem(position).equals("일")) {
            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        } else {
            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }

        date = CalendarFragment.a + "-" + CalendarFragment.b + "-" + getItem(position);
        loadData();
        if (arrayList.size() > 0) {
            viewHolder.im_Calendar_ViewHolder.setImageResource(R.drawable.draw_green);
            CalendarFragment.point++;
        } else {
            viewHolder.im_Calendar_ViewHolder.setImageResource(0);
        }
        CalendarFragment.tx_calendar_point.setText("이번달 운동 횟수 : " + CalendarFragment.point);



        return convertView;
    }

    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("calendar", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(date + Login.loginpos, null);
        Type type = new TypeToken<ArrayList<CalendarData>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
    }

    private class Calendar_ViewHolder {
        TextView tx_Calendar_ViewHolder;
        ImageView im_Calendar_ViewHolder;
    }
}
