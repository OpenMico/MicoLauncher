package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.core.io.UTF32Reader;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/* loaded from: classes.dex */
public final class ByteSourceJsonBootstrapper {
    public static final byte UTF8_BOM_1 = -17;
    public static final byte UTF8_BOM_2 = -69;
    public static final byte UTF8_BOM_3 = -65;
    private final IOContext a;
    private final InputStream b;
    private final byte[] c;
    private int d;
    private int e;
    private final boolean f;
    private boolean g;
    private int h;

    public ByteSourceJsonBootstrapper(IOContext iOContext, InputStream inputStream) {
        this.g = true;
        this.a = iOContext;
        this.b = inputStream;
        this.c = iOContext.allocReadIOBuffer();
        this.d = 0;
        this.e = 0;
        this.f = true;
    }

    public ByteSourceJsonBootstrapper(IOContext iOContext, byte[] bArr, int i, int i2) {
        this.g = true;
        this.a = iOContext;
        this.b = null;
        this.c = bArr;
        this.d = i;
        this.e = i + i2;
        this.f = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x005e, code lost:
        if (c((r1[r4 + 1] & 255) | ((r1[r4] & 255) << 8)) != false) goto L_0x0062;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.fasterxml.jackson.core.JsonEncoding detectEncoding() throws java.io.IOException {
        /*
            r7 = this;
            r0 = 4
            boolean r1 = r7.ensureLoaded(r0)
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0043
            byte[] r1 = r7.c
            int r4 = r7.d
            byte r5 = r1[r4]
            int r5 = r5 << 24
            int r6 = r4 + 1
            byte r6 = r1[r6]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << 16
            r5 = r5 | r6
            int r6 = r4 + 2
            byte r6 = r1[r6]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << 8
            r5 = r5 | r6
            int r4 = r4 + 3
            byte r1 = r1[r4]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r1 = r1 | r5
            boolean r4 = r7.a(r1)
            if (r4 == 0) goto L_0x0031
            goto L_0x0062
        L_0x0031:
            boolean r4 = r7.b(r1)
            if (r4 == 0) goto L_0x0038
            goto L_0x0062
        L_0x0038:
            int r1 = r1 >>> 16
            boolean r1 = r7.c(r1)
            if (r1 == 0) goto L_0x0041
            goto L_0x0062
        L_0x0041:
            r2 = r3
            goto L_0x0062
        L_0x0043:
            r1 = 2
            boolean r1 = r7.ensureLoaded(r1)
            if (r1 == 0) goto L_0x0061
            byte[] r1 = r7.c
            int r4 = r7.d
            byte r5 = r1[r4]
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r5 = r5 << 8
            int r4 = r4 + r2
            byte r1 = r1[r4]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r1 = r1 | r5
            boolean r1 = r7.c(r1)
            if (r1 == 0) goto L_0x0061
            goto L_0x0062
        L_0x0061:
            r2 = r3
        L_0x0062:
            if (r2 != 0) goto L_0x0067
            com.fasterxml.jackson.core.JsonEncoding r0 = com.fasterxml.jackson.core.JsonEncoding.UTF8
            goto L_0x008c
        L_0x0067:
            int r1 = r7.h
            if (r1 == r0) goto L_0x0083
            switch(r1) {
                case 1: goto L_0x0080;
                case 2: goto L_0x0076;
                default: goto L_0x006e;
            }
        L_0x006e:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Internal error"
            r0.<init>(r1)
            throw r0
        L_0x0076:
            boolean r0 = r7.g
            if (r0 == 0) goto L_0x007d
            com.fasterxml.jackson.core.JsonEncoding r0 = com.fasterxml.jackson.core.JsonEncoding.UTF16_BE
            goto L_0x008c
        L_0x007d:
            com.fasterxml.jackson.core.JsonEncoding r0 = com.fasterxml.jackson.core.JsonEncoding.UTF16_LE
            goto L_0x008c
        L_0x0080:
            com.fasterxml.jackson.core.JsonEncoding r0 = com.fasterxml.jackson.core.JsonEncoding.UTF8
            goto L_0x008c
        L_0x0083:
            boolean r0 = r7.g
            if (r0 == 0) goto L_0x008a
            com.fasterxml.jackson.core.JsonEncoding r0 = com.fasterxml.jackson.core.JsonEncoding.UTF32_BE
            goto L_0x008c
        L_0x008a:
            com.fasterxml.jackson.core.JsonEncoding r0 = com.fasterxml.jackson.core.JsonEncoding.UTF32_LE
        L_0x008c:
            com.fasterxml.jackson.core.io.IOContext r1 = r7.a
            r1.setEncoding(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.detectEncoding():com.fasterxml.jackson.core.JsonEncoding");
    }

    public static int skipUTF8BOM(DataInput dataInput) throws IOException {
        int readUnsignedByte = dataInput.readUnsignedByte();
        if (readUnsignedByte != 239) {
            return readUnsignedByte;
        }
        int readUnsignedByte2 = dataInput.readUnsignedByte();
        if (readUnsignedByte2 == 187) {
            int readUnsignedByte3 = dataInput.readUnsignedByte();
            if (readUnsignedByte3 == 191) {
                return dataInput.readUnsignedByte();
            }
            throw new IOException("Unexpected byte 0x" + Integer.toHexString(readUnsignedByte3) + " following 0xEF 0xBB; should get 0xBF as part of UTF-8 BOM");
        }
        throw new IOException("Unexpected byte 0x" + Integer.toHexString(readUnsignedByte2) + " following 0xEF; should get 0xBB as part of UTF-8 BOM");
    }

    public Reader constructReader() throws IOException {
        JsonEncoding encoding = this.a.getEncoding();
        int bits = encoding.bits();
        if (bits == 8 || bits == 16) {
            InputStream inputStream = this.b;
            if (inputStream == null) {
                inputStream = new ByteArrayInputStream(this.c, this.d, this.e);
            } else {
                int i = this.d;
                int i2 = this.e;
                if (i < i2) {
                    inputStream = new MergedStream(this.a, inputStream, this.c, i, i2);
                }
            }
            return new InputStreamReader(inputStream, encoding.getJavaName());
        } else if (bits == 32) {
            IOContext iOContext = this.a;
            return new UTF32Reader(iOContext, this.b, this.c, this.d, this.e, iOContext.getEncoding().isBigEndian());
        } else {
            throw new RuntimeException("Internal error");
        }
    }

    public JsonParser constructParser(int i, ObjectCodec objectCodec, ByteQuadsCanonicalizer byteQuadsCanonicalizer, CharsToNameCanonicalizer charsToNameCanonicalizer, int i2) throws IOException {
        if (detectEncoding() != JsonEncoding.UTF8 || !JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(i2)) {
            return new ReaderBasedJsonParser(this.a, i, constructReader(), objectCodec, charsToNameCanonicalizer.makeChild(i2));
        }
        return new UTF8StreamJsonParser(this.a, i, this.b, objectCodec, byteQuadsCanonicalizer.makeChild(i2), this.c, this.d, this.e, this.f);
    }

    public static MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
        if (!inputAccessor.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
        }
        byte nextByte = inputAccessor.nextByte();
        if (nextByte == -17) {
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != -69) {
                return MatchStrength.NO_MATCH;
            }
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != -65) {
                return MatchStrength.NO_MATCH;
            }
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            nextByte = inputAccessor.nextByte();
        }
        int a = a(inputAccessor, nextByte);
        if (a < 0) {
            return MatchStrength.INCONCLUSIVE;
        }
        if (a == 123) {
            int a2 = a(inputAccessor);
            if (a2 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (a2 == 34 || a2 == 125) {
                return MatchStrength.SOLID_MATCH;
            }
            return MatchStrength.NO_MATCH;
        } else if (a == 91) {
            int a3 = a(inputAccessor);
            if (a3 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (a3 == 93 || a3 == 91) {
                return MatchStrength.SOLID_MATCH;
            }
            return MatchStrength.SOLID_MATCH;
        } else {
            MatchStrength matchStrength = MatchStrength.WEAK_MATCH;
            if (a == 34) {
                return matchStrength;
            }
            if (a <= 57 && a >= 48) {
                return matchStrength;
            }
            if (a == 45) {
                int a4 = a(inputAccessor);
                if (a4 < 0) {
                    return MatchStrength.INCONCLUSIVE;
                }
                return (a4 > 57 || a4 < 48) ? MatchStrength.NO_MATCH : matchStrength;
            } else if (a == 110) {
                return a(inputAccessor, "ull", matchStrength);
            } else {
                if (a == 116) {
                    return a(inputAccessor, "rue", matchStrength);
                }
                if (a == 102) {
                    return a(inputAccessor, "alse", matchStrength);
                }
                return MatchStrength.NO_MATCH;
            }
        }
    }

    private static MatchStrength a(InputAccessor inputAccessor, String str, MatchStrength matchStrength) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != str.charAt(i)) {
                return MatchStrength.NO_MATCH;
            }
        }
        return matchStrength;
    }

    private static int a(InputAccessor inputAccessor) throws IOException {
        if (!inputAccessor.hasMoreBytes()) {
            return -1;
        }
        return a(inputAccessor, inputAccessor.nextByte());
    }

    private static int a(InputAccessor inputAccessor, byte b) throws IOException {
        while (true) {
            int i = b & 255;
            if (i != 32 && i != 13 && i != 10 && i != 9) {
                return i;
            }
            if (!inputAccessor.hasMoreBytes()) {
                return -1;
            }
            b = inputAccessor.nextByte();
        }
    }

    private boolean a(int i) throws IOException {
        if (i == -16842752) {
            a("3412");
        } else if (i == -131072) {
            this.d += 4;
            this.h = 4;
            this.g = false;
            return true;
        } else if (i == 65279) {
            this.g = true;
            this.d += 4;
            this.h = 4;
            return true;
        } else if (i == 65534) {
            a("2143");
        }
        int i2 = i >>> 16;
        if (i2 == 65279) {
            this.d += 2;
            this.h = 2;
            this.g = true;
            return true;
        } else if (i2 == 65534) {
            this.d += 2;
            this.h = 2;
            this.g = false;
            return true;
        } else if ((i >>> 8) != 15711167) {
            return false;
        } else {
            this.d += 3;
            this.h = 1;
            this.g = true;
            return true;
        }
    }

    private boolean b(int i) throws IOException {
        if ((i >> 8) == 0) {
            this.g = true;
        } else if ((16777215 & i) == 0) {
            this.g = false;
        } else if (((-16711681) & i) == 0) {
            a("3412");
        } else if ((i & (-65281)) != 0) {
            return false;
        } else {
            a("2143");
        }
        this.h = 4;
        return true;
    }

    private boolean c(int i) {
        if ((65280 & i) == 0) {
            this.g = true;
        } else if ((i & 255) != 0) {
            return false;
        } else {
            this.g = false;
        }
        this.h = 2;
        return true;
    }

    private void a(String str) throws IOException {
        throw new CharConversionException("Unsupported UCS-4 endianness (" + str + ") detected");
    }

    protected boolean ensureLoaded(int i) throws IOException {
        int i2;
        int i3 = this.e - this.d;
        while (i3 < i) {
            InputStream inputStream = this.b;
            if (inputStream == null) {
                i2 = -1;
            } else {
                byte[] bArr = this.c;
                int i4 = this.e;
                i2 = inputStream.read(bArr, i4, bArr.length - i4);
            }
            if (i2 < 1) {
                return false;
            }
            this.e += i2;
            i3 += i2;
        }
        return true;
    }
}
