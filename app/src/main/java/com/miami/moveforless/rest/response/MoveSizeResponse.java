package com.miami.moveforless.rest.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergbek on 12.11.2015.
 */
public class MoveSizeResponse {

    @SerializedName("id")
    public Integer id;

    @SerializedName("name")
    public String name;

    @SerializedName("rank_id")
    public String rank_id;


}