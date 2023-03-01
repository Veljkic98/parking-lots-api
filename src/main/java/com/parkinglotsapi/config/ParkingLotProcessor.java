package com.parkinglotsapi.config;

import com.parkinglotsapi.domain.ParkingLotDto;
import com.parkinglotsapi.domain.enums.ParkingType;
import com.parkinglotsapi.domain.models.ParkingLot;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class ParkingLotProcessor implements ItemProcessor<ParkingLotDto, ParkingLot> {

    @Override
    public ParkingLot process(ParkingLotDto parkingLotDto) throws Exception {
        if (!isValid(parkingLotDto)) {
            throw new Exception("");
        }
        return ParkingLot.builder()
                .name(parkingLotDto.getName())
                .type(parkingLotDto.getType())
                .year(parkingLotDto.getYear())
                .location(new GeoPoint(parkingLotDto.getLatitude(), parkingLotDto.getLongitude()))
                .build();
    }

    private boolean isValid(ParkingLotDto parkingLotDto) {
        return parkingLotDto.getLatitude() != null &&
                parkingLotDto.getLongitude() != null &&
                parkingLotDto.getYear() == 2018 &&
                parkingLotDto.getType().equals(ParkingType.PARKING);
    }

}
