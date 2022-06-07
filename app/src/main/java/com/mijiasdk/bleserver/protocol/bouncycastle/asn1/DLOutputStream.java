package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class DLOutputStream extends ASN1OutputStream {
    public DLOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OutputStream
    public void writeObject(ASN1Encodable aSN1Encodable) throws IOException {
        if (aSN1Encodable != null) {
            aSN1Encodable.toASN1Primitive().c().encode(this);
            return;
        }
        throw new IOException("null object detected");
    }
}
