package com.parkinglotsapi.mappers;

import com.parkinglotsapi.domain.ParkingLotDto;
import com.parkinglotsapi.domain.models.ParkingLot;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class ParkingSpotMapper {

    private ParkingSpotMapper() {
    }

    public static ParkingLot toEntity(ParkingLotDto parkingSpotDto) {
        return ParkingLot.builder()
                .name(parkingSpotDto.getName())
                .year(parkingSpotDto.getYear())
                .type(parkingSpotDto.getType())
                .location(new GeoPoint(parkingSpotDto.getLatitude(), parkingSpotDto.getLongitude()))
                .build();
    }

    public static ParkingLotDto toDto(ParkingLot parkingSpot) {
        return ParkingLotDto.builder()
                .name(parkingSpot.getName())
                .year(parkingSpot.getYear())
                .type(parkingSpot.getType())
                .latitude(parkingSpot.getLocation().getLat())
                .longitude(parkingSpot.getLocation().getLon())
                .build();
    }

}
