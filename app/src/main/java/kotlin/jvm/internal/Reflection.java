package kotlin.jvm.internal;

import java.util.Arrays;
import java.util.Collections;
import kotlin.SinceKotlin;
import kotlin.collections.ArraysKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;

/* loaded from: classes5.dex */
public class Reflection {
    private static final ReflectionFactory a;
    private static final KClass[] b;

    static {
        ReflectionFactory reflectionFactory = null;
        try {
            reflectionFactory = (ReflectionFactory) Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
        }
        if (reflectionFactory == null) {
            reflectionFactory = new ReflectionFactory();
        }
        a = reflectionFactory;
        b = new KClass[0];
    }

    public static KClass createKotlinClass(Class cls) {
        return a.createKotlinClass(cls);
    }

    public static KClass createKotlinClass(Class cls, String str) {
        return a.createKotlinClass(cls, str);
    }

    @SinceKotlin(version = "1.4")
    public static KDeclarationContainer getOrCreateKotlinPackage(Class cls) {
        return a.getOrCreateKotlinPackage(cls, "");
    }

    public static KDeclarationContainer getOrCreateKotlinPackage(Class cls, String str) {
        return a.getOrCreateKotlinPackage(cls, str);
    }

    public static KClass getOrCreateKotlinClass(Class cls) {
        return a.getOrCreateKotlinClass(cls);
    }

    public static KClass getOrCreateKotlinClass(Class cls, String str) {
        return a.getOrCreateKotlinClass(cls, str);
    }

    public static KClass[] getOrCreateKotlinClasses(Class[] clsArr) {
        int length = clsArr.length;
        if (length == 0) {
            return b;
        }
        KClass[] kClassArr = new KClass[length];
        for (int i = 0; i < length; i++) {
            kClassArr[i] = getOrCreateKotlinClass(clsArr[i]);
        }
        return kClassArr;
    }

    @SinceKotlin(version = "1.1")
    public static String renderLambdaToString(Lambda lambda) {
        return a.renderLambdaToString(lambda);
    }

    @SinceKotlin(version = "1.3")
    public static String renderLambdaToString(FunctionBase functionBase) {
        return a.renderLambdaToString(functionBase);
    }

    public static KFunction function(FunctionReference functionReference) {
        return a.function(functionReference);
    }

    public static KProperty0 property0(PropertyReference0 propertyReference0) {
        return a.property0(propertyReference0);
    }

    public static KMutableProperty0 mutableProperty0(MutablePropertyReference0 mutablePropertyReference0) {
        return a.mutableProperty0(mutablePropertyReference0);
    }

    public static KProperty1 property1(PropertyReference1 propertyReference1) {
        return a.property1(propertyReference1);
    }

    public static KMutableProperty1 mutableProperty1(MutablePropertyReference1 mutablePropertyReference1) {
        return a.mutableProperty1(mutablePropertyReference1);
    }

    public static KProperty2 property2(PropertyReference2 propertyReference2) {
        return a.property2(propertyReference2);
    }

    public static KMutableProperty2 mutableProperty2(MutablePropertyReference2 mutablePropertyReference2) {
        return a.mutableProperty2(mutablePropertyReference2);
    }

    @SinceKotlin(version = "1.4")
    public static KType typeOf(KClassifier kClassifier) {
        return a.typeOf(kClassifier, Collections.emptyList(), false);
    }

    @SinceKotlin(version = "1.4")
    public static KType typeOf(Class cls) {
        return a.typeOf(getOrCreateKotlinClass(cls), Collections.emptyList(), false);
    }

    @SinceKotlin(version = "1.4")
    public static KType typeOf(Class cls, KTypeProjection kTypeProjection) {
        return a.typeOf(getOrCreateKotlinClass(cls), Collections.singletonList(kTypeProjection), false);
    }

    @SinceKotlin(version = "1.4")
    public static KType typeOf(Class cls, KTypeProjection kTypeProjection, KTypeProjection kTypeProjection2) {
        return a.typeOf(getOrCreateKotlinClass(cls), Arrays.asList(kTypeProjection, kTypeProjection2), false);
    }

    @SinceKotlin(version = "1.4")
    public static KType typeOf(Class cls, KTypeProjection... kTypeProjectionArr) {
        return a.typeOf(getOrCreateKotlinClass(cls), ArraysKt.toList(kTypeProjectionArr), false);
    }

    @SinceKotlin(version = "1.4")
    public static KType nullableTypeOf(KClassifier kClassifier) {
        return a.typeOf(kClassifier, Collections.emptyList(), true);
    }

    @SinceKotlin(version = "1.4")
    public static KType nullableTypeOf(Class cls) {
        return a.typeOf(getOrCreateKotlinClass(cls), Collections.emptyList(), true);
    }

    @SinceKotlin(version = "1.4")
    public static KType nullableTypeOf(Class cls, KTypeProjection kTypeProjection) {
        return a.typeOf(getOrCreateKotlinClass(cls), Collections.singletonList(kTypeProjection), true);
    }

    @SinceKotlin(version = "1.4")
    public static KType nullableTypeOf(Class cls, KTypeProjection kTypeProjection, KTypeProjection kTypeProjection2) {
        return a.typeOf(getOrCreateKotlinClass(cls), Arrays.asList(kTypeProjection, kTypeProjection2), true);
    }

    @SinceKotlin(version = "1.4")
    public static KType nullableTypeOf(Class cls, KTypeProjection... kTypeProjectionArr) {
        return a.typeOf(getOrCreateKotlinClass(cls), ArraysKt.toList(kTypeProjectionArr), true);
    }

    @SinceKotlin(version = "1.4")
    public static KTypeParameter typeParameter(Object obj, String str, KVariance kVariance, boolean z) {
        return a.typeParameter(obj, str, kVariance, z);
    }

    @SinceKotlin(version = "1.4")
    public static void setUpperBounds(KTypeParameter kTypeParameter, KType kType) {
        a.setUpperBounds(kTypeParameter, Collections.singletonList(kType));
    }

    @SinceKotlin(version = "1.4")
    public static void setUpperBounds(KTypeParameter kTypeParameter, KType... kTypeArr) {
        a.setUpperBounds(kTypeParameter, ArraysKt.toList(kTypeArr));
    }
}
