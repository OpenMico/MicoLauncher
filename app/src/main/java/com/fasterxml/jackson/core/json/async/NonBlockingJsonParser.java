package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.TextBuffer;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class NonBlockingJsonParser extends NonBlockingJsonParserBase implements ByteArrayFeeder {
    protected byte[] _inputBuffer = NO_BYTES;
    protected int _origBufferLen;
    private static final int[] a = CharTypes.getInputCodeUtf8();
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();

    @Override // com.fasterxml.jackson.core.JsonParser
    public ByteArrayFeeder getNonBlockingInputFeeder() {
        return this;
    }

    public NonBlockingJsonParser(IOContext iOContext, int i, ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
        super(iOContext, i, byteQuadsCanonicalizer);
    }

    @Override // com.fasterxml.jackson.core.async.NonBlockingInputFeeder
    public final boolean needMoreInput() {
        return this._inputPtr >= this._inputEnd && !this._endOfInput;
    }

    @Override // com.fasterxml.jackson.core.async.ByteArrayFeeder
    public void feedInput(byte[] bArr, int i, int i2) throws IOException {
        if (this._inputPtr < this._inputEnd) {
            _reportError("Still have %d undecoded bytes, should not call 'feedInput'", Integer.valueOf(this._inputEnd - this._inputPtr));
        }
        if (i2 < i) {
            _reportError("Input end (%d) may not be before start (%d)", Integer.valueOf(i2), Integer.valueOf(i));
        }
        if (this._endOfInput) {
            _reportError("Already closed, can not feed more input");
        }
        this._currInputProcessed += this._origBufferLen;
        this._currInputRowStart = i - (this._inputEnd - this._currInputRowStart);
        this._currBufferStart = i;
        this._inputBuffer = bArr;
        this._inputPtr = i;
        this._inputEnd = i2;
        this._origBufferLen = i2 - i;
    }

    @Override // com.fasterxml.jackson.core.async.NonBlockingInputFeeder
    public void endOfInput() {
        this._endOfInput = true;
    }

    @Override // com.fasterxml.jackson.core.json.async.NonBlockingJsonParserBase, com.fasterxml.jackson.core.JsonParser
    public int releaseBuffered(OutputStream outputStream) throws IOException {
        int i = this._inputEnd - this._inputPtr;
        if (i > 0) {
            outputStream.write(this._inputBuffer, this._inputPtr, i);
        }
        return i;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected char _decodeEscaped() throws IOException {
        VersionUtil.throwInternal();
        return ' ';
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public JsonToken nextToken() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            if (this._closed) {
                return null;
            }
            if (!this._endOfInput) {
                return JsonToken.NOT_AVAILABLE;
            }
            if (this._currToken == JsonToken.NOT_AVAILABLE) {
                return _finishTokenWithEOF();
            }
            return _eofAsNextToken();
        } else if (this._currToken == JsonToken.NOT_AVAILABLE) {
            return _finishToken();
        } else {
            this._numTypesValid = 0;
            this._tokenInputTotal = this._currInputProcessed + this._inputPtr;
            this._binaryValue = null;
            byte[] bArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            int i2 = bArr[i] & 255;
            switch (this._majorState) {
                case 0:
                    return a(i2);
                case 1:
                    return e(i2);
                case 2:
                    return c(i2);
                case 3:
                    return d(i2);
                case 4:
                    return g(i2);
                case 5:
                    return e(i2);
                case 6:
                    return f(i2);
                default:
                    VersionUtil.throwInternal();
                    return null;
            }
        }
    }

    protected final JsonToken _finishToken() throws IOException {
        switch (this._minorState) {
            case 1:
                return b(this._pending32);
            case 2:
            case 3:
            case 6:
            case 11:
            case 20:
            case 21:
            case 22:
            case 27:
            case 28:
            case 29:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 46:
            case 47:
            case 48:
            case 49:
            default:
                VersionUtil.throwInternal();
                return null;
            case 4:
                byte[] bArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                return c(bArr[i] & 255);
            case 5:
                byte[] bArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                return d(bArr2[i2] & 255);
            case 7:
                return b(this._quadLength, this._pending32, this._pendingBytes);
            case 8:
                return _finishFieldWithEscape();
            case 9:
                return d(this._quadLength, this._pending32, this._pendingBytes);
            case 10:
                return c(this._quadLength, this._pending32, this._pendingBytes);
            case 12:
                byte[] bArr3 = this._inputBuffer;
                int i3 = this._inputPtr;
                this._inputPtr = i3 + 1;
                return e(bArr3[i3] & 255);
            case 13:
                byte[] bArr4 = this._inputBuffer;
                int i4 = this._inputPtr;
                this._inputPtr = i4 + 1;
                return f(bArr4[i4] & 255);
            case 14:
                byte[] bArr5 = this._inputBuffer;
                int i5 = this._inputPtr;
                this._inputPtr = i5 + 1;
                return g(bArr5[i5] & 255);
            case 15:
                byte[] bArr6 = this._inputBuffer;
                int i6 = this._inputPtr;
                this._inputPtr = i6 + 1;
                return h(bArr6[i6] & 255);
            case 16:
                return _finishKeywordToken("null", this._pending32, JsonToken.VALUE_NULL);
            case 17:
                return _finishKeywordToken("true", this._pending32, JsonToken.VALUE_TRUE);
            case 18:
                return _finishKeywordToken("false", this._pending32, JsonToken.VALUE_FALSE);
            case 19:
                return _finishNonStdToken(this._nonStdTokenType, this._pending32);
            case 23:
                byte[] bArr7 = this._inputBuffer;
                int i7 = this._inputPtr;
                this._inputPtr = i7 + 1;
                return _finishNumberMinus(bArr7[i7] & 255);
            case 24:
                return _finishNumberLeadingZeroes();
            case 25:
                return _finishNumberLeadingNegZeroes();
            case 26:
                return _finishNumberIntegralPart(this._textBuffer.getBufferWithoutReset(), this._textBuffer.getCurrentSegmentSize());
            case 30:
                return _finishFloatFraction();
            case 31:
                byte[] bArr8 = this._inputBuffer;
                int i8 = this._inputPtr;
                this._inputPtr = i8 + 1;
                return _finishFloatExponent(true, bArr8[i8] & 255);
            case 32:
                byte[] bArr9 = this._inputBuffer;
                int i9 = this._inputPtr;
                this._inputPtr = i9 + 1;
                return _finishFloatExponent(false, bArr9[i9] & 255);
            case 40:
                return b();
            case 41:
                int b = b(this._quoted32, this._quotedDigits);
                if (b < 0) {
                    return JsonToken.NOT_AVAILABLE;
                }
                this._textBuffer.append((char) b);
                if (this._minorStateAfterSplit == 45) {
                    return c();
                }
                return b();
            case 42:
                TextBuffer textBuffer = this._textBuffer;
                int i10 = this._pending32;
                byte[] bArr10 = this._inputBuffer;
                int i11 = this._inputPtr;
                this._inputPtr = i11 + 1;
                textBuffer.append((char) c(i10, bArr10[i11]));
                if (this._minorStateAfterSplit == 45) {
                    return c();
                }
                return b();
            case 43:
                int i12 = this._pending32;
                int i13 = this._pendingBytes;
                byte[] bArr11 = this._inputBuffer;
                int i14 = this._inputPtr;
                this._inputPtr = i14 + 1;
                if (!e(i12, i13, bArr11[i14])) {
                    return JsonToken.NOT_AVAILABLE;
                }
                if (this._minorStateAfterSplit == 45) {
                    return c();
                }
                return b();
            case 44:
                int i15 = this._pending32;
                int i16 = this._pendingBytes;
                byte[] bArr12 = this._inputBuffer;
                int i17 = this._inputPtr;
                this._inputPtr = i17 + 1;
                if (!f(i15, i16, bArr12[i17])) {
                    return JsonToken.NOT_AVAILABLE;
                }
                if (this._minorStateAfterSplit == 45) {
                    return c();
                }
                return b();
            case 45:
                return c();
            case 50:
                return _finishErrorToken();
            case 51:
                return j(this._pending32);
            case 52:
                return a(this._pending32, true);
            case 53:
                return a(this._pending32, false);
            case 54:
                return l(this._pending32);
            case 55:
                return k(this._pending32);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected final JsonToken _finishTokenWithEOF() throws IOException {
        JsonToken jsonToken = this._currToken;
        int i = this._minorState;
        if (i == 3) {
            return _eofAsNextToken();
        }
        if (i == 12) {
            return _eofAsNextToken();
        }
        if (i == 50) {
            return _finishErrorTokenWithEOF();
        }
        switch (i) {
            case 16:
                return _finishKeywordTokenWithEOF("null", this._pending32, JsonToken.VALUE_NULL);
            case 17:
                return _finishKeywordTokenWithEOF("true", this._pending32, JsonToken.VALUE_TRUE);
            case 18:
                return _finishKeywordTokenWithEOF("false", this._pending32, JsonToken.VALUE_FALSE);
            case 19:
                return _finishNonStdTokenWithEOF(this._nonStdTokenType, this._pending32);
            default:
                switch (i) {
                    case 24:
                    case 25:
                        return _valueCompleteInt(0, "0");
                    case 26:
                        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                        if (this._numberNegative) {
                            currentSegmentSize--;
                        }
                        this._intLength = currentSegmentSize;
                        return _valueComplete(JsonToken.VALUE_NUMBER_INT);
                    default:
                        switch (i) {
                            case 30:
                                this._expLength = 0;
                                return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
                            case 31:
                                _reportInvalidEOF(": was expecting fraction after exponent marker", JsonToken.VALUE_NUMBER_FLOAT);
                                _reportInvalidEOF(": was expecting closing '*/' for comment", JsonToken.NOT_AVAILABLE);
                                return _eofAsNextToken();
                            case 32:
                                return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
                            default:
                                switch (i) {
                                    case 52:
                                    case 53:
                                        _reportInvalidEOF(": was expecting closing '*/' for comment", JsonToken.NOT_AVAILABLE);
                                        break;
                                    case 54:
                                    case 55:
                                        break;
                                    default:
                                        _reportInvalidEOF(": was expecting rest of token (internal state: " + this._minorState + ")", this._currToken);
                                        return jsonToken;
                                }
                                return _eofAsNextToken();
                        }
                }
        }
    }

    private final JsonToken a(int i) throws IOException {
        int i2 = i & 255;
        if (i2 == 239 && this._minorState != 1) {
            return b(1);
        }
        while (i2 <= 32) {
            if (i2 != 32) {
                if (i2 == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i2 == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i2 != 9) {
                    _throwInvalidSpace(i2);
                }
            }
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 3;
                if (this._closed) {
                    return null;
                }
                if (this._endOfInput) {
                    return _eofAsNextToken();
                }
                return JsonToken.NOT_AVAILABLE;
            }
            byte[] bArr = this._inputBuffer;
            int i3 = this._inputPtr;
            this._inputPtr = i3 + 1;
            i2 = bArr[i3] & 255;
        }
        return e(i2);
    }

    private final JsonToken b(int i) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            int i3 = bArr[i2] & 255;
            switch (i) {
                case 1:
                    if (i3 == 187) {
                        break;
                    } else {
                        _reportError("Unexpected byte 0x%02x following 0xEF; should get 0xBB as second byte UTF-8 BOM", Integer.valueOf(i3));
                        break;
                    }
                case 2:
                    if (i3 == 191) {
                        break;
                    } else {
                        _reportError("Unexpected byte 0x%02x following 0xEF 0xBB; should get 0xBF as third byte of UTF-8 BOM", Integer.valueOf(i3));
                        break;
                    }
                case 3:
                    this._currInputProcessed -= 3;
                    return a(i3);
            }
            i++;
        }
        this._pending32 = i;
        this._minorState = 1;
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken c(int i) throws IOException {
        String a2;
        if (i > 32 || (i = i(i)) > 0) {
            _updateTokenLocation();
            if (i != 34) {
                if (i == 125) {
                    return _closeObjectScope();
                }
                return n(i);
            } else if (this._inputPtr + 13 > this._inputEnd || (a2 = a()) == null) {
                return b(0, 0, 0);
            } else {
                return _fieldComplete(a2);
            }
        } else {
            this._minorState = 4;
            return this._currToken;
        }
    }

    private final JsonToken d(int i) throws IOException {
        String a2;
        if (i > 32 || (i = i(i)) > 0) {
            if (i != 44) {
                if (i == 125) {
                    return _closeObjectScope();
                }
                if (i == 35) {
                    return k(5);
                }
                if (i == 47) {
                    return j(5);
                }
                _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            int i2 = this._inputPtr;
            if (i2 >= this._inputEnd) {
                this._minorState = 4;
                JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
                this._currToken = jsonToken;
                return jsonToken;
            }
            byte b = this._inputBuffer[i2];
            this._inputPtr = i2 + 1;
            byte b2 = b;
            if (b <= 32) {
                int i3 = i(b);
                b2 = i3;
                if (i3 <= 0) {
                    this._minorState = 4;
                    return this._currToken;
                }
            }
            _updateTokenLocation();
            if (b2 != 34) {
                if (b2 != 125 || !JsonParser.Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features)) {
                    return n(b2 == 1 ? 1 : 0);
                }
                return _closeObjectScope();
            } else if (this._inputPtr + 13 > this._inputEnd || (a2 = a()) == null) {
                return b(0, 0, 0);
            } else {
                return _fieldComplete(a2);
            }
        } else {
            this._minorState = 5;
            return this._currToken;
        }
    }

    private final JsonToken e(int i) throws IOException {
        if (i > 32 || (i = i(i)) > 0) {
            _updateTokenLocation();
            if (i == 34) {
                return _startString();
            }
            if (i == 35) {
                return k(12);
            }
            if (i == 45) {
                return _startNegativeNumber();
            }
            if (i == 91) {
                return _startArrayScope();
            }
            if (i == 93) {
                return _closeArrayScope();
            }
            if (i == 102) {
                return _startFalseToken();
            }
            if (i == 110) {
                return _startNullToken();
            }
            if (i == 116) {
                return _startTrueToken();
            }
            if (i == 123) {
                return _startObjectScope();
            }
            if (i == 125) {
                return _closeObjectScope();
            }
            switch (i) {
                case 47:
                    return j(12);
                case 48:
                    return _startNumberLeadingZero();
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    return _startPositiveNumber(i);
                default:
                    return _startUnexpectedValue(false, i);
            }
        } else {
            this._minorState = 12;
            return this._currToken;
        }
    }

    private final JsonToken f(int i) throws IOException {
        if (i > 32 || (i = i(i)) > 0) {
            if (i != 44) {
                if (i == 93) {
                    return _closeArrayScope();
                }
                if (i == 125) {
                    return _closeObjectScope();
                }
                if (i == 47) {
                    return j(13);
                }
                if (i == 35) {
                    return k(13);
                }
                _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            int i2 = this._inputPtr;
            if (i2 >= this._inputEnd) {
                this._minorState = 15;
                JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
                this._currToken = jsonToken;
                return jsonToken;
            }
            byte b = this._inputBuffer[i2];
            this._inputPtr = i2 + 1;
            byte b2 = b;
            if (b <= 32) {
                int i3 = i(b);
                b2 = i3;
                if (i3 <= 0) {
                    this._minorState = 15;
                    return this._currToken;
                }
            }
            _updateTokenLocation();
            if (b2 == 34) {
                return _startString();
            }
            if (b2 == 35) {
                return k(15);
            }
            if (b2 == 45) {
                return _startNegativeNumber();
            }
            if (b2 == 91) {
                return _startArrayScope();
            }
            if (b2 != 93) {
                if (b2 == 102) {
                    return _startFalseToken();
                }
                if (b2 == 110) {
                    return _startNullToken();
                }
                if (b2 == 116) {
                    return _startTrueToken();
                }
                if (b2 == 123) {
                    return _startObjectScope();
                }
                if (b2 != 125) {
                    switch (b2) {
                        case 47:
                            return j(15);
                        case 48:
                            return _startNumberLeadingZero();
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                            return _startPositiveNumber(b2);
                    }
                } else if (isEnabled(JsonParser.Feature.ALLOW_TRAILING_COMMA)) {
                    return _closeObjectScope();
                }
            } else if (isEnabled(JsonParser.Feature.ALLOW_TRAILING_COMMA)) {
                return _closeArrayScope();
            }
            return _startUnexpectedValue(true, b2 == 1 ? 1 : 0);
        }
        this._minorState = 13;
        return this._currToken;
    }

    private final JsonToken g(int i) throws IOException {
        if (i > 32 || (i = i(i)) > 0) {
            if (i != 58) {
                if (i == 47) {
                    return j(14);
                }
                if (i == 35) {
                    return k(14);
                }
                _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
            }
            int i2 = this._inputPtr;
            if (i2 >= this._inputEnd) {
                this._minorState = 12;
                JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
                this._currToken = jsonToken;
                return jsonToken;
            }
            byte b = this._inputBuffer[i2];
            this._inputPtr = i2 + 1;
            byte b2 = b;
            if (b <= 32) {
                int i3 = i(b);
                b2 = i3;
                if (i3 <= 0) {
                    this._minorState = 12;
                    return this._currToken;
                }
            }
            _updateTokenLocation();
            if (b2 == 34) {
                return _startString();
            }
            if (b2 == 35) {
                return k(12);
            }
            if (b2 == 45) {
                return _startNegativeNumber();
            }
            if (b2 == 91) {
                return _startArrayScope();
            }
            if (b2 == 102) {
                return _startFalseToken();
            }
            if (b2 == 110) {
                return _startNullToken();
            }
            if (b2 == 116) {
                return _startTrueToken();
            }
            if (b2 == 123) {
                return _startObjectScope();
            }
            switch (b2) {
                case 47:
                    return j(12);
                case 48:
                    return _startNumberLeadingZero();
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    return _startPositiveNumber(b2 == 1 ? 1 : 0);
                default:
                    return _startUnexpectedValue(false, b2);
            }
        } else {
            this._minorState = 14;
            return this._currToken;
        }
    }

    private final JsonToken h(int i) throws IOException {
        if (i > 32 || (i = i(i)) > 0) {
            _updateTokenLocation();
            if (i == 34) {
                return _startString();
            }
            if (i == 35) {
                return k(15);
            }
            if (i == 45) {
                return _startNegativeNumber();
            }
            if (i == 91) {
                return _startArrayScope();
            }
            if (i != 93) {
                if (i == 102) {
                    return _startFalseToken();
                }
                if (i == 110) {
                    return _startNullToken();
                }
                if (i == 116) {
                    return _startTrueToken();
                }
                if (i == 123) {
                    return _startObjectScope();
                }
                if (i != 125) {
                    switch (i) {
                        case 47:
                            return j(15);
                        case 48:
                            return _startNumberLeadingZero();
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                            return _startPositiveNumber(i);
                    }
                } else if (isEnabled(JsonParser.Feature.ALLOW_TRAILING_COMMA)) {
                    return _closeObjectScope();
                }
            } else if (isEnabled(JsonParser.Feature.ALLOW_TRAILING_COMMA)) {
                return _closeArrayScope();
            }
            return _startUnexpectedValue(true, i);
        }
        this._minorState = 15;
        return this._currToken;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0025, code lost:
        if (r1._parsingContext.inArray() == false) goto L_0x0054;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.core.JsonToken _startUnexpectedValue(boolean r2, int r3) throws java.io.IOException {
        /*
            r1 = this;
            r2 = 39
            if (r3 == r2) goto L_0x0047
            r2 = 73
            r0 = 1
            if (r3 == r2) goto L_0x0042
            r2 = 78
            if (r3 == r2) goto L_0x003c
            r2 = 93
            if (r3 == r2) goto L_0x001f
            r2 = 125(0x7d, float:1.75E-43)
            if (r3 == r2) goto L_0x0054
            switch(r3) {
                case 43: goto L_0x0019;
                case 44: goto L_0x0028;
                default: goto L_0x0018;
            }
        L_0x0018:
            goto L_0x0054
        L_0x0019:
            r2 = 2
            com.fasterxml.jackson.core.JsonToken r2 = r1._finishNonStdToken(r2, r0)
            return r2
        L_0x001f:
            com.fasterxml.jackson.core.json.JsonReadContext r2 = r1._parsingContext
            boolean r2 = r2.inArray()
            if (r2 != 0) goto L_0x0028
            goto L_0x0054
        L_0x0028:
            com.fasterxml.jackson.core.JsonParser$Feature r2 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES
            boolean r2 = r1.isEnabled(r2)
            if (r2 == 0) goto L_0x0054
            int r2 = r1._inputPtr
            int r2 = r2 - r0
            r1._inputPtr = r2
            com.fasterxml.jackson.core.JsonToken r2 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL
            com.fasterxml.jackson.core.JsonToken r2 = r1._valueComplete(r2)
            return r2
        L_0x003c:
            r2 = 0
            com.fasterxml.jackson.core.JsonToken r2 = r1._finishNonStdToken(r2, r0)
            return r2
        L_0x0042:
            com.fasterxml.jackson.core.JsonToken r2 = r1._finishNonStdToken(r0, r0)
            return r2
        L_0x0047:
            com.fasterxml.jackson.core.JsonParser$Feature r2 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES
            boolean r2 = r1.isEnabled(r2)
            if (r2 == 0) goto L_0x0054
            com.fasterxml.jackson.core.JsonToken r2 = r1._startAposString()
            return r2
        L_0x0054:
            java.lang.String r2 = "expected a valid value (number, String, array, object, 'true', 'false' or 'null')"
            r1._reportUnexpectedChar(r3, r2)
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.async.NonBlockingJsonParser._startUnexpectedValue(boolean, int):com.fasterxml.jackson.core.JsonToken");
    }

    private final int i(int i) throws IOException {
        do {
            if (i != 32) {
                if (i == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i != 9) {
                    _throwInvalidSpace(i);
                }
            }
            if (this._inputPtr >= this._inputEnd) {
                this._currToken = JsonToken.NOT_AVAILABLE;
                return 0;
            }
            byte[] bArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            i = bArr[i2] & 255;
        } while (i <= 32);
        return i;
    }

    private final JsonToken j(int i) throws IOException {
        if (!JsonParser.Feature.ALLOW_COMMENTS.enabledIn(this._features)) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd) {
            this._pending32 = i;
            this._minorState = 51;
            JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
            this._currToken = jsonToken;
            return jsonToken;
        }
        byte[] bArr = this._inputBuffer;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        byte b = bArr[i2];
        if (b == 42) {
            return a(i, false);
        }
        if (b == 47) {
            return l(i);
        }
        _reportUnexpectedChar(b & 255, "was expecting either '*' or '/' for a comment");
        return null;
    }

    private final JsonToken k(int i) throws IOException {
        if (!JsonParser.Feature.ALLOW_YAML_COMMENTS.enabledIn(this._features)) {
            _reportUnexpectedChar(35, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_YAML_COMMENTS' not enabled for parser)");
        }
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            int i3 = bArr[i2] & 255;
            if (i3 < 32) {
                if (i3 == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i3 == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i3 != 9) {
                    _throwInvalidSpace(i3);
                }
                return m(i);
            }
        }
        this._minorState = 55;
        this._pending32 = i;
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken l(int i) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            int i3 = bArr[i2] & 255;
            if (i3 < 32) {
                if (i3 == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i3 == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i3 != 9) {
                    _throwInvalidSpace(i3);
                }
                return m(i);
            }
        }
        this._minorState = 54;
        this._pending32 = i;
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken a(int i, boolean z) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            int i3 = bArr[i2] & 255;
            if (i3 < 32) {
                if (i3 == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i3 == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i3 != 9) {
                    _throwInvalidSpace(i3);
                }
            } else if (i3 == 42) {
                z = true;
            } else if (i3 == 47 && z) {
                return m(i);
            }
            z = false;
        }
        this._minorState = z ? 52 : 53;
        this._pending32 = i;
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken m(int i) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = i;
            JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
            this._currToken = jsonToken;
            return jsonToken;
        }
        byte[] bArr = this._inputBuffer;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        int i3 = bArr[i2] & 255;
        switch (i) {
            case 4:
                return c(i3);
            case 5:
                return d(i3);
            default:
                switch (i) {
                    case 12:
                        return e(i3);
                    case 13:
                        return f(i3);
                    case 14:
                        return g(i3);
                    case 15:
                        return h(i3);
                    default:
                        VersionUtil.throwInternal();
                        return null;
                }
        }
    }

    protected JsonToken _startFalseToken() throws IOException {
        int i;
        int i2 = this._inputPtr;
        if (i2 + 4 < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i3 = i2 + 1;
            if (bArr[i2] == 97) {
                int i4 = i3 + 1;
                if (bArr[i3] == 108) {
                    int i5 = i4 + 1;
                    if (bArr[i4] == 115) {
                        int i6 = i5 + 1;
                        if (bArr[i5] == 101 && ((i = bArr[i6] & 255) < 48 || i == 93 || i == 125)) {
                            this._inputPtr = i6;
                            return _valueComplete(JsonToken.VALUE_FALSE);
                        }
                    }
                }
            }
        }
        this._minorState = 18;
        return _finishKeywordToken("false", 1, JsonToken.VALUE_FALSE);
    }

    protected JsonToken _startTrueToken() throws IOException {
        int i;
        int i2 = this._inputPtr;
        if (i2 + 3 < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i3 = i2 + 1;
            if (bArr[i2] == 114) {
                int i4 = i3 + 1;
                if (bArr[i3] == 117) {
                    int i5 = i4 + 1;
                    if (bArr[i4] == 101 && ((i = bArr[i5] & 255) < 48 || i == 93 || i == 125)) {
                        this._inputPtr = i5;
                        return _valueComplete(JsonToken.VALUE_TRUE);
                    }
                }
            }
        }
        this._minorState = 17;
        return _finishKeywordToken("true", 1, JsonToken.VALUE_TRUE);
    }

    protected JsonToken _startNullToken() throws IOException {
        int i;
        int i2 = this._inputPtr;
        if (i2 + 3 < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i3 = i2 + 1;
            if (bArr[i2] == 117) {
                int i4 = i3 + 1;
                if (bArr[i3] == 108) {
                    int i5 = i4 + 1;
                    if (bArr[i4] == 108 && ((i = bArr[i5] & 255) < 48 || i == 93 || i == 125)) {
                        this._inputPtr = i5;
                        return _valueComplete(JsonToken.VALUE_NULL);
                    }
                }
            }
        }
        this._minorState = 16;
        return _finishKeywordToken("null", 1, JsonToken.VALUE_NULL);
    }

    protected JsonToken _finishKeywordToken(String str, int i, JsonToken jsonToken) throws IOException {
        int length = str.length();
        while (this._inputPtr < this._inputEnd) {
            byte b = this._inputBuffer[this._inputPtr];
            if (i == length) {
                if (b < 48 || b == 93 || b == 125) {
                    return _valueComplete(jsonToken);
                }
            } else if (b == str.charAt(i)) {
                i++;
                this._inputPtr++;
            }
            this._minorState = 50;
            this._textBuffer.resetWithCopy(str, 0, i);
            return _finishErrorToken();
        }
        this._pending32 = i;
        JsonToken jsonToken2 = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken2;
        return jsonToken2;
    }

    protected JsonToken _finishKeywordTokenWithEOF(String str, int i, JsonToken jsonToken) throws IOException {
        if (i == str.length()) {
            this._currToken = jsonToken;
            return jsonToken;
        }
        this._textBuffer.resetWithCopy(str, 0, i);
        return _finishErrorTokenWithEOF();
    }

    protected JsonToken _finishNonStdToken(int i, int i2) throws IOException {
        String _nonStdToken = _nonStdToken(i);
        int length = _nonStdToken.length();
        while (this._inputPtr < this._inputEnd) {
            byte b = this._inputBuffer[this._inputPtr];
            if (i2 == length) {
                if (b < 48 || b == 93 || b == 125) {
                    return _valueNonStdNumberComplete(i);
                }
            } else if (b == _nonStdToken.charAt(i2)) {
                i2++;
                this._inputPtr++;
            }
            this._minorState = 50;
            this._textBuffer.resetWithCopy(_nonStdToken, 0, i2);
            return _finishErrorToken();
        }
        this._nonStdTokenType = i;
        this._pending32 = i2;
        this._minorState = 19;
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    protected JsonToken _finishNonStdTokenWithEOF(int i, int i2) throws IOException {
        String _nonStdToken = _nonStdToken(i);
        if (i2 == _nonStdToken.length()) {
            return _valueNonStdNumberComplete(i);
        }
        this._textBuffer.resetWithCopy(_nonStdToken, 0, i2);
        return _finishErrorTokenWithEOF();
    }

    protected JsonToken _finishErrorToken() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = (char) bArr[i];
            if (Character.isJavaIdentifierPart(c)) {
                this._textBuffer.append(c);
                if (this._textBuffer.size() >= 256) {
                }
            }
            return _reportErrorToken(this._textBuffer.contentsAsString());
        }
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    protected JsonToken _finishErrorTokenWithEOF() throws IOException {
        return _reportErrorToken(this._textBuffer.contentsAsString());
    }

    protected JsonToken _reportErrorToken(String str) throws IOException {
        _reportError("Unrecognized token '%s': was expecting %s", this._textBuffer.contentsAsString(), "'null', 'true' or 'false'");
        return JsonToken.NOT_AVAILABLE;
    }

    protected JsonToken _startPositiveNumber(int i) throws IOException {
        this._numberNegative = false;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        emptyAndGetCurrentSegment[0] = (char) i;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(1);
            JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
            this._currToken = jsonToken;
            return jsonToken;
        }
        int i2 = this._inputBuffer[this._inputPtr] & 255;
        int i3 = 1;
        while (true) {
            if (i2 < 48) {
                if (i2 == 46) {
                    this._intLength = i3;
                    this._inputPtr++;
                    return _startFloat(emptyAndGetCurrentSegment, i3, i2);
                }
            } else if (i2 <= 57) {
                if (i3 >= emptyAndGetCurrentSegment.length) {
                    emptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
                }
                int i4 = i3 + 1;
                emptyAndGetCurrentSegment[i3] = (char) i2;
                int i5 = this._inputPtr + 1;
                this._inputPtr = i5;
                if (i5 >= this._inputEnd) {
                    this._minorState = 26;
                    this._textBuffer.setCurrentLength(i4);
                    JsonToken jsonToken2 = JsonToken.NOT_AVAILABLE;
                    this._currToken = jsonToken2;
                    return jsonToken2;
                }
                i2 = this._inputBuffer[this._inputPtr] & 255;
                i3 = i4;
            } else if (i2 == 101 || i2 == 69) {
                this._intLength = i3;
                this._inputPtr++;
                return _startFloat(emptyAndGetCurrentSegment, i3, i2);
            }
        }
        this._intLength = i3;
        this._textBuffer.setCurrentLength(i3);
        return _valueComplete(JsonToken.VALUE_NUMBER_INT);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x008c, code lost:
        r8._intLength = r3 - 1;
        r8._textBuffer.setCurrentLength(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x009b, code lost:
        return _valueComplete(com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.core.JsonToken _startNegativeNumber() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 215
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.async.NonBlockingJsonParser._startNegativeNumber():com.fasterxml.jackson.core.JsonToken");
    }

    protected JsonToken _startNumberLeadingZero() throws IOException {
        int i = this._inputPtr;
        if (i >= this._inputEnd) {
            this._minorState = 24;
            JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
            this._currToken = jsonToken;
            return jsonToken;
        }
        int i2 = i + 1;
        int i3 = this._inputBuffer[i] & 255;
        if (i3 < 48) {
            if (i3 == 46) {
                this._inputPtr = i2;
                this._intLength = 1;
                char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
                emptyAndGetCurrentSegment[0] = '0';
                return _startFloat(emptyAndGetCurrentSegment, 1, i3);
            }
        } else if (i3 <= 57) {
            return _finishNumberLeadingZeroes();
        } else {
            if (i3 == 101 || i3 == 69) {
                this._inputPtr = i2;
                this._intLength = 1;
                char[] emptyAndGetCurrentSegment2 = this._textBuffer.emptyAndGetCurrentSegment();
                emptyAndGetCurrentSegment2[0] = '0';
                return _startFloat(emptyAndGetCurrentSegment2, 1, i3);
            } else if (!(i3 == 93 || i3 == 125)) {
                reportUnexpectedNumberChar(i3, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
        }
        return _valueCompleteInt(0, "0");
    }

    protected JsonToken _finishNumberMinus(int i) throws IOException {
        if (i <= 48) {
            if (i == 48) {
                return _finishNumberLeadingNegZeroes();
            }
            reportUnexpectedNumberChar(i, "expected digit (0-9) to follow minus sign, for valid numeric value");
        } else if (i > 57) {
            if (i == 73) {
                return _finishNonStdToken(3, 2);
            }
            reportUnexpectedNumberChar(i, "expected digit (0-9) to follow minus sign, for valid numeric value");
        }
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        emptyAndGetCurrentSegment[0] = '-';
        emptyAndGetCurrentSegment[1] = (char) i;
        this._intLength = 1;
        return _finishNumberIntegralPart(emptyAndGetCurrentSegment, 2);
    }

    protected JsonToken _finishNumberLeadingZeroes() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            int i2 = bArr[i] & 255;
            if (i2 < 48) {
                if (i2 == 46) {
                    char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
                    emptyAndGetCurrentSegment[0] = '0';
                    this._intLength = 1;
                    return _startFloat(emptyAndGetCurrentSegment, 1, i2);
                }
            } else if (i2 <= 57) {
                if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
                    reportInvalidNumber("Leading zeroes not allowed");
                    continue;
                }
                if (i2 != 48) {
                    char[] emptyAndGetCurrentSegment2 = this._textBuffer.emptyAndGetCurrentSegment();
                    emptyAndGetCurrentSegment2[0] = (char) i2;
                    this._intLength = 1;
                    return _finishNumberIntegralPart(emptyAndGetCurrentSegment2, 1);
                }
            } else if (i2 == 101 || i2 == 69) {
                char[] emptyAndGetCurrentSegment3 = this._textBuffer.emptyAndGetCurrentSegment();
                emptyAndGetCurrentSegment3[0] = '0';
                this._intLength = 1;
                return _startFloat(emptyAndGetCurrentSegment3, 1, i2);
            } else if (!(i2 == 93 || i2 == 125)) {
                reportUnexpectedNumberChar(i2, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
            this._inputPtr--;
            return _valueCompleteInt(0, "0");
        }
        this._minorState = 24;
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    protected JsonToken _finishNumberLeadingNegZeroes() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            int i2 = bArr[i] & 255;
            if (i2 < 48) {
                if (i2 == 46) {
                    char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
                    emptyAndGetCurrentSegment[0] = '-';
                    emptyAndGetCurrentSegment[1] = '0';
                    this._intLength = 1;
                    return _startFloat(emptyAndGetCurrentSegment, 2, i2);
                }
            } else if (i2 <= 57) {
                if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
                    reportInvalidNumber("Leading zeroes not allowed");
                    continue;
                }
                if (i2 != 48) {
                    char[] emptyAndGetCurrentSegment2 = this._textBuffer.emptyAndGetCurrentSegment();
                    emptyAndGetCurrentSegment2[0] = '-';
                    emptyAndGetCurrentSegment2[1] = (char) i2;
                    this._intLength = 1;
                    return _finishNumberIntegralPart(emptyAndGetCurrentSegment2, 2);
                }
            } else if (i2 == 101 || i2 == 69) {
                char[] emptyAndGetCurrentSegment3 = this._textBuffer.emptyAndGetCurrentSegment();
                emptyAndGetCurrentSegment3[0] = '-';
                emptyAndGetCurrentSegment3[1] = '0';
                this._intLength = 1;
                return _startFloat(emptyAndGetCurrentSegment3, 2, i2);
            } else if (!(i2 == 93 || i2 == 125)) {
                reportUnexpectedNumberChar(i2, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
            this._inputPtr--;
            return _valueCompleteInt(0, "0");
        }
        this._minorState = 25;
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    protected JsonToken _finishNumberIntegralPart(char[] cArr, int i) throws IOException {
        int i2 = this._numberNegative ? -1 : 0;
        while (this._inputPtr < this._inputEnd) {
            int i3 = this._inputBuffer[this._inputPtr] & 255;
            if (i3 < 48) {
                if (i3 == 46) {
                    this._intLength = i2 + i;
                    this._inputPtr++;
                    return _startFloat(cArr, i, i3);
                }
            } else if (i3 <= 57) {
                this._inputPtr++;
                if (i >= cArr.length) {
                    cArr = this._textBuffer.expandCurrentSegment();
                }
                i++;
                cArr[i] = (char) i3;
            } else if (i3 == 101 || i3 == 69) {
                this._intLength = i2 + i;
                this._inputPtr++;
                return _startFloat(cArr, i, i3);
            }
            this._intLength = i2 + i;
            this._textBuffer.setCurrentLength(i);
            return _valueComplete(JsonToken.VALUE_NUMBER_INT);
        }
        this._minorState = 26;
        this._textBuffer.setCurrentLength(i);
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    protected JsonToken _startFloat(char[] cArr, int i, int i2) throws IOException {
        char[] cArr2;
        int i3;
        int i4;
        int i5 = 0;
        if (i2 == 46) {
            if (i >= cArr.length) {
                cArr = this._textBuffer.expandCurrentSegment();
            }
            int i6 = i + 1;
            cArr[i] = '.';
            char[] cArr3 = cArr;
            i3 = 0;
            while (this._inputPtr < this._inputEnd) {
                byte[] bArr = this._inputBuffer;
                int i7 = this._inputPtr;
                this._inputPtr = i7 + 1;
                byte b = bArr[i7];
                if (b < 48 || b > 57) {
                    int i8 = b & 255;
                    if (i3 == 0) {
                        reportUnexpectedNumberChar(i8, "Decimal point not followed by a digit");
                    }
                    i4 = i6;
                    cArr2 = cArr3;
                    i2 = i8;
                } else {
                    if (i6 >= cArr3.length) {
                        cArr3 = this._textBuffer.expandCurrentSegment();
                    }
                    i6++;
                    cArr3[i6] = (char) b;
                    i3++;
                }
            }
            this._textBuffer.setCurrentLength(i6);
            this._minorState = 30;
            this._fractLength = i3;
            JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
            this._currToken = jsonToken;
            return jsonToken;
        }
        i4 = i;
        cArr2 = cArr;
        i3 = 0;
        this._fractLength = i3;
        if (i2 == 101 || i2 == 69) {
            if (i4 >= cArr2.length) {
                cArr2 = this._textBuffer.expandCurrentSegment();
            }
            int i9 = i4 + 1;
            cArr2[i4] = (char) i2;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(i9);
                this._minorState = 31;
                this._expLength = 0;
                JsonToken jsonToken2 = JsonToken.NOT_AVAILABLE;
                this._currToken = jsonToken2;
                return jsonToken2;
            }
            byte[] bArr2 = this._inputBuffer;
            int i10 = this._inputPtr;
            this._inputPtr = i10 + 1;
            byte b2 = bArr2[i10];
            if (b2 == 45 || b2 == 43) {
                if (i9 >= cArr2.length) {
                    cArr2 = this._textBuffer.expandCurrentSegment();
                }
                i4 = i9 + 1;
                cArr2[i9] = (char) b2;
                if (this._inputPtr >= this._inputEnd) {
                    this._textBuffer.setCurrentLength(i4);
                    this._minorState = 32;
                    this._expLength = 0;
                    JsonToken jsonToken3 = JsonToken.NOT_AVAILABLE;
                    this._currToken = jsonToken3;
                    return jsonToken3;
                }
                byte[] bArr3 = this._inputBuffer;
                int i11 = this._inputPtr;
                this._inputPtr = i11 + 1;
                b2 = bArr3[i11];
            } else {
                i4 = i9;
            }
            while (b2 >= 48 && b2 <= 57) {
                i5++;
                if (i4 >= cArr2.length) {
                    cArr2 = this._textBuffer.expandCurrentSegment();
                }
                int i12 = i4 + 1;
                cArr2[i4] = (char) b2;
                if (this._inputPtr >= this._inputEnd) {
                    this._textBuffer.setCurrentLength(i12);
                    this._minorState = 32;
                    this._expLength = i5;
                    JsonToken jsonToken4 = JsonToken.NOT_AVAILABLE;
                    this._currToken = jsonToken4;
                    return jsonToken4;
                }
                byte[] bArr4 = this._inputBuffer;
                int i13 = this._inputPtr;
                this._inputPtr = i13 + 1;
                b2 = bArr4[i13];
                i4 = i12;
            }
            int i14 = b2 & 255;
            if (i5 == 0) {
                reportUnexpectedNumberChar(i14, "Exponent indicator not followed by a digit");
            }
        }
        this._inputPtr--;
        this._textBuffer.setCurrentLength(i4);
        this._expLength = i5;
        return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
    }

    protected JsonToken _finishFloatFraction() throws IOException {
        byte b;
        int i = this._fractLength;
        char[] bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            byte[] bArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            b = bArr[i2];
            if (b < 48 || b > 57) {
                break;
            }
            i++;
            if (currentSegmentSize >= bufferWithoutReset.length) {
                bufferWithoutReset = this._textBuffer.expandCurrentSegment();
            }
            int i3 = currentSegmentSize + 1;
            bufferWithoutReset[currentSegmentSize] = (char) b;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(i3);
                this._fractLength = i;
                return JsonToken.NOT_AVAILABLE;
            }
            currentSegmentSize = i3;
        }
        if (i == 0) {
            reportUnexpectedNumberChar(b, "Decimal point not followed by a digit");
        }
        this._fractLength = i;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        if (b == 101 || b == 69) {
            this._textBuffer.append((char) b);
            this._expLength = 0;
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 31;
                return JsonToken.NOT_AVAILABLE;
            }
            this._minorState = 32;
            byte[] bArr2 = this._inputBuffer;
            int i4 = this._inputPtr;
            this._inputPtr = i4 + 1;
            return _finishFloatExponent(true, bArr2[i4] & 255);
        }
        this._inputPtr--;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        this._expLength = 0;
        return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, byte], vars: [r6v0 ??, r6v1 ??, r6v11 ??, r6v2 ??, r6v7 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
        */
    protected com.fasterxml.jackson.core.JsonToken _finishFloatExponent(boolean r5, 
    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, byte], vars: [r6v0 ??, r6v1 ??, r6v11 ??, r6v2 ??, r6v7 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r6v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    private final String a() throws IOException {
        byte[] bArr = this._inputBuffer;
        int[] iArr = _icLatin1;
        int i = this._inputPtr;
        int i2 = i + 1;
        int i3 = bArr[i] & 255;
        if (iArr[i3] == 0) {
            int i4 = i2 + 1;
            int i5 = bArr[i2] & 255;
            if (iArr[i5] == 0) {
                int i6 = (i3 << 8) | i5;
                int i7 = i4 + 1;
                int i8 = bArr[i4] & 255;
                if (iArr[i8] == 0) {
                    int i9 = (i6 << 8) | i8;
                    int i10 = i7 + 1;
                    int i11 = bArr[i7] & 255;
                    if (iArr[i11] == 0) {
                        int i12 = (i9 << 8) | i11;
                        int i13 = i10 + 1;
                        int i14 = bArr[i10] & 255;
                        if (iArr[i14] == 0) {
                            this._quad1 = i12;
                            return a(i13, i14);
                        } else if (i14 != 34) {
                            return null;
                        } else {
                            this._inputPtr = i13;
                            return _findName(i12, 4);
                        }
                    } else if (i11 != 34) {
                        return null;
                    } else {
                        this._inputPtr = i10;
                        return _findName(i9, 3);
                    }
                } else if (i8 != 34) {
                    return null;
                } else {
                    this._inputPtr = i7;
                    return _findName(i6, 2);
                }
            } else if (i5 != 34) {
                return null;
            } else {
                this._inputPtr = i4;
                return _findName(i3, 1);
            }
        } else if (i3 != 34) {
            return null;
        } else {
            this._inputPtr = i2;
            return "";
        }
    }

    private final String a(int i, int i2) throws IOException {
        byte[] bArr = this._inputBuffer;
        int[] iArr = _icLatin1;
        int i3 = i + 1;
        int i4 = bArr[i] & 255;
        if (iArr[i4] == 0) {
            int i5 = i4 | (i2 << 8);
            int i6 = i3 + 1;
            int i7 = bArr[i3] & 255;
            if (iArr[i7] == 0) {
                int i8 = (i5 << 8) | i7;
                int i9 = i6 + 1;
                int i10 = bArr[i6] & 255;
                if (iArr[i10] == 0) {
                    int i11 = (i8 << 8) | i10;
                    int i12 = i9 + 1;
                    int i13 = bArr[i9] & 255;
                    if (iArr[i13] == 0) {
                        return a(i12, i13, i11);
                    }
                    if (i13 != 34) {
                        return null;
                    }
                    this._inputPtr = i12;
                    return _findName(this._quad1, i11, 4);
                } else if (i10 != 34) {
                    return null;
                } else {
                    this._inputPtr = i9;
                    return _findName(this._quad1, i8, 3);
                }
            } else if (i7 != 34) {
                return null;
            } else {
                this._inputPtr = i6;
                return _findName(this._quad1, i5, 2);
            }
        } else if (i4 != 34) {
            return null;
        } else {
            this._inputPtr = i3;
            return _findName(this._quad1, i2, 1);
        }
    }

    private final String a(int i, int i2, int i3) throws IOException {
        byte[] bArr = this._inputBuffer;
        int[] iArr = _icLatin1;
        int i4 = i + 1;
        int i5 = bArr[i] & 255;
        if (iArr[i5] == 0) {
            int i6 = i5 | (i2 << 8);
            int i7 = i4 + 1;
            int i8 = bArr[i4] & 255;
            if (iArr[i8] == 0) {
                int i9 = (i6 << 8) | i8;
                int i10 = i7 + 1;
                int i11 = bArr[i7] & 255;
                if (iArr[i11] == 0) {
                    int i12 = (i9 << 8) | i11;
                    int i13 = i10 + 1;
                    if ((bArr[i10] & 255) != 34) {
                        return null;
                    }
                    this._inputPtr = i13;
                    return _findName(this._quad1, i3, i12, 4);
                } else if (i11 != 34) {
                    return null;
                } else {
                    this._inputPtr = i10;
                    return _findName(this._quad1, i3, i9, 3);
                }
            } else if (i8 != 34) {
                return null;
            } else {
                this._inputPtr = i7;
                return _findName(this._quad1, i3, i6, 2);
            }
        } else if (i5 != 34) {
            return null;
        } else {
            this._inputPtr = i4;
            return _findName(this._quad1, i3, i2, 1);
        }
    }

    private final JsonToken b(int i, int i2, int i3) throws IOException {
        int[] iArr = this._quadBuffer;
        int[] iArr2 = _icLatin1;
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i4 = this._inputPtr;
            this._inputPtr = i4 + 1;
            int i5 = bArr[i4] & 255;
            if (iArr2[i5] == 0) {
                if (i3 < 4) {
                    i3++;
                    i2 = (i2 << 8) | i5;
                } else {
                    if (i >= iArr.length) {
                        int[] growArrayBy = growArrayBy(iArr, iArr.length);
                        this._quadBuffer = growArrayBy;
                        iArr = growArrayBy;
                    }
                    i++;
                    iArr[i] = i2;
                    i3 = 1;
                    i2 = i5;
                }
            } else if (i5 == 34) {
                if (i3 > 0) {
                    if (i >= iArr.length) {
                        iArr = growArrayBy(iArr, iArr.length);
                        this._quadBuffer = iArr;
                    }
                    i++;
                    iArr[i] = _padLastQuad(i2, i3);
                } else if (i == 0) {
                    return _fieldComplete("");
                }
                String findName = this._symbols.findName(iArr, i);
                if (findName == null) {
                    findName = _addName(iArr, i, i3);
                }
                return _fieldComplete(findName);
            } else {
                if (i5 != 92) {
                    _throwUnquotedSpace(i5, "name");
                } else {
                    i5 = d();
                    if (i5 < 0) {
                        this._minorState = 8;
                        this._minorStateAfterSplit = 7;
                        this._quadLength = i;
                        this._pending32 = i2;
                        this._pendingBytes = i3;
                        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
                        this._currToken = jsonToken;
                        return jsonToken;
                    }
                }
                if (i >= iArr.length) {
                    iArr = growArrayBy(iArr, iArr.length);
                    this._quadBuffer = iArr;
                }
                if (i5 > 127) {
                    if (i3 >= 4) {
                        i++;
                        iArr[i] = i2;
                        i2 = 0;
                        i3 = 0;
                    }
                    if (i5 < 2048) {
                        i2 = (i2 << 8) | (i5 >> 6) | 192;
                        i3++;
                    } else {
                        int i6 = (i2 << 8) | (i5 >> 12) | 224;
                        int i7 = i3 + 1;
                        if (i7 >= 4) {
                            i++;
                            iArr[i] = i6;
                            i6 = 0;
                            i7 = 0;
                        }
                        i2 = (i6 << 8) | ((i5 >> 6) & 63) | 128;
                        i3 = i7 + 1;
                    }
                    i5 = (i5 & 63) | 128;
                }
                if (i3 < 4) {
                    i3++;
                    i2 = (i2 << 8) | i5;
                } else {
                    i++;
                    iArr[i] = i2;
                    i3 = 1;
                    i2 = i5;
                }
            }
        }
        this._quadLength = i;
        this._pending32 = i2;
        this._pendingBytes = i3;
        this._minorState = 7;
        JsonToken jsonToken2 = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken2;
        return jsonToken2;
    }

    private JsonToken n(int i) throws IOException {
        if (i != 35) {
            if (i != 39) {
                if (i == 47) {
                    return j(4);
                }
                if (i == 93) {
                    return _closeArrayScope();
                }
            } else if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
                return d(0, 0, 0);
            }
        } else if (JsonParser.Feature.ALLOW_YAML_COMMENTS.enabledIn(this._features)) {
            return k(4);
        }
        if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            _reportUnexpectedChar((char) i, "was expecting double-quote to start field name");
        }
        if (CharTypes.getInputCodeUtf8JsNames()[i] != 0) {
            _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        return c(0, i, 1);
    }

    private JsonToken c(int i, int i2, int i3) throws IOException {
        int[] iArr = this._quadBuffer;
        int[] inputCodeUtf8JsNames = CharTypes.getInputCodeUtf8JsNames();
        while (this._inputPtr < this._inputEnd) {
            int i4 = this._inputBuffer[this._inputPtr] & 255;
            if (inputCodeUtf8JsNames[i4] != 0) {
                if (i3 > 0) {
                    if (i >= iArr.length) {
                        iArr = growArrayBy(iArr, iArr.length);
                        this._quadBuffer = iArr;
                    }
                    i++;
                    iArr[i] = i2;
                }
                String findName = this._symbols.findName(iArr, i);
                if (findName == null) {
                    findName = _addName(iArr, i, i3);
                }
                return _fieldComplete(findName);
            }
            this._inputPtr++;
            if (i3 < 4) {
                i3++;
                i2 = (i2 << 8) | i4;
            } else {
                if (i >= iArr.length) {
                    iArr = growArrayBy(iArr, iArr.length);
                    this._quadBuffer = iArr;
                }
                i++;
                iArr[i] = i2;
                i2 = i4;
                i3 = 1;
            }
        }
        this._quadLength = i;
        this._pending32 = i2;
        this._pendingBytes = i3;
        this._minorState = 10;
        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken;
        return jsonToken;
    }

    private JsonToken d(int i, int i2, int i3) throws IOException {
        int[] iArr = this._quadBuffer;
        int[] iArr2 = _icLatin1;
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i4 = this._inputPtr;
            this._inputPtr = i4 + 1;
            int i5 = bArr[i4] & 255;
            if (i5 == 39) {
                if (i3 > 0) {
                    if (i >= iArr.length) {
                        iArr = growArrayBy(iArr, iArr.length);
                        this._quadBuffer = iArr;
                    }
                    i++;
                    iArr[i] = _padLastQuad(i2, i3);
                } else if (i == 0) {
                    return _fieldComplete("");
                }
                String findName = this._symbols.findName(iArr, i);
                if (findName == null) {
                    findName = _addName(iArr, i, i3);
                }
                return _fieldComplete(findName);
            }
            if (!(i5 == 34 || iArr2[i5] == 0)) {
                if (i5 != 92) {
                    _throwUnquotedSpace(i5, "name");
                } else {
                    i5 = d();
                    if (i5 < 0) {
                        this._minorState = 8;
                        this._minorStateAfterSplit = 9;
                        this._quadLength = i;
                        this._pending32 = i2;
                        this._pendingBytes = i3;
                        JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
                        this._currToken = jsonToken;
                        return jsonToken;
                    }
                }
                if (i5 > 127) {
                    if (i3 >= 4) {
                        if (i >= iArr.length) {
                            iArr = growArrayBy(iArr, iArr.length);
                            this._quadBuffer = iArr;
                        }
                        i++;
                        iArr[i] = i2;
                        i2 = 0;
                        i3 = 0;
                    }
                    if (i5 < 2048) {
                        i2 = (i2 << 8) | (i5 >> 6) | 192;
                        i3++;
                    } else {
                        int i6 = (i2 << 8) | (i5 >> 12) | 224;
                        int i7 = i3 + 1;
                        if (i7 >= 4) {
                            if (i >= iArr.length) {
                                int[] growArrayBy = growArrayBy(iArr, iArr.length);
                                this._quadBuffer = growArrayBy;
                                iArr = growArrayBy;
                            }
                            i++;
                            iArr[i] = i6;
                            i6 = 0;
                            i7 = 0;
                        }
                        i2 = (i6 << 8) | ((i5 >> 6) & 63) | 128;
                        i3 = i7 + 1;
                    }
                    i5 = (i5 & 63) | 128;
                }
            }
            if (i3 < 4) {
                i3++;
                i2 = (i2 << 8) | i5;
            } else {
                if (i >= iArr.length) {
                    iArr = growArrayBy(iArr, iArr.length);
                    this._quadBuffer = iArr;
                }
                i++;
                iArr[i] = i2;
                i2 = i5;
                i3 = 1;
            }
        }
        this._quadLength = i;
        this._pending32 = i2;
        this._pendingBytes = i3;
        this._minorState = 9;
        JsonToken jsonToken2 = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken2;
        return jsonToken2;
    }

    protected final JsonToken _finishFieldWithEscape() throws IOException {
        int b = b(this._quoted32, this._quotedDigits);
        if (b < 0) {
            this._minorState = 8;
            return JsonToken.NOT_AVAILABLE;
        }
        if (this._quadLength >= this._quadBuffer.length) {
            this._quadBuffer = growArrayBy(this._quadBuffer, 32);
        }
        int i = this._pending32;
        int i2 = this._pendingBytes;
        int i3 = 1;
        if (b > 127) {
            if (i2 >= 4) {
                int[] iArr = this._quadBuffer;
                int i4 = this._quadLength;
                this._quadLength = i4 + 1;
                iArr[i4] = i;
                i = 0;
                i2 = 0;
            }
            if (b < 2048) {
                i = (i << 8) | (b >> 6) | 192;
                i2++;
            } else {
                int i5 = (i << 8) | (b >> 12) | 224;
                int i6 = i2 + 1;
                if (i6 >= 4) {
                    int[] iArr2 = this._quadBuffer;
                    int i7 = this._quadLength;
                    this._quadLength = i7 + 1;
                    iArr2[i7] = i5;
                    i5 = 0;
                    i6 = 0;
                }
                i = (i5 << 8) | ((b >> 6) & 63) | 128;
                i2 = i6 + 1;
            }
            b = (b & 63) | 128;
        }
        if (i2 < 4) {
            i3 = 1 + i2;
            b |= i << 8;
        } else {
            int[] iArr3 = this._quadBuffer;
            int i8 = this._quadLength;
            this._quadLength = i8 + 1;
            iArr3[i8] = i;
        }
        if (this._minorStateAfterSplit == 9) {
            return d(this._quadLength, b, i3);
        }
        return b(this._quadLength, b, i3);
    }

    private int b(int i, int i2) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._quoted32 = i;
            this._quotedDigits = i2;
            return -1;
        }
        byte[] bArr = this._inputBuffer;
        int i3 = this._inputPtr;
        this._inputPtr = i3 + 1;
        byte b = bArr[i3];
        if (i2 == -1) {
            if (b == 34 || b == 47 || b == 92) {
                return b;
            }
            if (b == 98) {
                return 8;
            }
            if (b == 102) {
                return 12;
            }
            if (b == 110) {
                return 10;
            }
            if (b == 114) {
                return 13;
            }
            switch (b) {
                case 116:
                    return 9;
                case 117:
                    if (this._inputPtr < this._inputEnd) {
                        byte[] bArr2 = this._inputBuffer;
                        int i4 = this._inputPtr;
                        this._inputPtr = i4 + 1;
                        b = bArr2[i4];
                        i2 = 0;
                        break;
                    } else {
                        this._quotedDigits = 0;
                        this._quoted32 = 0;
                        return -1;
                    }
                default:
                    return _handleUnrecognizedCharacterEscape((char) b);
            }
        }
        while (true) {
            int i5 = b & 255;
            int charToHex = CharTypes.charToHex(i5);
            if (charToHex < 0) {
                _reportUnexpectedChar(i5, "expected a hex-digit for character escape sequence");
            }
            i = (i << 4) | charToHex;
            i2++;
            if (i2 == 4) {
                return i;
            }
            if (this._inputPtr >= this._inputEnd) {
                this._quotedDigits = i2;
                this._quoted32 = i;
                return -1;
            }
            byte[] bArr3 = this._inputBuffer;
            int i6 = this._inputPtr;
            this._inputPtr = i6 + 1;
            b = bArr3[i6];
        }
    }

    protected JsonToken _startString() throws IOException {
        int i = this._inputPtr;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = a;
        int min = Math.min(this._inputEnd, emptyAndGetCurrentSegment.length + i);
        byte[] bArr = this._inputBuffer;
        int i2 = 0;
        while (true) {
            if (i >= min) {
                break;
            }
            int i3 = bArr[i] & 255;
            if (iArr[i3] == 0) {
                i++;
                i2++;
                emptyAndGetCurrentSegment[i2] = (char) i3;
            } else if (i3 == 34) {
                this._inputPtr = i + 1;
                this._textBuffer.setCurrentLength(i2);
                return _valueComplete(JsonToken.VALUE_STRING);
            }
        }
        this._textBuffer.setCurrentLength(i2);
        this._inputPtr = i;
        return b();
    }

    private final JsonToken b() throws IOException {
        int[] iArr = a;
        byte[] bArr = this._inputBuffer;
        char[] bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        int i = this._inputPtr;
        int i2 = this._inputEnd - 5;
        while (i < this._inputEnd) {
            boolean z = false;
            if (currentSegmentSize >= bufferWithoutReset.length) {
                bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            int min = Math.min(this._inputEnd, (bufferWithoutReset.length - currentSegmentSize) + i);
            while (true) {
                if (i < min) {
                    int i3 = i + 1;
                    int i4 = bArr[i] & 255;
                    if (iArr[i4] == 0) {
                        currentSegmentSize++;
                        bufferWithoutReset[currentSegmentSize] = (char) i4;
                        i = i3;
                    } else if (i4 == 34) {
                        this._inputPtr = i3;
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        return _valueComplete(JsonToken.VALUE_STRING);
                    } else if (i3 >= i2) {
                        this._inputPtr = i3;
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        int i5 = iArr[i4];
                        if (i3 < this._inputEnd) {
                            z = true;
                        }
                        if (!a(i4, i5, z)) {
                            this._minorStateAfterSplit = 40;
                            JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
                            this._currToken = jsonToken;
                            return jsonToken;
                        }
                        bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
                        currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                        i = this._inputPtr;
                    } else {
                        switch (iArr[i4]) {
                            case 1:
                                this._inputPtr = i3;
                                i4 = e();
                                i = this._inputPtr;
                                break;
                            case 2:
                                i = i3 + 1;
                                i4 = c(i4, this._inputBuffer[i3]);
                                break;
                            case 3:
                                byte[] bArr2 = this._inputBuffer;
                                int i6 = i3 + 1;
                                byte b = bArr2[i3];
                                i = i6 + 1;
                                i4 = g(i4, b, bArr2[i6]);
                                break;
                            case 4:
                                byte[] bArr3 = this._inputBuffer;
                                int i7 = i3 + 1;
                                byte b2 = bArr3[i3];
                                int i8 = i7 + 1;
                                byte b3 = bArr3[i7];
                                i = i8 + 1;
                                int a2 = a(i4, b2, b3, bArr3[i8]);
                                currentSegmentSize++;
                                bufferWithoutReset[currentSegmentSize] = (char) (55296 | (a2 >> 10));
                                if (currentSegmentSize >= bufferWithoutReset.length) {
                                    bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                                    currentSegmentSize = 0;
                                }
                                i4 = (a2 & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | 56320;
                                break;
                            default:
                                if (i4 < 32) {
                                    _throwUnquotedSpace(i4, "string value");
                                } else {
                                    _reportInvalidChar(i4);
                                }
                                i = i3;
                                break;
                        }
                        if (currentSegmentSize >= bufferWithoutReset.length) {
                            bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                            currentSegmentSize = 0;
                        }
                        currentSegmentSize++;
                        bufferWithoutReset[currentSegmentSize] = (char) i4;
                    }
                }
            }
        }
        this._inputPtr = i;
        this._minorState = 40;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        JsonToken jsonToken2 = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken2;
        return jsonToken2;
    }

    protected JsonToken _startAposString() throws IOException {
        int i = this._inputPtr;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = a;
        int min = Math.min(this._inputEnd, emptyAndGetCurrentSegment.length + i);
        byte[] bArr = this._inputBuffer;
        int i2 = 0;
        while (i < min) {
            int i3 = bArr[i] & 255;
            if (i3 == 39) {
                this._inputPtr = i + 1;
                this._textBuffer.setCurrentLength(i2);
                return _valueComplete(JsonToken.VALUE_STRING);
            } else if (iArr[i3] != 0) {
                break;
            } else {
                i++;
                i2++;
                emptyAndGetCurrentSegment[i2] = (char) i3;
            }
        }
        this._textBuffer.setCurrentLength(i2);
        this._inputPtr = i;
        return c();
    }

    private final JsonToken c() throws IOException {
        int[] iArr = a;
        byte[] bArr = this._inputBuffer;
        char[] bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        int i = this._inputPtr;
        int i2 = this._inputEnd - 5;
        while (i < this._inputEnd) {
            boolean z = false;
            if (currentSegmentSize >= bufferWithoutReset.length) {
                bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            int min = Math.min(this._inputEnd, (bufferWithoutReset.length - currentSegmentSize) + i);
            while (true) {
                if (i < min) {
                    int i3 = i + 1;
                    int i4 = bArr[i] & 255;
                    if (iArr[i4] == 0 || i4 == 34) {
                        if (i4 == 39) {
                            this._inputPtr = i3;
                            this._textBuffer.setCurrentLength(currentSegmentSize);
                            return _valueComplete(JsonToken.VALUE_STRING);
                        }
                        currentSegmentSize++;
                        bufferWithoutReset[currentSegmentSize] = (char) i4;
                        i = i3;
                    } else if (i3 >= i2) {
                        this._inputPtr = i3;
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        int i5 = iArr[i4];
                        if (i3 < this._inputEnd) {
                            z = true;
                        }
                        if (!a(i4, i5, z)) {
                            this._minorStateAfterSplit = 45;
                            JsonToken jsonToken = JsonToken.NOT_AVAILABLE;
                            this._currToken = jsonToken;
                            return jsonToken;
                        }
                        bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
                        currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                        i = this._inputPtr;
                    } else {
                        switch (iArr[i4]) {
                            case 1:
                                this._inputPtr = i3;
                                i4 = e();
                                i = this._inputPtr;
                                break;
                            case 2:
                                i = i3 + 1;
                                i4 = c(i4, this._inputBuffer[i3]);
                                break;
                            case 3:
                                byte[] bArr2 = this._inputBuffer;
                                int i6 = i3 + 1;
                                byte b = bArr2[i3];
                                i = i6 + 1;
                                i4 = g(i4, b, bArr2[i6]);
                                break;
                            case 4:
                                byte[] bArr3 = this._inputBuffer;
                                int i7 = i3 + 1;
                                byte b2 = bArr3[i3];
                                int i8 = i7 + 1;
                                byte b3 = bArr3[i7];
                                i = i8 + 1;
                                int a2 = a(i4, b2, b3, bArr3[i8]);
                                currentSegmentSize++;
                                bufferWithoutReset[currentSegmentSize] = (char) (55296 | (a2 >> 10));
                                if (currentSegmentSize >= bufferWithoutReset.length) {
                                    bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                                    currentSegmentSize = 0;
                                }
                                i4 = (a2 & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | 56320;
                                break;
                            default:
                                if (i4 < 32) {
                                    _throwUnquotedSpace(i4, "string value");
                                } else {
                                    _reportInvalidChar(i4);
                                }
                                i = i3;
                                break;
                        }
                        if (currentSegmentSize >= bufferWithoutReset.length) {
                            bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                            currentSegmentSize = 0;
                        }
                        currentSegmentSize++;
                        bufferWithoutReset[currentSegmentSize] = (char) i4;
                    }
                }
            }
        }
        this._inputPtr = i;
        this._minorState = 45;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        JsonToken jsonToken2 = JsonToken.NOT_AVAILABLE;
        this._currToken = jsonToken2;
        return jsonToken2;
    }

    private final boolean a(int i, int i2, boolean z) throws IOException {
        switch (i2) {
            case 1:
                int b = b(0, -1);
                if (b < 0) {
                    this._minorState = 41;
                    return false;
                }
                this._textBuffer.append((char) b);
                return true;
            case 2:
                if (z) {
                    byte[] bArr = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    this._textBuffer.append((char) c(i, bArr[i3]));
                    return true;
                }
                this._minorState = 42;
                this._pending32 = i;
                return false;
            case 3:
                int i4 = i & 15;
                if (z) {
                    byte[] bArr2 = this._inputBuffer;
                    int i5 = this._inputPtr;
                    this._inputPtr = i5 + 1;
                    return e(i4, 1, bArr2[i5]);
                }
                this._minorState = 43;
                this._pending32 = i4;
                this._pendingBytes = 1;
                return false;
            case 4:
                int i6 = i & 7;
                if (z) {
                    byte[] bArr3 = this._inputBuffer;
                    int i7 = this._inputPtr;
                    this._inputPtr = i7 + 1;
                    return f(i6, 1, bArr3[i7]);
                }
                this._pending32 = i6;
                this._pendingBytes = 1;
                this._minorState = 44;
                return false;
            default:
                if (i < 32) {
                    _throwUnquotedSpace(i, "string value");
                } else {
                    _reportInvalidChar(i);
                }
                this._textBuffer.append((char) i);
                return true;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, byte], vars: [r6v0 ??, r6v1 ??, r6v5 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
        */
    private final boolean e(int r4, int r5, 
    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, byte], vars: [r6v0 ??, r6v1 ??, r6v5 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r6v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, byte], vars: [r9v0 ??, r9v1 ??, r9v11 ??, r9v2 ??, r9v8 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
        */
    private final boolean f(int r7, int r8, 
    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, byte], vars: [r9v0 ??, r9v1 ??, r9v11 ??, r9v2 ??, r9v8 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r9v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    private final int d() throws IOException {
        if (this._inputEnd - this._inputPtr < 5) {
            return b(0, -1);
        }
        return e();
    }

    private final int e() throws IOException {
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        byte b = bArr[i];
        if (b == 34 || b == 47 || b == 92) {
            return (char) b;
        }
        if (b == 98) {
            return 8;
        }
        if (b == 102) {
            return 12;
        }
        if (b == 110) {
            return 10;
        }
        if (b == 114) {
            return 13;
        }
        switch (b) {
            case 116:
                return 9;
            case 117:
                byte[] bArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                byte b2 = bArr2[i2];
                int charToHex = CharTypes.charToHex(b2);
                if (charToHex >= 0) {
                    byte[] bArr3 = this._inputBuffer;
                    int i3 = this._inputPtr;
                    this._inputPtr = i3 + 1;
                    b2 = bArr3[i3];
                    int charToHex2 = CharTypes.charToHex(b2);
                    if (charToHex2 >= 0) {
                        int i4 = (charToHex << 4) | charToHex2;
                        byte[] bArr4 = this._inputBuffer;
                        int i5 = this._inputPtr;
                        this._inputPtr = i5 + 1;
                        byte b3 = bArr4[i5];
                        int charToHex3 = CharTypes.charToHex(b3);
                        if (charToHex3 >= 0) {
                            int i6 = (i4 << 4) | charToHex3;
                            byte[] bArr5 = this._inputBuffer;
                            int i7 = this._inputPtr;
                            this._inputPtr = i7 + 1;
                            byte b4 = bArr5[i7];
                            int charToHex4 = CharTypes.charToHex(b4);
                            if (charToHex4 >= 0) {
                                return (i6 << 4) | charToHex4;
                            }
                            b2 = b4;
                        } else {
                            b2 = b3;
                        }
                    }
                }
                _reportUnexpectedChar(b2 & 255, "expected a hex-digit for character escape sequence");
                return -1;
            default:
                return _handleUnrecognizedCharacterEscape((char) b);
        }
    }

    private final int c(int i, int i2) throws IOException {
        if ((i2 & 192) != 128) {
            _reportInvalidOther(i2 & 255, this._inputPtr);
        }
        return ((i & 31) << 6) | (i2 & 63);
    }

    private final int g(int i, int i2, int i3) throws IOException {
        int i4 = i & 15;
        if ((i2 & 192) != 128) {
            _reportInvalidOther(i2 & 255, this._inputPtr);
        }
        int i5 = (i4 << 6) | (i2 & 63);
        if ((i3 & 192) != 128) {
            _reportInvalidOther(i3 & 255, this._inputPtr);
        }
        return (i5 << 6) | (i3 & 63);
    }

    private final int a(int i, int i2, int i3, int i4) throws IOException {
        if ((i2 & 192) != 128) {
            _reportInvalidOther(i2 & 255, this._inputPtr);
        }
        int i5 = ((i & 7) << 6) | (i2 & 63);
        if ((i3 & 192) != 128) {
            _reportInvalidOther(i3 & 255, this._inputPtr);
        }
        int i6 = (i5 << 6) | (i3 & 63);
        if ((i4 & 192) != 128) {
            _reportInvalidOther(i4 & 255, this._inputPtr);
        }
        return ((i6 << 6) | (i4 & 63)) - 65536;
    }
}
