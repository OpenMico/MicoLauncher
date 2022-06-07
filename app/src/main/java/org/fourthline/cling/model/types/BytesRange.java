package org.fourthline.cling.model.types;

import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes5.dex */
public class BytesRange {
    public static final String PREFIX = "bytes=";
    private Long byteLength;
    private Long firstByte;
    private Long lastByte;

    public BytesRange(Long l, Long l2) {
        this.firstByte = l;
        this.lastByte = l2;
        this.byteLength = null;
    }

    public BytesRange(Long l, Long l2, Long l3) {
        this.firstByte = l;
        this.lastByte = l2;
        this.byteLength = l3;
    }

    public Long getFirstByte() {
        return this.firstByte;
    }

    public Long getLastByte() {
        return this.lastByte;
    }

    public Long getByteLength() {
        return this.byteLength;
    }

    public String getString() {
        return getString(false, null);
    }

    public String getString(boolean z) {
        return getString(z, null);
    }

    public String getString(boolean z, String str) {
        if (str == null) {
            str = PREFIX;
        }
        if (this.firstByte != null) {
            str = str + this.firstByte.toString();
        }
        String str2 = str + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        if (this.lastByte != null) {
            str2 = str2 + this.lastByte.toString();
        }
        if (!z) {
            return str2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("/");
        Long l = this.byteLength;
        sb.append(l != null ? l.toString() : "*");
        return sb.toString();
    }

    public static BytesRange valueOf(String str) throws InvalidValueException {
        return valueOf(str, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0071 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.fourthline.cling.model.types.BytesRange valueOf(java.lang.String r5, java.lang.String r6) throws org.fourthline.cling.model.types.InvalidValueException {
        /*
            if (r6 == 0) goto L_0x0004
            r0 = r6
            goto L_0x0006
        L_0x0004:
            java.lang.String r0 = "bytes="
        L_0x0006:
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x0079
            if (r6 == 0) goto L_0x000f
            goto L_0x0011
        L_0x000f:
            java.lang.String r6 = "bytes="
        L_0x0011:
            int r6 = r6.length()
            java.lang.String r6 = r5.substring(r6)
            java.lang.String r0 = "[-/]"
            java.lang.String[] r6 = r6.split(r0)
            int r0 = r6.length
            r1 = 0
            switch(r0) {
                case 1: goto L_0x005a;
                case 2: goto L_0x0043;
                case 3: goto L_0x0025;
                default: goto L_0x0024;
            }
        L_0x0024:
            goto L_0x0079
        L_0x0025:
            r0 = 2
            r2 = r6[r0]
            int r2 = r2.length()
            if (r2 == 0) goto L_0x0043
            r2 = r6[r0]
            java.lang.String r3 = "*"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0043
            r0 = r6[r0]
            long r2 = java.lang.Long.parseLong(r0)
            java.lang.Long r0 = java.lang.Long.valueOf(r2)
            goto L_0x0044
        L_0x0043:
            r0 = r1
        L_0x0044:
            r2 = 1
            r3 = r6[r2]
            int r3 = r3.length()
            if (r3 == 0) goto L_0x0058
            r2 = r6[r2]
            long r2 = java.lang.Long.parseLong(r2)
            java.lang.Long r2 = java.lang.Long.valueOf(r2)
            goto L_0x005c
        L_0x0058:
            r2 = r1
            goto L_0x005c
        L_0x005a:
            r0 = r1
            r2 = r0
        L_0x005c:
            r3 = 0
            r4 = r6[r3]
            int r4 = r4.length()
            if (r4 == 0) goto L_0x006f
            r6 = r6[r3]
            long r3 = java.lang.Long.parseLong(r6)
            java.lang.Long r1 = java.lang.Long.valueOf(r3)
        L_0x006f:
            if (r1 != 0) goto L_0x0073
            if (r2 == 0) goto L_0x0079
        L_0x0073:
            org.fourthline.cling.model.types.BytesRange r5 = new org.fourthline.cling.model.types.BytesRange
            r5.<init>(r1, r2, r0)
            return r5
        L_0x0079:
            org.fourthline.cling.model.types.InvalidValueException r6 = new org.fourthline.cling.model.types.InvalidValueException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Can't parse Bytes Range: "
            r0.append(r1)
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            r6.<init>(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.model.types.BytesRange.valueOf(java.lang.String, java.lang.String):org.fourthline.cling.model.types.BytesRange");
    }
}
