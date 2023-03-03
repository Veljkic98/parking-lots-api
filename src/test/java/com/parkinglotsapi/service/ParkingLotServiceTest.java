package com.parkinglotsapi.service;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.domain.enums.ParkingType;
import com.parkinglotsapi.domain.model.ParkingLot;
import com.parkinglotsapi.exception.BadRequestException;
import com.parkinglotsapi.exception.NotFoundException;
import com.parkinglotsapi.repository.ParkingLotRepository;
import com.parkinglotsapi.service.impl.ParkingLotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.parkinglotsapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    private ParkingLot parkingLot;

    @BeforeEach
    public void setUp() {
        parkingLot = ParkingLot.builder()
                .documentId(UUID.randomUUID())
                .location(new GeoPoint(LATITUDE, LONGITUDE))
                .name(NAME)
                .year(YEAR)
                .type(ParkingType.PARKING)
                .build();
    }

    @Test
    void shouldReturnNearestParkingLotForGivenLatAndLonIfExists() {
        when(parkingLotRepository.searchTop1By(any())).thenReturn(Optional.of(parkingLot));

        ParkingLotDto parkingLotDtoResponse = parkingLotService.getClosestParking(LATITUDE, LONGITUDE);

        assertEquals(parkingLot.getDocumentId(), parkingLotDtoResponse.getDocumentId());
        assertEquals(LATITUDE, parkingLotDtoResponse.getLatitude());
        assertEquals(LONGITUDE, parkingLotDtoResponse.getLongitude());
        assertEquals(NAME, parkingLotDtoResponse.getName());
        assertEquals(ParkingType.PARKING, parkingLotDtoResponse.getType());
    }

    @Test
    void shouldReturnNotFoundIfParkingLotNotExist() {
        when(parkingLotRepository.searchTop1By(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            parkingLotService.getClosestParking(LATITUDE, LONGITUDE);
        });

        assertTrue(exception.getMessage().contains("Parking Lot not found"));
    }

    @Test
    void shouldReturnBadRequestIfLatitudeInvalid() {
        Exception exception = assertThrows(BadRequestException.class, () -> {
            parkingLotService.getClosestParking(LATITUDE_INVALID_VALUE, LONGITUDE);
        });

        assertTrue(exception.getMessage().contains("Latitude must have value between -90 and 90, but given is"));
    }

    @Test
    void shouldReturnBadRequestIfLongitudeInvalid() {
        Exception exception = assertThrows(BadRequestException.class, () -> {
            parkingLotService.getClosestParking(LATITUDE, LONGITUDE_INVALID_VALUE);
        });

        assertTrue(exception.getMessage().contains("Longitude must have value between -180 and 180, but given is"));
    }

    @Test
    void shouldReturnParkingLotsIn1kmRadius() {
        when(parkingLotRepository.searchWithinRadius(anyDouble(), anyDouble(), anyInt())).thenReturn(List.of(parkingLot));

        List<ParkingLotDto> parkingLotDtosResponse = parkingLotService.getParkingScore(LATITUDE, LONGITUDE);

        assertFalse(parkingLotDtosResponse.isEmpty());
        assertEquals(parkingLot.getDocumentId(), parkingLotDtosResponse.get(0).getDocumentId());
        assertEquals(LATITUDE, parkingLotDtosResponse.get(0).getLatitude());
        assertEquals(LONGITUDE, parkingLotDtosResponse.get(0).getLongitude());
        assertEquals(NAME, parkingLotDtosResponse.get(0).getName());
        assertEquals(ParkingType.PARKING, parkingLotDtosResponse.get(0).getType());
    }

    @Test
    void shouldReturnEmptyListIfThereIsNoParkingLotsIn1kmRadius() {
        when(parkingLotRepository.searchWithinRadius(anyDouble(), anyDouble(), anyInt())).thenReturn(List.of());

        List<ParkingLotDto> parkingLotDtosResponse = parkingLotService.getParkingScore(LATITUDE, LONGITUDE);

        assertTrue(parkingLotDtosResponse.isEmpty());
    }

    @Test
    void shouldReturnBadRequestIfLatitudeIsInvalid() {
        Exception exception = assertThrows(BadRequestException.class, () -> {
            parkingLotService.getParkingScore(LATITUDE_INVALID_VALUE, LONGITUDE);
        });

        assertTrue(exception.getMessage().contains("Latitude must have value between -90 and 90, but given is"));
    }

    @Test
    void shouldReturnBadRequestIfLongitudeIsInvalid() {
        Exception exception = assertThrows(BadRequestException.class, () -> {
            parkingLotService.getParkingScore(LATITUDE, LONGITUDE_INVALID_VALUE);
        });

        assertTrue(exception.getMessage().contains("Longitude must have value between -180 and 180, but given is"));
    }

}
