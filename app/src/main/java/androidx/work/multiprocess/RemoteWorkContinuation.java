package androidx.work.multiprocess;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.OneTimeWorkRequest;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class RemoteWorkContinuation {
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected abstract RemoteWorkContinuation combineInternal(@NonNull List<RemoteWorkContinuation> list);

    @NonNull
    public abstract ListenableFuture<Void> enqueue();

    @NonNull
    public abstract RemoteWorkContinuation then(@NonNull List<OneTimeWorkRequest> list);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected RemoteWorkContinuation() {
    }

    @NonNull
    public final RemoteWorkContinuation then(@NonNull OneTimeWorkRequest oneTimeWorkRequest) {
        return then(Collections.singletonList(oneTimeWorkRequest));
    }

    @NonNull
    public static RemoteWorkContinuation combine(@NonNull List<RemoteWorkContinuation> list) {
        return list.get(0).combineInternal(list);
    }
}
