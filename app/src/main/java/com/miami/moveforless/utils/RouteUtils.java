package com.miami.moveforless.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.miami.moveforless.rest.response.RouteInfo;
import com.miami.moveforless.rest.response.RouteResponse;

import java.util.List;

/**
 * Created by klim on 02.11.15.
 */
public class RouteUtils {

    public static RouteInfo parseRouteResponse(RouteResponse _routeResponse) {
        RouteInfo result = null;
        if (_routeResponse.status.equals("OK")) {
            List<LatLng> points = PolyUtil.decode(_routeResponse.getPoints());
            result = new RouteInfo.Builder()
                    .setDirectionPoints(points)
                    .setDistance(_routeResponse.getDistance())
                    .setDuration(_routeResponse.getDuration())
                    .build();
        }
        return result;
    }


}
