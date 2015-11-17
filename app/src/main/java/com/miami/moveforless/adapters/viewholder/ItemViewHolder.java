package com.miami.moveforless.adapters.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
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
        tvRequiredPickupDate.setText(TimeUtil.getDate(model.RequiredPickupDate.trim(),
                Const.TIME_12_HOUR_FORMAT));
        tvPickupDate.setText(TimeUtil.getDate(model.pickup_date.trim(),
                Const.TIME_MONTH_DAY_FORMAT));
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
        LayoutInflater layoutInflater = (LayoutInflater) App.getAppContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_window, null);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        tvDetailAddress = (TextView) popupView.findViewById(R.id.tvDetailAddress_PW);

        tvDetailAddress.setText(mJobModel.from_address.trim());

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(v, Gravity.TOP | Gravity.LEFT, locateView(v).left, locateView(v).bottom);
    }
}
