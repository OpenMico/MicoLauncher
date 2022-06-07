package androidx.work.impl.constraints.controllers;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.work.NetworkType;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

/* loaded from: classes.dex */
public class NetworkUnmeteredController extends ConstraintController<NetworkState> {
    public NetworkUnmeteredController(@NonNull Context context, @NonNull TaskExecutor taskExecutor) {
        super(Trackers.getInstance(context, taskExecutor).getNetworkStateTracker());
    }

    @Override // androidx.work.impl.constraints.controllers.ConstraintController
    boolean a(@NonNull WorkSpec workSpec) {
        return workSpec.constraints.getRequiredNetworkType() == NetworkType.UNMETERED || (Build.VERSION.SDK_INT >= 30 && workSpec.constraints.getRequiredNetworkType() == NetworkType.TEMPORARILY_UNMETERED);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(@NonNull NetworkState networkState) {
        return !networkState.isConnected() || networkState.isMetered();
    }
}
