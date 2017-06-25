package com.system.design.locationBasedServiceAndRealtime;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 9/19/16.
 *
 * Support two basic uber features:

 Drivers report their locations.
 Rider request a uber, return a matched driver.
 When rider request a uber, match a closest available driver with him, then mark the driver not available.

 When next time this matched driver report his location, return with the rider's information.

 You can implement it with the following instructions:

 report(driver_id, lat, lng)
 1) return null if no matched rider.
 2) return matched trip information.

 request(rider_id, lat, lng)
 1) create a trip with rider's information.
 2) find a closest driver, mark this driver not available.
 3) fill driver_id into this trip.
 4) return trip

 This is the definition of Trip in Java:

 public class Trip {
 public int id; // trip's id, primary key
 public int driver_id, rider_id; // foreign key
 public double lat, lng; // pick up location
 }

 report(1, 36.1344, 77.5672) // return null
 report(2, 45.1344, 76.5672) // return null
 request(2, 39.1344, 76.5672) // return a trip, LOG(INFO): Trip(rider_id: 2, driver_id: 1, lat: 39.1344, lng: 76.5672)
 report(1, 38.1344, 75.5672) // return a trip, LOG(INFO): Trip(rider_id: 2, driver_id: 1, lat: 39.1344, lng: 76.5672)
 report(2, 45.1344, 76.5672) // return null
 */
public class MiniUber {
    /** key is driver id, value is Trip/location */
    private Map<Integer, Trip> driver2Trip;
    private Map<Integer, Location> driver2Location;

    public MiniUber() {
        driver2Trip = new HashMap<>();
        driver2Location = new HashMap<>();
    }

    // @param driver_id an integer
    // @param lat, lng driver's location
    // return matched trip information if there have matched rider or null
    public Trip report(int driver_id, double lat, double lng) {
        if (driver2Trip.containsKey(driver_id)) {
            return driver2Trip.get(driver_id);
        }
        Location location = null;
        if (driver2Location.containsKey(driver_id)) {
            location = driver2Location.get(driver_id);
            location.latitude = lat;
            location.longitude = lng;
        } else {
            driver2Location.put(driver_id, Location.create(lat, lng));
        }
        return null;
    }

    // @param rider_id an integer
    // @param lat, lng rider's location
    // return a trip
    public Trip request(int rider_id, double lat, double lng) {
        Trip trip = Trip.create(rider_id, lat, lng);
        double defaultDist = -1;
        int driver_id = -1;

        for (Map.Entry<Integer, Location> entry : driver2Location.entrySet()) {
            Location loc = entry.getValue();
            double distance = Distance.dist(loc.latitude, loc.longitude, lat, lng);
            if (defaultDist < 0 || distance < defaultDist) {
                driver_id = entry.getKey();
                defaultDist = distance;
            }
        }
        if (driver_id != -1) { // find one driver
            driver2Location.remove(driver_id); // then set driver unavailability
        }
        trip.driver_id = driver_id;
        driver2Trip.put(driver_id, trip); // add new trip into entry
        return trip;
    }
}

/** use class Location in MiniYelp because that apply singleton pattern*/
//class Location {
//    public double lat, lng;
//    public Location(double lat, double lng) {
//        this.lat = lat;
//        this.lng = lng;
//    }
//}

class Trip {
    public int id; // trip's id, primary key
    public int driver_id, rider_id; // foreign key
    public double lat, lng; // pick up location
    private Trip() {}
    public static Trip create(int rider_id, double lat, double lng) {
        Trip trip = new Trip();
        trip.rider_id = rider_id;
        trip.lat = lat;
        trip.lng = lng;
        return trip;
    }
}
class Distance {
    /**
     * M is miles
     * K is kilometers
     * N is nautical meters for sailing in sea
     */
    public static double dist(double lat1, double lng1, double lat2, double lng2) {
        return dist(lat1, lng1, lat2, lng2, 'M');
    }
    public static double dist(double lat1, double lng1, double lat2, double lng2, char unit) {
        double theta = lng1 - lng2;
        double distance = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                        + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;
        if (unit == 'K') {
            distance = distance * 1.609344;
        }
        if (unit == 'N') {
            distance = distance * 0.8684;
        }
        return distance;
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}