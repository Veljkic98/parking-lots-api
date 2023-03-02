package com.parkinglotsapi.domain.dto;

import com.parkinglotsapi.domain.enums.ParkingType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotDto {

    private UUID documentId;

    private String name;

    private Integer year;

    private ParkingType type;

    private Double latitude;

    private Double longitude;

}
