package com.xiaomi.micolauncher.common.crash;

import java.io.File;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.crash.-$$Lambda$DumpWifiHelper$DumpWifiWork$34MZQcBihB_NW-Pf-0QF9Ucoo0I  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$DumpWifiHelper$DumpWifiWork$34MZQcBihB_NWPf0QF9Ucoo0I implements Comparator {
    public static final /* synthetic */ $$Lambda$DumpWifiHelper$DumpWifiWork$34MZQcBihB_NWPf0QF9Ucoo0I INSTANCE = new $$Lambda$DumpWifiHelper$DumpWifiWork$34MZQcBihB_NWPf0QF9Ucoo0I();

    private /* synthetic */ $$Lambda$DumpWifiHelper$DumpWifiWork$34MZQcBihB_NWPf0QF9Ucoo0I() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int signum;
        signum = Long.signum(Long.valueOf(((File) obj).getName()).longValue() - Long.valueOf(((File) obj2).getName()).longValue());
        return signum;
    }
}
