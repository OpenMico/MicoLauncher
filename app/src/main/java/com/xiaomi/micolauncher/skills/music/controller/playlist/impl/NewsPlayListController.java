package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import android.content.Context;
import android.media.session.MediaSession;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.ai.api.Common;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.model.api.NewsItem;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class NewsPlayListController extends BasePlaylistController {
    private List<NewsItem> a;
    private NewsItem b;
    private AtomicBoolean c = new AtomicBoolean(false);

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public boolean canRegEventBus() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getMediaType() {
        return 15;
    }

    public NewsPlayListController(Context context, MediaSession mediaSession, AudioPlayer audioPlayer) {
        super(context, mediaSession, audioPlayer);
    }

    public void playList(String str, int i) {
        this.dialogId = null;
        this.a = (List) Gsons.getGson().fromJson(str, new TypeToken<List<NewsItem>>() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.NewsPlayListController.1
        }.getType());
        sendPlaylist(false);
        j();
        playByIndex(i, true);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void loadMore() {
        RecommendDataManager.getManager().refreshNewsList();
        this.c.set(true);
        this.loadMoreDisposable = ApiManager.displayService.getRecommendNewsList(Hardware.current(getContext()).getName(), false).takeUntil(new Predicate() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$NewsPlayListController$zRODcZSme04mpWbPWcj9Sd-AHmQ
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean f;
                f = NewsPlayListController.this.f((List) obj);
                return f;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$NewsPlayListController$XWMZk2d74npAPYih8iNar0fkfv8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                NewsPlayListController.this.e((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$NewsPlayListController$18q1v6dp2n_gc2rHcPDxXpqLksY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                NewsPlayListController.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean f(List list) throws Exception {
        return isReleased();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(List list) throws Exception {
        d(list);
        L.player.d("NewsPlayListController load news data success");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        a(false, 0);
        L.player.e("NewsPlayListController loadNewsData : ", th);
    }

    private void d(List<MainPage.News> list) {
        if (ContainerUtil.hasData(list)) {
            ArrayList arrayList = new ArrayList();
            for (MainPage.News news : list) {
                if ("sound".equals(news.getType())) {
                    arrayList.add(new NewsItem(news));
                }
            }
            this.c.set(false);
            if (ContainerUtil.hasData(arrayList)) {
                this.a.clear();
                this.a.addAll(arrayList);
                sendPlaylist(false);
                playByIndex(0, false);
                return;
            }
            L.player.d("NewsPlayListController newsItems is empty");
            a(false, 0);
            return;
        }
        a(false, 0);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public int getPlaylistCount() {
        List<NewsItem> list = this.a;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public List<Remote.Response.TrackData> sendPlaylist(boolean z) {
        ArrayList arrayList = new ArrayList(this.a.size());
        for (int i = 0; i < this.a.size(); i++) {
            arrayList.add(Remote.Response.TrackData.from(this.a.get(i), i));
        }
        a(arrayList);
        return arrayList;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void doPlay(int i) {
        this.b = ContainerUtil.isOutOfBound((long) i, this.a) ? null : this.a.get(i);
        NewsItem newsItem = this.b;
        if (newsItem == null || TextUtils.isEmpty(newsItem.getPlayUrl())) {
            this.musicIndexHelper.setLoadingMore(false);
            next();
            return;
        }
        a(this.b.getPlayUrl(), (String) null);
        sendMetadata();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    public Remote.Response.PlayingData getPlayingData() {
        return Remote.Response.PlayingData.from(this.b);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController, com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController
    public void release() {
        super.release();
        this.a.clear();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.playlist.impl.BasePlaylistController
    protected Common.AudioType getAudioType() {
        return Common.AudioType.NEWS;
    }
}
