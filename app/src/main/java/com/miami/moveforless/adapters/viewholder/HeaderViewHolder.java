package com.miami.moveforless.adapters.viewholder;

import android.view.View;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.database.model.JobModel;

/**
 * header holder
 * Created by SetKrul on 30.10.2015.
 */
public class HeaderViewHolder extends AbstractViewHolder {

    private TextView tvHeaderJob;
    private TextView tvHeaderJobSize;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        tvHeaderJob = (TextView) itemView.findViewById(R.id.tvHeaderJob_HH);
        tvHeaderJobSize = (TextView) itemView.findViewById(R.id.tvHeaderJobSize_HH);
    }

    @Override
    public void bind(JobModel model) {
        if (model != null) {
            tvHeaderJob.setText(model.title);
            if (model.isFuture)
                tvHeaderJobSize.setText(model.getFutureChildSize());
            else
                tvHeaderJobSize.setText(model.getChildSize());
        }
    }
}