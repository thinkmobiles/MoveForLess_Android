package com.miami.moveforless.utils;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
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

    public static void click(View _view, Action1 _action) {
        RxView.clicks(_view).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(_action);
    }

    public static Observable<Object> click(View _view) {
        return RxView.clicks(_view).throttleFirst(500, TimeUnit.MILLISECONDS);
    }

}
