package com.parkinglotsapi.mapper;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.domain.model.ParkingLot;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class ParkingLotMapper {

    private ParkingLotMapper() {
        // private constructor to disable instantiation of mapper/util class
    }

    public static ParkingLot toEntity(ParkingLotDto parkingLotDto) {
        return ParkingLot.builder()
                .name(parkingLotDto.getName())
                .year(parkingLotDto.getYear())
                .type(parkingLotDto.getType())
                .location(new GeoPoint(parkingLotDto.getLatitude(), parkingLotDto.getLongitude()))
                .build();
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
