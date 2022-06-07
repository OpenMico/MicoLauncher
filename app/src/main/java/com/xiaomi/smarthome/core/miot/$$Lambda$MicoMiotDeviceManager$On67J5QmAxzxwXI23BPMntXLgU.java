package com.xiaomi.smarthome.core.miot;

import com.xiaomi.miot.support.core.MiotRoom;
import java.util.function.Predicate;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$On67J5QmAxz-xwXI23BPMntXLgU  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$MicoMiotDeviceManager$On67J5QmAxzxwXI23BPMntXLgU implements Predicate {
    public static final /* synthetic */ $$Lambda$MicoMiotDeviceManager$On67J5QmAxzxwXI23BPMntXLgU INSTANCE = new $$Lambda$MicoMiotDeviceManager$On67J5QmAxzxwXI23BPMntXLgU();

    private /* synthetic */ $$Lambda$MicoMiotDeviceManager$On67J5QmAxzxwXI23BPMntXLgU() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z;
        z = ((MiotRoom) obj).isCurrentSpeakerRoom;
        return z;
    }
}
