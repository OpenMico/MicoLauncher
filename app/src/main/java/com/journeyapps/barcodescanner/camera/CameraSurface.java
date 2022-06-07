package com.journeyapps.barcodescanner.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import java.io.IOException;

/* loaded from: classes2.dex */
public class CameraSurface {
    private SurfaceHolder a;
    private SurfaceTexture b;

    public CameraSurface(SurfaceHolder surfaceHolder) {
        if (surfaceHolder != null) {
            this.a = surfaceHolder;
            return;
        }
        throw new IllegalArgumentException("surfaceHolder may not be null");
    }

    public CameraSurface(SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null) {
            this.b = surfaceTexture;
            return;
        }
        throw new IllegalArgumentException("surfaceTexture may not be null");
    }

    public SurfaceHolder getSurfaceHolder() {
        return this.a;
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.b;
    }

    public void setPreview(Camera camera) throws IOException {
        SurfaceHolder surfaceHolder = this.a;
        if (surfaceHolder != null) {
            camera.setPreviewDisplay(surfaceHolder);
        } else {
            camera.setPreviewTexture(this.b);
        }
    }
}
