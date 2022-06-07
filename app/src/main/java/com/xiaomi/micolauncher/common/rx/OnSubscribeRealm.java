package com.xiaomi.micolauncher.common.rx;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

/* loaded from: classes3.dex */
public abstract class OnSubscribeRealm<T> implements ObservableOnSubscribe<T> {
    private ObservableEmitter emitter = null;
    private final boolean ignoreError;

    public abstract Realm getRealm();

    public abstract T operate(Realm realm);

    /* JADX INFO: Access modifiers changed from: protected */
    public OnSubscribeRealm(boolean z) {
        this.ignoreError = z;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public void subscribe(ObservableEmitter<T> observableEmitter) {
        Realm realm = getRealm();
        if (realm == null) {
            sendOnError(new Throwable("realm is empty"));
            return;
        }
        this.emitter = observableEmitter;
        boolean z = false;
        T t = null;
        try {
            realm.beginTransaction();
            t = operate(realm);
            if (t != null) {
                realm.commitTransaction();
            } else {
                realm.cancelTransaction();
            }
        } catch (Error e) {
            realm.cancelTransaction();
            if (!this.ignoreError) {
                sendOnError(e);
                z = true;
            }
        } catch (RuntimeException e2) {
            realm.cancelTransaction();
            if (!this.ignoreError) {
                sendOnError(new RealmException("Error during transaction.", e2));
                z = true;
            }
        }
        if (!z && t != null) {
            sendOnNext(t);
        }
        try {
            realm.close();
        } catch (RealmException e3) {
            if (!this.ignoreError) {
                sendOnError(e3);
                z = true;
            }
        }
        if (z) {
            return;
        }
        if (t != null) {
            sendOnCompleted();
        } else {
            sendOnError(new RealmException("Error object is null"));
        }
    }

    private void sendOnNext(T t) {
        ObservableEmitter observableEmitter = this.emitter;
        if (observableEmitter != null) {
            observableEmitter.onNext(t);
        }
    }

    private void sendOnError(Throwable th) {
        ObservableEmitter observableEmitter = this.emitter;
        if (observableEmitter != null) {
            observableEmitter.onError(th);
            this.emitter = null;
        }
    }

    private void sendOnCompleted() {
        ObservableEmitter observableEmitter = this.emitter;
        if (observableEmitter != null) {
            observableEmitter.onComplete();
            this.emitter = null;
        }
    }
}
