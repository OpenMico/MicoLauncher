package com.xiaomi.smarthome.ui.adapter;

import com.xiaomi.mico.settingslib.core.MicoSettingRoom;
import java.util.function.Predicate;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.ui.adapter.-$$Lambda$MiotRegionAdapter$MiotRegionHome$BVdz-0dtxsEYY1amm7TWXkv5Boo  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$MiotRegionAdapter$MiotRegionHome$BVdz0dtxsEYY1amm7TWXkv5Boo implements Predicate {
    public static final /* synthetic */ $$Lambda$MiotRegionAdapter$MiotRegionHome$BVdz0dtxsEYY1amm7TWXkv5Boo INSTANCE = new $$Lambda$MiotRegionAdapter$MiotRegionHome$BVdz0dtxsEYY1amm7TWXkv5Boo();

    private /* synthetic */ $$Lambda$MiotRegionAdapter$MiotRegionHome$BVdz0dtxsEYY1amm7TWXkv5Boo() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isCurrentSpeakerRoom;
        isCurrentSpeakerRoom = ((MicoSettingRoom) obj).isCurrentSpeakerRoom();
        return isCurrentSpeakerRoom;
    }
}
