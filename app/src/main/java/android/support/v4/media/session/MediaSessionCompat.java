package android.support.v4.media.session;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import androidx.annotation.DoNotInline;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.app.BundleCompat;
import androidx.core.os.BuildCompat;
import androidx.media.MediaBrowserProtocol;
import androidx.media.MediaSessionManager;
import androidx.media.VolumeProviderCompat;
import androidx.media.session.MediaButtonReceiver;
import androidx.versionedparcelable.ParcelUtils;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class MediaSessionCompat {
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_CAPTIONING_ENABLED = "android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_PLAYBACK_SPEED = "android.support.v4.media.session.action.ARGUMENT_PLAYBACK_SPEED";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_RATING = "android.support.v4.media.session.action.ARGUMENT_RATING";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_REPEAT_MODE = "android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_SHUFFLE_MODE = "android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
    public static final String ACTION_FLAG_AS_INAPPROPRIATE = "android.support.v4.media.session.action.FLAG_AS_INAPPROPRIATE";
    public static final String ACTION_FOLLOW = "android.support.v4.media.session.action.FOLLOW";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_CAPTIONING_ENABLED = "android.support.v4.media.session.action.SET_CAPTIONING_ENABLED";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_PLAYBACK_SPEED = "android.support.v4.media.session.action.SET_PLAYBACK_SPEED";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_RATING = "android.support.v4.media.session.action.SET_RATING";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_REPEAT_MODE = "android.support.v4.media.session.action.SET_REPEAT_MODE";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_SHUFFLE_MODE = "android.support.v4.media.session.action.SET_SHUFFLE_MODE";
    public static final String ACTION_SKIP_AD = "android.support.v4.media.session.action.SKIP_AD";
    public static final String ACTION_UNFOLLOW = "android.support.v4.media.session.action.UNFOLLOW";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE_VALUE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE_VALUE";
    @Deprecated
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_QUEUE_COMMANDS = 4;
    @Deprecated
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_EXTRA_BINDER = "android.support.v4.media.session.EXTRA_BINDER";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_SESSION2_TOKEN = "android.support.v4.media.session.SESSION_TOKEN2";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_TOKEN = "android.support.v4.media.session.TOKEN";
    public static final int MEDIA_ATTRIBUTE_ALBUM = 1;
    public static final int MEDIA_ATTRIBUTE_ARTIST = 0;
    public static final int MEDIA_ATTRIBUTE_PLAYLIST = 2;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int PENDING_INTENT_FLAG_MUTABLE;
    static int a;
    private final a b;
    private final MediaControllerCompat c;
    private final ArrayList<OnActiveChangeListener> d;

    /* loaded from: classes.dex */
    public interface OnActiveChangeListener {
        void onActiveChanged();
    }

    /* loaded from: classes.dex */
    public interface a {
        void a(int i);

        void a(PendingIntent pendingIntent);

        void a(Bundle bundle);

        void a(MediaMetadataCompat mediaMetadataCompat);

        void a(Callback callback, Handler handler);

        void a(PlaybackStateCompat playbackStateCompat);

        void a(MediaSessionManager.RemoteUserInfo remoteUserInfo);

        void a(VolumeProviderCompat volumeProviderCompat);

        void a(CharSequence charSequence);

        void a(String str, Bundle bundle);

        void a(List<QueueItem> list);

        void a(boolean z);

        boolean a();

        void b();

        void b(int i);

        void b(PendingIntent pendingIntent);

        void b(boolean z);

        Token c();

        void c(int i);

        PlaybackStateCompat d();

        void d(int i);

        Object e();

        void e(int i);

        Object f();

        String g();

        MediaSessionManager.RemoteUserInfo h();

        Callback i();
    }

    static {
        PENDING_INTENT_FLAG_MUTABLE = BuildCompat.isAtLeastS() ? 33554432 : 0;
    }

    public MediaSessionCompat(@NonNull Context context, @NonNull String str) {
        this(context, str, null, null);
    }

    public MediaSessionCompat(@NonNull Context context, @NonNull String str, @Nullable ComponentName componentName, @Nullable PendingIntent pendingIntent) {
        this(context, str, componentName, pendingIntent, null);
    }

    public MediaSessionCompat(@NonNull Context context, @NonNull String str, @Nullable ComponentName componentName, @Nullable PendingIntent pendingIntent, @Nullable Bundle bundle) {
        this(context, str, componentName, pendingIntent, bundle, null);
    }

    @SuppressLint({"WrongConstant"})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public MediaSessionCompat(@NonNull Context context, @NonNull String str, @Nullable ComponentName componentName, @Nullable PendingIntent pendingIntent, @Nullable Bundle bundle, @Nullable VersionedParcelable versionedParcelable) {
        ComponentName componentName2;
        PendingIntent pendingIntent2;
        this.d = new ArrayList<>();
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        } else if (!TextUtils.isEmpty(str)) {
            if (componentName == null) {
                ComponentName mediaButtonReceiverComponent = MediaButtonReceiver.getMediaButtonReceiverComponent(context);
                if (mediaButtonReceiverComponent == null) {
                    Log.w("MediaSessionCompat", "Couldn't find a unique registered media button receiver in the given context.");
                }
                componentName2 = mediaButtonReceiverComponent;
            } else {
                componentName2 = componentName;
            }
            if (componentName2 == null || pendingIntent != null) {
                pendingIntent2 = pendingIntent;
            } else {
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.setComponent(componentName2);
                pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent, PENDING_INTENT_FLAG_MUTABLE);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (Build.VERSION.SDK_INT >= 29) {
                    this.b = new g(context, str, versionedParcelable, bundle);
                } else if (Build.VERSION.SDK_INT >= 28) {
                    this.b = new f(context, str, versionedParcelable, bundle);
                } else if (Build.VERSION.SDK_INT >= 22) {
                    this.b = new e(context, str, versionedParcelable, bundle);
                } else {
                    this.b = new d(context, str, versionedParcelable, bundle);
                }
                setCallback(new Callback() { // from class: android.support.v4.media.session.MediaSessionCompat.1
                }, new Handler(Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper()));
                this.b.b(pendingIntent2);
            } else if (Build.VERSION.SDK_INT >= 19) {
                this.b = new c(context, str, componentName2, pendingIntent2, versionedParcelable, bundle);
            } else if (Build.VERSION.SDK_INT >= 18) {
                this.b = new b(context, str, componentName2, pendingIntent2, versionedParcelable, bundle);
            } else {
                this.b = new h(context, str, componentName2, pendingIntent2, versionedParcelable, bundle);
            }
            this.c = new MediaControllerCompat(context, this);
            if (a == 0) {
                a = (int) (TypedValue.applyDimension(1, 320.0f, context.getResources().getDisplayMetrics()) + 0.5f);
            }
        } else {
            throw new IllegalArgumentException("tag must not be null or empty");
        }
    }

    private MediaSessionCompat(Context context, a aVar) {
        this.d = new ArrayList<>();
        this.b = aVar;
        this.c = new MediaControllerCompat(context, this);
    }

    public void setCallback(Callback callback) {
        setCallback(callback, null);
    }

    public void setCallback(Callback callback, Handler handler) {
        if (callback == null) {
            this.b.a((Callback) null, (Handler) null);
            return;
        }
        a aVar = this.b;
        if (handler == null) {
            handler = new Handler();
        }
        aVar.a(callback, handler);
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        this.b.a(pendingIntent);
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        this.b.b(pendingIntent);
    }

    public void setFlags(int i) {
        this.b.a(i);
    }

    public void setPlaybackToLocal(int i) {
        this.b.b(i);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat != null) {
            this.b.a(volumeProviderCompat);
            return;
        }
        throw new IllegalArgumentException("volumeProvider may not be null!");
    }

    public void setActive(boolean z) {
        this.b.a(z);
        Iterator<OnActiveChangeListener> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().onActiveChanged();
        }
    }

    public boolean isActive() {
        return this.b.a();
    }

    public void sendSessionEvent(String str, Bundle bundle) {
        if (!TextUtils.isEmpty(str)) {
            this.b.a(str, bundle);
            return;
        }
        throw new IllegalArgumentException("event cannot be null or empty");
    }

    public void release() {
        this.b.b();
    }

    public Token getSessionToken() {
        return this.b.c();
    }

    public MediaControllerCompat getController() {
        return this.c;
    }

    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        this.b.a(playbackStateCompat);
    }

    public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        this.b.a(mediaMetadataCompat);
    }

    public void setQueue(List<QueueItem> list) {
        if (list != null) {
            HashSet hashSet = new HashSet();
            for (QueueItem queueItem : list) {
                if (queueItem != null) {
                    if (hashSet.contains(Long.valueOf(queueItem.getQueueId()))) {
                        Log.e("MediaSessionCompat", "Found duplicate queue id: " + queueItem.getQueueId(), new IllegalArgumentException("id of each queue item should be unique"));
                    }
                    hashSet.add(Long.valueOf(queueItem.getQueueId()));
                } else {
                    throw new IllegalArgumentException("queue shouldn't have null items");
                }
            }
        }
        this.b.a(list);
    }

    public void setQueueTitle(CharSequence charSequence) {
        this.b.a(charSequence);
    }

    public void setRatingType(int i) {
        this.b.c(i);
    }

    public void setCaptioningEnabled(boolean z) {
        this.b.b(z);
    }

    public void setRepeatMode(int i) {
        this.b.d(i);
    }

    public void setShuffleMode(int i) {
        this.b.e(i);
    }

    public void setExtras(Bundle bundle) {
        this.b.a(bundle);
    }

    public Object getMediaSession() {
        return this.b.e();
    }

    public Object getRemoteControlClient() {
        return this.b.f();
    }

    @NonNull
    public final MediaSessionManager.RemoteUserInfo getCurrentControllerInfo() {
        return this.b.h();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String getCallingPackage() {
        return this.b.g();
    }

    public void addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener != null) {
            this.d.add(onActiveChangeListener);
            return;
        }
        throw new IllegalArgumentException("Listener may not be null");
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener != null) {
            this.d.remove(onActiveChangeListener);
            return;
        }
        throw new IllegalArgumentException("Listener may not be null");
    }

    public static MediaSessionCompat fromMediaSession(Context context, Object obj) {
        a aVar;
        if (Build.VERSION.SDK_INT < 21 || context == null || obj == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            aVar = new g(obj);
        } else if (Build.VERSION.SDK_INT >= 28) {
            aVar = new f(obj);
        } else {
            aVar = new d(obj);
        }
        return new MediaSessionCompat(context, aVar);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static void ensureClassLoader(@Nullable Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MediaSessionCompat.class.getClassLoader());
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static Bundle unparcelWithClassLoader(@Nullable Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        ensureClassLoader(bundle);
        try {
            bundle.isEmpty();
            return bundle;
        } catch (BadParcelableException unused) {
            Log.e("MediaSessionCompat", "Could not unparcel the data.");
            return null;
        }
    }

    static PlaybackStateCompat a(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat) {
        if (playbackStateCompat != null) {
            long j = -1;
            if (playbackStateCompat.getPosition() != -1) {
                if (playbackStateCompat.getState() == 3 || playbackStateCompat.getState() == 4 || playbackStateCompat.getState() == 5) {
                    long lastPositionUpdateTime = playbackStateCompat.getLastPositionUpdateTime();
                    if (lastPositionUpdateTime > 0) {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        long playbackSpeed = (playbackStateCompat.getPlaybackSpeed() * ((float) (elapsedRealtime - lastPositionUpdateTime))) + playbackStateCompat.getPosition();
                        if (mediaMetadataCompat != null && mediaMetadataCompat.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                            j = mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
                        }
                        return new PlaybackStateCompat.Builder(playbackStateCompat).setState(playbackStateCompat.getState(), (j < 0 || playbackSpeed <= j) ? playbackSpeed < 0 ? 0L : playbackSpeed : j, playbackStateCompat.getPlaybackSpeed(), elapsedRealtime).build();
                    }
                }
                return playbackStateCompat;
            }
        }
        return playbackStateCompat;
    }

    /* loaded from: classes.dex */
    public static abstract class Callback {
        private boolean a;
        final Object b = new Object();
        final MediaSession.Callback c;
        @GuardedBy("mLock")
        WeakReference<a> d;
        @GuardedBy("mLock")
        a e;

        public void onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        }

        public void onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i) {
        }

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        }

        public void onCustomAction(String str, Bundle bundle) {
        }

        public void onFastForward() {
        }

        public void onPause() {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String str, Bundle bundle) {
        }

        public void onPrepareFromSearch(String str, Bundle bundle) {
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
        }

        public void onRemoveQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        }

        @Deprecated
        public void onRemoveQueueItemAt(int i) {
        }

        public void onRewind() {
        }

        public void onSeekTo(long j) {
        }

        public void onSetCaptioningEnabled(boolean z) {
        }

        public void onSetPlaybackSpeed(float f) {
        }

        public void onSetRating(RatingCompat ratingCompat) {
        }

        public void onSetRating(RatingCompat ratingCompat, Bundle bundle) {
        }

        public void onSetRepeatMode(int i) {
        }

        public void onSetShuffleMode(int i) {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onSkipToQueueItem(long j) {
        }

        public void onStop() {
        }

        public Callback() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.c = new b();
            } else {
                this.c = null;
            }
            this.d = new WeakReference<>(null);
        }

        void a(a aVar, Handler handler) {
            synchronized (this.b) {
                this.d = new WeakReference<>(aVar);
                a aVar2 = null;
                if (this.e != null) {
                    this.e.removeCallbacksAndMessages(null);
                }
                if (!(aVar == null || handler == null)) {
                    aVar2 = new a(handler.getLooper());
                }
                this.e = aVar2;
            }
        }

        public boolean onMediaButtonEvent(Intent intent) {
            a aVar;
            a aVar2;
            KeyEvent keyEvent;
            if (Build.VERSION.SDK_INT >= 27) {
                return false;
            }
            synchronized (this.b) {
                aVar = this.d.get();
                aVar2 = this.e;
            }
            if (aVar == null || aVar2 == null || (keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT")) == null || keyEvent.getAction() != 0) {
                return false;
            }
            MediaSessionManager.RemoteUserInfo h = aVar.h();
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 79 || keyCode == 85) {
                if (keyEvent.getRepeatCount() != 0) {
                    b(aVar, aVar2);
                } else if (this.a) {
                    aVar2.removeMessages(1);
                    this.a = false;
                    PlaybackStateCompat d = aVar.d();
                    if (((d == null ? 0L : d.getActions()) & 32) != 0) {
                        onSkipToNext();
                    }
                } else {
                    this.a = true;
                    aVar2.sendMessageDelayed(aVar2.obtainMessage(1, h), ViewConfiguration.getDoubleTapTimeout());
                }
                return true;
            }
            b(aVar, aVar2);
            return false;
        }

        void b(a aVar, Handler handler) {
            if (this.a) {
                boolean z = false;
                this.a = false;
                handler.removeMessages(1);
                PlaybackStateCompat d = aVar.d();
                long actions = d == null ? 0L : d.getActions();
                boolean z2 = d != null && d.getState() == 3;
                boolean z3 = (516 & actions) != 0;
                if ((actions & 514) != 0) {
                    z = true;
                }
                if (z2 && z) {
                    onPause();
                } else if (!z2 && z3) {
                    onPlay();
                }
            }
        }

        /* loaded from: classes.dex */
        public class a extends Handler {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(Looper looper) {
                super(looper);
                Callback.this = r1;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                a aVar;
                a aVar2;
                if (message.what == 1) {
                    synchronized (Callback.this.b) {
                        aVar = Callback.this.d.get();
                        aVar2 = Callback.this.e;
                    }
                    if (aVar != null && Callback.this == aVar.i() && aVar2 != null) {
                        aVar.a((MediaSessionManager.RemoteUserInfo) message.obj);
                        Callback.this.b(aVar, aVar2);
                        aVar.a((MediaSessionManager.RemoteUserInfo) null);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @RequiresApi(21)
        /* loaded from: classes.dex */
        public class b extends MediaSession.Callback {
            b() {
                Callback.this = r1;
            }

            @Override // android.media.session.MediaSession.Callback
            public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
                d a = a();
                if (a != null) {
                    MediaSessionCompat.ensureClassLoader(bundle);
                    a(a);
                    try {
                        QueueItem queueItem = null;
                        IBinder iBinder = null;
                        queueItem = null;
                        if (str.equals(MediaControllerCompat.COMMAND_GET_EXTRA_BINDER)) {
                            Bundle bundle2 = new Bundle();
                            Token c = a.c();
                            IMediaSession extraBinder = c.getExtraBinder();
                            if (extraBinder != null) {
                                iBinder = extraBinder.asBinder();
                            }
                            BundleCompat.putBinder(bundle2, MediaSessionCompat.KEY_EXTRA_BINDER, iBinder);
                            ParcelUtils.putVersionedParcelable(bundle2, MediaSessionCompat.KEY_SESSION2_TOKEN, c.getSession2Token());
                            resultReceiver.send(0, bundle2);
                        } else if (str.equals(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM)) {
                            Callback.this.onAddQueueItem((MediaDescriptionCompat) bundle.getParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION));
                        } else if (str.equals(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM_AT)) {
                            Callback.this.onAddQueueItem((MediaDescriptionCompat) bundle.getParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION), bundle.getInt(MediaControllerCompat.COMMAND_ARGUMENT_INDEX));
                        } else if (str.equals(MediaControllerCompat.COMMAND_REMOVE_QUEUE_ITEM)) {
                            Callback.this.onRemoveQueueItem((MediaDescriptionCompat) bundle.getParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION));
                        } else if (!str.equals(MediaControllerCompat.COMMAND_REMOVE_QUEUE_ITEM_AT)) {
                            Callback.this.onCommand(str, bundle, resultReceiver);
                        } else if (a.h != null) {
                            int i = bundle.getInt(MediaControllerCompat.COMMAND_ARGUMENT_INDEX, -1);
                            if (i >= 0 && i < a.h.size()) {
                                queueItem = a.h.get(i);
                            }
                            if (queueItem != null) {
                                Callback.this.onRemoveQueueItem(queueItem.getDescription());
                            }
                        }
                    } catch (BadParcelableException unused) {
                        Log.e("MediaSessionCompat", "Could not unparcel the extra data.");
                    }
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public boolean onMediaButtonEvent(Intent intent) {
                d a = a();
                if (a == null) {
                    return false;
                }
                a(a);
                boolean onMediaButtonEvent = Callback.this.onMediaButtonEvent(intent);
                b(a);
                return onMediaButtonEvent || super.onMediaButtonEvent(intent);
            }

            @Override // android.media.session.MediaSession.Callback
            public void onPlay() {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onPlay();
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onPlayFromMediaId(String str, Bundle bundle) {
                d a = a();
                if (a != null) {
                    MediaSessionCompat.ensureClassLoader(bundle);
                    a(a);
                    Callback.this.onPlayFromMediaId(str, bundle);
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onPlayFromSearch(String str, Bundle bundle) {
                d a = a();
                if (a != null) {
                    MediaSessionCompat.ensureClassLoader(bundle);
                    a(a);
                    Callback.this.onPlayFromSearch(str, bundle);
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            @RequiresApi(23)
            public void onPlayFromUri(Uri uri, Bundle bundle) {
                d a = a();
                if (a != null) {
                    MediaSessionCompat.ensureClassLoader(bundle);
                    a(a);
                    Callback.this.onPlayFromUri(uri, bundle);
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSkipToQueueItem(long j) {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onSkipToQueueItem(j);
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onPause() {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onPause();
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSkipToNext() {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onSkipToNext();
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSkipToPrevious() {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onSkipToPrevious();
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onFastForward() {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onFastForward();
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onRewind() {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onRewind();
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onStop() {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onStop();
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSeekTo(long j) {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onSeekTo(j);
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSetRating(Rating rating) {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onSetRating(RatingCompat.fromRating(rating));
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onCustomAction(String str, Bundle bundle) {
                d a = a();
                if (a != null) {
                    MediaSessionCompat.ensureClassLoader(bundle);
                    a(a);
                    try {
                        if (str.equals(MediaSessionCompat.ACTION_PLAY_FROM_URI)) {
                            Bundle bundle2 = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                            MediaSessionCompat.ensureClassLoader(bundle2);
                            Callback.this.onPlayFromUri((Uri) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), bundle2);
                        } else if (str.equals(MediaSessionCompat.ACTION_PREPARE)) {
                            Callback.this.onPrepare();
                        } else if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
                            String string = bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID);
                            Bundle bundle3 = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                            MediaSessionCompat.ensureClassLoader(bundle3);
                            Callback.this.onPrepareFromMediaId(string, bundle3);
                        } else if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH)) {
                            String string2 = bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_QUERY);
                            Bundle bundle4 = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                            MediaSessionCompat.ensureClassLoader(bundle4);
                            Callback.this.onPrepareFromSearch(string2, bundle4);
                        } else if (str.equals(MediaSessionCompat.ACTION_PREPARE_FROM_URI)) {
                            Bundle bundle5 = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                            MediaSessionCompat.ensureClassLoader(bundle5);
                            Callback.this.onPrepareFromUri((Uri) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), bundle5);
                        } else if (str.equals(MediaSessionCompat.ACTION_SET_CAPTIONING_ENABLED)) {
                            Callback.this.onSetCaptioningEnabled(bundle.getBoolean(MediaSessionCompat.ACTION_ARGUMENT_CAPTIONING_ENABLED));
                        } else if (str.equals(MediaSessionCompat.ACTION_SET_REPEAT_MODE)) {
                            Callback.this.onSetRepeatMode(bundle.getInt(MediaSessionCompat.ACTION_ARGUMENT_REPEAT_MODE));
                        } else if (str.equals(MediaSessionCompat.ACTION_SET_SHUFFLE_MODE)) {
                            Callback.this.onSetShuffleMode(bundle.getInt(MediaSessionCompat.ACTION_ARGUMENT_SHUFFLE_MODE));
                        } else if (str.equals(MediaSessionCompat.ACTION_SET_RATING)) {
                            Bundle bundle6 = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                            MediaSessionCompat.ensureClassLoader(bundle6);
                            Callback.this.onSetRating((RatingCompat) bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_RATING), bundle6);
                        } else if (str.equals(MediaSessionCompat.ACTION_SET_PLAYBACK_SPEED)) {
                            Callback.this.onSetPlaybackSpeed(bundle.getFloat(MediaSessionCompat.ACTION_ARGUMENT_PLAYBACK_SPEED, 1.0f));
                        } else {
                            Callback.this.onCustomAction(str, bundle);
                        }
                    } catch (BadParcelableException unused) {
                        Log.e("MediaSessionCompat", "Could not unparcel the data.");
                    }
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            @RequiresApi(24)
            public void onPrepare() {
                d a = a();
                if (a != null) {
                    a(a);
                    Callback.this.onPrepare();
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            @RequiresApi(24)
            public void onPrepareFromMediaId(String str, Bundle bundle) {
                d a = a();
                if (a != null) {
                    MediaSessionCompat.ensureClassLoader(bundle);
                    a(a);
                    Callback.this.onPrepareFromMediaId(str, bundle);
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            @RequiresApi(24)
            public void onPrepareFromSearch(String str, Bundle bundle) {
                d a = a();
                if (a != null) {
                    MediaSessionCompat.ensureClassLoader(bundle);
                    a(a);
                    Callback.this.onPrepareFromSearch(str, bundle);
                    b(a);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            @RequiresApi(24)
            public void onPrepareFromUri(Uri uri, Bundle bundle) {
                d a = a();
                if (a != null) {
                    MediaSessionCompat.ensureClassLoader(bundle);
                    a(a);
                    Callback.this.onPrepareFromUri(uri, bundle);
                    b(a);
                }
            }

            private void a(a aVar) {
                if (Build.VERSION.SDK_INT < 28) {
                    String g = aVar.g();
                    if (TextUtils.isEmpty(g)) {
                        g = MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER;
                    }
                    aVar.a(new MediaSessionManager.RemoteUserInfo(g, -1, -1));
                }
            }

            private void b(a aVar) {
                aVar.a((MediaSessionManager.RemoteUserInfo) null);
            }

            private d a() {
                d dVar;
                synchronized (Callback.this.b) {
                    dVar = (d) Callback.this.d.get();
                }
                if (dVar == null || Callback.this != dVar.i()) {
                    return null;
                }
                return dVar;
            }
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    /* loaded from: classes.dex */
    public static final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() { // from class: android.support.v4.media.session.MediaSessionCompat.Token.1
            /* renamed from: a */
            public Token createFromParcel(Parcel parcel) {
                Object obj;
                if (Build.VERSION.SDK_INT >= 21) {
                    obj = parcel.readParcelable(null);
                } else {
                    obj = parcel.readStrongBinder();
                }
                return new Token(obj);
            }

            /* renamed from: a */
            public Token[] newArray(int i) {
                return new Token[i];
            }
        };
        private final Object a;
        private final Object b;
        @GuardedBy("mLock")
        private IMediaSession c;
        @GuardedBy("mLock")
        private VersionedParcelable d;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        Token(Object obj) {
            this(obj, null, null);
        }

        Token(Object obj, IMediaSession iMediaSession) {
            this(obj, iMediaSession, null);
        }

        Token(Object obj, IMediaSession iMediaSession, VersionedParcelable versionedParcelable) {
            this.a = new Object();
            this.b = obj;
            this.c = iMediaSession;
            this.d = versionedParcelable;
        }

        public static Token fromToken(Object obj) {
            return fromToken(obj, null);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public static Token fromToken(Object obj, IMediaSession iMediaSession) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            if (obj instanceof MediaSession.Token) {
                return new Token(obj, iMediaSession);
            }
            throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            if (Build.VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable) this.b, i);
            } else {
                parcel.writeStrongBinder((IBinder) this.b);
            }
        }

        public int hashCode() {
            Object obj = this.b;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            Object obj2 = this.b;
            if (obj2 == null) {
                return token.b == null;
            }
            Object obj3 = token.b;
            if (obj3 == null) {
                return false;
            }
            return obj2.equals(obj3);
        }

        public Object getToken() {
            return this.b;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public IMediaSession getExtraBinder() {
            IMediaSession iMediaSession;
            synchronized (this.a) {
                iMediaSession = this.c;
            }
            return iMediaSession;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public void setExtraBinder(IMediaSession iMediaSession) {
            synchronized (this.a) {
                this.c = iMediaSession;
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public VersionedParcelable getSession2Token() {
            VersionedParcelable versionedParcelable;
            synchronized (this.a) {
                versionedParcelable = this.d;
            }
            return versionedParcelable;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void setSession2Token(VersionedParcelable versionedParcelable) {
            synchronized (this.a) {
                this.d = versionedParcelable;
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MediaSessionCompat.KEY_TOKEN, this);
            synchronized (this.a) {
                if (this.c != null) {
                    BundleCompat.putBinder(bundle, MediaSessionCompat.KEY_EXTRA_BINDER, this.c.asBinder());
                }
                if (this.d != null) {
                    ParcelUtils.putVersionedParcelable(bundle, MediaSessionCompat.KEY_SESSION2_TOKEN, this.d);
                }
            }
            return bundle;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static Token fromBundle(Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            bundle.setClassLoader(Token.class.getClassLoader());
            IMediaSession asInterface = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, MediaSessionCompat.KEY_EXTRA_BINDER));
            VersionedParcelable versionedParcelable = ParcelUtils.getVersionedParcelable(bundle, MediaSessionCompat.KEY_SESSION2_TOKEN);
            Token token = (Token) bundle.getParcelable(MediaSessionCompat.KEY_TOKEN);
            if (token == null) {
                return null;
            }
            return new Token(token.b, asInterface, versionedParcelable);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    /* loaded from: classes.dex */
    public static final class QueueItem implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>() { // from class: android.support.v4.media.session.MediaSessionCompat.QueueItem.1
            /* renamed from: a */
            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            /* renamed from: a */
            public QueueItem[] newArray(int i) {
                return new QueueItem[i];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat a;
        private final long b;
        private MediaSession.QueueItem c;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long j) {
            this(null, mediaDescriptionCompat, j);
        }

        private QueueItem(MediaSession.QueueItem queueItem, MediaDescriptionCompat mediaDescriptionCompat, long j) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null");
            } else if (j != -1) {
                this.a = mediaDescriptionCompat;
                this.b = j;
                this.c = queueItem;
            } else {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
        }

        QueueItem(Parcel parcel) {
            this.a = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.b = parcel.readLong();
        }

        public MediaDescriptionCompat getDescription() {
            return this.a;
        }

        public long getQueueId() {
            return this.b;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.a.writeToParcel(parcel, i);
            parcel.writeLong(this.b);
        }

        public Object getQueueItem() {
            if (this.c != null || Build.VERSION.SDK_INT < 21) {
                return this.c;
            }
            this.c = a.a((MediaDescription) this.a.getMediaDescription(), this.b);
            return this.c;
        }

        public static QueueItem fromQueueItem(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            MediaSession.QueueItem queueItem = (MediaSession.QueueItem) obj;
            return new QueueItem(queueItem, MediaDescriptionCompat.fromMediaDescription(a.a(queueItem)), a.b(queueItem));
        }

        public static List<QueueItem> fromQueueItemList(List<?> list) {
            if (list == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(fromQueueItem(it.next()));
            }
            return arrayList;
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.a + ", Id=" + this.b + " }";
        }

        @RequiresApi(21)
        /* loaded from: classes.dex */
        public static class a {
            @DoNotInline
            static MediaSession.QueueItem a(MediaDescription mediaDescription, long j) {
                return new MediaSession.QueueItem(mediaDescription, j);
            }

            @DoNotInline
            static MediaDescription a(MediaSession.QueueItem queueItem) {
                return queueItem.getDescription();
            }

            @DoNotInline
            static long b(MediaSession.QueueItem queueItem) {
                return queueItem.getQueueId();
            }
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    /* loaded from: classes.dex */
    public static final class ResultReceiverWrapper implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator<ResultReceiverWrapper>() { // from class: android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper.1
            /* renamed from: a */
            public ResultReceiverWrapper createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            /* renamed from: a */
            public ResultReceiverWrapper[] newArray(int i) {
                return new ResultReceiverWrapper[i];
            }
        };
        ResultReceiver a;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ResultReceiverWrapper(@NonNull ResultReceiver resultReceiver) {
            this.a = resultReceiver;
        }

        ResultReceiverWrapper(Parcel parcel) {
            this.a = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.a.writeToParcel(parcel, i);
        }
    }

    /* loaded from: classes.dex */
    public static class h implements a {
        private final PendingIntent A;
        private final b B;
        private final Token C;
        private c D;
        private MediaSessionManager.RemoteUserInfo E;
        final String a;
        final Bundle b;
        final String c;
        final AudioManager d;
        final RemoteControlClient e;
        volatile Callback j;
        MediaMetadataCompat l;
        PlaybackStateCompat m;
        PendingIntent n;
        List<QueueItem> o;
        CharSequence p;
        int q;
        boolean r;
        int s;
        int t;
        Bundle u;
        int v;
        int w;
        VolumeProviderCompat x;
        private final Context y;
        private final ComponentName z;
        final Object f = new Object();
        final RemoteCallbackList<IMediaControllerCallback> g = new RemoteCallbackList<>();
        boolean h = false;
        boolean i = false;
        int k = 3;
        private VolumeProviderCompat.Callback F = new VolumeProviderCompat.Callback() { // from class: android.support.v4.media.session.MediaSessionCompat.h.1
            @Override // androidx.media.VolumeProviderCompat.Callback
            public void onVolumeChanged(VolumeProviderCompat volumeProviderCompat) {
                if (h.this.x == volumeProviderCompat) {
                    h.this.a(new ParcelableVolumeInfo(h.this.v, h.this.w, volumeProviderCompat.getVolumeControl(), volumeProviderCompat.getMaxVolume(), volumeProviderCompat.getCurrentVolume()));
                }
            }
        };

        int a(long j) {
            int i = (1 & j) != 0 ? 32 : 0;
            if ((2 & j) != 0) {
                i |= 16;
            }
            if ((4 & j) != 0) {
                i |= 4;
            }
            if ((8 & j) != 0) {
                i |= 2;
            }
            if ((16 & j) != 0) {
                i |= 1;
            }
            if ((32 & j) != 0) {
                i |= 128;
            }
            if ((64 & j) != 0) {
                i |= 64;
            }
            return (j & 512) != 0 ? i | 8 : i;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void b(PendingIntent pendingIntent) {
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public Object e() {
            return null;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public Object f() {
            return null;
        }

        int g(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
                case 6:
                case 8:
                    return 8;
                case 7:
                    return 9;
                case 9:
                    return 7;
                case 10:
                case 11:
                    return 6;
                default:
                    return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public String g() {
            return null;
        }

        public h(Context context, String str, ComponentName componentName, PendingIntent pendingIntent, VersionedParcelable versionedParcelable, Bundle bundle) {
            if (componentName != null) {
                this.y = context;
                this.a = context.getPackageName();
                this.b = bundle;
                this.d = (AudioManager) context.getSystemService("audio");
                this.c = str;
                this.z = componentName;
                this.A = pendingIntent;
                this.B = new b();
                this.C = new Token(this.B, null, versionedParcelable);
                this.q = 0;
                this.v = 1;
                this.w = 3;
                this.e = new RemoteControlClient(pendingIntent);
                return;
            }
            throw new IllegalArgumentException("MediaButtonReceiver component may not be null");
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0032 A[Catch: all -> 0x0039, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0008, B:10:0x0012, B:12:0x001d, B:14:0x0023, B:16:0x0027, B:17:0x002c, B:19:0x0032, B:20:0x0037), top: B:25:0x0003 }] */
        @Override // android.support.v4.media.session.MediaSessionCompat.a
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a(android.support.v4.media.session.MediaSessionCompat.Callback r5, android.os.Handler r6) {
            /*
                r4 = this;
                java.lang.Object r0 = r4.f
                monitor-enter(r0)
                android.support.v4.media.session.MediaSessionCompat$h$c r1 = r4.D     // Catch: all -> 0x0039
                r2 = 0
                if (r1 == 0) goto L_0x000d
                android.support.v4.media.session.MediaSessionCompat$h$c r1 = r4.D     // Catch: all -> 0x0039
                r1.removeCallbacksAndMessages(r2)     // Catch: all -> 0x0039
            L_0x000d:
                if (r5 == 0) goto L_0x001c
                if (r6 != 0) goto L_0x0012
                goto L_0x001c
            L_0x0012:
                android.support.v4.media.session.MediaSessionCompat$h$c r1 = new android.support.v4.media.session.MediaSessionCompat$h$c     // Catch: all -> 0x0039
                android.os.Looper r3 = r6.getLooper()     // Catch: all -> 0x0039
                r1.<init>(r3)     // Catch: all -> 0x0039
                goto L_0x001d
            L_0x001c:
                r1 = r2
            L_0x001d:
                r4.D = r1     // Catch: all -> 0x0039
                android.support.v4.media.session.MediaSessionCompat$Callback r1 = r4.j     // Catch: all -> 0x0039
                if (r1 == r5) goto L_0x002c
                android.support.v4.media.session.MediaSessionCompat$Callback r1 = r4.j     // Catch: all -> 0x0039
                if (r1 == 0) goto L_0x002c
                android.support.v4.media.session.MediaSessionCompat$Callback r1 = r4.j     // Catch: all -> 0x0039
                r1.a(r2, r2)     // Catch: all -> 0x0039
            L_0x002c:
                r4.j = r5     // Catch: all -> 0x0039
                android.support.v4.media.session.MediaSessionCompat$Callback r5 = r4.j     // Catch: all -> 0x0039
                if (r5 == 0) goto L_0x0037
                android.support.v4.media.session.MediaSessionCompat$Callback r5 = r4.j     // Catch: all -> 0x0039
                r5.a(r4, r6)     // Catch: all -> 0x0039
            L_0x0037:
                monitor-exit(r0)     // Catch: all -> 0x0039
                return
            L_0x0039:
                r5 = move-exception
                monitor-exit(r0)     // Catch: all -> 0x0039
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.session.MediaSessionCompat.h.a(android.support.v4.media.session.MediaSessionCompat$Callback, android.os.Handler):void");
        }

        void a(int i, int i2, int i3, Object obj, Bundle bundle) {
            synchronized (this.f) {
                if (this.D != null) {
                    Message obtainMessage = this.D.obtainMessage(i, i2, i3, obj);
                    Bundle bundle2 = new Bundle();
                    int callingUid = Binder.getCallingUid();
                    bundle2.putInt(MediaBrowserProtocol.DATA_CALLING_UID, callingUid);
                    bundle2.putString("data_calling_pkg", f(callingUid));
                    int callingPid = Binder.getCallingPid();
                    if (callingPid > 0) {
                        bundle2.putInt(MediaBrowserProtocol.DATA_CALLING_PID, callingPid);
                    } else {
                        bundle2.putInt(MediaBrowserProtocol.DATA_CALLING_PID, -1);
                    }
                    if (bundle != null) {
                        bundle2.putBundle("data_extras", bundle);
                    }
                    obtainMessage.setData(bundle2);
                    obtainMessage.sendToTarget();
                }
            }
        }

        String f(int i) {
            String nameForUid = this.y.getPackageManager().getNameForUid(i);
            return TextUtils.isEmpty(nameForUid) ? MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER : nameForUid;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(int i) {
            synchronized (this.f) {
                this.k = i | 1 | 2;
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void b(int i) {
            VolumeProviderCompat volumeProviderCompat = this.x;
            if (volumeProviderCompat != null) {
                volumeProviderCompat.setCallback(null);
            }
            this.w = i;
            this.v = 1;
            int i2 = this.v;
            int i3 = this.w;
            a(new ParcelableVolumeInfo(i2, i3, 2, this.d.getStreamMaxVolume(i3), this.d.getStreamVolume(this.w)));
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(VolumeProviderCompat volumeProviderCompat) {
            if (volumeProviderCompat != null) {
                VolumeProviderCompat volumeProviderCompat2 = this.x;
                if (volumeProviderCompat2 != null) {
                    volumeProviderCompat2.setCallback(null);
                }
                this.v = 2;
                this.x = volumeProviderCompat;
                a(new ParcelableVolumeInfo(this.v, this.w, this.x.getVolumeControl(), this.x.getMaxVolume(), this.x.getCurrentVolume()));
                volumeProviderCompat.setCallback(this.F);
                return;
            }
            throw new IllegalArgumentException("volumeProvider may not be null");
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(boolean z) {
            if (z != this.i) {
                this.i = z;
                j();
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public boolean a() {
            return this.i;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(String str, Bundle bundle) {
            b(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void b() {
            this.i = false;
            this.h = true;
            j();
            k();
            a((Callback) null, (Handler) null);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public Token c() {
            return this.C;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(PlaybackStateCompat playbackStateCompat) {
            synchronized (this.f) {
                this.m = playbackStateCompat;
            }
            c(playbackStateCompat);
            if (this.i) {
                if (playbackStateCompat == null) {
                    this.e.setPlaybackState(0);
                    this.e.setTransportControlFlags(0);
                    return;
                }
                b(playbackStateCompat);
                this.e.setTransportControlFlags(a(playbackStateCompat.getActions()));
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public PlaybackStateCompat d() {
            PlaybackStateCompat playbackStateCompat;
            synchronized (this.f) {
                playbackStateCompat = this.m;
            }
            return playbackStateCompat;
        }

        void b(PlaybackStateCompat playbackStateCompat) {
            this.e.setPlaybackState(g(playbackStateCompat.getState()));
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(MediaMetadataCompat mediaMetadataCompat) {
            if (mediaMetadataCompat != null) {
                mediaMetadataCompat = new MediaMetadataCompat.Builder(mediaMetadataCompat, MediaSessionCompat.a).build();
            }
            synchronized (this.f) {
                this.l = mediaMetadataCompat;
            }
            b(mediaMetadataCompat);
            if (this.i) {
                b(mediaMetadataCompat == null ? null : mediaMetadataCompat.getBundle()).apply();
            }
        }

        RemoteControlClient.MetadataEditor b(Bundle bundle) {
            RemoteControlClient.MetadataEditor editMetadata = this.e.editMetadata(true);
            if (bundle == null) {
                return editMetadata;
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ART)) {
                Bitmap bitmap = (Bitmap) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_ART);
                if (bitmap != null) {
                    bitmap = bitmap.copy(bitmap.getConfig(), false);
                }
                editMetadata.putBitmap(100, bitmap);
            } else if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ART)) {
                Bitmap bitmap2 = (Bitmap) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_ALBUM_ART);
                if (bitmap2 != null) {
                    bitmap2 = bitmap2.copy(bitmap2.getConfig(), false);
                }
                editMetadata.putBitmap(100, bitmap2);
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM)) {
                editMetadata.putString(1, bundle.getString(MediaMetadataCompat.METADATA_KEY_ALBUM));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST)) {
                editMetadata.putString(13, bundle.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_ARTIST)) {
                editMetadata.putString(2, bundle.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_AUTHOR)) {
                editMetadata.putString(3, bundle.getString(MediaMetadataCompat.METADATA_KEY_AUTHOR));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_COMPILATION)) {
                editMetadata.putString(15, bundle.getString(MediaMetadataCompat.METADATA_KEY_COMPILATION));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_COMPOSER)) {
                editMetadata.putString(4, bundle.getString(MediaMetadataCompat.METADATA_KEY_COMPOSER));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DATE)) {
                editMetadata.putString(5, bundle.getString(MediaMetadataCompat.METADATA_KEY_DATE));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER)) {
                editMetadata.putLong(14, bundle.getLong(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                editMetadata.putLong(9, bundle.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_GENRE)) {
                editMetadata.putString(6, bundle.getString(MediaMetadataCompat.METADATA_KEY_GENRE));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_TITLE)) {
                editMetadata.putString(7, bundle.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER)) {
                editMetadata.putLong(0, bundle.getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_WRITER)) {
                editMetadata.putString(11, bundle.getString(MediaMetadataCompat.METADATA_KEY_WRITER));
            }
            return editMetadata;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(PendingIntent pendingIntent) {
            synchronized (this.f) {
                this.n = pendingIntent;
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(List<QueueItem> list) {
            this.o = list;
            b(list);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(CharSequence charSequence) {
            this.p = charSequence;
            b(charSequence);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void c(int i) {
            this.q = i;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void b(boolean z) {
            if (this.r != z) {
                this.r = z;
                c(z);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void d(int i) {
            if (this.s != i) {
                this.s = i;
                h(i);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void e(int i) {
            if (this.t != i) {
                this.t = i;
                i(i);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(Bundle bundle) {
            this.u = bundle;
            c(bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public MediaSessionManager.RemoteUserInfo h() {
            MediaSessionManager.RemoteUserInfo remoteUserInfo;
            synchronized (this.f) {
                remoteUserInfo = this.E;
            }
            return remoteUserInfo;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            synchronized (this.f) {
                this.E = remoteUserInfo;
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public Callback i() {
            Callback callback;
            synchronized (this.f) {
                callback = this.j;
            }
            return callback;
        }

        void j() {
            if (this.i) {
                a(this.A, this.z);
                this.d.registerRemoteControlClient(this.e);
                a(this.l);
                a(this.m);
                return;
            }
            b(this.A, this.z);
            this.e.setPlaybackState(0);
            this.d.unregisterRemoteControlClient(this.e);
        }

        void a(PendingIntent pendingIntent, ComponentName componentName) {
            this.d.registerMediaButtonEventReceiver(componentName);
        }

        void b(PendingIntent pendingIntent, ComponentName componentName) {
            this.d.unregisterMediaButtonEventReceiver(componentName);
        }

        void a(int i, int i2) {
            if (this.v == 2) {
                VolumeProviderCompat volumeProviderCompat = this.x;
                if (volumeProviderCompat != null) {
                    volumeProviderCompat.onAdjustVolume(i);
                    return;
                }
                return;
            }
            this.d.adjustStreamVolume(this.w, i, i2);
        }

        void b(int i, int i2) {
            if (this.v == 2) {
                VolumeProviderCompat volumeProviderCompat = this.x;
                if (volumeProviderCompat != null) {
                    volumeProviderCompat.onSetVolumeTo(i);
                    return;
                }
                return;
            }
            this.d.setStreamVolume(this.w, i, i2);
        }

        void a(ParcelableVolumeInfo parcelableVolumeInfo) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onVolumeInfoChanged(parcelableVolumeInfo);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void k() {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onSessionDestroyed();
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
            this.g.kill();
        }

        private void b(String str, Bundle bundle) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onEvent(str, bundle);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void c(PlaybackStateCompat playbackStateCompat) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onPlaybackStateChanged(playbackStateCompat);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void b(MediaMetadataCompat mediaMetadataCompat) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onMetadataChanged(mediaMetadataCompat);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void b(List<QueueItem> list) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onQueueChanged(list);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void b(CharSequence charSequence) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onQueueTitleChanged(charSequence);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void c(boolean z) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onCaptioningEnabledChanged(z);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void h(int i) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onRepeatModeChanged(i);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void i(int i) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onShuffleModeChanged(i);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        private void c(Bundle bundle) {
            for (int beginBroadcast = this.g.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.g.getBroadcastItem(beginBroadcast).onExtrasChanged(bundle);
                } catch (RemoteException unused) {
                }
            }
            this.g.finishBroadcast();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public class b extends IMediaSession.Stub {
            @Override // android.support.v4.media.session.IMediaSession
            public boolean isShuffleModeEnabledRemoved() {
                return false;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean isTransportControlEnabled() {
                return true;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setShuffleModeEnabledRemoved(boolean z) throws RemoteException {
            }

            b() {
                h.this = r1;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                a(1, new a(str, bundle, resultReceiverWrapper == null ? null : resultReceiverWrapper.a));
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean sendMediaButton(KeyEvent keyEvent) {
                a(21, keyEvent);
                return true;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (h.this.h) {
                    try {
                        iMediaControllerCallback.onSessionDestroyed();
                    } catch (Exception unused) {
                    }
                } else {
                    h.this.g.register(iMediaControllerCallback, new MediaSessionManager.RemoteUserInfo(h.this.f(getCallingUid()), getCallingPid(), getCallingUid()));
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                h.this.g.unregister(iMediaControllerCallback);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public String getPackageName() {
                return h.this.a;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public Bundle getSessionInfo() {
                if (h.this.b == null) {
                    return null;
                }
                return new Bundle(h.this.b);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public String getTag() {
                return h.this.c;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public PendingIntent getLaunchPendingIntent() {
                PendingIntent pendingIntent;
                synchronized (h.this.f) {
                    pendingIntent = h.this.n;
                }
                return pendingIntent;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public long getFlags() {
                long j;
                synchronized (h.this.f) {
                    j = h.this.k;
                }
                return j;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public ParcelableVolumeInfo getVolumeAttributes() {
                int i;
                int i2;
                int i3;
                int i4;
                int i5;
                synchronized (h.this.f) {
                    i = h.this.v;
                    i2 = h.this.w;
                    VolumeProviderCompat volumeProviderCompat = h.this.x;
                    if (i == 2) {
                        int volumeControl = volumeProviderCompat.getVolumeControl();
                        int maxVolume = volumeProviderCompat.getMaxVolume();
                        i3 = volumeProviderCompat.getCurrentVolume();
                        i4 = maxVolume;
                        i5 = volumeControl;
                    } else {
                        i4 = h.this.d.getStreamMaxVolume(i2);
                        i3 = h.this.d.getStreamVolume(i2);
                        i5 = 2;
                    }
                }
                return new ParcelableVolumeInfo(i, i2, i5, i4, i3);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void adjustVolume(int i, int i2, String str) {
                h.this.a(i, i2);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setVolumeTo(int i, int i2, String str) {
                h.this.b(i, i2);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepare() throws RemoteException {
                a(3);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromMediaId(String str, Bundle bundle) throws RemoteException {
                a(4, str, bundle);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromSearch(String str, Bundle bundle) throws RemoteException {
                a(5, str, bundle);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                a(6, uri, bundle);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void play() throws RemoteException {
                a(7);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                a(8, str, bundle);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                a(9, str, bundle);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                a(10, uri, bundle);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void skipToQueueItem(long j) {
                a(11, Long.valueOf(j));
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void pause() throws RemoteException {
                a(12);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void stop() throws RemoteException {
                a(13);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void next() throws RemoteException {
                a(14);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void previous() throws RemoteException {
                a(15);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void fastForward() throws RemoteException {
                a(16);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rewind() throws RemoteException {
                a(17);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void seekTo(long j) throws RemoteException {
                a(18, Long.valueOf(j));
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rate(RatingCompat ratingCompat) throws RemoteException {
                a(19, ratingCompat);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) throws RemoteException {
                a(31, ratingCompat, bundle);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setPlaybackSpeed(float f) throws RemoteException {
                a(32, Float.valueOf(f));
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setCaptioningEnabled(boolean z) throws RemoteException {
                a(29, Boolean.valueOf(z));
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setRepeatMode(int i) throws RemoteException {
                a(23, i);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setShuffleMode(int i) throws RemoteException {
                a(30, i);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                a(20, str, bundle);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public MediaMetadataCompat getMetadata() {
                return h.this.l;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public PlaybackStateCompat getPlaybackState() {
                PlaybackStateCompat playbackStateCompat;
                MediaMetadataCompat mediaMetadataCompat;
                synchronized (h.this.f) {
                    playbackStateCompat = h.this.m;
                    mediaMetadataCompat = h.this.l;
                }
                return MediaSessionCompat.a(playbackStateCompat, mediaMetadataCompat);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public List<QueueItem> getQueue() {
                List<QueueItem> list;
                synchronized (h.this.f) {
                    list = h.this.o;
                }
                return list;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                a(25, mediaDescriptionCompat);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i) {
                a(26, mediaDescriptionCompat, i);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                a(27, mediaDescriptionCompat);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void removeQueueItemAt(int i) {
                a(28, i);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public CharSequence getQueueTitle() {
                return h.this.p;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public Bundle getExtras() {
                Bundle bundle;
                synchronized (h.this.f) {
                    bundle = h.this.u;
                }
                return bundle;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getRatingType() {
                return h.this.q;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean isCaptioningEnabled() {
                return h.this.r;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getRepeatMode() {
                return h.this.s;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getShuffleMode() {
                return h.this.t;
            }

            void a(int i) {
                h.this.a(i, 0, 0, null, null);
            }

            void a(int i, int i2) {
                h.this.a(i, i2, 0, null, null);
            }

            void a(int i, Object obj) {
                h.this.a(i, 0, 0, obj, null);
            }

            void a(int i, Object obj, int i2) {
                h.this.a(i, i2, 0, obj, null);
            }

            void a(int i, Object obj, Bundle bundle) {
                h.this.a(i, 0, 0, obj, bundle);
            }
        }

        /* loaded from: classes.dex */
        private static final class a {
            public final String a;
            public final Bundle b;
            public final ResultReceiver c;

            public a(String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.a = str;
                this.b = bundle;
                this.c = resultReceiver;
            }
        }

        /* loaded from: classes.dex */
        public class c extends Handler {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public c(Looper looper) {
                super(looper);
                h.this = r1;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Callback callback = h.this.j;
                if (callback != null) {
                    Bundle data = message.getData();
                    MediaSessionCompat.ensureClassLoader(data);
                    h.this.a(new MediaSessionManager.RemoteUserInfo(data.getString("data_calling_pkg"), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID)));
                    Bundle bundle = data.getBundle("data_extras");
                    MediaSessionCompat.ensureClassLoader(bundle);
                    try {
                        switch (message.what) {
                            case 1:
                                a aVar = (a) message.obj;
                                callback.onCommand(aVar.a, aVar.b, aVar.c);
                                break;
                            case 2:
                                h.this.a(message.arg1, 0);
                                break;
                            case 3:
                                callback.onPrepare();
                                break;
                            case 4:
                                callback.onPrepareFromMediaId((String) message.obj, bundle);
                                break;
                            case 5:
                                callback.onPrepareFromSearch((String) message.obj, bundle);
                                break;
                            case 6:
                                callback.onPrepareFromUri((Uri) message.obj, bundle);
                                break;
                            case 7:
                                callback.onPlay();
                                break;
                            case 8:
                                callback.onPlayFromMediaId((String) message.obj, bundle);
                                break;
                            case 9:
                                callback.onPlayFromSearch((String) message.obj, bundle);
                                break;
                            case 10:
                                callback.onPlayFromUri((Uri) message.obj, bundle);
                                break;
                            case 11:
                                callback.onSkipToQueueItem(((Long) message.obj).longValue());
                                break;
                            case 12:
                                callback.onPause();
                                break;
                            case 13:
                                callback.onStop();
                                break;
                            case 14:
                                callback.onSkipToNext();
                                break;
                            case 15:
                                callback.onSkipToPrevious();
                                break;
                            case 16:
                                callback.onFastForward();
                                break;
                            case 17:
                                callback.onRewind();
                                break;
                            case 18:
                                callback.onSeekTo(((Long) message.obj).longValue());
                                break;
                            case 19:
                                callback.onSetRating((RatingCompat) message.obj);
                                break;
                            case 20:
                                callback.onCustomAction((String) message.obj, bundle);
                                break;
                            case 21:
                                KeyEvent keyEvent = (KeyEvent) message.obj;
                                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                                if (!callback.onMediaButtonEvent(intent)) {
                                    a(keyEvent, callback);
                                    break;
                                }
                                break;
                            case 22:
                                h.this.b(message.arg1, 0);
                                break;
                            case 23:
                                callback.onSetRepeatMode(message.arg1);
                                break;
                            case 25:
                                callback.onAddQueueItem((MediaDescriptionCompat) message.obj);
                                break;
                            case 26:
                                callback.onAddQueueItem((MediaDescriptionCompat) message.obj, message.arg1);
                                break;
                            case 27:
                                callback.onRemoveQueueItem((MediaDescriptionCompat) message.obj);
                                break;
                            case 28:
                                if (h.this.o != null) {
                                    QueueItem queueItem = (message.arg1 < 0 || message.arg1 >= h.this.o.size()) ? null : h.this.o.get(message.arg1);
                                    if (queueItem != null) {
                                        callback.onRemoveQueueItem(queueItem.getDescription());
                                        break;
                                    }
                                }
                                break;
                            case 29:
                                callback.onSetCaptioningEnabled(((Boolean) message.obj).booleanValue());
                                break;
                            case 30:
                                callback.onSetShuffleMode(message.arg1);
                                break;
                            case 31:
                                callback.onSetRating((RatingCompat) message.obj, bundle);
                                break;
                            case 32:
                                callback.onSetPlaybackSpeed(((Float) message.obj).floatValue());
                                break;
                        }
                    } finally {
                        h.this.a((MediaSessionManager.RemoteUserInfo) null);
                    }
                }
            }

            private void a(KeyEvent keyEvent, Callback callback) {
                if (keyEvent != null && keyEvent.getAction() == 0) {
                    long actions = h.this.m == null ? 0L : h.this.m.getActions();
                    int keyCode = keyEvent.getKeyCode();
                    if (keyCode != 79) {
                        switch (keyCode) {
                            case 85:
                                break;
                            case 86:
                                if ((actions & 1) != 0) {
                                    callback.onStop();
                                    return;
                                }
                                return;
                            case 87:
                                if ((actions & 32) != 0) {
                                    callback.onSkipToNext();
                                    return;
                                }
                                return;
                            case 88:
                                if ((actions & 16) != 0) {
                                    callback.onSkipToPrevious();
                                    return;
                                }
                                return;
                            case 89:
                                if ((actions & 8) != 0) {
                                    callback.onRewind();
                                    return;
                                }
                                return;
                            case 90:
                                if ((actions & 64) != 0) {
                                    callback.onFastForward();
                                    return;
                                }
                                return;
                            default:
                                switch (keyCode) {
                                    case 126:
                                        if ((actions & 4) != 0) {
                                            callback.onPlay();
                                            return;
                                        }
                                        return;
                                    case 127:
                                        if ((actions & 2) != 0) {
                                            callback.onPause();
                                            return;
                                        }
                                        return;
                                    default:
                                        return;
                                }
                        }
                    }
                    Log.w("MediaSessionCompat", "KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(18)
    /* loaded from: classes.dex */
    public static class b extends h {
        private static boolean y = true;

        b(Context context, String str, ComponentName componentName, PendingIntent pendingIntent, VersionedParcelable versionedParcelable, Bundle bundle) {
            super(context, str, componentName, pendingIntent, versionedParcelable, bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.h, android.support.v4.media.session.MediaSessionCompat.a
        public void a(Callback callback, Handler handler) {
            super.a(callback, handler);
            if (callback == null) {
                this.e.setPlaybackPositionUpdateListener(null);
                return;
            }
            this.e.setPlaybackPositionUpdateListener(new RemoteControlClient.OnPlaybackPositionUpdateListener() { // from class: android.support.v4.media.session.MediaSessionCompat.b.1
                @Override // android.media.RemoteControlClient.OnPlaybackPositionUpdateListener
                public void onPlaybackPositionUpdate(long j) {
                    b.this.a(18, -1, -1, Long.valueOf(j), null);
                }
            });
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.h
        void b(PlaybackStateCompat playbackStateCompat) {
            long position = playbackStateCompat.getPosition();
            float playbackSpeed = playbackStateCompat.getPlaybackSpeed();
            long lastPositionUpdateTime = playbackStateCompat.getLastPositionUpdateTime();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (playbackStateCompat.getState() == 3) {
                long j = 0;
                if (position > 0) {
                    if (lastPositionUpdateTime > 0) {
                        j = elapsedRealtime - lastPositionUpdateTime;
                        if (playbackSpeed > 0.0f && playbackSpeed != 1.0f) {
                            j = ((float) j) * playbackSpeed;
                        }
                    }
                    position += j;
                }
            }
            this.e.setPlaybackState(g(playbackStateCompat.getState()), position, playbackSpeed);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.h
        int a(long j) {
            int a = super.a(j);
            return (j & 256) != 0 ? a | 256 : a;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.h
        void a(PendingIntent pendingIntent, ComponentName componentName) {
            if (y) {
                try {
                    this.d.registerMediaButtonEventReceiver(pendingIntent);
                } catch (NullPointerException unused) {
                    Log.w("MediaSessionCompat", "Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
                    y = false;
                }
            }
            if (!y) {
                super.a(pendingIntent, componentName);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.h
        void b(PendingIntent pendingIntent, ComponentName componentName) {
            if (y) {
                this.d.unregisterMediaButtonEventReceiver(pendingIntent);
            } else {
                super.b(pendingIntent, componentName);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(19)
    /* loaded from: classes.dex */
    public static class c extends b {
        c(Context context, String str, ComponentName componentName, PendingIntent pendingIntent, VersionedParcelable versionedParcelable, Bundle bundle) {
            super(context, str, componentName, pendingIntent, versionedParcelable, bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.b, android.support.v4.media.session.MediaSessionCompat.h, android.support.v4.media.session.MediaSessionCompat.a
        public void a(Callback callback, Handler handler) {
            super.a(callback, handler);
            if (callback == null) {
                this.e.setMetadataUpdateListener(null);
                return;
            }
            this.e.setMetadataUpdateListener(new RemoteControlClient.OnMetadataUpdateListener() { // from class: android.support.v4.media.session.MediaSessionCompat.c.1
                @Override // android.media.RemoteControlClient.OnMetadataUpdateListener
                public void onMetadataUpdate(int i, Object obj) {
                    if (i == 268435457 && (obj instanceof Rating)) {
                        c.this.a(19, -1, -1, RatingCompat.fromRating(obj), null);
                    }
                }
            });
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.b, android.support.v4.media.session.MediaSessionCompat.h
        int a(long j) {
            int a = super.a(j);
            return (j & 128) != 0 ? a | 512 : a;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.h
        RemoteControlClient.MetadataEditor b(Bundle bundle) {
            RemoteControlClient.MetadataEditor b = super.b(bundle);
            if (((this.m == null ? 0L : this.m.getActions()) & 128) != 0) {
                b.addEditableKey(268435457);
            }
            if (bundle == null) {
                return b;
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_YEAR)) {
                b.putLong(8, bundle.getLong(MediaMetadataCompat.METADATA_KEY_YEAR));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_RATING)) {
                b.putObject(101, (Object) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_RATING));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_USER_RATING)) {
                b.putObject(268435457, (Object) bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_USER_RATING));
            }
            return b;
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class d implements a {
        final MediaSession a;
        final Token b;
        Bundle d;
        PlaybackStateCompat g;
        List<QueueItem> h;
        MediaMetadataCompat i;
        int j;
        boolean k;
        int l;
        int m;
        @GuardedBy("mLock")
        Callback n;
        @GuardedBy("mLock")
        MediaSessionManager.RemoteUserInfo o;
        final Object c = new Object();
        boolean e = false;
        final RemoteCallbackList<IMediaControllerCallback> f = new RemoteCallbackList<>();

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public Object f() {
            return null;
        }

        d(Context context, String str, VersionedParcelable versionedParcelable, Bundle bundle) {
            this.a = a(context, str, bundle);
            this.b = new Token(this.a.getSessionToken(), new a(), versionedParcelable);
            this.d = bundle;
            a(3);
        }

        d(Object obj) {
            if (obj instanceof MediaSession) {
                this.a = (MediaSession) obj;
                this.b = new Token(this.a.getSessionToken(), new a());
                this.d = null;
                a(3);
                return;
            }
            throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
        }

        public MediaSession a(Context context, String str, Bundle bundle) {
            return new MediaSession(context, str);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(Callback callback, Handler handler) {
            synchronized (this.c) {
                this.n = callback;
                this.a.setCallback(callback == null ? null : callback.c, handler);
                if (callback != null) {
                    callback.a(this, handler);
                }
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        @SuppressLint({"WrongConstant"})
        public void a(int i) {
            this.a.setFlags(i | 1 | 2);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void b(int i) {
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            builder.setLegacyStreamType(i);
            this.a.setPlaybackToLocal(builder.build());
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(VolumeProviderCompat volumeProviderCompat) {
            this.a.setPlaybackToRemote((VolumeProvider) volumeProviderCompat.getVolumeProvider());
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(boolean z) {
            this.a.setActive(z);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public boolean a() {
            return this.a.isActive();
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(String str, Bundle bundle) {
            if (Build.VERSION.SDK_INT < 23) {
                for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        this.f.getBroadcastItem(beginBroadcast).onEvent(str, bundle);
                    } catch (RemoteException unused) {
                    }
                }
                this.f.finishBroadcast();
            }
            this.a.sendSessionEvent(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void b() {
            this.e = true;
            this.f.kill();
            if (Build.VERSION.SDK_INT == 27) {
                try {
                    Field declaredField = this.a.getClass().getDeclaredField("mCallback");
                    declaredField.setAccessible(true);
                    Handler handler = (Handler) declaredField.get(this.a);
                    if (handler != null) {
                        handler.removeCallbacksAndMessages(null);
                    }
                } catch (Exception e) {
                    Log.w("MediaSessionCompat", "Exception happened while accessing MediaSession.mCallback.", e);
                }
            }
            this.a.setCallback(null);
            this.a.release();
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public Token c() {
            return this.b;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(PlaybackStateCompat playbackStateCompat) {
            this.g = playbackStateCompat;
            for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.f.getBroadcastItem(beginBroadcast).onPlaybackStateChanged(playbackStateCompat);
                } catch (RemoteException unused) {
                }
            }
            this.f.finishBroadcast();
            this.a.setPlaybackState(playbackStateCompat == null ? null : (PlaybackState) playbackStateCompat.getPlaybackState());
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public PlaybackStateCompat d() {
            return this.g;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(MediaMetadataCompat mediaMetadataCompat) {
            this.i = mediaMetadataCompat;
            this.a.setMetadata(mediaMetadataCompat == null ? null : (MediaMetadata) mediaMetadataCompat.getMediaMetadata());
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(PendingIntent pendingIntent) {
            this.a.setSessionActivity(pendingIntent);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void b(PendingIntent pendingIntent) {
            this.a.setMediaButtonReceiver(pendingIntent);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(List<QueueItem> list) {
            this.h = list;
            if (list == null) {
                this.a.setQueue(null);
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (QueueItem queueItem : list) {
                arrayList.add((MediaSession.QueueItem) queueItem.getQueueItem());
            }
            this.a.setQueue(arrayList);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(CharSequence charSequence) {
            this.a.setQueueTitle(charSequence);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void c(int i) {
            this.j = i;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void b(boolean z) {
            if (this.k != z) {
                this.k = z;
                for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        this.f.getBroadcastItem(beginBroadcast).onCaptioningEnabledChanged(z);
                    } catch (RemoteException unused) {
                    }
                }
                this.f.finishBroadcast();
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void d(int i) {
            if (this.l != i) {
                this.l = i;
                for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        this.f.getBroadcastItem(beginBroadcast).onRepeatModeChanged(i);
                    } catch (RemoteException unused) {
                    }
                }
                this.f.finishBroadcast();
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void e(int i) {
            if (this.m != i) {
                this.m = i;
                for (int beginBroadcast = this.f.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        this.f.getBroadcastItem(beginBroadcast).onShuffleModeChanged(i);
                    } catch (RemoteException unused) {
                    }
                }
                this.f.finishBroadcast();
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(Bundle bundle) {
            this.a.setExtras(bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public Object e() {
            return this.a;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public void a(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            synchronized (this.c) {
                this.o = remoteUserInfo;
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public String g() {
            if (Build.VERSION.SDK_INT < 24) {
                return null;
            }
            try {
                return (String) this.a.getClass().getMethod("getCallingPackage", new Class[0]).invoke(this.a, new Object[0]);
            } catch (Exception e) {
                Log.e("MediaSessionCompat", "Cannot execute MediaSession.getCallingPackage()", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public MediaSessionManager.RemoteUserInfo h() {
            MediaSessionManager.RemoteUserInfo remoteUserInfo;
            synchronized (this.c) {
                remoteUserInfo = this.o;
            }
            return remoteUserInfo;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.a
        public Callback i() {
            Callback callback;
            synchronized (this.c) {
                callback = this.n;
            }
            return callback;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public class a extends IMediaSession.Stub {
            @Override // android.support.v4.media.session.IMediaSession
            public List<QueueItem> getQueue() {
                return null;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean isShuffleModeEnabledRemoved() {
                return false;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setShuffleModeEnabledRemoved(boolean z) throws RemoteException {
            }

            a() {
                d.this = r1;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean sendMediaButton(KeyEvent keyEvent) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (!d.this.e) {
                    d.this.f.register(iMediaControllerCallback, new MediaSessionManager.RemoteUserInfo(MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER, getCallingPid(), getCallingUid()));
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                d.this.f.unregister(iMediaControllerCallback);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public String getPackageName() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public Bundle getSessionInfo() {
                if (d.this.d == null) {
                    return null;
                }
                return new Bundle(d.this.d);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public String getTag() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public PendingIntent getLaunchPendingIntent() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public long getFlags() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public ParcelableVolumeInfo getVolumeAttributes() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void adjustVolume(int i, int i2, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setVolumeTo(int i, int i2, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepare() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromMediaId(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromSearch(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void play() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void skipToQueueItem(long j) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void pause() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void stop() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void next() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void previous() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void fastForward() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rewind() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void seekTo(long j) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rate(RatingCompat ratingCompat) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setPlaybackSpeed(float f) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setCaptioningEnabled(boolean z) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setRepeatMode(int i) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setShuffleMode(int i) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public MediaMetadataCompat getMetadata() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public PlaybackStateCompat getPlaybackState() {
                return MediaSessionCompat.a(d.this.g, d.this.i);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void removeQueueItemAt(int i) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public CharSequence getQueueTitle() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public Bundle getExtras() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getRatingType() {
                return d.this.j;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean isCaptioningEnabled() {
                return d.this.k;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getRepeatMode() {
                return d.this.l;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getShuffleMode() {
                return d.this.m;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean isTransportControlEnabled() {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(22)
    /* loaded from: classes.dex */
    public static class e extends d {
        e(Context context, String str, VersionedParcelable versionedParcelable, Bundle bundle) {
            super(context, str, versionedParcelable, bundle);
        }

        e(Object obj) {
            super(obj);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.d, android.support.v4.media.session.MediaSessionCompat.a
        public void c(int i) {
            this.a.setRatingType(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(28)
    /* loaded from: classes.dex */
    public static class f extends e {
        @Override // android.support.v4.media.session.MediaSessionCompat.d, android.support.v4.media.session.MediaSessionCompat.a
        public void a(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        }

        f(Context context, String str, VersionedParcelable versionedParcelable, Bundle bundle) {
            super(context, str, versionedParcelable, bundle);
        }

        f(Object obj) {
            super(obj);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.d, android.support.v4.media.session.MediaSessionCompat.a
        @NonNull
        public final MediaSessionManager.RemoteUserInfo h() {
            return new MediaSessionManager.RemoteUserInfo(this.a.getCurrentControllerInfo());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static class g extends f {
        g(Context context, String str, VersionedParcelable versionedParcelable, Bundle bundle) {
            super(context, str, versionedParcelable, bundle);
        }

        g(Object obj) {
            super(obj);
            this.d = ((MediaSession) obj).getController().getSessionInfo();
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.d
        public MediaSession a(Context context, String str, Bundle bundle) {
            return new MediaSession(context, str, bundle);
        }
    }
}
