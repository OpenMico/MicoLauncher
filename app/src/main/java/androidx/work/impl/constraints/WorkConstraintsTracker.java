package androidx.work.impl.constraints;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Logger;
import androidx.work.impl.constraints.controllers.BatteryChargingController;
import androidx.work.impl.constraints.controllers.BatteryNotLowController;
import androidx.work.impl.constraints.controllers.ConstraintController;
import androidx.work.impl.constraints.controllers.NetworkConnectedController;
import androidx.work.impl.constraints.controllers.NetworkMeteredController;
import androidx.work.impl.constraints.controllers.NetworkNotRoamingController;
import androidx.work.impl.constraints.controllers.NetworkUnmeteredController;
import androidx.work.impl.constraints.controllers.StorageNotLowController;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class WorkConstraintsTracker implements ConstraintController.OnConstraintUpdatedCallback {
    private static final String a = Logger.tagWithPrefix("WorkConstraintsTracker");
    @Nullable
    private final WorkConstraintsCallback b;
    private final ConstraintController<?>[] c;
    private final Object d = new Object();

    public WorkConstraintsTracker(@NonNull Context context, @NonNull TaskExecutor taskExecutor, @Nullable WorkConstraintsCallback workConstraintsCallback) {
        Context applicationContext = context.getApplicationContext();
        this.b = workConstraintsCallback;
        this.c = new ConstraintController[]{new BatteryChargingController(applicationContext, taskExecutor), new BatteryNotLowController(applicationContext, taskExecutor), new StorageNotLowController(applicationContext, taskExecutor), new NetworkConnectedController(applicationContext, taskExecutor), new NetworkUnmeteredController(applicationContext, taskExecutor), new NetworkNotRoamingController(applicationContext, taskExecutor), new NetworkMeteredController(applicationContext, taskExecutor)};
    }

    public void replace(@NonNull Iterable<WorkSpec> iterable) {
        synchronized (this.d) {
            for (ConstraintController<?> constraintController : this.c) {
                constraintController.setCallback(null);
            }
            for (ConstraintController<?> constraintController2 : this.c) {
                constraintController2.replace(iterable);
            }
            for (ConstraintController<?> constraintController3 : this.c) {
                constraintController3.setCallback(this);
            }
        }
    }

    public void reset() {
        synchronized (this.d) {
            for (ConstraintController<?> constraintController : this.c) {
                constraintController.reset();
            }
        }
    }

    public boolean areAllConstraintsMet(@NonNull String str) {
        synchronized (this.d) {
            ConstraintController<?>[] constraintControllerArr = this.c;
            for (ConstraintController<?> constraintController : constraintControllerArr) {
                if (constraintController.isWorkSpecConstrained(str)) {
                    Logger.get().debug(a, String.format("Work %s constrained by %s", str, constraintController.getClass().getSimpleName()), new Throwable[0]);
                    return false;
                }
            }
            return true;
        }
    }

    @Override // androidx.work.impl.constraints.controllers.ConstraintController.OnConstraintUpdatedCallback
    public void onConstraintMet(@NonNull List<String> list) {
        synchronized (this.d) {
            ArrayList arrayList = new ArrayList();
            for (String str : list) {
                if (areAllConstraintsMet(str)) {
                    Logger.get().debug(a, String.format("Constraints met for %s", str), new Throwable[0]);
                    arrayList.add(str);
                }
            }
            if (this.b != null) {
                this.b.onAllConstraintsMet(arrayList);
            }
        }
    }

    @Override // androidx.work.impl.constraints.controllers.ConstraintController.OnConstraintUpdatedCallback
    public void onConstraintNotMet(@NonNull List<String> list) {
        synchronized (this.d) {
            if (this.b != null) {
                this.b.onAllConstraintsNotMet(list);
            }
        }
    }
}
