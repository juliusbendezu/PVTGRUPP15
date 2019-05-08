package com.dsv2019.pvt15.prepapp.maprelated;

public class Hospital extends MapObject {

    private String name;
    private String address;

    public Hospital(Position pos, String address, String name) {
        super(pos, address);
        this.name = name;
        this.address= address;
    }

    @Override
    public String toString() {
        return name + "\n" + " Adress: " + address;
    }
}
