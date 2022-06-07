package com.xiaomi.smarthome.ui.adapter;

import com.xiaomi.mico.settingslib.core.MicoSettingRoom;
import java.util.function.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.ui.adapter.-$$Lambda$MiotRegionAdapter$MiotRegionHome$y47bZ1zeNfh2LPpTZ73szbBOnXY  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$MiotRegionAdapter$MiotRegionHome$y47bZ1zeNfh2LPpTZ73szbBOnXY implements Function {
    public static final /* synthetic */ $$Lambda$MiotRegionAdapter$MiotRegionHome$y47bZ1zeNfh2LPpTZ73szbBOnXY INSTANCE = new $$Lambda$MiotRegionAdapter$MiotRegionHome$y47bZ1zeNfh2LPpTZ73szbBOnXY();

    private /* synthetic */ $$Lambda$MiotRegionAdapter$MiotRegionHome$y47bZ1zeNfh2LPpTZ73szbBOnXY() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String roomId;
        roomId = ((MicoSettingRoom) obj).getRoomId();
        return roomId;
    }
}
