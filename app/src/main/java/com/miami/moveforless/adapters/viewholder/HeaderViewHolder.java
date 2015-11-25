package com.miami.moveforless.adapters.viewholder;

import android.view.View;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.utils.TimeUtil;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class HeaderViewHolder extends AbstractViewHolder {

    private  TextView tvHeaderJob;
    private  TextView tvHeaderJobSize;
    private TextView tvActiveFullname;
    private TextView tvActivePostTitle;
    private TextView tvActivePhone;
    private TextView tvActiveFromZipcode;
    private TextView tvActivePickupDate;
    private TextView tvActiveRequiredPickupDate;
    private TextView tvActiveToZipcode;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        tvHeaderJob = (TextView) itemView.findViewById(R.id.tvHeaderJob_HH);
        tvHeaderJobSize = (TextView) itemView.findViewById(R.id.tvHeaderJobSize_HH);
//        tvActiveFullname = (TextView) itemView.findViewById(R.id.tvActiveFullname_HH);
//        tvActivePostTitle = (TextView) itemView.findViewById(R.id.tvActivePostTitle_HH);
//        tvActivePhone = (TextView) itemView.findViewById(R.id.tvActivePhone_HH);
//        tvActiveFromZipcode = (TextView) itemView.findViewById(R.id.tvActiveFromZipcode_HH);
//        tvActivePickupDate = (TextView) itemView.findViewById(R.id.tvActivePickupDate_HH);
//        tvActiveRequiredPickupDate = (TextView) itemView.findViewById(R.id.tvActiveRequiredPickupDate_HH);
//        tvActiveToZipcode = (TextView) itemView.findViewById(R.id.tvActiveToZipcode_HH);
    }

    @Override
    public void bind(JobModel model) {
        if (TimeUtil.getCurrentDay() == model.getDay())
            tvHeaderJob.setText("Today " + model.getFullDate());
        else if (TimeUtil.getNextDay() == model.getDay())
            tvHeaderJob.setText("Tomorrow " + model.getFullDate());
        int size = model.size;
        tvHeaderJobSize.setText("(" + size + ")");

//        tvActiveFullname.setText(model.from_fullname.trim());
//        tvActivePostTitle.setText(model.post_title.trim());
//        tvActivePhone.setText(model.from_phone.trim());
//        tvActiveFromZipcode.setText(model.from_zipcode.trim());
//        tvActiveFullname.setText(model.from_fullname.trim());
//        tvActiveToZipcode.setText(model.to_zipcode.trim());

//        tvActiveRequiredPickupDate.setText(model.getRequiredPickupDate());
//        tvActivePickupDate.setText(model.getPickup_date());
    }


}