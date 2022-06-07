package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/* loaded from: classes.dex */
public class ReaderBasedJsonParser extends ParserBase {
    protected static final int FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
    protected boolean _bufferRecyclable;
    protected final int _hashSeed;
    protected char[] _inputBuffer;
    protected int _nameStartCol;
    protected long _nameStartOffset;
    protected int _nameStartRow;
    protected ObjectCodec _objectCodec;
    protected Reader _reader;
    protected final CharsToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete;

    public ReaderBasedJsonParser(IOContext iOContext, int i, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer, char[] cArr, int i2, int i3, boolean z) {
        super(iOContext, i);
        this._reader = reader;
        this._inputBuffer = cArr;
        this._inputPtr = i2;
        this._inputEnd = i3;
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
        this._bufferRecyclable = z;
    }

    public ReaderBasedJsonParser(IOContext iOContext, int i, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer) {
        super(iOContext, i);
        this._reader = reader;
        this._inputBuffer = iOContext.allocTokenBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
        this._bufferRecyclable = true;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int releaseBuffered(Writer writer) throws IOException {
        int i = this._inputEnd - this._inputPtr;
        if (i < 1) {
            return 0;
        }
        writer.write(this._inputBuffer, this._inputPtr, i);
        return i;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public Object getInputSource() {
        return this._reader;
    }

    @Deprecated
    protected char getNextChar(String str) throws IOException {
        return getNextChar(str, null);
    }

    protected char getNextChar(String str, JsonToken jsonToken) throws IOException {
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(str, jsonToken);
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        return cArr[i];
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _releaseBuffers() throws IOException {
        char[] cArr;
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable && (cArr = this._inputBuffer) != null) {
            this._inputBuffer = null;
            this._ioContext.releaseTokenBuffer(cArr);
        }
    }

    protected void _loadMoreGuaranteed() throws IOException {
        if (!_loadMore()) {
            _reportInvalidEOF();
        }
    }

    protected boolean _loadMore() throws IOException {
        int i = this._inputEnd;
        long j = i;
        this._currInputProcessed += j;
        this._currInputRowStart -= i;
        this._nameStartOffset -= j;
        Reader reader = this._reader;
        if (reader != null) {
            char[] cArr = this._inputBuffer;
            int read = reader.read(cArr, 0, cArr.length);
            if (read > 0) {
                this._inputPtr = 0;
                this._inputEnd = read;
                return true;
            }
            _closeInput();
            if (read == 0) {
                throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
            }
        }
        return false;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public final String getText() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_STRING) {
            return _getText2(jsonToken);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.contentsAsString();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int getText(Writer writer) throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.contentsToWriter(writer);
        } else if (jsonToken == JsonToken.FIELD_NAME) {
            String currentName = this._parsingContext.getCurrentName();
            writer.write(currentName);
            return currentName.length();
        } else if (jsonToken == null) {
            return 0;
        } else {
            if (jsonToken.isNumeric()) {
                return this._textBuffer.contentsToWriter(writer);
            }
            char[] asCharArray = jsonToken.asCharArray();
            writer.write(asCharArray);
            return asCharArray.length;
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public final String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.contentsAsString();
        } else if (this._currToken == JsonToken.FIELD_NAME) {
            return getCurrentName();
        } else {
            return super.getValueAsString(null);
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public final String getValueAsString(String str) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.contentsAsString();
        } else if (this._currToken == JsonToken.FIELD_NAME) {
            return getCurrentName();
        } else {
            return super.getValueAsString(str);
        }
    }

    protected final String _getText2(JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        switch (jsonToken.id()) {
            case 5:
                return this._parsingContext.getCurrentName();
            case 6:
            case 7:
            case 8:
                return this._textBuffer.contentsAsString();
            default:
                return jsonToken.asString();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public final char[] getTextCharacters() throws IOException {
        if (this._currToken == null) {
            return null;
        }
        switch (this._currToken.id()) {
            case 5:
                if (!this._nameCopied) {
                    String currentName = this._parsingContext.getCurrentName();
                    int length = currentName.length();
                    if (this._nameCopyBuffer == null) {
                        this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(length);
                    } else if (this._nameCopyBuffer.length < length) {
                        this._nameCopyBuffer = new char[length];
                    }
                    currentName.getChars(0, length, this._nameCopyBuffer, 0);
                    this._nameCopied = true;
                }
                return this._nameCopyBuffer;
            case 6:
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                    break;
                }
                break;
            case 7:
            case 8:
                break;
            default:
                return this._currToken.asCharArray();
        }
        return this._textBuffer.getTextBuffer();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public final int getTextLength() throws IOException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken.id()) {
            case 5:
                return this._parsingContext.getCurrentName().length();
            case 6:
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                    break;
                }
                break;
            case 7:
            case 8:
                break;
            default:
                return this._currToken.asCharArray().length;
        }
        return this._textBuffer.size();
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public final int getTextOffset() throws IOException {
        if (this._currToken != null) {
            switch (this._currToken.id()) {
                case 5:
                    return 0;
                case 6:
                    if (this._tokenIncomplete) {
                        this._tokenIncomplete = false;
                        _finishString();
                    }
                    return this._textBuffer.getTextOffset();
                case 7:
                case 8:
                    return this._textBuffer.getTextOffset();
            }
        }
        return 0;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && this._binaryValue != null) {
            return this._binaryValue;
        }
        if (this._currToken != JsonToken.VALUE_STRING) {
            _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (this._tokenIncomplete) {
            try {
                this._binaryValue = _decodeBase64(base64Variant);
                this._tokenIncomplete = false;
            } catch (IllegalArgumentException e) {
                throw _constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + e.getMessage());
            }
        } else if (this._binaryValue == null) {
            ByteArrayBuilder _getByteArrayBuilder = _getByteArrayBuilder();
            _decodeBase64(getText(), _getByteArrayBuilder, base64Variant);
            this._binaryValue = _getByteArrayBuilder.toByteArray();
        }
        return this._binaryValue;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
        if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
            byte[] binaryValue = getBinaryValue(base64Variant);
            outputStream.write(binaryValue);
            return binaryValue.length;
        }
        byte[] allocBase64Buffer = this._ioContext.allocBase64Buffer();
        try {
            return _readBinary(base64Variant, outputStream, allocBase64Buffer);
        } finally {
            this._ioContext.releaseBase64Buffer(allocBase64Buffer);
        }
    }

    protected int _readBinary(Base64Variant base64Variant, OutputStream outputStream, byte[] bArr) throws IOException {
        int i = 3;
        int length = bArr.length - 3;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                _loadMoreGuaranteed();
            }
            char[] cArr = this._inputBuffer;
            int i4 = this._inputPtr;
            this._inputPtr = i4 + 1;
            char c = cArr[i4];
            if (c > ' ') {
                int decodeBase64Char = base64Variant.decodeBase64Char(c);
                if (decodeBase64Char < 0) {
                    if (c == '\"') {
                        break;
                    }
                    decodeBase64Char = _decodeBase64Escape(base64Variant, c, 0);
                    if (decodeBase64Char < 0) {
                    }
                }
                if (i3 > length) {
                    i2 += i3;
                    outputStream.write(bArr, 0, i3);
                    i3 = 0;
                }
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr2 = this._inputBuffer;
                int i5 = this._inputPtr;
                this._inputPtr = i5 + 1;
                char c2 = cArr2[i5];
                int decodeBase64Char2 = base64Variant.decodeBase64Char(c2);
                if (decodeBase64Char2 < 0) {
                    decodeBase64Char2 = _decodeBase64Escape(base64Variant, c2, 1);
                }
                int i6 = (decodeBase64Char << 6) | decodeBase64Char2;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr3 = this._inputBuffer;
                int i7 = this._inputPtr;
                this._inputPtr = i7 + 1;
                char c3 = cArr3[i7];
                int decodeBase64Char3 = base64Variant.decodeBase64Char(c3);
                if (decodeBase64Char3 < 0) {
                    if (decodeBase64Char3 != -2) {
                        if (c3 == '\"') {
                            i3++;
                            bArr[i3] = (byte) (i6 >> 4);
                            if (base64Variant.usesPadding()) {
                                this._inputPtr--;
                                _handleBase64MissingPadding(base64Variant);
                            }
                        } else {
                            decodeBase64Char3 = _decodeBase64Escape(base64Variant, c3, 2);
                        }
                    }
                    if (decodeBase64Char3 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            _loadMoreGuaranteed();
                        }
                        char[] cArr4 = this._inputBuffer;
                        int i8 = this._inputPtr;
                        this._inputPtr = i8 + 1;
                        char c4 = cArr4[i8];
                        if (base64Variant.usesPaddingChar(c4) || _decodeBase64Escape(base64Variant, c4, i) == -2) {
                            i3++;
                            bArr[i3] = (byte) (i6 >> 4);
                        } else {
                            throw reportInvalidBase64Char(base64Variant, c4, i, "expected padding character '" + base64Variant.getPaddingChar() + LrcRow.SINGLE_QUOTE);
                        }
                    }
                }
                int i9 = (i6 << 6) | decodeBase64Char3;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr5 = this._inputBuffer;
                int i10 = this._inputPtr;
                this._inputPtr = i10 + 1;
                char c5 = cArr5[i10];
                int decodeBase64Char4 = base64Variant.decodeBase64Char(c5);
                if (decodeBase64Char4 < 0) {
                    if (decodeBase64Char4 == -2) {
                        i = 3;
                    } else if (c5 == '\"') {
                        int i11 = i9 >> 2;
                        int i12 = i3 + 1;
                        bArr[i3] = (byte) (i11 >> 8);
                        i3 = i12 + 1;
                        bArr[i12] = (byte) i11;
                        if (base64Variant.usesPadding()) {
                            this._inputPtr--;
                            _handleBase64MissingPadding(base64Variant);
                        }
                    } else {
                        i = 3;
                        decodeBase64Char4 = _decodeBase64Escape(base64Variant, c5, 3);
                    }
                    if (decodeBase64Char4 == -2) {
                        int i13 = i9 >> 2;
                        int i14 = i3 + 1;
                        bArr[i3] = (byte) (i13 >> 8);
                        i3 = i14 + 1;
                        bArr[i14] = (byte) i13;
                        i = i;
                    }
                } else {
                    i = 3;
                }
                int i15 = (i9 << 6) | decodeBase64Char4;
                int i16 = i3 + 1;
                bArr[i3] = (byte) (i15 >> 16);
                int i17 = i16 + 1;
                bArr[i16] = (byte) (i15 >> 8);
                i3 = i17 + 1;
                bArr[i17] = (byte) i15;
            }
            i = i;
        }
        this._tokenIncomplete = false;
        if (i3 <= 0) {
            return i2;
        }
        int i18 = i2 + i3;
        outputStream.write(bArr, 0, i3);
        return i18;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public final JsonToken nextToken() throws IOException {
        JsonToken jsonToken;
        if (this._currToken == JsonToken.FIELD_NAME) {
            return a();
        }
        this._numTypesValid = 0;
        if (this._tokenIncomplete) {
            _skipString();
        }
        int f = f();
        if (f < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (f == 93 || f == 125) {
            f(f);
            return this._currToken;
        }
        if (this._parsingContext.expectComma()) {
            f = e(f);
            if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (f == 93 || f == 125)) {
                f(f);
                return this._currToken;
            }
        }
        boolean inObject = this._parsingContext.inObject();
        if (inObject) {
            p();
            this._parsingContext.setCurrentName(f == 34 ? _parseName() : _handleOddName(f));
            this._currToken = JsonToken.FIELD_NAME;
            f = d();
        }
        o();
        if (f == 34) {
            this._tokenIncomplete = true;
            jsonToken = JsonToken.VALUE_STRING;
        } else if (f == 45) {
            jsonToken = _parseNegNumber();
        } else if (f == 91) {
            if (!inObject) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            }
            jsonToken = JsonToken.START_ARRAY;
        } else if (f == 102) {
            m();
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (f != 110) {
            if (f != 116) {
                if (f == 123) {
                    if (!inObject) {
                        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    }
                    jsonToken = JsonToken.START_OBJECT;
                } else if (f != 125) {
                    switch (f) {
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                            jsonToken = _parsePosNumber(f);
                            break;
                        default:
                            jsonToken = _handleOddValue(f);
                            break;
                    }
                } else {
                    _reportUnexpectedChar(f, "expected a value");
                }
            }
            l();
            jsonToken = JsonToken.VALUE_TRUE;
        } else {
            n();
            jsonToken = JsonToken.VALUE_NULL;
        }
        if (inObject) {
            this._nextToken = jsonToken;
            return this._currToken;
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken a() {
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public void finishToken() throws IOException {
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public boolean nextFieldName(SerializableString serializableString) throws IOException {
        int i = 0;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            a();
            return false;
        }
        if (this._tokenIncomplete) {
            _skipString();
        }
        int f = f();
        if (f < 0) {
            close();
            this._currToken = null;
            return false;
        }
        this._binaryValue = null;
        if (f == 93 || f == 125) {
            f(f);
            return false;
        }
        if (this._parsingContext.expectComma()) {
            f = e(f);
            if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (f == 93 || f == 125)) {
                f(f);
                return false;
            }
        }
        if (!this._parsingContext.inObject()) {
            o();
            b(f);
            return false;
        }
        p();
        if (f == 34) {
            char[] asQuotedChars = serializableString.asQuotedChars();
            int length = asQuotedChars.length;
            if (this._inputPtr + length + 4 < this._inputEnd) {
                int i2 = this._inputPtr + length;
                if (this._inputBuffer[i2] == '\"') {
                    int i3 = this._inputPtr;
                    while (i3 != i2) {
                        if (asQuotedChars[i] == this._inputBuffer[i3]) {
                            i++;
                            i3++;
                        }
                    }
                    this._parsingContext.setCurrentName(serializableString.getValue());
                    a(d(i3 + 1));
                    return true;
                }
            }
        }
        return _isNextTokenNameMaybe(f, serializableString.getValue());
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public String nextFieldName() throws IOException {
        JsonToken jsonToken;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            a();
            return null;
        }
        if (this._tokenIncomplete) {
            _skipString();
        }
        int f = f();
        if (f < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (f == 93 || f == 125) {
            f(f);
            return null;
        }
        if (this._parsingContext.expectComma()) {
            f = e(f);
            if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (f == 93 || f == 125)) {
                f(f);
                return null;
            }
        }
        if (!this._parsingContext.inObject()) {
            o();
            b(f);
            return null;
        }
        p();
        String _parseName = f == 34 ? _parseName() : _handleOddName(f);
        this._parsingContext.setCurrentName(_parseName);
        this._currToken = JsonToken.FIELD_NAME;
        int d = d();
        o();
        if (d == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return _parseName;
        }
        if (d == 45) {
            jsonToken = _parseNegNumber();
        } else if (d == 91) {
            jsonToken = JsonToken.START_ARRAY;
        } else if (d == 102) {
            m();
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (d == 110) {
            n();
            jsonToken = JsonToken.VALUE_NULL;
        } else if (d == 116) {
            l();
            jsonToken = JsonToken.VALUE_TRUE;
        } else if (d != 123) {
            switch (d) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    jsonToken = _parsePosNumber(d);
                    break;
                default:
                    jsonToken = _handleOddValue(d);
                    break;
            }
        } else {
            jsonToken = JsonToken.START_OBJECT;
        }
        this._nextToken = jsonToken;
        return _parseName;
    }

    private final void a(int i) throws IOException {
        this._currToken = JsonToken.FIELD_NAME;
        o();
        if (i == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
        } else if (i == 45) {
            this._nextToken = _parseNegNumber();
        } else if (i == 91) {
            this._nextToken = JsonToken.START_ARRAY;
        } else if (i == 102) {
            _matchToken("false", 1);
            this._nextToken = JsonToken.VALUE_FALSE;
        } else if (i == 110) {
            _matchToken("null", 1);
            this._nextToken = JsonToken.VALUE_NULL;
        } else if (i == 116) {
            _matchToken("true", 1);
            this._nextToken = JsonToken.VALUE_TRUE;
        } else if (i != 123) {
            switch (i) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    this._nextToken = _parsePosNumber(i);
                    return;
                default:
                    this._nextToken = _handleOddValue(i);
                    return;
            }
        } else {
            this._nextToken = JsonToken.START_OBJECT;
        }
    }

    protected boolean _isNextTokenNameMaybe(int i, String str) throws IOException {
        JsonToken jsonToken;
        String _parseName = i == 34 ? _parseName() : _handleOddName(i);
        this._parsingContext.setCurrentName(_parseName);
        this._currToken = JsonToken.FIELD_NAME;
        int d = d();
        o();
        if (d == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return str.equals(_parseName);
        }
        if (d == 45) {
            jsonToken = _parseNegNumber();
        } else if (d == 91) {
            jsonToken = JsonToken.START_ARRAY;
        } else if (d == 102) {
            m();
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (d == 110) {
            n();
            jsonToken = JsonToken.VALUE_NULL;
        } else if (d == 116) {
            l();
            jsonToken = JsonToken.VALUE_TRUE;
        } else if (d != 123) {
            switch (d) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    jsonToken = _parsePosNumber(d);
                    break;
                default:
                    jsonToken = _handleOddValue(d);
                    break;
            }
        } else {
            jsonToken = JsonToken.START_OBJECT;
        }
        this._nextToken = jsonToken;
        return str.equals(_parseName);
    }

    private final JsonToken b(int i) throws IOException {
        if (i == 34) {
            this._tokenIncomplete = true;
            JsonToken jsonToken = JsonToken.VALUE_STRING;
            this._currToken = jsonToken;
            return jsonToken;
        } else if (i != 91) {
            if (i != 93) {
                if (i == 102) {
                    _matchToken("false", 1);
                    JsonToken jsonToken2 = JsonToken.VALUE_FALSE;
                    this._currToken = jsonToken2;
                    return jsonToken2;
                } else if (i == 110) {
                    _matchToken("null", 1);
                    JsonToken jsonToken3 = JsonToken.VALUE_NULL;
                    this._currToken = jsonToken3;
                    return jsonToken3;
                } else if (i == 116) {
                    _matchToken("true", 1);
                    JsonToken jsonToken4 = JsonToken.VALUE_TRUE;
                    this._currToken = jsonToken4;
                    return jsonToken4;
                } else if (i != 123) {
                    switch (i) {
                        case 44:
                            break;
                        case 45:
                            JsonToken _parseNegNumber = _parseNegNumber();
                            this._currToken = _parseNegNumber;
                            return _parseNegNumber;
                        default:
                            switch (i) {
                                case 48:
                                case 49:
                                case 50:
                                case 51:
                                case 52:
                                case 53:
                                case 54:
                                case 55:
                                case 56:
                                case 57:
                                    JsonToken _parsePosNumber = _parsePosNumber(i);
                                    this._currToken = _parsePosNumber;
                                    return _parsePosNumber;
                            }
                            JsonToken _handleOddValue = _handleOddValue(i);
                            this._currToken = _handleOddValue;
                            return _handleOddValue;
                    }
                } else {
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    JsonToken jsonToken5 = JsonToken.START_OBJECT;
                    this._currToken = jsonToken5;
                    return jsonToken5;
                }
            }
            if (isEnabled(JsonParser.Feature.ALLOW_MISSING_VALUES)) {
                this._inputPtr--;
                JsonToken jsonToken6 = JsonToken.VALUE_NULL;
                this._currToken = jsonToken6;
                return jsonToken6;
            }
            JsonToken _handleOddValue2 = _handleOddValue(i);
            this._currToken = _handleOddValue2;
            return _handleOddValue2;
        } else {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            JsonToken jsonToken7 = JsonToken.START_ARRAY;
            this._currToken = jsonToken7;
            return jsonToken7;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public final String nextTextValue() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_STRING) {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                }
                return this._textBuffer.contentsAsString();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (jsonToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return null;
        } else if (nextToken() == JsonToken.VALUE_STRING) {
            return getText();
        } else {
            return null;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public final int nextIntValue(int i) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : i;
        }
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        this._currToken = jsonToken;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            return getIntValue();
        }
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return i;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public final long nextLongValue(long j) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : j;
        }
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        this._currToken = jsonToken;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            return getLongValue();
        }
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return j;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public final Boolean nextBooleanValue() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (jsonToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (jsonToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return null;
        }
        JsonToken nextToken = nextToken();
        if (nextToken != null) {
            int id = nextToken.id();
            if (id == 9) {
                return Boolean.TRUE;
            }
            if (id == 10) {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    protected final JsonToken _parsePosNumber(int i) throws IOException {
        int i2 = this._inputPtr;
        int i3 = i2 - 1;
        int i4 = this._inputEnd;
        if (i == 48) {
            return a(false, i3);
        }
        int i5 = 1;
        while (i2 < i4) {
            int i6 = i2 + 1;
            char c = this._inputBuffer[i2];
            if (c >= '0' && c <= '9') {
                i5++;
                i2 = i6;
            } else if (c == '.' || c == 'e' || c == 'E') {
                this._inputPtr = i6;
                return a(c, i3, i6, false, i5);
            } else {
                int i7 = i6 - 1;
                this._inputPtr = i7;
                if (this._parsingContext.inRoot()) {
                    c(c);
                }
                this._textBuffer.resetWithShared(this._inputBuffer, i3, i7 - i3);
                return resetInt(false, i5);
            }
        }
        this._inputPtr = i3;
        return a(false, i3);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, char], vars: [r7v0 ??, r7v1 ??, r7v19 ??, r7v2 ??, r7v8 ??, r7v7 ??, r7v13 ??, r7v6 ??, r7v11 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
        */
    private final com.fasterxml.jackson.core.JsonToken a(
    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, char], vars: [r7v0 ??, r7v1 ??, r7v19 ??, r7v2 ??, r7v8 ??, r7v7 ??, r7v13 ??, r7v6 ??, r7v11 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r7v0 ??
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

    protected final JsonToken _parseNegNumber() throws IOException {
        int i = this._inputPtr;
        int i2 = i - 1;
        int i3 = this._inputEnd;
        if (i >= i3) {
            return a(true, i2);
        }
        int i4 = i + 1;
        char c = this._inputBuffer[i];
        if (c > '9' || c < '0') {
            this._inputPtr = i4;
            return _handleInvalidNumberStart(c, true);
        } else if (c == '0') {
            return a(true, i2);
        } else {
            int i5 = 1;
            while (i4 < i3) {
                int i6 = i4 + 1;
                char c2 = this._inputBuffer[i4];
                if (c2 >= '0' && c2 <= '9') {
                    i5++;
                    i4 = i6;
                } else if (c2 == '.' || c2 == 'e' || c2 == 'E') {
                    this._inputPtr = i6;
                    return a(c2, i2, i6, true, i5);
                } else {
                    int i7 = i6 - 1;
                    this._inputPtr = i7;
                    if (this._parsingContext.inRoot()) {
                        c(c2);
                    }
                    this._textBuffer.resetWithShared(this._inputBuffer, i2, i7 - i2);
                    return resetInt(true, i5);
                }
            }
            return a(true, i2);
        }
    }

    private final JsonToken a(boolean z, int i) throws IOException {
        int i2;
        char c;
        boolean z2;
        int i3;
        char c2;
        int i4;
        if (z) {
            i++;
        }
        this._inputPtr = i;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int i5 = 0;
        if (z) {
            emptyAndGetCurrentSegment[0] = '-';
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (this._inputPtr < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            int i6 = this._inputPtr;
            this._inputPtr = i6 + 1;
            c = cArr[i6];
        } else {
            c = getNextChar("No digit following minus sign", JsonToken.VALUE_NUMBER_INT);
        }
        if (c == '0') {
            c = b();
        }
        char[] cArr2 = emptyAndGetCurrentSegment;
        int i7 = 0;
        while (c >= '0' && c <= '9') {
            i7++;
            if (i2 >= cArr2.length) {
                cArr2 = this._textBuffer.finishCurrentSegment();
                i2 = 0;
            }
            int i8 = i2 + 1;
            cArr2[i2] = c;
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                c = 0;
                i2 = i8;
                z2 = true;
                break;
            }
            char[] cArr3 = this._inputBuffer;
            int i9 = this._inputPtr;
            this._inputPtr = i9 + 1;
            c = cArr3[i9];
            i2 = i8;
        }
        z2 = false;
        if (i7 == 0) {
            return _handleInvalidNumberStart(c, z);
        }
        if (c == '.') {
            if (i2 >= cArr2.length) {
                cArr2 = this._textBuffer.finishCurrentSegment();
                i2 = 0;
            }
            int i10 = i2 + 1;
            cArr2[i2] = c;
            int i11 = 0;
            while (true) {
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    z2 = true;
                    break;
                }
                char[] cArr4 = this._inputBuffer;
                int i12 = this._inputPtr;
                this._inputPtr = i12 + 1;
                c = cArr4[i12];
                if (c < '0' || c > '9') {
                    break;
                }
                i11++;
                if (i10 >= cArr2.length) {
                    cArr2 = this._textBuffer.finishCurrentSegment();
                    i10 = 0;
                }
                i10++;
                cArr2[i10] = c;
            }
            if (i11 == 0) {
                reportUnexpectedNumberChar(c, "Decimal point not followed by a digit");
            }
            i3 = i11;
            i2 = i10;
        } else {
            i3 = 0;
        }
        if (c == 'e' || c == 'E') {
            if (i2 >= cArr2.length) {
                cArr2 = this._textBuffer.finishCurrentSegment();
                i2 = 0;
            }
            int i13 = i2 + 1;
            cArr2[i2] = c;
            if (this._inputPtr < this._inputEnd) {
                char[] cArr5 = this._inputBuffer;
                int i14 = this._inputPtr;
                this._inputPtr = i14 + 1;
                c2 = cArr5[i14];
            } else {
                c2 = getNextChar("expected a digit for number exponent");
            }
            if (c2 == '-' || c2 == '+') {
                if (i13 >= cArr2.length) {
                    cArr2 = this._textBuffer.finishCurrentSegment();
                    i13 = 0;
                }
                i13++;
                cArr2[i13] = c2;
                if (this._inputPtr < this._inputEnd) {
                    char[] cArr6 = this._inputBuffer;
                    int i15 = this._inputPtr;
                    this._inputPtr = i15 + 1;
                    c = cArr6[i15];
                } else {
                    c = getNextChar("expected a digit for number exponent");
                }
                i4 = 0;
            } else {
                i4 = 0;
                c = c2;
            }
            while (c <= '9' && c >= '0') {
                i4++;
                if (i13 >= cArr2.length) {
                    cArr2 = this._textBuffer.finishCurrentSegment();
                    i13 = 0;
                }
                i2 = i13 + 1;
                cArr2[i13] = c;
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    i5 = i4;
                    z2 = true;
                    break;
                }
                char[] cArr7 = this._inputBuffer;
                int i16 = this._inputPtr;
                this._inputPtr = i16 + 1;
                c = cArr7[i16];
                i13 = i2;
            }
            i5 = i4;
            i2 = i13;
            if (i5 == 0) {
                reportUnexpectedNumberChar(c, "Exponent indicator not followed by a digit");
            }
        }
        if (!z2) {
            this._inputPtr--;
            if (this._parsingContext.inRoot()) {
                c(c);
            }
        }
        this._textBuffer.setCurrentLength(i2);
        return reset(z, i7, i3, i5);
    }

    private final char b() throws IOException {
        char c;
        if (this._inputPtr >= this._inputEnd || ((c = this._inputBuffer[this._inputPtr]) >= '0' && c <= '9')) {
            return c();
        }
        return '0';
    }

    private char c() throws IOException {
        char c;
        if ((this._inputPtr >= this._inputEnd && !_loadMore()) || (c = this._inputBuffer[this._inputPtr]) < '0' || c > '9') {
            return '0';
        }
        if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            reportInvalidNumber("Leading zeroes not allowed");
        }
        this._inputPtr++;
        if (c == '0') {
            do {
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    break;
                }
                c = this._inputBuffer[this._inputPtr];
                if (c < '0' || c > '9') {
                    return '0';
                }
                this._inputPtr++;
            } while (c == '0');
        }
        return c;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, char], vars: [r7v0 ??, r7v1 ??, r7v5 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
        */
    protected com.fasterxml.jackson.core.JsonToken _handleInvalidNumberStart(
    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, char], vars: [r7v0 ??, r7v1 ??, r7v5 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r7v0 ??
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

    private final void c(int i) throws IOException {
        this._inputPtr++;
        if (i == 13) {
            _skipCR();
        } else if (i != 32) {
            switch (i) {
                case 9:
                    return;
                case 10:
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                    return;
                default:
                    _reportMissingRootWS(i);
                    return;
            }
        }
    }

    protected final String _parseName() throws IOException {
        int i = this._inputPtr;
        int i2 = this._hashSeed;
        int[] iArr = _icLatin1;
        while (true) {
            if (i >= this._inputEnd) {
                break;
            }
            char c = this._inputBuffer[i];
            if (c >= iArr.length || iArr[c] == 0) {
                i2 = (i2 * 33) + c;
                i++;
            } else if (c == '\"') {
                int i3 = this._inputPtr;
                this._inputPtr = i + 1;
                return this._symbols.findSymbol(this._inputBuffer, i3, i - i3, i2);
            }
        }
        int i4 = this._inputPtr;
        this._inputPtr = i;
        return a(i4, i2, 34);
    }

    private String a(int i, int i2, int i3) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
        char[] currentSegment = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            char[] cArr = this._inputBuffer;
            int i4 = this._inputPtr;
            this._inputPtr = i4 + 1;
            char c = cArr[i4];
            if (c <= '\\') {
                if (c == '\\') {
                    c = _decodeEscaped();
                } else if (c <= i3) {
                    if (c == i3) {
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        TextBuffer textBuffer = this._textBuffer;
                        return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
                    } else if (c < ' ') {
                        _throwUnquotedSpace(c, "name");
                    }
                }
            }
            i2 = (i2 * 33) + c;
            int i5 = currentSegmentSize + 1;
            currentSegment[currentSegmentSize] = c;
            if (i5 >= currentSegment.length) {
                currentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            } else {
                currentSegmentSize = i5;
            }
        }
    }

    protected String _handleOddName(int i) throws IOException {
        boolean z;
        if (i == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return _parseAposName();
        }
        if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            _reportUnexpectedChar(i, "was expecting double-quote to start field name");
        }
        int[] inputCodeLatin1JsNames = CharTypes.getInputCodeLatin1JsNames();
        int length = inputCodeLatin1JsNames.length;
        if (i < length) {
            z = inputCodeLatin1JsNames[i] == 0;
        } else {
            z = Character.isJavaIdentifierPart((char) i);
        }
        if (!z) {
            _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int i2 = this._inputPtr;
        int i3 = this._hashSeed;
        int i4 = this._inputEnd;
        if (i2 < i4) {
            do {
                char c = this._inputBuffer[i2];
                if (c < length) {
                    if (inputCodeLatin1JsNames[c] != 0) {
                        int i5 = this._inputPtr - 1;
                        this._inputPtr = i2;
                        return this._symbols.findSymbol(this._inputBuffer, i5, i2 - i5, i3);
                    }
                } else if (!Character.isJavaIdentifierPart(c)) {
                    int i6 = this._inputPtr - 1;
                    this._inputPtr = i2;
                    return this._symbols.findSymbol(this._inputBuffer, i6, i2 - i6, i3);
                }
                i3 = (i3 * 33) + c;
                i2++;
            } while (i2 < i4);
            this._inputPtr = i2;
            return a(this._inputPtr - 1, i3, inputCodeLatin1JsNames);
        }
        this._inputPtr = i2;
        return a(this._inputPtr - 1, i3, inputCodeLatin1JsNames);
    }

    protected String _parseAposName() throws IOException {
        int i = this._inputPtr;
        int i2 = this._hashSeed;
        int i3 = this._inputEnd;
        if (i < i3) {
            int[] iArr = _icLatin1;
            int length = iArr.length;
            do {
                char c = this._inputBuffer[i];
                if (c != '\'') {
                    if (c < length && iArr[c] != 0) {
                        break;
                    }
                    i2 = (i2 * 33) + c;
                    i++;
                } else {
                    int i4 = this._inputPtr;
                    this._inputPtr = i + 1;
                    return this._symbols.findSymbol(this._inputBuffer, i4, i - i4, i2);
                }
            } while (i < i3);
        }
        int i5 = this._inputPtr;
        this._inputPtr = i;
        return a(i5, i2, 39);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003d, code lost:
        if (r2._parsingContext.inArray() == false) goto L_0x0095;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.core.JsonToken _handleOddValue(int r3) throws java.io.IOException {
        /*
            r2 = this;
            r0 = 39
            if (r3 == r0) goto L_0x0088
            r0 = 73
            r1 = 1
            if (r3 == r0) goto L_0x006c
            r0 = 78
            if (r3 == r0) goto L_0x0050
            r0 = 93
            if (r3 == r0) goto L_0x0037
            switch(r3) {
                case 43: goto L_0x0016;
                case 44: goto L_0x0040;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0095
        L_0x0016:
            int r3 = r2._inputPtr
            int r0 = r2._inputEnd
            if (r3 < r0) goto L_0x0027
            boolean r3 = r2._loadMore()
            if (r3 != 0) goto L_0x0027
            com.fasterxml.jackson.core.JsonToken r3 = com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT
            r2._reportInvalidEOFInValue(r3)
        L_0x0027:
            char[] r3 = r2._inputBuffer
            int r0 = r2._inputPtr
            int r1 = r0 + 1
            r2._inputPtr = r1
            char r3 = r3[r0]
            r0 = 0
            com.fasterxml.jackson.core.JsonToken r3 = r2._handleInvalidNumberStart(r3, r0)
            return r3
        L_0x0037:
            com.fasterxml.jackson.core.json.JsonReadContext r0 = r2._parsingContext
            boolean r0 = r0.inArray()
            if (r0 != 0) goto L_0x0040
            goto L_0x0095
        L_0x0040:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0095
            int r3 = r2._inputPtr
            int r3 = r3 - r1
            r2._inputPtr = r3
            com.fasterxml.jackson.core.JsonToken r3 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL
            return r3
        L_0x0050:
            java.lang.String r0 = "NaN"
            r2._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0066
            java.lang.String r3 = "NaN"
            r0 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            com.fasterxml.jackson.core.JsonToken r3 = r2.resetAsNaN(r3, r0)
            return r3
        L_0x0066:
            java.lang.String r0 = "Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r2._reportError(r0)
            goto L_0x0095
        L_0x006c:
            java.lang.String r0 = "Infinity"
            r2._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0082
            java.lang.String r3 = "Infinity"
            r0 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            com.fasterxml.jackson.core.JsonToken r3 = r2.resetAsNaN(r3, r0)
            return r3
        L_0x0082:
            java.lang.String r0 = "Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r2._reportError(r0)
            goto L_0x0095
        L_0x0088:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0095
            com.fasterxml.jackson.core.JsonToken r3 = r2._handleApos()
            return r3
        L_0x0095:
            boolean r0 = java.lang.Character.isJavaIdentifierStart(r3)
            if (r0 == 0) goto L_0x00b2
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = ""
            r0.append(r1)
            char r1 = (char) r3
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "('true', 'false' or 'null')"
            r2._reportInvalidToken(r0, r1)
        L_0x00b2:
            java.lang.String r0 = "expected a valid value (number, String, array, object, 'true', 'false' or 'null')"
            r2._reportUnexpectedChar(r3, r0)
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser._handleOddValue(int):com.fasterxml.jackson.core.JsonToken");
    }

    protected JsonToken _handleApos() throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c <= '\\') {
                if (c == '\\') {
                    c = _decodeEscaped();
                } else if (c <= '\'') {
                    if (c == '\'') {
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        return JsonToken.VALUE_STRING;
                    } else if (c < ' ') {
                        _throwUnquotedSpace(c, "string value");
                    }
                }
            }
            if (currentSegmentSize >= emptyAndGetCurrentSegment.length) {
                emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            currentSegmentSize++;
            emptyAndGetCurrentSegment[currentSegmentSize] = c;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0069 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0061 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(int r5, int r6, int[] r7) throws java.io.IOException {
        /*
            r4 = this;
            com.fasterxml.jackson.core.util.TextBuffer r0 = r4._textBuffer
            char[] r1 = r4._inputBuffer
            int r2 = r4._inputPtr
            int r2 = r2 - r5
            r0.resetWithShared(r1, r5, r2)
            com.fasterxml.jackson.core.util.TextBuffer r5 = r4._textBuffer
            char[] r5 = r5.getCurrentSegment()
            com.fasterxml.jackson.core.util.TextBuffer r0 = r4._textBuffer
            int r0 = r0.getCurrentSegmentSize()
            int r1 = r7.length
        L_0x0017:
            int r2 = r4._inputPtr
            int r3 = r4._inputEnd
            if (r2 < r3) goto L_0x0024
            boolean r2 = r4._loadMore()
            if (r2 != 0) goto L_0x0024
            goto L_0x0037
        L_0x0024:
            char[] r2 = r4._inputBuffer
            int r3 = r4._inputPtr
            char r2 = r2[r3]
            if (r2 >= r1) goto L_0x0031
            r3 = r7[r2]
            if (r3 == 0) goto L_0x0051
            goto L_0x0037
        L_0x0031:
            boolean r3 = java.lang.Character.isJavaIdentifierPart(r2)
            if (r3 != 0) goto L_0x0051
        L_0x0037:
            com.fasterxml.jackson.core.util.TextBuffer r5 = r4._textBuffer
            r5.setCurrentLength(r0)
            com.fasterxml.jackson.core.util.TextBuffer r5 = r4._textBuffer
            char[] r7 = r5.getTextBuffer()
            int r0 = r5.getTextOffset()
            int r5 = r5.size()
            com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer r1 = r4._symbols
            java.lang.String r5 = r1.findSymbol(r7, r0, r5, r6)
            return r5
        L_0x0051:
            int r3 = r4._inputPtr
            int r3 = r3 + 1
            r4._inputPtr = r3
            int r6 = r6 * 33
            int r6 = r6 + r2
            int r3 = r0 + 1
            r5[r0] = r2
            int r0 = r5.length
            if (r3 < r0) goto L_0x0069
            com.fasterxml.jackson.core.util.TextBuffer r5 = r4._textBuffer
            char[] r5 = r5.finishCurrentSegment()
            r0 = 0
            goto L_0x0017
        L_0x0069:
            r0 = r3
            goto L_0x0017
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser.a(int, int, int[]):java.lang.String");
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected final void _finishString() throws IOException {
        int i = this._inputPtr;
        int i2 = this._inputEnd;
        if (i < i2) {
            int[] iArr = _icLatin1;
            int length = iArr.length;
            while (true) {
                char c = this._inputBuffer[i];
                if (c >= length || iArr[c] == 0) {
                    i++;
                    if (i >= i2) {
                        break;
                    }
                } else if (c == '\"') {
                    this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, i - this._inputPtr);
                    this._inputPtr = i + 1;
                    return;
                }
            }
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, i - this._inputPtr);
        this._inputPtr = i;
        _finishString2();
    }

    protected void _finishString2() throws IOException {
        char[] currentSegment = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        int[] iArr = _icLatin1;
        int length = iArr.length;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c < length && iArr[c] != 0) {
                if (c == '\"') {
                    this._textBuffer.setCurrentLength(currentSegmentSize);
                    return;
                } else if (c == '\\') {
                    c = _decodeEscaped();
                } else if (c < ' ') {
                    _throwUnquotedSpace(c, "string value");
                }
            }
            if (currentSegmentSize >= currentSegment.length) {
                currentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            currentSegmentSize++;
            currentSegment[currentSegmentSize] = c;
        }
    }

    protected final void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int i = this._inputPtr;
        int i2 = this._inputEnd;
        char[] cArr = this._inputBuffer;
        while (true) {
            if (i >= i2) {
                this._inputPtr = i;
                if (!_loadMore()) {
                    _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
                }
                i = this._inputPtr;
                i2 = this._inputEnd;
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c <= '\\') {
                if (c == '\\') {
                    this._inputPtr = i3;
                    _decodeEscaped();
                    i = this._inputPtr;
                    i2 = this._inputEnd;
                } else if (c <= '\"') {
                    if (c == '\"') {
                        this._inputPtr = i3;
                        return;
                    } else if (c < ' ') {
                        this._inputPtr = i3;
                        _throwUnquotedSpace(c, "string value");
                    }
                }
            }
            i = i3;
        }
    }

    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || _loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
            this._inputPtr++;
        }
        this._currInputRow++;
        this._currInputRowStart = this._inputPtr;
    }

    private final int d() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return a(false);
        }
        char c = this._inputBuffer[this._inputPtr];
        if (c == ':') {
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr + 1;
            this._inputPtr = i;
            char c2 = cArr[i];
            if (c2 <= ' ') {
                if (c2 == ' ' || c2 == '\t') {
                    char[] cArr2 = this._inputBuffer;
                    int i2 = this._inputPtr + 1;
                    this._inputPtr = i2;
                    char c3 = cArr2[i2];
                    if (c3 > ' ') {
                        if (c3 == '/' || c3 == '#') {
                            return a(true);
                        }
                        this._inputPtr++;
                        return c3;
                    }
                }
                return a(true);
            } else if (c2 == '/' || c2 == '#') {
                return a(true);
            } else {
                this._inputPtr++;
                return c2;
            }
        } else {
            if (c == ' ' || c == '\t') {
                char[] cArr3 = this._inputBuffer;
                int i3 = this._inputPtr + 1;
                this._inputPtr = i3;
                c = cArr3[i3];
            }
            if (c != ':') {
                return a(false);
            }
            char[] cArr4 = this._inputBuffer;
            int i4 = this._inputPtr + 1;
            this._inputPtr = i4;
            char c4 = cArr4[i4];
            if (c4 <= ' ') {
                if (c4 == ' ' || c4 == '\t') {
                    char[] cArr5 = this._inputBuffer;
                    int i5 = this._inputPtr + 1;
                    this._inputPtr = i5;
                    char c5 = cArr5[i5];
                    if (c5 > ' ') {
                        if (c5 == '/' || c5 == '#') {
                            return a(true);
                        }
                        this._inputPtr++;
                        return c5;
                    }
                }
                return a(true);
            } else if (c4 == '/' || c4 == '#') {
                return a(true);
            } else {
                this._inputPtr++;
                return c4;
            }
        }
    }

    private final int a(boolean z) throws IOException {
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char c = cArr[i];
                if (c > ' ') {
                    if (c == '/') {
                        h();
                    } else if (c != '#' || !j()) {
                        if (z) {
                            return c;
                        }
                        if (c != ':') {
                            _reportUnexpectedChar(c, "was expecting a colon to separate field name and value");
                        }
                        z = true;
                    }
                } else if (c < ' ') {
                    if (c == '\n') {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (c == '\r') {
                        _skipCR();
                    } else if (c != '\t') {
                        _throwInvalidSpace(c);
                    }
                }
            } else {
                _reportInvalidEOF(" within/between " + this._parsingContext.typeDesc() + " entries", null);
                return -1;
            }
        }
    }

    private final int d(int i) throws IOException {
        int i2;
        char[] cArr = this._inputBuffer;
        int i3 = i + 1;
        char c = cArr[i];
        if (c == ':') {
            int i4 = i3 + 1;
            char c2 = cArr[i3];
            if (c2 > ' ') {
                if (!(c2 == '/' || c2 == '#')) {
                    this._inputPtr = i4;
                    return c2;
                }
            } else if (c2 == ' ' || c2 == '\t') {
                int i5 = i4 + 1;
                char c3 = this._inputBuffer[i4];
                if (c3 <= ' ' || c3 == '/' || c3 == '#') {
                    i4 = i5;
                } else {
                    this._inputPtr = i5;
                    return c3;
                }
            }
            this._inputPtr = i4 - 1;
            return a(true);
        }
        if (c == ' ' || c == '\t') {
            i2 = i3 + 1;
            c = this._inputBuffer[i3];
        } else {
            i2 = i3;
        }
        boolean z = c == ':';
        if (z) {
            int i6 = i2 + 1;
            char c4 = this._inputBuffer[i2];
            if (c4 > ' ') {
                if (!(c4 == '/' || c4 == '#')) {
                    this._inputPtr = i6;
                    return c4;
                }
            } else if (c4 == ' ' || c4 == '\t') {
                int i7 = i6 + 1;
                char c5 = this._inputBuffer[i6];
                if (c5 <= ' ' || c5 == '/' || c5 == '#') {
                    i2 = i7;
                } else {
                    this._inputPtr = i7;
                    return c5;
                }
            }
            i2 = i6;
        }
        this._inputPtr = i2 - 1;
        return a(z);
    }

    private final int e(int i) throws IOException {
        if (i != 44) {
            _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
        }
        while (this._inputPtr < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            char c = cArr[i2];
            if (c > ' ') {
                if (c != '/' && c != '#') {
                    return c;
                }
                this._inputPtr--;
                return e();
            } else if (c < ' ') {
                if (c == '\n') {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (c == '\r') {
                    _skipCR();
                } else if (c != '\t') {
                    _throwInvalidSpace(c);
                }
            }
        }
        return e();
    }

    private final int e() throws IOException {
        char c;
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                c = cArr[i];
                if (c > ' ') {
                    if (c == '/') {
                        h();
                    } else if (c != '#' || !j()) {
                        break;
                    }
                } else if (c < ' ') {
                    if (c == '\n') {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (c == '\r') {
                        _skipCR();
                    } else if (c != '\t') {
                        _throwInvalidSpace(c);
                    }
                }
            } else {
                throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
            }
        }
        return c;
    }

    private final int f() throws IOException {
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            return _eofAsNextChar();
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        if (c <= ' ') {
            if (c != ' ') {
                if (c == '\n') {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (c == '\r') {
                    _skipCR();
                } else if (c != '\t') {
                    _throwInvalidSpace(c);
                }
            }
            while (this._inputPtr < this._inputEnd) {
                char[] cArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                char c2 = cArr2[i2];
                if (c2 > ' ') {
                    if (c2 != '/' && c2 != '#') {
                        return c2;
                    }
                    this._inputPtr--;
                    return g();
                } else if (c2 != ' ') {
                    if (c2 == '\n') {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (c2 == '\r') {
                        _skipCR();
                    } else if (c2 != '\t') {
                        _throwInvalidSpace(c2);
                    }
                }
            }
            return g();
        } else if (c != '/' && c != '#') {
            return c;
        } else {
            this._inputPtr--;
            return g();
        }
    }

    private int g() throws IOException {
        char c;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                return _eofAsNextChar();
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            c = cArr[i];
            if (c > ' ') {
                if (c == '/') {
                    h();
                } else if (c != '#' || !j()) {
                    break;
                }
            } else if (c != ' ') {
                if (c == '\n') {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (c == '\r') {
                    _skipCR();
                } else if (c != '\t') {
                    _throwInvalidSpace(c);
                }
            }
        }
        return c;
    }

    private void h() throws IOException {
        if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(" in a comment", null);
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        if (c == '/') {
            k();
        } else if (c == '*') {
            i();
        } else {
            _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0028, code lost:
        _reportInvalidEOF(" in a comment", null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void i() throws java.io.IOException {
        /*
            r3 = this;
        L_0x0000:
            int r0 = r3._inputPtr
            int r1 = r3._inputEnd
            if (r0 < r1) goto L_0x000c
            boolean r0 = r3._loadMore()
            if (r0 == 0) goto L_0x0028
        L_0x000c:
            char[] r0 = r3._inputBuffer
            int r1 = r3._inputPtr
            int r2 = r1 + 1
            r3._inputPtr = r2
            char r0 = r0[r1]
            r1 = 42
            if (r0 > r1) goto L_0x0000
            if (r0 != r1) goto L_0x0040
            int r0 = r3._inputPtr
            int r1 = r3._inputEnd
            if (r0 < r1) goto L_0x002f
            boolean r0 = r3._loadMore()
            if (r0 != 0) goto L_0x002f
        L_0x0028:
            java.lang.String r0 = " in a comment"
            r1 = 0
            r3._reportInvalidEOF(r0, r1)
            return
        L_0x002f:
            char[] r0 = r3._inputBuffer
            int r1 = r3._inputPtr
            char r0 = r0[r1]
            r1 = 47
            if (r0 != r1) goto L_0x0000
            int r0 = r3._inputPtr
            int r0 = r0 + 1
            r3._inputPtr = r0
            return
        L_0x0040:
            r1 = 32
            if (r0 >= r1) goto L_0x0000
            r1 = 10
            if (r0 != r1) goto L_0x0053
            int r0 = r3._currInputRow
            int r0 = r0 + 1
            r3._currInputRow = r0
            int r0 = r3._inputPtr
            r3._currInputRowStart = r0
            goto L_0x0000
        L_0x0053:
            r1 = 13
            if (r0 != r1) goto L_0x005b
            r3._skipCR()
            goto L_0x0000
        L_0x005b:
            r1 = 9
            if (r0 == r1) goto L_0x0000
            r3._throwInvalidSpace(r0)
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser.i():void");
    }

    private boolean j() throws IOException {
        if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        k();
        return true;
    }

    private void k() throws IOException {
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char c = cArr[i];
                if (c < ' ') {
                    if (c == '\n') {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                        return;
                    } else if (c == '\r') {
                        _skipCR();
                        return;
                    } else if (c != '\t') {
                        _throwInvalidSpace(c);
                    }
                }
            } else {
                return;
            }
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected char _decodeEscaped() throws IOException {
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        if (c == '\"' || c == '/' || c == '\\') {
            return c;
        }
        if (c == 'b') {
            return '\b';
        }
        if (c == 'f') {
            return '\f';
        }
        if (c == 'n') {
            return '\n';
        }
        if (c == 'r') {
            return '\r';
        }
        switch (c) {
            case 't':
                return '\t';
            case 'u':
                int i2 = 0;
                for (int i3 = 0; i3 < 4; i3++) {
                    if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                        _reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
                    }
                    char[] cArr2 = this._inputBuffer;
                    int i4 = this._inputPtr;
                    this._inputPtr = i4 + 1;
                    char c2 = cArr2[i4];
                    int charToHex = CharTypes.charToHex(c2);
                    if (charToHex < 0) {
                        _reportUnexpectedChar(c2, "expected a hex-digit for character escape sequence");
                    }
                    i2 = (i2 << 4) | charToHex;
                }
                return (char) i2;
            default:
                return _handleUnrecognizedCharacterEscape(c);
        }
    }

    private final void l() throws IOException {
        int i;
        char c;
        int i2 = this._inputPtr;
        if (i2 + 3 < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            if (cArr[i2] == 'r') {
                int i3 = i2 + 1;
                if (cArr[i3] == 'u') {
                    int i4 = i3 + 1;
                    if (cArr[i4] == 'e' && ((c = cArr[(i = i4 + 1)]) < '0' || c == ']' || c == '}')) {
                        this._inputPtr = i;
                        return;
                    }
                }
            }
        }
        _matchToken("true", 1);
    }

    private final void m() throws IOException {
        int i;
        char c;
        int i2 = this._inputPtr;
        if (i2 + 4 < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            if (cArr[i2] == 'a') {
                int i3 = i2 + 1;
                if (cArr[i3] == 'l') {
                    int i4 = i3 + 1;
                    if (cArr[i4] == 's') {
                        int i5 = i4 + 1;
                        if (cArr[i5] == 'e' && ((c = cArr[(i = i5 + 1)]) < '0' || c == ']' || c == '}')) {
                            this._inputPtr = i;
                            return;
                        }
                    }
                }
            }
        }
        _matchToken("false", 1);
    }

    private final void n() throws IOException {
        int i;
        char c;
        int i2 = this._inputPtr;
        if (i2 + 3 < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            if (cArr[i2] == 'u') {
                int i3 = i2 + 1;
                if (cArr[i3] == 'l') {
                    int i4 = i3 + 1;
                    if (cArr[i4] == 'l' && ((c = cArr[(i = i4 + 1)]) < '0' || c == ']' || c == '}')) {
                        this._inputPtr = i;
                        return;
                    }
                }
            }
        }
        _matchToken("null", 1);
    }

    protected final void _matchToken(String str, int i) throws IOException {
        int length = str.length();
        if (this._inputPtr + length >= this._inputEnd) {
            a(str, i);
            return;
        }
        do {
            if (this._inputBuffer[this._inputPtr] != str.charAt(i)) {
                _reportInvalidToken(str.substring(0, i));
            }
            this._inputPtr++;
            i++;
        } while (i < length);
        char c = this._inputBuffer[this._inputPtr];
        if (c >= '0' && c != ']' && c != '}') {
            a(str, i, c);
        }
    }

    private final void a(String str, int i) throws IOException {
        char c;
        int length = str.length();
        do {
            if ((this._inputPtr >= this._inputEnd && !_loadMore()) || this._inputBuffer[this._inputPtr] != str.charAt(i)) {
                _reportInvalidToken(str.substring(0, i));
            }
            this._inputPtr++;
            i++;
        } while (i < length);
        if ((this._inputPtr < this._inputEnd || _loadMore()) && (c = this._inputBuffer[this._inputPtr]) >= '0' && c != ']' && c != '}') {
            a(str, i, c);
        }
    }

    private final void a(String str, int i, int i2) throws IOException {
        if (Character.isJavaIdentifierPart((char) i2)) {
            _reportInvalidToken(str.substring(0, i));
        }
    }

    protected byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder _getByteArrayBuilder = _getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                _loadMoreGuaranteed();
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c > ' ') {
                int decodeBase64Char = base64Variant.decodeBase64Char(c);
                if (decodeBase64Char < 0) {
                    if (c == '\"') {
                        return _getByteArrayBuilder.toByteArray();
                    }
                    decodeBase64Char = _decodeBase64Escape(base64Variant, c, 0);
                    if (decodeBase64Char < 0) {
                        continue;
                    }
                }
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                char c2 = cArr2[i2];
                int decodeBase64Char2 = base64Variant.decodeBase64Char(c2);
                if (decodeBase64Char2 < 0) {
                    decodeBase64Char2 = _decodeBase64Escape(base64Variant, c2, 1);
                }
                int i3 = (decodeBase64Char << 6) | decodeBase64Char2;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr3 = this._inputBuffer;
                int i4 = this._inputPtr;
                this._inputPtr = i4 + 1;
                char c3 = cArr3[i4];
                int decodeBase64Char3 = base64Variant.decodeBase64Char(c3);
                if (decodeBase64Char3 < 0) {
                    if (decodeBase64Char3 != -2) {
                        if (c3 == '\"') {
                            _getByteArrayBuilder.append(i3 >> 4);
                            if (base64Variant.usesPadding()) {
                                this._inputPtr--;
                                _handleBase64MissingPadding(base64Variant);
                            }
                            return _getByteArrayBuilder.toByteArray();
                        }
                        decodeBase64Char3 = _decodeBase64Escape(base64Variant, c3, 2);
                    }
                    if (decodeBase64Char3 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            _loadMoreGuaranteed();
                        }
                        char[] cArr4 = this._inputBuffer;
                        int i5 = this._inputPtr;
                        this._inputPtr = i5 + 1;
                        char c4 = cArr4[i5];
                        if (base64Variant.usesPaddingChar(c4) || _decodeBase64Escape(base64Variant, c4, 3) == -2) {
                            _getByteArrayBuilder.append(i3 >> 4);
                        } else {
                            throw reportInvalidBase64Char(base64Variant, c4, 3, "expected padding character '" + base64Variant.getPaddingChar() + LrcRow.SINGLE_QUOTE);
                        }
                    }
                }
                int i6 = (i3 << 6) | decodeBase64Char3;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr5 = this._inputBuffer;
                int i7 = this._inputPtr;
                this._inputPtr = i7 + 1;
                char c5 = cArr5[i7];
                int decodeBase64Char4 = base64Variant.decodeBase64Char(c5);
                if (decodeBase64Char4 < 0) {
                    if (decodeBase64Char4 != -2) {
                        if (c5 == '\"') {
                            _getByteArrayBuilder.appendTwoBytes(i6 >> 2);
                            if (base64Variant.usesPadding()) {
                                this._inputPtr--;
                                _handleBase64MissingPadding(base64Variant);
                            }
                            return _getByteArrayBuilder.toByteArray();
                        }
                        decodeBase64Char4 = _decodeBase64Escape(base64Variant, c5, 3);
                    }
                    if (decodeBase64Char4 == -2) {
                        _getByteArrayBuilder.appendTwoBytes(i6 >> 2);
                    }
                }
                _getByteArrayBuilder.appendThreeBytes((i6 << 6) | decodeBase64Char4);
            }
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
    public JsonLocation getTokenLocation() {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return new JsonLocation(_getSourceReference(), -1L, this._tokenInputTotal - 1, this._tokenInputRow, this._tokenInputCol);
        }
        return new JsonLocation(_getSourceReference(), -1L, this._currInputProcessed + (this._nameStartOffset - 1), this._nameStartRow, this._nameStartCol);
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
    public JsonLocation getCurrentLocation() {
        return new JsonLocation(_getSourceReference(), -1L, this._inputPtr + this._currInputProcessed, this._currInputRow, (this._inputPtr - this._currInputRowStart) + 1);
    }

    private final void o() {
        int i = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + i;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = i - this._currInputRowStart;
    }

    private final void p() {
        int i = this._inputPtr;
        this._nameStartOffset = i;
        this._nameStartRow = this._currInputRow;
        this._nameStartCol = i - this._currInputRowStart;
    }

    protected void _reportInvalidToken(String str) throws IOException {
        _reportInvalidToken(str, "'null', 'true', 'false' or NaN");
    }

    protected void _reportInvalidToken(String str, String str2) throws IOException {
        StringBuilder sb = new StringBuilder(str);
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                break;
            }
            char c = this._inputBuffer[this._inputPtr];
            if (Character.isJavaIdentifierPart(c)) {
                this._inputPtr++;
                sb.append(c);
                if (sb.length() >= 256) {
                    sb.append("...");
                    break;
                }
            } else {
                break;
            }
        }
        _reportError("Unrecognized token '%s': was expecting %s", sb, str2);
    }

    private void f(int i) throws JsonParseException {
        if (i == 93) {
            o();
            if (!this._parsingContext.inArray()) {
                _reportMismatchedEndMarker(i, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (i == 125) {
            o();
            if (!this._parsingContext.inObject()) {
                _reportMismatchedEndMarker(i, ']');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_OBJECT;
        }
    }
}
