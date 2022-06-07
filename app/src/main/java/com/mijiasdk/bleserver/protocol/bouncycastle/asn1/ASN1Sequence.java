package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Iterable;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/* loaded from: classes2.dex */
public abstract class ASN1Sequence extends ASN1Primitive implements Iterable<ASN1Encodable> {
    protected Vector seq = new Vector();

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public abstract void encode(ASN1OutputStream aSN1OutputStream) throws IOException;

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return true;
    }

    public static ASN1Sequence getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Sequence)) {
            return (ASN1Sequence) obj;
        }
        if (obj instanceof ASN1SequenceParser) {
            return getInstance(((ASN1SequenceParser) obj).toASN1Primitive());
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct sequence from byte[]: " + e.getMessage());
            }
        } else {
            if (obj instanceof ASN1Encodable) {
                ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
                if (aSN1Primitive instanceof ASN1Sequence) {
                    return (ASN1Sequence) aSN1Primitive;
                }
            }
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1Sequence getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            if (aSN1TaggedObject.isExplicit()) {
                return getInstance(aSN1TaggedObject.getObject().toASN1Primitive());
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        } else if (aSN1TaggedObject.isExplicit()) {
            if (aSN1TaggedObject instanceof BERTaggedObject) {
                return new BERSequence(aSN1TaggedObject.getObject());
            }
            return new DLSequence(aSN1TaggedObject.getObject());
        } else if (aSN1TaggedObject.getObject() instanceof ASN1Sequence) {
            return (ASN1Sequence) aSN1TaggedObject.getObject();
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + aSN1TaggedObject.getClass().getName());
        }
    }

    public ASN1Sequence() {
    }

    public ASN1Sequence(ASN1Encodable aSN1Encodable) {
        this.seq.addElement(aSN1Encodable);
    }

    public ASN1Sequence(ASN1EncodableVector aSN1EncodableVector) {
        for (int i = 0; i != aSN1EncodableVector.size(); i++) {
            this.seq.addElement(aSN1EncodableVector.get(i));
        }
    }

    public ASN1Sequence(ASN1Encodable[] aSN1EncodableArr) {
        for (int i = 0; i != aSN1EncodableArr.length; i++) {
            this.seq.addElement(aSN1EncodableArr[i]);
        }
    }

    public ASN1Encodable[] toArray() {
        ASN1Encodable[] aSN1EncodableArr = new ASN1Encodable[size()];
        for (int i = 0; i != size(); i++) {
            aSN1EncodableArr[i] = getObjectAt(i);
        }
        return aSN1EncodableArr;
    }

    public Enumeration getObjects() {
        return this.seq.elements();
    }

    public ASN1SequenceParser parser() {
        return new ASN1SequenceParser() { // from class: com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Sequence.1
            private final int c;
            private int d;

            {
                ASN1Sequence.this = this;
                this.c = ASN1Sequence.this.size();
            }

            @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1SequenceParser
            public ASN1Encodable readObject() throws IOException {
                int i = this.d;
                if (i == this.c) {
                    return null;
                }
                ASN1Sequence aSN1Sequence = ASN1Sequence.this;
                this.d = i + 1;
                ASN1Encodable objectAt = aSN1Sequence.getObjectAt(i);
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

    public ASN1Encodable getObjectAt(int i) {
        return (ASN1Encodable) this.seq.elementAt(i);
    }

    public int size() {
        return this.seq.size();
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
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1Sequence)) {
            return false;
        }
        ASN1Sequence aSN1Sequence = (ASN1Sequence) aSN1Primitive;
        if (size() != aSN1Sequence.size()) {
            return false;
        }
        Enumeration objects = getObjects();
        Enumeration objects2 = aSN1Sequence.getObjects();
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
        return (ASN1Encodable) enumeration.nextElement();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive b() {
        DERSequence dERSequence = new DERSequence();
        dERSequence.seq = this.seq;
        return dERSequence;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive c() {
        DLSequence dLSequence = new DLSequence();
        dLSequence.seq = this.seq;
        return dLSequence;
    }

    public String toString() {
        return this.seq.toString();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.util.Iterable, java.lang.Iterable
    public Iterator<ASN1Encodable> iterator() {
        return new Arrays.Iterator(toArray());
    }
}
