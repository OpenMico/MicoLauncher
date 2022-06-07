package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.fasterxml.jackson.core.JsonPointer;
import com.google.android.exoplayer2.C;
import io.netty.util.internal.StringUtil;
import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

/* loaded from: classes.dex */
public abstract class JSONLexerBase implements JSONLexer, Closeable {
    protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
    protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
    protected int bp;
    protected char ch;
    protected int eofPos;
    protected int features;
    protected boolean hasSpecial;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected int token;
    private static final ThreadLocal<char[]> SBUF_LOCAL = new ThreadLocal<>();
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    protected static final int[] digits = new int[103];
    protected Calendar calendar = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;
    protected Locale locale = JSON.defaultLocale;
    public int matchStat = 0;
    protected int nanos = 0;

    public static boolean isWhitespace(char c) {
        return c <= ' ' && (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f' || c == '\b');
    }

    public abstract String addSymbol(int i, int i2, int i3, SymbolTable symbolTable);

    protected abstract void arrayCopy(int i, char[] cArr, int i2, int i3);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract byte[] bytesValue();

    protected abstract boolean charArrayCompare(char[] cArr);

    public abstract char charAt(int i);

    protected abstract void copyTo(int i, int i2, char[] cArr);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract BigDecimal decimalValue();

    public abstract int indexOf(char c, int i);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        return "";
    }

