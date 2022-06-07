package jp.wasabeef.glide.transformations.gpu;

import android.graphics.PointF;
import androidx.annotation.NonNull;
import java.security.MessageDigest;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;

/* loaded from: classes5.dex */
public class SwirlFilterTransformation extends GPUFilterTransformation {
    private float a;
    private float b;
    private PointF c;

    public SwirlFilterTransformation() {
        this(0.5f, 1.0f, new PointF(0.5f, 0.5f));
    }

    public SwirlFilterTransformation(float f, float f2, PointF pointF) {
        super(new GPUImageSwirlFilter());
        this.a = f;
        this.b = f2;
        this.c = pointF;
        GPUImageSwirlFilter gPUImageSwirlFilter = (GPUImageSwirlFilter) getFilter();
        gPUImageSwirlFilter.setRadius(this.a);
        gPUImageSwirlFilter.setAngle(this.b);
        gPUImageSwirlFilter.setCenter(this.c);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation
    public String toString() {
        return "SwirlFilterTransformation(radius=" + this.a + ",angle=" + this.b + ",center=" + this.c.toString() + ")";
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof SwirlFilterTransformation) {
            SwirlFilterTransformation swirlFilterTransformation = (SwirlFilterTransformation) obj;
            float f = swirlFilterTransformation.a;
            float f2 = this.a;
            if (f == f2 && swirlFilterTransformation.b == f2 && swirlFilterTransformation.c.equals(this.c.x, this.c.y)) {
                return true;
            }
        }
        return false;
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation.1".hashCode() + ((int) (this.a * 1000.0f)) + ((int) (this.b * 10.0f)) + this.c.hashCode();
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation.1" + this.a + this.b + this.c.hashCode()).getBytes(CHARSET));
    }
}
