package jp.wasabeef.glide.transformations.gpu;

import androidx.annotation.NonNull;
import java.security.MessageDigest;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;

/* loaded from: classes5.dex */
public class BrightnessFilterTransformation extends GPUFilterTransformation {
    private float a;

    public BrightnessFilterTransformation() {
        this(0.0f);
    }

    public BrightnessFilterTransformation(float f) {
        super(new GPUImageBrightnessFilter());
        this.a = f;
        ((GPUImageBrightnessFilter) getFilter()).setBrightness(this.a);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation
    public String toString() {
        return "BrightnessFilterTransformation(brightness=" + this.a + ")";
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return (obj instanceof BrightnessFilterTransformation) && ((BrightnessFilterTransformation) obj).a == this.a;
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation.1".hashCode() + ((int) ((this.a + 1.0f) * 10.0f));
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation.1" + this.a).getBytes(CHARSET));
    }
}
