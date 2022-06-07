package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import kotlin.jvm.internal.CharCompanionObject;
import okhttp3.internal.ws.WebSocketProtocol;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Chars {
    public static final int BYTES = 2;

    public static int compare(char c, char c2) {
        return c - c2;
    }

    @GwtIncompatible
    public static char fromBytes(byte b2, byte b3) {
        return (char) ((b2 << 8) | (b3 & 255));
    }

    public static int hashCode(char c) {
        return c;
    }

    public static char saturatedCast(long j) {
        if (j > WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            return CharCompanionObject.MAX_VALUE;
        }
        if (j < 0) {
            return (char) 0;
        }
        return (char) j;
    }

    private Chars() {
    }

    public static char checkedCast(long j) {
        char c = (char) j;
        Preconditions.checkArgument(((long) c) == j, "Out of range: %s", j);
        return c;
    }

    public static boolean contains(char[] cArr, char c) {
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(char[] cArr, char c) {
        return c(cArr, c, 0, cArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(char[] cArr, char c, int i, int i2) {
        while (i < i2) {
            if (cArr[i] == c) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0023, code lost:
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOf(char[] r5, char[] r6) {
        /*
            java.lang.String r0 = "array"
            com.google.common.base.Preconditions.checkNotNull(r5, r0)
            java.lang.String r0 = "target"
            com.google.common.base.Preconditions.checkNotNull(r6, r0)
            int r0 = r6.length
            r1 = 0
            if (r0 != 0) goto L_0x000f
            return r1
        L_0x000f:
            r0 = r1
        L_0x0010:
            int r2 = r5.length
            int r3 = r6.length
            int r2 = r2 - r3
            int r2 = r2 + 1
            if (r0 >= r2) goto L_0x002a
            r2 = r1
        L_0x0018:
            int r3 = r6.length
            if (r2 >= r3) goto L_0x0029
            int r3 = r0 + r2
            char r3 = r5[r3]
            char r4 = r6[r2]
            if (r3 == r4) goto L_0x0026
            int r0 = r0 + 1
            goto L_0x0010
        L_0x0026:
            int r2 = r2 + 1
            goto L_0x0018
        L_0x0029:
            return r0
        L_0x002a:
            r5 = -1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.primitives.Chars.indexOf(char[], char[]):int");
    }

    public static int lastIndexOf(char[] cArr, char c) {
        return d(cArr, c, 0, cArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int d(char[] cArr, char c, int i, int i2) {
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            if (cArr[i3] == c) {
                return i3;
            }
        }
        return -1;
    }

    public static char min(char... cArr) {
        Preconditions.checkArgument(cArr.length > 0);
        char c = cArr[0];
        for (int i = 1; i < cArr.length; i++) {
            if (cArr[i] < c) {
                c = cArr[i];
            }
        }
        return c;
    }

    public static char max(char... cArr) {
        Preconditions.checkArgument(cArr.length > 0);
        char c = cArr[0];
        for (int i = 1; i < cArr.length; i++) {
            if (cArr[i] > c) {
                c = cArr[i];
            }
        }
        return c;
    }

    @Beta
    public static char constrainToRange(char c, char c2, char c3) {
        Preconditions.checkArgument(c2 <= c3, "min (%s) must be less than or equal to max (%s)", c2, c3);
        return c < c2 ? c2 : c < c3 ? c : c3;
    }

    public static char[] concat(char[]... cArr) {
        int i = 0;
        for (char[] cArr2 : cArr) {
            i += cArr2.length;
        }
        char[] cArr3 = new char[i];
        int i2 = 0;
        for (char[] cArr4 : cArr) {
            System.arraycopy(cArr4, 0, cArr3, i2, cArr4.length);
            i2 += cArr4.length;
        }
        return cArr3;
    }

    @GwtIncompatible
    public static byte[] toByteArray(char c) {
        return new byte[]{(byte) (c >> '\b'), (byte) c};
    }

    @GwtIncompatible
    public static char fromByteArray(byte[] bArr) {
        Preconditions.checkArgument(bArr.length >= 2, "array too small: %s < %s", bArr.length, 2);
        return fromBytes(bArr[0], bArr[1]);
    }

    public static char[] ensureCapacity(char[] cArr, int i, int i2) {
        boolean z = true;
        Preconditions.checkArgument(i >= 0, "Invalid minLength: %s", i);
        if (i2 < 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "Invalid padding: %s", i2);
        return cArr.length < i ? Arrays.copyOf(cArr, i + i2) : cArr;
    }

    public static String join(String str, char... cArr) {
        Preconditions.checkNotNull(str);
        int length = cArr.length;
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder((str.length() * (length - 1)) + length);
        sb.append(cArr[0]);
        for (int i = 1; i < length; i++) {
            sb.append(str);
            sb.append(cArr[i]);
        }
        return sb.toString();
    }

    public static Comparator<char[]> lexicographicalComparator() {
        return b.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private enum b implements Comparator<char[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Chars.lexicographicalComparator()";
        }

        /* renamed from: a */
        public int compare(char[] cArr, char[] cArr2) {
            int min = Math.min(cArr.length, cArr2.length);
            for (int i = 0; i < min; i++) {
                int compare = Chars.compare(cArr[i], cArr2[i]);
                if (compare != 0) {
                    return compare;
                }
            }
            return cArr.length - cArr2.length;
        }
    }

    public static char[] toArray(Collection<Character> collection) {
        if (collection instanceof a) {
            return ((a) collection).a();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = ((Character) Preconditions.checkNotNull(array[i])).charValue();
        }
        return cArr;
    }

    public static void sortDescending(char[] cArr) {
        Preconditions.checkNotNull(cArr);
        sortDescending(cArr, 0, cArr.length);
    }

    public static void sortDescending(char[] cArr, int i, int i2) {
        Preconditions.checkNotNull(cArr);
        Preconditions.checkPositionIndexes(i, i2, cArr.length);
        Arrays.sort(cArr, i, i2);
        reverse(cArr, i, i2);
    }

    public static void reverse(char[] cArr) {
        Preconditions.checkNotNull(cArr);
        reverse(cArr, 0, cArr.length);
    }

    public static void reverse(char[] cArr, int i, int i2) {
        Preconditions.checkNotNull(cArr);
        Preconditions.checkPositionIndexes(i, i2, cArr.length);
        for (int i3 = i2 - 1; i < i3; i3--) {
            char c = cArr[i];
            cArr[i] = cArr[i3];
            cArr[i3] = c;
            i++;
        }
    }

    public static List<Character> asList(char... cArr) {
        if (cArr.length == 0) {
            return Collections.emptyList();
        }
        return new a(cArr);
    }

    @GwtCompatible
    /* loaded from: classes2.dex */
    private static class a extends AbstractList<Character> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final char[] array;
        final int end;
        final int start;

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return false;
        }

        a(char[] cArr) {
            this(cArr, 0, cArr.length);
        }

        a(char[] cArr, int i, int i2) {
            this.array = cArr;
            this.start = i;
            this.end = i2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.end - this.start;
        }

        /* renamed from: a */
        public Character get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Character.valueOf(this.array[this.start + i]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return (obj instanceof Character) && Chars.c(this.array, ((Character) obj).charValue(), this.start, this.end) != -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(Object obj) {
            int c;
            if (!(obj instanceof Character) || (c = Chars.c(this.array, ((Character) obj).charValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return c - this.start;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(Object obj) {
            int d;
            if (!(obj instanceof Character) || (d = Chars.d(this.array, ((Character) obj).charValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return d - this.start;
        }

        /* renamed from: a */
        public Character set(int i, Character ch) {
            Preconditions.checkElementIndex(i, size());
            char[] cArr = this.array;
            int i2 = this.start;
            char c = cArr[i2 + i];
            cArr[i2 + i] = ((Character) Preconditions.checkNotNull(ch)).charValue();
            return Character.valueOf(c);
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Character> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            if (i == i2) {
                return Collections.emptyList();
            }
            char[] cArr = this.array;
            int i3 = this.start;
            return new a(cArr, i + i3, i3 + i2);
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(@NullableDecl Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return super.equals(obj);
            }
            a aVar = (a) obj;
            int size = size();
            if (aVar.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.array[this.start + i] != aVar.array[aVar.start + i]) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            int i = 1;
            for (int i2 = this.start; i2 < this.end; i2++) {
                i = (i * 31) + Chars.hashCode(this.array[i2]);
            }
            return i;
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 3);
            sb.append('[');
            sb.append(this.array[this.start]);
            int i = this.start;
            while (true) {
                i++;
                if (i < this.end) {
                    sb.append(", ");
                    sb.append(this.array[i]);
                } else {
                    sb.append(']');
                    return sb.toString();
                }
            }
        }

        char[] a() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }
    }
}
