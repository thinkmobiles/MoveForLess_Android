package com.miami.moveforless.adapters.viewholder;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.database.model.JobModel;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class SubHeaderViewHolder extends AbstractViewHolder {

    private TextView tvHeaderJob;
    private TextView tvHeaderJobSize;
    private ImageView ivSpinnerHeader;

    public SubHeaderViewHolder(View itemView) {
        super(itemView);
        tvHeaderJob = (TextView) itemView.findViewById(R.id.tvHeaderJob_SSH);
        tvHeaderJobSize = (TextView) itemView.findViewById(R.id.tvHeaderJobSize_SSH);
        ivSpinnerHeader = (ImageView) itemView.findViewById(R.id.ivSpinnerHeader_SSH);
    }

    @Override
    public void bind(JobModel model) {
        if (model != null) {

            setIconSpinner(model.mExpand);
            tvHeaderJob.setText(model.title);
            tvHeaderJobSize.setText(model.getChildSize());
        }
    }

    @Override
    public void setIconSpinner(boolean expand) {
        if (expand)
            ivSpinnerHeader.setImageDrawable(ContextCompat.getDrawable(App.getAppContext(),
                    R.drawable.ic_spinner_up));
        else
            ivSpinnerHeader.setImageDrawable(ContextCompat.getDrawable(App.getAppContext(),
                    R.drawable.ic_spinner_down));
    }

}
