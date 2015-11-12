package com.miami.moveforless.rest.response;

import com.google.gson.annotations.SerializedName;
import com.miami.moveforless.database.MoveForLessDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Sergbek on 12.11.2015.
 */

@Table(databaseName = MoveForLessDatabase.NAME)
public class NumberMenResponse extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public long _id;

    @Column
    @SerializedName("id")
    public Integer id;

    @Column
    @SerializedName("number_men")
    public String number_men;

    @Column
    @SerializedName("rate_money")
    public String rate_money;
}
