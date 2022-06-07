package com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.Util;

/* loaded from: classes2.dex */
public class CameraInstance {
    private static final String a = "CameraInstance";
    private a b;
    private CameraSurface c;
    private CameraManager d;
    private Handler e;
    private DisplayConfiguration f;
    private boolean g;
    private boolean h;
    private Handler i;
    private CameraSettings j;
    private Runnable k;
    private Runnable l;
    private Runnable m;
    private Runnable n;

    public CameraInstance(Context context) {
        this.g = false;
        this.h = true;
        this.j = new CameraSettings();
        this.k = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.a, "Opening camera");
                    CameraInstance.this.d.open();
                } catch (Exception e) {
                    CameraInstance.this.a(e);
                    Log.e(CameraInstance.a, "Failed to open camera", e);
                }
            }
        };
        this.l = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.a, "Configuring camera");
                    CameraInstance.this.d.configure();
                    if (CameraInstance.this.e != null) {
                        CameraInstance.this.e.obtainMessage(R.id.zxing_prewiew_size_ready, CameraInstance.this.b()).sendToTarget();
                    }
                } catch (Exception e) {
                    CameraInstance.this.a(e);
                    Log.e(CameraInstance.a, "Failed to configure camera", e);
                }
            }
        };
        this.m = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.a, "Starting preview");
                    CameraInstance.this.d.setPreviewDisplay(CameraInstance.this.c);
                    CameraInstance.this.d.startPreview();
                } catch (Exception e) {
                    CameraInstance.this.a(e);
                    Log.e(CameraInstance.a, "Failed to start preview", e);
                }
            }
        };
        this.n = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.a, "Closing camera");
                    CameraInstance.this.d.stopPreview();
                    CameraInstance.this.d.close();
                } catch (Exception e) {
                    Log.e(CameraInstance.a, "Failed to close camera", e);
                }
                CameraInstance.this.h = true;
                CameraInstance.this.e.sendEmptyMessage(R.id.zxing_camera_closed);
                CameraInstance.this.b.b();
            }
        };
        Util.validateMainThread();
        this.b = a.a();
        this.d = new CameraManager(context);
        this.d.setCameraSettings(this.j);
        this.i = new Handler();
    }

    public CameraInstance(CameraManager cameraManager) {
        this.g = false;
        this.h = true;
        this.j = new CameraSettings();
        this.k = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.a, "Opening camera");
                    CameraInstance.this.d.open();
                } catch (Exception e) {
                    CameraInstance.this.a(e);
                    Log.e(CameraInstance.a, "Failed to open camera", e);
                }
            }
        };
        this.l = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.a, "Configuring camera");
                    CameraInstance.this.d.configure();
                    if (CameraInstance.this.e != null) {
                        CameraInstance.this.e.obtainMessage(R.id.zxing_prewiew_size_ready, CameraInstance.this.b()).sendToTarget();
                    }
                } catch (Exception e) {
                    CameraInstance.this.a(e);
                    Log.e(CameraInstance.a, "Failed to configure camera", e);
                }
            }
        };
        this.m = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.a, "Starting preview");
                    CameraInstance.this.d.setPreviewDisplay(CameraInstance.this.c);
                    CameraInstance.this.d.startPreview();
                } catch (Exception e) {
                    CameraInstance.this.a(e);
                    Log.e(CameraInstance.a, "Failed to start preview", e);
                }
            }
        };
        this.n = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.a, "Closing camera");
                    CameraInstance.this.d.stopPreview();
                    CameraInstance.this.d.close();
                } catch (Exception e) {
                    Log.e(CameraInstance.a, "Failed to close camera", e);
                }
                CameraInstance.this.h = true;
                CameraInstance.this.e.sendEmptyMessage(R.id.zxing_camera_closed);
                CameraInstance.this.b.b();
            }
        };
        Util.validateMainThread();
        this.d = cameraManager;
    }

    public void setDisplayConfiguration(DisplayConfiguration displayConfiguration) {
        this.f = displayConfiguration;
        this.d.setDisplayConfiguration(displayConfiguration);
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.f;
    }

    public void setReadyHandler(Handler handler) {
        this.e = handler;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        setSurface(new CameraSurface(surfaceHolder));
    }

    public void setSurface(CameraSurface cameraSurface) {
        this.c = cameraSurface;
    }

    public CameraSettings getCameraSettings() {
        return this.j;
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        if (!this.g) {
            this.j = cameraSettings;
            this.d.setCameraSettings(cameraSettings);
        }
    }

    public Size b() {
        return this.d.getPreviewSize();
    }

    public int getCameraRotation() {
        return this.d.getCameraRotation();
    }

    public void open() {
        Util.validateMainThread();
        this.g = true;
        this.h = false;
        this.b.b(this.k);
    }

    public void configureCamera() {
        Util.validateMainThread();
        c();
        this.b.a(this.l);
    }

    public void startPreview() {
        Util.validateMainThread();
        c();
        this.b.a(this.m);
    }

    public void setTorch(final boolean z) {
        Util.validateMainThread();
        if (this.g) {
            this.b.a(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.1
                @Override // java.lang.Runnable
                public void run() {
                    CameraInstance.this.d.setTorch(z);
                }
            });
        }
    }

    public void changeCameraParameters(final CameraParametersCallback cameraParametersCallback) {
        Util.validateMainThread();
        if (this.g) {
            this.b.a(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.2
                @Override // java.lang.Runnable
                public void run() {
                    CameraInstance.this.d.changeCameraParameters(cameraParametersCallback);
                }
            });
        }
    }

    public void close() {
        Util.validateMainThread();
        if (this.g) {
            this.b.a(this.n);
        } else {
            this.h = true;
        }
        this.g = false;
    }

    public boolean isOpen() {
        return this.g;
    }

    public boolean isCameraClosed() {
        return this.h;
    }

    public void requestPreview(final PreviewCallback previewCallback) {
        this.i.post(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.3
            @Override // java.lang.Runnable
            public void run() {
                if (!CameraInstance.this.g) {
                    Log.d(CameraInstance.a, "Camera is closed, not requesting preview");
                } else {
                    CameraInstance.this.b.a(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraInstance.this.d.requestPreviewFrame(previewCallback);
                        }
                    });
                }
            }
        });
    }

    private void c() {
        if (!this.g) {
            throw new IllegalStateException("CameraInstance is not open");
        }
    }

    public void a(Exception exc) {
        Handler handler = this.e;
        if (handler != null) {
            handler.obtainMessage(R.id.zxing_camera_error, exc).sendToTarget();
        }
    }

    protected CameraManager getCameraManager() {
        return this.d;
    }

    protected a getCameraThread() {
        return this.b;
    }

    protected CameraSurface getSurface() {
        return this.c;
    }
}
