package com.miami.moveforless.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.managers.MapManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.rest.response.RouteInfo;
import com.miami.moveforless.utils.RxUtils;
import com.miami.moveforless.utils.ScreenUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import rx.Subscription;

/**
 * route dialog
 * Created by klim on 02.11.15.
 */
public class RouteDialog extends BaseDialog {
    private static final String TAG = "RouteDialog";

    @BindString(R.string.close)
    String strBtnNegativeTitle;
    @BindColor(R.color.cyan_dark)
    int loadingColor;
    @Bind(R.id.pbLoading_DR)
    ProgressBar pbLoading;
    @Bind(R.id.tvEndAddress_DR)
    TextView tvDuration;
    @Bind(R.id.tvStartAddress_DR)
    TextView tvDistance;
    @Bind(R.id.map_container_DR)
    FrameLayout mMapContainer;
    @Bind(R.id.btnNegative_DR)
    TextView btnClose;

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_route;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        View view = super.onCreateView(_inflater, _container, _savedInstanceState);
        final int height = ScreenUtils.getScreenHeight(App.getAppContext());
        mMapContainer.getLayoutParams().height = (int) (height * 0.6);
        mMapContainer.getLayoutParams().width = (int) (height * 0.8);

        return view;
    }

    @Override
    protected void setupViews() {
        btnClose.setText(strBtnNegativeTitle);
        RxUtils.click(btnClose).subscribe(o -> dismiss());

        setCancelable(true);

        pbLoading.getIndeterminateDrawable().setColorFilter(loadingColor,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        pbLoading.setVisibility(View.VISIBLE);

        FragmentManager fm = getChildFragmentManager();
        mMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container_DR);
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container_DR, mMapFragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap == null) {
            mMap = mMapFragment.getMap();
        }
        tryToShowRoute();
    }

    private void tryToShowRoute() {

        addSubscription(RestClient.getInstance().getRoute("Uzhhorod", "Kiev")
                .subscribe(this::showRoute, this::onError));
    }

    private void addMap(List<LatLng> _points) {
        Log.d(TAG, "add map");
        getChildFragmentManager().beginTransaction().show(mMapFragment).commit();
        new MapManager(mMap, _points);

    }

    private void showRoute(RouteInfo _routeInfo) {
        tvDuration.setText(_routeInfo.getDuration());
        tvDistance.setText(_routeInfo.getDistance());
        addMap(_routeInfo.getDirectionPoints());
        pbLoading.setVisibility(View.GONE);
    }

    private void onError(Throwable _throwable) {
        pbLoading.setVisibility(View.GONE);
        Toast.makeText(App.getAppContext(), ErrorParser.parseRouteError(_throwable), Toast.LENGTH_SHORT).show();
    }

}
