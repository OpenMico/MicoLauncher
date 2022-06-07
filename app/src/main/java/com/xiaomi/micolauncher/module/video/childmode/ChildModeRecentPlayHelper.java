package com.xiaomi.micolauncher.module.video.childmode;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.bean.RecentPlayItem;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildModeRecentPlayHelper {
    public static final String TAG = "ChildModeRecentPlayHelper";
    private static final long a = TimeUnit.HOURS.toMillis(8);
    private Context e;
    private long f;
    private List<RecentPlayItem> c = new ArrayList();
    private a d = new a(this);
    private final CompositeDisposable b = new CompositeDisposable();

    public ChildModeRecentPlayHelper(Context context) {
        this.e = context;
    }

    public void loadRecentPlayList(final Context context) {
        if (System.currentTimeMillis() - this.f >= a) {
            Observable.concat(ApiManager.minaService.audioHistory().flatMap($$Lambda$ChildModeRecentPlayHelper$ZXFG0brgKo7rnQRY_zeGxiX5Fs.INSTANCE), ApiManager.minaService.getStationHistory(Hardware.current(context).getName(), 50, "kid"), VideoDataManager.getManager().loadVideoListFromDBInSevenDays()).subscribeOn(MicoSchedulers.io()).subscribe(new Observer<List<?>>() { // from class: com.xiaomi.micolauncher.module.video.childmode.ChildModeRecentPlayHelper.1
                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    L.childmode.v(" %s loadRecentPlayList onSubscribe", ChildModeRecentPlayHelper.TAG);
                    ChildModeRecentPlayHelper.this.c.clear();
                }

                /* renamed from: a */
                public void onNext(List<?> list) {
                    ChildModeRecentPlayHelper.this.a(list, context);
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    L.childmode.e("loadRecentPlayList failed", th);
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    L.childmode.v("loadRecentPlayList onComplete");
                    if (ContainerUtil.isEmpty(ChildModeRecentPlayHelper.this.c)) {
                        ChildModeRecentPlayHelper.this.a(context);
                    }
                    ChildModeRecentPlayHelper.this.f = System.currentTimeMillis();
                    ChildModeRecentPlayHelper.this.d.sendEmptyMessageDelayed(1, ChildModeRecentPlayHelper.a);
                    EventBusRegistry.getEventBus().post(new RecommendEvent.ChildRecentPlayUpdated());
                }
            });
        }
    }

    public static /* synthetic */ ObservableSource a(List list) throws Exception {
        return ApiManager.minaService.getSongInfo(Gsons.getGson().toJson(list));
    }

    public void a(List<?> list, Context context) {
        if (!ContainerUtil.isEmpty(list)) {
            Object obj = list.get(0);
            if (obj instanceof Music.Song) {
                this.c.add(new RecentPlayItem(context, (Music.Song) list.get(new Random().nextInt(list.size()))));
            } else if (obj instanceof Station.Item) {
                long earliestTime = getEarliestTime();
                Iterator<?> it = list.iterator();
                while (it.hasNext()) {
                    Station.Item item = (Station.Item) it.next();
                    if (item.getUpdateTime() >= earliestTime) {
                        this.c.add(new RecentPlayItem(item, context));
                    }
                }
            } else if (obj instanceof VideoItem) {
                this.c.add(new RecentPlayItem((VideoItem) obj, context));
            }
        }
    }

    public void a(final Context context) {
        this.c.add(new RecentPlayItem(context.getString(R.string.listen_child_song), RecentPlayItem.ITEM_TYPE_CHILD_SONG, R.drawable.music, "http://y.gtimg.cn/music/photo/radio/track_radio_341_10_5.jpg", R.drawable.music_title, new RecentPlayItem.ItemClickHandler() { // from class: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeRecentPlayHelper$ehYwXe-gIVodkpp4H36BYHNj_J8
            @Override // com.xiaomi.micolauncher.module.video.childmode.bean.RecentPlayItem.ItemClickHandler
            public final void handleItemClick() {
                SchemaManager.handleSchema(context, "mico://homepage/songbook?type=radio&id=341");
            }
        }));
        this.c.add(new RecentPlayItem(context.getString(R.string.story_recommendation), RecentPlayItem.ITEM_TYPE_CHILD_STORY, R.drawable.story, "", R.drawable.story_title, $$Lambda$ChildModeRecentPlayHelper$tHmDnnXUsnL9Wxelfn0EGLHd2k.INSTANCE));
    }

    public static /* synthetic */ void b() {
        SpeechManager.getInstance().nlpRequest("播放故事");
    }

    public static long getEarliestTime() {
        return SystemClock.currentThreadTimeMillis() - TimeUnit.DAYS.toMillis(7L);
    }

    public List getRecentPlayList() {
        return this.c;
    }

    public void release() {
        this.b.dispose();
        this.d.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends Handler {
        private final WeakReference<ChildModeRecentPlayHelper> a;

        a(ChildModeRecentPlayHelper childModeRecentPlayHelper) {
            this.a = new WeakReference<>(childModeRecentPlayHelper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            ChildModeRecentPlayHelper childModeRecentPlayHelper = this.a.get();
            if (childModeRecentPlayHelper != null) {
                childModeRecentPlayHelper.loadRecentPlayList(childModeRecentPlayHelper.e);
            }
        }
    }
}
