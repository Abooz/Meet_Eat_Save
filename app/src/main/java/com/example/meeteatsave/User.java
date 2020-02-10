package com.example.meeteatsave;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String uid;
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

    public User(String uid, String userFirstName, String userLastName, String age, String telephoneNumber, String streetAndHouseNumber, String plz, String city, String sex) {
        this.uid = uid;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.age = age;
        this.telephoneNumber = telephoneNumber;
        this.streetAndHouseNumber = streetAndHouseNumber;
        this.plz = plz;
        this.sex = sex;
        this.city = city;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("userFirstName", userFirstName);
        result.put("userLastName", userLastName);
        result.put("age", age);
        result.put("city", city);
        result.put("telephoneNumber", telephoneNumber);
        result.put("streetAndHouseNumber", streetAndHouseNumber);
        result.put("plz", plz);
        result.put("sex", sex);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
