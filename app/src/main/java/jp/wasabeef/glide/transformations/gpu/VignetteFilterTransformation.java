package jp.wasabeef.glide.transformations.gpu;

import android.graphics.PointF;
import androidx.annotation.NonNull;
import java.security.MessageDigest;
import java.util.Arrays;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;

/* loaded from: classes5.dex */
public class VignetteFilterTransformation extends GPUFilterTransformation {
    private PointF a;
    private float[] b;
    private float c;
    private float d;

    public VignetteFilterTransformation() {
        this(new PointF(0.5f, 0.5f), new float[]{0.0f, 0.0f, 0.0f}, 0.0f, 0.75f);
    }

    public VignetteFilterTransformation(PointF pointF, float[] fArr, float f, float f2) {
        super(new GPUImageVignetteFilter());
        this.a = pointF;
        this.b = fArr;
        this.c = f;
        this.d = f2;
        GPUImageVignetteFilter gPUImageVignetteFilter = (GPUImageVignetteFilter) getFilter();
        gPUImageVignetteFilter.setVignetteCenter(this.a);
        gPUImageVignetteFilter.setVignetteColor(this.b);
        gPUImageVignetteFilter.setVignetteStart(this.c);
        gPUImageVignetteFilter.setVignetteEnd(this.d);
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation
    public String toString() {
        return "VignetteFilterTransformation(center=" + this.a.toString() + ",color=" + Arrays.toString(this.b) + ",start=" + this.c + ",end=" + this.d + ")";
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof VignetteFilterTransformation) {
            VignetteFilterTransformation vignetteFilterTransformation = (VignetteFilterTransformation) obj;
            if (vignetteFilterTransformation.a.equals(this.a.x, this.a.y) && Arrays.equals(vignetteFilterTransformation.b, this.b) && vignetteFilterTransformation.c == this.c && vignetteFilterTransformation.d == this.d) {
                return true;
            }
        }
        return false;
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation.1".hashCode() + this.a.hashCode() + Arrays.hashCode(this.b) + ((int) (this.c * 100.0f)) + ((int) (this.d * 10.0f));
    }

    @Override // jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation, jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation.1" + this.a + Arrays.hashCode(this.b) + this.c + this.d).getBytes(CHARSET));
    }
}
