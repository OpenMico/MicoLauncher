package com.umeng.commonsdk.stateless;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.umeng.commonsdk.debug.UMLogCommon;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class UMSLEnvelopeBuild {
    private static final String TAG = "UMSLEnvelopeBuild";
    private static boolean isEncryptEnabled;
    public static Context mContext;
    public static String module;

    public static boolean isReadyBuildNew(Context context, UMLogDataProtocol.UMBusinessType uMBusinessType) {
        return false;
    }

    public JSONObject buildSLEnvelope(Context context, JSONObject jSONObject, JSONObject jSONObject2, String str) {
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.umeng.commonsdk.stateless.UMSLEnvelopeBuild$1] */
    public JSONObject buildSLBaseHeader(final Context context) {
        new Thread() { // from class: com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Looper.prepare();
                    Toast.makeText(context.getApplicationContext(), UMLogCommon.SC_10015, 1).show();
                    Looper.loop();
                } catch (Throwable unused) {
                }
            }
        }.start();
        Log.e("UMLog", UMLogCommon.SC_10015);
        return null;
    }

    public static void setEncryptEnabled(boolean z) {
        isEncryptEnabled = z;
    }
}
