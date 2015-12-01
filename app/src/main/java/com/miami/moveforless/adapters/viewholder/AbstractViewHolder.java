package com.miami.moveforless.adapters.viewholder;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.miami.moveforless.database.model.JobModel;

/**
 * Created by SetKrul on 30.10.2015.
 */
public abstract class AbstractViewHolder extends RecyclerView.ViewHolder {

    public AbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(JobModel model);

    public abstract void setIconSpinner(boolean expand);
}
