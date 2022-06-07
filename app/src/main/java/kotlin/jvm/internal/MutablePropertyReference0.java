package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KProperty0;

/* loaded from: classes5.dex */
public abstract class MutablePropertyReference0 extends MutablePropertyReference implements KMutableProperty0 {
    public MutablePropertyReference0() {
    }

    @SinceKotlin(version = "1.1")
    public MutablePropertyReference0(Object obj) {
        super(obj);
    }

    @SinceKotlin(version = "1.4")
    public MutablePropertyReference0(Object obj, Class cls, String str, String str2, int i) {
        super(obj, cls, str, str2, i);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        return Reflection.mutableProperty0(this);
    }

    @Override // kotlin.jvm.functions.Function0
    public Object invoke() {
        return get();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [kotlin.reflect.KProperty0$Getter] */
    @Override // kotlin.reflect.KProperty, kotlin.reflect.KProperty0
    public KProperty0.Getter getGetter() {
        return ((KMutableProperty0) getReflected()).getGetter();
    }

    @Override // kotlin.reflect.KMutableProperty, kotlin.reflect.KMutableProperty0
    public KMutableProperty0.Setter getSetter() {
        return ((KMutableProperty0) getReflected()).getSetter();
    }

    @Override // kotlin.reflect.KProperty0
    @SinceKotlin(version = "1.1")
    public Object getDelegate() {
        return ((KMutableProperty0) getReflected()).getDelegate();
    }
}
