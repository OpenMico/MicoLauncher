package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes2.dex */
public class DERSetParser implements ASN1SetParser {
    private ASN1StreamParser a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DERSetParser(ASN1StreamParser aSN1StreamParser) {
        this.a = aSN1StreamParser;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1SetParser
    public ASN1Encodable readObject() throws IOException {
        return this.a.readObject();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() throws IOException {
        return new DERSet(this.a.a(), false);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage(), e);
        }
    }
}
