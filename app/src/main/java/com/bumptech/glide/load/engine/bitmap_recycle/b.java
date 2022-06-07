package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.Util;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AttributeStrategy.java */
/* loaded from: classes.dex */
public class b implements e {
    private final C0039b a = new C0039b();
    private final d<a, Bitmap> b = new d<>();

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public void put(Bitmap bitmap) {
        this.b.a(this.a.a(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig()), bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public Bitmap get(int i, int i2, Bitmap.Config config) {
        return this.b.a((d<a, Bitmap>) this.a.a(i, i2, config));
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public Bitmap removeLast() {
        return this.b.a();
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public String logBitmap(Bitmap bitmap) {
        return a(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public String logBitmap(int i, int i2, Bitmap.Config config) {
        return a(i, i2, config);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public int getSize(Bitmap bitmap) {
        return Util.getBitmapByteSize(bitmap);
    }

    public String toString() {
        return "AttributeStrategy:\n  " + this.b;
    }

    private static String a(Bitmap bitmap) {
        return a(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
    }

    static String a(int i, int i2, Bitmap.Config config) {
        return "[" + i + "x" + i2 + "], " + config;
    }

    /* compiled from: AttributeStrategy.java */
    @VisibleForTesting
    /* renamed from: com.bumptech.glide.load.engine.bitmap_recycle.b$b  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    static class C0039b extends c<a> {
        C0039b() {
        }

        a a(int i, int i2, Bitmap.Config config) {
            a c = c();
            c.a(i, i2, config);
            return c;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public a b() {
            return new a(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AttributeStrategy.java */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class a implements f {
        private final C0039b a;
        private int b;
        private int c;
        private Bitmap.Config d;

        public a(C0039b bVar) {
            this.a = bVar;
        }

        public void a(int i, int i2, Bitmap.Config config) {
            this.b = i;
            this.c = i2;
            this.d = config;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.b == aVar.b && this.c == aVar.c && this.d == aVar.d;
        }

        public int hashCode() {
            int i = ((this.b * 31) + this.c) * 31;
            Bitmap.Config config = this.d;
            return i + (config != null ? config.hashCode() : 0);
        }

        public String toString() {
            return b.a(this.b, this.c, this.d);
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.f
        public void a() {
            this.a.a(this);
        }
    }
}
