package com.xiaomi.micolauncher.common.ubus;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.ubus.UBus;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes3.dex */
public class HandlerWrapper implements UBus.UbusHandler {
    private final Map<UbusMessage, Object> a;
    private UBus.UbusHandler b;

    public HandlerWrapper(UBus.UbusHandler ubusHandler, Map<UbusMessage, Object> map) {
        this.b = ubusHandler;
        this.a = map;
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return this.b.canHandle(context, str, str2);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        String handle = this.b.handle(context, str, str2, str3);
        Object eventFor = getEventFor(str, str2);
        if (eventFor != null) {
            EventBusRegistry.getEventBus().post(eventFor);
        }
        return handle;
    }

    @VisibleForTesting
    @Nullable
    public Object getEventFor(String str, String str2) {
        Map<UbusMessage, Object> map = this.a;
        if (map == null) {
            return null;
        }
        return map.get(new UbusMessage(str, str2));
    }

    /* loaded from: classes3.dex */
    public static class UbusMessage {
        public String method;
        public String path;

        public UbusMessage(String str, String str2) {
            this.path = str;
            this.method = str2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            UbusMessage ubusMessage = (UbusMessage) obj;
            return Objects.equals(this.path, ubusMessage.path) && Objects.equals(this.method, ubusMessage.method);
        }

        public int hashCode() {
            return Objects.hash(this.path, this.method);
        }
    }
}
