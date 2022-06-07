package miuix.animation.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.IAnimTarget;
import miuix.animation.ViewTarget;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.utils.LinkNode;
import miuix.animation.utils.LogUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RunnerHandler.java */
/* loaded from: classes5.dex */
public class d extends Handler {
    private boolean g;
    private boolean k;
    private boolean l;
    private final Set<IAnimTarget> a = new HashSet();
    private final Map<IAnimTarget, a> b = new ConcurrentHashMap();
    private final Map<IAnimTarget, TransitionInfo> c = new HashMap();
    private final List<AnimTask> d = new ArrayList();
    private final List<IAnimTarget> e = new ArrayList();
    private final List<TransitionInfo> f = new ArrayList();
    private long h = 0;
    private long i = 0;
    private int j = 0;
    private final int[] m = new int[2];

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RunnerHandler.java */
    /* loaded from: classes5.dex */
    public static class a {
        IAnimTarget a;
        AnimState b;

        private a() {
        }
    }

    public d(@NonNull Looper looper) {
        super(looper);
    }

    public void a(a aVar) {
        if (aVar.a.isAnimRunning(new FloatProperty[0])) {
            aVar.d = System.nanoTime();
            this.b.put(aVar.a, aVar);
        }
    }

    public void a(IAnimTarget iAnimTarget, AnimState animState) {
        a aVar = new a();
        aVar.a = iAnimTarget;
        if (animState.isTemporary) {
            aVar.b = new AnimState();
            aVar.b.set(animState);
        } else {
            aVar.b = animState;
        }
        obtainMessage(4, aVar).sendToTarget();
    }

    @Override // android.os.Handler
    public void handleMessage(@NonNull Message message) {
        switch (message.what) {
            case 1:
                TransitionInfo remove = TransitionInfo.a.remove(Integer.valueOf(message.arg1));
                if (remove != null) {
                    a(remove.c, (IAnimTarget) remove, (Map<IAnimTarget, IAnimTarget>) this.c);
                    if (!this.g) {
                        e();
                        break;
                    }
                }
                break;
            case 2:
                b();
                break;
            case 3:
                if (this.l) {
                    long currentTimeMillis = System.currentTimeMillis();
                    long averageDelta = AnimRunner.getInst().getAverageDelta();
                    boolean booleanValue = ((Boolean) message.obj).booleanValue();
                    if (this.k) {
                        if (!this.g) {
                            a(currentTimeMillis, currentTimeMillis - this.h, booleanValue);
                            break;
                        }
                    } else {
                        this.k = true;
                        this.i = 0L;
                        this.j = 0;
                        a(currentTimeMillis, averageDelta, booleanValue);
                        break;
                    }
                }
                break;
            case 4:
                a((a) message.obj);
                break;
            case 5:
                this.a.clear();
                a();
                break;
        }
        message.obj = null;
    }

    private void a(a aVar) {
        boolean z = aVar.a instanceof ViewTarget;
        for (Object obj : aVar.b.keySet()) {
            FloatProperty property = aVar.b.getProperty(obj);
            UpdateInfo updateInfo = aVar.a.animManager.d.get(property);
            if (updateInfo != null) {
                double d = aVar.b.get(aVar.a, property);
                if (updateInfo != null) {
                    updateInfo.animInfo.setToValue = d;
                    if (!z) {
                        updateInfo.setTargetValue(aVar.a);
                    }
                } else if (property instanceof IIntValueProperty) {
                    aVar.a.setIntValue((IIntValueProperty) property, (int) d);
                } else {
                    aVar.a.setValue(property, (float) d);
                }
            }
        }
        if (!aVar.a.isAnimRunning(new FloatProperty[0])) {
            aVar.a.animManager.d.clear();
        }
    }

    private <T extends LinkNode> void a(IAnimTarget iAnimTarget, T t, Map<IAnimTarget, T> map) {
        T t2 = map.get(iAnimTarget);
        if (t2 == null) {
            map.put(iAnimTarget, t);
        } else {
            t2.addToTail(t);
        }
    }

