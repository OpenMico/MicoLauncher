package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.device.Event;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;

/* loaded from: classes2.dex */
public class SendMiotSpecEventNotifyTask implements Runnable {
    private static final String TAG = "SendSpecEventNotifyTask";
    private Event event;
    private ICompletedListener listener;
    private HostRuntimeManager runtime;

    public SendMiotSpecEventNotifyTask(HostRuntimeManager hostRuntimeManager, Event event, ICompletedListener iCompletedListener) {
        Log.d(TAG, "SendMiotSpecEventNotifyTask");
        this.runtime = hostRuntimeManager;
        this.event = event;
        this.listener = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.sendMiotSpecEventNotify(this.event, new CompletedListener() { // from class: com.xiaomi.miot.host.service.tasks.SendMiotSpecEventNotifyTask.1
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    if (SendMiotSpecEventNotifyTask.this.listener != null) {
                        try {
                            SendMiotSpecEventNotifyTask.this.listener.onSucceed(str);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    if (SendMiotSpecEventNotifyTask.this.listener != null) {
                        try {
                            SendMiotSpecEventNotifyTask.this.listener.onFailed(miotError);
                        } catch (RemoteException e) {
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
