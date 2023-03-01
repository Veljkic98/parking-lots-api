package com.parkinglotsapi.config;

import com.parkinglotsapi.domain.models.ParkingLot;
import com.parkinglotsapi.repositories.ParkingLotRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ParkingLotRepositoryWriter<T> implements ItemWriter<ParkingLot> {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public void write(List<? extends ParkingLot> list) throws Exception {
        parkingLotRepository.saveAll(list);
    }

}
