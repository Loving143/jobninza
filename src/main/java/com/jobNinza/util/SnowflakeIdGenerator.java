package com.jobNinza.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {

    private static final long EPOCH = 1704067200000L;
    // 01-Jan-2024 00:00:00 UTC

    private static final long WORKER_ID_BITS = 10;
    private static final long SEQUENCE_BITS = 12;

    private static final long MAX_WORKER_ID =
            (1L << WORKER_ID_BITS) - 1;

    private static final long MAX_SEQUENCE =
            (1L << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_SHIFT =
            SEQUENCE_BITS;

    private static final long TIMESTAMP_SHIFT =
            SEQUENCE_BITS + WORKER_ID_BITS;

    private final long workerId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;


    public SnowflakeIdGenerator() {

        // For a single application instance
        this.workerId = 1L;

        if (workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException(
                    "Worker ID cannot be greater than "
                            + MAX_WORKER_ID
            );
        }
    }


    public synchronized Long nextId() {

        long currentTimestamp =
                System.currentTimeMillis();

        // Clock moved backwards
        if (currentTimestamp < lastTimestamp) {

            throw new IllegalStateException(
                    "Clock moved backwards. Refusing to generate ID."
            );
        }

        // Same millisecond
        if (currentTimestamp == lastTimestamp) {

            sequence =
                    (sequence + 1) & MAX_SEQUENCE;

            // Sequence exhausted
            if (sequence == 0) {

                currentTimestamp =
                        waitForNextMillisecond(
                                lastTimestamp
                        );
            }

        } else {

            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - EPOCH)
                << TIMESTAMP_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }


    private long waitForNextMillisecond(
            long lastTimestamp) {

        long timestamp =
                System.currentTimeMillis();

        while (timestamp <= lastTimestamp) {

            timestamp =
                    System.currentTimeMillis();
        }

        return timestamp;
    }
}