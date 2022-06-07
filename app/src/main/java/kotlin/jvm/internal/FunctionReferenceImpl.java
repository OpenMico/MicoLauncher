package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

/* loaded from: classes5.dex */
public class FunctionReferenceImpl extends FunctionReference {
    public FunctionReferenceImpl(int i, KDeclarationContainer kDeclarationContainer, String str, String str2) {
        super(i, NO_RECEIVER, ((ClassBasedDeclarationContainer) kDeclarationContainer).getJClass(), str, str2, kDeclarationContainer instanceof KClass ? 0 : 1);
    }

    @SinceKotlin(version = "1.4")
    public FunctionReferenceImpl(int i, Class cls, String str, String str2, int i2) {
        super(i, NO_RECEIVER, cls, str, str2, i2);
    }

    @SinceKotlin(version = "1.4")
    public FunctionReferenceImpl(int i, Object obj, Class cls, String str, String str2, int i2) {
        super(i, obj, cls, str, str2, i2);
    }
}
