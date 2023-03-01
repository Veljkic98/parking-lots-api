package com.parkinglotsapi.controller;

import com.parkinglotsapi.domain.ParkingLotDto;
import com.parkinglotsapi.services.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/parking-spot")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ParkingLotDto parkingSpotDto) {
        parkingSpotService.saveParkingSpot(parkingSpotDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parkingSpotService.getAll());
    }

}
