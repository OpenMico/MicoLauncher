package com.xiaomi.mico.settingslib.utils;

/* loaded from: classes3.dex */
public abstract class LazyValue<Param, Value> {
    private volatile boolean a = false;
    private volatile Value b;

    protected abstract Value onInit(Param param);

    private synchronized void a(Param param) {
        if (!this.a) {
            this.b = onInit(param);
            this.a = true;
        }
    }

    public final Value get(Param param) {
        if (!this.a) {
            a(param);
        }
        return this.b;
    }

    public boolean hasResolved() {
        return this.a;
    }

    public synchronized void reset() {
        this.a = false;
    }
}
