package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class GifFrameLoader {
    final RequestManager a;
    private final GifDecoder b;
    private final Handler c;
    private final List<FrameCallback> d;
    private final BitmapPool e;
    private boolean f;
    private boolean g;
    private boolean h;
    private RequestBuilder<Bitmap> i;
    private a j;
    private boolean k;
    private a l;
    private Bitmap m;
    private Transformation<Bitmap> n;
    private a o;
    @Nullable
    private c p;
    private int q;
    private int r;
    private int s;

    /* loaded from: classes.dex */
    public interface FrameCallback {
        void onFrameReady();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public interface c {
        void a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifFrameLoader(Glide glide, GifDecoder gifDecoder, int i, int i2, Transformation<Bitmap> transformation, Bitmap bitmap) {
        this(glide.getBitmapPool(), Glide.with(glide.getContext()), gifDecoder, null, a(Glide.with(glide.getContext()), i, i2), transformation, bitmap);
    }

    GifFrameLoader(BitmapPool bitmapPool, RequestManager requestManager, GifDecoder gifDecoder, Handler handler, RequestBuilder<Bitmap> requestBuilder, Transformation<Bitmap> transformation, Bitmap bitmap) {
        this.d = new ArrayList();
        this.a = requestManager;
        handler = handler == null ? new Handler(Looper.getMainLooper(), new b()) : handler;
        this.e = bitmapPool;
        this.c = handler;
        this.i = requestBuilder;
        this.b = gifDecoder;
        a(transformation, bitmap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Transformation<Bitmap> transformation, Bitmap bitmap) {
        this.n = (Transformation) Preconditions.checkNotNull(transformation);
        this.m = (Bitmap) Preconditions.checkNotNull(bitmap);
        this.i = this.i.apply((BaseRequestOptions<?>) new RequestOptions().transform(transformation));
        this.q = Util.getBitmapByteSize(bitmap);
        this.r = bitmap.getWidth();
        this.s = bitmap.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Transformation<Bitmap> a() {
        return this.n;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Bitmap b() {
        return this.m;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(FrameCallback frameCallback) {
        if (this.k) {
            throw new IllegalStateException("Cannot subscribe to a cleared frame loader");
        } else if (!this.d.contains(frameCallback)) {
            boolean isEmpty = this.d.isEmpty();
            this.d.add(frameCallback);
            if (isEmpty) {
                m();
            }
        } else {
            throw new IllegalStateException("Cannot subscribe twice in a row");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(FrameCallback frameCallback) {
        this.d.remove(frameCallback);
        if (this.d.isEmpty()) {
            n();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        return this.r;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d() {
        return this.s;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int e() {
        return this.b.getByteSize() + this.q;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int f() {
        a aVar = this.j;
        if (aVar != null) {
            return aVar.a;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ByteBuffer g() {
        return this.b.getData().asReadOnlyBuffer();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int h() {
        return this.b.getFrameCount();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int i() {
        return this.b.getTotalIterationCount();
    }

    private void m() {
        if (!this.f) {
            this.f = true;
            this.k = false;
            o();
        }
    }

    private void n() {
        this.f = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void j() {
        this.d.clear();
        p();
        n();
        a aVar = this.j;
        if (aVar != null) {
            this.a.clear(aVar);
            this.j = null;
        }
        a aVar2 = this.l;
        if (aVar2 != null) {
            this.a.clear(aVar2);
            this.l = null;
        }
        a aVar3 = this.o;
        if (aVar3 != null) {
            this.a.clear(aVar3);
            this.o = null;
        }
        this.b.clear();
        this.k = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Bitmap k() {
        a aVar = this.j;
        return aVar != null ? aVar.a() : this.m;
    }

    private void o() {
        if (this.f && !this.g) {
            if (this.h) {
                Preconditions.checkArgument(this.o == null, "Pending target must be null when starting from the first frame");
                this.b.resetFrameIndex();
                this.h = false;
            }
            a aVar = this.o;
            if (aVar != null) {
                this.o = null;
                a(aVar);
                return;
            }
            this.g = true;
            long uptimeMillis = SystemClock.uptimeMillis() + this.b.getNextDelay();
            this.b.advance();
            this.l = new a(this.c, this.b.getCurrentFrameIndex(), uptimeMillis);
            this.i.apply((BaseRequestOptions<?>) RequestOptions.signatureOf(q())).load((Object) this.b).into((RequestBuilder<Bitmap>) this.l);
        }
    }

    private void p() {
        Bitmap bitmap = this.m;
        if (bitmap != null) {
            this.e.put(bitmap);
            this.m = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void l() {
        Preconditions.checkArgument(!this.f, "Can't restart a running animation");
        this.h = true;
        a aVar = this.o;
        if (aVar != null) {
            this.a.clear(aVar);
            this.o = null;
        }
    }

    @VisibleForTesting
    void a(a aVar) {
        c cVar = this.p;
        if (cVar != null) {
            cVar.a();
        }
        this.g = false;
        if (this.k) {
            this.c.obtainMessage(2, aVar).sendToTarget();
        } else if (this.f) {
            if (aVar.a() != null) {
                p();
                a aVar2 = this.j;
                this.j = aVar;
                for (int size = this.d.size() - 1; size >= 0; size--) {
                    this.d.get(size).onFrameReady();
                }
                if (aVar2 != null) {
                    this.c.obtainMessage(2, aVar2).sendToTarget();
                }
            }
            o();
        } else if (this.h) {
            this.c.obtainMessage(2, aVar).sendToTarget();
        } else {
            this.o = aVar;
        }
    }

    /* loaded from: classes.dex */
    private class b implements Handler.Callback {
        b() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                GifFrameLoader.this.a((a) message.obj);
                return true;
            } else if (message.what != 2) {
                return false;
            } else {
                GifFrameLoader.this.a.clear((a) message.obj);
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class a extends CustomTarget<Bitmap> {
        final int a;
        private final Handler b;
        private final long c;
        private Bitmap d;

        a(Handler handler, int i, long j) {
            this.b = handler;
            this.a = i;
            this.c = j;
        }

        Bitmap a() {
            return this.d;
        }

        /* renamed from: a */
        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
            this.d = bitmap;
            this.b.sendMessageAtTime(this.b.obtainMessage(1, this), this.c);
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadCleared(@Nullable Drawable drawable) {
            this.d = null;
        }
    }

    private static RequestBuilder<Bitmap> a(RequestManager requestManager, int i, int i2) {
        return requestManager.asBitmap().apply((BaseRequestOptions<?>) RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).useAnimationPool(true).skipMemoryCache(true).override(i, i2));
    }

    private static Key q() {
        return new ObjectKey(Double.valueOf(Math.random()));
    }
}
