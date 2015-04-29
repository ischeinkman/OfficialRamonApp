package org.ramonaza.officialramonapp.datafiles.condrive_database;

import org.ramonaza.officialramonapp.datafiles.InfoWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanscheinkman on 3/14/15.
 */
public class DriverInfoWrapper implements InfoWrapper{
    private int spots;
    private String name;
    private int area;
    private int id;
    private List<ContactInfoWrapper> alephsInCar;

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

}
