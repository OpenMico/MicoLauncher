package androidx.work;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.startup.Initializer;
import androidx.work.Configuration;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class WorkManagerInitializer implements Initializer<WorkManager> {
    private static final String a = Logger.tagWithPrefix("WrkMgrInitializer");

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.startup.Initializer
    @NonNull
    public WorkManager create(@NonNull Context context) {
        Logger.get().debug(a, "Initializing WorkManager with default configuration.", new Throwable[0]);
        WorkManager.initialize(context, new Configuration.Builder().build());
        return WorkManager.getInstance(context);
    }

    @Override // androidx.startup.Initializer
    @NonNull
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
