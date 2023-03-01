package com.parkinglotsapi.domain.models;

import com.parkinglotsapi.domain.enums.ParkingType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Builder
@Getter
@Setter
@Document(indexName = "parking-spot")
public class ParkingLot {

    @Id
    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Integer)
    private int year;

    @Field(type = FieldType.Keyword)
    private ParkingType type;

    @GeoPointField
    @Field(type = FieldType.Object)
    private GeoPoint location;

}
