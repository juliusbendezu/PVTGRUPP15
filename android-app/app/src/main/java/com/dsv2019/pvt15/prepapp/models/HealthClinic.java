package com.dsv2019.pvt15.prepapp.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class HealthClinic implements ClusterItem
{
    private final LatLng position;
    private final String address;
    private final String name;
    private final Double latitude;
    private final Double longitude;

    public HealthClinic(LatLng position, String address, String name)
    {
        this.position = position;
        this.address = address;
        this.name = name;
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
        return name;
    }

    @Override
    public String getSnippet()
    {
        return address;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }
}
