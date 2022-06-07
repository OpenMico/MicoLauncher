package com.xiaomi.micolauncher.skills.voip.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.SystemCameraEvent;

/* loaded from: classes3.dex */
public class CameraStatusHelper {
    public static final int CAMERA_DISABLED = 1;
    public static final int CAMERA_ENABLED = 0;
    private static volatile CameraStatusHelper b;
    ContentObserver a;
    private Context c;
    private boolean d;
    private boolean e;
    private ContentObserver f;

    public static CameraStatusHelper getInstance() {
        if (b == null) {
            synchronized (CameraStatusHelper.class) {
                if (b == null) {
                    b = new CameraStatusHelper(MicoApplication.getGlobalContext());
                }
            }
        }
        return b;
    }

    private CameraStatusHelper(Context context) {
        this.c = context;
        a();
        registerCameraEnabledObserver();
        if (hasLensCover()) {
            b();
            registerCameraCoverObserver();
        }
    }

    public boolean setCameraEnabled(boolean z) {
        L.camera2.d("CameraStatusHelper: set cameraEnabled to %b", Boolean.valueOf(z));
        Bundle bundle = new Bundle();
        bundle.putBoolean("enable", z);
        Bundle call = this.c.getContentResolver().call(Uri.parse("content://com.android.systemui.provider.MICO_FEATURE"), "setCameraEnable", (String) null, bundle);
        return call != null && call.getInt("code", 1) == 0;
    }

    public boolean isCameraEnabled() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        boolean z = false;
        if (Settings.System.getInt(this.c.getContentResolver(), "mi_camera_disable", 0) == 0) {
            z = true;
        }
        this.d = z;
    }

    public boolean hasLensCover() {
        return Hardware.isX10() || Hardware.isX6A();
    }

    public boolean isCameraLensCovered() {
        return hasLensCover() && this.e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        boolean z = true;
        if (Settings.Secure.getInt(this.c.getContentResolver(), "SW_CAMERA_LENS_COVER", 0) != 1) {
            z = false;
        }
        this.e = z;
    }

    public void registerCameraEnabledObserver() {
        L.camera2.d("CameraStatusHelper: registerCameraEnabledObserver");
        Uri uriFor = Settings.System.getUriFor("mi_camera_disable");
        this.f = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaomi.micolauncher.skills.voip.utils.CameraStatusHelper.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                CameraStatusHelper.this.a();
                EventBusRegistry.getEventBus().post(new SystemCameraEvent(CameraStatusHelper.this.d));
                L.camera2.d("CameraStatusHelper: cameraEnabled change to %b", Boolean.valueOf(CameraStatusHelper.this.d));
            }
        };
        this.c.getContentResolver().registerContentObserver(uriFor, false, this.f);
    }

    public void unregisterCameraEnabledObserver() {
        L.camera2.d("CameraStatusHelper: unregisterCameraEnabledObserver");
        if (this.f == null) {
            L.camera2.d("CameraStatusHelper: cameraEnabledObserver is null, can not unregister.");
        } else {
            this.c.getContentResolver().unregisterContentObserver(this.f);
        }
    }

    public void registerCameraCoverObserver() {
        L.camera2.d("CameraStatusHelper: registerCameraCoverObserver");
        Uri uriFor = Settings.Secure.getUriFor("SW_CAMERA_LENS_COVER");
        this.a = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaomi.micolauncher.skills.voip.utils.CameraStatusHelper.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                CameraStatusHelper.this.b();
                L.camera2.d("CameraStatusHelper: cameraLensCovered change to %b", Boolean.valueOf(CameraStatusHelper.this.e));
            }
        };
        this.c.getContentResolver().registerContentObserver(uriFor, false, this.a);
    }

    public void unregisterCameraCoverObserver() {
        L.camera2.d("CameraStatusHelper: unregisterCameraCoverObserver");
        if (this.a == null) {
            L.camera2.d("CameraStatusHelper: cameraCoverObserver is null, can not unregister.");
        } else {
            this.c.getContentResolver().unregisterContentObserver(this.f);
        }
    }
}
