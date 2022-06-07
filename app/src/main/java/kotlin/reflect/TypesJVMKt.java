package kotlin.reflect;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.KTypeBase;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypesJVM.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a\"\u0010\n\u001a\u00020\u00012\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0003\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0001H\u0002\u001a\u0016\u0010\u0012\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0003\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00078BX\u0083\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\b\u001a\u0004\b\u0005\u0010\t¨\u0006\u0015"}, d2 = {"javaType", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KType;", "getJavaType$annotations", "(Lkotlin/reflect/KType;)V", "getJavaType", "(Lkotlin/reflect/KType;)Ljava/lang/reflect/Type;", "Lkotlin/reflect/KTypeProjection;", "(Lkotlin/reflect/KTypeProjection;)V", "(Lkotlin/reflect/KTypeProjection;)Ljava/lang/reflect/Type;", "createPossiblyInnerType", "jClass", "Ljava/lang/Class;", "arguments", "", "typeToString", "", "type", "computeJavaType", "forceWrapper", "", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class TypesJVMKt {
    @LowPriorityInOverloadResolution
    @SinceKotlin(version = "1.4")
    @ExperimentalStdlibApi
    public static /* synthetic */ void getJavaType$annotations(KType kType) {
    }

    @NotNull
    public static final Type getJavaType(@NotNull KType javaType) {
        Type javaType2;
        Intrinsics.checkNotNullParameter(javaType, "$this$javaType");
        return (!(javaType instanceof KTypeBase) || (javaType2 = ((KTypeBase) javaType).getJavaType()) == null) ? a(javaType, false, 1, null) : javaType2;
    }

    static /* synthetic */ Type a(KType kType, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return a(kType, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ExperimentalStdlibApi
    public static final Type a(KType kType, boolean z) {
        KClassifier classifier = kType.getClassifier();
        if (classifier instanceof KTypeParameter) {
            return new d((KTypeParameter) classifier);
        }
        if (classifier instanceof KClass) {
            Class javaObjectType = z ? JvmClassMappingKt.getJavaObjectType((KClass) classifier) : JvmClassMappingKt.getJavaClass((KClass) classifier);
            List<KTypeProjection> arguments = kType.getArguments();
            if (arguments.isEmpty()) {
                return javaObjectType;
            }
            if (!javaObjectType.isArray()) {
                return a(javaObjectType, arguments);
            }
            Class<?> componentType = javaObjectType.getComponentType();
            Intrinsics.checkNotNullExpressionValue(componentType, "jClass.componentType");
            if (componentType.isPrimitive()) {
                return javaObjectType;
            }
            KTypeProjection kTypeProjection = (KTypeProjection) CollectionsKt.singleOrNull((List<? extends Object>) arguments);
            if (kTypeProjection != null) {
                KVariance component1 = kTypeProjection.component1();
                KType component2 = kTypeProjection.component2();
                if (component1 != null) {
                    switch (component1) {
                        case IN:
                            break;
                        case INVARIANT:
                        case OUT:
                            Intrinsics.checkNotNull(component2);
                            Type a2 = a(component2, false, 1, null);
                            Type type = javaObjectType;
                            if (!(a2 instanceof Class)) {
                                type = new a(a2);
                            }
                            return type;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }
                return javaObjectType;
            }
            throw new IllegalArgumentException("kotlin.Array must have exactly one type argument: " + kType);
        }
        throw new UnsupportedOperationException("Unsupported type classifier: " + kType);
    }

    @ExperimentalStdlibApi
    private static final Type a(Class<?> cls, List<KTypeProjection> list) {
        Class<?> declaringClass = cls.getDeclaringClass();
        if (declaringClass == null) {
            List<KTypeProjection> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (KTypeProjection kTypeProjection : list2) {
                arrayList.add(a(kTypeProjection));
            }
            return new b(cls, null, arrayList);
        } else if (Modifier.isStatic(cls.getModifiers())) {
            Class<?> cls2 = declaringClass;
            List<KTypeProjection> list3 = list;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            for (KTypeProjection kTypeProjection2 : list3) {
                arrayList2.add(a(kTypeProjection2));
            }
            return new b(cls, cls2, arrayList2);
        } else {
            int length = cls.getTypeParameters().length;
            Type a2 = a(declaringClass, list.subList(length, list.size()));
            List<KTypeProjection> subList = list.subList(0, length);
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(subList, 10));
            for (KTypeProjection kTypeProjection3 : subList) {
                arrayList3.add(a(kTypeProjection3));
            }
            return new b(cls, a2, arrayList3);
        }
    }

    private static final Type a(KTypeProjection kTypeProjection) {
        KVariance variance = kTypeProjection.getVariance();
        if (variance == null) {
            return WildcardTypeImpl.a.getSTAR();
        }
        KType type = kTypeProjection.getType();
        Intrinsics.checkNotNull(type);
        switch (variance) {
            case INVARIANT:
                return a(type, true);
            case IN:
                return new WildcardTypeImpl(null, a(type, true));
            case OUT:
                return new WildcardTypeImpl(a(type, true), null);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String a(Type type) {
        String str;
        if (!(type instanceof Class)) {
            return type.toString();
        }
        Class cls = (Class) type;
        if (cls.isArray()) {
            Sequence generateSequence = SequencesKt.generateSequence(type, a.a);
            str = ((Class) SequencesKt.last(generateSequence)).getName() + StringsKt.repeat(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, SequencesKt.count(generateSequence));
        } else {
            str = cls.getName();
        }
        Intrinsics.checkNotNullExpressionValue(str, "if (type.isArray) {\n    …\n        } else type.name");
        return str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TypesJVM.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u0012\u0012\u0002\b\u0003 \u0002*\b\u0012\u0002\b\u0003\u0018\u00010\u00010\u00012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0001¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Ljava/lang/Class;", "kotlin.jvm.PlatformType", "p1", "invoke"}, k = 3, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final /* synthetic */ class a extends FunctionReferenceImpl implements Function1<Class<? extends Object>, Class<?>> {
        public static final a a = new a();

        a() {
            super(1, Class.class, "getComponentType", "getComponentType()Ljava/lang/Class;", 0);
        }

        /* renamed from: a */
        public final Class<?> invoke(@NotNull Class<?> p1) {
            Intrinsics.checkNotNullParameter(p1, "p1");
            return p1.getComponentType();
        }
    }
}
