package com.miami.moveforless.adapters.viewholder;

import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.customviews.DetailInfoPopupWindow;
import com.miami.moveforless.database.model.JobModel;
import com.miami.moveforless.utils.RxUtils;


/**
 * Created by SetKrul on 30.10.2015.
 */
public class ItemViewHolder extends AbstractViewHolder {

    private JobModel mJobModel;
    private TextView tvFullname;
    private TextView tvPostTitle;
    private TextView tvPhone;
    private TextView tvFromZipcode;
    private TextView tvPickupDate;
    private TextView tvRequiredPickupDate;
    private TextView tvToZipcode;
    private LinearLayout itemContainer;
    private View vBottomDivider;
    private View vVerticalDivider1;
    private View vVerticalDivider2;
    private View vVerticalDivider3;
    private View vVerticalDivider4;
    private View vVerticalDivider5;
    private View vVerticalDivider6;
    private View vVerticalDivider7;

    public ItemViewHolder(View itemView) {

        super(itemView);
        tvFullname = (TextView) itemView.findViewById(R.id.tvFullName_SI);
        tvPostTitle = (TextView) itemView.findViewById(R.id.tvPostTitle_SI);
        tvPhone = (TextView) itemView.findViewById(R.id.tvPhone_SI);
        tvFromZipcode = (TextView) itemView.findViewById(R.id.tvFromZipCode_SI);
        tvToZipcode = (TextView) itemView.findViewById(R.id.tvToZipCode_SI);
        tvPickupDate = (TextView) itemView.findViewById(R.id.tvPickupDate_SI);
        tvRequiredPickupDate = (TextView) itemView.findViewById(R.id.tvRequiredPickupTime_SI);
        itemContainer = (LinearLayout) itemView.findViewById(R.id.itemContainer_SI);
        vBottomDivider = itemView.findViewById(R.id.vBottomDivider_LIOH);
        vVerticalDivider1 = itemView.findViewById(R.id.vVerticalDivider1_SI);
        vVerticalDivider2 = itemView.findViewById(R.id.vVerticalDivider2_SI);
        vVerticalDivider3 = itemView.findViewById(R.id.vVerticalDivider3_SI);
        vVerticalDivider4 = itemView.findViewById(R.id.vVerticalDivider4_SI);
        vVerticalDivider5 = itemView.findViewById(R.id.vVerticalDivider5_SI);
        vVerticalDivider6 = itemView.findViewById(R.id.vVerticalDivider6_SI);
        vVerticalDivider7 = itemView.findViewById(R.id.vVerticalDivider7_SI);

        RxUtils.click(tvToZipcode, o -> tvToZipCodeClicked());
        RxUtils.click(tvFromZipcode, o -> tvFromZipCodeClicked());
    }

    @Override
    public void bind(JobModel model) {
        if (model.isActive == 1) {
            itemContainer.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.active_item));
            vVerticalDivider1.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.yellow));
            vVerticalDivider2.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.yellow));
            vVerticalDivider3.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.yellow));
            vVerticalDivider4.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.yellow));
            vVerticalDivider5.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.yellow));
            vVerticalDivider6.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.yellow));
            vVerticalDivider7.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.yellow));
        } else {
            itemContainer.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.blue_light));
            vVerticalDivider1.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.vertical_divider));
            vVerticalDivider2.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.vertical_divider));
            vVerticalDivider3.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.vertical_divider));
            vVerticalDivider4.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.vertical_divider));
            vVerticalDivider5.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.vertical_divider));
            vVerticalDivider6.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.vertical_divider));
            vVerticalDivider7.setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.vertical_divider));
        }
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
    public void setIconSpinner(boolean expand) {
    }

    public boolean locateView(float x) {
        int[] toZipLoc_int = new int[2];
        int[] fromZipLoc_int = new int[2];
        if (tvToZipcode == null || tvFromZipcode == null) return false;

        tvToZipcode.getLocationOnScreen(toZipLoc_int);
        tvFromZipcode.getLocationOnScreen(fromZipLoc_int);

        Rect toZipCodeLocation = new Rect(toZipLoc_int[0],
                toZipLoc_int[1],
                toZipLoc_int[0] + tvToZipcode.getWidth(),
                toZipLoc_int[1] + tvToZipcode.getHeight());

        Rect fromZipCodeLocation = new Rect(fromZipLoc_int[0],
                fromZipLoc_int[1],
                fromZipLoc_int[0] + tvFromZipcode.getWidth(),
                fromZipLoc_int[1] + tvFromZipcode.getHeight());

        return toZipCodeLocation.contains((int) x, toZipCodeLocation.centerY())
                || fromZipCodeLocation.contains((int) x, fromZipCodeLocation.centerY());
    }

    private void tvToZipCodeClicked() {
        DetailInfoPopupWindow toPopup = new DetailInfoPopupWindow(App.getAppContext(), mJobModel
                .to_city + " " +
                mJobModel.to_address);
        toPopup.show(tvToZipcode);
    }

    private void tvFromZipCodeClicked() {
        DetailInfoPopupWindow fromPopups = new DetailInfoPopupWindow(App.getAppContext(), mJobModel
                .from_city + " " +
                mJobModel.from_address);
        fromPopups.show(tvFromZipcode);
    }

    public void enableBottomDivider(boolean enable) {
        if (enable)
            vBottomDivider.setVisibility(View.VISIBLE);
    }

}
