package com.miami.moveforless.database.model;

import android.text.TextUtils;
import android.text.format.DateFormat;

import com.miami.moveforless.database.MoveForLessDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;
import com.raizlabs.android.dbflow.structure.BaseQueryModel;

import java.util.List;

/**
 * Created by Sergbek on 13.11.2015.
 */

@QueryModel(databaseName = MoveForLessDatabase.NAME)
public class JobModel extends BaseQueryModel {

    public Boolean mExpand = false;
    public Boolean mSub = false;
    public List<JobModel> child;

    @Column
    public Integer isActive;

    @Column
    public String status_slug;

    @Column
    public String post_title;

    @Column
    public String RequiredPickupDate;

    @Column
    public String from_fullname;

    @Column
    public String from_phone;

    @Column
    public String from_zipcode;

    @Column
    public String from_city;

    @Column
    public String from_address;

    @Column
    public String to_zipcode;

    @Column
    public String to_city;

    @Column
    public String to_address;

    @Column
    public String pickup_date;

    public String getPickup_date() {
        if (!TextUtils.isEmpty(RequiredPickupDate)) {
            Long intUnixTime = Long.valueOf(pickup_date);
            return DateFormat.format("MM/dd", intUnixTime).toString();
        }

        return "";
    }

    public String getRequiredPickupDate() {
        if (!TextUtils.isEmpty(RequiredPickupDate)) {
            Long intUnixTime = Long.valueOf(RequiredPickupDate);
            return DateFormat.format("hh:mmaa", intUnixTime).toString();
        }

        return "";
    }

    public String getFullDate(){
        if (!TextUtils.isEmpty(pickup_date)){
            Long intUnixTime = Long.valueOf(pickup_date);
            return DateFormat.format("E MM/dd/yyyy", intUnixTime).toString();
        }

        return "";
    }

    public int getDay(){
        if (!TextUtils.isEmpty(pickup_date)){
            Long intUnixTime = Long.valueOf(pickup_date);

            return Integer.parseInt(DateFormat.format("dd", intUnixTime).toString());
        }
        return 0;
    }

    public String getChildSize(){
        if (child != null){
            return "(" + child.size() + ")";
        }
        return "";
    }
}