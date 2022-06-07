package com.xiaomi.micolauncher.common.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaMetadata;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.android.systemui.qs.mi.ISystemUIMediaPlayerCallback;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.IMicoRemoteService;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;

/* loaded from: classes3.dex */
public class MicoRemoteService extends Service {
    @GuardedBy("systemUiMediaPlayerCallbackLock")
    private ISystemUIMediaPlayerCallback b;
    private final Object a = new Object();
    private a c = null;
    private final IMicoRemoteService.Stub d = new IMicoRemoteService.Stub() { // from class: com.xiaomi.micolauncher.common.services.MicoRemoteService.1
        @Override // com.xiaomi.micolauncher.IMicoRemoteService
        public boolean isNightModeOn() throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.micolauncher.IMicoRemoteService
        public void registerSystemUIMediaPlayerCallback(ISystemUIMediaPlayerCallback iSystemUIMediaPlayerCallback) {
            Log.d("MicoRemoteService", "registerSystemUIMediaPlayerCallback");
            synchronized (MicoRemoteService.this.a) {
                MicoRemoteService.this.b = iSystemUIMediaPlayerCallback;
            }
        }

        @Override // com.xiaomi.micolauncher.IMicoRemoteService
        public void unregisterSystemUIMediaPlayerCallback(ISystemUIMediaPlayerCallback iSystemUIMediaPlayerCallback) {
            Log.d("MicoRemoteService", "unregisterSystemUIMediaPlayerCallback");
            synchronized (MicoRemoteService.this.a) {
                MicoRemoteService.this.b = null;
            }
        }

        @Override // com.xiaomi.micolauncher.IMicoRemoteService
        public MediaMetadata getMediaMetadata() {
            Remote.Response.PlayingData currentPlayingData;
            MediaSessionController instance = MediaSessionController.getInstance();
            if (instance == null || (currentPlayingData = instance.getCurrentPlayingData()) == null) {
                return null;
            }
            MediaMetadata.Builder builder = new MediaMetadata.Builder();
            builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, currentPlayingData.title);
            builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, currentPlayingData.getSubTitle());
            builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, currentPlayingData.albumName);
            builder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, currentPlayingData.outCoverBitmap);
            return builder.build();
        }

        @Override // com.xiaomi.micolauncher.IMicoRemoteService
        public int getPlaybackState() {
            MediaSessionController instance = MediaSessionController.getInstance();
            if (instance == null) {
                return 0;
            }
            switch (instance.getPlayStatus()) {
                case 0:
                    return 1;
                case 1:
                    return 3;
                case 2:
                    return 2;
                default:
                    return 0;
            }
        }

        @Override // com.xiaomi.micolauncher.IMicoRemoteService
        public void notifyMediaMetadataChanged(MediaMetadata mediaMetadata) {
            Message obtainMessage = MicoRemoteService.this.c.obtainMessage();
            obtainMessage.what = 1;
            obtainMessage.obj = mediaMetadata;
            MicoRemoteService.this.c.sendMessage(obtainMessage);
        }

        @Override // com.xiaomi.micolauncher.IMicoRemoteService
        public void notifyPlaybackStateChanged(int i) {
            Message obtainMessage = MicoRemoteService.this.c.obtainMessage();
            obtainMessage.what = 2;
            obtainMessage.arg1 = i;
            MicoRemoteService.this.c.sendMessage(obtainMessage);
        }
    };

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.c = new a(ThreadUtil.getLightWorkHandler().getLooper());
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return this.d;
    }

    /* loaded from: classes3.dex */
    private class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.d("MicoRemoteService", "MicoRemoteServiceHandler.handleMessage, msg.what = " + message.what);
            switch (message.what) {
                case 1:
                    MediaMetadata mediaMetadata = (MediaMetadata) message.obj;
                    synchronized (MicoRemoteService.this.a) {
                        if (MicoRemoteService.this.b != null) {
                            try {
                                MicoRemoteService.this.b.onMetadataChanged(mediaMetadata);
                            } catch (RemoteException e) {
                                Log.e("MicoRemoteService", "systemUIMediaPlayerCallback.onMetadataChanged throws android.os.RemoteException", e);
                            }
                        }
                    }
                    return;
                case 2:
                    int i = message.arg1;
                    synchronized (MicoRemoteService.this.a) {
                        if (MicoRemoteService.this.b != null) {
                            try {
                                MicoRemoteService.this.b.onPlaybackStateChanged(i);
                            } catch (RemoteException e2) {
                                Log.e("MicoRemoteService", "systemUIMediaPlayerCallback.onPlaybackStateChanged throws android.os.RemoteException", e2);
                            }
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
