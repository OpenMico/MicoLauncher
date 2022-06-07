package io.netty.util;

import io.netty.util.ByteProcessor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class AsciiString implements CharSequence, Comparable<CharSequence> {
    public static final int INDEX_NOT_FOUND = -1;
    private final byte[] a;
    private final int b;
    private final int c;
    private int d;
    private String e;
    public static final AsciiString EMPTY_STRING = new AsciiString("");
    public static final HashingStrategy<CharSequence> CASE_INSENSITIVE_HASHER = new HashingStrategy<CharSequence>() { // from class: io.netty.util.AsciiString.1
        /* renamed from: a */
        public int hashCode(CharSequence charSequence) {
            return AsciiString.hashCode(charSequence);
        }

        /* renamed from: a */
        public boolean equals(CharSequence charSequence, CharSequence charSequence2) {
            return AsciiString.contentEqualsIgnoreCase(charSequence, charSequence2);
        }
    };
    public static final HashingStrategy<CharSequence> CASE_SENSITIVE_HASHER = new HashingStrategy<CharSequence>() { // from class: io.netty.util.AsciiString.2
        /* renamed from: a */
        public int hashCode(CharSequence charSequence) {
            return AsciiString.hashCode(charSequence);
        }

        /* renamed from: a */
        public boolean equals(CharSequence charSequence, CharSequence charSequence2) {
            return AsciiString.contentEquals(charSequence, charSequence2);
        }
    };

    /* loaded from: classes4.dex */
    public interface b {
        boolean a(char c, char c2);
    }

    public static char b2c(byte b2) {
        return (char) (b2 & 255);
    }

    private static boolean c(byte b2) {
        return b2 >= 97 && b2 <= 122;
    }

    public static byte c2b(char c2) {
        if (c2 > 255) {
            c2 = '?';
        }
        return (byte) c2;
    }

    public static boolean isUpperCase(byte b2) {
        return b2 >= 65 && b2 <= 90;
    }

    public static boolean isUpperCase(char c2) {
        return c2 >= 'A' && c2 <= 'Z';
    }

    public AsciiString(byte[] bArr) {
        this(bArr, true);
    }

    public AsciiString(byte[] bArr, boolean z) {
        this(bArr, 0, bArr.length, z);
    }

    public AsciiString(byte[] bArr, int i, int i2, boolean z) {
        if (z) {
            this.a = Arrays.copyOfRange(bArr, i, i + i2);
            this.b = 0;
        } else if (!MathUtil.isOutOfBounds(i, i2, bArr.length)) {
            this.a = bArr;
            this.b = i;
        } else {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + i + ") <= start + length(" + i2 + ") <= value.length(" + bArr.length + ')');
        }
        this.c = i2;
    }

    public AsciiString(ByteBuffer byteBuffer) {
        this(byteBuffer, true);
    }

    public AsciiString(ByteBuffer byteBuffer, boolean z) {
        this(byteBuffer, byteBuffer.position(), byteBuffer.remaining(), z);
    }

    public AsciiString(ByteBuffer byteBuffer, int i, int i2, boolean z) {
        if (!MathUtil.isOutOfBounds(i, i2, byteBuffer.capacity())) {
            if (!byteBuffer.hasArray()) {
                this.a = new byte[i2];
                int position = byteBuffer.position();
                byteBuffer.get(this.a, 0, i2);
                byteBuffer.position(position);
                this.b = 0;
            } else if (z) {
                int arrayOffset = byteBuffer.arrayOffset() + i;
                this.a = Arrays.copyOfRange(byteBuffer.array(), arrayOffset, arrayOffset + i2);
                this.b = 0;
            } else {
                this.a = byteBuffer.array();
                this.b = i;
            }
            this.c = i2;
            return;
        }
        throw new IndexOutOfBoundsException("expected: 0 <= start(" + i + ") <= start + length(" + i2 + ") <= value.capacity(" + byteBuffer.capacity() + ')');
    }

    public AsciiString(char[] cArr) {
        this(cArr, 0, cArr.length);
    }

    public AsciiString(char[] cArr, int i, int i2) {
        if (!MathUtil.isOutOfBounds(i, i2, cArr.length)) {
            this.a = new byte[i2];
            int i3 = i;
            int i4 = 0;
            while (i4 < i2) {
                this.a[i4] = c2b(cArr[i3]);
                i4++;
                i3++;
            }
            this.b = 0;
            this.c = i2;
            return;
        }
        throw new IndexOutOfBoundsException("expected: 0 <= start(" + i + ") <= start + length(" + i2 + ") <= value.length(" + cArr.length + ')');
    }

    public AsciiString(char[] cArr, Charset charset) {
        this(cArr, charset, 0, cArr.length);
    }

    public AsciiString(char[] cArr, Charset charset, int i, int i2) {
        CharBuffer wrap = CharBuffer.wrap(cArr, i, i2);
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        ByteBuffer allocate = ByteBuffer.allocate((int) (encoder.maxBytesPerChar() * i2));
        encoder.encode(wrap, allocate, true);
        int arrayOffset = allocate.arrayOffset();
        this.a = Arrays.copyOfRange(allocate.array(), arrayOffset, allocate.position() + arrayOffset);
        this.b = 0;
        this.c = this.a.length;
    }

    public AsciiString(CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public AsciiString(CharSequence charSequence, int i, int i2) {
        if (!MathUtil.isOutOfBounds(i, i2, charSequence.length())) {
            this.a = new byte[i2];
            int i3 = i;
            int i4 = 0;
            while (i4 < i2) {
                this.a[i4] = c2b(charSequence.charAt(i3));
                i4++;
                i3++;
            }
            this.b = 0;
            this.c = i2;
            return;
        }
        throw new IndexOutOfBoundsException("expected: 0 <= start(" + i + ") <= start + length(" + i2 + ") <= value.length(" + charSequence.length() + ')');
    }

    public AsciiString(CharSequence charSequence, Charset charset) {
        this(charSequence, charset, 0, charSequence.length());
    }

    public AsciiString(CharSequence charSequence, Charset charset, int i, int i2) {
        CharBuffer wrap = CharBuffer.wrap(charSequence, i, i + i2);
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        ByteBuffer allocate = ByteBuffer.allocate((int) (encoder.maxBytesPerChar() * i2));
        encoder.encode(wrap, allocate, true);
        int arrayOffset = allocate.arrayOffset();
        this.a = Arrays.copyOfRange(allocate.array(), arrayOffset, allocate.position() + arrayOffset);
        this.b = 0;
        this.c = this.a.length;
    }

    public int forEachByte(ByteProcessor byteProcessor) throws Exception {
        return a(0, length(), byteProcessor);
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) throws Exception {
        if (!MathUtil.isOutOfBounds(i, i2, length())) {
            return a(i, i2, byteProcessor);
        }
        throw new IndexOutOfBoundsException("expected: 0 <= index(" + i + ") <= start + length(" + i2 + ") <= length(" + length() + ')');
    }

    private int a(int i, int i2, ByteProcessor byteProcessor) throws Exception {
        int i3 = this.b;
        int i4 = i3 + i + i2;
        for (int i5 = i3 + i; i5 < i4; i5++) {
            if (!byteProcessor.process(this.a[i5])) {
                return i5 - this.b;
            }
        }
        return -1;
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) throws Exception {
        return b(0, length(), byteProcessor);
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) throws Exception {
        if (!MathUtil.isOutOfBounds(i, i2, length())) {
            return b(i, i2, byteProcessor);
        }
        throw new IndexOutOfBoundsException("expected: 0 <= index(" + i + ") <= start + length(" + i2 + ") <= length(" + length() + ')');
    }

    private int b(int i, int i2, ByteProcessor byteProcessor) throws Exception {
        int i3 = this.b;
        int i4 = i3 + i;
        for (int i5 = ((i3 + i) + i2) - 1; i5 >= i4; i5--) {
            if (!byteProcessor.process(this.a[i5])) {
                return i5 - this.b;
            }
        }
        return -1;
    }

    public byte byteAt(int i) {
        if (i < 0 || i >= this.c) {
            throw new IndexOutOfBoundsException("index: " + i + " must be in the range [0," + this.c + ")");
        } else if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.getByte(this.a, i + this.b);
        } else {
            return this.a[i + this.b];
        }
    }

    public boolean isEmpty() {
        return this.c == 0;
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.c;
    }

    public void arrayChanged() {
        this.e = null;
        this.d = 0;
    }

    public byte[] array() {
        return this.a;
    }

    public int arrayOffset() {
        return this.b;
    }

    public boolean isEntireArrayUsed() {
        return this.b == 0 && this.c == this.a.length;
    }

    public byte[] toByteArray() {
        return toByteArray(0, length());
    }

    public byte[] toByteArray(int i, int i2) {
        byte[] bArr = this.a;
        int i3 = this.b;
        return Arrays.copyOfRange(bArr, i + i3, i2 + i3);
    }

    public void copy(int i, byte[] bArr, int i2, int i3) {
        if (!MathUtil.isOutOfBounds(i, i3, length())) {
            System.arraycopy(this.a, i + this.b, ObjectUtil.checkNotNull(bArr, "dst"), i2, i3);
            return;
        }
        throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + i + ") <= srcIdx + length(" + i3 + ") <= srcLen(" + length() + ')');
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return b2c(byteAt(i));
    }

    public boolean contains(CharSequence charSequence) {
        return indexOf(charSequence) >= 0;
    }

    public int compareTo(CharSequence charSequence) {
        int i = 0;
        if (this == charSequence) {
            return 0;
        }
        int length = length();
        int length2 = charSequence.length();
        int min = Math.min(length, length2);
        int arrayOffset = arrayOffset();
        while (i < min) {
            int b2c = b2c(this.a[arrayOffset]) - charSequence.charAt(i);
            if (b2c != 0) {
                return b2c;
            }
            i++;
            arrayOffset++;
        }
        return length - length2;
    }

    public AsciiString concat(CharSequence charSequence) {
        int length = length();
        int length2 = charSequence.length();
        if (length2 == 0) {
            return this;
        }
        if (charSequence.getClass() == AsciiString.class) {
            AsciiString asciiString = (AsciiString) charSequence;
            if (isEmpty()) {
                return asciiString;
            }
            byte[] bArr = new byte[length + length2];
            System.arraycopy(this.a, arrayOffset(), bArr, 0, length);
            System.arraycopy(asciiString.a, asciiString.arrayOffset(), bArr, length, length2);
            return new AsciiString(bArr, false);
        } else if (isEmpty()) {
            return new AsciiString(charSequence);
        } else {
            byte[] bArr2 = new byte[length2 + length];
            System.arraycopy(this.a, arrayOffset(), bArr2, 0, length);
            int i = 0;
            while (length < bArr2.length) {
                bArr2[length] = c2b(charSequence.charAt(i));
                length++;
                i++;
            }
            return new AsciiString(bArr2, false);
        }
    }

    public boolean endsWith(CharSequence charSequence) {
        int length = charSequence.length();
        return regionMatches(length() - length, charSequence, 0, length);
    }

    public boolean contentEqualsIgnoreCase(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() != length()) {
            return false;
        }
        if (charSequence.getClass() == AsciiString.class) {
            AsciiString asciiString = (AsciiString) charSequence;
            int arrayOffset = arrayOffset();
            int arrayOffset2 = asciiString.arrayOffset();
            while (arrayOffset < length()) {
                if (!a(this.a[arrayOffset], asciiString.a[arrayOffset2])) {
                    return false;
                }
                arrayOffset++;
                arrayOffset2++;
            }
            return true;
        }
        int arrayOffset3 = arrayOffset();
        int i = 0;
        while (arrayOffset3 < length()) {
            if (!b(b2c(this.a[arrayOffset3]), charSequence.charAt(i))) {
                return false;
            }
            arrayOffset3++;
            i++;
        }
        return true;
    }

    public char[] toCharArray() {
        return toCharArray(0, length());
    }

    public char[] toCharArray(int i, int i2) {
        int i3 = i2 - i;
        if (i3 == 0) {
            return EmptyArrays.EMPTY_CHARS;
        }
        if (!MathUtil.isOutOfBounds(i, i3, length())) {
            char[] cArr = new char[i3];
            int i4 = 0;
            int arrayOffset = i + arrayOffset();
            while (i4 < i3) {
                cArr[i4] = b2c(this.a[arrayOffset]);
                i4++;
                arrayOffset++;
            }
            return cArr;
        }
        throw new IndexOutOfBoundsException("expected: 0 <= start(" + i + ") <= srcIdx + length(" + i3 + ") <= srcLen(" + length() + ')');
    }

    public void copy(int i, char[] cArr, int i2, int i3) {
        if (cArr == null) {
            throw new NullPointerException("dst");
        } else if (!MathUtil.isOutOfBounds(i, i3, length())) {
            int i4 = i3 + i2;
            int arrayOffset = i + arrayOffset();
            while (i2 < i4) {
                cArr[i2] = b2c(this.a[arrayOffset]);
                i2++;
                arrayOffset++;
            }
        } else {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + i + ") <= srcIdx + length(" + i3 + ") <= srcLen(" + length() + ')');
        }
    }

    public AsciiString subSequence(int i) {
        return subSequence(i, length());
    }

    @Override // java.lang.CharSequence
    public AsciiString subSequence(int i, int i2) {
        return subSequence(i, i2, true);
    }

    public AsciiString subSequence(int i, int i2, boolean z) {
        int i3 = i2 - i;
        if (MathUtil.isOutOfBounds(i, i3, length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + i + ") <= end (" + i2 + ") <= length(" + length() + ')');
        } else if (i == 0 && i2 == length()) {
            return this;
        } else {
            if (i2 == i) {
                return EMPTY_STRING;
            }
            return new AsciiString(this.a, i + this.b, i3, z);
        }
    }

    public int indexOf(CharSequence charSequence) {
        return indexOf(charSequence, 0);
    }

    public int indexOf(CharSequence charSequence, int i) {
        char charAt;
        if (i < 0) {
            i = 0;
        }
        int length = length();
        int length2 = charSequence.length();
        if (length2 <= 0) {
            return i < length ? i : length;
        }
        if (length2 > length - i || (charAt = charSequence.charAt(0)) > 255) {
            return -1;
        }
        ByteProcessor.IndexOfProcessor indexOfProcessor = new ByteProcessor.IndexOfProcessor((byte) charAt);
        while (true) {
            try {
                int forEachByte = forEachByte(i, length - i, indexOfProcessor);
                if (forEachByte == -1 || length2 + forEachByte > length) {
                    break;
                }
                int i2 = forEachByte;
                int i3 = 0;
                do {
                    i3++;
                    if (i3 >= length2) {
                        break;
                    }
                    i2++;
                } while (b2c(this.a[arrayOffset() + i2]) == charSequence.charAt(i3));
                if (i3 == length2) {
                    return forEachByte;
                }
                i = forEachByte + 1;
            } catch (Exception e) {
                PlatformDependent.throwException(e);
                return -1;
            }
        }
        return -1;
    }

    public int indexOf(char c2, int i) {
        if (i < 0) {
            i = 0;
        }
        int length = length();
        if (c2 > 255) {
            return -1;
        }
        try {
            return forEachByte(i, length - i, new ByteProcessor.IndexOfProcessor((byte) c2));
        } catch (Exception e) {
            PlatformDependent.throwException(e);
            return -1;
        }
    }

    public int lastIndexOf(CharSequence charSequence) {
        return lastIndexOf(charSequence, length());
    }

    public int lastIndexOf(CharSequence charSequence, int i) {
        int length = length();
        int length2 = charSequence.length();
        if (length2 > length || i < 0) {
            return -1;
        }
        if (length2 <= 0) {
            return i < length ? i : length;
        }
        int min = Math.min(i, length - length2);
        char charAt = charSequence.charAt(0);
        if (charAt > 255) {
            return -1;
        }
        ByteProcessor.IndexOfProcessor indexOfProcessor = new ByteProcessor.IndexOfProcessor((byte) charAt);
        while (true) {
            try {
                int forEachByteDesc = forEachByteDesc(min, length - min, indexOfProcessor);
                if (forEachByteDesc == -1) {
                    return -1;
                }
                int i2 = forEachByteDesc;
                int i3 = 0;
                do {
                    i3++;
                    if (i3 >= length2) {
                        break;
                    }
                    i2++;
                } while (b2c(this.a[arrayOffset() + i2]) == charSequence.charAt(i3));
                if (i3 == length2) {
                    return forEachByteDesc;
                }
                min = forEachByteDesc - 1;
            } catch (Exception e) {
                PlatformDependent.throwException(e);
                return -1;
            }
        }
    }

    public boolean regionMatches(int i, CharSequence charSequence, int i2, int i3) {
        if (charSequence == null) {
            throw new NullPointerException("string");
        } else if (i2 < 0 || charSequence.length() - i2 < i3) {
            return false;
        } else {
            int length = length();
            if (i < 0 || length - i < i3) {
                return false;
            }
            if (i3 <= 0) {
                return true;
            }
            int i4 = i3 + i2;
            int arrayOffset = i + arrayOffset();
            while (i2 < i4) {
                if (b2c(this.a[arrayOffset]) != charSequence.charAt(i2)) {
                    return false;
                }
                i2++;
                arrayOffset++;
            }
            return true;
        }
    }

    public boolean regionMatches(boolean z, int i, CharSequence charSequence, int i2, int i3) {
        if (!z) {
            return regionMatches(i, charSequence, i2, i3);
        }
        if (charSequence != null) {
            int length = length();
            if (i < 0 || i3 > length - i || i2 < 0 || i3 > charSequence.length() - i2) {
                return false;
            }
            int arrayOffset = i + arrayOffset();
            int i4 = i3 + arrayOffset;
            while (arrayOffset < i4) {
                arrayOffset++;
                i2++;
                if (!b(b2c(this.a[arrayOffset]), charSequence.charAt(i2))) {
                    return false;
                }
            }
            return true;
        }
        throw new NullPointerException("string");
    }

    public AsciiString replace(char c2, char c3) {
        if (c2 > 255) {
            return this;
        }
        byte c2b = c2b(c2);
        try {
            if (forEachByte(new ByteProcessor.IndexOfProcessor(c2b)) == -1) {
                return this;
            }
            byte c2b2 = c2b(c3);
            byte[] bArr = new byte[length()];
            int arrayOffset = arrayOffset();
            int i = 0;
            while (i < bArr.length) {
                byte b2 = this.a[arrayOffset];
                if (b2 == c2b) {
                    b2 = c2b2;
                }
                bArr[i] = b2;
                i++;
                arrayOffset++;
            }
            return new AsciiString(bArr, false);
        } catch (Exception e) {
            PlatformDependent.throwException(e);
            return this;
        }
    }

    public boolean startsWith(CharSequence charSequence) {
        return startsWith(charSequence, 0);
    }

    public boolean startsWith(CharSequence charSequence, int i) {
        return regionMatches(i, charSequence, 0, charSequence.length());
    }

    public AsciiString toLowerCase() {
        boolean z;
        int length = length() + arrayOffset();
        int arrayOffset = arrayOffset();
        while (true) {
            if (arrayOffset >= length) {
                z = true;
                break;
            }
            byte b2 = this.a[arrayOffset];
            if (b2 >= 65 && b2 <= 90) {
                z = false;
                break;
            }
            arrayOffset++;
        }
        if (z) {
            return this;
        }
        byte[] bArr = new byte[length()];
        int arrayOffset2 = arrayOffset();
        int i = 0;
        while (i < bArr.length) {
            bArr[i] = a(this.a[arrayOffset2]);
            i++;
            arrayOffset2++;
        }
        return new AsciiString(bArr, false);
    }

    public AsciiString toUpperCase() {
        boolean z;
        int length = length() + arrayOffset();
        int arrayOffset = arrayOffset();
        while (true) {
            if (arrayOffset >= length) {
                z = true;
                break;
            }
            byte b2 = this.a[arrayOffset];
            if (b2 >= 97 && b2 <= 122) {
                z = false;
                break;
            }
            arrayOffset++;
        }
        if (z) {
            return this;
        }
        byte[] bArr = new byte[length()];
        int arrayOffset2 = arrayOffset();
        int i = 0;
        while (i < bArr.length) {
            bArr[i] = b(this.a[arrayOffset2]);
            i++;
            arrayOffset2++;
        }
        return new AsciiString(bArr, false);
    }

    public AsciiString trim() {
        int arrayOffset = arrayOffset();
        int arrayOffset2 = (arrayOffset() + length()) - 1;
        while (arrayOffset <= arrayOffset2 && this.a[arrayOffset] <= 32) {
            arrayOffset++;
        }
        int i = arrayOffset2;
        while (i >= arrayOffset && this.a[i] <= 32) {
            i--;
        }
        return (arrayOffset == 0 && i == arrayOffset2) ? this : new AsciiString(this.a, arrayOffset, (i - arrayOffset) + 1, false);
    }

    public boolean contentEquals(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() != length()) {
            return false;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return equals(charSequence);
        }
        int arrayOffset = arrayOffset();
        for (int i = 0; i < charSequence.length(); i++) {
            if (b2c(this.a[arrayOffset]) != charSequence.charAt(i)) {
                return false;
            }
            arrayOffset++;
        }
        return true;
    }

    public boolean matches(String str) {
        return Pattern.matches(str, this);
    }

    public AsciiString[] split(String str, int i) {
        return a(Pattern.compile(str).split(this, i));
    }

    public AsciiString[] split(char c2) {
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList();
        int length = length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (charAt(i2) == c2) {
                if (i == i2) {
                    arrayList.add(EMPTY_STRING);
                } else {
                    arrayList.add(new AsciiString(this.a, arrayOffset() + i, i2 - i, false));
                }
                i = i2 + 1;
            }
        }
        if (i == 0) {
            arrayList.add(this);
        } else if (i != length) {
            arrayList.add(new AsciiString(this.a, arrayOffset() + i, length - i, false));
        } else {
            for (int size = arrayList.size() - 1; size >= 0 && ((AsciiString) arrayList.get(size)).isEmpty(); size--) {
                arrayList.remove(size);
            }
        }
        return (AsciiString[]) arrayList.toArray(new AsciiString[arrayList.size()]);
    }

    public int hashCode() {
        if (this.d == 0) {
            this.d = PlatformDependent.hashCodeAscii(this.a, this.b, this.c);
        }
        return this.d;
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != AsciiString.class) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AsciiString asciiString = (AsciiString) obj;
        return length() == asciiString.length() && hashCode() == asciiString.hashCode() && PlatformDependent.equals(array(), arrayOffset(), asciiString.array(), asciiString.arrayOffset(), length());
    }

    @Override // java.lang.CharSequence
    public String toString() {
        String str = this.e;
        if (str != null) {
            return str;
        }
        this.e = toString(0);
        return this.e;
    }

    public String toString(int i) {
        return toString(i, length());
    }

    public String toString(int i, int i2) {
        int i3 = i2 - i;
        if (i3 == 0) {
            return "";
        }
        if (!MathUtil.isOutOfBounds(i, i3, length())) {
            return new String(this.a, 0, i + this.b, i3);
        }
        throw new IndexOutOfBoundsException("expected: 0 <= start(" + i + ") <= srcIdx + length(" + i3 + ") <= srcLen(" + length() + ')');
    }

    public boolean parseBoolean() {
        return this.c >= 1 && this.a[this.b] != 0;
    }

    public char parseChar() {
        return parseChar(0);
    }

    public char parseChar(int i) {
        if (i + 1 < length()) {
            int i2 = i + this.b;
            return (char) (b2c(this.a[i2 + 1]) | (b2c(this.a[i2]) << '\b'));
        }
        throw new IndexOutOfBoundsException("2 bytes required to convert to character. index " + i + " would go out of bounds.");
    }

    public short parseShort() {
        return parseShort(0, length(), 10);
    }

    public short parseShort(int i) {
        return parseShort(0, length(), i);
    }

    public short parseShort(int i, int i2) {
        return parseShort(i, i2, 10);
    }

    public short parseShort(int i, int i2, int i3) {
        int parseInt = parseInt(i, i2, i3);
        short s = (short) parseInt;
        if (s == parseInt) {
            return s;
        }
        throw new NumberFormatException(subSequence(i, i2, false).toString());
    }

    public int parseInt() {
        return parseInt(0, length(), 10);
    }

    public int parseInt(int i) {
        return parseInt(0, length(), i);
    }

    public int parseInt(int i, int i2) {
        return parseInt(i, i2, 10);
    }

    public int parseInt(int i, int i2, int i3) {
        if (i3 < 2 || i3 > 36) {
            throw new NumberFormatException();
        } else if (i != i2) {
            boolean z = byteAt(i) == 45;
            if (z) {
                int i4 = i + 1;
                if (i4 != i2) {
                    i = i4;
                } else {
                    throw new NumberFormatException(subSequence(i, i2, false).toString());
                }
            }
            return a(i, i2, i3, z);
        } else {
            throw new NumberFormatException();
        }
    }

    private int a(int i, int i2, int i3, boolean z) {
        int i4 = Integer.MIN_VALUE / i3;
        int i5 = i;
        int i6 = 0;
        while (i5 < i2) {
            i5++;
            int digit = Character.digit((char) (this.a[i5 + this.b] & 255), i3);
            if (digit == -1) {
                throw new NumberFormatException(subSequence(i, i2, false).toString());
            } else if (i4 <= i6) {
                int i7 = (i6 * i3) - digit;
                if (i7 <= i6) {
                    i6 = i7;
                } else {
                    throw new NumberFormatException(subSequence(i, i2, false).toString());
                }
            } else {
                throw new NumberFormatException(subSequence(i, i2, false).toString());
            }
        }
        if (z || (i6 = -i6) >= 0) {
            return i6;
        }
        throw new NumberFormatException(subSequence(i, i2, false).toString());
    }

    public long parseLong() {
        return parseLong(0, length(), 10);
    }

    public long parseLong(int i) {
        return parseLong(0, length(), i);
    }

    public long parseLong(int i, int i2) {
        return parseLong(i, i2, 10);
    }

    public long parseLong(int i, int i2, int i3) {
        if (i3 < 2 || i3 > 36) {
            throw new NumberFormatException();
        } else if (i != i2) {
            boolean z = byteAt(i) == 45;
            if (z) {
                int i4 = i + 1;
                if (i4 != i2) {
                    i = i4;
                } else {
                    throw new NumberFormatException(subSequence(i, i2, false).toString());
                }
            }
            return b(i, i2, i3, z);
        } else {
            throw new NumberFormatException();
        }
    }

    private long b(int i, int i2, int i3, boolean z) {
        long j = i3;
        long j2 = Long.MIN_VALUE / j;
        int i4 = i;
        long j3 = 0;
        while (i4 < i2) {
            i4++;
            int digit = Character.digit((char) (this.a[i4 + this.b] & 255), i3);
            if (digit == -1) {
                throw new NumberFormatException(subSequence(i, i2, false).toString());
            } else if (j2 <= j3) {
                long j4 = (j3 * j) - digit;
                if (j4 <= j3) {
                    j3 = j4;
                } else {
                    throw new NumberFormatException(subSequence(i, i2, false).toString());
                }
            } else {
                throw new NumberFormatException(subSequence(i, i2, false).toString());
            }
        }
        if (!z) {
            j3 = -j3;
            if (j3 < 0) {
                throw new NumberFormatException(subSequence(i, i2, false).toString());
            }
        }
        return j3;
    }

    public float parseFloat() {
        return parseFloat(0, length());
    }

    public float parseFloat(int i, int i2) {
        return Float.parseFloat(toString(i, i2));
    }

    public double parseDouble() {
        return parseDouble(0, length());
    }

    public double parseDouble(int i, int i2) {
        return Double.parseDouble(toString(i, i2));
    }

    public static AsciiString of(CharSequence charSequence) {
        return charSequence.getClass() == AsciiString.class ? (AsciiString) charSequence : new AsciiString(charSequence);
    }

    public static int hashCode(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return ((AsciiString) charSequence).hashCode();
        }
        return PlatformDependent.hashCodeAscii(charSequence);
    }

    public static boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        return a(charSequence, charSequence2, c.a);
    }

    public static boolean containsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return a(charSequence, charSequence2, a.a);
    }

    public static boolean contentEqualsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == charSequence2;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return ((AsciiString) charSequence).contentEqualsIgnoreCase(charSequence2);
        }
        if (charSequence2.getClass() == AsciiString.class) {
            return ((AsciiString) charSequence2).contentEqualsIgnoreCase(charSequence);
        }
        if (charSequence.length() != charSequence2.length()) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        while (i < charSequence.length()) {
            if (!b(charSequence.charAt(i), charSequence2.charAt(i2))) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public static boolean containsContentEqualsIgnoreCase(Collection<CharSequence> collection, CharSequence charSequence) {
        for (CharSequence charSequence2 : collection) {
            if (contentEqualsIgnoreCase(charSequence, charSequence2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAllContentEqualsIgnoreCase(Collection<CharSequence> collection, Collection<CharSequence> collection2) {
        for (CharSequence charSequence : collection2) {
            if (!containsContentEqualsIgnoreCase(collection, charSequence)) {
                return false;
            }
        }
        return true;
    }

    public static boolean contentEquals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == charSequence2;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return ((AsciiString) charSequence).contentEquals(charSequence2);
        }
        if (charSequence2.getClass() == AsciiString.class) {
            return ((AsciiString) charSequence2).contentEquals(charSequence);
        }
        if (charSequence.length() != charSequence2.length()) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static AsciiString[] a(String[] strArr) {
        AsciiString[] asciiStringArr = new AsciiString[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            asciiStringArr[i] = new AsciiString(strArr[i]);
        }
        return asciiStringArr;
    }

    /* loaded from: classes4.dex */
    public static final class c implements b {
        static final c a = new c();

        @Override // io.netty.util.AsciiString.b
        public boolean a(char c, char c2) {
            return c == c2;
        }

        private c() {
        }
    }

    /* loaded from: classes4.dex */
    public static final class a implements b {
        static final a a = new a();

        private a() {
        }

        @Override // io.netty.util.AsciiString.b
        public boolean a(char c, char c2) {
            return AsciiString.b(c, c2);
        }
    }

    /* loaded from: classes4.dex */
    public static final class d implements b {
        static final d a = new d();

        private d() {
        }

        @Override // io.netty.util.AsciiString.b
        public boolean a(char c, char c2) {
            return Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2);
        }
    }

    private static boolean a(CharSequence charSequence, CharSequence charSequence2, b bVar) {
        if (charSequence == null || charSequence2 == null || charSequence.length() < charSequence2.length()) {
            return false;
        }
        if (charSequence2.length() == 0) {
            return true;
        }
        int i = 0;
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            if (bVar.a(charSequence2.charAt(i), charSequence.charAt(i2))) {
                i++;
                if (i == charSequence2.length()) {
                    return true;
                }
            } else if (charSequence.length() - i2 < charSequence2.length()) {
                return false;
            } else {
                i = 0;
            }
        }
        return false;
    }

    private static boolean a(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3, b bVar) {
        if (i < 0 || i3 > charSequence.length() - i || i2 < 0 || i3 > charSequence2.length() - i2) {
            return false;
        }
        int i4 = i3 + i;
        while (i < i4) {
            i++;
            i2++;
            if (!bVar.a(charSequence.charAt(i), charSequence2.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean regionMatches(CharSequence charSequence, boolean z, int i, CharSequence charSequence2, int i2, int i3) {
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return ((String) charSequence).regionMatches(z, i, (String) charSequence2, i2, i3);
        }
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).regionMatches(z, i, charSequence2, i2, i3);
        }
        return a(charSequence, i, charSequence2, i2, i3, z ? d.a : c.a);
    }

    public static boolean regionMatchesAscii(CharSequence charSequence, boolean z, int i, CharSequence charSequence2, int i2, int i3) {
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        if (!z && (charSequence instanceof String) && (charSequence2 instanceof String)) {
            return ((String) charSequence).regionMatches(false, i, (String) charSequence2, i2, i3);
        }
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).regionMatches(z, i, charSequence2, i2, i3);
        }
        return a(charSequence, i, charSequence2, i2, i3, z ? a.a : c.a);
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        int length = charSequence2.length();
        int length2 = (charSequence.length() - length) + 1;
        if (i > length2) {
            return -1;
        }
        if (length == 0) {
            return i;
        }
        while (i < length2) {
            if (regionMatches(charSequence, true, i, charSequence2, 0, length)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int indexOfIgnoreCaseAscii(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null || charSequence2 == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        int length = charSequence2.length();
        int length2 = (charSequence.length() - length) + 1;
        if (i > length2) {
            return -1;
        }
        if (length == 0) {
            return i;
        }
        while (i < length2) {
            if (regionMatchesAscii(charSequence, true, i, charSequence2, 0, length)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int indexOf(CharSequence charSequence, char c2, int i) {
        if (charSequence instanceof String) {
            return ((String) charSequence).indexOf(c2, i);
        }
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).indexOf(c2, i);
        }
        if (charSequence == null) {
            return -1;
        }
        int length = charSequence.length();
        if (i < 0) {
            i = 0;
        }
        while (i < length) {
            if (charSequence.charAt(i) == c2) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private static boolean a(byte b2, byte b3) {
        return b2 == b3 || a(b2) == a(b3);
    }

    public static boolean b(char c2, char c3) {
        return c2 == c3 || a(c2) == a(c3);
    }

    private static byte a(byte b2) {
        return isUpperCase(b2) ? (byte) (b2 + 32) : b2;
    }

    private static char a(char c2) {
        return isUpperCase(c2) ? (char) (c2 + ' ') : c2;
    }

    private static byte b(byte b2) {
        return c(b2) ? (byte) (b2 - 32) : b2;
    }
}
