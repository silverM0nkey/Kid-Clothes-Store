package com.happybaby.kcs.models;

public class CustomerProfile {

    static public String CUSTOMER_ANONYMOUS = "anonymous";
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String identification;
    private String isGoccoAndFriends;
    private String access_token;
    private Integer storeId;

    private static CustomerProfile customerProfile;

    private CustomerProfile(){
        email = CUSTOMER_ANONYMOUS;
        identification = CUSTOMER_ANONYMOUS;
    }

    static public CustomerProfile getCustomerProfile(){

        if (customerProfile ==null) {
            customerProfile = new CustomerProfile();
            return customerProfile;
        }
        else
            return customerProfile;
    }

    public void setData(String identification, String email, String firstName, String lastName, String phone, String isGoccoAndFriends) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.identification = identification;
        this.isGoccoAndFriends = isGoccoAndFriends;
    }

    public void logout(){
        email = CUSTOMER_ANONYMOUS;
        identification = CUSTOMER_ANONYMOUS;
        this.firstName = null;
        this.lastName = null;
        this.phone = null;
        this.isGoccoAndFriends = null;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getIdentification() { return identification; }

    public void setIdentification(String identification) { this.identification = identification; }

    public String getIsGoccoAndFriends() { return isGoccoAndFriends; }

    public void setIsGoccoAndFriends(String isGoccoAndFriends) { this.isGoccoAndFriends = isGoccoAndFriends; }

    public String getAccess_token() { return access_token; }

    public void setAccess_token(String access_token) { this.access_token = access_token; }

    public Integer getStoreId() { return storeId; }

    public void setStoreId(Integer storeId) { this.storeId = storeId; }
}

