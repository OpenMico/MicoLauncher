package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class InputStreamRewinder implements DataRewinder<InputStream> {
    private final RecyclableBufferedInputStream a;

    public InputStreamRewinder(InputStream inputStream, ArrayPool arrayPool) {
        this.a = new RecyclableBufferedInputStream(inputStream, arrayPool);
        this.a.mark(5242880);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.bumptech.glide.load.data.DataRewinder
    @NonNull
    public InputStream rewindAndGet() throws IOException {
        this.a.reset();
        return this.a;
    }

    @Override // com.bumptech.glide.load.data.DataRewinder
    public void cleanup() {
        this.a.release();
    }

    public void fixMarkLimits() {
        this.a.fixMarkLimit();
    }

    /* loaded from: classes.dex */
    public static final class Factory implements DataRewinder.Factory<InputStream> {
        private final ArrayPool a;

        public Factory(ArrayPool arrayPool) {
            this.a = arrayPool;
        }

        @NonNull
        public DataRewinder<InputStream> build(InputStream inputStream) {
            return new InputStreamRewinder(inputStream, this.a);
        }

        @Override // com.bumptech.glide.load.data.DataRewinder.Factory
        @NonNull
        public Class<InputStream> getDataClass() {
            return InputStream.class;
        }
    }
}
