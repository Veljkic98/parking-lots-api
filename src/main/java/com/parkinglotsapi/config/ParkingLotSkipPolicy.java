package com.parkinglotsapi.config;

import com.parkinglotsapi.exception.BatchSkipException;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import javax.annotation.Nonnull;

public class ParkingLotSkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(@Nonnull Throwable t, int i) throws SkipLimitExceededException {
        return t instanceof BatchSkipException;
    }

}
