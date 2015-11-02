package com.miami.moveforless.dialogs;

import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.location.TrackingService;
import com.miami.moveforless.managers.MapManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClientApi;
import com.miami.moveforless.rest.response.RouteInfo;

import butterknife.BindString;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klim on 02.11.15.
 */
public class RouteDialog extends BaseDialog implements TrackingService.OnConnectedListener{
    @BindString(R.string.close)
    String btnNegativeTitle;

    private GoogleMap mMap;
    private MapManager mMapManager;
    private TrackingService mTrackingService;
    private Subscription mLastLocation;
    private Subscription mRouteInfo;

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_route;
    }

    @Override
    protected void setupViews() {
        setNegativeTitle(btnNegativeTitle);
        setCancelable(true);

        if (mMap == null) {
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_DR))
                    .getMap();
            mMapManager = new MapManager(mMap);
        }

        mTrackingService = new TrackingService(getActivity());
        mTrackingService.setOnConnectedListener(this);

    }

    @Override
    public void onConnected() {
        if (mLastLocation != null) removeSubscription(mLastLocation);
        mLastLocation = mTrackingService.getLocationObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(location1 -> RestClientApi.getInstance().getRoute(location1, "Seattle"))
                .subscribe(routeInfo -> showRoute(routeInfo), throwable -> onError(throwable));
        addSubscription(mLastLocation);

    }


    @Override
    public void onPause() {
        super.onPause();
        mTrackingService.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTrackingService.connect();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SupportMapFragment f = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_DR);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }

    private void showRoute(RouteInfo _routeInfo) {
        mMapManager.showRoute(_routeInfo.getDirectionPoints());
    }

    private void onError(Throwable _throwable) {
        Toast.makeText(App.getAppContext(), ErrorParser.parseRouteError(_throwable), Toast.LENGTH_SHORT).show();
    }

}
