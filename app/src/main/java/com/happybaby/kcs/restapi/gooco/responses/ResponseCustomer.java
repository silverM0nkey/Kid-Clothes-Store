package com.happybaby.kcs.restapi.gooco.responses;

public class ResponseCustomer {


    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String identification;
    private String isGoccoAndFriends;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIsGoccoAndFriends() {
        return isGoccoAndFriends;
    }

    public void setIsGoccoAndFriends(String isGoccoAndFriends) {
        this.isGoccoAndFriends = isGoccoAndFriends;
    }
}