    public abstract boolean isEOF();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract char next();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String numberString();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanTypeName(SymbolTable symbolTable) {
        return null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String stringVal();

    public abstract String subString(int i, int i2);

    protected abstract char[] sub_chars(int i, int i2);

    protected void lexError(String str, Object... objArr) {
        this.token = 1;
    }

    static {
        for (int i = 48; i <= 57; i++) {
            digits[i] = i - 48;
        }
        for (int i2 = 97; i2 <= 102; i2++) {
            digits[i2] = (i2 - 97) + 10;
        }
        for (int i3 = 65; i3 <= 70; i3++) {
            digits[i3] = (i3 - 65) + 10;
        }
    }

    public JSONLexerBase(int i) {
        this.stringDefaultValue = null;
        this.features = i;
        if ((i & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
        this.sbuf = SBUF_LOCAL.get();
        if (this.sbuf == null) {
            this.sbuf = new char[512];
        }
    }

    public final int matchStat() {
        return this.matchStat;
    }

    public void setToken(int i) {
        this.token = i;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken() {
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            char c = this.ch;
            if (c == '/') {
                skipComment();
            } else if (c == '\"') {
                scanString();
                return;
            } else if (c == ',') {
                next();
                this.token = 16;
                return;
            } else if (c < '0' || c > '9') {
                char c2 = this.ch;
                if (c2 == '-') {
                    scanNumber();
                    return;
                }
                switch (c2) {
                    case '\b':
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                        next();
                        continue;
                    case '\'':
                        if (isEnabled(Feature.AllowSingleQuotes)) {
                            scanStringSingleQuote();
                            return;
                        }
                        throw new JSONException("Feature.AllowSingleQuotes is false");
                    case '(':
                        next();
                        this.token = 10;
                        return;
                    case ')':
                        next();
                        this.token = 11;
                        return;
                    case '+':
                        next();
                        scanNumber();
                        return;
                    case '.':
                        next();
                        this.token = 25;
                        return;
                    case ':':
                        next();
                        this.token = 17;
                        return;
                    case ';':
                        next();
                        this.token = 24;
                        return;
                    case 'N':
                    case 'S':
                    case 'T':
                    case 'u':
                        scanIdent();
                        return;
                    case '[':
                        next();
                        this.token = 14;
                        return;
                    case ']':
                        next();
                        this.token = 15;
                        return;
                    case 'f':
                        scanFalse();
                        return;
                    case 'n':
                        scanNullOrNew();
                        return;
                    case 't':
                        scanTrue();
                        return;
                    case 'x':
                        scanHex();
                        return;
                    case '{':
                        next();
                        this.token = 12;
                        return;
                    case '}':
                        next();
                        this.token = 13;
                        return;
                    default:
                        if (!isEOF()) {
                            char c3 = this.ch;
                            if (c3 <= 31 || c3 == 127) {
                                next();
                                continue;
                            } else {
                                lexError("illegal.char", String.valueOf((int) c3));
                                next();
                                return;
                            }
                        } else if (this.token != 20) {
                            this.token = 20;
                            int i = this.bp;
                            this.pos = i;
                            this.eofPos = i;
                            return;
                        } else {
                            throw new JSONException("EOF error");
                        }
                }
            } else {
                scanNumber();
                return;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken(int i) {
        this.sp = 0;
        while (true) {
            if (i == 2) {
                char c = this.ch;
                if (c < '0' || c > '9') {
                    char c2 = this.ch;
                    if (c2 == '\"') {
                        this.pos = this.bp;
                        scanString();
                        return;
                    } else if (c2 == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (c2 == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                } else {
                    this.pos = this.bp;
                    scanNumber();
                    return;
                }
            } else if (i == 4) {
                char c3 = this.ch;
                if (c3 == '\"') {
                    this.pos = this.bp;
                    scanString();
                    return;
                } else if (c3 < '0' || c3 > '9') {
                    char c4 = this.ch;
                    if (c4 == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (c4 == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                } else {
                    this.pos = this.bp;
                    scanNumber();
                    return;
                }
            } else if (i == 12) {
                char c5 = this.ch;
                if (c5 == '{') {
                    this.token = 12;
                    next();
                    return;
                } else if (c5 == '[') {
                    this.token = 14;
                    next();
                    return;
                }
            } else if (i != 18) {
                if (i != 20) {
                    switch (i) {
                        case 14:
                            char c6 = this.ch;
                            if (c6 == '[') {
                                this.token = 14;
                                next();
                                return;
                            } else if (c6 == '{') {
                                this.token = 12;
                                next();
                                return;
                            }
                            break;
                        case 15:
                            if (this.ch == ']') {
                                this.token = 15;
                                next();
                                return;
                            }
                            break;
                        case 16:
                            char c7 = this.ch;
                            if (c7 == ',') {
                                this.token = 16;
                                next();
                                return;
                            } else if (c7 == '}') {
                                this.token = 13;
                                next();
                                return;
                            } else if (c7 == ']') {
                                this.token = 15;
                                next();
                                return;
                            } else if (c7 == 26) {
                                this.token = 20;
                                return;
                            } else if (c7 == 'n') {
                                scanNullOrNew(false);
                                return;
                            }
                            break;
                    }
                }
                if (this.ch == 26) {
                    this.token = 20;
                    return;
                }
            } else {
                nextIdent();
                return;
            }
            char c8 = this.ch;
            if (c8 == ' ' || c8 == '\n' || c8 == '\r' || c8 == '\t' || c8 == '\f' || c8 == '\b') {
                next();
            } else {
                nextToken();
                return;
            }
        }
    }

    public final void nextIdent() {
        while (isWhitespace(this.ch)) {
            next();
        }
        char c = this.ch;
        if (c == '_' || c == '$' || Character.isLetter(c)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon() {
        nextTokenWithChar(':');
    }

    public final void nextTokenWithChar(char c) {
        this.sp = 0;
        while (true) {
            char c2 = this.ch;
            if (c2 == c) {
                next();
                nextToken();
                return;
            } else if (c2 == ' ' || c2 == '\n' || c2 == '\r' || c2 == '\t' || c2 == '\f' || c2 == '\b') {
                next();
            } else {
                throw new JSONException("not match " + c + " - " + this.ch + ", info : " + info());
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int token() {
        return this.token;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String tokenName() {
        return JSONToken.name(this.token);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int pos() {
        return this.pos;
    }

    public final String stringDefaultValue() {
        return this.stringDefaultValue;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number integerValue() throws NumberFormatException {
        long j;
        long j2;
        boolean z = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i = this.np;
        int i2 = this.sp + i;
        char c = ' ';
        char charAt = charAt(i2 - 1);
        if (charAt == 'B') {
            i2--;
            c = 'B';
        } else if (charAt == 'L') {
            i2--;
            c = 'L';
        } else if (charAt == 'S') {
            i2--;
            c = 'S';
        }
        if (charAt(this.np) == '-') {
            j = Long.MIN_VALUE;
            i++;
            z = true;
        } else {
            j = C.TIME_UNSET;
        }
        long j3 = -922337203685477580L;
        if (i < i2) {
            i++;
            j2 = -(charAt(i) - '0');
        } else {
            j2 = 0;
        }
        while (i < i2) {
            i++;
            int charAt2 = charAt(i) - '0';
            if (j2 < j3) {
                return new BigInteger(numberString());
            }
            long j4 = j2 * 10;
            long j5 = charAt2;
            if (j4 < j + j5) {
                return new BigInteger(numberString());
            }
            j2 = j4 - j5;
            j3 = -922337203685477580L;
        }
        if (!z) {
            long j6 = -j2;
            if (j6 > 2147483647L || c == 'L') {
                return Long.valueOf(j6);
            }
            if (c == 'S') {
                return Short.valueOf((short) j6);
            }
            if (c == 'B') {
                return Byte.valueOf((byte) j6);
            }
            return Integer.valueOf((int) j6);
        } else if (i <= this.np + 1) {
            throw new NumberFormatException(numberString());
        } else if (j2 < -2147483648L || c == 'L') {
            return Long.valueOf(j2);
        } else {
            if (c == 'S') {
                return Short.valueOf((short) j2);
            }
            if (c == 'B') {
                return Byte.valueOf((byte) j2);
            }
            return Integer.valueOf((int) j2);
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon(int i) {
        nextTokenWithChar(':');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public float floatValue() {
        char charAt;
        String numberString = numberString();
        float parseFloat = Float.parseFloat(numberString);
        if ((parseFloat != 0.0f && parseFloat != Float.POSITIVE_INFINITY) || (charAt = numberString.charAt(0)) <= '0' || charAt > '9') {
            return parseFloat;
        }
        throw new JSONException("float overflow : " + numberString);
    }

    public double doubleValue() {
        return Double.parseDouble(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void config(Feature feature, boolean z) {
        this.features = Feature.config(this.features, feature, z);
        if ((this.features & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(Feature feature) {
        return isEnabled(feature.mask);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(int i) {
        return (i & this.features) != 0;
    }

    public final boolean isEnabled(int i, int i2) {
        return ((this.features & i2) == 0 && (i & i2) == 0) ? false : true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final char getCurrent() {
        return this.ch;
    }

    protected void skipComment() {
        char c;
        next();
        char c2 = this.ch;
        if (c2 == '/') {
            do {
                next();
                c = this.ch;
                if (c == '\n') {
                    next();
                    return;
                }
            } while (c != 26);
        } else if (c2 == '*') {
            next();
            while (true) {
                char c3 = this.ch;
                if (c3 == 26) {
                    return;
                }
                if (c3 == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                } else {
                    next();
                }
            }
        } else {
            throw new JSONException("invalid comment");
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable) {
        skipWhitespace();
        char c = this.ch;
        if (c == '\"') {
            return scanSymbol(symbolTable, '\"');
        }
        if (c == '\'') {
            if (isEnabled(Feature.AllowSingleQuotes)) {
                return scanSymbol(symbolTable, '\'');
            }
            throw new JSONException("syntax error");
        } else if (c == '}') {
            next();
            this.token = 13;
            return null;
        } else if (c == ',') {
            next();
            this.token = 16;
            return null;
        } else if (c == 26) {
            this.token = 20;
            return null;
        } else if (isEnabled(Feature.AllowUnQuotedFieldNames)) {
            return scanSymbolUnQuoted(symbolTable);
        } else {
            throw new JSONException("syntax error");
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable, char c) {
        String str;
        this.np = this.bp;
        this.sp = 0;
        boolean z = false;
        int i = 0;
        while (true) {
            char next = next();
            if (next == c) {
                this.token = 4;
                if (!z) {
                    int i2 = this.np;
                    str = addSymbol(i2 == -1 ? 0 : i2 + 1, this.sp, i, symbolTable);
                } else {
                    str = symbolTable.addSymbol(this.sbuf, 0, this.sp, i);
                }
                this.sp = 0;
                next();
                return str;
            } else if (next == 26) {
                throw new JSONException("unclosed.str");
            } else if (next == '\\') {
                if (!z) {
                    int i3 = this.sp;
                    char[] cArr = this.sbuf;
                    if (i3 >= cArr.length) {
                        int length = cArr.length * 2;
                        if (i3 <= length) {
                            i3 = length;
                        }
                        char[] cArr2 = new char[i3];
                        char[] cArr3 = this.sbuf;
                        System.arraycopy(cArr3, 0, cArr2, 0, cArr3.length);
                        this.sbuf = cArr2;
                    }
                    arrayCopy(this.np + 1, this.sbuf, 0, this.sp);
                    z = true;
                }
                char next2 = next();
                switch (next2) {
                    case '/':
                        i = (i * 31) + 47;
                        putChar(JsonPointer.SEPARATOR);
                        continue;
                    case '0':
                        i = (i * 31) + next2;
                        putChar((char) 0);
                        continue;
                    case '1':
                        i = (i * 31) + next2;
                        putChar((char) 1);
                        continue;
                    case '2':
                        i = (i * 31) + next2;
                        putChar((char) 2);
                        continue;
                    case '3':
                        i = (i * 31) + next2;
                        putChar((char) 3);
                        continue;
                    case '4':
                        i = (i * 31) + next2;
                        putChar((char) 4);
                        continue;
                    case '5':
                        i = (i * 31) + next2;
                        putChar((char) 5);
                        continue;
                    case '6':
                        i = (i * 31) + next2;
                        putChar((char) 6);
                        continue;
                    case '7':
                        i = (i * 31) + next2;
                        putChar((char) 7);
                        continue;
                    default:
                        switch (next2) {
                            case 't':
                                i = (i * 31) + 9;
                                putChar('\t');
                                continue;
                            case 'u':
                                int parseInt = Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16);
                                i = (i * 31) + parseInt;
                                putChar((char) parseInt);
                                continue;
                            case 'v':
                                i = (i * 31) + 11;
                                putChar((char) 11);
                                continue;
                            default:
                                switch (next2) {
                                    case '\"':
                                        i = (i * 31) + 34;
                                        putChar('\"');
                                        continue;
                                    case '\'':
                                        i = (i * 31) + 39;
                                        putChar('\'');
                                        continue;
                                    case 'F':
                                    case 'f':
                                        i = (i * 31) + 12;
                                        putChar('\f');
                                        continue;
                                    case '\\':
                                        i = (i * 31) + 92;
                                        putChar('\\');
                                        continue;
                                    case 'b':
                                        i = (i * 31) + 8;
                                        putChar('\b');
                                        continue;
                                    case 'n':
                                        i = (i * 31) + 10;
                                        putChar('\n');
                                        continue;
                                    case 'r':
                                        i = (i * 31) + 13;
                                        putChar('\r');
                                        continue;
                                    case 'x':
                                        char next3 = next();
                                        this.ch = next3;
                                        char next4 = next();
                                        this.ch = next4;
                                        int[] iArr = digits;
                                        char c2 = (char) ((iArr[next3] * 16) + iArr[next4]);
                                        i = (i * 31) + c2;
                                        putChar(c2);
                                        continue;
                                        continue;
                                        continue;
                                    default:
                                        this.ch = next2;
                                        throw new JSONException("unclosed.str.lit");
                                }
                        }
                }
            } else {
                i = (i * 31) + next;
                if (!z) {
                    this.sp++;
                } else {
                    int i4 = this.sp;
                    char[] cArr4 = this.sbuf;
                    if (i4 == cArr4.length) {
                        putChar(next);
                    } else {
                        this.sp = i4 + 1;
                        cArr4[i4] = next;
                    }
                }
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void resetStringPosition() {
        this.sp = 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        boolean z = false;
        if (this.token == 1 && this.pos == 0 && this.bp == 1) {
            this.bp = 0;
        }
        boolean[] zArr = IOUtils.firstIdentifierFlags;
        int i = this.ch;
        if (i >= zArr.length || zArr[i]) {
            z = true;
        }
        if (z) {
            boolean[] zArr2 = IOUtils.identifierFlags;
            this.np = this.bp;
            this.sp = 1;
            while (true) {
                char next = next();
                if (next < zArr2.length && !zArr2[next]) {
                    break;
                }
                i = (i * 31) + next;
                this.sp++;
            }
            this.ch = charAt(this.bp);
            this.token = 18;
            if (this.sp == 4 && i == 3392903 && charAt(this.np) == 'n' && charAt(this.np + 1) == 'u' && charAt(this.np + 2) == 'l' && charAt(this.np + 3) == 'l') {
                return null;
            }
            if (symbolTable == null) {
                return subString(this.np, this.sp);
            }
            return addSymbol(this.np, this.sp, i, symbolTable);
        }
        throw new JSONException("illegal identifier : " + this.ch + info());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanString() {
        char next;
        char next2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char next3 = next();
            if (next3 == '\"') {
                this.token = 4;
                this.ch = next();
                return;
            } else if (next3 != 26) {
                boolean z = true;
                if (next3 == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        int i = this.sp;
                        char[] cArr = this.sbuf;
                        if (i >= cArr.length) {
                            int length = cArr.length * 2;
                            if (i <= length) {
                                i = length;
                            }
                            char[] cArr2 = new char[i];
                            char[] cArr3 = this.sbuf;
                            System.arraycopy(cArr3, 0, cArr2, 0, cArr3.length);
                            this.sbuf = cArr2;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char next4 = next();
                    switch (next4) {
                        case '/':
                            putChar(JsonPointer.SEPARATOR);
                            continue;
                        case '0':
                            putChar((char) 0);
                            continue;
                        case '1':
                            putChar((char) 1);
                            continue;
                        case '2':
                            putChar((char) 2);
                            continue;
                        case '3':
                            putChar((char) 3);
                            continue;
                        case '4':
                            putChar((char) 4);
                            continue;
                        case '5':
                            putChar((char) 5);
                            continue;
                        case '6':
                            putChar((char) 6);
                            continue;
                        case '7':
                            putChar((char) 7);
                            continue;
                        default:
                            switch (next4) {
                                case 't':
                                    putChar('\t');
                                    continue;
                                case 'u':
                                    putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                                    continue;
                                case 'v':
                                    putChar((char) 11);
                                    continue;
                                default:
                                    switch (next4) {
                                        case '\"':
                                            putChar('\"');
                                            continue;
                                        case '\'':
                                            putChar('\'');
                                            continue;
                                        case 'F':
                                        case 'f':
                                            putChar('\f');
                                            continue;
                                        case '\\':
                                            putChar('\\');
                                            continue;
                                        case 'b':
                                            putChar('\b');
                                            continue;
                                        case 'n':
                                            putChar('\n');
                                            continue;
                                        case 'r':
                                            putChar('\r');
                                            continue;
                                        case 'x':
                                            next = next();
                                            next2 = next();
                                            boolean z2 = (next >= '0' && next <= '9') || (next >= 'a' && next <= 'f') || (next >= 'A' && next <= 'F');
                                            if ((next2 < '0' || next2 > '9') && ((next2 < 'a' || next2 > 'f') && (next2 < 'A' || next2 > 'F'))) {
                                                z = false;
                                            }
                                            if (z2 && z) {
                                                int[] iArr = digits;
                                                putChar((char) ((iArr[next] * 16) + iArr[next2]));
                                                continue;
                                                continue;
                                                continue;
                                            }
                                            break;
                                        default:
                                            this.ch = next4;
                                            throw new JSONException("unclosed string : " + next4);
                                    }
                            }
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else {
                    int i2 = this.sp;
                    char[] cArr4 = this.sbuf;
                    if (i2 == cArr4.length) {
                        putChar(next3);
                    } else {
                        this.sp = i2 + 1;
                        cArr4[i2] = next3;
                    }
                }
            } else if (!isEOF()) {
                putChar(JSONLexer.EOI);
            } else {
                throw new JSONException("unclosed string : " + next3);
            }
        }
        throw new JSONException("invalid escape character \\x" + next + next2);
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Locale getLocale() {
        return this.locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int intValue() {
        int i;
        boolean z;
        int i2 = 0;
        if (this.np == -1) {
            this.np = 0;
        }
        int i3 = this.np;
        int i4 = this.sp + i3;
        if (charAt(i3) == '-') {
            i = Integer.MIN_VALUE;
            i3++;
            z = true;
        } else {
            i = -2147483647;
            z = false;
        }
        if (i3 < i4) {
            i3++;
            i2 = -(charAt(i3) - '0');
        }
        while (i3 < i4) {
            int i5 = i3 + 1;
            char charAt = charAt(i3);
            if (charAt == 'L' || charAt == 'S' || charAt == 'B') {
                i3 = i5;
                break;
            }
            int i6 = charAt - '0';
            if (i2 >= -214748364) {
                int i7 = i2 * 10;
                if (i7 >= i + i6) {
                    i2 = i7 - i6;
                    i3 = i5;
                } else {
                    throw new NumberFormatException(numberString());
                }
            } else {
                throw new NumberFormatException(numberString());
            }
        }
        if (!z) {
            return -i2;
        }
        if (i3 > this.np + 1) {
            return i2;
        }
        throw new NumberFormatException(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        char[] cArr = this.sbuf;
        if (cArr.length <= 8192) {
            SBUF_LOCAL.set(cArr);
        }
        this.sbuf = null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isRef() {
        return this.sp == 4 && charAt(this.np + 1) == '$' && charAt(this.np + 2) == 'r' && charAt(this.np + 3) == 'e' && charAt(this.np + 4) == 'f';
    }

    public final int scanType(String str) {
        this.matchStat = 0;
        if (!charArrayCompare(typeFieldName)) {
            return -2;
        }
        int length = this.bp + typeFieldName.length;
        int length2 = str.length();
        for (int i = 0; i < length2; i++) {
            if (str.charAt(i) != charAt(length + i)) {
                return -1;
            }
        }
        int i2 = length + length2;
        if (charAt(i2) != '\"') {
            return -1;
        }
        int i3 = i2 + 1;
        this.ch = charAt(i3);
        char c = this.ch;
        if (c == ',') {
            int i4 = i3 + 1;
            this.ch = charAt(i4);
            this.bp = i4;
            this.token = 16;
            return 3;
        }
        if (c == '}') {
            i3++;
            this.ch = charAt(i3);
            char c2 = this.ch;
            if (c2 == ',') {
                this.token = 16;
                i3++;
                this.ch = charAt(i3);
            } else if (c2 == ']') {
                this.token = 15;
                i3++;
                this.ch = charAt(i3);
            } else if (c2 == '}') {
                this.token = 13;
                i3++;
                this.ch = charAt(i3);
            } else if (c2 != 26) {
                return -1;
            } else {
                this.token = 20;
            }
            this.matchStat = 4;
        }
        this.bp = i3;
        return this.matchStat;
    }

    public final boolean matchField(char[] cArr) {
        while (!charArrayCompare(cArr)) {
            if (!isWhitespace(this.ch)) {
                return false;
            }
            next();
        }
        this.bp += cArr.length;
        this.ch = charAt(this.bp);
        char c = this.ch;
        if (c == '{') {
            next();
            this.token = 12;
        } else if (c == '[') {
            next();
            this.token = 14;
        } else if (c == 'S' && charAt(this.bp + 1) == 'e' && charAt(this.bp + 2) == 't' && charAt(this.bp + 3) == '[') {
            this.bp += 3;
            this.ch = charAt(this.bp);
            this.token = 21;
        } else {
            nextToken();
        }
        return true;
    }

    public int matchField(long j) {
        throw new UnsupportedOperationException();
    }

    public boolean seekArrayToItem(int i) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long j, boolean z) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long[] jArr) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToFieldDeepScan(long j) {
        throw new UnsupportedOperationException();
    }

    public void skipObject() {
        throw new UnsupportedOperationException();
    }

    public void skipObject(boolean z) {
        throw new UnsupportedOperationException();
    }

    public void skipArray() {
        throw new UnsupportedOperationException();
    }

    public String scanFieldString(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return stringDefaultValue();
        }
        int length = cArr.length;
        int i = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int indexOf = indexOf('\"', this.bp + cArr.length + 1);
        if (indexOf != -1) {
            int length2 = this.bp + cArr.length + 1;
            String subString = subString(length2, indexOf - length2);
            if (subString.indexOf(92) != -1) {
                while (true) {
                    int i2 = 0;
                    for (int i3 = indexOf - 1; i3 >= 0 && charAt(i3) == '\\'; i3--) {
                        i2++;
                    }
                    if (i2 % 2 == 0) {
                        break;
                    }
                    indexOf = indexOf('\"', indexOf + 1);
                }
                int i4 = this.bp;
                int length3 = indexOf - ((cArr.length + i4) + 1);
                subString = readString(sub_chars(i4 + cArr.length + 1, length3), length3);
            }
            int i5 = this.bp;
            int length4 = i + (indexOf - ((cArr.length + i5) + 1)) + 1;
            int i6 = length4 + 1;
            char charAt = charAt(i5 + length4);
            if (charAt == ',') {
                this.bp += i6;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                return subString;
            } else if (charAt == '}') {
                int i7 = i6 + 1;
                char charAt2 = charAt(this.bp + i6);
                if (charAt2 == ',') {
                    this.token = 16;
                    this.bp += i7;
                    this.ch = charAt(this.bp);
                } else if (charAt2 == ']') {
                    this.token = 15;
                    this.bp += i7;
                    this.ch = charAt(this.bp);
                } else if (charAt2 == '}') {
                    this.token = 13;
                    this.bp += i7;
                    this.ch = charAt(this.bp);
                } else if (charAt2 == 26) {
                    this.token = 20;
                    this.bp += i7 - 1;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
                this.matchStat = 4;
                return subString;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        } else {
            throw new JSONException("unclosed str");
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanString(char c) {
        this.matchStat = 0;
        char charAt = charAt(this.bp + 0);
        if (charAt != 'n') {
            int i = 1;
            while (charAt != '\"') {
                if (isWhitespace(charAt)) {
                    i++;
                    charAt = charAt(this.bp + i);
                } else {
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
            }
            int i2 = this.bp + i;
            int indexOf = indexOf('\"', i2);
            if (indexOf != -1) {
                String subString = subString(this.bp + i, indexOf - i2);
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
                    int i5 = indexOf - i2;
                    subString = readString(sub_chars(this.bp + 1, i5), i5);
                }
                int i6 = i + (indexOf - i2) + 1;
                int i7 = i6 + 1;
                char charAt2 = charAt(this.bp + i6);
                while (charAt2 != c) {
                    if (isWhitespace(charAt2)) {
                        i7++;
                        charAt2 = charAt(this.bp + i7);
                    } else {
                        if (charAt2 == ']') {
                            this.bp += i7;
                            this.ch = charAt(this.bp);
                            this.matchStat = -1;
                        }
                        return subString;
                    }
                }
                this.bp += i7;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return subString;
            }
            throw new JSONException("unclosed str");
        } else if (charAt(this.bp + 1) != 'u' || charAt(this.bp + 1 + 1) != 'l' || charAt(this.bp + 1 + 2) != 'l') {
            this.matchStat = -1;
            return null;
        } else if (charAt(this.bp + 4) == c) {
            this.bp += 5;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return null;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public long scanFieldSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i2 = i + 1;
            char charAt = charAt(this.bp + i);
            if (charAt == '\"') {
                int i3 = i2 + 1;
                char charAt2 = charAt(this.bp + i2);
                if (charAt2 == ',') {
                    this.bp += i3;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return j;
                } else if (charAt2 == '}') {
                    int i4 = i3 + 1;
                    char charAt3 = charAt(this.bp + i3);
                    if (charAt3 == ',') {
                        this.token = 16;
                        this.bp += i4;
                        this.ch = charAt(this.bp);
                    } else if (charAt3 == ']') {
                        this.token = 15;
                        this.bp += i4;
                        this.ch = charAt(this.bp);
                    } else if (charAt3 == '}') {
                        this.token = 13;
                        this.bp += i4;
                        this.ch = charAt(this.bp);
                    } else if (charAt3 == 26) {
                        this.token = 20;
                        this.bp += i4 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.matchStat = 4;
                    return j;
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            } else {
                j = (j ^ charAt) * 1099511628211L;
                if (charAt == '\\') {
                    this.matchStat = -1;
                    return 0L;
                }
                i = i2;
            }
        }
    }

    public long scanEnumSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i2 = i + 1;
            char charAt = charAt(this.bp + i);
            if (charAt == '\"') {
                int i3 = i2 + 1;
                char charAt2 = charAt(this.bp + i2);
                if (charAt2 == ',') {
                    this.bp += i3;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return j;
                } else if (charAt2 == '}') {
                    int i4 = i3 + 1;
                    char charAt3 = charAt(this.bp + i3);
                    if (charAt3 == ',') {
                        this.token = 16;
                        this.bp += i4;
                        this.ch = charAt(this.bp);
                    } else if (charAt3 == ']') {
                        this.token = 15;
                        this.bp += i4;
                        this.ch = charAt(this.bp);
                    } else if (charAt3 == '}') {
                        this.token = 13;
                        this.bp += i4;
                        this.ch = charAt(this.bp);
                    } else if (charAt3 == 26) {
                        this.token = 20;
                        this.bp += i4 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.matchStat = 4;
                    return j;
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            } else {
                j = (j ^ ((charAt < 'A' || charAt > 'Z') ? charAt : charAt + ' ')) * 1099511628211L;
                if (charAt == '\\') {
                    this.matchStat = -1;
                    return 0L;
                }
                i = i2;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Enum<?> scanEnum(Class<?> cls, SymbolTable symbolTable, char c) {
        String scanSymbolWithSeperator = scanSymbolWithSeperator(symbolTable, c);
        if (scanSymbolWithSeperator == null) {
            return null;
        }
        return Enum.valueOf(cls, scanSymbolWithSeperator);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanSymbolWithSeperator(SymbolTable symbolTable, char c) {
        this.matchStat = 0;
        char charAt = charAt(this.bp + 0);
        if (charAt == 'n') {
            if (charAt(this.bp + 1) != 'u' || charAt(this.bp + 1 + 1) != 'l' || charAt(this.bp + 1 + 2) != 'l') {
                this.matchStat = -1;
                return null;
            } else if (charAt(this.bp + 4) == c) {
                this.bp += 5;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                return null;
            } else {
                this.matchStat = -1;
                return null;
            }
        } else if (charAt != '\"') {
            this.matchStat = -1;
            return null;
        } else {
            int i = 0;
            int i2 = 1;
            while (true) {
                int i3 = i2 + 1;
                char charAt2 = charAt(this.bp + i2);
                if (charAt2 == '\"') {
                    int i4 = this.bp;
                    int i5 = i4 + 0 + 1;
                    String addSymbol = addSymbol(i5, ((i4 + i3) - i5) - 1, i, symbolTable);
                    int i6 = i3 + 1;
                    char charAt3 = charAt(this.bp + i3);
                    while (charAt3 != c) {
                        if (isWhitespace(charAt3)) {
                            i6++;
                            charAt3 = charAt(this.bp + i6);
                        } else {
                            this.matchStat = -1;
                            return addSymbol;
                        }
                    }
                    this.bp += i6;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return addSymbol;
                }
                i = (i * 31) + charAt2;
                if (charAt2 == '\\') {
                    this.matchStat = -1;
                    return null;
                }
                i2 = i3;
            }
        }
    }

    public Collection<String> newCollectionByType(Class<?> cls) {
        if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (cls.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (cls.isAssignableFrom(LinkedList.class)) {
            return new LinkedList();
        }
        try {
            return (Collection) cls.newInstance();
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ef, code lost:
        if (r13.size() != 0) goto L_0x0171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f1, code lost:
        r0 = r1 + 1;
        r12 = charAt(r11.bp + r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00fa, code lost:
        if (r12 != ',') goto L_0x010d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00fc, code lost:
        r11.bp += r0;
        r11.ch = charAt(r11.bp);
        r11.matchStat = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x010c, code lost:
        return r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x010f, code lost:
        if (r12 != '}') goto L_0x016e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0111, code lost:
        r6 = r0 + 1;
        r12 = charAt(r11.bp + r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x011a, code lost:
        if (r12 != ',') goto L_0x012e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x011c, code lost:
        r11.token = 16;
        r11.bp += r6;
        r11.ch = charAt(r11.bp);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012e, code lost:
        if (r12 != ']') goto L_0x0142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0130, code lost:
        r11.token = 15;
        r11.bp += r6;
        r11.ch = charAt(r11.bp);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0142, code lost:
        if (r12 != '}') goto L_0x0156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0144, code lost:
        r11.token = 13;
        r11.bp += r6;
        r11.ch = charAt(r11.bp);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0158, code lost:
        if (r12 != 26) goto L_0x016b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x015a, code lost:
        r11.bp += r6 - 1;
        r11.token = 20;
        r11.ch = com.alibaba.fastjson.parser.JSONLexer.EOI;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0167, code lost:
        r11.matchStat = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x016a, code lost:
        return r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x016b, code lost:
        r11.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x016d, code lost:
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x016e, code lost:
        r11.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0170, code lost:
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0178, code lost:
        throw new com.alibaba.fastjson.JSONException("illega str");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r12, java.lang.Class<?> r13) {
        /*
            Method dump skipped, instructions count: 377
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x012d, code lost:
        if (r1 != r18) goto L_0x013f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012f, code lost:
        r16.bp += r3;
        r16.ch = charAt(r16.bp);
        r16.matchStat = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x013e, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x013f, code lost:
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0141, code lost:
        return;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void scanStringArray(java.util.Collection<java.lang.String> r17, char r18) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanStringArray(java.util.Collection, char):void");
    }

    public int scanFieldInt(char[] cArr) {
        int i;
        char charAt;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0;
        }
        int length = cArr.length;
        int i2 = length + 1;
        char charAt2 = charAt(this.bp + length);
        boolean z = charAt2 == '-';
        if (z) {
            i2++;
            charAt2 = charAt(this.bp + i2);
        }
        if (charAt2 < '0' || charAt2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int i3 = charAt2 - '0';
        while (true) {
            i = i2 + 1;
            charAt = charAt(this.bp + i2);
            if (charAt < '0' || charAt > '9') {
                break;
            }
            i3 = (i3 * 10) + (charAt - '0');
            i2 = i;
        }
        if (charAt == '.') {
            this.matchStat = -1;
            return 0;
        } else if ((i3 < 0 || i > cArr.length + 14) && !(i3 == Integer.MIN_VALUE && i == 17 && z)) {
            this.matchStat = -1;
            return 0;
        } else if (charAt == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z ? -i3 : i3;
        } else if (charAt == '}') {
            int i4 = i + 1;
            char charAt3 = charAt(this.bp + i);
            if (charAt3 == ',') {
                this.token = 16;
                this.bp += i4;
                this.ch = charAt(this.bp);
            } else if (charAt3 == ']') {
                this.token = 15;
                this.bp += i4;
                this.ch = charAt(this.bp);
            } else if (charAt3 == '}') {
                this.token = 13;
                this.bp += i4;
                this.ch = charAt(this.bp);
            } else if (charAt3 == 26) {
                this.token = 20;
                this.bp += i4 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return 0;
            }
            this.matchStat = 4;
            return z ? -i3 : i3;
        } else {
            this.matchStat = -1;
            return 0;
        }
    }

    public final int[] scanFieldIntArray(char[] cArr) {
        int i;
        int i2;
        char c;
        int[] iArr;
        boolean z;
        int i3;
        char charAt;
        this.matchStat = 0;
        int[] iArr2 = null;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i4 = length + 1;
        if (charAt(this.bp + length) != '[') {
            this.matchStat = -2;
            return null;
        }
        int i5 = i4 + 1;
        char charAt2 = charAt(this.bp + i4);
        int[] iArr3 = new int[16];
        if (charAt2 == ']') {
            i = i5 + 1;
            c = charAt(this.bp + i5);
            i2 = 0;
        } else {
            int i6 = 0;
            while (true) {
                if (charAt2 == '-') {
                    i5++;
                    charAt2 = charAt(this.bp + i5);
                    z = true;
                } else {
                    z = false;
                }
                if (charAt2 < '0' || charAt2 > '9') {
                    break;
                }
                int i7 = charAt2 - '0';
                while (true) {
                    i3 = i5 + 1;
                    charAt = charAt(this.bp + i5);
                    if (charAt < '0' || charAt > '9') {
                        break;
                    }
                    i7 = (i7 * 10) + (charAt - '0');
                    i5 = i3;
                }
                if (i6 >= iArr3.length) {
                    int[] iArr4 = new int[(iArr3.length * 3) / 2];
                    System.arraycopy(iArr3, 0, iArr4, 0, i6);
                    iArr3 = iArr4;
                }
                i2 = i6 + 1;
                if (z) {
                    i7 = -i7;
                }
                iArr3[i6] = i7;
                if (charAt == ',') {
                    i5 = i3 + 1;
                    charAt2 = charAt(this.bp + i3);
                    iArr2 = null;
                } else if (charAt == ']') {
                    i = i3 + 1;
                    c = charAt(this.bp + i3);
                    break;
                } else {
                    iArr2 = null;
                    charAt2 = charAt;
                    i5 = i3;
                }
                i6 = i2;
            }
            this.matchStat = -1;
            return iArr2;
        }
        if (i2 != iArr3.length) {
            iArr = new int[i2];
            System.arraycopy(iArr3, 0, iArr, 0, i2);
        } else {
            iArr = iArr3;
        }
        if (c == ',') {
            this.bp += i - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return iArr;
        } else if (c == '}') {
            int i8 = i + 1;
            char charAt3 = charAt(this.bp + i);
            if (charAt3 == ',') {
                this.token = 16;
                this.bp += i8 - 1;
                next();
            } else if (charAt3 == ']') {
                this.token = 15;
                this.bp += i8 - 1;
                next();
            } else if (charAt3 == '}') {
                this.token = 13;
                this.bp += i8 - 1;
                next();
            } else if (charAt3 == 26) {
                this.bp += i8 - 1;
                this.token = 20;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return iArr;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean scanBoolean(char c) {
        boolean z = false;
        this.matchStat = 0;
        char charAt = charAt(this.bp + 0);
        int i = 2;
        if (charAt == 't') {
            if (charAt(this.bp + 1) == 'r' && charAt(this.bp + 1 + 1) == 'u' && charAt(this.bp + 1 + 2) == 'e') {
                charAt = charAt(this.bp + 4);
                i = 5;
                z = true;
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (charAt == 'f') {
            if (charAt(this.bp + 1) == 'a' && charAt(this.bp + 1 + 1) == 'l' && charAt(this.bp + 1 + 2) == 's' && charAt(this.bp + 1 + 3) == 'e') {
                i = 6;
                charAt = charAt(this.bp + 5);
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (charAt == '1') {
            charAt = charAt(this.bp + 1);
            z = true;
        } else if (charAt == '0') {
            charAt = charAt(this.bp + 1);
        } else {
            i = 1;
        }
        while (charAt != c) {
            if (isWhitespace(charAt)) {
                i++;
                charAt = charAt(this.bp + i);
            } else {
                this.matchStat = -1;
                return z;
            }
        }
        this.bp += i;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        return z;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int scanInt(char c) {
        int i;
        int i2;
        int i3;
        char charAt;
        this.matchStat = 0;
        char charAt2 = charAt(this.bp + 0);
        boolean z = charAt2 == '\"';
        if (z) {
            charAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z2 = charAt2 == '-';
        if (z2) {
            i++;
            charAt2 = charAt(this.bp + i);
        }
        if (charAt2 >= '0' && charAt2 <= '9') {
            int i4 = charAt2 - '0';
            while (true) {
                i3 = i + 1;
                charAt = charAt(this.bp + i);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i4 = (i4 * 10) + (charAt - '0');
                i = i3;
            }
            if (charAt == '.') {
                this.matchStat = -1;
                return 0;
            } else if (i4 < 0) {
                this.matchStat = -1;
                return 0;
            } else {
                while (charAt != c) {
                    if (isWhitespace(charAt)) {
                        i3++;
                        charAt = charAt(this.bp + i3);
                    } else {
                        this.matchStat = -1;
                        return z2 ? -i4 : i4;
                    }
                }
                this.bp += i3;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return z2 ? -i4 : i4;
            }
        } else if (charAt2 == 'n' && charAt(this.bp + i) == 'u' && charAt(this.bp + i + 1) == 'l' && charAt(this.bp + i + 2) == 'l') {
            this.matchStat = 5;
            int i5 = i + 3;
            int i6 = i5 + 1;
            char charAt3 = charAt(this.bp + i5);
            if (!z || charAt3 != '\"') {
                i2 = i6;
            } else {
                i2 = i6 + 1;
                charAt3 = charAt(this.bp + i6);
            }
            while (charAt3 != ',') {
                if (charAt3 == ']') {
                    this.bp += i2;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0;
                } else if (isWhitespace(charAt3)) {
                    i2++;
                    charAt3 = charAt(this.bp + i2);
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
            this.bp += i2;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0;
        } else {
            this.matchStat = -1;
            return 0;
        }
    }

    public boolean scanFieldBoolean(char[] cArr) {
        boolean z;
        int i;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = cArr.length;
        int i2 = length + 1;
        char charAt = charAt(this.bp + length);
        if (charAt == 't') {
            int i3 = i2 + 1;
            if (charAt(this.bp + i2) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int i4 = i3 + 1;
            if (charAt(this.bp + i3) != 'u') {
                this.matchStat = -1;
                return false;
            }
            i = i4 + 1;
            if (charAt(this.bp + i4) != 'e') {
                this.matchStat = -1;
                return false;
            }
            z = true;
        } else if (charAt == 'f') {
            int i5 = i2 + 1;
            if (charAt(this.bp + i2) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int i6 = i5 + 1;
            if (charAt(this.bp + i5) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int i7 = i6 + 1;
            if (charAt(this.bp + i6) != 's') {
                this.matchStat = -1;
                return false;
            }
            i = i7 + 1;
            if (charAt(this.bp + i7) != 'e') {
                this.matchStat = -1;
                return false;
            }
            z = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        int i8 = i + 1;
        char charAt2 = charAt(this.bp + i);
        if (charAt2 == ',') {
            this.bp += i8;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z;
        } else if (charAt2 == '}') {
            int i9 = i8 + 1;
            char charAt3 = charAt(this.bp + i8);
            if (charAt3 == ',') {
                this.token = 16;
                this.bp += i9;
                this.ch = charAt(this.bp);
            } else if (charAt3 == ']') {
                this.token = 15;
                this.bp += i9;
                this.ch = charAt(this.bp);
            } else if (charAt3 == '}') {
                this.token = 13;
                this.bp += i9;
                this.ch = charAt(this.bp);
            } else if (charAt3 == 26) {
                this.token = 20;
                this.bp += i9 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return false;
            }
            this.matchStat = 4;
            return z;
        } else {
            this.matchStat = -1;
            return false;
        }
    }

    public long scanFieldLong(char[] cArr) {
        boolean z;
        int i;
        int i2;
        char charAt;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char charAt2 = charAt(this.bp + length);
        if (charAt2 == '-') {
            i = i3 + 1;
            charAt2 = charAt(this.bp + i3);
            z = true;
        } else {
            i = i3;
            z = false;
        }
        if (charAt2 < '0' || charAt2 > '9') {
            this.matchStat = -1;
            return 0L;
        }
        long j = charAt2 - '0';
        while (true) {
            i2 = i + 1;
            charAt = charAt(this.bp + i);
            if (charAt < '0' || charAt > '9') {
                break;
            }
            j = (j * 10) + (charAt - '0');
            i = i2;
        }
        if (charAt == '.') {
            this.matchStat = -1;
            return 0L;
        }
        if (!(i2 - cArr.length < 21 && (j >= 0 || (j == Long.MIN_VALUE && z)))) {
            this.matchStat = -1;
            return 0L;
        } else if (charAt == ',') {
            this.bp += i2;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z ? -j : j;
        } else if (charAt == '}') {
            int i4 = i2 + 1;
            char charAt3 = charAt(this.bp + i2);
            if (charAt3 == ',') {
                this.token = 16;
                this.bp += i4;
                this.ch = charAt(this.bp);
            } else if (charAt3 == ']') {
                this.token = 15;
                this.bp += i4;
                this.ch = charAt(this.bp);
            } else if (charAt3 == '}') {
                this.token = 13;
                this.bp += i4;
                this.ch = charAt(this.bp);
            } else if (charAt3 == 26) {
                this.token = 20;
                this.bp += i4 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return 0L;
            }
            this.matchStat = 4;
            return z ? -j : j;
        } else {
            this.matchStat = -1;
            return 0L;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char c) {
        int i;
        int i2;
        int i3;
        char charAt;
        char c2;
        this.matchStat = 0;
        char charAt2 = charAt(this.bp + 0);
        boolean z = charAt2 == '\"';
        if (z) {
            charAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z2 = charAt2 == '-';
        if (z2) {
            i++;
            charAt2 = charAt(this.bp + i);
        }
        if (charAt2 >= '0' && charAt2 <= '9') {
            long j = charAt2 - '0';
            while (true) {
                i3 = i + 1;
                charAt = charAt(this.bp + i);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                j = (j * 10) + (charAt - '0');
                i = i3;
            }
            if (charAt == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (j >= 0 || (j == Long.MIN_VALUE && z2)) {
                if (!z) {
                    c2 = c;
                } else if (charAt != '\"') {
                    this.matchStat = -1;
                    return 0L;
                } else {
                    i3++;
                    charAt = charAt(this.bp + i3);
                    c2 = c;
                }
                while (charAt != c2) {
                    if (isWhitespace(charAt)) {
                        i3++;
                        charAt = charAt(this.bp + i3);
                    } else {
                        this.matchStat = -1;
                        return j;
                    }
                }
                this.bp += i3;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return z2 ? -j : j;
            }
            throw new NumberFormatException(subString(this.bp, i3 - 1));
        } else if (charAt2 == 'n' && charAt(this.bp + i) == 'u' && charAt(this.bp + i + 1) == 'l' && charAt(this.bp + i + 2) == 'l') {
            this.matchStat = 5;
            int i4 = i + 3;
            int i5 = i4 + 1;
            char charAt3 = charAt(this.bp + i4);
            if (!z || charAt3 != '\"') {
                i2 = i5;
            } else {
                i2 = i5 + 1;
                charAt3 = charAt(this.bp + i5);
            }
            while (charAt3 != ',') {
                if (charAt3 == ']') {
                    this.bp += i2;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0L;
                } else if (isWhitespace(charAt3)) {
                    i2++;
                    charAt3 = charAt(this.bp + i2);
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            }
            this.bp += i2;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0L;
        } else {
            this.matchStat = -1;
            return 0L;
        }
    }

    public final float scanFieldFloat(char[] cArr) {
        int i;
        char charAt;
        boolean z;
        long j;
        int i2;
        char c;
        int i3;
        float f;
        int i4;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int length = cArr.length;
        int i5 = length + 1;
        char charAt2 = charAt(this.bp + length);
        boolean z2 = charAt2 == '\"';
        if (z2) {
            i5++;
            charAt2 = charAt(this.bp + i5);
        }
        boolean z3 = charAt2 == '-';
        if (z3) {
            i5++;
            charAt2 = charAt(this.bp + i5);
        }
        if (charAt2 >= '0') {
            char c2 = '9';
            if (charAt2 <= '9') {
                long j2 = charAt2 - '0';
                while (true) {
                    i = i5 + 1;
                    charAt = charAt(this.bp + i5);
                    if (charAt < '0' || charAt > '9') {
                        break;
                    }
                    j2 = (j2 * 10) + (charAt - '0');
                    i5 = i;
                }
                if (charAt == '.') {
                    int i6 = i + 1;
                    char charAt3 = charAt(this.bp + i);
                    if (charAt3 < '0' || charAt3 > '9') {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    z = z2;
                    j2 = (j2 * 10) + (charAt3 - '0');
                    j = 10;
                    while (true) {
                        i4 = i6 + 1;
                        charAt = charAt(this.bp + i6);
                        if (charAt < '0' || charAt > c2) {
                            break;
                        }
                        j2 = (j2 * 10) + (charAt - '0');
                        j *= 10;
                        i6 = i4;
                        c2 = '9';
                    }
                    i = i4;
                } else {
                    z = z2;
                    j = 1;
                }
                boolean z4 = charAt == 'e' || charAt == 'E';
                if (z4) {
                    int i7 = i + 1;
                    char charAt4 = charAt(this.bp + i);
                    if (charAt4 == '+' || charAt4 == '-') {
                        i = i7 + 1;
                        charAt = charAt(this.bp + i7);
                    } else {
                        i = i7;
                        charAt = charAt4;
                    }
                    while (charAt >= '0' && charAt <= '9') {
                        i++;
                        charAt = charAt(this.bp + i);
                    }
                }
                if (!z) {
                    int i8 = this.bp;
                    i3 = cArr.length + i8;
                    i2 = ((i8 + i) - i3) - 1;
                    c = charAt;
                } else if (charAt != '\"') {
                    this.matchStat = -1;
                    return 0.0f;
                } else {
                    int i9 = i + 1;
                    c = charAt(this.bp + i);
                    int i10 = this.bp;
                    i3 = cArr.length + i10 + 1;
                    i2 = ((i10 + i9) - i3) - 2;
                    i = i9;
                }
                if (z4 || i2 >= 17) {
                    f = Float.parseFloat(subString(i3, i2));
                } else {
                    f = (float) (j2 / j);
                    if (z3) {
                        f = -f;
                    }
                }
                if (c == ',') {
                    this.bp += i;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return f;
                } else if (c == '}') {
                    int i11 = i + 1;
                    char charAt5 = charAt(this.bp + i);
                    if (charAt5 == ',') {
                        this.token = 16;
                        this.bp += i11;
                        this.ch = charAt(this.bp);
                    } else if (charAt5 == ']') {
                        this.token = 15;
                        this.bp += i11;
                        this.ch = charAt(this.bp);
                    } else if (charAt5 == '}') {
                        this.token = 13;
                        this.bp += i11;
                        this.ch = charAt(this.bp);
                    } else if (charAt5 == 26) {
                        this.bp += i11 - 1;
                        this.token = 20;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    this.matchStat = 4;
                    return f;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
        }
        if (charAt2 == 'n' && charAt(this.bp + i5) == 'u' && charAt(this.bp + i5 + 1) == 'l' && charAt(this.bp + i5 + 2) == 'l') {
            this.matchStat = 5;
            int i12 = i5 + 3;
            int i13 = i12 + 1;
            char charAt6 = charAt(this.bp + i12);
            if (z2 && charAt6 == '\"') {
                i13++;
                charAt6 = charAt(this.bp + i13);
            }
            while (charAt6 != ',') {
                if (charAt6 == '}') {
                    this.bp += i13;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return 0.0f;
                } else if (isWhitespace(charAt6)) {
                    i13++;
                    charAt6 = charAt(this.bp + i13);
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            this.bp += i13;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        }
        this.matchStat = -1;
        return 0.0f;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final float scanFloat(char c) {
        int i;
        int i2;
        int i3;
        char charAt;
        int i4;
        int i5;
        float f;
        int i6;
        this.matchStat = 0;
        char charAt2 = charAt(this.bp + 0);
        boolean z = charAt2 == '\"';
        if (z) {
            charAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z2 = charAt2 == '-';
        if (z2) {
            i++;
            charAt2 = charAt(this.bp + i);
        }
        if (charAt2 >= '0' && charAt2 <= '9') {
            long j = charAt2 - '0';
            while (true) {
                i3 = i + 1;
                charAt = charAt(this.bp + i);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                j = (j * 10) + (charAt - '0');
                i = i3;
            }
            long j2 = 1;
            if (charAt == '.') {
                int i7 = i3 + 1;
                char charAt3 = charAt(this.bp + i3);
                if (charAt3 < '0' || charAt3 > '9') {
                    this.matchStat = -1;
                    return 0.0f;
                }
                j = (j * 10) + (charAt3 - '0');
                long j3 = 10;
                while (true) {
                    i6 = i7 + 1;
                    charAt = charAt(this.bp + i7);
                    if (charAt < '0' || charAt > '9') {
                        break;
                    }
                    j = (j * 10) + (charAt - '0');
                    j3 *= 10;
                    i7 = i6;
                }
                i3 = i6;
                j2 = j3;
            }
            boolean z3 = charAt == 'e' || charAt == 'E';
            if (z3) {
                int i8 = i3 + 1;
                char charAt4 = charAt(this.bp + i3);
                if (charAt4 == '+' || charAt4 == '-') {
                    i3 = i8 + 1;
                    charAt = charAt(this.bp + i8);
                } else {
                    i3 = i8;
                    charAt = charAt4;
                }
                while (charAt >= '0' && charAt <= '9') {
                    i3++;
                    charAt = charAt(this.bp + i3);
                }
            }
            if (!z) {
                i4 = this.bp;
                i5 = ((i4 + i3) - i4) - 1;
            } else if (charAt != '\"') {
                this.matchStat = -1;
                return 0.0f;
            } else {
                int i9 = i3 + 1;
                charAt = charAt(this.bp + i3);
                int i10 = this.bp;
                i4 = i10 + 1;
                i5 = ((i10 + i9) - i4) - 2;
                i3 = i9;
            }
            if (z3 || i5 >= 17) {
                f = Float.parseFloat(subString(i4, i5));
            } else {
                f = (float) (j / j2);
                if (z2) {
                    f = -f;
                }
            }
            if (charAt == c) {
                this.bp += i3;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return f;
            }
            this.matchStat = -1;
            return f;
        } else if (charAt2 == 'n' && charAt(this.bp + i) == 'u' && charAt(this.bp + i + 1) == 'l' && charAt(this.bp + i + 2) == 'l') {
            this.matchStat = 5;
            int i11 = i + 3;
            int i12 = i11 + 1;
            char charAt5 = charAt(this.bp + i11);
            if (!z || charAt5 != '\"') {
                i2 = i12;
            } else {
                i2 = i12 + 1;
                charAt5 = charAt(this.bp + i12);
            }
            while (charAt5 != ',') {
                if (charAt5 == ']') {
                    this.bp += i2;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0f;
                } else if (isWhitespace(charAt5)) {
                    i2++;
                    charAt5 = charAt(this.bp + i2);
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            this.bp += i2;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        } else {
            this.matchStat = -1;
            return 0.0f;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public double scanDouble(char c) {
        int i;
        int i2;
        char charAt;
        boolean z;
        long j;
        int i3;
        int i4;
        int i5;
        char c2;
        double d;
        int i6;
        char charAt2;
        this.matchStat = 0;
        char charAt3 = charAt(this.bp + 0);
        boolean z2 = charAt3 == '\"';
        if (z2) {
            charAt3 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z3 = charAt3 == '-';
        if (z3) {
            i++;
            charAt3 = charAt(this.bp + i);
        }
        if (charAt3 >= '0' && charAt3 <= '9') {
            long j2 = charAt3 - '0';
            while (true) {
                i2 = i + 1;
                charAt = charAt(this.bp + i);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                j2 = (j2 * 10) + (charAt - '0');
                i = i2;
            }
            if (charAt == '.') {
                int i7 = i2 + 1;
                char charAt4 = charAt(this.bp + i2);
                if (charAt4 < '0' || charAt4 > '9') {
                    this.matchStat = -1;
                    return 0.0d;
                }
                j2 = (j2 * 10) + (charAt4 - '0');
                long j3 = 10;
                while (true) {
                    i6 = i7 + 1;
                    charAt2 = charAt(this.bp + i7);
                    if (charAt2 < '0' || charAt2 > '9') {
                        break;
                    }
                    j2 = (j2 * 10) + (charAt2 - '0');
                    j3 *= 10;
                    i7 = i6;
                    z3 = z3;
                }
                z = z3;
                i2 = i6;
                charAt = charAt2;
                j = j3;
            } else {
                z = z3;
                j = 1;
            }
            boolean z4 = charAt == 'e' || charAt == 'E';
            if (z4) {
                int i8 = i2 + 1;
                charAt = charAt(this.bp + i2);
                if (charAt == '+' || charAt == '-') {
                    i2 = i8 + 1;
                    charAt = charAt(this.bp + i8);
                } else {
                    i2 = i8;
                }
                while (charAt >= '0' && charAt <= '9') {
                    i2++;
                    charAt = charAt(this.bp + i2);
                }
            }
            if (!z2) {
                i3 = this.bp;
                i4 = ((i3 + i2) - i3) - 1;
                c2 = charAt;
                i5 = i2;
            } else if (charAt != '\"') {
                this.matchStat = -1;
                return 0.0d;
            } else {
                i5 = i2 + 1;
                c2 = charAt(this.bp + i2);
                int i9 = this.bp;
                i3 = i9 + 1;
                i4 = ((i9 + i5) - i3) - 2;
            }
            if (z4 || i4 >= 17) {
                d = Double.parseDouble(subString(i3, i4));
            } else {
                d = j2 / j;
                if (z) {
                    d = -d;
                }
            }
            if (c2 == c) {
                this.bp += i5;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return d;
            }
            this.matchStat = -1;
            return d;
        } else if (charAt3 == 'n' && charAt(this.bp + i) == 'u' && charAt(this.bp + i + 1) == 'l' && charAt(this.bp + i + 2) == 'l') {
            this.matchStat = 5;
            int i10 = i + 3;
            int i11 = i10 + 1;
            char charAt5 = charAt(this.bp + i10);
            if (z2 && charAt5 == '\"') {
                i11++;
                charAt5 = charAt(this.bp + i11);
            }
            while (charAt5 != ',') {
                if (charAt5 == ']') {
                    this.bp += i11;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0d;
                } else if (isWhitespace(charAt5)) {
                    i11++;
                    charAt5 = charAt(this.bp + i11);
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            this.bp += i11;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        } else {
            this.matchStat = -1;
            return 0.0d;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public BigDecimal scanDecimal(char c) {
        int i;
        int i2;
        int i3;
        char charAt;
        int i4;
        int i5;
        char c2;
        this.matchStat = 0;
        char charAt2 = charAt(this.bp + 0);
        boolean z = charAt2 == '\"';
        if (z) {
            charAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        if (charAt2 == '-') {
            i++;
            charAt2 = charAt(this.bp + i);
        }
        if (charAt2 >= '0' && charAt2 <= '9') {
            while (true) {
                i3 = i + 1;
                charAt = charAt(this.bp + i);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i = i3;
            }
            if (charAt == '.') {
                int i6 = i3 + 1;
                char charAt3 = charAt(this.bp + i3);
                if (charAt3 >= '0' && charAt3 <= '9') {
                    while (true) {
                        i3 = i6 + 1;
                        charAt = charAt(this.bp + i6);
                        if (charAt < '0' || charAt > '9') {
                            break;
                        }
                        i6 = i3;
                    }
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            if (charAt == 'e' || charAt == 'E') {
                int i7 = i3 + 1;
                charAt = charAt(this.bp + i3);
                if (charAt == '+' || charAt == '-') {
                    i3 = i7 + 1;
                    charAt = charAt(this.bp + i7);
                } else {
                    i3 = i7;
                }
                while (charAt >= '0' && charAt <= '9') {
                    i3++;
                    charAt = charAt(this.bp + i3);
                }
            }
            if (!z) {
                i4 = this.bp;
                i5 = ((i4 + i3) - i4) - 1;
                c2 = charAt;
            } else if (charAt != '\"') {
                this.matchStat = -1;
                return null;
            } else {
                int i8 = i3 + 1;
                c2 = charAt(this.bp + i3);
                int i9 = this.bp;
                i4 = i9 + 1;
                i5 = ((i9 + i8) - i4) - 2;
                i3 = i8;
            }
            BigDecimal bigDecimal = new BigDecimal(sub_chars(i4, i5));
            if (c2 == ',') {
                this.bp += i3;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return bigDecimal;
            } else if (c2 == ']') {
                int i10 = i3 + 1;
                char charAt4 = charAt(this.bp + i3);
                if (charAt4 == ',') {
                    this.token = 16;
                    this.bp += i10;
                    this.ch = charAt(this.bp);
                } else if (charAt4 == ']') {
                    this.token = 15;
                    this.bp += i10;
                    this.ch = charAt(this.bp);
                } else if (charAt4 == '}') {
                    this.token = 13;
                    this.bp += i10;
                    this.ch = charAt(this.bp);
                } else if (charAt4 == 26) {
                    this.token = 20;
                    this.bp += i10 - 1;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return bigDecimal;
            } else {
                this.matchStat = -1;
                return null;
            }
        } else if (charAt2 == 'n' && charAt(this.bp + i) == 'u' && charAt(this.bp + i + 1) == 'l' && charAt(this.bp + i + 2) == 'l') {
            this.matchStat = 5;
            int i11 = i + 3;
            int i12 = i11 + 1;
            char charAt5 = charAt(this.bp + i11);
            if (!z || charAt5 != '\"') {
                i2 = i12;
            } else {
                i2 = i12 + 1;
                charAt5 = charAt(this.bp + i12);
            }
            while (charAt5 != ',') {
                if (charAt5 == '}') {
                    this.bp += i2;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                } else if (isWhitespace(charAt5)) {
                    i2++;
                    charAt5 = charAt(this.bp + i2);
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += i2;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:98:0x01b6, code lost:
        r18.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01b9, code lost:
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float[] scanFieldFloatArray(char[] r19) {
        /*
            Method dump skipped, instructions count: 442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldFloatArray(char[]):float[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b4, code lost:
        r21.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b8, code lost:
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x013c, code lost:
        r2 = r18 + 1;
        r1 = charAt(r21.bp + r18);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0147, code lost:
        if (r4 == r3.length) goto L_0x0151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0149, code lost:
        r5 = new float[r4];
        r6 = 0;
        java.lang.System.arraycopy(r3, 0, r5, 0, r4);
        r3 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0151, code lost:
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0153, code lost:
        if (r8 < r7.length) goto L_0x0160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0155, code lost:
        r5 = new float[(r7.length * 3) / 2];
        java.lang.System.arraycopy(r3, r6, r5, r6, r4);
        r7 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0160, code lost:
        r4 = r8 + 1;
        r7[r8] = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0166, code lost:
        if (r1 != ',') goto L_0x0173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0168, code lost:
        r3 = r2 + 1;
        r1 = charAt(r21.bp + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0175, code lost:
        if (r1 != ']') goto L_0x0181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0177, code lost:
        r3 = r2 + 1;
        r2 = charAt(r21.bp + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0181, code lost:
        r3 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0196, code lost:
        r21.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x019c, code lost:
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float[][] scanFieldFloatArray2(char[] r22) {
        /*
            Method dump skipped, instructions count: 549
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldFloatArray2(char[]):float[][]");
    }

    public final double scanFieldDouble(char[] cArr) {
        int i;
        char charAt;
        boolean z;
        int i2;
        long j;
        int i3;
        char c;
        int i4;
        double d;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0.0d;
        }
        int length = cArr.length;
        int i5 = length + 1;
        char charAt2 = charAt(this.bp + length);
        boolean z2 = charAt2 == '\"';
        if (z2) {
            i5++;
            charAt2 = charAt(this.bp + i5);
        }
        boolean z3 = charAt2 == '-';
        if (z3) {
            i5++;
            charAt2 = charAt(this.bp + i5);
        }
        if (charAt2 >= '0') {
            char c2 = '9';
            if (charAt2 <= '9') {
                int i6 = i5;
                long j2 = charAt2 - '0';
                while (true) {
                    i = i6 + 1;
                    charAt = charAt(this.bp + i6);
                    if (charAt < '0' || charAt > '9') {
                        break;
                    }
                    j2 = (j2 * 10) + (charAt - '0');
                    i6 = i;
                    z2 = z2;
                }
                if (charAt == '.') {
                    int i7 = i + 1;
                    char charAt3 = charAt(this.bp + i);
                    if (charAt3 >= '0' && charAt3 <= '9') {
                        z = z3;
                        j2 = (j2 * 10) + (charAt3 - '0');
                        j = 10;
                        while (true) {
                            i2 = i7 + 1;
                            charAt = charAt(this.bp + i7);
                            if (charAt < '0' || charAt > c2) {
                                break;
                            }
                            j2 = (j2 * 10) + (charAt - '0');
                            j *= 10;
                            i7 = i2;
                            c2 = '9';
                        }
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                } else {
                    z = z3;
                    i2 = i;
                    j = 1;
                }
                boolean z4 = charAt == 'e' || charAt == 'E';
                if (z4) {
                    int i8 = i2 + 1;
                    charAt = charAt(this.bp + i2);
                    if (charAt == '+' || charAt == '-') {
                        i2 = i8 + 1;
                        charAt = charAt(this.bp + i8);
                    } else {
                        i2 = i8;
                    }
                    while (charAt >= '0' && charAt <= '9') {
                        i2++;
                        charAt = charAt(this.bp + i2);
                    }
                }
                if (!z2) {
                    int i9 = this.bp;
                    i4 = cArr.length + i9;
                    i3 = ((i9 + i2) - i4) - 1;
                    c = charAt;
                } else if (charAt != '\"') {
                    this.matchStat = -1;
                    return 0.0d;
                } else {
                    int i10 = i2 + 1;
                    c = charAt(this.bp + i2);
                    int i11 = this.bp;
                    i4 = cArr.length + i11 + 1;
                    i3 = ((i11 + i10) - i4) - 2;
                    i2 = i10;
                }
                if (z4 || i3 >= 17) {
                    d = Double.parseDouble(subString(i4, i3));
                } else {
                    d = j2 / j;
                    if (z) {
                        d = -d;
                    }
                }
                if (c == ',') {
                    this.bp += i2;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return d;
                } else if (c == '}') {
                    int i12 = i2 + 1;
                    char charAt4 = charAt(this.bp + i2);
                    if (charAt4 == ',') {
                        this.token = 16;
                        this.bp += i12;
                        this.ch = charAt(this.bp);
                    } else if (charAt4 == ']') {
                        this.token = 15;
                        this.bp += i12;
                        this.ch = charAt(this.bp);
                    } else if (charAt4 == '}') {
                        this.token = 13;
                        this.bp += i12;
                        this.ch = charAt(this.bp);
                    } else if (charAt4 == 26) {
                        this.token = 20;
                        this.bp += i12 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    this.matchStat = 4;
                    return d;
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
        }
        if (charAt2 == 'n' && charAt(this.bp + i5) == 'u' && charAt(this.bp + i5 + 1) == 'l' && charAt(this.bp + i5 + 2) == 'l') {
            this.matchStat = 5;
            int i13 = i5 + 3;
            int i14 = i13 + 1;
            char charAt5 = charAt(this.bp + i13);
            if (z2 && charAt5 == '\"') {
                i14++;
                charAt5 = charAt(this.bp + i14);
            }
            while (charAt5 != ',') {
                if (charAt5 == '}') {
                    this.bp += i14;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return 0.0d;
                } else if (isWhitespace(charAt5)) {
                    i14++;
                    charAt5 = charAt(this.bp + i14);
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            this.bp += i14;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        }
        this.matchStat = -1;
        return 0.0d;
    }

    public BigDecimal scanFieldDecimal(char[] cArr) {
        int i;
        char charAt;
        int i2;
        char c;
        int i3;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i4 = length + 1;
        char charAt2 = charAt(this.bp + length);
        boolean z = charAt2 == '\"';
        if (z) {
            i4++;
            charAt2 = charAt(this.bp + i4);
        }
        if (charAt2 == '-') {
            i4++;
            charAt2 = charAt(this.bp + i4);
        }
        if (charAt2 >= '0' && charAt2 <= '9') {
            while (true) {
                i = i4 + 1;
                charAt = charAt(this.bp + i4);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i4 = i;
            }
            if (charAt == '.') {
                int i5 = i + 1;
                char charAt3 = charAt(this.bp + i);
                if (charAt3 >= '0' && charAt3 <= '9') {
                    while (true) {
                        i = i5 + 1;
                        charAt = charAt(this.bp + i5);
                        if (charAt < '0' || charAt > '9') {
                            break;
                        }
                        i5 = i;
                    }
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            if (charAt == 'e' || charAt == 'E') {
                int i6 = i + 1;
                charAt = charAt(this.bp + i);
                if (charAt == '+' || charAt == '-') {
                    i = i6 + 1;
                    charAt = charAt(this.bp + i6);
                } else {
                    i = i6;
                }
                while (charAt >= '0' && charAt <= '9') {
                    i++;
                    charAt = charAt(this.bp + i);
                }
            }
            if (!z) {
                int i7 = this.bp;
                i3 = cArr.length + i7;
                i2 = ((i7 + i) - i3) - 1;
                c = charAt;
            } else if (charAt != '\"') {
                this.matchStat = -1;
                return null;
            } else {
                int i8 = i + 1;
                c = charAt(this.bp + i);
                int i9 = this.bp;
                i3 = cArr.length + i9 + 1;
                i2 = ((i9 + i8) - i3) - 2;
                i = i8;
            }
            BigDecimal bigDecimal = new BigDecimal(sub_chars(i3, i2));
            if (c == ',') {
                this.bp += i;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return bigDecimal;
            } else if (c == '}') {
                int i10 = i + 1;
                char charAt4 = charAt(this.bp + i);
                if (charAt4 == ',') {
                    this.token = 16;
                    this.bp += i10;
                    this.ch = charAt(this.bp);
                } else if (charAt4 == ']') {
                    this.token = 15;
                    this.bp += i10;
                    this.ch = charAt(this.bp);
                } else if (charAt4 == '}') {
                    this.token = 13;
                    this.bp += i10;
                    this.ch = charAt(this.bp);
                } else if (charAt4 == 26) {
                    this.token = 20;
                    this.bp += i10 - 1;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return bigDecimal;
            } else {
                this.matchStat = -1;
                return null;
            }
        } else if (charAt2 == 'n' && charAt(this.bp + i4) == 'u' && charAt(this.bp + i4 + 1) == 'l' && charAt(this.bp + i4 + 2) == 'l') {
            this.matchStat = 5;
            int i11 = i4 + 3;
            int i12 = i11 + 1;
            char charAt5 = charAt(this.bp + i11);
            if (z && charAt5 == '\"') {
                i12++;
                charAt5 = charAt(this.bp + i12);
            }
            while (charAt5 != ',') {
                if (charAt5 == '}') {
                    this.bp += i12;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                } else if (isWhitespace(charAt5)) {
                    i12++;
                    charAt5 = charAt(this.bp + i12);
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += i12;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public BigInteger scanFieldBigInteger(char[] cArr) {
        char c;
        int i;
        char charAt;
        boolean z;
        int i2;
        char c2;
        int i3;
        BigInteger bigInteger;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i4 = length + 1;
        char charAt2 = charAt(this.bp + length);
        boolean z2 = charAt2 == '\"';
        if (z2) {
            i4++;
            charAt2 = charAt(this.bp + i4);
        }
        boolean z3 = charAt2 == '-';
        if (z3) {
            i4++;
            charAt2 = charAt(this.bp + i4);
        }
        char c3 = '0';
        if (charAt2 >= '0' && charAt2 <= '9') {
            long j = charAt2 - '0';
            while (true) {
                i = i4 + 1;
                charAt = charAt(this.bp + i4);
                if (charAt < c3 || charAt > '9') {
                    break;
                }
                long j2 = (10 * j) + (charAt - '0');
                if (j2 < j) {
                    z = true;
                    break;
                }
                j = j2;
                i4 = i;
                c3 = '0';
            }
            z = false;
            if (!z2) {
                int i5 = this.bp;
                i3 = cArr.length + i5;
                i2 = ((i5 + i) - i3) - 1;
                c2 = charAt;
            } else if (charAt != '\"') {
                this.matchStat = -1;
                return null;
            } else {
                int i6 = i + 1;
                c2 = charAt(this.bp + i);
                int i7 = this.bp;
                i3 = cArr.length + i7 + 1;
                i2 = ((i7 + i6) - i3) - 2;
                i = i6;
            }
            if (z || (i2 >= 20 && (!z3 || i2 >= 21))) {
                bigInteger = new BigInteger(subString(i3, i2));
            } else {
                if (z3) {
                    j = -j;
                }
                bigInteger = BigInteger.valueOf(j);
            }
            if (c2 == ',') {
                this.bp += i;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return bigInteger;
            } else if (c2 == '}') {
                int i8 = i + 1;
                char charAt3 = charAt(this.bp + i);
                if (charAt3 == ',') {
                    this.token = 16;
                    this.bp += i8;
                    this.ch = charAt(this.bp);
                } else if (charAt3 == ']') {
                    this.token = 15;
                    this.bp += i8;
                    this.ch = charAt(this.bp);
                } else if (charAt3 == '}') {
                    this.token = 13;
                    this.bp += i8;
                    this.ch = charAt(this.bp);
                } else if (charAt3 == 26) {
                    this.token = 20;
                    this.bp += i8 - 1;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return bigInteger;
            } else {
                this.matchStat = -1;
                return null;
            }
        } else if (charAt2 == 'n' && charAt(this.bp + i4) == 'u' && charAt(this.bp + i4 + 1) == 'l' && charAt(this.bp + i4 + 2) == 'l') {
            this.matchStat = 5;
            int i9 = i4 + 3;
            int i10 = i9 + 1;
            char charAt4 = charAt(this.bp + i9);
            if (!z2 || charAt4 != '\"') {
                c = StringUtil.COMMA;
            } else {
                i10++;
                charAt4 = charAt(this.bp + i10);
                c = StringUtil.COMMA;
            }
            while (charAt4 != c) {
                if (charAt4 == '}') {
                    this.bp += i10;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                } else if (isWhitespace(charAt4)) {
                    i10++;
                    charAt4 = charAt(this.bp + i10);
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += i10;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public Date scanFieldDate(char[] cArr) {
        Date date;
        int i;
        long j;
        int i2;
        char charAt;
        boolean z = false;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char charAt2 = charAt(this.bp + length);
        if (charAt2 == '\"') {
            int indexOf = indexOf('\"', this.bp + cArr.length + 1);
            if (indexOf != -1) {
                int length2 = this.bp + cArr.length + 1;
                String subString = subString(length2, indexOf - length2);
                if (subString.indexOf(92) != -1) {
                    while (true) {
                        int i4 = 0;
                        for (int i5 = indexOf - 1; i5 >= 0 && charAt(i5) == '\\'; i5--) {
                            i4++;
                        }
                        if (i4 % 2 == 0) {
                            break;
                        }
                        indexOf = indexOf('\"', indexOf + 1);
                    }
                    int i6 = this.bp;
                    int length3 = indexOf - ((cArr.length + i6) + 1);
                    subString = readString(sub_chars(i6 + cArr.length + 1, length3), length3);
                }
                int i7 = this.bp;
                int length4 = i3 + (indexOf - ((cArr.length + i7) + 1)) + 1;
                i = length4 + 1;
                charAt2 = charAt(i7 + length4);
                JSONScanner jSONScanner = new JSONScanner(subString);
                try {
                    if (jSONScanner.scanISO8601DateIfMatch(false)) {
                        date = jSONScanner.getCalendar().getTime();
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                } finally {
                    jSONScanner.close();
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else if (charAt2 == '-' || (charAt2 >= '0' && charAt2 <= '9')) {
            if (charAt2 == '-') {
                i3++;
                charAt2 = charAt(this.bp + i3);
                z = true;
            }
            if (charAt2 < '0' || charAt2 > '9') {
                i = i3;
                j = 0;
            } else {
                j = charAt2 - '0';
                while (true) {
                    i2 = i3 + 1;
                    charAt = charAt(this.bp + i3);
                    if (charAt < '0' || charAt > '9') {
                        break;
                    }
                    j = (j * 10) + (charAt - '0');
                    i3 = i2;
                }
                charAt2 = charAt;
                i = i2;
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
        if (charAt2 == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return date;
        } else if (charAt2 == '}') {
            int i8 = i + 1;
            char charAt3 = charAt(this.bp + i);
            if (charAt3 == ',') {
                this.token = 16;
                this.bp += i8;
                this.ch = charAt(this.bp);
            } else if (charAt3 == ']') {
                this.token = 15;
                this.bp += i8;
                this.ch = charAt(this.bp);
            } else if (charAt3 == '}') {
                this.token = 13;
                this.bp += i8;
                this.ch = charAt(this.bp);
            } else if (charAt3 == 26) {
                this.token = 20;
                this.bp += i8 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return date;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public Date scanDate(char c) {
        int i;
        Date date;
        long j;
        boolean z = false;
        this.matchStat = 0;
        char charAt = charAt(this.bp + 0);
        if (charAt == '\"') {
            int indexOf = indexOf('\"', this.bp + 1);
            if (indexOf != -1) {
                int i2 = this.bp + 1;
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
                    int i5 = this.bp;
                    int i6 = indexOf - (i5 + 1);
                    subString = readString(sub_chars(i5 + 1, i6), i6);
                }
                int i7 = this.bp;
                int i8 = (indexOf - (i7 + 1)) + 1 + 1;
                i = i8 + 1;
                charAt = charAt(i7 + i8);
                JSONScanner jSONScanner = new JSONScanner(subString);
                try {
                    if (jSONScanner.scanISO8601DateIfMatch(false)) {
                        date = jSONScanner.getCalendar().getTime();
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                } finally {
                    jSONScanner.close();
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            char c2 = '9';
            int i9 = 2;
            if (charAt == '-' || (charAt >= '0' && charAt <= '9')) {
                if (charAt == '-') {
                    charAt = charAt(this.bp + 1);
                    z = true;
                } else {
                    i9 = 1;
                }
                if (charAt >= '0' && charAt <= '9') {
                    j = charAt - '0';
                    while (true) {
                        i = i9 + 1;
                        charAt = charAt(this.bp + i9);
                        if (charAt < '0' || charAt > c2) {
                            break;
                        }
                        j = (j * 10) + (charAt - '0');
                        i9 = i;
                        c2 = '9';
                    }
                } else {
                    j = 0;
                    i = i9;
                }
                if (j < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (z) {
                    j = -j;
                }
                date = new Date(j);
            } else if (charAt == 'n' && charAt(this.bp + 1) == 'u' && charAt(this.bp + 1 + 1) == 'l' && charAt(this.bp + 1 + 2) == 'l') {
                this.matchStat = 5;
                charAt = charAt(this.bp + 4);
                i = 5;
                date = null;
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (charAt == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return date;
        } else if (charAt == ']') {
            int i10 = i + 1;
            char charAt2 = charAt(this.bp + i);
            if (charAt2 == ',') {
                this.token = 16;
                this.bp += i10;
                this.ch = charAt(this.bp);
            } else if (charAt2 == ']') {
                this.token = 15;
                this.bp += i10;
                this.ch = charAt(this.bp);
            } else if (charAt2 == '}') {
                this.token = 13;
                this.bp += i10;
                this.ch = charAt(this.bp);
            } else if (charAt2 == 26) {
                this.token = 20;
                this.bp += i10 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return date;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public UUID scanFieldUUID(char[] cArr) {
        int i;
        UUID uuid;
        char c;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i11 = length + 1;
        char charAt = charAt(this.bp + length);
        char c2 = 4;
        if (charAt == '\"') {
            int indexOf = indexOf('\"', this.bp + cArr.length + 1);
            if (indexOf != -1) {
                int length2 = this.bp + cArr.length + 1;
                int i12 = indexOf - length2;
                char c3 = 'F';
                char c4 = 'f';
                char c5 = 'A';
                char c6 = 'a';
                char c7 = '0';
                if (i12 == 36) {
                    int i13 = 0;
                    long j = 0;
                    while (i13 < 8) {
                        char charAt2 = charAt(length2 + i13);
                        if (charAt2 >= '0' && charAt2 <= '9') {
                            i10 = charAt2 - '0';
                        } else if (charAt2 >= 'a' && charAt2 <= 'f') {
                            i10 = (charAt2 - 'a') + 10;
                        } else if (charAt2 < c5 || charAt2 > c3) {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i10 = (charAt2 - 'A') + 10;
                        }
                        j = (j << 4) | i10;
                        i13++;
                        c5 = 'A';
                        c3 = 'F';
                    }
                    int i14 = 9;
                    while (i14 < 13) {
                        char charAt3 = charAt(length2 + i14);
                        if (charAt3 >= '0' && charAt3 <= '9') {
                            i9 = charAt3 - '0';
                        } else if (charAt3 >= 'a' && charAt3 <= c4) {
                            i9 = (charAt3 - 'a') + 10;
                        } else if (charAt3 < 'A' || charAt3 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i9 = (charAt3 - 'A') + 10;
                        }
                        j = (j << 4) | i9;
                        i14++;
                        indexOf = indexOf;
                        c4 = 'f';
                    }
                    long j2 = j;
                    for (int i15 = 14; i15 < 18; i15++) {
                        char charAt4 = charAt(length2 + i15);
                        if (charAt4 >= '0' && charAt4 <= '9') {
                            i8 = charAt4 - '0';
                        } else if (charAt4 >= 'a' && charAt4 <= 'f') {
                            i8 = (charAt4 - 'a') + 10;
                        } else if (charAt4 < 'A' || charAt4 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i8 = (charAt4 - 'A') + 10;
                        }
                        j2 = (j2 << 4) | i8;
                    }
                    int i16 = 19;
                    long j3 = 0;
                    while (i16 < 23) {
                        char charAt5 = charAt(length2 + i16);
                        if (charAt5 >= '0' && charAt5 <= '9') {
                            i7 = charAt5 - '0';
                        } else if (charAt5 >= 'a' && charAt5 <= 'f') {
                            i7 = (charAt5 - 'a') + 10;
                        } else if (charAt5 < 'A' || charAt5 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i7 = (charAt5 - 'A') + 10;
                        }
                        j3 = (j3 << c2) | i7;
                        i16++;
                        j2 = j2;
                        c2 = 4;
                    }
                    long j4 = j3;
                    for (int i17 = 24; i17 < 36; i17++) {
                        char charAt6 = charAt(length2 + i17);
                        if (charAt6 >= '0' && charAt6 <= '9') {
                            i6 = charAt6 - '0';
                        } else if (charAt6 >= 'a' && charAt6 <= 'f') {
                            i6 = (charAt6 - 'a') + 10;
                        } else if (charAt6 < 'A' || charAt6 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i6 = (charAt6 - 'A') + 10;
                        }
                        j4 = (j4 << 4) | i6;
                    }
                    uuid = new UUID(j2, j4);
                    int i18 = this.bp;
                    int length3 = i11 + (indexOf - ((cArr.length + i18) + 1)) + 1;
                    i = length3 + 1;
                    c = charAt(i18 + length3);
                } else if (i12 == 32) {
                    int i19 = 0;
                    long j5 = 0;
                    for (int i20 = 16; i19 < i20; i20 = 16) {
                        char charAt7 = charAt(length2 + i19);
                        if (charAt7 >= '0' && charAt7 <= '9') {
                            i5 = charAt7 - '0';
                        } else if (charAt7 >= 'a' && charAt7 <= 'f') {
                            i5 = (charAt7 - 'a') + 10;
                        } else if (charAt7 < 'A' || charAt7 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i5 = (charAt7 - 'A') + 10;
                        }
                        j5 = (j5 << 4) | i5;
                        i19++;
                    }
                    int i21 = 16;
                    long j6 = 0;
                    while (i21 < 32) {
                        char charAt8 = charAt(length2 + i21);
                        if (charAt8 < c7 || charAt8 > '9') {
                            if (charAt8 >= c6 && charAt8 <= 'f') {
                                i4 = (charAt8 - 'a') + 10;
                            }
                            if (charAt8 < 'A' || charAt8 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i4 = (charAt8 - 'A') + 10;
                        } else {
                            i4 = charAt8 - '0';
                        }
                        j6 = (j6 << 4) | i4;
                        i21++;
                        c7 = '0';
                        c6 = 'a';
                    }
                    uuid = new UUID(j5, j6);
                    int i22 = this.bp;
                    int length4 = i11 + (indexOf - ((cArr.length + i22) + 1)) + 1;
                    i = length4 + 1;
                    c = charAt(i22 + length4);
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            if (charAt == 'n') {
                int i23 = i11 + 1;
                if (charAt(this.bp + i11) == 'u') {
                    int i24 = i23 + 1;
                    if (charAt(this.bp + i23) == 'l') {
                        int i25 = i24 + 1;
                        if (charAt(this.bp + i24) == 'l') {
                            i = i25 + 1;
                            c = charAt(this.bp + i25);
                            uuid = null;
                        } else {
                            i3 = -1;
                        }
                    } else {
                        i3 = -1;
                    }
                } else {
                    i3 = -1;
                }
            } else {
                i3 = -1;
            }
            this.matchStat = i3;
            return null;
        }
        if (c == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return uuid;
        } else if (c == '}') {
            int i26 = i + 1;
            char charAt9 = charAt(this.bp + i);
            if (charAt9 == ',') {
                this.token = 16;
                this.bp += i26;
                this.ch = charAt(this.bp);
                i2 = 4;
            } else if (charAt9 == ']') {
                this.token = 15;
                this.bp += i26;
                this.ch = charAt(this.bp);
                i2 = 4;
            } else if (charAt9 == '}') {
                this.token = 13;
                this.bp += i26;
                this.ch = charAt(this.bp);
                i2 = 4;
            } else if (charAt9 == 26) {
                this.token = 20;
                this.bp += i26 - 1;
                this.ch = JSONLexer.EOI;
                i2 = 4;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = i2;
            return uuid;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public UUID scanUUID(char c) {
        int i;
        char c2;
        UUID uuid;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        this.matchStat = 0;
        char charAt = charAt(this.bp + 0);
        if (charAt == '\"') {
            int indexOf = indexOf('\"', this.bp + 1);
            if (indexOf != -1) {
                int i10 = this.bp + 1;
                int i11 = indexOf - i10;
                char c3 = '9';
                char c4 = 'A';
                char c5 = 'a';
                char c6 = '0';
                if (i11 == 36) {
                    int i12 = 0;
                    long j = 0;
                    while (i12 < 8) {
                        char charAt2 = charAt(i10 + i12);
                        if (charAt2 >= '0' && charAt2 <= '9') {
                            i9 = charAt2 - '0';
                        } else if (charAt2 >= c5 && charAt2 <= 'f') {
                            i9 = (charAt2 - 'a') + 10;
                        } else if (charAt2 < c4 || charAt2 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i9 = (charAt2 - 'A') + 10;
                        }
                        j = (j << 4) | i9;
                        i12++;
                        c4 = 'A';
                        c5 = 'a';
                    }
                    int i13 = 9;
                    while (i13 < 13) {
                        char charAt3 = charAt(i10 + i13);
                        if (charAt3 >= '0' && charAt3 <= c3) {
                            i8 = charAt3 - '0';
                        } else if (charAt3 >= 'a' && charAt3 <= 'f') {
                            i8 = (charAt3 - 'a') + 10;
                        } else if (charAt3 < 'A' || charAt3 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i8 = (charAt3 - 'A') + 10;
                        }
                        j = (j << 4) | i8;
                        i13++;
                        indexOf = indexOf;
                        c3 = '9';
                    }
                    long j2 = j;
                    for (int i14 = 14; i14 < 18; i14++) {
                        char charAt4 = charAt(i10 + i14);
                        if (charAt4 >= '0' && charAt4 <= '9') {
                            i7 = charAt4 - '0';
                        } else if (charAt4 >= 'a' && charAt4 <= 'f') {
                            i7 = (charAt4 - 'a') + 10;
                        } else if (charAt4 < 'A' || charAt4 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i7 = (charAt4 - 'A') + 10;
                        }
                        j2 = (j2 << 4) | i7;
                    }
                    int i15 = 19;
                    long j3 = 0;
                    while (i15 < 23) {
                        char charAt5 = charAt(i10 + i15);
                        if (charAt5 >= c6 && charAt5 <= '9') {
                            i6 = charAt5 - '0';
                        } else if (charAt5 >= 'a' && charAt5 <= 'f') {
                            i6 = (charAt5 - 'a') + 10;
                        } else if (charAt5 < 'A' || charAt5 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i6 = (charAt5 - 'A') + 10;
                        }
                        j3 = (j3 << 4) | i6;
                        i15++;
                        c6 = '0';
                    }
                    long j4 = j3;
                    for (int i16 = 24; i16 < 36; i16++) {
                        char charAt6 = charAt(i10 + i16);
                        if (charAt6 >= '0' && charAt6 <= '9') {
                            i5 = charAt6 - '0';
                        } else if (charAt6 >= 'a' && charAt6 <= 'f') {
                            i5 = (charAt6 - 'a') + 10;
                        } else if (charAt6 < 'A' || charAt6 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i5 = (charAt6 - 'A') + 10;
                        }
                        j4 = (j4 << 4) | i5;
                    }
                    uuid = new UUID(j2, j4);
                    int i17 = this.bp;
                    int i18 = (indexOf - (i17 + 1)) + 1 + 1;
                    i = i18 + 1;
                    c2 = charAt(i17 + i18);
                } else if (i11 == 32) {
                    long j5 = 0;
                    for (int i19 = 0; i19 < 16; i19++) {
                        char charAt7 = charAt(i10 + i19);
                        if (charAt7 >= '0' && charAt7 <= '9') {
                            i4 = charAt7 - '0';
                        } else if (charAt7 >= 'a' && charAt7 <= 'f') {
                            i4 = (charAt7 - 'a') + 10;
                        } else if (charAt7 < 'A' || charAt7 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i4 = (charAt7 - 'A') + 10;
                        }
                        j5 = (j5 << 4) | i4;
                    }
                    long j6 = 0;
                    for (int i20 = 16; i20 < 32; i20++) {
                        char charAt8 = charAt(i10 + i20);
                        if (charAt8 >= '0' && charAt8 <= '9') {
                            i3 = charAt8 - '0';
                            j6 = (j6 << 4) | i3;
                        }
                        if (charAt8 >= 'a' && charAt8 <= 'f') {
                            i3 = (charAt8 - 'a') + 10;
                        } else if (charAt8 < 'A' || charAt8 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            i3 = (charAt8 - 'A') + 10;
                        }
                        j6 = (j6 << 4) | i3;
                    }
                    uuid = new UUID(j5, j6);
                    int i21 = this.bp;
                    int i22 = (indexOf - (i21 + 1)) + 1 + 1;
                    i = i22 + 1;
                    c2 = charAt(i21 + i22);
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            if (charAt != 'n') {
                i2 = -1;
            } else if (charAt(this.bp + 1) != 'u') {
                i2 = -1;
            } else if (charAt(this.bp + 2) != 'l') {
                i2 = -1;
            } else if (charAt(this.bp + 3) == 'l') {
                i = 5;
                c2 = charAt(this.bp + 4);
                uuid = null;
            } else {
                i2 = -1;
            }
            this.matchStat = i2;
            return null;
        }
        if (c2 == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return uuid;
        } else if (c2 == ']') {
            int i23 = i + 1;
            char charAt9 = charAt(this.bp + i);
            if (charAt9 == ',') {
                this.token = 16;
                this.bp += i23;
                this.ch = charAt(this.bp);
            } else if (charAt9 == ']') {
                this.token = 15;
                this.bp += i23;
                this.ch = charAt(this.bp);
            } else if (charAt9 == '}') {
                this.token = 13;
                this.bp += i23;
                this.ch = charAt(this.bp);
            } else if (charAt9 == 26) {
                this.token = 20;
                this.bp += i23 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return uuid;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public final void scanTrue() {
        if (this.ch == 't') {
            next();
            if (this.ch == 'r') {
                next();
                if (this.ch == 'u') {
                    next();
                    if (this.ch == 'e') {
                        next();
                        char c = this.ch;
                        if (c == ' ' || c == ',' || c == '}' || c == ']' || c == '\n' || c == '\r' || c == '\t' || c == 26 || c == '\f' || c == '\b' || c == ':' || c == '/') {
                            this.token = 6;
                            return;
                        }
                        throw new JSONException("scan true error");
                    }
                    throw new JSONException("error parse true");
                }
                throw new JSONException("error parse true");
            }
            throw new JSONException("error parse true");
        }
        throw new JSONException("error parse true");
    }

    public final void scanNullOrNew() {
        scanNullOrNew(true);
    }

    public final void scanNullOrNew(boolean z) {
        char c;
        if (this.ch == 'n') {
            next();
            char c2 = this.ch;
            if (c2 == 'u') {
                next();
                if (this.ch == 'l') {
                    next();
                    if (this.ch == 'l') {
                        next();
                        char c3 = this.ch;
                        if (c3 == ' ' || c3 == ',' || c3 == '}' || c3 == ']' || c3 == '\n' || c3 == '\r' || c3 == '\t' || c3 == 26 || ((c3 == ':' && z) || (c = this.ch) == '\f' || c == '\b')) {
                            this.token = 8;
                            return;
                        }
                        throw new JSONException("scan null error");
                    }
                    throw new JSONException("error parse null");
                }
                throw new JSONException("error parse null");
            } else if (c2 == 'e') {
                next();
                if (this.ch == 'w') {
                    next();
                    char c4 = this.ch;
                    if (c4 == ' ' || c4 == ',' || c4 == '}' || c4 == ']' || c4 == '\n' || c4 == '\r' || c4 == '\t' || c4 == 26 || c4 == '\f' || c4 == '\b') {
                        this.token = 9;
                        return;
                    }
                    throw new JSONException("scan new error");
                }
                throw new JSONException("error parse new");
            } else {
                throw new JSONException("error parse new");
            }
        } else {
            throw new JSONException("error parse null or new");
        }
    }

    public final void scanFalse() {
        if (this.ch == 'f') {
            next();
            if (this.ch == 'a') {
                next();
                if (this.ch == 'l') {
                    next();
                    if (this.ch == 's') {
                        next();
                        if (this.ch == 'e') {
                            next();
                            char c = this.ch;
                            if (c == ' ' || c == ',' || c == '}' || c == ']' || c == '\n' || c == '\r' || c == '\t' || c == 26 || c == '\f' || c == '\b' || c == ':' || c == '/') {
                                this.token = 7;
                                return;
                            }
                            throw new JSONException("scan false error");
                        }
                        throw new JSONException("error parse false");
                    }
                    throw new JSONException("error parse false");
                }
                throw new JSONException("error parse false");
            }
            throw new JSONException("error parse false");
        }
        throw new JSONException("error parse false");
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String stringVal = stringVal();
        if ("null".equalsIgnoreCase(stringVal)) {
            this.token = 8;
        } else if ("new".equals(stringVal)) {
            this.token = 9;
        } else if ("true".equals(stringVal)) {
            this.token = 6;
        } else if ("false".equals(stringVal)) {
            this.token = 7;
        } else if ("undefined".equals(stringVal)) {
            this.token = 23;
        } else if ("Set".equals(stringVal)) {
            this.token = 21;
        } else if ("TreeSet".equals(stringVal)) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    public static String readString(char[] cArr, int i) {
        char[] cArr2 = new char[i];
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            char c = cArr[i2];
            if (c != '\\') {
                i3++;
                cArr2[i3] = c;
            } else {
                i2++;
                char c2 = cArr[i2];
                switch (c2) {
                    case '/':
                        i3++;
                        cArr2[i3] = JsonPointer.SEPARATOR;
                        continue;
                    case '0':
                        i3++;
                        cArr2[i3] = 0;
                        continue;
                    case '1':
                        i3++;
                        cArr2[i3] = 1;
                        continue;
                    case '2':
                        i3++;
                        cArr2[i3] = 2;
                        continue;
                    case '3':
                        i3++;
                        cArr2[i3] = 3;
                        continue;
                    case '4':
                        i3++;
                        cArr2[i3] = 4;
                        continue;
                    case '5':
                        i3++;
                        cArr2[i3] = 5;
                        continue;
                    case '6':
                        i3++;
                        cArr2[i3] = 6;
                        continue;
                    case '7':
                        i3++;
                        cArr2[i3] = 7;
                        continue;
                    default:
                        switch (c2) {
                            case 't':
                                i3++;
                                cArr2[i3] = '\t';
                                continue;
                            case 'u':
                                i3++;
                                int i4 = i2 + 1;
                                int i5 = i4 + 1;
                                int i6 = i5 + 1;
                                i2 = i6 + 1;
                                cArr2[i3] = (char) Integer.parseInt(new String(new char[]{cArr[i4], cArr[i5], cArr[i6], cArr[i2]}), 16);
                                continue;
                            case 'v':
                                i3++;
                                cArr2[i3] = 11;
                                continue;
                            default:
                                switch (c2) {
                                    case '\"':
                                        i3++;
                                        cArr2[i3] = '\"';
                                        continue;
                                    case '\'':
                                        i3++;
                                        cArr2[i3] = '\'';
                                        continue;
                                    case 'F':
                                    case 'f':
                                        i3++;
                                        cArr2[i3] = '\f';
                                        continue;
                                    case '\\':
                                        i3++;
                                        cArr2[i3] = '\\';
                                        continue;
                                    case 'b':
                                        i3++;
                                        cArr2[i3] = '\b';
                                        continue;
                                    case 'n':
                                        i3++;
                                        cArr2[i3] = '\n';
                                        continue;
                                    case 'r':
                                        i3++;
                                        cArr2[i3] = '\r';
                                        continue;
                                    case 'x':
                                        i3++;
                                        int[] iArr = digits;
                                        int i7 = i2 + 1;
                                        i2 = i7 + 1;
                                        cArr2[i3] = (char) ((iArr[cArr[i7]] * 16) + iArr[cArr[i2]]);
                                        continue;
                                        continue;
                                        continue;
                                    default:
                                        throw new JSONException("unclosed.str.lit");
                                }
                        }
                }
            }
            i2++;
        }
        return new String(cArr2, 0, i3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean isBlankInput() {
        int i = 0;
        while (true) {
            char charAt = charAt(i);
            if (charAt == 26) {
                this.token = 20;
                return true;
            } else if (!isWhitespace(charAt)) {
                return false;
            } else {
                i++;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void skipWhitespace() {
        while (true) {
            char c = this.ch;
            if (c > '/') {
                return;
            }
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == '\f' || c == '\b') {
                next();
            } else if (c == '/') {
                skipComment();
            } else {
                return;
            }
        }
    }

    private void scanStringSingleQuote() {
        char next;
        char next2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char next3 = next();
            if (next3 == '\'') {
                this.token = 4;
                next();
                return;
            } else if (next3 != 26) {
                boolean z = true;
                if (next3 == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        int i = this.sp;
                        char[] cArr = this.sbuf;
                        if (i > cArr.length) {
                            char[] cArr2 = new char[i * 2];
                            System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                            this.sbuf = cArr2;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char next4 = next();
                    switch (next4) {
                        case '/':
                            putChar(JsonPointer.SEPARATOR);
                            continue;
                        case '0':
                            putChar((char) 0);
                            continue;
                        case '1':
                            putChar((char) 1);
                            continue;
                        case '2':
                            putChar((char) 2);
                            continue;
                        case '3':
                            putChar((char) 3);
                            continue;
                        case '4':
                            putChar((char) 4);
                            continue;
                        case '5':
                            putChar((char) 5);
                            continue;
                        case '6':
                            putChar((char) 6);
                            continue;
                        case '7':
                            putChar((char) 7);
                            continue;
                        default:
                            switch (next4) {
                                case 't':
                                    putChar('\t');
                                    continue;
                                case 'u':
                                    putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                                    continue;
                                case 'v':
                                    putChar((char) 11);
                                    continue;
                                default:
                                    switch (next4) {
                                        case '\"':
                                            putChar('\"');
                                            continue;
                                        case '\'':
                                            putChar('\'');
                                            continue;
                                        case 'F':
                                        case 'f':
                                            putChar('\f');
                                            continue;
                                        case '\\':
                                            putChar('\\');
                                            continue;
                                        case 'b':
                                            putChar('\b');
                                            continue;
                                        case 'n':
                                            putChar('\n');
                                            continue;
                                        case 'r':
                                            putChar('\r');
                                            continue;
                                        case 'x':
                                            next = next();
                                            next2 = next();
                                            boolean z2 = (next >= '0' && next <= '9') || (next >= 'a' && next <= 'f') || (next >= 'A' && next <= 'F');
                                            if ((next2 < '0' || next2 > '9') && ((next2 < 'a' || next2 > 'f') && (next2 < 'A' || next2 > 'F'))) {
                                                z = false;
                                            }
                                            if (z2 && z) {
                                                int[] iArr = digits;
                                                putChar((char) ((iArr[next] * 16) + iArr[next2]));
                                                continue;
                                                continue;
                                                continue;
                                            }
                                            break;
                                        default:
                                            this.ch = next4;
                                            throw new JSONException("unclosed single-quote string");
                                    }
                            }
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else {
                    int i2 = this.sp;
                    char[] cArr3 = this.sbuf;
                    if (i2 == cArr3.length) {
                        putChar(next3);
                    } else {
                        this.sp = i2 + 1;
                        cArr3[i2] = next3;
                    }
                }
            } else if (!isEOF()) {
                putChar(JSONLexer.EOI);
            } else {
                throw new JSONException("unclosed single-quote string");
            }
        }
        throw new JSONException("invalid escape character \\x" + next + next2);
    }

    protected final void putChar(char c) {
        int i = this.sp;
        char[] cArr = this.sbuf;
        if (i == cArr.length) {
            char[] cArr2 = new char[cArr.length * 2];
            System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
            this.sbuf = cArr2;
        }
        char[] cArr3 = this.sbuf;
        int i2 = this.sp;
        this.sp = i2 + 1;
        cArr3[i2] = c;
    }

    public final void scanHex() {
        char next;
        if (this.ch == 'x') {
            next();
            if (this.ch == '\'') {
                this.np = this.bp;
                next();
                if (this.ch == '\'') {
                    next();
                    this.token = 26;
                    return;
                }
                while (true) {
                    next = next();
                    if ((next < '0' || next > '9') && (next < 'A' || next > 'F')) {
                        break;
                    }
                    this.sp++;
                }
                if (next == '\'') {
                    this.sp++;
                    next();
                    this.token = 26;
                    return;
                }
                throw new JSONException("illegal state. " + next);
            }
            throw new JSONException("illegal state. " + this.ch);
        }
        throw new JSONException("illegal state. " + this.ch);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ce  */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void scanNumber() {
        /*
            Method dump skipped, instructions count: 210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanNumber():void");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final long longValue() throws NumberFormatException {
        long j;
        long j2;
        boolean z = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i = this.np;
        int i2 = this.sp + i;
        if (charAt(i) == '-') {
            j = Long.MIN_VALUE;
            i++;
            z = true;
        } else {
            j = C.TIME_UNSET;
        }
        if (i < i2) {
            i++;
            j2 = -(charAt(i) - '0');
        } else {
            j2 = 0;
        }
        while (i < i2) {
            int i3 = i + 1;
            char charAt = charAt(i);
            if (charAt == 'L' || charAt == 'S' || charAt == 'B') {
                i = i3;
                break;
            }
            int i4 = charAt - '0';
            if (j2 >= -922337203685477580L) {
                long j3 = j2 * 10;
                long j4 = i4;
                if (j3 >= j + j4) {
                    j2 = j3 - j4;
                    i = i3;
                } else {
                    throw new NumberFormatException(numberString());
                }
            } else {
                throw new NumberFormatException(numberString());
            }
        }
        if (!z) {
            return -j2;
        }
        if (i > this.np + 1) {
            return j2;
        }
        throw new NumberFormatException(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number decimalValue(boolean z) {
        char charAt = charAt((this.np + this.sp) - 1);
        try {
            if (charAt == 'F') {
                return Float.valueOf(Float.parseFloat(numberString()));
            }
            if (charAt == 'D') {
                return Double.valueOf(Double.parseDouble(numberString()));
            }
            if (z) {
                return decimalValue();
            }
            return Double.valueOf(doubleValue());
        } catch (NumberFormatException e) {
            throw new JSONException(e.getMessage() + ", " + info());
        }
    }

    public String[] scanFieldStringArray(char[] cArr, int i, SymbolTable symbolTable) {
        throw new UnsupportedOperationException();
    }

    public boolean matchField2(char[] cArr) {
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int getFeatures() {
        return this.features;
    }
}
