package com.miami.moveforless.rest;

import com.miami.moveforless.Exceptions.ConnectionException;
import com.miami.moveforless.Exceptions.LocationException;
import com.miami.moveforless.Exceptions.RouteException;
import com.miami.moveforless.rest.response.ErrorResponse;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by klim on 21.10.15.
 */
public class ErrorParser {

    public static <T> T parseCustomError(Throwable _e, Class<T> clazz) {
        RetrofitError error = (RetrofitError) _e;
        return (T) error.getBodyAs(clazz);

    }

    public static String parseRetrofitError(Throwable _e) {
        RetrofitError error = (RetrofitError) _e;
        String result = "";

        switch (error.getKind()) {

            case NETWORK:
                result = "Network connection error";
                break;
            case UNEXPECTED:
                result = error.getCause().getMessage();
                break;
            case HTTP:
                Response response = error.getResponse();
                switch (response.getStatus()) {
                    case 404:
                        result = parseCustomError(_e, ErrorResponse.class).error;
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
                return "Bad network connection";
            }
        } catch (Exception exception) {
            return "Unknown error: " + _e.getLocalizedMessage();
        }
        return "Unknown error: " + _e.getLocalizedMessage();
    }

    public static String parseRouteError(Throwable _e) {
        try {
            if (_e instanceof RetrofitError) {
                return parseRetrofitError(_e);
            }
            if (_e instanceof TimeoutException) {
                return "Bad network connection";
            }
            if (_e instanceof RouteException) {
                return "Could not get the route";
            }

            if (_e instanceof LocationException) {
                return "Could not get current location";
            }

        } catch (Exception exception) {
            return "Unknown error: " + _e.getLocalizedMessage();
        }
        return "Unknown error: " + _e.getLocalizedMessage();

    }

    public static Exception checkConnectionError(Throwable _e) {
        try {
            if (_e instanceof RetrofitError) {
                RetrofitError error = (RetrofitError) _e;
                if (error.getKind() == RetrofitError.Kind.NETWORK)
                    return new ConnectionException();
            }
            if (_e instanceof TimeoutException) {
                return new ConnectionException();
            }
        } catch (Exception exception) {
            return new UnknownHostException();
        }
        return new UnknownHostException();
    }


}
