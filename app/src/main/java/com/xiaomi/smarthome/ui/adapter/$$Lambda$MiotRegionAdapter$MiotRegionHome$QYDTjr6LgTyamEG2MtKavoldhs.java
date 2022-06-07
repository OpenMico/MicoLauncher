package com.xiaomi.smarthome.ui.adapter;

import com.xiaomi.mico.settingslib.core.MicoSettingRoom;
import java.util.function.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.ui.adapter.-$$Lambda$MiotRegionAdapter$MiotRegionHome$QYDTjr6LgTy-amEG2MtKavoldhs  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$MiotRegionAdapter$MiotRegionHome$QYDTjr6LgTyamEG2MtKavoldhs implements Function {
    public static final /* synthetic */ $$Lambda$MiotRegionAdapter$MiotRegionHome$QYDTjr6LgTyamEG2MtKavoldhs INSTANCE = new $$Lambda$MiotRegionAdapter$MiotRegionHome$QYDTjr6LgTyamEG2MtKavoldhs();

    private /* synthetic */ $$Lambda$MiotRegionAdapter$MiotRegionHome$QYDTjr6LgTyamEG2MtKavoldhs() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String roomId;
        roomId = ((MicoSettingRoom) obj).getRoomId();
        return roomId;
    }
}
