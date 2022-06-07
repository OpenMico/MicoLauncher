package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class ASN1OutputStream {
    private OutputStream a;

    public ASN1OutputStream(OutputStream outputStream) {
        this.a = outputStream;
    }

    public void a(int i) throws IOException {
        if (i > 127) {
            int i2 = i;
            int i3 = 1;
            while (true) {
                i2 >>>= 8;
                if (i2 == 0) {
                    break;
                }
                i3++;
            }
            b((byte) (i3 | 128));
            for (int i4 = (i3 - 1) * 8; i4 >= 0; i4 -= 8) {
                b((byte) (i >> i4));
            }
            return;
        }
        b((byte) i);
    }

    public void b(int i) throws IOException {
        this.a.write(i);
    }

    public void a(byte[] bArr) throws IOException {
        this.a.write(bArr);
    }

    void a(byte[] bArr, int i, int i2) throws IOException {
        this.a.write(bArr, i, i2);
    }

    public void a(int i, byte[] bArr) throws IOException {
        b(i);
        a(bArr.length);
        a(bArr);
    }

    public void a(int i, int i2) throws IOException {
        if (i2 < 31) {
            b(i | i2);
            return;
        }
        b(i | 31);
        if (i2 < 128) {
            b(i2);
            return;
        }
        byte[] bArr = new byte[5];
        int length = bArr.length - 1;
        bArr[length] = (byte) (i2 & 127);
        do {
            i2 >>= 7;
            length--;
            bArr[length] = (byte) ((i2 & 127) | 128);
        } while (i2 > 127);
        a(bArr, length, bArr.length - length);
    }

    public void a(int i, int i2, byte[] bArr) throws IOException {
        a(i, i2);
        a(bArr.length);
        a(bArr);
    }

    protected void writeNull() throws IOException {
        this.a.write(5);
        this.a.write(0);
    }

    public void writeObject(ASN1Encodable aSN1Encodable) throws IOException {
        if (aSN1Encodable != null) {
            aSN1Encodable.toASN1Primitive().encode(this);
            return;
        }
        throw new IOException("null object detected");
    }

    public void a(ASN1Primitive aSN1Primitive) throws IOException {
        if (aSN1Primitive != null) {
            aSN1Primitive.encode(new a(this.a));
            return;
        }
        throw new IOException("null object detected");
    }

    public void close() throws IOException {
        this.a.close();
    }

    public void flush() throws IOException {
        this.a.flush();
    }

    public ASN1OutputStream a() {
        return new DEROutputStream(this.a);
    }

    public ASN1OutputStream b() {
        return new DLOutputStream(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a extends ASN1OutputStream {
        private boolean b = true;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(OutputStream outputStream) {
            super(outputStream);
            ASN1OutputStream.this = r1;
        }

        @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OutputStream
        public void b(int i) throws IOException {
            if (this.b) {
                this.b = false;
            } else {
                ASN1OutputStream.super.b(i);
            }
        }
    }
}
