package com.xiaomi.mi_soundbox_command_sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.xiaomi.mi_soundbox_command_sdk.IMiCommandCallback;
import com.xiaomi.mi_soundbox_command_sdk.IMiCommandInterface;
import com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface;

/* loaded from: classes3.dex */
public class SoundBoxCommandImpl implements SoundBoxCommandInterface {
    private HandlerThread b;
    private Handler c;
    private Handler d;
    private UserCommandCallback e;
    private IMiCommandInterface g;
    private String h;
    private Context k;
    private Gson a = new Gson();
    private boolean f = false;
    private b i = new b();
    private SoundBoxCommandInterface.PlayInfoArg j = null;
    private a l = new a();
    private IMiCommandCallback m = new IMiCommandCallback.Stub() { // from class: com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandImpl.3
        @Override // com.xiaomi.mi_soundbox_command_sdk.IMiCommandCallback
        public void onCommand(String str, String str2) throws RemoteException {
            LogHelper.i("Callback sdk received command " + str + ", arg " + str2);
            SoundBoxCommandImpl.this.a(str, str2);
        }
    };

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public void initialize(@NonNull Context context, @Nullable Handler handler) {
        synchronized (this) {
            if (!this.f) {
                this.f = true;
                LogHelper.i("Binding.");
                this.h = Utils.getPackage(context);
                Intent intent = new Intent("android.intent.action.MI_SOUND_BOX_COMMAND_SERVICE");
                intent.setPackage("com.xiaomi.micolauncher");
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                this.k = context;
                this.k.bindService(intent, this.l, 1);
                this.b = new HandlerThread("SoundBoxCommandImpl", 5);
                this.b.start();
                this.c = new Handler(this.b.getLooper());
                if (handler == null) {
                    handler = new Handler(Looper.getMainLooper());
                }
                this.d = handler;
                LogHelper.i("Bind done.");
            }
        }
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public boolean isInitialized() {
        boolean z;
        synchronized (this) {
            z = this.f;
        }
        return z;
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public boolean isServiceConnected() {
        return this.l.a();
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public UserCommandCallback getUserCommandCallback() {
        return this.e;
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public void setUserCommandCallback(UserCommandCallback userCommandCallback) {
        this.e = userCommandCallback;
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public void setPaused() {
        a(false);
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public void setResumed() {
        a(true);
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public void setStarted() {
        a(true);
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public void setStopped() {
        a(false);
    }

    private void a(boolean z) {
        synchronized (this) {
            if (this.f && this.g != null) {
                try {
                    this.g.setPlaying(this.h, z);
                } catch (RemoteException e) {
                    LogHelper.e("failed to set playing status " + z, e);
                }
            }
        }
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public void destroy() {
        synchronized (this) {
            if (this.f) {
                this.b.quitSafely();
                this.k.unbindService(this.l);
                this.f = false;
            }
        }
    }

    @Override // com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandInterface
    public void setPlayInfo(SoundBoxCommandInterface.PlayInfoArg playInfoArg) {
        if (playInfoArg == null) {
            LogHelper.e("setPlayInfo input is null");
            return;
        }
        String json = this.a.toJson(playInfoArg);
        IMiCommandInterface iMiCommandInterface = this.g;
        if (iMiCommandInterface == null) {
            this.j = playInfoArg;
            return;
        }
        try {
            iMiCommandInterface.setPlayInfo(json);
            LogHelper.i("send play info to server : " + json);
        } catch (RemoteException e) {
            LogHelper.e("failed to set play info " + json, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final String str, final String str2) {
        this.c.post(new Runnable() { // from class: com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandImpl.1
            @Override // java.lang.Runnable
            public void run() {
                SoundBoxCommandImpl.this.b(str2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, final String str2) {
        final MiSoundBoxCommandExtras miSoundBoxCommandExtras = !TextUtils.isEmpty(str) ? (MiSoundBoxCommandExtras) this.a.fromJson(str, (Class<Object>) MiSoundBoxCommandExtras.class) : null;
        LogHelper.i("Command " + str2 + " extras " + str + ", parsed result " + miSoundBoxCommandExtras);
        this.d.post(new Runnable() { // from class: com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandImpl.2
            @Override // java.lang.Runnable
            public void run() {
                UserCommandCallback userCommandCallback = SoundBoxCommandImpl.this.e;
                if (userCommandCallback != null) {
                    userCommandCallback.onCommand(str2, miSoundBoxCommandExtras);
                    return;
                }
                LogHelper.e("command ignored " + str2);
            }
        });
    }

    /* loaded from: classes3.dex */
    private class a implements ServiceConnection {
        private boolean b;

        private a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogHelper.i("Binding, mi command interface");
            this.b = true;
            IMiCommandInterface asInterface = IMiCommandInterface.Stub.asInterface(iBinder);
            SoundBoxCommandImpl.this.g = asInterface;
            try {
                asInterface.registerCallback(SoundBoxCommandImpl.this.h, SoundBoxCommandImpl.this.m);
            } catch (RemoteException e) {
                LogHelper.i("failed to set listener", e);
            }
            SoundBoxCommandImpl.this.d.post(new Runnable() { // from class: com.xiaomi.mi_soundbox_command_sdk.SoundBoxCommandImpl.a.1
                @Override // java.lang.Runnable
                public void run() {
                    UserCommandCallback userCommandCallback = SoundBoxCommandImpl.this.e;
                    if (userCommandCallback != null) {
                        userCommandCallback.onRemoteServiceBind();
                    } else {
                        LogHelper.e("user command callback is not set");
                    }
                }
            });
            if (SoundBoxCommandImpl.this.i.a()) {
                boolean b = SoundBoxCommandImpl.this.i.b();
                try {
                    SoundBoxCommandImpl.this.g.setPlaying(SoundBoxCommandImpl.this.h, b);
                } catch (RemoteException unused) {
                    LogHelper.i("failed to set playing status to " + b);
                }
            }
            if (SoundBoxCommandImpl.this.j != null) {
                SoundBoxCommandImpl soundBoxCommandImpl = SoundBoxCommandImpl.this;
                soundBoxCommandImpl.setPlayInfo(soundBoxCommandImpl.j);
                SoundBoxCommandImpl.this.j = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogHelper.i("Disconnected");
            this.b = false;
        }

        boolean a() {
            return this.b;
        }
    }

    /* loaded from: classes3.dex */
    private static class b {
        boolean a;
        boolean b;

        private b() {
        }

        boolean a() {
            return this.b;
        }

        boolean b() {
            this.b = false;
            return this.a;
        }
    }
}
