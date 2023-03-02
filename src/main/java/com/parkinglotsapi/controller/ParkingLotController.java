package com.parkinglotsapi.controller;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.services.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping
    public ResponseEntity<ParkingLotDto> getClosestParking(@RequestParam(name = "lat") Double latitude,
                                                           @RequestParam(name = "lon") Double longitude) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parkingLotService.getClosestParking(latitude, longitude));
    }

    @GetMapping("/radius")
    public ResponseEntity<List<ParkingLotDto>> getAll(@RequestParam(name = "lat") Double latitude,
                                                      @RequestParam(name = "lon") Double longitude) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parkingLotService.getParkingScore(latitude, longitude));
    }

}
