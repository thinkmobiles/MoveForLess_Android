package com.miami.moveforless.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.miami.moveforless.rest.response.ListMoveSizeResponse;
import com.miami.moveforless.rest.response.ListNumberMenResponse;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class ScheduleFragment extends BaseFragment implements View.OnClickListener {

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

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
    private Subscription jobListSubscription;
    private Subscription numberListSubscription;
    private Subscription moveListSubscription;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private ProgressDialog progressDialog;
    private List<JobResponse> jobResponses;

    protected void addSubscription(Subscription _subscription) {
        mSubscriptions.add(_subscription);
    }

    protected void removeSubscription(Subscription _subscription) {
        mSubscriptions.remove(_subscription);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void setupViews() {
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        //RxUtils.click(btnLogin, o -> login());
//        DatabaseController.getInstance().dropDataBase(getActivity());
        getData();
    }

    private void getData() {
        showLoadingDialog(getString(R.string.login));
        if (jobListSubscription != null) removeSubscription(jobListSubscription);
        jobListSubscription = RestClient.getInstance().jobList().subscribe(this::onSuccess,
                this::onError);
        addSubscription(jobListSubscription);

        if (numberListSubscription != null) removeSubscription(numberListSubscription);
        numberListSubscription = RestClient.getInstance().getListNumberMen().subscribe
                (this::onSuccesss, this::onError);
        addSubscription(numberListSubscription);

        if (moveListSubscription != null) removeSubscription(moveListSubscription);

        moveListSubscription = RestClient.getInstance().getListMoveSize().subscribe
                (this::onListMoveSuccess, this::onError);
        addSubscription(moveListSubscription);

    }


    private void onListMoveSuccess(ListMoveSizeResponse _listMoveSizeResponse) {
        for (int i = 0; i < _listMoveSizeResponse.move_sizes.size(); i++) {
            _listMoveSizeResponse.move_sizes.get(i).save();
        }

        moveListSubscription.unsubscribe();
        hideLoadingDialog();
    }

    private void onSuccesss(ListNumberMenResponse _listNumberMen) {

        for (int i = 0; i < _listNumberMen.number_men.size(); i++) {
            _listNumberMen.number_men.get(i).save();
        }

        numberListSubscription.unsubscribe();
        hideLoadingDialog();
    }

    private void onSuccess(List<JobResponse> _jobResponses) {
        jobResponses = _jobResponses;
        //view and write to bd

        for (int i = 0; i < _jobResponses.size(); i++) {
            _jobResponses.get(i).save();
        }

        jobListSubscription.unsubscribe();
        hideLoadingDialog();

//        List<JobModel> jobModels = DatabaseController.getInstance().getListJob();
//        jobModels.size();
//        DatabaseController.getInstance().dropDataBase(getActivity());
    }

    private void onError(Throwable _throwable) {
        hideLoadingDialog();
        Toast.makeText(getActivity(), ErrorParser.parse(_throwable), Toast.LENGTH_SHORT).show();
    }

    protected void showLoadingDialog(String _message) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(_message);
        progressDialog.show();

    }

    protected void hideLoadingDialog() {
        if (jobListSubscription.isUnsubscribed() && numberListSubscription.isUnsubscribed() &&
                moveListSubscription.isUnsubscribed())
            if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
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

//        List<JobModel> jobModels = DatabaseController.getInstance().getListJob();
        mModels = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            List<ExampleModel> child = new ArrayList<>();
            List<ExampleModel> child2 = new ArrayList<>();
            child2.add(new ExampleModel("Text child2 " + i, false, null));
            child2.add(new ExampleModel("Text child2 " + i, false, null));
            child2.add(new ExampleModel("Text child2 " + i, false, null));
            child2.add(new ExampleModel("Text child2 " + i, false, null));
            child2.add(new ExampleModel("Text child2 " + i, false, null));
            child2.add(new ExampleModel("Text child2 " + i, false, null));
            child.add(new ExampleModel("Text child with child " + i, false, child2));
            child.add(new ExampleModel("Text child " + i, false, null));
            child.add(new ExampleModel("Text child with child " + i, false, child2));
            child.add(new ExampleModel("Text child " + i, false, null));
            child.add(new ExampleModel("Text child with child " + i, false, child2));
            mModels.add(new ExampleModel("Header " + i, false, child));
        }
        mAdapter = new ExampleAdapter(getActivity(), mModels);
        mRecyclerView.setAdapter(mAdapter);
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
                            jobResponses.get(0)), true);
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
                final List<ExampleModel> filteredModelList = filter(mModels, s.toString());
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

    private List<ExampleModel> filter(List<ExampleModel> models, String query) {
        query = query.toLowerCase();

        final List<ExampleModel> filteredModelList = new ArrayList<>();
        for (ExampleModel model : models) {
            final String text = model.mText.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
