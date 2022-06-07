package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/* loaded from: classes.dex */
public final class UTF8Writer extends Writer {
    private final IOContext a;
    private OutputStream b;
    private byte[] c;
    private final int d;
    private int e = 0;
    private int f;

    public UTF8Writer(IOContext iOContext, OutputStream outputStream) {
        this.a = iOContext;
        this.b = outputStream;
        this.c = iOContext.allocWriteEncodingBuffer();
        this.d = this.c.length - 4;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c) throws IOException {
        write(c);
        return this;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        OutputStream outputStream = this.b;
        if (outputStream != null) {
            int i = this.e;
            if (i > 0) {
                outputStream.write(this.c, 0, i);
                this.e = 0;
            }
            OutputStream outputStream2 = this.b;
            this.b = null;
            byte[] bArr = this.c;
            if (bArr != null) {
                this.c = null;
                this.a.releaseWriteEncodingBuffer(bArr);
            }
            outputStream2.close();
            int i2 = this.f;
            this.f = 0;
            if (i2 > 0) {
                illegalSurrogate(i2);
            }
        }
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        OutputStream outputStream = this.b;
        if (outputStream != null) {
            int i = this.e;
            if (i > 0) {
                outputStream.write(this.c, 0, i);
                this.e = 0;
            }
            this.b.flush();
        }
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        write(cArr, 0, cArr.length);
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        if (i2 >= 2) {
            if (this.f > 0) {
                i++;
                i2--;
                write(convertSurrogate(cArr[i]));
            }
            int i3 = this.e;
            byte[] bArr = this.c;
            int i4 = this.d;
            int i5 = i2 + i;
            while (i < i5) {
                if (i3 >= i4) {
                    this.b.write(bArr, 0, i3);
                    i3 = 0;
                }
                int i6 = i + 1;
                char c = cArr[i];
                if (c < 128) {
                    int i7 = i3 + 1;
                    bArr[i3] = (byte) c;
                    int i8 = i5 - i6;
                    int i9 = i4 - i7;
                    if (i8 > i9) {
                        i8 = i9;
                    }
                    int i10 = i8 + i6;
                    i = i6;
                    i3 = i7;
                    while (i < i10) {
                        i6 = i + 1;
                        c = cArr[i];
                        if (c < 128) {
                            i3++;
                            bArr[i3] = (byte) c;
                            i = i6;
                        }
                    }
                    continue;
                }
                if (c < 2048) {
                    int i11 = i3 + 1;
                    bArr[i3] = (byte) ((c >> 6) | 192);
                    i3 = i11 + 1;
                    bArr[i11] = (byte) ((c & '?') | 128);
                    i = i6;
                } else if (c < 55296 || c > 57343) {
                    int i12 = i3 + 1;
                    bArr[i3] = (byte) ((c >> '\f') | 224);
                    int i13 = i12 + 1;
                    bArr[i12] = (byte) (((c >> 6) & 63) | 128);
                    i3 = i13 + 1;
                    bArr[i13] = (byte) ((c & '?') | 128);
                    i = i6;
                } else {
                    if (c > 56319) {
                        this.e = i3;
                        illegalSurrogate(c);
                    }
                    this.f = c;
                    if (i6 >= i5) {
                        break;
                    }
                    i = i6 + 1;
                    int convertSurrogate = convertSurrogate(cArr[i6]);
                    if (convertSurrogate > 1114111) {
                        this.e = i3;
                        illegalSurrogate(convertSurrogate);
                    }
                    int i14 = i3 + 1;
                    bArr[i3] = (byte) ((convertSurrogate >> 18) | PsExtractor.VIDEO_STREAM_MASK);
                    int i15 = i14 + 1;
                    bArr[i14] = (byte) (((convertSurrogate >> 12) & 63) | 128);
                    int i16 = i15 + 1;
                    bArr[i15] = (byte) (((convertSurrogate >> 6) & 63) | 128);
                    i3 = i16 + 1;
                    bArr[i16] = (byte) ((convertSurrogate & 63) | 128);
                }
            }
            this.e = i3;
        } else if (i2 == 1) {
            write(cArr[i]);
        }
    }

