package androidx.work.impl.constraints.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.impl.constraints.ConstraintListener;
import androidx.work.impl.constraints.trackers.ConstraintTracker;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class ConstraintController<T> implements ConstraintListener<T> {
    private final List<String> a = new ArrayList();
    private T b;
    private ConstraintTracker<T> c;
    private OnConstraintUpdatedCallback d;

    /* loaded from: classes.dex */
    public interface OnConstraintUpdatedCallback {
        void onConstraintMet(@NonNull List<String> list);

        void onConstraintNotMet(@NonNull List<String> list);
    }

    abstract boolean a(@NonNull WorkSpec workSpec);

    abstract boolean a(@NonNull T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConstraintController(ConstraintTracker<T> constraintTracker) {
        this.c = constraintTracker;
    }

    public void setCallback(@Nullable OnConstraintUpdatedCallback onConstraintUpdatedCallback) {
        if (this.d != onConstraintUpdatedCallback) {
            this.d = onConstraintUpdatedCallback;
            a(this.d, this.b);
        }
    }

    public void replace(@NonNull Iterable<WorkSpec> iterable) {
        this.a.clear();
        for (WorkSpec workSpec : iterable) {
            if (a(workSpec)) {
                this.a.add(workSpec.id);
            }
        }
        if (this.a.isEmpty()) {
            this.c.removeListener(this);
        } else {
            this.c.addListener(this);
        }
        a(this.d, this.b);
    }

    public void reset() {
        if (!this.a.isEmpty()) {
            this.a.clear();
            this.c.removeListener(this);
        }
    }

    public boolean isWorkSpecConstrained(@NonNull String str) {
        T t = this.b;
        return t != null && a((ConstraintController<T>) t) && this.a.contains(str);
    }

    private void a(@Nullable OnConstraintUpdatedCallback onConstraintUpdatedCallback, @Nullable T t) {
        if (!this.a.isEmpty() && onConstraintUpdatedCallback != null) {
            if (t == null || a((ConstraintController<T>) t)) {
                onConstraintUpdatedCallback.onConstraintNotMet(this.a);
            } else {
                onConstraintUpdatedCallback.onConstraintMet(this.a);
            }
        }
    }

    @Override // androidx.work.impl.constraints.ConstraintListener
    public void onConstraintChanged(@Nullable T t) {
        this.b = t;
        a(this.d, this.b);
    }
}
