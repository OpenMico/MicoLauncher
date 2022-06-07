package org.apache.commons.lang.text;

import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SystemUtils;

/* loaded from: classes5.dex */
public class StrBuilder implements Cloneable {
    private String a;
    private String b;
    protected char[] buffer;
    protected int size;

    public StrBuilder() {
        this(32);
    }

    public StrBuilder(int i) {
        this.buffer = new char[i <= 0 ? 32 : i];
    }

    public StrBuilder(String str) {
        if (str == null) {
            this.buffer = new char[32];
            return;
        }
        this.buffer = new char[str.length() + 32];
        append(str);
    }

    public String getNewLineText() {
        return this.a;
    }

    public StrBuilder setNewLineText(String str) {
        this.a = str;
        return this;
    }

    public String getNullText() {
        return this.b;
    }

    public StrBuilder setNullText(String str) {
        if (str != null && str.length() == 0) {
            str = null;
        }
        this.b = str;
        return this;
    }

    public int length() {
        return this.size;
    }

    public StrBuilder setLength(int i) {
        if (i >= 0) {
            int i2 = this.size;
            if (i < i2) {
                this.size = i;
            } else if (i > i2) {
                ensureCapacity(i);
                this.size = i;
                for (int i3 = this.size; i3 < i; i3++) {
                    this.buffer[i3] = 0;
                }
            }
            return this;
        }
        throw new StringIndexOutOfBoundsException(i);
    }

    public int capacity() {
        return this.buffer.length;
    }

    public StrBuilder ensureCapacity(int i) {
        char[] cArr = this.buffer;
        if (i > cArr.length) {
            this.buffer = new char[i * 2];
            System.arraycopy(cArr, 0, this.buffer, 0, this.size);
        }
        return this;
    }

