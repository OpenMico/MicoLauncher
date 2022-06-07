package com.xiaomi.micolauncher.common.ubus.handlers.session;

import android.content.Context;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ubus.UBus;

/* loaded from: classes3.dex */
public class SessionLogHandler implements UBus.UbusHandler {
    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return "mibrain".equals(str) && "nlp_result_get".equals(str2);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        UBus.Response response = new UBus.Response();
        L.base.e("path=%s, method=%s, info=%s", str, str2, "{}");
        response.info = "{}";
        response.code = 0;
        return response.toString();
    }
}
