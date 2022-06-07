package com.mi.milink.mediacore;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mi.milink.mediacore.MiPlayEngine;
import com.milink.kit.MiLinkContext;
import com.milink.kit.lock.LockProvider;
import com.milink.kit.lock.MiLinkLock;
import com.milink.kit.lock.MiLinkLockCallback;
import com.xiaomi.idm.handoff_sdk.HandoffApi;
import java.util.Objects;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MediaCoreServiceImpl.java */
/* loaded from: classes2.dex */
public final class a {
    private final b a;
    private final Context b;
    private final Handler c;
    private final MiLinkLockCallback d = new MiLinkLockCallback() { // from class: com.mi.milink.mediacore.a.1
        @Override // com.milink.kit.lock.MiLinkLockCallback
        public void onLockGranted(@NonNull String str, @NonNull String str2) {
            Log.i("MediaCoreService", String.format("MiLinkLockCallback onLockGranted: %s - %s", str, str2));
            MiLinkLockCallbackNative.onLockEvent(2, str, str2);
        }

        @Override // com.milink.kit.lock.MiLinkLockCallback
        public void onLockRevoked(@NonNull String str, @NonNull String str2) {
            Log.i("MediaCoreService", String.format("MiLinkLockCallback onLockRevoked: %s - %s", str, str2));
            MiLinkLockCallbackNative.onLockEvent(4, str, str2);
        }

        @Override // com.milink.kit.lock.MiLinkLockCallback
        public void onBeforeLockRevoke(@NonNull String str, @NonNull String str2) {
            Log.i("MediaCoreService", String.format("MiLinkLockCallback onBeforeLockRevoke: %s - %s", str, str2));
            MiLinkLockCallbackNative.onLockEvent(3, str, str2);
        }

        @Override // com.milink.kit.lock.MiLinkLockCallback
        public boolean onAcceptUnlock(@NonNull String str, @NonNull String str2, @NonNull String str3) {
            Log.i("MediaCoreService", String.format("MiLinkLockCallback onAcceptUnlock: %s - %s", str, str2));
            return MiLinkLockCallbackNative.onAcceptUnlock(str, str3, str2);
        }

        @Override // com.milink.kit.lock.MiLinkLockCallback
        public void onRequestLockDenied(@NonNull String str, @NonNull String str2) {
            Log.i("MediaCoreService", String.format("MiLinkLockCallback onRequestLockDenied: %s - %s", str, str2));
            MiLinkLockCallbackNative.onLockEvent(1, str, str2);
        }
    };

    /* compiled from: MediaCoreServiceImpl.java */
    /* loaded from: classes2.dex */
    interface b {
        void a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull Context context, @NonNull b bVar) {
        this.b = b.d(context);
        this.a = (b) Objects.requireNonNull(bVar);
        HandlerThread handlerThread = new HandlerThread("MediaCoreService", -1);
        handlerThread.start();
        this.c = new Handler(handlerThread.getLooper(), new C0128a());
    }

    public void a() {
        this.c.obtainMessage(1).sendToTarget();
    }

