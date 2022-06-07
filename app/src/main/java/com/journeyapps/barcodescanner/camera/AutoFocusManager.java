package com.journeyapps.barcodescanner.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.exoplayer2.SimpleExoPlayer;
import java.util.ArrayList;
import java.util.Collection;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes2.dex */
public final class AutoFocusManager {
    private static final String a = "AutoFocusManager";
    private static final Collection<String> h = new ArrayList(2);
    private boolean b;
    private boolean c;
    private final boolean d;
    private final Camera e;
    private int g = 1;
    private final Handler.Callback i = new Handler.Callback() { // from class: com.journeyapps.barcodescanner.camera.AutoFocusManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != AutoFocusManager.this.g) {
                return false;
            }
            AutoFocusManager.this.b();
            return true;
        }
    };
    private final Camera.AutoFocusCallback j = new Camera.AutoFocusCallback() { // from class: com.journeyapps.barcodescanner.camera.AutoFocusManager.2
        @Override // android.hardware.Camera.AutoFocusCallback
        public void onAutoFocus(boolean z, Camera camera) {
            AutoFocusManager.this.f.post(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.AutoFocusManager.2.1
                @Override // java.lang.Runnable
                public void run() {
                    AutoFocusManager.this.c = false;
                    AutoFocusManager.this.a();
                }
            });
        }
    };
    private Handler f = new Handler(this.i);

    static {
        h.add(DebugKt.DEBUG_PROPERTY_VALUE_AUTO);
        h.add("macro");
    }

    public AutoFocusManager(Camera camera, CameraSettings cameraSettings) {
        boolean z = true;
        this.e = camera;
        String focusMode = camera.getParameters().getFocusMode();
        this.d = (!cameraSettings.isAutoFocusEnabled() || !h.contains(focusMode)) ? false : z;
        String str = a;
        Log.i(str, "Current focus mode '" + focusMode + "'; use auto focus? " + this.d);
        start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a() {
        if (!this.b && !this.f.hasMessages(this.g)) {
            this.f.sendMessageDelayed(this.f.obtainMessage(this.g), SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }

    public void start() {
        this.b = false;
        b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (this.d && !this.b && !this.c) {
            try {
                this.e.autoFocus(this.j);
                this.c = true;
            } catch (RuntimeException e) {
                Log.w(a, "Unexpected exception while focusing", e);
                a();
            }
        }
    }

    private void c() {
        this.f.removeMessages(this.g);
    }

    public void stop() {
        this.b = true;
        this.c = false;
        c();
        if (this.d) {
            try {
                this.e.cancelAutoFocus();
            } catch (RuntimeException e) {
                Log.w(a, "Unexpected exception while cancelling focusing", e);
            }
        }
    }
}
