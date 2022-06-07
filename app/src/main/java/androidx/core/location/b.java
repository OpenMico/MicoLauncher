package androidx.core.location;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.os.Build;
import androidx.annotation.GuardedBy;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.util.Iterator;

/* compiled from: GpsStatusWrapper.java */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
class b extends GnssStatusCompat {
    private final GpsStatus a;
    @GuardedBy("mWrapped")
    private Iterator<GpsSatellite> c;
    @GuardedBy("mWrapped")
    private int b = -1;
    @GuardedBy("mWrapped")
    private int d = -1;
    @GuardedBy("mWrapped")
    private GpsSatellite e = null;

    private static int b(int i) {
        if (i > 0 && i <= 32) {
            return 1;
        }
        if (i >= 33 && i <= 64) {
            return 2;
        }
        if (i > 64 && i <= 88) {
            return 3;
        }
        if (i <= 200 || i > 235) {
            return (i < 193 || i > 200) ? 0 : 4;
        }
        return 5;
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasBasebandCn0DbHz(int i) {
        return false;
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasCarrierFrequencyHz(int i) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(GpsStatus gpsStatus) {
        this.a = (GpsStatus) Preconditions.checkNotNull(gpsStatus);
        this.c = this.a.getSatellites().iterator();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getSatelliteCount() {
        int i;
        synchronized (this.a) {
            if (this.b == -1) {
                for (GpsSatellite gpsSatellite : this.a.getSatellites()) {
                    this.b++;
                }
                this.b++;
            }
            i = this.b;
        }
        return i;
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getConstellationType(int i) {
        if (Build.VERSION.SDK_INT < 24) {
            return 1;
        }
        return b(a(i).getPrn());
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getSvid(int i) {
        if (Build.VERSION.SDK_INT < 24) {
            return a(i).getPrn();
        }
        return c(a(i).getPrn());
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getCn0DbHz(int i) {
        return a(i).getSnr();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getElevationDegrees(int i) {
        return a(i).getElevation();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getAzimuthDegrees(int i) {
        return a(i).getAzimuth();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasEphemerisData(int i) {
        return a(i).hasEphemeris();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasAlmanacData(int i) {
        return a(i).hasAlmanac();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean usedInFix(int i) {
        return a(i).usedInFix();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getCarrierFrequencyHz(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getBasebandCn0DbHz(int i) {
        throw new UnsupportedOperationException();
    }

    private GpsSatellite a(int i) {
        GpsSatellite gpsSatellite;
        synchronized (this.a) {
            if (i < this.d) {
                this.c = this.a.getSatellites().iterator();
                this.d = -1;
            }
            while (true) {
                if (this.d >= i) {
                    break;
                }
                this.d++;
                if (!this.c.hasNext()) {
                    this.e = null;
                    break;
                }
                this.e = this.c.next();
            }
            gpsSatellite = this.e;
        }
        return (GpsSatellite) Preconditions.checkNotNull(gpsSatellite);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        return this.a.equals(((b) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    private static int c(int i) {
        int b = b(i);
        if (b == 5) {
            return i - 200;
        }
        switch (b) {
            case 2:
                return i + 87;
            case 3:
                return i - 64;
            default:
                return i;
        }
    }
}
