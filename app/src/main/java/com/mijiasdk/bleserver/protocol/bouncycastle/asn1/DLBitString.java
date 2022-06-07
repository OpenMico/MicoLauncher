package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes2.dex */
public class DLBitString extends ASN1BitString {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return false;
    }

    public static ASN1BitString getInstance(Object obj) {
        if (obj == null || (obj instanceof DLBitString)) {
            return (DLBitString) obj;
        }
        if (obj instanceof DERBitString) {
            return (DERBitString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1BitString) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1BitString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof DLBitString)) {
            return getInstance(object);
        }
        return a(((ASN1OctetString) object).getOctets());
    }

    protected DLBitString(byte b, int i) {
        this(a(b), i);
    }

    private static byte[] a(byte b) {
        return new byte[]{b};
    }

    public DLBitString(byte[] bArr, int i) {
        super(bArr, i);
    }

    public DLBitString(byte[] bArr) {
        this(bArr, 0);
    }

    public DLBitString(int i) {
        super(getBytes(i), getPadBits(i));
    }

    public DLBitString(ASN1Encodable aSN1Encodable) throws IOException {
        super(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER), 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() {
        return i.a(this.data.length + 1) + 1 + this.data.length + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1BitString, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        byte[] bArr = this.data;
        byte[] bArr2 = new byte[bArr.length + 1];
        bArr2[0] = (byte) getPadBits();
        System.arraycopy(bArr, 0, bArr2, 1, bArr2.length - 1);
        aSN1OutputStream.a(3, bArr2);
    }

    static DLBitString a(byte[] bArr) {
        if (bArr.length >= 1) {
            byte b = bArr[0];
            byte[] bArr2 = new byte[bArr.length - 1];
            if (bArr2.length != 0) {
                System.arraycopy(bArr, 1, bArr2, 0, bArr.length - 1);
            }
            return new DLBitString(bArr2, b);
        }
        throw new IllegalArgumentException("truncated BIT STRING detected");
    }
}
