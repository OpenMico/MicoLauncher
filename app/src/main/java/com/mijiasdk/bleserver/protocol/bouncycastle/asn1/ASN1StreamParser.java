package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class ASN1StreamParser {
    private final InputStream a;
    private final int b;
    private final byte[][] c;

    public ASN1StreamParser(InputStream inputStream) {
        this(inputStream, i.a(inputStream));
    }

    public ASN1StreamParser(InputStream inputStream, int i) {
        this.a = inputStream;
        this.b = i;
        this.c = new byte[11];
    }

    public ASN1StreamParser(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    ASN1Encodable a(int i) throws IOException {
        if (i == 4) {
            return new BEROctetStringParser(this);
        }
        if (i == 8) {
            return new DERExternalParser(this);
        }
        switch (i) {
            case 16:
                return new BERSequenceParser(this);
            case 17:
                return new BERSetParser(this);
            default:
                throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(i));
        }
    }

    public ASN1Encodable a(boolean z, int i) throws IOException {
        InputStream inputStream = this.a;
        if (!(inputStream instanceof e)) {
            if (z) {
                if (i == 4) {
                    return new BEROctetStringParser(this);
                }
                switch (i) {
                    case 16:
                        return new DERSequenceParser(this);
                    case 17:
                        return new DERSetParser(this);
                }
            } else if (i == 4) {
                return new DEROctetStringParser((d) inputStream);
            } else {
                switch (i) {
                    case 16:
                        throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
                    case 17:
                        throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
                }
            }
            throw new ASN1Exception("implicit tagging not implemented");
        } else if (z) {
            return a(i);
        } else {
            throw new IOException("indefinite-length primitive encoding encountered");
        }
    }

    public ASN1Primitive b(boolean z, int i) throws IOException {
        if (!z) {
            return new DERTaggedObject(false, i, new DEROctetString(((d) this.a).b()));
        }
        ASN1EncodableVector a = a();
        if (this.a instanceof e) {
            if (a.size() == 1) {
                return new BERTaggedObject(true, i, a.get(0));
            }
            return new BERTaggedObject(false, i, a.a(a));
        } else if (a.size() == 1) {
            return new DERTaggedObject(true, i, a.get(0));
        } else {
            return new DERTaggedObject(false, i, c.a(a));
        }
    }

    public ASN1Encodable readObject() throws IOException {
        int read = this.a.read();
        if (read == -1) {
            return null;
        }
        boolean z = false;
        a(false);
        int a = ASN1InputStream.a(this.a, read);
        if ((read & 32) != 0) {
            z = true;
        }
        int b = ASN1InputStream.b(this.a, this.b);
        if (b >= 0) {
            d dVar = new d(this.a, b);
            if ((read & 64) != 0) {
                return new DERApplicationSpecific(z, a, dVar.b());
            }
            if ((read & 128) != 0) {
                return new BERTaggedObjectParser(z, a, new ASN1StreamParser(dVar));
            }
            if (z) {
                if (a == 4) {
                    return new BEROctetStringParser(new ASN1StreamParser(dVar));
                }
                if (a == 8) {
                    return new DERExternalParser(new ASN1StreamParser(dVar));
                }
                switch (a) {
                    case 16:
                        return new DERSequenceParser(new ASN1StreamParser(dVar));
                    case 17:
                        return new DERSetParser(new ASN1StreamParser(dVar));
                    default:
                        throw new IOException("unknown tag " + a + " encountered");
                }
            } else if (a == 4) {
                return new DEROctetStringParser(dVar);
            } else {
                try {
                    return ASN1InputStream.a(a, dVar, this.c);
                } catch (IllegalArgumentException e) {
                    throw new ASN1Exception("corrupted stream detected", e);
                }
            }
        } else if (z) {
            ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new e(this.a, this.b), this.b);
            if ((read & 64) != 0) {
                return new BERApplicationSpecificParser(a, aSN1StreamParser);
            }
            if ((read & 128) != 0) {
                return new BERTaggedObjectParser(true, a, aSN1StreamParser);
            }
            return aSN1StreamParser.a(a);
        } else {
            throw new IOException("indefinite-length primitive encoding encountered");
        }
    }

    private void a(boolean z) {
        InputStream inputStream = this.a;
        if (inputStream instanceof e) {
            ((e) inputStream).a(z);
        }
    }

    public ASN1EncodableVector a() throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while (true) {
            ASN1Encodable readObject = readObject();
            if (readObject == null) {
                return aSN1EncodableVector;
            }
            if (readObject instanceof InMemoryRepresentable) {
                aSN1EncodableVector.add(((InMemoryRepresentable) readObject).getLoadedObject());
            } else {
                aSN1EncodableVector.add(readObject.toASN1Primitive());
            }
        }
    }
}
