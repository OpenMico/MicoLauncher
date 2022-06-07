package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class DEROutputStream extends ASN1OutputStream {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OutputStream
    public ASN1OutputStream a() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OutputStream
    public ASN1OutputStream b() {
        return this;
    }

    public DEROutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OutputStream
    public void writeObject(ASN1Encodable aSN1Encodable) throws IOException {
        if (aSN1Encodable != null) {
            aSN1Encodable.toASN1Primitive().b().encode(this);
            return;
        }
        throw new IOException("null object detected");
    }
}
