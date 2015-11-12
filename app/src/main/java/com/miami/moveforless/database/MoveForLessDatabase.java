package com.miami.moveforless.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Sergbek on 12.11.2015.
 */

@Database(name = MoveForLessDatabase.NAME, version = MoveForLessDatabase.VERSION)
public class MoveForLessDatabase {

    public static final String NAME = "move_for_less";

    public static final int VERSION = 1;
}
