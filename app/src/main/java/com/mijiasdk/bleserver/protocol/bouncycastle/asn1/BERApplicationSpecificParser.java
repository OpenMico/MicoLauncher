package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes2.dex */
public class BERApplicationSpecificParser implements ASN1ApplicationSpecificParser {
    private final int a;
    private final ASN1StreamParser b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BERApplicationSpecificParser(int i, ASN1StreamParser aSN1StreamParser) {
        this.a = i;
        this.b = aSN1StreamParser;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1ApplicationSpecificParser
    public ASN1Encodable readObject() throws IOException {
        return this.b.readObject();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() throws IOException {
        return new BERApplicationSpecific(this.a, this.b.a());
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
