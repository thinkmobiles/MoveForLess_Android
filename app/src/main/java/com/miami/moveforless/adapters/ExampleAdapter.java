package com.miami.moveforless.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miami.moveforless.R;
import com.miami.moveforless.adapters.models.ExampleModel;
import com.miami.moveforless.adapters.viewholder.AbstractViewHolder;
import com.miami.moveforless.adapters.viewholder.ConstantHolder;
import com.miami.moveforless.adapters.viewholder.HeaderViewHolder;
import com.miami.moveforless.adapters.viewholder.ItemViewHolder;
import com.miami.moveforless.adapters.viewholder.SubHeaderViewHolder;
import com.miami.moveforless.adapters.viewholder.SubItemViewHolder;
import com.miami.moveforless.database.model.JobModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class ExampleAdapter extends RecyclerView.Adapter<AbstractViewHolder> {

    private final LayoutInflater mInflater;
    private final List<JobModel> mModels;

    public ExampleAdapter(Context context, List<JobModel> models) {
        this.mInflater = LayoutInflater.from(context);
        this.mModels = models;
    }

    @Override
    public int getItemViewType(int _position) {
//        if (getItem(_position).child == null){
            return ConstantHolder.Item;
//        } else {
//            if (getItem(_position).child != null && getItem(_position).child.get(0).child == null){
//                return ConstantHolder.SubHeader;
//            } else {
//                return ConstantHolder.Header;
//            }
//        }
//        return getItem(_position).child == null ? ConstantHolder.Item :  ConstantHolder.Header;
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView;
        switch (viewType) {
            case ConstantHolder.Item:
                itemView = mInflater.inflate(R.layout.item_example, parent, false);
                return new ItemViewHolder(itemView);
            case ConstantHolder.Header:
                itemView = mInflater.inflate(R.layout.header_example, parent, false);
                return new HeaderViewHolder(itemView);
            case ConstantHolder.SubHeader:
                itemView = mInflater.inflate(R.layout.sub_header_example, parent, false);
                return new SubHeaderViewHolder(itemView);
            case ConstantHolder.SubItem:
                itemView = mInflater.inflate(R.layout.sub_item_example, parent, false);
                return new SubItemViewHolder(itemView);
            default:
                itemView = mInflater.inflate(R.layout.header_example, parent, false);
                return new HeaderViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        final JobModel jobModel = mModels.get(position);
        holder.bind(jobModel);
    }

    public JobModel getItem(int _position) {
        return mModels.get(_position);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<JobModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    public void applyAndAnimateRemovals(List<JobModel> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final JobModel model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    public void applyAndAnimateAdditions(List<JobModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final JobModel model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<JobModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final JobModel model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public JobModel removeItem(int position) {
        final JobModel model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, JobModel model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    public void addChild(int position, JobModel model) {
        for (JobModel e : model.child) {
            mModels.add(position, e);
            notifyItemInserted(position);
        }
    }

    public JobModel removeChild(int position, JobModel model) {
        for (JobModel e : model.child) {
            mModels.remove(position);
            notifyItemRemoved(position);
        }
        return model;
    }

    public void moveItem(int fromPosition, int toPosition) {
        final JobModel model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}

