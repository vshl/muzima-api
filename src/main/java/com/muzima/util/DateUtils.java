package com.muzima.util;

import com.muzima.search.api.util.ISO8601Util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility method for date manipulation
 */
public class DateUtils {
    /**
     * {@inheritDoc}
     *
     * @param syncDate Date in local time
     * @return the equilavent UTC time in ISO8601 format
     */
    public static String getUtcTimeInIso8601(Date syncDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(syncDate);
        return ISO8601Util.fromCalendar(calendar);
    }
}
