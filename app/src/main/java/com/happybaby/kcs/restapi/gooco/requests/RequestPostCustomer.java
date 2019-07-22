package com.happybaby.kcs.restapi.gooco.requests;

public class RequestPostCustomer {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String identification;
    private String password;

    public RequestPostCustomer(String firstName, String lastName, String email, String phone, String identification, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.identification = identification;
        this.password = password;
    }
}
