package com.xiaomi.miot.host.service.tasks;

import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;

/* loaded from: classes2.dex */
public class SendTransparentMessageTask implements Runnable {
    private static final String TAG = "SendTransparentTask";
    private ICompletedListener listener;
    private String message;
    private HostRuntimeManager runtime;

    public SendTransparentMessageTask(HostRuntimeManager hostRuntimeManager, String str, ICompletedListener iCompletedListener) {
        Log.d(TAG, "SendTransparentMessageTask");
        this.runtime = hostRuntimeManager;
        this.message = str;
        this.listener = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.sendTransparentMessage(this.message, new CompletedListener() { // from class: com.xiaomi.miot.host.service.tasks.SendTransparentMessageTask.1
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    if (SendTransparentMessageTask.this.listener != null) {
                        try {
                            SendTransparentMessageTask.this.listener.onSucceed(str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    if (SendTransparentMessageTask.this.listener != null) {
                        try {
                            SendTransparentMessageTask.this.listener.onFailed(miotError);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (MiotException e) {
            e.printStackTrace();
        }
    }
}
