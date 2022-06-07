package autodispose2.android;

import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import autodispose2.AutoDispose;
import autodispose2.CompletableSubscribeProxy;
import autodispose2.FlowableSubscribeProxy;
import autodispose2.MaybeSubscribeProxy;
import autodispose2.ObservableSubscribeProxy;
import autodispose2.ParallelFlowableSubscribeProxy;
import autodispose2.ScopeProvider;
import autodispose2.SingleSubscribeProxy;
import com.xiaomi.onetrack.OneTrack;
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

/* compiled from: KotlinExtensions.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a'\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a'\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00060\b\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\t2\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a'\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00060\n\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u000b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a'\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00060\f\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\r2\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a'\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00060\u000e\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u000f2\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\r\u0010\u0010\u001a\u00020\u0011*\u00020\u0004H\u0087\b¨\u0006\u0012"}, d2 = {"autoDispose", "Lautodispose2/CompletableSubscribeProxy;", "Lio/reactivex/rxjava3/core/Completable;", OneTrack.Event.VIEW, "Landroid/view/View;", "Lautodispose2/FlowableSubscribeProxy;", ExifInterface.GPS_DIRECTION_TRUE, "Lio/reactivex/rxjava3/core/Flowable;", "Lautodispose2/MaybeSubscribeProxy;", "Lio/reactivex/rxjava3/core/Maybe;", "Lautodispose2/ObservableSubscribeProxy;", "Lio/reactivex/rxjava3/core/Observable;", "Lautodispose2/SingleSubscribeProxy;", "Lio/reactivex/rxjava3/core/Single;", "Lautodispose2/ParallelFlowableSubscribeProxy;", "Lio/reactivex/rxjava3/parallel/ParallelFlowable;", "scope", "Lautodispose2/ScopeProvider;", "autodispose-android_release"}, k = 2, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class KotlinExtensionsKt {
    @CheckReturnValue
    @NotNull
    public static final ScopeProvider scope(@NotNull View scope) {
        Intrinsics.checkParameterIsNotNull(scope, "$this$scope");
        ScopeProvider from = ViewScopeProvider.from(scope);
        Intrinsics.checkExpressionValueIsNotNull(from, "ViewScopeProvider.from(this)");
        return from;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> FlowableSubscribeProxy<T> autoDispose(@NotNull Flowable<T> autoDispose, @NotNull View view) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(view, "view");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(ViewScopeProvider.from(view)));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…copeProvider.from(view)))");
        return (FlowableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> ObservableSubscribeProxy<T> autoDispose(@NotNull Observable<T> autoDispose, @NotNull View view) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(view, "view");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(ViewScopeProvider.from(view)));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…copeProvider.from(view)))");
        return (ObservableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> SingleSubscribeProxy<T> autoDispose(@NotNull Single<T> autoDispose, @NotNull View view) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(view, "view");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(ViewScopeProvider.from(view)));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…copeProvider.from(view)))");
        return (SingleSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> MaybeSubscribeProxy<T> autoDispose(@NotNull Maybe<T> autoDispose, @NotNull View view) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(view, "view");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(ViewScopeProvider.from(view)));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…copeProvider.from(view)))");
        return (MaybeSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final CompletableSubscribeProxy autoDispose(@NotNull Completable autoDispose, @NotNull View view) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(view, "view");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(ViewScopeProvider.from(view)));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…copeProvider.from(view)))");
        return (CompletableSubscribeProxy) obj;
    }

    @CheckReturnValue
    @NotNull
    public static final <T> ParallelFlowableSubscribeProxy<T> autoDispose(@NotNull ParallelFlowable<T> autoDispose, @NotNull View view) {
        Intrinsics.checkParameterIsNotNull(autoDispose, "$this$autoDispose");
        Intrinsics.checkParameterIsNotNull(view, "view");
        Object obj = autoDispose.to(AutoDispose.autoDisposable(ViewScopeProvider.from(view)));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…copeProvider.from(view)))");
        return (ParallelFlowableSubscribeProxy) obj;
    }
}
