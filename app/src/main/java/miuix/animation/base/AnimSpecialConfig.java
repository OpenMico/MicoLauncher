package miuix.animation.base;

import miuix.animation.property.FloatProperty;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public class AnimSpecialConfig extends AnimConfig {
    public double maxValue;
    public double minValue;

    @Override // miuix.animation.base.AnimConfig
    @Deprecated
    public AnimSpecialConfig getSpecialConfig(String str) {
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    @Deprecated
    public AnimSpecialConfig getSpecialConfig(FloatProperty floatProperty) {
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    @Deprecated
    public AnimSpecialConfig queryAndCreateSpecial(String str) {
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    @Deprecated
    public AnimSpecialConfig queryAndCreateSpecial(FloatProperty floatProperty) {
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    @Deprecated
    public AnimConfig setSpecial(String str, AnimSpecialConfig animSpecialConfig) {
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    @Deprecated
    public AnimConfig setSpecial(FloatProperty floatProperty, AnimSpecialConfig animSpecialConfig) {
        return this;
    }

    public AnimSpecialConfig() {
        super(true);
    }

    public AnimSpecialConfig setMinAndMax(double d, double d2) {
        this.minValue = d;
        this.maxValue = d2;
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    public AnimConfig setSpecial(String str, long j, float... fArr) {
        super.a(this, null, j, fArr);
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    public AnimConfig setSpecial(String str, EaseManager.EaseStyle easeStyle, float... fArr) {
        super.a(this, easeStyle, -1L, fArr);
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    public AnimConfig setSpecial(String str, EaseManager.EaseStyle easeStyle, long j, float... fArr) {
        super.a(this, easeStyle, j, fArr);
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    public AnimConfig setSpecial(FloatProperty floatProperty, long j, float... fArr) {
        super.a(this, null, j, fArr);
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    public AnimConfig setSpecial(FloatProperty floatProperty, EaseManager.EaseStyle easeStyle, float... fArr) {
        super.a(this, easeStyle, -1L, fArr);
        return this;
    }

    @Override // miuix.animation.base.AnimConfig
    public AnimConfig setSpecial(FloatProperty floatProperty, EaseManager.EaseStyle easeStyle, long j, float... fArr) {
        super.a(this, easeStyle, j, fArr);
        return this;
    }
}
