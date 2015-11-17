package com.miami.moveforless.globalconstants;

import com.miami.moveforless.fragments.eventbus.FragmentType;

import static com.miami.moveforless.fragments.eventbus.FragmentType.CLAIM;
import static com.miami.moveforless.fragments.eventbus.FragmentType.CONFIRMATION;
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

    public static final int FEEDBACK_REQUEST_ID = 1000;

    public static final String JOB_ID_KEY = "job_id_key";
    public static final FragmentType[] JOB_DETAILS_ORDER = {
            JOB_DETAILS,
            QUESTIONNAIRE,
            SIGN_CONTRACT,
            STOP_JOB,
            SIGN_INVOICE,
            PAYMENT,
            CONFIRMATION,
            FEEDBACK,
            CLAIM
    };

    final public static String IMAGE_MIME_TYPE_PREFIX = "image";
    final public static String IMAGE_MIME_TYPE = IMAGE_MIME_TYPE_PREFIX + "/*";

    final public static String REQUEST_INTENT_TYPE_PHOTO = "type";
    final public static int REQUEST_TYPE_PHOTO = 0;
    final public static int REQUEST_PHOTO = 10000;
    final public static int REQUEST_GALLERY_IMAGE = 10002;

    public static final String TIME_12_HOUR_FORMAT = "hh:mmaa";
    public static final String TIME_MONTH_DAY_FORMAT = "MM/dd";
    public static final String TIME_MONTH_DAY_YEAR_FORMAT = "E MM/dd/yyyy";

}
