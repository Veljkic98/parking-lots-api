package com.parkinglotsapi.service.impl;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.domain.model.ParkingLot;
import com.parkinglotsapi.exception.BadRequestException;
import com.parkinglotsapi.exception.NotFoundException;
import com.parkinglotsapi.mapper.ParkingLotMapper;
import com.parkinglotsapi.repository.ParkingLotRepository;
import com.parkinglotsapi.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingLotServiceImpl.class);

    private final ParkingLotRepository parkingLotRepository;

    @Value("${search.distance}")
    private int searchDistance;

    @Override
    public ParkingLotDto getClosestParking(double latitude, double longitude) {
        validateLatAndLot(latitude, longitude);

        GeoPoint location = new GeoPoint(latitude, longitude);
        Sort sort = Sort.by(new GeoDistanceOrder("location", location).withUnit("m"));
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.searchTop1By(sort);

        if (parkingLotOptional.isEmpty()) {
            LOGGER.warn("No record could be found because database is empty.");
            throw new NotFoundException("Parking Lot not found. Empty database.");
        }

        return ParkingLotMapper.toDto(parkingLotOptional.get());
    }

    @Override
    public List<ParkingLotDto> getParkingScore(double latitude, double longitude) {
        validateLatAndLot(latitude, longitude);

        return parkingLotRepository.searchWithinRadius(latitude, longitude, searchDistance)
                .stream()
                .map(ParkingLotMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateLatAndLot(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90) {
            String errorMessage = String.format("Latitude must have value between -90 and 90, but given is %s", latitude);
            LOGGER.error(errorMessage);
            throw new BadRequestException(errorMessage);
        }

        if (longitude < -180 || longitude > 180) {
            String errorMessage = String.format("Longitude must have value between -180 and 180, but given is %s", longitude);
            LOGGER.error(errorMessage);
            throw new BadRequestException(errorMessage);
        }
    }

}
