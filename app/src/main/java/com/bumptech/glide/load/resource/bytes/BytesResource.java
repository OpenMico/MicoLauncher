package com.bumptech.glide.load.resource.bytes;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes.dex */
public class BytesResource implements Resource<byte[]> {
    private final byte[] a;

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
    }

    public BytesResource(byte[] bArr) {
        this.a = (byte[]) Preconditions.checkNotNull(bArr);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Class<byte[]> getResourceClass() {
        return byte[].class;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public byte[] get() {
        return this.a;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return this.a.length;
    }
}
