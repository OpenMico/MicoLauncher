package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: Platform.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
final class p {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(@NullableDecl Throwable th, Class<? extends Throwable> cls) {
        return cls.isInstance(th);
    }
}
