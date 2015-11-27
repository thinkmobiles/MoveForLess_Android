package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.activity.FragmentChanger;
import com.miami.moveforless.adapters.ScheduleAdapter;
import com.miami.moveforless.adapters.viewholder.RecyclerItemClickListener;
import com.miami.moveforless.database.DatabaseController;
import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.rest.response.JobResponse;
import com.miami.moveforless.utils.TimeUtil;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import rx.Observable;

/**
 * schedule screen
 * Created by SetKrul on 30.10.2015.
 */
public class ScheduleFragment extends BaseFragment implements View.OnClickListener {

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @BindString(R.string.loading)
    String strLoading;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.etSearch_FS)
    EditText etSearch;
    @Bind(R.id.tvBegin_FS)
    TextView tvBegin;
    @Bind(R.id.tvEnd_FS)
    TextView tvEnd;
    private ScheduleAdapter mAdapter;

    private CaldroidFragment dialogCaldroidFragment;
    private Date dateNow;
    private CaldroidListener listener;
    private List<JobResponse> jobResponses;

    private List<JobModel> jobModels;
    private List<JobModel> jobModelsSorted = new ArrayList<>();
    Integer currentDate = null;
    JobModel header = null;
    JobModel headerFuture = null;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        setHasOptionsMenu(true);
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        if (mAdapter == null) {
            Observable.just(generatedData()).subscribe(fin -> {
                Observable.just(getData()).subscribe(job ->{
                    mAdapter = new ScheduleAdapter(getActivity(), jobModelsSorted);
                    mRecyclerView.setAdapter(mAdapter);
                });
            });
        }

    }

    private List<JobModel> getData(){
        jobModels = DatabaseController.getInstance().getListJob();
        for (JobModel job : jobModels) {
            if (job.isActive == 1) {
                JobModel headerActive = new JobModel();
                headerActive.pickup_date = job.pickup_date;
                headerActive.isActive = 1;
                headerActive.child = new ArrayList<>();
                headerActive.child.add(job);
                jobModelsSorted.add(headerActive);
            } else {
                if (currentDate == null) {
                    currentDate = job.getDay();
                }
                if (currentDate == job.getDay()) {
                    dayControl(job);
                } else {
                    currentDate = job.getDay();
                    header = null;
                    dayControl(job);
                }
            }
        }
        return  jobModelsSorted;
    }

    private boolean generatedData() {
        jobModels = new ArrayList<>();

        Delete.tables(JobResponse.class);

        JobResponse jobModel = new JobResponse();
        jobModel.isActive = 1;
        jobModel.from_fullname = "Lesley";
        jobModel.from_address = "from some address";
        jobModel.to_address = "to some address";
        jobModel.from_city = "From some City";
        jobModel.to_city = "To some City";
        jobModel.from_phone = "783-223-5111";
        jobModel.from_zipcode = "31522";
        jobModel.to_zipcode = "33525";
        jobModel.pickup_date = "1448091900000";
        jobModel.status_slug = "assign";
        jobModel.post_title = "LD025135";
        jobModel.RequiredPickupDate = "1448091900000";
        jobModel.save();
        jobModel.insert();

        for (int i = 0; i < 2; i++) {
            JobResponse jobModelToday = new JobResponse();
            jobModelToday.isActive = 0;
            jobModelToday.from_fullname = "Dominique " + i;
            jobModelToday.from_address = "from some address";
            jobModelToday.to_address = "to some address";
            jobModelToday.from_city = "From some City";
            jobModelToday.to_city = "To some City";
            jobModelToday.from_phone = "326-562-891" + i;
            jobModelToday.from_zipcode = "31522";
            jobModelToday.to_zipcode = "33525";
            jobModelToday.pickup_date = "1448523900000";
            jobModelToday.status_slug = "assign";
            jobModelToday.post_title = "LD02513" + i;
            jobModelToday.RequiredPickupDate = "1448523900000";
            jobModelToday.save();
        }

        for (int i = 0; i < 2; i++) {
            JobResponse jobModelFuture = new JobResponse();
            jobModelFuture.isActive = 0;
            jobModelFuture.from_fullname = "Gav " + i;
            jobModelFuture.from_address = "from some address";
            jobModelFuture.to_address = "to some address";
            jobModelFuture.from_city = "From some City";
            jobModelFuture.to_city = "To some City";
            jobModelFuture.from_phone = "456-312-8945" + i;
            jobModelFuture.from_zipcode = "31522";
            jobModelFuture.to_zipcode = "33525";
            jobModelFuture.pickup_date = "1448610300000";
            jobModelFuture.status_slug = "assign";
            jobModelFuture.post_title = "LD02525" + i;
            jobModelFuture.RequiredPickupDate = "1448610300000";
            jobModelFuture.save();
        }

        for (int i = 0; i < 3; i++) {
            JobResponse jobModelFuture2 = new JobResponse();
            jobModelFuture2.isActive = 0;
            jobModelFuture2.from_fullname = "May " + i;
            jobModelFuture2.from_address = "from some address";
            jobModelFuture2.to_address = "to some address";
            jobModelFuture2.from_city = "From some City";
            jobModelFuture2.to_city = "To some City";
            jobModelFuture2.from_phone = "456-312-8945" + i;
            jobModelFuture2.from_zipcode = "31522";
            jobModelFuture2.to_zipcode = "33525";
            jobModelFuture2.pickup_date = "1448783100000";
            jobModelFuture2.status_slug = "assign";
            jobModelFuture2.post_title = "LD02535" + i;
            jobModelFuture2.RequiredPickupDate = "1448783100000";
            jobModelFuture2.save();
        }
        return true;
    }

    private void dayControl(JobModel job) {
        if (TimeUtil.getCurrentDay() == job.getDay()) {
            addDay(job);
        } else if (TimeUtil.getNextDay() == job.getDay()) {
            addDay(job);
        } else {
            addFuture(job);
        }
    }

    private void addFuture(JobModel job) {
//        if (headerFuture == null) {
//        headerFuture = new JobModel();
//
//        } else {
//
//        }
    }

    private void addDay(JobModel job) {
        if (header == null) {
            header = new JobModel();
            header.pickup_date = job.pickup_date;
            header.child = new ArrayList<>();
            header.child.add(job);
            jobModelsSorted.add(header);
        } else {
            header.child.add(job);
            jobModelsSorted.add(header);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_details) {
            if (getActivity() instanceof FragmentChanger) {
                ((FragmentChanger) getActivity()).switchFragment(JobFragment.newInstance(0), true);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBegin_FS:
                listener = new CaldroidListener() {

                    @Override
                    public void onSelectDate(Date date, View view) {
                        dialogCaldroidFragment.clearSelectedDates();
                        dialogCaldroidFragment.setSelectedDate(date);
                        dialogCaldroidFragment.refreshView();
                        dateNow = date;
                    }

                    @Override
                    public void onChangeMonth(int month, int year) {
//                String text = "month: " + month + " year: " + year;
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClickDate(Date date, View view) {
                    }

                    @Override
                    public void onCaldroidViewCreated() {
                    }

                };
                showCalendar(listener);
                break;
            case R.id.tvEnd_FS:
                listener = new CaldroidListener() {

                    @Override
                    public void onSelectDate(Date date, View view) {
                        dialogCaldroidFragment.clearSelectedDates();
                        dialogCaldroidFragment.setSelectedDate(date);
                        dialogCaldroidFragment.refreshView();
                        dateNow = date;
                    }

                    @Override
                    public void onChangeMonth(int month, int year) {
//                String text = "month: " + month + " year: " + year;
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClickDate(Date date, View view) {
                    }

                    @Override
                    public void onCaldroidViewCreated() {
                    }

                };
                showCalendar(listener);
                break;
        }
    }

    public String showCalendar(CaldroidListener _listener) {
        dialogCaldroidFragment = new CaldroidFragment();
        if (dateNow != null) {
            dialogCaldroidFragment.setSelectedDate(dateNow);
        }
        dialogCaldroidFragment.setCaldroidListener(_listener);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(CaldroidFragment.DIALOG_TITLE, "Select a date");
//                    dialogCaldroidFragment.setArguments(bundle);

        dialogCaldroidFragment.show(getActivity().getSupportFragmentManager(), "CALDROID_DIALOG_FRAGMENT");
        return "";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(),
                (_context, _view, _position) -> {
            if (mAdapter.getItem(_position).child != null) {
                if (!mAdapter.getItem(_position).mExpand) {
                    mAdapter.getItem(_position).mExpand = true;
                    mAdapter.addChild(_position + 1, mAdapter.getItem(_position));
                } else {
                    mAdapter.getItem(_position).mExpand = false;
                    for (int i = 0; i < mAdapter.getItem(_position).child.size(); i++) {
                        if (mAdapter.getItem(_position).child.get(i).mExpand) {
                            mAdapter.getItem(_position).child.get(i).mExpand = false;
                            mAdapter.removeChild(_position + i + 1, mAdapter.getItem(_position).child.get(i));
                        }
                    }
                    mAdapter.removeChild(_position + 1, mAdapter.getItem(_position));
                }
            } else {
                if (getActivity() instanceof FragmentChanger) {
                    ((FragmentChanger) getActivity()).switchFragment(JobFragment.newInstance(0), true);
                }
            }
        }));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                final List<JobModel> filteredModelList = filter(jobModels, s.toString());
//                mAdapter.animateTo(filteredModelList);
//                mRecyclerView.scrollToPosition(0);
            }
        });
    }

    private List<JobModel> filter(List<JobModel> models, String query) {
        query = query.toLowerCase();

        final List<JobModel> filteredModelList = new ArrayList<>();
        for (JobModel model : models) {
            final String text = model.from_fullname.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
