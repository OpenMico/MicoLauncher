package com.fasterxml.jackson.databind.jsonFormatVisitors;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xiaomi.idm.service.iot.LightService;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;

/* loaded from: classes.dex */
public enum JsonValueFormat {
    COLOR(LightService.LightPropertyCommand.COLOR),
    DATE(Common.DATE),
    DATE_TIME("date-time"),
    EMAIL(NotificationCompat.CATEGORY_EMAIL),
    HOST_NAME("host-name"),
    IP_ADDRESS("ip-address"),
    IPV6("ipv6"),
    PHONE("phone"),
    REGEX("regex"),
    STYLE("style"),
    TIME("time"),
    URI(MiSoundBoxCommandExtras.URI),
    UTC_MILLISEC("utc-millisec");
    
    private final String _desc;

    JsonValueFormat(String str) {
        this._desc = str;
    }

    @Override // java.lang.Enum
    @JsonValue
    public String toString() {
        return this._desc;
    }
}
