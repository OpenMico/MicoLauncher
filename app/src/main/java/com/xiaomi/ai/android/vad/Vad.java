package com.xiaomi.ai.android.vad;

import com.xiaomi.ai.log.Logger;

/* loaded from: classes2.dex */
public class Vad implements IVad {
    public static final int MAX_VAD_CHECK_SIZE = 16000;
    public static final int MIN_VAD_CHECK_SIZE = 8000;
    private long a;
    private int b;
    private boolean c;
    private boolean d;
    private byte[] e;
    private boolean f;
    private int g;
    private int h;
    private int i;
    private float j;
    private boolean k;

    public Vad() {
        this.h = 600;
        this.i = 200;
        this.j = 4.0f;
        this.e = new byte[16000];
    }

    public Vad(int i, int i2, float f) {
        this.h = 600;
        this.i = 200;
        this.j = 4.0f;
        this.e = new byte[16000];
        this.h = i;
        this.i = i2;
        this.j = f;
    }

    private native void native_delete(long j);

    private native long native_new();

    private native int native_vadCheckBegin(long j, byte[] bArr, int i, int i2);

    private native int native_vadCheckEnd(long j, byte[] bArr, int i, int i2);

    private native int native_vadInit(long j, int i, int i2, float f);

    private native int native_vadUnInit(long j);

    @Override // com.xiaomi.ai.android.vad.IVad
    public synchronized boolean checkVad(byte[] bArr) {
        if (!this.k) {
            Logger.d("Vad", "checkVad: mAvailable=" + this.k);
            return false;
        } else if (bArr == null) {
            return false;
        } else {
            if (bArr.length > 16000) {
                this.g = 0;
                return false;
            }
            int length = this.g + bArr.length;
            if (length > 16000) {
                System.arraycopy(bArr, 0, this.e, 0, bArr.length);
                this.g = bArr.length;
                return false;
            }
            System.arraycopy(bArr, 0, this.e, this.g, bArr.length);
            this.g = length;
            if (this.g <= 8000) {
                return false;
            }
            boolean isSpeak = isSpeak(this.e, this.g);
            this.g = 0;
            if (isSpeak || !this.f) {
                this.f = isSpeak;
                return false;
            }
            this.f = false;
            return true;
        }
    }

    protected void finalize() {
        super.finalize();
        long j = this.a;
        if (j != 0) {
            native_vadUnInit(j);
            native_delete(this.a);
            this.a = 0L;
        }
    }

    @Override // com.xiaomi.ai.android.vad.IVad
    public boolean init() {
        return init(this.h, this.i, this.j);
    }

    public boolean init(int i, int i2, float f) {
        release();
        this.a = native_new();
        long j = this.a;
        if (j == 0) {
            Logger.d("Vad", "init: failed to new native instance");
            return false;
        } else if (native_vadInit(j, i, i2, f) != 0) {
            Logger.d("Vad", "init: failed to init native vad");
            release();
            return false;
        } else {
            this.k = true;
            return true;
        }
    }

    public boolean isSpeak(byte[] bArr, int i) {
        if (i < 8000 || i > 16000) {
            throw new IllegalArgumentException("buffer length must more than 8k bytes and less than 16k bytes");
        } else if (!this.k) {
            Logger.d("Vad", "isSpeak: mAvailable=" + this.k);
            return false;
        } else {
            if (!this.d) {
                this.b++;
                if (!this.c) {
                    if (native_vadCheckBegin(this.a, bArr, i, this.b == 1 ? 0 : 1) >= 0) {
                        this.c = true;
                    }
                } else if (native_vadCheckEnd(this.a, bArr, i, 1) >= 0) {
                    this.c = false;
                    this.d = true;
                }
            }
            return this.c;
        }
    }

    @Override // com.xiaomi.ai.android.vad.IVad
    public void release() {
        this.d = false;
        this.c = false;
        this.b = 0;
        this.g = 0;
        this.f = false;
        long j = this.a;
        if (j != 0) {
            native_vadUnInit(j);
            native_delete(this.a);
            this.a = 0L;
        }
        this.k = false;
    }
}
