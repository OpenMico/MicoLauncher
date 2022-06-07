package autodispose2;

import autodispose2.AutoDispose;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.reactivestreams.Subscriber;

/* loaded from: classes.dex */
public final class AutoDispose {
    public static <T> AutoDisposeConverter<T> autoDisposable(ScopeProvider scopeProvider) {
        k.a(scopeProvider, "provider == null");
        return autoDisposable(Scopes.completableOf(scopeProvider));
    }

    /* renamed from: autodispose2.AutoDispose$1 */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements AutoDisposeConverter<T> {
        final /* synthetic */ CompletableSource a;

        AnonymousClass1(CompletableSource completableSource) {
            this.a = completableSource;
        }

        /* renamed from: a */
        public ParallelFlowableSubscribeProxy<T> apply(final ParallelFlowable<T> parallelFlowable) {
            if (!AutoDisposePlugins.b) {
                return new i(parallelFlowable, this.a);
            }
            final CompletableSource completableSource = this.a;
            return new ParallelFlowableSubscribeProxy() { // from class: autodispose2.-$$Lambda$AutoDispose$1$mqcmwxEwa__9uwBDcLeOMSlPm9w
                @Override // autodispose2.ParallelFlowableSubscribeProxy
                public final void subscribe(Subscriber[] subscriberArr) {
                    AutoDispose.AnonymousClass1.a(ParallelFlowable.this, completableSource, subscriberArr);
                }
            };
        }

        public static /* synthetic */ void a(ParallelFlowable parallelFlowable, CompletableSource completableSource, Subscriber[] subscriberArr) {
            new i(parallelFlowable, completableSource).subscribe(subscriberArr);
        }

        /* renamed from: a */
        public CompletableSubscribeProxy apply(final Completable completable) {
            if (!AutoDisposePlugins.b) {
                return new d(completable, this.a);
            }
            return new CompletableSubscribeProxy() { // from class: autodispose2.AutoDispose.1.1
                @Override // autodispose2.CompletableSubscribeProxy
                public Disposable subscribe() {
                    return new d(completable, AnonymousClass1.this.a).subscribe();
                }

                @Override // autodispose2.CompletableSubscribeProxy
                public Disposable subscribe(Action action) {
                    return new d(completable, AnonymousClass1.this.a).subscribe(action);
                }

                @Override // autodispose2.CompletableSubscribeProxy
                public Disposable subscribe(Action action, Consumer<? super Throwable> consumer) {
                    return new d(completable, AnonymousClass1.this.a).subscribe(action, consumer);
                }

                @Override // autodispose2.CompletableSubscribeProxy
                public void subscribe(CompletableObserver completableObserver) {
                    new d(completable, AnonymousClass1.this.a).subscribe(completableObserver);
                }

                @Override // autodispose2.CompletableSubscribeProxy
                public <E extends CompletableObserver> E subscribeWith(E e) {
                    return (E) new d(completable, AnonymousClass1.this.a).subscribeWith(e);
                }

                @Override // autodispose2.CompletableSubscribeProxy
                public TestObserver<Void> test() {
                    TestObserver<Void> testObserver = new TestObserver<>();
                    subscribe(testObserver);
                    return testObserver;
                }

                @Override // autodispose2.CompletableSubscribeProxy
                public TestObserver<Void> test(boolean z) {
                    TestObserver<Void> testObserver = new TestObserver<>();
                    if (z) {
                        testObserver.dispose();
                    }
                    subscribe(testObserver);
                    return testObserver;
                }
            };
        }

        /* renamed from: a */
        public FlowableSubscribeProxy<T> apply(final Flowable<T> flowable) {
            if (!AutoDisposePlugins.b) {
                return new f(flowable, this.a);
            }
            return new FlowableSubscribeProxy<T>() { // from class: autodispose2.AutoDispose.1.2
                @Override // autodispose2.FlowableSubscribeProxy
                public Disposable subscribe() {
                    return new f(flowable, AnonymousClass1.this.a).subscribe();
                }

                @Override // autodispose2.FlowableSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer) {
                    return new f(flowable, AnonymousClass1.this.a).subscribe(consumer);
                }

                @Override // autodispose2.FlowableSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
                    return new f(flowable, AnonymousClass1.this.a).subscribe(consumer, consumer2);
                }

                @Override // autodispose2.FlowableSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action) {
                    return new f(flowable, AnonymousClass1.this.a).subscribe(consumer, consumer2, action);
                }

