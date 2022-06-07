package miuix.arch.component;

import androidx.annotation.NonNull;
import java.util.Objects;

/* loaded from: classes5.dex */
public abstract class AppComponentDelegate<T> {
    private volatile T a;

    /* JADX INFO: Access modifiers changed from: protected */
    @NonNull
    public abstract Object callTask(Object obj, String str, Object obj2) throws Exception;

    @NonNull
    protected abstract T createAppComponent();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void performTask(Object obj, int i) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    @NonNull
    public final T getSingleton() {
        T t;
        if (this.a != null) {
            return this.a;
        }
        synchronized (this) {
            if (this.a == null) {
                this.a = (T) Objects.requireNonNull(createAppComponent());
            }
            t = this.a;
        }
        return t;
    }
}
