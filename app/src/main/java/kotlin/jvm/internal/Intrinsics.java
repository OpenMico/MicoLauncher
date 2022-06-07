package kotlin.jvm.internal;

import com.fasterxml.jackson.core.JsonPointer;
import java.util.Arrays;
import kotlin.KotlinNullPointerException;
import kotlin.SinceKotlin;
import kotlin.UninitializedPropertyAccessException;

/* loaded from: classes5.dex */
public class Intrinsics {
    public static int compare(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    public static int compare(long j, long j2) {
        int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        if (i < 0) {
            return -1;
        }
        return i == 0 ? 0 : 1;
    }

    private Intrinsics() {
    }

    public static String stringPlus(String str, Object obj) {
        return str + obj;
    }

    public static void checkNotNull(Object obj) {
        if (obj == null) {
            throwJavaNpe();
        }
    }

    public static void checkNotNull(Object obj, String str) {
        if (obj == null) {
            throwJavaNpe(str);
        }
    }

    public static void throwNpe() {
        throw ((KotlinNullPointerException) a(new KotlinNullPointerException()));
    }

    public static void throwNpe(String str) {
        throw ((KotlinNullPointerException) a(new KotlinNullPointerException(str)));
    }

    @SinceKotlin(version = "1.4")
    public static void throwJavaNpe() {
        throw ((NullPointerException) a(new NullPointerException()));
    }

    @SinceKotlin(version = "1.4")
    public static void throwJavaNpe(String str) {
        throw ((NullPointerException) a(new NullPointerException(str)));
    }

    public static void throwUninitializedProperty(String str) {
        throw ((UninitializedPropertyAccessException) a(new UninitializedPropertyAccessException(str)));
    }

    public static void throwUninitializedPropertyAccessException(String str) {
        throwUninitializedProperty("lateinit property " + str + " has not been initialized");
    }

    public static void throwAssert() {
        throw ((AssertionError) a(new AssertionError()));
    }

    public static void throwAssert(String str) {
        throw ((AssertionError) a(new AssertionError(str)));
    }

    public static void throwIllegalArgument() {
        throw ((IllegalArgumentException) a(new IllegalArgumentException()));
    }

    public static void throwIllegalArgument(String str) {
        throw ((IllegalArgumentException) a(new IllegalArgumentException(str)));
    }

    public static void throwIllegalState() {
        throw ((IllegalStateException) a(new IllegalStateException()));
    }

    public static void throwIllegalState(String str) {
        throw ((IllegalStateException) a(new IllegalStateException(str)));
    }

    public static void checkExpressionValueIsNotNull(Object obj, String str) {
        if (obj == null) {
            throw ((IllegalStateException) a(new IllegalStateException(str + " must not be null")));
        }
    }

    public static void checkNotNullExpressionValue(Object obj, String str) {
        if (obj == null) {
            throw ((NullPointerException) a(new NullPointerException(str + " must not be null")));
        }
    }

    public static void checkReturnedValueIsNotNull(Object obj, String str, String str2) {
        if (obj == null) {
            throw ((IllegalStateException) a(new IllegalStateException("Method specified as non-null returned null: " + str + "." + str2)));
        }
    }

    public static void checkReturnedValueIsNotNull(Object obj, String str) {
        if (obj == null) {
            throw ((IllegalStateException) a(new IllegalStateException(str)));
        }
    }

    public static void checkFieldIsNotNull(Object obj, String str, String str2) {
        if (obj == null) {
            throw ((IllegalStateException) a(new IllegalStateException("Field specified as non-null is null: " + str + "." + str2)));
        }
    }

    public static void checkFieldIsNotNull(Object obj, String str) {
        if (obj == null) {
            throw ((IllegalStateException) a(new IllegalStateException(str)));
        }
    }

    public static void checkParameterIsNotNull(Object obj, String str) {
        if (obj == null) {
            a(str);
        }
    }

    public static void checkNotNullParameter(Object obj, String str) {
        if (obj == null) {
            b(str);
        }
    }

    private static void a(String str) {
        throw ((IllegalArgumentException) a(new IllegalArgumentException(c(str))));
    }

    private static void b(String str) {
        throw ((NullPointerException) a(new NullPointerException(c(str))));
    }

    private static String c(String str) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        return "Parameter specified as non-null is null: method " + className + "." + methodName + ", parameter " + str;
    }

    public static boolean areEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    @SinceKotlin(version = "1.1")
    public static boolean areEqual(Double d, Double d2) {
        if (d == null) {
            if (d2 == null) {
                return true;
            }
        } else if (d2 != null && d.doubleValue() == d2.doubleValue()) {
            return true;
        }
        return false;
    }

    @SinceKotlin(version = "1.1")
    public static boolean areEqual(Double d, double d2) {
        return d != null && d.doubleValue() == d2;
    }

    @SinceKotlin(version = "1.1")
    public static boolean areEqual(double d, Double d2) {
        return d2 != null && d == d2.doubleValue();
    }

    @SinceKotlin(version = "1.1")
    public static boolean areEqual(Float f, Float f2) {
        if (f == null) {
            if (f2 == null) {
                return true;
            }
        } else if (f2 != null && f.floatValue() == f2.floatValue()) {
            return true;
        }
        return false;
    }

    @SinceKotlin(version = "1.1")
    public static boolean areEqual(Float f, float f2) {
        return f != null && f.floatValue() == f2;
    }

    @SinceKotlin(version = "1.1")
    public static boolean areEqual(float f, Float f2) {
        return f2 != null && f == f2.floatValue();
    }

    public static void throwUndefinedForReified() {
        throwUndefinedForReified("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
    }

    public static void throwUndefinedForReified(String str) {
        throw new UnsupportedOperationException(str);
    }

    public static void reifiedOperationMarker(int i, String str) {
        throwUndefinedForReified();
    }

    public static void reifiedOperationMarker(int i, String str, String str2) {
        throwUndefinedForReified(str2);
    }

    public static void needClassReification() {
        throwUndefinedForReified();
    }

    public static void needClassReification(String str) {
        throwUndefinedForReified(str);
    }

    public static void checkHasClass(String str) throws ClassNotFoundException {
        String replace = str.replace(JsonPointer.SEPARATOR, '.');
        try {
            Class.forName(replace);
        } catch (ClassNotFoundException e) {
            throw ((ClassNotFoundException) a(new ClassNotFoundException("Class " + replace + " is not found. Please update the Kotlin runtime to the latest version", e)));
        }
    }

    public static void checkHasClass(String str, String str2) throws ClassNotFoundException {
        String replace = str.replace(JsonPointer.SEPARATOR, '.');
        try {
            Class.forName(replace);
        } catch (ClassNotFoundException e) {
            throw ((ClassNotFoundException) a(new ClassNotFoundException("Class " + replace + " is not found: this code requires the Kotlin runtime of version at least " + str2, e)));
        }
    }

    private static <T extends Throwable> T a(T t) {
        return (T) a(t, Intrinsics.class.getName());
    }

    public static <T extends Throwable> T a(T t, String str) {
        StackTraceElement[] stackTrace = t.getStackTrace();
        int length = stackTrace.length;
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.equals(stackTrace[i2].getClassName())) {
                i = i2;
            }
        }
        t.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i + 1, length));
        return t;
    }

    @SinceKotlin(version = "1.4")
    /* loaded from: classes5.dex */
    public static class Kotlin {
        private Kotlin() {
        }
    }
}
