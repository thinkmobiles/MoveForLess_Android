package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.activity.FragmentChanger;
import com.miami.moveforless.adapters.ExampleAdapter;
import com.miami.moveforless.adapters.models.ExampleModel;
import com.miami.moveforless.adapters.viewholder.RecyclerItemClickListener;
import com.miami.moveforless.database.DatabaseController;
import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.rest.response.JobResponse;
import com.miami.moveforless.utils.TimeUtil;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import butterknife.BindString;
import rx.Observable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class ScheduleFragment extends BaseFragment implements View.OnClickListener {

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @BindString(R.string.loading) String strLoading;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.etSearch_FS)
    EditText etSearch;
    @Bind(R.id.tvBegin_FS)
    TextView tvBegin;
    @Bind(R.id.tvEnd_FS)
    TextView tvEnd;
    private ExampleAdapter mAdapter;
    private List<ExampleModel> mModels;
    private CaldroidFragment dialogCaldroidFragment;
    private Date dateNow;
    private CaldroidListener listener;
    private List<JobResponse> jobResponses;

    private List<JobModel> jobModels;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        setHasOptionsMenu(true);
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);

        jobModels = DatabaseController.getInstance().getListJob();
//        mAdapter = new ExampleAdapter(getActivity(), jobModels);
//        mRecyclerView.setAdapter(mAdapter);

    private void getData() {
        showLoadingDialog(strLoading);
        if (mJobDataSubscription != null) removeSubscription(mJobDataSubscription);
        mJobDataSubscription = Observable.combineLatest(
                RestClient.getInstance().jobList(),
                RestClient.getInstance().getListNumberMen(),
                RestClient.getInstance().getListMoveSize(),
                (jobResponses1, listNumberMenResponse, listMoveSizeResponse) ->{
                    DatabaseController.getInstance().dropDataBase(App.getAppContext());
                    saveInDatabase(jobResponses1);
                    saveInDatabase(listNumberMenResponse.number_men);
                    saveInDatabase(listMoveSizeResponse.move_sizes);

                    jobModels = DatabaseController.getInstance().getListJob();
                    mAdapter = new ExampleAdapter(getActivity(), jobModels);
                    mRecyclerView.setAdapter(mAdapter);
                        return jobResponses1 != null && listNumberMenResponse != null && listMoveSizeResponse != null;
                })
                .subscribe(this::onJobDataSuccess, this::onError);
        addSubscription(mJobDataSubscription);
    }

    private void saveInDatabase(List<? extends BaseModel> list) {
        for (BaseModel item : list) {
            item.save();
        }
    }

    private void onJobDataSuccess(boolean _isloaded) {
        hideLoadingDialog();
    }

    private void onError(Throwable _throwable) {
        hideLoadingDialog();
        showErrorDialog(ErrorParser.parse(_throwable));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

        dialogCaldroidFragment.show(getActivity().getSupportFragmentManager(),
                "CALDROID_DIALOG_FRAGMENT");
        return "";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setHasOptionsMenu(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        jobModels = DatabaseController.getInstance().getListJob();

//        mModels = new ArrayList<>();

//        for (int i = 0; i < 5; i++) {
//            List<ExampleModel> child = new ArrayList<>();
//            List<ExampleModel> child2 = new ArrayList<>();
//            child2.add(new ExampleModel("Text child2 " + i, false, null));
//            child2.add(new ExampleModel("Text child2 " + i, false, null));
//            child2.add(new ExampleModel("Text child2 " + i, false, null));
//            child2.add(new ExampleModel("Text child2 " + i, false, null));
//            child2.add(new ExampleModel("Text child2 " + i, false, null));
//            child2.add(new ExampleModel("Text child2 " + i, false, null));
//            child.add(new ExampleModel("Text child with child " + i, false, child2));
//            child.add(new ExampleModel("Text child " + i, false, null));
//            child.add(new ExampleModel("Text child with child " + i, false, child2));
//            child.add(new ExampleModel("Text child " + i, false, null));
//            child.add(new ExampleModel("Text child with child " + i, false, child2));
//            mModels.add(new ExampleModel("Header " + i, false, child));
//        }
//        mAdapter = new ExampleAdapter(getActivity(), jobModels);
//        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity()
                .getApplicationContext(), (_context, _view, _position) -> {
            if (mAdapter.getItem(_position).child != null) {
                if (!mAdapter.getItem(_position).mExpand) {
                    mAdapter.getItem(_position).mExpand = true;
                    mAdapter.addChild(_position + 1, mAdapter.getItem(_position));
                } else {
                    mAdapter.getItem(_position).mExpand = false;
                    for (int i = 0; i < mAdapter.getItem(_position).child.size(); i++) {
                        if (mAdapter.getItem(_position).child.get(i).mExpand) {
                            mAdapter.getItem(_position).child.get(i).mExpand = false;
                            mAdapter.removeChild(_position + i + 1, mAdapter.getItem(_position)
                                    .child.get(i));
                        }
                    }
                    mAdapter.removeChild(_position + 1, mAdapter.getItem(_position));
                }
            } else {
                if (getActivity() instanceof FragmentChanger) {
                    ((FragmentChanger) getActivity()).switchFragment(JobFragment.newInstance(0,
                            null), true);
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
                final List<JobModel> filteredModelList = filter(jobModels, s.toString());
                mAdapter.animateTo(filteredModelList);
                mRecyclerView.scrollToPosition(0);
            }
        });
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
////        inflater.inflate(R.menu.menu_main, menu);
//
//        final MenuItem item = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//
//    }

//    @Override
//    public boolean onQueryTextChange(String query) {
//        final List<ExampleModel> filteredModelList = filter(mModels, query);
//        mAdapter.animateTo(filteredModelList);
//        mRecyclerView.scrollToPosition(0);
//        return true;
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }

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
