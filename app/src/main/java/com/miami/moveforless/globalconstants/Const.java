package com.miami.moveforless.globalconstants;

import com.miami.moveforless.fragments.eventbus.FragmentType;

import static com.miami.moveforless.fragments.eventbus.FragmentType.FEEDBACK;
import static com.miami.moveforless.fragments.eventbus.FragmentType.JOB_DETAILS;
import static com.miami.moveforless.fragments.eventbus.FragmentType.PAYMENT;
import static com.miami.moveforless.fragments.eventbus.FragmentType.QUESTIONNAIRE;
import static com.miami.moveforless.fragments.eventbus.FragmentType.SIGN_CONTRACT;
import static com.miami.moveforless.fragments.eventbus.FragmentType.SIGN_INVOICE;
import static com.miami.moveforless.fragments.eventbus.FragmentType.STOP_JOB;

/**
 * Created by klim on 21.10.15.
 */
public class Const {

    public static final String JOB_ID_KEY = "job_id_key";
    public static final FragmentType[] JOB_DETAILS_ORDER = {
            JOB_DETAILS,
            QUESTIONNAIRE,
            SIGN_CONTRACT,
            STOP_JOB,
            SIGN_INVOICE,
            PAYMENT,
            FEEDBACK,
            JOB_DETAILS};


}
