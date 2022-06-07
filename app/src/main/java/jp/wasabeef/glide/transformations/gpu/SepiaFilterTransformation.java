package jp.wasabeef.glide.transformations.gpu;

import androidx.annotation.NonNull;
import java.security.MessageDigest;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;

/* loaded from: classes5.dex */
public class SepiaFilterTransformation extends GPUFilterTransformation {
    private float a;

    public SepiaFilterTransformation() {
        this(1.0f);
    }

    public SepiaFilterTransformation(float f) {
        super(new GPUImageSepiaFilter());
        this.a = f;
        ((GPUImageSepiaFilter) getFilter()).setIntensity(this.a);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation
    public String toString() {
        return "SepiaFilterTransformation(intensity=" + this.a + ")";
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof SepiaFilterTransformation;
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation.1".hashCode() + ((int) (this.a * 10.0f));
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation.1" + this.a).getBytes(CHARSET));
    }
}
