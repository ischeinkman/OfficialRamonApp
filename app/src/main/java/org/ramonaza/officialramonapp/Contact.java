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
        Log.d("DEBUG",String.format("%s.getName() called; returning %s",this.name,this.name));
        return this.name;
    }
    public String getEmail(){
        Log.d("DEBUG",String.format("%s.getEmail() called; returning %s",this.name,this.email));
        return this.email;
    }
    public String getSchool(){
        Log.d("DEBUG",String.format("%s.getSchool() called; returning %s",this.name,this.school));
        return this.school;
    }
    public String getPhoneNumber(){
        Log.d("DEBUG",String.format("%s.getPhoneNumber() called; returning %s",this.name,this.phoneNumber));
        return this.phoneNumber;
    }

    public String getYear(){
        Log.d("DEBUG",String.format("%s.getYear() called; returning %s",this.name,this.year));
        return this.year;
    }
    public String getAddress(){
        Log.d("DEBUG",String.format("%s.getAddress() called; returning %s",this.name,this.address));
        return this.address;
    }
}
