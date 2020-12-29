package com.fqc.common.utils;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class DistanceUtil {

    private static final double EARTH_RADIUS = 6378137.0;

    //TODO 待实现
    public static String getDistance(String coord, double locationLatitude, double locationLongitude) {
        String movieLatitude = coord.substring(0, coord.indexOf(","));
        String movieLongitude = coord.substring(coord.indexOf(",") + 1);
        double distance = getDistancess(Double.parseDouble(movieLongitude), Double.parseDouble(movieLatitude),
                locationLongitude, locationLatitude);

        return String.valueOf(distance + "km");
    }

    public static double getDistancess(double longitude1, double latitude1,
                                       double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000 / 1000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
