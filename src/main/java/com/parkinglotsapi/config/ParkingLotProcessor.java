package com.parkinglotsapi.config;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.domain.model.ParkingLot;
import com.parkinglotsapi.exception.BatchSkipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ParkingLotProcessor implements ItemProcessor<ParkingLotDto, ParkingLot> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingLotProcessor.class);

    @Override
    public ParkingLot process(@Nonnull ParkingLotDto parkingLotDto) {
        if (!isValid(parkingLotDto)) {
            LOGGER.warn(String.format("Error while trying to parse ParkingLotDto to ParkingLot. Skip record: %s", parkingLotDto));
            throw new BatchSkipException("Could not process record");
        }
        return ParkingLot.builder()
                .documentId(UUID.randomUUID())
                .name(parkingLotDto.getName())
                .type(parkingLotDto.getType())
                .year(parkingLotDto.getYear())
                .location(new GeoPoint(parkingLotDto.getLatitude(), parkingLotDto.getLongitude()))
                .build();
    }

    private boolean isValid(ParkingLotDto parkingLotDto) {
        return parkingLotDto.getLatitude() != null &&
                parkingLotDto.getLongitude() != null &&
                parkingLotDto.getYear() != null &&
                !parkingLotDto.getName().isEmpty();
    }

}
