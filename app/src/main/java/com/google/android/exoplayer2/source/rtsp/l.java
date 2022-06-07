package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UriUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RtspTrackTiming.java */
/* loaded from: classes2.dex */
public final class l {
    public final long a;
    public final int b;
    public final Uri c;

    /* JADX WARN: Removed duplicated region for block: B:26:0x0075 A[Catch: Exception -> 0x0092, TryCatch #0 {Exception -> 0x0092, blocks: (B:7:0x0027, B:14:0x0049, B:17:0x0055, B:20:0x0061, B:24:0x006c, B:25:0x006f, B:26:0x0075, B:27:0x007c, B:28:0x0084, B:29:0x008b, B:30:0x0091), top: B:49:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007c A[Catch: Exception -> 0x0092, TryCatch #0 {Exception -> 0x0092, blocks: (B:7:0x0027, B:14:0x0049, B:17:0x0055, B:20:0x0061, B:24:0x006c, B:25:0x006f, B:26:0x0075, B:27:0x007c, B:28:0x0084, B:29:0x008b, B:30:0x0091), top: B:49:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0084 A[Catch: Exception -> 0x0092, TryCatch #0 {Exception -> 0x0092, blocks: (B:7:0x0027, B:14:0x0049, B:17:0x0055, B:20:0x0061, B:24:0x006c, B:25:0x006f, B:26:0x0075, B:27:0x007c, B:28:0x0084, B:29:0x008b, B:30:0x0091), top: B:49:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x006f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.common.collect.ImmutableList<com.google.android.exoplayer2.source.rtsp.l> a(java.lang.String r18, android.net.Uri r19) throws com.google.android.exoplayer2.ParserException {
        /*
            com.google.common.collect.ImmutableList$Builder r0 = new com.google.common.collect.ImmutableList$Builder
            r0.<init>()
            java.lang.String r1 = ","
            r2 = r18
            java.lang.String[] r1 = com.google.android.exoplayer2.util.Util.split(r2, r1)
            int r2 = r1.length
            r3 = 0
            r4 = r3
        L_0x0010:
            if (r4 >= r2) goto L_0x00c7
            r5 = r1[r4]
            java.lang.String r6 = ";"
            java.lang.String[] r6 = com.google.android.exoplayer2.util.Util.split(r5, r6)
            int r7 = r6.length
            r12 = r3
            r8 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r13 = 0
            r14 = -1
        L_0x0023:
            if (r12 >= r7) goto L_0x0098
            r15 = r6[r12]
            java.lang.String r11 = "="
            java.lang.String[] r11 = com.google.android.exoplayer2.util.Util.splitAtFirst(r15, r11)     // Catch: Exception -> 0x0092
            r10 = r11[r3]     // Catch: Exception -> 0x0092
            r16 = 1
            r11 = r11[r16]     // Catch: Exception -> 0x0092
            int r3 = r10.hashCode()     // Catch: Exception -> 0x0092
            r17 = r1
            r1 = 113759(0x1bc5f, float:1.5941E-40)
            if (r3 == r1) goto L_0x0061
            r1 = 116079(0x1c56f, float:1.62661E-40)
            if (r3 == r1) goto L_0x0055
            r1 = 1524180539(0x5ad9263b, float:3.05610524E16)
            if (r3 == r1) goto L_0x0049
            goto L_0x006a
        L_0x0049:
            java.lang.String r1 = "rtptime"
            boolean r1 = r10.equals(r1)     // Catch: Exception -> 0x0092
            if (r1 == 0) goto L_0x006a
            r3 = 2
            r16 = r3
            goto L_0x006c
        L_0x0055:
            java.lang.String r1 = "url"
            boolean r1 = r10.equals(r1)     // Catch: Exception -> 0x0092
            if (r1 == 0) goto L_0x006a
            r16 = 0
            goto L_0x006c
        L_0x0061:
            java.lang.String r1 = "seq"
            boolean r1 = r10.equals(r1)     // Catch: Exception -> 0x0092
            if (r1 == 0) goto L_0x006a
            goto L_0x006c
        L_0x006a:
            r16 = -1
        L_0x006c:
            switch(r16) {
                case 0: goto L_0x0084;
                case 1: goto L_0x007c;
                case 2: goto L_0x0075;
                default: goto L_0x006f;
            }     // Catch: Exception -> 0x0092
        L_0x006f:
            r0 = 0
            com.google.android.exoplayer2.ParserException r0 = com.google.android.exoplayer2.ParserException.createForMalformedManifest(r10, r0)     // Catch: Exception -> 0x0092
            goto L_0x0091
        L_0x0075:
            long r8 = java.lang.Long.parseLong(r11)     // Catch: Exception -> 0x0092
            r1 = r19
            goto L_0x008b
        L_0x007c:
            int r1 = java.lang.Integer.parseInt(r11)     // Catch: Exception -> 0x0092
            r14 = r1
            r1 = r19
            goto L_0x008b
        L_0x0084:
            r1 = r19
            android.net.Uri r3 = b(r11, r1)     // Catch: Exception -> 0x0092
            r13 = r3
        L_0x008b:
            int r12 = r12 + 1
            r1 = r17
            r3 = 0
            goto L_0x0023
        L_0x0091:
            throw r0     // Catch: Exception -> 0x0092
        L_0x0092:
            r0 = move-exception
            com.google.android.exoplayer2.ParserException r0 = com.google.android.exoplayer2.ParserException.createForMalformedManifest(r15, r0)
            throw r0
        L_0x0098:
            r17 = r1
            r1 = r19
            if (r13 == 0) goto L_0x00c1
            java.lang.String r3 = r13.getScheme()
            if (r3 == 0) goto L_0x00bf
            r3 = -1
            if (r14 != r3) goto L_0x00b0
            r6 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r3 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r3 == 0) goto L_0x00bf
        L_0x00b0:
            com.google.android.exoplayer2.source.rtsp.l r3 = new com.google.android.exoplayer2.source.rtsp.l
            r3.<init>(r8, r14, r13)
            r0.add(r3)
            int r4 = r4 + 1
            r1 = r17
            r3 = 0
            goto L_0x0010
        L_0x00bf:
            r0 = 0
            goto L_0x00c2
        L_0x00c1:
            r0 = 0
        L_0x00c2:
            com.google.android.exoplayer2.ParserException r0 = com.google.android.exoplayer2.ParserException.createForMalformedManifest(r5, r0)
            throw r0
        L_0x00c7:
            com.google.common.collect.ImmutableList r0 = r0.build()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.rtsp.l.a(java.lang.String, android.net.Uri):com.google.common.collect.ImmutableList");
    }

    @VisibleForTesting
    static Uri b(String str, Uri uri) {
        Assertions.checkArgument(((String) Assertions.checkNotNull(uri.getScheme())).equals("rtsp"));
        Uri parse = Uri.parse(str);
        if (parse.isAbsolute()) {
            return parse;
        }
        String valueOf = String.valueOf(str);
        Uri parse2 = Uri.parse(valueOf.length() != 0 ? "rtsp://".concat(valueOf) : new String("rtsp://"));
        String uri2 = uri.toString();
        if (((String) Assertions.checkNotNull(parse2.getHost())).equals(uri.getHost())) {
            return parse2;
        }
        if (uri2.endsWith("/")) {
            return UriUtil.resolveToUri(uri2, str);
        }
        return UriUtil.resolveToUri(String.valueOf(uri2).concat("/"), str);
    }

    private l(long j, int i, Uri uri) {
        this.a = j;
        this.b = i;
        this.c = uri;
    }
}
