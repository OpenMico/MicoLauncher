package autodispose2;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinExtensions.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\f\u0010\u0002\u001a\u00020\u0003*\u00020\u0004H&J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u0007H&J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00060\b\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\tH&J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00060\n\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u000bH&J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00060\f\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\rH&J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00060\u000e\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u000fH&¨\u0006\u0010"}, d2 = {"Lautodispose2/AutoDisposeContext;", "", "autoDispose", "Lautodispose2/CompletableSubscribeProxy;", "Lio/reactivex/rxjava3/core/Completable;", "Lautodispose2/FlowableSubscribeProxy;", ExifInterface.GPS_DIRECTION_TRUE, "Lio/reactivex/rxjava3/core/Flowable;", "Lautodispose2/MaybeSubscribeProxy;", "Lio/reactivex/rxjava3/core/Maybe;", "Lautodispose2/ObservableSubscribeProxy;", "Lio/reactivex/rxjava3/core/Observable;", "Lautodispose2/SingleSubscribeProxy;", "Lio/reactivex/rxjava3/core/Single;", "Lautodispose2/ParallelFlowableSubscribeProxy;", "Lio/reactivex/rxjava3/parallel/ParallelFlowable;", "autodispose"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public interface AutoDisposeContext {
    @NotNull
    CompletableSubscribeProxy autoDispose(@NotNull Completable completable);

    @NotNull
    <T> FlowableSubscribeProxy<T> autoDispose(@NotNull Flowable<T> flowable);

    @NotNull
    <T> MaybeSubscribeProxy<T> autoDispose(@NotNull Maybe<T> maybe);

    @NotNull
    <T> ObservableSubscribeProxy<T> autoDispose(@NotNull Observable<T> observable);

    @NotNull
    <T> ParallelFlowableSubscribeProxy<T> autoDispose(@NotNull ParallelFlowable<T> parallelFlowable);

    @NotNull
    <T> SingleSubscribeProxy<T> autoDispose(@NotNull Single<T> single);
}
