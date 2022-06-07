package com.xiaomi.micolauncher.module.video.childmode;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.video.childmode.RecommendationLoadResultHelper;
import com.xiaomi.micolauncher.module.video.childmode.bean.RecommendPlayItem;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class RecommendationLoadResultHelper<E> {
    private Observable<List<E>> a;
    private long b;

    /* loaded from: classes3.dex */
    public interface DataConvertInterface<E> {
        RecommendPlayItem convertItem(E e);

        void onLoadFinish(boolean z, ArrayList<RecommendPlayItem> arrayList, ArrayList<RecommendPlayItem> arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RecommendationLoadResultHelper(Observable<List<E>> observable) {
        this.a = observable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(CompositeDisposable compositeDisposable, final String str, final DataConvertInterface<E> dataConvertInterface) {
        L.childmode.i("start %s list", str);
        this.b = System.currentTimeMillis();
        compositeDisposable.add(this.a.subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$RecommendationLoadResultHelper$XHJYTctiyA9YQmHHJvMYfrenE70
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendationLoadResultHelper.this.a(dataConvertInterface, str, (List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$RecommendationLoadResultHelper$NXAYgY5KPjt6enDO8gX-pMA6_YY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendationLoadResultHelper.a(RecommendationLoadResultHelper.DataConvertInterface.this, str, (Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(DataConvertInterface dataConvertInterface, String str, List list) throws Exception {
        a(list, dataConvertInterface);
        L.childmode.i("%s onSuccess list.size: %d, cost time: %d s", str, Integer.valueOf(list.size()), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - this.b)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(DataConvertInterface dataConvertInterface, String str, Throwable th) throws Exception {
        dataConvertInterface.onLoadFinish(false, new ArrayList<>(), new ArrayList<>());
        L.childmode.e(String.format(Locale.getDefault(), "%s onFailed: ", str), th);
    }

    private void a(List<E> list, DataConvertInterface<E> dataConvertInterface) {
        int i;
        if (ContainerUtil.isEmpty(list)) {
            dataConvertInterface.onLoadFinish(true, new ArrayList<>(), new ArrayList<>());
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (E e : list) {
            arrayList.add(dataConvertInterface.convertItem(e));
        }
        int size = list.size();
        if (list.size() == 1) {
            dataConvertInterface.onLoadFinish(true, new ArrayList<>(arrayList), new ArrayList<>());
            return;
        }
        if (size % 2 == 0) {
            i = size / 2;
        } else {
            i = (size / 2) + 1;
        }
        dataConvertInterface.onLoadFinish(true, new ArrayList<>(arrayList.subList(0, i)), new ArrayList<>(arrayList.subList(i, size)));
    }
}
