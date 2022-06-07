package com.xiaomi.miot.host.service.tasks;

import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;

/* loaded from: classes2.dex */
public class SendMessageTask implements Runnable {
    private static final String TAG = "SendMessageTask";
    private ICompletedListener listener;
    private String method;
    private String params;
    private HostRuntimeManager runtime;

    public SendMessageTask(HostRuntimeManager hostRuntimeManager, String str, String str2, ICompletedListener iCompletedListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.method = str;
        this.params = str2;
        this.listener = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.sendMessage(this.method, this.params, new CompletedListener() { // from class: com.xiaomi.miot.host.service.tasks.SendMessageTask.1
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    if (SendMessageTask.this.listener != null) {
                        try {
                            SendMessageTask.this.listener.onSucceed(str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    if (SendMessageTask.this.listener != null) {
                        try {
                            SendMessageTask.this.listener.onFailed(miotError);
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
