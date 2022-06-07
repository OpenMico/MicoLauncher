package io.realm.internal.android;

import android.os.Looper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.realm.internal.Capabilities;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class AndroidCapabilities implements Capabilities {
    @SuppressFBWarnings({"MS_CANNOT_BE_FINAL"})
    public static boolean EMULATE_MAIN_THREAD = false;
    private final Looper a = Looper.myLooper();
    private final boolean b = b();

    @Override // io.realm.internal.Capabilities
    public boolean canDeliverNotification() {
        return a() && !this.b;
    }

    @Override // io.realm.internal.Capabilities
    public void checkCanDeliverNotification(@Nullable String str) {
        String str2;
        String str3;
        if (!a()) {
            if (str == null) {
                str3 = "";
            } else {
                str3 = str + StringUtils.SPACE + "Realm cannot be automatically updated on a thread without a looper.";
            }
            throw new IllegalStateException(str3);
        } else if (this.b) {
            if (str == null) {
                str2 = "";
            } else {
                str2 = str + StringUtils.SPACE + "Realm cannot be automatically updated on an IntentService thread.";
            }
            throw new IllegalStateException(str2);
        }
    }

    @Override // io.realm.internal.Capabilities
    public boolean isMainThread() {
        Looper looper = this.a;
        return looper != null && (EMULATE_MAIN_THREAD || looper == Looper.getMainLooper());
    }

    private boolean a() {
        return this.a != null;
    }

    private static boolean b() {
        String name = Thread.currentThread().getName();
        return name != null && name.startsWith("IntentService[");
    }
}
