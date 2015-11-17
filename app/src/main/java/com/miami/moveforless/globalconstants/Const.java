package com.miami.moveforless.globalconstants;

import com.miami.moveforless.fragments.eventbus.FragmentType;
import com.paypal.android.sdk.payments.PayPalConfiguration;

import static com.miami.moveforless.fragments.eventbus.FragmentType.*;

/**
 * Created by klim on 21.10.15.
 */
public class Const {

    public static final int FEEDBACK_REQUEST_ID = 1234;
    public static final int CLAIM_REQUEST_ID = 1235;

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

    //pay
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    //sendbox test
    private static final String CONFIG_CLIENT_ID =
            "AekC6Q3O01zbLuDkSQEcbrCawwXXo5Hzi68OyHGHVcRU2D6neCcvwO6wrZli7YNpFvC55ZfT4zFcc_UA";
    //real test
//    private static final String CONFIG_CLIENT_ID = "AVWOzMxGpnIUKb6Zz-nQQUIixuEwcEkHQVuGaSLJpY0kd3vwLmd2fDQYh8KqgkCpjGnnopk_YG1zW5ia";


    public static final int REQUEST_CODE_PAYMENT = 1;
    public static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    public static final int REQUEST_CODE_PROFILE_SHARING = 3;

    public static PayPalConfiguration config = new PayPalConfiguration().environment(CONFIG_ENVIRONMENT).clientId
            (CONFIG_CLIENT_ID).rememberUser(false);

}
