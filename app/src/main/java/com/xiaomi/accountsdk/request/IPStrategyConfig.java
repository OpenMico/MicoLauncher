package com.xiaomi.accountsdk.request;

/* loaded from: classes2.dex */
public class IPStrategyConfig {
    static final String C_ID_MI_COM_BACKUP_IP_C3 = "111.13.142.141";
    static final String C_ID_MI_COM_BACKUP_IP_C4 = "183.84.5.8";
    private static final long DEFAULT_BACKUP_IP_LIST_EXPIRE_DURATION_MS = 604800000;
    private static final long DEFAULT_CACHED_IP_EXPIRE_DURATION_MS = 3600000;
    private static final long DEFAULT_PING_THRESHOLD = 0;
    private static final int DEFAULT_PING_TIME_COEFFICIENT = 2;
    private static long sBackupIpListExpireDurationMs = 604800000;
    private static long sCachedIpExpireDurationMs = 3600000;
    private static long sPingThreshold = 0;
    private static long sPingTimeCoefficient = 2;

    public static void init(IPUtilExternal iPUtilExternal) {
        if (iPUtilExternal != null) {
            sCachedIpExpireDurationMs = iPUtilExternal.loadCachedIpExpireDuration(sCachedIpExpireDurationMs);
            sBackupIpListExpireDurationMs = iPUtilExternal.loadBackupIpListExpireDuration(sBackupIpListExpireDurationMs);
            sPingThreshold = iPUtilExternal.loadPingThreshold(sPingThreshold);
            sPingTimeCoefficient = iPUtilExternal.loadPingTimeCoefficient(sPingTimeCoefficient);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long getCachedIpExpireDurationMs() {
        return sCachedIpExpireDurationMs;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long getBackupIpListExpireDurationMs() {
        return sBackupIpListExpireDurationMs;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long getPingThreshold() {
        return sPingThreshold;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long getPingTimeCoefficient() {
        return sPingTimeCoefficient;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setCachedIpExpireDurationMs(long j) {
        sCachedIpExpireDurationMs = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setBackupIpListExpireDurationMs(long j) {
        sBackupIpListExpireDurationMs = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setPingThreshold(long j) {
        sPingThreshold = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setPingTimeCoefficient(long j) {
        sPingTimeCoefficient = j;
    }
}
