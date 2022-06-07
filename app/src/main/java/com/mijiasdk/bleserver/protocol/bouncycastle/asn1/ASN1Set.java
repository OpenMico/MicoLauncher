package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Iterable;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/* loaded from: classes2.dex */
public abstract class ASN1Set extends ASN1Primitive implements Iterable<ASN1Encodable> {
    private Vector a = new Vector();
    private boolean b = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public abstract void encode(ASN1OutputStream aSN1OutputStream) throws IOException;

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return true;
    }

    public static ASN1Set getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Set)) {
            return (ASN1Set) obj;
        }
        if (obj instanceof ASN1SetParser) {
            return getInstance(((ASN1SetParser) obj).toASN1Primitive());
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct set from byte[]: " + e.getMessage());
            }
        } else {
            if (obj instanceof ASN1Encodable) {
                ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
                if (aSN1Primitive instanceof ASN1Set) {
                    return (ASN1Set) aSN1Primitive;
                }
            }
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1Set getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            if (aSN1TaggedObject.isExplicit()) {
                return (ASN1Set) aSN1TaggedObject.getObject();
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        } else if (aSN1TaggedObject.isExplicit()) {
            if (aSN1TaggedObject instanceof BERTaggedObject) {
                return new BERSet(aSN1TaggedObject.getObject());
            }
            return new DLSet(aSN1TaggedObject.getObject());
        } else if (aSN1TaggedObject.getObject() instanceof ASN1Set) {
            return (ASN1Set) aSN1TaggedObject.getObject();
        } else {
            if (aSN1TaggedObject.getObject() instanceof ASN1Sequence) {
                ASN1Sequence aSN1Sequence = (ASN1Sequence) aSN1TaggedObject.getObject();
                if (aSN1TaggedObject instanceof BERTaggedObject) {
                    return new BERSet(aSN1Sequence.toArray());
                }
                return new DLSet(aSN1Sequence.toArray());
            }
            throw new IllegalArgumentException("unknown object in getInstance: " + aSN1TaggedObject.getClass().getName());
        }
    }

    public ASN1Set() {
    }

    public ASN1Set(ASN1Encodable aSN1Encodable) {
        this.a.addElement(aSN1Encodable);
    }

    public ASN1Set(ASN1EncodableVector aSN1EncodableVector, boolean z) {
        for (int i = 0; i != aSN1EncodableVector.size(); i++) {
            this.a.addElement(aSN1EncodableVector.get(i));
        }
        if (z) {
            sort();
        }
    }

    public ASN1Set(ASN1Encodable[] aSN1EncodableArr, boolean z) {
        for (int i = 0; i != aSN1EncodableArr.length; i++) {
            this.a.addElement(aSN1EncodableArr[i]);
        }
        if (z) {
            sort();
        }
    }

    public Enumeration getObjects() {
        return this.a.elements();
    }

    public ASN1Encodable getObjectAt(int i) {
        return (ASN1Encodable) this.a.elementAt(i);
    }

    public int size() {
        return this.a.size();
    }

    public ASN1Encodable[] toArray() {
        ASN1Encodable[] aSN1EncodableArr = new ASN1Encodable[size()];
        for (int i = 0; i != size(); i++) {
            aSN1EncodableArr[i] = getObjectAt(i);
        }
        return aSN1EncodableArr;
    }

    public ASN1SetParser parser() {
        return new ASN1SetParser() { // from class: com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Set.1
            private final int c;
            private int d;

            {
                ASN1Set.this = this;
                this.c = ASN1Set.this.size();
            }

            @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1SetParser
            public ASN1Encodable readObject() throws IOException {
                int i = this.d;
                if (i == this.c) {
                    return null;
                }
                ASN1Set aSN1Set = ASN1Set.this;
                this.d = i + 1;
                ASN1Encodable objectAt = aSN1Set.getObjectAt(i);
                if (objectAt instanceof ASN1Sequence) {
                    return ((ASN1Sequence) objectAt).parser();
                }
                return objectAt instanceof ASN1Set ? ((ASN1Set) objectAt).parser() : objectAt;
            }

            @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.InMemoryRepresentable
            public ASN1Primitive getLoadedObject() {
                return this;
            }

            @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable
            public ASN1Primitive toASN1Primitive() {
                return this;
            }
        };
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        Enumeration objects = getObjects();
        int size = size();
        while (objects.hasMoreElements()) {
            size = (size * 17) ^ a(objects).hashCode();
        }
        return size;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive b() {
        if (this.b) {
            DERSet dERSet = new DERSet();
            ((ASN1Set) dERSet).a = this.a;
            return dERSet;
        }
        Vector vector = new Vector();
        for (int i = 0; i != this.a.size(); i++) {
            vector.addElement(this.a.elementAt(i));
        }
        DERSet dERSet2 = new DERSet();
        ((ASN1Set) dERSet2).a = vector;
        dERSet2.sort();
        return dERSet2;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive c() {
        DLSet dLSet = new DLSet();
        ((ASN1Set) dLSet).a = this.a;
        return dLSet;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1Set)) {
            return false;
        }
        ASN1Set aSN1Set = (ASN1Set) aSN1Primitive;
        if (size() != aSN1Set.size()) {
            return false;
        }
        Enumeration objects = getObjects();
        Enumeration objects2 = aSN1Set.getObjects();
        while (objects.hasMoreElements()) {
            ASN1Encodable a = a(objects);
            ASN1Encodable a2 = a(objects2);
            ASN1Primitive aSN1Primitive2 = a.toASN1Primitive();
            ASN1Primitive aSN1Primitive3 = a2.toASN1Primitive();
            if (aSN1Primitive2 != aSN1Primitive3 && !aSN1Primitive2.equals(aSN1Primitive3)) {
                return false;
            }
        }
        return true;
    }

    private ASN1Encodable a(Enumeration enumeration) {
        ASN1Encodable aSN1Encodable = (ASN1Encodable) enumeration.nextElement();
        return aSN1Encodable == null ? DERNull.INSTANCE : aSN1Encodable;
    }

    private boolean a(byte[] bArr, byte[] bArr2) {
        int min = Math.min(bArr.length, bArr2.length);
        for (int i = 0; i != min; i++) {
            if (bArr[i] != bArr2[i]) {
                return (bArr[i] & 255) < (bArr2[i] & 255);
            }
        }
        return min == bArr.length;
    }

    private byte[] a(ASN1Encodable aSN1Encodable) {
        try {
            return aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }

    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:56)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:30)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:18)
        */
    protected void sort() {
        /*
            r9 = this;
            boolean r0 = r9.b
            if (r0 != 0) goto L_0x005d
            r0 = 1
            r9.b = r0
            java.util.Vector r1 = r9.a
            int r1 = r1.size()
            if (r1 <= r0) goto L_0x005d
            java.util.Vector r1 = r9.a
            int r1 = r1.size()
            int r1 = r1 - r0
            r2 = r1
            r1 = r0
        L_0x0018:
            if (r1 == 0) goto L_0x005d
            java.util.Vector r1 = r9.a
            r3 = 0
            java.lang.Object r1 = r1.elementAt(r3)
            com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable r1 = (com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable) r1
            byte[] r1 = r9.a(r1)
            r5 = r1
            r1 = r3
            r4 = r1
        L_0x002a:
            if (r3 == r2) goto L_0x005a
            java.util.Vector r6 = r9.a
            int r7 = r3 + 1
            java.lang.Object r6 = r6.elementAt(r7)
            com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable r6 = (com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Encodable) r6
            byte[] r6 = r9.a(r6)
            boolean r8 = r9.a(r5, r6)
            if (r8 == 0) goto L_0x0042
            r5 = r6
            goto L_0x0058
        L_0x0042:
            java.util.Vector r1 = r9.a
            java.lang.Object r1 = r1.elementAt(r3)
            java.util.Vector r4 = r9.a
            java.lang.Object r6 = r4.elementAt(r7)
            r4.setElementAt(r6, r3)
            java.util.Vector r4 = r9.a
            r4.setElementAt(r1, r7)
            r4 = r0
            r1 = r3
        L_0x0058:
            r3 = r7
            goto L_0x002a
        L_0x005a:
            r2 = r1
            r1 = r4
            goto L_0x0018
        L_0x005d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Set.sort():void");
    }

    public String toString() {
        return this.a.toString();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.util.Iterable, java.lang.Iterable
    public Iterator<ASN1Encodable> iterator() {
        return new Arrays.Iterator(toArray());
    }
}
