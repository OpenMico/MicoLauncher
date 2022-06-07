package com.xiaomi.miplay.mylibrary.session;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.miplay.mylibrary.session.data.AppMetaData;
import com.xiaomi.miplay.mylibrary.session.data.MediaMetaData;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* loaded from: classes4.dex */
public class ActiveSessionRecord {
    @NonNull
    private final MediaSession.Token a;
    @NonNull
    private final MediaController b;
    @NonNull
    private final TransportController d;
    @NonNull
    private final ActiveAudioSessionManager e;
    @NonNull
    private final AppMetaData f;
    private Context h;
    @NonNull
    private final a c = new a();
    private AudioMediaController g = new AudioMediaController(new MediaControllerStub());

    public ActiveSessionRecord(@NonNull ActiveAudioSessionManager activeAudioSessionManager, @NonNull MediaController mediaController, @NonNull AppMetaData appMetaData, Context context) {
        this.h = context;
        this.e = activeAudioSessionManager;
        this.b = mediaController;
        this.a = mediaController.getSessionToken();
        this.d = new TransportController(mediaController);
        this.b.registerCallback(this.c);
        this.f = appMetaData;
    }

    @NonNull
    public AudioMediaController getAudioMediaController() {
        return this.g;
    }

    public void release() {
        this.b.unregisterCallback(this.c);
    }

    @NonNull
    public MediaController getMediaController() {
        return this.b;
    }

    public String getPackageName() {
        return this.f.getPackageName();
    }

    public int getPlaybackState() {
        PlaybackState playbackState = this.b.getPlaybackState();
        if (playbackState == null) {
            return 0;
        }
        return playbackState.getState();
    }

    public int getAppUid() {
        return this.f.getUid();
    }

    public void pause() {
        if (getPlaybackState() == 3) {
            this.d.pause();
        }
    }

    public void play() {
        if (getPlaybackState() != 3) {
            this.d.play();
        }
    }

    public void a(int i) {
        this.e.onPlaybackStateChanged(i);
    }

    public void a(MediaMetaData mediaMetaData, String str) {
        this.e.onMetadataChanged(mediaMetaData, str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.a.equals(((ActiveSessionRecord) obj).a);
    }

    public int hashCode() {
        return Objects.hash(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a extends MediaController.Callback {
        private a() {
            ActiveSessionRecord.this = r1;
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
            Logger.d("ActiveSessionRecord", "onSessionDestroyed", new Object[0]);
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(@Nullable PlaybackState playbackState) {
            super.onPlaybackStateChanged(playbackState);
            Logger.i("ActiveSessionRecord", "onPlaybackStateChanged.", new Object[0]);
            if (playbackState != null) {
                ActiveSessionRecord.this.e.a(ActiveSessionRecord.this, playbackState);
                ActiveSessionRecord.this.a(playbackState.getState());
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(@Nullable MediaMetadata mediaMetadata) {
            super.onMetadataChanged(mediaMetadata);
            ActiveSessionRecord.this.e.a(ActiveSessionRecord.this, mediaMetadata);
            MediaMetaData mediaMetaData = new MediaMetaData(mediaMetadata);
            Logger.i("ActiveSessionRecord", "dispatch meta change, " + mediaMetaData.toString(), new Object[0]);
            ActiveSessionRecord activeSessionRecord = ActiveSessionRecord.this;
            activeSessionRecord.a(mediaMetaData, activeSessionRecord.getPackageName());
        }
    }

    /* loaded from: classes4.dex */
    public static final class MediaControllerStub implements MediaControllerAPI {
        private final WeakReference<ActiveSessionRecord> a;

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public void stop() {
        }

        private MediaControllerStub(@NonNull ActiveSessionRecord activeSessionRecord) {
            this.a = new WeakReference<>(activeSessionRecord);
        }

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public void play() {
            ActiveSessionRecord activeSessionRecord = this.a.get();
            if (activeSessionRecord != null) {
                activeSessionRecord.play();
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public void pause() {
            ActiveSessionRecord activeSessionRecord = this.a.get();
            if (activeSessionRecord != null) {
                activeSessionRecord.pause();
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public void seekTo(long j) {
            ActiveSessionRecord activeSessionRecord = this.a.get();
            if (activeSessionRecord != null) {
                Logger.d("ActiveSessionRecord", "seekTo:" + j, new Object[0]);
                activeSessionRecord.d.seekTo(j);
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public void next() {
            ActiveSessionRecord activeSessionRecord = this.a.get();
            if (activeSessionRecord != null) {
                KeyEvent keyEvent = new KeyEvent(0, 87);
                KeyEvent keyEvent2 = new KeyEvent(1, 87);
                activeSessionRecord.b.dispatchMediaButtonEvent(keyEvent);
                activeSessionRecord.b.dispatchMediaButtonEvent(keyEvent2);
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public void previous() {
            ActiveSessionRecord activeSessionRecord = this.a.get();
            if (activeSessionRecord != null) {
                KeyEvent keyEvent = new KeyEvent(0, 88);
                KeyEvent keyEvent2 = new KeyEvent(1, 88);
                activeSessionRecord.b.dispatchMediaButtonEvent(keyEvent);
                activeSessionRecord.b.dispatchMediaButtonEvent(keyEvent2);
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public int getPlaybackState() {
            PlaybackState playbackState;
            ActiveSessionRecord activeSessionRecord = this.a.get();
            if (activeSessionRecord == null || (playbackState = activeSessionRecord.b.getPlaybackState()) == null) {
                return 0;
            }
            return playbackState.getState();
        }

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public long getPosition() {
            PlaybackState playbackState;
            ActiveSessionRecord activeSessionRecord = this.a.get();
            if (activeSessionRecord == null || (playbackState = activeSessionRecord.b.getPlaybackState()) == null) {
                return -1L;
            }
            return playbackState.getPosition();
        }

        @Override // com.xiaomi.miplay.mylibrary.session.MediaControllerAPI
        public MediaMetaData getMediaMetaData() {
            ActiveSessionRecord activeSessionRecord = this.a.get();
            if (activeSessionRecord == null) {
                return new MediaMetaData();
            }
            return new MediaMetaData(activeSessionRecord.getMediaController().getMetadata());
        }
    }
}
