package com.miami.moveforless.managers;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.miami.moveforless.R;
import com.miami.moveforless.globalconstants.RouteConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by klim on 23.09.15.
 */
public final class MapManager {
    private GoogleMap mMap;
    private List<Circle> circleList = new ArrayList();
    private Marker mStartMarker, mEndMarker;
    private Polyline mRoutePolyline;

    public MapManager(final GoogleMap _map) {
        mMap = _map;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
    }

    public final void drawCircle(final LatLng _position) {
        double radiusInMeters = 100.0;
        int strokeColor = 0xffff0000;
        int shadeColor = 0x44ff0000;

        final CircleOptions circleOptions = new CircleOptions().center(_position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);

        Circle circle = mMap.addCircle(circleOptions);
        circleList.add(circle);
    }

    private MarkerOptions prepareMarker(Location _location, boolean _isMyLocation) {
        final LatLng myLocation = new LatLng(_location.getLatitude(), _location.getLongitude());

        MarkerOptions marker = new MarkerOptions().position(myLocation);
        marker.title("Latitude: " + _location.getLatitude() + ", Longitude: " + _location.getLongitude());
        marker.snippet("Altitude: " + String.format("%.2f", _location.getAltitude()) + ", Accuracy: " + _location.getAccuracy());

        if (_isMyLocation)
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_a));
        return marker;
    }

    private void moveCamera(Location _location) {
        float currentZoomLevel = RouteConst.DEFAULT_ZOOM;
        if (mMap.getCameraPosition().zoom > RouteConst.DEFAULT_ZOOM) {
            currentZoomLevel = mMap.getCameraPosition().zoom;
        }
        final LatLng latLng = new LatLng(_location.getLatitude(), _location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, currentZoomLevel);
        mMap.animateCamera(cameraUpdate);
    }

    public final void drawEndMarker(LatLng _latLng) {
        if (mEndMarker == null) {
            Location location = new Location("TargetLocation");
            location.setLatitude(_latLng.latitude);
            location.setLongitude(_latLng.longitude);
            mEndMarker = mMap.addMarker(prepareMarker(location, false));
        } else {
            mEndMarker.setPosition(_latLng);
        }
    }

    public final void drawStartMarker(Location _location) {
        if (mStartMarker == null) {
            mStartMarker = mMap.addMarker(prepareMarker(_location, true));
        } else {
            final LatLng latLng = new LatLng(_location.getLatitude(), _location.getLongitude());
            mStartMarker.setPosition(latLng);
            mStartMarker.setTitle("Latitude: " + _location.getLatitude() + ", Longitude: " + _location.getLongitude());
            mStartMarker.setSnippet("Altitude: " + String.format("%.2f", _location.getAltitude()) + ", Accuracy: " + _location.getAccuracy());
        }
        moveCamera(_location);
    }

    public final Location getStartPosition() {
        if (mStartMarker == null)
            return null;

        Location location = new Location("");
        location.setLatitude(mStartMarker.getPosition().latitude);
        location.setLongitude(mStartMarker.getPosition().longitude);
        return location;
    }

    public final Location getEndPosition() {
        if (mEndMarker == null)
            return null;

        Location location = new Location("");
        location.setLatitude(mEndMarker.getPosition().latitude);
        location.setLongitude(mEndMarker.getPosition().longitude);
        return location;
    }

    public final void showRoute(List<LatLng> _points) {
        if (mRoutePolyline == null) {
            PolylineOptions rectLine = new PolylineOptions().width(8).color(Color.BLUE);
            rectLine.addAll(_points);
            mRoutePolyline = mMap.addPolyline(rectLine);
        } else {
            mRoutePolyline.setPoints(_points);
        }

    }

}
