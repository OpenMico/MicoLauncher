package com.efs.sdk.base.a.c.a;

import android.net.NetworkInfo;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.efs.sdk.base.IConfigRefreshAction;
import com.efs.sdk.base.a.a.b;
import com.efs.sdk.base.a.a.c;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.a.h.e;
import com.efs.sdk.base.http.HttpResponse;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class a implements IConfigRefreshAction {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.efs.sdk.base.a.c.a.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static class C0050a {
        private static final a a = new a();
    }

    public static a a() {
        return C0050a.a;
    }

    @Override // com.efs.sdk.base.IConfigRefreshAction
    @NonNull
    public final String refresh() {
        boolean z;
        NetworkInfo a = e.a(com.efs.sdk.base.a.d.a.a().c);
        int i = 0;
        if (a == null || !a.isConnected()) {
            z = false;
        } else {
            z = a.getState() == NetworkInfo.State.CONNECTED;
        }
        if (!z) {
            d.a("efs.config", "Config refresh fail, network is disconnected.");
            return "";
        }
        String str = "";
        String a2 = c.a().a(true);
        c a3 = c.a();
        while (true) {
            if (i >= 3) {
                break;
            }
            com.efs.sdk.base.a.a.a a4 = com.efs.sdk.base.a.a.a.a();
            String b = a3.b();
            String str2 = a2 + "/apm_cc";
            if (a4.a) {
                d.a("efs.px.api", "get config from server, url is ".concat(String.valueOf(str2)));
            }
            HashMap hashMap = new HashMap(1);
            hashMap.put("wpk-header", b);
            com.efs.sdk.base.a.h.b.c a5 = new com.efs.sdk.base.a.h.b.d(str2).a(hashMap).a(b.a()).a();
            a5.a.e = BluetoothConstants.GET;
            HttpResponse a6 = a5.a();
            if (a6.succ) {
                str = a6.data;
                break;
            } else if (TextUtils.isEmpty(a6.getBizCode()) || !"1000".equals(a6.getBizCode())) {
                return "";
            } else {
                i++;
            }
        }
        d.a("efs.config", "config request succ, config is:\n ".concat(String.valueOf(str)));
        return str;
    }
}
