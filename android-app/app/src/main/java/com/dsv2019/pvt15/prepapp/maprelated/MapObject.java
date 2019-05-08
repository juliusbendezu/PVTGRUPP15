package com.dsv2019.pvt15.prepapp.maprelated;

abstract public class MapObject
{
    private Position pos;
    private String address;

    public MapObject(Position pos, String address)
    {
        this.pos = pos;
        this.address = address;
    }

    public Position getPos()
    {
        return pos;
    }

    abstract public String toString();
}