    public void b() {
        this.c.obtainMessage(2).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"WrongConstant"})
    public void a(int i, @Nullable String str) {
        try {
            if (i == 1) {
                d();
            } else if (i == 16) {
                c();
            } else if (i != 32) {
                Log.i("MediaCoreService", "un-support event: " + i);
            } else {
                a(str);
            }
        } catch (Exception e) {
            Log.w("MediaCoreService", "onHandleEvent occur exception", e);
        }
    }

    private void a(String str) {
        if (str == null) {
            Log.w("MediaCoreService", "onSendNotice is null");
        } else {
            Log.d("MediaCoreService", "send notice size = " + str.length());
        }
        Intent intent = new Intent(MediaCoreConfig.ACTION_NOTICE);
        intent.putExtra("msg", str);
        this.b.sendStickyBroadcast(intent);
    }

    private void c() {
        try {
            Intent intent = new Intent();
            intent.setAction("com.xiaomi.mi_connect_service.MiConnectService");
            intent.setComponent(new ComponentName(HandoffApi.MI_CONNECT_SERVICE_PKG, "com.xiaomi.mi_connect_service.MiConnectService"));
            this.b.startService(intent);
            Log.i("MediaCoreService", "Start MiConnectService done.");
        } catch (Exception e) {
            Log.i("MediaCoreService", "Start MiConnectService fail: " + e.getMessage());
        }
    }

    private void d() {
        Intent intent = new Intent(MediaCoreConfig.ACTION_AUDIO_PLAYBACK_FOR_MUSIC);
        intent.setPackage("com.xiaomi.mimusic2");
        intent.setComponent(new ComponentName("com.xiaomi.mimusic2", "com.xiaomi.mimusic2.receiver.MiPlayReceiver"));
        intent.addFlags(268435456);
        this.b.sendBroadcast(intent);
        Log.i("MediaCoreService", "playback: send to music static receiver.");
        this.b.sendBroadcast(new Intent(MediaCoreConfig.ACTION_AUDIO_PLAYBACK));
        Log.i("MediaCoreService", "playback: send to dynamic receiver.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MediaCoreServiceImpl.java */
    /* loaded from: classes2.dex */
    public static class c implements MiPlayEngine.EventCallback {
        private final Handler a;

        c(@NonNull Handler handler) {
            this.a = handler;
        }

        @Override // com.mi.milink.mediacore.MiPlayEngine.EventCallback
        public void onEvent(int i, String str) {
            Log.i("MediaCoreService", String.format("on mi play event: %s", Integer.valueOf(i)));
            this.a.obtainMessage(3, i, 0, str).sendToTarget();
        }
    }

    /* compiled from: MediaCoreServiceImpl.java */
    /* renamed from: com.mi.milink.mediacore.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private class C0128a implements Handler.Callback {
        private C0128a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 1:
                    if (!b()) {
                        a.this.a.a();
                    }
                    return true;
                case 2:
                    a();
                    return true;
                case 3:
                    a.this.a(message.arg1, (String) message.obj);
                    return true;
                default:
                    return false;
            }
        }

        private void a() {
            try {
                Log.d("MediaCoreService", "perform release media core.");
                if (MiPlayEngine.isInstalled()) {
                    MiPlayEngine.uninstall();
                    Log.d("MediaCoreService", "MiPlayEngine uninstall done.");
                }
            } catch (Exception e) {
                Log.e("MediaCoreService", "MiPlayEngine Uninstall fail.", e);
            }
        }

        private boolean b() {
            try {
                Log.d("MediaCoreService", "perform init media core.");
                if (MiPlayEngine.isInstalled()) {
                    Log.d("MediaCoreService", "MiPlayEngine is installed.");
                    return true;
                }
                String c = b.c(a.this.b);
                if (c == null) {
                    Log.w("MediaCoreService", "Can't init MediaCoreService, bluetoothMac is null");
                    return false;
                }
                if (!MiLinkContext.hasMiLinkContext()) {
                    new MiLinkContext.Installer(a.this.b).install();
                }
                LockProvider lockProvider = (LockProvider) MiLinkContext.getInstance().require(LockProvider.class);
                MiLinkLock lock = lockProvider.getLock(LockProvider.AUDIO_LOCK_NAME, "miplay_music");
                if (lock == null) {
                    lock = lockProvider.requireLock(a.this.b, LockProvider.AUDIO_LOCK_NAME, "miplay_music", a.this.d);
                }
                int install = MiPlayEngine.install(new MiPlayEngine.Config.Builder().setBluetoothMAC(c).setOSVersion(b.a()).setNetworkType(b.a(a.this.b)).setDeviceID(MediaCoreConfig.getDeviceId(a.this.b)).setDeviceModel(MediaCoreConfig.getDeviceModel()).setEventCallback(new c(a.this.c)).setDataDir(a.this.b.getDir("media_core", 0)).setAudioMiLinkLock(lock).setDeviceCategory(MediaCoreConfig.getDeviceCategory()).setOptionArguments(MediaCoreConfig.getOptionArguments()).create());
                Log.d("MediaCoreService", "MiPlayEngine install result: " + install);
                return install == 0;
            } catch (Exception e) {
                Log.e("MediaCoreService", "MiPlayEngine install fail.", e);
                return false;
            }
        }
    }
}
