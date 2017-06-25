package com.system.design.locationBasedServiceAndRealtime;

/**
 * Created by charles on 9/19/16.
 *
 * This is a followup question for Geohash.
 Convert a Geohash string to latitude and longitude.

 Try http://geohash.co/.
 Check how to generate geohash on wiki: Geohash or just google it for more details.

 Example
 Given "wx4g0s", return lat = 39.92706299 and lng = 116.39465332.

 Return double[2], first double is latitude and second double is longitude.

Tag : Binary Search
 */
public class GeoHashAdvanced {
    /**
     * Reference
     * https://github.com/davidmoten/geo/blob/master/geo/src/main/java/com/github/davidmoten/geo/GeoHash.java
     *
     *
     *
     * @param geohash a base32 string
     * @return latitude and longitude a location coordinate pair
     * 1. get index in BASE32 by charat(i) in given geo hash
     * 2. decode integer to binary,
     * 3. if at even pos, then to calculate longitude
     *    else to calculate latitude
     * 4. use binary search to get final res;
     */
    private static String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";
    private static int[] mask = {16,8,4,2,1};

    public static double[] decode(String geohash) {
        // Write your code here
        boolean isEven = true;
        double[] lng = {-180, 180};
        double[] lat = {-90, 90};

        int pos = 0;
        for (int i = 0; i < geohash.length(); i++) {
            pos = BASE32.indexOf(geohash.charAt(i));
            for (int k = 0; k < 5; k++) {
                if (isEven) {
                    refineInterval(lng, pos, mask[k]);
                } else {
                    refineInterval(lat, pos, mask[k]);
                }
                isEven = !isEven;
            }
        }
        double[] location = new double[2];
        location[0] = (lat[0] + lat[1]) / 2;
        location[1] = (lng[0] + lng[1]) / 2;
        return location;
    }
    private static void refineInterval(double[] interval, int val, int mask) {
        /** for each bit, if current position is 1,
         * then set left = mid => will go to [mid, right]
         * else set right = mid, => go to [left, mid]
         */
        if ((val & mask) > 0) {
            interval[0] = (interval[0] + interval[1]) / 2;
        } else {
            interval[1] = (interval[0] + interval[1]) / 2;
        }
    }
}
