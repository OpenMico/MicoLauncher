package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes2.dex */
public class BERTaggedObjectParser implements ASN1TaggedObjectParser {
    private boolean a;
    private int b;
    private ASN1StreamParser c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BERTaggedObjectParser(boolean z, int i, ASN1StreamParser aSN1StreamParser) {
        this.a = z;
        this.b = i;
        this.c = aSN1StreamParser;
    }

    public boolean isConstructed() {
        return this.a;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this.b;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable getObjectParser(int i, boolean z) throws IOException {
        if (!z) {
            return this.c.a(this.a, i);
        }
        if (this.a) {
            return this.c.readObject();
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() throws IOException {
        return this.c.b(this.a, this.b);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage());
        }
    }
}
