package com.vegaschool.s10110678.weskate;

public class UserInfo {
    //Declaring user variables
    public String fullName, username, email;
    //public boolean kilometers;
    //public String landmarkpref;

    public UserInfo(){

    }

    //Creating constructor for user information
    public UserInfo(String fullname, String username, String email/* String landmarkpref, boolean kilometers*/){
        this.fullName = fullname;
        this.username = username;
        this.email = email;
        //this.landmarkpref = landmarkpref;
        //this.kilometers = kilometers;
    }
}
