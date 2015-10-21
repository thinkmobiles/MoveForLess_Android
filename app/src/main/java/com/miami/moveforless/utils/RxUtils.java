package com.miami.moveforless.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by klim on 21.10.15.
 */
public class RxUtils {


    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }
}
