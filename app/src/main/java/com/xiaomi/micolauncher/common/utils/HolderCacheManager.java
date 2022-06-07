package com.xiaomi.micolauncher.common.utils;

import android.os.SystemClock;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.event.MainPageVisibilityEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.util.LinkedList;

/* loaded from: classes3.dex */
public class HolderCacheManager {
    private int a;
    private int b;

    /* loaded from: classes3.dex */
    private static class a {
        private static final HolderCacheManager a = new HolderCacheManager();
    }

    public static HolderCacheManager getManager() {
        return a.a;
    }

    private HolderCacheManager() {
        this.a = 6;
        this.b = 0;
    }

    public void initViewHolder(final ObservableOnSubscribe observableOnSubscribe) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$HolderCacheManager$Yjj53V39dRXPXLBWdCtq-eOMZog
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HolderCacheManager.a(ObservableOnSubscribe.this, observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$HolderCacheManager$0nz7cbDpf51Ab4c3ELULT5Bn4AU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HolderCacheManager.this.a(obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$HolderCacheManager$8zGEDtnI0dOkTsPTc58Ed8oPy78
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HolderCacheManager.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObservableOnSubscribe observableOnSubscribe, ObservableEmitter observableEmitter) throws Exception {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        observableOnSubscribe.subscribe(observableEmitter);
        observableEmitter.onNext(1);
        Logger logger = L.startUpProfile;
        logger.d("initViewHolder: " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        setMainPageVisibility();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        setMainPageVisibility();
        L.exception.e("initViewHolder e:", th);
    }

    public void setMainPageVisibility() {
        L.homepage.d("setMainPageVisibility");
        this.b++;
        if (this.b >= this.a) {
            EventBusRegistry.getEventBus().postSticky(new MainPageVisibilityEvent(1));
        }
    }

    public static <T extends BaseViewHolder> void prepareViewHolders(int i, @NonNull LinkedList<T> linkedList, @NonNull ViewHolderFactory<T> viewHolderFactory) {
        if (linkedList != null) {
            linkedList.clear();
            for (int i2 = 0; i2 < i; i2++) {
                synchronized (linkedList) {
                    linkedList.add(viewHolderFactory.createViewHolder());
                }
            }
        }
    }

    public static <T extends BaseViewHolder> T fetchViewHolder(@NonNull LinkedList<T> linkedList, @Nullable ViewHolderFactory<T> viewHolderFactory) {
        T t;
        if (linkedList != null) {
            synchronized (linkedList) {
                if (!linkedList.isEmpty()) {
                    t = linkedList.poll();
                } else {
                    t = null;
                }
            }
        } else {
            t = null;
        }
        if (t == null) {
            if (viewHolderFactory == null) {
                return null;
            }
            t = viewHolderFactory.createViewHolder();
        }
        t.initInMain();
        return t;
    }

    public static <T extends View> void prepareViews(int i, @NonNull LinkedList<T> linkedList, @NonNull ViewFactory<T> viewFactory) {
        if (linkedList != null) {
            linkedList.clear();
            for (int i2 = 0; i2 < i; i2++) {
                synchronized (linkedList) {
                    linkedList.add(viewFactory.createView());
                }
            }
        }
    }

    public static <T extends View> T fetchView(@NonNull LinkedList<T> linkedList, @Nullable ViewFactory<T> viewFactory) {
        T t;
        if (linkedList != null) {
            synchronized (linkedList) {
                if (!linkedList.isEmpty()) {
                    t = linkedList.poll();
                } else {
                    t = null;
                }
            }
        } else {
            t = null;
        }
        if (t != null) {
            return t;
        }
        if (viewFactory == null) {
            return null;
        }
        return viewFactory.createView();
    }
}
