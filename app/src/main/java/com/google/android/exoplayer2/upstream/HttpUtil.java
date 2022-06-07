package com.google.android.exoplayer2.upstream;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.xiaomi.mipush.sdk.Constants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fourthline.cling.model.types.BytesRange;

/* loaded from: classes2.dex */
public final class HttpUtil {
    private static final Pattern a = Pattern.compile("bytes (\\d+)-(\\d+)/(?:\\d+|\\*)");
    private static final Pattern b = Pattern.compile("bytes (?:(?:\\d+-\\d+)|\\*)/(\\d+)");

    private HttpUtil() {
    }

    @Nullable
    public static String buildRangeRequestHeader(long j, long j2) {
        if (j == 0 && j2 == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(BytesRange.PREFIX);
        sb.append(j);
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        if (j2 != -1) {
            sb.append((j + j2) - 1);
        }
        return sb.toString();
    }

    public static long getDocumentSize(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return -1L;
        }
        Matcher matcher = b.matcher(str);
        if (matcher.matches()) {
            return Long.parseLong((String) Assertions.checkNotNull(matcher.group(1)));
        }
        return -1L;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getContentLength(@androidx.annotation.Nullable java.lang.String r7, @androidx.annotation.Nullable java.lang.String r8) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 != 0) goto L_0x0030
            long r0 = java.lang.Long.parseLong(r7)     // Catch: NumberFormatException -> 0x000b
            goto L_0x0032
        L_0x000b:
            java.lang.String r0 = "HttpUtil"
            java.lang.String r1 = java.lang.String.valueOf(r7)
            int r1 = r1.length()
            int r1 = r1 + 28
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r1)
            java.lang.String r1 = "Unexpected Content-Length ["
            r2.append(r1)
            r2.append(r7)
            java.lang.String r1 = "]"
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            com.google.android.exoplayer2.util.Log.e(r0, r1)
        L_0x0030:
            r0 = -1
        L_0x0032:
            boolean r2 = android.text.TextUtils.isEmpty(r8)
            if (r2 != 0) goto L_0x00d3
            java.util.regex.Pattern r2 = com.google.android.exoplayer2.upstream.HttpUtil.a
            java.util.regex.Matcher r2 = r2.matcher(r8)
            boolean r3 = r2.matches()
            if (r3 == 0) goto L_0x00d3
            r3 = 2
            java.lang.String r3 = r2.group(r3)     // Catch: NumberFormatException -> 0x00ae
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)     // Catch: NumberFormatException -> 0x00ae
            java.lang.String r3 = (java.lang.String) r3     // Catch: NumberFormatException -> 0x00ae
            long r3 = java.lang.Long.parseLong(r3)     // Catch: NumberFormatException -> 0x00ae
            r5 = 1
            java.lang.String r2 = r2.group(r5)     // Catch: NumberFormatException -> 0x00ae
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)     // Catch: NumberFormatException -> 0x00ae
            java.lang.String r2 = (java.lang.String) r2     // Catch: NumberFormatException -> 0x00ae
            long r5 = java.lang.Long.parseLong(r2)     // Catch: NumberFormatException -> 0x00ae
            long r3 = r3 - r5
            r5 = 1
            long r3 = r3 + r5
            r5 = 0
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L_0x006e
            r0 = r3
            goto L_0x00d3
        L_0x006e:
            int r2 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r2 == 0) goto L_0x00d3
            java.lang.String r2 = "HttpUtil"
            java.lang.String r5 = java.lang.String.valueOf(r7)     // Catch: NumberFormatException -> 0x00ae
            int r5 = r5.length()     // Catch: NumberFormatException -> 0x00ae
            int r5 = r5 + 26
            java.lang.String r6 = java.lang.String.valueOf(r8)     // Catch: NumberFormatException -> 0x00ae
            int r6 = r6.length()     // Catch: NumberFormatException -> 0x00ae
            int r5 = r5 + r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: NumberFormatException -> 0x00ae
            r6.<init>(r5)     // Catch: NumberFormatException -> 0x00ae
            java.lang.String r5 = "Inconsistent headers ["
            r6.append(r5)     // Catch: NumberFormatException -> 0x00ae
            r6.append(r7)     // Catch: NumberFormatException -> 0x00ae
            java.lang.String r7 = "] ["
            r6.append(r7)     // Catch: NumberFormatException -> 0x00ae
            r6.append(r8)     // Catch: NumberFormatException -> 0x00ae
            java.lang.String r7 = "]"
            r6.append(r7)     // Catch: NumberFormatException -> 0x00ae
            java.lang.String r7 = r6.toString()     // Catch: NumberFormatException -> 0x00ae
            com.google.android.exoplayer2.util.Log.w(r2, r7)     // Catch: NumberFormatException -> 0x00ae
            long r7 = java.lang.Math.max(r0, r3)     // Catch: NumberFormatException -> 0x00ae
            r0 = r7
            goto L_0x00d3
        L_0x00ae:
            java.lang.String r7 = "HttpUtil"
            java.lang.String r2 = java.lang.String.valueOf(r8)
            int r2 = r2.length()
            int r2 = r2 + 27
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "Unexpected Content-Range ["
            r3.append(r2)
            r3.append(r8)
            java.lang.String r8 = "]"
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            com.google.android.exoplayer2.util.Log.e(r7, r8)
        L_0x00d3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.HttpUtil.getContentLength(java.lang.String, java.lang.String):long");
    }
}
