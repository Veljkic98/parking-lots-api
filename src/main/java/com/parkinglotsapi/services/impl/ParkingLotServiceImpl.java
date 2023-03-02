package com.parkinglotsapi.services.impl;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.domain.models.ParkingLot;
import com.parkinglotsapi.exceptions.NotFoundException;
import com.parkinglotsapi.mappers.ParkingLotMapper;
import com.parkinglotsapi.repositories.ParkingLotRepository;
import com.parkinglotsapi.services.ParkingLotService;
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
        GeoPoint location = new GeoPoint(latitude, longitude);
        Sort sort = Sort.by(new GeoDistanceOrder("location", location).withUnit("m"));
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.searchTop1By(sort);

        if (parkingLotOptional.isEmpty()) {
            LOGGER.warn("No record could be found because database is empty.");
            throw new NotFoundException("Empty database");
        }

        return ParkingLotMapper.toDto(parkingLotOptional.get());
    }

    @Override
    public List<ParkingLotDto> getParkingScore(double latitude, double longitude) {
        return parkingLotRepository.searchWithinRadius(latitude, longitude, searchDistance)
                .stream()
                .map(ParkingLotMapper::toDto)
                .collect(Collectors.toList());

    }

}
