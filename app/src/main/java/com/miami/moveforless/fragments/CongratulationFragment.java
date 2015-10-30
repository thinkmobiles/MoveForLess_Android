package com.miami.moveforless.fragments;

import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;

/**
 * Created by klim on 28.10.15.
 */
public class CongratulationFragment extends BaseFragment {
    @Bind(R.id.btnBack_FC)      Button btnBack;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_congratulations;
    }

    public static CongratulationFragment newInstance() {
        return new CongratulationFragment();
    }


    @Override
    protected void setupViews() {
        RxUtils.click(btnBack, o -> btnBackClicked());
    }

    private void btnBackClicked() {
        //TODO implement back to list job screen
        Toast.makeText(getActivity(), "Back to list job", Toast.LENGTH_SHORT).show();
    }

}
