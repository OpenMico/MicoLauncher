package autodispose2;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.c;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinExtensions.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\b\u0007H\u0086\b\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\b\u0007H\u0086\b\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\t2\u0006\u0010\f\u001a\u00020\u0003H\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\t2\u0006\u0010\u0002\u001a\u00020\tH\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u000f2\u0006\u0010\f\u001a\u00020\u0003H\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u000f2\u0006\u0010\u0002\u001a\u00020\tH\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u00112\u0006\u0010\f\u001a\u00020\u0003H\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u00112\u0006\u0010\u0002\u001a\u00020\tH\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u00132\u0006\u0010\f\u001a\u00020\u0003H\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u00132\u0006\u0010\u0002\u001a\u00020\tH\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0014\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u00152\u0006\u0010\f\u001a\u00020\u0003H\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0014\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u00152\u0006\u0010\u0002\u001a\u00020\tH\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0016\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u00172\u0006\u0010\f\u001a\u00020\u0003H\u0087\b\u001a'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0016\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u00172\u0006\u0010\u0002\u001a\u00020\tH\u0087\b¨\u0006\u0018"}, d2 = {"withScope", "", "scope", "Lautodispose2/ScopeProvider;", "body", "Lkotlin/Function1;", "Lautodispose2/AutoDisposeContext;", "Lkotlin/ExtensionFunctionType;", "completableScope", "Lio/reactivex/rxjava3/core/Completable;", "autoDispose", "Lautodispose2/CompletableSubscribeProxy;", c.M, "Lautodispose2/FlowableSubscribeProxy;", ExifInterface.GPS_DIRECTION_TRUE, "Lio/reactivex/rxjava3/core/Flowable;", "Lautodispose2/MaybeSubscribeProxy;", "Lio/reactivex/rxjava3/core/Maybe;", "Lautodispose2/ObservableSubscribeProxy;", "Lio/reactivex/rxjava3/core/Observable;", "Lautodispose2/SingleSubscribeProxy;", "Lio/reactivex/rxjava3/core/Single;", "Lautodispose2/ParallelFlowableSubscribeProxy;", "Lio/reactivex/rxjava3/parallel/ParallelFlowable;", "autodispose"}, k = 2, mv = {1, 1, 15})
@JvmName(name = "KotlinExtensions")
/* loaded from: classes.dex */
public final class KotlinExtensions {
    @CheckReturnValue
    @NotNull
    public static final <T> FlowableSubscribeProxy<T> autoDispose(@NotNull Flowable<T> autoDispose, @NotNull Completable scope) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(scope));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (FlowableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> ObservableSubscribeProxy<T> autoDispose(@NotNull Observable<T> autoDispose, @NotNull Completable scope) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(scope));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (ObservableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> SingleSubscribeProxy<T> autoDispose(@NotNull Single<T> autoDispose, @NotNull Completable scope) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(scope));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (SingleSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> MaybeSubscribeProxy<T> autoDispose(@NotNull Maybe<T> autoDispose, @NotNull Completable scope) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(scope));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (MaybeSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final CompletableSubscribeProxy autoDispose(@NotNull Completable autoDispose, @NotNull Completable scope) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(scope));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable<Any>(scope))");
        return (CompletableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> ParallelFlowableSubscribeProxy<T> autoDispose(@NotNull ParallelFlowable<T> autoDispose, @NotNull Completable scope) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(scope));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(scope))");
        return (ParallelFlowableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> FlowableSubscribeProxy<T> autoDispose(@NotNull Flowable<T> autoDispose, @NotNull ScopeProvider provider) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(provider));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(provider))");
        return (FlowableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> ObservableSubscribeProxy<T> autoDispose(@NotNull Observable<T> autoDispose, @NotNull ScopeProvider provider) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(provider));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(provider))");
        return (ObservableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> SingleSubscribeProxy<T> autoDispose(@NotNull Single<T> autoDispose, @NotNull ScopeProvider provider) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(provider));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(provider))");
        return (SingleSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> MaybeSubscribeProxy<T> autoDispose(@NotNull Maybe<T> autoDispose, @NotNull ScopeProvider provider) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(provider));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(provider))");
        return (MaybeSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final CompletableSubscribeProxy autoDispose(@NotNull Completable autoDispose, @NotNull ScopeProvider provider) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(provider));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable<Any>(provider))");
        return (CompletableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> ParallelFlowableSubscribeProxy<T> autoDispose(@NotNull ParallelFlowable<T> autoDispose, @NotNull ScopeProvider provider) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(provider));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.autoDisposable(provider))");
        return (ParallelFlowableSubscribeProxy) obj;
    }

    public static final void withScope(@NotNull ScopeProvider scope, @NotNull Function1<? super AutoDisposeContext, Unit> body) {
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Intrinsics.checkParameterIsNotNull(body, "body");
        Completable completableOf = Scopes.completableOf(scope);
        Intrinsics.checkExpressionValueIsNotNull(completableOf, "completableOf(scope)");
        body.invoke(new RealAutoDisposeContext(completableOf));
    }

    public static final void withScope(@NotNull Completable completableScope, @NotNull Function1<? super AutoDisposeContext, Unit> body) {
        Intrinsics.checkParameterIsNotNull(completableScope, "completableScope");
        Intrinsics.checkParameterIsNotNull(body, "body");
        body.invoke(new RealAutoDisposeContext(completableScope));
    }
}
