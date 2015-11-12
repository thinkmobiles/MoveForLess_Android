package com.miami.moveforless.managers;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.miami.moveforless.R;

import java.util.List;

/**
 * Created by klim on 23.09.15.
 */
public final class MapManager {
    private GoogleMap mMap;

    public MapManager(final GoogleMap _map, List<LatLng> _routes) {
        mMap = _map;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnCameraChangeListener(cameraPosition -> {
            drawRoute(_routes);
            mMap.setOnCameraChangeListener(null);
        });

    }

    private MarkerOptions prepareMarker(LatLng _latLng, boolean _isMyLocation) {
        MarkerOptions marker = new MarkerOptions().position(_latLng);

        if (_isMyLocation)
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.icn_location_a));
        else marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.icn_location_b));
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
        PolylineOptions rectLine = new PolylineOptions().width(20).color(Color.BLUE);
        PolylineOptions rectLine2 = new PolylineOptions().width(10).color(Color.parseColor("#996a9b"));
        rectLine.addAll(_points);
        rectLine2.addAll(_points);
        mMap.addPolyline(rectLine);
        mMap.addPolyline(rectLine2);
        mMap.addMarker(prepareMarker(_points.get(0), true));
        mMap.addMarker(prepareMarker(_points.get(_points.size() - 1), false));

        moveCamera(_points.get(0), _points.get(_points.size() - 1));

    }

}
