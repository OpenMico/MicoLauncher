package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map;
import kotlin.jvm.internal.CharCompanionObject;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ArrayBasedUnicodeEscaper extends UnicodeEscaper {
    private final char[][] a;
    private final int b;
    private final int c;
    private final int d;
    private final char e;
    private final char f;

    protected abstract char[] escapeUnsafe(int i);

    protected ArrayBasedUnicodeEscaper(Map<Character, String> map, int i, int i2, @NullableDecl String str) {
        this(ArrayBasedEscaperMap.create(map), i, i2, str);
    }

    protected ArrayBasedUnicodeEscaper(ArrayBasedEscaperMap arrayBasedEscaperMap, int i, int i2, @NullableDecl String str) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        this.a = arrayBasedEscaperMap.a();
        this.b = this.a.length;
        if (i2 < i) {
            i2 = -1;
            i = Integer.MAX_VALUE;
        }
        this.c = i;
        this.d = i2;
        if (i >= 55296) {
            this.e = CharCompanionObject.MAX_VALUE;
            this.f = (char) 0;
            return;
        }
        this.e = (char) i;
        this.f = (char) Math.min(i2, 55295);
    }

    @Override // com.google.common.escape.UnicodeEscaper, com.google.common.escape.Escaper
    public final String escape(String str) {
        Preconditions.checkNotNull(str);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if ((charAt < this.b && this.a[charAt] != null) || charAt > this.f || charAt < this.e) {
                return escapeSlow(str, i);
            }
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.escape.UnicodeEscaper
    public final char[] escape(int i) {
        char[] cArr;
        if (i < this.b && (cArr = this.a[i]) != null) {
            return cArr;
        }
        if (i < this.c || i > this.d) {
            return escapeUnsafe(i);
        }
        return null;
    }

    @Override // com.google.common.escape.UnicodeEscaper
    protected final int nextEscapeIndex(CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            char charAt = charSequence.charAt(i);
            if ((charAt < this.b && this.a[charAt] != null) || charAt > this.f || charAt < this.e) {
                break;
            }
            i++;
        }
        return i;
    }
}
