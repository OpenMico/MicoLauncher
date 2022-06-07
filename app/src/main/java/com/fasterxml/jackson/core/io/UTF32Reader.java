package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.xiaomi.mipush.sdk.Constants;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* loaded from: classes.dex */
public class UTF32Reader extends Reader {
    protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
    protected static final char NC = 0;
    protected final boolean _bigEndian;
    protected byte[] _buffer;
    protected int _byteCount;
    protected int _charCount;
    protected final IOContext _context;
    protected InputStream _in;
    protected int _length;
    protected final boolean _managedBuffers;
    protected int _ptr;
    protected char _surrogate = 0;
    protected char[] _tmpBuf;

    public UTF32Reader(IOContext iOContext, InputStream inputStream, byte[] bArr, int i, int i2, boolean z) {
        boolean z2 = false;
        this._context = iOContext;
        this._in = inputStream;
        this._buffer = bArr;
        this._ptr = i;
        this._length = i2;
        this._bigEndian = z;
        this._managedBuffers = inputStream != null ? true : z2;
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        InputStream inputStream = this._in;
        if (inputStream != null) {
            this._in = null;
            a();
            inputStream.close();
        }
    }

    @Override // java.io.Reader
    public int read() throws IOException {
        if (this._tmpBuf == null) {
            this._tmpBuf = new char[1];
        }
        if (read(this._tmpBuf, 0, 1) < 1) {
            return -1;
        }
        return this._tmpBuf[0];
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3;
        int i4;
        int i5;
        int i6;
        if (this._buffer == null) {
            return -1;
        }
        if (i2 < 1) {
            return i2;
        }
        if (i < 0 || i + i2 > cArr.length) {
            a(cArr, i, i2);
        }
        int i7 = i2 + i;
        char c = this._surrogate;
        if (c != 0) {
            i3 = i + 1;
            cArr[i] = c;
            this._surrogate = (char) 0;
        } else {
            int i8 = this._length - this._ptr;
            if (i8 < 4 && !a(i8)) {
                if (i8 == 0) {
                    return -1;
                }
                a(this._length - this._ptr, 4);
            }
            i3 = i;
        }
        int i9 = this._length - 4;
        while (true) {
            if (i3 >= i7) {
                i4 = i3;
                break;
            }
            int i10 = this._ptr;
            if (this._bigEndian) {
                byte[] bArr = this._buffer;
                i6 = (bArr[i10] << 8) | (bArr[i10 + 1] & 255);
                i5 = (bArr[i10 + 3] & 255) | ((bArr[i10 + 2] & 255) << 8);
            } else {
                byte[] bArr2 = this._buffer;
                i5 = (bArr2[i10] & 255) | ((bArr2[i10 + 1] & 255) << 8);
                i6 = (bArr2[i10 + 3] << 8) | (bArr2[i10 + 2] & 255);
            }
            this._ptr += 4;
            if (i6 != 0) {
                int i11 = i6 & 65535;
                int i12 = ((i11 - 1) << 16) | i5;
                if (i11 > 16) {
                    a(i12, i3 - i, String.format(" (above 0x%08x)", 1114111));
                }
                i4 = i3 + 1;
                cArr[i3] = (char) ((i12 >> 10) + GeneratorBase.SURR1_FIRST);
                i5 = (i12 & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | 56320;
                if (i4 >= i7) {
                    this._surrogate = (char) i12;
                    break;
                }
                i3 = i4;
            }
            i4 = i3 + 1;
            cArr[i3] = (char) i5;
            if (this._ptr > i9) {
                break;
            }
            i3 = i4;
        }
        int i13 = i4 - i;
        this._charCount += i13;
        return i13;
    }

    private void a(int i, int i2) throws IOException {
        int i3 = this._charCount;
        throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + i + ", needed " + i2 + ", at char #" + i3 + ", byte #" + (this._byteCount + i) + ")");
    }

    private void a(int i, int i2, String str) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid UTF-32 character 0x");
        sb.append(Integer.toHexString(i));
        sb.append(str);
        sb.append(" at char #");
        sb.append(this._charCount + i2);
        sb.append(", byte #");
        sb.append((this._byteCount + this._ptr) - 1);
        sb.append(")");
        throw new CharConversionException(sb.toString());
    }

    private boolean a(int i) throws IOException {
        int i2;
        this._byteCount += this._length - i;
        if (i > 0) {
            int i3 = this._ptr;
            if (i3 > 0) {
                byte[] bArr = this._buffer;
                System.arraycopy(bArr, i3, bArr, 0, i);
                this._ptr = 0;
            }
            this._length = i;
        } else {
            this._ptr = 0;
            InputStream inputStream = this._in;
            int read = inputStream == null ? -1 : inputStream.read(this._buffer);
            if (read < 1) {
                this._length = 0;
                if (read < 0) {
                    if (this._managedBuffers) {
                        a();
                    }
                    return false;
                }
                b();
            }
            this._length = read;
        }
        while (true) {
            int i4 = this._length;
            if (i4 >= 4) {
                return true;
            }
            InputStream inputStream2 = this._in;
            if (inputStream2 == null) {
                i2 = -1;
            } else {
                byte[] bArr2 = this._buffer;
                i2 = inputStream2.read(bArr2, i4, bArr2.length - i4);
            }
            if (i2 < 1) {
                if (i2 < 0) {
                    if (this._managedBuffers) {
                        a();
                    }
                    a(this._length, 4);
                }
                b();
            }
            this._length += i2;
        }
    }

    private void a() {
        byte[] bArr = this._buffer;
        if (bArr != null) {
            this._buffer = null;
            this._context.releaseReadIOBuffer(bArr);
        }
    }

    private void a(char[] cArr, int i, int i2) throws IOException {
        throw new ArrayIndexOutOfBoundsException("read(buf," + i + Constants.ACCEPT_TIME_SEPARATOR_SP + i2 + "), cbuf[" + cArr.length + "]");
    }

    private void b() throws IOException {
        throw new IOException("Strange I/O stream, returned 0 bytes on read");
    }
}
