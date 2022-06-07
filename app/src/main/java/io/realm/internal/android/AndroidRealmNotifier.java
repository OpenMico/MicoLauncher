package io.realm.internal.android;

import android.os.Handler;
import android.os.Looper;
import io.realm.internal.Capabilities;
import io.realm.internal.Keep;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmNotifier;
import javax.annotation.Nullable;

@Keep
/* loaded from: classes5.dex */
public class AndroidRealmNotifier extends RealmNotifier {
    private Handler handler;

    public AndroidRealmNotifier(@Nullable OsSharedRealm osSharedRealm, Capabilities capabilities) {
        super(osSharedRealm);
        if (capabilities.canDeliverNotification()) {
            this.handler = new Handler(Looper.myLooper());
        } else {
            this.handler = null;
        }
    }

    @Override // io.realm.internal.RealmNotifier
    public boolean post(Runnable runnable) {
        Handler handler = this.handler;
        return handler != null && handler.post(runnable);
    }
}
