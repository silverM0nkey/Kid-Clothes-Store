package com.happybaby.kcs.models.fixed;

public class SortOptionItemModel {

    private String nameOption;
    private Boolean check;

    public SortOptionItemModel(String nameOption) {
        this.nameOption = nameOption;
        check = false;
    }

    public String getNameOption() {
        return nameOption;
    }

    public void setNameOption(String nameOption) {
        this.nameOption = nameOption;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
