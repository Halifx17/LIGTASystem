package com.example.ligtasystem;

public class User {

    public String firstname, lastname, username, birthdate, email, address, phoneNumber, password, profileUri;

    public User(String firstname, String lastname, String username, String birthdate, String email, String address, String phoneNumber, String password, String profileUri) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.birthdate = birthdate;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profileUri = profileUri;
    }

    public User(){

    }
}
