package com.xiaomi.micolauncher.skills.music.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.service.media.MediaBrowserService;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import com.xiaomi.micolauncher.skills.music.controller.playerimpl.MusicAudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController;
import com.xiaomi.micolauncher.skills.music.controller.playlist.impl.AudioItemListController;
import com.xiaomi.micolauncher.skills.music.controller.playlist.impl.CoursePlaylistController;
import com.xiaomi.micolauncher.skills.music.controller.playlist.impl.MusicPlaylistController;
import com.xiaomi.micolauncher.skills.music.controller.playlist.impl.NewsPlayListController;
import com.xiaomi.micolauncher.skills.music.controller.playlist.impl.PatchwallMusicController;
import com.xiaomi.micolauncher.skills.music.controller.playlist.impl.RecentlyMusicController;
import com.xiaomi.micolauncher.skills.music.controller.playlist.impl.SheetPlaylistController;
import com.xiaomi.micolauncher.skills.music.controller.playlist.impl.StationPlaylistController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AudioPlayerService extends MediaBrowserService {
    public static final String ARGS_KEY_CONN_PLAY = "conn_play";
    public static final String ARGS_KEY_DATA = "data";
    public static final String ARGS_KEY_DEPENDENCE = "dependence";
    public static final String ARGS_KEY_DURATION = "duration";
    public static final String ARGS_KEY_ID = "id";
    public static final String ARGS_KEY_LOOP_TYPE = "loop_type";
    public static final String ARGS_KEY_OFFSET = "offset";
    public static final String ARGS_KEY_ORIGIN = "origin";
    public static final String ARGS_KEY_PLAY_INFO = "play_info";
    public static final String ARGS_KEY_TYPE = "type";
    public static final int AUDIO_PLAYER_SERVICE_NOTIFICATION_ID = 1;
    public static final String CMD_PAUSE_AFTER_FINISH = "pause_player_after_finish";
    public static final String CMD_PLAY_AUDIO_LIST = "set_audio_list";
    public static final String CMD_PLAY_BABY_COURSE = "play_baby_course";
    public static final String CMD_PLAY_BY_INDEX = "play_by_index";
    public static final String CMD_PLAY_MUSIC_LIST = "play_music_list";
    public static final String CMD_PLAY_NEWS = "play_news";
    public static final String CMD_PLAY_PATCHWALL_MUSIC = "play_patchwall_music";
    public static final String CMD_PLAY_RECENTLY_MUSIC = "play_recently_music";
    public static final String CMD_PLAY_SHEET = "play_sheet";
    public static final String CMD_PLAY_STATION = "play_station";
    public static final String CMD_SET_LOOP_TYPE = "set_loop_type";
    public static final String EVENT_EXTRA_METADATA = "event_extra_metadata";
    public static final String EVENT_PLAYLIST_UPDATED = "event_playlist_updated";
    public static final String EXTRA_DATA = "data";
    public static final String EXTRA_KEY_CURRENT_INDEX = "current_index";
    public static final String EXTRA_KEY_PLAYING_DATA = "playing_data";
    public static final String MEDIA_SESSION_TAG = "MICO.player.music";
    public static final String META_CHANGED = "com.android.music.metachanged";
    public static final String PLAYSTATE_CHANGED = "com.android.music.playstatechanged";
    PlaylistController a;
    private MediaSession b;
    private AudioPlayerService c;
    private volatile AudioPlayer d;

    @Override // android.service.media.MediaBrowserService, android.app.Service
    public void onCreate() {
        super.onCreate();
        L.player.i("AudioPlayerService create");
        NotificationHelper.startForegroundNotification(this, 1);
        this.c = this;
        this.b = new MediaSession(this, MEDIA_SESSION_TAG);
        this.b.setCallback(new a(this.c));
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        intent.setClass(this, AudioPlayerService.class);
        this.b.setMediaButtonReceiver(PendingIntent.getService(this, 0, intent, 0));
        this.b.setActive(true);
        a();
        setSessionToken(this.b.getSessionToken());
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().resetMicoMediaController(this.b.getController());
        } else {
            L.player.i("AudioPlayerService MediaSessionController instance is null");
        }
    }

    private void a() {
        this.a = new AudioItemListController(this, this.b, b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AudioPlayer b() {
        if (this.d == null) {
            this.d = new MusicAudioPlayer(this.c);
        } else {
            this.d.reset();
        }
        return this.d;
    }

    @Override // android.service.media.MediaBrowserService
    public MediaBrowserService.BrowserRoot onGetRoot(@NonNull String str, int i, Bundle bundle) {
        L.player.i("AudioPlayerService onGetRoot");
        return new MediaBrowserService.BrowserRoot("", null);
    }

    @Override // android.service.media.MediaBrowserService
    public void onLoadChildren(@NonNull String str, @NonNull MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
        L.player.i("AudioPlayerService onLoadChildren");
        result.sendResult(new ArrayList());
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        L.player.i("AudioPlayerService onStartCommand");
        if (this.b != null) {
            L.player.i("AudioPlayerService mMediaSession is active: %b", Boolean.valueOf(this.b.isActive()));
            this.b.setActive(true);
            if (MediaSessionController.getInstance() != null) {
                MediaSessionController.getInstance().resetMicoMediaController(this.b.getController());
            }
        } else {
            L.player.i("AudioPlayerService mMediaSession is null");
        }
        NotificationHelper.startForegroundNotification(this, 1);
        return 3;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean stopService(Intent intent) {
        L.player.i("AudioPlayerService stopService");
        return super.stopService(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        L.player.i("AudioPlayerService onDestroy");
        this.a.release();
        this.b.release();
        this.b = null;
        MediaSessionController instance = MediaSessionController.getInstance();
        if (instance != null) {
            instance.onAudioPlayerServiceDestroy();
        }
        if (this.d != null) {
            this.d.stop();
            this.d.release();
        }
        this.d = null;
    }

    /* loaded from: classes3.dex */
    class a extends MediaSession.Callback {
        private Context b;

        public a(Context context) {
            this.b = context;
        }

        @Override // android.media.session.MediaSession.Callback
        public void onPlay() {
            L.player.i("AudioPlayerService onPlay");
            AudioPlayerService.this.a.play();
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSkipToPrevious() {
            L.player.i("AudioPlayerService onSkipToPrevious");
            AudioPlayerService.this.a.reportVideoManSwitch();
            AudioPlayerService.this.a.prev();
        }

        @Override // android.media.session.MediaSession.Callback
        public void onPause() {
            L.player.i("AudioPlayerService onPause");
            AudioPlayerService.this.a.pause(false);
            AudioPlayerService.this.a.setVolume(1.0f);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSkipToNext() {
            L.player.i("AudioPlayerService onSkipToNext");
            AudioPlayerService.this.a.reportVideoManSwitch();
            AudioPlayerService.this.a.next();
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSeekTo(long j) {
            L.player.i("AudioPlayerService onSeekTo position=%s", Long.valueOf(j));
            AudioPlayerService.this.a.seekTo(j);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSetRating(@NonNull Rating rating) {
            L.player.i("AudioPlayerService onSetRating");
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.media.session.MediaSession.Callback
        public void onCommand(@NonNull String str, Bundle bundle, ResultReceiver resultReceiver) {
            char c;
            SheetPlaylistController sheetPlaylistController;
            StationPlaylistController stationPlaylistController;
            MusicPlaylistController musicPlaylistController;
            NewsPlayListController newsPlayListController;
            RecentlyMusicController recentlyMusicController;
            PatchwallMusicController patchwallMusicController;
            CoursePlaylistController coursePlaylistController;
            L.player.d("AudioPlayerService music player onCommand %s", str);
            switch (str.hashCode()) {
                case -1877698306:
                    if (str.equals(AudioPlayerService.CMD_PLAY_NEWS)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1732694952:
                    if (str.equals(AudioPlayerService.CMD_SET_LOOP_TYPE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1442436252:
                    if (str.equals(AudioPlayerService.CMD_PLAY_AUDIO_LIST)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1366750109:
                    if (str.equals(AudioPlayerService.CMD_PLAY_MUSIC_LIST)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -333137003:
                    if (str.equals(AudioPlayerService.CMD_PLAY_BY_INDEX)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -304365783:
                    if (str.equals(AudioPlayerService.CMD_PLAY_STATION)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1017959723:
                    if (str.equals(AudioPlayerService.CMD_PAUSE_AFTER_FINISH)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1659388589:
                    if (str.equals(AudioPlayerService.CMD_PLAY_PATCHWALL_MUSIC)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1925584020:
                    if (str.equals(AudioPlayerService.CMD_PLAY_SHEET)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2011795641:
                    if (str.equals(AudioPlayerService.CMD_PLAY_RECENTLY_MUSIC)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2147063577:
                    if (str.equals(AudioPlayerService.CMD_PLAY_BABY_COURSE)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (!(AudioPlayerService.this.a == null || bundle == null)) {
                        AudioPlayerService.this.a.reportVideoManSwitch();
                        AudioPlayerService.this.a.playByIndex(bundle.getInt("data"), true);
                        break;
                    }
                    break;
                case 1:
                    if (bundle != null) {
                        if (AudioPlayerService.this.a == null || !(AudioPlayerService.this.a instanceof SheetPlaylistController)) {
                            AudioPlayerService.this.c();
                            sheetPlaylistController = new SheetPlaylistController(this.b, AudioPlayerService.this.b, AudioPlayerService.this.b());
                        } else {
                            sheetPlaylistController = (SheetPlaylistController) AudioPlayerService.this.a;
                            AudioPlayerService.this.a.reportVideoListSwitch();
                        }
                        sheetPlaylistController.resetErrorCount();
                        sheetPlaylistController.playSheet(bundle.getLong("id"), bundle.getInt("offset"), bundle.getInt(AudioPlayerService.ARGS_KEY_LOOP_TYPE, LoopType.LIST_LOOP.ordinal()));
                        AudioPlayerService.this.a = sheetPlaylistController;
                        break;
                    } else {
                        return;
                    }
                    break;
                case 2:
                    if (bundle != null) {
                        if (AudioPlayerService.this.a instanceof StationPlaylistController) {
                            stationPlaylistController = (StationPlaylistController) AudioPlayerService.this.a;
                            AudioPlayerService.this.a.reportVideoListSwitch();
                        } else {
                            AudioPlayerService.this.c();
                            stationPlaylistController = new StationPlaylistController(this.b, AudioPlayerService.this.b, AudioPlayerService.this.b());
                        }
                        stationPlaylistController.resetErrorCount();
                        stationPlaylistController.playStation(bundle.getString("id"), bundle.getString("origin"), bundle.getInt("type"), bundle.getBoolean(AudioPlayerService.ARGS_KEY_CONN_PLAY));
                        AudioPlayerService.this.a = stationPlaylistController;
                        break;
                    } else {
                        return;
                    }
                case 3:
                    if (bundle != null) {
                        if (AudioPlayerService.this.a instanceof MusicPlaylistController) {
                            musicPlaylistController = (MusicPlaylistController) AudioPlayerService.this.a;
                            AudioPlayerService.this.a.reportVideoListSwitch();
                        } else {
                            AudioPlayerService.this.c();
                            musicPlaylistController = new MusicPlaylistController(this.b, AudioPlayerService.this.b, AudioPlayerService.this.b());
                        }
                        musicPlaylistController.resetErrorCount();
                        musicPlaylistController.playPlaylist(bundle.getString("data"));
                        AudioPlayerService.this.a = musicPlaylistController;
                        break;
                    } else {
                        return;
                    }
                case 4:
                    if (!(AudioPlayerService.this.a == null || bundle == null)) {
                        AudioPlayerService.this.a.setLoopType(bundle.getInt(AudioPlayerService.ARGS_KEY_LOOP_TYPE), true);
                        break;
                    }
                    break;
                case 5:
                    if (bundle != null) {
                        if (AudioPlayerService.this.a instanceof NewsPlayListController) {
                            newsPlayListController = (NewsPlayListController) AudioPlayerService.this.a;
                            AudioPlayerService.this.a.reportVideoListSwitch();
                        } else {
                            AudioPlayerService.this.c();
                            newsPlayListController = new NewsPlayListController(this.b, AudioPlayerService.this.b, AudioPlayerService.this.b());
                        }
                        newsPlayListController.resetErrorCount();
                        newsPlayListController.playList(bundle.getString("data"), bundle.getInt("offset"));
                        AudioPlayerService audioPlayerService = AudioPlayerService.this;
                        audioPlayerService.a = newsPlayListController;
                        audioPlayerService.a.setVolume(1.0f);
                        break;
                    } else {
                        return;
                    }
                case 6:
                    if (!(AudioPlayerService.this.a == null || bundle == null)) {
                        AudioPlayerService.this.a.pausePlayerByFinish(bundle.getInt("offset"));
                        break;
                    }
                    break;
                case 7:
                    if (AudioPlayerService.this.a instanceof RecentlyMusicController) {
                        recentlyMusicController = (RecentlyMusicController) AudioPlayerService.this.a;
                        AudioPlayerService.this.a.reportVideoListSwitch();
                    } else {
                        AudioPlayerService.this.c();
                        recentlyMusicController = new RecentlyMusicController(this.b, AudioPlayerService.this.b, AudioPlayerService.this.b());
                    }
                    recentlyMusicController.resetErrorCount();
                    recentlyMusicController.playRecentlyMusic();
                    AudioPlayerService.this.a = recentlyMusicController;
                    break;
                case '\b':
                    if (bundle != null) {
                        if (AudioPlayerService.this.a instanceof PatchwallMusicController) {
                            patchwallMusicController = (PatchwallMusicController) AudioPlayerService.this.a;
                            AudioPlayerService.this.a.reportVideoListSwitch();
                        } else {
                            AudioPlayerService.this.c();
                            patchwallMusicController = new PatchwallMusicController(this.b, AudioPlayerService.this.b, AudioPlayerService.this.b());
                        }
                        patchwallMusicController.resetErrorCount();
                        patchwallMusicController.playPatchwall(bundle.getString("type"), bundle.getString("id"), bundle.getInt(AudioPlayerService.ARGS_KEY_LOOP_TYPE, LoopType.LIST_LOOP.ordinal()), bundle.getInt("offset"));
                        AudioPlayerService.this.a = patchwallMusicController;
                        break;
                    } else {
                        return;
                    }
                case '\t':
                    if (bundle != null) {
                        String string = bundle.getString("id");
                        if (!TextUtils.isEmpty(string)) {
                            if (AudioPlayerService.this.a instanceof CoursePlaylistController) {
                                coursePlaylistController = (CoursePlaylistController) AudioPlayerService.this.a;
                                AudioPlayerService.this.a.reportVideoListSwitch();
                            } else {
                                AudioPlayerService.this.c();
                                coursePlaylistController = new CoursePlaylistController(this.b, AudioPlayerService.this.b, AudioPlayerService.this.b());
                            }
                            coursePlaylistController.resetErrorCount();
                            coursePlaylistController.playCourse(string);
                            AudioPlayerService.this.a = coursePlaylistController;
                            break;
                        } else {
                            L.player.d("CMD_PLAY_BABY_COURSE courseId is empty");
                            return;
                        }
                    } else {
                        return;
                    }
                case '\n':
                    if (bundle != null) {
                        AudioPlayerService.this.a(bundle);
                        break;
                    } else {
                        return;
                    }
            }
            LocalPlayerManager.getInstance().setPlaylistController(AudioPlayerService.this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bundle bundle) {
        AudioItemListController audioItemListController;
        try {
            AudioPlayer.Play play = (AudioPlayer.Play) APIUtils.fromJsonString(bundle.getString("data"), AudioPlayer.Play.class);
            Template.PlayInfo playInfo = (Template.PlayInfo) APIUtils.fromJsonString(bundle.getString(ARGS_KEY_PLAY_INFO), Template.PlayInfo.class);
            String string = bundle.getString(ARGS_KEY_LOOP_TYPE);
            boolean z = bundle.getBoolean(ARGS_KEY_DEPENDENCE, false);
            String string2 = bundle.getString("id");
            long j = bundle.getLong("duration", 0L);
            if (this.a instanceof AudioItemListController) {
                audioItemListController = (AudioItemListController) this.a;
                this.a.reportVideoListSwitch();
            } else {
                c();
                audioItemListController = new AudioItemListController(this, this.b, b());
            }
            audioItemListController.resetErrorCount();
            audioItemListController.handlePlayList(string2, play, playInfo, string, z, j);
            this.a = audioItemListController;
            this.a.setVolume(1.0f);
        } catch (IOException e) {
            L.player.e("AudioPlayerService playAudioList error", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        L.player.d("AudioPlayerService destroyPlaylistController");
        PlaylistController playlistController = this.a;
        if (playlistController != null) {
            playlistController.reportVideoListSwitch();
            this.a.release();
            this.a = null;
        }
    }
}
