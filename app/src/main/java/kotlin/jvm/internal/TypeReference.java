package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeReference.kt */
@SinceKotlin(version = "1.4")
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0017\u001a\u00020\u0013H\u0002J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J\f\u0010\u0017\u001a\u00020\u0013*\u00020\u0006H\u0002R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u001c\u0010\u0012\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, d2 = {"Lkotlin/jvm/internal/TypeReference;", "Lkotlin/reflect/KType;", "classifier", "Lkotlin/reflect/KClassifier;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "isMarkedNullable", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Z)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lkotlin/reflect/KClassifier;", "()Z", "arrayClassName", "", "Ljava/lang/Class;", "getArrayClassName", "(Ljava/lang/Class;)Ljava/lang/String;", "asString", "equals", "other", "", "hashCode", "", "toString", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class TypeReference implements KType {
    @NotNull
    private final KClassifier a;
    @NotNull
    private final List<KTypeProjection> b;
    private final boolean c;

    public TypeReference(@NotNull KClassifier classifier, @NotNull List<KTypeProjection> arguments, boolean z) {
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.a = classifier;
        this.b = arguments;
        this.c = z;
    }

    @Override // kotlin.reflect.KType
    @NotNull
    public KClassifier getClassifier() {
        return this.a;
    }

    @Override // kotlin.reflect.KType
    @NotNull
    public List<KTypeProjection> getArguments() {
        return this.b;
    }

    @Override // kotlin.reflect.KType
    public boolean isMarkedNullable() {
        return this.c;
    }

    @Override // kotlin.reflect.KAnnotatedElement
    @NotNull
    public List<Annotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof TypeReference) {
            TypeReference typeReference = (TypeReference) obj;
            if (Intrinsics.areEqual(getClassifier(), typeReference.getClassifier()) && Intrinsics.areEqual(getArguments(), typeReference.getArguments()) && isMarkedNullable() == typeReference.isMarkedNullable()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((getClassifier().hashCode() * 31) + getArguments().hashCode()) * 31) + Boolean.valueOf(isMarkedNullable()).hashCode();
    }

    @NotNull
    public String toString() {
        return a() + " (Kotlin reflection is not available)";
    }

    private final String a() {
        String str;
        KClassifier classifier = getClassifier();
        Class<?> cls = null;
        if (!(classifier instanceof KClass)) {
            classifier = null;
        }
        KClass kClass = (KClass) classifier;
        if (kClass != null) {
            cls = JvmClassMappingKt.getJavaClass(kClass);
        }
        if (cls == null) {
            str = getClassifier().toString();
        } else {
            str = cls.isArray() ? a(cls) : cls.getName();
        }
        return str + (getArguments().isEmpty() ? "" : CollectionsKt.joinToString$default(getArguments(), ", ", "<", ">", 0, null, new a(), 24, null)) + (isMarkedNullable() ? "?" : "");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TypeReference.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lkotlin/reflect/KTypeProjection;", "invoke"}, k = 3, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class a extends Lambda implements Function1<KTypeProjection, CharSequence> {
        a() {
            super(1);
        }

        @NotNull
        /* renamed from: a */
        public final CharSequence invoke(@NotNull KTypeProjection it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return TypeReference.this.a(it);
        }
    }

    private final String a(Class<?> cls) {
        return Intrinsics.areEqual(cls, boolean[].class) ? "kotlin.BooleanArray" : Intrinsics.areEqual(cls, char[].class) ? "kotlin.CharArray" : Intrinsics.areEqual(cls, byte[].class) ? "kotlin.ByteArray" : Intrinsics.areEqual(cls, short[].class) ? "kotlin.ShortArray" : Intrinsics.areEqual(cls, int[].class) ? "kotlin.IntArray" : Intrinsics.areEqual(cls, float[].class) ? "kotlin.FloatArray" : Intrinsics.areEqual(cls, long[].class) ? "kotlin.LongArray" : Intrinsics.areEqual(cls, double[].class) ? "kotlin.DoubleArray" : "kotlin.Array";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String a(KTypeProjection kTypeProjection) {
        String str;
        if (kTypeProjection.getVariance() == null) {
            return "*";
        }
        KType type = kTypeProjection.getType();
        if (!(type instanceof TypeReference)) {
            type = null;
        }
        TypeReference typeReference = (TypeReference) type;
        if (typeReference == null || (str = typeReference.a()) == null) {
            str = String.valueOf(kTypeProjection.getType());
        }
        KVariance variance = kTypeProjection.getVariance();
        if (variance != null) {
            switch (variance) {
                case INVARIANT:
                    return str;
                case IN:
                    return "in " + str;
                case OUT:
                    return "out " + str;
            }
        }
        throw new NoWhenBranchMatchedException();
    }
}
