package com.xiaomi.smarthome.core.miot;

import com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter;
import java.util.function.Predicate;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$r8rDG80XX965sR4rsUvPchYqVDc  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$MicoMiotDeviceManager$r8rDG80XX965sR4rsUvPchYqVDc implements Predicate {
    public static final /* synthetic */ $$Lambda$MicoMiotDeviceManager$r8rDG80XX965sR4rsUvPchYqVDc INSTANCE = new $$Lambda$MicoMiotDeviceManager$r8rDG80XX965sR4rsUvPchYqVDc();

    private /* synthetic */ $$Lambda$MicoMiotDeviceManager$r8rDG80XX965sR4rsUvPchYqVDc() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isSelected;
        isSelected = ((MiotRegionAdapter.MiotRegion) obj).isSelected();
        return isSelected;
    }
}
