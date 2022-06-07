package jp.wasabeef.glide.transformations.gpu;

import androidx.annotation.NonNull;
import java.security.MessageDigest;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;

/* loaded from: classes5.dex */
public class InvertFilterTransformation extends GPUFilterTransformation {
    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation
    public String toString() {
        return "InvertFilterTransformation()";
    }

    public InvertFilterTransformation() {
        super(new GPUImageColorInvertFilter());
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof InvertFilterTransformation;
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation.1".hashCode();
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update("jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation.1".getBytes(CHARSET));
    }
}
