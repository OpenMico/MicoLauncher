package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.PermissionChecker;
import java.util.Calendar;

/* compiled from: TwilightManager.java */
/* loaded from: classes.dex */
class g {
    private static g a;
    private final Context b;
    private final LocationManager c;
    private final a d = new a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static g a(@NonNull Context context) {
        if (a == null) {
            Context applicationContext = context.getApplicationContext();
            a = new g(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
        }
        return a;
    }

    @VisibleForTesting
    g(@NonNull Context context, @NonNull LocationManager locationManager) {
        this.b = context;
        this.c = locationManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        a aVar = this.d;
        if (c()) {
            return aVar.a;
        }
        Location b = b();
        if (b != null) {
            a(b);
            return aVar.a;
        }
        Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int i = Calendar.getInstance().get(11);
        return i < 6 || i >= 22;
    }

    @SuppressLint({"MissingPermission"})
    private Location b() {
        Location location = null;
        Location a2 = PermissionChecker.checkSelfPermission(this.b, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? a("network") : null;
        if (PermissionChecker.checkSelfPermission(this.b, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            location = a("gps");
        }
        return (location == null || a2 == null) ? location != null ? location : a2 : location.getTime() > a2.getTime() ? location : a2;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    private Location a(String str) {
        try {
            if (this.c.isProviderEnabled(str)) {
                return this.c.getLastKnownLocation(str);
            }
            return null;
        } catch (Exception e) {
            Log.d("TwilightManager", "Failed to get last known location", e);
            return null;
        }
    }

    private boolean c() {
        return this.d.f > System.currentTimeMillis();
    }

    private void a(@NonNull Location location) {
        long j;
        a aVar = this.d;
        long currentTimeMillis = System.currentTimeMillis();
        f a2 = f.a();
        a2.a(currentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        long j2 = a2.a;
        a2.a(currentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z = true;
        if (a2.c != 1) {
            z = false;
        }
        long j3 = a2.b;
        long j4 = a2.a;
        a2.a(86400000 + currentTimeMillis, location.getLatitude(), location.getLongitude());
        long j5 = a2.b;
        if (j3 == -1 || j4 == -1) {
            j = 43200000 + currentTimeMillis;
        } else {
            j = (currentTimeMillis > j4 ? 0 + j5 : currentTimeMillis > j3 ? 0 + j4 : 0 + j3) + 60000;
        }
        aVar.a = z;
        aVar.b = j2;
        aVar.c = j3;
        aVar.d = j4;
        aVar.e = j5;
        aVar.f = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TwilightManager.java */
    /* loaded from: classes.dex */
    public static class a {
        boolean a;
        long b;
        long c;
        long d;
        long e;
        long f;

        a() {
        }
    }
}
