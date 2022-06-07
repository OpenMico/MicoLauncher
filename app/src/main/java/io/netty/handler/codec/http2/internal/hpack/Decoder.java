package io.netty.handler.codec.http2.internal.hpack;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import io.netty.handler.codec.http2.internal.hpack.c;
import io.netty.util.internal.EmptyArrays;
import java.io.IOException;
import java.io.InputStream;
import okio.Utf8;

/* loaded from: classes4.dex */
public final class Decoder {
    private static final IOException a = new IOException("HPACK - decompression failure");
    private static final IOException b = new IOException("HPACK - illegal index value");
    private static final IOException c = new IOException("HPACK - invalid max dynamic table size");
    private static final IOException d = new IOException("HPACK - max dynamic table size change required");
    private final a e;
    private int f;
    private int g;
    private int h;
    private boolean i = false;
    private long j;
    private a k;
    private c.a l;
    private int m;
    private boolean n;
    private int o;
    private int p;
    private int q;
    private byte[] r;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum a {
        READ_HEADER_REPRESENTATION,
        READ_MAX_DYNAMIC_TABLE_SIZE,
        READ_INDEXED_HEADER,
        READ_INDEXED_HEADER_NAME,
        READ_LITERAL_HEADER_NAME_LENGTH_PREFIX,
        READ_LITERAL_HEADER_NAME_LENGTH,
        READ_LITERAL_HEADER_NAME,
        SKIP_LITERAL_HEADER_NAME,
        READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX,
        READ_LITERAL_HEADER_VALUE_LENGTH,
        READ_LITERAL_HEADER_VALUE,
        SKIP_LITERAL_HEADER_VALUE
    }

