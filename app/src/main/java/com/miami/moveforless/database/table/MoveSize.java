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
public class MoveSize extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private long _id;

    @Column
    private int id;

    @Column
    private String name;

    @Column
    private String rank_id;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank_id() {
        return rank_id;
    }

    public void setRank_id(String rank_id) {
        this.rank_id = rank_id;
    }
}
