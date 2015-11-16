package com.miami.moveforless.database;


import android.content.Context;

import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.rest.response.JobResponse;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by Sergbek on 13.11.2015.
 */
public class DatabaseController implements AbstractControllerData {

    private DatabaseController(){}

    private static class SingletonHelper{
        private static final DatabaseController INSTANCE = new DatabaseController();
    }

    public static DatabaseController getInstance(){
        return SingletonHelper.INSTANCE;
    }

    @Override
    public List<JobModel> getListJob() {
//        return new Select().from(JobResponse.class).where("WHERE DATA > DATENOW || ISACTIVE = 1 ORDER BY ASC DATE").queryCustomList(JobModel.class);
        return new Select().from(JobResponse.class).queryCustomList(JobModel.class);
    }

    @Override
    public void updateJob(JobResponse jobResponse) {
        jobResponse.update();
    }

    @Override
    public void deleteJob(JobResponse jobResponse) {
        jobResponse.delete();
    }

    @Override
    public void dropDataBase(Context context) {
        context.deleteDatabase(MoveForLessDatabase.NAME +".db");
    }
}
