package com.miami.moveforless.adapters.viewholder;

import android.view.View;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.database.model.JobModel;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class SubHeaderViewHolder extends AbstractViewHolder {

    private TextView tvHeaderJob;
    private TextView tvHeaderJobSize;

    public SubHeaderViewHolder(View itemView) {
        super(itemView);
        tvHeaderJob = (TextView) itemView.findViewById(R.id.tvHeaderJob_SSH);
        tvHeaderJobSize = (TextView) itemView.findViewById(R.id.tvHeaderJobSize_SSH);
    }

    @Override
    public void bind(JobModel model) {
        if (model != null) {
            tvHeaderJob.setText(model.title);
            tvHeaderJobSize.setText(model.getChildSize());
        }
    }


}
