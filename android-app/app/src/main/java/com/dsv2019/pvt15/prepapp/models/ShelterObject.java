package com.dsv2019.pvt15.prepapp.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ShelterObject implements ClusterItem
{
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;


    public ShelterObject(LatLng latLng, String mTitle, String mSnippet)
    {
        this.mPosition = latLng;
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
    }

    @Override
    public LatLng getPosition()
    {
        return mPosition;
    }

    @Override
    public String getTitle()
    {
        return mTitle;
    }

    @Override
    public String getSnippet()
    {
        return mSnippet;
    }
}
