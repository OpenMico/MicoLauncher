package com.xiaomi.micolauncher.module.child.childstory;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.L;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildStoryDataManager {
    private String a;
    private String b;
    private List<String> c;

    /* loaded from: classes3.dex */
    private static class a {
        private static final ChildStoryDataManager a = new ChildStoryDataManager();
    }

    public static ChildStoryDataManager getManager() {
        return a.a;
    }

    public Observable<ChildStory> loadStoryDataFromRemote(int i, int i2) {
        if (ApiManager.isInited()) {
            return ApiManager.displayService.getChildStoryFlow(i, i2).doOnNext($$Lambda$ChildStoryDataManager$pJJYt5A176Y8vwZy673wcVC9HLo.INSTANCE);
        }
        L.base.w("ApiManager is not inited!");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ChildStory childStory) throws Exception {
        L.childContent.i("ChildStoryDataManager load data from remote  size  %d", Integer.valueOf(ContainerUtil.getSize(childStory.getBlocks())));
        ApiRealmHelper.getInstance().updateAsync("musicHomePage/kids/flow", Gsons.getGson().toJson(childStory));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Observable<ChildStory> a() {
        return Observable.create($$Lambda$ChildStoryDataManager$oY9AgkQkn_GwrvSrc_FBS4H_mxI.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("musicHomePage/kids/flow");
        if (!TextUtils.isEmpty(find)) {
            L.childContent.i("ChildStoryDataManager load data from cache");
            ChildStory childStory = (ChildStory) Gsons.getGson().fromJson(find, (Class<Object>) ChildStory.class);
            if (childStory != null) {
                observableEmitter.onNext(childStory);
                observableEmitter.onComplete();
                return;
            }
            return;
        }
        observableEmitter.onComplete();
        L.childContent.e("ChildStoryDataManager cache is null");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Observable<String> a(Context context) {
        if (ApiManager.isInited()) {
            return ApiManager.minaService.getStationHistory(Hardware.current(context).getName(), 50, "kid").map(new Function() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildStoryDataManager$fEJfJHQJoTbxfQk9FHO3mWCGviI
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    String b;
                    b = ChildStoryDataManager.this.b((List) obj);
                    return b;
                }
            });
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String b(List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            this.a = ((Station.Item) list.get(0)).getCover();
            ApiRealmHelper.getInstance().updateAsync("musicHomePage/kids/flow/recent/cover", this.a);
        }
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Observable<String> b() {
        if (ApiManager.isInited()) {
            return ApiManager.minaService.getStationCollectedList("kid").map(new Function() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildStoryDataManager$8Rq9_0YsC8H0vaV6Uqqi3bW4jt4
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    String a2;
                    a2 = ChildStoryDataManager.this.a((List) obj);
                    return a2;
                }
            });
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String a(List list) throws Exception {
        this.b = "";
        if (ContainerUtil.hasData(list)) {
            this.b = ((Station.Item) list.get(0)).getCover();
        }
        ApiRealmHelper.getInstance().updateAsync("musicHomePage/kids/flow/collect/cover", this.b);
        return this.b;
    }

    public String getRecentCover() {
        String find = ApiRealmHelper.getInstance().find("musicHomePage/kids/flow/recent/cover");
        return !TextUtils.isEmpty(find) ? find : this.a;
    }

    public String getCollectCover() {
        String find = ApiRealmHelper.getInstance().find("musicHomePage/kids/flow/collect/cover");
        return !TextUtils.isEmpty(find) ? find : this.b;
    }

    public List<String> getBlackList() {
        if (this.c == null) {
            this.c = new ArrayList();
        }
        return this.c;
    }

    public void setBlackList(List<String> list) {
        this.c = list;
    }
}
