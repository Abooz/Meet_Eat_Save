package com.example.meeteatsave;

public class User {

    private String uId;
    private String userFirstName;
    private String userLastName;
    private int age;
    private int telephoneNumber;
    private String streetAndHouseNumber;
    private int houseNumber;
    private int plz;
    private String city;
    private String sex;

    public User() {
    }

    public User(String userId, String userFirstName, String userLastName, int age, int telephoneNumber, String streetAndHouseNumber, int houseNumber, int plz, String city, String sex  ) {
        this.uId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.age = age;
        this.telephoneNumber = telephoneNumber;
        this.streetAndHouseNumber = streetAndHouseNumber;
        this.houseNumber = houseNumber;
        this.plz = plz;
        this.sex = sex;



    }

    public String getuId() {
        return uId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public int getAge() {
        return age;
    }



    public int getTelephoneNumber() {
        return telephoneNumber;
    }



    public String getStreetAndHouseNumber() {
        return streetAndHouseNumber;
    }



    public int getHouseNumber() {
        return houseNumber;
    }



    public int getPlz() {
        return plz;
    }



    public String getCity() {
        return city;
    }



    public String getSex() {
        return sex;
    }
}
