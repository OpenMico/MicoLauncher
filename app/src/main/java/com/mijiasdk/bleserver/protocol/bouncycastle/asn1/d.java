package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.io.Streams;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DefiniteLengthInputStream.java */
/* loaded from: classes2.dex */
public class d extends h {
    private static final byte[] b = new byte[0];
    private final int c;
    private int d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(InputStream inputStream, int i) {
        super(inputStream, i);
        if (i >= 0) {
            this.c = i;
            this.d = i;
            if (i == 0) {
                b(true);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("negative lengths not allowed");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.h
    public int a() {
        return this.d;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.d == 0) {
            return -1;
        }
        int read = this.a.read();
        if (read >= 0) {
            int i = this.d - 1;
            this.d = i;
            if (i == 0) {
                b(true);
            }
            return read;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.d;
        if (i3 == 0) {
            return -1;
        }
        int read = this.a.read(bArr, i, Math.min(i2, i3));
        if (read >= 0) {
            int i4 = this.d - read;
            this.d = i4;
            if (i4 == 0) {
                b(true);
            }
            return read;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte[] b() throws IOException {
        int i = this.d;
        if (i == 0) {
            return b;
        }
        byte[] bArr = new byte[i];
        int readFully = i - Streams.readFully(this.a, bArr);
        this.d = readFully;
        if (readFully == 0) {
            b(true);
            return bArr;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }
}
