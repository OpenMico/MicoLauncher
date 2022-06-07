package com.xiaomi.micolauncher.module.video.childmode;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.elvishew.xlog.Logger;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.Music;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.api.service.MinaService;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.main.FadeInCardView;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.RecommendationLoadResultHelper;
import com.xiaomi.micolauncher.module.video.childmode.bean.RecommendPlayItem;
import com.xiaomi.micolauncher.skills.video.model.RecommendCartoon;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildModeRecommendApiHelper {
    public static final String TAG = "ChildModeRecommendApiHelper";
    private static final long a = TimeUnit.HOURS.toMillis(8);
    private long b;
    private CompositeDisposable c;
    private SparseArray<RecommendList> d;
    private Set<c> e;
    private b f;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        public static final ChildModeRecommendApiHelper a = new ChildModeRecommendApiHelper();
    }

    /* loaded from: classes3.dex */
    public enum c {
        CHILD_SONG,
        CHILD_STORY,
        CHILD_CARTOON
    }

    public static ChildModeRecommendApiHelper getInstance() {
        return a.a;
    }

    private ChildModeRecommendApiHelper() {
        this.d = new SparseArray<>(3);
        this.e = new HashSet();
        this.f = new b(this);
        this.c = new CompositeDisposable();
        this.d.put(1, new RecommendList());
        this.d.put(2, new RecommendList());
        this.d.put(3, new RecommendList());
    }

    public void loadRecommendForChildMode(Context context) {
        if (ChildModeManager.getManager().isChildMode() && !a() && NetworkUtil.isWifiConnected(context)) {
            this.b = System.currentTimeMillis();
            c(context);
            b(context);
            a(context);
        }
    }

    public void loadRecommendForChildModeForce(Context context) {
        if (!a() && NetworkUtil.isWifiConnected(context)) {
            this.b = System.currentTimeMillis();
            c(context);
            b(context);
            a(context);
        }
    }

    private void a(final Context context) {
        if (ApiManager.isInited()) {
            new RecommendationLoadResultHelper(ApiManager.displayService.getRecommendCartoonsForChildMode()).a(this.c, "loadChildCartoon", new RecommendationLoadResultHelper.DataConvertInterface<RecommendCartoon>() { // from class: com.xiaomi.micolauncher.module.video.childmode.ChildModeRecommendApiHelper.1
                /* renamed from: a */
                public RecommendPlayItem convertItem(RecommendCartoon recommendCartoon) {
                    return new RecommendPlayItem(recommendCartoon);
                }

                @Override // com.xiaomi.micolauncher.module.video.childmode.RecommendationLoadResultHelper.DataConvertInterface
                public void onLoadFinish(boolean z, ArrayList<RecommendPlayItem> arrayList, ArrayList<RecommendPlayItem> arrayList2) {
                    ChildModeRecommendApiHelper.this.a(context, c.CHILD_CARTOON, arrayList, arrayList2);
                }
            });
        }
    }

    private void b(final Context context) {
        if (ApiManager.isInited()) {
            new RecommendationLoadResultHelper(ApiManager.displayService.getStationRecommend()).a(this.c, "loadChildStory", new RecommendationLoadResultHelper.DataConvertInterface<Station.Item>() { // from class: com.xiaomi.micolauncher.module.video.childmode.ChildModeRecommendApiHelper.2
                /* renamed from: a */
                public RecommendPlayItem convertItem(Station.Item item) {
                    return new RecommendPlayItem(item);
                }

                @Override // com.xiaomi.micolauncher.module.video.childmode.RecommendationLoadResultHelper.DataConvertInterface
                public void onLoadFinish(boolean z, ArrayList<RecommendPlayItem> arrayList, ArrayList<RecommendPlayItem> arrayList2) {
                    ChildModeRecommendApiHelper.this.a(context, c.CHILD_STORY, arrayList, arrayList2);
                }
            });
        }
    }

    private void c(final Context context) {
        Observable<List<PatchWall.Item>> b2 = b();
        if (b2 != null) {
            new RecommendationLoadResultHelper(b2).a(this.c, "loadChildSong", new RecommendationLoadResultHelper.DataConvertInterface<PatchWall.Item>() { // from class: com.xiaomi.micolauncher.module.video.childmode.ChildModeRecommendApiHelper.3
                /* renamed from: a */
                public RecommendPlayItem convertItem(PatchWall.Item item) {
                    return new RecommendPlayItem(item);
                }

                @Override // com.xiaomi.micolauncher.module.video.childmode.RecommendationLoadResultHelper.DataConvertInterface
                public void onLoadFinish(boolean z, ArrayList<RecommendPlayItem> arrayList, ArrayList<RecommendPlayItem> arrayList2) {
                    ChildModeRecommendApiHelper.this.a(context, c.CHILD_SONG, arrayList, arrayList2);
                    ChildModeRecommendApiHelper.this.a("CHILD_SONG_FIRST_HALF", arrayList);
                    ChildModeRecommendApiHelper.this.a("CHILD_SONG_LAST_HALF", arrayList2);
                }
            });
        }
    }

    public void a(final String str, final ArrayList<RecommendPlayItem> arrayList) {
        boolean hasData = ContainerUtil.hasData(arrayList);
        Logger logger = L.childmode;
        logger.v(TAG + " updateChildSongDB hasData = " + hasData);
        if (hasData) {
            Threads.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeRecommendApiHelper$w801eZbmeasl3E8M48PelTtrCRk
                @Override // java.lang.Runnable
                public final void run() {
                    ChildModeRecommendApiHelper.a(arrayList, str);
                }
            });
        } else {
            Threads.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeRecommendApiHelper$Fl9CHq1O9yUikg4OmX-xeWYUshQ
                @Override // java.lang.Runnable
                public final void run() {
                    ChildModeRecommendApiHelper.this.a(str);
                }
            });
        }
    }

    public static /* synthetic */ void a(ArrayList arrayList, String str) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(new PatchWall.Item((RecommendPlayItem) it.next()));
        }
        ApiRealmHelper.getInstance().updateAsync(str, Gsons.getGson().toJson(arrayList2));
    }

    public /* synthetic */ void a(String str) {
        String find = ApiRealmHelper.getInstance().find(str);
        Logger logger = L.childmode;
        logger.v(TAG + " updateChildSongDB cache: " + find);
        if (!TextUtils.isEmpty(find)) {
            try {
                ArrayList arrayList = (ArrayList) Gsons.getGson().fromJson(find, new TypeToken<ArrayList<PatchWall.Item>>() { // from class: com.xiaomi.micolauncher.module.video.childmode.ChildModeRecommendApiHelper.4
                }.getType());
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(new RecommendPlayItem((PatchWall.Item) it.next()));
                }
                Logger logger2 = L.childmode;
                logger2.v(TAG + " updateChildSongDB leftList.size = " + arrayList.size());
                this.d.get(1).leftList = arrayList2;
                EventBusRegistry.getEventBus().post(new RecommendEvent.ChildRecommendUpdated());
            } catch (Exception e) {
                Logger logger3 = L.childmode;
                logger3.e(TAG + " updateChildSongDB failed", e);
                e.printStackTrace();
            }
        }
    }

    public void a(Context context, c cVar, ArrayList<RecommendPlayItem> arrayList, ArrayList<RecommendPlayItem> arrayList2) {
        switch (cVar) {
            case CHILD_SONG:
                this.d.get(1).leftList = arrayList;
                this.d.get(2).rightList = arrayList2;
                break;
            case CHILD_STORY:
                this.d.get(1).rightList = arrayList;
                this.d.get(3).leftList = arrayList2;
                break;
            case CHILD_CARTOON:
                this.d.get(2).leftList = arrayList;
                this.d.get(3).rightList = arrayList2;
                break;
        }
        a(context, cVar);
    }

    private boolean a() {
        return Math.abs(System.currentTimeMillis() - this.b) < a;
    }

    private void a(Context context, c cVar) {
        this.e.add(cVar);
        if (this.e.size() == 3) {
            EventBusRegistry.getEventBus().post(new RecommendEvent.ChildRecommendUpdated());
            Message obtain = Message.obtain();
            obtain.obj = context;
            obtain.what = 0;
            this.f.sendMessageDelayed(obtain, a);
            this.e.clear();
        }
    }

    private Observable<List<PatchWall.Item>> b() {
        if (ApiManager.isInited()) {
            return ApiManager.displayService.getMusicCategoryList().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeRecommendApiHelper$mQ0q5LZCCwHoHgbKnxJJZ7nqWao
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource a2;
                    a2 = ChildModeRecommendApiHelper.this.a((List) obj);
                    return a2;
                }
            });
        }
        return null;
    }

    public /* synthetic */ ObservableSource a(List list) throws Exception {
        if (!ContainerUtil.isEmpty(list)) {
            return a((Music.Category) list.get(0));
        }
        L.childmode.e("ChildModeRecommendApiHelper#loadCategoryListFromRemote categories is empty");
        return Observable.just(new ArrayList());
    }

    private Observable<List<PatchWall.Item>> a(Music.Category category) {
        long categoryId = category.getCategoryId();
        String categoryName = category.getCategoryName();
        MinaService minaService = ApiManager.minaService;
        return minaService.getMusicCategoryList(categoryId + "", categoryName, 0, 40).map($$Lambda$ChildModeRecommendApiHelper$MUNigCH8iG6Cv2snDsIPIRaHnvQ.INSTANCE);
    }

    public void release() {
        this.c.dispose();
        this.f.removeCallbacksAndMessages(null);
        this.b = 0L;
    }

    @Nullable
    public RecommendList getRecommend(int i) {
        return this.d.get(i);
    }

    /* loaded from: classes3.dex */
    public static class RecommendList {
        public List<? extends FadeInCardView.FadeInCardItem> leftList = new ArrayList();
        public List<? extends FadeInCardView.FadeInCardItem> rightList = new ArrayList();

        RecommendList() {
        }
    }

    /* loaded from: classes3.dex */
    public static class b extends Handler {
        private final WeakReference<ChildModeRecommendApiHelper> a;

        b(ChildModeRecommendApiHelper childModeRecommendApiHelper) {
            super(Looper.getMainLooper());
            this.a = new WeakReference<>(childModeRecommendApiHelper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            ChildModeRecommendApiHelper childModeRecommendApiHelper = this.a.get();
            if (childModeRecommendApiHelper != null) {
                childModeRecommendApiHelper.loadRecommendForChildMode((Context) message.obj);
            }
        }
    }
}
