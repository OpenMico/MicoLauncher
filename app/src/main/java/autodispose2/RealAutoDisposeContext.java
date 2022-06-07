package autodispose2;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinExtensions.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0001\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0003H\u0016J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\tH\u0016J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\b0\n\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000bH\u0016J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\b0\f\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\rH\u0016J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\b0\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000fH\u0016J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\b0\u0010\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lautodispose2/RealAutoDisposeContext;", "Lautodispose2/AutoDisposeContext;", "scope", "Lio/reactivex/rxjava3/core/Completable;", "(Lio/reactivex/rxjava3/core/Completable;)V", "autoDispose", "Lautodispose2/CompletableSubscribeProxy;", "Lautodispose2/FlowableSubscribeProxy;", ExifInterface.GPS_DIRECTION_TRUE, "Lio/reactivex/rxjava3/core/Flowable;", "Lautodispose2/MaybeSubscribeProxy;", "Lio/reactivex/rxjava3/core/Maybe;", "Lautodispose2/ObservableSubscribeProxy;", "Lio/reactivex/rxjava3/core/Observable;", "Lautodispose2/SingleSubscribeProxy;", "Lio/reactivex/rxjava3/core/Single;", "Lautodispose2/ParallelFlowableSubscribeProxy;", "Lio/reactivex/rxjava3/parallel/ParallelFlowable;", "autodispose"}, k = 1, mv = {1, 1, 15})
@PublishedApi
/* loaded from: classes.dex */
public final class RealAutoDisposeContext implements AutoDisposeContext {
    private final Completable a;

    public RealAutoDisposeContext(@NotNull Completable scope) {
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        this.a = scope;
    }

    @Override // autodispose2.AutoDisposeContext
    @NotNull
    public <T> ParallelFlowableSubscribeProxy<T> autoDispose(@NotNull ParallelFlowable<T> autoDispose) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(this.a));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (ParallelFlowableSubscribeProxy) obj;
    }

    @Override // autodispose2.AutoDisposeContext
    @NotNull
    public <T> FlowableSubscribeProxy<T> autoDispose(@NotNull Flowable<T> autoDispose) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(this.a));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (FlowableSubscribeProxy) obj;
    }

    @Override // autodispose2.AutoDisposeContext
    @NotNull
    public <T> ObservableSubscribeProxy<T> autoDispose(@NotNull Observable<T> autoDispose) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(this.a));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (ObservableSubscribeProxy) obj;
    }

    @Override // autodispose2.AutoDisposeContext
    @NotNull
    public <T> SingleSubscribeProxy<T> autoDispose(@NotNull Single<T> autoDispose) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(this.a));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (SingleSubscribeProxy) obj;
    }

    @Override // autodispose2.AutoDisposeContext
    @NotNull
    public <T> MaybeSubscribeProxy<T> autoDispose(@NotNull Maybe<T> autoDispose) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(this.a));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (MaybeSubscribeProxy) obj;
    }

    @Override // autodispose2.AutoDisposeContext
    @NotNull
    public CompletableSubscribeProxy autoDispose(@NotNull Completable autoDispose) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(this.a));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable<Any>(scope))");
        return (CompletableSubscribeProxy) obj;
    }
}
