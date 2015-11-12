package com.miami.moveforless.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sergbek on 12.11.2015.
 */
public class ListNumberMenResponse {

    @SerializedName("number_men")
    public List<NumberMenResponse> number_men;
}
