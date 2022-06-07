package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.io.Streams;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class ASN1InputStream extends FilterInputStream implements BERTags {
    private final int a;
    private final boolean b;
    private final byte[][] c;

    public ASN1InputStream(InputStream inputStream) {
        this(inputStream, i.a(inputStream));
    }

    public ASN1InputStream(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    public ASN1InputStream(byte[] bArr, boolean z) {
        this(new ByteArrayInputStream(bArr), bArr.length, z);
    }

    public ASN1InputStream(InputStream inputStream, int i) {
        this(inputStream, i, false);
    }

    public ASN1InputStream(InputStream inputStream, boolean z) {
        this(inputStream, i.a(inputStream), z);
    }

    public ASN1InputStream(InputStream inputStream, int i, boolean z) {
        super(inputStream);
        this.a = i;
        this.b = z;
        this.c = new byte[11];
    }

    public int a() {
        return this.a;
    }

    protected int readLength() throws IOException {
        return b(this, this.a);
    }

    protected void readFully(byte[] bArr) throws IOException {
        if (Streams.readFully(this, bArr) != bArr.length) {
            throw new EOFException("EOF encountered in middle of object");
        }
    }

    protected ASN1Primitive buildObject(int i, int i2, int i3) throws IOException {
        boolean z = (i & 32) != 0;
        d dVar = new d(this, i3);
        if ((i & 64) != 0) {
            return new DERApplicationSpecific(z, i2, dVar.b());
        }
        if ((i & 128) != 0) {
            return new ASN1StreamParser(dVar).b(z, i2);
        }
        if (!z) {
            return a(i2, dVar, this.c);
        }
        if (i2 == 4) {
            ASN1EncodableVector a = a(dVar);
            ASN1OctetString[] aSN1OctetStringArr = new ASN1OctetString[a.size()];
            for (int i4 = 0; i4 != aSN1OctetStringArr.length; i4++) {
                aSN1OctetStringArr[i4] = (ASN1OctetString) a.get(i4);
            }
            return new BEROctetString(aSN1OctetStringArr);
        } else if (i2 == 8) {
            return new DERExternal(a(dVar));
        } else {
            switch (i2) {
                case 16:
                    if (this.b) {
                        return new g(dVar.b());
                    }
                    return c.a(a(dVar));
                case 17:
                    return c.b(a(dVar));
                default:
                    throw new IOException("unknown tag " + i2 + " encountered");
            }
        }
    }

    ASN1EncodableVector b() throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while (true) {
            ASN1Primitive readObject = readObject();
            if (readObject == null) {
                return aSN1EncodableVector;
            }
            aSN1EncodableVector.add(readObject);
        }
    }

    ASN1EncodableVector a(d dVar) throws IOException {
        return new ASN1InputStream(dVar).b();
    }

    public ASN1Primitive readObject() throws IOException {
        int read = read();
        if (read > 0) {
            int a = a(this, read);
            boolean z = (read & 32) != 0;
            int readLength = readLength();
            if (readLength >= 0) {
                try {
                    return buildObject(read, a, readLength);
                } catch (IllegalArgumentException e) {
                    throw new ASN1Exception("corrupted stream detected", e);
                }
            } else if (z) {
                ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new e(this, this.a), this.a);
                if ((read & 64) != 0) {
                    return new BERApplicationSpecificParser(a, aSN1StreamParser).getLoadedObject();
                }
                if ((read & 128) != 0) {
                    return new BERTaggedObjectParser(true, a, aSN1StreamParser).getLoadedObject();
                }
                if (a == 4) {
                    return new BEROctetStringParser(aSN1StreamParser).getLoadedObject();
                }
                if (a == 8) {
                    return new DERExternalParser(aSN1StreamParser).getLoadedObject();
                }
                switch (a) {
                    case 16:
                        return new BERSequenceParser(aSN1StreamParser).getLoadedObject();
                    case 17:
                        return new BERSetParser(aSN1StreamParser).getLoadedObject();
                    default:
                        throw new IOException("unknown BER object encountered");
                }
            } else {
                throw new IOException("indefinite-length primitive encoding encountered");
            }
        } else if (read != 0) {
            return null;
        } else {
            throw new IOException("unexpected end-of-contents marker");
        }
    }

    public static int a(InputStream inputStream, int i) throws IOException {
        int i2 = i & 31;
        if (i2 != 31) {
            return i2;
        }
        int i3 = 0;
        int read = inputStream.read();
        if ((read & 127) != 0) {
            while (read >= 0 && (read & 128) != 0) {
                i3 = (i3 | (read & 127)) << 7;
                read = inputStream.read();
            }
            if (read >= 0) {
                return i3 | (read & 127);
            }
            throw new EOFException("EOF found inside tag value.");
        }
        throw new IOException("corrupted stream - invalid high tag number found");
    }

    public static int b(InputStream inputStream, int i) throws IOException {
        int read = inputStream.read();
        if (read < 0) {
            throw new EOFException("EOF found when length expected");
        } else if (read == 128) {
            return -1;
        } else {
            if (read <= 127) {
                return read;
            }
            int i2 = read & 127;
            if (i2 <= 4) {
                int i3 = 0;
                for (int i4 = 0; i4 < i2; i4++) {
                    int read2 = inputStream.read();
                    if (read2 >= 0) {
                        i3 = (i3 << 8) + read2;
                    } else {
                        throw new EOFException("EOF found reading length");
                    }
                }
                if (i3 < 0) {
                    throw new IOException("corrupted stream - negative length found");
                } else if (i3 < i) {
                    return i3;
                } else {
                    throw new IOException("corrupted stream - out of bounds length found");
                }
            } else {
                throw new IOException("DER length more than 4 bytes: " + i2);
            }
        }
    }

    private static byte[] a(d dVar, byte[][] bArr) throws IOException {
        int a = dVar.a();
        if (dVar.a() >= bArr.length) {
            return dVar.b();
        }
        byte[] bArr2 = bArr[a];
        if (bArr2 == null) {
            bArr2 = new byte[a];
            bArr[a] = bArr2;
        }
        Streams.readFully(dVar, bArr2);
        return bArr2;
    }

    private static char[] b(d dVar) throws IOException {
        int read;
        int a = dVar.a() / 2;
        char[] cArr = new char[a];
        int i = 0;
        while (i < a) {
            int read2 = dVar.read();
            if (read2 < 0 || (read = dVar.read()) < 0) {
                break;
            }
            i++;
            cArr[i] = (char) ((read2 << 8) | (read & 255));
        }
        return cArr;
    }

    public static ASN1Primitive a(int i, d dVar, byte[][] bArr) throws IOException {
        if (i == 10) {
            return ASN1Enumerated.a(a(dVar, bArr));
        }
        if (i == 12) {
            return new DERUTF8String(dVar.b());
        }
        if (i == 30) {
            return new DERBMPString(b(dVar));
        }
        switch (i) {
            case 1:
                return ASN1Boolean.a(a(dVar, bArr));
            case 2:
                return new ASN1Integer(dVar.b(), false);
            case 3:
                return ASN1BitString.a(dVar.a(), dVar);
            case 4:
                return new DEROctetString(dVar.b());
            case 5:
                return DERNull.INSTANCE;
            case 6:
                return ASN1ObjectIdentifier.a(a(dVar, bArr));
            default:
                switch (i) {
                    case 18:
                        return new DERNumericString(dVar.b());
                    case 19:
                        return new DERPrintableString(dVar.b());
                    case 20:
                        return new DERT61String(dVar.b());
                    case 21:
                        return new DERVideotexString(dVar.b());
                    case 22:
                        return new DERIA5String(dVar.b());
                    case 23:
                        return new ASN1UTCTime(dVar.b());
                    case 24:
                        return new ASN1GeneralizedTime(dVar.b());
                    case 25:
                        return new DERGraphicString(dVar.b());
                    case 26:
                        return new DERVisibleString(dVar.b());
                    case 27:
                        return new DERGeneralString(dVar.b());
                    case 28:
                        return new DERUniversalString(dVar.b());
                    default:
                        throw new IOException("unknown tag " + i + " encountered");
                }
        }
    }
}
