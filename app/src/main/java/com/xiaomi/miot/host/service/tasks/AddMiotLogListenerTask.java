package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.lan.impl.MiotLogUtil;
import com.xiaomi.miot.host.service.listener.IMiotLogListener;
import com.xiaomi.miot.typedef.listener.MiotLogListener;

/* loaded from: classes2.dex */
public class AddMiotLogListenerTask implements Runnable {
    private static final String TAG = "AddMiotLogListenerTask";
    private IMiotLogListener listener;
    private MiotLogListener mMiotLogListener = new MiotLogListener() { // from class: com.xiaomi.miot.host.service.tasks.AddMiotLogListenerTask.1
        @Override // com.xiaomi.miot.typedef.listener.MiotLogListener
        public void log(int i, String str, String str2) {
            if (AddMiotLogListenerTask.this.listener != null) {
                try {
                    AddMiotLogListenerTask.this.listener.log(i, str, str2);
                } catch (RemoteException unused) {
                }
            }
        }
    };

    public AddMiotLogListenerTask(IMiotLogListener iMiotLogListener) {
        Log.d(TAG, TAG);
        this.listener = iMiotLogListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        MiotLogUtil.setMiotLogListener(this.mMiotLogListener);
    }
}
