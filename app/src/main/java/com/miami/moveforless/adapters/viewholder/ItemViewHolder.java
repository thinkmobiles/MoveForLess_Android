package com.miami.moveforless.adapters.viewholder;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.customviews.DetailAddressPopupWindow;
import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.TimeUtil;


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
    private TextView tvDetailAddress;

    public ItemViewHolder(View itemView) {
        super(itemView);
        tvFullname = (TextView) itemView.findViewById(R.id.tvFullname_IE);
        tvPostTitle = (TextView) itemView.findViewById(R.id.tvPostTitle_IE);
        tvPhone = (TextView) itemView.findViewById(R.id.tvPhone_IE);
        tvFromZipcode = (TextView) itemView.findViewById(R.id.tvFromZipcode_IE);
        tvToZipcode = (TextView) itemView.findViewById(R.id.tvToZipcode_IE);
        tvPickupDate = (TextView) itemView.findViewById(R.id.tvPickupDate_IE);
        tvRequiredPickupDate = (TextView) itemView.findViewById(R.id.tvRequiredPickupDate_IE);

        tvToZipcode.setOnClickListener(this);
        tvFromZipcode.setOnClickListener(this);
    }

    @Override
    public void bind(JobModel model) {
        mJobModel = model;
        tvFullname.setText(model.from_fullname.trim());
        tvPostTitle.setText(model.post_title.trim());
        tvPhone.setText(model.from_phone.trim());
        tvFromZipcode.setText(model.from_zipcode.trim());
        tvFullname.setText(model.from_fullname.trim());
        tvToZipcode.setText(model.to_zipcode.trim());
        tvRequiredPickupDate.setText(TimeUtil.getDate(model.RequiredPickupDate.trim(), Const
                .TIME_12_HOUR_FORMAT));
        tvPickupDate.setText(TimeUtil.getDate(model.pickup_date.trim(), Const
                .TIME_MONTH_DAY_FORMAT));
    }


    private Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight() + 30;

        return location;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvToZipcode_IE:
                DetailAddressPopupWindow toPopup = new DetailAddressPopupWindow(App.getAppContext(), mJobModel.to_city + " " +
                        mJobModel.to_address);
                toPopup.show(v);
                break;
            case R.id.tvFromZipcode_IE:
                DetailAddressPopupWindow fromPopups = new DetailAddressPopupWindow(App.getAppContext(), mJobModel.from_city + " " +
                        mJobModel.from_address);
                fromPopups.show(v);
                break;
        }
    }
}
