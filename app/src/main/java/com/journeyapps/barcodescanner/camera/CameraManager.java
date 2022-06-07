package com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.zxing.client.android.AmbientLightManager;
import com.google.zxing.client.android.camera.CameraConfigurationUtils;
import com.google.zxing.client.android.camera.open.OpenCameraInterface;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.SourceData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class CameraManager {
    private static final String a = "CameraManager";
    private Camera b;
    private Camera.CameraInfo c;
    private AutoFocusManager d;
    private AmbientLightManager e;
    private boolean f;
    private String g;
    private DisplayConfiguration i;
    private Size j;
    private Size k;
    private Context m;
    private CameraSettings h = new CameraSettings();
    private int l = -1;
    private final a n = new a();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a implements Camera.PreviewCallback {
        private PreviewCallback b;
        private Size c;

        public a() {
        }

        public void a(Size size) {
            this.c = size;
        }

        public void a(PreviewCallback previewCallback) {
            this.b = previewCallback;
        }

        @Override // android.hardware.Camera.PreviewCallback
        public void onPreviewFrame(byte[] bArr, Camera camera) {
            Size size = this.c;
            PreviewCallback previewCallback = this.b;
            if (size == null || previewCallback == null) {
                Log.d(CameraManager.a, "Got preview callback, but no handler or resolution available");
                if (previewCallback != null) {
                    previewCallback.onPreviewError(new Exception("No resolution available"));
                    return;
                }
                return;
            }
            try {
                if (bArr != null) {
                    previewCallback.onPreview(new SourceData(bArr, size.width, size.height, camera.getParameters().getPreviewFormat(), CameraManager.this.getCameraRotation()));
                    return;
                }
                throw new NullPointerException("No preview data received");
            } catch (RuntimeException e) {
                Log.e(CameraManager.a, "Camera preview failed", e);
                previewCallback.onPreviewError(e);
            }
        }
    }

    public CameraManager(Context context) {
        this.m = context;
    }

    public void open() {
        this.b = OpenCameraInterface.open(this.h.getRequestedCameraId());
        if (this.b != null) {
            int cameraId = OpenCameraInterface.getCameraId(this.h.getRequestedCameraId());
            this.c = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, this.c);
            return;
        }
        throw new RuntimeException("Failed to open camera");
    }

    public void configure() {
        if (this.b != null) {
            d();
            return;
        }
        throw new RuntimeException("Camera not open");
    }

    public void setPreviewDisplay(SurfaceHolder surfaceHolder) throws IOException {
        setPreviewDisplay(new CameraSurface(surfaceHolder));
    }

    public void setPreviewDisplay(CameraSurface cameraSurface) throws IOException {
        cameraSurface.setPreview(this.b);
    }

    public void startPreview() {
        Camera camera = this.b;
        if (camera != null && !this.f) {
            camera.startPreview();
            this.f = true;
            this.d = new AutoFocusManager(this.b, this.h);
            this.e = new AmbientLightManager(this.m, this, this.h);
            this.e.start();
        }
    }

    public void stopPreview() {
        AutoFocusManager autoFocusManager = this.d;
        if (autoFocusManager != null) {
            autoFocusManager.stop();
            this.d = null;
        }
        AmbientLightManager ambientLightManager = this.e;
        if (ambientLightManager != null) {
            ambientLightManager.stop();
            this.e = null;
        }
        Camera camera = this.b;
        if (camera != null && this.f) {
            camera.stopPreview();
            this.n.a((PreviewCallback) null);
            this.f = false;
        }
    }

    public void close() {
        Camera camera = this.b;
        if (camera != null) {
            camera.release();
            this.b = null;
        }
    }

    public boolean isCameraRotated() {
        int i = this.l;
        if (i != -1) {
            return i % Opcodes.GETFIELD != 0;
        }
        throw new IllegalStateException("Rotation not calculated yet. Call configure() first.");
    }

    public int getCameraRotation() {
        return this.l;
    }

    private Camera.Parameters b() {
        Camera.Parameters parameters = this.b.getParameters();
        String str = this.g;
        if (str == null) {
            this.g = parameters.flatten();
        } else {
            parameters.unflatten(str);
        }
        return parameters;
    }

    private void a(boolean z) {
        Camera.Parameters b = b();
        if (b == null) {
            Log.w(a, "Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }
        String str = a;
        Log.i(str, "Initial camera parameters: " + b.flatten());
        if (z) {
            Log.w(a, "In camera config safe mode -- most settings will not be honored");
        }
        CameraConfigurationUtils.setFocus(b, this.h.getFocusMode(), z);
        if (!z) {
            CameraConfigurationUtils.setTorch(b, false);
            if (this.h.isScanInverted()) {
                CameraConfigurationUtils.setInvertColor(b);
            }
            if (this.h.isBarcodeSceneModeEnabled()) {
                CameraConfigurationUtils.setBarcodeSceneMode(b);
            }
            if (this.h.isMeteringEnabled() && Build.VERSION.SDK_INT >= 15) {
                CameraConfigurationUtils.setVideoStabilization(b);
                CameraConfigurationUtils.setFocusArea(b);
                CameraConfigurationUtils.setMetering(b);
            }
        }
        List<Size> a2 = a(b);
        if (a2.size() == 0) {
            this.j = null;
        } else {
            this.j = this.i.getBestPreviewSize(a2, isCameraRotated());
            b.setPreviewSize(this.j.width, this.j.height);
        }
        if (Build.DEVICE.equals("glass-1")) {
            CameraConfigurationUtils.setBestPreviewFPS(b);
        }
        String str2 = a;
        Log.i(str2, "Final camera parameters: " + b.flatten());
        this.b.setParameters(b);
    }

    private static List<Size> a(Camera.Parameters parameters) {
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        ArrayList arrayList = new ArrayList();
        if (supportedPreviewSizes == null) {
            Camera.Size previewSize = parameters.getPreviewSize();
            if (previewSize != null) {
                arrayList.add(new Size(previewSize.width, previewSize.height));
            }
            return arrayList;
        }
        for (Camera.Size size : supportedPreviewSizes) {
            arrayList.add(new Size(size.width, size.height));
        }
        return arrayList;
    }

    private int c() {
        int i;
        int i2 = 0;
        switch (this.i.getRotation()) {
            case 1:
                i2 = 90;
                break;
            case 2:
                i2 = Opcodes.GETFIELD;
                break;
            case 3:
                i2 = 270;
                break;
        }
        if (this.c.facing == 1) {
            i = (360 - ((this.c.orientation + i2) % 360)) % 360;
        } else {
            i = ((this.c.orientation - i2) + 360) % 360;
        }
        String str = a;
        Log.i(str, "Camera Display Orientation: " + i);
        return i;
    }

    private void a(int i) {
        this.b.setDisplayOrientation(i);
    }

    private void d() {
        try {
            this.l = c();
            a(this.l);
        } catch (Exception unused) {
            Log.w(a, "Failed to set rotation.");
        }
        try {
            a(false);
        } catch (Exception unused2) {
            try {
                a(true);
            } catch (Exception unused3) {
                Log.w(a, "Camera rejected even safe-mode parameters! No configuration");
            }
        }
        Camera.Size previewSize = this.b.getParameters().getPreviewSize();
        if (previewSize == null) {
            this.k = this.j;
        } else {
            this.k = new Size(previewSize.width, previewSize.height);
        }
        this.n.a(this.k);
    }

    public boolean isOpen() {
        return this.b != null;
    }

    public Size getNaturalPreviewSize() {
        return this.k;
    }

    public Size getPreviewSize() {
        if (this.k == null) {
            return null;
        }
        if (isCameraRotated()) {
            return this.k.rotate();
        }
        return this.k;
    }

    public void requestPreviewFrame(PreviewCallback previewCallback) {
        Camera camera = this.b;
        if (camera != null && this.f) {
            this.n.a(previewCallback);
            camera.setOneShotPreviewCallback(this.n);
        }
    }

    public CameraSettings getCameraSettings() {
        return this.h;
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        this.h = cameraSettings;
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.i;
    }

    public void setDisplayConfiguration(DisplayConfiguration displayConfiguration) {
        this.i = displayConfiguration;
    }

    public void setTorch(boolean z) {
        if (this.b != null) {
            try {
                if (z != isTorchOn()) {
                    if (this.d != null) {
                        this.d.stop();
                    }
                    Camera.Parameters parameters = this.b.getParameters();
                    CameraConfigurationUtils.setTorch(parameters, z);
                    if (this.h.isExposureEnabled()) {
                        CameraConfigurationUtils.setBestExposure(parameters, z);
                    }
                    this.b.setParameters(parameters);
                    if (this.d != null) {
                        this.d.start();
                    }
                }
            } catch (RuntimeException e) {
                Log.e(a, "Failed to set torch", e);
            }
        }
    }

    public void changeCameraParameters(CameraParametersCallback cameraParametersCallback) {
        Camera camera = this.b;
        if (camera != null) {
            try {
                camera.setParameters(cameraParametersCallback.changeCameraParameters(camera.getParameters()));
            } catch (RuntimeException e) {
                Log.e(a, "Failed to change camera parameters", e);
            }
        }
    }

    public boolean isTorchOn() {
        String flashMode;
        Camera.Parameters parameters = this.b.getParameters();
        if (parameters == null || (flashMode = parameters.getFlashMode()) == null) {
            return false;
        }
        return "on".equals(flashMode) || "torch".equals(flashMode);
    }

    public Camera getCamera() {
        return this.b;
    }
}
