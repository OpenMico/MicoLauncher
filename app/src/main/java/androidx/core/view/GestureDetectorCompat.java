package androidx.core.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/* loaded from: classes.dex */
public final class GestureDetectorCompat {
    private final a a;

    /* loaded from: classes.dex */
    interface a {
        void a(GestureDetector.OnDoubleTapListener onDoubleTapListener);

        void a(boolean z);

        boolean a();

        boolean a(MotionEvent motionEvent);
    }

    /* loaded from: classes.dex */
    static class b implements a {
        private static final int j = ViewConfiguration.getTapTimeout();
        private static final int k = ViewConfiguration.getDoubleTapTimeout();
        final GestureDetector.OnGestureListener a;
        GestureDetector.OnDoubleTapListener b;
        boolean c;
        boolean d;
        MotionEvent e;
        private int f;
        private int g;
        private int h;
        private int i;
        private final Handler l;
        private boolean m;
        private boolean n;
        private boolean o;
        private MotionEvent p;
        private boolean q;
        private float r;
        private float s;
        private float t;
        private float u;
        private boolean v;
        private VelocityTracker w;

        /* loaded from: classes.dex */
        private class a extends Handler {
            a() {
            }

            a(Handler handler) {
                super(handler.getLooper());
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        b.this.a.onShowPress(b.this.e);
                        return;
                    case 2:
                        b.this.b();
                        return;
                    case 3:
                        if (b.this.b == null) {
                            return;
                        }
                        if (!b.this.c) {
                            b.this.b.onSingleTapConfirmed(b.this.e);
                            return;
                        } else {
                            b.this.d = true;
                            return;
                        }
                    default:
                        throw new RuntimeException("Unknown message " + message);
                }
            }
        }

        b(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            if (handler != null) {
                this.l = new a(handler);
            } else {
                this.l = new a();
            }
            this.a = onGestureListener;
            if (onGestureListener instanceof GestureDetector.OnDoubleTapListener) {
                a((GestureDetector.OnDoubleTapListener) onGestureListener);
            }
            a(context);
        }

        private void a(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.a != null) {
                this.v = true;
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
                int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
                this.h = viewConfiguration.getScaledMinimumFlingVelocity();
                this.i = viewConfiguration.getScaledMaximumFlingVelocity();
                this.f = scaledTouchSlop * scaledTouchSlop;
                this.g = scaledDoubleTapSlop * scaledDoubleTapSlop;
            } else {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            }
        }

        @Override // androidx.core.view.GestureDetectorCompat.a
        public void a(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.b = onDoubleTapListener;
        }

        @Override // androidx.core.view.GestureDetectorCompat.a
        public void a(boolean z) {
            this.v = z;
        }

        @Override // androidx.core.view.GestureDetectorCompat.a
        public boolean a() {
            return this.v;
        }

        /* JADX WARN: Removed duplicated region for block: B:95:0x01fb  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x0212  */
        @Override // androidx.core.view.GestureDetectorCompat.a
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean a(android.view.MotionEvent r12) {
            /*
                Method dump skipped, instructions count: 598
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.GestureDetectorCompat.b.a(android.view.MotionEvent):boolean");
        }

        private void c() {
            this.l.removeMessages(1);
            this.l.removeMessages(2);
            this.l.removeMessages(3);
            this.w.recycle();
            this.w = null;
            this.q = false;
            this.c = false;
            this.n = false;
            this.o = false;
            this.d = false;
            if (this.m) {
                this.m = false;
            }
        }

        private void d() {
            this.l.removeMessages(1);
            this.l.removeMessages(2);
            this.l.removeMessages(3);
            this.q = false;
            this.n = false;
            this.o = false;
            this.d = false;
            if (this.m) {
                this.m = false;
            }
        }

        private boolean a(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            if (!this.o || motionEvent3.getEventTime() - motionEvent2.getEventTime() > k) {
                return false;
            }
            int x = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
            int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
            return (x * x) + (y * y) < this.g;
        }

        void b() {
            this.l.removeMessages(3);
            this.d = false;
            this.m = true;
            this.a.onLongPress(this.e);
        }
    }

    /* loaded from: classes.dex */
    static class c implements a {
        private final GestureDetector a;

        c(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.a = new GestureDetector(context, onGestureListener, handler);
        }

        @Override // androidx.core.view.GestureDetectorCompat.a
        public boolean a() {
            return this.a.isLongpressEnabled();
        }

        @Override // androidx.core.view.GestureDetectorCompat.a
        public boolean a(MotionEvent motionEvent) {
            return this.a.onTouchEvent(motionEvent);
        }

        @Override // androidx.core.view.GestureDetectorCompat.a
        public void a(boolean z) {
            this.a.setIsLongpressEnabled(z);
        }

        @Override // androidx.core.view.GestureDetectorCompat.a
        public void a(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.a.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        if (Build.VERSION.SDK_INT > 17) {
            this.a = new c(context, onGestureListener, handler);
        } else {
            this.a = new b(context, onGestureListener, handler);
        }
    }

    public boolean isLongpressEnabled() {
        return this.a.a();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.a.a(motionEvent);
    }

    public void setIsLongpressEnabled(boolean z) {
        this.a.a(z);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.a.a(onDoubleTapListener);
    }
}
