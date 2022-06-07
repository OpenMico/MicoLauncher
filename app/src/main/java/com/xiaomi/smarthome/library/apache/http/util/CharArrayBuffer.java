package com.xiaomi.smarthome.library.apache.http.util;

import com.xiaomi.smarthome.library.apache.http.protocol.HTTP;

/* loaded from: classes4.dex */
public final class CharArrayBuffer {
    private char[] a;
    private int b;

    public CharArrayBuffer(int i) {
        if (i >= 0) {
            this.a = new char[i];
            return;
        }
        throw new IllegalArgumentException("Buffer capacity may not be negative");
    }

    private void a(int i) {
        char[] cArr = new char[Math.max(this.a.length << 1, i)];
        System.arraycopy(this.a, 0, cArr, 0, this.b);
        this.a = cArr;
    }

    public void append(char[] cArr, int i, int i2) {
        int i3;
        if (cArr != null) {
            if (i < 0 || i > cArr.length || i2 < 0 || (i3 = i + i2) < 0 || i3 > cArr.length) {
                throw new IndexOutOfBoundsException();
            } else if (i2 != 0) {
                int i4 = this.b + i2;
                if (i4 > this.a.length) {
                    a(i4);
                }
                System.arraycopy(cArr, i, this.a, this.b, i2);
                this.b = i4;
            }
        }
    }

    public void append(String str) {
        if (str == null) {
            str = "null";
        }
        int length = str.length();
        int i = this.b + length;
        if (i > this.a.length) {
            a(i);
        }
        str.getChars(0, length, this.a, this.b);
        this.b = i;
    }

    public void append(CharArrayBuffer charArrayBuffer, int i, int i2) {
        if (charArrayBuffer != null) {
            append(charArrayBuffer.a, i, i2);
        }
    }

    public void append(CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer != null) {
            append(charArrayBuffer.a, 0, charArrayBuffer.b);
        }
    }

    public void append(char c) {
        int i = this.b + 1;
        if (i > this.a.length) {
            a(i);
        }
        this.a[this.b] = c;
        this.b = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4 */
    public void append(byte[] bArr, int i, int i2) {
        int i3;
        if (bArr != 0) {
            if (i < 0 || i > bArr.length || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
                throw new IndexOutOfBoundsException();
            } else if (i2 != 0) {
                int i4 = this.b;
                int i5 = i2 + i4;
                if (i5 > this.a.length) {
                    a(i5);
                }
                while (i4 < i5) {
                    byte b = bArr[i];
                    if (b < 0) {
                        b += 256;
                    }
                    this.a[i4] = b == true ? (char) 1 : (char) 0;
                    i++;
                    i4++;
                }
                this.b = i5;
            }
        }
    }

    public void append(ByteArrayBuffer byteArrayBuffer, int i, int i2) {
        if (byteArrayBuffer != null) {
            append(byteArrayBuffer.buffer(), i, i2);
        }
    }

    public void append(Object obj) {
        append(String.valueOf(obj));
    }

    public void clear() {
        this.b = 0;
    }

    public char[] toCharArray() {
        int i = this.b;
        char[] cArr = new char[i];
        if (i > 0) {
            System.arraycopy(this.a, 0, cArr, 0, i);
        }
        return cArr;
    }

    public char charAt(int i) {
        return this.a[i];
    }

    public char[] buffer() {
        return this.a;
    }

    public int capacity() {
        return this.a.length;
    }

    public int length() {
        return this.b;
    }

    public void ensureCapacity(int i) {
        int length = this.a.length;
        int i2 = this.b;
        if (i > length - i2) {
            a(i2 + i);
        }
    }

    public void setLength(int i) {
        if (i < 0 || i > this.a.length) {
            throw new IndexOutOfBoundsException();
        }
        this.b = i;
    }

    public boolean isEmpty() {
        return this.b == 0;
    }

    public boolean isFull() {
        return this.b == this.a.length;
    }

    public int indexOf(int i, int i2, int i3) {
        if (i2 < 0) {
            i2 = 0;
        }
        int i4 = this.b;
        if (i3 > i4) {
            i3 = i4;
        }
        if (i2 > i3) {
            return -1;
        }
        while (i2 < i3) {
            if (this.a[i2] == i) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public int indexOf(int i) {
        return indexOf(i, 0, this.b);
    }

    public String substring(int i, int i2) {
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i2 > this.b) {
            throw new IndexOutOfBoundsException();
        } else if (i <= i2) {
            return new String(this.a, i, i2 - i);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public String substringTrimmed(int i, int i2) {
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i2 > this.b) {
            throw new IndexOutOfBoundsException();
        } else if (i <= i2) {
            while (i < i2 && HTTP.isWhitespace(this.a[i])) {
                i++;
            }
            while (i2 > i && HTTP.isWhitespace(this.a[i2 - 1])) {
                i2--;
            }
            return new String(this.a, i, i2 - i);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public String toString() {
        return new String(this.a, 0, this.b);
    }
}
