package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import com.blankj.utilcode.constant.CacheConstants;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/* loaded from: classes.dex */
public final class JSONScanner extends JSONLexerBase {
    private final int len;
    private final String text;

    static boolean checkDate(char c, char c2, char c3, char c4, char c5, char c6, int i, int i2) {
        if (c < '0' || c > '9' || c2 < '0' || c2 > '9' || c3 < '0' || c3 > '9' || c4 < '0' || c4 > '9') {
            return false;
        }
        if (c5 == '0') {
            if (c6 < '1' || c6 > '9') {
                return false;
            }
        } else if (c5 != '1') {
            return false;
        } else {
            if (!(c6 == '0' || c6 == '1' || c6 == '2')) {
                return false;
            }
        }
        if (i == 48) {
            return i2 >= 49 && i2 <= 57;
        }
        if (i == 49 || i == 50) {
            return i2 >= 48 && i2 <= 57;
        }
        if (i == 51) {
            return i2 == 48 || i2 == 49;
        }
        return false;
    }

    private boolean checkTime(char c, char c2, char c3, char c4, char c5, char c6) {
        if (c == '0') {
            if (c2 < '0' || c2 > '9') {
                return false;
            }
        } else if (c == '1') {
            if (c2 < '0' || c2 > '9') {
                return false;
            }
        } else if (c != '2' || c2 < '0' || c2 > '4') {
            return false;
        }
        if (c3 < '0' || c3 > '5') {
            if (!(c3 == '6' && c4 == '0')) {
                return false;
            }
        } else if (c4 < '0' || c4 > '9') {
            return false;
        }
        return (c5 < '0' || c5 > '5') ? c5 == '6' && c6 == '0' : c6 >= '0' && c6 <= '9';
    }

