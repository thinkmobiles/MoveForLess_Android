package com.miami.moveforless.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miami.moveforless.R;
import com.miami.moveforless.adapters.ExampleAdapter;
import com.miami.moveforless.adapters.models.ExampleModel;
import com.miami.moveforless.adapters.viewholder.RecyclerItemClickListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class ScheduleFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private List<ExampleModel> mModels;
    private CaldroidFragment dialogCaldroidFragment;
    private Date dateNow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.schedule_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mModels = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
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
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new
                RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(Context _context, View _view, int _position) {
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
                            final CaldroidListener listener = new CaldroidListener() {

                                @Override
                                public void onSelectDate(Date date, View view) {
//                            Toast.makeText(getActivity(), new SimpleDateFormat("dd MMM yyyy").format(date), Toast
//                                    .LENGTH_SHORT).show();
                                    dialogCaldroidFragment.clearSelectedDates();
                                    dialogCaldroidFragment.setSelectedDate(date);
                                    dialogCaldroidFragment.refreshView();
                                    dateNow = date;
//                dialogCaldroidFragment.clearDisableDates();
//                dialogCaldroidFragment.clearSelectedDates();
//                dialogCaldroidFragment.setCalendarDate(date);
//                dialogCaldroidFragment.setSelectedDates(date, date);

                                }

                                @Override
                                public void onChangeMonth(int month, int year) {
//                String text = "month: " + month + " year: " + year;
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onLongClickDate(Date date, View view) {
//                Toast.makeText(getApplicationContext(), "Long click " + formatter.format(date), Toast.LENGTH_SHORT)
//                        .show();
                                }

                                @Override
                                public void onCaldroidViewCreated() {}

                            };
//                    Toast.makeText(getActivity(), "click" + _position, Toast.LENGTH_SHORT).show();
                            dialogCaldroidFragment = new CaldroidFragment();
                            if (dateNow != null) {
                                dialogCaldroidFragment.setSelectedDate(dateNow);
                            }
                            dialogCaldroidFragment.setCaldroidListener(listener);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(CaldroidFragment.DIALOG_TITLE, "Select a date");
//                    dialogCaldroidFragment.setArguments(bundle);

                            dialogCaldroidFragment.show(getActivity().getSupportFragmentManager(), "CALDROID_DIALOG_FRAGMENT");
                        }
                    }
                }));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<ExampleModel> filteredModelList = filter(mModels, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

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
