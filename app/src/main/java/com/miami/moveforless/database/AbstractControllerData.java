package com.miami.moveforless.database;

import android.content.Context;

import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.rest.response.JobResponse;

import java.util.List;

/**
 * Created by Sergbek on 13.11.2015.
 */
public interface AbstractControllerData {

    List<JobModel> getListJob();

    void updateJob(JobResponse jobResponse);

    void dropDataBase(Context context);
}
