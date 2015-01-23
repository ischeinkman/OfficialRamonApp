package org.ramonaza.officialramonapp.dataFiles;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilan Scheinkman on 1/12/15.
 */
public class ContactInfoWrapper{
    private String name;
    private String email;
    private String school;
    private String phoneNumber;
    private String address;
    private String gradYear;
    private String[] argArray;

    public ContactInfoWrapper(String[] args){
        this.name=args[0];
        this.school=args[1];
        this.gradYear=args[2];
        this.address=args[3];
        this.email=args[4];
        this.phoneNumber=args[5];
        this.argArray=args;
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
    public String getAddress(){
        return this.address;
    }
    public String[] getArgArray(){
        return this.argArray;
    }
    public String getGradYear(){
        return this.gradYear;
    }
    public String getYear() {
        String yearString = "";
        switch (getDifference())    {
            case -1: yearString += "College ";
            case  3: yearString += "Freshman"; break;
            case -2: yearString += "College ";
            case  2: yearString += "Sophomore"; break;
            case -3: yearString += "College ";
            case  1: yearString += "Junior"; break;
            case -4: yearString += "College ";
            case  0: yearString += "Senior"; break;
            
            case  4: yearString += "8th Grader"; break;
            case  5: yearString += "7th Grader"; break;
            case  6: yearString += "Invalid"; break;
            
            default: yearString += "Alumnus";
        }
        return yearString;
    }
    private int getDifference() {
        Date date = new Date();
        int gradYearInt = Integer.parseInt(gradYear);           // Graduation year of contact (month would be june or 6)
        
        DateFormat DF = new SimpleDateFormat("yyyy");           // get the current year
        int currentYear = Integer.parseInt(DF.format(date));
        
        DF = new SimpleDateFormat("MM");                        // get the current month
        int currentMonth = Integer.parseInt(DF.format(date));
        
        if (currentMonth > 6)   currentYear++;                  // the school year passes through the real year, this should fix that
        
        return gradYearInt - currentYear;
    }


}
