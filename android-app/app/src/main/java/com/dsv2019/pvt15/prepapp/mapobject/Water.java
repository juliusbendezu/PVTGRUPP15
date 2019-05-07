package com.dsv2019.pvt15.prepapp.mapobject;

public class Water extends MapObject {

    private String address;

    public Water(Position pos, String address) {
        super(pos, address);
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address: "+ address;
    }
}
