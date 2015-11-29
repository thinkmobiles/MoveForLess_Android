package com.miami.moveforless.database;


import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.rest.response.JobResponse;
import com.miami.moveforless.rest.response.MoveSizeResponse;
import com.miami.moveforless.rest.response.NumberMenResponse;
import com.miami.moveforless.utils.TimeUtil;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.Model;

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
        return new Select().from(JobResponse.class).where(Condition.column("pickup_date")
                .greaterThan(TimeUtil.getUnixTime())).or(Condition.column("isActive").eq(1))
                .orderBy("isActive DESC, pickup_date").queryCustomList(JobModel.class);
    }

    @Override
    public JobModel getActiveJob() {
        return new Select().from(JobResponse.class).where(Condition.column("isActive").eq(1)).queryCustomSingle(JobModel.class);
    }

    @Override
    public JobModel getNumberJob(String _number) {
        return new Select().from(JobResponse.class).where(Condition.column("post_title").eq(_number)).queryCustomSingle(JobModel.class);
    }

    @Override
    public List<NumberMenResponse> getListNumberMen() {
        return new Select().from(NumberMenResponse.class).queryList();
    }

    @Override
    public List<MoveSizeResponse> getListMoveSize() {
        return new Select().from(MoveSizeResponse.class).queryList();
    }

    @Override
    public void updateJob(JobResponse _jobResponse) {
        _jobResponse.update();
    }

    @SafeVarargs
    @Override
    public final void dropDataBase(Class<? extends Model>... tables) {
        Delete.tables(tables);
    }


}
