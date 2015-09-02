package org.ramonaza.officialramonapp.people.rides.backend;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanscheinkman on 3/14/15.
 */
public class DriverInfoWrapper implements InfoWrapper{
    private int spots;
    private String name;
    private int area;
    private String address;
    private String latitude;
    private String longitude;
    private int id;
    private List<ContactInfoWrapper> alephsInCar;

    public DriverInfoWrapper(){
        this.alephsInCar=new ArrayList<ContactInfoWrapper>(spots+1);
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFreeSpots(){
        return spots-alephsInCar.size();
    }

    public int getSpots() {
        return spots;
    }

    public void setSpots(int spots) {
        this.spots = spots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void addAlephToCar(ContactInfoWrapper aleph){
        alephsInCar.add(aleph);
    }
    public void removeAlephFromCar(ContactInfoWrapper aleph){
        alephsInCar.remove(aleph);
    }
    public List<ContactInfoWrapper> getAlephsInCar(){
        return alephsInCar;
    }
    public List<ContactInfoWrapper> attemptGenerate(List<ContactInfoWrapper> totalAlephPool){
        List<ContactInfoWrapper> rval=new ArrayList<ContactInfoWrapper>();
        boolean contIter=true;
        while(this.getFreeSpots()>0 && contIter){
            contIter=false;
            for (ContactInfoWrapper cAleph:totalAlephPool){
                if (cAleph.getArea()==this.getArea()){
                    rval.add(cAleph);
                    this.addAlephToCar(cAleph);
                    contIter=true;
                }
            }
        }
        return rval; //returning the added alephs allows easy removal from the external present pool
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof DriverInfoWrapper &&((DriverInfoWrapper) o).getName().equals(getName()));
    }
}
