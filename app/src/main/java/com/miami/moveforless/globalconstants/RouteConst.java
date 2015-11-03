package com.miami.moveforless.globalconstants;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by klim on 02.11.15.
 */
public class RouteConst {

    public static final String LOCATION_UPDATES_KEY = "location updates";
    public static final String ROUTE_UPDATES_KEY = "route updates";
    public static final int REQUEST_CODE_RESOLUTION = 1;

    public static final int UPDATE_INTERVAL = 10000; // 10 sec
    public static final int FASTEST_INTERVAL = 5000; // 5 sec
    public static final int DISPLACEMENT = 10; // 10 meters

    public static final float DEFAULT_ZOOM = 15f;

    public static final float GEOFENCE_RADIUS_IN_METERS = 100f;
    public static final String GEOFENCE_ACTION = "geofence action";
    public static final String GEOFENCE_TYPE = "geofence type";

    public static final LatLng DEFAULT_LOCATION = new LatLng(25.7738889, -80.1938889);
}
