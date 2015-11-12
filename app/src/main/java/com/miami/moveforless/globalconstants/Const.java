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
    };

    final public static String IMAGE_MIME_TYPE_PREFIX = "image";
    final public static String IMAGE_MIME_TYPE = IMAGE_MIME_TYPE_PREFIX + "/*";

    final public static String REQUEST_INTENT_TYPE_PHOTO = "type";
    final public static int REQUEST_TYPE_PHOTO = 0;
    final public static int REQUEST_PHOTO = 1;
    final public static int REQUEST_GALLERY_IMAGE = 2;

}
