package com.miami.moveforless.adapters.viewholder;

import android.view.View;
import android.widget.TextView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.customviews.DetailAddressPopupWindow;
import com.miami.moveforless.database.model.JobModel;


/**
 * Created by SetKrul on 30.10.2015.
 */
public class ItemViewHolder extends AbstractViewHolder implements View.OnClickListener {

    private JobModel mJobModel;
    private TextView tvFullname;
    private TextView tvPostTitle;
    private TextView tvPhone;
    private TextView tvFromZipcode;
    private TextView tvPickupDate;
    private TextView tvRequiredPickupDate;
    private TextView tvToZipcode;

    public ItemViewHolder(View itemView) {
        super(itemView);
        tvFullname = (TextView) itemView.findViewById(R.id.tvFullName_SI);
        tvPostTitle = (TextView) itemView.findViewById(R.id.tvPostTitle_SI);
        tvPhone = (TextView) itemView.findViewById(R.id.tvPhone_SI);
        tvFromZipcode = (TextView) itemView.findViewById(R.id.tvFromZipCode_SI);
        tvToZipcode = (TextView) itemView.findViewById(R.id.tvToZipCode_SI);
        tvPickupDate = (TextView) itemView.findViewById(R.id.tvPickupDate_SI);
        tvRequiredPickupDate = (TextView) itemView.findViewById(R.id.tvRequiredPickupTime_SI);

        tvToZipcode.setOnClickListener(this);
        tvFromZipcode.setOnClickListener(this);
    }

    @Override
    public void bind(JobModel model) {
        mJobModel = model;
        tvFullname.setText(model.from_fullname);
        tvPostTitle.setText(model.post_title);
        tvPhone.setText(model.from_phone);
        tvFromZipcode.setText(model.from_zipcode);
        tvFullname.setText(model.from_fullname);
        tvToZipcode.setText(model.to_zipcode);

        tvRequiredPickupDate.setText(model.getRequiredPickupDate());
        tvPickupDate.setText(model.getPickup_date());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvToZipCode_SI:
                DetailAddressPopupWindow toPopup = new DetailAddressPopupWindow(App.getAppContext(), mJobModel
                        .to_city + " " +
                        mJobModel.to_address);
                toPopup.show(v);
                break;
            case R.id.tvFromZipCode_SI:
                DetailAddressPopupWindow fromPopups = new DetailAddressPopupWindow(App.getAppContext(), mJobModel
                        .from_city + " " +
                        mJobModel.from_address);
                fromPopups.show(v);
                break;
        }
    }
}
