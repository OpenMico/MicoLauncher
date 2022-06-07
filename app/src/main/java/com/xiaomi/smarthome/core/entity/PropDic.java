package com.xiaomi.smarthome.core.entity;

import com.xiaomi.idm.service.iot.LightService;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: SmartHomeBeans.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/PropDic;", "", "prop", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getProp", "()Ljava/lang/String;", "P_ON", "P_MODE", "P_TARGET_TEMP", "P_PM25", "P_BRIGHTNESS", "P_FAN_LEVEL", "P_MONITOR_CONTROL", "P_STATUS", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public enum PropDic {
    P_ON("on"),
    P_MODE("mode"),
    P_TARGET_TEMP("target-temperature"),
    P_PM25("pm2.5-density"),
    P_BRIGHTNESS(LightService.LightPropertyCommand.BRIGHTNESS),
    P_FAN_LEVEL("fan-level"),
    P_MONITOR_CONTROL("motor-control"),
    P_STATUS("status");
    
    @NotNull
    private final String prop;

    PropDic(String str) {
        this.prop = str;
    }

    @NotNull
    public final String getProp() {
        return this.prop;
    }
}
