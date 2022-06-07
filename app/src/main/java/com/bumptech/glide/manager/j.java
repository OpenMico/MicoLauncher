package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.util.GlideSuppliers;
import com.bumptech.glide.util.Util;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* compiled from: SingletonConnectivityReceiver.java */
/* loaded from: classes.dex */
final class j {
    private static volatile j b;
    @GuardedBy("this")
    final Set<ConnectivityMonitor.ConnectivityListener> a = new HashSet();
    private final a c;
    @GuardedBy("this")
    private boolean d;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SingletonConnectivityReceiver.java */
    /* loaded from: classes.dex */
    public interface a {
        boolean a();

        void b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static j a(@NonNull Context context) {
        if (b == null) {
            synchronized (j.class) {
                if (b == null) {
                    b = new j(context.getApplicationContext());
                }
            }
        }
        return b;
    }

    private j(@NonNull final Context context) {
        a aVar;
        GlideSuppliers.GlideSupplier memorize = GlideSuppliers.memorize(new GlideSuppliers.GlideSupplier<ConnectivityManager>() { // from class: com.bumptech.glide.manager.j.1
            /* renamed from: a */
            public ConnectivityManager get() {
                return (ConnectivityManager) context.getSystemService("connectivity");
            }
        });
        ConnectivityMonitor.ConnectivityListener connectivityListener = new ConnectivityMonitor.ConnectivityListener() { // from class: com.bumptech.glide.manager.j.2
            @Override // com.bumptech.glide.manager.ConnectivityMonitor.ConnectivityListener
            public void onConnectivityChanged(boolean z) {
                ArrayList<ConnectivityMonitor.ConnectivityListener> arrayList;
                synchronized (j.this) {
                    arrayList = new ArrayList(j.this.a);
                }
                for (ConnectivityMonitor.ConnectivityListener connectivityListener2 : arrayList) {
                    connectivityListener2.onConnectivityChanged(z);
                }
            }
        };
        if (Build.VERSION.SDK_INT >= 24) {
            aVar = new b(memorize, connectivityListener);
        } else {
            aVar = new c(context, memorize, connectivityListener);
        }
        this.c = aVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(ConnectivityMonitor.ConnectivityListener connectivityListener) {
        this.a.add(connectivityListener);
        a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void b(ConnectivityMonitor.ConnectivityListener connectivityListener) {
        this.a.remove(connectivityListener);
        b();
    }

    @GuardedBy("this")
    private void a() {
        if (!this.d && !this.a.isEmpty()) {
            this.d = this.c.a();
        }
    }

    @GuardedBy("this")
    private void b() {
        if (this.d && this.a.isEmpty()) {
            this.c.b();
            this.d = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SingletonConnectivityReceiver.java */
    @RequiresApi(24)
    /* loaded from: classes.dex */
    public static final class b implements a {
        boolean a;
        final ConnectivityMonitor.ConnectivityListener b;
        private final GlideSuppliers.GlideSupplier<ConnectivityManager> c;
        private final ConnectivityManager.NetworkCallback d = new AnonymousClass1();

        /* compiled from: SingletonConnectivityReceiver.java */
        /* renamed from: com.bumptech.glide.manager.j$b$1  reason: invalid class name */
        /* loaded from: classes.dex */
        class AnonymousClass1 extends ConnectivityManager.NetworkCallback {
            AnonymousClass1() {
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(@NonNull Network network) {
                b(true);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(@NonNull Network network) {
                b(false);
            }

            private void b(final boolean z) {
                Util.postOnUiThread(new Runnable() { // from class: com.bumptech.glide.manager.j.b.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass1.this.a(z);
                    }
                });
            }

            void a(boolean z) {
                Util.assertMainThread();
                boolean z2 = b.this.a;
                b bVar = b.this;
                bVar.a = z;
                if (z2 != z) {
                    bVar.b.onConnectivityChanged(z);
                }
            }
        }

        b(GlideSuppliers.GlideSupplier<ConnectivityManager> glideSupplier, ConnectivityMonitor.ConnectivityListener connectivityListener) {
            this.c = glideSupplier;
            this.b = connectivityListener;
        }

        @Override // com.bumptech.glide.manager.j.a
        @SuppressLint({"MissingPermission"})
        public boolean a() {
            this.a = this.c.get().getActiveNetwork() != null;
            try {
                this.c.get().registerDefaultNetworkCallback(this.d);
                return true;
            } catch (RuntimeException e) {
                if (Log.isLoggable("ConnectivityMonitor", 5)) {
                    Log.w("ConnectivityMonitor", "Failed to register callback", e);
                }
                return false;
            }
        }

        @Override // com.bumptech.glide.manager.j.a
        public void b() {
            this.c.get().unregisterNetworkCallback(this.d);
        }
    }

    /* compiled from: SingletonConnectivityReceiver.java */
    /* loaded from: classes.dex */
    private static final class c implements a {
        final ConnectivityMonitor.ConnectivityListener a;
        boolean b;
        private final Context c;
        private final GlideSuppliers.GlideSupplier<ConnectivityManager> d;
        private final BroadcastReceiver e = new BroadcastReceiver() { // from class: com.bumptech.glide.manager.j.c.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(@NonNull Context context, Intent intent) {
                boolean z = c.this.b;
                c cVar = c.this;
                cVar.b = cVar.c();
                if (z != c.this.b) {
                    if (Log.isLoggable("ConnectivityMonitor", 3)) {
                        Log.d("ConnectivityMonitor", "connectivity changed, isConnected: " + c.this.b);
                    }
                    c.this.a.onConnectivityChanged(c.this.b);
                }
            }
        };

        c(Context context, GlideSuppliers.GlideSupplier<ConnectivityManager> glideSupplier, ConnectivityMonitor.ConnectivityListener connectivityListener) {
            this.c = context.getApplicationContext();
            this.d = glideSupplier;
            this.a = connectivityListener;
        }

        @Override // com.bumptech.glide.manager.j.a
        public boolean a() {
            this.b = c();
            try {
                this.c.registerReceiver(this.e, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                return true;
            } catch (SecurityException e) {
                if (!Log.isLoggable("ConnectivityMonitor", 5)) {
                    return false;
                }
                Log.w("ConnectivityMonitor", "Failed to register", e);
                return false;
            }
        }

        @Override // com.bumptech.glide.manager.j.a
        public void b() {
            this.c.unregisterReceiver(this.e);
        }

        @SuppressLint({"MissingPermission"})
        boolean c() {
            try {
                NetworkInfo activeNetworkInfo = this.d.get().getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            } catch (RuntimeException e) {
                if (Log.isLoggable("ConnectivityMonitor", 5)) {
                    Log.w("ConnectivityMonitor", "Failed to determine connectivity status when connectivity changed", e);
                }
                return true;
            }
        }
    }
}
