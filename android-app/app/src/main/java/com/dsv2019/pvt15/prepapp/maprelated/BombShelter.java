package com.dsv2019.pvt15.prepapp.maprelated;

import com.google.android.gms.maps.model.LatLng;

public class BombShelter extends MapObject
{

    private int capacity;
    private String address;

    public BombShelter(Position pos, String address, int capacity)
    {
        super(pos, address);
        this.capacity = capacity;
        this.address = address;
    }

    public String toString()
    {
        return "Addressen är: " + address + "\n" + " Kapacitet " + capacity + " antal personer.";
    }

}
