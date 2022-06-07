package com.xiaomi.smarthome.core.entity;

import com.xiaomi.mi_soundbox_command_sdk.Commands;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: SmartHomeBeans.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/AirConditionMode;", "", "mode", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getMode", "()Ljava/lang/String;", "MODE_COLD", "MODE_HOT", "MODE_WIND", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public enum AirConditionMode {
    MODE_COLD("2"),
    MODE_HOT(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND),
    MODE_WIND(Commands.ResolutionValues.BITSTREAM_BLUE_HIGH);
    
    @NotNull
    private final String mode;

    AirConditionMode(String str) {
        this.mode = str;
    }

    @NotNull
    public final String getMode() {
        return this.mode;
    }
}
