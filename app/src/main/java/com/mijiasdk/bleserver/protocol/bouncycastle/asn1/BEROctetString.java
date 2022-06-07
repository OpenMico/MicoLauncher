package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/* loaded from: classes2.dex */
public class BEROctetString extends ASN1OctetString {
    private ASN1OctetString[] b;

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return true;
    }

    private static byte[] a(ASN1OctetString[] aSN1OctetStringArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i != aSN1OctetStringArr.length; i++) {
            try {
                byteArrayOutputStream.write(((DEROctetString) aSN1OctetStringArr[i]).getOctets());
            } catch (IOException e) {
                throw new IllegalArgumentException("exception converting octets " + e.toString());
            } catch (ClassCastException unused) {
                throw new IllegalArgumentException(aSN1OctetStringArr[i].getClass().getName() + " found in input should only contain DEROctetString");
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public BEROctetString(byte[] bArr) {
        super(bArr);
    }

    public BEROctetString(ASN1OctetString[] aSN1OctetStringArr) {
        super(a(aSN1OctetStringArr));
        this.b = aSN1OctetStringArr;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OctetString
    public byte[] getOctets() {
        return this.a;
    }

    public Enumeration getObjects() {
        if (this.b == null) {
            return d().elements();
        }
        return new Enumeration() { // from class: com.mijiasdk.bleserver.protocol.bouncycastle.asn1.BEROctetString.1
            int a = 0;

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.a < BEROctetString.this.b.length;
            }

            @Override // java.util.Enumeration
            public Object nextElement() {
                ASN1OctetString[] aSN1OctetStringArr = BEROctetString.this.b;
                int i = this.a;
                this.a = i + 1;
                return aSN1OctetStringArr[i];
            }
        };
    }

    private Vector d() {
        Vector vector = new Vector();
        int i = 0;
        while (i < this.a.length) {
            int i2 = i + 1000;
            byte[] bArr = new byte[(i2 > this.a.length ? this.a.length : i2) - i];
            System.arraycopy(this.a, i, bArr, 0, bArr.length);
            vector.addElement(new DEROctetString(bArr));
            i = i2;
        }
        return vector;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        Enumeration objects = getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            i += ((ASN1Encodable) objects.nextElement()).toASN1Primitive().a();
        }
        return i + 2 + 2;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OctetString, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.b(36);
        aSN1OutputStream.b(128);
        Enumeration objects = getObjects();
        while (objects.hasMoreElements()) {
            aSN1OutputStream.writeObject((ASN1Encodable) objects.nextElement());
        }
        aSN1OutputStream.b(0);
        aSN1OutputStream.b(0);
    }

    public static BEROctetString a(ASN1Sequence aSN1Sequence) {
        ASN1OctetString[] aSN1OctetStringArr = new ASN1OctetString[aSN1Sequence.size()];
        Enumeration objects = aSN1Sequence.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            i++;
            aSN1OctetStringArr[i] = (ASN1OctetString) objects.nextElement();
        }
        return new BEROctetString(aSN1OctetStringArr);
    }
}
