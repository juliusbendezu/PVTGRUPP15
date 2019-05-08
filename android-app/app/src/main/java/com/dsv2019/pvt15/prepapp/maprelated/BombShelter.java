package com.dsv2019.pvt15.prepapp.maprelated;

public class BombShelter extends MapObject {

    int capacity;
    String address;

    public BombShelter(Position pos, String address, int capacity) {
        super(pos, address);
        this.capacity = capacity;
        this.address = address;
    }

    public String toString(){
        return "Addressen är: " + address + "\n" + " Kappacitet " + capacity + " antal personer.";
    }

}
