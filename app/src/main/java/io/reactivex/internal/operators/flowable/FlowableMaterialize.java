package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableMaterialize<T> extends a<T, Notification<T>> {
    public FlowableMaterialize(Flowable<T> flowable) {
        super(flowable);
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Notification<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber));
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends SinglePostCompleteSubscriber<T, Notification<T>> {
        private static final long serialVersionUID = -3740826063558713822L;

        a(Subscriber<? super Notification<T>> subscriber) {
            super(subscriber);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.produced++;
            this.downstream.onNext(Notification.createOnNext(t));
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            complete(Notification.createOnError(th));
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            complete(Notification.createOnComplete());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public void onDrop(Notification<T> notification) {
            if (notification.isOnError()) {
                RxJavaPlugins.onError(notification.getError());
            }
        }
    }
}
