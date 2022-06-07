package io.realm.log;

import io.realm.internal.Keep;
import javax.annotation.Nullable;

@Keep
/* loaded from: classes5.dex */
public interface RealmLogger {
    void log(int i, String str, @Nullable Throwable th, @Nullable String str2);
}
