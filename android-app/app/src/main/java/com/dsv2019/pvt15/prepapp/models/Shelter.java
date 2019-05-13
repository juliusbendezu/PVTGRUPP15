package com.dsv2019.pvt15.prepapp.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Shelter implements ClusterItem
{
    private final LatLng position;
    private final String address;
    private final String numberOfOccupants;


    public Shelter(LatLng latLng, String mTitle, String mSnippet)
    {
        this.position = latLng;
        this.address = mTitle;
        this.numberOfOccupants = mSnippet;
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
        return numberOfOccupants;
    }
}
