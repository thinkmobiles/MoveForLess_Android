package com.miami.moveforless.rest;

import com.miami.moveforless.rest.response.ErrorModel;

import java.util.concurrent.TimeoutException;

import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.exceptions.CompositeException;

/**
 * Created by klim on 21.10.15.
 */
public class ErrorParser {

    public static <T> T parseCustomError(Throwable _e, Class<T> clazz) {
        RetrofitError error = (RetrofitError) _e;
        return (T)error.getBodyAs(clazz);

    }

    public static String parseRetrofitError(Throwable _e) {
        RetrofitError error = (RetrofitError) _e;
        String result = "";

        switch (error.getKind()) {

            case NETWORK:
                result = error.getLocalizedMessage();
                break;
            case UNEXPECTED:
                result = error.getLocalizedMessage();
                break;
            case HTTP:
                Response response = error.getResponse();
                switch (response.getStatus()) {
                    case 404:
                        result = parseCustomError(_e, ErrorModel.class).error;
                        if (result == null) {
                            result = "Bad error response";
                        }
                        break;
                    default:
                        result = response.getReason();
                }
                break;
            default:
                result = "Unknown error kind: " + error.getKind();

        }

        return result;
    }


    public static String parse(Throwable _e) {
        try {
            if (_e instanceof RetrofitError) {
                return parseRetrofitError(_e);
            }
            if (_e instanceof TimeoutException) {
                return  "Bad network connection";
            }
        } catch (Exception exception) {
            return "Unknown error: " + _e.getLocalizedMessage();
        }
        return "Unknown error: " + _e.getLocalizedMessage();
    }
}
