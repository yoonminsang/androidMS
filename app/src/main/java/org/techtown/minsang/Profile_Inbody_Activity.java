package org.techtown.minsang;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Profile_Inbody_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText ed_date;
    EditText ed_weight;
    EditText ed_muslemass;
    EditText ed_bodyfat;
    EditText ed_bodyfatpercentage;
    EditText ed_abdominalfatpercentage;
    CheckBox ck_date;
    CheckBox ck_bodyfatpercentage;

    double date;
    double weight;
    double muslemass;
    double bodyfat;
    double bodyfatpercentage;
    double abdominalfatpercentage;

    long nowIndex;

    String sort = "date";

    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DbOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__inbody_);
        Button bt_save = (Button) findViewById(R.id.bt_save);
        bt_save.setOnClickListener(this);
//        bt_identify = (Button) v.findViewById(R.id.bt_identify);
//        bt_identify.setOnClickListener(this);
        Button bt_alldelete = (Button) findViewById(R.id.bt_alldelete);
        bt_alldelete.setOnClickListener(this);
        Button bt_sort = (Button) findViewById(R.id.bt_sort);
        bt_sort.setOnClickListener(this);
        ed_date = (EditText) findViewById(R.id.ed_date);
        ed_weight = (EditText) findViewById(R.id.ed_weight);
        ed_muslemass = (EditText) findViewById(R.id.ed_muslemass);
        ed_bodyfat = (EditText) findViewById(R.id.ed_bodyfat);
        ed_bodyfatpercentage = (EditText) findViewById(R.id.ed_bodyfatpercentage);
        ed_abdominalfatpercentage = (EditText) findViewById(R.id.ed_abdominalfatpercentage);
        ck_date = (CheckBox) findViewById(R.id.ck_date);
        ck_date.setOnClickListener(this);
        ck_bodyfatpercentage = (CheckBox) findViewById(R.id.ck_bodyfatpercentage);
        ck_bodyfatpercentage.setOnClickListener(this);

        SimpleDateFormat dayFormat = new SimpleDateFormat("YY.MMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        String weekDay = dayFormat.format(calendar.getTime());
        ed_date.setText(weekDay);
        System.out.println(ed_date.getText());

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.text_inbody);
//        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item);
        ListView listView = (ListView) findViewById(R.id.result);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onClickListener);
        listView.setOnItemLongClickListener(longClickListener);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        ck_date.setChecked(true);
        showDatabase(sort);

//        bt_save.setEnabled(true);
//        bt_identify.setEnabled(false);

    }

    public void setInsertMode() {
        ed_date.setText("");
        ed_weight.setText("");
        ed_muslemass.setText("");
        ed_bodyfat.setText("");
        ed_bodyfatpercentage.setText("");
        ed_abdominalfatpercentage.setText("");
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
            ed_date.setText(tempData[0].trim());
            ed_weight.setText(tempData[1].trim());
            ed_muslemass.setText(tempData[2].trim());
            ed_bodyfat.setText(tempData[3].trim());
            ed_bodyfatpercentage.setText(tempData[4].trim());
            ed_abdominalfatpercentage.setText(tempData[5].trim());
//            bt_save.setEnabled(false);
//            bt_identify.setEnabled(true);
        }
    };

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            nowIndex = Long.parseLong(arrayIndex.get(position));
            String[] nowData = arrayData.get(position).split("\\s+");
            String viewData = nowData[0] + ", " + nowData[1] + ", " + nowData[2] + ", " + nowData[3] + ", " + nowData[4] + ", " + nowData[5];
            AlertDialog.Builder dialog = new AlertDialog.Builder(Profile_Inbody_Activity.this);
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

            Double ddate=iCursor.getDouble(iCursor.getColumnIndex("date"));
            String tempDate=String.format("%07.4f",ddate);
            tempDate=setTextLength(tempDate,10);

            Double dweight=iCursor.getDouble(iCursor.getColumnIndex("weight"));
            String tempWeight=String.format("%05.1f",dweight);
            tempWeight=setTextLength(tempWeight,10);

            Double dmuslemass=iCursor.getDouble(iCursor.getColumnIndex("muslemass"));
            String tempMuslemass=String.format("%04.1f",dmuslemass);
            tempMuslemass=setTextLength(tempMuslemass,10);

            Double dbodyfat=iCursor.getDouble(iCursor.getColumnIndex("bodyfat"));
            String tempBodyfat=String.format("%04.1f",dbodyfat);
            tempBodyfat=setTextLength(tempBodyfat,10);

            Double dbodyfatpercentage=iCursor.getDouble(iCursor.getColumnIndex("bodyfatpercentage"));
            String tempBodyfatpercentage=String.format("%04.1f",dbodyfatpercentage);
            tempBodyfatpercentage=setTextLength(tempBodyfatpercentage,10);

            Double dabdominalfatpercentage=iCursor.getDouble(iCursor.getColumnIndex("abdominalfatpercentage"));
            String tempAbdominalfatpercentage=String.format("%04.2f",dabdominalfatpercentage);
            tempAbdominalfatpercentage=setTextLength(tempAbdominalfatpercentage,10);

            String Result = tempDate + tempWeight + tempMuslemass + tempBodyfat + tempBodyfatpercentage + tempAbdominalfatpercentage;
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
                text = text + " ";
            }
        }
        return text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                date = Double.parseDouble(ed_date.getText().toString());
                weight = Double.parseDouble(ed_weight.getText().toString());
                muslemass = Double.parseDouble(ed_muslemass.getText().toString());
                bodyfat = Double.parseDouble(ed_bodyfat.getText().toString());
                bodyfatpercentage = Double.parseDouble(ed_bodyfatpercentage.getText().toString());
                abdominalfatpercentage = Double.parseDouble(ed_abdominalfatpercentage.getText().toString());
                if (nowIndex>0) {
                    mDbOpenHelper.updateColumn(nowIndex, date, weight, muslemass, bodyfat, bodyfatpercentage, abdominalfatpercentage);
                    showDatabase(sort);
                    setInsertMode();
                    break;
                } else {
                    mDbOpenHelper.open();
                    mDbOpenHelper.insertColumn(date, weight, muslemass, bodyfat, bodyfatpercentage, abdominalfatpercentage);
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

            case R.id.bt_alldelete:
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

            case R.id.ck_date:
                ck_bodyfatpercentage.setChecked(false);
                sort = "date";
                break;

            case R.id.ck_bodyfatpercentage:
                ck_date.setChecked(false);
                sort = "bodyfatpercentage";
                break;

            case R.id.bt_sort:
                showDatabase(sort);
                break;
        }
    }
}