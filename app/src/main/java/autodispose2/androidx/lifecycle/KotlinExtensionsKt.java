package autodispose2.androidx.lifecycle;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import autodispose2.AutoDispose;
import autodispose2.CompletableSubscribeProxy;
import autodispose2.FlowableSubscribeProxy;
import autodispose2.MaybeSubscribeProxy;
import autodispose2.ObservableSubscribeProxy;
import autodispose2.ParallelFlowableSubscribeProxy;
import autodispose2.ScopeProvider;
import autodispose2.SingleSubscribeProxy;
import autodispose2.lifecycle.CorrespondingEventsFunction;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinExtensions.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a!\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t2\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\b0\n\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000b2\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\b0\f\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\r2\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\b0\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000f2\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\b0\u0010\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u00112\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b\u001a\r\u0010\u0012\u001a\u00020\u0013*\u00020\u0014H\u0087\b\u001a\u0015\u0010\u0012\u001a\u00020\u0013*\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u0006H\u0087\b\u001a\u001b\u0010\u0012\u001a\u00020\u0013*\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016H\u0087\b\u001a\r\u0010\u0012\u001a\u00020\u0013*\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0012\u001a\u00020\u0013*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0087\b\u001a\u001b\u0010\u0012\u001a\u00020\u0013*\u00020\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016H\u0087\b¨\u0006\u0017"}, d2 = {"autoDispose", "Lautodispose2/CompletableSubscribeProxy;", "Lio/reactivex/rxjava3/core/Completable;", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "untilEvent", "Landroidx/lifecycle/Lifecycle$Event;", "Lautodispose2/FlowableSubscribeProxy;", ExifInterface.GPS_DIRECTION_TRUE, "Lio/reactivex/rxjava3/core/Flowable;", "Lautodispose2/MaybeSubscribeProxy;", "Lio/reactivex/rxjava3/core/Maybe;", "Lautodispose2/ObservableSubscribeProxy;", "Lio/reactivex/rxjava3/core/Observable;", "Lautodispose2/SingleSubscribeProxy;", "Lio/reactivex/rxjava3/core/Single;", "Lautodispose2/ParallelFlowableSubscribeProxy;", "Lio/reactivex/rxjava3/parallel/ParallelFlowable;", "scope", "Lautodispose2/ScopeProvider;", "Landroidx/lifecycle/Lifecycle;", "boundaryResolver", "Lautodispose2/lifecycle/CorrespondingEventsFunction;", "autodispose-androidx-lifecycle_release"}, k = 2, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class KotlinExtensionsKt {
    @CheckReturnValue
    @NotNull
    public static final ScopeProvider scope(@NotNull LifecycleOwner scope) {
        Intrinsics.checkParameterIsNotNull(scope, "$this$scope");
        AndroidLifecycleScopeProvider from = AndroidLifecycleScopeProvider.from(scope);
        Intrinsics.checkExpressionValueIsNotNull(from, "AndroidLifecycleScopeProvider.from(\n    this)");
        return from;
    }

    @CheckReturnValue
    @NotNull
    public static final ScopeProvider scope(@NotNull LifecycleOwner scope, @NotNull Lifecycle.Event untilEvent) {
        Intrinsics.checkParameterIsNotNull(scope, "$this$scope");
        Intrinsics.checkParameterIsNotNull(untilEvent, "untilEvent");
        AndroidLifecycleScopeProvider from = AndroidLifecycleScopeProvider.from(scope, untilEvent);
        Intrinsics.checkExpressionValueIsNotNull(from, "AndroidLifecycleScopePro…rom(this,\n    untilEvent)");
        return from;
    }

    @CheckReturnValue
    @NotNull
    public static final ScopeProvider scope(@NotNull LifecycleOwner scope, @NotNull CorrespondingEventsFunction<Lifecycle.Event> boundaryResolver) {
        Intrinsics.checkParameterIsNotNull(scope, "$this$scope");
        Intrinsics.checkParameterIsNotNull(boundaryResolver, "boundaryResolver");
        AndroidLifecycleScopeProvider from = AndroidLifecycleScopeProvider.from(scope, boundaryResolver);
        Intrinsics.checkExpressionValueIsNotNull(from, "AndroidLifecycleScopePro…is,\n    boundaryResolver)");
        return from;
    }

    @CheckReturnValue
    @NotNull
    public static final ScopeProvider scope(@NotNull Lifecycle scope) {
        Intrinsics.checkParameterIsNotNull(scope, "$this$scope");
        AndroidLifecycleScopeProvider from = AndroidLifecycleScopeProvider.from(scope);
        Intrinsics.checkExpressionValueIsNotNull(from, "AndroidLifecycleScopeProvider.from(\n    this)");
        return from;
    }

    @CheckReturnValue
    @NotNull
    public static final ScopeProvider scope(@NotNull Lifecycle scope, @NotNull Lifecycle.Event untilEvent) {
        Intrinsics.checkParameterIsNotNull(scope, "$this$scope");
        Intrinsics.checkParameterIsNotNull(untilEvent, "untilEvent");
        AndroidLifecycleScopeProvider from = AndroidLifecycleScopeProvider.from(scope, untilEvent);
        Intrinsics.checkExpressionValueIsNotNull(from, "AndroidLifecycleScopePro…om(\n    this, untilEvent)");
        return from;
    }

    @CheckReturnValue
    @NotNull
    public static final ScopeProvider scope(@NotNull Lifecycle scope, @NotNull CorrespondingEventsFunction<Lifecycle.Event> boundaryResolver) {
        Intrinsics.checkParameterIsNotNull(scope, "$this$scope");
        Intrinsics.checkParameterIsNotNull(boundaryResolver, "boundaryResolver");
        AndroidLifecycleScopeProvider from = AndroidLifecycleScopeProvider.from(scope, boundaryResolver);
        Intrinsics.checkExpressionValueIsNotNull(from, "AndroidLifecycleScopePro…  this, boundaryResolver)");
        return from;
    }

    public static /* synthetic */ FlowableSubscribeProxy autoDispose$default(Flowable autoDispose, LifecycleOwner lifecycleOwner, Lifecycle.Event event, int i, Object obj) {
        if ((i & 2) != 0) {
            event = null;
        }
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (FlowableSubscribeProxy) obj2;
        }
        Object obj3 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj3, "this.to(AutoDispose.auto…            untilEvent)))");
        return (FlowableSubscribeProxy) obj3;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> FlowableSubscribeProxy<T> autoDispose(@NotNull Flowable<T> autoDispose, @NotNull LifecycleOwner lifecycleOwner, @Nullable Lifecycle.Event event) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (FlowableSubscribeProxy) obj;
        }
        Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…            untilEvent)))");
        return (FlowableSubscribeProxy) obj2;
    }

    public static /* synthetic */ ObservableSubscribeProxy autoDispose$default(Observable autoDispose, LifecycleOwner lifecycleOwner, Lifecycle.Event event, int i, Object obj) {
        if ((i & 2) != 0) {
            event = null;
        }
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (ObservableSubscribeProxy) obj2;
        }
        Object obj3 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj3, "this.to(AutoDispose.auto…            untilEvent)))");
        return (ObservableSubscribeProxy) obj3;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> ObservableSubscribeProxy<T> autoDispose(@NotNull Observable<T> autoDispose, @NotNull LifecycleOwner lifecycleOwner, @Nullable Lifecycle.Event event) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (ObservableSubscribeProxy) obj;
        }
        Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…            untilEvent)))");
        return (ObservableSubscribeProxy) obj2;
    }

    public static /* synthetic */ SingleSubscribeProxy autoDispose$default(Single autoDispose, LifecycleOwner lifecycleOwner, Lifecycle.Event event, int i, Object obj) {
        if ((i & 2) != 0) {
            event = null;
        }
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (SingleSubscribeProxy) obj2;
        }
        Object obj3 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj3, "this.to(AutoDispose.auto…            untilEvent)))");
        return (SingleSubscribeProxy) obj3;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> SingleSubscribeProxy<T> autoDispose(@NotNull Single<T> autoDispose, @NotNull LifecycleOwner lifecycleOwner, @Nullable Lifecycle.Event event) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (SingleSubscribeProxy) obj;
        }
        Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…            untilEvent)))");
        return (SingleSubscribeProxy) obj2;
    }

    public static /* synthetic */ MaybeSubscribeProxy autoDispose$default(Maybe autoDispose, LifecycleOwner lifecycleOwner, Lifecycle.Event event, int i, Object obj) {
        if ((i & 2) != 0) {
            event = null;
        }
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (MaybeSubscribeProxy) obj2;
        }
        Object obj3 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj3, "this.to(AutoDispose.auto…            untilEvent)))");
        return (MaybeSubscribeProxy) obj3;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> MaybeSubscribeProxy<T> autoDispose(@NotNull Maybe<T> autoDispose, @NotNull LifecycleOwner lifecycleOwner, @Nullable Lifecycle.Event event) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (MaybeSubscribeProxy) obj;
        }
        Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…            untilEvent)))");
        return (MaybeSubscribeProxy) obj2;
    }

    public static /* synthetic */ CompletableSubscribeProxy autoDispose$default(Completable autoDispose, LifecycleOwner lifecycleOwner, Lifecycle.Event event, int i, Object obj) {
        if ((i & 2) != 0) {
            event = null;
        }
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (CompletableSubscribeProxy) obj2;
        }
        Object obj3 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj3, "this.to(AutoDispose.auto…            untilEvent)))");
        return (CompletableSubscribeProxy) obj3;
    }

    @CheckReturnValue
    @NotNull
    public static final CompletableSubscribeProxy autoDispose(@NotNull Completable autoDispose, @NotNull LifecycleOwner lifecycleOwner, @Nullable Lifecycle.Event event) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (CompletableSubscribeProxy) obj;
        }
        Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…            untilEvent)))");
        return (CompletableSubscribeProxy) obj2;
    }

    public static /* synthetic */ ParallelFlowableSubscribeProxy autoDispose$default(ParallelFlowable autoDispose, LifecycleOwner lifecycleOwner, Lifecycle.Event event, int i, Object obj) {
        if ((i & 2) != 0) {
            event = null;
        }
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (ParallelFlowableSubscribeProxy) obj2;
        }
        Object obj3 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj3, "this.to(AutoDispose.auto…            untilEvent)))");
        return (ParallelFlowableSubscribeProxy) obj3;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> ParallelFlowableSubscribeProxy<T> autoDispose(@NotNull ParallelFlowable<T> autoDispose, @NotNull LifecycleOwner lifecycleOwner, @Nullable Lifecycle.Event event) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(lifecycleOwner, "lifecycleOwner");
        if (event == null) {
            Object obj = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));
            Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
            return (ParallelFlowableSubscribeProxy) obj;
        }
        Object obj2 = autoDispose.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, event)));
        Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…            untilEvent)))");
        return (ParallelFlowableSubscribeProxy) obj2;
    }
}
