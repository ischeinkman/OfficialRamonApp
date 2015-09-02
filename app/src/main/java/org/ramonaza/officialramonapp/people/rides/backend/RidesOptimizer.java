package org.ramonaza.officialramonapp.people.rides.backend;

import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ilanscheinkman on 9/1/15.
 */
public class RidesOptimizer {

    /**
     * Calculate rides based on latitude and longitude, iterating over the passengers
     * and assigning them a driver.
      */
    public static final int ALGORITHM_LATLONG_ALEPHS_FIRST=0;

    /**
     * Calculate rides based on latitude and longitude, iterating over the drivers
     * and assigning them passengers.
     */
    public static final int ALGORITHM_LATLONG_DRIVERS_FIRST=1;

    private Set<ContactInfoWrapper> alephsToOptimize;
    private List<DriverInfoWrapper> driversToOptimize;
    private int algorithm;
    private boolean retainPreexisting;

    public RidesOptimizer(){
        this.alephsToOptimize=new HashSet<ContactInfoWrapper>();
        this.driversToOptimize=new ArrayList<DriverInfoWrapper>();
    }

    /**
     * Load driverless passengers into the optimizer.
     * @param passengersToLoad the passengers to load
     * @return this
     */
    public RidesOptimizer loadPassengers(ContactInfoWrapper... passengersToLoad){
        for(ContactInfoWrapper a:passengersToLoad) alephsToOptimize.add(a);
        return this;
    }

    /**
     * Load drivers into the optimizer.
     * @param driversToLoad the drivers to load
     * @return this
     */
    public RidesOptimizer loadDriver(DriverInfoWrapper... driversToLoad){
        for(DriverInfoWrapper d:driversToLoad) driversToOptimize.add(d);
        return this;
    }

    /**
     * Set the algorithm and strength of the optimization.
     * @param algorithm the algorithm to use, based on public constants
     * @param retainPreexisting whether or not the optimizer should keep preexisting rides settings
     * @return this
     */
    public RidesOptimizer setAlgorithm(int algorithm, boolean retainPreexisting){
        this.algorithm=algorithm;
        this.retainPreexisting=retainPreexisting;
        return this;
    }

    /**
     * Optimize the rides. This app results in either all loaded passengers being in a car or all
     * loaded cars being full.
     */
    public void optimize(){
        if (algorithm <= 0 || alephsToOptimize.isEmpty() || driversToOptimize.isEmpty()) return;
        if(!retainPreexisting){
            for(DriverInfoWrapper driver:driversToOptimize){
                for(ContactInfoWrapper aleph:driver.getAlephsInCar()){
                    driver.removeAlephFromCar(aleph);
                }
            }
        }
        switch (algorithm){
            case ALGORITHM_LATLONG_ALEPHS_FIRST:
                latLongAlephsFirst();
                break;
            case ALGORITHM_LATLONG_DRIVERS_FIRST:
                latLongDriversFirst();
                break;
        }
    }

    private void latLongAlephsFirst(){
        List<ContactInfoWrapper> allContacts=new ArrayList<ContactInfoWrapper>(alephsToOptimize);
        for(ContactInfoWrapper toOptimize : allContacts){
            DriverInfoWrapper driver=getClosestDriver(toOptimize);
            if(driver == null) break;
            driver.addAlephToCar(toOptimize);
            alephsToOptimize.remove(toOptimize);
        }
    }

    private DriverInfoWrapper getClosestDriver(ContactInfoWrapper aleph){
        double minDist=Double.MAX_VALUE;
        DriverInfoWrapper rDriver=null;
        for(DriverInfoWrapper driver:driversToOptimize){
            if(driver.getFreeSpots()<=0) continue;
            double curDist=Math.sqrt((
                    aleph.getLatitude()-driver.getLatitude())
                    *(aleph.getLatitude()-driver.getLatitude())
                    +(aleph.getLongitude()-driver.getLongitude())
                    *(aleph.getLongitude()-driver.getLongitude()));
            if(curDist <minDist){
                minDist=curDist;
                rDriver=driver;
            }
        }
        return rDriver;
    }


    private void latLongDriversFirst(){
        boolean allFull=false;
        while(!alephsToOptimize.isEmpty() && !allFull){
            allFull=true;
            for(DriverInfoWrapper toOptimize:driversToOptimize){
                if(toOptimize.getFreeSpots()<=0) continue;
                ContactInfoWrapper aleph=getClosestAleph(toOptimize);
                if(aleph == null) break;
                toOptimize.addAlephToCar(aleph);
                alephsToOptimize.remove(aleph);
                allFull=false;
            }
        }
    }

    private ContactInfoWrapper getClosestAleph(DriverInfoWrapper driver){
        double minDist=Double.MAX_VALUE;
        ContactInfoWrapper rAleph=null;
        for(ContactInfoWrapper aleph:alephsToOptimize){
            double curDist=Math.sqrt((
                    aleph.getLatitude()-driver.getLatitude())
                    *(aleph.getLatitude()-driver.getLatitude())
                    +(aleph.getLongitude()-driver.getLongitude())
                    *(aleph.getLongitude()-driver.getLongitude()));
            if(curDist <minDist){
                minDist=curDist;
                rAleph=aleph;
            }
        }
        return rAleph;
    }

}
