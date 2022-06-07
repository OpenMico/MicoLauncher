package com.xiaomi.ai.android.codec;

import com.alibaba.fastjson.asm.Opcodes;
import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;

/* loaded from: classes2.dex */
public class AudioEncoder {
    private d a;
    private String b;
    private long c;
    private byte[] d;

    public AudioEncoder(Engine engine) {
        this.a = (d) engine;
    }

    private native void native_delete(long j);

    private native int native_encode(long j, byte[] bArr, byte[] bArr2, boolean z);

    private native int native_init(long j);

    private native long native_new();

    private native int native_setEncoderMode(long j, int i);

    private native int native_setOpusBitrate(long j, int i);

    public int a(byte[] bArr, boolean z) {
        if (this.b.equals(AivsConfig.Asr.CODEC_BV32_FLOAT)) {
            this.d = new byte[(((bArr.length - 1) / Opcodes.IF_ICMPNE) + 1) * 20];
        }
        return native_encode(this.c, bArr, this.d, z);
    }

    public boolean a() {
        long j;
        c();
        AivsConfig b = this.a.b();
        this.b = b.getString(AivsConfig.Asr.CODEC, AivsConfig.Asr.CODEC_PCM);
        if (AivsConfig.Asr.CODEC_PCM.equals(this.b)) {
            Logger.d("AudioEncoder", "init: no need encode pcm");
            return false;
        }
        this.c = native_new();
        if (this.c == 0) {
            Logger.d("AudioEncoder", "init: failed to new native instance");
            return false;
        }
        if (this.b.equals(AivsConfig.Asr.CODEC_BV32_FLOAT)) {
            native_setEncoderMode(this.c, 1);
        } else if (this.b.equals(AivsConfig.Asr.CODEC_OPUS)) {
            native_setEncoderMode(this.c, 2);
            int i = 32000;
            if (32000 == b.getInt(AivsConfig.Asr.OPUS_BITRATE, 32000)) {
                j = this.c;
            } else {
                j = this.c;
                i = AivsConfig.Asr.OPUS_BITRATE_64K;
            }
            native_setOpusBitrate(j, i);
            this.d = new byte[4096];
        }
        native_init(this.c);
        return true;
    }

    public byte[] b() {
        return this.d;
    }

    public void c() {
        long j = this.c;
        if (j != 0) {
            native_delete(j);
            this.c = 0L;
        }
    }

    protected void finalize() {
        super.finalize();
        long j = this.c;
        if (j != 0) {
            native_delete(j);
            this.c = 0L;
        }
    }
}
