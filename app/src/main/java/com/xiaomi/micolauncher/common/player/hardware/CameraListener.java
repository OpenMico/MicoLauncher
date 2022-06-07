package com.xiaomi.micolauncher.common.player.hardware;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.AsyncTask;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;
import kotlinx.coroutines.DebugKt;

@TargetApi(15)
/* loaded from: classes3.dex */
public class CameraListener {
    private final int a;
    private int b;
    private int c;
    public final int cameraId;
    private int d;
    private Camera h;
    private SurfaceTexture i;
    private FloatBuffer j;
    public final float[] addent = {0.0f, 0.0f};
    private boolean e = true;
    private boolean f = false;
    private boolean g = false;

    public static int findCameraId(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == i) {
                return i2;
            }
        }
        return -1;
    }

    public CameraListener(int i, int i2, int i3, int i4, int i5) {
        this.a = i;
        this.cameraId = i2;
        this.d = a(i2, i5);
        this.b = i3;
        this.c = i4;
        a(this.d);
    }

    public FloatBuffer getOrientationMatrix() {
        return this.j;
    }

    public void register() {
        if (this.e) {
            this.e = false;
            b();
            a();
        }
    }

    public void unregister() {
        this.e = true;
        b();
    }

    public synchronized void update() {
        if (this.i != null && this.g) {
            this.i.updateTexImage();
            this.g = false;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.xiaomi.micolauncher.common.player.hardware.CameraListener$1] */
    @SuppressLint({"StaticFieldLeak"})
    private void a() {
        if (!this.f) {
            this.f = true;
            new AsyncTask<Void, Void, Camera>() { // from class: com.xiaomi.micolauncher.common.player.hardware.CameraListener.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Camera doInBackground(Void... voidArr) {
                    if (CameraListener.this.e) {
                        return null;
                    }
                    try {
                        return Camera.open(CameraListener.this.cameraId);
                    } catch (RuntimeException unused) {
                        return null;
                    }
                }

                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public void onPostExecute(Camera camera) {
                    if (camera != null && !CameraListener.this.a(camera)) {
                        camera.release();
                    }
                    CameraListener.this.f = false;
                }
            }.execute(new Void[0]);
        }
    }

    private void b() {
        Camera camera = this.h;
        if (camera != null) {
            camera.stopPreview();
            this.h.release();
            this.h = null;
            this.i.release();
            this.i = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Camera camera) {
        if (this.e || camera == null) {
            return false;
        }
        camera.setDisplayOrientation(this.d);
        Camera.Parameters parameters = camera.getParameters();
        parameters.setRotation(this.d);
        a(parameters);
        b(parameters);
        c(parameters);
        camera.setParameters(parameters);
        this.i = new SurfaceTexture(this.a);
        this.i.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.xiaomi.micolauncher.common.player.hardware.CameraListener.2
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                synchronized (CameraListener.this) {
                    CameraListener.this.g = true;
                }
            }
        });
        try {
            camera.setPreviewTexture(this.i);
            this.h = camera;
            camera.startPreview();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    private static int a(int i, int i2) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        return ((cameraInfo.orientation - i2) + 360) % 360;
    }

    private void a(int i) {
        if (i == 90) {
            this.j = FloatBuffer.wrap(new float[]{0.0f, -1.0f, -1.0f, 0.0f});
            float[] fArr = this.addent;
            fArr[0] = 1.0f;
            fArr[1] = 1.0f;
        } else if (i == 180) {
            this.j = FloatBuffer.wrap(new float[]{-1.0f, 0.0f, 0.0f, 1.0f});
            float[] fArr2 = this.addent;
            fArr2[0] = 1.0f;
            fArr2[1] = 0.0f;
        } else if (i != 270) {
            this.j = FloatBuffer.wrap(new float[]{1.0f, 0.0f, 0.0f, -1.0f});
            float[] fArr3 = this.addent;
            fArr3[0] = 0.0f;
            fArr3[1] = 1.0f;
        } else {
            this.j = FloatBuffer.wrap(new float[]{0.0f, 1.0f, 1.0f, 0.0f});
            float[] fArr4 = this.addent;
            fArr4[0] = 0.0f;
            fArr4[1] = 0.0f;
        }
    }

    private void a(Camera.Parameters parameters) {
        Camera.Size a = a(parameters.getSupportedPreviewSizes(), this.b, this.c, this.d);
        if (a != null) {
            this.b = a.width;
            this.c = a.height;
            parameters.setPreviewSize(this.b, this.c);
        }
    }

    private static Camera.Size a(List<Camera.Size> list, int i, int i2, int i3) {
        int i4;
        int i5;
        if (i3 == 90 || i3 == 270) {
            i4 = i;
            i5 = i2;
        } else {
            i5 = i;
            i4 = i2;
        }
        double d = i5 / i4;
        Camera.Size size = null;
        double d2 = Double.MAX_VALUE;
        Camera.Size size2 = null;
        double d3 = Double.MAX_VALUE;
        for (Camera.Size size3 : list) {
            double abs = Math.abs(size3.height - i4) + Math.abs(size3.width - i5);
            if (abs < d2) {
                size2 = size3;
                d2 = abs;
            }
            if (Math.abs((size3.width / size3.height) - d) < 0.1d && abs < d3) {
                size = size3;
                d3 = abs;
            }
            i5 = i5;
            i4 = i4;
        }
        return size != null ? size : size2;
    }

    private static void b(Camera.Parameters parameters) {
        try {
            int[] a = a(parameters.getSupportedPreviewFpsRange());
            if (a[0] > 0) {
                parameters.setPreviewFpsRange(a[0], a[1]);
            }
        } catch (RuntimeException unused) {
        }
    }

    private static int[] a(List<int[]> list) {
        int[] iArr = {0, 0};
        int size = list.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return iArr;
            }
            int[] iArr2 = list.get(i);
            if (iArr2[0] >= iArr[0] && iArr2[1] > iArr[1]) {
                iArr = iArr2;
            }
            size = i;
        }
    }

    private static void c(Camera.Parameters parameters) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes.contains("continuous-picture")) {
            parameters.setFocusMode("continuous-picture");
        } else if (supportedFocusModes.contains("continuous-video")) {
            parameters.setFocusMode("continuous-video");
        } else if (supportedFocusModes.contains(DebugKt.DEBUG_PROPERTY_VALUE_AUTO)) {
            parameters.setFocusMode(DebugKt.DEBUG_PROPERTY_VALUE_AUTO);
        }
    }
}
