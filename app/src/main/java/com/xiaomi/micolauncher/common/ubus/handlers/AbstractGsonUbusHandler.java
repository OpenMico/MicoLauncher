package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.xiaomi.micolauncher.common.ubus.UBus;

/* loaded from: classes3.dex */
public abstract class AbstractGsonUbusHandler implements UBus.UbusHandler {
    static final int CODE_ERROR_UNKNOWN = -1;
    static final int CODE_UNEXPECTED_METHOD = -2;
    static final int CODE_UNEXPECTED_PARAM = -3;
    public static final int DATA_NAME_DATA = 0;
    public static final int DATA_NAME_INFO = 1;
    private final int dataName;

    /* loaded from: classes3.dex */
    public static class HandleResultCodeOnError {
        static final int SUCCESS = 0;
        public int errorCode = 0;
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public abstract boolean canHandle(Context context, String str, String str2);

    public abstract Object handleProto(Context context, String str, String str2, String str3, HandleResultCodeOnError handleResultCodeOnError);

    public AbstractGsonUbusHandler() {
        this(1);
    }

    public AbstractGsonUbusHandler(int i) {
        this.dataName = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v1, types: [T, java.lang.Object] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String handle(android.content.Context r9, java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            r8 = this;
            com.xiaomi.micolauncher.common.ubus.UBus$ResponseTemplate r0 = new com.xiaomi.micolauncher.common.ubus.UBus$ResponseTemplate
            int r1 = r8.dataName
            if (r1 != 0) goto L_0x0009
            com.google.gson.Gson r1 = com.xiaomi.micolauncher.common.ubus.UBus.ResponseTemplate.GSON_EXCLUDE_INFO
            goto L_0x000b
        L_0x0009:
            com.google.gson.Gson r1 = com.xiaomi.micolauncher.common.ubus.UBus.ResponseTemplate.GSON_EXCLUDE_DATA
        L_0x000b:
            r0.<init>(r1)
            com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler$HandleResultCodeOnError r1 = new com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler$HandleResultCodeOnError
            r1.<init>()
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r1
            java.lang.Object r9 = r2.handleProto(r3, r4, r5, r6, r7)
            r0.data = r9
            com.google.gson.Gson r10 = com.xiaomi.mico.common.Gsons.getGson()
            java.lang.String r9 = r10.toJson(r9)
            r0.info = r9
            int r9 = r1.errorCode
            r0.code = r9
            java.lang.String r9 = r0.toString()
            com.elvishew.xlog.Logger r10 = com.xiaomi.micolauncher.common.L.ubus
            java.lang.String r12 = "AbstractGsonUbusHandler: %s respond to server with %s, code %s"
            r0 = 3
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r2 = 0
            r0[r2] = r11
            r11 = 1
            r0[r11] = r9
            r11 = 2
            int r1 = r1.errorCode
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0[r11] = r1
            r10.i(r12, r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler.handle(android.content.Context, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }
}
