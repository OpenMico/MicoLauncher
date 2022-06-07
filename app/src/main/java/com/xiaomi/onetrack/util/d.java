package com.xiaomi.onetrack.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.passport.StatConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d {
    private static final String a = "d";
    private static final String b = "com.xiaomi.onetrack.DEBUG";
    private static final String c = "com.xiaomi.onetrack.permissions.DEBUG_MODE";
    private static final String d = "/api/open/device/writeBack";
    private static final String e = "http://";
    private static volatile d f = null;
    private static final int g = 100;
    private Handler i = new e(this, Looper.getMainLooper());
    private BroadcastReceiver j = new f(this);
    private final Context h = a.a();

    public static d a() {
        if (f == null) {
            synchronized (d.class) {
                if (f == null) {
                    f = new d();
                }
            }
        }
        return f;
    }

    private d() {
        a(this.h);
    }

    private void a(Context context) {
        if (context != null) {
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(b);
                context.registerReceiver(this.j, intentFilter, c, null);
            } catch (Exception e2) {
                String str = a;
                p.a(str, "registerDebugModeReceiver: " + e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, String str3) {
        i.a(new g(this, str, str2, str3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("code");
            String optString = jSONObject.optString("message");
            String optString2 = jSONObject.optString("result");
            boolean optBoolean = jSONObject.optBoolean(StatConstants.BIND_SUCCESS);
            Message obtain = Message.obtain();
            obtain.what = 100;
            Bundle bundle = new Bundle();
            if (optInt != 0 || !optBoolean) {
                bundle.putString("hint", optString);
            } else {
                bundle.putString("hint", optString2);
            }
            obtain.setData(bundle);
            this.i.sendMessage(obtain);
        } catch (JSONException e2) {
            p.b(a, e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        Toast.makeText(this.h, str, 1).show();
    }
}
