package miuix.animation.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ColorProperty;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.LinkNode;
import miuix.animation.utils.LogUtils;

/* loaded from: classes5.dex */
public class TransitionInfo extends LinkNode<TransitionInfo> {
    public static final Map<Integer, TransitionInfo> a = new ConcurrentHashMap();
    private static final AtomicInteger l = new AtomicInteger();
    public final IAnimTarget c;
    public final Object d;
    public volatile Object e;
    public volatile AnimState g;
    public volatile AnimState h;
    public volatile long i;
    public volatile List<UpdateInfo> j;
    public final int b = l.incrementAndGet();
    public volatile AnimConfig f = new AnimConfig();
    public List<AnimTask> k = new ArrayList();
    private final c m = new c();

    /* loaded from: classes5.dex */
    public interface IUpdateInfoCreator {
        UpdateInfo getUpdateInfo(FloatProperty floatProperty);
    }

    public static void a(AnimTask animTask, c cVar, UpdateInfo updateInfo, byte b) {
        if (animTask != null && b == 1 && updateInfo.animInfo.delay > 0 && animTask.animStats.a > 0) {
            animTask.animStats.a--;
            cVar.a--;
        }
    }

    public TransitionInfo(IAnimTarget iAnimTarget, AnimState animState, AnimState animState2, AnimConfigLink animConfigLink) {
        this.c = iAnimTarget;
        this.g = a(animState);
        this.h = a(animState2);
        this.d = this.h.getTag();
        if (animState2.isTemporary) {
            this.e = this.d + String.valueOf(this.b);
        } else {
            this.e = this.d;
        }
        this.j = null;
        c();
        this.f.copy(animState2.getConfig());
        if (animConfigLink != null) {
            animConfigLink.addTo(this.f);
        }
        iAnimTarget.getNotifier().addListeners(this.e, this.f);
    }

    public void a(boolean z) {
        int size = this.j.size();
        int max = Math.max(1, size / 4000);
        int ceil = (int) Math.ceil(size / max);
        if (this.k.size() > max) {
            List<AnimTask> list = this.k;
            list.subList(max, list.size()).clear();
        } else {
            for (int size2 = this.k.size(); size2 < max; size2++) {
                this.k.add(new AnimTask());
            }
        }
        int i = 0;
        for (AnimTask animTask : this.k) {
            animTask.info = this;
            int i2 = i + ceil > size ? size - i : ceil;
            animTask.setup(i, i2);
            if (z) {
                animTask.animStats.a = i2;
            } else {
                animTask.a();
            }
            i += i2;
        }
    }

    private AnimState a(AnimState animState) {
        if (animState == null || !animState.isTemporary) {
            return animState;
        }
        AnimState animState2 = new AnimState();
        animState2.set(animState);
        return animState2;
    }

    public int a() {
        return this.h.keySet().size();
    }

    public boolean a(FloatProperty floatProperty) {
        return this.h.contains(floatProperty);
    }

    private void c() {
        if (this.g != null) {
            for (Object obj : this.h.keySet()) {
                FloatProperty tempProperty = this.h.getTempProperty(obj);
                if ((tempProperty instanceof ColorProperty) && AnimValueUtils.isInvalid(AnimValueUtils.getValueOfTarget(this.c, tempProperty, Double.MAX_VALUE))) {
                    double d = this.g.get(this.c, tempProperty);
                    if (!AnimValueUtils.isInvalid(d)) {
                        this.c.setIntValue((ColorProperty) tempProperty, (int) d);
                    }
                }
            }
        }
    }

    public void a(IUpdateInfoCreator iUpdateInfoCreator) {
        this.i = System.nanoTime();
        AnimState animState = this.g;
        AnimState animState2 = this.h;
        boolean isLogEnabled = LogUtils.isLogEnabled();
        if (isLogEnabled) {
            LogUtils.debug("-- doSetup, target = " + this.c + ", key = " + this.e + ", f = " + animState + ", t = " + animState2 + "\nconfig = " + this.f, new Object[0]);
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : animState2.keySet()) {
            FloatProperty property = animState2.getProperty(obj);
            UpdateInfo updateInfo = iUpdateInfoCreator.getUpdateInfo(property);
            if (updateInfo != null) {
                arrayList.add(updateInfo);
                updateInfo.animInfo.targetValue = animState2.get(this.c, property);
                if (animState != null) {
                    updateInfo.animInfo.startValue = animState.get(this.c, property);
                } else {
                    double valueOfTarget = AnimValueUtils.getValueOfTarget(this.c, property, updateInfo.animInfo.startValue);
                    if (!AnimValueUtils.isInvalid(valueOfTarget)) {
                        updateInfo.animInfo.startValue = valueOfTarget;
                    }
                }
                AnimValueUtils.handleSetToValue(updateInfo);
                if (isLogEnabled) {
                    LogUtils.debug("-- doSetup, target = " + this.c + ", property = " + property.getName() + ", startValue = " + updateInfo.animInfo.startValue + ", targetValue = " + updateInfo.animInfo.targetValue + ", value = " + updateInfo.animInfo.value, new Object[0]);
                }
            }
        }
        this.j = arrayList;
    }

    public c b() {
        this.m.clear();
        for (AnimTask animTask : this.k) {
            this.m.a(animTask.animStats);
        }
        return this.m;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TransitionInfo{target = ");
        IAnimTarget iAnimTarget = this.c;
        sb.append(iAnimTarget != null ? iAnimTarget.getTargetObject() : null);
        sb.append(", key = ");
        sb.append(this.e);
        sb.append(", propSize = ");
        sb.append(this.h.keySet().size());
        sb.append(", next = ");
        sb.append(this.next);
        sb.append('}');
        return sb.toString();
    }
}