    public StrBuilder minimizeCapacity() {
        if (this.buffer.length > length()) {
            char[] cArr = this.buffer;
            this.buffer = new char[length()];
            System.arraycopy(cArr, 0, this.buffer, 0, this.size);
        }
        return this;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public StrBuilder clear() {
        this.size = 0;
        return this;
    }

    public char charAt(int i) {
        if (i >= 0 && i < length()) {
            return this.buffer[i];
        }
        throw new StringIndexOutOfBoundsException(i);
    }

    public StrBuilder setCharAt(int i, char c2) {
        if (i < 0 || i >= length()) {
            throw new StringIndexOutOfBoundsException(i);
        }
        this.buffer[i] = c2;
        return this;
    }

    public StrBuilder deleteCharAt(int i) {
        if (i < 0 || i >= this.size) {
            throw new StringIndexOutOfBoundsException(i);
        }
        a(i, i + 1, 1);
        return this;
    }

    public char[] toCharArray() {
        int i = this.size;
        if (i == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        char[] cArr = new char[i];
        System.arraycopy(this.buffer, 0, cArr, 0, i);
        return cArr;
    }

    public char[] toCharArray(int i, int i2) {
        int validateRange = validateRange(i, i2) - i;
        if (validateRange == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        char[] cArr = new char[validateRange];
        System.arraycopy(this.buffer, i, cArr, 0, validateRange);
        return cArr;
    }

    public char[] getChars(char[] cArr) {
        int length = length();
        if (cArr == null || cArr.length < length) {
            cArr = new char[length];
        }
        System.arraycopy(this.buffer, 0, cArr, 0, length);
        return cArr;
    }

    public void getChars(int i, int i2, char[] cArr, int i3) {
        if (i < 0) {
            throw new StringIndexOutOfBoundsException(i);
        } else if (i2 < 0 || i2 > length()) {
            throw new StringIndexOutOfBoundsException(i2);
        } else if (i <= i2) {
            System.arraycopy(this.buffer, i, cArr, i3, i2 - i);
        } else {
            throw new StringIndexOutOfBoundsException("end < start");
        }
    }

    public StrBuilder appendNewLine() {
        String str = this.a;
        if (str != null) {
            return append(str);
        }
        append(SystemUtils.LINE_SEPARATOR);
        return this;
    }

    public StrBuilder appendNull() {
        String str = this.b;
        return str == null ? this : append(str);
    }

    public StrBuilder append(Object obj) {
        if (obj == null) {
            return appendNull();
        }
        return append(obj.toString());
    }

    public StrBuilder append(String str) {
        if (str == null) {
            return appendNull();
        }
        int length = str.length();
        if (length > 0) {
            int length2 = length();
            ensureCapacity(length2 + length);
            str.getChars(0, length, this.buffer, length2);
            this.size += length;
        }
        return this;
    }

    public StrBuilder append(String str, int i, int i2) {
        int i3;
        if (str == null) {
            return appendNull();
        }
        if (i < 0 || i > str.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        } else if (i2 < 0 || (i3 = i + i2) > str.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        } else {
            if (i2 > 0) {
                int length = length();
                ensureCapacity(length + i2);
                str.getChars(i, i3, this.buffer, length);
                this.size += i2;
            }
            return this;
        }
    }

    public StrBuilder append(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return appendNull();
        }
        int length = stringBuffer.length();
        if (length > 0) {
            int length2 = length();
            ensureCapacity(length2 + length);
            stringBuffer.getChars(0, length, this.buffer, length2);
            this.size += length;
        }
        return this;
    }

    public StrBuilder append(StringBuffer stringBuffer, int i, int i2) {
        int i3;
        if (stringBuffer == null) {
            return appendNull();
        }
        if (i < 0 || i > stringBuffer.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        } else if (i2 < 0 || (i3 = i + i2) > stringBuffer.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        } else {
            if (i2 > 0) {
                int length = length();
                ensureCapacity(length + i2);
                stringBuffer.getChars(i, i3, this.buffer, length);
                this.size += i2;
            }
            return this;
        }
    }

    public StrBuilder append(StrBuilder strBuilder) {
        if (strBuilder == null) {
            return appendNull();
        }
        int length = strBuilder.length();
        if (length > 0) {
            int length2 = length();
            ensureCapacity(length2 + length);
            System.arraycopy(strBuilder.buffer, 0, this.buffer, length2, length);
            this.size += length;
        }
        return this;
    }

    public StrBuilder append(StrBuilder strBuilder, int i, int i2) {
        int i3;
        if (strBuilder == null) {
            return appendNull();
        }
        if (i < 0 || i > strBuilder.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        } else if (i2 < 0 || (i3 = i + i2) > strBuilder.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        } else {
            if (i2 > 0) {
                int length = length();
                ensureCapacity(length + i2);
                strBuilder.getChars(i, i3, this.buffer, length);
                this.size += i2;
            }
            return this;
        }
    }

    public StrBuilder append(char[] cArr) {
        if (cArr == null) {
            return appendNull();
        }
        int length = cArr.length;
        if (length > 0) {
            int length2 = length();
            ensureCapacity(length2 + length);
            System.arraycopy(cArr, 0, this.buffer, length2, length);
            this.size += length;
        }
        return this;
    }

    public StrBuilder append(char[] cArr, int i, int i2) {
        if (cArr == null) {
            return appendNull();
        }
        if (i < 0 || i > cArr.length) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid startIndex: ");
            stringBuffer.append(i2);
            throw new StringIndexOutOfBoundsException(stringBuffer.toString());
        } else if (i2 < 0 || i + i2 > cArr.length) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid length: ");
            stringBuffer2.append(i2);
            throw new StringIndexOutOfBoundsException(stringBuffer2.toString());
        } else {
            if (i2 > 0) {
                int length = length();
                ensureCapacity(length + i2);
                System.arraycopy(cArr, i, this.buffer, length, i2);
                this.size += i2;
            }
            return this;
        }
    }

    public StrBuilder append(boolean z) {
        if (z) {
            ensureCapacity(this.size + 4);
            char[] cArr = this.buffer;
            int i = this.size;
            this.size = i + 1;
            cArr[i] = 't';
            int i2 = this.size;
            this.size = i2 + 1;
            cArr[i2] = 'r';
            int i3 = this.size;
            this.size = i3 + 1;
            cArr[i3] = 'u';
            int i4 = this.size;
            this.size = i4 + 1;
            cArr[i4] = 'e';
        } else {
            ensureCapacity(this.size + 5);
            char[] cArr2 = this.buffer;
            int i5 = this.size;
            this.size = i5 + 1;
            cArr2[i5] = 'f';
            int i6 = this.size;
            this.size = i6 + 1;
            cArr2[i6] = 'a';
            int i7 = this.size;
            this.size = i7 + 1;
            cArr2[i7] = 'l';
            int i8 = this.size;
            this.size = i8 + 1;
            cArr2[i8] = 's';
            int i9 = this.size;
            this.size = i9 + 1;
            cArr2[i9] = 'e';
        }
        return this;
    }

    public StrBuilder append(char c2) {
        ensureCapacity(length() + 1);
        char[] cArr = this.buffer;
        int i = this.size;
        this.size = i + 1;
        cArr[i] = c2;
        return this;
    }

    public StrBuilder append(int i) {
        return append(String.valueOf(i));
    }

    public StrBuilder append(long j) {
        return append(String.valueOf(j));
    }

    public StrBuilder append(float f) {
        return append(String.valueOf(f));
    }

    public StrBuilder append(double d) {
        return append(String.valueOf(d));
    }

