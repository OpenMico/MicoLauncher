package miuix.animation.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public class AnimConfig {
    public static final long FLAG_DELTA = 1;
    public static final long FLAG_INIT = 2;
    public static final long FLAG_INT = 4;
    public static final int TINT_ALPHA = 0;
    public static final int TINT_AUTO = -1;
    public static final int TINT_OPAQUE = 1;
    public static final EaseManager.EaseStyle sDefEase = EaseManager.getStyle(-2, 0.85f, 0.3f);
    private final Map<String, AnimSpecialConfig> a;
    public long delay;
    public EaseManager.EaseStyle ease;
    public long flags;
    public float fromSpeed;
    public final HashSet<TransitionListener> listeners;
    @Deprecated
    public long minDuration;
    public Object tag;
    public int tintMode;

    public AnimConfig() {
        this(false);
    }

    public AnimConfig(boolean z) {
        this.fromSpeed = Float.MAX_VALUE;
        this.tintMode = -1;
        if (!z) {
            this.a = new HashMap();
            this.listeners = new HashSet<>();
            return;
        }
        this.a = null;
        this.listeners = null;
    }

    public AnimConfig(AnimConfig animConfig) {
        this(false);
        copy(animConfig);
    }

    public void copy(AnimConfig animConfig) {
        if (animConfig != null && animConfig != this) {
            this.delay = animConfig.delay;
            this.ease = animConfig.ease;
            this.listeners.addAll(animConfig.listeners);
            this.tag = animConfig.tag;
            this.flags = animConfig.flags;
            this.fromSpeed = animConfig.fromSpeed;
            this.minDuration = animConfig.minDuration;
            this.tintMode = animConfig.tintMode;
            Map<String, AnimSpecialConfig> map = this.a;
            if (map != null) {
                map.clear();
                this.a.putAll(animConfig.a);
            }
        }
    }

    public void clear() {
        this.delay = 0L;
        this.ease = null;
        this.listeners.clear();
        this.tag = null;
        this.flags = 0L;
        this.fromSpeed = Float.MAX_VALUE;
        this.minDuration = 0L;
        this.tintMode = -1;
        Map<String, AnimSpecialConfig> map = this.a;
        if (map != null) {
            map.clear();
        }
    }

    public AnimConfig setEase(EaseManager.EaseStyle easeStyle) {
        this.ease = easeStyle;
        return this;
    }

    public AnimConfig setEase(int i, float... fArr) {
        this.ease = EaseManager.getStyle(i, fArr);
        return this;
    }

    public AnimConfig setDelay(long j) {
        this.delay = j;
        return this;
    }

    public AnimConfig setFromSpeed(float f) {
        this.fromSpeed = f;
        return this;
    }

    public AnimConfig addListeners(TransitionListener... transitionListenerArr) {
        Collections.addAll(this.listeners, transitionListenerArr);
        return this;
    }

    public AnimConfig setTintMode(int i) {
        this.tintMode = i;
        return this;
    }

    public void addSpecialConfigs(AnimConfig animConfig) {
        this.a.putAll(animConfig.a);
    }

    public AnimConfig removeListeners(TransitionListener... transitionListenerArr) {
        if (transitionListenerArr.length == 0) {
            this.listeners.clear();
        } else {
            this.listeners.removeAll(Arrays.asList(transitionListenerArr));
        }
        return this;
    }

    public AnimConfig setTag(Object obj) {
        this.tag = obj;
        return this;
    }

    public AnimConfig setMinDuration(long j) {
        this.minDuration = j;
        return this;
    }

    public AnimConfig setSpecial(String str, long j, float... fArr) {
        return setSpecial(str, (EaseManager.EaseStyle) null, j, fArr);
    }

    public AnimConfig setSpecial(String str, EaseManager.EaseStyle easeStyle, float... fArr) {
        return setSpecial(str, easeStyle, 0L, fArr);
    }

    public AnimConfig setSpecial(String str, EaseManager.EaseStyle easeStyle, long j, float... fArr) {
        a(a(str, true), easeStyle, j, fArr);
        return this;
    }

    public AnimConfig setSpecial(FloatProperty floatProperty, long j, float... fArr) {
        return setSpecial(floatProperty, (EaseManager.EaseStyle) null, j, fArr);
    }

    public AnimConfig setSpecial(FloatProperty floatProperty, EaseManager.EaseStyle easeStyle, float... fArr) {
        setSpecial(floatProperty, easeStyle, -1L, fArr);
        return this;
    }

    public AnimConfig setSpecial(FloatProperty floatProperty, EaseManager.EaseStyle easeStyle, long j, float... fArr) {
        a(a(floatProperty, true), easeStyle, j, fArr);
        return this;
    }

    public void a(AnimSpecialConfig animSpecialConfig, EaseManager.EaseStyle easeStyle, long j, float... fArr) {
        if (easeStyle != null) {
            animSpecialConfig.setEase(easeStyle);
        }
        if (j > 0) {
            animSpecialConfig.setDelay(j);
        }
        if (fArr.length > 0) {
            animSpecialConfig.setFromSpeed(fArr[0]);
        }
    }

    public AnimSpecialConfig getSpecialConfig(FloatProperty floatProperty) {
        return a(floatProperty, false);
    }

    public AnimSpecialConfig queryAndCreateSpecial(FloatProperty floatProperty) {
        return a(floatProperty, true);
    }

    public AnimSpecialConfig queryAndCreateSpecial(String str) {
        return a(str, true);
    }

    private AnimSpecialConfig a(FloatProperty floatProperty, boolean z) {
        if (floatProperty == null) {
            return null;
        }
        return a(floatProperty.getName(), z);
    }

    public AnimSpecialConfig getSpecialConfig(String str) {
        return a(str, false);
    }

    public AnimConfig setSpecial(FloatProperty floatProperty, AnimSpecialConfig animSpecialConfig) {
        if (animSpecialConfig != null) {
            this.a.put(floatProperty.getName(), animSpecialConfig);
        } else {
            this.a.remove(floatProperty.getName());
        }
        return this;
    }

    public AnimConfig setSpecial(String str, AnimSpecialConfig animSpecialConfig) {
        if (animSpecialConfig != null) {
            this.a.put(str, animSpecialConfig);
        } else {
            this.a.remove(str);
        }
        return this;
    }

    private AnimSpecialConfig a(String str, boolean z) {
        AnimSpecialConfig animSpecialConfig = this.a.get(str);
        if (animSpecialConfig != null || !z) {
            return animSpecialConfig;
        }
        AnimSpecialConfig animSpecialConfig2 = new AnimSpecialConfig();
        this.a.put(str, animSpecialConfig2);
        return animSpecialConfig2;
    }

    public String toString() {
        return "AnimConfig{delay=" + this.delay + ", minDuration=" + this.minDuration + ", ease=" + this.ease + ", fromSpeed=" + this.fromSpeed + ", tintMode=" + this.tintMode + ", tag=" + this.tag + ", flags=" + this.flags + ", listeners=" + this.listeners + ", specialNameMap = " + ((Object) CommonUtils.mapToString(this.a, "    ")) + '}';
    }
}
