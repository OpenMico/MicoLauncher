package androidx.media;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.core.app.BundleCompat;
import androidx.core.util.Pair;
import androidx.media.MediaSessionManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class MediaBrowserServiceCompat extends Service {
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_MEDIA_ITEM = "media_item";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_SEARCH_RESULTS = "search_results";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_ERROR = -1;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_OK = 0;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_PROGRESS_UPDATE = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    static final boolean a = Log.isLoggable("MBServiceCompat", 3);
    a e;
    MediaSessionCompat.Token g;
    private b h;
    final a b = new a(MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER, -1, -1, null, null);
    final ArrayList<a> c = new ArrayList<>();
    final ArrayMap<IBinder, a> d = new ArrayMap<>();
    final l f = new l();

    /* loaded from: classes.dex */
    interface b {
        IBinder a(Intent intent);

        void a();

        void a(MediaSessionCompat.Token token);

        void a(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle);

        void a(String str, Bundle bundle);

        Bundle b();

        MediaSessionManager.RemoteUserInfo c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface j {
        IBinder a();

        void a(String str, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException;

        void a(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2) throws RemoteException;

        void b() throws RemoteException;
    }

    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i2, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result);

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void onSubscribe(String str, Bundle bundle) {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void onUnsubscribe(String str) {
    }

    /* loaded from: classes.dex */
    class g implements b {
        private Messenger b;

        g() {
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public void a() {
            this.b = new Messenger(MediaBrowserServiceCompat.this.f);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public IBinder a(Intent intent) {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
                return this.b.getBinder();
            }
            return null;
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public void a(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.f.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.g.1
                @Override // java.lang.Runnable
                public void run() {
                    Iterator<a> it = MediaBrowserServiceCompat.this.d.values().iterator();
                    while (it.hasNext()) {
                        a next = it.next();
                        try {
                            next.f.a(next.h.getRootId(), token, next.h.getExtras());
                        } catch (RemoteException unused) {
                            Log.w("MBServiceCompat", "Connection for " + next.a + " is no longer valid.");
                            it.remove();
                        }
                    }
                }
            });
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public void a(@NonNull final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.f.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.g.2
                @Override // java.lang.Runnable
                public void run() {
                    for (IBinder iBinder : MediaBrowserServiceCompat.this.d.keySet()) {
                        ArrayMap<IBinder, a> arrayMap = MediaBrowserServiceCompat.this.d;
                        g.this.a(arrayMap.get(iBinder), str, bundle);
                    }
                }
            });
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public void a(@NonNull final MediaSessionManager.RemoteUserInfo remoteUserInfo, @NonNull final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.f.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.g.3
                @Override // java.lang.Runnable
                public void run() {
                    for (int i = 0; i < MediaBrowserServiceCompat.this.d.size(); i++) {
                        a valueAt = MediaBrowserServiceCompat.this.d.valueAt(i);
                        if (valueAt.d.equals(remoteUserInfo)) {
                            g.this.a(valueAt, str, bundle);
                            return;
                        }
                    }
                }
            });
        }

        void a(a aVar, String str, Bundle bundle) {
            List<Pair<IBinder, Bundle>> list = aVar.g.get(str);
            if (list != null) {
                for (Pair<IBinder, Bundle> pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, pair.second)) {
                        MediaBrowserServiceCompat.this.a(str, aVar, pair.second, bundle);
                    }
                }
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public Bundle b() {
            if (MediaBrowserServiceCompat.this.e == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            } else if (MediaBrowserServiceCompat.this.e.e == null) {
                return null;
            } else {
                return new Bundle(MediaBrowserServiceCompat.this.e.e);
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public MediaSessionManager.RemoteUserInfo c() {
            if (MediaBrowserServiceCompat.this.e != null) {
                return MediaBrowserServiceCompat.this.e.d;
            }
            throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    class c implements b {
        final List<Bundle> a = new ArrayList();
        MediaBrowserService b;
        Messenger c;

        c() {
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public void a() {
            this.b = new a(MediaBrowserServiceCompat.this);
            this.b.onCreate();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public IBinder a(Intent intent) {
            return this.b.onBind(intent);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public void a(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.c.1
                @Override // java.lang.Runnable
                public void run() {
                    c.this.b(token);
                }
            });
        }

        void b(MediaSessionCompat.Token token) {
            if (!this.a.isEmpty()) {
                IMediaSession extraBinder = token.getExtraBinder();
                if (extraBinder != null) {
                    for (Bundle bundle : this.a) {
                        BundleCompat.putBinder(bundle, MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder.asBinder());
                    }
                }
                this.a.clear();
            }
            this.b.setSessionToken((MediaSession.Token) token.getToken());
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public void a(String str, Bundle bundle) {
            b(str, bundle);
            c(str, bundle);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public void a(MediaSessionManager.RemoteUserInfo remoteUserInfo, String str, Bundle bundle) {
            b(remoteUserInfo, str, bundle);
        }

        public BrowserRoot a(String str, int i, Bundle bundle) {
            int i2;
            Bundle bundle2;
            if (bundle == null || bundle.getInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 0) == 0) {
                i2 = -1;
                bundle2 = null;
            } else {
                bundle.remove(MediaBrowserProtocol.EXTRA_CLIENT_VERSION);
                this.c = new Messenger(MediaBrowserServiceCompat.this.f);
                bundle2 = new Bundle();
                bundle2.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2);
                BundleCompat.putBinder(bundle2, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.c.getBinder());
                if (MediaBrowserServiceCompat.this.g != null) {
                    IMediaSession extraBinder = MediaBrowserServiceCompat.this.g.getExtraBinder();
                    BundleCompat.putBinder(bundle2, MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder == null ? null : extraBinder.asBinder());
                } else {
                    this.a.add(bundle2);
                }
                int i3 = bundle.getInt(MediaBrowserProtocol.EXTRA_CALLING_PID, -1);
                bundle.remove(MediaBrowserProtocol.EXTRA_CALLING_PID);
                i2 = i3;
            }
            a aVar = new a(str, i2, i, bundle, null);
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.e = aVar;
            BrowserRoot onGetRoot = mediaBrowserServiceCompat.onGetRoot(str, i, bundle);
            MediaBrowserServiceCompat mediaBrowserServiceCompat2 = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat2.e = null;
            if (onGetRoot == null) {
                return null;
            }
            if (this.c != null) {
                mediaBrowserServiceCompat2.c.add(aVar);
            }
            if (bundle2 == null) {
                bundle2 = onGetRoot.getExtras();
            } else if (onGetRoot.getExtras() != null) {
                bundle2.putAll(onGetRoot.getExtras());
            }
            return new BrowserRoot(onGetRoot.getRootId(), bundle2);
        }

        public void a(String str, final h<List<Parcel>> hVar) {
            Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str) { // from class: androidx.media.MediaBrowserServiceCompat.c.2
                /* JADX INFO: Access modifiers changed from: package-private */
                public void a(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                    ArrayList arrayList;
                    if (list != null) {
                        arrayList = new ArrayList();
                        for (MediaBrowserCompat.MediaItem mediaItem : list) {
                            Parcel obtain = Parcel.obtain();
                            mediaItem.writeToParcel(obtain, 0);
                            arrayList.add(obtain);
                        }
                    } else {
                        arrayList = null;
                    }
                    hVar.a((h) arrayList);
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    hVar.a();
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.e = mediaBrowserServiceCompat.b;
            MediaBrowserServiceCompat.this.onLoadChildren(str, result);
            MediaBrowserServiceCompat.this.e = null;
        }

        void b(String str, Bundle bundle) {
            this.b.notifyChildrenChanged(str);
        }

        void c(final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.f.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.c.3
                @Override // java.lang.Runnable
                public void run() {
                    for (IBinder iBinder : MediaBrowserServiceCompat.this.d.keySet()) {
                        ArrayMap<IBinder, a> arrayMap = MediaBrowserServiceCompat.this.d;
                        c.this.a(arrayMap.get(iBinder), str, bundle);
                    }
                }
            });
        }

        void b(final MediaSessionManager.RemoteUserInfo remoteUserInfo, final String str, final Bundle bundle) {
            MediaBrowserServiceCompat.this.f.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.c.4
                @Override // java.lang.Runnable
                public void run() {
                    for (int i = 0; i < MediaBrowserServiceCompat.this.d.size(); i++) {
                        a valueAt = MediaBrowserServiceCompat.this.d.valueAt(i);
                        if (valueAt.d.equals(remoteUserInfo)) {
                            c.this.a(valueAt, str, bundle);
                        }
                    }
                }
            });
        }

        void a(a aVar, String str, Bundle bundle) {
            List<Pair<IBinder, Bundle>> list = aVar.g.get(str);
            if (list != null) {
                for (Pair<IBinder, Bundle> pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, pair.second)) {
                        MediaBrowserServiceCompat.this.a(str, aVar, pair.second, bundle);
                    }
                }
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public Bundle b() {
            if (this.c == null) {
                return null;
            }
            if (MediaBrowserServiceCompat.this.e == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            } else if (MediaBrowserServiceCompat.this.e.e == null) {
                return null;
            } else {
                return new Bundle(MediaBrowserServiceCompat.this.e.e);
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.b
        public MediaSessionManager.RemoteUserInfo c() {
            if (MediaBrowserServiceCompat.this.e != null) {
                return MediaBrowserServiceCompat.this.e.d;
            }
            throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }

        @RequiresApi(21)
        /* loaded from: classes.dex */
        class a extends MediaBrowserService {
            a(Context context) {
                attachBaseContext(context);
            }

            @Override // android.service.media.MediaBrowserService
            @SuppressLint({"SyntheticAccessor"})
            public MediaBrowserService.BrowserRoot onGetRoot(String str, int i, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                BrowserRoot a = c.this.a(str, i, bundle == null ? null : new Bundle(bundle));
                if (a == null) {
                    return null;
                }
                return new MediaBrowserService.BrowserRoot(a.a, a.b);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
                c.this.a(str, new h<>(result));
            }
        }
    }

    @RequiresApi(23)
    /* loaded from: classes.dex */
    class d extends c {
        d() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c, androidx.media.MediaBrowserServiceCompat.b
        public void a() {
            this.b = new a(MediaBrowserServiceCompat.this);
            this.b.onCreate();
        }

        public void b(String str, final h<Parcel> hVar) {
            Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(str) { // from class: androidx.media.MediaBrowserServiceCompat.d.1
                /* JADX INFO: Access modifiers changed from: package-private */
                public void a(@Nullable MediaBrowserCompat.MediaItem mediaItem) {
                    if (mediaItem == null) {
                        hVar.a((h) null);
                        return;
                    }
                    Parcel obtain = Parcel.obtain();
                    mediaItem.writeToParcel(obtain, 0);
                    hVar.a((h) obtain);
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    hVar.a();
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.e = mediaBrowserServiceCompat.b;
            MediaBrowserServiceCompat.this.onLoadItem(str, result);
            MediaBrowserServiceCompat.this.e = null;
        }

        /* loaded from: classes.dex */
        class a extends c.a {
            a(Context context) {
                super(context);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadItem(String str, MediaBrowserService.Result<MediaBrowser.MediaItem> result) {
                d.this.b(str, new h<>(result));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(26)
    /* loaded from: classes.dex */
    public class e extends d {
        e() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.d, androidx.media.MediaBrowserServiceCompat.c, androidx.media.MediaBrowserServiceCompat.b
        public void a() {
            this.b = new a(MediaBrowserServiceCompat.this);
            this.b.onCreate();
        }

        public void a(String str, final h<List<Parcel>> hVar, final Bundle bundle) {
            Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str) { // from class: androidx.media.MediaBrowserServiceCompat.e.1
                /* JADX INFO: Access modifiers changed from: package-private */
                public void a(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                    if (list == null) {
                        hVar.a((h) null);
                        return;
                    }
                    if ((b() & 1) != 0) {
                        list = MediaBrowserServiceCompat.this.a(list, bundle);
                    }
                    ArrayList arrayList = new ArrayList();
                    for (MediaBrowserCompat.MediaItem mediaItem : list) {
                        Parcel obtain = Parcel.obtain();
                        mediaItem.writeToParcel(obtain, 0);
                        arrayList.add(obtain);
                    }
                    hVar.a((h) arrayList);
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    hVar.a();
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.e = mediaBrowserServiceCompat.b;
            MediaBrowserServiceCompat.this.onLoadChildren(str, result, bundle);
            MediaBrowserServiceCompat.this.e = null;
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c, androidx.media.MediaBrowserServiceCompat.b
        public Bundle b() {
            if (MediaBrowserServiceCompat.this.e == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            } else if (MediaBrowserServiceCompat.this.e == MediaBrowserServiceCompat.this.b) {
                return this.b.getBrowserRootHints();
            } else {
                if (MediaBrowserServiceCompat.this.e.e == null) {
                    return null;
                }
                return new Bundle(MediaBrowserServiceCompat.this.e.e);
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c
        void b(String str, Bundle bundle) {
            if (bundle != null) {
                this.b.notifyChildrenChanged(str, bundle);
            } else {
                super.b(str, bundle);
            }
        }

        /* loaded from: classes.dex */
        class a extends d.a {
            a(Context context) {
                super(context);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                MediaBrowserServiceCompat.this.e = MediaBrowserServiceCompat.this.b;
                e.this.a(str, new h<>(result), bundle);
                MediaBrowserServiceCompat.this.e = null;
            }
        }
    }

    @RequiresApi(28)
    /* loaded from: classes.dex */
    class f extends e {
        f() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.c, androidx.media.MediaBrowserServiceCompat.b
        public MediaSessionManager.RemoteUserInfo c() {
            if (MediaBrowserServiceCompat.this.e == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            } else if (MediaBrowserServiceCompat.this.e == MediaBrowserServiceCompat.this.b) {
                return new MediaSessionManager.RemoteUserInfo(this.b.getCurrentBrowserInfo());
            } else {
                return MediaBrowserServiceCompat.this.e.d;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class l extends Handler {
        private final i b;

        l() {
            this.b = new i();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case 1:
                    Bundle bundle = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    MediaSessionCompat.ensureClassLoader(bundle);
                    this.b.a(data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), bundle, new k(message.replyTo));
                    return;
                case 2:
                    this.b.a(new k(message.replyTo));
                    return;
                case 3:
                    Bundle bundle2 = data.getBundle(MediaBrowserProtocol.DATA_OPTIONS);
                    MediaSessionCompat.ensureClassLoader(bundle2);
                    this.b.a(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), bundle2, new k(message.replyTo));
                    return;
                case 4:
                    this.b.a(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), new k(message.replyTo));
                    return;
                case 5:
                    this.b.a(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new k(message.replyTo));
                    return;
                case 6:
                    Bundle bundle3 = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    MediaSessionCompat.ensureClassLoader(bundle3);
                    this.b.a(new k(message.replyTo), data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), bundle3);
                    return;
                case 7:
                    this.b.b(new k(message.replyTo));
                    return;
                case 8:
                    Bundle bundle4 = data.getBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(bundle4);
                    this.b.a(data.getString(MediaBrowserProtocol.DATA_SEARCH_QUERY), bundle4, (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new k(message.replyTo));
                    return;
                case 9:
                    Bundle bundle5 = data.getBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(bundle5);
                    this.b.b(data.getString(MediaBrowserProtocol.DATA_CUSTOM_ACTION), bundle5, (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new k(message.replyTo));
                    return;
                default:
                    Log.w("MBServiceCompat", "Unhandled message: " + message + "\n  Service version: 2\n  Client version: " + message.arg1);
                    return;
            }
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(Message message, long j) {
            Bundle data = message.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid());
            int callingPid = Binder.getCallingPid();
            if (callingPid > 0) {
                data.putInt(MediaBrowserProtocol.DATA_CALLING_PID, callingPid);
            } else if (!data.containsKey(MediaBrowserProtocol.DATA_CALLING_PID)) {
                data.putInt(MediaBrowserProtocol.DATA_CALLING_PID, -1);
            }
            return super.sendMessageAtTime(message, j);
        }

        public void a(Runnable runnable) {
            if (Thread.currentThread() == getLooper().getThread()) {
                runnable.run();
            } else {
                post(runnable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class a implements IBinder.DeathRecipient {
        public final String a;
        public final int b;
        public final int c;
        public final MediaSessionManager.RemoteUserInfo d;
        public final Bundle e;
        public final j f;
        public final HashMap<String, List<Pair<IBinder, Bundle>>> g = new HashMap<>();
        public BrowserRoot h;

        a(String str, int i, int i2, Bundle bundle, j jVar) {
            this.a = str;
            this.b = i;
            this.c = i2;
            this.d = new MediaSessionManager.RemoteUserInfo(str, i, i2);
            this.e = bundle;
            this.f = jVar;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MediaBrowserServiceCompat.this.f.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.a.1
                @Override // java.lang.Runnable
                public void run() {
                    MediaBrowserServiceCompat.this.d.remove(a.this.f.a());
                }
            });
        }
    }

    /* loaded from: classes.dex */
    public static class Result<T> {
        private final Object a;
        private boolean b;
        private boolean c;
        private boolean d;
        private int e;

        void a(@Nullable T t) {
        }

        Result(Object obj) {
            this.a = obj;
        }

        public void sendResult(@Nullable T t) {
            if (this.c || this.d) {
                throw new IllegalStateException("sendResult() called when either sendResult() or sendError() had already been called for: " + this.a);
            }
            this.c = true;
            a((Result<T>) t);
        }

        public void sendProgressUpdate(@Nullable Bundle bundle) {
            if (this.c || this.d) {
                throw new IllegalStateException("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: " + this.a);
            }
            a(bundle);
            b(bundle);
        }

        public void sendError(@Nullable Bundle bundle) {
            if (this.c || this.d) {
                throw new IllegalStateException("sendError() called when either sendResult() or sendError() had already been called for: " + this.a);
            }
            this.d = true;
            c(bundle);
        }

        public void detach() {
            if (this.b) {
                throw new IllegalStateException("detach() called when detach() had already been called for: " + this.a);
            } else if (this.c) {
                throw new IllegalStateException("detach() called when sendResult() had already been called for: " + this.a);
            } else if (!this.d) {
                this.b = true;
            } else {
                throw new IllegalStateException("detach() called when sendError() had already been called for: " + this.a);
            }
        }

        boolean a() {
            return this.b || this.c || this.d;
        }

        void a(int i) {
            this.e = i;
        }

        int b() {
            return this.e;
        }

        void b(@Nullable Bundle bundle) {
            throw new UnsupportedOperationException("It is not supported to send an interim update for " + this.a);
        }

        void c(@Nullable Bundle bundle) {
            throw new UnsupportedOperationException("It is not supported to send an error for " + this.a);
        }

        private void a(@Nullable Bundle bundle) {
            if (bundle != null && bundle.containsKey(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)) {
                float f = bundle.getFloat(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS);
                if (f < -1.0E-5f || f > 1.00001f) {
                    throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0]");
                }
            }
        }
    }

    /* loaded from: classes.dex */
    private class i {
        i() {
        }

        public void a(final String str, final int i, final int i2, final Bundle bundle, final j jVar) {
            if (MediaBrowserServiceCompat.this.a(str, i2)) {
                MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.1
                    @Override // java.lang.Runnable
                    public void run() {
                        IBinder a = jVar.a();
                        MediaBrowserServiceCompat.this.d.remove(a);
                        a aVar = new a(str, i, i2, bundle, jVar);
                        MediaBrowserServiceCompat.this.e = aVar;
                        aVar.h = MediaBrowserServiceCompat.this.onGetRoot(str, i2, bundle);
                        MediaBrowserServiceCompat.this.e = null;
                        if (aVar.h == null) {
                            Log.i("MBServiceCompat", "No root for client " + str + " from service " + getClass().getName());
                            try {
                                jVar.b();
                            } catch (RemoteException unused) {
                                Log.w("MBServiceCompat", "Calling onConnectFailed() failed. Ignoring. pkg=" + str);
                            }
                        } else {
                            try {
                                MediaBrowserServiceCompat.this.d.put(a, aVar);
                                a.linkToDeath(aVar, 0);
                                if (MediaBrowserServiceCompat.this.g != null) {
                                    jVar.a(aVar.h.getRootId(), MediaBrowserServiceCompat.this.g, aVar.h.getExtras());
                                }
                            } catch (RemoteException unused2) {
                                Log.w("MBServiceCompat", "Calling onConnect() failed. Dropping client. pkg=" + str);
                                MediaBrowserServiceCompat.this.d.remove(a);
                            }
                        }
                    }
                });
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + i2 + " package=" + str);
        }

        public void a(final j jVar) {
            MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.2
                @Override // java.lang.Runnable
                public void run() {
                    a remove = MediaBrowserServiceCompat.this.d.remove(jVar.a());
                    if (remove != null) {
                        remove.f.a().unlinkToDeath(remove, 0);
                    }
                }
            });
        }

        public void a(final String str, final IBinder iBinder, final Bundle bundle, final j jVar) {
            MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.3
                @Override // java.lang.Runnable
                public void run() {
                    a aVar = MediaBrowserServiceCompat.this.d.get(jVar.a());
                    if (aVar == null) {
                        Log.w("MBServiceCompat", "addSubscription for callback that isn't registered id=" + str);
                        return;
                    }
                    MediaBrowserServiceCompat.this.a(str, aVar, iBinder, bundle);
                }
            });
        }

        public void a(final String str, final IBinder iBinder, final j jVar) {
            MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.4
                @Override // java.lang.Runnable
                public void run() {
                    a aVar = MediaBrowserServiceCompat.this.d.get(jVar.a());
                    if (aVar == null) {
                        Log.w("MBServiceCompat", "removeSubscription for callback that isn't registered id=" + str);
                    } else if (!MediaBrowserServiceCompat.this.a(str, aVar, iBinder)) {
                        Log.w("MBServiceCompat", "removeSubscription called for " + str + " which is not subscribed");
                    }
                }
            });
        }

        public void a(final String str, final ResultReceiver resultReceiver, final j jVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.5
                    @Override // java.lang.Runnable
                    public void run() {
                        a aVar = MediaBrowserServiceCompat.this.d.get(jVar.a());
                        if (aVar == null) {
                            Log.w("MBServiceCompat", "getMediaItem for callback that isn't registered id=" + str);
                            return;
                        }
                        MediaBrowserServiceCompat.this.a(str, aVar, resultReceiver);
                    }
                });
            }
        }

        public void a(final j jVar, final String str, final int i, final int i2, final Bundle bundle) {
            MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.6
                @Override // java.lang.Runnable
                public void run() {
                    a aVar;
                    IBinder a = jVar.a();
                    MediaBrowserServiceCompat.this.d.remove(a);
                    Iterator<a> it = MediaBrowserServiceCompat.this.c.iterator();
                    while (true) {
                        aVar = null;
                        if (!it.hasNext()) {
                            break;
                        }
                        a next = it.next();
                        if (next.c == i2) {
                            if (TextUtils.isEmpty(str) || i <= 0) {
                                aVar = new a(next.a, next.b, next.c, bundle, jVar);
                            }
                            it.remove();
                        }
                    }
                    a aVar2 = aVar == null ? new a(str, i, i2, bundle, jVar) : aVar;
                    MediaBrowserServiceCompat.this.d.put(a, aVar2);
                    try {
                        a.linkToDeath(aVar2, 0);
                    } catch (RemoteException unused) {
                        Log.w("MBServiceCompat", "IBinder is already dead.");
                    }
                }
            });
        }

        public void b(final j jVar) {
            MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.7
                @Override // java.lang.Runnable
                public void run() {
                    IBinder a = jVar.a();
                    a remove = MediaBrowserServiceCompat.this.d.remove(a);
                    if (remove != null) {
                        a.unlinkToDeath(remove, 0);
                    }
                }
            });
        }

        public void a(final String str, final Bundle bundle, final ResultReceiver resultReceiver, final j jVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.8
                    @Override // java.lang.Runnable
                    public void run() {
                        a aVar = MediaBrowserServiceCompat.this.d.get(jVar.a());
                        if (aVar == null) {
                            Log.w("MBServiceCompat", "search for callback that isn't registered query=" + str);
                            return;
                        }
                        MediaBrowserServiceCompat.this.a(str, bundle, aVar, resultReceiver);
                    }
                });
            }
        }

        public void b(final String str, final Bundle bundle, final ResultReceiver resultReceiver, final j jVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.f.a(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.i.9
                    @Override // java.lang.Runnable
                    public void run() {
                        a aVar = MediaBrowserServiceCompat.this.d.get(jVar.a());
                        if (aVar == null) {
                            Log.w("MBServiceCompat", "sendCustomAction for callback that isn't registered action=" + str + ", extras=" + bundle);
                            return;
                        }
                        MediaBrowserServiceCompat.this.b(str, bundle, aVar, resultReceiver);
                    }
                });
            }
        }
    }

    /* loaded from: classes.dex */
    private static class k implements j {
        final Messenger a;

        k(Messenger messenger) {
            this.a = messenger;
        }

        @Override // androidx.media.MediaBrowserServiceCompat.j
        public IBinder a() {
            return this.a.getBinder();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.j
        public void a(String str, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2);
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str);
            bundle2.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, token);
            bundle2.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, bundle);
            a(1, bundle2);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.j
        public void b() throws RemoteException {
            a(2, null);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.j
        public void a(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2) throws RemoteException {
            Bundle bundle3 = new Bundle();
            bundle3.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str);
            bundle3.putBundle(MediaBrowserProtocol.DATA_OPTIONS, bundle);
            bundle3.putBundle(MediaBrowserProtocol.DATA_NOTIFY_CHILDREN_CHANGED_OPTIONS, bundle2);
            if (list != null) {
                bundle3.putParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST, list instanceof ArrayList ? (ArrayList) list : new ArrayList<>(list));
            }
            a(3, bundle3);
        }

        private void a(int i, Bundle bundle) throws RemoteException {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = 2;
            obtain.setData(bundle);
            this.a.send(obtain);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class h<T> {
        MediaBrowserService.Result a;

        h(MediaBrowserService.Result result) {
            this.a = result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a(T t) {
            if (t instanceof List) {
                this.a.sendResult(a((List) t));
            } else if (t instanceof Parcel) {
                Parcel parcel = (Parcel) t;
                parcel.setDataPosition(0);
                this.a.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            } else {
                this.a.sendResult(null);
            }
        }

        public void a() {
            this.a.detach();
        }

        List<MediaBrowser.MediaItem> a(List<Parcel> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Parcel parcel : list) {
                parcel.setDataPosition(0);
                arrayList.add((MediaBrowser.MediaItem) MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
            return arrayList;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void attachToBaseContext(Context context) {
        attachBaseContext(context);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 28) {
            this.h = new f();
        } else if (Build.VERSION.SDK_INT >= 26) {
            this.h = new e();
        } else if (Build.VERSION.SDK_INT >= 23) {
            this.h = new d();
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.h = new c();
        } else {
            this.h = new g();
        }
        this.h.a();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.h.a(intent);
    }

    public void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result, @NonNull Bundle bundle) {
        result.a(1);
        onLoadChildren(str, result);
    }

    public void onLoadItem(String str, @NonNull Result<MediaBrowserCompat.MediaItem> result) {
        result.a(2);
        result.sendResult(null);
    }

    public void onSearch(@NonNull String str, Bundle bundle, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.a(4);
        result.sendResult(null);
    }

    public void onCustomAction(@NonNull String str, Bundle bundle, @NonNull Result<Bundle> result) {
        result.sendError(null);
    }

    public void setSessionToken(MediaSessionCompat.Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null");
        } else if (this.g == null) {
            this.g = token;
            this.h.a(token);
        } else {
            throw new IllegalStateException("The session token has already been set");
        }
    }

    @Nullable
    public MediaSessionCompat.Token getSessionToken() {
        return this.g;
    }

    public final Bundle getBrowserRootHints() {
        return this.h.b();
    }

    @NonNull
    public final MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
        return this.h.c();
    }

    public void notifyChildrenChanged(@NonNull String str) {
        if (str != null) {
            this.h.a(str, null);
            return;
        }
        throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
    }

    public void notifyChildrenChanged(@NonNull String str, @NonNull Bundle bundle) {
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        } else if (bundle != null) {
            this.h.a(str, bundle);
        } else {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void notifyChildrenChanged(@NonNull MediaSessionManager.RemoteUserInfo remoteUserInfo, @NonNull String str, @NonNull Bundle bundle) {
        if (remoteUserInfo == null) {
            throw new IllegalArgumentException("remoteUserInfo cannot be null in notifyChildrenChanged");
        } else if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        } else if (bundle != null) {
            this.h.a(remoteUserInfo, str, bundle);
        } else {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
    }

    boolean a(String str, int i2) {
        if (str == null) {
            return false;
        }
        for (String str2 : getPackageManager().getPackagesForUid(i2)) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    void a(String str, a aVar, IBinder iBinder, Bundle bundle) {
        List<Pair<IBinder, Bundle>> list = aVar.g.get(str);
        if (list == null) {
            list = new ArrayList<>();
        }
        for (Pair<IBinder, Bundle> pair : list) {
            if (iBinder == pair.first && MediaBrowserCompatUtils.areSameOptions(bundle, pair.second)) {
                return;
            }
        }
        list.add(new Pair<>(iBinder, bundle));
        aVar.g.put(str, list);
        a(str, aVar, bundle, (Bundle) null);
        this.e = aVar;
        onSubscribe(str, bundle);
        this.e = null;
    }

    boolean a(String str, a aVar, IBinder iBinder) {
        boolean z = true;
        boolean z2 = false;
        try {
            if (iBinder == null) {
                if (aVar.g.remove(str) == null) {
                    z = false;
                }
                return z;
            }
            List<Pair<IBinder, Bundle>> list = aVar.g.get(str);
            if (list != null) {
                Iterator<Pair<IBinder, Bundle>> it = list.iterator();
                while (it.hasNext()) {
                    if (iBinder == it.next().first) {
                        it.remove();
                        z2 = true;
                    }
                }
                if (list.size() == 0) {
                    aVar.g.remove(str);
                }
            }
            return z2;
        } finally {
            this.e = aVar;
            onUnsubscribe(str);
            this.e = null;
        }
    }

    void a(final String str, final a aVar, final Bundle bundle, final Bundle bundle2) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str) { // from class: androidx.media.MediaBrowserServiceCompat.1
            /* JADX INFO: Access modifiers changed from: package-private */
            public void a(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                if (MediaBrowserServiceCompat.this.d.get(aVar.f.a()) == aVar) {
                    if ((b() & 1) != 0) {
                        list = MediaBrowserServiceCompat.this.a(list, bundle);
                    }
                    try {
                        aVar.f.a(str, list, bundle, bundle2);
                    } catch (RemoteException unused) {
                        Log.w("MBServiceCompat", "Calling onLoadChildren() failed for id=" + str + " package=" + aVar.a);
                    }
                } else if (MediaBrowserServiceCompat.a) {
                    Log.d("MBServiceCompat", "Not sending onLoadChildren result for connection that has been disconnected. pkg=" + aVar.a + " id=" + str);
                }
            }
        };
        this.e = aVar;
        if (bundle == null) {
            onLoadChildren(str, result);
        } else {
            onLoadChildren(str, result, bundle);
        }
        this.e = null;
        if (!result.a()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + aVar.a + " id=" + str);
        }
    }

    List<MediaBrowserCompat.MediaItem> a(List<MediaBrowserCompat.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i3 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (i2 == -1 && i3 == -1) {
            return list;
        }
        int i4 = i3 * i2;
        int i5 = i4 + i3;
        if (i2 < 0 || i3 < 1 || i4 >= list.size()) {
            return Collections.emptyList();
        }
        if (i5 > list.size()) {
            i5 = list.size();
        }
        return list.subList(i4, i5);
    }

    void a(String str, a aVar, final ResultReceiver resultReceiver) {
        Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(str) { // from class: androidx.media.MediaBrowserServiceCompat.2
            /* JADX INFO: Access modifiers changed from: package-private */
            public void a(@Nullable MediaBrowserCompat.MediaItem mediaItem) {
                if ((b() & 2) != 0) {
                    resultReceiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, mediaItem);
                resultReceiver.send(0, bundle);
            }
        };
        this.e = aVar;
        onLoadItem(str, result);
        this.e = null;
        if (!result.a()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + str);
        }
    }

    void a(String str, Bundle bundle, a aVar, final ResultReceiver resultReceiver) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str) { // from class: androidx.media.MediaBrowserServiceCompat.3
            /* JADX INFO: Access modifiers changed from: package-private */
            public void a(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                if ((b() & 4) != 0 || list == null) {
                    resultReceiver.send(-1, null);
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Parcelable[]) list.toArray(new MediaBrowserCompat.MediaItem[0]));
                resultReceiver.send(0, bundle2);
            }
        };
        this.e = aVar;
        onSearch(str, bundle, result);
        this.e = null;
        if (!result.a()) {
            throw new IllegalStateException("onSearch must call detach() or sendResult() before returning for query=" + str);
        }
    }

    void b(String str, Bundle bundle, a aVar, final ResultReceiver resultReceiver) {
        Result<Bundle> result = new Result<Bundle>(str) { // from class: androidx.media.MediaBrowserServiceCompat.4
            /* JADX INFO: Access modifiers changed from: package-private */
            public void a(@Nullable Bundle bundle2) {
                resultReceiver.send(0, bundle2);
            }

            @Override // androidx.media.MediaBrowserServiceCompat.Result
            void b(@Nullable Bundle bundle2) {
                resultReceiver.send(1, bundle2);
            }

            @Override // androidx.media.MediaBrowserServiceCompat.Result
            void c(@Nullable Bundle bundle2) {
                resultReceiver.send(-1, bundle2);
            }
        };
        this.e = aVar;
        onCustomAction(str, bundle, result);
        this.e = null;
        if (!result.a()) {
            throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + str + " extras=" + bundle);
        }
    }

    /* loaded from: classes.dex */
    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        @Deprecated
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        private final String a;
        private final Bundle b;

        public BrowserRoot(@NonNull String str, @Nullable Bundle bundle) {
            if (str != null) {
                this.a = str;
                this.b = bundle;
                return;
            }
            throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead");
        }

        public String getRootId() {
            return this.a;
        }

        public Bundle getExtras() {
            return this.b;
        }
    }
}
