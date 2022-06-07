package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Map;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class ArrayBasedEscaperMap {
    private static final char[][] b = (char[][]) Array.newInstance(char.class, 0, 0);
    private final char[][] a;

    public static ArrayBasedEscaperMap create(Map<Character, String> map) {
        return new ArrayBasedEscaperMap(a(map));
    }

    private ArrayBasedEscaperMap(char[][] cArr) {
        this.a = cArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public char[][] a() {
        return this.a;
    }

    @VisibleForTesting
    static char[][] a(Map<Character, String> map) {
        Preconditions.checkNotNull(map);
        if (map.isEmpty()) {
            return b;
        }
        char[][] cArr = new char[((Character) Collections.max(map.keySet())).charValue() + 1];
        for (Character ch : map.keySet()) {
            char charValue = ch.charValue();
            cArr[charValue] = map.get(Character.valueOf(charValue)).toCharArray();
        }
        return cArr;
    }
}