    @Override // java.io.Writer
    public void write(int i) throws IOException {
        int i2;
        if (this.f > 0) {
            i = convertSurrogate(i);
        } else if (i >= 55296 && i <= 57343) {
            if (i > 56319) {
                illegalSurrogate(i);
            }
            this.f = i;
            return;
        }
        int i3 = this.e;
        if (i3 >= this.d) {
            this.b.write(this.c, 0, i3);
            this.e = 0;
        }
        if (i < 128) {
            byte[] bArr = this.c;
            int i4 = this.e;
            this.e = i4 + 1;
            bArr[i4] = (byte) i;
            return;
        }
        int i5 = this.e;
        if (i < 2048) {
            byte[] bArr2 = this.c;
            int i6 = i5 + 1;
            bArr2[i5] = (byte) ((i >> 6) | 192);
            i2 = i6 + 1;
            bArr2[i6] = (byte) ((i & 63) | 128);
        } else if (i <= 65535) {
            byte[] bArr3 = this.c;
            int i7 = i5 + 1;
            bArr3[i5] = (byte) ((i >> 12) | 224);
            int i8 = i7 + 1;
            bArr3[i7] = (byte) (((i >> 6) & 63) | 128);
            i2 = i8 + 1;
            bArr3[i8] = (byte) ((i & 63) | 128);
        } else {
            if (i > 1114111) {
                illegalSurrogate(i);
            }
            byte[] bArr4 = this.c;
            int i9 = i5 + 1;
            bArr4[i5] = (byte) ((i >> 18) | PsExtractor.VIDEO_STREAM_MASK);
            int i10 = i9 + 1;
            bArr4[i9] = (byte) (((i >> 12) & 63) | 128);
            int i11 = i10 + 1;
            bArr4[i10] = (byte) (((i >> 6) & 63) | 128);
            i2 = i11 + 1;
            bArr4[i11] = (byte) ((i & 63) | 128);
        }
        this.e = i2;
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        write(str, 0, str.length());
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
        if (i2 >= 2) {
            if (this.f > 0) {
                i++;
                i2--;
                write(convertSurrogate(str.charAt(i)));
            }
            int i3 = this.e;
            byte[] bArr = this.c;
            int i4 = this.d;
            int i5 = i2 + i;
            while (i < i5) {
                if (i3 >= i4) {
                    this.b.write(bArr, 0, i3);
                    i3 = 0;
                }
                int i6 = i + 1;
                char charAt = str.charAt(i);
                if (charAt < 128) {
                    int i7 = i3 + 1;
                    bArr[i3] = (byte) charAt;
                    int i8 = i5 - i6;
                    int i9 = i4 - i7;
                    if (i8 > i9) {
                        i8 = i9;
                    }
                    int i10 = i8 + i6;
                    i = i6;
                    i3 = i7;
                    while (i < i10) {
                        i6 = i + 1;
                        charAt = str.charAt(i);
                        if (charAt < 128) {
                            i3++;
                            bArr[i3] = (byte) charAt;
                            i = i6;
                        }
                    }
                    continue;
                }
                if (charAt < 2048) {
                    int i11 = i3 + 1;
                    bArr[i3] = (byte) ((charAt >> 6) | 192);
                    i3 = i11 + 1;
                    bArr[i11] = (byte) ((charAt & '?') | 128);
                    i = i6;
                } else if (charAt < 55296 || charAt > 57343) {
                    int i12 = i3 + 1;
                    bArr[i3] = (byte) ((charAt >> '\f') | 224);
                    int i13 = i12 + 1;
                    bArr[i12] = (byte) (((charAt >> 6) & 63) | 128);
                    i3 = i13 + 1;
                    bArr[i13] = (byte) ((charAt & '?') | 128);
                    i = i6;
                } else {
                    if (charAt > 56319) {
                        this.e = i3;
                        illegalSurrogate(charAt);
                    }
                    this.f = charAt;
                    if (i6 >= i5) {
                        break;
                    }
                    i = i6 + 1;
                    int convertSurrogate = convertSurrogate(str.charAt(i6));
                    if (convertSurrogate > 1114111) {
                        this.e = i3;
                        illegalSurrogate(convertSurrogate);
                    }
                    int i14 = i3 + 1;
                    bArr[i3] = (byte) ((convertSurrogate >> 18) | PsExtractor.VIDEO_STREAM_MASK);
                    int i15 = i14 + 1;
                    bArr[i14] = (byte) (((convertSurrogate >> 12) & 63) | 128);
                    int i16 = i15 + 1;
                    bArr[i15] = (byte) (((convertSurrogate >> 6) & 63) | 128);
                    i3 = i16 + 1;
                    bArr[i16] = (byte) ((convertSurrogate & 63) | 128);
                }
            }
            this.e = i3;
        } else if (i2 == 1) {
            write(str.charAt(i));
        }
    }

    protected int convertSurrogate(int i) throws IOException {
        int i2 = this.f;
        this.f = 0;
        if (i >= 56320 && i <= 57343) {
            return ((i2 - GeneratorBase.SURR1_FIRST) << 10) + 65536 + (i - 56320);
        }
        throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString(i2) + ", second 0x" + Integer.toHexString(i) + "; illegal combination");
    }

    protected static void illegalSurrogate(int i) throws IOException {
        throw new IOException(illegalSurrogateDesc(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String illegalSurrogateDesc(int i) {
        if (i > 1114111) {
            return "Illegal character point (0x" + Integer.toHexString(i) + ") to output; max is 0x10FFFF as per RFC 4627";
        } else if (i < 55296) {
            return "Illegal character point (0x" + Integer.toHexString(i) + ") to output";
        } else if (i <= 56319) {
            return "Unmatched first part of surrogate pair (0x" + Integer.toHexString(i) + ")";
        } else {
            return "Unmatched second part of surrogate pair (0x" + Integer.toHexString(i) + ")";
        }
    }
}
