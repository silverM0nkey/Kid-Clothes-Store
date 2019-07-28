package com.happybaby.kcs.presenters;

import com.happybaby.kcs.fragments.interfaces.ProfileView;
import com.happybaby.kcs.models.CustomerProfile;

public class ProfilePresenter {

    ProfileView profileView;

    public ProfilePresenter(ProfileView profileView) {
        this.profileView = profileView;
    }

    public void setUp() {
        if (CustomerProfile.getCustomerProfile().getEmail().equals(CustomerProfile.CUSTOMER_ANONYMOUS)) {
            if (profileView != null) {
                profileView.setLogin();
            }
        } else {
            if (profileView != null) {
                profileView.setCustomerProfile(CustomerProfile.getCustomerProfile().getFirstName(),
                        CustomerProfile.getCustomerProfile().getLastName(),
                        CustomerProfile.getCustomerProfile().getEmail(),
                        CustomerProfile.getCustomerProfile().getPhone());
            }
        }
    }

    public void logout() {
        CustomerProfile.getCustomerProfile().logout();
        setUp();
    }

    public void unbindView(){
        this.profileView = null;
    }


}
