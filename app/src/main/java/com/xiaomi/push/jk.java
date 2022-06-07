package com.xiaomi.push;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class jk extends jn {
    protected InputStream a;

    /* renamed from: a  reason: collision with other field name */
    protected OutputStream f184a;

    protected jk() {
        this.a = null;
        this.f184a = null;
    }

    public jk(OutputStream outputStream) {
        this.a = null;
        this.f184a = null;
        this.f184a = outputStream;
    }

    @Override // com.xiaomi.push.jn
    /* renamed from: a */
    public int mo1109a(byte[] bArr, int i, int i2) {
        InputStream inputStream = this.a;
        if (inputStream != null) {
            try {
                int read = inputStream.read(bArr, i, i2);
                if (read >= 0) {
                    return read;
                }
                throw new jo(4);
            } catch (IOException e) {
                throw new jo(0, e);
            }
        } else {
            throw new jo(1, "Cannot read from null inputStream");
        }
    }

    @Override // com.xiaomi.push.jn
    /* renamed from: a  reason: collision with other method in class */
    public void mo1109a(byte[] bArr, int i, int i2) {
        OutputStream outputStream = this.f184a;
        if (outputStream != null) {
            try {
                outputStream.write(bArr, i, i2);
            } catch (IOException e) {
                throw new jo(0, e);
            }
        } else {
            throw new jo(1, "Cannot write to null outputStream");
        }
    }
}
