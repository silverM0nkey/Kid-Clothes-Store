package com.happybaby.kcs.restapi.gooco.requests;

public class RequestLogin {

    private String grant_type;
    private String username;
    private String password;
    private String client_id;

    public RequestLogin(String grant_type, String username, String password, String client_id) {
        this.grant_type = grant_type;
        this.username = username;
        this.password = password;
        this.client_id = client_id;
    }
}
