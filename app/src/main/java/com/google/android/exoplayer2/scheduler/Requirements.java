package com.google.android.exoplayer2.scheduler;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class Requirements implements Parcelable {
    public static final Parcelable.Creator<Requirements> CREATOR = new Parcelable.Creator<Requirements>() { // from class: com.google.android.exoplayer2.scheduler.Requirements.1
        /* renamed from: a */
        public Requirements createFromParcel(Parcel parcel) {
            return new Requirements(parcel.readInt());
        }

        /* renamed from: a */
        public Requirements[] newArray(int i) {
            return new Requirements[i];
        }
    };
    public static final int DEVICE_CHARGING = 8;
    public static final int DEVICE_IDLE = 4;
    public static final int DEVICE_STORAGE_NOT_LOW = 16;
    public static final int NETWORK = 1;
    public static final int NETWORK_UNMETERED = 2;
    private final int a;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface RequirementFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Requirements(int i) {
        this.a = (i & 2) != 0 ? i | 1 : i;
    }

    public int getRequirements() {
        return this.a;
    }

    public Requirements filterRequirements(int i) {
        int i2 = this.a;
        int i3 = i & i2;
        return i3 == i2 ? this : new Requirements(i3);
    }

    public boolean isNetworkRequired() {
        return (this.a & 1) != 0;
    }

    public boolean isUnmeteredNetworkRequired() {
        return (this.a & 2) != 0;
    }

    public boolean isChargingRequired() {
        return (this.a & 8) != 0;
    }

    public boolean isIdleRequired() {
        return (this.a & 4) != 0;
    }

    public boolean isStorageNotLowRequired() {
        return (this.a & 16) != 0;
    }

    public boolean checkRequirements(Context context) {
        return getNotMetRequirements(context) == 0;
    }

    public int getNotMetRequirements(Context context) {
        int a = a(context);
        if (isChargingRequired() && !b(context)) {
            a |= 8;
        }
        if (isIdleRequired() && !c(context)) {
            a |= 4;
        }
        return (!isStorageNotLowRequired() || d(context)) ? a : a | 16;
    }

    private int a(Context context) {
        if (!isNetworkRequired()) {
            return 0;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) Assertions.checkNotNull(context.getSystemService("connectivity"));
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected() || !a(connectivityManager)) {
            return this.a & 3;
        }
        return (!isUnmeteredNetworkRequired() || !connectivityManager.isActiveNetworkMetered()) ? 0 : 2;
    }

    private boolean b(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver == null) {
            return false;
        }
        int intExtra = registerReceiver.getIntExtra("status", -1);
        return intExtra == 2 || intExtra == 5;
    }

    private boolean c(Context context) {
        PowerManager powerManager = (PowerManager) Assertions.checkNotNull(context.getSystemService("power"));
        if (Util.SDK_INT >= 23) {
            return powerManager.isDeviceIdleMode();
        }
        if (Util.SDK_INT >= 20) {
            if (!powerManager.isInteractive()) {
                return true;
            }
        } else if (!powerManager.isScreenOn()) {
            return true;
        }
        return false;
    }

    private boolean d(Context context) {
        return context.registerReceiver(null, new IntentFilter("android.intent.action.DEVICE_STORAGE_LOW")) == null;
    }

    private static boolean a(ConnectivityManager connectivityManager) {
        if (Util.SDK_INT < 24) {
            return true;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            return false;
        }
        try {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            if (networkCapabilities != null) {
                if (networkCapabilities.hasCapability(16)) {
                    return true;
                }
            }
            return false;
        } catch (SecurityException unused) {
            return true;
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.a == ((Requirements) obj).a;
    }

    public int hashCode() {
        return this.a;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
    }
}
