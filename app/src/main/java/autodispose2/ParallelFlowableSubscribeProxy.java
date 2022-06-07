package autodispose2;

import org.reactivestreams.Subscriber;

/* loaded from: classes.dex */
public interface ParallelFlowableSubscribeProxy<T> {
    void subscribe(Subscriber<? super T>[] subscriberArr);
}
