package androidx.activity.contextaware;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes.dex */
public final class ContextAwareHelper {
    private final Set<OnContextAvailableListener> a = new CopyOnWriteArraySet();
    private volatile Context b;

    @Nullable
    public Context peekAvailableContext() {
        return this.b;
    }

    public void addOnContextAvailableListener(@NonNull OnContextAvailableListener onContextAvailableListener) {
        if (this.b != null) {
            onContextAvailableListener.onContextAvailable(this.b);
        }
        this.a.add(onContextAvailableListener);
    }

    public void removeOnContextAvailableListener(@NonNull OnContextAvailableListener onContextAvailableListener) {
        this.a.remove(onContextAvailableListener);
    }

    public void dispatchOnContextAvailable(@NonNull Context context) {
        this.b = context;
        for (OnContextAvailableListener onContextAvailableListener : this.a) {
            onContextAvailableListener.onContextAvailable(context);
        }
    }

    public void clearAvailableContext() {
        this.b = null;
    }
}
