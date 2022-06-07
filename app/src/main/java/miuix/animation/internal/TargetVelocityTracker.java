package miuix.animation.internal;

import android.util.ArrayMap;
import java.lang.ref.WeakReference;
import java.util.Map;
import miuix.animation.IAnimTarget;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.VelocityMonitor;

/* loaded from: classes5.dex */
public class TargetVelocityTracker {
    private Map<FloatProperty, a> a = new ArrayMap();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a {
        VelocityMonitor a;
        b b;

        private a() {
            this.a = new VelocityMonitor();
            this.b = new b(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class b implements Runnable {
        WeakReference<IAnimTarget> a;
        FloatProperty b;
        a c;

        b(a aVar) {
            this.c = aVar;
        }

        void a(IAnimTarget iAnimTarget, FloatProperty floatProperty) {
            iAnimTarget.handler.removeCallbacks(this);
            WeakReference<IAnimTarget> weakReference = this.a;
            if (weakReference == null || weakReference.get() != iAnimTarget) {
                this.a = new WeakReference<>(iAnimTarget);
            }
            this.b = floatProperty;
            iAnimTarget.handler.postDelayed(this, 600L);
        }

        @Override // java.lang.Runnable
        public void run() {
            IAnimTarget iAnimTarget = this.a.get();
            if (iAnimTarget != null) {
                if (!iAnimTarget.isAnimRunning(this.b)) {
                    iAnimTarget.setVelocity(this.b, 0.0d);
                }
                this.c.a.clear();
            }
        }
    }

    public void trackVelocity(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        a a2 = a(floatProperty);
        a2.a.update(d);
        float velocity = a2.a.getVelocity(0);
        if (velocity != 0.0f) {
            a2.b.a(iAnimTarget, floatProperty);
            iAnimTarget.setVelocity(floatProperty, velocity);
        }
    }

    private a a(FloatProperty floatProperty) {
        a aVar = this.a.get(floatProperty);
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a();
        this.a.put(floatProperty, aVar2);
        return aVar2;
    }
}
