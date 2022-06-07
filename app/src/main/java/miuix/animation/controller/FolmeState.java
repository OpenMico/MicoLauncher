package miuix.animation.controller;

import java.lang.reflect.Array;
import miuix.animation.Folme;
import miuix.animation.IAnimTarget;
import miuix.animation.IStateStyle;
import miuix.animation.ValueTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.internal.AnimRunner;
import miuix.animation.internal.PredictTask;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.LogUtils;

/* loaded from: classes5.dex */
public class FolmeState implements IFolmeStateStyle {
    IAnimTarget a;
    b b = new b();
    AnimConfigLink c = new AnimConfigLink();
    private boolean d = true;

    @Override // miuix.animation.IStateContainer
    @Deprecated
    public void addConfig(Object obj, AnimConfig... animConfigArr) {
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle autoSetTo(Object... objArr) {
        return this;
    }

    @Override // miuix.animation.IStateStyle
    @Deprecated
    public IStateStyle setConfig(AnimConfig animConfig, FloatProperty... floatPropertyArr) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FolmeState(IAnimTarget iAnimTarget) {
        this.a = iAnimTarget;
    }

    @Override // miuix.animation.controller.IFolmeStateStyle
    public IAnimTarget getTarget() {
        return this.a;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setTo(Object obj) {
        return setTo(obj, new AnimConfig[0]);
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setTo(Object obj, AnimConfig... animConfigArr) {
        return a(obj, AnimConfigLink.linkConfig(animConfigArr));
    }

    private IStateStyle a(final Object obj, final AnimConfigLink animConfigLink) {
        IAnimTarget iAnimTarget = this.a;
        if (iAnimTarget == null) {
            return this;
        }
        if ((obj instanceof Integer) || (obj instanceof Float)) {
            return setTo(obj, animConfigLink);
        }
        iAnimTarget.executeOnInitialized(new Runnable() { // from class: miuix.animation.controller.FolmeState.1
            @Override // java.lang.Runnable
            public void run() {
                AnimState state = FolmeState.this.getState(obj);
                IAnimTarget target = FolmeState.this.getTarget();
                if (LogUtils.isLogEnabled()) {
                    LogUtils.debug("FolmeState.setTo, state = " + state, new Object[0]);
                }
                target.animManager.setTo(state, animConfigLink);
                FolmeState.this.b.b(state);
            }
        });
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle to(AnimConfig... animConfigArr) {
        return to(getCurrentState(), animConfigArr);
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle to(Object obj, AnimConfig... animConfigArr) {
        if ((obj instanceof AnimState) || this.b.a(obj)) {
            return fromTo(null, getState(obj), animConfigArr);
        }
        if (!obj.getClass().isArray()) {
            return to(obj, animConfigArr);
        }
        int length = Array.getLength(obj);
        Object[] objArr = new Object[animConfigArr.length + length];
        System.arraycopy(obj, 0, objArr, 0, length);
        System.arraycopy(animConfigArr, 0, objArr, length, animConfigArr.length);
        return to(objArr);
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle then(Object obj, AnimConfig... animConfigArr) {
        this.b.a(obj, 1L);
        return to(obj, animConfigArr);
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle fromTo(Object obj, Object obj2, AnimConfig... animConfigArr) {
        AnimConfigLink a = a();
        for (AnimConfig animConfig : animConfigArr) {
            a.add(animConfig, new boolean[0]);
        }
        return a(obj, obj2, a);
    }

    private IStateStyle a(Object obj, Object obj2, AnimConfigLink animConfigLink) {
        if (this.d) {
            this.b.c(obj2);
            if (obj != null) {
                setTo(obj);
            }
            AnimState state = getState(obj2);
            this.b.a(state, animConfigLink);
            AnimRunner.getInst().run(this.a, getState(obj), getState(obj2), animConfigLink);
            this.b.b(state);
            animConfigLink.clear();
        }
        return this;
    }

    @Override // miuix.animation.IStateContainer
    public void enableDefaultAnim(boolean z) {
        this.d = z;
    }

    @Override // miuix.animation.IStateContainer
    public void clean() {
        cancel();
    }

    @Override // miuix.animation.ICancelableStyle
    public void cancel() {
        AnimRunner.getInst().cancel(this.a, (FloatProperty[]) null);
    }

    @Override // miuix.animation.ICancelableStyle
    public void cancel(FloatProperty... floatPropertyArr) {
        AnimRunner.getInst().cancel(this.a, floatPropertyArr);
    }

    @Override // miuix.animation.ICancelableStyle
    public void cancel(String... strArr) {
        IAnimTarget target = getTarget();
        if (strArr.length != 0 && (target instanceof ValueTarget)) {
            AnimRunner.getInst().cancel(this.a, strArr);
        }
    }

    @Override // miuix.animation.ICancelableStyle
    public void end(Object... objArr) {
        if (objArr.length <= 0) {
            return;
        }
        if (objArr[0] instanceof FloatProperty) {
            FloatProperty[] floatPropertyArr = new FloatProperty[objArr.length];
            System.arraycopy(objArr, 0, floatPropertyArr, 0, objArr.length);
            AnimRunner.getInst().end(this.a, floatPropertyArr);
            return;
        }
        String[] strArr = new String[objArr.length];
        System.arraycopy(objArr, 0, strArr, 0, objArr.length);
        AnimRunner.getInst().end(this.a, strArr);
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setTo(Object... objArr) {
        AnimConfigLink a = a();
        a(this.b.a(getTarget(), a, objArr), a);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle to(Object... objArr) {
        return fromTo(null, this.b.b(getTarget(), a(), objArr), new AnimConfig[0]);
    }

    @Override // miuix.animation.IStateStyle
    public long predictDuration(Object... objArr) {
        IAnimTarget target = getTarget();
        AnimConfigLink a = a();
        AnimState b = this.b.b(target, a, objArr);
        long predictDuration = PredictTask.predictDuration(target, null, b, a);
        this.b.b(b);
        a.clear();
        return predictDuration;
    }

    private AnimConfigLink a() {
        return this.c;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle then(Object... objArr) {
        AnimConfig animConfig = new AnimConfig();
        AnimState state = getState(objArr);
        state.flags = 1L;
        return to(state, animConfig);
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setFlags(long j) {
        getTarget().setFlags(j);
        return this;
    }

    @Override // miuix.animation.controller.IFolmeStateStyle
    public AnimState getState(Object obj) {
        return this.b.b(obj);
    }

    @Override // miuix.animation.controller.IFolmeStateStyle
    public void addState(AnimState animState) {
        this.b.a(animState);
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setup(Object obj) {
        this.b.c(obj);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle set(Object obj) {
        this.b.c(obj);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle addListener(TransitionListener transitionListener) {
        this.b.a(transitionListener);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle removeListener(TransitionListener transitionListener) {
        this.b.b(transitionListener);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle addInitProperty(FloatProperty floatProperty, int i) {
        this.b.a(floatProperty, i);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle addInitProperty(FloatProperty floatProperty, float f) {
        this.b.a(floatProperty, f);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle addInitProperty(String str, int i) {
        this.b.a(str, i);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle addInitProperty(String str, float f) {
        this.b.a(str, f);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle add(FloatProperty floatProperty, int i, long j) {
        this.b.a(floatProperty, i, j);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle add(String str, int i, long j) {
        this.b.a(str, i, j);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle add(FloatProperty floatProperty, int i) {
        this.b.b(floatProperty, i);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle add(FloatProperty floatProperty, float f) {
        this.b.b(floatProperty, f);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle add(FloatProperty floatProperty, float f, long j) {
        this.b.a(floatProperty, f, j);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle add(String str, int i) {
        this.b.b(str, i);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle add(String str, float f) {
        this.b.b(str, f);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle add(String str, float f, long j) {
        this.b.a(str, f, j);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setEase(EaseManager.EaseStyle easeStyle, FloatProperty... floatPropertyArr) {
        this.b.a(easeStyle, floatPropertyArr);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setEase(int i, float... fArr) {
        this.b.a(i, fArr);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setEase(FloatProperty floatProperty, int i, float... fArr) {
        this.b.a(floatProperty, i, fArr);
        return this;
    }

    @Override // miuix.animation.IStateStyle
    public AnimState getCurrentState() {
        return this.b.a();
    }

    @Override // miuix.animation.IStateStyle
    public float getPredictValue(FloatProperty floatProperty, float... fArr) {
        float velocity = (float) this.a.getVelocity(floatProperty);
        float value = this.a.getValue(floatProperty);
        float thresholdVelocity = (float) Folme.getTarget(this.a).getThresholdVelocity(floatProperty);
        if (velocity != 0.0f) {
            thresholdVelocity = Math.abs(thresholdVelocity) * Math.signum(velocity);
        }
        return (fArr == null || fArr.length == 0) ? value + Folme.getPredictDistance(velocity, thresholdVelocity) : value + Folme.getPredictDistanceWithFriction(velocity, fArr[0], thresholdVelocity);
    }

    @Override // miuix.animation.IStateStyle
    public float getPredictFriction(FloatProperty floatProperty, float f) {
        float velocity = (float) this.a.getVelocity(floatProperty);
        if (velocity == 0.0f) {
            return -1.0f;
        }
        return Folme.getPredictFriction(this.a.getValue(floatProperty), f, velocity, Math.signum(velocity) * ((float) Folme.getTarget(this.a).getThresholdVelocity(floatProperty)));
    }

    @Override // miuix.animation.IStateStyle
    public IStateStyle setTransitionFlags(long j, FloatProperty... floatPropertyArr) {
        b bVar = this.b;
        bVar.a(bVar.a(), j, floatPropertyArr);
        return this;
    }
}
