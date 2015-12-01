package com.miami.moveforless.adapters.viewholder;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.database.model.JobModel;

/**
 * Created by SetKrul on 25.11.2015.
 */
public class ActiveHeaderViewHolder extends AbstractViewHolder {


    private ImageView ivSpinnerHeader;

    public ActiveHeaderViewHolder(View itemView) {
        super(itemView);
        ivSpinnerHeader = (ImageView) itemView.findViewById(R.id.ivSpinnerHeader_SAH);
    }

    @Override
    public void bind(JobModel model) {
        if (model != null) {
            setIconSpinner(model.mExpand);
        }
    }

    @Override
    public void setIconSpinner(boolean expand) {
        if (expand)
            ivSpinnerHeader.setImageDrawable(ContextCompat.getDrawable(App.getAppContext(),
                    R.drawable.icn_up_y));
        else
            ivSpinnerHeader.setImageDrawable(ContextCompat.getDrawable(App.getAppContext(),
                    R.drawable.icn_down_y));
    }
}
