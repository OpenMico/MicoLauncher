package com.xiaomi.mico.common;

import android.os.Build;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongSparseArray;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ContainerUtil {
    private static final ArrayList<Object> a = new ArrayList<Object>() { // from class: com.xiaomi.mico.common.ContainerUtil.1
        @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean add(Object obj) {
            throw new UnsupportedOperationException("ContainerUtil : Unsupported add operation on const empty list");
        }

        @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean addAll(Collection<?> collection) {
            throw new UnsupportedOperationException("ContainerUtil : Unsupported addAll operation on const empty list");
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public boolean addAll(int i, Collection<?> collection) {
            throw new UnsupportedOperationException("ContainerUtil : Unsupported addAll operation on const empty list");
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public void add(int i, Object obj) {
            throw new UnsupportedOperationException("ContainerUtil : Unsupported add by index operation on const empty list");
        }
    };

    /* loaded from: classes3.dex */
    public interface ElementComparator<T1, T2> {
        boolean isDifferent(T1 t1, T2 t2);
    }

    /* loaded from: classes3.dex */
    public enum SizeType {
        SIZE_LESS_THAN_1000,
        SIZE_NOT_LESS_THAN_1000,
        SIZE_UNKNOWN
    }

    private static int a() {
        return 19;
    }

    private static int b() {
        return 23;
    }

    private ContainerUtil() {
    }

    public static <T> ArrayList<T> getEmptyArrayList() {
        return (ArrayList<T>) a;
    }

    public static <T> List<T> getEmptyList() {
        return a;
    }

    public static <T> boolean hasData(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static <T> boolean hasData(LongSparseArray<T> longSparseArray) {
        return longSparseArray != null && longSparseArray.size() > 0;
    }

    public static <T> boolean hasData(SparseArray<T> sparseArray) {
        return sparseArray != null && sparseArray.size() > 0;
    }

    public static <T> boolean hasData(int[] iArr) {
        return iArr != null && iArr.length > 0;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return !hasData(collection);
    }

    public static <T> boolean isEmpty(SparseArray<T> sparseArray) {
        return !hasData(sparseArray);
    }

    public static <T> T getFirst(Collection<T> collection) {
        if (hasData(collection)) {
            return collection.iterator().next();
        }
        return null;
    }

    public static <T> long getFirst(long[] jArr, int i) {
        return hasData(jArr) ? jArr[0] : i;
    }

    public static <T> int getFirst(int[] iArr, int i) {
        return hasData(iArr) ? iArr[0] : i;
    }

    public static <T> boolean isEmpty(int[] iArr) {
        return !hasData(iArr);
    }

    public static <T> boolean isEmpty(long[] jArr) {
        return !hasData(jArr);
    }

    public static <T> boolean isEmpty(float[] fArr) {
        return !hasData(fArr);
    }

    public static boolean hasData(long[] jArr) {
        return jArr != null && jArr.length > 0;
    }

    public static <T> boolean hasData(float[] fArr) {
        return (fArr == null || fArr.length == 0) ? false : true;
    }

    public static <K, V> int getSize(Map<K, V> map) {
        if (hasData(map)) {
            return map.size();
        }
        return 0;
    }

    public static <T> int getSize(Collection<T> collection) {
        if (hasData(collection)) {
            return collection.size();
        }
        return 0;
    }

    public static <T> int getSize(T[] tArr) {
        if (hasData(tArr)) {
            return tArr.length;
        }
        return 0;
    }

    public static <T> int getSize(int[] iArr) {
        if (hasData(iArr)) {
            return iArr.length;
        }
        return 0;
    }

    public static <T> int getSize(long[] jArr) {
        if (hasData(jArr)) {
            return jArr.length;
        }
        return 0;
    }

    public static int getSize(boolean[] zArr) {
        if (hasData(zArr)) {
            return zArr.length;
        }
        return 0;
    }

    public static int getSize(byte[] bArr) {
        if (isEmpty(bArr)) {
            return 0;
        }
        return bArr.length;
    }

    public static int getSize(float[] fArr) {
        if (isEmpty(fArr)) {
            return 0;
        }
        return fArr.length;
    }

    public static <K, V> boolean hasData(Map<K, V> map) {
        return map != null && !map.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return !hasData(map);
    }

    public static <T> Collection<T> removeEmpties(Collection<T> collection) {
        if (isEmpty(collection)) {
            return collection;
        }
        ArrayList arrayList = new ArrayList();
        for (T t : collection) {
            if (t != null) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean hasData(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static <T> boolean isEmpty(T[] tArr) {
        return !hasData(tArr);
    }

    public static boolean isEmpty(boolean[] zArr) {
        return !hasData(zArr);
    }

    public static boolean isEmpty(byte[] bArr) {
        return !hasData(bArr);
    }

    public static <T> boolean hasData(T[] tArr) {
        return (tArr == null || tArr.length == 0) ? false : true;
    }

    public static boolean hasData(boolean[] zArr) {
        return (zArr == null || zArr.length == 0) ? false : true;
    }

    public static boolean hasData(byte[] bArr) {
        return (bArr == null || bArr.length == 0) ? false : true;
    }

    public static ArrayList<Float> toFloatArrayList(float[] fArr) {
        if (fArr == null) {
            return null;
        }
        ArrayList<Float> arrayList = new ArrayList<>();
        for (float f : fArr) {
            arrayList.add(Float.valueOf(f));
        }
        return arrayList;
    }

    public static <T> ArrayList<T> toArrayList(T[]... tArr) {
        if (tArr == null) {
            return null;
        }
        ArrayList<T> arrayList = new ArrayList<>();
        for (T[] tArr2 : tArr) {
            if (!isEmpty(tArr2)) {
                Collections.addAll(arrayList, tArr2);
            }
        }
        return arrayList;
    }

    public static <T> ArrayList<T> toArrayList(T[] tArr) {
        return toArrayList(tArr, null);
    }

    public static boolean hasData(JSONObject jSONObject) {
        return jSONObject != null && hasData(jSONObject.names());
    }

    public static boolean isEmpty(JSONObject jSONObject) {
        return !hasData(jSONObject);
    }

    public static boolean hasData(JSONArray jSONArray) {
        return (jSONArray == null || jSONArray.length() == 0) ? false : true;
    }

    public static boolean isEmpty(JSONArray jSONArray) {
        return !hasData(jSONArray);
    }

    public static <T> boolean isOutOfBound(long j, Collection<T> collection) {
        return j < 0 || j >= ((long) getSize(collection));
    }

    public static <T> boolean isOutOfBound(long j, T[] tArr) {
        return j < 0 || j >= ((long) getSize(tArr));
    }

    public static boolean isOutOfBound(long j, long[] jArr) {
        return j < 0 || j >= ((long) getSize(jArr));
    }

    public static boolean isOutOfBound(long j, int[] iArr) {
        return j < 0 || j >= ((long) getSize(iArr));
    }

    public static <T> int getSize(SparseArray<T> sparseArray) {
        if (sparseArray != null) {
            return sparseArray.size();
        }
        return 0;
    }

    public static <T1, T2> boolean isListDifferent(List<T1> list, List<T2> list2, ElementComparator<T1, T2> elementComparator) {
        int size = getSize(list);
        if (size != getSize(list2)) {
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (elementComparator.isDifferent(list.get(i), list2.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Integer> toArrayList(int[] iArr) {
        ArrayList<Integer> arrayList = new ArrayList<>(getSize(iArr));
        if (isEmpty(iArr)) {
            return arrayList;
        }
        for (int i : iArr) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public static ArrayList<Long> toArrayList(long[] jArr) {
        ArrayList<Long> arrayList = new ArrayList<>(getSize(jArr));
        if (isEmpty(jArr)) {
            return arrayList;
        }
        for (long j : jArr) {
            arrayList.add(Long.valueOf(j));
        }
        return arrayList;
    }

    public static <T> boolean contains(T[] tArr, T t) {
        if (!(tArr == null || t == null)) {
            for (T t2 : tArr) {
                if (t2.equals(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <K, V> Map<K, V> createMap(SizeType sizeType) {
        return a(sizeType, a()) ? new ArrayMap() : new HashMap();
    }

    public static <K, V> Map<K, V> createMapWithCapacity(int i) {
        int max = Math.max(1, i);
        return a(i, a()) ? new ArrayMap(max) : new HashMap(max);
    }

    public static <T> Set<T> createSet(SizeType sizeType) {
        return a(sizeType, b()) ? new ArraySet() : new HashSet();
    }

    public static <T> Set<T> createSet(int i) {
        return a(i, b()) ? new ArraySet() : new HashSet();
    }

    private static boolean a(int i, int i2) {
        return a(i < 1000 ? SizeType.SIZE_LESS_THAN_1000 : SizeType.SIZE_NOT_LESS_THAN_1000, i2);
    }

    private static boolean a(SizeType sizeType, int i) {
        return sizeType == SizeType.SIZE_LESS_THAN_1000 && Build.VERSION.SDK_INT >= i;
    }

    public static int getFirstPositive(int... iArr) {
        for (int i : iArr) {
            if (i != 0) {
                return i;
            }
        }
        return 0;
    }

    public static float getFirstNoLessThan(float f, float... fArr) {
        for (float f2 : fArr) {
            if (f2 >= f) {
                return f2;
            }
        }
        return f;
    }

    public static <T> List<T> shallowCopy(List<T> list) {
        if (list == null) {
            return new ArrayList();
        }
        return new ArrayList(list);
    }

    public static <T> List<T> shallowCopy(List<T> list, boolean z) {
        if (list == null) {
            return new ArrayList();
        }
        return z ? new LinkedList(list) : new ArrayList(list);
    }

    public static <T> boolean equal(List<T> list, List<T> list2) {
        int size = getSize(list);
        if (size != getSize(list2)) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            T t = list.get(i);
            T t2 = list2.get(i);
            if (t != null) {
                if (!t.equals(t2)) {
                    return false;
                }
            } else if (t2 != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(Object obj, Object obj2) {
        return (obj == null && obj2 == null) || !(obj == null || obj2 == null || !obj.equals(obj2));
    }
}
