package com.miami.moveforless.managers;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.miami.moveforless.R;
import com.miami.moveforless.globalconstants.RouteConst;
import com.miami.moveforless.location.TrackingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by klim on 23.09.15.
 */
public final class MapManager implements GoogleMap.OnMapLoadedCallback{
    private GoogleMap mMap;
    private boolean isMapReady = false;
    private List<LatLng> mRoutes;

    public MapManager(final GoogleMap _map) {
        mMap = _map;
        mMap.setOnMapLoadedCallback(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        drawDefaultLocation();
    }


    private MarkerOptions prepareMarker(LatLng _latLng, boolean _isMyLocation) {
        MarkerOptions marker = new MarkerOptions().position(_latLng);

        if (_isMyLocation)
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_a));
        else marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_b));
        return marker;
    }

    private void moveCamera(LatLng _startPoint, LatLng _endPoint) {

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        boundsBuilder.include(_startPoint);
        boundsBuilder.include(_endPoint);

        LatLngBounds bounds = boundsBuilder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
    }

    private void drawRoute(List<LatLng> _points) {
        PolylineOptions rectLine = new PolylineOptions().width(8).color(Color.BLUE);
        rectLine.addAll(_points);
        mMap.addPolyline(rectLine);

        mMap.addMarker(prepareMarker(_points.get(0), true));
        mMap.addMarker(prepareMarker(_points.get(_points.size() - 1), false));

        moveCamera(_points.get(0), _points.get(_points.size() - 1));

    }

    public final void showRoute(List<LatLng> _points) {
        if (isMapReady) {
            drawRoute(_points);
        } else {
            mRoutes = _points;
        }
    }

    private void drawDefaultLocation() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(RouteConst.DEFAULT_LOCATION)
                .zoom(13)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onMapLoaded() {
        isMapReady = true;
        if (mRoutes != null) {
            drawRoute(mRoutes);
            mRoutes = null;
        } else {

        }
    }
}
