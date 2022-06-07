package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.alibaba.fastjson.asm.Opcodes;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DERTaggedObject extends ASN1TaggedObject {
    private static final byte[] e = new byte[0];

    public DERTaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        super(z, i, aSN1Encodable);
    }

    public DERTaggedObject(int i, ASN1Encodable aSN1Encodable) {
        super(true, i, aSN1Encodable);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        if (this.b || this.c) {
            return true;
        }
        return this.d.toASN1Primitive().b().isConstructed();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        if (this.b) {
            return i.b(this.a) + 1;
        }
        int a = this.d.toASN1Primitive().b().a();
        if (this.c) {
            return i.b(this.a) + i.a(a) + a;
        }
        return i.b(this.a) + (a - 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1TaggedObject, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        boolean z = this.b;
        int i = Opcodes.IF_ICMPNE;
        if (!z) {
            ASN1Primitive b = this.d.toASN1Primitive().b();
            if (this.c) {
                aSN1OutputStream.a(Opcodes.IF_ICMPNE, this.a);
                aSN1OutputStream.a(b.a());
                aSN1OutputStream.writeObject(b);
                return;
            }
            if (!b.isConstructed()) {
                i = 128;
            }
            aSN1OutputStream.a(i, this.a);
            aSN1OutputStream.a(b);
            return;
        }
        aSN1OutputStream.a(Opcodes.IF_ICMPNE, this.a, e);
    }
}
