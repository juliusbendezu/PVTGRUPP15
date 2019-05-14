package com.dsv2019.pvt15.prepapp.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class HealthClinic implements ClusterItem
{
    private final LatLng position;
    private final String address;
    private final String name;

    public HealthClinic(LatLng position, String address, String name)
    {
        this.position = position;
        this.address = address;
        this.name = name;
    }

    @Override
    public LatLng getPosition()
    {
        return null;
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
}
