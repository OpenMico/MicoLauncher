package org.reactivestreams;

import java.util.Objects;
import java.util.concurrent.Flow;

/* loaded from: classes3.dex */
public final class FlowAdapters {
    private FlowAdapters() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Publisher<T> toPublisher(Flow.Publisher<? extends T> publisher) {
        Objects.requireNonNull(publisher, "flowPublisher");
        if (publisher instanceof a) {
            return (Publisher<? extends T>) ((a) publisher).a;
        }
        if (publisher instanceof Publisher) {
            return (Publisher) publisher;
        }
        return new e(publisher);
    }

    public static <T> Flow.Publisher<T> toFlowPublisher(Publisher<? extends T> publisher) {
        Objects.requireNonNull(publisher, "reactiveStreamsPublisher");
        if (publisher instanceof e) {
            return (Flow.Publisher<? extends T>) ((e) publisher).a;
        }
        if (publisher instanceof Flow.Publisher) {
            return (Flow.Publisher) publisher;
        }
        return new a(publisher);
    }

    public static <T, U> Processor<T, U> toProcessor(Flow.Processor<? super T, ? extends U> processor) {
        Objects.requireNonNull(processor, "flowProcessor");
        if (processor instanceof b) {
            return (Processor<? super T, ? extends U>) ((b) processor).a;
        }
        if (processor instanceof Processor) {
            return (Processor) processor;
        }
        return new f(processor);
    }

    public static <T, U> Flow.Processor<T, U> toFlowProcessor(Processor<? super T, ? extends U> processor) {
        Objects.requireNonNull(processor, "reactiveStreamsProcessor");
        if (processor instanceof f) {
            return (Flow.Processor<? super T, ? extends U>) ((f) processor).a;
        }
        if (processor instanceof Flow.Processor) {
            return (Flow.Processor) processor;
        }
        return new b(processor);
    }

    public static <T> Flow.Subscriber<T> toFlowSubscriber(Subscriber<T> subscriber) {
        Objects.requireNonNull(subscriber, "reactiveStreamsSubscriber");
        if (subscriber instanceof g) {
            return (Flow.Subscriber<? super T>) ((g) subscriber).a;
        }
        if (subscriber instanceof Flow.Subscriber) {
            return (Flow.Subscriber) subscriber;
        }
        return new c(subscriber);
    }

    public static <T> Subscriber<T> toSubscriber(Flow.Subscriber<T> subscriber) {
        Objects.requireNonNull(subscriber, "flowSubscriber");
        if (subscriber instanceof c) {
            return (Subscriber<? super T>) ((c) subscriber).a;
        }
        if (subscriber instanceof Subscriber) {
            return (Subscriber) subscriber;
        }
        return new g(subscriber);
    }

    /* loaded from: classes3.dex */
    static final class d implements Flow.Subscription {
        final Subscription a;

        public d(Subscription subscription) {
            this.a = subscription;
        }
    }

    /* loaded from: classes3.dex */
    static final class c<T> implements Flow.Subscriber<T> {
        final Subscriber<? super T> a;

        public c(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }
    }

    /* loaded from: classes3.dex */
    static final class g<T> implements Subscriber<T> {
        final Flow.Subscriber<? super T> a;

        public g(Flow.Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.a.onSubscribe(subscription == null ? null : new d(subscription));
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
        }
    }

    /* loaded from: classes3.dex */
    static final class f<T, U> implements Processor<T, U> {
        final Flow.Processor<? super T, ? extends U> a;

        public f(Flow.Processor<? super T, ? extends U> processor) {
            this.a = processor;
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.a.onSubscribe(subscription == null ? null : new d(subscription));
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super U> subscriber) {
            this.a.subscribe(subscriber == null ? null : new c(subscriber));
        }
    }

    /* loaded from: classes3.dex */
    static final class b<T, U> implements Flow.Processor<T, U> {
        final Processor<? super T, ? extends U> a;

        public b(Processor<? super T, ? extends U> processor) {
            this.a = processor;
        }
    }

    /* loaded from: classes3.dex */
    static final class e<T> implements Publisher<T> {
        final Flow.Publisher<? extends T> a;

        public e(Flow.Publisher<? extends T> publisher) {
            this.a = publisher;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            this.a.subscribe(subscriber == null ? null : new c(subscriber));
        }
    }

    /* loaded from: classes3.dex */
    static final class a<T> implements Flow.Publisher<T> {
        final Publisher<? extends T> a;

        public a(Publisher<? extends T> publisher) {
            this.a = publisher;
        }
    }
}
