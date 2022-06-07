package com.xiaomi.mico.wifidemo;

import android.net.wifi.ScanResult;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.xiaomi.mico.wifidemo.-$$Lambda$WifiAutoConfigActivity$9hhHTBHHrYDE2CM9iLqtyVEIAsg  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$WifiAutoConfigActivity$9hhHTBHHrYDE2CM9iLqtyVEIAsg implements Comparator {
    public static final /* synthetic */ $$Lambda$WifiAutoConfigActivity$9hhHTBHHrYDE2CM9iLqtyVEIAsg INSTANCE = new $$Lambda$WifiAutoConfigActivity$9hhHTBHHrYDE2CM9iLqtyVEIAsg();

    private /* synthetic */ $$Lambda$WifiAutoConfigActivity$9hhHTBHHrYDE2CM9iLqtyVEIAsg() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int a;
        a = WifiAutoConfigActivity.a((ScanResult) obj, (ScanResult) obj2);
        return a;
    }
}
