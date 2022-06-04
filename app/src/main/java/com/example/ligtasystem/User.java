package com.example.ligtasystem;

public class User {

    public String firstname, middlename, lastname, username, birthdate, email, address, phoneNumber, password;

    public User(String firstname, String middlename, String lastname, String username, String birthdate, String email, String address, String phoneNumber, String password) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.username = username;
        this.birthdate = birthdate;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(){

    }
}
