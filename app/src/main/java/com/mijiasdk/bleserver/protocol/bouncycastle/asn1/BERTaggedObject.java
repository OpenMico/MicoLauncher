package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.alibaba.fastjson.asm.Opcodes;
import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class BERTaggedObject extends ASN1TaggedObject {
    public BERTaggedObject(int i, ASN1Encodable aSN1Encodable) {
        super(true, i, aSN1Encodable);
    }

    public BERTaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        super(z, i, aSN1Encodable);
    }

    public BERTaggedObject(int i) {
        super(false, i, new BERSequence());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        if (this.b || this.c) {
            return true;
        }
        return this.d.toASN1Primitive().b().isConstructed();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        if (this.b) {
            return i.b(this.a) + 1;
        }
        int a = this.d.toASN1Primitive().a();
        if (this.c) {
            return i.b(this.a) + i.a(a) + a;
        }
        return i.b(this.a) + (a - 1);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1TaggedObject, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        Enumeration enumeration;
        aSN1OutputStream.a(Opcodes.IF_ICMPNE, this.a);
        aSN1OutputStream.b(128);
        if (!this.b) {
            if (!this.c) {
                if (this.d instanceof ASN1OctetString) {
                    if (this.d instanceof BEROctetString) {
                        enumeration = ((BEROctetString) this.d).getObjects();
                    } else {
                        enumeration = new BEROctetString(((ASN1OctetString) this.d).getOctets()).getObjects();
                    }
                } else if (this.d instanceof ASN1Sequence) {
                    enumeration = ((ASN1Sequence) this.d).getObjects();
                } else if (this.d instanceof ASN1Set) {
                    enumeration = ((ASN1Set) this.d).getObjects();
                } else {
                    throw new ASN1Exception("not implemented: " + this.d.getClass().getName());
                }
                while (enumeration.hasMoreElements()) {
                    aSN1OutputStream.writeObject((ASN1Encodable) enumeration.nextElement());
                }
            } else {
                aSN1OutputStream.writeObject(this.d);
            }
        }
        aSN1OutputStream.b(0);
        aSN1OutputStream.b(0);
    }
}
