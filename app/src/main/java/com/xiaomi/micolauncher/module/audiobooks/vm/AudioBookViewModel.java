package com.xiaomi.micolauncher.module.audiobooks.vm;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.AudioDiscoveryPage;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.audiobooks.diffutil.AudioBookDiffUtilCallback;
import com.xiaomi.micolauncher.module.audiobooks.model.AudioBookRepository;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class AudioBookViewModel extends AndroidViewModel {
    private final String c;
    private final String d;
    private final String e;
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isErrorOccurred = new MutableLiveData<>();
    public MutableLiveData<List<AudioDiscoveryPage.TabListBean.TabListItemBean>> tablist = new MutableLiveData<>();
    public MutableLiveData<List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean>> items = new MutableLiveData<>();
    private AudioBookRepository a = AudioBookRepository.getInstance();
    private CompositeDisposable b = new CompositeDisposable();

    public AudioBookViewModel(@NonNull Application application) {
        super(application);
        this.c = application.getString(R.string.audio_book_collection);
        this.d = application.getString(R.string.audio_book_paid);
        this.e = application.getString(R.string.audio_book_recent);
    }

    public void getTabList() {
        this.b.add(this.a.getTabList().doOnSubscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$U4vaRO6x2Eyg4Yp9EJbSns4lfAs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.e((Disposable) obj);
            }
        }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$3xwAZxvzx4HdVOlcm9YBGGv0umY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.b((AudioDiscoveryPage.TabListBean) obj);
            }
        }).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$iWplfxTOelHgJp7_ZH2Vf73pRXQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.a((AudioDiscoveryPage.TabListBean) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$X57eLyi43fGhfRFGXeaSn4qhHR0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.e((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(Disposable disposable) throws Exception {
        this.loading.postValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(AudioDiscoveryPage.TabListBean tabListBean) throws Exception {
        this.loading.postValue(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AudioDiscoveryPage.TabListBean tabListBean) throws Exception {
        if (tabListBean != null && ContainerUtil.hasData(tabListBean.getTabList())) {
            AudioDiscoveryPage.TabListBean.TabListItemBean tabListItemBean = new AudioDiscoveryPage.TabListBean.TabListItemBean();
            tabListItemBean.setDesc(this.d);
            tabListItemBean.setName("payment");
            AudioDiscoveryPage.TabListBean.TabListItemBean tabListItemBean2 = new AudioDiscoveryPage.TabListBean.TabListItemBean();
            tabListItemBean2.setDesc(this.c);
            tabListItemBean2.setName("collection");
            AudioDiscoveryPage.TabListBean.TabListItemBean tabListItemBean3 = new AudioDiscoveryPage.TabListBean.TabListItemBean();
            tabListItemBean3.setDesc(this.e);
            tabListItemBean3.setName("recent");
            tabListBean.getTabList().add(0, tabListItemBean3);
            tabListBean.getTabList().add(0, tabListItemBean);
            tabListBean.getTabList().add(0, tabListItemBean2);
            this.tablist.postValue(tabListBean.getTabList());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(Throwable th) throws Exception {
        Logger logger = L.audiobook;
        Object[] objArr = new Object[1];
        objArr[0] = th != null ? th.getMessage() : "null";
        logger.e("getTabList error occurred :%s", objArr);
        this.isErrorOccurred.postValue(true);
    }

    public void getDiscAudioTabFlow(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1741312354) {
            if (str.equals("collection")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != -934918565) {
            if (hashCode == -786681338 && str.equals("payment")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("recent")) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                a();
                return;
            case 1:
                a(getApplication());
                return;
            case 2:
                b();
                return;
            default:
                a(str);
                return;
        }
    }

    private void a(final String str) {
        this.b.add(this.a.getDiscAudioTabFlow(str).doOnSubscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$jxFfqSdJ5hLOpOrAfyRRmWDZ6Wc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.d((Disposable) obj);
            }
        }).doOnComplete(new Action() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$Pd13PBWsHFxoQasKARHYdC-i5GY
            @Override // io.reactivex.functions.Action
            public final void run() {
                AudioBookViewModel.this.f();
            }
        }).doOnError(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$_fNM_OCU1Ketoaxs5zBCJgCxiJU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.d((Throwable) obj);
            }
        }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$gwFdF9fYuHhRWlkNS_vEZc4kVmU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.a(str, (AudioDiscoveryPage.Flow) obj);
            }
        }).subscribe());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Disposable disposable) throws Exception {
        this.loading.postValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f() throws Exception {
        this.loading.postValue(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Throwable th) throws Exception {
        this.isErrorOccurred.postValue(true);
        Logger logger = L.api;
        Object[] objArr = new Object[1];
        objArr[0] = th != null ? th.getMessage() : "";
        logger.e("getDiscAudioTabFlow error occurred, error is %s", objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, AudioDiscoveryPage.Flow flow) throws Exception {
        if (flow == null || flow.getBlocks() == null || flow.getBlocks().size() == 0) {
            this.items.postValue(null);
            return;
        }
        if (DebugHelper.isDebugVersion()) {
            L.audiobook.d("tabName is %s,response is %s", str, Gsons.getGson().toJson(flow.getBlocks()));
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < flow.getBlocks().size(); i++) {
            AudioDiscoveryPage.Flow.BlocksBean blocksBean = flow.getBlocks().get(i);
            ArrayList arrayList2 = new ArrayList();
            if (i != 0) {
                arrayList2.add(new AudioDiscoveryPage.Flow.BlocksBean.ItemsBean(blocksBean.getTitle()));
            }
            if (ContainerUtil.hasData(blocksBean.getItems())) {
                arrayList2.addAll(blocksBean.getItems());
            }
            arrayList.addAll(arrayList2);
        }
        this.items.postValue(arrayList);
    }

    private void a() {
        this.b.add(this.a.getPaymentAudioBooks().map(new Function() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$0K7J2lW_flaxT-_usyNozWNqLMM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List a;
                a = AudioBookViewModel.this.a((Order) obj);
                return a;
            }
        }).doOnSubscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$s6pSd46cSBCZTnwKUcVF9VIQdss
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.c((Disposable) obj);
            }
        }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$99oxgAGaWhgdkrX4-Y3gTT6s2LU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.e((List) obj);
            }
        }).doOnComplete(new Action() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$vcIxANBQ9l71PrZFg2NhqbftO8w
            @Override // io.reactivex.functions.Action
            public final void run() {
                AudioBookViewModel.this.e();
            }
        }).doOnError(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$sGioY86mckBLQX68qA-sUxMgIQM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.c((Throwable) obj);
            }
        }).subscribe());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List a(Order order) throws Exception {
        return a((List<Station.Item>) null, order.list, "getPaymentAudioBooks %s");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Disposable disposable) throws Exception {
        this.loading.postValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(List list) throws Exception {
        this.items.postValue(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() throws Exception {
        this.loading.postValue(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Throwable th) throws Exception {
        Logger logger = L.audiobook;
        Object[] objArr = new Object[1];
        objArr[0] = th != null ? th.getMessage() : "";
        logger.e("getPaymentAudioBooks throws error %s", objArr);
        this.isErrorOccurred.postValue(true);
    }

    private void b() {
        this.b.add(this.a.getCollectAudioBooks().map(new Function() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$5DxYuX59wFM8uktvyrTvXFbwPOY
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List d;
                d = AudioBookViewModel.this.d((List) obj);
                return d;
            }
        }).doOnSubscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$wEvoZLgVgKiDWqhphX8WSjn919s
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.b((Disposable) obj);
            }
        }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$ZKM_OcrPdxYKNhWgtLrcK2tCcZc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.c((List) obj);
            }
        }).doOnError(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$Co6BXDEZ1q31AYHihRaMpvt7iFw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.b((Throwable) obj);
            }
        }).doOnComplete(new Action() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$i6oOBtTWPeH7lzIF7IW86rmiodI
            @Override // io.reactivex.functions.Action
            public final void run() {
                AudioBookViewModel.this.d();
            }
        }).subscribe());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List d(List list) throws Exception {
        return a(list, (List<Order.OrderInfo>) null, "getCollectAudioBooks %s");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Disposable disposable) throws Exception {
        this.loading.postValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(List list) throws Exception {
        this.items.postValue(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Throwable th) throws Exception {
        Logger logger = L.audiobook;
        Object[] objArr = new Object[1];
        objArr[0] = th != null ? th.getMessage() : "";
        logger.e("getCollectAudioBooks throws error %s", objArr);
        this.isErrorOccurred.postValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() throws Exception {
        this.loading.postValue(false);
    }

    private void a(Context context) {
        this.b.add(this.a.getHistory(context).map(new Function() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$OC9JBcu_imps7aO7pW5MoFooI4w
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List b;
                b = AudioBookViewModel.this.b((List) obj);
                return b;
            }
        }).doOnError(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$2UbJUxqXNxeIodeee231Hd8k0xE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.a((Throwable) obj);
            }
        }).doOnSubscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$G9wKpyZr7BEExrWVx80eO_e8Hp0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.a((Disposable) obj);
            }
        }).doOnComplete(new Action() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$Hbtemd-8hCLQgRJFOKWghLcZkB8
            @Override // io.reactivex.functions.Action
            public final void run() {
                AudioBookViewModel.this.c();
            }
        }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$Bkk75-k63GJkgxkuqsYqpgC1qDw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookViewModel.this.a((List) obj);
            }
        }).subscribe());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List b(List list) throws Exception {
        return a(list, (List<Order.OrderInfo>) null, "getHistory %s");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        Logger logger = L.audiobook;
        Object[] objArr = new Object[1];
        objArr[0] = th != null ? th.getMessage() : "";
        logger.e("getHistory throws error %s", objArr);
        this.isErrorOccurred.postValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Disposable disposable) throws Exception {
        this.loading.postValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() throws Exception {
        this.loading.postValue(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        this.items.postValue(list);
    }

    @NotNull
    private List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> a(List<Station.Item> list, List<Order.OrderInfo> list2, String str) {
        if (DebugHelper.isDebugVersion()) {
            L.audiobook.d(str, Gsons.getGson().toJson(list));
        }
        ArrayList arrayList = new ArrayList();
        if (ContainerUtil.hasData(list)) {
            for (Station.Item item : list) {
                arrayList.add(AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.build(item));
            }
        } else if (ContainerUtil.hasData(list2)) {
            for (Order.OrderInfo orderInfo : list2) {
                arrayList.add(AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.build(orderInfo));
            }
        }
        return arrayList;
    }

    public Observable<DiffUtil.DiffResult> calcDiffResult(final List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> list, final List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> list2) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.audiobooks.vm.-$$Lambda$AudioBookViewModel$JlVTzUgsadEwQw2z4d3O87BIZ7I
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AudioBookViewModel.a(list, list2, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, List list2, ObservableEmitter observableEmitter) throws Exception {
        try {
            try {
                observableEmitter.onNext(DiffUtil.calculateDiff(new AudioBookDiffUtilCallback(list, list2)));
            } catch (Exception e) {
                L.audiobook.e("calcDiffResult error occurred %s", e);
                observableEmitter.onError(e);
            }
        } finally {
            observableEmitter.onComplete();
        }
    }

    public boolean isImmutableTabFlow(String str) {
        return !TextUtils.equals(str, "collection") && !TextUtils.equals(str, "payment") && !TextUtils.equals(str, "recent");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        this.a.destroy();
        this.b.dispose();
    }
}
