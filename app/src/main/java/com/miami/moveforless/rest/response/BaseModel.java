package com.miami.moveforless.rest.response;

import com.google.gson.Gson;

/**
 * Created by klim on 03.11.15.
 */
public class BaseModel {

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static public RouteInfo deserialize(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, RouteInfo.class);
    }

}
