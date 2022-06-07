package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.NumberInput;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class TextBuffer {
    static final char[] a = new char[0];
    private final BufferRecycler b;
    private char[] c;
    private int d;
    private int e;
    private ArrayList<char[]> f;
    private boolean g;
    private int h;
    private char[] i;
    private int j;
    private String k;
    private char[] l;

    public TextBuffer(BufferRecycler bufferRecycler) {
        this.b = bufferRecycler;
    }

    public void releaseBuffers() {
        if (this.b == null) {
            resetWithEmpty();
        } else if (this.i != null) {
            resetWithEmpty();
            char[] cArr = this.i;
            this.i = null;
            this.b.releaseCharBuffer(2, cArr);
        }
    }

    public void resetWithEmpty() {
        this.d = -1;
        this.j = 0;
        this.e = 0;
        this.c = null;
        this.k = null;
        this.l = null;
        if (this.g) {
            a();
        }
    }

    public void resetWith(char c) {
        this.d = -1;
        this.e = 0;
        this.k = null;
        this.l = null;
        if (this.g) {
            a();
        } else if (this.i == null) {
            this.i = a(1);
        }
        this.i[0] = c;
        this.h = 1;
        this.j = 1;
    }

    public void resetWithShared(char[] cArr, int i, int i2) {
        this.k = null;
        this.l = null;
        this.c = cArr;
        this.d = i;
        this.e = i2;
        if (this.g) {
            a();
        }
    }

    public void resetWithCopy(char[] cArr, int i, int i2) {
        this.c = null;
        this.d = -1;
        this.e = 0;
        this.k = null;
        this.l = null;
        if (this.g) {
            a();
        } else if (this.i == null) {
            this.i = a(i2);
        }
        this.h = 0;
        this.j = 0;
        append(cArr, i, i2);
    }

    public void resetWithCopy(String str, int i, int i2) {
        this.c = null;
        this.d = -1;
        this.e = 0;
        this.k = null;
        this.l = null;
        if (this.g) {
            a();
        } else if (this.i == null) {
            this.i = a(i2);
        }
        this.h = 0;
        this.j = 0;
        append(str, i, i2);
    }

    public void resetWithString(String str) {
        this.c = null;
        this.d = -1;
        this.e = 0;
        this.k = str;
        this.l = null;
        if (this.g) {
            a();
        }
        this.j = 0;
    }

    public char[] getBufferWithoutReset() {
        return this.i;
    }

    private char[] a(int i) {
        BufferRecycler bufferRecycler = this.b;
        if (bufferRecycler != null) {
            return bufferRecycler.allocCharBuffer(2, i);
        }
        return new char[Math.max(i, 1000)];
    }

    private void a() {
        this.g = false;
        this.f.clear();
        this.h = 0;
        this.j = 0;
    }

    public int size() {
        if (this.d >= 0) {
            return this.e;
        }
        char[] cArr = this.l;
        if (cArr != null) {
            return cArr.length;
        }
        String str = this.k;
        if (str != null) {
            return str.length();
        }
        return this.h + this.j;
    }

    public int getTextOffset() {
        int i = this.d;
        if (i >= 0) {
            return i;
        }
        return 0;
    }

    public boolean hasTextAsCharacters() {
        return this.d >= 0 || this.l != null || this.k == null;
    }

    public char[] getTextBuffer() {
        if (this.d >= 0) {
            return this.c;
        }
        char[] cArr = this.l;
        if (cArr != null) {
            return cArr;
        }
        String str = this.k;
        if (str != null) {
            char[] charArray = str.toCharArray();
            this.l = charArray;
            return charArray;
        } else if (this.g) {
            return contentsAsArray();
        } else {
            char[] cArr2 = this.i;
            return cArr2 == null ? a : cArr2;
        }
    }

    public String contentsAsString() {
        if (this.k == null) {
            char[] cArr = this.l;
            if (cArr != null) {
                this.k = new String(cArr);
            } else {
                int i = this.d;
                if (i >= 0) {
                    int i2 = this.e;
                    if (i2 < 1) {
                        this.k = "";
                        return "";
                    }
                    this.k = new String(this.c, i, i2);
                } else {
                    int i3 = this.h;
                    int i4 = this.j;
                    if (i3 == 0) {
                        this.k = i4 == 0 ? "" : new String(this.i, 0, i4);
                    } else {
                        StringBuilder sb = new StringBuilder(i3 + i4);
                        ArrayList<char[]> arrayList = this.f;
                        if (arrayList != null) {
                            int size = arrayList.size();
                            for (int i5 = 0; i5 < size; i5++) {
                                char[] cArr2 = this.f.get(i5);
                                sb.append(cArr2, 0, cArr2.length);
                            }
                        }
                        sb.append(this.i, 0, this.j);
                        this.k = sb.toString();
                    }
                }
            }
        }
        return this.k;
    }

    public char[] contentsAsArray() {
        char[] cArr = this.l;
        if (cArr != null) {
            return cArr;
        }
        char[] b = b();
        this.l = b;
        return b;
    }

    public BigDecimal contentsAsDecimal() throws NumberFormatException {
        char[] cArr;
        char[] cArr2;
        char[] cArr3 = this.l;
        if (cArr3 != null) {
            return NumberInput.parseBigDecimal(cArr3);
        }
        int i = this.d;
        if (i >= 0 && (cArr2 = this.c) != null) {
            return NumberInput.parseBigDecimal(cArr2, i, this.e);
        }
        if (this.h != 0 || (cArr = this.i) == null) {
            return NumberInput.parseBigDecimal(contentsAsArray());
        }
        return NumberInput.parseBigDecimal(cArr, 0, this.j);
    }

    public double contentsAsDouble() throws NumberFormatException {
        return NumberInput.parseDouble(contentsAsString());
    }

    public int contentsAsInt(boolean z) {
        char[] cArr;
        int i = this.d;
        if (i < 0 || (cArr = this.c) == null) {
            if (z) {
                return -NumberInput.parseInt(this.i, 1, this.j - 1);
            }
            return NumberInput.parseInt(this.i, 0, this.j);
        } else if (z) {
            return -NumberInput.parseInt(cArr, i + 1, this.e - 1);
        } else {
            return NumberInput.parseInt(cArr, i, this.e);
        }
    }

    public long contentsAsLong(boolean z) {
        char[] cArr;
        int i = this.d;
        if (i < 0 || (cArr = this.c) == null) {
            if (z) {
                return -NumberInput.parseLong(this.i, 1, this.j - 1);
            }
            return NumberInput.parseLong(this.i, 0, this.j);
        } else if (z) {
            return -NumberInput.parseLong(cArr, i + 1, this.e - 1);
        } else {
            return NumberInput.parseLong(cArr, i, this.e);
        }
    }

    public int contentsToWriter(Writer writer) throws IOException {
        int i;
        char[] cArr = this.l;
        if (cArr != null) {
            writer.write(cArr);
            return this.l.length;
        }
        String str = this.k;
        if (str != null) {
            writer.write(str);
            return this.k.length();
        }
        int i2 = this.d;
        if (i2 >= 0) {
            int i3 = this.e;
            if (i3 > 0) {
                writer.write(this.c, i2, i3);
            }
            return i3;
        }
        ArrayList<char[]> arrayList = this.f;
        if (arrayList != null) {
            int size = arrayList.size();
            i = 0;
            for (int i4 = 0; i4 < size; i4++) {
                char[] cArr2 = this.f.get(i4);
                int length = cArr2.length;
                writer.write(cArr2, 0, length);
                i += length;
            }
        } else {
            i = 0;
        }
        int i5 = this.j;
        if (i5 <= 0) {
            return i;
        }
        writer.write(this.i, 0, i5);
        return i + i5;
    }

    public void ensureNotShared() {
        if (this.d >= 0) {
            b(16);
        }
    }

    public void append(char c) {
        if (this.d >= 0) {
            b(16);
        }
        this.k = null;
        this.l = null;
        char[] cArr = this.i;
        if (this.j >= cArr.length) {
            c(1);
            cArr = this.i;
        }
        int i = this.j;
        this.j = i + 1;
        cArr[i] = c;
    }

    public void append(char[] cArr, int i, int i2) {
        if (this.d >= 0) {
            b(i2);
        }
        this.k = null;
        this.l = null;
        char[] cArr2 = this.i;
        int length = cArr2.length;
        int i3 = this.j;
        int i4 = length - i3;
        if (i4 >= i2) {
            System.arraycopy(cArr, i, cArr2, i3, i2);
            this.j += i2;
            return;
        }
        if (i4 > 0) {
            System.arraycopy(cArr, i, cArr2, i3, i4);
            i += i4;
            i2 -= i4;
        }
        do {
            c(i2);
            int min = Math.min(this.i.length, i2);
            System.arraycopy(cArr, i, this.i, 0, min);
            this.j += min;
            i += min;
            i2 -= min;
        } while (i2 > 0);
    }

    public void append(String str, int i, int i2) {
        if (this.d >= 0) {
            b(i2);
        }
        this.k = null;
        this.l = null;
        char[] cArr = this.i;
        int length = cArr.length;
        int i3 = this.j;
        int i4 = length - i3;
        if (i4 >= i2) {
            str.getChars(i, i + i2, cArr, i3);
            this.j += i2;
            return;
        }
        if (i4 > 0) {
            int i5 = i + i4;
            str.getChars(i, i5, cArr, i3);
            i2 -= i4;
            i = i5;
        }
        while (true) {
            c(i2);
            int min = Math.min(this.i.length, i2);
            int i6 = i + min;
            str.getChars(i, i6, this.i, 0);
            this.j += min;
            i2 -= min;
            if (i2 > 0) {
                i = i6;
            } else {
                return;
            }
        }
    }

    public char[] getCurrentSegment() {
        if (this.d >= 0) {
            b(1);
        } else {
            char[] cArr = this.i;
            if (cArr == null) {
                this.i = a(0);
            } else if (this.j >= cArr.length) {
                c(1);
            }
        }
        return this.i;
    }

    public char[] emptyAndGetCurrentSegment() {
        this.d = -1;
        this.j = 0;
        this.e = 0;
        this.c = null;
        this.k = null;
        this.l = null;
        if (this.g) {
            a();
        }
        char[] cArr = this.i;
        if (cArr != null) {
            return cArr;
        }
        char[] a2 = a(0);
        this.i = a2;
        return a2;
    }

    public int getCurrentSegmentSize() {
        return this.j;
    }

    public void setCurrentLength(int i) {
        this.j = i;
    }

    public String setCurrentAndReturn(int i) {
        this.j = i;
        if (this.h > 0) {
            return contentsAsString();
        }
        int i2 = this.j;
        String str = i2 == 0 ? "" : new String(this.i, 0, i2);
        this.k = str;
        return str;
    }

    public char[] finishCurrentSegment() {
        if (this.f == null) {
            this.f = new ArrayList<>();
        }
        this.g = true;
        this.f.add(this.i);
        int length = this.i.length;
        this.h += length;
        this.j = 0;
        int i = length + (length >> 1);
        if (i < 1000) {
            i = 1000;
        } else if (i > 262144) {
            i = 262144;
        }
        char[] d = d(i);
        this.i = d;
        return d;
    }

    public char[] expandCurrentSegment() {
        char[] cArr = this.i;
        int length = cArr.length;
        int i = (length >> 1) + length;
        if (i > 262144) {
            i = (length >> 2) + length;
        }
        char[] copyOf = Arrays.copyOf(cArr, i);
        this.i = copyOf;
        return copyOf;
    }

    public char[] expandCurrentSegment(int i) {
        char[] cArr = this.i;
        if (cArr.length >= i) {
            return cArr;
        }
        char[] copyOf = Arrays.copyOf(cArr, i);
        this.i = copyOf;
        return copyOf;
    }

    public String toString() {
        return contentsAsString();
    }

    private void b(int i) {
        int i2 = this.e;
        this.e = 0;
        char[] cArr = this.c;
        this.c = null;
        int i3 = this.d;
        this.d = -1;
        int i4 = i + i2;
        char[] cArr2 = this.i;
        if (cArr2 == null || i4 > cArr2.length) {
            this.i = a(i4);
        }
        if (i2 > 0) {
            System.arraycopy(cArr, i3, this.i, 0, i2);
        }
        this.h = 0;
        this.j = i2;
    }

    private void c(int i) {
        if (this.f == null) {
            this.f = new ArrayList<>();
        }
        char[] cArr = this.i;
        this.g = true;
        this.f.add(cArr);
        this.h += cArr.length;
        this.j = 0;
        int length = cArr.length;
        int i2 = length + (length >> 1);
        if (i2 < 1000) {
            i2 = 1000;
        } else if (i2 > 262144) {
            i2 = 262144;
        }
        this.i = d(i2);
    }

    private char[] b() {
        int i;
        String str = this.k;
        if (str != null) {
            return str.toCharArray();
        }
        int i2 = this.d;
        if (i2 >= 0) {
            int i3 = this.e;
            if (i3 < 1) {
                return a;
            }
            if (i2 == 0) {
                return Arrays.copyOf(this.c, i3);
            }
            return Arrays.copyOfRange(this.c, i2, i3 + i2);
        }
        int size = size();
        if (size < 1) {
            return a;
        }
        char[] d = d(size);
        ArrayList<char[]> arrayList = this.f;
        if (arrayList != null) {
            int size2 = arrayList.size();
            i = 0;
            for (int i4 = 0; i4 < size2; i4++) {
                char[] cArr = this.f.get(i4);
                int length = cArr.length;
                System.arraycopy(cArr, 0, d, i, length);
                i += length;
            }
        } else {
            i = 0;
        }
        System.arraycopy(this.i, 0, d, i, this.j);
        return d;
    }

    private char[] d(int i) {
        return new char[i];
    }
}
