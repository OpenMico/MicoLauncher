package com.xiaomi.miot.host.service.tasks;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.miot.host.runtime.HostRuntimeManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class GetDevicePropsTask implements Runnable {
    private static final String TAG = "GetDevicePropsTask";
    private ICompletedListener listener;
    private List<String> props = new ArrayList();
    private HostRuntimeManager runtime;

    public GetDevicePropsTask(HostRuntimeManager hostRuntimeManager, List<String> list, ICompletedListener iCompletedListener) {
        Log.d(TAG, TAG);
        this.runtime = hostRuntimeManager;
        this.props.addAll(list);
        this.listener = iCompletedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(TAG, "run");
        try {
            this.runtime.getDeviceProps(this.props, new CompletedListener() { // from class: com.xiaomi.miot.host.service.tasks.GetDevicePropsTask.1
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    if (GetDevicePropsTask.this.listener != null) {
                        try {
                            GetDevicePropsTask.this.listener.onSucceed(str);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    if (GetDevicePropsTask.this.listener != null) {
                        try {
                            GetDevicePropsTask.this.listener.onFailed(miotError);
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
