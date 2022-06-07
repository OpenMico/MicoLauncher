package miuix.animation.controller;

import android.util.ArrayMap;
import java.util.Map;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.EaseManager;

/* compiled from: StateManager.java */
/* loaded from: classes5.dex */
class b {
    Object b;
    final Map<Object, AnimState> a = new ArrayMap();
    final AnimState c = new AnimState("defaultTo", true);
    final AnimState d = new AnimState("defaultSetTo", true);
    final AnimState e = new AnimState("autoSetTo", true);
    a f = new a();

    public boolean a(Object obj) {
        return this.a.containsKey(obj);
    }

    public void a(AnimState animState) {
        this.a.put(animState.getTag(), animState);
    }

    public AnimState b(Object obj) {
        return a(obj, true);
    }

    public AnimState a(IAnimTarget iAnimTarget, AnimConfigLink animConfigLink, Object... objArr) {
        AnimState a = a(this.d, objArr);
        a(iAnimTarget, a, animConfigLink, objArr);
        return a;
    }

    public AnimState b(IAnimTarget iAnimTarget, AnimConfigLink animConfigLink, Object... objArr) {
        AnimState a = a(a(), objArr);
        a(iAnimTarget, a, animConfigLink, objArr);
        return a;
    }

    private AnimState a(Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AnimState) {
            return (AnimState) obj;
        }
        AnimState animState = this.a.get(obj);
        if (animState != null || !z) {
            return animState;
        }
        AnimState animState2 = new AnimState(obj);
        a(animState2);
        return animState2;
    }

    public AnimState c(Object obj) {
        AnimState animState;
        if (obj instanceof AnimState) {
            animState = (AnimState) obj;
        } else {
            animState = this.a.get(obj);
            if (animState == null) {
                animState = new AnimState(obj);
                a(animState);
            }
        }
        this.b = animState;
        return animState;
    }

    public void a(TransitionListener transitionListener) {
        a().getConfig().addListeners(transitionListener);
    }

    public void b(TransitionListener transitionListener) {
        a().getConfig().removeListeners(transitionListener);
    }

    public void a(EaseManager.EaseStyle easeStyle, FloatProperty... floatPropertyArr) {
        AnimConfig config = a().getConfig();
        if (floatPropertyArr.length == 0) {
            config.setEase(easeStyle);
            return;
        }
        for (FloatProperty floatProperty : floatPropertyArr) {
            config.setSpecial(floatProperty, easeStyle, new float[0]);
        }
    }

    public void a(int i, float... fArr) {
        a().getConfig().setEase(i, fArr);
    }

    public void a(FloatProperty floatProperty, int i, float... fArr) {
        a().getConfig().setSpecial(floatProperty, i, fArr);
    }

    public void a(Object obj, long j) {
        b(obj).flags = j;
    }

    public void a(Object obj, long j, FloatProperty... floatPropertyArr) {
        AnimConfig config = b(obj).getConfig();
        if (floatPropertyArr.length == 0) {
            config.flags = j;
            return;
        }
        for (FloatProperty floatProperty : floatPropertyArr) {
            AnimSpecialConfig specialConfig = config.getSpecialConfig(floatProperty);
            if (specialConfig == null) {
                specialConfig = new AnimSpecialConfig();
                config.setSpecial(floatProperty, specialConfig);
            }
            specialConfig.flags = j;
        }
    }

    public void a(FloatProperty floatProperty, int i) {
        a(floatProperty, i, 2L);
    }

    public void a(FloatProperty floatProperty, float f) {
        a(floatProperty, f, 2L);
    }

    public void a(String str, int i) {
        a(str, i, 2L);
    }

    public void a(String str, float f) {
        a(str, f, 2L);
    }

    public void b(String str, float f) {
        a().add(str, f);
    }

    public void b(String str, int i) {
        a().add(str, i);
    }

    public void a(String str, float f, long j) {
        AnimState a = a();
        a.setConfigFlag(str, j);
        a.add(str, f);
    }

    public void a(String str, int i, long j) {
        AnimState a = a();
        a.setConfigFlag(str, j);
        a.add(str, i);
    }

    public void b(FloatProperty floatProperty, int i) {
        a().add(floatProperty, i);
    }

    public void b(FloatProperty floatProperty, float f) {
        a().add(floatProperty, f);
    }

    public void a(FloatProperty floatProperty, int i, long j) {
        AnimState a = a();
        a.setConfigFlag(floatProperty, j);
        a.add(floatProperty, i);
    }

    public void a(FloatProperty floatProperty, float f, long j) {
        AnimState a = a();
        a.setConfigFlag(floatProperty, j);
        a.add(floatProperty, f);
    }

    public AnimState a() {
        if (this.b == null) {
            this.b = this.c;
        }
        return b(this.b);
    }

    public void a(AnimState animState, AnimConfigLink animConfigLink) {
        AnimState animState2 = this.c;
        if (animState != animState2) {
            animConfigLink.add(animState2.getConfig(), new boolean[0]);
        }
    }

    public void b(AnimState animState) {
        if (animState == this.c || animState == this.d) {
            animState.clear();
        }
    }

    private AnimState a(Object obj, Object... objArr) {
        AnimState animState;
        if (objArr.length > 0) {
            animState = a(objArr[0], false);
            if (animState == null) {
                animState = a(objArr);
            }
        } else {
            animState = null;
        }
        return animState == null ? b(obj) : animState;
    }

    private AnimState a(Object... objArr) {
        Object obj = objArr[0];
        Object obj2 = objArr.length > 1 ? objArr[1] : null;
        if (!(obj instanceof String) || !(obj2 instanceof String)) {
            return null;
        }
        return a(obj, true);
    }

    private void a(IAnimTarget iAnimTarget, AnimState animState, AnimConfigLink animConfigLink, Object... objArr) {
        this.f.a(iAnimTarget, animState, animConfigLink, objArr);
    }
}
