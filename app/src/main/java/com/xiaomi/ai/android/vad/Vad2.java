package com.xiaomi.ai.android.vad;

import com.xiaomi.ai.log.Logger;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class Vad2 implements IVad {
    private static boolean d = false;
    private long a;
    private long b;
    private int[] c;
    private byte[] e;
    private int f;
    private boolean g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private int o;
    private boolean p;

    /* loaded from: classes2.dex */
    public class DecodeResult {
        public boolean hasVoice;
        public boolean hasVoicePre;
        public int minVoiceLength;
        public int packNumBeg;
        public int packNumEnd;
        public int packNumPreBeg;
        public int packNumVoice;
        public int retVal;

        public DecodeResult() {
        }
    }

    public Vad2(int i, int i2) {
        this.c = new int[8];
        this.e = new byte[640];
        this.f = 0;
        this.h = 0;
        this.i = -1;
        this.j = 1;
        this.k = false;
        this.l = 60;
        this.m = 50;
        this.n = 1500;
        this.o = 6000;
        this.l = i;
        this.m = i2;
        Logger.a("Vad2", "minVoice:" + i + ", minSil:" + i2);
    }

    public Vad2(int i, int i2, int i3, int i4) {
        this.c = new int[8];
        this.e = new byte[640];
        this.f = 0;
        this.h = 0;
        this.i = -1;
        this.j = 1;
        this.k = false;
        this.l = 60;
        this.m = 50;
        this.n = 1500;
        this.o = 6000;
        this.l = i;
        this.m = i2;
        this.n = i3;
        this.o = i4;
        Logger.a("Vad2", "minVoice:" + i + ", minSil:" + i2 + ", maxVoice:" + i3 + ", maxLengthReset:" + i4);
    }

    private native long createTask(long j);

    private native int endDelay(long j, long j2);

    private native boolean hasVoice(long j, long j2, byte[] bArr, int i, int[] iArr);

    private native long newVad(int i, int i2, int i3, int i4);

    private native boolean releaseVad(long j);

    private native int startDelay(long j, long j2);

    private native boolean stopTask(long j, long j2, int[] iArr);

    @Override // com.xiaomi.ai.android.vad.IVad
    public synchronized boolean checkVad(byte[] bArr) {
        if (!this.p) {
            Logger.d("Vad2", "checkVad: mAvailable=" + this.p);
            return false;
        }
        int length = bArr.length;
        int i = this.f + length;
        byte[] bArr2 = new byte[i];
        System.arraycopy(this.e, 0, bArr2, 0, this.f);
        System.arraycopy(bArr, 0, bArr2, this.f, length);
        this.f = i % 640;
        int i2 = i / 640;
        for (int i3 = 0; i3 < i2; i3++) {
            System.arraycopy(bArr2, i3 * 640, this.e, 0, 640);
            boolean hasVoice = hasVoice(this.e, 0, 640);
            this.h++;
            if (!this.g && hasVoice) {
                Logger.a("Vad2", "detect vad start at pack:" + this.h);
                this.g = true;
            }
            if (!this.g || hasVoice) {
                this.g = hasVoice;
            } else {
                Logger.a("Vad2", "stop speak at pack:" + this.h);
                this.g = false;
                return true;
            }
        }
        System.arraycopy(bArr2, i2 * 640, this.e, 0, this.f);
        return false;
    }

    protected void finalize() {
        release();
        super.finalize();
    }

    public int getEndDelay() {
        if (!this.p) {
            Logger.d("Vad2", "getEndDelay: mAvailable=false");
            return -1;
        }
        long j = this.a;
        if (j == 0) {
            return -1;
        }
        long j2 = this.b;
        if (j2 != 0) {
            return endDelay(j, j2);
        }
        return -1;
    }

    public int getStartDelay() {
        if (!this.p) {
            Logger.d("Vad2", "getStartDelay: mAvailable=false");
            return -1;
        }
        long j = this.a;
        if (j == 0) {
            return -1;
        }
        long j2 = this.b;
        if (j2 != 0) {
            return startDelay(j, j2);
        }
        return -1;
    }

    public synchronized boolean hasVoice(byte[] bArr, int i, int i2) {
        if (i2 != 640) {
            throw new IllegalArgumentException("buffer length must be 640");
        } else if (!this.p) {
            Logger.d("Vad2", "hasVoice: mAvailable=" + this.p);
            return false;
        } else {
            DecodeResult processTask = processTask(bArr, 640);
            if (processTask == null) {
                return false;
            }
            if (!processTask.hasVoice) {
                return false;
            }
            if (this.i != processTask.packNumBeg) {
                Logger.a("Vad2", "isSpeak:mPackNumBeg=" + (processTask.packNumBeg * 0.01d));
                this.k = true;
            }
            if (processTask.packNumEnd > this.j && this.k) {
                Logger.a("Vad2", "isSpeak:mPackNumEnd=" + (processTask.packNumEnd * 0.01d));
                this.k = false;
            }
            this.i = processTask.packNumBeg;
            this.j = processTask.packNumEnd;
            return this.k;
        }
    }

    @Override // com.xiaomi.ai.android.vad.IVad
    public boolean init() {
        if (!d) {
            System.loadLibrary("vad2");
            d = true;
        }
        release();
        this.a = newVad(this.l, this.m, this.n, this.o);
        this.b = createTask(this.a);
        this.g = false;
        this.h = 0;
        this.i = -1;
        this.j = 1;
        this.f = 0;
        this.p = true;
        return true;
    }

    public synchronized boolean isSpeak(byte[] bArr, int i, int i2) {
        if (!this.p) {
            Logger.d("Vad2", "isSpeak: mAvailable=" + this.p);
            return false;
        }
        int i3 = this.f + i2;
        byte[] bArr2 = new byte[i3];
        System.arraycopy(this.e, 0, bArr2, 0, this.f);
        System.arraycopy(bArr, i, bArr2, this.f, i2);
        this.f = i3 % 640;
        int i4 = i3 / 640;
        boolean z = false;
        for (int i5 = 0; i5 < i4; i5++) {
            System.arraycopy(bArr2, i5 * 640, this.e, 0, 640);
            if (hasVoice(this.e, 0, 640)) {
                z = true;
            }
        }
        System.arraycopy(bArr2, i4 * 640, this.e, 0, this.f);
        return z;
    }

    public DecodeResult processTask(byte[] bArr, int i) {
        if (!this.p) {
            Logger.d("Vad2", "processTask: mAvailable=" + this.p);
            return null;
        }
        if (!(bArr == null || i == 0)) {
            DecodeResult decodeResult = new DecodeResult();
            Arrays.fill(this.c, 0);
            if (hasVoice(this.a, this.b, bArr, i, this.c)) {
                int[] iArr = this.c;
                decodeResult.retVal = iArr[0];
                boolean z = true;
                decodeResult.hasVoice = iArr[1] == 1;
                int[] iArr2 = this.c;
                decodeResult.packNumVoice = iArr2[2];
                decodeResult.packNumBeg = iArr2[3];
                decodeResult.packNumEnd = iArr2[4];
                decodeResult.minVoiceLength = iArr2[5];
                if (iArr2[6] != 1) {
                    z = false;
                }
                decodeResult.hasVoicePre = z;
                decodeResult.packNumPreBeg = this.c[7];
                return decodeResult;
            }
        }
        return null;
    }

    @Override // com.xiaomi.ai.android.vad.IVad
    public synchronized void release() {
        stopTask();
        if (this.a != 0) {
            releaseVad(this.a);
            this.g = false;
            this.h = 0;
            this.i = -1;
            this.j = 1;
            this.a = 0L;
            this.f = 0;
            Logger.a("Vad2", "releaseVad");
        }
        this.p = false;
        Logger.a("Vad2", "release");
    }

    public boolean reset() {
        Arrays.fill(this.c, 0);
        boolean stopTask = stopTask(this.a, this.b, this.c);
        if (stopTask) {
            this.b = createTask(this.a);
        }
        this.g = false;
        this.h = 0;
        this.i = -1;
        this.j = 1;
        this.f = 0;
        return stopTask;
    }

    public DecodeResult stopTask() {
        Logger.a("Vad2", "stopTask");
        long j = this.a;
        if (j != 0) {
            long j2 = this.b;
            if (j2 != 0 && stopTask(j, j2, this.c)) {
                Logger.a("Vad2", "stopTask: get result");
                DecodeResult decodeResult = new DecodeResult();
                int[] iArr = this.c;
                boolean z = false;
                decodeResult.retVal = iArr[0];
                decodeResult.hasVoice = iArr[1] == 1;
                int[] iArr2 = this.c;
                decodeResult.packNumVoice = iArr2[2];
                decodeResult.packNumBeg = iArr2[3];
                decodeResult.packNumEnd = iArr2[4];
                decodeResult.minVoiceLength = iArr2[5];
                if (iArr2[6] == 1) {
                    z = true;
                }
                decodeResult.hasVoicePre = z;
                decodeResult.packNumPreBeg = this.c[7];
                this.b = 0L;
                return decodeResult;
            }
        }
        this.b = 0L;
        return null;
    }
}
