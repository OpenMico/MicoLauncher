package android.support.v4.media.session;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.app.BundleCompat;
import androidx.media.AudioAttributesCompat;
import androidx.media.R;
import androidx.versionedparcelable.ParcelUtils;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class MediaControllerCompat {
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String COMMAND_ADD_QUEUE_ITEM = "android.support.v4.media.session.command.ADD_QUEUE_ITEM";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String COMMAND_ADD_QUEUE_ITEM_AT = "android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String COMMAND_ARGUMENT_INDEX = "android.support.v4.media.session.command.ARGUMENT_INDEX";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String COMMAND_ARGUMENT_MEDIA_DESCRIPTION = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String COMMAND_GET_EXTRA_BINDER = "android.support.v4.media.session.command.GET_EXTRA_BINDER";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String COMMAND_REMOVE_QUEUE_ITEM = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String COMMAND_REMOVE_QUEUE_ITEM_AT = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT";
    private final a a;
    private final MediaSessionCompat.Token b;
    @SuppressLint({"BanConcurrentHashMap"})
    private final ConcurrentHashMap<Callback, Boolean> c = new ConcurrentHashMap<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface a {
        TransportControls a();

        void a(int i, int i2);

        void a(MediaDescriptionCompat mediaDescriptionCompat);

        void a(MediaDescriptionCompat mediaDescriptionCompat, int i);

        void a(Callback callback);

        void a(Callback callback, Handler handler);

        void a(String str, Bundle bundle, ResultReceiver resultReceiver);

        boolean a(KeyEvent keyEvent);

        PlaybackStateCompat b();

        void b(int i, int i2);

        void b(MediaDescriptionCompat mediaDescriptionCompat);

        MediaMetadataCompat c();

        List<MediaSessionCompat.QueueItem> d();

        CharSequence e();

        Bundle f();

        int g();

        boolean h();

        int i();

        int j();

        long k();

        PlaybackInfo l();

        PendingIntent m();

        boolean n();

        String o();

        Bundle p();

        Object q();
    }

    public static void setMediaController(@NonNull Activity activity, MediaControllerCompat mediaControllerCompat) {
        activity.getWindow().getDecorView().setTag(R.id.media_controller_compat_view_tag, mediaControllerCompat);
        if (Build.VERSION.SDK_INT >= 21) {
            MediaControllerImplApi21.a(activity, mediaControllerCompat);
        }
    }

    public static MediaControllerCompat getMediaController(@NonNull Activity activity) {
        Object tag = activity.getWindow().getDecorView().getTag(R.id.media_controller_compat_view_tag);
        if (tag instanceof MediaControllerCompat) {
            return (MediaControllerCompat) tag;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return MediaControllerImplApi21.a(activity);
        }
        return null;
    }

    static void a(String str, Bundle bundle) {
        if (str != null) {
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1348483723) {
                if (hashCode == 503011406 && str.equals(MediaSessionCompat.ACTION_UNFOLLOW)) {
                    c2 = 1;
                }
            } else if (str.equals(MediaSessionCompat.ACTION_FOLLOW)) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                case 1:
                    if (bundle == null || !bundle.containsKey(MediaSessionCompat.ARGUMENT_MEDIA_ATTRIBUTE)) {
                        throw new IllegalArgumentException("An extra field android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE is required for this action " + str + ".");
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public MediaControllerCompat(Context context, @NonNull MediaSessionCompat mediaSessionCompat) {
        if (mediaSessionCompat != null) {
            this.b = mediaSessionCompat.getSessionToken();
            if (Build.VERSION.SDK_INT >= 29) {
                this.a = new b(context, this.b);
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.a = new MediaControllerImplApi21(context, this.b);
            } else {
                this.a = new c(this.b);
            }
        } else {
            throw new IllegalArgumentException("session must not be null");
        }
    }

    public MediaControllerCompat(Context context, @NonNull MediaSessionCompat.Token token) {
        if (token != null) {
            this.b = token;
            if (Build.VERSION.SDK_INT >= 21) {
                this.a = new MediaControllerImplApi21(context, token);
            } else {
                this.a = new c(token);
            }
        } else {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
    }

    public TransportControls getTransportControls() {
        return this.a.a();
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return this.a.a(keyEvent);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public PlaybackStateCompat getPlaybackState() {
        return this.a.b();
    }

    public MediaMetadataCompat getMetadata() {
        return this.a.c();
    }

    public List<MediaSessionCompat.QueueItem> getQueue() {
        return this.a.d();
    }

    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.a.a(mediaDescriptionCompat);
    }

    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i) {
        this.a.a(mediaDescriptionCompat, i);
    }

    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.a.b(mediaDescriptionCompat);
    }

    @Deprecated
    public void removeQueueItemAt(int i) {
        MediaSessionCompat.QueueItem queueItem;
        List<MediaSessionCompat.QueueItem> queue = getQueue();
        if (queue != null && i >= 0 && i < queue.size() && (queueItem = queue.get(i)) != null) {
            removeQueueItem(queueItem.getDescription());
        }
    }

    public CharSequence getQueueTitle() {
        return this.a.e();
    }

    public Bundle getExtras() {
        return this.a.f();
    }

    public int getRatingType() {
        return this.a.g();
    }

    public boolean isCaptioningEnabled() {
        return this.a.h();
    }

    public int getRepeatMode() {
        return this.a.i();
    }

    public int getShuffleMode() {
        return this.a.j();
    }

    public long getFlags() {
        return this.a.k();
    }

    public PlaybackInfo getPlaybackInfo() {
        return this.a.l();
    }

    public PendingIntent getSessionActivity() {
        return this.a.m();
    }

    public MediaSessionCompat.Token getSessionToken() {
        return this.b;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public VersionedParcelable getSession2Token() {
        return this.b.getSession2Token();
    }

    public void setVolumeTo(int i, int i2) {
        this.a.a(i, i2);
    }

    public void adjustVolume(int i, int i2) {
        this.a.b(i, i2);
    }

    public void registerCallback(@NonNull Callback callback) {
        registerCallback(callback, null);
    }

    public void registerCallback(@NonNull Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        } else if (this.c.putIfAbsent(callback, true) != null) {
            Log.w("MediaControllerCompat", "the callback has already been registered");
        } else {
            if (handler == null) {
                handler = new Handler();
            }
            callback.a(handler);
            this.a.a(callback, handler);
        }
    }

    public void unregisterCallback(@NonNull Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        } else if (this.c.remove(callback) == null) {
            Log.w("MediaControllerCompat", "the callback has never been registered");
        } else {
            try {
                this.a.a(callback);
            } finally {
                callback.a(null);
            }
        }
    }

    public void sendCommand(@NonNull String str, @Nullable Bundle bundle, @Nullable ResultReceiver resultReceiver) {
        if (!TextUtils.isEmpty(str)) {
            this.a.a(str, bundle, resultReceiver);
            return;
        }
        throw new IllegalArgumentException("command must neither be null nor empty");
    }

    public boolean isSessionReady() {
        return this.a.n();
    }

    public String getPackageName() {
        return this.a.o();
    }

    @NonNull
    public Bundle getSessionInfo() {
        return this.a.p();
    }

    public Object getMediaController() {
        return this.a.q();
    }

    /* loaded from: classes.dex */
    public static abstract class Callback implements IBinder.DeathRecipient {
        final MediaController.Callback a;
        b b;
        IMediaControllerCallback c;

        public void onAudioInfoChanged(PlaybackInfo playbackInfo) {
        }

        public void onCaptioningEnabledChanged(boolean z) {
        }

        public void onExtrasChanged(Bundle bundle) {
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) {
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
        }

        public void onRepeatModeChanged(int i) {
        }

        public void onSessionDestroyed() {
        }

        public void onSessionEvent(String str, Bundle bundle) {
        }

        public void onSessionReady() {
        }

        public void onShuffleModeChanged(int i) {
        }

        public Callback() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.a = new a(this);
                return;
            }
            this.a = null;
            this.c = new c(this);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public IMediaControllerCallback getIControllerCallback() {
            return this.c;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            a(8, null, null);
        }

        void a(Handler handler) {
            if (handler == null) {
                b bVar = this.b;
                if (bVar != null) {
                    bVar.a = false;
                    bVar.removeCallbacksAndMessages(null);
                    this.b = null;
                    return;
                }
                return;
            }
            this.b = new b(handler.getLooper());
            this.b.a = true;
        }

        void a(int i, Object obj, Bundle bundle) {
            b bVar = this.b;
            if (bVar != null) {
                Message obtainMessage = bVar.obtainMessage(i, obj);
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }
        }

        @RequiresApi(21)
        /* loaded from: classes.dex */
        private static class a extends MediaController.Callback {
            private final WeakReference<Callback> a;

            a(Callback callback) {
                this.a = new WeakReference<>(callback);
            }

            @Override // android.media.session.MediaController.Callback
            public void onSessionDestroyed() {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onSessionDestroyed();
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onSessionEvent(String str, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback callback = this.a.get();
                if (callback == null) {
                    return;
                }
                if (callback.c == null || Build.VERSION.SDK_INT >= 23) {
                    callback.onSessionEvent(str, bundle);
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onPlaybackStateChanged(PlaybackState playbackState) {
                Callback callback = this.a.get();
                if (callback != null && callback.c == null) {
                    callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(playbackState));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onMetadataChanged(MediaMetadata mediaMetadata) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(mediaMetadata));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onQueueChanged(List<MediaSession.QueueItem> list) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(list));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onQueueTitleChanged(charSequence);
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onExtrasChanged(Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onExtrasChanged(bundle);
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onAudioInfoChanged(new PlaybackInfo(playbackInfo.getPlaybackType(), AudioAttributesCompat.wrap(playbackInfo.getAudioAttributes()), playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume()));
                }
            }
        }

        /* loaded from: classes.dex */
        private static class c extends IMediaControllerCallback.Stub {
            private final WeakReference<Callback> a;

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onShuffleModeChangedRemoved(boolean z) throws RemoteException {
            }

            c(Callback callback) {
                this.a = new WeakReference<>(callback);
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onEvent(String str, Bundle bundle) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(1, str, bundle);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(8, null, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(2, playbackStateCompat, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(3, mediaMetadataCompat, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(5, list, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(6, charSequence, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onCaptioningEnabledChanged(boolean z) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(11, Boolean.valueOf(z), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onRepeatModeChanged(int i) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(9, Integer.valueOf(i), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onShuffleModeChanged(int i) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(12, Integer.valueOf(i), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(7, bundle, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(4, parcelableVolumeInfo != null ? new PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume) : null, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionReady() throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(13, null, null);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public class b extends Handler {
            boolean a = false;

            b(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (this.a) {
                    switch (message.what) {
                        case 1:
                            Bundle data = message.getData();
                            MediaSessionCompat.ensureClassLoader(data);
                            Callback.this.onSessionEvent((String) message.obj, data);
                            return;
                        case 2:
                            Callback.this.onPlaybackStateChanged((PlaybackStateCompat) message.obj);
                            return;
                        case 3:
                            Callback.this.onMetadataChanged((MediaMetadataCompat) message.obj);
                            return;
                        case 4:
                            Callback.this.onAudioInfoChanged((PlaybackInfo) message.obj);
                            return;
                        case 5:
                            Callback.this.onQueueChanged((List) message.obj);
                            return;
                        case 6:
                            Callback.this.onQueueTitleChanged((CharSequence) message.obj);
                            return;
                        case 7:
                            Bundle bundle = (Bundle) message.obj;
                            MediaSessionCompat.ensureClassLoader(bundle);
                            Callback.this.onExtrasChanged(bundle);
                            return;
                        case 8:
                            Callback.this.onSessionDestroyed();
                            return;
                        case 9:
                            Callback.this.onRepeatModeChanged(((Integer) message.obj).intValue());
                            return;
                        case 10:
                        default:
                            return;
                        case 11:
                            Callback.this.onCaptioningEnabledChanged(((Boolean) message.obj).booleanValue());
                            return;
                        case 12:
                            Callback.this.onShuffleModeChanged(((Integer) message.obj).intValue());
                            return;
                        case 13:
                            Callback.this.onSessionReady();
                            return;
                    }
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static abstract class TransportControls {
        @Deprecated
        public static final String EXTRA_LEGACY_STREAM_TYPE = "android.media.session.extra.LEGACY_STREAM_TYPE";

        public abstract void fastForward();

        public abstract void pause();

        public abstract void play();

        public abstract void playFromMediaId(String str, Bundle bundle);

        public abstract void playFromSearch(String str, Bundle bundle);

        public abstract void playFromUri(Uri uri, Bundle bundle);

        public abstract void prepare();

        public abstract void prepareFromMediaId(String str, Bundle bundle);

        public abstract void prepareFromSearch(String str, Bundle bundle);

        public abstract void prepareFromUri(Uri uri, Bundle bundle);

        public abstract void rewind();

        public abstract void seekTo(long j);

        public abstract void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle);

        public abstract void sendCustomAction(String str, Bundle bundle);

        public abstract void setCaptioningEnabled(boolean z);

        public void setPlaybackSpeed(float f) {
        }

        public abstract void setRating(RatingCompat ratingCompat);

        public abstract void setRating(RatingCompat ratingCompat, Bundle bundle);

        public abstract void setRepeatMode(int i);

        public abstract void setShuffleMode(int i);

        public abstract void skipToNext();

        public abstract void skipToPrevious();

        public abstract void skipToQueueItem(long j);

        public abstract void stop();

        TransportControls() {
        }
    }

    /* loaded from: classes.dex */
    public static final class PlaybackInfo {
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        private final int a;
        private final AudioAttributesCompat b;
        private final int c;
        private final int d;
        private final int e;

        PlaybackInfo(int i, int i2, int i3, int i4, int i5) {
            this(i, new AudioAttributesCompat.Builder().setLegacyStreamType(i2).build(), i3, i4, i5);
        }

        PlaybackInfo(int i, @NonNull AudioAttributesCompat audioAttributesCompat, int i2, int i3, int i4) {
            this.a = i;
            this.b = audioAttributesCompat;
            this.c = i2;
            this.d = i3;
            this.e = i4;
        }

        public int getPlaybackType() {
            return this.a;
        }

        @Deprecated
        public int getAudioStream() {
            return this.b.getLegacyStreamType();
        }

        @NonNull
        public AudioAttributesCompat getAudioAttributes() {
            return this.b;
        }

        public int getVolumeControl() {
            return this.c;
        }

        public int getMaxVolume() {
            return this.d;
        }

        public int getCurrentVolume() {
            return this.e;
        }
    }

    /* loaded from: classes.dex */
    static class c implements a {
        private IMediaSession a;
        private TransportControls b;
        private Bundle c;

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public boolean n() {
            return true;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public Object q() {
            return null;
        }

        c(MediaSessionCompat.Token token) {
            this.a = IMediaSession.Stub.asInterface((IBinder) token.getToken());
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(Callback callback, Handler handler) {
            if (callback != null) {
                try {
                    this.a.asBinder().linkToDeath(callback, 0);
                    this.a.registerCallbackListener(callback.c);
                    callback.a(13, null, null);
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                    callback.a(8, null, null);
                }
            } else {
                throw new IllegalArgumentException("callback may not be null.");
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(Callback callback) {
            if (callback != null) {
                try {
                    this.a.unregisterCallbackListener(callback.c);
                    this.a.asBinder().unlinkToDeath(callback, 0);
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", e);
                }
            } else {
                throw new IllegalArgumentException("callback may not be null.");
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public boolean a(KeyEvent keyEvent) {
            if (keyEvent != null) {
                try {
                    this.a.sendMediaButton(keyEvent);
                    return false;
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in dispatchMediaButtonEvent.", e);
                    return false;
                }
            } else {
                throw new IllegalArgumentException("event may not be null.");
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public TransportControls a() {
            if (this.b == null) {
                this.b = new h(this.a);
            }
            return this.b;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public PlaybackStateCompat b() {
            try {
                return this.a.getPlaybackState();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public MediaMetadataCompat c() {
            try {
                return this.a.getMetadata();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getMetadata.", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public List<MediaSessionCompat.QueueItem> d() {
            try {
                return this.a.getQueue();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getQueue.", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(MediaDescriptionCompat mediaDescriptionCompat) {
            try {
                if ((this.a.getFlags() & 4) != 0) {
                    this.a.addQueueItem(mediaDescriptionCompat);
                    return;
                }
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in addQueueItem.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(MediaDescriptionCompat mediaDescriptionCompat, int i) {
            try {
                if ((this.a.getFlags() & 4) != 0) {
                    this.a.addQueueItemAt(mediaDescriptionCompat, i);
                    return;
                }
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in addQueueItemAt.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void b(MediaDescriptionCompat mediaDescriptionCompat) {
            try {
                if ((this.a.getFlags() & 4) != 0) {
                    this.a.removeQueueItem(mediaDescriptionCompat);
                    return;
                }
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in removeQueueItem.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public CharSequence e() {
            try {
                return this.a.getQueueTitle();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getQueueTitle.", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public Bundle f() {
            try {
                return this.a.getExtras();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getExtras.", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public int g() {
            try {
                return this.a.getRatingType();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getRatingType.", e);
                return 0;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public boolean h() {
            try {
                return this.a.isCaptioningEnabled();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", e);
                return false;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public int i() {
            try {
                return this.a.getRepeatMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", e);
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public int j() {
            try {
                return this.a.getShuffleMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", e);
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public long k() {
            try {
                return this.a.getFlags();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getFlags.", e);
                return 0L;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public PlaybackInfo l() {
            try {
                ParcelableVolumeInfo volumeAttributes = this.a.getVolumeAttributes();
                return new PlaybackInfo(volumeAttributes.volumeType, volumeAttributes.audioStream, volumeAttributes.controlType, volumeAttributes.maxVolume, volumeAttributes.currentVolume);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getPlaybackInfo.", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public PendingIntent m() {
            try {
                return this.a.getLaunchPendingIntent();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getSessionActivity.", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(int i, int i2) {
            try {
                this.a.setVolumeTo(i, i2, null);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setVolumeTo.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void b(int i, int i2) {
            try {
                this.a.adjustVolume(i, i2, null);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in adjustVolume.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
            try {
                this.a.sendCommand(str, bundle, resultReceiver == null ? null : new MediaSessionCompat.ResultReceiverWrapper(resultReceiver));
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in sendCommand.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public String o() {
            try {
                return this.a.getPackageName();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getPackageName.", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public Bundle p() {
            try {
                this.c = this.a.getSessionInfo();
            } catch (RemoteException e) {
                Log.d("MediaControllerCompat", "Dead object in getSessionInfo.", e);
            }
            this.c = MediaSessionCompat.unparcelWithClassLoader(this.c);
            Bundle bundle = this.c;
            return bundle == null ? Bundle.EMPTY : new Bundle(bundle);
        }
    }

    /* loaded from: classes.dex */
    static class h extends TransportControls {
        private IMediaSession a;

        public h(IMediaSession iMediaSession) {
            this.a = iMediaSession;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepare() {
            try {
                this.a.prepare();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in prepare.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromMediaId(String str, Bundle bundle) {
            try {
                this.a.prepareFromMediaId(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in prepareFromMediaId.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromSearch(String str, Bundle bundle) {
            try {
                this.a.prepareFromSearch(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in prepareFromSearch.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromUri(Uri uri, Bundle bundle) {
            try {
                this.a.prepareFromUri(uri, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in prepareFromUri.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void play() {
            try {
                this.a.play();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in play.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromMediaId(String str, Bundle bundle) {
            try {
                this.a.playFromMediaId(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in playFromMediaId.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromSearch(String str, Bundle bundle) {
            try {
                this.a.playFromSearch(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in playFromSearch.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromUri(Uri uri, Bundle bundle) {
            try {
                this.a.playFromUri(uri, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in playFromUri.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToQueueItem(long j) {
            try {
                this.a.skipToQueueItem(j);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in skipToQueueItem.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void pause() {
            try {
                this.a.pause();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in pause.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void stop() {
            try {
                this.a.stop();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in stop.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void seekTo(long j) {
            try {
                this.a.seekTo(j);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in seekTo.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void fastForward() {
            try {
                this.a.fastForward();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in fastForward.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToNext() {
            try {
                this.a.next();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in skipToNext.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void rewind() {
            try {
                this.a.rewind();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in rewind.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToPrevious() {
            try {
                this.a.previous();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in skipToPrevious.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat) {
            try {
                this.a.rate(ratingCompat);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setRating.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat, Bundle bundle) {
            try {
                this.a.rateWithExtras(ratingCompat, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setRating.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setPlaybackSpeed(float f) {
            if (f != 0.0f) {
                try {
                    this.a.setPlaybackSpeed(f);
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in setPlaybackSpeed.", e);
                }
            } else {
                throw new IllegalArgumentException("speed must not be zero");
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setCaptioningEnabled(boolean z) {
            try {
                this.a.setCaptioningEnabled(z);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setCaptioningEnabled.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRepeatMode(int i) {
            try {
                this.a.setRepeatMode(i);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setRepeatMode.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setShuffleMode(int i) {
            try {
                this.a.setShuffleMode(i);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in setShuffleMode.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
            sendCustomAction(customAction.getAction(), bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.a(str, bundle);
            try {
                this.a.sendCustomAction(str, bundle);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in sendCustomAction.", e);
            }
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    static class MediaControllerImplApi21 implements a {
        protected final MediaController a;
        protected Bundle c;
        final MediaSessionCompat.Token d;
        final Object b = new Object();
        @GuardedBy("mLock")
        private final List<Callback> e = new ArrayList();
        private HashMap<Callback, a> f = new HashMap<>();

        MediaControllerImplApi21(Context context, MediaSessionCompat.Token token) {
            this.d = token;
            this.a = new MediaController(context, (MediaSession.Token) this.d.getToken());
            if (this.d.getExtraBinder() == null) {
                s();
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public final void a(Callback callback, Handler handler) {
            this.a.registerCallback(callback.a, handler);
            synchronized (this.b) {
                if (this.d.getExtraBinder() != null) {
                    a aVar = new a(callback);
                    this.f.put(callback, aVar);
                    callback.c = aVar;
                    try {
                        this.d.getExtraBinder().registerCallbackListener(aVar);
                        callback.a(13, null, null);
                    } catch (RemoteException e) {
                        Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                    }
                } else {
                    callback.c = null;
                    this.e.add(callback);
                }
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public final void a(Callback callback) {
            this.a.unregisterCallback(callback.a);
            synchronized (this.b) {
                if (this.d.getExtraBinder() != null) {
                    try {
                        a remove = this.f.remove(callback);
                        if (remove != null) {
                            callback.c = null;
                            this.d.getExtraBinder().unregisterCallbackListener(remove);
                        }
                    } catch (RemoteException e) {
                        Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", e);
                    }
                } else {
                    this.e.remove(callback);
                }
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public boolean a(KeyEvent keyEvent) {
            return this.a.dispatchMediaButtonEvent(keyEvent);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public TransportControls a() {
            MediaController.TransportControls transportControls = this.a.getTransportControls();
            if (Build.VERSION.SDK_INT >= 29) {
                return new g(transportControls);
            }
            if (Build.VERSION.SDK_INT >= 24) {
                return new f(transportControls);
            }
            if (Build.VERSION.SDK_INT >= 23) {
                return new e(transportControls);
            }
            return new d(transportControls);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public PlaybackStateCompat b() {
            if (this.d.getExtraBinder() != null) {
                try {
                    return this.d.getExtraBinder().getPlaybackState();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", e);
                }
            }
            PlaybackState playbackState = this.a.getPlaybackState();
            if (playbackState != null) {
                return PlaybackStateCompat.fromPlaybackState(playbackState);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public MediaMetadataCompat c() {
            MediaMetadata metadata = this.a.getMetadata();
            if (metadata != null) {
                return MediaMetadataCompat.fromMediaMetadata(metadata);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public List<MediaSessionCompat.QueueItem> d() {
            List<MediaSession.QueueItem> queue = this.a.getQueue();
            if (queue != null) {
                return MediaSessionCompat.QueueItem.fromQueueItemList(queue);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((k() & 4) != 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat);
                a(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM, bundle, null);
                return;
            }
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if ((k() & 4) != 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat);
                bundle.putInt(MediaControllerCompat.COMMAND_ARGUMENT_INDEX, i);
                a(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM_AT, bundle, null);
                return;
            }
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void b(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((k() & 4) != 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat);
                a(MediaControllerCompat.COMMAND_REMOVE_QUEUE_ITEM, bundle, null);
                return;
            }
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public CharSequence e() {
            return this.a.getQueueTitle();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public Bundle f() {
            return this.a.getExtras();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public int g() {
            if (Build.VERSION.SDK_INT < 22 && this.d.getExtraBinder() != null) {
                try {
                    return this.d.getExtraBinder().getRatingType();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in getRatingType.", e);
                }
            }
            return this.a.getRatingType();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public boolean h() {
            if (this.d.getExtraBinder() == null) {
                return false;
            }
            try {
                return this.d.getExtraBinder().isCaptioningEnabled();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", e);
                return false;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public int i() {
            if (this.d.getExtraBinder() == null) {
                return -1;
            }
            try {
                return this.d.getExtraBinder().getRepeatMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", e);
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public int j() {
            if (this.d.getExtraBinder() == null) {
                return -1;
            }
            try {
                return this.d.getExtraBinder().getShuffleMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", e);
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public long k() {
            return this.a.getFlags();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public PlaybackInfo l() {
            MediaController.PlaybackInfo playbackInfo = this.a.getPlaybackInfo();
            if (playbackInfo != null) {
                return new PlaybackInfo(playbackInfo.getPlaybackType(), AudioAttributesCompat.wrap(playbackInfo.getAudioAttributes()), playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume());
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public PendingIntent m() {
            return this.a.getSessionActivity();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(int i, int i2) {
            this.a.setVolumeTo(i, i2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void b(int i, int i2) {
            this.a.adjustVolume(i, i2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
            this.a.sendCommand(str, bundle, resultReceiver);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public boolean n() {
            return this.d.getExtraBinder() != null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public String o() {
            return this.a.getPackageName();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public Bundle p() {
            Bundle bundle = this.c;
            if (bundle != null) {
                return new Bundle(bundle);
            }
            if (this.d.getExtraBinder() != null) {
                try {
                    this.c = this.d.getExtraBinder().getSessionInfo();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in getSessionInfo.", e);
                    this.c = Bundle.EMPTY;
                }
            }
            this.c = MediaSessionCompat.unparcelWithClassLoader(this.c);
            Bundle bundle2 = this.c;
            return bundle2 == null ? Bundle.EMPTY : new Bundle(bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.a
        public Object q() {
            return this.a;
        }

        private void s() {
            a(MediaControllerCompat.COMMAND_GET_EXTRA_BINDER, null, new ExtraBinderRequestResultReceiver(this));
        }

        @GuardedBy("mLock")
        void r() {
            if (this.d.getExtraBinder() != null) {
                for (Callback callback : this.e) {
                    a aVar = new a(callback);
                    this.f.put(callback, aVar);
                    callback.c = aVar;
                    try {
                        this.d.getExtraBinder().registerCallbackListener(aVar);
                        callback.a(13, null, null);
                    } catch (RemoteException e) {
                        Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                    }
                }
                this.e.clear();
            }
        }

        static void a(@NonNull Activity activity, @Nullable MediaControllerCompat mediaControllerCompat) {
            activity.setMediaController(mediaControllerCompat != null ? new MediaController(activity, (MediaSession.Token) mediaControllerCompat.getSessionToken().getToken()) : null);
        }

        @Nullable
        static MediaControllerCompat a(@NonNull Activity activity) {
            MediaController mediaController = activity.getMediaController();
            if (mediaController == null) {
                return null;
            }
            return new MediaControllerCompat(activity, MediaSessionCompat.Token.fromToken(mediaController.getSessionToken()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class ExtraBinderRequestResultReceiver extends ResultReceiver {
            private WeakReference<MediaControllerImplApi21> a;

            ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImplApi21) {
                super(null);
                this.a = new WeakReference<>(mediaControllerImplApi21);
            }

            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int i, Bundle bundle) {
                MediaControllerImplApi21 mediaControllerImplApi21 = this.a.get();
                if (mediaControllerImplApi21 != null && bundle != null) {
                    synchronized (mediaControllerImplApi21.b) {
                        mediaControllerImplApi21.d.setExtraBinder(IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, MediaSessionCompat.KEY_EXTRA_BINDER)));
                        mediaControllerImplApi21.d.setSession2Token(ParcelUtils.getVersionedParcelable(bundle, MediaSessionCompat.KEY_SESSION2_TOKEN));
                        mediaControllerImplApi21.r();
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class a extends Callback.c {
            a(Callback callback) {
                super(callback);
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.c, android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.c, android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.c, android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.c, android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.c, android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.c, android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                throw new AssertionError();
            }
        }
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    static class b extends MediaControllerImplApi21 {
        b(Context context, MediaSessionCompat.Token token) {
            super(context, token);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21, android.support.v4.media.session.MediaControllerCompat.a
        public Bundle p() {
            if (this.c != null) {
                return new Bundle(this.c);
            }
            this.c = this.a.getSessionInfo();
            this.c = MediaSessionCompat.unparcelWithClassLoader(this.c);
            return this.c == null ? Bundle.EMPTY : new Bundle(this.c);
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    static class d extends TransportControls {
        protected final MediaController.TransportControls a;

        d(MediaController.TransportControls transportControls) {
            this.a = transportControls;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepare() {
            sendCustomAction(MediaSessionCompat.ACTION_PREPARE, (Bundle) null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromMediaId(String str, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID, str);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromSearch(String str, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaSessionCompat.ACTION_ARGUMENT_QUERY, str);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromUri(Uri uri, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI, uri);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_PREPARE_FROM_URI, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void play() {
            this.a.play();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void pause() {
            this.a.pause();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void stop() {
            this.a.stop();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void seekTo(long j) {
            this.a.seekTo(j);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void fastForward() {
            this.a.fastForward();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void rewind() {
            this.a.rewind();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToNext() {
            this.a.skipToNext();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToPrevious() {
            this.a.skipToPrevious();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat) {
            this.a.setRating(ratingCompat != null ? (Rating) ratingCompat.getRating() : null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable(MediaSessionCompat.ACTION_ARGUMENT_RATING, ratingCompat);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_SET_RATING, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setPlaybackSpeed(float f) {
            if (f != 0.0f) {
                Bundle bundle = new Bundle();
                bundle.putFloat(MediaSessionCompat.ACTION_ARGUMENT_PLAYBACK_SPEED, f);
                sendCustomAction(MediaSessionCompat.ACTION_SET_PLAYBACK_SPEED, bundle);
                return;
            }
            throw new IllegalArgumentException("speed must not be zero");
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setCaptioningEnabled(boolean z) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(MediaSessionCompat.ACTION_ARGUMENT_CAPTIONING_ENABLED, z);
            sendCustomAction(MediaSessionCompat.ACTION_SET_CAPTIONING_ENABLED, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRepeatMode(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt(MediaSessionCompat.ACTION_ARGUMENT_REPEAT_MODE, i);
            sendCustomAction(MediaSessionCompat.ACTION_SET_REPEAT_MODE, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setShuffleMode(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt(MediaSessionCompat.ACTION_ARGUMENT_SHUFFLE_MODE, i);
            sendCustomAction(MediaSessionCompat.ACTION_SET_SHUFFLE_MODE, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromMediaId(String str, Bundle bundle) {
            this.a.playFromMediaId(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromSearch(String str, Bundle bundle) {
            this.a.playFromSearch(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromUri(Uri uri, Bundle bundle) {
            if (uri == null || Uri.EMPTY.equals(uri)) {
                throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI, uri);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_PLAY_FROM_URI, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToQueueItem(long j) {
            this.a.skipToQueueItem(j);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
            MediaControllerCompat.a(customAction.getAction(), bundle);
            this.a.sendCustomAction(customAction.getAction(), bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.a(str, bundle);
            this.a.sendCustomAction(str, bundle);
        }
    }

    @RequiresApi(23)
    /* loaded from: classes.dex */
    static class e extends d {
        e(MediaController.TransportControls transportControls) {
            super(transportControls);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.d, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromUri(Uri uri, Bundle bundle) {
            this.a.playFromUri(uri, bundle);
        }
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    static class f extends e {
        f(MediaController.TransportControls transportControls) {
            super(transportControls);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.d, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepare() {
            this.a.prepare();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.d, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromMediaId(String str, Bundle bundle) {
            this.a.prepareFromMediaId(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.d, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromSearch(String str, Bundle bundle) {
            this.a.prepareFromSearch(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.d, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromUri(Uri uri, Bundle bundle) {
            this.a.prepareFromUri(uri, bundle);
        }
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    static class g extends f {
        g(MediaController.TransportControls transportControls) {
            super(transportControls);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.d, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setPlaybackSpeed(float f) {
            if (f != 0.0f) {
                this.a.setPlaybackSpeed(f);
                return;
            }
            throw new IllegalArgumentException("speed must not be zero");
        }
    }
}
