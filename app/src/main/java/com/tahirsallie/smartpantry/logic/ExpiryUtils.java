package com.tahirsallie.smartpantry.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class ExpiryUtils {

    /** Items expiring within this many days count as "expiring soon". */
    public static final int SOON_DAYS = 3;

    private ExpiryUtils() {}

    /**
     * Whole days from today until the date ("yyyy-MM-dd").
     * Negative = already expired, 0 = today. Returns null if the date is empty or invalid.
     */
    public static Integer daysUntilExpiry(String date) {
        if (date == null || date.trim().isEmpty()) return null;

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        fmt.setLenient(false);
        try {
            Date expiry = fmt.parse(date.trim());
            if (expiry == null) return null;

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            long diffMillis = expiry.getTime() - today.getTimeInMillis();
            return (int) Math.round(diffMillis / 86400000.0);   // round absorbs DST shifts
        } catch (ParseException e) {
            return null;
        }
    }
}
