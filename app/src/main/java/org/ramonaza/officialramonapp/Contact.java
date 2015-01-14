package org.ramonaza.officialramonapp;

import android.util.Log;

/**
 * Created by Ilan Scheinkman on 1/12/15.
 */
public class Contact {
    private String name;
    private String email;
    private String school;
    private String phoneNumber;
    private String year;
    private String address;

    public Contact(String[] args){
        for (String inputStr:args){
            Log.d("DEBUG",String.format("Creating %s: %s inputted",args[0],inputStr));
        }
        this.name=args[0];
        this.school=args[1];
        this.year=args[2];
        this.address=args[3];
        this.email=args[4];
        this.phoneNumber=args[5];
     }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getSchool(){
        return this.school;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public String getYear(){
        return this.year;
    }
    public String getAddress(){
        return this.address;
    }
}
