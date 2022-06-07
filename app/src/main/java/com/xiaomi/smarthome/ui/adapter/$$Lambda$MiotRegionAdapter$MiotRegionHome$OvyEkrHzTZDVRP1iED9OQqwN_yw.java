package com.xiaomi.smarthome.ui.adapter;

import com.xiaomi.mico.settingslib.core.MicoSettingRoom;
import java.util.function.Predicate;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.ui.adapter.-$$Lambda$MiotRegionAdapter$MiotRegionHome$OvyEkrHzTZDVRP1iED9OQqwN_yw  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$MiotRegionAdapter$MiotRegionHome$OvyEkrHzTZDVRP1iED9OQqwN_yw implements Predicate {
    public static final /* synthetic */ $$Lambda$MiotRegionAdapter$MiotRegionHome$OvyEkrHzTZDVRP1iED9OQqwN_yw INSTANCE = new $$Lambda$MiotRegionAdapter$MiotRegionHome$OvyEkrHzTZDVRP1iED9OQqwN_yw();

    private /* synthetic */ $$Lambda$MiotRegionAdapter$MiotRegionHome$OvyEkrHzTZDVRP1iED9OQqwN_yw() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isConfigured;
        isConfigured = ((MicoSettingRoom) obj).isConfigured();
        return isConfigured;
    }
}
