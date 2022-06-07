package androidx.core.location;

import android.content.Context;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.DoNotInline;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.collection.SimpleArrayMap;
import androidx.core.location.GnssStatusCompat;
import androidx.core.os.CancellationSignal;
import androidx.core.os.ExecutorCompat;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes.dex */
public final class LocationManagerCompat {
    private static Field a;
    @GuardedBy("sGnssStatusListeners")
    private static final SimpleArrayMap<Object, Object> b = new SimpleArrayMap<>();

    public static boolean isLocationEnabled(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return a.a(locationManager);
        }
        if (Build.VERSION.SDK_INT <= 19) {
            try {
                if (a == null) {
                    a = LocationManager.class.getDeclaredField("mContext");
                    a.setAccessible(true);
                }
                Context context = (Context) a.get(locationManager);
                if (context != null) {
                    if (Build.VERSION.SDK_INT == 19) {
                        return Settings.Secure.getInt(context.getContentResolver(), "location_mode", 0) != 0;
                    }
                    return !TextUtils.isEmpty(Settings.Secure.getString(context.getContentResolver(), "location_providers_allowed"));
                }
            } catch (ClassCastException | IllegalAccessException | NoSuchFieldException | SecurityException unused) {
            }
        }
        return locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps");
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void getCurrentLocation(@NonNull LocationManager locationManager, @NonNull String str, @Nullable CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull final Consumer<Location> consumer) {
        if (Build.VERSION.SDK_INT >= 30) {
            b.a(locationManager, str, cancellationSignal, executor, consumer);
            return;
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        final Location lastKnownLocation = locationManager.getLastKnownLocation(str);
        if (lastKnownLocation == null || SystemClock.elapsedRealtime() - LocationCompat.getElapsedRealtimeMillis(lastKnownLocation) >= 10000) {
            final c cVar = new c(locationManager, executor, consumer);
            locationManager.requestLocationUpdates(str, 0L, 0.0f, cVar, Looper.getMainLooper());
            if (cancellationSignal != null) {
                cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.core.location.LocationManagerCompat.2
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
                    public void onCancel() {
                        c.this.a();
                    }
                });
            }
            cVar.a(30000L);
            return;
        }
        executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.1
            @Override // java.lang.Runnable
            public void run() {
                Consumer.this.accept(lastKnownLocation);
            }
        });
    }

    @Nullable
    public static String getGnssHardwareModelName(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return a.b(locationManager);
        }
        return null;
    }

    public static int getGnssYearOfHardware(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return a.c(locationManager);
        }
        return 0;
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback, @NonNull Handler handler) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, ExecutorCompat.create(handler), callback);
        }
        return registerGnssStatusCallback(locationManager, new f(handler), callback);
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            return a(locationManager, null, executor, callback);
        }
        Looper myLooper = Looper.myLooper();
        if (myLooper == null) {
            myLooper = Looper.getMainLooper();
        }
        return a(locationManager, new Handler(myLooper), executor, callback);
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0116 A[Catch: all -> 0x0132, TryCatch #7 {all -> 0x0132, blocks: (B:73:0x00e4, B:81:0x00f5, B:82:0x010b, B:85:0x010e, B:87:0x0116, B:89:0x011e, B:90:0x0124, B:91:0x0125, B:92:0x012a, B:93:0x012b, B:94:0x0131), top: B:106:0x00a4 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x012b A[Catch: all -> 0x0132, TryCatch #7 {all -> 0x0132, blocks: (B:73:0x00e4, B:81:0x00f5, B:82:0x010b, B:85:0x010e, B:87:0x0116, B:89:0x011e, B:90:0x0124, B:91:0x0125, B:92:0x012a, B:93:0x012b, B:94:0x0131), top: B:106:0x00a4 }] */
    @androidx.annotation.RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(final android.location.LocationManager r9, android.os.Handler r10, java.util.concurrent.Executor r11, androidx.core.location.GnssStatusCompat.Callback r12) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.location.LocationManagerCompat.a(android.location.LocationManager, android.os.Handler, java.util.concurrent.Executor, androidx.core.location.GnssStatusCompat$Callback):boolean");
    }

    public static void unregisterGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            synchronized (b) {
                GnssStatus.Callback callback2 = (d) b.remove(callback);
                if (callback2 != null) {
                    locationManager.unregisterGnssStatusCallback(callback2);
                }
            }
        } else if (Build.VERSION.SDK_INT >= 24) {
            synchronized (b) {
                g gVar = (g) b.remove(callback);
                if (gVar != null) {
                    gVar.a();
                    locationManager.unregisterGnssStatusCallback(gVar);
                }
            }
        } else {
            synchronized (b) {
                e eVar = (e) b.remove(callback);
                if (eVar != null) {
                    eVar.a();
                    locationManager.removeGpsStatusListener(eVar);
                }
            }
        }
    }

    private LocationManagerCompat() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class d extends GnssStatus.Callback {
        final GnssStatusCompat.Callback a;

        d(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.a = callback;
        }

        @Override // android.location.GnssStatus.Callback
        public void onStarted() {
            this.a.onStarted();
        }

        @Override // android.location.GnssStatus.Callback
        public void onStopped() {
            this.a.onStopped();
        }

        @Override // android.location.GnssStatus.Callback
        public void onFirstFix(int i) {
            this.a.onFirstFix(i);
        }

        @Override // android.location.GnssStatus.Callback
        public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
            this.a.onSatelliteStatusChanged(GnssStatusCompat.wrap(gnssStatus));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(24)
    /* loaded from: classes.dex */
    public static class g extends GnssStatus.Callback {
        final GnssStatusCompat.Callback a;
        @Nullable
        volatile Executor b;

        g(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.a = callback;
        }

        public void a(Executor executor) {
            boolean z = true;
            Preconditions.checkArgument(executor != null, "invalid null executor");
            if (this.b != null) {
                z = false;
            }
            Preconditions.checkState(z);
            this.b = executor;
        }

        public void a() {
            this.b = null;
        }

        @Override // android.location.GnssStatus.Callback
        public void onStarted() {
            final Executor executor = this.b;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.g.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (g.this.b == executor) {
                            g.this.a.onStarted();
                        }
                    }
                });
            }
        }

        @Override // android.location.GnssStatus.Callback
        public void onStopped() {
            final Executor executor = this.b;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.g.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (g.this.b == executor) {
                            g.this.a.onStopped();
                        }
                    }
                });
            }
        }

        @Override // android.location.GnssStatus.Callback
        public void onFirstFix(final int i) {
            final Executor executor = this.b;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.g.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (g.this.b == executor) {
                            g.this.a.onFirstFix(i);
                        }
                    }
                });
            }
        }

        @Override // android.location.GnssStatus.Callback
        public void onSatelliteStatusChanged(final GnssStatus gnssStatus) {
            final Executor executor = this.b;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.g.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (g.this.b == executor) {
                            g.this.a.onSatelliteStatusChanged(GnssStatusCompat.wrap(gnssStatus));
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class e implements GpsStatus.Listener {
        final GnssStatusCompat.Callback a;
        @Nullable
        volatile Executor b;
        private final LocationManager c;

        e(LocationManager locationManager, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.c = locationManager;
            this.a = callback;
        }

        public void a(Executor executor) {
            Preconditions.checkState(this.b == null);
            this.b = executor;
        }

        public void a() {
            this.b = null;
        }

        @Override // android.location.GpsStatus.Listener
        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        public void onGpsStatusChanged(int i) {
            final Executor executor = this.b;
            if (executor != null) {
                switch (i) {
                    case 1:
                        executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.e.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (e.this.b == executor) {
                                    e.this.a.onStarted();
                                }
                            }
                        });
                        return;
                    case 2:
                        executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.e.2
                            @Override // java.lang.Runnable
                            public void run() {
                                if (e.this.b == executor) {
                                    e.this.a.onStopped();
                                }
                            }
                        });
                        return;
                    case 3:
                        GpsStatus gpsStatus = this.c.getGpsStatus(null);
                        if (gpsStatus != null) {
                            final int timeToFirstFix = gpsStatus.getTimeToFirstFix();
                            executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.e.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (e.this.b == executor) {
                                        e.this.a.onFirstFix(timeToFirstFix);
                                    }
                                }
                            });
                            return;
                        }
                        return;
                    case 4:
                        GpsStatus gpsStatus2 = this.c.getGpsStatus(null);
                        if (gpsStatus2 != null) {
                            final GnssStatusCompat wrap = GnssStatusCompat.wrap(gpsStatus2);
                            executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.e.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (e.this.b == executor) {
                                        e.this.a.onSatelliteStatusChanged(wrap);
                                    }
                                }
                            });
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @RequiresApi(30)
    /* loaded from: classes.dex */
    private static class b {
        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        static void a(LocationManager locationManager, @NonNull String str, @Nullable CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull final Consumer<Location> consumer) {
            locationManager.getCurrentLocation(str, cancellationSignal != null ? (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject() : null, executor, new java.util.function.Consumer<Location>() { // from class: androidx.core.location.LocationManagerCompat.b.1
                /* renamed from: a */
                public void accept(Location location) {
                    Consumer.this.accept(location);
                }
            });
        }
    }

    @RequiresApi(28)
    /* loaded from: classes.dex */
    private static class a {
        @DoNotInline
        static boolean a(LocationManager locationManager) {
            return locationManager.isLocationEnabled();
        }

        @DoNotInline
        static String b(LocationManager locationManager) {
            return locationManager.getGnssHardwareModelName();
        }

        @DoNotInline
        static int c(LocationManager locationManager) {
            return locationManager.getGnssYearOfHardware();
        }
    }

    /* loaded from: classes.dex */
    private static final class c implements LocationListener {
        @Nullable
        Runnable a;
        private final LocationManager b;
        private final Executor c;
        private final Handler d = new Handler(Looper.getMainLooper());
        private Consumer<Location> e;
        @GuardedBy("this")
        private boolean f;

        @Override // android.location.LocationListener
        public void onProviderEnabled(@NonNull String str) {
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        c(LocationManager locationManager, Executor executor, Consumer<Location> consumer) {
            this.b = locationManager;
            this.c = executor;
            this.e = consumer;
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void a() {
            synchronized (this) {
                if (!this.f) {
                    this.f = true;
                    b();
                }
            }
        }

        public void a(long j) {
            synchronized (this) {
                if (!this.f) {
                    this.a = new Runnable() { // from class: androidx.core.location.LocationManagerCompat.c.1
                        @Override // java.lang.Runnable
                        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
                        public void run() {
                            c cVar = c.this;
                            cVar.a = null;
                            cVar.onLocationChanged(null);
                        }
                    };
                    this.d.postDelayed(this.a, j);
                }
            }
        }

        @Override // android.location.LocationListener
        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void onProviderDisabled(@NonNull String str) {
            onLocationChanged(null);
        }

        @Override // android.location.LocationListener
        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void onLocationChanged(@Nullable final Location location) {
            synchronized (this) {
                if (!this.f) {
                    this.f = true;
                    final Consumer<Location> consumer = this.e;
                    this.c.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.c.2
                        @Override // java.lang.Runnable
                        public void run() {
                            consumer.accept(location);
                        }
                    });
                    b();
                }
            }
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        private void b() {
            this.e = null;
            this.b.removeUpdates(this);
            Runnable runnable = this.a;
            if (runnable != null) {
                this.d.removeCallbacks(runnable);
                this.a = null;
            }
        }
    }

    /* loaded from: classes.dex */
    private static final class f implements Executor {
        private final Handler a;

        f(@NonNull Handler handler) {
            this.a = (Handler) Preconditions.checkNotNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            if (Looper.myLooper() == this.a.getLooper()) {
                runnable.run();
            } else if (!this.a.post((Runnable) Preconditions.checkNotNull(runnable))) {
                throw new RejectedExecutionException(this.a + " is shutting down");
            }
        }
    }
}
