package org.ramonaza.officialramonapp.datafiles.condrive_database;

import org.ramonaza.officialramonapp.datafiles.InfoWrapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilan Scheinkman on 1/12/15.
 */
public class ContactInfoWrapper implements InfoWrapper{
    private String name;
    private String email;
    private String school;
    private String phoneNumber;
    private String address;
    private String gradYear;
    private int idNum;
    private int area;
    private boolean present;

    public boolean isPresent() {
        return present;
    }
    public int getArea(){
        return area;
    }
    public void setArea(int inArea){
        if(this.area==-1)this.area=inArea;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public ContactInfoWrapper(){
        this.setPresent(false);
        this.setArea(-1);
    }

    public void setName(String name) {
        if(this.name==null) this.name = name;
    }

    public void setEmail(String email) {
        if(this.email==null) this.email = email;
    }

    public void setSchool(String school) {
        if(this.school==null)this.school = school;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(this.phoneNumber==null) this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        if(this.address==null) this.address = address;
    }

    public void setGradYear(String gradYear) {
        if(this.gradYear==null) this.gradYear = gradYear;
    }


    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSchool() {
        return this.school;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public String getGradYear() {
        return this.gradYear;
    }

    public String getYear() {
        String yearString = "";
        switch (getDifference()) {
            case -1:
                yearString += "College ";
            case 3:
                yearString += "Freshman";
                break;
            case -2:
                yearString += "College ";
            case 2:
                yearString += "Sophomore";
                break;
            case -3:
                yearString += "College ";
            case 1:
                yearString += "Junior";
                break;
            case -4:
                yearString += "College ";
            case 0:
                yearString += "Senior";
                break;

            case 4:
                yearString += "8th Grader";
                break;
            case 5:
                yearString += "7th Grader";
                break;
            case 6:
                yearString += "Invalid";
                break;

            case 99:
                yearString += "Advisor";
                break;

            default:
                yearString += "Alumnus";
        }
        return yearString;
    }

    public int getId(){
        return idNum;
    }
    public void setId(int inputId){
        this.idNum=inputId;
    }

    private int getDifference() {
        Date date = new Date();
        int gradYearInt;
        try {
            gradYearInt = Integer.parseInt(gradYear);// Graduation year of contact (month would be june or 6)
            DateFormat DF = new SimpleDateFormat("yyyy");           // get the current year
            int currentYear = Integer.parseInt(DF.format(date));

            DF = new SimpleDateFormat("MM");                        // get the current month
            int currentMonth = Integer.parseInt(DF.format(date));

            if (currentMonth > 8)
                currentYear++;                  // the school year passes through the real year, this should fix that

            return gradYearInt - currentYear;
        } catch (NumberFormatException e) {
            if(gradYear==null) return 6;
            if (gradYear.equals("Advisor")) {
                return 99;
            }
            else{
                return 33;
            }
        }
    }

    @Override
    public boolean equals(Object inObj){
        if (inObj instanceof ContactInfoWrapper){
            if(((ContactInfoWrapper) inObj).getName().equals(this.getName())){
                return true;
            }
        }
        return false;
    }
}
        




