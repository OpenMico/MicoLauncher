package com.xiaomi.micolauncher.overlay.client;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback;

/* compiled from: LauncherOverlayCallback.java */
/* loaded from: classes3.dex */
class a extends ILauncherOverlayCallback.Stub implements Handler.Callback {
    private final Handler a = new Handler(Looper.getMainLooper(), this);
    private LauncherClient b;

    public a(LauncherClient launcherClient) {
        this.b = launcherClient;
    }

    @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
    public void overlayStatusChanged(int i) {
        Message.obtain(this.a, 1, Integer.valueOf(i)).sendToTarget();
    }

    @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
    public void overlayTransitionStart(float f) {
        Message.obtain(this.a, 2, Float.valueOf(f)).sendToTarget();
    }

    @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
    public void overlayTransitionChanged(float f) {
        Message.obtain(this.a, 3, Float.valueOf(f)).sendToTarget();
    }

    @Override // com.xiaomi.micolauncher.overlay.ILauncherOverlayCallback
    public void overlayTransitionEnd(float f) {
        Message.obtain(this.a, 4, Float.valueOf(f)).sendToTarget();
    }

    public void a() {
        this.b = null;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(@NonNull Message message) {
        if (this.b == null) {
            return true;
        }
        switch (message.what) {
            case 1:
                this.b.a(((Integer) message.obj).intValue());
                return true;
            case 2:
                this.b.onOverlayTransitionStart(((Float) message.obj).floatValue());
                return true;
            case 3:
                this.b.onOverlayTransitionChanged(((Float) message.obj).floatValue());
                return true;
            case 4:
                this.b.onOverlayTransitionEnd(((Float) message.obj).floatValue());
                return true;
            default:
                return false;
        }
    }
}
