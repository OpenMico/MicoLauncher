package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class ByteBufferBitmapDecoder implements ResourceDecoder<ByteBuffer, Bitmap> {
    private final Downsampler a;

    public ByteBufferBitmapDecoder(Downsampler downsampler) {
        this.a = downsampler;
    }

    public boolean handles(@NonNull ByteBuffer byteBuffer, @NonNull Options options) {
        return this.a.handles(byteBuffer);
    }

    public Resource<Bitmap> decode(@NonNull ByteBuffer byteBuffer, int i, int i2, @NonNull Options options) throws IOException {
        return this.a.decode(byteBuffer, i, i2, options);
    }
}
