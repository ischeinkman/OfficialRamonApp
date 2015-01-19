package org.ramonaza.officialramonapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilan Scheinkman on 1/12/15.
 */
public class Contact {
    private String name;
    private String email;
    private String school;
    private String phoneNumber;
    private String address;
    private String gradYear;

    public Contact(String[] args){
        this.name=args[0];
        this.school=args[1];
        this.gradYear=toYear(args[2]);
        this.address=args[3];
        this.email=args[4];
        this.phoneNumber=args[5];
     }
    private String toYear(String year)  {   // converts a wrongly formatted year to a correctly formatted year (hopefully)
        try {                               // eg: "17" to "2017"
            Integer.parseInt(year);         // if the string is not an integer (eg: "Sophomore"), don't try to make it one
        } catch (java.lang.NumberFormatException e)  {
            return year;
        }
        Date date = new Date();
        DateFormat DF = new SimpleDateFormat("yyyy");
        String curryear = DF.format(date);
        switch (year.length())  {   // add the correct amount of missing digits to the given year string
            case 0: return "Not Given";
            case 1: return curryear.substring(0, 3) + year;
            case 2: return curryear.substring(0, 2) + year;
            case 3: return curryear.substring(0, 1) + year;
            case 4: return year;
            default: return year.substring(0, 4);
        }
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
    public String getGradYear(){
        return this.gradYear;
    }
    public String getYear() {
        try {                               // If the aleph entered a string instead of his grad year, display the string
            Integer.parseInt(gradYear);
        } catch (java.lang.NumberFormatException e)  {
            return gradYear;
        }
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
