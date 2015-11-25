package com.miami.moveforless.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by klim on 28.10.15.
 */
public class CongratulationFragment extends BaseJobDetailFragment {
    @BindString(R.string.congratulation_text)   String strCongratulationsFeedback;
    @BindString(R.string.invoice_successfull)   String strCongratulationsInvoice;
    @Bind(R.id.btnBack_FC)              Button btnBack;
    @Bind(R.id.btnReview_FC)            Button btnReview;
    @Bind(R.id.tvMessage_FC)            TextView tvMessage;

    private String mMessage;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_congratulations;
    }

    public static CongratulationFragment newInstance() {
        return new CongratulationFragment();
    }


    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        mMessage = strCongratulationsInvoice;
        btnReview.setVisibility(View.VISIBLE);
        RxUtils.click(btnBack, o -> btnBackClicked());
        RxUtils.click(btnReview, o -> btnReviewClicked());
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMessage.setText(mMessage);
    }

    @Override
    protected boolean isAllowGoHome() {
        return true;
    }

    @Override
    public void onPageSelected() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.FEEDBACK_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                tvMessage.setText(strCongratulationsFeedback);
                btnReview.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void btnBackClicked() {
        getActivity().onBackPressed();
    }

    private void btnReviewClicked() {
        BusProvider.getInstance().post(new SwitchJobDetailsEvent());
    }

}
