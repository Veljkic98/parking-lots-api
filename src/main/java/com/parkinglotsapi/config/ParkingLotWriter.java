package com.parkinglotsapi.config;

import com.parkinglotsapi.domain.model.ParkingLot;
import com.parkinglotsapi.repository.ParkingLotRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.List;

public class ParkingLotWriter<T> implements ItemWriter<ParkingLot> {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public void write(@Nonnull List<? extends ParkingLot> data) {
        parkingLotRepository.saveAll(data);
    }

}
