package com.xiaomi.miot.host.service.tasks;

import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.typedef.exception.MiotException;

/* loaded from: classes2.dex */
public class MiotRefreshInfoTask implements Runnable {
    private static final String TAG = "MiotRefreshInfoTask";
    private String mMiotInfo;
    private HostRuntimeManager runtime;

    public MiotRefreshInfoTask(HostRuntimeManager hostRuntimeManager, String str) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.mMiotInfo = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.refreshMiotInfo(this.mMiotInfo);
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }
}
