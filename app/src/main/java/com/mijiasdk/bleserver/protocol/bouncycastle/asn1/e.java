package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IndefiniteLengthInputStream.java */
/* loaded from: classes2.dex */
public class e extends h {
    private int b;
    private int c;
    private boolean d = false;
    private boolean e = true;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(InputStream inputStream, int i) throws IOException {
        super(inputStream, i);
        this.b = inputStream.read();
        this.c = inputStream.read();
        if (this.c >= 0) {
            b();
            return;
        }
        throw new EOFException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.e = z;
        b();
    }

    private boolean b() {
        if (!this.d && this.e && this.b == 0 && this.c == 0) {
            this.d = true;
            b(true);
        }
        return this.d;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.e || i2 < 3) {
            return super.read(bArr, i, i2);
        }
        if (this.d) {
            return -1;
        }
        int read = this.a.read(bArr, i + 2, i2 - 2);
        if (read >= 0) {
            bArr[i] = (byte) this.b;
            bArr[i + 1] = (byte) this.c;
            this.b = this.a.read();
            this.c = this.a.read();
            if (this.c >= 0) {
                return read + 2;
            }
            throw new EOFException();
        }
        throw new EOFException();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (b()) {
            return -1;
        }
        int read = this.a.read();
        if (read >= 0) {
            int i = this.b;
            this.b = this.c;
            this.c = read;
            return i;
        }
        throw new EOFException();
    }
}
