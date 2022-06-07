package com.google.android.exoplayer2.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public final class NetworkTypeObserver {
    @Nullable
    private static NetworkTypeObserver a;
    private final Handler b = new Handler(Looper.getMainLooper());
    private final CopyOnWriteArrayList<WeakReference<Listener>> c = new CopyOnWriteArrayList<>();
    private final Object d = new Object();
    @GuardedBy("networkTypeLock")
    private int e = 0;

    /* loaded from: classes2.dex */
    public interface Listener {
        void onNetworkTypeChanged(int i);
    }

    /* loaded from: classes2.dex */
    public static final class Config {
        private static volatile boolean a;

        public static void disable5GNsaDisambiguation() {
            a = true;
        }

        private Config() {
        }
    }

    public static synchronized NetworkTypeObserver getInstance(Context context) {
        NetworkTypeObserver networkTypeObserver;
        synchronized (NetworkTypeObserver.class) {
            if (a == null) {
                a = new NetworkTypeObserver(context);
            }
            networkTypeObserver = a;
        }
        return networkTypeObserver;
    }

    @VisibleForTesting
    public static synchronized void resetForTests() {
        synchronized (NetworkTypeObserver.class) {
            a = null;
        }
    }

    private NetworkTypeObserver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(new a(), intentFilter);
    }

    public void register(final Listener listener) {
        a();
        this.c.add(new WeakReference<>(listener));
        this.b.post(new Runnable() { // from class: com.google.android.exoplayer2.util.-$$Lambda$NetworkTypeObserver$DTQRzevtkL7jGnWhLFcVnlnlEF4
            @Override // java.lang.Runnable
            public final void run() {
                NetworkTypeObserver.this.a(listener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Listener listener) {
        listener.onNetworkTypeChanged(getNetworkType());
    }

    public int getNetworkType() {
        int i;
        synchronized (this.d) {
            i = this.e;
        }
        return i;
    }

    private void a() {
        Iterator<WeakReference<Listener>> it = this.c.iterator();
        while (it.hasNext()) {
            WeakReference<Listener> next = it.next();
            if (next.get() == null) {
                this.c.remove(next);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        synchronized (this.d) {
            if (this.e != i) {
                this.e = i;
                Iterator<WeakReference<Listener>> it = this.c.iterator();
                while (it.hasNext()) {
                    WeakReference<Listener> next = it.next();
                    Listener listener = next.get();
                    if (listener != null) {
                        listener.onNetworkTypeChanged(i);
                    } else {
                        this.c.remove(next);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return 0;
        }
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return 1;
            }
            switch (activeNetworkInfo.getType()) {
                case 0:
                case 4:
                case 5:
                    return a(activeNetworkInfo);
                case 1:
                    return 2;
                case 2:
                case 3:
                case 7:
                case 8:
                default:
                    return 8;
                case 6:
                    return 5;
                case 9:
                    return 7;
            }
        } catch (SecurityException unused) {
            return 0;
        }
    }

    private static int a(NetworkInfo networkInfo) {
        switch (networkInfo.getSubtype()) {
            case 1:
            case 2:
                return 3;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 17:
                return 4;
            case 13:
                return 5;
            case 16:
            case 19:
            default:
                return 6;
            case 18:
                return 2;
            case 20:
                return Util.SDK_INT >= 29 ? 9 : 0;
        }
    }

    /* loaded from: classes2.dex */
    private final class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int b = NetworkTypeObserver.b(context);
            if (Util.SDK_INT >= 29 && !Config.a && b == 5) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) Assertions.checkNotNull((TelephonyManager) context.getSystemService("phone"));
                    b bVar = new b();
                    if (Util.SDK_INT < 31) {
                        telephonyManager.listen(bVar, 1);
                    } else {
                        telephonyManager.listen(bVar, 1048576);
                    }
                    telephonyManager.listen(bVar, 0);
                    return;
                } catch (RuntimeException unused) {
                }
            }
            NetworkTypeObserver.this.a(b);
        }
    }

    /* loaded from: classes2.dex */
    private class b extends PhoneStateListener {
        private b() {
        }

        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(@Nullable ServiceState serviceState) {
            String serviceState2 = serviceState == null ? "" : serviceState.toString();
            NetworkTypeObserver.this.a(serviceState2.contains("nrState=CONNECTED") || serviceState2.contains("nrState=NOT_RESTRICTED") ? 10 : 5);
        }
    }
}
