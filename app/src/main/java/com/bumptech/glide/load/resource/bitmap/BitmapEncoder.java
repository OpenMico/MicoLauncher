package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;

/* loaded from: classes.dex */
public class BitmapEncoder implements ResourceEncoder<Bitmap> {
    @Nullable
    private final ArrayPool a;
    public static final Option<Integer> COMPRESSION_QUALITY = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", 90);
    public static final Option<Bitmap.CompressFormat> COMPRESSION_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");

    public BitmapEncoder(@NonNull ArrayPool arrayPool) {
        this.a = arrayPool;
    }

    @Deprecated
    public BitmapEncoder() {
        this.a = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0068, code lost:
        if (r5 != null) goto L_0x004b;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean encode(@androidx.annotation.NonNull com.bumptech.glide.load.engine.Resource<android.graphics.Bitmap> r8, @androidx.annotation.NonNull java.io.File r9, @androidx.annotation.NonNull com.bumptech.glide.load.Options r10) {
        /*
            r7 = this;
            java.lang.Object r8 = r8.get()
            android.graphics.Bitmap r8 = (android.graphics.Bitmap) r8
            android.graphics.Bitmap$CompressFormat r0 = r7.a(r8, r10)
            java.lang.String r1 = "encode: [%dx%d] %s"
            int r2 = r8.getWidth()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            int r3 = r8.getHeight()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            com.bumptech.glide.util.pool.GlideTrace.beginSectionFormat(r1, r2, r3, r0)
            long r1 = com.bumptech.glide.util.LogTime.getLogTime()     // Catch: all -> 0x00c6
            com.bumptech.glide.load.Option<java.lang.Integer> r3 = com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_QUALITY     // Catch: all -> 0x00c6
            java.lang.Object r3 = r10.get(r3)     // Catch: all -> 0x00c6
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch: all -> 0x00c6
            int r3 = r3.intValue()     // Catch: all -> 0x00c6
            r4 = 0
            r5 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch: IOException -> 0x0057, all -> 0x0055
            r6.<init>(r9)     // Catch: IOException -> 0x0057, all -> 0x0055
            com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool r9 = r7.a     // Catch: IOException -> 0x0052, all -> 0x004f
            if (r9 == 0) goto L_0x0043
            com.bumptech.glide.load.data.BufferedOutputStream r9 = new com.bumptech.glide.load.data.BufferedOutputStream     // Catch: IOException -> 0x0052, all -> 0x004f
            com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool r5 = r7.a     // Catch: IOException -> 0x0052, all -> 0x004f
            r9.<init>(r6, r5)     // Catch: IOException -> 0x0052, all -> 0x004f
            r5 = r9
            goto L_0x0044
        L_0x0043:
            r5 = r6
        L_0x0044:
            r8.compress(r0, r3, r5)     // Catch: IOException -> 0x0057, all -> 0x0055
            r5.close()     // Catch: IOException -> 0x0057, all -> 0x0055
            r4 = 1
        L_0x004b:
            r5.close()     // Catch: IOException -> 0x006b, all -> 0x00c6
            goto L_0x006b
        L_0x004f:
            r8 = move-exception
            r5 = r6
            goto L_0x00c0
        L_0x0052:
            r9 = move-exception
            r5 = r6
            goto L_0x0058
        L_0x0055:
            r8 = move-exception
            goto L_0x00c0
        L_0x0057:
            r9 = move-exception
        L_0x0058:
            java.lang.String r3 = "BitmapEncoder"
            r6 = 3
            boolean r3 = android.util.Log.isLoggable(r3, r6)     // Catch: all -> 0x0055
            if (r3 == 0) goto L_0x0068
            java.lang.String r3 = "BitmapEncoder"
            java.lang.String r6 = "Failed to encode Bitmap"
            android.util.Log.d(r3, r6, r9)     // Catch: all -> 0x0055
        L_0x0068:
            if (r5 == 0) goto L_0x006b
            goto L_0x004b
        L_0x006b:
            java.lang.String r9 = "BitmapEncoder"
            r3 = 2
            boolean r9 = android.util.Log.isLoggable(r9, r3)     // Catch: all -> 0x00c6
            if (r9 == 0) goto L_0x00bc
            java.lang.String r9 = "BitmapEncoder"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: all -> 0x00c6
            r3.<init>()     // Catch: all -> 0x00c6
            java.lang.String r5 = "Compressed with type: "
            r3.append(r5)     // Catch: all -> 0x00c6
            r3.append(r0)     // Catch: all -> 0x00c6
            java.lang.String r0 = " of size "
            r3.append(r0)     // Catch: all -> 0x00c6
            int r0 = com.bumptech.glide.util.Util.getBitmapByteSize(r8)     // Catch: all -> 0x00c6
            r3.append(r0)     // Catch: all -> 0x00c6
            java.lang.String r0 = " in "
            r3.append(r0)     // Catch: all -> 0x00c6
            double r0 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1)     // Catch: all -> 0x00c6
            r3.append(r0)     // Catch: all -> 0x00c6
            java.lang.String r0 = ", options format: "
            r3.append(r0)     // Catch: all -> 0x00c6
            com.bumptech.glide.load.Option<android.graphics.Bitmap$CompressFormat> r0 = com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_FORMAT     // Catch: all -> 0x00c6
            java.lang.Object r10 = r10.get(r0)     // Catch: all -> 0x00c6
            r3.append(r10)     // Catch: all -> 0x00c6
            java.lang.String r10 = ", hasAlpha: "
            r3.append(r10)     // Catch: all -> 0x00c6
            boolean r8 = r8.hasAlpha()     // Catch: all -> 0x00c6
            r3.append(r8)     // Catch: all -> 0x00c6
            java.lang.String r8 = r3.toString()     // Catch: all -> 0x00c6
            android.util.Log.v(r9, r8)     // Catch: all -> 0x00c6
        L_0x00bc:
            com.bumptech.glide.util.pool.GlideTrace.endSection()
            return r4
        L_0x00c0:
            if (r5 == 0) goto L_0x00c5
            r5.close()     // Catch: IOException -> 0x00c5, all -> 0x00c6
        L_0x00c5:
            throw r8     // Catch: all -> 0x00c6
        L_0x00c6:
            r8 = move-exception
            com.bumptech.glide.util.pool.GlideTrace.endSection()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.BitmapEncoder.encode(com.bumptech.glide.load.engine.Resource, java.io.File, com.bumptech.glide.load.Options):boolean");
    }

    private Bitmap.CompressFormat a(Bitmap bitmap, Options options) {
        Bitmap.CompressFormat compressFormat = (Bitmap.CompressFormat) options.get(COMPRESSION_FORMAT);
        if (compressFormat != null) {
            return compressFormat;
        }
        if (bitmap.hasAlpha()) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    @Override // com.bumptech.glide.load.ResourceEncoder
    @NonNull
    public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
        return EncodeStrategy.TRANSFORMED;
    }
}
