package com.fasterxml.jackson.databind.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class CompactStringObjectMap implements Serializable {
    private static final CompactStringObjectMap a = new CompactStringObjectMap(1, 0, new Object[4]);
    private static final long serialVersionUID = 1;
    private final Object[] _hashArea;
    private final int _hashMask;
    private final int _spillCount;

    private static final int a(int i) {
        if (i <= 5) {
            return 8;
        }
        if (i <= 12) {
            return 16;
        }
        int i2 = 32;
        while (i2 < i + (i >> 2)) {
            i2 += i2;
        }
        return i2;
    }

    private CompactStringObjectMap(int i, int i2, Object[] objArr) {
        this._hashMask = i;
        this._spillCount = i2;
        this._hashArea = objArr;
    }

    public static <T> CompactStringObjectMap construct(Map<String, T> map) {
        if (map.isEmpty()) {
            return a;
        }
        int a2 = a(map.size());
        int i = a2 - 1;
        int i2 = (a2 >> 1) + a2;
        Object[] objArr = new Object[i2 * 2];
        int i3 = 0;
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String key = entry.getKey();
            int hashCode = key.hashCode() & i;
            int i4 = hashCode + hashCode;
            if (objArr[i4] != null) {
                i4 = ((hashCode >> 1) + a2) << 1;
                if (objArr[i4] != null) {
                    i4 = (i2 << 1) + i3;
                    i3 += 2;
                    if (i4 >= objArr.length) {
                        objArr = Arrays.copyOf(objArr, objArr.length + 4);
                    }
                }
            }
            objArr[i4] = key;
            objArr[i4 + 1] = entry.getValue();
        }
        return new CompactStringObjectMap(i, i3, objArr);
    }

    public Object find(String str) {
        int hashCode = str.hashCode() & this._hashMask;
        int i = hashCode << 1;
        Object obj = this._hashArea[i];
        if (obj == str || str.equals(obj)) {
            return this._hashArea[i + 1];
        }
        return a(str, hashCode, obj);
    }

    private final Object a(String str, int i, Object obj) {
        if (obj == null) {
            return null;
        }
        int i2 = this._hashMask + 1;
        int i3 = ((i >> 1) + i2) << 1;
        Object obj2 = this._hashArea[i3];
        if (str.equals(obj2)) {
            return this._hashArea[i3 + 1];
        }
        if (obj2 != null) {
            int i4 = (i2 + (i2 >> 1)) << 1;
            int i5 = this._spillCount + i4;
            while (i4 < i5) {
                Object obj3 = this._hashArea[i4];
                if (obj3 == str || str.equals(obj3)) {
                    return this._hashArea[i4 + 1];
                }
                i4 += 2;
            }
        }
        return null;
    }

    public Object findCaseInsensitive(String str) {
        int length = this._hashArea.length;
        for (int i = 0; i < length; i += 2) {
            Object obj = this._hashArea[i];
            if (obj != null && ((String) obj).equalsIgnoreCase(str)) {
                return this._hashArea[i + 1];
            }
        }
        return null;
    }

    public List<String> keys() {
        int length = this._hashArea.length;
        ArrayList arrayList = new ArrayList(length >> 2);
        for (int i = 0; i < length; i += 2) {
            Object obj = this._hashArea[i];
            if (obj != null) {
                arrayList.add((String) obj);
            }
        }
        return arrayList;
    }
}
