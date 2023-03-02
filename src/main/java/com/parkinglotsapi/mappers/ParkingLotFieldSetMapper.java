package com.parkinglotsapi.mappers;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.domain.enums.ParkingType;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ParkingLotFieldSetMapper implements FieldSetMapper<ParkingLotDto> {

    @Override
    public ParkingLotDto mapFieldSet(FieldSet fieldSet) throws BindException {
        return ParkingLotDto.builder()
                .name(fieldSet.readString("A_Name"))
                .type(ParkingType.valueOf(fieldSet.readString("Type").toUpperCase()))
                .year(fieldSet.readInt("Year"))
                .latitude(fieldSet.readDouble("Latitude"))
                .longitude(fieldSet.readDouble("Longitude"))
                .build();
    }

}
