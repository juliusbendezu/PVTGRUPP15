package com.dsv2019.pvt15.prepapp.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Shelter implements ClusterItem
{
    private final LatLng position;
    private final String address;
    private final String numberOfOccupants;
    private final Double latitude;
    private final Double longitude;

    public Double getLatitude()
    {
        return latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public Shelter(LatLng position, String address, String numberOfOccupants)
    {
        this.position = position;
        this.address = address;
        this.numberOfOccupants = numberOfOccupants;
        this.latitude = position.latitude;
        this.longitude = position.longitude;
    }

    @Override
    public LatLng getPosition()
    {
        return position;
    }

    @Override
    public String getTitle()
    {
        return address;
    }

    @Override
    public String getSnippet()
    {
        return "" + numberOfOccupants;
    }
}
