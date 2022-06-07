package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.io.Streams;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class BEROctetStringParser implements ASN1OctetStringParser {
    private ASN1StreamParser a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BEROctetStringParser(ASN1StreamParser aSN1StreamParser) {
        this.a = aSN1StreamParser;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OctetStringParser
    public InputStream getOctetStream() {
        return new b(this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() throws IOException {
        return new BEROctetString(Streams.readAll(getOctetStream()));
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
