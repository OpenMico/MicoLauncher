package miuix.animation.internal;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.physics.AnimationHandler;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;

/* loaded from: classes5.dex */
public class AnimRunner implements AnimationHandler.AnimationFrameCallback {
    public static final long MAX_DELTA = 16;
    private volatile long c;
    private long d;
    private long[] e;
    private int f;
    private volatile boolean g;
    private float h;
    private static final HandlerThread a = new HandlerThread("AnimRunnerThread", 5);
    public static final d sRunnerHandler = new d(a.getLooper());
    private static final Handler b = new Handler(Looper.getMainLooper()) { // from class: miuix.animation.internal.AnimRunner.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    AnimRunner.e();
                    return;
                case 1:
                    AnimRunner.f();
                    return;
                default:
                    return;
            }
        }
    };

    /* loaded from: classes5.dex */
    public static class a {
        static final AnimRunner a = new AnimRunner();
    }

    public static AnimRunner getInst() {
        return a.a;
    }

    static {
        a.start();
    }

    private static void a(Collection<IAnimTarget> collection, boolean z) {
        if (collection.size() == 0) {
            sRunnerHandler.sendEmptyMessage(5);
        }
        for (IAnimTarget iAnimTarget : collection) {
            boolean isAnimRunning = iAnimTarget.animManager.isAnimRunning(new FloatProperty[0]);
            boolean isAnimSetup = iAnimTarget.animManager.isAnimSetup();
            boolean isValidFlag = iAnimTarget.isValidFlag();
            if (isAnimRunning) {
                if (z) {
                    iAnimTarget.animManager.runUpdate();
                } else {
                    iAnimTarget.animManager.update(false);
                }
            } else if (!isAnimSetup && !isAnimRunning && iAnimTarget.hasFlags(1L) && isValidFlag) {
                Folme.clean(iAnimTarget);
            }
        }
    }

    public static void e() {
        AnimRunner inst = getInst();
        if (!inst.g) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("AnimRunner.start", new Object[0]);
            }
            inst.h = Folme.getTimeRatio();
            inst.g = true;
            AnimationHandler.getInstance().addAnimationFrameCallback(inst, 0L);
        }
    }

    public static void f() {
        AnimRunner inst = getInst();
        if (inst.g) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("AnimRunner.endAnimation", new Object[0]);
            }
            inst.g = false;
            AnimationHandler.getInstance().removeCallback(inst);
        }
    }

    private AnimRunner() {
        this.c = 16L;
        this.e = new long[]{0, 0, 0, 0, 0};
        this.f = 0;
    }

    @Override // miuix.animation.physics.AnimationHandler.AnimationFrameCallback
    public boolean doAnimationFrame(long j) {
        b(j);
        if (this.g) {
            Collection<IAnimTarget> targets = Folme.getTargets();
            boolean z = false;
            int i = 0;
            for (IAnimTarget iAnimTarget : targets) {
                if (iAnimTarget.animManager.isAnimRunning(new FloatProperty[0])) {
                    i += iAnimTarget.animManager.getTotalAnimCount();
                }
            }
            if (i > 500) {
                z = true;
            }
            if ((!z && targets.size() > 0) || targets.size() == 0) {
                a(targets, z);
            }
            Message obtainMessage = sRunnerHandler.obtainMessage();
            obtainMessage.what = 3;
            obtainMessage.obj = Boolean.valueOf(z);
            sRunnerHandler.sendMessage(obtainMessage);
            if (z && targets.size() > 0) {
                a(targets, z);
            }
        }
        return this.g;
    }

    public void cancel(IAnimTarget iAnimTarget, String... strArr) {
        sRunnerHandler.a(new a(iAnimTarget, (byte) 4, strArr, null));
    }

    public void cancel(IAnimTarget iAnimTarget, FloatProperty... floatPropertyArr) {
        sRunnerHandler.a(new a(iAnimTarget, (byte) 4, null, floatPropertyArr));
    }

    public void end(IAnimTarget iAnimTarget, String... strArr) {
        if (CommonUtils.isArrayEmpty(strArr)) {
            iAnimTarget.handler.sendEmptyMessage(3);
        }
        sRunnerHandler.a(new a(iAnimTarget, (byte) 3, strArr, null));
    }

    public void end(IAnimTarget iAnimTarget, FloatProperty... floatPropertyArr) {
        if (CommonUtils.isArrayEmpty(floatPropertyArr)) {
            iAnimTarget.handler.sendEmptyMessage(3);
        }
        sRunnerHandler.a(new a(iAnimTarget, (byte) 3, null, floatPropertyArr));
    }

    public void run(IAnimTarget iAnimTarget, AnimState animState, AnimState animState2, AnimConfigLink animConfigLink) {
        run(new TransitionInfo(iAnimTarget, animState, animState2, animConfigLink));
    }

    public void run(final TransitionInfo transitionInfo) {
        transitionInfo.c.executeOnInitialized(new Runnable() { // from class: miuix.animation.internal.AnimRunner.2
            @Override // java.lang.Runnable
            public void run() {
                transitionInfo.c.animManager.startAnim(transitionInfo);
            }
        });
    }

    public void a() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            e();
        } else {
            b.sendEmptyMessage(0);
        }
    }

    public void b() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            f();
        } else {
            b.sendEmptyMessage(1);
        }
    }

    public long getAverageDelta() {
        return this.c;
    }

    private long a(long j) {
        long a2 = a(this.e);
        if (a2 > 0) {
            j = a2;
        }
        if (j == 0 || j > 16) {
            j = 16;
        }
        return (long) Math.ceil(((float) j) / this.h);
    }

    private void b(long j) {
        long j2 = this.d;
        long j3 = 0;
        if (j2 == 0) {
            this.d = j;
        } else {
            j3 = j - j2;
            this.d = j;
        }
        int i = this.f;
        this.e[i % 5] = j3;
        this.f = i + 1;
        this.c = a(j3);
    }

    private long a(long[] jArr) {
        int i = 0;
        long j = 0;
        for (long j2 : jArr) {
            j += j2;
            if (j2 > 0) {
                i++;
            }
        }
        if (i > 0) {
            return j / i;
        }
        return 0L;
    }
}