    public StrBuilder appendln(Object obj) {
        return append(obj).appendNewLine();
    }

    public StrBuilder appendln(String str) {
        return append(str).appendNewLine();
    }

    public StrBuilder appendln(String str, int i, int i2) {
        return append(str, i, i2).appendNewLine();
    }

    public StrBuilder appendln(StringBuffer stringBuffer) {
        return append(stringBuffer).appendNewLine();
    }

    public StrBuilder appendln(StringBuffer stringBuffer, int i, int i2) {
        return append(stringBuffer, i, i2).appendNewLine();
    }

    public StrBuilder appendln(StrBuilder strBuilder) {
        return append(strBuilder).appendNewLine();
    }

    public StrBuilder appendln(StrBuilder strBuilder, int i, int i2) {
        return append(strBuilder, i, i2).appendNewLine();
    }

    public StrBuilder appendln(char[] cArr) {
        return append(cArr).appendNewLine();
    }

    public StrBuilder appendln(char[] cArr, int i, int i2) {
        return append(cArr, i, i2).appendNewLine();
    }

    public StrBuilder appendln(boolean z) {
        return append(z).appendNewLine();
    }

    public StrBuilder appendln(char c2) {
        return append(c2).appendNewLine();
    }

    public StrBuilder appendln(int i) {
        return append(i).appendNewLine();
    }

    public StrBuilder appendln(long j) {
        return append(j).appendNewLine();
    }

    public StrBuilder appendln(float f) {
        return append(f).appendNewLine();
    }

    public StrBuilder appendln(double d) {
        return append(d).appendNewLine();
    }

    public StrBuilder appendAll(Object[] objArr) {
        if (objArr != null && objArr.length > 0) {
            for (Object obj : objArr) {
                append(obj);
            }
        }
        return this;
    }

    public StrBuilder appendAll(Collection collection) {
        if (collection != null && collection.size() > 0) {
            for (Object obj : collection) {
                append(obj);
            }
        }
        return this;
    }

    public StrBuilder appendAll(Iterator it) {
        if (it != null) {
            while (it.hasNext()) {
                append(it.next());
            }
        }
        return this;
    }

    public StrBuilder appendWithSeparators(Object[] objArr, String str) {
        if (objArr != null && objArr.length > 0) {
            if (str == null) {
                str = "";
            }
            append(objArr[0]);
            for (int i = 1; i < objArr.length; i++) {
                append(str);
                append(objArr[i]);
            }
        }
        return this;
    }