    public JSONScanner(String str) {
        this(str, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(String str, int i) {
        super(i);
        this.text = str;
        this.len = this.text.length();
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int i) {
        return i >= this.len ? JSONLexer.EOI : this.text.charAt(i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int i = this.bp + 1;
        this.bp = i;
        char charAt = i >= this.len ? JSONLexer.EOI : this.text.charAt(i);
        this.ch = charAt;
        return charAt;
    }

    public JSONScanner(char[] cArr, int i) {
        this(cArr, i, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] cArr, int i, int i2) {
        this(new String(cArr, 0, i), i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void copyTo(int i, int i2, char[] cArr) {
        this.text.getChars(i, i2 + i, cArr, 0);
    }

    static boolean charArrayCompare(String str, int i, char[] cArr) {
        int length = cArr.length;
        if (length + i > str.length()) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (cArr[i2] != str.charAt(i + i2)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] cArr) {
        return charArrayCompare(this.text, this.bp, cArr);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char c, int i) {
        return this.text.indexOf(c, i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int i, int i2, int i3, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, i, i2, i3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        if (this.token == 26) {
            int i = this.np + 1;
            int i2 = this.sp;
            if (i2 % 2 == 0) {
                byte[] bArr = new byte[i2 / 2];
                for (int i3 = 0; i3 < bArr.length; i3++) {
                    int i4 = (i3 * 2) + i;
                    char charAt = this.text.charAt(i4);
                    char charAt2 = this.text.charAt(i4 + 1);
                    char c = '0';
                    int i5 = charAt - (charAt <= '9' ? '0' : '7');
                    if (charAt2 > '9') {
                        c = '7';
                    }
                    bArr[i3] = (byte) ((i5 << 4) | (charAt2 - c));
                }
                return bArr;
            }
            throw new JSONException("illegal state. " + i2);
        } else if (!this.hasSpecial) {
            return IOUtils.decodeBase64(this.text, this.np + 1, this.sp);
        } else {
            return IOUtils.decodeBase64(new String(this.sbuf, 0, this.sp));
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        if (!this.hasSpecial) {
            return subString(this.np + 1, this.sp);
        }
        return new String(this.sbuf, 0, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int i, int i2) {
        if (!ASMUtils.IS_ANDROID) {
            return this.text.substring(i, i2 + i);
        }
        if (i2 < this.sbuf.length) {
            this.text.getChars(i, i + i2, this.sbuf, 0);
            return new String(this.sbuf, 0, i2);
        }
        char[] cArr = new char[i2];
        this.text.getChars(i, i2 + i, cArr, 0);
        return new String(cArr);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char[] sub_chars(int i, int i2) {
        if (!ASMUtils.IS_ANDROID || i2 >= this.sbuf.length) {
            char[] cArr = new char[i2];
            this.text.getChars(i, i2 + i, cArr, 0);
            return cArr;
        }
        this.text.getChars(i, i2 + i, this.sbuf, 0);
        return this.sbuf;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        char charAt = charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (charAt == 'L' || charAt == 'S' || charAt == 'B' || charAt == 'F' || charAt == 'D') {
            i--;
        }
        return subString(this.np, i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        char charAt = charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (charAt == 'L' || charAt == 'S' || charAt == 'B' || charAt == 'F' || charAt == 'D') {
            i--;
        }
        int i2 = this.np;
        if (i < this.sbuf.length) {
            this.text.getChars(i2, i2 + i, this.sbuf, 0);
            return new BigDecimal(this.sbuf, 0, i);
        }
        char[] cArr = new char[i];
        this.text.getChars(i2, i + i2, cArr, 0);
        return new BigDecimal(cArr);
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    public boolean scanISO8601DateIfMatch(boolean z) {
        return scanISO8601DateIfMatch(z, this.len - this.bp);
    }

    private boolean scanISO8601DateIfMatch(boolean z, int i) {
        char c;
        char c2;
        boolean z2;
        char c3;
        char c4;
        char c5;
        char c6;
        int i2;
        int i3;
        int i4;
        int i5;
        char c7;
        char c8;
        char c9;
        char c10;
        char c11;
        char c12;
        char c13;
        char c14;
        char c15;
        char c16;
        int i6;
        int i7;
        int i8;
        char c17;
        int i9;
        char charAt;
        char c18;
        char c19;
        char charAt2;
        int i10;
        int i11;
        char charAt3;
        char charAt4;
        if (i < 8) {
            return false;
        }
        char charAt5 = charAt(this.bp);
        char charAt6 = charAt(this.bp + 1);
        char charAt7 = charAt(this.bp + 2);
        char charAt8 = charAt(this.bp + 3);
        char charAt9 = charAt(this.bp + 4);
        char c20 = 5;
        char charAt10 = charAt(this.bp + 5);
        char charAt11 = charAt(this.bp + 6);
        char charAt12 = charAt(this.bp + 7);
        if (!z) {
            if (i > 13) {
                char charAt13 = charAt((this.bp + i) - 1);
                char charAt14 = charAt((this.bp + i) - 2);
                if (charAt5 == '/' && charAt6 == 'D' && charAt7 == 'a' && charAt8 == 't' && charAt9 == 'e' && charAt10 == '(' && charAt13 == '/' && charAt14 == ')') {
                    int i12 = -1;
                    for (int i13 = 6; i13 < i; i13++) {
                        char charAt15 = charAt(this.bp + i13);
                        if (charAt15 != '+') {
                            if (charAt15 < '0' || charAt15 > '9') {
                                break;
                            }
                        } else {
                            i12 = i13;
                        }
                    }
                    if (i12 == -1) {
                        return false;
                    }
                    int i14 = this.bp + 6;
                    long parseLong = Long.parseLong(subString(i14, (this.bp + i12) - i14));
                    this.calendar = Calendar.getInstance(this.timeZone, this.locale);
                    this.calendar.setTimeInMillis(parseLong);
                    this.token = 5;
                    return true;
                }
                c20 = 5;
            } else {
                c20 = 5;
            }
        }
        if (i == 8 || i == 14) {
            c = charAt6;
            z2 = false;
            c2 = '9';
        } else {
            if (i == 16) {
                char charAt16 = charAt(this.bp + 10);
                if (charAt16 == 'T') {
                    c = charAt6;
                    z2 = false;
                    c2 = '9';
                } else if (charAt16 == ' ') {
                    c = charAt6;
                    z2 = false;
                    c2 = '9';
                }
            }
            if (i == 17 && charAt(this.bp + 6) != '-') {
                c = charAt6;
                z2 = false;
                c2 = '9';
            } else if (i < 9) {
                return false;
            } else {
                char charAt17 = charAt(this.bp + 8);
                char charAt18 = charAt(this.bp + 9);
                if ((charAt9 == '-' && charAt12 == '-') || (charAt9 == '/' && charAt12 == '/')) {
                    if (charAt18 == ' ') {
                        c16 = charAt10;
                        c15 = charAt11;
                        c13 = charAt17;
                        i6 = 9;
                        c14 = '0';
                        charAt17 = charAt7;
                    } else {
                        c15 = charAt11;
                        c14 = charAt17;
                        c13 = charAt18;
                        i6 = 10;
                        charAt17 = charAt7;
                        c16 = charAt10;
                    }
                } else if (charAt9 == '-' && charAt11 == '-') {
                    if (charAt17 == ' ') {
                        charAt17 = charAt7;
                        c15 = charAt10;
                        c13 = charAt12;
                        i6 = 8;
                        c16 = '0';
                        c14 = '0';
                    } else {
                        c15 = charAt10;
                        c14 = charAt12;
                        c13 = charAt17;
                        i6 = 9;
                        c16 = '0';
                        charAt17 = charAt7;
                    }
                } else if ((charAt7 == '.' && charAt10 == '.') || (charAt7 == '-' && charAt10 == '-')) {
                    c15 = charAt9;
                    c14 = charAt5;
                    c13 = charAt6;
                    charAt5 = charAt11;
                    charAt6 = charAt12;
                    i6 = 10;
                    charAt8 = charAt18;
                    c16 = charAt8;
                } else if (charAt17 == 'T') {
                    charAt17 = charAt7;
                    c16 = charAt9;
                    c15 = charAt10;
                    c14 = charAt11;
                    c13 = charAt12;
                    i6 = 8;
                } else if (charAt9 != 24180 && charAt9 != 45380) {
                    return false;
                } else {
                    if (charAt12 == 26376 || charAt12 == 50900) {
                        if (charAt18 == 26085 || charAt18 == 51068) {
                            c16 = charAt10;
                            c15 = charAt11;
                            c13 = charAt17;
                            i6 = 10;
                            c14 = '0';
                            charAt17 = charAt7;
                        } else if (charAt(this.bp + 10) != 26085 && charAt(this.bp + 10) != 51068) {
                            return false;
                        } else {
                            i6 = 11;
                            c15 = charAt11;
                            c14 = charAt17;
                            c13 = charAt18;
                            charAt17 = charAt7;
                            c16 = charAt10;
                        }
                    } else if (charAt11 != 26376 && charAt11 != 50900) {
                        return false;
                    } else {
                        if (charAt17 == 26085 || charAt17 == 51068) {
                            charAt17 = charAt7;
                            c15 = charAt10;
                            c13 = charAt12;
                            i6 = 10;
                            c16 = '0';
                            c14 = '0';
                        } else if (charAt18 != 26085 && charAt18 != 51068) {
                            return false;
                        } else {
                            c15 = charAt10;
                            c14 = charAt12;
                            c13 = charAt17;
                            i6 = 10;
                            c16 = '0';
                            charAt17 = charAt7;
                        }
                    }
                }
                if (!checkDate(charAt5, charAt6, charAt17, charAt8, c16, c15, c14, c13)) {
                    return false;
                }
                setCalendar(charAt5, charAt6, charAt17, charAt8, c16, c15, c14, c13);
                char charAt19 = charAt(this.bp + i6);
                if (charAt19 == 'T' && i == 16 && i6 == 8 && charAt(this.bp + 15) == 'Z') {
                    char charAt20 = charAt(this.bp + i6 + 1);
                    char charAt21 = charAt(this.bp + i6 + 2);
                    char charAt22 = charAt(this.bp + i6 + 3);
                    char charAt23 = charAt(this.bp + i6 + 4);
                    char charAt24 = charAt(this.bp + i6 + 5);
                    char charAt25 = charAt(this.bp + i6 + 6);
                    if (!checkTime(charAt20, charAt21, charAt22, charAt23, charAt24, charAt25)) {
                        return false;
                    }
                    setTime(charAt20, charAt21, charAt22, charAt23, charAt24, charAt25);
                    this.calendar.set(14, 0);
                    if (this.calendar.getTimeZone().getRawOffset() != 0) {
                        String[] availableIDs = TimeZone.getAvailableIDs(0);
                        if (availableIDs.length > 0) {
                            this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs[0]));
                        }
                    }
                    this.token = 5;
                    return true;
                } else if (charAt19 == 'T' || (charAt19 == ' ' && !z)) {
                    if (!(i >= i6 + 9 && charAt(this.bp + i6 + 3) == ':' && charAt(this.bp + i6 + 6) == ':')) {
                        return false;
                    }
                    char charAt26 = charAt(this.bp + i6 + 1);
                    char charAt27 = charAt(this.bp + i6 + 2);
                    char charAt28 = charAt(this.bp + i6 + 4);
                    char charAt29 = charAt(this.bp + i6 + 5);
                    char charAt30 = charAt(this.bp + i6 + 7);
                    char charAt31 = charAt(this.bp + i6 + 8);
                    if (!checkTime(charAt26, charAt27, charAt28, charAt29, charAt30, charAt31)) {
                        return false;
                    }
                    setTime(charAt26, charAt27, charAt28, charAt29, charAt30, charAt31);
                    int i15 = -1;
                    if (charAt(this.bp + i6 + 9) == '.') {
                        int i16 = i6 + 11;
                        if (i < i16 || (charAt2 = charAt(this.bp + i6 + 10)) < '0' || charAt2 > '9') {
                            return false;
                        }
                        int i17 = charAt2 - '0';
                        if (i <= i16 || (charAt4 = charAt(this.bp + i6 + 11)) < '0' || charAt4 > '9') {
                            i10 = i17;
                            i11 = 1;
                        } else {
                            i10 = (i17 * 10) + (charAt4 - '0');
                            i11 = 2;
                        }
                        if (i11 != 2 || (charAt3 = charAt(this.bp + i6 + 12)) < '0' || charAt3 > '9') {
                            i15 = i11;
                            i7 = i10;
                        } else {
                            i7 = (i10 * 10) + (charAt3 - '0');
                            i15 = 3;
                        }
                    } else {
                        i7 = 0;
                    }
                    this.calendar.set(14, i7);
                    char charAt32 = charAt(this.bp + i6 + 10 + i15);
                    if (charAt32 == ' ') {
                        int i18 = i15 + 1;
                        i8 = i18;
                        c17 = charAt(this.bp + i6 + 10 + i18);
                    } else {
                        i8 = i15;
                        c17 = charAt32;
                    }
                    if (c17 == '+' || c17 == '-') {
                        char charAt33 = charAt(this.bp + i6 + 10 + i8 + 1);
                        if (charAt33 < '0' || charAt33 > '1' || (charAt = charAt(this.bp + i6 + 10 + i8 + 2)) < '0' || charAt > '9') {
                            return false;
                        }
                        char charAt34 = charAt(this.bp + i6 + 10 + i8 + 3);
                        if (charAt34 == ':') {
                            char charAt35 = charAt(this.bp + i6 + 10 + i8 + 4);
                            char charAt36 = charAt(this.bp + i6 + 10 + i8 + 5);
                            if (charAt35 == '4' && charAt36 == '5') {
                                if (!(charAt33 == '1' && (charAt == '2' || charAt == '3'))) {
                                    if (charAt33 != '0') {
                                        return false;
                                    }
                                    if (!(charAt == '5' || charAt == '8')) {
                                        return false;
                                    }
                                }
                            } else if (!((charAt35 == '0' || charAt35 == '3') && charAt36 == '0')) {
                                return false;
                            }
                            c18 = charAt36;
                            i9 = 6;
                            c19 = charAt35;
                        } else if (charAt34 == '0') {
                            char charAt37 = charAt(this.bp + i6 + 10 + i8 + 4);
                            if (!(charAt37 == '0' || charAt37 == '3')) {
                                return false;
                            }
                            c19 = charAt37;
                            i9 = 5;
                            c18 = '0';
                        } else if (charAt34 == '3' && charAt(this.bp + i6 + 10 + i8 + 4) == '0') {
                            c19 = '3';
                            i9 = 5;
                            c18 = '0';
                        } else if (charAt34 == '4' && charAt(this.bp + i6 + 10 + i8 + 4) == '5') {
                            c19 = '4';
                            c18 = '5';
                            i9 = 5;
                        } else {
                            i9 = 3;
                            c19 = '0';
                            c18 = '0';
                        }
                        setTimeZone(c17, charAt33, charAt, c19, c18);
                    } else if (c17 == 'Z') {
                        if (this.calendar.getTimeZone().getRawOffset() != 0) {
                            String[] availableIDs2 = TimeZone.getAvailableIDs(0);
                            if (availableIDs2.length > 0) {
                                this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs2[0]));
                            }
                        }
                        i9 = 1;
                    } else {
                        i9 = 0;
                    }
                    int i19 = i6 + 10 + i8 + i9;
                    char charAt38 = charAt(this.bp + i19);
                    if (!(charAt38 == 26 || charAt38 == '\"')) {
                        return false;
                    }
                    int i20 = this.bp + i19;
                    this.bp = i20;
                    this.ch = charAt(i20);
                    this.token = 5;
                    return true;
                } else if (charAt19 == '\"' || charAt19 == 26 || charAt19 == 26085 || charAt19 == 51068) {
                    this.calendar.set(11, 0);
                    this.calendar.set(12, 0);
                    this.calendar.set(13, 0);
                    this.calendar.set(14, 0);
                    int i21 = this.bp + i6;
                    this.bp = i21;
                    this.ch = charAt(i21);
                    this.token = 5;
                    return true;
                } else if ((charAt19 != '+' && charAt19 != '-') || this.len != i6 + 6 || charAt(this.bp + i6 + 3) != ':' || charAt(this.bp + i6 + 4) != '0' || charAt(this.bp + i6 + 5) != '0') {
                    return false;
                } else {
                    setTime('0', '0', '0', '0', '0', '0');
                    this.calendar.set(14, 0);
                    setTimeZone(charAt19, charAt(this.bp + i6 + 1), charAt(this.bp + i6 + 2));
                    return true;
                }
            }
        }
        if (z) {
            return z2;
        }
        char charAt39 = charAt(this.bp + 8);
        boolean z3 = charAt9 == '-' && charAt12 == '-';
        boolean z4 = z3 && i == 16;
        boolean z5 = z3 && i == 17;
        if (z5 || z4) {
            c3 = charAt(this.bp + 9);
            c6 = charAt10;
            c5 = charAt11;
            c4 = charAt39;
        } else if (charAt9 == '-' && charAt11 == '-') {
            c5 = charAt10;
            c3 = charAt12;
            c6 = '0';
            c4 = '0';
        } else {
            c6 = charAt9;
            c5 = charAt10;
            c4 = charAt11;
            c3 = charAt12;
        }
        if (!checkDate(charAt5, c, charAt7, charAt8, c6, c5, c4, c3)) {
            return false;
        }
        setCalendar(charAt5, c, charAt7, charAt8, c6, c5, c4, c3);
        if (i != 8) {
            char charAt40 = charAt(this.bp + 9);
            char charAt41 = charAt(this.bp + 10);
            char charAt42 = charAt(this.bp + 11);
            char charAt43 = charAt(this.bp + 12);
            char charAt44 = charAt(this.bp + 13);
            if (!(z5 && charAt41 == 'T' && charAt44 == ':' && charAt(this.bp + 16) == 'Z') && (!z4 || !((charAt41 == ' ' || charAt41 == 'T') && charAt44 == ':'))) {
                c11 = charAt40;
                c10 = charAt41;
                c9 = charAt42;
                c8 = charAt43;
                c7 = charAt44;
            } else {
                c10 = charAt(this.bp + 14);
                c9 = charAt(this.bp + 15);
                charAt39 = charAt42;
                c11 = charAt43;
                c8 = '0';
                c7 = '0';
            }
            if (!checkTime(charAt39, c11, c10, c9, c8, c7)) {
                return false;
            }
            if (i != 17 || z5) {
                i5 = 0;
                c12 = '0';
            } else {
                char charAt45 = charAt(this.bp + 14);
                char charAt46 = charAt(this.bp + 15);
                char charAt47 = charAt(this.bp + 16);
                if (charAt45 < '0' || charAt45 > c2 || charAt46 < '0' || charAt46 > c2 || charAt47 < '0' || charAt47 > c2) {
                    return false;
                }
                i5 = ((charAt45 - '0') * 100) + ((charAt46 - '0') * 10) + (charAt47 - '0');
                c12 = '0';
            }
            i3 = ((charAt39 - '0') * 10) + (c11 - c12);
            i2 = (c9 - c12) + ((c10 - c12) * 10);
            i4 = ((c8 - c12) * 10) + (c7 - c12);
        } else {
            i2 = 0;
            i5 = 0;
            i4 = 0;
            i3 = 0;
        }
        this.calendar.set(11, i3);
        this.calendar.set(12, i2);
        this.calendar.set(13, i4);
        this.calendar.set(14, i5);
        this.token = 5;
        return true;
    }

    protected void setTime(char c, char c2, char c3, char c4, char c5, char c6) {
        this.calendar.set(11, ((c - '0') * 10) + (c2 - '0'));
        this.calendar.set(12, ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(13, ((c5 - '0') * 10) + (c6 - '0'));
    }

    protected void setTimeZone(char c, char c2, char c3) {
        setTimeZone(c, c2, c3, '0', '0');
    }

    protected void setTimeZone(char c, char c2, char c3, char c4, char c5) {
        int i = ((((c2 - '0') * 10) + (c3 - '0')) * CacheConstants.HOUR * 1000) + ((((c4 - '0') * 10) + (c5 - '0')) * 60 * 1000);
        if (c == '-') {
            i = -i;
        }
        if (this.calendar.getTimeZone().getRawOffset() != i) {
            this.calendar.setTimeZone(new SimpleTimeZone(i, Integer.toString(i)));
        }
    }

    private void setCalendar(char c, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
        this.calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar.set(1, ((c - '0') * 1000) + ((c2 - '0') * 100) + ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(2, (((c5 - '0') * 10) + (c6 - '0')) - 1);
        this.calendar.set(5, ((c7 - '0') * 10) + (c8 - '0'));
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        if (this.bp != this.len) {
            return this.ch == 26 && this.bp + 1 >= this.len;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0068, code lost:
        if (r3 != '.') goto L_0x006d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006a, code lost:
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006c, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006d, code lost:
        if (r15 >= 0) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x006f, code lost:
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0071, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0072, code lost:
        if (r6 == false) goto L_0x0081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0074, code lost:
        if (r3 == '\"') goto L_0x0079;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0076, code lost:
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0078, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0079, code lost:
        r11 = r11 + 1;
        r3 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0085, code lost:
        if (r3 == ',') goto L_0x009c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0087, code lost:
        if (r3 != '}') goto L_0x008a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x008e, code lost:
        if (isWhitespace(r3) == false) goto L_0x0099;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0090, code lost:
        r11 = r11 + 1;
        r3 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0099, code lost:
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x009b, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x009c, code lost:
        r11 = r11 - 1;
        r14.bp = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a1, code lost:
        if (r3 != ',') goto L_0x00b7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00a3, code lost:
        r0 = r14.bp + 1;
        r14.bp = r0;
        r14.ch = charAt(r0);
        r14.matchStat = 3;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00b3, code lost:
        if (r7 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00b6, code lost:
        return -r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00b7, code lost:
        if (r3 != '}') goto L_0x011d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b9, code lost:
        r14.bp = r11;
        r3 = r14.bp + 1;
        r14.bp = r3;
        r3 = charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00c4, code lost:
        if (r3 != ',') goto L_0x00d4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c6, code lost:
        r14.token = 16;
        r0 = r14.bp + 1;
        r14.bp = r0;
        r14.ch = charAt(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d6, code lost:
        if (r3 != ']') goto L_0x00e8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d8, code lost:
        r14.token = 15;
        r0 = r14.bp + 1;
        r14.bp = r0;
        r14.ch = charAt(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00e8, code lost:
        if (r3 != '}') goto L_0x00fa;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00ea, code lost:
        r14.token = 13;
        r0 = r14.bp + 1;
        r14.bp = r0;
        r14.ch = charAt(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00fc, code lost:
        if (r3 != 26) goto L_0x0106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00fe, code lost:
        r14.token = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0102, code lost:
        r14.matchStat = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x010a, code lost:
        if (isWhitespace(r3) == false) goto L_0x0116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x010c, code lost:
        r3 = r14.bp + 1;
        r14.bp = r3;
        r3 = charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0116, code lost:
        r14.bp = r1;
        r14.ch = r2;
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x011c, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x011d, code lost:
        if (r7 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0120, code lost:
        return -r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:?, code lost:
        return r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:?, code lost:
        return r15;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int scanFieldInt(char[] r15) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldInt(char[]):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldString(char[] cArr) {
        this.matchStat = 0;
        int i = this.bp;
        char c = this.ch;
        while (!charArrayCompare(this.text, this.bp, cArr)) {
            if (isWhitespace(this.ch)) {
                next();
            } else {
                this.matchStat = -2;
                return stringDefaultValue();
            }
        }
        int length = this.bp + cArr.length;
        int i2 = length + 1;
        if (charAt(length) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int indexOf = indexOf('\"', i2);
        if (indexOf != -1) {
            String subString = subString(i2, indexOf - i2);
            if (subString.indexOf(92) != -1) {
                while (true) {
                    int i3 = 0;
                    for (int i4 = indexOf - 1; i4 >= 0 && charAt(i4) == '\\'; i4--) {
                        i3++;
                    }
                    if (i3 % 2 == 0) {
                        break;
                    }
                    indexOf = indexOf('\"', indexOf + 1);
                }
                int length2 = indexOf - ((this.bp + cArr.length) + 1);
                subString = readString(sub_chars(this.bp + cArr.length + 1, length2), length2);
            }
            char charAt = charAt(indexOf + 1);
            while (charAt != ',' && charAt != '}') {
                if (isWhitespace(charAt)) {
                    indexOf++;
                    charAt = charAt(indexOf + 1);
                } else {
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
            }
            this.bp = indexOf + 1;
            this.ch = charAt;
            if (charAt == ',') {
                int i5 = this.bp + 1;
                this.bp = i5;
                this.ch = charAt(i5);
                this.matchStat = 3;
                return subString;
            }
            int i6 = this.bp + 1;
            this.bp = i6;
            char charAt2 = charAt(i6);
            if (charAt2 == ',') {
                this.token = 16;
                int i7 = this.bp + 1;
                this.bp = i7;
                this.ch = charAt(i7);
            } else if (charAt2 == ']') {
                this.token = 15;
                int i8 = this.bp + 1;
                this.bp = i8;
                this.ch = charAt(i8);
            } else if (charAt2 == '}') {
                this.token = 13;
                int i9 = this.bp + 1;
                this.bp = i9;
                this.ch = charAt(i9);
            } else if (charAt2 == 26) {
                this.token = 20;
            } else {
                this.bp = i;
                this.ch = c;
                this.matchStat = -1;
                return stringDefaultValue();
            }
            this.matchStat = 4;
            return subString;
        }
        throw new JSONException("unclosed str");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanFieldDate(char[] cArr) {
        Date date;
        char c;
        long j;
        int i;
        boolean z = false;
        this.matchStat = 0;
        int i2 = this.bp;
        char c2 = this.ch;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = this.bp + cArr.length;
        int i3 = length + 1;
        char charAt = charAt(length);
        if (charAt == '\"') {
            int indexOf = indexOf('\"', i3);
            if (indexOf != -1) {
                this.bp = i3;
                if (scanISO8601DateIfMatch(false, indexOf - i3)) {
                    date = this.calendar.getTime();
                    char charAt2 = charAt(indexOf + 1);
                    this.bp = i2;
                    while (charAt2 != ',' && charAt2 != '}') {
                        if (isWhitespace(charAt2)) {
                            indexOf++;
                            charAt2 = charAt(indexOf + 1);
                        } else {
                            this.matchStat = -1;
                            return null;
                        }
                    }
                    this.bp = indexOf + 1;
                    this.ch = charAt2;
                    c = charAt2;
                } else {
                    this.bp = i2;
                    this.matchStat = -1;
                    return null;
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            char c3 = '9';
            char c4 = '0';
            if (charAt == '-' || (charAt >= '0' && charAt <= '9')) {
                if (charAt == '-') {
                    i3++;
                    charAt = charAt(i3);
                    z = true;
                }
                if (charAt < '0' || charAt > '9') {
                    c = charAt;
                    j = 0;
                } else {
                    j = charAt - '0';
                    while (true) {
                        i = i3 + 1;
                        c = charAt(i3);
                        if (c < c4 || c > c3) {
                            break;
                        }
                        j = (j * 10) + (c - '0');
                        i3 = i;
                        c3 = '9';
                        c4 = '0';
                    }
                    if (c == ',' || c == '}') {
                        this.bp = i - 1;
                    }
                }
                if (j < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (z) {
                    j = -j;
                }
                date = new Date(j);
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (c == ',') {
            int i4 = this.bp + 1;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 3;
            this.token = 16;
            return date;
        }
        int i5 = this.bp + 1;
        this.bp = i5;
        char charAt3 = charAt(i5);
        if (charAt3 == ',') {
            this.token = 16;
            int i6 = this.bp + 1;
            this.bp = i6;
            this.ch = charAt(i6);
        } else if (charAt3 == ']') {
            this.token = 15;
            int i7 = this.bp + 1;
            this.bp = i7;
            this.ch = charAt(i7);
        } else if (charAt3 == '}') {
            this.token = 13;
            int i8 = this.bp + 1;
            this.bp = i8;
            this.ch = charAt(i8);
        } else if (charAt3 == 26) {
            this.token = 20;
        } else {
            this.bp = i2;
            this.ch = c2;
            this.matchStat = -1;
            return null;
        }
        this.matchStat = 4;
        return date;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i = length + 1;
        if (charAt(length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i2 = i + 1;
            char charAt = charAt(i);
            if (charAt == '\"') {
                this.bp = i2;
                char charAt2 = charAt(this.bp);
                this.ch = charAt2;
                while (charAt2 != ',') {
                    if (charAt2 == '}') {
                        next();
                        skipWhitespace();
                        char current = getCurrent();
                        if (current == ',') {
                            this.token = 16;
                            int i3 = this.bp + 1;
                            this.bp = i3;
                            this.ch = charAt(i3);
                        } else if (current == ']') {
                            this.token = 15;
                            int i4 = this.bp + 1;
                            this.bp = i4;
                            this.ch = charAt(i4);
                        } else if (current == '}') {
                            this.token = 13;
                            int i5 = this.bp + 1;
                            this.bp = i5;
                            this.ch = charAt(i5);
                        } else if (current == 26) {
                            this.token = 20;
                        } else {
                            this.matchStat = -1;
                            return 0L;
                        }
                        this.matchStat = 4;
                        return j;
                    } else if (isWhitespace(charAt2)) {
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        charAt2 = charAt(i6);
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                }
                int i7 = this.bp + 1;
                this.bp = i7;
                this.ch = charAt(i7);
                this.matchStat = 3;
                return j;
            } else if (i2 > this.len) {
                this.matchStat = -1;
                return 0L;
            } else {
                j = (j ^ charAt) * 1099511628211L;
                i = i2;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00dd, code lost:
        if (r1 != ']') goto L_0x00ef;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e3, code lost:
        if (r3.size() != 0) goto L_0x00ef;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e5, code lost:
        r1 = r9 + 1;
        r5 = r3;
        r3 = charAt(r9);
        r2 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ef, code lost:
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f1, code lost:
        return null;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r18, java.lang.Class<?> r19) {
        /*
            Method dump skipped, instructions count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldLong(char[] cArr) {
        int i;
        char c;
        boolean z;
        int i2;
        char charAt;
        int i3;
        char c2;
        this.matchStat = 0;
        int i4 = this.bp;
        char c3 = this.ch;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i5 = length + 1;
        char charAt2 = charAt(length);
        boolean z2 = charAt2 == '\"';
        if (z2) {
            i = i5 + 1;
            c = charAt(i5);
        } else {
            i = i5;
            c = charAt2;
        }
        if (c == '-') {
            i++;
            z = true;
            c = charAt(i);
        } else {
            z = false;
        }
        if (c >= '0') {
            char c4 = '9';
            if (c <= '9') {
                long j = c - '0';
                while (true) {
                    i2 = i + 1;
                    charAt = charAt(i);
                    if (charAt < '0' || charAt > c4) {
                        break;
                    }
                    j = (j * 10) + (charAt - '0');
                    i = i2;
                    c4 = '9';
                }
                if (charAt == '.') {
                    this.matchStat = -1;
                    return 0L;
                }
                if (!z2) {
                    i3 = i2;
                    c2 = charAt;
                } else if (charAt != '\"') {
                    this.matchStat = -1;
                    return 0L;
                } else {
                    i3 = i2 + 1;
                    c2 = charAt(i2);
                }
                if (c2 == ',' || c2 == '}') {
                    this.bp = i3 - 1;
                }
                if (!(j >= 0 || (j == Long.MIN_VALUE && z))) {
                    this.bp = i4;
                    this.ch = c3;
                    this.matchStat = -1;
                    return 0L;
                }
                while (c2 != ',') {
                    if (c2 == '}') {
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        char charAt3 = charAt(i6);
                        while (true) {
                            if (charAt3 == ',') {
                                this.token = 16;
                                int i7 = this.bp + 1;
                                this.bp = i7;
                                this.ch = charAt(i7);
                                break;
                            } else if (charAt3 == ']') {
                                this.token = 15;
                                int i8 = this.bp + 1;
                                this.bp = i8;
                                this.ch = charAt(i8);
                                break;
                            } else if (charAt3 == '}') {
                                this.token = 13;
                                int i9 = this.bp + 1;
                                this.bp = i9;
                                this.ch = charAt(i9);
                                break;
                            } else if (charAt3 == 26) {
                                this.token = 20;
                                break;
                            } else if (isWhitespace(charAt3)) {
                                int i10 = this.bp + 1;
                                this.bp = i10;
                                charAt3 = charAt(i10);
                            } else {
                                this.bp = i4;
                                this.ch = c3;
                                this.matchStat = -1;
                                return 0L;
                            }
                        }
                        this.matchStat = 4;
                        return z ? -j : j;
                    } else if (isWhitespace(c2)) {
                        this.bp = i3;
                        i3++;
                        c2 = charAt(i3);
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                }
                int i11 = this.bp + 1;
                this.bp = i11;
                this.ch = charAt(i11);
                this.matchStat = 3;
                this.token = 16;
                return z ? -j : j;
            }
        }
        this.bp = i4;
        this.ch = c3;
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean scanFieldBoolean(char[] cArr) {
        char c;
        int i;
        char c2;
        boolean z;
        int i2;
        int i3;
        int i4;
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return false;
        }
        int i5 = this.bp;
        int length = this.bp + cArr.length;
        int i6 = length + 1;
        char charAt = charAt(length);
        boolean z2 = charAt == '\"';
        if (z2) {
            i = i6 + 1;
            c = charAt(i6);
        } else {
            i = i6;
            c = charAt;
        }
        if (c == 't') {
            int i7 = i + 1;
            if (charAt(i) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int i8 = i7 + 1;
            if (charAt(i7) != 'u') {
                this.matchStat = -1;
                return false;
            }
            int i9 = i8 + 1;
            if (charAt(i8) != 'e') {
                this.matchStat = -1;
                return false;
            }
            if (z2) {
                i9++;
                if (charAt(i9) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            }
            this.bp = i9;
            c2 = charAt(this.bp);
            z = true;
        } else if (c == 'f') {
            int i10 = i + 1;
            if (charAt(i) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int i11 = i10 + 1;
            if (charAt(i10) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int i12 = i11 + 1;
            if (charAt(i11) != 's') {
                this.matchStat = -1;
                return false;
            }
            int i13 = i12 + 1;
            if (charAt(i12) != 'e') {
                this.matchStat = -1;
                return false;
            }
            if (z2) {
                i4 = i13 + 1;
                if (charAt(i13) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                i4 = i13;
            }
            this.bp = i4;
            c2 = charAt(this.bp);
            z = false;
        } else if (c == '1') {
            if (z2) {
                i3 = i + 1;
                if (charAt(i) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                i3 = i;
            }
            this.bp = i3;
            c2 = charAt(this.bp);
            z = true;
        } else if (c == '0') {
            if (z2) {
                i2 = i + 1;
                if (charAt(i) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                i2 = i;
            }
            this.bp = i2;
            c2 = charAt(this.bp);
            z = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        while (true) {
            if (c2 == ',') {
                int i14 = this.bp + 1;
                this.bp = i14;
                this.ch = charAt(i14);
                this.matchStat = 3;
                this.token = 16;
                break;
            } else if (c2 == '}') {
                int i15 = this.bp + 1;
                this.bp = i15;
                char charAt2 = charAt(i15);
                while (true) {
                    if (charAt2 == ',') {
                        this.token = 16;
                        int i16 = this.bp + 1;
                        this.bp = i16;
                        this.ch = charAt(i16);
                        break;
                    } else if (charAt2 == ']') {
                        this.token = 15;
                        int i17 = this.bp + 1;
                        this.bp = i17;
                        this.ch = charAt(i17);
                        break;
                    } else if (charAt2 == '}') {
                        this.token = 13;
                        int i18 = this.bp + 1;
                        this.bp = i18;
                        this.ch = charAt(i18);
                        break;
                    } else if (charAt2 == 26) {
                        this.token = 20;
                        break;
                    } else if (isWhitespace(charAt2)) {
                        int i19 = this.bp + 1;
                        this.bp = i19;
                        charAt2 = charAt(i19);
                    } else {
                        this.matchStat = -1;
                        return false;
                    }
                }
                this.matchStat = 4;
            } else if (isWhitespace(c2)) {
                int i20 = this.bp + 1;
                this.bp = i20;
                c2 = charAt(i20);
            } else {
                this.bp = i5;
                charAt(this.bp);
                this.matchStat = -1;
                return false;
            }
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0082, code lost:
        if (r4 != '.') goto L_0x0087;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0084, code lost:
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0086, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0087, code lost:
        if (r7 == false) goto L_0x0095;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0089, code lost:
        if (r4 == '\"') goto L_0x008e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x008b, code lost:
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008d, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x008e, code lost:
        r2 = r13 + 1;
        r4 = charAt(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0095, code lost:
        r2 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0096, code lost:
        if (r3 >= 0) goto L_0x009b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0098, code lost:
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x009a, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009d, code lost:
        if (r4 != r17) goto L_0x00b2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009f, code lost:
        r16.bp = r2;
        r16.ch = charAt(r16.bp);
        r16.matchStat = 3;
        r16.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00ae, code lost:
        if (r8 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00b1, code lost:
        return -r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b6, code lost:
        if (isWhitespace(r4) == false) goto L_0x00c2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b8, code lost:
        r2 = r2 + 1;
        r4 = charAt(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c2, code lost:
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c4, code lost:
        if (r8 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c7, code lost:
        return -r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:?, code lost:
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:?, code lost:
        return r3;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int scanInt(char r17) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanInt(char):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public double scanDouble(char c) {
        int i;
        char charAt;
        long j;
        int i2;
        int i3;
        double d;
        int i4;
        this.matchStat = 0;
        int i5 = this.bp;
        int i6 = i5 + 1;
        char charAt2 = charAt(i5);
        boolean z = charAt2 == '\"';
        if (z) {
            i6++;
            charAt2 = charAt(i6);
        }
        boolean z2 = charAt2 == '-';
        if (z2) {
            i6++;
            charAt2 = charAt(i6);
        }
        if (charAt2 >= '0') {
            char c2 = '9';
            if (charAt2 <= '9') {
                long j2 = charAt2 - '0';
                while (true) {
                    i = i6 + 1;
                    charAt = charAt(i6);
                    if (charAt < '0' || charAt > '9') {
                        break;
                    }
                    j2 = (j2 * 10) + (charAt - '0');
                    i6 = i;
                }
                if (charAt == '.') {
                    int i7 = i + 1;
                    char charAt3 = charAt(i);
                    if (charAt3 < '0' || charAt3 > '9') {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    j2 = (j2 * 10) + (charAt3 - '0');
                    j = 10;
                    while (true) {
                        i4 = i7 + 1;
                        charAt = charAt(i7);
                        if (charAt < '0' || charAt > c2) {
                            break;
                        }
                        j2 = (j2 * 10) + (charAt - '0');
                        j *= 10;
                        i7 = i4;
                        c2 = '9';
                    }
                    i = i4;
                } else {
                    j = 1;
                }
                boolean z3 = charAt == 'e' || charAt == 'E';
                if (z3) {
                    int i8 = i + 1;
                    char charAt4 = charAt(i);
                    if (charAt4 == '+' || charAt4 == '-') {
                        i = i8 + 1;
                        charAt = charAt(i8);
                    } else {
                        i = i8;
                        charAt = charAt4;
                    }
                    while (charAt >= '0' && charAt <= '9') {
                        i++;
                        charAt = charAt(i);
                    }
                }
                if (!z) {
                    i3 = this.bp;
                    i2 = (i - i3) - 1;
                } else if (charAt != '\"') {
                    this.matchStat = -1;
                    return 0.0d;
                } else {
                    int i9 = i + 1;
                    char charAt5 = charAt(i);
                    i3 = this.bp + 1;
                    i2 = (i9 - i3) - 2;
                    i = i9;
                    charAt = charAt5;
                }
                if (z3 || i2 >= 18) {
                    d = Double.parseDouble(subString(i3, i2));
                } else {
                    d = j2 / j;
                    if (z2) {
                        d = -d;
                    }
                }
                if (charAt == c) {
                    this.bp = i;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return d;
                }
                this.matchStat = -1;
                return d;
            }
        }
        if (charAt2 == 'n') {
            int i10 = i6 + 1;
            if (charAt(i6) == 'u') {
                int i11 = i10 + 1;
                if (charAt(i10) == 'l') {
                    int i12 = i11 + 1;
                    if (charAt(i11) == 'l') {
                        this.matchStat = 5;
                        int i13 = i12 + 1;
                        char charAt6 = charAt(i12);
                        if (z && charAt6 == '\"') {
                            i13++;
                            charAt6 = charAt(i13);
                        }
                        while (charAt6 != ',') {
                            if (charAt6 == ']') {
                                this.bp = i13;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0.0d;
                            } else if (isWhitespace(charAt6)) {
                                i13++;
                                charAt6 = charAt(i13);
                            } else {
                                this.matchStat = -1;
                                return 0.0d;
                            }
                        }
                        this.bp = i13;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0.0d;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0.0d;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char c) {
        int i;
        char charAt;
        boolean z = false;
        this.matchStat = 0;
        int i2 = this.bp;
        int i3 = i2 + 1;
        char charAt2 = charAt(i2);
        boolean z2 = charAt2 == '\"';
        if (z2) {
            i3++;
            charAt2 = charAt(i3);
        }
        boolean z3 = charAt2 == '-';
        if (z3) {
            i3++;
            charAt2 = charAt(i3);
        }
        char c2 = '0';
        if (charAt2 < '0' || charAt2 > '9') {
            if (charAt2 == 'n') {
                int i4 = i3 + 1;
                if (charAt(i3) == 'u') {
                    int i5 = i4 + 1;
                    if (charAt(i4) == 'l') {
                        int i6 = i5 + 1;
                        if (charAt(i5) == 'l') {
                            this.matchStat = 5;
                            int i7 = i6 + 1;
                            char charAt3 = charAt(i6);
                            if (z2 && charAt3 == '\"') {
                                i7++;
                                charAt3 = charAt(i7);
                            }
                            while (charAt3 != ',') {
                                if (charAt3 == ']') {
                                    this.bp = i7;
                                    this.ch = charAt(this.bp);
                                    this.matchStat = 5;
                                    this.token = 15;
                                    return 0L;
                                } else if (isWhitespace(charAt3)) {
                                    i7++;
                                    charAt3 = charAt(i7);
                                } else {
                                    this.matchStat = -1;
                                    return 0L;
                                }
                            }
                            this.bp = i7;
                            this.ch = charAt(this.bp);
                            this.matchStat = 5;
                            this.token = 16;
                            return 0L;
                        }
                    }
                }
            }
            this.matchStat = -1;
            return 0L;
        }
        long j = charAt2 - '0';
        while (true) {
            i = i3 + 1;
            charAt = charAt(i3);
            if (charAt < c2 || charAt > '9') {
                break;
            }
            j = (j * 10) + (charAt - '0');
            i3 = i;
            c2 = '0';
        }
        if (charAt == '.') {
            this.matchStat = -1;
            return 0L;
        }
        if (z2) {
            if (charAt != '\"') {
                this.matchStat = -1;
                return 0L;
            }
            i++;
            charAt = charAt(i);
        }
        if (j >= 0 || (j == Long.MIN_VALUE && z3)) {
            z = true;
        }
        if (!z) {
            this.matchStat = -1;
            return 0L;
        }
        while (charAt != c) {
            if (isWhitespace(charAt)) {
                i++;
                charAt = charAt(i);
            } else {
                this.matchStat = -1;
                return j;
            }
        }
        this.bp = i;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        this.token = 16;
        return z3 ? -j : j;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanDate(char c) {
        char c2;
        Date date;
        boolean z;
        int i;
        long j;
        int i2;
        this.matchStat = 0;
        int i3 = this.bp;
        char c3 = this.ch;
        int i4 = this.bp;
        int i5 = i4 + 1;
        char charAt = charAt(i4);
        if (charAt == '\"') {
            int indexOf = indexOf('\"', i5);
            if (indexOf != -1) {
                this.bp = i5;
                if (scanISO8601DateIfMatch(false, indexOf - i5)) {
                    date = this.calendar.getTime();
                    c2 = charAt(indexOf + 1);
                    this.bp = i3;
                    while (c2 != ',' && c2 != ']') {
                        if (isWhitespace(c2)) {
                            indexOf++;
                            c2 = charAt(indexOf + 1);
                        } else {
                            this.bp = i3;
                            this.ch = c3;
                            this.matchStat = -1;
                            return null;
                        }
                    }
                    this.bp = indexOf + 1;
                    this.ch = c2;
                } else {
                    this.bp = i3;
                    this.ch = c3;
                    this.matchStat = -1;
                    return null;
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            char c4 = '9';
            char c5 = '0';
            if (charAt == '-' || (charAt >= '0' && charAt <= '9')) {
                if (charAt == '-') {
                    i = i5 + 1;
                    charAt = charAt(i5);
                    z = true;
                } else {
                    z = false;
                    i = i5;
                }
                if (charAt < '0' || charAt > '9') {
                    c2 = charAt;
                    j = 0;
                } else {
                    j = charAt - '0';
                    while (true) {
                        i2 = i + 1;
                        c2 = charAt(i);
                        if (c2 < c5 || c2 > c4) {
                            break;
                        }
                        j = (j * 10) + (c2 - '0');
                        i = i2;
                        c4 = '9';
                        c5 = '0';
                    }
                    if (c2 == ',' || c2 == ']') {
                        this.bp = i2 - 1;
                    }
                }
                if (j < 0) {
                    this.bp = i3;
                    this.ch = c3;
                    this.matchStat = -1;
                    return null;
                }
                if (z) {
                    j = -j;
                }
                date = new Date(j);
            } else {
                if (charAt == 'n') {
                    int i6 = i5 + 1;
                    if (charAt(i5) == 'u') {
                        int i7 = i6 + 1;
                        if (charAt(i6) == 'l') {
                            int i8 = i7 + 1;
                            if (charAt(i7) == 'l') {
                                c2 = charAt(i8);
                                this.bp = i8;
                                date = null;
                            }
                        }
                    }
                }
                this.bp = i3;
                this.ch = c3;
                this.matchStat = -1;
                return null;
            }
        }
        if (c2 == ',') {
            int i9 = this.bp + 1;
            this.bp = i9;
            this.ch = charAt(i9);
            this.matchStat = 3;
            return date;
        }
        int i10 = this.bp + 1;
        this.bp = i10;
        char charAt2 = charAt(i10);
        if (charAt2 == ',') {
            this.token = 16;
            int i11 = this.bp + 1;
            this.bp = i11;
            this.ch = charAt(i11);
        } else if (charAt2 == ']') {
            this.token = 15;
            int i12 = this.bp + 1;
            this.bp = i12;
            this.ch = charAt(i12);
        } else if (charAt2 == '}') {
            this.token = 13;
            int i13 = this.bp + 1;
            this.bp = i13;
            this.ch = charAt(i13);
        } else if (charAt2 == 26) {
            this.ch = JSONLexer.EOI;
            this.token = 20;
        } else {
            this.bp = i3;
            this.ch = c3;
            this.matchStat = -1;
            return null;
        }
        this.matchStat = 4;
        return date;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void arrayCopy(int i, char[] cArr, int i2, int i3) {
        this.text.getChars(i, i3 + i, cArr, i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 1;
        int i3 = 1;
        while (i < this.bp) {
            if (this.text.charAt(i) == '\n') {
                i2++;
                i3 = 1;
            }
            i++;
            i3++;
        }
        sb.append("pos ");
        sb.append(this.bp);
        sb.append(", line ");
        sb.append(i2);
        sb.append(", column ");
        sb.append(i3);
        if (this.text.length() < 65535) {
            sb.append(this.text);
        } else {
            sb.append(this.text.substring(0, 65535));
        }
        return sb.toString();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String[] scanFieldStringArray(char[] cArr, int i, SymbolTable symbolTable) {
        char c;
        int i2;
        int i3 = this.bp;
        char c2 = this.ch;
        while (isWhitespace(this.ch)) {
            next();
        }
        if (cArr != null) {
            this.matchStat = 0;
            if (!charArrayCompare(cArr)) {
                this.matchStat = -2;
                return null;
            }
            int length = this.bp + cArr.length;
            int i4 = length + 1;
            char charAt = this.text.charAt(length);
            while (isWhitespace(charAt)) {
                i4++;
                charAt = this.text.charAt(i4);
            }
            if (charAt == ':') {
                i2 = i4 + 1;
                c = this.text.charAt(i4);
                while (isWhitespace(c)) {
                    i2++;
                    c = this.text.charAt(i2);
                }
            } else {
                this.matchStat = -1;
                return null;
            }
        } else {
            i2 = this.bp + 1;
            c = this.ch;
        }
        if (c == '[') {
            this.bp = i2;
            this.ch = this.text.charAt(this.bp);
            String[] strArr = i >= 0 ? new String[i] : new String[4];
            int i5 = 0;
            while (true) {
                if (isWhitespace(this.ch)) {
                    next();
                } else if (this.ch != '\"') {
                    this.bp = i3;
                    this.ch = c2;
                    this.matchStat = -1;
                    return null;
                } else {
                    String scanSymbol = scanSymbol(symbolTable, '\"');
                    if (i5 == strArr.length) {
                        String[] strArr2 = new String[strArr.length + (strArr.length >> 1) + 1];
                        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                        strArr = strArr2;
                    }
                    int i6 = i5 + 1;
                    strArr[i5] = scanSymbol;
                    while (isWhitespace(this.ch)) {
                        next();
                    }
                    if (this.ch == ',') {
                        next();
                        i5 = i6;
                    } else {
                        if (strArr.length != i6) {
                            String[] strArr3 = new String[i6];
                            System.arraycopy(strArr, 0, strArr3, 0, i6);
                            strArr = strArr3;
                        }
                        while (isWhitespace(this.ch)) {
                            next();
                        }
                        if (this.ch == ']') {
                            next();
                            return strArr;
                        }
                        this.bp = i3;
                        this.ch = c2;
                        this.matchStat = -1;
                        return null;
                    }
                }
            }
        } else if (c != 'n' || !this.text.startsWith("ull", this.bp + 1)) {
            this.matchStat = -1;
            return null;
        } else {
            this.bp += 4;
            this.ch = this.text.charAt(this.bp);
            return null;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean matchField2(char[] cArr) {
        while (isWhitespace(this.ch)) {
            next();
        }
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = this.bp + cArr.length;
        int i = length + 1;
        char charAt = this.text.charAt(length);
        while (isWhitespace(charAt)) {
            i++;
            charAt = this.text.charAt(i);
        }
        if (charAt == ':') {
            this.bp = i;
            this.ch = charAt(this.bp);
            return true;
        }
        this.matchStat = -2;
        return false;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject() {
        skipObject(false);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject(boolean z) {
        int i = this.bp;
        boolean z2 = false;
        int i2 = 0;
        while (i < this.text.length()) {
            char charAt = this.text.charAt(i);
            if (charAt == '\\') {
                if (i < this.len - 1) {
                    i++;
                } else {
                    this.ch = charAt;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
            } else if (charAt == '\"') {
                z2 = !z2;
            } else if (charAt == '{') {
                if (!z2) {
                    i2++;
                }
            } else if (charAt == '}' && !z2 && i2 - 1 == -1) {
                this.bp = i + 1;
                int i3 = this.bp;
                int length = this.text.length();
                char c = JSONLexer.EOI;
                if (i3 == length) {
                    this.ch = JSONLexer.EOI;
                    this.token = 20;
                    return;
                }
                this.ch = this.text.charAt(this.bp);
                if (this.ch == ',') {
                    this.token = 16;
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    if (i4 < this.text.length()) {
                        c = this.text.charAt(i4);
                    }
                    this.ch = c;
                    return;
                } else if (this.ch == '}') {
                    this.token = 13;
                    next();
                    return;
                } else if (this.ch == ']') {
                    this.token = 15;
                    next();
                    return;
                } else {
                    nextToken(16);
                    return;
                }
            }
            i++;
        }
        for (int i5 = 0; i5 < this.bp; i5++) {
            if (i5 < this.text.length() && this.text.charAt(i5) == ' ') {
                i++;
            }
        }
        if (i == this.text.length()) {
            throw new JSONException("illegal str, " + info());
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipArray() {
        skipArray(false);
    }

    public final void skipArray(boolean z) {
        int i = this.bp;
        boolean z2 = false;
        int i2 = 0;
        while (i < this.text.length()) {
            char charAt = this.text.charAt(i);
            if (charAt == '\\') {
                if (i < this.len - 1) {
                    i++;
                } else {
                    this.ch = charAt;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
            } else if (charAt == '\"') {
                z2 = !z2;
            } else if (charAt != '[') {
                char c = JSONLexer.EOI;
                if (charAt == '{' && z) {
                    int i3 = this.bp + 1;
                    this.bp = i3;
                    if (i3 < this.text.length()) {
                        c = this.text.charAt(i3);
                    }
                    this.ch = c;
                    skipObject(z);
                } else if (charAt == ']' && !z2 && i2 - 1 == -1) {
                    this.bp = i + 1;
                    if (this.bp == this.text.length()) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    }
                    this.ch = this.text.charAt(this.bp);
                    nextToken(16);
                    return;
                }
            } else if (!z2) {
                i2++;
            }
            i++;
        }
        if (i == this.text.length()) {
            throw new JSONException("illegal str, " + info());
        }
    }

    public final void skipString() {
        if (this.ch == '\"') {
            int i = this.bp;
            while (true) {
                i++;
                if (i < this.text.length()) {
                    char charAt = this.text.charAt(i);
                    if (charAt == '\\') {
                        if (i < this.len - 1) {
                            i++;
                        }
                    } else if (charAt == '\"') {
                        String str = this.text;
                        int i2 = i + 1;
                        this.bp = i2;
                        this.ch = str.charAt(i2);
                        return;
                    }
                } else {
                    throw new JSONException("unclosed str");
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean seekArrayToItem(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index must > 0, but " + i);
        } else if (this.token == 20) {
            return false;
        } else {
            if (this.token == 14) {
                int i2 = 0;
                while (true) {
                    boolean z = true;
                    if (i2 < i) {
                        skipWhitespace();
                        if (this.ch == '\"' || this.ch == '\'') {
                            skipString();
                            if (this.ch == ',') {
                                next();
                            } else if (this.ch == ']') {
                                next();
                                nextToken(16);
                                return false;
                            } else {
                                throw new JSONException("illegal json.");
                            }
                        } else {
                            if (this.ch == '{') {
                                next();
                                this.token = 12;
                                skipObject(false);
                            } else if (this.ch == '[') {
                                next();
                                this.token = 14;
                                skipArray(false);
                            } else {
                                int i3 = this.bp + 1;
                                while (true) {
                                    if (i3 >= this.text.length()) {
                                        z = false;
                                        break;
                                    }
                                    char charAt = this.text.charAt(i3);
                                    if (charAt == ',') {
                                        this.bp = i3 + 1;
                                        this.ch = charAt(this.bp);
                                        break;
                                    } else if (charAt == ']') {
                                        this.bp = i3 + 1;
                                        this.ch = charAt(this.bp);
                                        nextToken();
                                        return false;
                                    } else {
                                        i3++;
                                    }
                                }
                                if (!z) {
                                    throw new JSONException("illegal json.");
                                }
                            }
                            if (this.token != 16) {
                                if (this.token == 15) {
                                    return false;
                                }
                                throw new UnsupportedOperationException();
                            }
                        }
                        i2++;
                    } else {
                        nextToken();
                        return true;
                    }
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int seekObjectToField(long j, boolean z) {
        if (this.token == 20) {
            return -1;
        }
        if (this.token == 13 || this.token == 15) {
            nextToken();
            return -1;
        } else if (this.token == 12 || this.token == 16) {
            while (this.ch != '}') {
                char c = this.ch;
                char c2 = JSONLexer.EOI;
                if (c == 26) {
                    return -1;
                }
                if (this.ch != '\"') {
                    skipWhitespace();
                }
                if (this.ch == '\"') {
                    long j2 = -3750763034362895579L;
                    int i = this.bp + 1;
                    while (true) {
                        if (i >= this.text.length()) {
                            break;
                        }
                        char charAt = this.text.charAt(i);
                        if (charAt == '\\') {
                            i++;
                            if (i != this.text.length()) {
                                charAt = this.text.charAt(i);
                            } else {
                                throw new JSONException("unclosed str, " + info());
                            }
                        }
                        if (charAt == '\"') {
                            this.bp = i + 1;
                            this.ch = this.bp >= this.text.length() ? (char) 26 : this.text.charAt(this.bp);
                        } else {
                            j2 = (j2 ^ charAt) * 1099511628211L;
                            i++;
                        }
                    }
                    if (j2 == j) {
                        if (this.ch != ':') {
                            skipWhitespace();
                        }
                        if (this.ch != ':') {
                            return 3;
                        }
                        int i2 = this.bp + 1;
                        this.bp = i2;
                        this.ch = i2 >= this.text.length() ? (char) 26 : this.text.charAt(i2);
                        if (this.ch == ',') {
                            int i3 = this.bp + 1;
                            this.bp = i3;
                            if (i3 < this.text.length()) {
                                c2 = this.text.charAt(i3);
                            }
                            this.ch = c2;
                            this.token = 16;
                            return 3;
                        } else if (this.ch == ']') {
                            int i4 = this.bp + 1;
                            this.bp = i4;
                            if (i4 < this.text.length()) {
                                c2 = this.text.charAt(i4);
                            }
                            this.ch = c2;
                            this.token = 15;
                            return 3;
                        } else if (this.ch == '}') {
                            int i5 = this.bp + 1;
                            this.bp = i5;
                            if (i5 < this.text.length()) {
                                c2 = this.text.charAt(i5);
                            }
                            this.ch = c2;
                            this.token = 13;
                            return 3;
                        } else if (this.ch < '0' || this.ch > '9') {
                            nextToken(2);
                            return 3;
                        } else {
                            this.sp = 0;
                            this.pos = this.bp;
                            scanNumber();
                            return 3;
                        }
                    } else {
                        if (this.ch != ':') {
                            skipWhitespace();
                        }
                        if (this.ch == ':') {
                            int i6 = this.bp + 1;
                            this.bp = i6;
                            this.ch = i6 >= this.text.length() ? (char) 26 : this.text.charAt(i6);
                            if (!(this.ch == '\"' || this.ch == '\'' || this.ch == '{' || this.ch == '[' || this.ch == '0' || this.ch == '1' || this.ch == '2' || this.ch == '3' || this.ch == '4' || this.ch == '5' || this.ch == '6' || this.ch == '7' || this.ch == '8' || this.ch == '9' || this.ch == '+' || this.ch == '-')) {
                                skipWhitespace();
                            }
                            if (this.ch == '-' || this.ch == '+' || (this.ch >= '0' && this.ch <= '9')) {
                                next();
                                while (this.ch >= '0' && this.ch <= '9') {
                                    next();
                                }
                                if (this.ch == '.') {
                                    next();
                                    while (this.ch >= '0' && this.ch <= '9') {
                                        next();
                                    }
                                }
                                if (this.ch == 'E' || this.ch == 'e') {
                                    next();
                                    if (this.ch == '-' || this.ch == '+') {
                                        next();
                                    }
                                    while (this.ch >= '0' && this.ch <= '9') {
                                        next();
                                    }
                                }
                                if (this.ch != ',') {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == '\"') {
                                skipString();
                                if (!(this.ch == ',' || this.ch == '}')) {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == 't') {
                                next();
                                if (this.ch == 'r') {
                                    next();
                                    if (this.ch == 'u') {
                                        next();
                                        if (this.ch == 'e') {
                                            next();
                                        }
                                    }
                                }
                                if (!(this.ch == ',' || this.ch == '}')) {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == 'n') {
                                next();
                                if (this.ch == 'u') {
                                    next();
                                    if (this.ch == 'l') {
                                        next();
                                        if (this.ch == 'l') {
                                            next();
                                        }
                                    }
                                }
                                if (!(this.ch == ',' || this.ch == '}')) {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == 'f') {
                                next();
                                if (this.ch == 'a') {
                                    next();
                                    if (this.ch == 'l') {
                                        next();
                                        if (this.ch == 's') {
                                            next();
                                            if (this.ch == 'e') {
                                                next();
                                            }
                                        }
                                    }
                                }
                                if (!(this.ch == ',' || this.ch == '}')) {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == '{') {
                                int i7 = this.bp + 1;
                                this.bp = i7;
                                if (i7 < this.text.length()) {
                                    c2 = this.text.charAt(i7);
                                }
                                this.ch = c2;
                                if (z) {
                                    this.token = 12;
                                    return 1;
                                }
                                skipObject(false);
                                if (this.token == 13) {
                                    return -1;
                                }
                            } else if (this.ch == '[') {
                                next();
                                if (z) {
                                    this.token = 14;
                                    return 2;
                                }
                                skipArray(false);
                                if (this.token == 13) {
                                    return -1;
                                }
                            } else {
                                throw new UnsupportedOperationException();
                            }
                        } else {
                            throw new JSONException("illegal json, " + info());
                        }
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            next();
            nextToken();
            return -1;
        } else {
            throw new UnsupportedOperationException(JSONToken.name(this.token));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x019c, code lost:
        if (r14.ch == '0') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01a2, code lost:
        if (r14.ch == '1') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01a8, code lost:
        if (r14.ch == '2') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01ae, code lost:
        if (r14.ch == '3') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01b4, code lost:
        if (r14.ch == '4') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x01ba, code lost:
        if (r14.ch == '5') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x01c0, code lost:
        if (r14.ch == '6') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x01c6, code lost:
        if (r14.ch == '7') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x01cc, code lost:
        if (r14.ch == '8') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x01d0, code lost:
        if (r14.ch == '9') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x01d4, code lost:
        if (r14.ch == '+') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01d8, code lost:
        if (r14.ch == '-') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x01da, code lost:
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x01df, code lost:
        if (r14.ch == '-') goto L_0x023b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x01e3, code lost:
        if (r14.ch == '+') goto L_0x023b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x01e7, code lost:
        if (r14.ch < '0') goto L_0x01ee;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x01eb, code lost:
        if (r14.ch > '9') goto L_0x01ee;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x01f0, code lost:
        if (r14.ch != '\"') goto L_0x0209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x01f2, code lost:
        skipString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x01f7, code lost:
        if (r14.ch == ',') goto L_0x0200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x01fb, code lost:
        if (r14.ch == '}') goto L_0x0200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x01fd, code lost:
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0202, code lost:
        if (r14.ch != ',') goto L_0x0013;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0204, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x020b, code lost:
        if (r14.ch != '{') goto L_0x0229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x020d, code lost:
        r2 = r14.bp + 1;
        r14.bp = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0219, code lost:
        if (r2 < r14.text.length()) goto L_0x021c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x021c, code lost:
        r4 = r14.text.charAt(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0222, code lost:
        r14.ch = r4;
        skipObject(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x022b, code lost:
        if (r14.ch != '[') goto L_0x0235;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x022d, code lost:
        next();
        skipArray(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x023a, code lost:
        throw new java.lang.UnsupportedOperationException();
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x023b, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0240, code lost:
        if (r14.ch < '0') goto L_0x024a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0244, code lost:
        if (r14.ch > '9') goto L_0x024a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0246, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x024e, code lost:
        if (r14.ch != '.') goto L_0x025f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0250, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0255, code lost:
        if (r14.ch < '0') goto L_0x025f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0259, code lost:
        if (r14.ch > '9') goto L_0x025f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x025b, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0263, code lost:
        if (r14.ch == 'E') goto L_0x026b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0269, code lost:
        if (r14.ch != 'e') goto L_0x0285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x026b, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0270, code lost:
        if (r14.ch == '-') goto L_0x0276;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0274, code lost:
        if (r14.ch != '+') goto L_0x0279;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0276, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x027b, code lost:
        if (r14.ch < '0') goto L_0x0285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x027f, code lost:
        if (r14.ch > '9') goto L_0x0285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0281, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0287, code lost:
        if (r14.ch == ',') goto L_0x028c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0289, code lost:
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x028e, code lost:
        if (r14.ch != ',') goto L_0x0013;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0290, code lost:
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x02af, code lost:
        throw new com.alibaba.fastjson.JSONException("illegal json, " + info());
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a6, code lost:
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a9, code lost:
        if (r8 >= r15.length) goto L_0x00b5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00af, code lost:
        if (r6 != r15[r8]) goto L_0x00b2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b2, code lost:
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b5, code lost:
        r8 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00be, code lost:
        if (r8 == (-1)) goto L_0x015d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00c2, code lost:
        if (r14.ch == ':') goto L_0x00c7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c4, code lost:
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c9, code lost:
        if (r14.ch != ':') goto L_0x0159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00cb, code lost:
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d7, code lost:
        if (r15 < r14.text.length()) goto L_0x00db;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00d9, code lost:
        r15 = 26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00db, code lost:
        r15 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00e1, code lost:
        r14.ch = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00e5, code lost:
        if (r14.ch != ',') goto L_0x0101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00e7, code lost:
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00f3, code lost:
        if (r15 < r14.text.length()) goto L_0x00f6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00f6, code lost:
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00fc, code lost:
        r14.ch = r4;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0105, code lost:
        if (r14.ch != ']') goto L_0x0123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0107, code lost:
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0113, code lost:
        if (r15 < r14.text.length()) goto L_0x0116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0116, code lost:
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x011c, code lost:
        r14.ch = r4;
        r14.token = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0125, code lost:
        if (r14.ch != '}') goto L_0x0143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0127, code lost:
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0133, code lost:
        if (r15 < r14.text.length()) goto L_0x0136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0136, code lost:
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x013c, code lost:
        r14.ch = r4;
        r14.token = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0145, code lost:
        if (r14.ch < '0') goto L_0x0155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0149, code lost:
        if (r14.ch > '9') goto L_0x0155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x014b, code lost:
        r14.sp = 0;
        r14.pos = r14.bp;
        scanNumber();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0155, code lost:
        nextToken(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0159, code lost:
        r14.matchStat = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x015c, code lost:
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x015f, code lost:
        if (r14.ch == ':') goto L_0x0164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0161, code lost:
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0166, code lost:
        if (r14.ch != ':') goto L_0x0295;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0168, code lost:
        r3 = r14.bp + 1;
        r14.bp = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0174, code lost:
        if (r3 < r14.text.length()) goto L_0x0178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0176, code lost:
        r3 = 26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0178, code lost:
        r3 = r14.text.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x017e, code lost:
        r14.ch = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x018a, code lost:
        if (r14.ch == '\"') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0190, code lost:
        if (r14.ch == '\'') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0194, code lost:
        if (r14.ch == '{') goto L_0x01dd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0198, code lost:
        if (r14.ch == '[') goto L_0x01dd;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int seekObjectToField(long[] r15) {
        /*
            Method dump skipped, instructions count: 694
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.seekObjectToField(long[]):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String scanTypeName(SymbolTable symbolTable) {
        int indexOf;
        if (!this.text.startsWith("\"@type\":\"", this.bp) || (indexOf = this.text.indexOf(34, this.bp + 9)) == -1) {
            return null;
        }
        this.bp += 9;
        int i = 0;
        for (int i2 = this.bp; i2 < indexOf; i2++) {
            i = (i * 31) + this.text.charAt(i2);
        }
        String addSymbol = addSymbol(this.bp, indexOf - this.bp, i, symbolTable);
        char charAt = this.text.charAt(indexOf + 1);
        if (!(charAt == ',' || charAt == ']')) {
            return null;
        }
        this.bp = indexOf + 2;
        this.ch = this.text.charAt(this.bp);
        return addSymbol;
    }
}
