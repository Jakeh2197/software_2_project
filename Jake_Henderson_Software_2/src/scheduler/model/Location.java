/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.model;

import java.time.LocalTime;

/**
 *
 * @author Jake
 */
public class Location {
    
    //days the locations are open
    public static enum DAYSOPEN {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    }
    
    //hours the locations are open
    public static LocalTime open = LocalTime.of(8, 0);
    public static LocalTime close = LocalTime.of(17, 0);
    
    private String locationName;
    private String timeZone;
    
    public Location(String locationName, String timeZone) {
        this.locationName = locationName;
        this.timeZone = timeZone;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    
}
