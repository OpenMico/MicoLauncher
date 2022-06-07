package androidx.work.impl.constraints.trackers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.impl.constraints.ConstraintListener;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class ConstraintTracker<T> {
    private static final String b = Logger.tagWithPrefix("ConstraintTracker");
    T a;
    private final Object c = new Object();
    private final Set<ConstraintListener<T>> d = new LinkedHashSet();
    protected final Context mAppContext;
    protected final TaskExecutor mTaskExecutor;

    public abstract T getInitialState();

    public abstract void startTracking();

    public abstract void stopTracking();

    public ConstraintTracker(@NonNull Context context, @NonNull TaskExecutor taskExecutor) {
        this.mAppContext = context.getApplicationContext();
        this.mTaskExecutor = taskExecutor;
    }

    public void addListener(ConstraintListener<T> constraintListener) {
        synchronized (this.c) {
            if (this.d.add(constraintListener)) {
                if (this.d.size() == 1) {
                    this.a = getInitialState();
                    Logger.get().debug(b, String.format("%s: initial state = %s", getClass().getSimpleName(), this.a), new Throwable[0]);
                    startTracking();
                }
                constraintListener.onConstraintChanged(this.a);
            }
        }
    }

    public void removeListener(ConstraintListener<T> constraintListener) {
        synchronized (this.c) {
            if (this.d.remove(constraintListener) && this.d.isEmpty()) {
                stopTracking();
            }
        }
    }

    public void setState(T t) {
        synchronized (this.c) {
            if (this.a != t && (this.a == null || !this.a.equals(t))) {
                this.a = t;
                final ArrayList arrayList = new ArrayList(this.d);
                this.mTaskExecutor.getMainThreadExecutor().execute(new Runnable() { // from class: androidx.work.impl.constraints.trackers.ConstraintTracker.1
                    @Override // java.lang.Runnable
                    public void run() {
                        for (ConstraintListener constraintListener : arrayList) {
                            constraintListener.onConstraintChanged(ConstraintTracker.this.a);
                        }
                    }
                });
            }
        }
    }
}
