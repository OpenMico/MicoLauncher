package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class ASN1Primitive extends ASN1Object {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int a() throws IOException;

    abstract boolean asn1Equals(ASN1Primitive aSN1Primitive);

    public ASN1Primitive b() {
        return this;
    }

    public ASN1Primitive c() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void encode(ASN1OutputStream aSN1OutputStream) throws IOException;

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public abstract int hashCode();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean isConstructed();

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this;
    }

    public static ASN1Primitive fromByteArray(byte[] bArr) throws IOException {
        ASN1InputStream aSN1InputStream = new ASN1InputStream(bArr);
        try {
            ASN1Primitive readObject = aSN1InputStream.readObject();
            if (aSN1InputStream.available() == 0) {
                return readObject;
            }
            throw new IOException("Extra data detected in stream");
        } catch (ClassCastException unused) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ASN1Encodable) && asn1Equals(((ASN1Encodable) obj).toASN1Primitive());
    }
}
