package com.miami.moveforless.database;

import android.content.Context;

import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.rest.response.JobResponse;
import com.miami.moveforless.rest.response.MoveSizeResponse;
import com.miami.moveforless.rest.response.NumberMenResponse;
import com.raizlabs.android.dbflow.structure.Model;

import java.util.List;

/**
 * Created by Sergbek on 13.11.2015.
 */
public interface AbstractControllerData {

    List<JobModel> getListJob();

    JobModel getActiveJob();

    JobModel getNumberJob(String _number);

    List<NumberMenResponse> getListNumberMen();

    List<MoveSizeResponse> getListMoveSize();

    void updateJob(JobResponse _jobResponse);

    void dropDataBase(Class<? extends Model>... tables);
}
