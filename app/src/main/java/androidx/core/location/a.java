package androidx.core.location;

import android.location.GnssStatus;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;

/* compiled from: GnssStatusWrapper.java */
@RequiresApi(24)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
class a extends GnssStatusCompat {
    private final GnssStatus a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(GnssStatus gnssStatus) {
        this.a = (GnssStatus) Preconditions.checkNotNull(gnssStatus);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getSatelliteCount() {
        return this.a.getSatelliteCount();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getConstellationType(int i) {
        return this.a.getConstellationType(i);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getSvid(int i) {
        return this.a.getSvid(i);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getCn0DbHz(int i) {
        return this.a.getCn0DbHz(i);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getElevationDegrees(int i) {
        return this.a.getElevationDegrees(i);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getAzimuthDegrees(int i) {
        return this.a.getAzimuthDegrees(i);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasEphemerisData(int i) {
        return this.a.hasEphemerisData(i);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasAlmanacData(int i) {
        return this.a.hasAlmanacData(i);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean usedInFix(int i) {
        return this.a.usedInFix(i);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasCarrierFrequencyHz(int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.a.hasCarrierFrequencyHz(i);
        }
        return false;
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getCarrierFrequencyHz(int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.a.getCarrierFrequencyHz(i);
        }
        throw new UnsupportedOperationException();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasBasebandCn0DbHz(int i) {
        if (Build.VERSION.SDK_INT >= 30) {
            return this.a.hasBasebandCn0DbHz(i);
        }
        return false;
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getBasebandCn0DbHz(int i) {
        if (Build.VERSION.SDK_INT >= 30) {
            return this.a.getBasebandCn0DbHz(i);
        }
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        return this.a.equals(((a) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
