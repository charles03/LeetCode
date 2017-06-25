package com.system.design.locationBasedServiceAndRealtime;

/**
 * Created by charles on 9/19/16.
 *
 * Geohash is a hash function that convert a location coordinate pair into a base32 string.
 Check how to generate geohash on wiki: Geohash or just google it for more details.

 Try http://geohash.co/.
 You task is converting a (latitude, longitude) pair into a geohash string.

 Example
 Given lat = 39.92816697, lng = 116.38954991 and precision = 12 which indicate how long the hash string should be.
 You should return wx4g0s8q3jf9.

 The precision is between 1 ~ 12.
 */
public class GeoHash {
    /**
     * @param latitude, longitude a location coordinate pair
     * @param precision an integer between 1 to 12
     * @return a base32 string
     */
    // without a,i,l,o
    private static String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    public static String encode(double latitude, double longitude, int precision) {
        String latBinary = getBinary(latitude, -90, 90);
        String lngBinary = getBinary(longitude, -180, 180);
        // combine both latBin and lngBin, interactively
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            sb.append(lngBinary.charAt(i)); // first longitude
            sb.append(latBinary.charAt(i)); // then latitude
        }
        /** why use base 32 and interval is 5
         * is 2^5 = 32, is int size in cpu/machine
         */
        int index = 0;
        StringBuilder geoHash = new StringBuilder();
        for (int i = 0; i < 60; i += 5) {
            index = bin2int(sb.substring(i, i+5));
            geoHash.append(BASE32.charAt(index));
        }
        return geoHash.substring(0, precision);
    }
    private static int bin2int(String binary) {
        return Integer.parseInt(binary, 2); // use java api, given 2 as radix
    }
    /** use binary search method to generate 0-1 string */
    private static String getBinary(double value, double left, double right) {
        StringBuilder sb = new StringBuilder();
        double mid = 0.0;
        for (int i = 0; i < 30; i++) {
            mid = (left + right) / 2.0;
            if (value > mid) {
                left = mid;
                sb.append("1");
            } else {
                right = mid;
                sb.append("0");
            }
        }
        return sb.toString();
    }
}
