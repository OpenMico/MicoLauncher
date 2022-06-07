package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DERApplicationSpecific extends ASN1ApplicationSpecific {
    /* JADX INFO: Access modifiers changed from: package-private */
    public DERApplicationSpecific(boolean z, int i, byte[] bArr) {
        super(z, i, bArr);
    }

    public DERApplicationSpecific(int i, byte[] bArr) {
        this(false, i, bArr);
    }

    public DERApplicationSpecific(int i, ASN1Encodable aSN1Encodable) throws IOException {
        this(true, i, aSN1Encodable);
    }

    public DERApplicationSpecific(boolean z, int i, ASN1Encodable aSN1Encodable) throws IOException {
        super(z || aSN1Encodable.toASN1Primitive().isConstructed(), i, a(z, aSN1Encodable));
    }

    private static byte[] a(boolean z, ASN1Encodable aSN1Encodable) throws IOException {
        byte[] encoded = aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER);
        if (z) {
            return encoded;
        }
        int lengthOfHeader = getLengthOfHeader(encoded);
        byte[] bArr = new byte[encoded.length - lengthOfHeader];
        System.arraycopy(encoded, lengthOfHeader, bArr, 0, bArr.length);
        return bArr;
    }

    public DERApplicationSpecific(int i, ASN1EncodableVector aSN1EncodableVector) {
        super(true, i, a(aSN1EncodableVector));
    }

    private static byte[] a(ASN1EncodableVector aSN1EncodableVector) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i != aSN1EncodableVector.size(); i++) {
            try {
                byteArrayOutputStream.write(((ASN1Object) aSN1EncodableVector.get(i)).getEncoded(ASN1Encoding.DER));
            } catch (IOException e) {
                throw new ASN1ParsingException("malformed object: " + e, e);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1ApplicationSpecific, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.a(this.isConstructed ? 96 : 64, this.tag, this.octets);
    }
}
