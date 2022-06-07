package com.blankj.utilcode.util;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes.dex */
public final class FlashlightUtils {
    private static Camera a;
    private static SurfaceTexture b;

    private FlashlightUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isFlashlightEnable() {
        return Utils.getApp().getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    public static boolean isFlashlightOn() {
        if (!a()) {
            return false;
        }
        return "torch".equals(a.getParameters().getFlashMode());
    }

    public static void setFlashlightStatus(boolean z) {
        if (a()) {
            Camera.Parameters parameters = a.getParameters();
            if (z) {
                if (!"torch".equals(parameters.getFlashMode())) {
                    try {
                        a.setPreviewTexture(b);
                        a.startPreview();
                        parameters.setFlashMode("torch");
                        a.setParameters(parameters);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!"off".equals(parameters.getFlashMode())) {
                parameters.setFlashMode("off");
                a.setParameters(parameters);
            }
        }
    }

    public static void destroy() {
        Camera camera = a;
        if (camera != null) {
            camera.release();
            b = null;
            a = null;
        }
    }

    private static boolean a() {
        if (a == null) {
            try {
                a = Camera.open(0);
                b = new SurfaceTexture(0);
            } catch (Throwable th) {
                Log.e("FlashlightUtils", "init failed: ", th);
                return false;
            }
        }
        if (a != null) {
            return true;
        }
        Log.e("FlashlightUtils", "init failed.");
        return false;
    }
}
