package io.netty.util.internal;

import java.util.Arrays;

/* loaded from: classes4.dex */
public final class AppendableCharSequence implements Appendable, CharSequence {
    private char[] a;
    private int b;

    public AppendableCharSequence(int i) {
        if (i >= 1) {
            this.a = new char[i];
            return;
        }
        throw new IllegalArgumentException("length: " + i + " (length: >= 1)");
    }

    private AppendableCharSequence(char[] cArr) {
        if (cArr.length >= 1) {
            this.a = cArr;
            this.b = cArr.length;
            return;
        }
        throw new IllegalArgumentException("length: " + cArr.length + " (length: >= 1)");
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.b;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        if (i <= this.b) {
            return this.a[i];
        }
        throw new IndexOutOfBoundsException();
    }

    public char charAtUnsafe(int i) {
        return this.a[i];
    }

    @Override // java.lang.CharSequence
    public AppendableCharSequence subSequence(int i, int i2) {
        return new AppendableCharSequence(Arrays.copyOfRange(this.a, i, i2));
    }

    @Override // java.lang.Appendable
    public AppendableCharSequence append(char c) {
        try {
            char[] cArr = this.a;
            int i = this.b;
            this.b = i + 1;
            cArr[i] = c;
        } catch (IndexOutOfBoundsException unused) {
            a();
            this.a[this.b - 1] = c;
        }
        return this;
    }

    @Override // java.lang.Appendable
    public AppendableCharSequence append(CharSequence charSequence) {
        return append(charSequence, 0, charSequence.length());
    }

    @Override // java.lang.Appendable
    public AppendableCharSequence append(CharSequence charSequence, int i, int i2) {
        if (charSequence.length() >= i2) {
            int i3 = i2 - i;
            char[] cArr = this.a;
            int length = cArr.length;
            int i4 = this.b;
            if (i3 > length - i4) {
                this.a = a(cArr, i4 + i3, i4);
            }
            if (charSequence instanceof AppendableCharSequence) {
                System.arraycopy(((AppendableCharSequence) charSequence).a, i, this.a, this.b, i3);
                this.b += i3;
                return this;
            }
            while (i < i2) {
                char[] cArr2 = this.a;
                int i5 = this.b;
                this.b = i5 + 1;
                cArr2[i5] = charSequence.charAt(i);
                i++;
            }
            return this;
        }
        throw new IndexOutOfBoundsException();
    }

    public void reset() {
        this.b = 0;
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return new String(this.a, 0, this.b);
    }

    public String substring(int i, int i2) {
        int i3 = i2 - i;
        int i4 = this.b;
        if (i <= i4 && i3 <= i4) {
            return new String(this.a, i, i3);
        }
        throw new IndexOutOfBoundsException();
    }

    public String subStringUnsafe(int i, int i2) {
        return new String(this.a, i, i2 - i);
    }

    private void a() {
        char[] cArr = this.a;
        int length = cArr.length << 1;
        if (length >= 0) {
            this.a = new char[length];
            System.arraycopy(cArr, 0, this.a, 0, cArr.length);
            return;
        }
        throw new IllegalStateException();
    }

    private static char[] a(char[] cArr, int i, int i2) {
        int length = cArr.length;
        do {
            length <<= 1;
            if (length < 0) {
                throw new IllegalStateException();
            }
        } while (i > length);
        char[] cArr2 = new char[length];
        System.arraycopy(cArr, 0, cArr2, 0, i2);
        return cArr2;
    }
}
