package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class LiveDataReactiveStreams {
    private LiveDataReactiveStreams() {
    }

    @NonNull
    public static <T> Publisher<T> toPublisher(@NonNull LifecycleOwner lifecycleOwner, @NonNull LiveData<T> liveData) {
        return new LiveDataPublisher(lifecycleOwner, liveData);
    }

    /* loaded from: classes.dex */
    private static final class LiveDataPublisher<T> implements Publisher<T> {
        final LifecycleOwner mLifecycle;
        final LiveData<T> mLiveData;

        LiveDataPublisher(LifecycleOwner lifecycleOwner, LiveData<T> liveData) {
            this.mLifecycle = lifecycleOwner;
            this.mLiveData = liveData;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            subscriber.onSubscribe(new LiveDataSubscription(subscriber, this.mLifecycle, this.mLiveData));
        }

        /* loaded from: classes.dex */
        static final class LiveDataSubscription<T> implements Observer<T>, Subscription {
            volatile boolean mCanceled;
            @Nullable
            T mLatest;
            final LifecycleOwner mLifecycle;
            final LiveData<T> mLiveData;
            boolean mObserving;
            long mRequested;
            final Subscriber<? super T> mSubscriber;

            LiveDataSubscription(Subscriber<? super T> subscriber, LifecycleOwner lifecycleOwner, LiveData<T> liveData) {
                this.mSubscriber = subscriber;
                this.mLifecycle = lifecycleOwner;
                this.mLiveData = liveData;
            }

            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable T t) {
                if (!this.mCanceled) {
                    if (this.mRequested > 0) {
                        this.mLatest = null;
                        this.mSubscriber.onNext(t);
                        long j = this.mRequested;
                        if (j != Long.MAX_VALUE) {
                            this.mRequested = j - 1;
                            return;
                        }
                        return;
                    }
                    this.mLatest = t;
                }
            }

            @Override // org.reactivestreams.Subscription
            public void request(final long j) {
                if (!this.mCanceled) {
                    ArchTaskExecutor.getInstance().executeOnMainThread(new Runnable() { // from class: androidx.lifecycle.LiveDataReactiveStreams.LiveDataPublisher.LiveDataSubscription.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!LiveDataSubscription.this.mCanceled) {
                                if (j <= 0) {
                                    LiveDataSubscription liveDataSubscription = LiveDataSubscription.this;
                                    liveDataSubscription.mCanceled = true;
                                    if (liveDataSubscription.mObserving) {
                                        LiveDataSubscription.this.mLiveData.removeObserver(LiveDataSubscription.this);
                                        LiveDataSubscription.this.mObserving = false;
                                    }
                                    LiveDataSubscription liveDataSubscription2 = LiveDataSubscription.this;
                                    liveDataSubscription2.mLatest = null;
                                    liveDataSubscription2.mSubscriber.onError(new IllegalArgumentException("Non-positive request"));
                                    return;
                                }
                                LiveDataSubscription liveDataSubscription3 = LiveDataSubscription.this;
                                liveDataSubscription3.mRequested = liveDataSubscription3.mRequested + j >= LiveDataSubscription.this.mRequested ? LiveDataSubscription.this.mRequested + j : Long.MAX_VALUE;
                                if (!LiveDataSubscription.this.mObserving) {
                                    LiveDataSubscription liveDataSubscription4 = LiveDataSubscription.this;
                                    liveDataSubscription4.mObserving = true;
                                    liveDataSubscription4.mLiveData.observe(LiveDataSubscription.this.mLifecycle, LiveDataSubscription.this);
                                } else if (LiveDataSubscription.this.mLatest != null) {
                                    LiveDataSubscription liveDataSubscription5 = LiveDataSubscription.this;
                                    liveDataSubscription5.onChanged(liveDataSubscription5.mLatest);
                                    LiveDataSubscription.this.mLatest = null;
                                }
                            }
                        }
                    });
                }
            }

            @Override // org.reactivestreams.Subscription
            public void cancel() {
                if (!this.mCanceled) {
                    this.mCanceled = true;
                    ArchTaskExecutor.getInstance().executeOnMainThread(new Runnable() { // from class: androidx.lifecycle.LiveDataReactiveStreams.LiveDataPublisher.LiveDataSubscription.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (LiveDataSubscription.this.mObserving) {
                                LiveDataSubscription.this.mLiveData.removeObserver(LiveDataSubscription.this);
                                LiveDataSubscription.this.mObserving = false;
                            }
                            LiveDataSubscription.this.mLatest = null;
                        }
                    });
                }
            }
        }
    }

    @NonNull
    public static <T> LiveData<T> fromPublisher(@NonNull Publisher<T> publisher) {
        return new PublisherLiveData(publisher);
    }

    /* loaded from: classes.dex */
    private static class PublisherLiveData<T> extends LiveData<T> {
        private final Publisher<T> mPublisher;
        final AtomicReference<PublisherLiveData<T>.LiveDataSubscriber> mSubscriber = new AtomicReference<>();

        PublisherLiveData(@NonNull Publisher<T> publisher) {
            this.mPublisher = publisher;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.lifecycle.LiveData
        public void onActive() {
            super.onActive();
            PublisherLiveData<T>.LiveDataSubscriber liveDataSubscriber = new LiveDataSubscriber();
            this.mSubscriber.set(liveDataSubscriber);
            this.mPublisher.subscribe(liveDataSubscriber);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.lifecycle.LiveData
        public void onInactive() {
            super.onInactive();
            PublisherLiveData<T>.LiveDataSubscriber andSet = this.mSubscriber.getAndSet(null);
            if (andSet != null) {
                andSet.cancelSubscription();
            }
        }

        /* loaded from: classes.dex */
        final class LiveDataSubscriber extends AtomicReference<Subscription> implements Subscriber<T> {
            LiveDataSubscriber() {
            }

            @Override // org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                if (compareAndSet(null, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.cancel();
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(T t) {
                PublisherLiveData.this.postValue(t);
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(final Throwable th) {
                PublisherLiveData.this.mSubscriber.compareAndSet(this, null);
                ArchTaskExecutor.getInstance().executeOnMainThread(new Runnable() { // from class: androidx.lifecycle.LiveDataReactiveStreams.PublisherLiveData.LiveDataSubscriber.1
                    @Override // java.lang.Runnable
                    public void run() {
                        throw new RuntimeException("LiveData does not handle errors. Errors from publishers should be handled upstream and propagated as state", th);
                    }
                });
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                PublisherLiveData.this.mSubscriber.compareAndSet(this, null);
            }

            public void cancelSubscription() {
                Subscription subscription = get();
                if (subscription != null) {
                    subscription.cancel();
                }
            }
        }
    }
}
