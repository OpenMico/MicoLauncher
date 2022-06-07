package org.fourthline.cling.support.model.dlna.types;

import com.xiaomi.mipush.sdk.Constants;
import org.fourthline.cling.model.types.InvalidValueException;

/* loaded from: classes5.dex */
public class NormalPlayTimeRange {
    public static final String PREFIX = "npt=";
    private NormalPlayTime timeDuration;
    private NormalPlayTime timeEnd;
    private NormalPlayTime timeStart;

    public NormalPlayTimeRange(long j, long j2) {
        this.timeStart = new NormalPlayTime(j);
        this.timeEnd = new NormalPlayTime(j2);
    }

    public NormalPlayTimeRange(NormalPlayTime normalPlayTime, NormalPlayTime normalPlayTime2) {
        this.timeStart = normalPlayTime;
        this.timeEnd = normalPlayTime2;
    }

    public NormalPlayTimeRange(NormalPlayTime normalPlayTime, NormalPlayTime normalPlayTime2, NormalPlayTime normalPlayTime3) {
        this.timeStart = normalPlayTime;
        this.timeEnd = normalPlayTime2;
        this.timeDuration = normalPlayTime3;
    }

    public NormalPlayTime getTimeStart() {
        return this.timeStart;
    }

    public NormalPlayTime getTimeEnd() {
        return this.timeEnd;
    }

    public NormalPlayTime getTimeDuration() {
        return this.timeDuration;
    }

    public String getString() {
        return getString(true);
    }

    public String getString(boolean z) {
        String str = PREFIX + this.timeStart.getString() + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        if (this.timeEnd != null) {
            str = str + this.timeEnd.getString();
        }
        if (!z) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/");
        NormalPlayTime normalPlayTime = this.timeDuration;
        sb.append(normalPlayTime != null ? normalPlayTime.getString() : "*");
        return sb.toString();
    }

    public static NormalPlayTimeRange valueOf(String str) throws InvalidValueException {
        return valueOf(str, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004e A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.fourthline.cling.support.model.dlna.types.NormalPlayTimeRange valueOf(java.lang.String r6, boolean r7) throws org.fourthline.cling.model.types.InvalidValueException {
        /*
            java.lang.String r0 = "npt="
            boolean r0 = r6.startsWith(r0)
            if (r0 == 0) goto L_0x0061
            r0 = 4
            java.lang.String r0 = r6.substring(r0)
            java.lang.String r1 = "[-/]"
            java.lang.String[] r0 = r0.split(r1)
            int r1 = r0.length
            r2 = 0
            r3 = 1
            switch(r1) {
                case 1: goto L_0x0044;
                case 2: goto L_0x0034;
                case 3: goto L_0x001a;
                default: goto L_0x0019;
            }
        L_0x0019:
            goto L_0x0061
        L_0x001a:
            r1 = 2
            r4 = r0[r1]
            int r4 = r4.length()
            if (r4 == 0) goto L_0x0034
            r4 = r0[r1]
            java.lang.String r5 = "*"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0034
            r1 = r0[r1]
            org.fourthline.cling.support.model.dlna.types.NormalPlayTime r1 = org.fourthline.cling.support.model.dlna.types.NormalPlayTime.valueOf(r1)
            goto L_0x0035
        L_0x0034:
            r1 = r2
        L_0x0035:
            r4 = r0[r3]
            int r4 = r4.length()
            if (r4 == 0) goto L_0x0045
            r2 = r0[r3]
            org.fourthline.cling.support.model.dlna.types.NormalPlayTime r2 = org.fourthline.cling.support.model.dlna.types.NormalPlayTime.valueOf(r2)
            goto L_0x0045
        L_0x0044:
            r1 = r2
        L_0x0045:
            r4 = 0
            r5 = r0[r4]
            int r5 = r5.length()
            if (r5 == 0) goto L_0x0061
            if (r7 == 0) goto L_0x0055
            if (r7 == 0) goto L_0x0061
            int r7 = r0.length
            if (r7 <= r3) goto L_0x0061
        L_0x0055:
            r6 = r0[r4]
            org.fourthline.cling.support.model.dlna.types.NormalPlayTime r6 = org.fourthline.cling.support.model.dlna.types.NormalPlayTime.valueOf(r6)
            org.fourthline.cling.support.model.dlna.types.NormalPlayTimeRange r7 = new org.fourthline.cling.support.model.dlna.types.NormalPlayTimeRange
            r7.<init>(r6, r2, r1)
            return r7
        L_0x0061:
            org.fourthline.cling.model.types.InvalidValueException r7 = new org.fourthline.cling.model.types.InvalidValueException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Can't parse NormalPlayTimeRange: "
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            r7.<init>(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.support.model.dlna.types.NormalPlayTimeRange.valueOf(java.lang.String, boolean):org.fourthline.cling.support.model.dlna.types.NormalPlayTimeRange");
    }
}
