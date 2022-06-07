package com.umeng.commonsdk;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.internal.a;
import com.umeng.commonsdk.internal.b;

/* loaded from: classes2.dex */
public class UMConfigureInternation {
    private static boolean isInternal = false;

    public static synchronized void sendInternal(final Context context) {
        synchronized (UMConfigureInternation.class) {
            if (context != null) {
                if (!isInternal) {
                    new Thread(new Runnable() { // from class: com.umeng.commonsdk.UMConfigureInternation.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                String currentProcessName = UMFrUtils.getCurrentProcessName(context);
                                String packageName = context.getPackageName();
                                if (!TextUtils.isEmpty(currentProcessName) && !TextUtils.isEmpty(packageName) && currentProcessName.equals(packageName) && UMEnvelopeBuild.isReadyBuild(context, UMLogDataProtocol.UMBusinessType.U_INTERNAL)) {
                                    UMWorkDispatch.sendEvent(context, a.n, b.a(context).a(), null);
                                }
                            } catch (Throwable unused) {
                            }
                        }
                    }).start();
                    isInternal = true;
                }
            }
        }
    }

    public static void doSelfCheck(Context context) {
        try {
            String currentProcessName = UMFrUtils.getCurrentProcessName(context);
            String packageName = context.getPackageName();
            if (!TextUtils.isEmpty(currentProcessName) && !TextUtils.isEmpty(packageName) && currentProcessName.equals(packageName)) {
                UMWorkDispatch.sendEvent(context, a.z, b.a(context).a(), null);
            }
        } catch (Throwable unused) {
        }
    }
}
