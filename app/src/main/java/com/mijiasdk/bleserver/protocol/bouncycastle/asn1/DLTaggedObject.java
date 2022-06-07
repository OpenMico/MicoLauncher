package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.alibaba.fastjson.asm.Opcodes;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DLTaggedObject extends ASN1TaggedObject {
    private static final byte[] e = new byte[0];

    public DLTaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        super(z, i, aSN1Encodable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        if (this.b || this.c) {
            return true;
        }
        return this.d.toASN1Primitive().c().isConstructed();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        if (this.b) {
            return i.b(this.a) + 1;
        }
        int a = this.d.toASN1Primitive().c().a();
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
            ASN1Primitive c = this.d.toASN1Primitive().c();
            if (this.c) {
                aSN1OutputStream.a(Opcodes.IF_ICMPNE, this.a);
                aSN1OutputStream.a(c.a());
                aSN1OutputStream.writeObject(c);
                return;
            }
            if (!c.isConstructed()) {
                i = 128;
            }
            aSN1OutputStream.a(i, this.a);
            aSN1OutputStream.a(c);
            return;
        }
        aSN1OutputStream.a(Opcodes.IF_ICMPNE, this.a, e);
    }
}
