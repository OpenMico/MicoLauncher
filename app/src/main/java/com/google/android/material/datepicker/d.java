package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: DateStrings.java */
/* loaded from: classes2.dex */
class d {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(Context context, long j) {
        return DateUtils.formatDateTime(context, j - TimeZone.getDefault().getOffset(j), 36);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(long j) {
        return a(j, Locale.getDefault());
    }

    static String a(long j, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return k.a(locale).format(new Date(j));
        }
        return k.e(locale).format(new Date(j));
    }

    static String b(long j) {
        return b(j, Locale.getDefault());
    }

    static String b(long j, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return k.b(locale).format(new Date(j));
        }
        return k.f(locale).format(new Date(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String c(long j) {
        return c(j, Locale.getDefault());
    }

    static String c(long j, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return k.c(locale).format(new Date(j));
        }
        return k.g(locale).format(new Date(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String d(long j) {
        return d(j, Locale.getDefault());
    }

    static String d(long j, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return k.d(locale).format(new Date(j));
        }
        return k.g(locale).format(new Date(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String e(long j) {
        return a(j, (SimpleDateFormat) null);
    }

    static String a(long j, @Nullable SimpleDateFormat simpleDateFormat) {
        Calendar b = k.b();
        Calendar c = k.c();
        c.setTimeInMillis(j);
        if (simpleDateFormat != null) {
            return simpleDateFormat.format(new Date(j));
        }
        if (b.get(1) == c.get(1)) {
            return b(j);
        }
        return a(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Pair<String, String> a(@Nullable Long l, @Nullable Long l2) {
        return a(l, l2, null);
    }

    static Pair<String, String> a(@Nullable Long l, @Nullable Long l2, @Nullable SimpleDateFormat simpleDateFormat) {
        if (l == null && l2 == null) {
            return Pair.create(null, null);
        }
        if (l == null) {
            return Pair.create(null, a(l2.longValue(), simpleDateFormat));
        }
        if (l2 == null) {
            return Pair.create(a(l.longValue(), simpleDateFormat), null);
        }
        Calendar b = k.b();
        Calendar c = k.c();
        c.setTimeInMillis(l.longValue());
        Calendar c2 = k.c();
        c2.setTimeInMillis(l2.longValue());
        if (simpleDateFormat != null) {
            return Pair.create(simpleDateFormat.format(new Date(l.longValue())), simpleDateFormat.format(new Date(l2.longValue())));
        } else if (c.get(1) != c2.get(1)) {
            return Pair.create(a(l.longValue(), Locale.getDefault()), a(l2.longValue(), Locale.getDefault()));
        } else {
            if (c.get(1) == b.get(1)) {
                return Pair.create(b(l.longValue(), Locale.getDefault()), b(l2.longValue(), Locale.getDefault()));
            }
            return Pair.create(b(l.longValue(), Locale.getDefault()), a(l2.longValue(), Locale.getDefault()));
        }
    }
}
