package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import com.journeyapps.barcodescanner.Size;
import java.util.List;

/* loaded from: classes2.dex */
public class DisplayConfiguration {
    private static final String a = "DisplayConfiguration";
    private Size b;
    private int c;
    private boolean d = false;
    private PreviewScalingStrategy e = new FitCenterStrategy();

    public DisplayConfiguration(int i) {
        this.c = i;
    }

    public DisplayConfiguration(int i, Size size) {
        this.c = i;
        this.b = size;
    }

    public int getRotation() {
        return this.c;
    }

    public Size getViewfinderSize() {
        return this.b;
    }

    public PreviewScalingStrategy getPreviewScalingStrategy() {
        return this.e;
    }

    public void setPreviewScalingStrategy(PreviewScalingStrategy previewScalingStrategy) {
        this.e = previewScalingStrategy;
    }

    public Size getDesiredPreviewSize(boolean z) {
        Size size = this.b;
        if (size == null) {
            return null;
        }
        return z ? size.rotate() : size;
    }

    public Size getBestPreviewSize(List<Size> list, boolean z) {
        return this.e.getBestPreviewSize(list, getDesiredPreviewSize(z));
    }

    public Rect scalePreview(Size size) {
        return this.e.scalePreview(size, this.b);
    }
}
