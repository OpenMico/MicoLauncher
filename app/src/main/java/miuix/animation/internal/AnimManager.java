package miuix.animation.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.internal.TransitionInfo;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;

/* loaded from: classes5.dex */
public class AnimManager implements TransitionInfo.IUpdateInfoCreator {
    IAnimTarget a;
    private List<UpdateInfo> g;
    final Set<Object> b = new HashSet();
    final Set<Object> c = new HashSet();
    final ConcurrentHashMap<FloatProperty, UpdateInfo> d = new ConcurrentHashMap<>();
    final ConcurrentHashMap<Object, TransitionInfo> e = new ConcurrentHashMap<>();
    final ConcurrentLinkedQueue<TransitionInfo> f = new ConcurrentLinkedQueue<>();
    private final Runnable h = new Runnable() { // from class: miuix.animation.internal.AnimManager.1
        @Override // java.lang.Runnable
        public void run() {
            AnimManager.this.update(true);
        }
    };

    public void update(boolean z) {
        this.a.handler.update(z);
    }

    public void runUpdate() {
        this.a.post(this.h);
    }

    public void setTarget(IAnimTarget iAnimTarget) {
        this.a = iAnimTarget;
    }

    public void a(List<TransitionInfo> list) {
        for (TransitionInfo transitionInfo : this.e.values()) {
            if (transitionInfo.j != null && !transitionInfo.j.isEmpty()) {
                list.add(transitionInfo);
            }
        }
    }

    public void clear() {
        this.b.clear();
        this.c.clear();
        this.d.clear();
        this.e.clear();
        this.f.clear();
    }

    public int getTotalAnimCount() {
        int i = 0;
        for (TransitionInfo transitionInfo : this.e.values()) {
            i += transitionInfo.a();
        }
        return i;
    }

    public boolean isAnimSetup() {
        return AnimRunner.sRunnerHandler.hasMessages(1);
    }

    public boolean isAnimRunning(FloatProperty... floatPropertyArr) {
        if (CommonUtils.isArrayEmpty(floatPropertyArr) && !(this.e.isEmpty() && this.f.isEmpty())) {
            return true;
        }
        for (TransitionInfo transitionInfo : this.e.values()) {
            if (a(transitionInfo, floatPropertyArr)) {
                return true;
            }
        }
        return false;
    }

    private boolean a(TransitionInfo transitionInfo, FloatProperty... floatPropertyArr) {
        for (FloatProperty floatProperty : floatPropertyArr) {
            if (transitionInfo.a(floatProperty)) {
                return true;
            }
        }
        return false;
    }

    public void startAnim(TransitionInfo transitionInfo) {
        if (c(transitionInfo)) {
            LogUtils.debug(this + ".startAnim, pendState", new Object[0]);
            return;
        }
        TransitionInfo.a.put(Integer.valueOf(transitionInfo.b), transitionInfo);
        AnimRunner.sRunnerHandler.obtainMessage(1, transitionInfo.b, 0).sendToTarget();
    }

    public void a(TransitionInfo transitionInfo) {
        this.e.put(transitionInfo.e, transitionInfo);
        transitionInfo.a(this);
        transitionInfo.a(true);
        b(transitionInfo);
        boolean contains = transitionInfo.c.animManager.b.contains(transitionInfo.e);
        if (!transitionInfo.f.listeners.isEmpty() && contains) {
            TransitionInfo.a.put(Integer.valueOf(transitionInfo.b), transitionInfo);
            transitionInfo.c.handler.obtainMessage(4, transitionInfo.b, 0, transitionInfo).sendToTarget();
        }
    }

    private void b(TransitionInfo transitionInfo) {
        for (TransitionInfo transitionInfo2 : this.e.values()) {
            if (transitionInfo2 != transitionInfo) {
                List<UpdateInfo> list = transitionInfo2.j;
                if (this.g == null) {
                    this.g = new ArrayList();
                }
                for (UpdateInfo updateInfo : list) {
                    if (!transitionInfo.h.contains(updateInfo.property)) {
                        this.g.add(updateInfo);
                    }
                }
                if (this.g.isEmpty()) {
                    a(transitionInfo2, 5, 4);
                } else if (this.g.size() != transitionInfo2.j.size()) {
                    transitionInfo2.j = this.g;
                    this.g = null;
                    transitionInfo2.a(false);
                } else {
                    this.g.clear();
                }
            }
        }
    }

    public void a(TransitionInfo transitionInfo, int i, int i2) {
        this.e.remove(transitionInfo.e);
        if (this.b.remove(transitionInfo.e)) {
            this.c.remove(transitionInfo.e);
            TransitionInfo.a.put(Integer.valueOf(transitionInfo.b), transitionInfo);
            this.a.handler.obtainMessage(i, transitionInfo.b, i2, transitionInfo).sendToTarget();
        }
        if (!isAnimRunning(new FloatProperty[0])) {
            this.d.clear();
        }
    }

    private boolean c(TransitionInfo transitionInfo) {
        if (!CommonUtils.hasFlags(transitionInfo.h.flags, 1L)) {
            return false;
        }
        this.f.add(transitionInfo);
        return true;
    }

    public void setTo(AnimState animState, AnimConfigLink animConfigLink) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("setTo, target = " + this.a, "to = " + animState);
        }
        if (animState.keySet().size() > 150) {
            AnimRunner.sRunnerHandler.a(this.a, animState);
        } else {
            a(animState, animConfigLink);
        }
    }

    private void a(AnimState animState, AnimConfigLink animConfigLink) {
        for (Object obj : animState.keySet()) {
            FloatProperty tempProperty = animState.getTempProperty(obj);
            double d = animState.get(this.a, tempProperty);
            UpdateInfo updateInfo = this.a.animManager.d.get(tempProperty);
            if (updateInfo != null) {
                updateInfo.animInfo.setToValue = d;
            }
            if (tempProperty instanceof IIntValueProperty) {
                this.a.setIntValue((IIntValueProperty) tempProperty, (int) d);
            } else {
                this.a.setValue(tempProperty, (float) d);
            }
            this.a.trackVelocity(tempProperty, d);
        }
        this.a.setToNotify(animState, animConfigLink);
    }

    public double getVelocity(FloatProperty floatProperty) {
        return getUpdateInfo(floatProperty).velocity;
    }

    public void setVelocity(FloatProperty floatProperty, float f) {
        getUpdateInfo(floatProperty).velocity = f;
    }

    @Override // miuix.animation.internal.TransitionInfo.IUpdateInfoCreator
    public UpdateInfo getUpdateInfo(FloatProperty floatProperty) {
        UpdateInfo updateInfo = this.d.get(floatProperty);
        if (updateInfo != null) {
            return updateInfo;
        }
        UpdateInfo updateInfo2 = new UpdateInfo(floatProperty);
        UpdateInfo putIfAbsent = this.d.putIfAbsent(floatProperty, updateInfo2);
        return putIfAbsent != null ? putIfAbsent : updateInfo2;
    }
}
