package com.parkinglotsapi.services;

import com.parkinglotsapi.domain.dto.ParkingLotDto;

import java.util.List;

public interface ParkingLotService {

    ParkingLotDto getClosestParking(double latitude, double longitude);

    List<ParkingLotDto> getParkingScore(double latitude, double longitude);

}
