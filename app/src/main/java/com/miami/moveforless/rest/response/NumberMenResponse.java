package com.miami.moveforless.rest.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergbek on 12.11.2015.
 */
public class NumberMenResponse {

    @SerializedName("id")
    public Integer id;

    @SerializedName("number_men")
    public String number_men;

    @SerializedName("rate_money")
    public String rate_money;
}
