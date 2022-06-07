package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.util.ExceptionPassthroughInputStream;
import com.bumptech.glide.util.MarkEnforcingInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class StreamBitmapDecoder implements ResourceDecoder<InputStream, Bitmap> {
    private final Downsampler a;
    private final ArrayPool b;

    public StreamBitmapDecoder(Downsampler downsampler, ArrayPool arrayPool) {
        this.a = downsampler;
        this.b = arrayPool;
    }

    public boolean handles(@NonNull InputStream inputStream, @NonNull Options options) {
        return this.a.handles(inputStream);
    }

    public Resource<Bitmap> decode(@NonNull InputStream inputStream, int i, int i2, @NonNull Options options) throws IOException {
        RecyclableBufferedInputStream recyclableBufferedInputStream;
        boolean z;
        if (inputStream instanceof RecyclableBufferedInputStream) {
            recyclableBufferedInputStream = (RecyclableBufferedInputStream) inputStream;
            z = false;
        } else {
            z = true;
            recyclableBufferedInputStream = new RecyclableBufferedInputStream(inputStream, this.b);
        }
        ExceptionPassthroughInputStream obtain = ExceptionPassthroughInputStream.obtain(recyclableBufferedInputStream);
        try {
            return this.a.decode(new MarkEnforcingInputStream(obtain), i, i2, options, new a(recyclableBufferedInputStream, obtain));
        } finally {
            obtain.release();
            if (z) {
                recyclableBufferedInputStream.release();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a implements Downsampler.DecodeCallbacks {
        private final RecyclableBufferedInputStream a;
        private final ExceptionPassthroughInputStream b;

        a(RecyclableBufferedInputStream recyclableBufferedInputStream, ExceptionPassthroughInputStream exceptionPassthroughInputStream) {
            this.a = recyclableBufferedInputStream;
            this.b = exceptionPassthroughInputStream;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
        public void onObtainBounds() {
            this.a.fixMarkLimit();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
        public void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) throws IOException {
            IOException exception = this.b.getException();
            if (exception != null) {
                if (bitmap != null) {
                    bitmapPool.put(bitmap);
                }
                throw exception;
            }
        }
    }
}
