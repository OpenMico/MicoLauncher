package com.xiaomi.micolauncher.skills.music.controller;

import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Template;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.capability.InstructionCapabilityImpl;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class LocalPlayerManager {
    private static final LocalPlayerManager a = new LocalPlayerManager();
    private Remote.Response.PlayerStatus c;
    private Remote.Response.PlayerStatus d;
    private PlaylistController e;
    private volatile List<String> f;
    private volatile AudioPlayer.AudioItemV1 h;
    private Template.Stations i;
    private String j;
    private Remote.Response.PlayerStatus b = new Remote.Response.PlayerStatus();
    private CopyOnWriteArrayList<MetadataChangedCallback> g = new CopyOnWriteArrayList<>();

    /* loaded from: classes3.dex */
    public interface MetadataChangedCallback {
        void onMediaListChanged(Remote.Response.PlayerStatus playerStatus);

        void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus);

        void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus);
    }

    public static LocalPlayerManager getInstance() {
        return a;
    }

    private LocalPlayerManager() {
    }

    public void setPlaylistController(PlaylistController playlistController) {
        this.e = playlistController;
    }

    public Remote.Response.PlayerStatus getPlayerStatus() {
        return this.b;
    }

    public Remote.Response.PlayerStatus getPlayingPlayerStatus() {
        return getPlayingPlayerStatus(true);
    }

    public Remote.Response.PlayerStatus getPlayingPlayerStatus(boolean z) {
        Remote.Response.PlayerStatus playerStatus;
        if (ConnectType.type == ConnectType.BLUETOOTH) {
            Remote.Response.PlayerStatus playerStatus2 = this.c;
            if (playerStatus2 != null) {
                return playerStatus2;
            }
        } else if (ConnectType.type == ConnectType.MIPLAY && (playerStatus = this.d) != null) {
            return playerStatus;
        }
        if (z) {
            a();
        }
        return this.b;
    }

    private void a() {
        PlaylistController playlistController = this.e;
        if (playlistController != null && !playlistController.isReleased()) {
            this.e.updatePlaybackStatus();
        }
        this.b.list_id = getPlayListId();
    }

    public PlaylistController getPlaylistController() {
        return this.e;
    }

    public void setMediaType(int i) {
        this.b.media_type = i;
    }

    public int getMediaType() {
        return this.b.media_type;
    }

    public void setLoopType(int i) {
        this.b.loop_type = i;
    }

    public int getLoopType() {
        Remote.Response.PlayerStatus playerStatus = this.b;
        if (playerStatus != null) {
            return playerStatus.loop_type;
        }
        return LoopType.LIST_LOOP.ordinal();
    }

    public void setVolume(int i) {
        this.b.volume = i;
    }

    public void setPlayerStatus(int i, long j, long j2) {
        Remote.Response.PlayerStatus playerStatus = this.b;
        playerStatus.status = i;
        if (playerStatus.play_song_detail != null) {
            this.b.play_song_detail.duration = j;
            this.b.play_song_detail.position = j2;
        }
    }

    public void setPlayerStatus(int i) {
        this.b.status = i;
    }

    public void setPlayerPosition(long j, long j2) {
        if (this.b.play_song_detail != null) {
            this.b.play_song_detail.duration = j;
            this.b.play_song_detail.position = j2;
        }
    }

    public void setMicoPlayingData(String str, Remote.Response.PlayingData playingData, boolean z) {
        boolean z2 = true;
        boolean z3 = this.c != null;
        this.c = null;
        this.d = null;
        ConnectType.type = ConnectType.MICO;
        Remote.Response.PlayerStatus playingPlayerStatus = getPlayingPlayerStatus();
        if (!isResourceChange(playingPlayerStatus, playingData) && !z3) {
            z2 = false;
        }
        if (!z2 && playingPlayerStatus.play_song_detail != null) {
            playingData.outCoverBitmap = playingPlayerStatus.play_song_detail.outCoverBitmap;
        }
        playingPlayerStatus.play_song_detail = playingData;
        if (z2 || z) {
            notifyMediaMetadataChanged(playingPlayerStatus, str);
        }
    }

    public void setPlayingList(int i, List<Remote.Response.TrackData> list, boolean z) {
        if (this.b.play_song_detail != null) {
            this.b.play_song_detail.screenExtend.index = i;
        }
        Remote.Response.PlayerStatus playerStatus = this.b;
        playerStatus.extra_track_list = list;
        playerStatus.isManualLoadMore = z;
        ArrayList arrayList = new ArrayList();
        try {
            for (Remote.Response.TrackData trackData : list) {
                if (trackData.audioID != null) {
                    arrayList.add(Long.valueOf(Long.parseLong(trackData.audioID)));
                }
            }
            this.b.track_list = arrayList;
        } catch (Exception e) {
            L.player.e("error in parsing track list", e);
        }
        c();
    }

    public Remote.Response.PlayingData getCurrentPlayingData() {
        return getPlayerStatus().play_song_detail;
    }

    public void registerCallback(MetadataChangedCallback metadataChangedCallback) {
        if (!this.g.contains(metadataChangedCallback)) {
            L.player.d("%s registerCallback=%s", "[LocalPlayerManager]", metadataChangedCallback);
            this.g.add(metadataChangedCallback);
        }
    }

    public void unregisterCallback(MetadataChangedCallback metadataChangedCallback) {
        L.player.d("%s unregisterCallback=%s", "[LocalPlayerManager]", metadataChangedCallback);
        this.g.remove(metadataChangedCallback);
    }

    public void notifyMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        notifyMediaMetadataChanged(playerStatus, null);
    }

    public void notifyMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus, String str) {
        L.player.d("%s onMediaMetadataChanged", "[LocalPlayerManager]");
        if (playerStatus.media_type == 21) {
            ConnectType.type = ConnectType.MIPLAY;
            this.c = null;
            this.d = playerStatus;
        } else if (playerStatus.media_type == 20) {
            ConnectType.type = ConnectType.BLUETOOTH;
            this.d = null;
            this.c = playerStatus;
        }
        if (str != null && SpeechManager.getInstance().isDialogMode() && SpeechManager.getInstance().isDialogContinuous()) {
            String speechDialogId = InstructionCapabilityImpl.getInstance().getSpeechDialogId();
            String previousDialogId = InstructionCapabilityImpl.getInstance().getPreviousDialogId();
            if (!str.equals(speechDialogId) && str.equals(previousDialogId)) {
                L.speech.w("%s SHOULD RESTART dialog! dialogId: %s, preDialogId: %s, curDialogId: %s", "[LocalPlayerManager]", str, previousDialogId, speechDialogId);
                SpeechManager.getInstance().setWakeup();
            }
        }
        this.g.forEach(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$LocalPlayerManager$Xhb_8LJ4SrU85lvbJIunDshjQP8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LocalPlayerManager.this.b((LocalPlayerManager.MetadataChangedCallback) obj);
            }
        });
    }

    public /* synthetic */ void b(MetadataChangedCallback metadataChangedCallback) {
        L.player.d("%s onMediaMetadataChanged=%s", "[LocalPlayerManager]", metadataChangedCallback);
        metadataChangedCallback.onMediaMetadataChanged(b());
    }

    private Remote.Response.PlayerStatus b() {
        if (ConnectType.type == ConnectType.BLUETOOTH) {
            return this.c;
        }
        if (ConnectType.type == ConnectType.MIPLAY) {
            return this.d;
        }
        return this.b;
    }

    private void c() {
        this.g.forEach(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$LocalPlayerManager$QIZx-tcqyqXEw_PcXrveornll3w
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LocalPlayerManager.this.a((LocalPlayerManager.MetadataChangedCallback) obj);
            }
        });
    }

    public /* synthetic */ void a(MetadataChangedCallback metadataChangedCallback) {
        L.player.d("LocalPlayerManager onMediaListChanged=%s", metadataChangedCallback);
        metadataChangedCallback.onMediaListChanged(this.b);
    }

    public void notifyPlaybackStateChanged(final Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus != null) {
            if (playerStatus.media_type == 21) {
                this.c = null;
                this.d = playerStatus;
                ConnectType.type = ConnectType.MIPLAY;
            } else if (playerStatus.media_type == 20) {
                this.c = playerStatus;
                this.d = null;
                ConnectType.type = ConnectType.BLUETOOTH;
            }
        }
        this.g.forEach(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$LocalPlayerManager$o-zA5m_QphraLHlmv3e2kyhTmKM
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LocalPlayerManager.this.a(playerStatus, (LocalPlayerManager.MetadataChangedCallback) obj);
            }
        });
    }

    public /* synthetic */ void a(Remote.Response.PlayerStatus playerStatus, MetadataChangedCallback metadataChangedCallback) {
        metadataChangedCallback.onPlayerStateChanged(playerStatus == null ? this.b : b());
    }

    public static boolean isResourceChange(Remote.Response.PlayerStatus playerStatus, Remote.Response.PlayingData playingData) {
        return !ContainerUtil.equals(playerStatus.play_song_detail, playingData);
    }

    public List<Remote.Response.TrackData> getPlayList() {
        return this.b.extra_track_list;
    }

    public void dumpPlaylist() {
        List<Remote.Response.TrackData> playList = getPlayList();
        if (playList != null) {
            L.debug.json(Gsons.getGson().toJson(playList));
        }
    }

    public void clearPlaylist() {
        if (this.b.extra_track_list != null) {
            this.b.extra_track_list.clear();
        }
    }

    public long getPlayListId() {
        if (this.b.play_song_detail != null) {
            return this.b.play_song_detail.screenExtend.playlistId;
        }
        return -1L;
    }

    public static List<Remote.Response.TrackData> getRandomPlayList() {
        return getRandomPlayList(null);
    }

    public static List<Remote.Response.TrackData> getRandomPlayList(List<Remote.Response.TrackData> list) {
        PlaylistController playlistController = getInstance().getPlaylistController();
        List<Integer> randomIndexList = (playlistController == null || playlistController.getMusicIndexHelper() == null) ? null : playlistController.getMusicIndexHelper().getRandomIndexList();
        if (randomIndexList == null) {
            return null;
        }
        Logger logger = L.player;
        logger.d("MusicIndexHelper 随机歌曲index：" + randomIndexList);
        int i = 0;
        if (list == null) {
            List<Remote.Response.TrackData> playList = getInstance().getPlayList();
            if (ContainerUtil.isEmpty(playList)) {
                return null;
            }
            if (playList.size() != randomIndexList.size()) {
                L.player.e("PlayerApi getRandomPlayList 2 originalPlayList size=%d, randomIndexList size=%d", Integer.valueOf(playList.size()), Integer.valueOf(randomIndexList.size()));
                return null;
            }
            ArrayList arrayList = new ArrayList(playList.size());
            while (i < randomIndexList.size()) {
                int intValue = randomIndexList.get(i).intValue();
                if (intValue <= playList.size()) {
                    arrayList.add(playList.get(intValue));
                }
                i++;
            }
            return arrayList;
        } else if (list.size() != randomIndexList.size()) {
            L.player.e("PlayerApi getRandomPlayList 1 originalPlayList size=%d, randomIndexList size=%d", Integer.valueOf(list.size()), Integer.valueOf(randomIndexList.size()));
            return null;
        } else {
            ArrayList arrayList2 = new ArrayList(list.size());
            while (i < randomIndexList.size()) {
                arrayList2.add(list.get(randomIndexList.get(i).intValue()));
                i++;
            }
            return arrayList2;
        }
    }

    public boolean isShuffleLoop() {
        return this.b.loop_type == LoopType.SHUFFLE.ordinal();
    }

    public int getPlayingIndexInCurrentList() {
        PlaylistController playlistController = getInstance().getPlaylistController();
        if (playlistController == null || playlistController.getMusicIndexHelper() == null) {
            return 0;
        }
        return playlistController.getMusicIndexHelper().getPlayingIndexInCurrentList();
    }

    public int getRandomPlayIndexByOriginalIndex(int i) {
        PlaylistController playlistController = getInstance().getPlaylistController();
        if (playlistController == null || playlistController.getMusicIndexHelper() == null) {
            return 0;
        }
        return playlistController.getMusicIndexHelper().getRandomPlayIndexByOriginalIndex(i);
    }

    public int getPlayingIndexInOriginalList(int i) {
        PlaylistController playlistController = getInstance().getPlaylistController();
        if (playlistController == null || playlistController.getMusicIndexHelper() == null) {
            return -1;
        }
        return playlistController.getMusicIndexHelper().getPlayingIndexInOriginalList(i);
    }

    public Template.Stations getStations() {
        if (this.i == null) {
            this.i = new Template.Stations();
        }
        return this.i;
    }

    public void setStations(Template.Stations stations) {
        this.i = stations;
    }

    public void clearStations() {
        this.i = null;
    }

    public AudioPlayer.AudioItemV1 getAlarmAudioItem() {
        return this.h;
    }

    public void setAlarmAudioItem(AudioPlayer.AudioItemV1 audioItemV1) {
        this.h = audioItemV1;
    }

    public boolean canLoadMore() {
        PlaylistController playlistController = this.e;
        if (playlistController != null) {
            return playlistController.canLoadMore();
        }
        return false;
    }

    public boolean canPreLoadMore() {
        PlaylistController playlistController = this.e;
        if (playlistController != null) {
            return playlistController.canPreLoadMore();
        }
        return false;
    }

    public boolean loadMore(MetadataLoadMoreCallback metadataLoadMoreCallback) {
        PlaylistController playlistController = this.e;
        if (playlistController == null || !playlistController.canLoadMore()) {
            return false;
        }
        this.e.doLoadMore(metadataLoadMoreCallback);
        return true;
    }

    public boolean preLoadMore(MetadataPreLoadMoreCallback metadataPreLoadMoreCallback) {
        PlaylistController playlistController = this.e;
        if (playlistController == null || !playlistController.canPreLoadMore()) {
            return false;
        }
        this.e.doPreLoadMore(metadataPreLoadMoreCallback);
        return true;
    }

    public Observable<List<String>> loadBlackList() {
        if (this.f != null) {
            return Observable.just(this.f);
        }
        return ApiHelper.getAuthorizationValue().flatMap(new Function<String, ObservableSource<List<String>>>() { // from class: com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.1
            /* renamed from: a */
            public ObservableSource<List<String>> apply(String str) throws Exception {
                return ApiManager.aiMiService.getBlackList(str);
            }
        }).map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$LocalPlayerManager$2srbDmUrrCFcX-_21Kuj8o6m5DI
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List a2;
                a2 = LocalPlayerManager.this.a((List) obj);
                return a2;
            }
        }).onErrorReturnItem(new ArrayList());
    }

    public /* synthetic */ List a(List list) throws Exception {
        if (list != null) {
            this.f = list;
        }
        return list;
    }

    public void clearBlackList() {
        this.f = null;
        loadBlackList().subscribeOn(MicoSchedulers.io()).subscribe(new io.reactivex.functions.Consumer<List<String>>() { // from class: com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.2
            /* renamed from: a */
            public void accept(List<String> list) throws Exception {
            }
        }, new io.reactivex.functions.Consumer<Throwable>() { // from class: com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.3
            /* renamed from: a */
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    public List<String> getBlackListKey() {
        return this.f;
    }

    public boolean isBlack(String str) {
        boolean z = false;
        if (ContainerUtil.isEmpty(this.f)) {
            return false;
        }
        for (String str2 : this.f) {
            if (!TextUtils.isEmpty(str) && str.contains(str2)) {
                z = true;
            }
        }
        return z;
    }

    public String getDeviceName() {
        return this.j;
    }

    public void setDeviceName(String str) {
        this.j = str;
    }
}
