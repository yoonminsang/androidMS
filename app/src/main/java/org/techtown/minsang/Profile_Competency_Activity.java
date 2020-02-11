package org.techtown.minsang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.techtown.minsang.DbOpenHelper2;
import org.techtown.minsang.R;

public class Profile_Competency_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText ed_date2;
    EditText ed_squart;
    EditText ed_benchpress;
    EditText ed_deadlift;
    CheckBox ck_date2;
    CheckBox ck_total2;

    double date;
    double sqart;
    double benchpress;
    double deadlift;
    double total;

    long nowIndex;

    String sort = "date";

    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DbOpenHelper2 mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__competency_);

        Button bt_save2 = (Button) findViewById(R.id.bt_save2);
        bt_save2.setOnClickListener(this);
//        bt_identify = (Button) v.findViewById(R.id.bt_identify);
//        bt_identify.setOnClickListener(this);
        Button bt_alldelete2 = (Button) findViewById(R.id.bt_alldelete2);
        bt_alldelete2.setOnClickListener(this);
        Button bt_sort2 = (Button) findViewById(R.id.bt_sort2);
        bt_sort2.setOnClickListener(this);
        ed_date2 = (EditText) findViewById(R.id.ed_date2);
        ed_squart = (EditText) findViewById(R.id.ed_squart);
        ed_benchpress = (EditText) findViewById(R.id.ed_benchpress);
        ed_deadlift = (EditText) findViewById(R.id.ed_deadlift);
        ck_date2 = (CheckBox) findViewById(R.id.ck_date2);
        ck_date2.setOnClickListener(this);
        ck_total2 = (CheckBox) findViewById(R.id.ck_total2);
        ck_total2.setOnClickListener(this);

        SimpleDateFormat dayFormat = new SimpleDateFormat("YY.MMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        String weekDay = dayFormat.format(calendar.getTime());
        ed_date2.setText(weekDay);
        System.out.println(ed_date2.getText());

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.text_inbody);
//        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item);
        ListView listView = (ListView) findViewById(R.id.result2);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onClickListener);
        listView.setOnItemLongClickListener(longClickListener);

        mDbOpenHelper = new DbOpenHelper2(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        ck_date2.setChecked(true);
        showDatabase(sort);

//        bt_save.setEnabled(true);
//        bt_identify.setEnabled(false);

    }

    public void setInsertMode() {
        ed_date2.setText("");
        ed_squart.setText("");
        ed_benchpress.setText("");
        ed_deadlift.setText("");
//        bt_save.setEnabled(true);
//        bt_identify.setEnabled(false);
    }

    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            nowIndex = Long.parseLong(arrayIndex.get(position));
            Log.e("On Click", "nowIndex = " + nowIndex);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);
            ed_date2.setText(tempData[0].trim());
            ed_squart.setText(tempData[1].trim());
            ed_benchpress.setText(tempData[2].trim());
            ed_deadlift.setText(tempData[3].trim());
//            bt_save.setEnabled(false);
//            bt_identify.setEnabled(true);
        }
    };

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            String[] nowData = arrayData.get(position).split("\\s+");
            String viewData = nowData[0] + ", " + nowData[1] + ", " + nowData[2] + ", " + nowData[3];
            AlertDialog.Builder dialog = new AlertDialog.Builder(Profile_Competency_Activity.this);
            dialog.setTitle("데이터 삭제")
                    .setMessage("해당 데이터를 삭제 하시겠습니까?" + "\n" + viewData)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDbOpenHelper.deleteColumn(nowIndex);
                            showDatabase(sort);
                            setInsertMode();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setInsertMode();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };

    public void showDatabase(String sort) {
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        arrayData.clear();
        arrayIndex.clear();
        while (iCursor.moveToNext()) {
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));

            Double ddate = iCursor.getDouble(iCursor.getColumnIndex("date"));
            String tempDate = String.format("%07.4f", ddate);
            tempDate = setTextLength(tempDate, 12);

            String tempSquart = iCursor.getString(iCursor.getColumnIndex("squart"));
            tempSquart = setTextLength(tempSquart, 8);

            String tempBenchpress = iCursor.getString(iCursor.getColumnIndex("benchpress"));
            tempBenchpress = setTextLength(tempBenchpress, 9);

            String tempDeadlift = iCursor.getString(iCursor.getColumnIndex("deadlift"));
            tempDeadlift = setTextLength(tempDeadlift, 8);

            String tempTotal = iCursor.getString(iCursor.getColumnIndex("total"));

            String Result = tempDate + tempSquart + tempBenchpress + tempDeadlift + tempTotal;
            arrayData.add(Result);
            arrayIndex.add(tempIndex);
        }
        arrayAdapter.clear();
        arrayAdapter.addAll(arrayData);
        arrayAdapter.notifyDataSetChanged();
    }

    public String setTextLength(String text, int length) {
        if (text.length() < length) {
            int gap = length - text.length();
            for (int i = 0; i < gap; i++) {
                text = text + "  ";
            }
        }
        return text;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save2:
                date = Double.parseDouble(ed_date2.getText().toString());
                sqart = Double.parseDouble(ed_squart.getText().toString());
                benchpress = Double.parseDouble(ed_benchpress.getText().toString());
                deadlift = Double.parseDouble(ed_deadlift.getText().toString());
                total = sqart + benchpress + deadlift;
                if (nowIndex > 0) {
                    mDbOpenHelper.updateColumn(nowIndex, date, sqart, benchpress, deadlift, total);
                    showDatabase(sort);
                    setInsertMode();
                    break;
                } else {
                    mDbOpenHelper.open();
                    mDbOpenHelper.insertColumn(date, sqart, benchpress, deadlift, total);
                    showDatabase(sort);
                    setInsertMode();
                    break;
                }


//            case R.id.bt_identify:
//                date = Double.parseDouble(ed_date.getText().toString());
//                weight = Double.parseDouble(ed_weight.getText().toString());
//                muslemass = Double.parseDouble(ed_muslemass.getText().toString());
//                bodyfat = Double.parseDouble(ed_bodyfat.getText().toString());
//                bodyfatpercentage = Double.parseDouble(ed_bodyfatpercentage.getText().toString());
//                abdominalfatpercentage = Double.parseDouble(ed_abdominalfatpercentage.getText().toString());
//                mDbOpenHelper.updateColumn(nowIndex, date, weight, muslemass, bodyfat, bodyfatpercentage, abdominalfatpercentage);
//                showDatabase(sort);
//                setInsertMode();
//                break;

            case R.id.bt_alldelete2:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("데이터 삭제")
                        .setMessage("전체 데이터를 삭제 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDbOpenHelper.deleteAllColumns();
                                showDatabase(sort);
                                setInsertMode();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setInsertMode();
                            }
                        })
                        .create()
                        .show();
                break;

            case R.id.ck_date2:
                ck_total2.setChecked(false);
                sort = "date";
                break;

            case R.id.ck_total2:
                ck_date2.setChecked(false);
                sort = "total";
                break;

            case R.id.bt_sort2:
                showDatabase(sort);
                break;
        }
    }
}
