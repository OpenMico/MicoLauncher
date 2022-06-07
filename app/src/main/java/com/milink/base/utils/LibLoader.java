package com.milink.base.utils;

import androidx.annotation.NonNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public class LibLoader {
    private static final Set<String> a = new HashSet();

    private LibLoader() {
    }

    public static void loadLibrary(@NonNull String str) {
        synchronized (a) {
            if (!a.contains(Objects.requireNonNull(str))) {
                System.loadLibrary(str);
                a.add(str);
            }
        }
    }
}
