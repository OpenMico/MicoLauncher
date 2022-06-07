package org.apache.commons.lang3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class EnumUtils {
    public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> cls) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        E[] enumConstants = cls.getEnumConstants();
        for (E e : enumConstants) {
            linkedHashMap.put(e.name(), e);
        }
        return linkedHashMap;
    }

    public static <E extends Enum<E>> List<E> getEnumList(Class<E> cls) {
        return new ArrayList(Arrays.asList(cls.getEnumConstants()));
    }

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> cls, String str) {
        if (str == null) {
            return false;
        }
        try {
            Enum.valueOf(cls, str);
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public static <E extends Enum<E>> E getEnum(Class<E> cls, String str) {
        if (str == null) {
            return null;
        }
        try {
            return (E) Enum.valueOf(cls, str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public static <E extends Enum<E>> long generateBitVector(Class<E> cls, Iterable<? extends E> iterable) {
        a(cls);
        Validate.notNull(iterable);
        Iterator<? extends E> it = iterable.iterator();
        long j = 0;
        while (it.hasNext()) {
            Enum r6 = (Enum) it.next();
            Validate.isTrue(r6 != null, "null elements not permitted", new Object[0]);
            j |= 1 << r6.ordinal();
        }
        return j;
    }

    public static <E extends Enum<E>> long[] generateBitVectors(Class<E> cls, Iterable<? extends E> iterable) {
        b(cls);
        Validate.notNull(iterable);
        EnumSet noneOf = EnumSet.noneOf(cls);
        Iterator<? extends E> it = iterable.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Enum r1 = (Enum) it.next();
            if (r1 == null) {
                z = false;
            }
            Validate.isTrue(z, "null elements not permitted", new Object[0]);
            noneOf.add(r1);
        }
        long[] jArr = new long[((cls.getEnumConstants().length - 1) / 64) + 1];
        Iterator it2 = noneOf.iterator();
        while (it2.hasNext()) {
            Enum r0 = (Enum) it2.next();
            int ordinal = r0.ordinal() / 64;
            jArr[ordinal] = jArr[ordinal] | (1 << (r0.ordinal() % 64));
        }
        ArrayUtils.reverse(jArr);
        return jArr;
    }

    public static <E extends Enum<E>> long generateBitVector(Class<E> cls, E... eArr) {
        Validate.noNullElements(eArr);
        return generateBitVector(cls, Arrays.asList(eArr));
    }

    public static <E extends Enum<E>> long[] generateBitVectors(Class<E> cls, E... eArr) {
        b(cls);
        Validate.noNullElements(eArr);
        EnumSet noneOf = EnumSet.noneOf(cls);
        Collections.addAll(noneOf, eArr);
        long[] jArr = new long[((cls.getEnumConstants().length - 1) / 64) + 1];
        Iterator it = noneOf.iterator();
        while (it.hasNext()) {
            Enum r0 = (Enum) it.next();
            int ordinal = r0.ordinal() / 64;
            jArr[ordinal] = jArr[ordinal] | (1 << (r0.ordinal() % 64));
        }
        ArrayUtils.reverse(jArr);
        return jArr;
    }

    public static <E extends Enum<E>> EnumSet<E> processBitVector(Class<E> cls, long j) {
        a(cls).getEnumConstants();
        return processBitVectors(cls, j);
    }

    public static <E extends Enum<E>> EnumSet<E> processBitVectors(Class<E> cls, long... jArr) {
        EnumSet<E> noneOf = EnumSet.noneOf(b(cls));
        long[] clone = ArrayUtils.clone((long[]) Validate.notNull(jArr));
        ArrayUtils.reverse(clone);
        E[] enumConstants = cls.getEnumConstants();
        for (E e : enumConstants) {
            int ordinal = e.ordinal() / 64;
            if (ordinal < clone.length && (clone[ordinal] & (1 << (e.ordinal() % 64))) != 0) {
                noneOf.add(e);
            }
        }
        return noneOf;
    }

    private static <E extends Enum<E>> Class<E> a(Class<E> cls) {
        Enum[] enumArr = (Enum[]) b(cls).getEnumConstants();
        Validate.isTrue(enumArr.length <= 64, "Cannot store %s %s values in %s bits", Integer.valueOf(enumArr.length), cls.getSimpleName(), 64);
        return cls;
    }

    private static <E extends Enum<E>> Class<E> b(Class<E> cls) {
        Validate.notNull(cls, "EnumClass must be defined.", new Object[0]);
        Validate.isTrue(cls.isEnum(), "%s does not seem to be an Enum type", cls);
        return cls;
    }
}
