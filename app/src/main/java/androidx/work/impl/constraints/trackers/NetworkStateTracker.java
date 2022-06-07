package androidx.work.impl.constraints.trackers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.work.Logger;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class NetworkStateTracker extends ConstraintTracker<NetworkState> {
    static final String b = Logger.tagWithPrefix("NetworkStateTracker");
    private final ConnectivityManager c = (ConnectivityManager) this.mAppContext.getSystemService("connectivity");
    @RequiresApi(24)
    private b d;
    private a e;

    public NetworkStateTracker(@NonNull Context context, @NonNull TaskExecutor taskExecutor) {
        super(context, taskExecutor);
        if (c()) {
            this.d = new b();
        } else {
            this.e = new a();
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.work.impl.constraints.trackers.ConstraintTracker
    public NetworkState getInitialState() {
        return a();
    }

    @Override // androidx.work.impl.constraints.trackers.ConstraintTracker
    public void startTracking() {
        if (c()) {
            try {
                Logger.get().debug(b, "Registering network callback", new Throwable[0]);
                this.c.registerDefaultNetworkCallback(this.d);
            } catch (IllegalArgumentException | SecurityException e) {
                Logger.get().error(b, "Received exception while registering network callback", e);
            }
        } else {
            Logger.get().debug(b, "Registering broadcast receiver", new Throwable[0]);
            this.mAppContext.registerReceiver(this.e, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    @Override // androidx.work.impl.constraints.trackers.ConstraintTracker
    public void stopTracking() {
        if (c()) {
            try {
                Logger.get().debug(b, "Unregistering network callback", new Throwable[0]);
                this.c.unregisterNetworkCallback(this.d);
            } catch (IllegalArgumentException | SecurityException e) {
                Logger.get().error(b, "Received exception while unregistering network callback", e);
            }
        } else {
            Logger.get().debug(b, "Unregistering broadcast receiver", new Throwable[0]);
            this.mAppContext.unregisterReceiver(this.e);
        }
    }

    private static boolean c() {
        return Build.VERSION.SDK_INT >= 24;
    }

    NetworkState a() {
        NetworkInfo activeNetworkInfo = this.c.getActiveNetworkInfo();
        boolean z = true;
        boolean z2 = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        boolean b2 = b();
        boolean isActiveNetworkMetered = ConnectivityManagerCompat.isActiveNetworkMetered(this.c);
        if (activeNetworkInfo == null || activeNetworkInfo.isRoaming()) {
            z = false;
        }
        return new NetworkState(z2, b2, isActiveNetworkMetered, z);
    }

    @VisibleForTesting
    boolean b() {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        try {
            NetworkCapabilities networkCapabilities = this.c.getNetworkCapabilities(this.c.getActiveNetwork());
            if (networkCapabilities != null) {
                if (networkCapabilities.hasCapability(16)) {
                    return true;
                }
            }
            return false;
        } catch (SecurityException e) {
            Logger.get().error(b, "Unable to validate active network", e);
            return false;
        }
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    private class b extends ConnectivityManager.NetworkCallback {
        b() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            Logger.get().debug(NetworkStateTracker.b, String.format("Network capabilities changed: %s", networkCapabilities), new Throwable[0]);
            NetworkStateTracker networkStateTracker = NetworkStateTracker.this;
            networkStateTracker.setState(networkStateTracker.a());
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(@NonNull Network network) {
            Logger.get().debug(NetworkStateTracker.b, "Network connection lost", new Throwable[0]);
            NetworkStateTracker networkStateTracker = NetworkStateTracker.this;
            networkStateTracker.setState(networkStateTracker.a());
        }
    }

    /* loaded from: classes.dex */
    private class a extends BroadcastReceiver {
        a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                Logger.get().debug(NetworkStateTracker.b, "Network broadcast received", new Throwable[0]);
                NetworkStateTracker networkStateTracker = NetworkStateTracker.this;
                networkStateTracker.setState(networkStateTracker.a());
            }
        }
    }
}
