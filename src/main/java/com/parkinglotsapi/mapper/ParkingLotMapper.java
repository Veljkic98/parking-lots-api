package com.parkinglotsapi.mapper;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.domain.model.ParkingLot;

public class ParkingLotMapper {

    private ParkingLotMapper() {
        // private constructor to disable instantiation of mapper/util class
    }

    public static ParkingLotDto toDto(ParkingLot parkingLot) {
        return ParkingLotDto.builder()
                .documentId(parkingLot.getDocumentId())
                .name(parkingLot.getName())
                .year(parkingLot.getYear())
                .type(parkingLot.getType())
                .latitude(parkingLot.getLocation().getLat())
                .longitude(parkingLot.getLocation().getLon())
                .build();
    }

}
