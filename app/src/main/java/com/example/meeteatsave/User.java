package com.example.meeteatsave;

public class User {

    private String uId;
    private String userFirstName;
    private String userLastName;
    private String age;
    private String telephoneNumber;
    private String streetAndHouseNumber;
    private String plz;
    private String city;
    private String sex;

    public User() {
    }

    public User(String userId, String userFirstName, String userLastName, String age, String telephoneNumber, String streetAndHouseNumber, String plz, String city, String sex) {
        this.uId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.age = age;
        this.telephoneNumber = telephoneNumber;
        this.streetAndHouseNumber = streetAndHouseNumber;
        this.plz = plz;
        this.sex = sex;
        this.city = city;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getStreetAndHouseNumber() {
        return streetAndHouseNumber;
    }

    public void setStreetAndHouseNumber(String streetAndHouseNumber) {
        this.streetAndHouseNumber = streetAndHouseNumber;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
