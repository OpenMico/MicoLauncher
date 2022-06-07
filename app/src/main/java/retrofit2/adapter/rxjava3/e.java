package retrofit2.adapter.rxjava3;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import retrofit2.Call;
import retrofit2.CallAdapter;

/* compiled from: RxJava3CallAdapter.java */
/* loaded from: classes6.dex */
final class e<R> implements CallAdapter<R, Object> {
    private final Type a;
    @Nullable
    private final Scheduler b;
    private final boolean c;
    private final boolean d;
    private final boolean e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    private final boolean i;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Type type, @Nullable Scheduler scheduler, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        this.a = type;
        this.b = scheduler;
        this.c = z;
        this.d = z2;
        this.e = z3;
        this.f = z4;
        this.g = z5;
        this.h = z6;
        this.i = z7;
    }

    @Override // retrofit2.CallAdapter
    public Type responseType() {
        return this.a;
    }

    @Override // retrofit2.CallAdapter
    public Object adapt(Call<R> call) {
        Observable observable;
        Observable bVar = this.c ? new b(call) : new c(call);
        if (this.d) {
            observable = new d(bVar);
        } else {
            observable = this.e ? new a(bVar) : bVar;
        }
        Scheduler scheduler = this.b;
        if (scheduler != null) {
            observable = observable.subscribeOn(scheduler);
        }
        if (this.f) {
            return observable.toFlowable(BackpressureStrategy.LATEST);
        }
        if (this.g) {
            return observable.singleOrError();
        }
        if (this.h) {
            return observable.singleElement();
        }
        if (this.i) {
            return observable.ignoreElements();
        }
        return RxJavaPlugins.onAssembly(observable);
    }
}
