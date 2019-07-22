package com.happybaby.kcs.models;

public class FilterListModel {

    private Boolean check;
    private String name;

    public FilterListModel(String name, boolean check) {
        this.name = name;
        this.check = check;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