    static {
        a.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        b.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        c.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        d.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public Decoder(int i, int i2) {
        this.e = new a(i2);
        this.f = i;
        this.g = i2;
        this.h = i2;
        a();
    }

    private void a() {
        this.j = 0L;
        this.k = a.READ_HEADER_REPRESENTATION;
        this.l = c.a.NONE;
    }

    public void decode(InputStream inputStream, HeaderListener headerListener) throws IOException {
        while (inputStream.available() > 0) {
            boolean z = false;
            switch (this.k) {
                case READ_HEADER_REPRESENTATION:
                    byte read = (byte) inputStream.read();
                    if (!this.i || (read & 224) == 32) {
                        if (read >= 0) {
                            if ((read & SignedBytes.MAX_POWER_OF_TWO) != 64) {
                                if ((read & 32) != 32) {
                                    this.l = (read & 16) == 16 ? c.a.NEVER : c.a.NONE;
                                    this.m = read & 15;
                                    int i = this.m;
                                    if (i != 0) {
                                        if (i != 15) {
                                            b(i);
                                            this.k = a.READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX;
                                            break;
                                        } else {
                                            this.k = a.READ_INDEXED_HEADER_NAME;
                                            break;
                                        }
                                    } else {
                                        this.k = a.READ_LITERAL_HEADER_NAME_LENGTH_PREFIX;
                                        break;
                                    }
                                } else {
                                    this.m = read & Ascii.US;
                                    int i2 = this.m;
                                    if (i2 != 31) {
                                        a(i2);
                                        this.k = a.READ_HEADER_REPRESENTATION;
                                        break;
                                    } else {
                                        this.k = a.READ_MAX_DYNAMIC_TABLE_SIZE;
                                        break;
                                    }
                                }
                            } else {
                                this.l = c.a.INCREMENTAL;
                                this.m = read & Utf8.REPLACEMENT_BYTE;
                                int i3 = this.m;
                                if (i3 != 0) {
                                    if (i3 != 63) {
                                        b(i3);
                                        this.k = a.READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX;
                                        break;
                                    } else {
                                        this.k = a.READ_INDEXED_HEADER_NAME;
                                        break;
                                    }
                                } else {
                                    this.k = a.READ_LITERAL_HEADER_NAME_LENGTH_PREFIX;
                                    break;
                                }
                            }
                        } else {
                            this.m = read & Byte.MAX_VALUE;
                            int i4 = this.m;
                            if (i4 != 0) {
                                if (i4 != 127) {
                                    a(i4, headerListener);
                                    break;
                                } else {
                                    this.k = a.READ_INDEXED_HEADER;
                                    break;
                                }
                            } else {
                                throw b;
                            }
                        }
                    } else {
                        throw d;
                    }
                case READ_MAX_DYNAMIC_TABLE_SIZE:
                    int a2 = a(inputStream);
                    if (a2 != -1) {
                        int i5 = this.m;
                        if (a2 <= Integer.MAX_VALUE - i5) {
                            a(i5 + a2);
                            this.k = a.READ_HEADER_REPRESENTATION;
                            break;
                        } else {
                            throw a;
                        }
                    } else {
                        return;
                    }
                case READ_INDEXED_HEADER:
                    int a3 = a(inputStream);
                    if (a3 != -1) {
                        int i6 = this.m;
                        if (a3 <= Integer.MAX_VALUE - i6) {
                            a(i6 + a3, headerListener);
                            this.k = a.READ_HEADER_REPRESENTATION;
                            break;
                        } else {
                            throw a;
                        }
                    } else {
                        return;
                    }
                case READ_INDEXED_HEADER_NAME:
                    int a4 = a(inputStream);
                    if (a4 != -1) {
                        int i7 = this.m;
                        if (a4 <= Integer.MAX_VALUE - i7) {
                            b(i7 + a4);
                            this.k = a.READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX;
                            break;
                        } else {
                            throw a;
                        }
                    } else {
                        return;
                    }
                case READ_LITERAL_HEADER_NAME_LENGTH_PREFIX:
                    byte read2 = (byte) inputStream.read();
                    if ((read2 & 128) == 128) {
                        z = true;
                    }
                    this.n = z;
                    this.m = read2 & Byte.MAX_VALUE;
                    int i8 = this.m;
                    if (i8 != 127) {
                        this.p = i8;
                        if (a(this.p)) {
                            if (this.l == c.a.NONE) {
                                this.r = EmptyArrays.EMPTY_BYTES;
                                this.o = this.p;
                                this.k = a.SKIP_LITERAL_HEADER_NAME;
                                break;
                            } else if (this.p + 32 > this.e.b()) {
                                this.e.d();
                                this.r = EmptyArrays.EMPTY_BYTES;
                                this.o = this.p;
                                this.k = a.SKIP_LITERAL_HEADER_NAME;
                                break;
                            }
                        }
                        this.k = a.READ_LITERAL_HEADER_NAME;
                        break;
                    } else {
                        this.k = a.READ_LITERAL_HEADER_NAME_LENGTH;
                        break;
                    }
                case READ_LITERAL_HEADER_NAME_LENGTH:
                    this.p = a(inputStream);
                    int i9 = this.p;
                    if (i9 != -1) {
                        int i10 = this.m;
                        if (i9 <= Integer.MAX_VALUE - i10) {
                            this.p = i9 + i10;
                            if (a(this.p)) {
                                if (this.l == c.a.NONE) {
                                    this.r = EmptyArrays.EMPTY_BYTES;
                                    this.o = this.p;
                                    this.k = a.SKIP_LITERAL_HEADER_NAME;
                                    break;
                                } else if (this.p + 32 > this.e.b()) {
                                    this.e.d();
                                    this.r = EmptyArrays.EMPTY_BYTES;
                                    this.o = this.p;
                                    this.k = a.SKIP_LITERAL_HEADER_NAME;
                                    break;
                                }
                            }
                            this.k = a.READ_LITERAL_HEADER_NAME;
                            break;
                        } else {
                            throw a;
                        }
                    } else {
                        return;
                    }
                case READ_LITERAL_HEADER_NAME:
                    int available = inputStream.available();
                    int i11 = this.p;
                    if (available >= i11) {
                        this.r = a(inputStream, i11);
                        this.k = a.READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX;
                        break;
                    } else {
                        return;
                    }
                case SKIP_LITERAL_HEADER_NAME:
                    int i12 = this.o;
                    this.o = (int) (i12 - inputStream.skip(i12));
                    if (this.o != 0) {
                        break;
                    } else {
                        this.k = a.READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX;
                        break;
                    }
                case READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX:
                    byte read3 = (byte) inputStream.read();
                    if ((read3 & 128) == 128) {
                        z = true;
                    }
                    this.n = z;
                    this.m = read3 & Byte.MAX_VALUE;
                    int i13 = this.m;
                    if (i13 != 127) {
                        this.q = i13;
                        long j = this.p + this.q;
                        if (a(j)) {
                            this.j = this.f + 1;
                            if (this.l == c.a.NONE) {
                                this.k = a.SKIP_LITERAL_HEADER_VALUE;
                                break;
                            } else if (j + 32 > this.e.b()) {
                                this.e.d();
                                this.k = a.SKIP_LITERAL_HEADER_VALUE;
                                break;
                            }
                        }
                        if (this.q != 0) {
                            this.k = a.READ_LITERAL_HEADER_VALUE;
                            break;
                        } else {
                            a(headerListener, this.r, EmptyArrays.EMPTY_BYTES, this.l);
                            this.k = a.READ_HEADER_REPRESENTATION;
                            break;
                        }
                    } else {
                        this.k = a.READ_LITERAL_HEADER_VALUE_LENGTH;
                        break;
                    }
                case READ_LITERAL_HEADER_VALUE_LENGTH:
                    this.q = a(inputStream);
                    int i14 = this.q;
                    if (i14 != -1) {
                        int i15 = this.m;
                        if (i14 <= Integer.MAX_VALUE - i15) {
                            this.q = i14 + i15;
                            long j2 = this.p + this.q;
                            int i16 = this.f;
                            if (this.j + j2 > i16) {
                                this.j = i16 + 1;
                                if (this.l == c.a.NONE) {
                                    this.k = a.SKIP_LITERAL_HEADER_VALUE;
                                    break;
                                } else if (j2 + 32 > this.e.b()) {
                                    this.e.d();
                                    this.k = a.SKIP_LITERAL_HEADER_VALUE;
                                    break;
                                }
                            }
                            this.k = a.READ_LITERAL_HEADER_VALUE;
                            break;
                        } else {
                            throw a;
                        }
                    } else {
                        return;
                    }
                case READ_LITERAL_HEADER_VALUE:
                    int available2 = inputStream.available();
                    int i17 = this.q;
                    if (available2 >= i17) {
                        a(headerListener, this.r, a(inputStream, i17), this.l);
                        this.k = a.READ_HEADER_REPRESENTATION;
                        break;
                    } else {
                        return;
                    }
                case SKIP_LITERAL_HEADER_VALUE:
                    int i18 = this.q;
                    this.q = (int) (i18 - inputStream.skip(i18));
                    if (this.q != 0) {
                        break;
                    } else {
                        this.k = a.READ_HEADER_REPRESENTATION;
                        break;
                    }
                default:
                    throw new IllegalStateException("should not reach here");
            }
        }
    }

    public boolean endHeaderBlock() {
        boolean z = this.j > ((long) this.f);
        a();
        return z;
    }

    public void setMaxHeaderTableSize(int i) {
        this.g = i;
        int i2 = this.g;
        if (i2 < this.h) {
            this.i = true;
            this.e.b(i2);
        }
    }

    public int getMaxHeaderTableSize() {
        return this.e.b();
    }

    private void a(int i) throws IOException {
        if (i <= this.g) {
            this.h = i;
            this.i = false;
            this.e.b(i);
            return;
        }
        throw c;
    }

    private void b(int i) throws IOException {
        if (i <= f.a) {
            this.r = f.a(i).f;
        } else if (i - f.a <= this.e.a()) {
            this.r = this.e.a(i - f.a).f;
        } else {
            throw b;
        }
    }

    private void a(int i, HeaderListener headerListener) throws IOException {
        if (i <= f.a) {
            b a2 = f.a(i);
            a(headerListener, a2.f, a2.g, false);
        } else if (i - f.a <= this.e.a()) {
            b a3 = this.e.a(i - f.a);
            a(headerListener, a3.f, a3.g, false);
        } else {
            throw b;
        }
    }

    private void a(HeaderListener headerListener, byte[] bArr, byte[] bArr2, c.a aVar) {
        a(headerListener, bArr, bArr2, aVar == c.a.NEVER);
        switch (aVar) {
            case NONE:
            case NEVER:
                return;
            case INCREMENTAL:
                this.e.a(new b(bArr, bArr2));
                return;
            default:
                throw new IllegalStateException("should not reach here");
        }
    }

    private void a(HeaderListener headerListener, byte[] bArr, byte[] bArr2, boolean z) {
        long length = this.j + bArr.length + bArr2.length;
        int i = this.f;
        if (length <= i) {
            headerListener.addHeader(bArr, bArr2, z);
            this.j = (int) length;
            return;
        }
        this.j = i + 1;
    }

    private boolean a(long j) {
        long j2 = j + this.j;
        int i = this.f;
        if (j2 <= i) {
            return false;
        }
        this.j = i + 1;
        return true;
    }

    private byte[] a(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        if (inputStream.read(bArr) == i) {
            return this.n ? Huffman.DECODER.a(bArr) : bArr;
        }
        throw a;
    }

    private static int a(InputStream inputStream) throws IOException {
        inputStream.mark(5);
        int i = 0;
        for (int i2 = 0; i2 < 32; i2 += 7) {
            if (inputStream.available() != 0) {
                byte read = (byte) inputStream.read();
                if (i2 == 28 && (read & 248) != 0) {
                    break;
                }
                i |= (read & Byte.MAX_VALUE) << i2;
                if ((read & 128) == 0) {
                    return i;
                }
            } else {
                inputStream.reset();
                return -1;
            }
        }
        inputStream.reset();
        throw a;
    }
}
