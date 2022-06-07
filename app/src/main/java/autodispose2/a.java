package autodispose2;

import io.reactivex.rxjava3.annotations.Nullable;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AtomicThrowable.java */
/* loaded from: classes.dex */
final class a extends AtomicReference<Throwable> {
    private static final long serialVersionUID = 3949248817947090603L;

    public boolean a(Throwable th) {
        return r.a(this, th);
    }

    @Nullable
    public Throwable a() {
        return r.a(this);
    }
}
