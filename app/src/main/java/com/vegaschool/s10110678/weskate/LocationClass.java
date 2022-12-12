package com.vegaschool.s10110678.weskate;
import java.lang.Math;

public class LocationClass {
    public long longitude;
    public long latitude;
    public String name;

    public LocationClass(long _longitude, long _latitude, String _name){
        longitude = _longitude;
        latitude = _latitude;
        name = _name;
    }

    @Override public String toString(){
        char _long;
        char _lat;

        if(longitude > 0) _long = 'S';
        else _long = 'N';

        if(latitude > 0) _lat = 'E';
        else _lat = 'W';

        return name + " at:\n" + Math.abs(longitude) + "°" + _long + ", " + Math.abs(latitude) + "°" + _lat ;
    }

}
