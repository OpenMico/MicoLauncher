package com.xiaomi.micolauncher.module.miot;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.module.miot.OtDefs;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.host.service.listener.ITransparentListener;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class OtApi {
    private static final Logger a = L.ot;
    @GuardedBy("messagesQueue")
    private boolean c;
    private final Queue<String> b = new LinkedList();
    private final a d = new b();

    /* loaded from: classes3.dex */
    public interface OnRegisterListener {
        void onFailed(int i);

        void onMessage(String str);
    }

    /* loaded from: classes3.dex */
    public interface OnSendTransparentMessageListener {
        void onFailed(int i);

        void onSucceed(String str);
    }

    /* loaded from: classes3.dex */
    public interface a {
        void a(int i);

        void a(Context context);

        void a(Context context, MiotDeviceManager.OnBindListener onBindListener);

        void a(MiotDeviceManager.OnOtStatusListener onOtStatusListener);

        void a(MiotDeviceManager.OnStartDeviceListener onStartDeviceListener);

        boolean a();

        boolean a(String str, OnSendTransparentMessageListener onSendTransparentMessageListener);

        boolean a(List<String> list, OnRegisterListener onRegisterListener);

        void b();

        boolean c();
    }

    public void getOtLocalStatus(MiotDeviceManager.OnOtStatusListener onOtStatusListener) {
        this.d.a(onOtStatusListener);
    }

    /* loaded from: classes3.dex */
    private static class c {
        private static final OtApi a = new OtApi();
    }

    public static OtApi getInstance() {
        return c.a;
    }

    public boolean isBinded() {
        return this.d.a();
    }

    public boolean hasSetWifiStatus() {
        return this.d.c();
    }

    public boolean initialize(List<String> list, OnRegisterListener onRegisterListener) {
        return this.d.a(list, onRegisterListener);
    }

    public void setOnStartListener(MiotDeviceManager.OnStartDeviceListener onStartDeviceListener) {
        this.d.a(onStartDeviceListener);
    }

    public void bindService(Context context, MiotDeviceManager.OnBindListener onBindListener) {
        a.i("begin bind OT service");
        this.d.a(context, onBindListener);
    }

    public void unbindService(Context context) {
        this.d.a(context);
    }

    public void startDevice() {
        Threads.verifyMainThread();
        if (!isBinded()) {
            a.w("because of not Bind, ignore start MiotDevice");
        } else {
            this.d.b();
        }
    }

    public void a(String str, OnSendTransparentMessageListener onSendTransparentMessageListener) {
        synchronized (this.b) {
            if (this.b.size() > 100) {
                a.e("too many buffered messages. clear them");
                this.b.clear();
            }
            if (this.c) {
                a.i("queue message %s", str);
                this.b.add(str);
                return;
            }
            b(str, onSendTransparentMessageListener);
        }
    }

    private void b(String str, final OnSendTransparentMessageListener onSendTransparentMessageListener) {
        this.d.a(str, new OnSendTransparentMessageListener() { // from class: com.xiaomi.micolauncher.module.miot.OtApi.1
            @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnSendTransparentMessageListener
            public void onSucceed(String str2) {
                OnSendTransparentMessageListener onSendTransparentMessageListener2 = onSendTransparentMessageListener;
                if (onSendTransparentMessageListener2 != null) {
                    onSendTransparentMessageListener2.onSucceed(str2);
                }
                OtApi.this.a(onSendTransparentMessageListener);
            }

            @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnSendTransparentMessageListener
            public void onFailed(int i) {
                OnSendTransparentMessageListener onSendTransparentMessageListener2 = onSendTransparentMessageListener;
                if (onSendTransparentMessageListener2 != null) {
                    onSendTransparentMessageListener2.onFailed(i);
                }
                OtApi.this.a(onSendTransparentMessageListener);
            }
        });
    }

    public void a(OnSendTransparentMessageListener onSendTransparentMessageListener) {
        synchronized (this.b) {
            this.c = false;
            if (!this.b.isEmpty()) {
                String remove = this.b.remove();
                a.i("continueSendNextMessage message %s", remove);
                a(remove, onSendTransparentMessageListener);
            }
        }
    }

    public void setWifiMode(int i) {
        this.d.a(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class b implements a {
        b() {
            OtApi.this = r1;
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public boolean a() {
            return MiotDeviceManager.getInstance().isServiceBind();
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public void a(MiotDeviceManager.OnStartDeviceListener onStartDeviceListener) {
            MiotDeviceManager.getInstance().setOnStartDeviceListener(onStartDeviceListener);
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public void a(Context context, MiotDeviceManager.OnBindListener onBindListener) {
            MiotDeviceManager.getInstance().startService(onBindListener);
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public void a(Context context) {
            try {
                if (MiotDeviceManager.getInstance().isServiceBind()) {
                    MiotHostManager.getInstance().unbind(context);
                }
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public void b() {
            OtApi.a.i("OtApi: MiotDeviceManager startDevice");
            MiotDeviceManager.getInstance().startDevice();
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public boolean a(List<String> list, final OnRegisterListener onRegisterListener) {
            MiotDeviceManager.getInstance().initialize(list, new ITransparentListener.Stub() { // from class: com.xiaomi.micolauncher.module.miot.OtApi.b.1
                @Override // com.xiaomi.miot.host.service.listener.ITransparentListener
                public void onMessage(String str) {
                    OnRegisterListener onRegisterListener2 = onRegisterListener;
                    if (onRegisterListener2 != null) {
                        onRegisterListener2.onMessage(str);
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ITransparentListener
                public void onFailed(MiotError miotError) {
                    OnRegisterListener onRegisterListener2 = onRegisterListener;
                    if (onRegisterListener2 != null) {
                        onRegisterListener2.onFailed(miotError.getCode());
                    }
                }
            });
            return true;
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public boolean a(final String str, final OnSendTransparentMessageListener onSendTransparentMessageListener) {
            return b(str, new OnSendTransparentMessageListener() { // from class: com.xiaomi.micolauncher.module.miot.OtApi.b.2
                @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnSendTransparentMessageListener
                public void onSucceed(String str2) {
                    boolean z = false;
                    try {
                        String optString = new JSONObject(str).optString(SchemaActivity.KEY_METHOD);
                        if (!TextUtils.isEmpty(optString)) {
                            if (optString.startsWith(OtDefs.METHOD_PREFIX_INTERNAL)) {
                                z = true;
                            }
                        }
                    } catch (JSONException e) {
                        OtApi.a.e("doSendTransparentMessage onSucceed filter _internal error", e);
                    }
                    if (!z) {
                        OnSendTransparentMessageListener onSendTransparentMessageListener2 = onSendTransparentMessageListener;
                        if (onSendTransparentMessageListener2 != null) {
                            onSendTransparentMessageListener2.onSucceed(str2);
                            return;
                        }
                        return;
                    }
                    OtDefs.d dVar = new OtDefs.d();
                    dVar.method = "_internal.hello";
                    b.this.b(Gsons.getGson().toJson(dVar), onSendTransparentMessageListener);
                }

                @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnSendTransparentMessageListener
                public void onFailed(int i) {
                    OnSendTransparentMessageListener onSendTransparentMessageListener2 = onSendTransparentMessageListener;
                    if (onSendTransparentMessageListener2 != null) {
                        onSendTransparentMessageListener2.onFailed(i);
                    }
                }
            });
        }

        public boolean b(String str, final OnSendTransparentMessageListener onSendTransparentMessageListener) {
            OtApi.a.i("OtApi:send transparent message=%s", str);
            try {
                MiotHostManager.getInstance().sendTransparentMessage(str, new ICompletedListener.Stub() { // from class: com.xiaomi.micolauncher.module.miot.OtApi.b.3
                    @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                    public void onSucceed(String str2) {
                        OnSendTransparentMessageListener onSendTransparentMessageListener2 = onSendTransparentMessageListener;
                        if (onSendTransparentMessageListener2 != null) {
                            onSendTransparentMessageListener2.onSucceed(str2);
                        }
                    }

                    @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                    public void onFailed(MiotError miotError) {
                        OnSendTransparentMessageListener onSendTransparentMessageListener2 = onSendTransparentMessageListener;
                        if (onSendTransparentMessageListener2 != null) {
                            onSendTransparentMessageListener2.onFailed(miotError.getCode());
                        }
                    }
                });
                return true;
            } catch (MiotException e) {
                OtApi.a.e("OtApi: sendTransparentMessage", e);
                return false;
            }
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public void a(int i) {
            if (!a()) {
                OtApi.a.e("failed to set wifi mode for not binded");
            }
            try {
                MiotHostManager.getInstance().setWifiMode(i);
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public boolean c() {
            return MiotDeviceManager.getInstance().a();
        }

        @Override // com.xiaomi.micolauncher.module.miot.OtApi.a
        public void a(MiotDeviceManager.OnOtStatusListener onOtStatusListener) {
            MiotDeviceManager.getInstance().getOtLocalStatus(onOtStatusListener);
        }
    }
}
