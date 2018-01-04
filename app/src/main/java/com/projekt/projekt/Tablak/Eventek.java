package com.projekt.projekt.Tablak;

/**
 * Created by Andras on 1/3/2018.
 */

public class Eventek {
   public String eventID;
    public String eventName;
    public String eventDescription;
    public Double eventLocationLongitude;
    public Double eventLocationLatitude;
    public String creatorUid;


    public Eventek(){

    }
    public Eventek(String eventID, String eventName, String eventDescription, Double eventLocationLongitude, Double eventLocationLatitude, String creatorUid) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventLocationLongitude = eventLocationLongitude;
        this.eventLocationLatitude = eventLocationLatitude;
        this.creatorUid = creatorUid;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }



    public Double getEventLocationLongitude() {
        return eventLocationLongitude;
    }

    public void setEventLocationLongitude(Double eventLocationLongitude) {
        this.eventLocationLongitude = eventLocationLongitude;
    }

    public Double getEventLocationLatitude() {
        return eventLocationLatitude;
    }

    public void setEventLocationLatitude(Double eventLocationLatitude) {
        this.eventLocationLatitude = eventLocationLatitude;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    @Override
    public String toString() {
        return "Eventek{" +
                "eventID='" + eventID + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventLocationLongitude=" + eventLocationLongitude +
                ", eventLocationLatitude=" + eventLocationLatitude +
                ", creatorUid='" + creatorUid + '\'' +
                '}';
    }
}