package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Strings;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DERNumericString extends ASN1Primitive implements ASN1String {
    private final byte[] a;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return false;
    }

    public static DERNumericString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERNumericString)) {
            return (DERNumericString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (DERNumericString) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static DERNumericString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof DERNumericString)) {
            return getInstance(object);
        }
        return new DERNumericString(ASN1OctetString.getInstance(object).getOctets());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DERNumericString(byte[] bArr) {
        this.a = bArr;
    }

    public DERNumericString(String str) {
        this(str, false);
    }

    public DERNumericString(String str, boolean z) {
        if (!z || isNumericString(str)) {
            this.a = Strings.toByteArray(str);
            return;
        }
        throw new IllegalArgumentException("string contains illegal characters");
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1String
    public String getString() {
        return Strings.fromByteArray(this.a);
    }

    public String toString() {
        return getString();
    }

    public byte[] getOctets() {
        return Arrays.clone(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() {
        return i.a(this.a.length) + 1 + this.a.length;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.a(18, this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERNumericString)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((DERNumericString) aSN1Primitive).a);
    }

    public static boolean isNumericString(String str) {
        for (int length = str.length() - 1; length >= 0; length--) {
            char charAt = str.charAt(length);
            if (charAt > 127) {
                return false;
            }
            if (('0' > charAt || charAt > '9') && charAt != ' ') {
                return false;
            }
        }
        return true;
    }
}
