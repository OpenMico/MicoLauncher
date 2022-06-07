package org.checkerframework.checker.nullness;

import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes5.dex */
public final class NullnessUtils {
    static final /* synthetic */ boolean a = !NullnessUtils.class.desiredAssertionStatus();

    private NullnessUtils() {
        throw new AssertionError("shouldn't be instantiated");
    }

    @EnsuresNonNull({"#1"})
    public static <T> T castNonNull(T t) {
        if (a || t != null) {
            return t;
        }
        throw new AssertionError("Misuse of castNonNull: called with a null argument");
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[] castNonNullDeep(T[] tArr) {
        return (T[]) a((Object[]) tArr);
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[][] castNonNullDeep(T[][] tArr) {
        return (T[][]) ((Object[][]) a((Object[]) tArr));
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[][][] castNonNullDeep(T[][][] tArr) {
        return (T[][][]) ((Object[][][]) a((Object[]) tArr));
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[][][][] castNonNullDeep(T[][][][] tArr) {
        return (T[][][][]) ((Object[][][][]) a((Object[]) tArr));
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[][][][][] castNonNullDeep(T[][][][][] tArr) {
        return (T[][][][][]) ((Object[][][][][]) a((Object[]) tArr));
    }

    private static <T> T[] a(T[] tArr) {
        if (a || tArr != null) {
            for (int i = 0; i < tArr.length; i++) {
                if (a || tArr[i] != null) {
                    a(tArr[i]);
                } else {
                    throw new AssertionError("Misuse of castNonNull: called with a null array element");
                }
            }
            return tArr;
        }
        throw new AssertionError("Misuse of castNonNullArray: called with a null array argument");
    }

    private static void a(Object obj) {
        if (a || obj != null) {
            Class<?> componentType = obj.getClass().getComponentType();
            if (componentType != null && !componentType.isPrimitive()) {
                a((Object[]) obj);
                return;
            }
            return;
        }
        throw new AssertionError("Misuse of checkIfArray: called with a null argument");
    }
}
