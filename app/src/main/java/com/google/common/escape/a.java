package com.google.common.escape;

import com.google.common.annotations.GwtCompatible;

/* compiled from: Platform.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
final class a {
    private static final ThreadLocal<char[]> a = new ThreadLocal<char[]>() { // from class: com.google.common.escape.a.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public char[] initialValue() {
            return new char[1024];
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static char[] a() {
        return a.get();
    }
}
