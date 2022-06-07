package androidx.work.impl.diagnostics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.impl.workers.DiagnosticsWorker;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class DiagnosticsReceiver extends BroadcastReceiver {
    private static final String a = Logger.tagWithPrefix("DiagnosticsRcvr");

    @Override // android.content.BroadcastReceiver
    public void onReceive(@NonNull Context context, @Nullable Intent intent) {
        if (intent != null) {
            Logger.get().debug(a, "Requesting diagnostics", new Throwable[0]);
            try {
                WorkManager.getInstance(context).enqueue(OneTimeWorkRequest.from(DiagnosticsWorker.class));
            } catch (IllegalStateException e) {
                Logger.get().error(a, "WorkManager is not initialized", e);
            }
        }
    }
}
