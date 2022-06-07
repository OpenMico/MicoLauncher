package com.milink.kit.lock;

/* loaded from: classes2.dex */
public interface LockHolder {
    String identify();

    String tag();

    default boolean isNoneHolder() {
        return "".equals(tag()) && "".equals(identify());
    }
}
