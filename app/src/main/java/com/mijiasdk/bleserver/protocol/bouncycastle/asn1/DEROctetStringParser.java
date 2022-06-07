package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class DEROctetStringParser implements ASN1OctetStringParser {
    private d a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DEROctetStringParser(d dVar) {
        this.a = dVar;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OctetStringParser
    public InputStream getOctetStream() {
        return this.a;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() throws IOException {
        return new DEROctetString(this.a.b());
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }
}
