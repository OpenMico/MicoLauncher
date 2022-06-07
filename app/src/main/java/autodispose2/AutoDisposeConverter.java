package autodispose2;

import io.reactivex.rxjava3.core.CompletableConverter;
import io.reactivex.rxjava3.core.FlowableConverter;
import io.reactivex.rxjava3.core.MaybeConverter;
import io.reactivex.rxjava3.core.ObservableConverter;
import io.reactivex.rxjava3.core.SingleConverter;
import io.reactivex.rxjava3.parallel.ParallelFlowableConverter;

/* loaded from: classes.dex */
public interface AutoDisposeConverter<T> extends CompletableConverter<CompletableSubscribeProxy>, FlowableConverter<T, FlowableSubscribeProxy<T>>, MaybeConverter<T, MaybeSubscribeProxy<T>>, ObservableConverter<T, ObservableSubscribeProxy<T>>, SingleConverter<T, SingleSubscribeProxy<T>>, ParallelFlowableConverter<T, ParallelFlowableSubscribeProxy<T>> {
}
