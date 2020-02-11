package org.techtown.minsang;

import android.os.Parcel;
import android.os.Parcelable;

public class CalendarData2 implements Parcelable {
    private String sets;
    private String weight;
    private String raps;

    public CalendarData2(String sets, String weight, String raps) {
        this.sets = sets;
        this.weight = weight;
        this.raps = raps;
    }

    protected CalendarData2(Parcel in) {
        sets = in.readString();
        weight = in.readString();
        raps = in.readString();
    }

    public static final Creator<CalendarData2> CREATOR = new Creator<CalendarData2>() {
        @Override
        public CalendarData2 createFromParcel(Parcel in) {
            return new CalendarData2(in);
        }

        @Override
        public CalendarData2[] newArray(int size) {
            return new CalendarData2[size];
        }
    };

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRaps() {
        return raps;
    }

    public void setRaps(String raps) {
        this.raps = raps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sets);
        dest.writeString(weight);
        dest.writeString(raps);
    }
}