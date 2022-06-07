package com.xiaomi.ai.api.common;

/* loaded from: classes3.dex */
public class Context<T> extends Message<ContextHeader, T> {
    public Context() {
    }

    public Context(ContextHeader contextHeader, T t) {
        super(contextHeader, t);
    }
}
