package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.cache.DiskCache;
import java.io.File;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DataCacheWriter.java */
/* loaded from: classes.dex */
public class e<DataType> implements DiskCache.Writer {
    private final Encoder<DataType> a;
    private final DataType b;
    private final Options c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Encoder<DataType> encoder, DataType datatype, Options options) {
        this.a = encoder;
        this.b = datatype;
        this.c = options;
    }

    @Override // com.bumptech.glide.load.engine.cache.DiskCache.Writer
    public boolean write(@NonNull File file) {
        return this.a.encode(this.b, file, this.c);
    }
}
