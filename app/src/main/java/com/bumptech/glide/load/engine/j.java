package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;
import java.util.Map;

/* compiled from: EngineKey.java */
/* loaded from: classes.dex */
public class j implements Key {
    private final Object a;
    private final int b;
    private final int c;
    private final Class<?> d;
    private final Class<?> e;
    private final Key f;
    private final Map<Class<?>, Transformation<?>> g;
    private final Options h;
    private int i;

    public j(Object obj, Key key, int i, int i2, Map<Class<?>, Transformation<?>> map, Class<?> cls, Class<?> cls2, Options options) {
        this.a = Preconditions.checkNotNull(obj);
        this.f = (Key) Preconditions.checkNotNull(key, "Signature must not be null");
        this.b = i;
        this.c = i2;
        this.g = (Map) Preconditions.checkNotNull(map);
        this.d = (Class) Preconditions.checkNotNull(cls, "Resource class must not be null");
        this.e = (Class) Preconditions.checkNotNull(cls2, "Transcode class must not be null");
        this.h = (Options) Preconditions.checkNotNull(options);
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof j)) {
            return false;
        }
        j jVar = (j) obj;
        return this.a.equals(jVar.a) && this.f.equals(jVar.f) && this.c == jVar.c && this.b == jVar.b && this.g.equals(jVar.g) && this.d.equals(jVar.d) && this.e.equals(jVar.e) && this.h.equals(jVar.h);
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        if (this.i == 0) {
            this.i = this.a.hashCode();
            this.i = (this.i * 31) + this.f.hashCode();
            this.i = (this.i * 31) + this.b;
            this.i = (this.i * 31) + this.c;
            this.i = (this.i * 31) + this.g.hashCode();
            this.i = (this.i * 31) + this.d.hashCode();
            this.i = (this.i * 31) + this.e.hashCode();
            this.i = (this.i * 31) + this.h.hashCode();
        }
        return this.i;
    }

    public String toString() {
        return "EngineKey{model=" + this.a + ", width=" + this.b + ", height=" + this.c + ", resourceClass=" + this.d + ", transcodeClass=" + this.e + ", signature=" + this.f + ", hashCode=" + this.i + ", transformations=" + this.g + ", options=" + this.h + '}';
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        throw new UnsupportedOperationException();
    }
}
