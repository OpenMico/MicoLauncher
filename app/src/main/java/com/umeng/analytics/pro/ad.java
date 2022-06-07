package com.umeng.analytics.pro;

import android.content.Context;
import com.umeng.commonsdk.debug.UMLog;
import org.repackage.com.heytap.openid.sdk.OpenIDSDK;

/* compiled from: OppoDeviceIdSupplier.java */
/* loaded from: classes2.dex */
public class ad implements y {
    private boolean a = false;

    @Override // com.umeng.analytics.pro.y
    public String a(Context context) {
        try {
            if (!this.a) {
                OpenIDSDK.a(context);
                this.a = true;
            }
            if (OpenIDSDK.a()) {
                return OpenIDSDK.c(context);
            }
            UMLog.mutlInfo(2, "当前设备不支持获取OAID");
            return null;
        } catch (Exception unused) {
            UMLog.mutlInfo(2, "未检测到您集成OAID SDK包");
            return null;
        }
    }
}
