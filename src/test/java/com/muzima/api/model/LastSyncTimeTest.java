/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LastSyncTimeTest {
    @Test
    public void shouldSortLastSyncTimeByAscendingDate() throws Exception {
        long date1 = 1392731464276L;

        LastSyncTime entry1 = lastSyncTime(date1);
        LastSyncTime entry2 = lastSyncTime(date1 + 100000L);
        LastSyncTime entry3 = lastSyncTime(date1 + 200000L);
        List<LastSyncTime> sortedLastSyncTimes = asList(entry2, entry1, entry3);
        Collections.sort(sortedLastSyncTimes);
        assertThat("Entry1 is inCorrect", sortedLastSyncTimes.get(0), is(entry1));
        assertThat("Entry2 is inCorrect", sortedLastSyncTimes.get(1), is(entry2));
        assertThat("Entry3 is inCorrect", sortedLastSyncTimes.get(2), is(entry3));
    }

    private LastSyncTime lastSyncTime(long milliseconds) {
        LastSyncTime lastSyncTime = new LastSyncTime();
        Date date = new Date(milliseconds);
        lastSyncTime.setLastSyncDate(date);
        return lastSyncTime;
    }
}
