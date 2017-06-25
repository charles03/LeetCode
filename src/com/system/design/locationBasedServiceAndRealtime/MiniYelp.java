package com.system.design.locationBasedServiceAndRealtime;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by charles on 9/19/16.
 *
 * Design a simple yelp system. Support the following features:

 Add a restaurant with name and location.
 Remove a restaurant with id.
 Search the nearby restaurants by given location.
 A location is represented by latitude and longitude, both in double. A Location class is given in code.

 Nearby is defined by distance smaller than k Km .
 Restaurant class is already provided and you can directly call Restaurant.create() to create a new object.
 Also, a Helper class is provided to calculate the distance between two location, use Helper.get_distance(location1, location2).

 A GeoHash class is provided to convert a location to a string.
 Try GeoHash.encode(location) to convert location to a geohash string and GeoHash.decode(string) to convert a string to location.

 Example
 addRestauraunt("Lint Cafe", 10.4999999, 11.599999) // return 1
 addRestauraunt("Code Cafe", 10.4999999, 11.512109) // return 2
 neighbors(10.5, 11.6, 6.7) // return ["Lint Cafe"]
 removeRestauraunt(1)
 neighbors(10.5, 9.6, 6.7) // return []


 // The distance between location(10.5, 11.6) and "Lint Code" is 0.0001099 km
 // The distance between location(10.5, 11.6) and "Code Code" is 9.6120978 km
 */
public class MiniYelp {
    private NavigableMap<String, Restaurant> restaurantMap; // key is geohash code geo(lat,lng) + "." + id;
    private Map<Integer, String> geoMap; // key is restaurant id, value is geohash coded by lat/lng
    private int precision = 10; // precision between 1~12
    // this is to get precision based on hashcode substring length
    private double[] ERROR = {2500, 630, 78, 20, 2.4, 0.61, 0.076, 0.01911, 0.00478, 0.0005971, 0.0001492, 0.0000186};

    public MiniYelp() {
        restaurantMap = new TreeMap<>();
        geoMap = new HashMap<>();
    }

    // @param name a string
    // @param location a Location
    // @return an integer, restaurant's id
    public int addRestaurant(String name, Location location) {
        Restaurant restaurant = Restaurant.create(name, location);
        /** need to encode geohash */
        String hashcode = GeoHash.encode(location.latitude, location.longitude, precision)
                            + "." + restaurant.id;
        geoMap.put(restaurant.id, hashcode);
        restaurantMap.put(hashcode, restaurant);
        return restaurant.id;
    }

    // @param restaurant_id an integer
    public void removeRestaurant(int restaurant_id) {
        if (!geoMap.containsKey(restaurant_id)) {
            return;
        }
        String hashcode = geoMap.get(restaurant_id);
        geoMap.remove(restaurant_id);
        if (restaurantMap.containsKey(hashcode)) {
            restaurantMap.remove(hashcode);
        }
    }

    // @param location a Location
    // @param k an integer, distance smaller than k miles
    // @return a list of restaurant's name and sort by
    // distance from near to far.
    public List<String> neighbors(Location location, double range) {
        List<String> res = new ArrayList<>();
        int precision = getPrecision(range);
        String hashcode = GeoHash.encode(location.latitude, location.longitude, precision);

        List<Node> list = new ArrayList<>();
        String geoKey = null;
        Restaurant restaurant = null;
        Location candidate = null;
        double distance = 0.0;
        for (Map.Entry<String, Restaurant> entry :
                restaurantMap.subMap(hashcode, true, hashcode+"#", true).entrySet()) {
            geoKey = entry.getKey();
            restaurant = entry.getValue();
            candidate = restaurant.location;
            distance = Distance.dist(location.latitude, location.longitude, candidate.latitude, candidate.longitude);
            if (distance <= range) {
                list.add(new Node(distance, restaurant));
            }
        }
        Collections.sort(list, (o1, o2) -> (int)(o1.distance - o2.distance));

        for (Node node : list) {
            res.add(node.restaurant.name);
        }
        return res;
    }
    private int getPrecision(double range) {
        /** based on error tolerance to get precision of geohash by given distance range */
        for (int i = 0; i < 12; i++) {
            if (range > ERROR[i]) {
                return i;
            }
        }
        return 12;
    }
}

class Node {
    public double distance;
    public Restaurant restaurant;
    public Node(double d, Restaurant r) {
        this.distance = d;
        this.restaurant = r;
    }
}

class Location {
    private Location() {}
    public double latitude, longitude;
    public static Location create(double latitude, double longitude) {
        Location loc = new Location();
        loc.latitude = latitude;
        loc.longitude = longitude;
        return loc;
    }
}

class Restaurant {
    private static int INIT = 1;
    private static AtomicInteger idGen = new AtomicInteger(INIT);

    public int id;
    public String name;
    public Location location;
    private Restaurant() {}
    public static Restaurant create(String name, Location location) {
        Restaurant restaurant = new Restaurant();
        restaurant.name = name;
        restaurant.location = location;
        restaurant.id = idGen.getAndIncrement();
        return restaurant;
    }
}

