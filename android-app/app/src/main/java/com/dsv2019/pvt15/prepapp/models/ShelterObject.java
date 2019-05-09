package com.dsv2019.pvt15.prepapp.models;

public class ShelterObject
{
    private String address;
    private double latitude;
    private double longitude;
    private int numberOfOccupants;

    public ShelterObject(String address, double latitude, double longitude, int numberOfOccupants)
    {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numberOfOccupants = numberOfOccupants;
    }

    public String getAddress()
    {
        return address;
    }


    public double getLatitude()
    {
        return latitude;
    }


    public double getLongitude()
    {
        return longitude;
    }


    public int getNumberOfOccupants()
    {
        return numberOfOccupants;
    }


}
