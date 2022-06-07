package com.google.android.material.datepicker;

import androidx.annotation.Nullable;
import java.util.Calendar;
import java.util.TimeZone;

/* compiled from: TimeSource.java */
/* loaded from: classes2.dex */
class j {
    private static final j a = new j(null, null);
    @Nullable
    private final Long b;
    @Nullable
    private final TimeZone c;

    private j(@Nullable Long l, @Nullable TimeZone timeZone) {
        this.b = l;
        this.c = timeZone;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static j a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Calendar b() {
        return a(this.c);
    }

    Calendar a(@Nullable TimeZone timeZone) {
        Calendar instance = timeZone == null ? Calendar.getInstance() : Calendar.getInstance(timeZone);
        Long l = this.b;
        if (l != null) {
            instance.setTimeInMillis(l.longValue());
        }
        return instance;
    }
}
