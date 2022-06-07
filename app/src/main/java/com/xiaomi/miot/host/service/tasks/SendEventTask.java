package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.property.Property;
import java.util.List;

/* loaded from: classes2.dex */
public class SendEventTask implements Runnable {
    private static final String TAG = "SendEventTask";
    private ICompletedListener listener;
    private List<Property> properties;
    private HostRuntimeManager runtime;

    public SendEventTask(HostRuntimeManager hostRuntimeManager, List<Property> list, ICompletedListener iCompletedListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.properties = list;
        this.listener = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.sendEvents(this.properties, new CompletedListener() { // from class: com.xiaomi.miot.host.service.tasks.SendEventTask.1
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    if (SendEventTask.this.listener != null) {
                        try {
                            SendEventTask.this.listener.onSucceed(str);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    if (SendEventTask.this.listener != null) {
                        try {
                            SendEventTask.this.listener.onFailed(miotError);
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
