package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: RtspSessionTiming.java */
/* loaded from: classes2.dex */
public final class j {
    public static final j a = new j(0, C.TIME_UNSET);
    private static final Pattern d = Pattern.compile("npt=([.\\d]+|now)\\s?-\\s?([.\\d]+)?");
    public final long b;
    public final long c;

    public static j a(String str) throws ParserException {
        long parseFloat;
        Matcher matcher = d.matcher(str);
        Assertions.checkArgument(matcher.matches());
        boolean z = true;
        String str2 = (String) Assertions.checkNotNull(matcher.group(1));
        long parseFloat2 = str2.equals("now") ? 0L : Float.parseFloat(str2) * 1000.0f;
        String group = matcher.group(2);
        if (group != null) {
            try {
                parseFloat = Float.parseFloat(group) * 1000.0f;
                if (parseFloat <= parseFloat2) {
                    z = false;
                }
                Assertions.checkArgument(z);
            } catch (NumberFormatException e) {
                throw ParserException.createForMalformedManifest(group, e);
            }
        } else {
            parseFloat = C.TIME_UNSET;
        }
        return new j(parseFloat2, parseFloat);
    }

    public static String a(long j) {
        return Util.formatInvariant("npt=%.3f-", Double.valueOf(j / 1000.0d));
    }

    private j(long j, long j2) {
        this.b = j;
        this.c = j2;
    }

    public boolean a() {
        return this.c == C.TIME_UNSET;
    }

    public long b() {
        return this.c - this.b;
    }
}