    public StrBuilder appendWithSeparators(Collection collection, String str) {
        if (collection != null && collection.size() > 0) {
            if (str == null) {
                str = "";
            }
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                append(it.next());
                if (it.hasNext()) {
                    append(str);
                }
            }
        }
        return this;
    }

    public StrBuilder appendWithSeparators(Iterator it, String str) {
        if (it != null) {
            if (str == null) {
                str = "";
            }
            while (it.hasNext()) {
                append(it.next());
                if (it.hasNext()) {
                    append(str);
                }
            }
        }
        return this;
    }

    public StrBuilder appendSeparator(String str) {
        return appendSeparator(str, (String) null);
    }

    public StrBuilder appendSeparator(String str, String str2) {
        if (isEmpty()) {
            str = str2;
        }
        if (str != null) {
            append(str);
        }
        return this;
    }

    public StrBuilder appendSeparator(char c2) {
        if (size() > 0) {
            append(c2);
        }
        return this;
    }

    public StrBuilder appendSeparator(char c2, char c3) {
        if (size() > 0) {
            append(c2);
        } else {
            append(c3);
        }
        return this;
    }

    public StrBuilder appendSeparator(String str, int i) {
        if (str != null && i > 0) {
            append(str);
        }
        return this;
    }

    public StrBuilder appendSeparator(char c2, int i) {
        if (i > 0) {
            append(c2);
        }
        return this;
    }

    public StrBuilder appendPadding(int i, char c2) {
        if (i >= 0) {
            ensureCapacity(this.size + i);
            for (int i2 = 0; i2 < i; i2++) {
                char[] cArr = this.buffer;
                int i3 = this.size;
                this.size = i3 + 1;
                cArr[i3] = c2;
            }
        }
        return this;
    }

    public StrBuilder appendFixedWidthPadLeft(Object obj, int i, char c2) {
        if (i > 0) {
            ensureCapacity(this.size + i);
            String nullText = obj == null ? getNullText() : obj.toString();
            if (nullText == null) {
                nullText = "";
            }
            int length = nullText.length();
            if (length >= i) {
                nullText.getChars(length - i, length, this.buffer, this.size);
            } else {
                int i2 = i - length;
                for (int i3 = 0; i3 < i2; i3++) {
                    this.buffer[this.size + i3] = c2;
                }
                nullText.getChars(0, length, this.buffer, this.size + i2);
            }
            this.size += i;
        }
        return this;
    }

    public StrBuilder appendFixedWidthPadLeft(int i, int i2, char c2) {
        return appendFixedWidthPadLeft(String.valueOf(i), i2, c2);
    }

    public StrBuilder appendFixedWidthPadRight(Object obj, int i, char c2) {
        if (i > 0) {
            ensureCapacity(this.size + i);
            String nullText = obj == null ? getNullText() : obj.toString();
            if (nullText == null) {
                nullText = "";
            }
            int length = nullText.length();
            if (length >= i) {
                nullText.getChars(0, i, this.buffer, this.size);
            } else {
                int i2 = i - length;
                nullText.getChars(0, length, this.buffer, this.size);
                for (int i3 = 0; i3 < i2; i3++) {
                    this.buffer[this.size + length + i3] = c2;
                }
            }
            this.size += i;
        }
        return this;
    }

    public StrBuilder appendFixedWidthPadRight(int i, int i2, char c2) {
        return appendFixedWidthPadRight(String.valueOf(i), i2, c2);
    }

    public StrBuilder insert(int i, Object obj) {
        if (obj == null) {
            return insert(i, this.b);
        }
        return insert(i, obj.toString());
    }

    public StrBuilder insert(int i, String str) {
        validateIndex(i);
        if (str == null) {
            str = this.b;
        }
        int length = str == null ? 0 : str.length();
        if (length > 0) {
            int i2 = this.size + length;
            ensureCapacity(i2);
            char[] cArr = this.buffer;
            System.arraycopy(cArr, i, cArr, i + length, this.size - i);
            this.size = i2;
            str.getChars(0, length, this.buffer, i);
        }
        return this;
    }

    public StrBuilder insert(int i, char[] cArr) {
        validateIndex(i);
        if (cArr == null) {
            return insert(i, this.b);
        }
        int length = cArr.length;
        if (length > 0) {
            ensureCapacity(this.size + length);
            char[] cArr2 = this.buffer;
            System.arraycopy(cArr2, i, cArr2, i + length, this.size - i);
            System.arraycopy(cArr, 0, this.buffer, i, length);
            this.size += length;
        }
        return this;
    }

    public StrBuilder insert(int i, char[] cArr, int i2, int i3) {
        validateIndex(i);
        if (cArr == null) {
            return insert(i, this.b);
        }
        if (i2 < 0 || i2 > cArr.length) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid offset: ");
            stringBuffer.append(i2);
            throw new StringIndexOutOfBoundsException(stringBuffer.toString());
        } else if (i3 < 0 || i2 + i3 > cArr.length) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid length: ");
            stringBuffer2.append(i3);
            throw new StringIndexOutOfBoundsException(stringBuffer2.toString());
        } else {
            if (i3 > 0) {
                ensureCapacity(this.size + i3);
                char[] cArr2 = this.buffer;
                System.arraycopy(cArr2, i, cArr2, i + i3, this.size - i);
                System.arraycopy(cArr, i2, this.buffer, i, i3);
                this.size += i3;
            }
            return this;
        }
    }

    public StrBuilder insert(int i, boolean z) {
        validateIndex(i);
        if (z) {
            ensureCapacity(this.size + 4);
            char[] cArr = this.buffer;
            System.arraycopy(cArr, i, cArr, i + 4, this.size - i);
            char[] cArr2 = this.buffer;
            int i2 = i + 1;
            cArr2[i] = 't';
            int i3 = i2 + 1;
            cArr2[i2] = 'r';
            cArr2[i3] = 'u';
            cArr2[i3 + 1] = 'e';
            this.size += 4;
        } else {
            ensureCapacity(this.size + 5);
            char[] cArr3 = this.buffer;
            System.arraycopy(cArr3, i, cArr3, i + 5, this.size - i);
            char[] cArr4 = this.buffer;
            int i4 = i + 1;
            cArr4[i] = 'f';
            int i5 = i4 + 1;
            cArr4[i4] = 'a';
            int i6 = i5 + 1;
            cArr4[i5] = 'l';
            cArr4[i6] = 's';
            cArr4[i6 + 1] = 'e';
            this.size += 5;
        }
        return this;
    }

    public StrBuilder insert(int i, char c2) {
        validateIndex(i);
        ensureCapacity(this.size + 1);
        char[] cArr = this.buffer;
        System.arraycopy(cArr, i, cArr, i + 1, this.size - i);
        this.buffer[i] = c2;
        this.size++;
        return this;
    }

    public StrBuilder insert(int i, int i2) {
        return insert(i, String.valueOf(i2));
    }

    public StrBuilder insert(int i, long j) {
        return insert(i, String.valueOf(j));
    }

    public StrBuilder insert(int i, float f) {
        return insert(i, String.valueOf(f));
    }

    public StrBuilder insert(int i, double d) {
        return insert(i, String.valueOf(d));
    }

    private void a(int i, int i2, int i3) {
        char[] cArr = this.buffer;
        System.arraycopy(cArr, i2, cArr, i, this.size - i2);
        this.size -= i3;
    }

    public StrBuilder delete(int i, int i2) {
        int validateRange = validateRange(i, i2);
        int i3 = validateRange - i;
        if (i3 > 0) {
            a(i, validateRange, i3);
        }
        return this;
    }

    public StrBuilder deleteAll(char c2) {
        int i = 0;
        while (i < this.size) {
            if (this.buffer[i] == c2) {
                int i2 = i;
                do {
                    i2++;
                    if (i2 >= this.size) {
                        break;
                    }
                } while (this.buffer[i2] == c2);
                int i3 = i2 - i;
                a(i, i2, i3);
                i = i2 - i3;
            }
            i++;
        }
        return this;
    }

    public StrBuilder deleteFirst(char c2) {
        int i = 0;
        while (true) {
            if (i >= this.size) {
                break;
            } else if (this.buffer[i] == c2) {
                a(i, i + 1, 1);
                break;
            } else {
                i++;
            }
        }
        return this;
    }

    public StrBuilder deleteAll(String str) {
        int length = str == null ? 0 : str.length();
        if (length > 0) {
            int indexOf = indexOf(str, 0);
            while (indexOf >= 0) {
                a(indexOf, indexOf + length, length);
                indexOf = indexOf(str, indexOf);
            }
        }
        return this;
    }

    public StrBuilder deleteFirst(String str) {
        int indexOf;
        int length = str == null ? 0 : str.length();
        if (length > 0 && (indexOf = indexOf(str, 0)) >= 0) {
            a(indexOf, indexOf + length, length);
        }
        return this;
    }

    public StrBuilder deleteAll(StrMatcher strMatcher) {
        return replace(strMatcher, null, 0, this.size, -1);
    }

    public StrBuilder deleteFirst(StrMatcher strMatcher) {
        return replace(strMatcher, null, 0, this.size, 1);
    }

    private void a(int i, int i2, int i3, String str, int i4) {
        int i5 = (this.size - i3) + i4;
        if (i4 != i3) {
            ensureCapacity(i5);
            char[] cArr = this.buffer;
            System.arraycopy(cArr, i2, cArr, i + i4, this.size - i2);
            this.size = i5;
        }
        if (i4 > 0) {
            str.getChars(0, i4, this.buffer, i);
        }
    }

    public StrBuilder replace(int i, int i2, String str) {
        int validateRange = validateRange(i, i2);
        a(i, validateRange, validateRange - i, str, str == null ? 0 : str.length());
        return this;
    }

    public StrBuilder replaceAll(char c2, char c3) {
        if (c2 != c3) {
            for (int i = 0; i < this.size; i++) {
                char[] cArr = this.buffer;
                if (cArr[i] == c2) {
                    cArr[i] = c3;
                }
            }
        }
        return this;
    }

    public StrBuilder replaceFirst(char c2, char c3) {
        if (c2 != c3) {
            int i = 0;
            while (true) {
                if (i >= this.size) {
                    break;
                }
                char[] cArr = this.buffer;
                if (cArr[i] == c2) {
                    cArr[i] = c3;
                    break;
                }
                i++;
            }
        }
        return this;
    }

    public StrBuilder replaceAll(String str, String str2) {
        int i;
        int length = str == null ? 0 : str.length();
        if (length > 0) {
            if (str2 == null) {
                i = 0;
            } else {
                i = str2.length();
            }
            int indexOf = indexOf(str, 0);
            while (indexOf >= 0) {
                a(indexOf, indexOf + length, length, str2, i);
                indexOf = indexOf(str, indexOf + i);
            }
        }
        return this;
    }

    public StrBuilder replaceFirst(String str, String str2) {
        int indexOf;
        int i = 0;
        int length = str == null ? 0 : str.length();
        if (length > 0 && (indexOf = indexOf(str, 0)) >= 0) {
            if (str2 != null) {
                i = str2.length();
            }
            a(indexOf, indexOf + length, length, str2, i);
        }
        return this;
    }

    public StrBuilder replaceAll(StrMatcher strMatcher, String str) {
        return replace(strMatcher, str, 0, this.size, -1);
    }

    public StrBuilder replaceFirst(StrMatcher strMatcher, String str) {
        return replace(strMatcher, str, 0, this.size, 1);
    }

    public StrBuilder replace(StrMatcher strMatcher, String str, int i, int i2, int i3) {
        return a(strMatcher, str, i, validateRange(i, i2), i3);
    }

    private StrBuilder a(StrMatcher strMatcher, String str, int i, int i2, int i3) {
        if (strMatcher == null || this.size == 0) {
            return this;
        }
        int length = str == null ? 0 : str.length();
        char[] cArr = this.buffer;
        int i4 = i3;
        int i5 = i2;
        int i6 = i;
        while (i6 < i5 && i4 != 0) {
            int isMatch = strMatcher.isMatch(cArr, i6, i, i5);
            if (isMatch > 0) {
                a(i6, i6 + isMatch, isMatch, str, length);
                i5 = (i5 - isMatch) + length;
                i6 = (i6 + length) - 1;
                if (i4 > 0) {
                    i4--;
                }
            }
            i6++;
        }
        return this;
    }

    public StrBuilder reverse() {
        int i = this.size;
        if (i == 0) {
            return this;
        }
        int i2 = i / 2;
        char[] cArr = this.buffer;
        int i3 = 0;
        int i4 = i - 1;
        while (i3 < i2) {
            char c2 = cArr[i3];
            cArr[i3] = cArr[i4];
            cArr[i4] = c2;
            i3++;
            i4--;
        }
        return this;
    }

    public StrBuilder trim() {
        int i = this.size;
        if (i == 0) {
            return this;
        }
        char[] cArr = this.buffer;
        int i2 = 0;
        while (i2 < i && cArr[i2] <= ' ') {
            i2++;
        }
        while (i2 < i && cArr[i - 1] <= ' ') {
            i--;
        }
        int i3 = this.size;
        if (i < i3) {
            delete(i, i3);
        }
        if (i2 > 0) {
            delete(0, i2);
        }
        return this;
    }

    public boolean startsWith(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return true;
        }
        if (length > this.size) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (this.buffer[i] != str.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean endsWith(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return true;
        }
        int i = this.size;
        if (length > i) {
            return false;
        }
        int i2 = i - length;
        int i3 = 0;
        while (i3 < length) {
            if (this.buffer[i2] != str.charAt(i3)) {
                return false;
            }
            i3++;
            i2++;
        }
        return true;
    }

    public String substring(int i) {
        return substring(i, this.size);
    }

    public String substring(int i, int i2) {
        return new String(this.buffer, i, validateRange(i, i2) - i);
    }

    public String leftString(int i) {
        if (i <= 0) {
            return "";
        }
        int i2 = this.size;
        if (i >= i2) {
            return new String(this.buffer, 0, i2);
        }
        return new String(this.buffer, 0, i);
    }

    public String rightString(int i) {
        if (i <= 0) {
            return "";
        }
        int i2 = this.size;
        if (i >= i2) {
            return new String(this.buffer, 0, i2);
        }
        return new String(this.buffer, i2 - i, i);
    }

    public String midString(int i, int i2) {
        int i3;
        if (i < 0) {
            i = 0;
        }
        if (i2 <= 0 || i >= (i3 = this.size)) {
            return "";
        }
        if (i3 <= i + i2) {
            return new String(this.buffer, i, i3 - i);
        }
        return new String(this.buffer, i, i2);
    }

    public boolean contains(char c2) {
        char[] cArr = this.buffer;
        for (int i = 0; i < this.size; i++) {
            if (cArr[i] == c2) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(String str) {
        return indexOf(str, 0) >= 0;
    }

    public boolean contains(StrMatcher strMatcher) {
        return indexOf(strMatcher, 0) >= 0;
    }

    public int indexOf(char c2) {
        return indexOf(c2, 0);
    }

    public int indexOf(char c2, int i) {
        if (i < 0) {
            i = 0;
        }
        if (i >= this.size) {
            return -1;
        }
        char[] cArr = this.buffer;
        while (i < this.size) {
            if (cArr[i] == c2) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0037, code lost:
        r10 = r10 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int indexOf(java.lang.String r9, int r10) {
        /*
            r8 = this;
            r0 = 0
            if (r10 >= 0) goto L_0x0004
            r10 = r0
        L_0x0004:
            r1 = -1
            if (r9 == 0) goto L_0x003f
            int r2 = r8.size
            if (r10 < r2) goto L_0x000c
            goto L_0x003f
        L_0x000c:
            int r2 = r9.length()
            r3 = 1
            if (r2 != r3) goto L_0x001c
            char r9 = r9.charAt(r0)
            int r9 = r8.indexOf(r9, r10)
            return r9
        L_0x001c:
            if (r2 != 0) goto L_0x001f
            return r10
        L_0x001f:
            int r4 = r8.size
            if (r2 <= r4) goto L_0x0024
            return r1
        L_0x0024:
            char[] r5 = r8.buffer
            int r4 = r4 - r2
            int r4 = r4 + r3
        L_0x0028:
            if (r10 >= r4) goto L_0x003e
            r3 = r0
        L_0x002b:
            if (r3 >= r2) goto L_0x003d
            char r6 = r9.charAt(r3)
            int r7 = r10 + r3
            char r7 = r5[r7]
            if (r6 == r7) goto L_0x003a
            int r10 = r10 + 1
            goto L_0x0028
        L_0x003a:
            int r3 = r3 + 1
            goto L_0x002b
        L_0x003d:
            return r10
        L_0x003e:
            return r1
        L_0x003f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.text.StrBuilder.indexOf(java.lang.String, int):int");
    }

    public int indexOf(StrMatcher strMatcher) {
        return indexOf(strMatcher, 0);
    }

    public int indexOf(StrMatcher strMatcher, int i) {
        int i2;
        if (i < 0) {
            i = 0;
        }
        if (strMatcher == null || i >= (i2 = this.size)) {
            return -1;
        }
        char[] cArr = this.buffer;
        for (int i3 = i; i3 < i2; i3++) {
            if (strMatcher.isMatch(cArr, i3, i, i2) > 0) {
                return i3;
            }
        }
        return -1;
    }

    public int lastIndexOf(char c2) {
        return lastIndexOf(c2, this.size - 1);
    }

    public int lastIndexOf(char c2, int i) {
        int i2 = this.size;
        if (i >= i2) {
            i = i2 - 1;
        }
        if (i < 0) {
            return -1;
        }
        while (i >= 0) {
            if (this.buffer[i] == c2) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, this.size - 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0036, code lost:
        r9 = r9 - 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int lastIndexOf(java.lang.String r8, int r9) {
        /*
            r7 = this;
            int r0 = r7.size
            r1 = 1
            if (r9 < r0) goto L_0x0007
            int r9 = r0 + (-1)
        L_0x0007:
            r0 = -1
            if (r8 == 0) goto L_0x0041
            if (r9 >= 0) goto L_0x000d
            goto L_0x0041
        L_0x000d:
            int r2 = r8.length()
            if (r2 <= 0) goto L_0x003d
            int r3 = r7.size
            if (r2 > r3) goto L_0x003d
            r3 = 0
            if (r2 != r1) goto L_0x0023
            char r8 = r8.charAt(r3)
            int r8 = r7.lastIndexOf(r8, r9)
            return r8
        L_0x0023:
            int r9 = r9 - r2
            int r9 = r9 + r1
        L_0x0025:
            if (r9 < 0) goto L_0x0040
            r1 = r3
        L_0x0028:
            if (r1 >= r2) goto L_0x003c
            char r4 = r8.charAt(r1)
            char[] r5 = r7.buffer
            int r6 = r9 + r1
            char r5 = r5[r6]
            if (r4 == r5) goto L_0x0039
            int r9 = r9 + (-1)
            goto L_0x0025
        L_0x0039:
            int r1 = r1 + 1
            goto L_0x0028
        L_0x003c:
            return r9
        L_0x003d:
            if (r2 != 0) goto L_0x0040
            return r9
        L_0x0040:
            return r0
        L_0x0041:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.text.StrBuilder.lastIndexOf(java.lang.String, int):int");
    }

    public int lastIndexOf(StrMatcher strMatcher) {
        return lastIndexOf(strMatcher, this.size);
    }

    public int lastIndexOf(StrMatcher strMatcher, int i) {
        int i2 = this.size;
        if (i >= i2) {
            i = i2 - 1;
        }
        if (strMatcher == null || i < 0) {
            return -1;
        }
        char[] cArr = this.buffer;
        int i3 = i + 1;
        while (i >= 0) {
            if (strMatcher.isMatch(cArr, i, 0, i3) > 0) {
                return i;
            }
            i--;
        }
        return -1;
    }

    public StrTokenizer asTokenizer() {
        return new b(this);
    }

    public Reader asReader() {
        return new a(this);
    }

    public Writer asWriter() {
        return new c(this);
    }

    public boolean equalsIgnoreCase(StrBuilder strBuilder) {
        if (this == strBuilder) {
            return true;
        }
        int i = this.size;
        if (i != strBuilder.size) {
            return false;
        }
        char[] cArr = this.buffer;
        char[] cArr2 = strBuilder.buffer;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            char c2 = cArr[i2];
            char c3 = cArr2[i2];
            if (!(c2 == c3 || Character.toUpperCase(c2) == Character.toUpperCase(c3))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(StrBuilder strBuilder) {
        if (this == strBuilder) {
            return true;
        }
        int i = this.size;
        if (i != strBuilder.size) {
            return false;
        }
        char[] cArr = this.buffer;
        char[] cArr2 = strBuilder.buffer;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (cArr[i2] != cArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (obj instanceof StrBuilder) {
            return equals((StrBuilder) obj);
        }
        return false;
    }

    public int hashCode() {
        char[] cArr = this.buffer;
        int i = 0;
        for (int i2 = this.size - 1; i2 >= 0; i2--) {
            i = (i * 31) + cArr[i2];
        }
        return i;
    }

    public String toString() {
        return new String(this.buffer, 0, this.size);
    }

    public StringBuffer toStringBuffer() {
        StringBuffer stringBuffer = new StringBuffer(this.size);
        stringBuffer.append(this.buffer, 0, this.size);
        return stringBuffer;
    }

    public Object clone() throws CloneNotSupportedException {
        StrBuilder strBuilder = (StrBuilder) super.clone();
        strBuilder.buffer = new char[this.buffer.length];
        char[] cArr = this.buffer;
        System.arraycopy(cArr, 0, strBuilder.buffer, 0, cArr.length);
        return strBuilder;
    }

    protected int validateRange(int i, int i2) {
        if (i >= 0) {
            int i3 = this.size;
            if (i2 > i3) {
                i2 = i3;
            }
            if (i <= i2) {
                return i2;
            }
            throw new StringIndexOutOfBoundsException("end < start");
        }
        throw new StringIndexOutOfBoundsException(i);
    }

    protected void validateIndex(int i) {
        if (i < 0 || i > this.size) {
            throw new StringIndexOutOfBoundsException(i);
        }
    }

    /* loaded from: classes5.dex */
    class b extends StrTokenizer {
        private final StrBuilder a;

        b(StrBuilder strBuilder) {
            this.a = strBuilder;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.apache.commons.lang.text.StrTokenizer
        public List tokenize(char[] cArr, int i, int i2) {
            if (cArr == null) {
                return super.tokenize(this.a.buffer, 0, this.a.size());
            }
            return super.tokenize(cArr, i, i2);
        }

        @Override // org.apache.commons.lang.text.StrTokenizer
        public String getContent() {
            String content = super.getContent();
            return content == null ? this.a.toString() : content;
        }
    }

    /* loaded from: classes5.dex */
    class a extends Reader {
        private int a;
        private int b;
        private final StrBuilder c;

        @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Reader
        public boolean markSupported() {
            return true;
        }

        a(StrBuilder strBuilder) {
            this.c = strBuilder;
        }

        @Override // java.io.Reader
        public int read() {
            if (!ready()) {
                return -1;
            }
            StrBuilder strBuilder = this.c;
            int i = this.a;
            this.a = i + 1;
            return strBuilder.charAt(i);
        }

        @Override // java.io.Reader
        public int read(char[] cArr, int i, int i2) {
            int i3;
            if (i < 0 || i2 < 0 || i > cArr.length || (i3 = i + i2) > cArr.length || i3 < 0) {
                throw new IndexOutOfBoundsException();
            } else if (i2 == 0) {
                return 0;
            } else {
                if (this.a >= this.c.size()) {
                    return -1;
                }
                if (this.a + i2 > this.c.size()) {
                    i2 = this.c.size() - this.a;
                }
                StrBuilder strBuilder = this.c;
                int i4 = this.a;
                strBuilder.getChars(i4, i4 + i2, cArr, i);
                this.a += i2;
                return i2;
            }
        }

        @Override // java.io.Reader
        public long skip(long j) {
            if (this.a + j > this.c.size()) {
                j = this.c.size() - this.a;
            }
            if (j < 0) {
                return 0L;
            }
            this.a = (int) (this.a + j);
            return j;
        }

        @Override // java.io.Reader
        public boolean ready() {
            return this.a < this.c.size();
        }

        @Override // java.io.Reader
        public void mark(int i) {
            this.b = this.a;
        }

        @Override // java.io.Reader
        public void reset() {
            this.a = this.b;
        }
    }

    /* loaded from: classes5.dex */
    class c extends Writer {
        private final StrBuilder a;

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        c(StrBuilder strBuilder) {
            this.a = strBuilder;
        }

        @Override // java.io.Writer
        public void write(int i) {
            this.a.append((char) i);
        }

        @Override // java.io.Writer
        public void write(char[] cArr) {
            this.a.append(cArr);
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            this.a.append(cArr, i, i2);
        }

        @Override // java.io.Writer
        public void write(String str) {
            this.a.append(str);
        }

        @Override // java.io.Writer
        public void write(String str, int i, int i2) {
            this.a.append(str, i, i2);
        }
    }
}
