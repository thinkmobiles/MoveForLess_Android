package com.miami.moveforless.adapters.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.utils.TimeUtil;

/**
 * header holder
 * Created by SetKrul on 30.10.2015.
 */
public class HeaderViewHolder extends AbstractViewHolder {

    private  TextView tvHeaderJob;
    private  TextView tvHeaderJobSize;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        tvHeaderJob = (TextView) itemView.findViewById(R.id.tvHeaderJob_HH);
        tvHeaderJobSize = (TextView) itemView.findViewById(R.id.tvHeaderJobSize_HH);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bind(JobModel model) {
        if (model != null) {
//            if (TimeUtil.getCurrentDay() == model.getDay())
//                tvHeaderJob.setText("Today " + model.getFullDate());
//            else if (TimeUtil.getNextDay() == model.getDay())
//                tvHeaderJob.setText("Tomorrow " + model.getFullDate());
//            tvHeaderJobSize.setText(model.getChildSize());
        }
    }
}