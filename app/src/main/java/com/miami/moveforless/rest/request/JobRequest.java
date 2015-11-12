package com.miami.moveforless.rest.request;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class JobRequest {
    public String key;
    public String token;

    public JobRequest(String _key, String _token){
        this.key = _key;
        this.token = _token;
    }
}