                @Override // autodispose2.FlowableSubscribeProxy
                public void subscribe(Subscriber<? super T> subscriber) {
                    new f(flowable, AnonymousClass1.this.a).subscribe(subscriber);
                }

                @Override // autodispose2.FlowableSubscribeProxy
                public <E extends Subscriber<? super T>> E subscribeWith(E e) {
                    return (E) new f(flowable, AnonymousClass1.this.a).subscribeWith(e);
                }

                @Override // autodispose2.FlowableSubscribeProxy
                public TestSubscriber<T> test() {
                    TestSubscriber<T> testSubscriber = new TestSubscriber<>();
                    subscribe(testSubscriber);
                    return testSubscriber;
                }

                @Override // autodispose2.FlowableSubscribeProxy
                public TestSubscriber<T> test(long j) {
                    TestSubscriber<T> testSubscriber = new TestSubscriber<>(j);
                    subscribe(testSubscriber);
                    return testSubscriber;
                }

                @Override // autodispose2.FlowableSubscribeProxy
                public TestSubscriber<T> test(long j, boolean z) {
                    TestSubscriber<T> testSubscriber = new TestSubscriber<>(j);
                    if (z) {
                        testSubscriber.cancel();
                    }
                    subscribe(testSubscriber);
                    return testSubscriber;
                }
            };
        }

        /* renamed from: a */
        public MaybeSubscribeProxy<T> apply(final Maybe<T> maybe) {
            if (!AutoDisposePlugins.b) {
                return new g(maybe, this.a);
            }
            return new MaybeSubscribeProxy<T>() { // from class: autodispose2.AutoDispose.1.3
                @Override // autodispose2.MaybeSubscribeProxy
                public Disposable subscribe() {
                    return new g(maybe, AnonymousClass1.this.a).subscribe();
                }

                @Override // autodispose2.MaybeSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer) {
                    return new g(maybe, AnonymousClass1.this.a).subscribe(consumer);
                }

                @Override // autodispose2.MaybeSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
                    return new g(maybe, AnonymousClass1.this.a).subscribe(consumer, consumer2);
                }

                @Override // autodispose2.MaybeSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action) {
                    return new g(maybe, AnonymousClass1.this.a).subscribe(consumer, consumer2, action);
                }

                @Override // autodispose2.MaybeSubscribeProxy
                public void subscribe(MaybeObserver<? super T> maybeObserver) {
                    new g(maybe, AnonymousClass1.this.a).subscribe(maybeObserver);
                }

                @Override // autodispose2.MaybeSubscribeProxy
                public <E extends MaybeObserver<? super T>> E subscribeWith(E e) {
                    return (E) new g(maybe, AnonymousClass1.this.a).subscribeWith(e);
                }

                @Override // autodispose2.MaybeSubscribeProxy
                public TestObserver<T> test() {
                    TestObserver<T> testObserver = new TestObserver<>();
                    subscribe(testObserver);
                    return testObserver;
                }

                @Override // autodispose2.MaybeSubscribeProxy
                public TestObserver<T> test(boolean z) {
                    TestObserver<T> testObserver = new TestObserver<>();
                    if (z) {
                        testObserver.dispose();
                    }
                    subscribe(testObserver);
                    return testObserver;
                }
            };
        }

        /* renamed from: a */
        public ObservableSubscribeProxy<T> apply(final Observable<T> observable) {
            if (!AutoDisposePlugins.b) {
                return new h(observable, this.a);
            }
            return new ObservableSubscribeProxy<T>() { // from class: autodispose2.AutoDispose.1.4
                @Override // autodispose2.ObservableSubscribeProxy
                public Disposable subscribe() {
                    return new h(observable, AnonymousClass1.this.a).subscribe();
                }

                @Override // autodispose2.ObservableSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer) {
                    return new h(observable, AnonymousClass1.this.a).subscribe(consumer);
                }

                @Override // autodispose2.ObservableSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
                    return new h(observable, AnonymousClass1.this.a).subscribe(consumer, consumer2);
                }

                @Override // autodispose2.ObservableSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action) {
                    return new h(observable, AnonymousClass1.this.a).subscribe(consumer, consumer2, action);
                }

                @Override // autodispose2.ObservableSubscribeProxy
                public void subscribe(Observer<? super T> observer) {
                    new h(observable, AnonymousClass1.this.a).subscribe(observer);
                }

                @Override // autodispose2.ObservableSubscribeProxy
                public <E extends Observer<? super T>> E subscribeWith(E e) {
                    return (E) new h(observable, AnonymousClass1.this.a).subscribeWith(e);
                }

                @Override // autodispose2.ObservableSubscribeProxy
                public TestObserver<T> test() {
                    TestObserver<T> testObserver = new TestObserver<>();
                    subscribe(testObserver);
                    return testObserver;
                }

                @Override // autodispose2.ObservableSubscribeProxy
                public TestObserver<T> test(boolean z) {
                    TestObserver<T> testObserver = new TestObserver<>();
                    if (z) {
                        testObserver.dispose();
                    }
                    subscribe(testObserver);
                    return testObserver;
                }
            };
        }

        /* renamed from: a */
        public SingleSubscribeProxy<T> apply(final Single<T> single) {
            if (!AutoDisposePlugins.b) {
                return new j(single, this.a);
            }
            return new SingleSubscribeProxy<T>() { // from class: autodispose2.AutoDispose.1.5
                @Override // autodispose2.SingleSubscribeProxy
                public Disposable subscribe() {
                    return new j(single, AnonymousClass1.this.a).subscribe();
                }

                @Override // autodispose2.SingleSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer) {
                    return new j(single, AnonymousClass1.this.a).subscribe(consumer);
                }

                @Override // autodispose2.SingleSubscribeProxy
                public Disposable subscribe(BiConsumer<? super T, ? super Throwable> biConsumer) {
                    return new j(single, AnonymousClass1.this.a).subscribe(biConsumer);
                }

                @Override // autodispose2.SingleSubscribeProxy
                public Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
                    return new j(single, AnonymousClass1.this.a).subscribe(consumer, consumer2);
                }

                @Override // autodispose2.SingleSubscribeProxy
                public void subscribe(SingleObserver<? super T> singleObserver) {
                    new j(single, AnonymousClass1.this.a).subscribe(singleObserver);
                }

                @Override // autodispose2.SingleSubscribeProxy
                public <E extends SingleObserver<? super T>> E subscribeWith(E e) {
                    return (E) new j(single, AnonymousClass1.this.a).subscribeWith(e);
                }

                @Override // autodispose2.SingleSubscribeProxy
                public TestObserver<T> test() {
                    TestObserver<T> testObserver = new TestObserver<>();
                    subscribe(testObserver);
                    return testObserver;
                }

                @Override // autodispose2.SingleSubscribeProxy
                public TestObserver<T> test(boolean z) {
                    TestObserver<T> testObserver = new TestObserver<>();
                    if (z) {
                        testObserver.dispose();
                    }
                    subscribe(testObserver);
                    return testObserver;
                }
            };
        }
    }

    public static <T> AutoDisposeConverter<T> autoDisposable(CompletableSource completableSource) {
        k.a(completableSource, "scope == null");
        return new AnonymousClass1(completableSource);
    }

    private AutoDispose() {
        throw new AssertionError("No instances");
    }
}
