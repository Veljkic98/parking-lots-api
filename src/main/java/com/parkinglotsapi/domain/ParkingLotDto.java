package com.parkinglotsapi.domain;

import com.parkinglotsapi.domain.enums.ParkingType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotDto {

    private String name;

    private Integer year;

    private ParkingType type;

    private Double latitude;

    private Double longitude;

}
