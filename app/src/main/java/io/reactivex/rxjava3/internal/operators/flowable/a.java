package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamPublisher;
import java.util.Objects;
import org.reactivestreams.Publisher;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractFlowableWithUpstream.java */
/* loaded from: classes5.dex */
public abstract class a<T, R> extends Flowable<R> implements HasUpstreamPublisher<T> {
    protected final Flowable<T> source;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Flowable<T> flowable) {
        this.source = (Flowable) Objects.requireNonNull(flowable, "source is null");
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamPublisher
    public final Publisher<T> source() {
        return this.source;
    }
}
