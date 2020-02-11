package org.techtown.minsang;

import android.provider.BaseColumns;

public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String DATE = "date";
        public static final String WEIGHT = "weight";
        public static final String MUSLEMASS = "muslemass";
        public static final String BODYFAT = "bodyfat";
        public static final String BODYFATPERCENTAGE = "bodyfatpercentage";
        public static final String ABDOMINALFATPERCENTAGE = "abdominalfatpercentage";
        public static final String _TABLENAME0 = "usertable"+Login.loginpos;
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer primary key autoincrement, "
                + DATE + " double not null , "
                + WEIGHT + " double not null , "
                + MUSLEMASS + " double not null , "
                + BODYFAT + " double not null , "
                + BODYFATPERCENTAGE + " double not null , "
                + ABDOMINALFATPERCENTAGE + " double not null );";
    }

    public static final class CreateDB1 implements BaseColumns {
        public static final String DATE = "date";
        public static final String SQUART = "squart";
        public static final String BENCHPRESS = "benchpress";
        public static final String DEADLIFT = "deadlift";
        public static final String TOTAL = "total";
        public static final String _TABLENAME0 = "usertable"+Login.loginpos;
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer primary key autoincrement, "
                + DATE + " double not null , "
                + SQUART + " double not null , "
                + BENCHPRESS + " double not null , "
                + DEADLIFT + " double not null , "
                + TOTAL + " double not null );";
    }
}