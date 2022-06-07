package android.support.v4.media;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.core.app.BundleCompat;
import androidx.media.MediaBrowserCompatUtils;
import androidx.media.MediaBrowserProtocol;
import androidx.media.MediaBrowserServiceCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class MediaBrowserCompat {
    public static final String CUSTOM_ACTION_DOWNLOAD = "android.support.v4.media.action.DOWNLOAD";
    public static final String CUSTOM_ACTION_REMOVE_DOWNLOADED_FILE = "android.support.v4.media.action.REMOVE_DOWNLOADED_FILE";
    public static final String EXTRA_DOWNLOAD_PROGRESS = "android.media.browse.extra.DOWNLOAD_PROGRESS";
    public static final String EXTRA_MEDIA_ID = "android.media.browse.extra.MEDIA_ID";
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    static final boolean a = Log.isLoggable("MediaBrowserCompat", 3);
    private final c b;

    /* loaded from: classes.dex */
    public static abstract class CustomActionCallback {
        public void onError(String str, Bundle bundle, Bundle bundle2) {
        }

        public void onProgressUpdate(String str, Bundle bundle, Bundle bundle2) {
        }

        public void onResult(String str, Bundle bundle, Bundle bundle2) {
        }
    }

    /* loaded from: classes.dex */
    public static abstract class SearchCallback {
        public void onError(@NonNull String str, Bundle bundle) {
        }

        public void onSearchResult(@NonNull String str, Bundle bundle, @NonNull List<MediaItem> list) {
        }
    }

    /* loaded from: classes.dex */
    public interface c {
        void a(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback);

        void a(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback);

        void a(@NonNull String str, @Nullable Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback);

        void a(@NonNull String str, @NonNull ItemCallback itemCallback);

        void a(@NonNull String str, SubscriptionCallback subscriptionCallback);

        void d();

        void e();

        boolean f();

        ComponentName g();

        @NonNull
        String h();

        @Nullable
        Bundle i();

        @NonNull
        MediaSessionCompat.Token j();

        @Nullable
        Bundle k();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface h {
        void a(Messenger messenger);

        void a(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle);

        void a(Messenger messenger, String str, List<MediaItem> list, Bundle bundle, Bundle bundle2);
    }

    public MediaBrowserCompat(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.b = new f(context, componentName, connectionCallback, bundle);
        } else if (Build.VERSION.SDK_INT >= 23) {
            this.b = new e(context, componentName, connectionCallback, bundle);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.b = new d(context, componentName, connectionCallback, bundle);
        } else {
            this.b = new g(context, componentName, connectionCallback, bundle);
        }
    }

    public void connect() {
        Log.d("MediaBrowserCompat", "Connecting to a MediaBrowserService.");
        this.b.d();
    }

    public void disconnect() {
        this.b.e();
    }

    public boolean isConnected() {
        return this.b.f();
    }

    @NonNull
    public ComponentName getServiceComponent() {
        return this.b.g();
    }

    @NonNull
    public String getRoot() {
        return this.b.h();
    }

    @Nullable
    public Bundle getExtras() {
        return this.b.i();
    }

    @NonNull
    public MediaSessionCompat.Token getSessionToken() {
        return this.b.j();
    }

    public void subscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (subscriptionCallback != null) {
            this.b.a(str, (Bundle) null, subscriptionCallback);
        } else {
            throw new IllegalArgumentException("callback is null");
        }
    }

    public void subscribe(@NonNull String str, @NonNull Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback is null");
        } else if (bundle != null) {
            this.b.a(str, bundle, subscriptionCallback);
        } else {
            throw new IllegalArgumentException("options are null");
        }
    }

    public void unsubscribe(@NonNull String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b.a(str, (SubscriptionCallback) null);
            return;
        }
        throw new IllegalArgumentException("parentId is empty");
    }

    public void unsubscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        } else if (subscriptionCallback != null) {
            this.b.a(str, subscriptionCallback);
        } else {
            throw new IllegalArgumentException("callback is null");
        }
    }

    public void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
        this.b.a(str, itemCallback);
    }

    public void search(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("query cannot be empty");
        } else if (searchCallback != null) {
            this.b.a(str, bundle, searchCallback);
        } else {
            throw new IllegalArgumentException("callback cannot be null");
        }
    }

    public void sendCustomAction(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback) {
        if (!TextUtils.isEmpty(str)) {
            this.b.a(str, bundle, customActionCallback);
            return;
        }
        throw new IllegalArgumentException("action cannot be empty");
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Bundle getNotifyChildrenChangedOptions() {
        return this.b.k();
    }

    @SuppressLint({"BanParcelableUsage"})
    /* loaded from: classes.dex */
    public static class MediaItem implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator<MediaItem>() { // from class: android.support.v4.media.MediaBrowserCompat.MediaItem.1
            /* renamed from: a */
            public MediaItem createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            /* renamed from: a */
            public MediaItem[] newArray(int i) {
                return new MediaItem[i];
            }
        };
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final int a;
        private final MediaDescriptionCompat b;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static MediaItem fromMediaItem(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            MediaBrowser.MediaItem mediaItem = (MediaBrowser.MediaItem) obj;
            return new MediaItem(MediaDescriptionCompat.fromMediaDescription(a.a(mediaItem)), a.b(mediaItem));
        }

        public static List<MediaItem> fromMediaItemList(List<?> list) {
            if (list == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(fromMediaItem(it.next()));
            }
            return arrayList;
        }

        public MediaItem(@NonNull MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("description cannot be null");
            } else if (!TextUtils.isEmpty(mediaDescriptionCompat.getMediaId())) {
                this.a = i;
                this.b = mediaDescriptionCompat;
            } else {
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
        }

        MediaItem(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            this.b.writeToParcel(parcel, i);
        }

        @NonNull
        public String toString() {
            return "MediaItem{mFlags=" + this.a + ", mDescription=" + this.b + '}';
        }

        public int getFlags() {
            return this.a;
        }

        public boolean isBrowsable() {
            return (this.a & 1) != 0;
        }

        public boolean isPlayable() {
            return (this.a & 2) != 0;
        }

        @NonNull
        public MediaDescriptionCompat getDescription() {
            return this.b;
        }

        @Nullable
        public String getMediaId() {
            return this.b.getMediaId();
        }
    }

    /* loaded from: classes.dex */
    public static class ConnectionCallback {
        final MediaBrowser.ConnectionCallback a;
        b b;

        /* loaded from: classes.dex */
        public interface b {
            void a();

            void b();

            void c();
        }

        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }

        public ConnectionCallback() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.a = new a();
            } else {
                this.a = null;
            }
        }

        void a(b bVar) {
            this.b = bVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @RequiresApi(21)
        /* loaded from: classes.dex */
        public class a extends MediaBrowser.ConnectionCallback {
            a() {
                ConnectionCallback.this = r1;
            }

            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public void onConnected() {
                if (ConnectionCallback.this.b != null) {
                    ConnectionCallback.this.b.a();
                }
                ConnectionCallback.this.onConnected();
            }

            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public void onConnectionSuspended() {
                if (ConnectionCallback.this.b != null) {
                    ConnectionCallback.this.b.b();
                }
                ConnectionCallback.this.onConnectionSuspended();
            }

            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public void onConnectionFailed() {
                if (ConnectionCallback.this.b != null) {
                    ConnectionCallback.this.b.c();
                }
                ConnectionCallback.this.onConnectionFailed();
            }
        }
    }

    /* loaded from: classes.dex */
    public static abstract class SubscriptionCallback {
        final MediaBrowser.SubscriptionCallback a;
        final IBinder b = new Binder();
        WeakReference<j> c;

        public void onChildrenLoaded(@NonNull String str, @NonNull List<MediaItem> list) {
        }

        public void onChildrenLoaded(@NonNull String str, @NonNull List<MediaItem> list, @NonNull Bundle bundle) {
        }

        public void onError(@NonNull String str) {
        }

        public void onError(@NonNull String str, @NonNull Bundle bundle) {
        }

        public SubscriptionCallback() {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a = new b();
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.a = new a();
            } else {
                this.a = null;
            }
        }

        void a(j jVar) {
            this.c = new WeakReference<>(jVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @RequiresApi(21)
        /* loaded from: classes.dex */
        public class a extends MediaBrowser.SubscriptionCallback {
            a() {
                SubscriptionCallback.this = r1;
            }

            @Override // android.media.browse.MediaBrowser.SubscriptionCallback
            public void onChildrenLoaded(@NonNull String str, List<MediaBrowser.MediaItem> list) {
                j jVar = SubscriptionCallback.this.c == null ? null : SubscriptionCallback.this.c.get();
                if (jVar == null) {
                    SubscriptionCallback.this.onChildrenLoaded(str, MediaItem.fromMediaItemList(list));
                    return;
                }
                List<MediaItem> fromMediaItemList = MediaItem.fromMediaItemList(list);
                List<SubscriptionCallback> c = jVar.c();
                List<Bundle> b = jVar.b();
                for (int i = 0; i < c.size(); i++) {
                    Bundle bundle = b.get(i);
                    if (bundle == null) {
                        SubscriptionCallback.this.onChildrenLoaded(str, fromMediaItemList);
                    } else {
                        SubscriptionCallback.this.onChildrenLoaded(str, a(fromMediaItemList, bundle), bundle);
                    }
                }
            }

            @Override // android.media.browse.MediaBrowser.SubscriptionCallback
            public void onError(@NonNull String str) {
                SubscriptionCallback.this.onError(str);
            }

            List<MediaItem> a(List<MediaItem> list, Bundle bundle) {
                if (list == null) {
                    return null;
                }
                int i = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
                int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
                if (i == -1 && i2 == -1) {
                    return list;
                }
                int i3 = i2 * i;
                int i4 = i3 + i2;
                if (i < 0 || i2 < 1 || i3 >= list.size()) {
                    return Collections.emptyList();
                }
                if (i4 > list.size()) {
                    i4 = list.size();
                }
                return list.subList(i3, i4);
            }
        }

        @RequiresApi(26)
        /* loaded from: classes.dex */
        private class b extends a {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            b() {
                super();
                SubscriptionCallback.this = r1;
            }

            @Override // android.media.browse.MediaBrowser.SubscriptionCallback
            public void onChildrenLoaded(@NonNull String str, @NonNull List<MediaBrowser.MediaItem> list, @NonNull Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                SubscriptionCallback.this.onChildrenLoaded(str, MediaItem.fromMediaItemList(list), bundle);
            }

            @Override // android.media.browse.MediaBrowser.SubscriptionCallback
            public void onError(@NonNull String str, @NonNull Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                SubscriptionCallback.this.onError(str, bundle);
            }
        }
    }

    /* loaded from: classes.dex */
    public static abstract class ItemCallback {
        final MediaBrowser.ItemCallback a;

        public void onError(@NonNull String str) {
        }

        public void onItemLoaded(MediaItem mediaItem) {
        }

        public ItemCallback() {
            if (Build.VERSION.SDK_INT >= 23) {
                this.a = new a();
            } else {
                this.a = null;
            }
        }

        @RequiresApi(23)
        /* loaded from: classes.dex */
        private class a extends MediaBrowser.ItemCallback {
            a() {
                ItemCallback.this = r1;
            }

            @Override // android.media.browse.MediaBrowser.ItemCallback
            public void onItemLoaded(MediaBrowser.MediaItem mediaItem) {
                ItemCallback.this.onItemLoaded(MediaItem.fromMediaItem(mediaItem));
            }

            @Override // android.media.browse.MediaBrowser.ItemCallback
            public void onError(@NonNull String str) {
                ItemCallback.this.onError(str);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class g implements c, h {
        final Context a;
        final ComponentName b;
        final ConnectionCallback c;
        final Bundle d;
        a g;
        i h;
        Messenger i;
        private String k;
        private MediaSessionCompat.Token l;
        private Bundle m;
        private Bundle n;
        final b e = new b(this);
        private final ArrayMap<String, j> j = new ArrayMap<>();
        int f = 1;

        public g(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            if (context == null) {
                throw new IllegalArgumentException("context must not be null");
            } else if (componentName == null) {
                throw new IllegalArgumentException("service component must not be null");
            } else if (connectionCallback != null) {
                this.a = context;
                this.b = componentName;
                this.c = connectionCallback;
                this.d = bundle == null ? null : new Bundle(bundle);
            } else {
                throw new IllegalArgumentException("connection callback must not be null");
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void d() {
            int i = this.f;
            if (i == 0 || i == 1) {
                this.f = 2;
                this.e.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.g.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (g.this.f != 0) {
                            g.this.f = 2;
                            if (MediaBrowserCompat.a && g.this.g != null) {
                                throw new RuntimeException("mServiceConnection should be null. Instead it is " + g.this.g);
                            } else if (g.this.h != null) {
                                throw new RuntimeException("mServiceBinderWrapper should be null. Instead it is " + g.this.h);
                            } else if (g.this.i == null) {
                                Intent intent = new Intent(MediaBrowserServiceCompat.SERVICE_INTERFACE);
                                intent.setComponent(g.this.b);
                                g gVar = g.this;
                                gVar.g = new a();
                                boolean z = false;
                                try {
                                    z = g.this.a.bindService(intent, g.this.g, 1);
                                } catch (Exception unused) {
                                    Log.e("MediaBrowserCompat", "Failed binding to service " + g.this.b);
                                }
                                if (!z) {
                                    g.this.a();
                                    g.this.c.onConnectionFailed();
                                }
                                if (MediaBrowserCompat.a) {
                                    Log.d("MediaBrowserCompat", "connect...");
                                    g.this.b();
                                }
                            } else {
                                throw new RuntimeException("mCallbacksMessenger should be null. Instead it is " + g.this.i);
                            }
                        }
                    }
                });
                return;
            }
            throw new IllegalStateException("connect() called while neigther disconnecting nor disconnected (state=" + a(this.f) + ")");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void e() {
            this.f = 0;
            this.e.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.g.2
                @Override // java.lang.Runnable
                public void run() {
                    if (g.this.i != null) {
                        try {
                            g.this.h.a(g.this.i);
                        } catch (RemoteException unused) {
                            Log.w("MediaBrowserCompat", "RemoteException during connect for " + g.this.b);
                        }
                    }
                    int i = g.this.f;
                    g.this.a();
                    if (i != 0) {
                        g.this.f = i;
                    }
                    if (MediaBrowserCompat.a) {
                        Log.d("MediaBrowserCompat", "disconnect...");
                        g.this.b();
                    }
                }
            });
        }

        void a() {
            a aVar = this.g;
            if (aVar != null) {
                this.a.unbindService(aVar);
            }
            this.f = 1;
            this.g = null;
            this.h = null;
            this.i = null;
            this.e.a(null);
            this.k = null;
            this.l = null;
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public boolean f() {
            return this.f == 3;
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        @NonNull
        public ComponentName g() {
            if (f()) {
                return this.b;
            }
            throw new IllegalStateException("getServiceComponent() called while not connected (state=" + this.f + ")");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        @NonNull
        public String h() {
            if (f()) {
                return this.k;
            }
            throw new IllegalStateException("getRoot() called while not connected(state=" + a(this.f) + ")");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        @Nullable
        public Bundle i() {
            if (f()) {
                return this.m;
            }
            throw new IllegalStateException("getExtras() called while not connected (state=" + a(this.f) + ")");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        @NonNull
        public MediaSessionCompat.Token j() {
            if (f()) {
                return this.l;
            }
            throw new IllegalStateException("getSessionToken() called while not connected(state=" + this.f + ")");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            j jVar = this.j.get(str);
            if (jVar == null) {
                jVar = new j();
                this.j.put(str, jVar);
            }
            Bundle bundle2 = bundle == null ? null : new Bundle(bundle);
            jVar.a(bundle2, subscriptionCallback);
            if (f()) {
                try {
                    this.h.a(str, subscriptionCallback.b, bundle2, this.i);
                } catch (RemoteException unused) {
                    Log.d("MediaBrowserCompat", "addSubscription failed with RemoteException parentId=" + str);
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            j jVar = this.j.get(str);
            if (jVar != null) {
                try {
                    if (subscriptionCallback != null) {
                        List<SubscriptionCallback> c = jVar.c();
                        List<Bundle> b = jVar.b();
                        for (int size = c.size() - 1; size >= 0; size--) {
                            if (c.get(size) == subscriptionCallback) {
                                if (f()) {
                                    this.h.a(str, subscriptionCallback.b, this.i);
                                }
                                c.remove(size);
                                b.remove(size);
                            }
                        }
                    } else if (f()) {
                        this.h.a(str, (IBinder) null, this.i);
                    }
                } catch (RemoteException unused) {
                    Log.d("MediaBrowserCompat", "removeSubscription failed with RemoteException parentId=" + str);
                }
                if (jVar.a() || subscriptionCallback == null) {
                    this.j.remove(str);
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull final String str, @NonNull final ItemCallback itemCallback) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("mediaId is empty");
            } else if (itemCallback == null) {
                throw new IllegalArgumentException("cb is null");
            } else if (!f()) {
                Log.i("MediaBrowserCompat", "Not connected, unable to retrieve the MediaItem.");
                this.e.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.g.3
                    @Override // java.lang.Runnable
                    public void run() {
                        itemCallback.onError(str);
                    }
                });
            } else {
                try {
                    this.h.a(str, new ItemReceiver(str, itemCallback, this.e), this.i);
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error getting media item: " + str);
                    this.e.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.g.4
                        @Override // java.lang.Runnable
                        public void run() {
                            itemCallback.onError(str);
                        }
                    });
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull final String str, final Bundle bundle, @NonNull final SearchCallback searchCallback) {
            if (f()) {
                try {
                    this.h.a(str, bundle, new SearchResultReceiver(str, bundle, searchCallback, this.e), this.i);
                } catch (RemoteException e) {
                    Log.i("MediaBrowserCompat", "Remote error searching items with query: " + str, e);
                    this.e.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.g.5
                        @Override // java.lang.Runnable
                        public void run() {
                            searchCallback.onError(str, bundle);
                        }
                    });
                }
            } else {
                throw new IllegalStateException("search() called while not connected (state=" + a(this.f) + ")");
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull final String str, final Bundle bundle, @Nullable final CustomActionCallback customActionCallback) {
            if (f()) {
                try {
                    this.h.b(str, bundle, new CustomActionResultReceiver(str, bundle, customActionCallback, this.e), this.i);
                } catch (RemoteException e) {
                    Log.i("MediaBrowserCompat", "Remote error sending a custom action: action=" + str + ", extras=" + bundle, e);
                    if (customActionCallback != null) {
                        this.e.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.g.6
                            @Override // java.lang.Runnable
                            public void run() {
                                customActionCallback.onError(str, bundle, null);
                            }
                        });
                    }
                }
            } else {
                throw new IllegalStateException("Cannot send a custom action (" + str + ") with extras " + bundle + " because the browser is not connected to the service.");
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.h
        public void a(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle) {
            if (a(messenger, "onConnect")) {
                if (this.f != 2) {
                    Log.w("MediaBrowserCompat", "onConnect from service while mState=" + a(this.f) + "... ignoring");
                    return;
                }
                this.k = str;
                this.l = token;
                this.m = bundle;
                this.f = 3;
                if (MediaBrowserCompat.a) {
                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                    b();
                }
                this.c.onConnected();
                try {
                    for (Map.Entry<String, j> entry : this.j.entrySet()) {
                        String key = entry.getKey();
                        j value = entry.getValue();
                        List<SubscriptionCallback> c = value.c();
                        List<Bundle> b = value.b();
                        for (int i = 0; i < c.size(); i++) {
                            this.h.a(key, c.get(i).b, b.get(i), this.i);
                        }
                    }
                } catch (RemoteException unused) {
                    Log.d("MediaBrowserCompat", "addSubscription failed with RemoteException.");
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.h
        public void a(Messenger messenger) {
            Log.e("MediaBrowserCompat", "onConnectFailed for " + this.b);
            if (a(messenger, "onConnectFailed")) {
                if (this.f != 2) {
                    Log.w("MediaBrowserCompat", "onConnect from service while mState=" + a(this.f) + "... ignoring");
                    return;
                }
                a();
                this.c.onConnectionFailed();
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.h
        public void a(Messenger messenger, String str, List<MediaItem> list, Bundle bundle, Bundle bundle2) {
            if (a(messenger, "onLoadChildren")) {
                if (MediaBrowserCompat.a) {
                    Log.d("MediaBrowserCompat", "onLoadChildren for " + this.b + " id=" + str);
                }
                j jVar = this.j.get(str);
                if (jVar != null) {
                    SubscriptionCallback a2 = jVar.a(bundle);
                    if (a2 == null) {
                        return;
                    }
                    if (bundle == null) {
                        if (list == null) {
                            a2.onError(str);
                            return;
                        }
                        this.n = bundle2;
                        a2.onChildrenLoaded(str, list);
                        this.n = null;
                    } else if (list == null) {
                        a2.onError(str, bundle);
                    } else {
                        this.n = bundle2;
                        a2.onChildrenLoaded(str, list, bundle);
                        this.n = null;
                    }
                } else if (MediaBrowserCompat.a) {
                    Log.d("MediaBrowserCompat", "onLoadChildren for id that isn't subscribed id=" + str);
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public Bundle k() {
            return this.n;
        }

        private static String a(int i) {
            switch (i) {
                case 0:
                    return "CONNECT_STATE_DISCONNECTING";
                case 1:
                    return "CONNECT_STATE_DISCONNECTED";
                case 2:
                    return "CONNECT_STATE_CONNECTING";
                case 3:
                    return "CONNECT_STATE_CONNECTED";
                case 4:
                    return "CONNECT_STATE_SUSPENDED";
                default:
                    return "UNKNOWN/" + i;
            }
        }

        private boolean a(Messenger messenger, String str) {
            int i;
            if (this.i == messenger && (i = this.f) != 0 && i != 1) {
                return true;
            }
            int i2 = this.f;
            if (i2 == 0 || i2 == 1) {
                return false;
            }
            Log.i("MediaBrowserCompat", str + " for " + this.b + " with mCallbacksMessenger=" + this.i + " this=" + this);
            return false;
        }

        void b() {
            Log.d("MediaBrowserCompat", "MediaBrowserCompat...");
            Log.d("MediaBrowserCompat", "  mServiceComponent=" + this.b);
            Log.d("MediaBrowserCompat", "  mCallback=" + this.c);
            Log.d("MediaBrowserCompat", "  mRootHints=" + this.d);
            Log.d("MediaBrowserCompat", "  mState=" + a(this.f));
            Log.d("MediaBrowserCompat", "  mServiceConnection=" + this.g);
            Log.d("MediaBrowserCompat", "  mServiceBinderWrapper=" + this.h);
            Log.d("MediaBrowserCompat", "  mCallbacksMessenger=" + this.i);
            Log.d("MediaBrowserCompat", "  mRootId=" + this.k);
            Log.d("MediaBrowserCompat", "  mMediaSessionToken=" + this.l);
        }

        /* loaded from: classes.dex */
        public class a implements ServiceConnection {
            a() {
                g.this = r1;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
                a(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.g.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (MediaBrowserCompat.a) {
                            Log.d("MediaBrowserCompat", "MediaServiceConnection.onServiceConnected name=" + componentName + " binder=" + iBinder);
                            g.this.b();
                        }
                        if (a.this.a("onServiceConnected")) {
                            g.this.h = new i(iBinder, g.this.d);
                            g.this.i = new Messenger(g.this.e);
                            g.this.e.a(g.this.i);
                            g.this.f = 2;
                            try {
                                if (MediaBrowserCompat.a) {
                                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                                    g.this.b();
                                }
                                g.this.h.a(g.this.a, g.this.i);
                            } catch (RemoteException unused) {
                                Log.w("MediaBrowserCompat", "RemoteException during connect for " + g.this.b);
                                if (MediaBrowserCompat.a) {
                                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                                    g.this.b();
                                }
                            }
                        }
                    }
                });
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(final ComponentName componentName) {
                a(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.g.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (MediaBrowserCompat.a) {
                            Log.d("MediaBrowserCompat", "MediaServiceConnection.onServiceDisconnected name=" + componentName + " this=" + this + " mServiceConnection=" + g.this.g);
                            g.this.b();
                        }
                        if (a.this.a("onServiceDisconnected")) {
                            g.this.h = null;
                            g.this.i = null;
                            g.this.e.a(null);
                            g.this.f = 4;
                            g.this.c.onConnectionSuspended();
                        }
                    }
                });
            }

            private void a(Runnable runnable) {
                if (Thread.currentThread() == g.this.e.getLooper().getThread()) {
                    runnable.run();
                } else {
                    g.this.e.post(runnable);
                }
            }

            boolean a(String str) {
                if (g.this.g == this && g.this.f != 0 && g.this.f != 1) {
                    return true;
                }
                if (g.this.f == 0 || g.this.f == 1) {
                    return false;
                }
                Log.i("MediaBrowserCompat", str + " for " + g.this.b + " with mServiceConnection=" + g.this.g + " this=" + this);
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class d implements ConnectionCallback.b, c, h {
        final Context a;
        protected final MediaBrowser b;
        protected final Bundle c;
        protected int e;
        protected i f;
        protected Messenger g;
        private MediaSessionCompat.Token i;
        private Bundle j;
        protected final b d = new b(this);
        private final ArrayMap<String, j> h = new ArrayMap<>();

        @Override // android.support.v4.media.MediaBrowserCompat.h
        public void a(Messenger messenger) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.h
        public void a(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback.b
        public void c() {
        }

        d(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            this.a = context;
            this.c = bundle != null ? new Bundle(bundle) : new Bundle();
            this.c.putInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 1);
            this.c.putInt(MediaBrowserProtocol.EXTRA_CALLING_PID, Process.myPid());
            connectionCallback.a(this);
            this.b = new MediaBrowser(context, componentName, connectionCallback.a, this.c);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void d() {
            this.b.connect();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void e() {
            Messenger messenger;
            i iVar = this.f;
            if (!(iVar == null || (messenger = this.g) == null)) {
                try {
                    iVar.b(messenger);
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error unregistering client messenger.");
                }
            }
            this.b.disconnect();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public boolean f() {
            return this.b.isConnected();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public ComponentName g() {
            return this.b.getServiceComponent();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        @NonNull
        public String h() {
            return this.b.getRoot();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        @Nullable
        public Bundle i() {
            return this.b.getExtras();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        @NonNull
        public MediaSessionCompat.Token j() {
            if (this.i == null) {
                this.i = MediaSessionCompat.Token.fromToken(this.b.getSessionToken());
            }
            return this.i;
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            j jVar = this.h.get(str);
            if (jVar == null) {
                jVar = new j();
                this.h.put(str, jVar);
            }
            subscriptionCallback.a(jVar);
            Bundle bundle2 = bundle == null ? null : new Bundle(bundle);
            jVar.a(bundle2, subscriptionCallback);
            i iVar = this.f;
            if (iVar == null) {
                this.b.subscribe(str, subscriptionCallback.a);
                return;
            }
            try {
                iVar.a(str, subscriptionCallback.b, bundle2, this.g);
            } catch (RemoteException unused) {
                Log.i("MediaBrowserCompat", "Remote error subscribing media item: " + str);
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            j jVar = this.h.get(str);
            if (jVar != null) {
                i iVar = this.f;
                if (iVar != null) {
                    try {
                        if (subscriptionCallback == null) {
                            iVar.a(str, (IBinder) null, this.g);
                        } else {
                            List<SubscriptionCallback> c = jVar.c();
                            List<Bundle> b = jVar.b();
                            for (int size = c.size() - 1; size >= 0; size--) {
                                if (c.get(size) == subscriptionCallback) {
                                    this.f.a(str, subscriptionCallback.b, this.g);
                                    c.remove(size);
                                    b.remove(size);
                                }
                            }
                        }
                    } catch (RemoteException unused) {
                        Log.d("MediaBrowserCompat", "removeSubscription failed with RemoteException parentId=" + str);
                    }
                } else if (subscriptionCallback == null) {
                    this.b.unsubscribe(str);
                } else {
                    List<SubscriptionCallback> c2 = jVar.c();
                    List<Bundle> b2 = jVar.b();
                    for (int size2 = c2.size() - 1; size2 >= 0; size2--) {
                        if (c2.get(size2) == subscriptionCallback) {
                            c2.remove(size2);
                            b2.remove(size2);
                        }
                    }
                    if (c2.size() == 0) {
                        this.b.unsubscribe(str);
                    }
                }
                if (jVar.a() || subscriptionCallback == null) {
                    this.h.remove(str);
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull final String str, @NonNull final ItemCallback itemCallback) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("mediaId is empty");
            } else if (itemCallback == null) {
                throw new IllegalArgumentException("cb is null");
            } else if (!this.b.isConnected()) {
                Log.i("MediaBrowserCompat", "Not connected, unable to retrieve the MediaItem.");
                this.d.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.d.1
                    @Override // java.lang.Runnable
                    public void run() {
                        itemCallback.onError(str);
                    }
                });
            } else if (this.f == null) {
                this.d.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.d.2
                    @Override // java.lang.Runnable
                    public void run() {
                        itemCallback.onError(str);
                    }
                });
            } else {
                try {
                    this.f.a(str, new ItemReceiver(str, itemCallback, this.d), this.g);
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error getting media item: " + str);
                    this.d.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.d.3
                        @Override // java.lang.Runnable
                        public void run() {
                            itemCallback.onError(str);
                        }
                    });
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull final String str, final Bundle bundle, @NonNull final SearchCallback searchCallback) {
            if (!f()) {
                throw new IllegalStateException("search() called while not connected");
            } else if (this.f == null) {
                Log.i("MediaBrowserCompat", "The connected service doesn't support search.");
                this.d.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.d.4
                    @Override // java.lang.Runnable
                    public void run() {
                        searchCallback.onError(str, bundle);
                    }
                });
            } else {
                try {
                    this.f.a(str, bundle, new SearchResultReceiver(str, bundle, searchCallback, this.d), this.g);
                } catch (RemoteException e) {
                    Log.i("MediaBrowserCompat", "Remote error searching items with query: " + str, e);
                    this.d.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.d.5
                        @Override // java.lang.Runnable
                        public void run() {
                            searchCallback.onError(str, bundle);
                        }
                    });
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull final String str, final Bundle bundle, @Nullable final CustomActionCallback customActionCallback) {
            if (f()) {
                if (this.f == null) {
                    Log.i("MediaBrowserCompat", "The connected service doesn't support sendCustomAction.");
                    if (customActionCallback != null) {
                        this.d.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.d.6
                            @Override // java.lang.Runnable
                            public void run() {
                                customActionCallback.onError(str, bundle, null);
                            }
                        });
                    }
                }
                try {
                    this.f.b(str, bundle, new CustomActionResultReceiver(str, bundle, customActionCallback, this.d), this.g);
                } catch (RemoteException e) {
                    Log.i("MediaBrowserCompat", "Remote error sending a custom action: action=" + str + ", extras=" + bundle, e);
                    if (customActionCallback != null) {
                        this.d.post(new Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.d.7
                            @Override // java.lang.Runnable
                            public void run() {
                                customActionCallback.onError(str, bundle, null);
                            }
                        });
                    }
                }
            } else {
                throw new IllegalStateException("Cannot send a custom action (" + str + ") with extras " + bundle + " because the browser is not connected to the service.");
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback.b
        public void a() {
            try {
                Bundle extras = this.b.getExtras();
                if (extras != null) {
                    this.e = extras.getInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 0);
                    IBinder binder = BundleCompat.getBinder(extras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER);
                    if (binder != null) {
                        this.f = new i(binder, this.c);
                        this.g = new Messenger(this.d);
                        this.d.a(this.g);
                        try {
                            this.f.b(this.a, this.g);
                        } catch (RemoteException unused) {
                            Log.i("MediaBrowserCompat", "Remote error registering client messenger.");
                        }
                    }
                    IMediaSession asInterface = IMediaSession.Stub.asInterface(BundleCompat.getBinder(extras, MediaBrowserProtocol.EXTRA_SESSION_BINDER));
                    if (asInterface != null) {
                        this.i = MediaSessionCompat.Token.fromToken(this.b.getSessionToken(), asInterface);
                    }
                }
            } catch (IllegalStateException e) {
                Log.e("MediaBrowserCompat", "Unexpected IllegalStateException", e);
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback.b
        public void b() {
            this.f = null;
            this.g = null;
            this.i = null;
            this.d.a(null);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.h
        public void a(Messenger messenger, String str, List<MediaItem> list, Bundle bundle, Bundle bundle2) {
            if (this.g == messenger) {
                j jVar = this.h.get(str);
                if (jVar != null) {
                    SubscriptionCallback a = jVar.a(bundle);
                    if (a == null) {
                        return;
                    }
                    if (bundle == null) {
                        if (list == null) {
                            a.onError(str);
                            return;
                        }
                        this.j = bundle2;
                        a.onChildrenLoaded(str, list);
                        this.j = null;
                    } else if (list == null) {
                        a.onError(str, bundle);
                    } else {
                        this.j = bundle2;
                        a.onChildrenLoaded(str, list, bundle);
                        this.j = null;
                    }
                } else if (MediaBrowserCompat.a) {
                    Log.d("MediaBrowserCompat", "onLoadChildren for id that isn't subscribed id=" + str);
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c
        public Bundle k() {
            return this.j;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(23)
    /* loaded from: classes.dex */
    public static class e extends d {
        e(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.d, android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull String str, @NonNull ItemCallback itemCallback) {
            if (this.f == null) {
                this.b.getItem(str, itemCallback.a);
            } else {
                super.a(str, itemCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(26)
    /* loaded from: classes.dex */
    public static class f extends e {
        f(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.d, android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull String str, @Nullable Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            if (this.f != null && this.e >= 2) {
                super.a(str, bundle, subscriptionCallback);
            } else if (bundle == null) {
                this.b.subscribe(str, subscriptionCallback.a);
            } else {
                this.b.subscribe(str, bundle, subscriptionCallback.a);
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.d, android.support.v4.media.MediaBrowserCompat.c
        public void a(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            if (this.f != null && this.e >= 2) {
                super.a(str, subscriptionCallback);
            } else if (subscriptionCallback == null) {
                this.b.unsubscribe(str);
            } else {
                this.b.unsubscribe(str, subscriptionCallback.a);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class j {
        private final List<SubscriptionCallback> a = new ArrayList();
        private final List<Bundle> b = new ArrayList();

        public boolean a() {
            return this.a.isEmpty();
        }

        public List<Bundle> b() {
            return this.b;
        }

        public List<SubscriptionCallback> c() {
            return this.a;
        }

        public SubscriptionCallback a(Bundle bundle) {
            for (int i = 0; i < this.b.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions(this.b.get(i), bundle)) {
                    return this.a.get(i);
                }
            }
            return null;
        }

        public void a(Bundle bundle, SubscriptionCallback subscriptionCallback) {
            for (int i = 0; i < this.b.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions(this.b.get(i), bundle)) {
                    this.a.set(i, subscriptionCallback);
                    return;
                }
            }
            this.a.add(subscriptionCallback);
            this.b.add(bundle);
        }
    }

    /* loaded from: classes.dex */
    public static class b extends Handler {
        private final WeakReference<h> a;
        private WeakReference<Messenger> b;

        b(h hVar) {
            this.a = new WeakReference<>(hVar);
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            WeakReference<Messenger> weakReference = this.b;
            if (weakReference != null && weakReference.get() != null && this.a.get() != null) {
                Bundle data = message.getData();
                MediaSessionCompat.ensureClassLoader(data);
                h hVar = this.a.get();
                Messenger messenger = this.b.get();
                try {
                    switch (message.what) {
                        case 1:
                            Bundle bundle = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                            MediaSessionCompat.ensureClassLoader(bundle);
                            hVar.a(messenger, data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (MediaSessionCompat.Token) data.getParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN), bundle);
                            break;
                        case 2:
                            hVar.a(messenger);
                            break;
                        case 3:
                            Bundle bundle2 = data.getBundle(MediaBrowserProtocol.DATA_OPTIONS);
                            MediaSessionCompat.ensureClassLoader(bundle2);
                            Bundle bundle3 = data.getBundle(MediaBrowserProtocol.DATA_NOTIFY_CHILDREN_CHANGED_OPTIONS);
                            MediaSessionCompat.ensureClassLoader(bundle3);
                            hVar.a(messenger, data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), data.getParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST), bundle2, bundle3);
                            break;
                        default:
                            Log.w("MediaBrowserCompat", "Unhandled message: " + message + "\n  Client version: 1\n  Service version: " + message.arg1);
                            break;
                    }
                } catch (BadParcelableException unused) {
                    Log.e("MediaBrowserCompat", "Could not unparcel the data.");
                    if (message.what == 1) {
                        hVar.a(messenger);
                    }
                }
            }
        }

        void a(Messenger messenger) {
            this.b = new WeakReference<>(messenger);
        }
    }

    /* loaded from: classes.dex */
    public static class i {
        private Messenger a;
        private Bundle b;

        public i(IBinder iBinder, Bundle bundle) {
            this.a = new Messenger(iBinder);
            this.b = bundle;
        }

        void a(Context context, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString(MediaBrowserProtocol.DATA_PACKAGE_NAME, context.getPackageName());
            bundle.putInt(MediaBrowserProtocol.DATA_CALLING_PID, Process.myPid());
            bundle.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, this.b);
            a(1, bundle, messenger);
        }

        void a(Messenger messenger) throws RemoteException {
            a(2, (Bundle) null, messenger);
        }

        void a(String str, IBinder iBinder, Bundle bundle, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str);
            BundleCompat.putBinder(bundle2, MediaBrowserProtocol.DATA_CALLBACK_TOKEN, iBinder);
            bundle2.putBundle(MediaBrowserProtocol.DATA_OPTIONS, bundle);
            a(3, bundle2, messenger);
        }

        void a(String str, IBinder iBinder, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str);
            BundleCompat.putBinder(bundle, MediaBrowserProtocol.DATA_CALLBACK_TOKEN, iBinder);
            a(4, bundle, messenger);
        }

        void a(String str, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str);
            bundle.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, resultReceiver);
            a(5, bundle, messenger);
        }

        void b(Context context, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString(MediaBrowserProtocol.DATA_PACKAGE_NAME, context.getPackageName());
            bundle.putInt(MediaBrowserProtocol.DATA_CALLING_PID, Process.myPid());
            bundle.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, this.b);
            a(6, bundle, messenger);
        }

        void b(Messenger messenger) throws RemoteException {
            a(7, (Bundle) null, messenger);
        }

        void a(String str, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaBrowserProtocol.DATA_SEARCH_QUERY, str);
            bundle2.putBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS, bundle);
            bundle2.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, resultReceiver);
            a(8, bundle2, messenger);
        }

        void b(String str, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaBrowserProtocol.DATA_CUSTOM_ACTION, str);
            bundle2.putBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS, bundle);
            bundle2.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, resultReceiver);
            a(9, bundle2, messenger);
        }

        private void a(int i, Bundle bundle, Messenger messenger) throws RemoteException {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = 1;
            obtain.setData(bundle);
            obtain.replyTo = messenger;
            this.a.send(obtain);
        }
    }

    /* loaded from: classes.dex */
    public static class ItemReceiver extends ResultReceiver {
        private final String d;
        private final ItemCallback e;

        ItemReceiver(String str, ItemCallback itemCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = itemCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            if (bundle != null) {
                bundle = MediaSessionCompat.unparcelWithClassLoader(bundle);
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                this.e.onError(this.d);
                return;
            }
            Parcelable parcelable = bundle.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
            if (parcelable == null || (parcelable instanceof MediaItem)) {
                this.e.onItemLoaded((MediaItem) parcelable);
            } else {
                this.e.onError(this.d);
            }
        }
    }

    /* loaded from: classes.dex */
    private static class SearchResultReceiver extends ResultReceiver {
        private final String d;
        private final Bundle e;
        private final SearchCallback f;

        SearchResultReceiver(String str, Bundle bundle, SearchCallback searchCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = bundle;
            this.f = searchCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            if (bundle != null) {
                bundle = MediaSessionCompat.unparcelWithClassLoader(bundle);
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS)) {
                this.f.onError(this.d, this.e);
                return;
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS);
            if (parcelableArray != null) {
                ArrayList arrayList = new ArrayList();
                for (Parcelable parcelable : parcelableArray) {
                    arrayList.add((MediaItem) parcelable);
                }
                this.f.onSearchResult(this.d, this.e, arrayList);
                return;
            }
            this.f.onError(this.d, this.e);
        }
    }

    /* loaded from: classes.dex */
    private static class CustomActionResultReceiver extends ResultReceiver {
        private final String d;
        private final Bundle e;
        private final CustomActionCallback f;

        CustomActionResultReceiver(String str, Bundle bundle, CustomActionCallback customActionCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = bundle;
            this.f = customActionCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            if (this.f != null) {
                MediaSessionCompat.ensureClassLoader(bundle);
                switch (i) {
                    case -1:
                        this.f.onError(this.d, this.e, bundle);
                        return;
                    case 0:
                        this.f.onResult(this.d, this.e, bundle);
                        return;
                    case 1:
                        this.f.onProgressUpdate(this.d, this.e, bundle);
                        return;
                    default:
                        Log.w("MediaBrowserCompat", "Unknown result code: " + i + " (extras=" + this.e + ", resultData=" + bundle + ")");
                        return;
                }
            }
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class a {
        @DoNotInline
        static MediaDescription a(MediaBrowser.MediaItem mediaItem) {
            return mediaItem.getDescription();
        }

        @DoNotInline
        static int b(MediaBrowser.MediaItem mediaItem) {
            return mediaItem.getFlags();
        }
    }
}
