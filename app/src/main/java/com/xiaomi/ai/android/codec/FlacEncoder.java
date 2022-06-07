package com.xiaomi.ai.android.codec;

import com.xiaomi.ai.log.Logger;

/* loaded from: classes2.dex */
public class FlacEncoder {
    private long a;

    private native void native_delete(long j);

    private native byte[] native_flac_encode(long j, byte[] bArr, int i, int i2, int i3);

    private native byte[] native_flac_encode_32bits(long j, byte[] bArr, int i, int i2, int i3);

    private native long native_new();

    public boolean a() {
        b();
        this.a = native_new();
        if (this.a != 0) {
            return true;
        }
        Logger.d("AudioEncoder", "init: failed to new native instance");
        return false;
    }

    public synchronized byte[] a(byte[] bArr, int i, int i2, int i3) {
        if (bArr == null) {
            Logger.d("AudioEncoder", "encode：data is null");
            return null;
        } else if (this.a == 0) {
            Logger.d("AudioEncoder", "encode：mNativeInstance==0");
            return null;
        } else if (i2 == 32) {
            return native_flac_encode_32bits(this.a, bArr, i, i2, i3);
        } else {
            return native_flac_encode(this.a, bArr, i, i2, i3);
        }
    }

    public void b() {
        long j = this.a;
        if (j != 0) {
            native_delete(j);
            this.a = 0L;
        }
    }

    protected void finalize() {
        super.finalize();
        long j = this.a;
        if (j != 0) {
            native_delete(j);
            this.a = 0L;
        }
    }
}
