package com.xiaomi.micolauncher.common.speech.utils;

import android.os.Parcel;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class WakeupInfo {
    private static int a;
    private static long k;
    private static long l;
    private static long m;
    private int b;
    private float c;
    private float d;
    private int e;
    private long f;
    private long g;
    private long h;
    private long i;
    private String j;

    public WakeupInfo(Parcel parcel) {
        if (parcel != null) {
            byte[] bArr = new byte[36];
            parcel.setDataPosition(0);
            this.b = parcel.readInt();
            this.c = parcel.readFloat();
            this.d = parcel.readFloat();
            this.e = parcel.readInt();
            this.f = parcel.readLong();
            if (parcel.readInt() == 36) {
                try {
                    parcel.readByteArray(bArr);
                    this.j = new String(bArr);
                } catch (RuntimeException e) {
                    L.speech.e("%s readByteArray.error=%s", "[WakeupInfo]: ", e);
                }
                L.speech.d("%s mWakeupId=%s", "[WakeupInfo]: ", this.j);
            } else {
                parcel.setDataPosition(parcel.dataPosition() - 4);
            }
            this.g = parcel.readLong();
            this.h = parcel.readLong();
            this.i = parcel.readLong();
            a++;
            L.latency.d("WuwSize=%d, score=%f, wakeupId=%s", Integer.valueOf(this.e), Float.valueOf(this.d), this.j);
        }
    }

    public WakeupInfo() {
        this.b = 0;
        this.c = 0.0f;
        this.d = 0.0f;
        this.e = 0;
        this.f = 0L;
        this.g = 0L;
    }

    private long a() {
        long j = this.g;
        if (j != 0) {
            long j2 = k;
            if (j2 > 0) {
                return j2 - j;
            }
        }
        return 0L;
    }

    public long getAlgDelay() {
        long j = this.h;
        if (j > 0) {
            long j2 = this.g;
            if (j2 > 0) {
                return j - j2;
            }
        }
        return 0L;
    }

    private long b() {
        long j = k;
        if (j > 0) {
            long j2 = l;
            if (j2 > 0) {
                return j - j2;
            }
        }
        return 0L;
    }

    private long c() {
        long j = this.i;
        if (j > 0) {
            long j2 = this.h;
            if (j2 > 0 && j >= j2) {
                return j - j2;
            }
        }
        return 0L;
    }

    private long d() {
        long j = l;
        if (j <= 0) {
            return 0L;
        }
        long j2 = this.i;
        return j2 <= 0 ? j - this.h : j - j2;
    }

    public static void setPlaySoundAtMs() {
        k = System.currentTimeMillis();
    }

    public static void setLocalWakeupAtMs() {
        m = System.currentTimeMillis();
    }

    public static void setWakeupEventAtMs() {
        l = System.currentTimeMillis();
    }

    public long getWakeupEventAtMs() {
        return l;
    }

    public int getWuwSize() {
        return this.e;
    }

    public float getWakeupAngle() {
        return this.c;
    }

    public float getWakeupScore() {
        return this.d;
    }

    public int getWakeupCnt() {
        return a;
    }

    public String getWakeupId() {
        return this.j;
    }

    public long getAlgWakeupAtMs() {
        return this.h;
    }

    public long getMdWakeupAtMs() {
        return this.i;
    }

    public long getShouldWakeupAtMs() {
        return this.g;
    }

    public void printWakeupInfo() {
        if (this.g > 0) {
            ThreadUtil.getWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$WakeupInfo$u7B--UVBv6P9hjnnJI4jMfWHi98
                @Override // java.lang.Runnable
                public final void run() {
                    WakeupInfo.this.e();
                }
            }, TimeUnit.SECONDS.toMillis(1L));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        L.latency.i("wakeup_latency.alg=%s, .md=%s, .event=%s, .play=%s, .total=%s", Long.valueOf(getAlgDelay()), Long.valueOf(c()), Long.valueOf(d()), Long.valueOf(b()), Long.valueOf(a()));
    }
}
