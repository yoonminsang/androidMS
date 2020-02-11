package org.techtown.minsang;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CalendarData implements Parcelable{
    private String name;
    private ArrayList<CalendarData2> arrayList;

    public CalendarData(String name, ArrayList<CalendarData2> arrayList) {
        this.name = name;
        this.arrayList = arrayList;
    }

    protected CalendarData(Parcel in) {
        name = in.readString();
        in.readTypedList(arrayList, CalendarData2.CREATOR);
    }

    public static final Creator<CalendarData> CREATOR = new Creator<CalendarData>() {
        @Override
        public CalendarData createFromParcel(Parcel in) {
            return new CalendarData(in);
        }

        @Override
        public CalendarData[] newArray(int size) {
            return new CalendarData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CalendarData2> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<CalendarData2> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(arrayList);
    }
}