package com.parkinglotsapi.config;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class ParkingLotSkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, int i) throws SkipLimitExceededException {
        return t instanceof Exception;
    }

}
