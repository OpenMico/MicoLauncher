package org.seamless.util;

import android.support.v4.media.session.PlaybackStateCompat;
import com.xiaomi.infra.galaxy.fds.Constants;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class Text {
    public static final String ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssz";

    public static String ltrim(String str) {
        return str.replaceAll("(?s)^\\s+", "");
    }

    public static String rtrim(String str) {
        return str.replaceAll("(?s)\\s+$", "");
    }

    public static String displayFilesize(long j) {
        if (j >= Constants.DEFAULT_SPACE_LIMIT) {
            return new BigDecimal(((j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + " GiB";
        } else if (j >= 1048576) {
            return new BigDecimal((j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + " MiB";
        } else if (j >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return new BigDecimal(j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + " KiB";
        } else {
            return new BigDecimal(j) + " bytes";
        }
    }

    public static Calendar fromISO8601String(TimeZone timeZone, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO8601_PATTERN);
        simpleDateFormat.setTimeZone(timeZone);
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(simpleDateFormat.parse(str));
            return gregorianCalendar;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toISO8601String(TimeZone timeZone, Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return toISO8601String(timeZone, gregorianCalendar);
    }

    public static String toISO8601String(TimeZone timeZone, long j) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(j);
        return toISO8601String(timeZone, gregorianCalendar);
    }

    public static String toISO8601String(TimeZone timeZone, Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO8601_PATTERN);
        simpleDateFormat.setTimeZone(timeZone);
        try {
            return simpleDateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
