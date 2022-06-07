package com.xiaomi.smarthome.core.miot;

import com.xiaomi.mico.settingslib.core.MicoSettingHome;
import java.util.function.Function;
import java.util.stream.Stream;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$B6lJWww6FccuSV-qGwTLW1koMDo  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$MicoMiotDeviceManager$B6lJWww6FccuSVqGwTLW1koMDo implements Function {
    public static final /* synthetic */ $$Lambda$MicoMiotDeviceManager$B6lJWww6FccuSVqGwTLW1koMDo INSTANCE = new $$Lambda$MicoMiotDeviceManager$B6lJWww6FccuSVqGwTLW1koMDo();

    private /* synthetic */ $$Lambda$MicoMiotDeviceManager$B6lJWww6FccuSVqGwTLW1koMDo() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Stream d;
        d = MicoMiotDeviceManager.d((MicoSettingHome) obj);
        return d;
    }
}
