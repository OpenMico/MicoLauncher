package com.xiaomi.micolauncher.module.appstore.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import com.xiaomi.micolauncher.module.appstore.manager.Rx2ServiceBindingFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/* loaded from: classes3.dex */
public class Rx2ServiceBindingFactory {
    public <B extends Binder> Observable<B> bind(Context context, Intent intent) {
        return bind(context, intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ a a() throws Exception {
        return new a();
    }

    public <B extends Binder> Observable<B> bind(final Context context, final Intent intent, final int i) {
        $$Lambda$Rx2ServiceBindingFactory$0ESf6ZSqt9AXNeQqnmKwatJuBbo r0 = $$Lambda$Rx2ServiceBindingFactory$0ESf6ZSqt9AXNeQqnmKwatJuBbo.INSTANCE;
        Function function = new Function() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$Rx2ServiceBindingFactory$GNOHbVvzW3XipBKekMGCY6ulA2k
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = Rx2ServiceBindingFactory.a(context, intent, i, (Rx2ServiceBindingFactory.a) obj);
                return a2;
            }
        };
        context.getClass();
        return Observable.using(r0, function, new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$GR9QJ-xveR0NAwasw5mCZ6DuOuQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                context.unbindService((Rx2ServiceBindingFactory.a) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(Context context, Intent intent, int i, a aVar) throws Exception {
        context.bindService(intent, aVar, i);
        return Observable.create(aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a<B extends Binder> implements ServiceConnection, ObservableOnSubscribe<B> {
        private ObservableEmitter<? super B> a;

        private a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ObservableEmitter<? super B> observableEmitter = this.a;
            if (observableEmitter != null && iBinder != null) {
                observableEmitter.onNext((Binder) iBinder);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            ObservableEmitter<? super B> observableEmitter = this.a;
            if (observableEmitter != null) {
                observableEmitter.onComplete();
            }
        }

        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(ObservableEmitter<B> observableEmitter) throws Exception {
            this.a = observableEmitter;
        }
    }
}
