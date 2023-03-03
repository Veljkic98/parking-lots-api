package com.parkinglotsapi.domain.enums;

public enum ParkingType {

    PARKING("Parking"),
    HOME("Home");

    private final String name;

    ParkingType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
