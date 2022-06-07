package androidx.dynamicanimation.animation;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Choreographer;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AnimationHandler.java */
/* loaded from: classes.dex */
public class a {
    public static final ThreadLocal<a> a = new ThreadLocal<>();
    private c f;
    private final SimpleArrayMap<b, Long> d = new SimpleArrayMap<>();
    final ArrayList<b> b = new ArrayList<>();
    private final C0015a e = new C0015a();
    long c = 0;
    private boolean g = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AnimationHandler.java */
    /* loaded from: classes.dex */
    public interface b {
        boolean doAnimationFrame(long j);
    }

    a() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AnimationHandler.java */
    /* renamed from: androidx.dynamicanimation.animation.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0015a {
        C0015a() {
        }

        void a() {
            a.this.c = SystemClock.uptimeMillis();
            a aVar = a.this;
            aVar.a(aVar.c);
            if (a.this.b.size() > 0) {
                a.this.b().a();
            }
        }
    }

    public static a a() {
        if (a.get() == null) {
            a.set(new a());
        }
        return a.get();
    }

    c b() {
        if (this.f == null) {
            if (Build.VERSION.SDK_INT >= 16) {
                this.f = new e(this.e);
            } else {
                this.f = new d(this.e);
            }
        }
        return this.f;
    }

    public void a(b bVar, long j) {
        if (this.b.size() == 0) {
            b().a();
        }
        if (!this.b.contains(bVar)) {
            this.b.add(bVar);
        }
        if (j > 0) {
            this.d.put(bVar, Long.valueOf(SystemClock.uptimeMillis() + j));
        }
    }

    public void a(b bVar) {
        this.d.remove(bVar);
        int indexOf = this.b.indexOf(bVar);
        if (indexOf >= 0) {
            this.b.set(indexOf, null);
            this.g = true;
        }
    }

    void a(long j) {
        long uptimeMillis = SystemClock.uptimeMillis();
        for (int i = 0; i < this.b.size(); i++) {
            b bVar = this.b.get(i);
            if (bVar != null && b(bVar, uptimeMillis)) {
                bVar.doAnimationFrame(j);
            }
        }
        c();
    }

    private boolean b(b bVar, long j) {
        Long l = this.d.get(bVar);
        if (l == null) {
            return true;
        }
        if (l.longValue() >= j) {
            return false;
        }
        this.d.remove(bVar);
        return true;
    }

    private void c() {
        if (this.g) {
            for (int size = this.b.size() - 1; size >= 0; size--) {
                if (this.b.get(size) == null) {
                    this.b.remove(size);
                }
            }
            this.g = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AnimationHandler.java */
    @RequiresApi(16)
    /* loaded from: classes.dex */
    public static class e extends c {
        private final Choreographer b = Choreographer.getInstance();
        private final Choreographer.FrameCallback c = new Choreographer.FrameCallback() { // from class: androidx.dynamicanimation.animation.a.e.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                e.this.a.a();
            }
        };

        e(C0015a aVar) {
            super(aVar);
        }

        @Override // androidx.dynamicanimation.animation.a.c
        void a() {
            this.b.postFrameCallback(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AnimationHandler.java */
    /* loaded from: classes.dex */
    public static class d extends c {
        long b = -1;
        private final Runnable c = new Runnable() { // from class: androidx.dynamicanimation.animation.a.d.1
            @Override // java.lang.Runnable
            public void run() {
                d.this.b = SystemClock.uptimeMillis();
                d.this.a.a();
            }
        };
        private final Handler d = new Handler(Looper.myLooper());

        d(C0015a aVar) {
            super(aVar);
        }

        @Override // androidx.dynamicanimation.animation.a.c
        void a() {
            this.d.postDelayed(this.c, Math.max(10 - (SystemClock.uptimeMillis() - this.b), 0L));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AnimationHandler.java */
    /* loaded from: classes.dex */
    public static abstract class c {
        final C0015a a;

        abstract void a();

        c(C0015a aVar) {
            this.a = aVar;
        }
    }
}
