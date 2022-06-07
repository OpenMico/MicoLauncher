package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Strings;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DERT61String extends ASN1Primitive implements ASN1String {
    private byte[] a;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return false;
    }

    public static DERT61String getInstance(Object obj) {
        if (obj == null || (obj instanceof DERT61String)) {
            return (DERT61String) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (DERT61String) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static DERT61String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof DERT61String)) {
            return getInstance(object);
        }
        return new DERT61String(ASN1OctetString.getInstance(object).getOctets());
    }

    public DERT61String(byte[] bArr) {
        this.a = Arrays.clone(bArr);
    }

    public DERT61String(String str) {
        this.a = Strings.toByteArray(str);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1String
    public String getString() {
        return Strings.fromByteArray(this.a);
    }

    public String toString() {
        return getString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() {
        return i.a(this.a.length) + 1 + this.a.length;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.a(20, this.a);
    }

    public byte[] getOctets() {
        return Arrays.clone(this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERT61String)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((DERT61String) aSN1Primitive).a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(this.a);
    }
}
