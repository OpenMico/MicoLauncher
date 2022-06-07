package com.xiaomi.micolauncher.skills.music.controller;

import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChannelManager {
    private static final Comparator<Music.Channel> a = new Comparator<Music.Channel>() { // from class: com.xiaomi.micolauncher.skills.music.controller.ChannelManager.1
        /* renamed from: a */
        public int compare(Music.Channel channel, Music.Channel channel2) {
            if (!channel2.isDefault || channel.isDefault) {
                return (!channel.isDefault || channel2.isDefault) ? 0 : -1;
            }
            return 1;
        }
    };
    private List<Music.Channel> b;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static final ChannelManager a = new ChannelManager();
    }

    private ChannelManager() {
        this.b = new LinkedList();
        loadChannelList(true);
    }

    public static ChannelManager getInstance() {
        return a.a;
    }

    private Observable<Long> a() {
        return loadChannelList(false).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$aIPK-LSzegIfQXv6_w0mp_sm3e8
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                Long c;
                c = ChannelManager.this.c((List) obj);
                return c;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Long c(List list) throws Exception {
        long j;
        Iterator<Music.Channel> it = this.b.iterator();
        while (true) {
            if (!it.hasNext()) {
                j = 0;
                break;
            }
            Music.Channel next = it.next();
            if (next.isDefault) {
                j = next.id;
                break;
            }
        }
        return Long.valueOf(j);
    }

    public Observable<Boolean> removeFavourite(final Music.AudioInfo audioInfo) {
        return a().flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$P_PqykP5rPUfdoiJXKBqah1NKYA
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = ChannelManager.this.b(audioInfo, (Long) obj);
                return b;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource b(Music.AudioInfo audioInfo, Long l) throws Exception {
        return delCollectAudio(l.longValue(), audioInfo);
    }

    public Observable<Boolean> addToFavourite(final Music.AudioInfo audioInfo) {
        return a().flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$9y9GJWBSVXIb4_QLbUS19OJbNP4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ChannelManager.this.a(audioInfo, (Long) obj);
                return a2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(Music.AudioInfo audioInfo, Long l) throws Exception {
        return addCollectAudio(l.longValue(), audioInfo);
    }

    public Observable<Boolean> addCollectAudio(final long j, final Music.AudioInfo audioInfo) {
        return loadChannelList(false).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$IxHSX77uPeW_gZ1E1B05GgRklQA
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = ChannelManager.b(Music.AudioInfo.this, j, (List) obj);
                return b;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource b(Music.AudioInfo audioInfo, long j, List list) throws Exception {
        return ApiHelper.addCollectV3(audioInfo.audioID, Long.toString(j));
    }

    public Observable<Boolean> delCollectAudio(final long j, final Music.AudioInfo audioInfo) {
        return loadChannelList(false).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$Fu77CDEew48Qtmg-_fEPtYanXB4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ChannelManager.a(Music.AudioInfo.this, j, (List) obj);
                return a2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(Music.AudioInfo audioInfo, long j, List list) throws Exception {
        return ApiHelper.delCollectV3(audioInfo.audioID, Long.toString(j));
    }

    public Observable<Boolean> addSongs(final long j, final List<Music.Song> list) {
        return loadChannelList(false).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$s03XK6MuCAIe9B2OB5R35Qlbuhw
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource addSongs;
                List list2 = (List) obj;
                addSongs = ApiHelper.addSongs(j, list);
                return addSongs;
            }
        }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$x6kQA0Hl-Cu-I9DIoSQAnfMXctg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChannelManager.this.b((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Boolean bool) throws Exception {
        loadChannelList(true).subscribe(new DefaultObserver<List<Music.Channel>>() { // from class: com.xiaomi.micolauncher.skills.music.controller.ChannelManager.2
            /* renamed from: a */
            public void onSuccess(List<Music.Channel> list) {
            }
        });
    }

    public Observable<List<Music.Channel>> loadChannelList(boolean z) {
        List<Music.Channel> list = this.b;
        if (list == null || list.size() == 0) {
            z = true;
        }
        if (!z) {
            return Observable.just(this.b);
        }
        this.b.clear();
        return ApiManager.minaService.getChannelList().map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$z9FJq5E_J7ursYcqDg0o4n8T5CI
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List b;
                b = ChannelManager.this.b((List) obj);
                return b;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List b(List list) throws Exception {
        if (list != null) {
            this.b.addAll(list);
            Collections.sort(this.b, a);
        }
        return this.b;
    }

    public Observable<Boolean> deleteSongs(long j, List<Music.Song> list) {
        return ApiHelper.deleteSongs(j, list).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$kfm_weKDa3v9i4k5aD7YpanhuXE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChannelManager.this.a((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Boolean bool) throws Exception {
        loadChannelList(true).subscribe(new DefaultObserver<List<Music.Channel>>() { // from class: com.xiaomi.micolauncher.skills.music.controller.ChannelManager.3
            /* renamed from: a */
            public void onSuccess(List<Music.Channel> list) {
            }
        });
    }

    public Observable<Long> loadDefaultChannelId() {
        List<Music.Channel> list = this.b;
        if (list == null || list.isEmpty()) {
            return ApiManager.minaService.getChannelList().map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$ChannelManager$g5XLxI1fXdXJSLaXvxBifbr2C-s
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Long a2;
                    a2 = ChannelManager.this.a((List) obj);
                    return a2;
                }
            });
        }
        for (Music.Channel channel : this.b) {
            if (channel.isDefault) {
                return Observable.just(Long.valueOf(channel.id));
            }
        }
        return Observable.just(Long.valueOf(this.b.get(0).id));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Long a(List list) throws Exception {
        if (list == null) {
            return -1L;
        }
        this.b.clear();
        this.b.addAll(list);
        Collections.sort(this.b, new Comparator<Music.Channel>() { // from class: com.xiaomi.micolauncher.skills.music.controller.ChannelManager.4
            /* renamed from: a */
            public int compare(Music.Channel channel, Music.Channel channel2) {
                if (!channel2.isDefault || channel.isDefault) {
                    return (!channel.isDefault || channel2.isDefault) ? 0 : -1;
                }
                return 1;
            }
        });
        if (!this.b.isEmpty()) {
            return Long.valueOf(this.b.get(0).id);
        }
        return -1L;
    }
}
