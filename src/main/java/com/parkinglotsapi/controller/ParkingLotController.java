package com.parkinglotsapi.controller;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.service.ParkingLotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/parking-lots")
@Tag(name = "Parking Lots API")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping(path = "/nearest")
    @Operation(summary = "Get nearest parking lot for a given latitude and longitude")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the nearest parking lot.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingLotDto.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Latitude must have value between -90 and 90 " +
                            "and longitude must have value between -180 and 180."),
            @ApiResponse(
                    responseCode = "404",
                    description = "No parking lot founded.")
    })
    public ResponseEntity<ParkingLotDto> getClosestParking(@RequestParam(name = "lat") Double latitude,
                                                           @RequestParam(name = "lon") Double longitude) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parkingLotService.getClosestParking(latitude, longitude));
    }

    @GetMapping("/distance")
    @Operation(summary = "Get all parking lots for a given latitude and longitude in 1km radius.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all parking lots for a given latitude and longitude in 1km radius.",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParkingLotDto.class))) }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Latitude must have value between -90 and 90 " +
                            "and longitude must have value between -180 and 180."),
            @ApiResponse(
                    responseCode = "404",
                    description = "No parking lot founded.")
    })
    public ResponseEntity<List<ParkingLotDto>> getAll(@RequestParam(name = "lat") Double latitude,
                                                      @RequestParam(name = "lon") Double longitude) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parkingLotService.getParkingScore(latitude, longitude));
    }

}
