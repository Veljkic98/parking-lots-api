package com.parkinglotsapi.services.impl;

import com.parkinglotsapi.domain.ParkingLotDto;
import com.parkinglotsapi.mappers.ParkingSpotMapper;
import com.parkinglotsapi.repositories.ParkingLotRepository;
import com.parkinglotsapi.services.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingLotRepository parkingSpotRepository;

    public void saveParkingSpot(final ParkingLotDto parkingSpotDto) {
        parkingSpotRepository.save(ParkingSpotMapper.toEntity(parkingSpotDto));
    }

    @Override
    public List<ParkingLotDto> getAll() {
        return parkingSpotRepository.findAll()
                .stream()
                .map(ParkingSpotMapper::toDto)
                .collect(Collectors.toList());
    }

}
