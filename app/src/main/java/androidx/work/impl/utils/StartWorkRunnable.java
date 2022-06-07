package androidx.work.impl.utils;

import androidx.annotation.RestrictTo;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkManagerImpl;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class StartWorkRunnable implements Runnable {
    private WorkManagerImpl a;
    private String b;
    private WorkerParameters.RuntimeExtras c;

    public StartWorkRunnable(WorkManagerImpl workManagerImpl, String str, WorkerParameters.RuntimeExtras runtimeExtras) {
        this.a = workManagerImpl;
        this.b = str;
        this.c = runtimeExtras;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.getProcessor().startWork(this.b, this.c);
    }
}
