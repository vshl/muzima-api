/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.util;

import com.muzima.search.api.util.ISO8601Util;

import java.sql.Timestamp;
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

    /**
     * Compares two java.util.Date objects, but handles java.sql.Timestamp (which is not directly
     * comparable to a date) by dropping its nanosecond value.
     */
    public static int compare(Date d1, Date d2) {
        if (d1 instanceof Timestamp && d2 instanceof Timestamp) {
            return d1.compareTo(d2);
        }
        if (d1 instanceof Timestamp) {
            d1 = new Date(((Timestamp) d1).getTime());
        }
        if (d2 instanceof Timestamp) {
            d2 = new Date(((Timestamp) d2).getTime());
        }
        return d1.compareTo(d2);
    }

    /**
     * Compares two Date/Timestamp objects, treating null as the earliest possible date.
     */
    public static int compareWithNullAsEarliest(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return 0;
        }
        if (d1 == null) {
            return -1;
        } else if (d2 == null) {
            return 1;
        } else {
            return compare(d1, d2);
        }
    }

    /**
     * Compares two Date/Timestamp objects, treating null as the earliest possible date.
     */
    public static int compareWithNullAsLatest(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return 0;
        }
        if (d1 == null) {
            return 1;
        } else if (d2 == null) {
            return -1;
        } else {
            return compare(d1, d2);
        }
    }
}
