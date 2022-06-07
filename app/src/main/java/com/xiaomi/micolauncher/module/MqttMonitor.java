package com.xiaomi.micolauncher.module;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.mico.mqtt.service.iface.IMQTTService;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class MqttMonitor {
    public static final String ECHO = "Hello";
    public static final String MQTT_PKG = "com.xiaomi.mico.mqtt.service.application";
    public static final String MQTT_SERVICE = "com.xiaomi.mico.mqtt.service.MqttService";
    public static final int TYPE_ALIVE = 2;
    public static final int TYPE_DEAD = 3;
    public static final int TYPE_UNKNOWN = 1;
    private final Context a;
    private IMQTTService b;
    private ServiceConnection c = new ServiceConnection() { // from class: com.xiaomi.micolauncher.module.MqttMonitor.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            L.init.i("MqttMonitor mqtt onServiceConnected %s", componentName);
            MqttMonitor.this.b = IMQTTService.Stub.asInterface(iBinder);
            MqttMonitor.this.a(true);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            L.init.i("MqttMonitor mqtt onServiceDisconnected %s", componentName);
            MqttMonitor.this.b = null;
            MqttMonitor.this.b();
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            L.init.i("MqttMonitor mqtt onBindingDied %s", componentName);
            MqttMonitor.this.b = null;
            MqttMonitor.this.b();
        }
    };

    /* loaded from: classes3.dex */
    public interface Listener {
        void onUnbind(boolean z);
    }

    public MqttMonitor(Context context) {
        this.a = context;
    }

    public void killMqtt() {
        a().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$MqttMonitor$1YeiExWcvLTn-lV3mdpVlhX4_hk
            @Override // java.lang.Runnable
            public final void run() {
                MqttMonitor.this.d();
            }
        });
        a().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$MqttMonitor$RqtBMVDdP7kJeUzl-pkMF1E4BA4
            @Override // java.lang.Runnable
            public final void run() {
                MqttMonitor.this.c();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        ActivityUtil.forceStopPackage(this.a, MQTT_PKG);
        a(false);
        startMqtt(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        c(true);
    }

    private Handler a() {
        return Threads.getLightWorkHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(boolean z) {
        IMQTTService iMQTTService = this.b;
        if (iMQTTService == null) {
            L.ubus.w("mqtt instance is null.");
            return 1;
        }
        try {
            String echo = iMQTTService.echo(ECHO);
            L.ubus.i("It is still alive ? echo : %s", echo);
            return ECHO.equals(echo) ? 2 : 3;
        } catch (RemoteException e) {
            L.ubus.i("Ubus is still dead", e);
            L.ubus.i("expected alive ? %s", Boolean.valueOf(z));
            return 3;
        }
    }

    public void startMqtt() {
        startMqtt(false);
    }

    public void startMqtt(final boolean z) {
        a().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$MqttMonitor$moRFRbs94Q9e6quq-MXW7uObiHo
            @Override // java.lang.Runnable
            public final void run() {
                MqttMonitor.this.c(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void c(boolean z) {
        int a = a(true);
        if (z || a != 2) {
            L.ubus.i("start mqtt");
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(MQTT_PKG, MQTT_SERVICE));
            this.a.bindService(intent, this.c, 1);
            return;
        }
        L.ubus.i("mqtt is alive , ignore");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (this.b != null) {
            this.a.unbindService(this.c);
            this.b = null;
        }
    }
}
