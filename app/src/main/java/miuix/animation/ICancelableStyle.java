package miuix.animation;

import miuix.animation.property.FloatProperty;

/* loaded from: classes5.dex */
public interface ICancelableStyle {
    void cancel();

    void cancel(String... strArr);

    void cancel(FloatProperty... floatPropertyArr);

    void end(Object... objArr);
}
