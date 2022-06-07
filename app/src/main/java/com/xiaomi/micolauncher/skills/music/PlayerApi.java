package com.xiaomi.micolauncher.skills.music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadata;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MicoMediaControllerProxy;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.music.model.api.NewsItem;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerActivityV2;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import java.util.List;
import java.util.Optional;

/* loaded from: classes3.dex */
public class PlayerApi {
    public static boolean pauseByManually = false;

    public static void toggle() {
    }

    public static Remote.Response.PlayerStatus getPlayerStatus() {
        return LocalPlayerManager.getInstance().getPlayingPlayerStatus();
    }

    public static Remote.Response.PlayerStatus getWiFiPlayerStatus() {
        return LocalPlayerManager.getInstance().getPlayerStatus();
    }

    public static void next() {
        L.player.d("PlayerApi next");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().next();
        }
    }

    public static void prev() {
        L.player.d("PlayerApi prev");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().prev();
        }
    }

    public static void nextWiFi() {
        L.player.d("PlayerApi nextWiFi");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().nextWiFi();
        }
    }

    public static void prevWiFi() {
        L.player.d("PlayerApi prevWiFi");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().prevWiFi();
        }
    }

    public static void pause() {
        L.player.d("PlayerApi pause");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().pause();
        }
    }

    public static void play() {
        L.player.d("PlayerApi play");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().play();
        }
    }

    public static void playWiFiMusic() {
        L.player.d("PlayerApi playWiFiMusic");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playWiFi();
        }
    }

    public static void playWiFiMusicAndPushData() {
        L.player.d("PlayerApi playWiFiMusic");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playWiFiAndPushData();
        }
    }

    public static void playByIndex(int i) {
        L.player.d("PlayerApi playByIndex");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playByIndex(i);
        }
    }

    public static void b(int i) {
        L.player.d("PlayerApi setVolume");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().setVolume(i);
        }
    }

    public static void seekTo(long j) {
        L.player.d("PlayerApi seekTo=%s", Long.valueOf(j));
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().seekTo(j);
        }
    }

    public static void playOrPause() {
        L.player.d("PlayerApi playOrPause");
        int i = getPlayerStatus().status;
        if (i == 1) {
            pauseByManually = true;
            pause();
        } else if (i == 2) {
            play();
        }
    }

    public static boolean toggleLove(boolean z) {
        return CollectionManager.getInstance().doSetLovable(z);
    }

    public static int nextLoop() {
        return MediaSessionController.getInstance().nextLoop();
    }

    public static void setLoopType(int i) {
        if (Remote.Request.LoopType.isValidLoop(i) && MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().setLoopType(i);
        }
    }

    public static void refreshLoveStatus() {
        CollectionManager.getInstance().refreshLoveStatus(Optional.empty());
    }

    public static void setPlayerShutdownTimer(Remote.Request.PlayerShutdown playerShutdown) {
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().setPlayerShutdownTimer(playerShutdown);
        }
    }

    public static boolean isPlaying() {
        return 1 == getPlayerStatus().status;
    }

    public static void pushMediaState() {
        L.player.d("PlayerApi pushMediaState");
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().pushMediaState();
        }
    }

    @SuppressLint({"CheckResult"})
    public static void playRecently(Context context) {
        L.player.d("PlayerApi playRecently");
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO) && MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playRecentlyMusic();
            a(context);
        }
    }

    public static void playSheet(Context context, String str, LoopType loopType) {
        playSheet(context, str, 0, loopType);
    }

    public static void playSheet(Context context, String str, int i, LoopType loopType) {
        L.player.d("playSheet sheetId=%s,offset=%s,loopType=%s", str, Integer.valueOf(i), loopType);
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO)) {
            if (loopType == null) {
                loopType = LoopType.LIST_LOOP;
            }
            int ordinal = loopType.ordinal();
            if (MediaSessionController.getInstance() != null) {
                MediaSessionController.getInstance().playSheet(str, i, ordinal);
                showPlayerView(context, true);
            }
        }
    }

    public static void b(Context context, String str, int i, LoopType loopType) {
        L.player.d("playSheetRemote sheetId=%s,offset=%s,loopType=%s", str, Integer.valueOf(i), loopType);
        playSheet(context, str, i, loopType);
    }

    public static void playPatchwallBlock(Context context, String str, long j) {
        playPatchwallBlock(context, str, j, LoopType.LIST_LOOP.ordinal(), 0);
    }

    public static void playPatchwallBlock(Context context, String str, long j, int i, int i2) {
        L.player.d("playPatchwallBlock type=%s,sheetId=%s,loopType=%s,index=%s", str, Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2));
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO) && MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playPatchwallMusic(str, String.valueOf(j), i, i2);
            showPlayerView(context, true);
        }
    }

    public static Remote.Response.PlayerShutdownTimer getShutdownTimer() {
        Remote.Response.PlayerShutdownTimer playerShutdownTimer = new Remote.Response.PlayerShutdownTimer();
        if (MediaSessionController.getInstance() != null) {
            long playerShutdownRemainTime = MediaSessionController.getInstance().getPlayerShutdownRemainTime();
            if (playerShutdownRemainTime == -2) {
                playerShutdownTimer.type = 2;
                playerShutdownTimer.remainTime = 0L;
            } else {
                playerShutdownTimer.type = 1;
                playerShutdownTimer.remainTime = playerShutdownRemainTime;
            }
        }
        return playerShutdownTimer;
    }

    public static void clearPlayerStatus() {
        if (MicoApplication.getGlobalContext() != null) {
            MediaMetadata build = new MediaMetadata.Builder().build();
            Intent intent = new Intent(PlayerEvent.ACTION_MUSIC_META_CHANGE);
            intent.putExtra(PlayerEvent.METADATA, build);
            intent.putExtra("source", PlayerEvent.MUSIC_SOURCE_IDLE);
            MicoApplication.getGlobalContext().sendBroadcast(intent);
            if (MediaSessionController.getInstance() != null) {
                LocalPlayerManager.getInstance().clearPlaylist();
            }
        }
        clearSystemBarText();
    }

    public static void clearSystemBarText() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_DISPLAY_BAR_EXTRA_TEXT);
        intent.setPackage(Constants.PKG_SYSTEM_UI);
        intent.putExtra("content", "");
        MicoApplication.getApp().sendBroadcast(intent);
    }

    public static void showSystemBarText() {
        MediaMetadata buildMediaMetadata = MicoMediaControllerProxy.buildMediaMetadata(LocalPlayerManager.getInstance().getPlayerStatus());
        if (buildMediaMetadata == null) {
            L.player.d("updateMediaMetadata:metadata is null");
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_DISPLAY_BAR_EXTRA_TEXT);
        intent.setPackage(Constants.PKG_SYSTEM_UI);
        String string = buildMediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
        CharSequence text = buildMediaMetadata.getText(MediaMetadataCompat.METADATA_KEY_ARTIST);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(string)) {
            sb.append((CharSequence) string);
        }
        if (!TextUtils.isEmpty(text)) {
            sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            sb.append(text);
        }
        intent.putExtra("content", sb.toString());
        MicoApplication.getApp().sendBroadcast(intent);
    }

    public static void playOrPlayRecommend() {
        L.player.d("PlayerApi playOrPlayRecommend");
        if (MediaSessionController.getInstance() == null) {
            L.player.d("PlayerApi MediaSessionController is null");
            return;
        }
        List<Remote.Response.TrackData> playList = LocalPlayerManager.getInstance().getPlayList();
        if (MediaSessionController.getInstance().isBTUsing() || MediaSessionController.getInstance().isMiplayUsing()) {
            MediaSessionController.getInstance().play();
        } else if (ContainerUtil.isEmpty(playList)) {
            LocalPlayerManager.getInstance().dumpPlaylist();
            MediaSessionController.getInstance().playRecommendMusic();
        } else if (MediaSessionController.getInstance().isMicoUsing()) {
            MediaSessionController.getInstance().play();
        } else {
            MediaSessionController.getInstance().playWiFi();
            showSystemBarText();
        }
    }

    public static void playStation(Context context, String str, String str2, int i) {
        playStation(context, str, str2, i, false);
    }

    public static void playStation(Context context, String str, String str2, int i, boolean z) {
        L.player.d("playStation stationId=%s,origin=%s,type=%d,connHistory=%b", str, str2, Integer.valueOf(i), Boolean.valueOf(z));
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO) && MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playStation(str, str2, i, z);
            a(context);
        }
    }

    public static void playBabyCourse(String str) {
        L.player.d("playBabyCourse courseId=%s", str);
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO) && MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playBabyCourse(str);
            a(MicoApplication.getGlobalContext());
        }
    }

    public static void playNews(Context context, List<NewsItem> list, int i) {
        L.player.d("PlayerApi playNews");
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO) && MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playNewsList(Gsons.getGson().toJson(list), i);
            a(context);
        }
    }

    public static void playRecommendOrShowPlayerView(Context context) {
        L.player.d("PlayerApi playRecommendOrShowPlayerView");
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO) && MediaSessionController.getInstance() != null) {
            if (ContainerUtil.isEmpty(LocalPlayerManager.getInstance().getPlayList()) && !MediaSessionController.getInstance().isBTPlaying() && !MediaSessionController.getInstance().isMiplayUsing()) {
                MediaSessionController.getInstance().playRecommendMusic();
            }
            a(context);
        }
    }

    public static void playMusicListByRemote(Context context, String str) {
        L.player.d("PlayerApi playMusicListByRemote");
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO) && MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playMusicList(str);
            a(context);
        }
    }

    public static void playMusicList(Context context, List<Music.Song> list, int i) {
        L.player.d("PlayerApi playMusicList");
        if (ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO) && MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playMusicList(Gsons.getGson().toJson(Remote.Request.Playlist_Message.fromSongPlaylist(list, i)));
            a(context);
        }
    }

    private static void a(Context context) {
        showPlayerView(context, false);
    }

    public static void showPlayerView(Context context, boolean z) {
        L.player.d("PlayerApi showPlayerView");
        if (!ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO)) {
            L.childmode.e("time manage effect can not show player view");
        } else if (VoipModel.getInstance().isVoipActive()) {
            L.player.w("PlayerApi showPlayerView, Voip is running!");
        } else if (context == null) {
            L.player.e("PlayerApi showPlayerView context is null");
        } else {
            ActivityLifeCycleManager.getInstance().finishVideoPlayerActivity();
            Intent intent = new Intent(context, PlayerActivityV2.class);
            intent.addFlags(536870912);
            ActivityLifeCycleManager.startActivityQuietly(intent);
            if (z) {
                MusicProfile.shareInstance().checkAuthStateAndShowView();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class UbusHandler implements UBus.UbusHandler {
        public static final String PATH_LAYER = "mediaplayer";
        public static final String PLAYER_GET_PLAY_STATUS = "player_get_play_status";
        public static final String PLAYER_PLAY_ALBUM_PLAYLIST = "player_play_album_playlist";
        public static final String PLAYER_PLAY_MUSIC = "player_play_music";
        public static final String PLAYER_PLAY_OPERATION = "player_play_operation";
        public static final String PLAYER_SET_VOLUME = "player_set_volume";

        @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
        public boolean canHandle(Context context, String str, String str2) {
            return str.equals(PATH_LAYER);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x0160, code lost:
            if (r9.equals("stop") != false) goto L_0x018d;
         */
        @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String handle(android.content.Context r9, java.lang.String r10, java.lang.String r11, java.lang.String r12) {
            /*
                Method dump skipped, instructions count: 580
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.music.PlayerApi.UbusHandler.handle(android.content.Context, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
        }
    }
}
