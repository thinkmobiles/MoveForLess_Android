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

import com.miami.moveforless.R;
import com.miami.moveforless.activity.FragmentChanger;
import com.miami.moveforless.adapters.ScheduleAdapter;
import com.miami.moveforless.adapters.viewholder.AbstractViewHolder;
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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    private List<JobModel> jobModels;
    private static JobModel currentHeader;
    private JobModel futureHeader;
    private LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());

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
            Observable.just(generatedData())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(aBoolean -> Observable.just(getData()))
                    .map(this::createExpandableList)
                    .subscribe(jobModels1 -> {
                        mAdapter = new ScheduleAdapter(getActivity(), jobModels1);
                        mRecyclerView.setAdapter(mAdapter);
                    });
        }
    }

    private List<JobModel> createExpandableList(List<JobModel> models) {
        List<JobModel> sortedJobList = new ArrayList<>();

        Observable.from(models)
                .subscribe(jobModel -> {
                    if (jobModel.isActive == 1) {
                        addActiveItem(sortedJobList, jobModel);
                    } else {
                        if (sortedJobList.size() == 0) {
                            currentHeader = createHeader(jobModel, false, false);
                            sortedJobList.add(currentHeader);
                        }
                        if (jobModel.getDay() == currentHeader.getDay()) {
                            addItemIfTheSameDay(sortedJobList, jobModel);
                        } else {
                            if (TimeUtil.isFuture(jobModel.getDay())) {
                                if (!currentHeader.isFuture && futureHeader == null) {
                                    futureHeader = createHeader(jobModel, false, true);
                                    futureHeader.mExpand = true;
                                    sortedJobList.add(futureHeader);
                                }
                                addFutureItem(sortedJobList, jobModel);
                            } else {
                                addItemIfTheNotSameDay(sortedJobList, jobModel);
                            }
                        }
                    }
                });

        return sortedJobList;
    }

    private void addItemIfTheNotSameDay(List<JobModel> _sortedJobList, JobModel jobModel) {
        currentHeader = createHeader(jobModel, false, false);
        currentHeader.child.add(0, jobModel);
        _sortedJobList.add(currentHeader);
        _sortedJobList.add(jobModel);
        jobModel.mSub = true;
    }

    private void addFutureItem(List<JobModel> _sortedJobList, JobModel jobModel) {
        currentHeader = createHeader(jobModel, false, false);
        currentHeader.child.add(0, jobModel);
        futureHeader.child.add(0, currentHeader);
        _sortedJobList.add(currentHeader);
    }

    private void addItemIfTheSameDay(List<JobModel> _sortedJobList, JobModel jobModel) {
        if (jobModel.getDay() == TimeUtil.getCurrentDay()
                || jobModel.getDay() == TimeUtil.getNextDay())
            _sortedJobList.add(jobModel);
        else
            jobModel.mSub = true;

        currentHeader.child.add(0, jobModel);
    }

    private void addActiveItem(List<JobModel> _sortedJobList, JobModel jobModel) {
        currentHeader = createHeader(jobModel, true, false);
        currentHeader.child.add(jobModel);
        currentHeader.mExpand = true;
        _sortedJobList.add(currentHeader);
        _sortedJobList.add(jobModel);
    }

    private JobModel createHeader(JobModel _model, boolean _isActive, boolean _isFuture) {
        JobModel header = new JobModel();
        header.child = new ArrayList<>();
        if (_isActive) {
            header.isActive = 1;
        } else if (_isFuture) {
            header.title = "FUTURE";
            header.isFuture = true;
        } else {
            header.pickup_date = _model.pickup_date;

            if (TimeUtil.getCurrentDay() == _model.getDay()) {
                header.title = "Today " + _model.getFullDate();
                header.mExpand = true;
            } else if (TimeUtil.getNextDay() == _model.getDay()) {
                header.title = "Tomorrow " + _model.getFullDate();
                header.mExpand = true;
            } else {
                header.title = _model.getFullDate();
                header.mSub = true;
            }
        }
        return header;
    }

    private List<JobModel> getData() {
        jobModels = DatabaseController.getInstance().getListJob();

        return jobModels;
    }

    private boolean generatedData() {
        Delete.tables(JobResponse.class);

        JobResponse jobModel = new JobResponse();
        jobModel.isActive = 1;
        jobModel.from_fullname = "Lesley";
        jobModel.from_address = "from some Citysssssssssssssssssss";
        jobModel.to_address = "to some Citysssssssssssssssssssssss";
        jobModel.from_city = "From some Citysssssssssssssssssssssss";
        jobModel.to_city = "To some Citysssssssssssssssssssssssssss";
        jobModel.from_phone = "783-223-5111";
        jobModel.from_zipcode = "31522";
        jobModel.to_zipcode = "33525";
        jobModel.pickup_date = "1441835191";
        jobModel.status_slug = "assign";
        jobModel.post_title = "LD025135";
        jobModel.RequiredPickupDate = "1449885191";
        jobModel.save();

        for (int i = 0; i < 2; i++) {
            JobResponse jobModelToday = new JobResponse();
            jobModelToday.isActive = 0;
            jobModelToday.from_fullname = "Dominique " + i;
            jobModelToday.from_address = "from some Cityssssssssssssssssssssss";
            jobModelToday.to_address = "to some Citysssssssssssssssssssssssss";
            jobModelToday.from_city = "From some Citysssssssssssssssssssssssss";
            jobModelToday.to_city = "To some Cityssssssssssssssssssssssssssss";
            jobModelToday.from_phone = "326-562-891" + i;
            jobModelToday.from_zipcode = "31522";
            jobModelToday.to_zipcode = "33525";
            jobModelToday.pickup_date = "1448921735";
            jobModelToday.status_slug = "assign";
            jobModelToday.post_title = "LD02513" + i;
            jobModelToday.RequiredPickupDate = "1448835191";
            jobModelToday.save();
        }

        for (int i = 0; i < 2; i++) {
            JobResponse jobModelFuture = new JobResponse();
            jobModelFuture.isActive = 0;
            jobModelFuture.from_fullname = "Gav " + i;
            jobModelFuture.from_address = "from some Cityssssssssssssssssssssssssssssssssssssssssssssssssssssss";
            jobModelFuture.to_address = "to some Cityssssssssssssssssssssssssssssssssssssssssssssssssssssss";
            jobModelFuture.from_city = "From some Cityssssssssssssssssssssssssssssssssssssssssssssssssssssss";
            jobModelFuture.to_city = "To some Cityssssssssssssssssssssssssssssssssssssssssssssssssssssss";
            jobModelFuture.from_phone = "456-312-8945" + i;
            jobModelFuture.from_zipcode = "31522";
            jobModelFuture.to_zipcode = "33525";
            jobModelFuture.pickup_date = "1448985191";
            jobModelFuture.status_slug = "assign";
            jobModelFuture.post_title = "LD02525" + i;
            jobModelFuture.RequiredPickupDate = "1448885191";
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
            jobModelFuture2.pickup_date = "1449885191";
            jobModelFuture2.status_slug = "assign";
            jobModelFuture2.post_title = "LD02535" + i;
            jobModelFuture2.RequiredPickupDate = "1449885191";
            jobModelFuture2.save();
        }

        for (int i = 0; i < 3; i++) {
            JobResponse jobModelFuture2 = new JobResponse();
            jobModelFuture2.isActive = 0;
            jobModelFuture2.from_fullname = "Mart " + i;
            jobModelFuture2.from_address = "from some address";
            jobModelFuture2.to_address = "to some address";
            jobModelFuture2.from_city = "From some City";
            jobModelFuture2.to_city = "To some City";
            jobModelFuture2.from_phone = "456-312-8945" + i;
            jobModelFuture2.from_zipcode = "31522";
            jobModelFuture2.to_zipcode = "33525";
            jobModelFuture2.pickup_date = "1459885191";
            jobModelFuture2.status_slug = "assign";
            jobModelFuture2.post_title = "LD02535" + i;
            jobModelFuture2.RequiredPickupDate = "1449885191";
            jobModelFuture2.save();
        }

        for (int i = 0; i < 2; i++) {
            JobResponse jobModelFuture2 = new JobResponse();
            jobModelFuture2.isActive = 0;
            jobModelFuture2.from_fullname = "Mart " + i;
            jobModelFuture2.from_address = "from some address";
            jobModelFuture2.to_address = "to some address";
            jobModelFuture2.from_city = "From some City";
            jobModelFuture2.to_city = "To some City";
            jobModelFuture2.from_phone = "456-312-8945" + i;
            jobModelFuture2.from_zipcode = "31522";
            jobModelFuture2.to_zipcode = "33525";
            jobModelFuture2.pickup_date = "1559885191";
            jobModelFuture2.status_slug = "assign";
            jobModelFuture2.post_title = "LD02535" + i;
            jobModelFuture2.RequiredPickupDate = "1449885191";
            jobModelFuture2.save();
        }

        for (int i = 0; i < 1; i++) {
            JobResponse jobModelFuture2 = new JobResponse();
            jobModelFuture2.isActive = 0;
            jobModelFuture2.from_fullname = "Mart " + i;
            jobModelFuture2.from_address = "from some address";
            jobModelFuture2.to_address = "to some address";
            jobModelFuture2.from_city = "From some City";
            jobModelFuture2.to_city = "To some City";
            jobModelFuture2.from_phone = "456-312-8945" + i;
            jobModelFuture2.from_zipcode = "31522";
            jobModelFuture2.to_zipcode = "33525";
            jobModelFuture2.pickup_date = "1449989191";
            jobModelFuture2.status_slug = "assign";
            jobModelFuture2.post_title = "LD02535" + i;
            jobModelFuture2.RequiredPickupDate = "1449885191";
            jobModelFuture2.save();
        }

        return true;
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
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                (_context, _view, _position, _viewHolder) -> {
                    if (mAdapter.getItem(_position).child != null) {
                        if (!mAdapter.getItem(_position).mExpand) {
                            mAdapter.getItem(_position).mExpand = true;
                            mAdapter.addChild(_position + 1, mAdapter.getItem(_position));
                            ((AbstractViewHolder) _viewHolder).setIconSpinner(R.drawable.ic_spinner_up);
                            scrollRecyclerView(_position);
                        } else {
                            mAdapter.getItem(_position).mExpand = false;
                            for (int i = 0; i < mAdapter.getItem(_position).child.size(); i++) {
                                if (mAdapter.getItem(_position).child.get(i).mExpand) {
                                    mAdapter.getItem(_position).child.get(i).mExpand = false;
                                    mAdapter.removeChild(_position + i + 1, mAdapter.getItem(_position).child.get(i));
                                }
                            }
                            mAdapter.removeChild(_position + 1, mAdapter.getItem(_position));
                            ((AbstractViewHolder) _viewHolder).setIconSpinner(R.drawable.ic_spinner_down);

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

    private void scrollRecyclerView(int position) {
        int lastItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        int sizeChild = mAdapter.getItem(position).child.size();
        if (sizeChild + position > lastItem)
            mRecyclerView.scrollToPosition(position + sizeChild);
    }
}
