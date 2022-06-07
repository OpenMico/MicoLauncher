package com.journeyapps.barcodescanner.camera;

/* loaded from: classes2.dex */
public class CameraSettings {
    private int a = -1;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private boolean e = true;
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;
    private FocusMode i = FocusMode.AUTO;

    /* loaded from: classes2.dex */
    public enum FocusMode {
        AUTO,
        CONTINUOUS,
        INFINITY,
        MACRO
    }

    public int getRequestedCameraId() {
        return this.a;
    }

    public void setRequestedCameraId(int i) {
        this.a = i;
    }

    public boolean isScanInverted() {
        return this.b;
    }

    public void setScanInverted(boolean z) {
        this.b = z;
    }

    public boolean isBarcodeSceneModeEnabled() {
        return this.c;
    }

    public void setBarcodeSceneModeEnabled(boolean z) {
        this.c = z;
    }

    public boolean isExposureEnabled() {
        return this.g;
    }

    public void setExposureEnabled(boolean z) {
        this.g = z;
    }

    public boolean isMeteringEnabled() {
        return this.d;
    }

    public void setMeteringEnabled(boolean z) {
        this.d = z;
    }

    public boolean isAutoFocusEnabled() {
        return this.e;
    }

    public void setAutoFocusEnabled(boolean z) {
        this.e = z;
        if (z && this.f) {
            this.i = FocusMode.CONTINUOUS;
        } else if (z) {
            this.i = FocusMode.AUTO;
        } else {
            this.i = null;
        }
    }

    public boolean isContinuousFocusEnabled() {
        return this.f;
    }

    public void setContinuousFocusEnabled(boolean z) {
        this.f = z;
        if (z) {
            this.i = FocusMode.CONTINUOUS;
        } else if (this.e) {
            this.i = FocusMode.AUTO;
        } else {
            this.i = null;
        }
    }

    public FocusMode getFocusMode() {
        return this.i;
    }

    public void setFocusMode(FocusMode focusMode) {
        this.i = focusMode;
    }

    public boolean isAutoTorchEnabled() {
        return this.h;
    }

    public void setAutoTorchEnabled(boolean z) {
        this.h = z;
    }
}
