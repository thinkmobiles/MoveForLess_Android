package com.miami.moveforless.dialogs;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.managers.MapManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.rest.response.RouteInfo;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import rx.Subscription;

/**
 * Created by klim on 02.11.15.
 */
public class RouteDialog extends BaseDialog {
    @BindString(R.string.close)
    String strBtnNegativeTitle;
    @BindColor(R.color.cyan_800)
    int loadingColor;
    @Bind(R.id.pbLoading_DR)
    ProgressBar pbLoading;
    @Bind(R.id.tvDuration_DR)
    TextView tvDuration;
    @Bind(R.id.tvDistance_DR)
    TextView tvDistance;

    private GoogleMap mMap;
    private MapManager mMapManager;
    private Subscription mLastLocation;
    private SupportMapFragment mMapFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_route;
    }

    @Override
    protected void setupViews() {
        setNegativeTitle(strBtnNegativeTitle);
        setCancelable(true);

        if (mMap == null) {
            mMapFragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_DR));
            mMap = mMapFragment.getMap();
            mMapManager = new MapManager(mMap);
        }

        pbLoading.getIndeterminateDrawable().setColorFilter(loadingColor,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        pbLoading.setVisibility(View.VISIBLE);
        tryToShowRoute();
    }

    private void tryToShowRoute() {
        if (mLastLocation != null) removeSubscription(mLastLocation);
        mLastLocation = RestClient.getInstance().getRoute("Uzhorod", "Kiev")
                .subscribe(routeInfo -> showRoute(routeInfo), throwable -> onError(throwable));
        addSubscription(mLastLocation);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SupportMapFragment f = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_DR);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }

    private void showRoute(RouteInfo _routeInfo) {
        tvDuration.setText(_routeInfo.getDuration());
        tvDistance.setText(_routeInfo.getDistance());
        mMapFragment.getView().setVisibility(View.VISIBLE);
        mMapManager.showRoute(_routeInfo.getDirectionPoints());
        pbLoading.setVisibility(View.GONE);
    }

    private void onError(Throwable _throwable) {
        pbLoading.setVisibility(View.GONE);
        Toast.makeText(App.getAppContext(), ErrorParser.parseRouteError(_throwable), Toast.LENGTH_SHORT).show();
    }

}
