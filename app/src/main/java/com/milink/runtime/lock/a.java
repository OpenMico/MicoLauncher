package com.milink.runtime.lock;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.milink.base.utils.ObservableFutureImpl;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: ReplyHelper.java */
/* loaded from: classes2.dex */
class a {
    private final AtomicLong a = new AtomicLong();
    private final int b = new Random().nextInt(100);
    private final Map<String, ObservableFutureImpl<Bundle>> c = Collections.synchronizedMap(new ArrayMap());

    /* JADX INFO: Access modifiers changed from: package-private */
    public String a() {
        String str = this.b + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this.a.addAndGet((((int) SystemClock.uptimeMillis()) & 4) + 1);
        this.c.put(str, new ObservableFutureImpl<>());
        return str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public Bundle a(@NonNull String str, int i, @NonNull TimeUnit timeUnit) throws TimeoutException, InterruptedException {
        ObservableFutureImpl<Bundle> observableFutureImpl = this.c.get(Objects.requireNonNull(str));
        if (observableFutureImpl != null) {
            this.c.remove(str);
            return (Bundle) Objects.requireNonNull(observableFutureImpl.get(i, (TimeUnit) Objects.requireNonNull(timeUnit)));
        }
        throw new IllegalStateException("not found reply future.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(String str, @NonNull Bundle bundle) {
        ObservableFutureImpl<Bundle> observableFutureImpl = this.c.get(Objects.requireNonNull(str));
        if (observableFutureImpl == null) {
            return false;
        }
        observableFutureImpl.setData((Bundle) Objects.requireNonNull(bundle));
        return true;
    }
}
