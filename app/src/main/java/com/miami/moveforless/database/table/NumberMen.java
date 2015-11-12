package com.miami.moveforless.database.table;

import com.miami.moveforless.database.MoveForLessDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Sergbek on 12.11.2015.
 */

@Table(databaseName = MoveForLessDatabase.NAME)
public class NumberMen extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private long _id;

    @Column
    private int id;

    @Column
    private String number_men;

    @Column
    private String rate_money;


    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber_men() {
        return number_men;
    }

    public void setNumber_men(String number_men) {
        this.number_men = number_men;
    }

    public String getRate_money() {
        return rate_money;
    }

    public void setRate_money(String rate_money) {
        this.rate_money = rate_money;
    }
}
