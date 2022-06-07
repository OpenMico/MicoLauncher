package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.List;

/* compiled from: ApplicationProtocolUtil.java */
/* loaded from: classes4.dex */
final class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<String> a(Iterable<String> iterable) {
        return a(2, iterable);
    }

    static List<String> a(int i, Iterable<String> iterable) {
        if (iterable == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(i);
        for (String str : iterable) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("protocol cannot be null or empty");
            }
            arrayList.add(str);
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        throw new IllegalArgumentException("protocols cannot empty");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<String> a(String... strArr) {
        return a(2, strArr);
    }

    static List<String> a(int i, String... strArr) {
        if (strArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(i);
        for (String str : strArr) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("protocol cannot be null or empty");
            }
            arrayList.add(str);
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        throw new IllegalArgumentException("protocols cannot empty");
    }
}
