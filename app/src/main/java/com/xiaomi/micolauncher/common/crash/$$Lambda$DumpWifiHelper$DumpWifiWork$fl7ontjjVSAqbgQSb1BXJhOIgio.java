package com.xiaomi.micolauncher.common.crash;

import com.xiaomi.micolauncher.common.crash.DumpWifiHelper;
import java.io.File;
import java.io.FilenameFilter;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.crash.-$$Lambda$DumpWifiHelper$DumpWifiWork$fl7ontjjVSAqbgQSb1BXJhOIgio  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$DumpWifiHelper$DumpWifiWork$fl7ontjjVSAqbgQSb1BXJhOIgio implements FilenameFilter {
    public static final /* synthetic */ $$Lambda$DumpWifiHelper$DumpWifiWork$fl7ontjjVSAqbgQSb1BXJhOIgio INSTANCE = new $$Lambda$DumpWifiHelper$DumpWifiWork$fl7ontjjVSAqbgQSb1BXJhOIgio();

    private /* synthetic */ $$Lambda$DumpWifiHelper$DumpWifiWork$fl7ontjjVSAqbgQSb1BXJhOIgio() {
    }

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return DumpWifiHelper.DumpWifiWork.lambda$getWifiLogFilesLocked$0(file, str);
    }
}
