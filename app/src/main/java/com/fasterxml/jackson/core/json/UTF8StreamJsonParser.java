package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import okio.Utf8;

/* loaded from: classes.dex */
public class UTF8StreamJsonParser extends ParserBase {
    protected boolean _bufferRecyclable;
    protected byte[] _inputBuffer;
    protected InputStream _inputStream;
    protected int _nameStartCol;
    protected int _nameStartOffset;
    protected int _nameStartRow;
    protected ObjectCodec _objectCodec;
    protected int[] _quadBuffer = new int[16];
    protected final ByteQuadsCanonicalizer _symbols;
    protected boolean _tokenIncomplete;
    private int b;
    private static final int[] a = CharTypes.getInputCodeUtf8();
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
    protected static final int FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();

    private static final int b(int i, int i2) {
        return i2 == 4 ? i : i | ((-1) << (i2 << 3));
    }

    public UTF8StreamJsonParser(IOContext iOContext, int i, InputStream inputStream, ObjectCodec objectCodec, ByteQuadsCanonicalizer byteQuadsCanonicalizer, byte[] bArr, int i2, int i3, boolean z) {
        super(iOContext, i);
        this._inputStream = inputStream;
        this._objectCodec = objectCodec;
        this._symbols = byteQuadsCanonicalizer;
        this._inputBuffer = bArr;
        this._inputPtr = i2;
        this._inputEnd = i3;
        this._currInputRowStart = i2;
        this._currInputProcessed = -i2;
        this._bufferRecyclable = z;
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
    public int releaseBuffered(OutputStream outputStream) throws IOException {
        int i = this._inputEnd - this._inputPtr;
        if (i < 1) {
            return 0;
        }
        outputStream.write(this._inputBuffer, this._inputPtr, i);
        return i;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public Object getInputSource() {
        return this._inputStream;
    }

    protected final boolean _loadMore() throws IOException {
        byte[] bArr;
        int length;
        int i = this._inputEnd;
        this._currInputProcessed += this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        this._nameStartOffset -= i;
        InputStream inputStream = this._inputStream;
        if (inputStream == null || (length = (bArr = this._inputBuffer).length) == 0) {
            return false;
        }
        int read = inputStream.read(bArr, 0, length);
        if (read > 0) {
            this._inputPtr = 0;
            this._inputEnd = read;
            return true;
        }
        _closeInput();
        if (read == 0) {
            throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
        }
        return false;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _closeInput() throws IOException {
        if (this._inputStream != null) {
            if (this._ioContext.isResourceManaged() || isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
                this._inputStream.close();
            }
            this._inputStream = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.core.base.ParserBase
    public void _releaseBuffers() throws IOException {
        byte[] bArr;
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable && (bArr = this._inputBuffer) != null) {
            this._inputBuffer = NO_BYTES;
            this._ioContext.releaseReadIOBuffer(bArr);
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public String getText() throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return _getText2(this._currToken);
        }
        if (!this._tokenIncomplete) {
            return this._textBuffer.contentsAsString();
        }
        this._tokenIncomplete = false;
        return _finishAndReturnString();
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
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) {
                return this._textBuffer.contentsAsString();
            }
            this._tokenIncomplete = false;
            return _finishAndReturnString();
        } else if (this._currToken == JsonToken.FIELD_NAME) {
            return getCurrentName();
        } else {
            return super.getValueAsString(null);
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public String getValueAsString(String str) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) {
                return this._textBuffer.contentsAsString();
            }
            this._tokenIncomplete = false;
            return _finishAndReturnString();
        } else if (this._currToken == JsonToken.FIELD_NAME) {
            return getCurrentName();
        } else {
            return super.getValueAsString(str);
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public int getValueAsInt() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_NUMBER_INT && jsonToken != JsonToken.VALUE_NUMBER_FLOAT) {
            return super.getValueAsInt(0);
        }
        if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
                return _parseIntValue();
            }
            if ((this._numTypesValid & 1) == 0) {
                convertNumberToInt();
            }
        }
        return this._numberInt;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public int getValueAsInt(int i) throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_NUMBER_INT && jsonToken != JsonToken.VALUE_NUMBER_FLOAT) {
            return super.getValueAsInt(i);
        }
        if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
                return _parseIntValue();
            }
            if ((this._numTypesValid & 1) == 0) {
                convertNumberToInt();
            }
        }
        return this._numberInt;
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
    public char[] getTextCharacters() throws IOException {
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
    public int getTextLength() throws IOException {
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
    public int getTextOffset() throws IOException {
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
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
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

    /* JADX WARN: Code restructure failed: missing block: B:54:0x012f, code lost:
        r16._tokenIncomplete = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0131, code lost:
        if (r8 <= 0) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0133, code lost:
        r7 = r7 + r8;
        r18.write(r19, 0, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0137, code lost:
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:?, code lost:
        return r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int _readBinary(com.fasterxml.jackson.core.Base64Variant r17, java.io.OutputStream r18, byte[] r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8StreamJsonParser._readBinary(com.fasterxml.jackson.core.Base64Variant, java.io.OutputStream, byte[]):int");
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public JsonToken nextToken() throws IOException {
        JsonToken jsonToken;
        if (this._currToken == JsonToken.FIELD_NAME) {
            return a();
        }
        this._numTypesValid = 0;
        if (this._tokenIncomplete) {
            _skipString();
        }
        int e = e();
        if (e < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (e == 93) {
            q();
            JsonToken jsonToken2 = JsonToken.END_ARRAY;
            this._currToken = jsonToken2;
            return jsonToken2;
        } else if (e == 125) {
            r();
            JsonToken jsonToken3 = JsonToken.END_OBJECT;
            this._currToken = jsonToken3;
            return jsonToken3;
        } else {
            if (this._parsingContext.expectComma()) {
                if (e != 44) {
                    _reportUnexpectedChar(e, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
                }
                e = c();
                if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (e == 93 || e == 125)) {
                    return j(e);
                }
            }
            if (!this._parsingContext.inObject()) {
                o();
                return a(e);
            }
            p();
            this._parsingContext.setCurrentName(_parseName(e));
            this._currToken = JsonToken.FIELD_NAME;
            int g = g();
            o();
            if (g == 34) {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
                return this._currToken;
            }
            if (g == 45) {
                jsonToken = _parseNegNumber();
            } else if (g == 91) {
                jsonToken = JsonToken.START_ARRAY;
            } else if (g == 102) {
                _matchFalse();
                jsonToken = JsonToken.VALUE_FALSE;
            } else if (g == 110) {
                _matchNull();
                jsonToken = JsonToken.VALUE_NULL;
            } else if (g == 116) {
                _matchTrue();
                jsonToken = JsonToken.VALUE_TRUE;
            } else if (g != 123) {
                switch (g) {
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
                        jsonToken = _parsePosNumber(g);
                        break;
                    default:
                        jsonToken = _handleUnexpectedValue(g);
                        break;
                }
            } else {
                jsonToken = JsonToken.START_OBJECT;
            }
            this._nextToken = jsonToken;
            return this._currToken;
        }
    }

    private final JsonToken a(int i) throws IOException {
        if (i == 34) {
            this._tokenIncomplete = true;
            JsonToken jsonToken = JsonToken.VALUE_STRING;
            this._currToken = jsonToken;
            return jsonToken;
        } else if (i == 45) {
            JsonToken _parseNegNumber = _parseNegNumber();
            this._currToken = _parseNegNumber;
            return _parseNegNumber;
        } else if (i == 91) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            JsonToken jsonToken2 = JsonToken.START_ARRAY;
            this._currToken = jsonToken2;
            return jsonToken2;
        } else if (i == 102) {
            _matchFalse();
            JsonToken jsonToken3 = JsonToken.VALUE_FALSE;
            this._currToken = jsonToken3;
            return jsonToken3;
        } else if (i == 110) {
            _matchNull();
            JsonToken jsonToken4 = JsonToken.VALUE_NULL;
            this._currToken = jsonToken4;
            return jsonToken4;
        } else if (i == 116) {
            _matchTrue();
            JsonToken jsonToken5 = JsonToken.VALUE_TRUE;
            this._currToken = jsonToken5;
            return jsonToken5;
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
                    JsonToken _parsePosNumber = _parsePosNumber(i);
                    this._currToken = _parsePosNumber;
                    return _parsePosNumber;
                default:
                    JsonToken _handleUnexpectedValue = _handleUnexpectedValue(i);
                    this._currToken = _handleUnexpectedValue;
                    return _handleUnexpectedValue;
            }
        } else {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            JsonToken jsonToken6 = JsonToken.START_OBJECT;
            this._currToken = jsonToken6;
            return jsonToken6;
        }
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
        int e = e();
        if (e < 0) {
            close();
            this._currToken = null;
            return false;
        }
        this._binaryValue = null;
        if (e == 93) {
            q();
            this._currToken = JsonToken.END_ARRAY;
            return false;
        } else if (e == 125) {
            r();
            this._currToken = JsonToken.END_OBJECT;
            return false;
        } else {
            if (this._parsingContext.expectComma()) {
                if (e != 44) {
                    _reportUnexpectedChar(e, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
                }
                e = c();
                if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (e == 93 || e == 125)) {
                    j(e);
                    return false;
                }
            }
            if (!this._parsingContext.inObject()) {
                o();
                a(e);
                return false;
            }
            p();
            if (e == 34) {
                byte[] asQuotedUTF8 = serializableString.asQuotedUTF8();
                int length = asQuotedUTF8.length;
                if (this._inputPtr + length + 4 < this._inputEnd) {
                    int i2 = this._inputPtr + length;
                    if (this._inputBuffer[i2] == 34) {
                        int i3 = this._inputPtr;
                        while (i3 != i2) {
                            if (asQuotedUTF8[i] == this._inputBuffer[i3]) {
                                i++;
                                i3++;
                            }
                        }
                        this._parsingContext.setCurrentName(serializableString.getValue());
                        c(b(i3 + 1));
                        return true;
                    }
                }
            }
            return a(e, serializableString);
        }
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
        int e = e();
        if (e < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (e == 93) {
            q();
            this._currToken = JsonToken.END_ARRAY;
            return null;
        } else if (e == 125) {
            r();
            this._currToken = JsonToken.END_OBJECT;
            return null;
        } else {
            if (this._parsingContext.expectComma()) {
                if (e != 44) {
                    _reportUnexpectedChar(e, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
                }
                e = c();
                if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (e == 93 || e == 125)) {
                    j(e);
                    return null;
                }
            }
            if (!this._parsingContext.inObject()) {
                o();
                a(e);
                return null;
            }
            p();
            String _parseName = _parseName(e);
            this._parsingContext.setCurrentName(_parseName);
            this._currToken = JsonToken.FIELD_NAME;
            int g = g();
            o();
            if (g == 34) {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
                return _parseName;
            }
            if (g == 45) {
                jsonToken = _parseNegNumber();
            } else if (g == 91) {
                jsonToken = JsonToken.START_ARRAY;
            } else if (g == 102) {
                _matchFalse();
                jsonToken = JsonToken.VALUE_FALSE;
            } else if (g == 110) {
                _matchNull();
                jsonToken = JsonToken.VALUE_NULL;
            } else if (g == 116) {
                _matchTrue();
                jsonToken = JsonToken.VALUE_TRUE;
            } else if (g != 123) {
                switch (g) {
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
                        jsonToken = _parsePosNumber(g);
                        break;
                    default:
                        jsonToken = _handleUnexpectedValue(g);
                        break;
                }
            } else {
                jsonToken = JsonToken.START_OBJECT;
            }
            this._nextToken = jsonToken;
            return _parseName;
        }
    }

    private final int b(int i) throws IOException {
        int i2;
        int i3;
        byte[] bArr = this._inputBuffer;
        int i4 = i + 1;
        byte b = bArr[i];
        if (b == 58) {
            int i5 = i4 + 1;
            byte b2 = bArr[i4];
            if (b2 > 32) {
                if (!(b2 == 47 || b2 == 35)) {
                    this._inputPtr = i5;
                    return b2;
                }
            } else if (b2 == 32 || b2 == 9) {
                int i6 = i5 + 1;
                byte b3 = this._inputBuffer[i5];
                if (b3 <= 32 || b3 == 47 || b3 == 35) {
                    i5 = i6;
                } else {
                    this._inputPtr = i6;
                    return b3;
                }
            }
            this._inputPtr = i5 - 1;
            return a(true);
        }
        if (b == 32 || b == 9) {
            i2 = i4 + 1;
            b = this._inputBuffer[i4];
        } else {
            i2 = i4;
        }
        if (b == 58) {
            int i7 = i2 + 1;
            byte b4 = this._inputBuffer[i2];
            if (b4 > 32) {
                if (!(b4 == 47 || b4 == 35)) {
                    this._inputPtr = i7;
                    return b4;
                }
            } else if (b4 == 32 || b4 == 9) {
                i3 = i7 + 1;
                byte b5 = this._inputBuffer[i7];
                if (!(b5 <= 32 || b5 == 47 || b5 == 35)) {
                    this._inputPtr = i3;
                    return b5;
                }
                this._inputPtr = i3 - 1;
                return a(true);
            }
            i3 = i7;
            this._inputPtr = i3 - 1;
            return a(true);
        }
        this._inputPtr = i2 - 1;
        return a(false);
    }

    private final void c(int i) throws IOException {
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
            _matchFalse();
            this._nextToken = JsonToken.VALUE_FALSE;
        } else if (i == 110) {
            _matchNull();
            this._nextToken = JsonToken.VALUE_NULL;
        } else if (i == 116) {
            _matchTrue();
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
                    this._nextToken = _handleUnexpectedValue(i);
                    return;
            }
        } else {
            this._nextToken = JsonToken.START_OBJECT;
        }
    }

    private final boolean a(int i, SerializableString serializableString) throws IOException {
        JsonToken jsonToken;
        String _parseName = _parseName(i);
        this._parsingContext.setCurrentName(_parseName);
        boolean equals = _parseName.equals(serializableString.getValue());
        this._currToken = JsonToken.FIELD_NAME;
        int g = g();
        o();
        if (g == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return equals;
        }
        if (g == 45) {
            jsonToken = _parseNegNumber();
        } else if (g == 91) {
            jsonToken = JsonToken.START_ARRAY;
        } else if (g == 102) {
            _matchFalse();
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (g == 110) {
            _matchNull();
            jsonToken = JsonToken.VALUE_NULL;
        } else if (g == 116) {
            _matchTrue();
            jsonToken = JsonToken.VALUE_TRUE;
        } else if (g != 123) {
            switch (g) {
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
                    jsonToken = _parsePosNumber(g);
                    break;
                default:
                    jsonToken = _handleUnexpectedValue(g);
                    break;
            }
        } else {
            jsonToken = JsonToken.START_OBJECT;
        }
        this._nextToken = jsonToken;
        return equals;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public String nextTextValue() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken != JsonToken.VALUE_STRING) {
                if (jsonToken == JsonToken.START_ARRAY) {
                    this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                } else if (jsonToken == JsonToken.START_OBJECT) {
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                }
                return null;
            } else if (!this._tokenIncomplete) {
                return this._textBuffer.contentsAsString();
            } else {
                this._tokenIncomplete = false;
                return _finishAndReturnString();
            }
        } else if (nextToken() == JsonToken.VALUE_STRING) {
            return getText();
        } else {
            return null;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int nextIntValue(int i) throws IOException {
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
    public long nextLongValue(long j) throws IOException {
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
    public Boolean nextBooleanValue() throws IOException {
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
        if (nextToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (nextToken == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        return null;
    }

    protected JsonToken _parsePosNumber(int i) throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        if (i == 48) {
            i = b();
        }
        emptyAndGetCurrentSegment[0] = (char) i;
        int min = Math.min(this._inputEnd, (this._inputPtr + emptyAndGetCurrentSegment.length) - 1);
        int i2 = 1;
        int i3 = 1;
        while (this._inputPtr < min) {
            byte[] bArr = this._inputBuffer;
            int i4 = this._inputPtr;
            this._inputPtr = i4 + 1;
            int i5 = bArr[i4] & 255;
            if (i5 >= 48 && i5 <= 57) {
                i3++;
                i2++;
                emptyAndGetCurrentSegment[i2] = (char) i5;
            } else if (i5 == 46 || i5 == 101 || i5 == 69) {
                return a(emptyAndGetCurrentSegment, i2, i5, false, i3);
            } else {
                this._inputPtr--;
                this._textBuffer.setCurrentLength(i2);
                if (this._parsingContext.inRoot()) {
                    d(i5);
                }
                return resetInt(false, i3);
            }
        }
        return a(emptyAndGetCurrentSegment, i2, false, i3);
    }

    protected JsonToken _parseNegNumber() throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        emptyAndGetCurrentSegment[0] = '-';
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        int i2 = bArr[i] & 255;
        if (i2 <= 48) {
            if (i2 != 48) {
                return _handleInvalidNumberStart(i2, true);
            }
            i2 = b();
        } else if (i2 > 57) {
            return _handleInvalidNumberStart(i2, true);
        }
        int i3 = 2;
        emptyAndGetCurrentSegment[1] = (char) i2;
        int min = Math.min(this._inputEnd, (this._inputPtr + emptyAndGetCurrentSegment.length) - 2);
        int i4 = 1;
        while (this._inputPtr < min) {
            byte[] bArr2 = this._inputBuffer;
            int i5 = this._inputPtr;
            this._inputPtr = i5 + 1;
            int i6 = bArr2[i5] & 255;
            if (i6 >= 48 && i6 <= 57) {
                i4++;
                i3++;
                emptyAndGetCurrentSegment[i3] = (char) i6;
            } else if (i6 == 46 || i6 == 101 || i6 == 69) {
                return a(emptyAndGetCurrentSegment, i3, i6, true, i4);
            } else {
                this._inputPtr--;
                this._textBuffer.setCurrentLength(i3);
                if (this._parsingContext.inRoot()) {
                    d(i6);
                }
                return resetInt(true, i4);
            }
        }
        return a(emptyAndGetCurrentSegment, i3, true, i4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0044, code lost:
        if (r3 == 46) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0048, code lost:
        if (r3 == 101) goto L_0x0072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004c, code lost:
        if (r3 != 69) goto L_0x004f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004f, code lost:
        r6._inputPtr--;
        r6._textBuffer.setCurrentLength(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0060, code lost:
        if (r6._parsingContext.inRoot() == false) goto L_0x006d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0062, code lost:
        d(r6._inputBuffer[r6._inputPtr] & 255);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0071, code lost:
        return resetInt(r9, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0078, code lost:
        return a(r1, r2, r3, r9, r5);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final com.fasterxml.jackson.core.JsonToken a(char[] r7, int r8, boolean r9, int r10) throws java.io.IOException {
        /*
            r6 = this;
            r1 = r7
            r2 = r8
            r5 = r10
        L_0x0003:
            int r7 = r6._inputPtr
            int r8 = r6._inputEnd
            if (r7 < r8) goto L_0x0019
            boolean r7 = r6._loadMore()
            if (r7 != 0) goto L_0x0019
            com.fasterxml.jackson.core.util.TextBuffer r7 = r6._textBuffer
            r7.setCurrentLength(r2)
            com.fasterxml.jackson.core.JsonToken r7 = r6.resetInt(r9, r5)
            return r7
        L_0x0019:
            byte[] r7 = r6._inputBuffer
            int r8 = r6._inputPtr
            int r10 = r8 + 1
            r6._inputPtr = r10
            byte r7 = r7[r8]
            r3 = r7 & 255(0xff, float:3.57E-43)
            r7 = 57
            if (r3 > r7) goto L_0x0042
            r7 = 48
            if (r3 >= r7) goto L_0x002e
            goto L_0x0042
        L_0x002e:
            int r7 = r1.length
            if (r2 < r7) goto L_0x0039
            com.fasterxml.jackson.core.util.TextBuffer r7 = r6._textBuffer
            char[] r7 = r7.finishCurrentSegment()
            r2 = 0
            r1 = r7
        L_0x0039:
            int r7 = r2 + 1
            char r8 = (char) r3
            r1[r2] = r8
            int r5 = r5 + 1
            r2 = r7
            goto L_0x0003
        L_0x0042:
            r7 = 46
            if (r3 == r7) goto L_0x0072
            r7 = 101(0x65, float:1.42E-43)
            if (r3 == r7) goto L_0x0072
            r7 = 69
            if (r3 != r7) goto L_0x004f
            goto L_0x0072
        L_0x004f:
            int r7 = r6._inputPtr
            int r7 = r7 + (-1)
            r6._inputPtr = r7
            com.fasterxml.jackson.core.util.TextBuffer r7 = r6._textBuffer
            r7.setCurrentLength(r2)
            com.fasterxml.jackson.core.json.JsonReadContext r7 = r6._parsingContext
            boolean r7 = r7.inRoot()
            if (r7 == 0) goto L_0x006d
            byte[] r7 = r6._inputBuffer
            int r8 = r6._inputPtr
            byte r7 = r7[r8]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r6.d(r7)
        L_0x006d:
            com.fasterxml.jackson.core.JsonToken r7 = r6.resetInt(r9, r5)
            return r7
        L_0x0072:
            r0 = r6
            r4 = r9
            com.fasterxml.jackson.core.JsonToken r7 = r0.a(r1, r2, r3, r4, r5)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8StreamJsonParser.a(char[], int, boolean, int):com.fasterxml.jackson.core.JsonToken");
    }

    private final int b() throws IOException {
        int i;
        if ((this._inputPtr >= this._inputEnd && !_loadMore()) || (i = this._inputBuffer[this._inputPtr] & 255) < 48 || i > 57) {
            return 48;
        }
        if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            reportInvalidNumber("Leading zeroes not allowed");
        }
        this._inputPtr++;
        if (i == 48) {
            do {
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    break;
                }
                i = this._inputBuffer[this._inputPtr] & 255;
                if (i < 48 || i > 57) {
                    return 48;
                }
                this._inputPtr++;
            } while (i == 48);
        }
        return i;
    }

    private final JsonToken a(char[] cArr, int i, int i2, boolean z, int i3) throws IOException {
        boolean z2;
        int i4;
        char[] cArr2;
        int i5;
        int i6 = 0;
        if (i2 == 46) {
            if (i >= cArr.length) {
                cArr = this._textBuffer.finishCurrentSegment();
                i = 0;
            }
            i++;
            cArr[i] = (char) i2;
            int i7 = i2;
            char[] cArr3 = cArr;
            int i8 = 0;
            while (true) {
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    z2 = true;
                    break;
                }
                byte[] bArr = this._inputBuffer;
                int i9 = this._inputPtr;
                this._inputPtr = i9 + 1;
                i7 = bArr[i9] & 255;
                if (i7 < 48 || i7 > 57) {
                    break;
                }
                i8++;
                if (i >= cArr3.length) {
                    cArr3 = this._textBuffer.finishCurrentSegment();
                    i = 0;
                }
                i++;
                cArr3[i] = (char) i7;
            }
            z2 = false;
            if (i8 == 0) {
                reportUnexpectedNumberChar(i7, "Decimal point not followed by a digit");
            }
            i4 = i8;
            cArr = cArr3;
            i2 = i7;
        } else {
            i4 = 0;
            z2 = false;
        }
        if (i2 == 101 || i2 == 69) {
            if (i >= cArr.length) {
                cArr = this._textBuffer.finishCurrentSegment();
                i = 0;
            }
            int i10 = i + 1;
            cArr[i] = (char) i2;
            if (this._inputPtr >= this._inputEnd) {
                _loadMoreGuaranteed();
            }
            byte[] bArr2 = this._inputBuffer;
            int i11 = this._inputPtr;
            this._inputPtr = i11 + 1;
            int i12 = bArr2[i11] & 255;
            if (i12 == 45 || i12 == 43) {
                if (i10 >= cArr.length) {
                    cArr = this._textBuffer.finishCurrentSegment();
                    i10 = 0;
                }
                i10++;
                cArr[i10] = (char) i12;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                byte[] bArr3 = this._inputBuffer;
                int i13 = this._inputPtr;
                this._inputPtr = i13 + 1;
                i2 = bArr3[i13] & 255;
                cArr2 = cArr;
                i5 = 0;
            } else {
                i2 = i12;
                cArr2 = cArr;
                i5 = 0;
            }
            while (i2 >= 48 && i2 <= 57) {
                i5++;
                if (i10 >= cArr2.length) {
                    cArr2 = this._textBuffer.finishCurrentSegment();
                    i10 = 0;
                }
                int i14 = i10 + 1;
                cArr2[i10] = (char) i2;
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    i6 = i5;
                    z2 = true;
                    i = i14;
                    break;
                }
                byte[] bArr4 = this._inputBuffer;
                int i15 = this._inputPtr;
                this._inputPtr = i15 + 1;
                i2 = bArr4[i15] & 255;
                i10 = i14;
            }
            i6 = i5;
            i = i10;
            if (i6 == 0) {
                reportUnexpectedNumberChar(i2, "Exponent indicator not followed by a digit");
            }
        }
        if (!z2) {
            this._inputPtr--;
            if (this._parsingContext.inRoot()) {
                d(i2);
            }
        }
        this._textBuffer.setCurrentLength(i);
        return resetFloat(z, i3, i4, i6);
    }

    private final void d(int i) throws IOException {
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

    protected final String _parseName(int i) throws IOException {
        if (i != 34) {
            return _handleOddName(i);
        }
        if (this._inputPtr + 13 > this._inputEnd) {
            return slowParseName();
        }
        byte[] bArr = this._inputBuffer;
        int[] iArr = _icLatin1;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        int i3 = bArr[i2] & 255;
        if (iArr[i3] != 0) {
            return i3 == 34 ? "" : a(0, i3, 0);
        }
        int i4 = this._inputPtr;
        this._inputPtr = i4 + 1;
        int i5 = bArr[i4] & 255;
        if (iArr[i5] == 0) {
            int i6 = (i3 << 8) | i5;
            int i7 = this._inputPtr;
            this._inputPtr = i7 + 1;
            int i8 = bArr[i7] & 255;
            if (iArr[i8] == 0) {
                int i9 = (i6 << 8) | i8;
                int i10 = this._inputPtr;
                this._inputPtr = i10 + 1;
                int i11 = bArr[i10] & 255;
                if (iArr[i11] == 0) {
                    int i12 = (i9 << 8) | i11;
                    int i13 = this._inputPtr;
                    this._inputPtr = i13 + 1;
                    int i14 = bArr[i13] & 255;
                    if (iArr[i14] == 0) {
                        this.b = i12;
                        return parseMediumName(i14);
                    } else if (i14 == 34) {
                        return a(i12, 4);
                    } else {
                        return a(i12, i14, 4);
                    }
                } else if (i11 == 34) {
                    return a(i9, 3);
                } else {
                    return a(i9, i11, 3);
                }
            } else if (i8 == 34) {
                return a(i6, 2);
            } else {
                return a(i6, i8, 2);
            }
        } else if (i5 == 34) {
            return a(i3, 1);
        } else {
            return a(i3, i5, 1);
        }
    }

    protected final String parseMediumName(int i) throws IOException {
        byte[] bArr = this._inputBuffer;
        int[] iArr = _icLatin1;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        int i3 = bArr[i2] & 255;
        if (iArr[i3] == 0) {
            int i4 = (i << 8) | i3;
            int i5 = this._inputPtr;
            this._inputPtr = i5 + 1;
            int i6 = bArr[i5] & 255;
            if (iArr[i6] == 0) {
                int i7 = (i4 << 8) | i6;
                int i8 = this._inputPtr;
                this._inputPtr = i8 + 1;
                int i9 = bArr[i8] & 255;
                if (iArr[i9] == 0) {
                    int i10 = (i7 << 8) | i9;
                    int i11 = this._inputPtr;
                    this._inputPtr = i11 + 1;
                    int i12 = bArr[i11] & 255;
                    if (iArr[i12] == 0) {
                        return parseMediumName2(i12, i10);
                    }
                    if (i12 == 34) {
                        return b(this.b, i10, 4);
                    }
                    return a(this.b, i10, i12, 4);
                } else if (i9 == 34) {
                    return b(this.b, i7, 3);
                } else {
                    return a(this.b, i7, i9, 3);
                }
            } else if (i6 == 34) {
                return b(this.b, i4, 2);
            } else {
                return a(this.b, i4, i6, 2);
            }
        } else if (i3 == 34) {
            return b(this.b, i, 1);
        } else {
            return a(this.b, i, i3, 1);
        }
    }

    protected final String parseMediumName2(int i, int i2) throws IOException {
        byte[] bArr = this._inputBuffer;
        int[] iArr = _icLatin1;
        int i3 = this._inputPtr;
        this._inputPtr = i3 + 1;
        int i4 = bArr[i3] & 255;
        if (iArr[i4] == 0) {
            int i5 = (i << 8) | i4;
            int i6 = this._inputPtr;
            this._inputPtr = i6 + 1;
            int i7 = bArr[i6] & 255;
            if (iArr[i7] == 0) {
                int i8 = (i5 << 8) | i7;
                int i9 = this._inputPtr;
                this._inputPtr = i9 + 1;
                int i10 = bArr[i9] & 255;
                if (iArr[i10] == 0) {
                    int i11 = (i8 << 8) | i10;
                    int i12 = this._inputPtr;
                    this._inputPtr = i12 + 1;
                    int i13 = bArr[i12] & 255;
                    if (iArr[i13] == 0) {
                        return parseLongName(i13, i2, i11);
                    }
                    if (i13 == 34) {
                        return b(this.b, i2, i11, 4);
                    }
                    return a(this.b, i2, i11, i13, 4);
                } else if (i10 == 34) {
                    return b(this.b, i2, i8, 3);
                } else {
                    return a(this.b, i2, i8, i10, 3);
                }
            } else if (i7 == 34) {
                return b(this.b, i2, i5, 2);
            } else {
                return a(this.b, i2, i5, i7, 2);
            }
        } else if (i4 == 34) {
            return b(this.b, i2, i, 1);
        } else {
            return a(this.b, i2, i, i4, 1);
        }
    }

    protected final String parseLongName(int i, int i2, int i3) throws IOException {
        int[] iArr = this._quadBuffer;
        iArr[0] = this.b;
        iArr[1] = i2;
        iArr[2] = i3;
        byte[] bArr = this._inputBuffer;
        int[] iArr2 = _icLatin1;
        int i4 = i;
        int i5 = 3;
        while (this._inputPtr + 4 <= this._inputEnd) {
            int i6 = this._inputPtr;
            this._inputPtr = i6 + 1;
            int i7 = bArr[i6] & 255;
            if (iArr2[i7] == 0) {
                int i8 = i7 | (i4 << 8);
                int i9 = this._inputPtr;
                this._inputPtr = i9 + 1;
                int i10 = bArr[i9] & 255;
                if (iArr2[i10] == 0) {
                    int i11 = (i8 << 8) | i10;
                    int i12 = this._inputPtr;
                    this._inputPtr = i12 + 1;
                    int i13 = bArr[i12] & 255;
                    if (iArr2[i13] == 0) {
                        int i14 = (i11 << 8) | i13;
                        int i15 = this._inputPtr;
                        this._inputPtr = i15 + 1;
                        i4 = bArr[i15] & 255;
                        if (iArr2[i4] == 0) {
                            int[] iArr3 = this._quadBuffer;
                            if (i5 >= iArr3.length) {
                                this._quadBuffer = growArrayBy(iArr3, i5);
                            }
                            i5++;
                            this._quadBuffer[i5] = i14;
                        } else if (i4 == 34) {
                            return a(this._quadBuffer, i5, i14, 4);
                        } else {
                            return parseEscapedName(this._quadBuffer, i5, i14, i4, 4);
                        }
                    } else if (i13 == 34) {
                        return a(this._quadBuffer, i5, i11, 3);
                    } else {
                        return parseEscapedName(this._quadBuffer, i5, i11, i13, 3);
                    }
                } else if (i10 == 34) {
                    return a(this._quadBuffer, i5, i8, 2);
                } else {
                    return parseEscapedName(this._quadBuffer, i5, i8, i10, 2);
                }
            } else if (i7 == 34) {
                return a(this._quadBuffer, i5, i4, 1);
            } else {
                return parseEscapedName(this._quadBuffer, i5, i4, i7, 1);
            }
        }
        return parseEscapedName(this._quadBuffer, i5, 0, i4, 0);
    }

    protected String slowParseName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(": was expecting closing '\"' for name", JsonToken.FIELD_NAME);
        }
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        int i2 = bArr[i] & 255;
        return i2 == 34 ? "" : parseEscapedName(this._quadBuffer, 0, 0, i2, 0);
    }

    private final String a(int i, int i2, int i3) throws IOException {
        return parseEscapedName(this._quadBuffer, 0, i, i2, i3);
    }

    private final String a(int i, int i2, int i3, int i4) throws IOException {
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        return parseEscapedName(iArr, 1, i2, i3, i4);
    }

    private final String a(int i, int i2, int i3, int i4, int i5) throws IOException {
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        iArr[1] = i2;
        return parseEscapedName(iArr, 2, i3, i4, i5);
    }

    protected final String parseEscapedName(int[] iArr, int i, int i2, int i3, int i4) throws IOException {
        int[] iArr2 = _icLatin1;
        while (true) {
            if (iArr2[i3] != 0) {
                if (i3 == 34) {
                    break;
                }
                if (i3 != 92) {
                    _throwUnquotedSpace(i3, "name");
                } else {
                    i3 = _decodeEscaped();
                }
                if (i3 > 127) {
                    if (i4 >= 4) {
                        if (i >= iArr.length) {
                            iArr = growArrayBy(iArr, iArr.length);
                            this._quadBuffer = iArr;
                        }
                        i++;
                        iArr[i] = i2;
                        i2 = 0;
                        i4 = 0;
                    }
                    if (i3 < 2048) {
                        i2 = (i2 << 8) | (i3 >> 6) | 192;
                        i4++;
                    } else {
                        int i5 = (i2 << 8) | (i3 >> 12) | 224;
                        int i6 = i4 + 1;
                        if (i6 >= 4) {
                            if (i >= iArr.length) {
                                iArr = growArrayBy(iArr, iArr.length);
                                this._quadBuffer = iArr;
                            }
                            i++;
                            iArr[i] = i5;
                            i5 = 0;
                            i6 = 0;
                        }
                        i2 = (i5 << 8) | ((i3 >> 6) & 63) | 128;
                        i4 = i6 + 1;
                    }
                    i3 = (i3 & 63) | 128;
                }
            }
            if (i4 < 4) {
                i4++;
                i2 = (i2 << 8) | i3;
            } else {
                if (i >= iArr.length) {
                    iArr = growArrayBy(iArr, iArr.length);
                    this._quadBuffer = iArr;
                }
                i++;
                iArr[i] = i2;
                i2 = i3;
                i4 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            byte[] bArr = this._inputBuffer;
            int i7 = this._inputPtr;
            this._inputPtr = i7 + 1;
            i3 = bArr[i7] & 255;
        }
        if (i4 > 0) {
            if (i >= iArr.length) {
                iArr = growArrayBy(iArr, iArr.length);
                this._quadBuffer = iArr;
            }
            i++;
            iArr[i] = b(i2, i4);
        }
        String findName = this._symbols.findName(iArr, i);
        return findName == null ? a(iArr, i, i4) : findName;
    }

    protected String _handleOddName(int i) throws IOException {
        if (i == 39 && isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return _parseAposName();
        }
        if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            _reportUnexpectedChar((char) _decodeCharForError(i), "was expecting double-quote to start field name");
        }
        int[] inputCodeUtf8JsNames = CharTypes.getInputCodeUtf8JsNames();
        if (inputCodeUtf8JsNames[i] != 0) {
            _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int[] iArr = this._quadBuffer;
        int i2 = 0;
        int i3 = i;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i2 < 4) {
                i2++;
                i5 = (i5 << 8) | i3;
            } else {
                if (i4 >= iArr.length) {
                    iArr = growArrayBy(iArr, iArr.length);
                    this._quadBuffer = iArr;
                }
                i4++;
                iArr[i4] = i5;
                i5 = i3;
                i2 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            i3 = this._inputBuffer[this._inputPtr] & 255;
            if (inputCodeUtf8JsNames[i3] != 0) {
                break;
            }
            this._inputPtr++;
        }
        if (i2 > 0) {
            if (i4 >= iArr.length) {
                int[] growArrayBy = growArrayBy(iArr, iArr.length);
                this._quadBuffer = growArrayBy;
                iArr = growArrayBy;
            }
            i4++;
            iArr[i4] = i5;
        }
        String findName = this._symbols.findName(iArr, i4);
        return findName == null ? a(iArr, i4, i2) : findName;
    }

    protected String _parseAposName() throws IOException {
        int i;
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(": was expecting closing ''' for field name", JsonToken.FIELD_NAME);
        }
        byte[] bArr = this._inputBuffer;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        int i3 = bArr[i2] & 255;
        if (i3 == 39) {
            return "";
        }
        int[] iArr = this._quadBuffer;
        int[] iArr2 = _icLatin1;
        int[] iArr3 = iArr;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i3 != 39) {
            if (!(iArr2[i3] == 0 || i3 == 34)) {
                if (i3 != 92) {
                    _throwUnquotedSpace(i3, "name");
                } else {
                    i3 = _decodeEscaped();
                }
                if (i3 > 127) {
                    if (i4 >= 4) {
                        if (i5 >= iArr3.length) {
                            iArr3 = growArrayBy(iArr3, iArr3.length);
                            this._quadBuffer = iArr3;
                        }
                        i5++;
                        iArr3[i5] = i6;
                        i4 = 0;
                        i6 = 0;
                    }
                    if (i3 < 2048) {
                        i6 = (i6 << 8) | (i3 >> 6) | 192;
                        i4++;
                    } else {
                        int i7 = (i6 << 8) | (i3 >> 12) | 224;
                        int i8 = i4 + 1;
                        if (i8 >= 4) {
                            if (i5 >= iArr3.length) {
                                int[] growArrayBy = growArrayBy(iArr3, iArr3.length);
                                this._quadBuffer = growArrayBy;
                                iArr3 = growArrayBy;
                            }
                            i5++;
                            iArr3[i5] = i7;
                            i8 = 0;
                            i7 = 0;
                        }
                        i6 = (i7 << 8) | ((i3 >> 6) & 63) | 128;
                        i4 = i8 + 1;
                    }
                    i3 = (i3 & 63) | 128;
                }
            }
            if (i4 < 4) {
                i4++;
                i6 = i3 | (i6 << 8);
            } else {
                if (i5 >= iArr3.length) {
                    iArr3 = growArrayBy(iArr3, iArr3.length);
                    this._quadBuffer = iArr3;
                }
                i5++;
                iArr3[i5] = i6;
                i6 = i3;
                i4 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            byte[] bArr2 = this._inputBuffer;
            int i9 = this._inputPtr;
            this._inputPtr = i9 + 1;
            i3 = bArr2[i9] & 255;
        }
        if (i4 > 0) {
            if (i5 >= iArr3.length) {
                int[] growArrayBy2 = growArrayBy(iArr3, iArr3.length);
                this._quadBuffer = growArrayBy2;
                iArr3 = growArrayBy2;
            }
            i = i5 + 1;
            iArr3[i5] = b(i6, i4);
        } else {
            i = i5;
        }
        String findName = this._symbols.findName(iArr3, i);
        return findName == null ? a(iArr3, i, i4) : findName;
    }

    private final String a(int i, int i2) throws JsonParseException {
        int b = b(i, i2);
        String findName = this._symbols.findName(b);
        if (findName != null) {
            return findName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = b;
        return a(iArr, 1, i2);
    }

    private final String b(int i, int i2, int i3) throws JsonParseException {
        int b = b(i2, i3);
        String findName = this._symbols.findName(i, b);
        if (findName != null) {
            return findName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        iArr[1] = b;
        return a(iArr, 2, i3);
    }

    private final String b(int i, int i2, int i3, int i4) throws JsonParseException {
        int b = b(i3, i4);
        String findName = this._symbols.findName(i, i2, b);
        if (findName != null) {
            return findName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = b(b, i4);
        return a(iArr, 3, i4);
    }

    private final String a(int[] iArr, int i, int i2, int i3) throws JsonParseException {
        if (i >= iArr.length) {
            iArr = growArrayBy(iArr, iArr.length);
            this._quadBuffer = iArr;
        }
        int i4 = i + 1;
        iArr[i] = b(i2, i3);
        String findName = this._symbols.findName(iArr, i4);
        return findName == null ? a(iArr, i4, i3) : findName;
    }

    private final String a(int[] iArr, int i, int i2) throws JsonParseException {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = ((i << 2) - 4) + i2;
        if (i2 < 4) {
            int i9 = i - 1;
            i3 = iArr[i9];
            iArr[i9] = i3 << ((4 - i2) << 3);
        } else {
            i3 = 0;
        }
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int i10 = 0;
        int i11 = 0;
        while (i10 < i8) {
            int i12 = (iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3)) & 255;
            i10++;
            if (i12 > 127) {
                if ((i12 & 224) == 192) {
                    i4 = i12 & 31;
                    i5 = 1;
                } else if ((i12 & PsExtractor.VIDEO_STREAM_MASK) == 224) {
                    i4 = i12 & 15;
                    i5 = 2;
                } else if ((i12 & 248) == 240) {
                    i4 = i12 & 7;
                    i5 = 3;
                } else {
                    _reportInvalidInitial(i12);
                    i5 = 1;
                    i4 = 1;
                }
                if (i10 + i5 > i8) {
                    _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
                }
                int i13 = iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3);
                i10++;
                if ((i13 & 192) != 128) {
                    _reportInvalidOther(i13);
                }
                int i14 = (i13 & 63) | (i4 << 6);
                if (i5 > 1) {
                    int i15 = iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3);
                    i10++;
                    if ((i15 & 192) != 128) {
                        _reportInvalidOther(i15);
                    }
                    i7 = (i15 & 63) | (i14 << 6);
                    i6 = 2;
                    if (i5 > 2) {
                        int i16 = iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3);
                        i10++;
                        if ((i16 & 192) != 128) {
                            _reportInvalidOther(i16 & 255);
                        }
                        i7 = (i7 << 6) | (i16 & 63);
                        i6 = 2;
                    }
                } else {
                    i7 = i14;
                    i6 = 2;
                }
                if (i5 > i6) {
                    int i17 = i7 - 65536;
                    if (i11 >= emptyAndGetCurrentSegment.length) {
                        emptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
                    }
                    i11++;
                    emptyAndGetCurrentSegment[i11] = (char) ((i17 >> 10) + GeneratorBase.SURR1_FIRST);
                    i12 = (i17 & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | 56320;
                } else {
                    i12 = i7;
                }
            }
            if (i11 >= emptyAndGetCurrentSegment.length) {
                emptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
            }
            i11++;
            emptyAndGetCurrentSegment[i11] = (char) i12;
        }
        String str = new String(emptyAndGetCurrentSegment, 0, i11);
        if (i2 < 4) {
            iArr[i - 1] = i3;
        }
        return this._symbols.addName(str, iArr, i);
    }

    protected void _loadMoreGuaranteed() throws IOException {
        if (!_loadMore()) {
            _reportInvalidEOF();
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _finishString() throws IOException {
        int i = this._inputPtr;
        if (i >= this._inputEnd) {
            _loadMoreGuaranteed();
            i = this._inputPtr;
        }
        int i2 = 0;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = a;
        int min = Math.min(this._inputEnd, emptyAndGetCurrentSegment.length + i);
        byte[] bArr = this._inputBuffer;
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
                return;
            }
        }
        this._inputPtr = i;
        a(emptyAndGetCurrentSegment, i2);
    }

    protected String _finishAndReturnString() throws IOException {
        int i = this._inputPtr;
        if (i >= this._inputEnd) {
            _loadMoreGuaranteed();
            i = this._inputPtr;
        }
        int i2 = 0;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = a;
        int min = Math.min(this._inputEnd, emptyAndGetCurrentSegment.length + i);
        byte[] bArr = this._inputBuffer;
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
                return this._textBuffer.setCurrentAndReturn(i2);
            }
        }
        this._inputPtr = i;
        a(emptyAndGetCurrentSegment, i2);
        return this._textBuffer.contentsAsString();
    }

    private final void a(char[] cArr, int i) throws IOException {
        int[] iArr = a;
        byte[] bArr = this._inputBuffer;
        while (true) {
            int i2 = this._inputPtr;
            if (i2 >= this._inputEnd) {
                _loadMoreGuaranteed();
                i2 = this._inputPtr;
            }
            if (i >= cArr.length) {
                cArr = this._textBuffer.finishCurrentSegment();
                i = 0;
            }
            int min = Math.min(this._inputEnd, (cArr.length - i) + i2);
            while (true) {
                if (i2 < min) {
                    int i3 = i2 + 1;
                    int i4 = bArr[i2] & 255;
                    if (iArr[i4] != 0) {
                        this._inputPtr = i3;
                        if (i4 == 34) {
                            this._textBuffer.setCurrentLength(i);
                            return;
                        }
                        switch (iArr[i4]) {
                            case 1:
                                i4 = _decodeEscaped();
                                break;
                            case 2:
                                i4 = e(i4);
                                break;
                            case 3:
                                if (this._inputEnd - this._inputPtr < 2) {
                                    i4 = f(i4);
                                    break;
                                } else {
                                    i4 = g(i4);
                                    break;
                                }
                            case 4:
                                int h = h(i4);
                                i++;
                                cArr[i] = (char) (55296 | (h >> 10));
                                if (i >= cArr.length) {
                                    cArr = this._textBuffer.finishCurrentSegment();
                                    i = 0;
                                }
                                i4 = (h & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | 56320;
                                break;
                            default:
                                if (i4 >= 32) {
                                    _reportInvalidChar(i4);
                                    break;
                                } else {
                                    _throwUnquotedSpace(i4, "string value");
                                    break;
                                }
                        }
                        if (i >= cArr.length) {
                            cArr = this._textBuffer.finishCurrentSegment();
                            i = 0;
                        }
                        i++;
                        cArr[i] = (char) i4;
                    } else {
                        i++;
                        cArr[i] = (char) i4;
                        i2 = i3;
                    }
                } else {
                    this._inputPtr = i2;
                }
            }
        }
    }

    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int[] iArr = a;
        byte[] bArr = this._inputBuffer;
        while (true) {
            int i = this._inputPtr;
            int i2 = this._inputEnd;
            if (i >= i2) {
                _loadMoreGuaranteed();
                i = this._inputPtr;
                i2 = this._inputEnd;
            }
            while (true) {
                if (i < i2) {
                    int i3 = i + 1;
                    int i4 = bArr[i] & 255;
                    if (iArr[i4] != 0) {
                        this._inputPtr = i3;
                        if (i4 != 34) {
                            switch (iArr[i4]) {
                                case 1:
                                    _decodeEscaped();
                                    continue;
                                case 2:
                                    l();
                                    continue;
                                case 3:
                                    m();
                                    continue;
                                case 4:
                                    i(i4);
                                    continue;
                                default:
                                    if (i4 >= 32) {
                                        _reportInvalidChar(i4);
                                        break;
                                    } else {
                                        _throwUnquotedSpace(i4, "string value");
                                        continue;
                                    }
                            }
                        } else {
                            return;
                        }
                    } else {
                        i = i3;
                    }
                } else {
                    this._inputPtr = i;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0043, code lost:
        if (r2._parsingContext.inArray() == false) goto L_0x00a1;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.core.JsonToken _handleUnexpectedValue(int r3) throws java.io.IOException {
        /*
            r2 = this;
            r0 = 39
            if (r3 == r0) goto L_0x0094
            r0 = 73
            r1 = 1
            if (r3 == r0) goto L_0x0078
            r0 = 78
            if (r3 == r0) goto L_0x005c
            r0 = 93
            if (r3 == r0) goto L_0x003d
            r0 = 125(0x7d, float:1.75E-43)
            if (r3 == r0) goto L_0x0056
            switch(r3) {
                case 43: goto L_0x001a;
                case 44: goto L_0x0046;
                default: goto L_0x0018;
            }
        L_0x0018:
            goto L_0x00a1
        L_0x001a:
            int r3 = r2._inputPtr
            int r0 = r2._inputEnd
            if (r3 < r0) goto L_0x002b
            boolean r3 = r2._loadMore()
            if (r3 != 0) goto L_0x002b
            com.fasterxml.jackson.core.JsonToken r3 = com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT
            r2._reportInvalidEOFInValue(r3)
        L_0x002b:
            byte[] r3 = r2._inputBuffer
            int r0 = r2._inputPtr
            int r1 = r0 + 1
            r2._inputPtr = r1
            byte r3 = r3[r0]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r0 = 0
            com.fasterxml.jackson.core.JsonToken r3 = r2._handleInvalidNumberStart(r3, r0)
            return r3
        L_0x003d:
            com.fasterxml.jackson.core.json.JsonReadContext r0 = r2._parsingContext
            boolean r0 = r0.inArray()
            if (r0 != 0) goto L_0x0046
            goto L_0x00a1
        L_0x0046:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0056
            int r3 = r2._inputPtr
            int r3 = r3 - r1
            r2._inputPtr = r3
            com.fasterxml.jackson.core.JsonToken r3 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL
            return r3
        L_0x0056:
            java.lang.String r0 = "expected a value"
            r2._reportUnexpectedChar(r3, r0)
            goto L_0x0094
        L_0x005c:
            java.lang.String r0 = "NaN"
            r2._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0072
            java.lang.String r3 = "NaN"
            r0 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            com.fasterxml.jackson.core.JsonToken r3 = r2.resetAsNaN(r3, r0)
            return r3
        L_0x0072:
            java.lang.String r0 = "Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r2._reportError(r0)
            goto L_0x00a1
        L_0x0078:
            java.lang.String r0 = "Infinity"
            r2._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x008e
            java.lang.String r3 = "Infinity"
            r0 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            com.fasterxml.jackson.core.JsonToken r3 = r2.resetAsNaN(r3, r0)
            return r3
        L_0x008e:
            java.lang.String r0 = "Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r2._reportError(r0)
            goto L_0x00a1
        L_0x0094:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x00a1
            com.fasterxml.jackson.core.JsonToken r3 = r2._handleApos()
            return r3
        L_0x00a1:
            boolean r0 = java.lang.Character.isJavaIdentifierStart(r3)
            if (r0 == 0) goto L_0x00be
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = ""
            r0.append(r1)
            char r1 = (char) r3
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "('true', 'false' or 'null')"
            r2._reportInvalidToken(r0, r1)
        L_0x00be:
            java.lang.String r0 = "expected a valid value (number, String, array, object, 'true', 'false' or 'null')"
            r2._reportUnexpectedChar(r3, r0)
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8StreamJsonParser._handleUnexpectedValue(int):com.fasterxml.jackson.core.JsonToken");
    }

    protected JsonToken _handleApos() throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = a;
        byte[] bArr = this._inputBuffer;
        int i = 0;
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                _loadMoreGuaranteed();
            }
            if (i >= emptyAndGetCurrentSegment.length) {
                emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                i = 0;
            }
            int i2 = this._inputEnd;
            int length = this._inputPtr + (emptyAndGetCurrentSegment.length - i);
            if (length < i2) {
                i2 = length;
            }
            while (this._inputPtr < i2) {
                int i3 = this._inputPtr;
                this._inputPtr = i3 + 1;
                int i4 = bArr[i3] & 255;
                if (i4 != 39 && iArr[i4] == 0) {
                    i++;
                    emptyAndGetCurrentSegment[i] = (char) i4;
                } else if (i4 == 39) {
                    this._textBuffer.setCurrentLength(i);
                    return JsonToken.VALUE_STRING;
                } else {
                    switch (iArr[i4]) {
                        case 1:
                            i4 = _decodeEscaped();
                            break;
                        case 2:
                            i4 = e(i4);
                            break;
                        case 3:
                            if (this._inputEnd - this._inputPtr < 2) {
                                i4 = f(i4);
                                break;
                            } else {
                                i4 = g(i4);
                                break;
                            }
                        case 4:
                            int h = h(i4);
                            i++;
                            emptyAndGetCurrentSegment[i] = (char) (55296 | (h >> 10));
                            if (i >= emptyAndGetCurrentSegment.length) {
                                emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                                i = 0;
                            }
                            i4 = 56320 | (h & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES);
                            break;
                        default:
                            if (i4 < 32) {
                                _throwUnquotedSpace(i4, "string value");
                            }
                            _reportInvalidChar(i4);
                            break;
                    }
                    if (i >= emptyAndGetCurrentSegment.length) {
                        emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                        i = 0;
                    }
                    i++;
                    emptyAndGetCurrentSegment[i] = (char) i4;
                }
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, byte], vars: [r3v0 ??, r3v1 ??, r3v6 ??, r3v2 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
        */
    protected com.fasterxml.jackson.core.JsonToken _handleInvalidNumberStart(
    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [int, byte], vars: [r3v0 ??, r3v1 ??, r3v6 ??, r3v2 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r3v0 ??
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

    protected final void _matchTrue() throws IOException {
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
                        return;
                    }
                }
            }
        }
        a("true", 1);
    }

    protected final void _matchFalse() throws IOException {
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
                            return;
                        }
                    }
                }
            }
        }
        a("false", 1);
    }

    protected final void _matchNull() throws IOException {
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
                        return;
                    }
                }
            }
        }
        a("null", 1);
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
        int i2 = this._inputBuffer[this._inputPtr] & 255;
        if (i2 >= 48 && i2 != 93 && i2 != 125) {
            a(str, i, i2);
        }
    }

    private final void a(String str, int i) throws IOException {
        int i2;
        int length = str.length();
        do {
            if ((this._inputPtr >= this._inputEnd && !_loadMore()) || this._inputBuffer[this._inputPtr] != str.charAt(i)) {
                _reportInvalidToken(str.substring(0, i));
            }
            this._inputPtr++;
            i++;
        } while (i < length);
        if ((this._inputPtr < this._inputEnd || _loadMore()) && (i2 = this._inputBuffer[this._inputPtr] & 255) >= 48 && i2 != 93 && i2 != 125) {
            a(str, i, i2);
        }
    }

    private final void a(String str, int i, int i2) throws IOException {
        if (Character.isJavaIdentifierPart((char) _decodeCharForError(i2))) {
            _reportInvalidToken(str.substring(0, i));
        }
    }

    private final int c() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] bArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            int i2 = bArr[i] & 255;
            if (i2 > 32) {
                if (i2 != 47 && i2 != 35) {
                    return i2;
                }
                this._inputPtr--;
                return d();
            } else if (i2 != 32) {
                if (i2 == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i2 == 13) {
                    _skipCR();
                } else if (i2 != 9) {
                    _throwInvalidSpace(i2);
                }
            }
        }
        return d();
    }

    private final int d() throws IOException {
        int i;
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                byte[] bArr = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                i = bArr[i2] & 255;
                if (i > 32) {
                    if (i == 47) {
                        h();
                    } else if (i != 35 || !j()) {
                        break;
                    }
                } else if (i != 32) {
                    if (i == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (i == 13) {
                        _skipCR();
                    } else if (i != 9) {
                        _throwInvalidSpace(i);
                    }
                }
            } else {
                throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
            }
        }
        return i;
    }

    private final int e() throws IOException {
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            return _eofAsNextChar();
        }
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        int i2 = bArr[i] & 255;
        if (i2 <= 32) {
            if (i2 != 32) {
                if (i2 == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i2 == 13) {
                    _skipCR();
                } else if (i2 != 9) {
                    _throwInvalidSpace(i2);
                }
            }
            while (this._inputPtr < this._inputEnd) {
                byte[] bArr2 = this._inputBuffer;
                int i3 = this._inputPtr;
                this._inputPtr = i3 + 1;
                int i4 = bArr2[i3] & 255;
                if (i4 > 32) {
                    if (i4 != 47 && i4 != 35) {
                        return i4;
                    }
                    this._inputPtr--;
                    return f();
                } else if (i4 != 32) {
                    if (i4 == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (i4 == 13) {
                        _skipCR();
                    } else if (i4 != 9) {
                        _throwInvalidSpace(i4);
                    }
                }
            }
            return f();
        } else if (i2 != 47 && i2 != 35) {
            return i2;
        } else {
            this._inputPtr--;
            return f();
        }
    }

    private final int f() throws IOException {
        int i;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                return _eofAsNextChar();
            }
            byte[] bArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            i = bArr[i2] & 255;
            if (i > 32) {
                if (i == 47) {
                    h();
                } else if (i != 35 || !j()) {
                    break;
                }
            } else if (i != 32) {
                if (i == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i == 13) {
                    _skipCR();
                } else if (i != 9) {
                    _throwInvalidSpace(i);
                }
            }
        }
        return i;
    }

    private final int g() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return a(false);
        }
        byte b = this._inputBuffer[this._inputPtr];
        if (b == 58) {
            byte[] bArr = this._inputBuffer;
            int i = this._inputPtr + 1;
            this._inputPtr = i;
            byte b2 = bArr[i];
            if (b2 <= 32) {
                if (b2 == 32 || b2 == 9) {
                    byte[] bArr2 = this._inputBuffer;
                    int i2 = this._inputPtr + 1;
                    this._inputPtr = i2;
                    byte b3 = bArr2[i2];
                    if (b3 > 32) {
                        if (b3 == 47 || b3 == 35) {
                            return a(true);
                        }
                        this._inputPtr++;
                        return b3;
                    }
                }
                return a(true);
            } else if (b2 == 47 || b2 == 35) {
                return a(true);
            } else {
                this._inputPtr++;
                return b2;
            }
        } else {
            if (b == 32 || b == 9) {
                byte[] bArr3 = this._inputBuffer;
                int i3 = this._inputPtr + 1;
                this._inputPtr = i3;
                b = bArr3[i3];
            }
            if (b != 58) {
                return a(false);
            }
            byte[] bArr4 = this._inputBuffer;
            int i4 = this._inputPtr + 1;
            this._inputPtr = i4;
            byte b4 = bArr4[i4];
            if (b4 <= 32) {
                if (b4 == 32 || b4 == 9) {
                    byte[] bArr5 = this._inputBuffer;
                    int i5 = this._inputPtr + 1;
                    this._inputPtr = i5;
                    byte b5 = bArr5[i5];
                    if (b5 > 32) {
                        if (b5 == 47 || b5 == 35) {
                            return a(true);
                        }
                        this._inputPtr++;
                        return b5;
                    }
                }
                return a(true);
            } else if (b4 == 47 || b4 == 35) {
                return a(true);
            } else {
                this._inputPtr++;
                return b4;
            }
        }
    }

    private final int a(boolean z) throws IOException {
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                byte[] bArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                int i2 = bArr[i] & 255;
                if (i2 > 32) {
                    if (i2 == 47) {
                        h();
                    } else if (i2 != 35 || !j()) {
                        if (z) {
                            return i2;
                        }
                        if (i2 != 58) {
                            _reportUnexpectedChar(i2, "was expecting a colon to separate field name and value");
                        }
                        z = true;
                    }
                } else if (i2 != 32) {
                    if (i2 == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (i2 == 13) {
                        _skipCR();
                    } else if (i2 != 9) {
                        _throwInvalidSpace(i2);
                    }
                }
            } else {
                _reportInvalidEOF(" within/between " + this._parsingContext.typeDesc() + " entries", null);
                return -1;
            }
        }
    }

    private final void h() throws IOException {
        if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(" in a comment", null);
        }
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        int i2 = bArr[i] & 255;
        if (i2 == 47) {
            k();
        } else if (i2 == 42) {
            i();
        } else {
            _reportUnexpectedChar(i2, "was expecting either '*' or '/' for a comment");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x004b, code lost:
        _reportInvalidEOF(" in a comment", null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0051, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void i() throws java.io.IOException {
        /*
            r4 = this;
            int[] r0 = com.fasterxml.jackson.core.io.CharTypes.getInputCodeComment()
        L_0x0004:
            int r1 = r4._inputPtr
            int r2 = r4._inputEnd
            if (r1 < r2) goto L_0x0010
            boolean r1 = r4._loadMore()
            if (r1 == 0) goto L_0x004b
        L_0x0010:
            byte[] r1 = r4._inputBuffer
            int r2 = r4._inputPtr
            int r3 = r2 + 1
            r4._inputPtr = r3
            byte r1 = r1[r2]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = r0[r1]
            if (r2 == 0) goto L_0x0004
            r3 = 10
            if (r2 == r3) goto L_0x0067
            r3 = 13
            if (r2 == r3) goto L_0x0063
            r3 = 42
            if (r2 == r3) goto L_0x003f
            switch(r2) {
                case 2: goto L_0x003b;
                case 3: goto L_0x0037;
                case 4: goto L_0x0033;
                default: goto L_0x002f;
            }
        L_0x002f:
            r4._reportInvalidChar(r1)
            goto L_0x0004
        L_0x0033:
            r4.i(r1)
            goto L_0x0004
        L_0x0037:
            r4.m()
            goto L_0x0004
        L_0x003b:
            r4.l()
            goto L_0x0004
        L_0x003f:
            int r1 = r4._inputPtr
            int r2 = r4._inputEnd
            if (r1 < r2) goto L_0x0052
            boolean r1 = r4._loadMore()
            if (r1 != 0) goto L_0x0052
        L_0x004b:
            java.lang.String r0 = " in a comment"
            r1 = 0
            r4._reportInvalidEOF(r0, r1)
            return
        L_0x0052:
            byte[] r1 = r4._inputBuffer
            int r2 = r4._inputPtr
            byte r1 = r1[r2]
            r2 = 47
            if (r1 != r2) goto L_0x0004
            int r0 = r4._inputPtr
            int r0 = r0 + 1
            r4._inputPtr = r0
            return
        L_0x0063:
            r4._skipCR()
            goto L_0x0004
        L_0x0067:
            int r1 = r4._currInputRow
            int r1 = r1 + 1
            r4._currInputRow = r1
            int r1 = r4._inputPtr
            r4._currInputRowStart = r1
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8StreamJsonParser.i():void");
    }

    private final boolean j() throws IOException {
        if (!isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        k();
        return true;
    }

    private final void k() throws IOException {
        int[] inputCodeComment = CharTypes.getInputCodeComment();
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                byte[] bArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                int i2 = bArr[i] & 255;
                int i3 = inputCodeComment[i2];
                if (i3 != 0) {
                    if (i3 == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                        return;
                    } else if (i3 == 13) {
                        _skipCR();
                        return;
                    } else if (i3 != 42) {
                        switch (i3) {
                            case 2:
                                l();
                                continue;
                            case 3:
                                m();
                                continue;
                            case 4:
                                i(i2);
                                continue;
                            default:
                                if (i3 < 0) {
                                    _reportInvalidChar(i2);
                                    break;
                                } else {
                                    continue;
                                }
                        }
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
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        byte b = bArr[i];
        if (b == 34 || b == 47 || b == 92) {
            return (char) b;
        }
        if (b == 98) {
            return '\b';
        }
        if (b == 102) {
            return '\f';
        }
        if (b == 110) {
            return '\n';
        }
        if (b == 114) {
            return '\r';
        }
        switch (b) {
            case 116:
                return '\t';
            case 117:
                int i2 = 0;
                for (int i3 = 0; i3 < 4; i3++) {
                    if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                        _reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
                    }
                    byte[] bArr2 = this._inputBuffer;
                    int i4 = this._inputPtr;
                    this._inputPtr = i4 + 1;
                    byte b2 = bArr2[i4];
                    int charToHex = CharTypes.charToHex(b2);
                    if (charToHex < 0) {
                        _reportUnexpectedChar(b2, "expected a hex-digit for character escape sequence");
                    }
                    i2 = (i2 << 4) | charToHex;
                }
                return (char) i2;
            default:
                return _handleUnrecognizedCharacterEscape((char) _decodeCharForError(b));
        }
    }

    protected int _decodeCharForError(int i) throws IOException {
        char c;
        int i2 = i & 255;
        if (i2 <= 127) {
            return i2;
        }
        if ((i2 & 224) == 192) {
            i2 &= 31;
            c = 1;
        } else if ((i2 & PsExtractor.VIDEO_STREAM_MASK) == 224) {
            i2 &= 15;
            c = 2;
        } else if ((i2 & 248) == 240) {
            i2 &= 7;
            c = 3;
        } else {
            _reportInvalidInitial(i2 & 255);
            c = 1;
        }
        int n = n();
        if ((n & 192) != 128) {
            _reportInvalidOther(n & 255);
        }
        int i3 = (i2 << 6) | (n & 63);
        if (c <= 1) {
            return i3;
        }
        int n2 = n();
        if ((n2 & 192) != 128) {
            _reportInvalidOther(n2 & 255);
        }
        int i4 = (i3 << 6) | (n2 & 63);
        if (c <= 2) {
            return i4;
        }
        int n3 = n();
        if ((n3 & 192) != 128) {
            _reportInvalidOther(n3 & 255);
        }
        return (i4 << 6) | (n3 & 63);
    }

    private final int e(int i) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr = this._inputBuffer;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        byte b = bArr[i2];
        if ((b & 192) != 128) {
            _reportInvalidOther(b & 255, this._inputPtr);
        }
        return ((i & 31) << 6) | (b & Utf8.REPLACEMENT_BYTE);
    }

    private final int f(int i) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        int i2 = i & 15;
        byte[] bArr = this._inputBuffer;
        int i3 = this._inputPtr;
        this._inputPtr = i3 + 1;
        byte b = bArr[i3];
        if ((b & 192) != 128) {
            _reportInvalidOther(b & 255, this._inputPtr);
        }
        int i4 = (i2 << 6) | (b & Utf8.REPLACEMENT_BYTE);
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr2 = this._inputBuffer;
        int i5 = this._inputPtr;
        this._inputPtr = i5 + 1;
        byte b2 = bArr2[i5];
        if ((b2 & 192) != 128) {
            _reportInvalidOther(b2 & 255, this._inputPtr);
        }
        return (i4 << 6) | (b2 & Utf8.REPLACEMENT_BYTE);
    }

    private final int g(int i) throws IOException {
        int i2 = i & 15;
        byte[] bArr = this._inputBuffer;
        int i3 = this._inputPtr;
        this._inputPtr = i3 + 1;
        byte b = bArr[i3];
        if ((b & 192) != 128) {
            _reportInvalidOther(b & 255, this._inputPtr);
        }
        int i4 = (i2 << 6) | (b & Utf8.REPLACEMENT_BYTE);
        byte[] bArr2 = this._inputBuffer;
        int i5 = this._inputPtr;
        this._inputPtr = i5 + 1;
        byte b2 = bArr2[i5];
        if ((b2 & 192) != 128) {
            _reportInvalidOther(b2 & 255, this._inputPtr);
        }
        return (i4 << 6) | (b2 & Utf8.REPLACEMENT_BYTE);
    }

    private final int h(int i) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr = this._inputBuffer;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        byte b = bArr[i2];
        if ((b & 192) != 128) {
            _reportInvalidOther(b & 255, this._inputPtr);
        }
        int i3 = ((i & 7) << 6) | (b & Utf8.REPLACEMENT_BYTE);
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr2 = this._inputBuffer;
        int i4 = this._inputPtr;
        this._inputPtr = i4 + 1;
        byte b2 = bArr2[i4];
        if ((b2 & 192) != 128) {
            _reportInvalidOther(b2 & 255, this._inputPtr);
        }
        int i5 = (i3 << 6) | (b2 & Utf8.REPLACEMENT_BYTE);
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr3 = this._inputBuffer;
        int i6 = this._inputPtr;
        this._inputPtr = i6 + 1;
        byte b3 = bArr3[i6];
        if ((b3 & 192) != 128) {
            _reportInvalidOther(b3 & 255, this._inputPtr);
        }
        return ((i5 << 6) | (b3 & Utf8.REPLACEMENT_BYTE)) - 65536;
    }

    private final void l() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        byte b = bArr[i];
        if ((b & 192) != 128) {
            _reportInvalidOther(b & 255, this._inputPtr);
        }
    }

    private final void m() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        byte b = bArr[i];
        if ((b & 192) != 128) {
            _reportInvalidOther(b & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr2 = this._inputBuffer;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        byte b2 = bArr2[i2];
        if ((b2 & 192) != 128) {
            _reportInvalidOther(b2 & 255, this._inputPtr);
        }
    }

    private final void i(int i) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr = this._inputBuffer;
        int i2 = this._inputPtr;
        this._inputPtr = i2 + 1;
        byte b = bArr[i2];
        if ((b & 192) != 128) {
            _reportInvalidOther(b & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr2 = this._inputBuffer;
        int i3 = this._inputPtr;
        this._inputPtr = i3 + 1;
        byte b2 = bArr2[i3];
        if ((b2 & 192) != 128) {
            _reportInvalidOther(b2 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr3 = this._inputBuffer;
        int i4 = this._inputPtr;
        this._inputPtr = i4 + 1;
        byte b3 = bArr3[i4];
        if ((b3 & 192) != 128) {
            _reportInvalidOther(b3 & 255, this._inputPtr);
        }
    }

    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || _loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
            this._inputPtr++;
        }
        this._currInputRow++;
        this._currInputRowStart = this._inputPtr;
    }

    private int n() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            _loadMoreGuaranteed();
        }
        byte[] bArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        return bArr[i] & 255;
    }

    protected void _reportInvalidToken(String str, int i) throws IOException {
        this._inputPtr = i;
        _reportInvalidToken(str, "'null', 'true', 'false' or NaN");
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
            byte[] bArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char _decodeCharForError = (char) _decodeCharForError(bArr[i]);
            if (Character.isJavaIdentifierPart(_decodeCharForError)) {
                sb.append(_decodeCharForError);
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

    protected void _reportInvalidChar(int i) throws JsonParseException {
        if (i < 32) {
            _throwInvalidSpace(i);
        }
        _reportInvalidInitial(i);
    }

    protected void _reportInvalidInitial(int i) throws JsonParseException {
        _reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(i));
    }

    protected void _reportInvalidOther(int i) throws JsonParseException {
        _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(i));
    }

    protected void _reportInvalidOther(int i, int i2) throws JsonParseException {
        this._inputPtr = i2;
        _reportInvalidOther(i);
    }

    protected final byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder _getByteArrayBuilder = _getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                _loadMoreGuaranteed();
            }
            byte[] bArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            int i2 = bArr[i] & 255;
            if (i2 > 32) {
                int decodeBase64Char = base64Variant.decodeBase64Char(i2);
                if (decodeBase64Char < 0) {
                    if (i2 == 34) {
                        return _getByteArrayBuilder.toByteArray();
                    }
                    decodeBase64Char = _decodeBase64Escape(base64Variant, i2, 0);
                    if (decodeBase64Char < 0) {
                        continue;
                    }
                }
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                byte[] bArr2 = this._inputBuffer;
                int i3 = this._inputPtr;
                this._inputPtr = i3 + 1;
                int i4 = bArr2[i3] & 255;
                int decodeBase64Char2 = base64Variant.decodeBase64Char(i4);
                if (decodeBase64Char2 < 0) {
                    decodeBase64Char2 = _decodeBase64Escape(base64Variant, i4, 1);
                }
                int i5 = (decodeBase64Char << 6) | decodeBase64Char2;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                byte[] bArr3 = this._inputBuffer;
                int i6 = this._inputPtr;
                this._inputPtr = i6 + 1;
                int i7 = bArr3[i6] & 255;
                int decodeBase64Char3 = base64Variant.decodeBase64Char(i7);
                if (decodeBase64Char3 < 0) {
                    if (decodeBase64Char3 != -2) {
                        if (i7 == 34) {
                            _getByteArrayBuilder.append(i5 >> 4);
                            if (base64Variant.usesPadding()) {
                                this._inputPtr--;
                                _handleBase64MissingPadding(base64Variant);
                            }
                            return _getByteArrayBuilder.toByteArray();
                        }
                        decodeBase64Char3 = _decodeBase64Escape(base64Variant, i7, 2);
                    }
                    if (decodeBase64Char3 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            _loadMoreGuaranteed();
                        }
                        byte[] bArr4 = this._inputBuffer;
                        int i8 = this._inputPtr;
                        this._inputPtr = i8 + 1;
                        int i9 = bArr4[i8] & 255;
                        if (base64Variant.usesPaddingChar(i9) || _decodeBase64Escape(base64Variant, i9, 3) == -2) {
                            _getByteArrayBuilder.append(i5 >> 4);
                        } else {
                            throw reportInvalidBase64Char(base64Variant, i9, 3, "expected padding character '" + base64Variant.getPaddingChar() + LrcRow.SINGLE_QUOTE);
                        }
                    }
                }
                int i10 = (i5 << 6) | decodeBase64Char3;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                byte[] bArr5 = this._inputBuffer;
                int i11 = this._inputPtr;
                this._inputPtr = i11 + 1;
                int i12 = bArr5[i11] & 255;
                int decodeBase64Char4 = base64Variant.decodeBase64Char(i12);
                if (decodeBase64Char4 < 0) {
                    if (decodeBase64Char4 != -2) {
                        if (i12 == 34) {
                            _getByteArrayBuilder.appendTwoBytes(i10 >> 2);
                            if (base64Variant.usesPadding()) {
                                this._inputPtr--;
                                _handleBase64MissingPadding(base64Variant);
                            }
                            return _getByteArrayBuilder.toByteArray();
                        }
                        decodeBase64Char4 = _decodeBase64Escape(base64Variant, i12, 3);
                    }
                    if (decodeBase64Char4 == -2) {
                        _getByteArrayBuilder.appendTwoBytes(i10 >> 2);
                    }
                }
                _getByteArrayBuilder.appendThreeBytes((i10 << 6) | decodeBase64Char4);
            }
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
    public JsonLocation getTokenLocation() {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return new JsonLocation(_getSourceReference(), this._tokenInputTotal - 1, -1L, this._tokenInputRow, this._tokenInputCol);
        }
        return new JsonLocation(_getSourceReference(), this._currInputProcessed + (this._nameStartOffset - 1), -1L, this._nameStartRow, this._nameStartCol);
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
    public JsonLocation getCurrentLocation() {
        return new JsonLocation(_getSourceReference(), this._currInputProcessed + this._inputPtr, -1L, this._currInputRow, (this._inputPtr - this._currInputRowStart) + 1);
    }

    private final void o() {
        this._tokenInputRow = this._currInputRow;
        int i = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + i;
        this._tokenInputCol = i - this._currInputRowStart;
    }

    private final void p() {
        this._nameStartRow = this._currInputRow;
        int i = this._inputPtr;
        this._nameStartOffset = i;
        this._nameStartCol = i - this._currInputRowStart;
    }

    private final JsonToken j(int i) throws JsonParseException {
        if (i == 125) {
            r();
            JsonToken jsonToken = JsonToken.END_OBJECT;
            this._currToken = jsonToken;
            return jsonToken;
        }
        q();
        JsonToken jsonToken2 = JsonToken.END_ARRAY;
        this._currToken = jsonToken2;
        return jsonToken2;
    }

    private final void q() throws JsonParseException {
        o();
        if (!this._parsingContext.inArray()) {
            _reportMismatchedEndMarker(93, '}');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
    }

    private final void r() throws JsonParseException {
        o();
        if (!this._parsingContext.inObject()) {
            _reportMismatchedEndMarker(125, ']');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
    }
}
