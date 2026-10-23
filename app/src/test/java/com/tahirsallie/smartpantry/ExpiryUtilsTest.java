package com.tahirsallie.smartpantry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.tahirsallie.smartpantry.logic.ExpiryUtils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExpiryUtilsTest {

    private static String dayOffset(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, days);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(c.getTime());
    }

    @Test
    public void today_isZeroDays() {
        assertEquals(Integer.valueOf(0), ExpiryUtils.daysUntilExpiry(dayOffset(0)));
    }

    @Test
    public void future_isPositive() {
        assertEquals(Integer.valueOf(2), ExpiryUtils.daysUntilExpiry(dayOffset(2)));
    }

    @Test
    public void past_isNegative() {
        assertEquals(Integer.valueOf(-1), ExpiryUtils.daysUntilExpiry(dayOffset(-1)));
    }

    @Test
    public void emptyOrInvalid_returnsNull() {
        assertNull(ExpiryUtils.daysUntilExpiry(""));
        assertNull(ExpiryUtils.daysUntilExpiry(null));
        assertNull(ExpiryUtils.daysUntilExpiry("not-a-date"));
        assertNull(ExpiryUtils.daysUntilExpiry("2026-13-45"));
    }
}
