package com.miami.moveforless.fragments;

import android.widget.Button;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.FragmentType;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;

/**
 * Created by klim on 28.10.15.
 */
public class CongratulationFragment extends BaseJobDetailFragment {
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

    @Override
    protected boolean isAllowGoHome() {
        return true;
    }

    private void btnBackClicked() {
        //TODO implement back to list job screen
        BusProvider.getInstance().post(new SwitchJobDetailsEvent(FragmentType.SCHEDULE));
    }

}
