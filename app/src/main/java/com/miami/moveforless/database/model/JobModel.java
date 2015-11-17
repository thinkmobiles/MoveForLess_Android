package com.miami.moveforless.database.model;

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

    public Boolean mExpand;
    public Boolean mSub;
    public List<JobModel> child;

    public JobModel(List<JobModel> child) {
        this.child = child;
    }

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

    public JobModel() {
    }
}