    private void a() {
        if (this.k) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("RunnerHandler.stopAnimRunner", "total time = " + this.i, "frame count = " + this.j);
            }
            this.k = false;
            this.l = false;
            this.i = 0L;
            this.j = 0;
            AnimRunner.getInst().b();
        }
    }

    private void b() {
        boolean z = false;
        this.g = false;
        for (IAnimTarget iAnimTarget : this.a) {
            if (a(iAnimTarget, this.f) || a(iAnimTarget)) {
                z = true;
            } else {
                this.e.add(iAnimTarget);
            }
            this.f.clear();
        }
        this.a.removeAll(this.e);
        this.e.clear();
        if (!this.c.isEmpty()) {
            e();
            z = true;
        }
        if (!z) {
            a();
        }
    }

    private boolean a(IAnimTarget iAnimTarget) {
        TransitionInfo poll = iAnimTarget.animManager.f.poll();
        if (poll == null) {
            return false;
        }
        a(poll.c, (IAnimTarget) poll, (Map<IAnimTarget, IAnimTarget>) this.c);
        return true;
    }

    private boolean a(TransitionInfo transitionInfo) {
        for (TransitionInfo transitionInfo2 = this.c.get(transitionInfo.c); transitionInfo2 != null; transitionInfo2 = (TransitionInfo) transitionInfo2.next) {
            if (transitionInfo2 == transitionInfo) {
                return true;
            }
        }
        return false;
    }

    private boolean a(IAnimTarget iAnimTarget, List<TransitionInfo> list) {
        a aVar;
        int i;
        iAnimTarget.animManager.a(list);
        a aVar2 = this.b.get(iAnimTarget);
        int i2 = 0;
        int i3 = 0;
        for (TransitionInfo transitionInfo : list) {
            if (a(transitionInfo)) {
                i3++;
            } else {
                if (aVar2 == null || transitionInfo.i <= aVar2.d) {
                    i2 = i2;
                    aVar = aVar2;
                } else {
                    i2++;
                    aVar = null;
                }
                c b = transitionInfo.b();
                if (b.a()) {
                    a(transitionInfo, aVar, b);
                }
                if (LogUtils.isLogEnabled()) {
                    String str = "---- updateAnim, target = " + iAnimTarget;
                    Object[] objArr = new Object[6];
                    objArr[0] = "key = " + transitionInfo.e;
                    objArr[1] = "useOp = " + aVar;
                    objArr[2] = "info.startTime = " + transitionInfo.i;
                    StringBuilder sb = new StringBuilder();
                    sb.append("opInfo.time = ");
                    sb.append(aVar2 != null ? Long.valueOf(aVar2.d) : null);
                    i = 3;
                    objArr[3] = sb.toString();
                    i = 4;
                    objArr[4] = "stats.isRunning = " + b.b();
                    objArr[5] = "stats = " + b;
                    LogUtils.debug(str, objArr);
                } else {
                    i = 3;
                    i = 4;
                }
                if (!b.b()) {
                    AnimManager animManager = iAnimTarget.animManager;
                    if (b.e > b.f) {
                    }
                    animManager.a(transitionInfo, 2, i);
                } else {
                    i3++;
                }
            }
        }
        if (aVar2 != null && (i2 == list.size() || aVar2.a())) {
            this.b.remove(iAnimTarget);
        }
        list.clear();
        return i3 > 0;
    }

    private static void a(TransitionInfo transitionInfo, a aVar, c cVar) {
        boolean contains = transitionInfo.c.animManager.b.contains(transitionInfo.e);
        for (AnimTask animTask : transitionInfo.k) {
            List<UpdateInfo> list = transitionInfo.j;
            int i = animTask.startPos;
            int animCount = animTask.getAnimCount() + i;
            while (i < animCount) {
                UpdateInfo updateInfo = list.get(i);
                if (updateInfo != null && !a(animTask, cVar, updateInfo) && contains && aVar != null) {
                    a(animTask, cVar, updateInfo, aVar);
                }
                i++;
            }
        }
        if (!contains) {
            transitionInfo.c.animManager.b.add(transitionInfo.e);
        }
        if (cVar.b() && cVar.d > 0 && transitionInfo.c.animManager.c.add(transitionInfo.e)) {
            TransitionInfo.a.put(Integer.valueOf(transitionInfo.b), transitionInfo);
            transitionInfo.c.handler.obtainMessage(0, transitionInfo.b, 0).sendToTarget();
        }
    }

    private static boolean a(AnimTask animTask, c cVar, UpdateInfo updateInfo) {
        if (!AnimValueUtils.handleSetToValue(updateInfo)) {
            return false;
        }
        if (AnimTask.isRunning(updateInfo.animInfo.op)) {
            animTask.animStats.e++;
            cVar.e++;
            updateInfo.setOp((byte) 4);
            TransitionInfo.a(animTask, cVar, updateInfo, updateInfo.animInfo.op);
        }
        return true;
    }

    private static void a(AnimTask animTask, c cVar, UpdateInfo updateInfo, a aVar) {
        byte b = updateInfo.animInfo.op;
        if (AnimTask.isRunning(b) && aVar.b != 0) {
            if ((aVar.c == null || aVar.c.contains(updateInfo.property)) && AnimTask.isRunning(updateInfo.animInfo.op)) {
                aVar.e++;
                if (aVar.b == 3) {
                    if (updateInfo.animInfo.targetValue != Double.MAX_VALUE) {
                        updateInfo.animInfo.value = updateInfo.animInfo.targetValue;
                    }
                    animTask.animStats.f++;
                    cVar.f++;
                } else if (aVar.b == 4) {
                    animTask.animStats.e++;
                    cVar.e++;
                }
                updateInfo.setOp(aVar.b);
                TransitionInfo.a(animTask, cVar, updateInfo, b);
            }
        }
    }

    private void a(long j, long j2, boolean z) {
        if (this.a.isEmpty()) {
            a();
            return;
        }
        this.h = j;
        long averageDelta = AnimRunner.getInst().getAverageDelta();
        if (this.j != 1 || j2 <= 2 * averageDelta) {
            averageDelta = j2;
        }
        this.i += averageDelta;
        this.j++;
        ThreadPoolUtil.getSplitCount(d(), this.m);
        int[] iArr = this.m;
        int i = iArr[0];
        int i2 = iArr[1];
        for (IAnimTarget iAnimTarget : this.a) {
            iAnimTarget.animManager.a(this.f);
        }
        a(this.f, i2, i);
        this.g = !this.d.isEmpty();
        AnimTask.sTaskCount.set(this.d.size());
        for (AnimTask animTask : this.d) {
            animTask.start(this.i, averageDelta, z);
        }
        this.f.clear();
        this.d.clear();
    }

    private void a(List<TransitionInfo> list, int i, int i2) {
        for (TransitionInfo transitionInfo : list) {
            for (AnimTask animTask : transitionInfo.k) {
                AnimTask c = c();
                if (c == null || (this.d.size() < i2 && c.getTotalAnimCount() + animTask.getAnimCount() > i)) {
                    this.d.add(animTask);
                } else {
                    c.addToTail(animTask);
                }
            }
        }
    }

    private AnimTask c() {
        AnimTask animTask = null;
        int i = Integer.MAX_VALUE;
        for (AnimTask animTask2 : this.d) {
            int totalAnimCount = animTask2.getTotalAnimCount();
            if (totalAnimCount < i) {
                animTask = animTask2;
                i = totalAnimCount;
            }
        }
        return animTask;
    }

    private int d() {
        int i = 0;
        for (IAnimTarget iAnimTarget : this.a) {
            i += iAnimTarget.animManager.getTotalAnimCount();
        }
        return i;
    }

    private void e() {
        for (TransitionInfo transitionInfo : this.c.values()) {
            this.a.add(transitionInfo.c);
            do {
                transitionInfo.c.animManager.a(transitionInfo);
                transitionInfo = transitionInfo.remove();
            } while (transitionInfo != null);
        }
        this.c.clear();
        if (!this.l) {
            this.l = true;
            AnimRunner.getInst().a();
        }
    }
}
