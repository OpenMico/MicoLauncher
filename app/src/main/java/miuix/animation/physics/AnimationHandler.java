package miuix.animation.physics;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.view.Choreographer;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class AnimationHandler {
    public static final ThreadLocal<AnimationHandler> sAnimatorHandler = new ThreadLocal<>();
    private b d;
    private final ArrayMap<AnimationFrameCallback, Long> a = new ArrayMap<>();
    private final ArrayList<AnimationFrameCallback> b = new ArrayList<>();
    private final a c = new a();
    private long e = 0;
    private boolean f = false;

    /* loaded from: classes5.dex */
    public interface AnimationFrameCallback {
        boolean doAnimationFrame(long j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public class a {
        a() {
        }

        void a() {
            AnimationHandler.this.e = SystemClock.uptimeMillis();
            AnimationHandler animationHandler = AnimationHandler.this;
            animationHandler.a(animationHandler.e);
            if (AnimationHandler.this.b.size() > 0) {
                AnimationHandler.this.a().a();
            }
        }
    }

    public static AnimationHandler getInstance() {
        if (sAnimatorHandler.get() == null) {
            sAnimatorHandler.set(new AnimationHandler());
        }
        return sAnimatorHandler.get();
    }

    public static long getFrameTime() {
        if (sAnimatorHandler.get() == null) {
            return 0L;
        }
        return sAnimatorHandler.get().e;
    }

    public void setProvider(b bVar) {
        this.d = bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public b a() {
        if (this.d == null) {
            if (Build.VERSION.SDK_INT >= 16) {
                this.d = new d(this.c);
            } else {
                this.d = new c(this.c);
            }
        }
        return this.d;
    }

    public void addAnimationFrameCallback(AnimationFrameCallback animationFrameCallback, long j) {
        if (this.b.size() == 0) {
            a().a();
        }
        if (!this.b.contains(animationFrameCallback)) {
            this.b.add(animationFrameCallback);
        }
        if (j > 0) {
            this.a.put(animationFrameCallback, Long.valueOf(SystemClock.uptimeMillis() + j));
        }
    }

    public void removeCallback(AnimationFrameCallback animationFrameCallback) {
        this.a.remove(animationFrameCallback);
        int indexOf = this.b.indexOf(animationFrameCallback);
        if (indexOf >= 0) {
            this.b.set(indexOf, null);
            this.f = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j) {
        long uptimeMillis = SystemClock.uptimeMillis();
        for (int i = 0; i < this.b.size(); i++) {
            AnimationFrameCallback animationFrameCallback = this.b.get(i);
            if (animationFrameCallback != null && a(animationFrameCallback, uptimeMillis)) {
                animationFrameCallback.doAnimationFrame(j);
            }
        }
        b();
    }

    private boolean a(AnimationFrameCallback animationFrameCallback, long j) {
        Long l = this.a.get(animationFrameCallback);
        if (l == null) {
            return true;
        }
        if (l.longValue() >= j) {
            return false;
        }
        this.a.remove(animationFrameCallback);
        return true;
    }

    private void b() {
        if (this.f) {
            for (int size = this.b.size() - 1; size >= 0; size--) {
                if (this.b.get(size) == null) {
                    this.b.remove(size);
                }
            }
            this.f = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class d extends b {
        private final Choreographer b = Choreographer.getInstance();
        private final Choreographer.FrameCallback c = new Choreographer.FrameCallback() { // from class: miuix.animation.physics.AnimationHandler.d.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                d.this.a.a();
            }
        };

        d(a aVar) {
            super(aVar);
        }

        @Override // miuix.animation.physics.AnimationHandler.b
        void a() {
            this.b.postFrameCallback(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class c extends b {
        private long d = -1;
        private final Runnable b = new Runnable() { // from class: miuix.animation.physics.AnimationHandler.c.1
            @Override // java.lang.Runnable
            public void run() {
                c.this.d = SystemClock.uptimeMillis();
                c.this.a.a();
            }
        };
        private final Handler c = new Handler(Looper.myLooper());

        c(a aVar) {
            super(aVar);
        }

        @Override // miuix.animation.physics.AnimationHandler.b
        void a() {
            this.c.postDelayed(this.b, Math.max(10 - (SystemClock.uptimeMillis() - this.d), 0L));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static abstract class b {
        final a a;

        abstract void a();

        b(a aVar) {
            this.a = aVar;
        }
    }
}
