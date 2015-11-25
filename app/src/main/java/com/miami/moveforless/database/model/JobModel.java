package com.miami.moveforless.database.model;

import android.text.TextUtils;
import android.text.format.DateFormat;

import com.miami.moveforless.database.MoveForLessDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;
import com.raizlabs.android.dbflow.structure.BaseQueryModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Sergbek on 13.11.2015.
 */

@QueryModel(databaseName = MoveForLessDatabase.NAME)
public class JobModel extends BaseQueryModel {

    public boolean mExpand;
//    public boolean mSub;

    public int size;

    public int realPosition;
    public boolean isFuture;
//    public List<JobModel> child;

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

    @Column
    public Integer isActive;

    public JobModel(boolean _expand, int _size, String _pickup_date, boolean _isFuture, Integer _isActive) {
        mExpand = _expand;
        size = _size;
        pickup_date = _pickup_date;
        isFuture = _isFuture;
        isActive = _isActive;
    }

    public JobModel() {
    }

    public String getPickup_date() {
        if (!TextUtils.isEmpty(pickup_date)) {
            Integer intUnixTime = Integer.valueOf(pickup_date);
            return DateFormat.format("MM/dd", intUnixTime * 1000L).toString();
        }

        return "";
    }

    public String getRequiredPickupDate() {
        if (!TextUtils.isEmpty(RequiredPickupDate)) {
            Integer intUnixTime = Integer.valueOf(RequiredPickupDate);
            return DateFormat.format("hh:mmaa", intUnixTime * 1000L).toString();
        }

        return "";
    }

    public String getFullDate(){
        if (!TextUtils.isEmpty(pickup_date)){
            Integer intUnixTime = Integer.valueOf(pickup_date);
            return DateFormat.format("E MM/dd/yyyy", intUnixTime * 1000L).toString();
        }

        return "";
    }

    public int getDay(){
        if (!TextUtils.isEmpty(pickup_date)){
            Integer intUnixTime = Integer.valueOf(pickup_date);

            return Integer.parseInt(DateFormat.format("dd", intUnixTime * 1000L).toString());
        }

        return 0;
    }

    public String getStatus_slug() {
       return TextUtils.isEmpty(status_slug) ? status_slug : "";
    }

    public String getPost_title() {
        return TextUtils.isEmpty(post_title) ? post_title : "";

    }

    public String getFrom_fullname() {
        return TextUtils.isEmpty(from_fullname) ? from_fullname : "";

    }

    public String getFrom_phone() {
        return TextUtils.isEmpty(from_phone) ? from_phone : "";

    }

    public String getFrom_zipcode() {
        return TextUtils.isEmpty(from_zipcode) ? from_zipcode : "";

    }

    public String getFrom_city() {
        return TextUtils.isEmpty(from_city) ? from_city : "";

    }

    public String getFrom_address() {
        return TextUtils.isEmpty(from_address) ? from_address : "";

    }

    public String getTo_zipcode() {
        return TextUtils.isEmpty(to_zipcode) ? to_zipcode : "";

    }

    public String getTo_city() {
        return TextUtils.isEmpty(to_city) ? to_city : "";

    }

    public String getTo_address() {
        return TextUtils.isEmpty(to_address) ? to_address : "";

    }
}
