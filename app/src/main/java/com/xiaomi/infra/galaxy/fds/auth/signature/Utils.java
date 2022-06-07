package com.xiaomi.infra.galaxy.fds.auth.signature;

import com.google.common.collect.LinkedListMultimap;
import com.xiaomi.accountsdk.utils.DateUtils;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public class Utils {
    private static final ThreadLocal<SimpleDateFormat> a = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.auth.signature.Utils.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };
    private static final ThreadLocal<SimpleDateFormat> b = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.auth.signature.Utils.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yy HH:mm:ss z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };
    private static final ThreadLocal<SimpleDateFormat> c = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.auth.signature.Utils.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.PATTERN_ASCTIME, Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };

    public static LinkedListMultimap<String, String> parseUriParameters(URI uri) {
        LinkedListMultimap<String, String> create = LinkedListMultimap.create();
        String query = uri.getQuery();
        if (query != null) {
            String[] split = query.split(MusicGroupListActivity.SPECIAL_SYMBOL);
            for (String str : split) {
                String[] split2 = str.split("=");
                if (split2.length >= 2) {
                    create.put(split2[0], str.substring(split2[0].length() + 1));
                } else {
                    create.put(split2[0], "");
                }
            }
        }
        return create;
    }

    public static Date parseDateTimeFromString(String str) {
        Date a2 = a(str, a.get());
        if (a2 == null) {
            a2 = a(str, b.get());
        }
        return a2 == null ? a(str, c.get()) : a2;
    }

    public static long parseDateTimeToMilliseconds(String str) {
        Date parseDateTimeFromString = parseDateTimeFromString(str);
        if (parseDateTimeFromString != null) {
            return parseDateTimeFromString.getTime();
        }
        return 0L;
    }

    public static String getGMTDatetime(Date date) {
        return a.get().format(date);
    }

    private static Date a(String str, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException unused) {
            return null;
        }
    }
}
