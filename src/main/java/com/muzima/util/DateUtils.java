/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
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
