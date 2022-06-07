package androidx.work;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

/* loaded from: classes.dex */
public interface ProgressUpdater {
    @NonNull
    ListenableFuture<Void> updateProgress(@NonNull Context context, @NonNull UUID uuid, @NonNull Data data);
}
