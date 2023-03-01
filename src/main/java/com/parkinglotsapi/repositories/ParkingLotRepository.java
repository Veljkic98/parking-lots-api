package com.parkinglotsapi.repositories;

import com.parkinglotsapi.domain.models.ParkingLot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends ElasticsearchRepository<ParkingLot, Integer> {

    List<ParkingLot> findAll();

}
