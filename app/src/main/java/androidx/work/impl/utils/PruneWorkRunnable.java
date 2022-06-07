package androidx.work.impl.utils;

import androidx.annotation.RestrictTo;
import androidx.work.Operation;
import androidx.work.impl.OperationImpl;
import androidx.work.impl.WorkManagerImpl;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class PruneWorkRunnable implements Runnable {
    private final WorkManagerImpl a;
    private final OperationImpl b = new OperationImpl();

    public PruneWorkRunnable(WorkManagerImpl workManagerImpl) {
        this.a = workManagerImpl;
    }

    public Operation getOperation() {
        return this.b;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.getWorkDatabase().workSpecDao().pruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast();
            this.b.setState(Operation.SUCCESS);
        } catch (Throwable th) {
            this.b.setState(new Operation.State.FAILURE(th));
        }
    }
}
