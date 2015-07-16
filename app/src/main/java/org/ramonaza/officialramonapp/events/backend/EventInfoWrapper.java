package org.ramonaza.officialramonapp.events.backend;

import android.os.Parcel;
import android.os.Parcelable;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;

/**
 * Created by ilanscheinkman on 1/29/15.
 */
public class EventInfoWrapper  implements Parcelable, InfoWrapper {
    private String name;
    private String desc;
    private String planner;
    private String meet;
    private String mapsLocation;
    private String bring;
    private String date;
    private int Id;


    public EventInfoWrapper(String[] argArray){
        this.name=argArray[0];
        this.desc=argArray[1];
        this.planner=argArray[2];
        this.meet=argArray[3];
        this.mapsLocation=argArray[4];
        this.bring=argArray[5];
        this.date=argArray[6];
        this.Id=-1;
    }
    public EventInfoWrapper(String unParsedRSS){
        String[] splitFeed=unParsedRSS.split(" <br/> ");
        this.name=splitFeed[2];
        this.desc=splitFeed[3];
        this.planner=splitFeed[7];
        this.meet=splitFeed[5]+" @ "+splitFeed[4];
        this.mapsLocation=splitFeed[8];
        this.bring=splitFeed[6];
        this.date=splitFeed[1];

    }


    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPlanner() {
        return planner;
    }

    public String getMeet() {
        return meet;
    }

    public String getMapsLocation() {
        return mapsLocation;
    }

    public String getBring() {
        return bring;
    }

    public String getDate() {
        return date;
    }

    public String[] generateArgs(){
        return new String[]{this.name,this.desc,this.planner,this.meet,this.mapsLocation,this.bring,this.date};
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeStringArray(this.generateArgs());
    }

    public static final Parcelable.Creator<EventInfoWrapper> CREATOR=new Parcelable.Creator<EventInfoWrapper>(){
        public EventInfoWrapper createFromParcel(Parcel in){
            return new EventInfoWrapper(in.createStringArray());
        }
        public EventInfoWrapper[] newArray(int size){
            return new EventInfoWrapper[size];
        }
    };

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public int getId() {
        return Id;
    }
}
