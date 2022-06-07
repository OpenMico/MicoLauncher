package io.realm.internal;

import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public interface Capabilities {
    boolean canDeliverNotification();

    void checkCanDeliverNotification(@Nullable String str);

    boolean isMainThread();
}
