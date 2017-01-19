package com.hiq.freedomvision.models;

/**
 * Created by akanksha on 21/10/16.
 */

public class UserChatObject {

    public static final int INFO = 0;
    public static final int HOTEL = 1;
    public static final int SIGHTSEEING = 2;
    public static final int DESTINATION = 3;
    public static final int PACKAGES = 4;

    private int type = -1;
    private String destName;
    private String originCity;
    private int userType;
    private String noOfPeople;
    private String noOfNights;
    private String priceRange;
    private  String username;
    private String email;
    private String mobile;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public int getUserType() {
        return userType;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(String noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public String getNoOfNights() {
        return noOfNights;
    }

    public void setNoOfNights(String noOfNights) {
        this.noOfNights = noOfNights;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
