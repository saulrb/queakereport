package com.example.android.quakereport.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by saul on 2/1/2017.
 */

public class Earthquake {
    private Double mag;
    private String place;
    private Date time;
    private static final String DATE_FORMAT = "MMM dd, yyyy";
    private static final String TIME_FORMAT = "h:mm a";
    private static final String DOUBLE_FORMAT = "0.0";

    public Earthquake(Double mag, String place, Date time) {
        this.mag = mag;
        this.place = place;
        this.time = time;
    }

    public Double getMag() {
        return mag;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(time);
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(time);
    }

    public String getMagFormatted() {
        DecimalFormat formmater = new DecimalFormat(DOUBLE_FORMAT);
        return formmater.format(this.mag);
    }
}
