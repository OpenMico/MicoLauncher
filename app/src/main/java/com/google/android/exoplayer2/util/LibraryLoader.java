package com.google.android.exoplayer2.util;

import java.util.Arrays;

/* loaded from: classes2.dex */
public final class LibraryLoader {
    private String[] a;
    private boolean b;
    private boolean c;

    public LibraryLoader(String... strArr) {
        this.a = strArr;
    }

    public synchronized void setLibraries(String... strArr) {
        Assertions.checkState(!this.b, "Cannot set libraries after loading");
        this.a = strArr;
    }

    public synchronized boolean isAvailable() {
        if (this.b) {
            return this.c;
        }
        this.b = true;
        try {
            for (String str : this.a) {
                System.loadLibrary(str);
            }
            this.c = true;
        } catch (UnsatisfiedLinkError unused) {
            String valueOf = String.valueOf(Arrays.toString(this.a));
            Log.w("LibraryLoader", valueOf.length() != 0 ? "Failed to load ".concat(valueOf) : new String("Failed to load "));
        }
        return this.c;
    }
}
