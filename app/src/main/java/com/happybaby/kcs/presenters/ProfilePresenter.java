package com.happybaby.kcs.presenters;

import com.happybaby.kcs.fragments.ProfileFragment;
import com.happybaby.kcs.models.CustomerProfile;

public class ProfilePresenter {

    ProfileFragment profileFragment;

    public ProfilePresenter(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }

    public void setUp() {
        if (CustomerProfile.getCustomerProfile().getEmail().equals(CustomerProfile.CUSTOMER_ANONYMOUS)) {
            profileFragment.setLogin();
        } else {
            profileFragment.setCustomerProfile(CustomerProfile.getCustomerProfile().getFirstName(),
                CustomerProfile.getCustomerProfile().getLastName(),
                CustomerProfile.getCustomerProfile().getEmail(),
                CustomerProfile.getCustomerProfile().getPhone());
        }
    }

    public void logout() {
        CustomerProfile.getCustomerProfile().logout();
        setUp();
    }


}
