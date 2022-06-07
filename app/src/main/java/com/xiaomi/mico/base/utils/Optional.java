package com.xiaomi.mico.base.utils;

import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public class Optional<M> {
    private final M a;

    public Optional(@Nullable M m) {
        this.a = m;
    }

    public boolean isEmpty() {
        return this.a == null;
    }

    public M get() {
        M m = this.a;
        if (m != null) {
            return m;
        }
        throw new NoSuchElementException("No value present");
    }
}
