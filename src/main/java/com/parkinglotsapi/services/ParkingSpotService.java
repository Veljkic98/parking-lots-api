package com.parkinglotsapi.services;

import com.parkinglotsapi.domain.ParkingLotDto;

import java.util.List;

public interface ParkingSpotService {

    void saveParkingSpot(final ParkingLotDto parkingSpotDto);

    List<ParkingLotDto> getAll();

}
