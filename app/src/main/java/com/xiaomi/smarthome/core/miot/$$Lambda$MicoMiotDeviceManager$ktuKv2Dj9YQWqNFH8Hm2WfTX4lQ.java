package com.xiaomi.smarthome.core.miot;

import com.xiaomi.mico.settingslib.core.MicoSettingRoom;
import java.util.function.Predicate;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$ktuKv2Dj9YQWqNFH8Hm2WfTX4lQ  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$MicoMiotDeviceManager$ktuKv2Dj9YQWqNFH8Hm2WfTX4lQ implements Predicate {
    public static final /* synthetic */ $$Lambda$MicoMiotDeviceManager$ktuKv2Dj9YQWqNFH8Hm2WfTX4lQ INSTANCE = new $$Lambda$MicoMiotDeviceManager$ktuKv2Dj9YQWqNFH8Hm2WfTX4lQ();

    private /* synthetic */ $$Lambda$MicoMiotDeviceManager$ktuKv2Dj9YQWqNFH8Hm2WfTX4lQ() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isSelected;
        isSelected = ((MicoSettingRoom) obj).isSelected();
        return isSelected;
    }
}
