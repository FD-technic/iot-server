package cz.ferdo.iot_server.core;

import cz.ferdo.iot_server.measurement.enums.Period;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Service
public class PeriodService {

    public LocalDateTime findDateFrom(Period period) {
        return switch (period) {
            case DAY -> now().minusDays(1);
            case WEEK -> now().minusWeeks(1);
            case MONTH -> now().minusMonths(1);
            case QUARTER -> now().minusMonths(3);
            case HALF -> now().minusMonths(6);
            case YEAR -> now().minusYears(1);
            case ALL -> throw new IllegalStateException("ALL should be handled before switch");
        };
    }
}
