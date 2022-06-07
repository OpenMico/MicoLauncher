package autodispose2.lifecycle;

import autodispose2.OutsideScopeException;
import io.reactivex.rxjava3.functions.Function;

/* loaded from: classes.dex */
public interface CorrespondingEventsFunction<E> extends Function<E, E> {
    @Override // io.reactivex.rxjava3.functions.Function
    E apply(E e) throws OutsideScopeException;
}
