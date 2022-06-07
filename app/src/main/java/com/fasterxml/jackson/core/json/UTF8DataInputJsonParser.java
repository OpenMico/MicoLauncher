package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

/* loaded from: classes.dex */
public class UTF8DataInputJsonParser extends ParserBase {
    protected DataInput _inputData;
    protected int _nextByte;
    protected ObjectCodec _objectCodec;
    protected int[] _quadBuffer = new int[16];
    protected final ByteQuadsCanonicalizer _symbols;
    protected boolean _tokenIncomplete;
    private int b;
    private static final int[] a = CharTypes.getInputCodeUtf8();
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();

    private static final int c(int i, int i2) {
        return i2 == 4 ? i : i | ((-1) << (i2 << 3));
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _closeInput() throws IOException {
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int releaseBuffered(OutputStream outputStream) throws IOException {
        return 0;
    }

    public UTF8DataInputJsonParser(IOContext iOContext, int i, DataInput dataInput, ObjectCodec objectCodec, ByteQuadsCanonicalizer byteQuadsCanonicalizer, int i2) {
        super(iOContext, i);
        this._nextByte = -1;
        this._objectCodec = objectCodec;
        this._symbols = byteQuadsCanonicalizer;
        this._inputData = dataInput;
        this._nextByte = i2;
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
    public Object getInputSource() {
        return this._inputData;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
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
        return d();
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
            return d();
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
            return d();
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

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public int getTextLength() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.size();
        } else if (this._currToken == JsonToken.FIELD_NAME) {
            return this._parsingContext.getCurrentName().length();
        } else {
            if (this._currToken == null) {
                return 0;
            }
            if (this._currToken.isNumeric()) {
                return this._textBuffer.size();
            }
            return this._currToken.asCharArray().length;
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00d4, code lost:
        r11._tokenIncomplete = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00d6, code lost:
        if (r4 <= 0) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d8, code lost:
        r3 = r3 + r4;
        r13.write(r14, 0, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00dc, code lost:
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:?, code lost:
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int _readBinary(com.fasterxml.jackson.core.Base64Variant r12, java.io.OutputStream r13, byte[] r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 269
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8DataInputJsonParser._readBinary(com.fasterxml.jackson.core.Base64Variant, java.io.OutputStream, byte[]):int");
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public JsonToken nextToken() throws IOException {
        JsonToken jsonToken;
        if (this._closed) {
            return null;
        }
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
        this._tokenInputRow = this._currInputRow;
        if (f == 93 || f == 125) {
            h(f);
            return this._currToken;
        }
        if (this._parsingContext.expectComma()) {
            if (f != 44) {
                _reportUnexpectedChar(f, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            f = e();
            if (JsonParser.Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (f == 93 || f == 125)) {
                h(f);
                return this._currToken;
            }
        }
        if (!this._parsingContext.inObject()) {
            return a(f);
        }
        this._parsingContext.setCurrentName(_parseName(f));
        this._currToken = JsonToken.FIELD_NAME;
        int g = g();
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
            _matchToken("false", 1);
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (g == 110) {
            _matchToken("null", 1);
            jsonToken = JsonToken.VALUE_NULL;
        } else if (g == 116) {
            _matchToken("true", 1);
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
            _matchToken("false", 1);
            JsonToken jsonToken3 = JsonToken.VALUE_FALSE;
            this._currToken = jsonToken3;
            return jsonToken3;
        } else if (i == 110) {
            _matchToken("null", 1);
            JsonToken jsonToken4 = JsonToken.VALUE_NULL;
            this._currToken = jsonToken4;
            return jsonToken4;
        } else if (i == 116) {
            _matchToken("true", 1);
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
        this._binaryValue = null;
        this._tokenInputRow = this._currInputRow;
        if (e == 93 || e == 125) {
            h(e);
            return null;
        }
        if (this._parsingContext.expectComma()) {
            if (e != 44) {
                _reportUnexpectedChar(e, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            e = e();
            if (JsonParser.Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (e == 93 || e == 125)) {
                h(e);
                return null;
            }
        }
        if (!this._parsingContext.inObject()) {
            a(e);
            return null;
        }
        String _parseName = _parseName(e);
        this._parsingContext.setCurrentName(_parseName);
        this._currToken = JsonToken.FIELD_NAME;
        int g = g();
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
            _matchToken("false", 1);
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (g == 110) {
            _matchToken("null", 1);
            jsonToken = JsonToken.VALUE_NULL;
        } else if (g == 116) {
            _matchToken("true", 1);
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
                return d();
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
        int i2;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int i3 = 1;
        if (i == 48) {
            i2 = b();
            if (i2 > 57 || i2 < 48) {
                emptyAndGetCurrentSegment[0] = '0';
            } else {
                i3 = 0;
            }
        } else {
            emptyAndGetCurrentSegment[0] = (char) i;
            i2 = this._inputData.readUnsignedByte();
        }
        int i4 = i3;
        while (i2 <= 57 && i2 >= 48) {
            i4++;
            i3++;
            emptyAndGetCurrentSegment[i3] = (char) i2;
            i2 = this._inputData.readUnsignedByte();
        }
        if (i2 == 46 || i2 == 101 || i2 == 69) {
            return a(emptyAndGetCurrentSegment, i3, i2, false, i4);
        }
        this._textBuffer.setCurrentLength(i3);
        if (this._parsingContext.inRoot()) {
            c();
        } else {
            this._nextByte = i2;
        }
        return resetInt(false, i4);
    }

    protected JsonToken _parseNegNumber() throws IOException {
        int i;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        emptyAndGetCurrentSegment[0] = '-';
        int readUnsignedByte = this._inputData.readUnsignedByte();
        emptyAndGetCurrentSegment[1] = (char) readUnsignedByte;
        if (readUnsignedByte <= 48) {
            if (readUnsignedByte != 48) {
                return _handleInvalidNumberStart(readUnsignedByte, true);
            }
            i = b();
        } else if (readUnsignedByte > 57) {
            return _handleInvalidNumberStart(readUnsignedByte, true);
        } else {
            i = this._inputData.readUnsignedByte();
        }
        int i2 = 2;
        int i3 = 1;
        while (i <= 57 && i >= 48) {
            i3++;
            i2++;
            emptyAndGetCurrentSegment[i2] = (char) i;
            i = this._inputData.readUnsignedByte();
        }
        if (i == 46 || i == 101 || i == 69) {
            return a(emptyAndGetCurrentSegment, i2, i, true, i3);
        }
        this._textBuffer.setCurrentLength(i2);
        this._nextByte = i;
        if (this._parsingContext.inRoot()) {
            c();
        }
        return resetInt(true, i3);
    }

    private final int b() throws IOException {
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if (readUnsignedByte < 48 || readUnsignedByte > 57) {
            return readUnsignedByte;
        }
        if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            reportInvalidNumber("Leading zeroes not allowed");
        }
        while (readUnsignedByte == 48) {
            readUnsignedByte = this._inputData.readUnsignedByte();
        }
        return readUnsignedByte;
    }

    private final JsonToken a(char[] cArr, int i, int i2, boolean z, int i3) throws IOException {
        int i4;
        int i5;
        char[] cArr2;
        int readUnsignedByte;
        if (i2 == 46) {
            i++;
            cArr[i] = (char) i2;
            char[] cArr3 = cArr;
            int i6 = 0;
            while (true) {
                readUnsignedByte = this._inputData.readUnsignedByte();
                if (readUnsignedByte < 48 || readUnsignedByte > 57) {
                    break;
                }
                i6++;
                if (i >= cArr3.length) {
                    cArr3 = this._textBuffer.finishCurrentSegment();
                    i = 0;
                }
                i++;
                cArr3[i] = (char) readUnsignedByte;
            }
            if (i6 == 0) {
                reportUnexpectedNumberChar(readUnsignedByte, "Decimal point not followed by a digit");
            }
            i4 = i6;
            cArr = cArr3;
            i2 = readUnsignedByte;
        } else {
            i4 = 0;
        }
        if (i2 == 101 || i2 == 69) {
            if (i >= cArr.length) {
                cArr = this._textBuffer.finishCurrentSegment();
                i = 0;
            }
            int i7 = i + 1;
            cArr[i] = (char) i2;
            int readUnsignedByte2 = this._inputData.readUnsignedByte();
            if (readUnsignedByte2 == 45 || readUnsignedByte2 == 43) {
                if (i7 >= cArr.length) {
                    cArr = this._textBuffer.finishCurrentSegment();
                    i7 = 0;
                }
                i7++;
                cArr[i7] = (char) readUnsignedByte2;
                i2 = this._inputData.readUnsignedByte();
                cArr2 = cArr;
                i5 = 0;
            } else {
                i2 = readUnsignedByte2;
                cArr2 = cArr;
                i5 = 0;
            }
            while (i2 <= 57 && i2 >= 48) {
                i5++;
                if (i7 >= cArr2.length) {
                    cArr2 = this._textBuffer.finishCurrentSegment();
                    i7 = 0;
                }
                i7++;
                cArr2[i7] = (char) i2;
                i2 = this._inputData.readUnsignedByte();
            }
            if (i5 == 0) {
                reportUnexpectedNumberChar(i2, "Exponent indicator not followed by a digit");
            }
            i = i7;
        } else {
            i5 = 0;
        }
        this._nextByte = i2;
        if (this._parsingContext.inRoot()) {
            c();
        }
        this._textBuffer.setCurrentLength(i);
        return resetFloat(z, i3, i4, i5);
    }

    private final void c() throws IOException {
        int i = this._nextByte;
        if (i <= 32) {
            this._nextByte = -1;
            if (i == 13 || i == 10) {
                this._currInputRow++;
                return;
            }
            return;
        }
        _reportMissingRootWS(i);
    }

    protected final String _parseName(int i) throws IOException {
        if (i != 34) {
            return _handleOddName(i);
        }
        int[] iArr = _icLatin1;
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if (iArr[readUnsignedByte] != 0) {
            return readUnsignedByte == 34 ? "" : b(0, readUnsignedByte, 0);
        }
        int readUnsignedByte2 = this._inputData.readUnsignedByte();
        if (iArr[readUnsignedByte2] == 0) {
            int i2 = (readUnsignedByte << 8) | readUnsignedByte2;
            int readUnsignedByte3 = this._inputData.readUnsignedByte();
            if (iArr[readUnsignedByte3] == 0) {
                int i3 = (i2 << 8) | readUnsignedByte3;
                int readUnsignedByte4 = this._inputData.readUnsignedByte();
                if (iArr[readUnsignedByte4] == 0) {
                    int i4 = (i3 << 8) | readUnsignedByte4;
                    int readUnsignedByte5 = this._inputData.readUnsignedByte();
                    if (iArr[readUnsignedByte5] == 0) {
                        this.b = i4;
                        return b(readUnsignedByte5);
                    } else if (readUnsignedByte5 == 34) {
                        return b(i4, 4);
                    } else {
                        return b(i4, readUnsignedByte5, 4);
                    }
                } else if (readUnsignedByte4 == 34) {
                    return b(i3, 3);
                } else {
                    return b(i3, readUnsignedByte4, 3);
                }
            } else if (readUnsignedByte3 == 34) {
                return b(i2, 2);
            } else {
                return b(i2, readUnsignedByte3, 2);
            }
        } else if (readUnsignedByte2 == 34) {
            return b(readUnsignedByte, 1);
        } else {
            return b(readUnsignedByte, readUnsignedByte2, 1);
        }
    }

    private final String b(int i) throws IOException {
        int[] iArr = _icLatin1;
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if (iArr[readUnsignedByte] == 0) {
            int i2 = (i << 8) | readUnsignedByte;
            int readUnsignedByte2 = this._inputData.readUnsignedByte();
            if (iArr[readUnsignedByte2] == 0) {
                int i3 = (i2 << 8) | readUnsignedByte2;
                int readUnsignedByte3 = this._inputData.readUnsignedByte();
                if (iArr[readUnsignedByte3] == 0) {
                    int i4 = (i3 << 8) | readUnsignedByte3;
                    int readUnsignedByte4 = this._inputData.readUnsignedByte();
                    if (iArr[readUnsignedByte4] == 0) {
                        return a(readUnsignedByte4, i4);
                    }
                    if (readUnsignedByte4 == 34) {
                        return c(this.b, i4, 4);
                    }
                    return a(this.b, i4, readUnsignedByte4, 4);
                } else if (readUnsignedByte3 == 34) {
                    return c(this.b, i3, 3);
                } else {
                    return a(this.b, i3, readUnsignedByte3, 3);
                }
            } else if (readUnsignedByte2 == 34) {
                return c(this.b, i2, 2);
            } else {
                return a(this.b, i2, readUnsignedByte2, 2);
            }
        } else if (readUnsignedByte == 34) {
            return c(this.b, i, 1);
        } else {
            return a(this.b, i, readUnsignedByte, 1);
        }
    }

    private final String a(int i, int i2) throws IOException {
        int[] iArr = _icLatin1;
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if (iArr[readUnsignedByte] == 0) {
            int i3 = (i << 8) | readUnsignedByte;
            int readUnsignedByte2 = this._inputData.readUnsignedByte();
            if (iArr[readUnsignedByte2] == 0) {
                int i4 = (i3 << 8) | readUnsignedByte2;
                int readUnsignedByte3 = this._inputData.readUnsignedByte();
                if (iArr[readUnsignedByte3] == 0) {
                    int i5 = (i4 << 8) | readUnsignedByte3;
                    int readUnsignedByte4 = this._inputData.readUnsignedByte();
                    if (iArr[readUnsignedByte4] == 0) {
                        return a(readUnsignedByte4, i2, i5);
                    }
                    if (readUnsignedByte4 == 34) {
                        return b(this.b, i2, i5, 4);
                    }
                    return a(this.b, i2, i5, readUnsignedByte4, 4);
                } else if (readUnsignedByte3 == 34) {
                    return b(this.b, i2, i4, 3);
                } else {
                    return a(this.b, i2, i4, readUnsignedByte3, 3);
                }
            } else if (readUnsignedByte2 == 34) {
                return b(this.b, i2, i3, 2);
            } else {
                return a(this.b, i2, i3, readUnsignedByte2, 2);
            }
        } else if (readUnsignedByte == 34) {
            return b(this.b, i2, i, 1);
        } else {
            return a(this.b, i2, i, readUnsignedByte, 1);
        }
    }

    private final String a(int i, int i2, int i3) throws IOException {
        int[] iArr = this._quadBuffer;
        iArr[0] = this.b;
        iArr[1] = i2;
        iArr[2] = i3;
        int[] iArr2 = _icLatin1;
        int i4 = i;
        int i5 = 3;
        while (true) {
            int readUnsignedByte = this._inputData.readUnsignedByte();
            if (iArr2[readUnsignedByte] == 0) {
                int i6 = (i4 << 8) | readUnsignedByte;
                int readUnsignedByte2 = this._inputData.readUnsignedByte();
                if (iArr2[readUnsignedByte2] == 0) {
                    int i7 = (i6 << 8) | readUnsignedByte2;
                    int readUnsignedByte3 = this._inputData.readUnsignedByte();
                    if (iArr2[readUnsignedByte3] == 0) {
                        int i8 = (i7 << 8) | readUnsignedByte3;
                        int readUnsignedByte4 = this._inputData.readUnsignedByte();
                        if (iArr2[readUnsignedByte4] == 0) {
                            int[] iArr3 = this._quadBuffer;
                            if (i5 >= iArr3.length) {
                                this._quadBuffer = a(iArr3, i5);
                            }
                            i5++;
                            this._quadBuffer[i5] = i8;
                            i4 = readUnsignedByte4;
                        } else if (readUnsignedByte4 == 34) {
                            return a(this._quadBuffer, i5, i8, 4);
                        } else {
                            return parseEscapedName(this._quadBuffer, i5, i8, readUnsignedByte4, 4);
                        }
                    } else if (readUnsignedByte3 == 34) {
                        return a(this._quadBuffer, i5, i7, 3);
                    } else {
                        return parseEscapedName(this._quadBuffer, i5, i7, readUnsignedByte3, 3);
                    }
                } else if (readUnsignedByte2 == 34) {
                    return a(this._quadBuffer, i5, i6, 2);
                } else {
                    return parseEscapedName(this._quadBuffer, i5, i6, readUnsignedByte2, 2);
                }
            } else if (readUnsignedByte == 34) {
                return a(this._quadBuffer, i5, i4, 1);
            } else {
                return parseEscapedName(this._quadBuffer, i5, i4, readUnsignedByte, 1);
            }
        }
    }

    private final String b(int i, int i2, int i3) throws IOException {
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
                            iArr = a(iArr, iArr.length);
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
                                iArr = a(iArr, iArr.length);
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
                    iArr = a(iArr, iArr.length);
                    this._quadBuffer = iArr;
                }
                i++;
                iArr[i] = i2;
                i2 = i3;
                i4 = 1;
            }
            i3 = this._inputData.readUnsignedByte();
        }
        if (i4 > 0) {
            if (i >= iArr.length) {
                iArr = a(iArr, iArr.length);
                this._quadBuffer = iArr;
            }
            i++;
            iArr[i] = c(i2, i4);
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
        do {
            if (i2 < 4) {
                i2++;
                i5 = (i5 << 8) | i3;
            } else {
                if (i4 >= iArr.length) {
                    iArr = a(iArr, iArr.length);
                    this._quadBuffer = iArr;
                }
                i4++;
                iArr[i4] = i5;
                i2 = 1;
                i5 = i3;
            }
            i3 = this._inputData.readUnsignedByte();
        } while (inputCodeUtf8JsNames[i3] == 0);
        this._nextByte = i3;
        if (i2 > 0) {
            if (i4 >= iArr.length) {
                int[] a2 = a(iArr, iArr.length);
                this._quadBuffer = a2;
                iArr = a2;
            }
            i4++;
            iArr[i4] = i5;
        }
        String findName = this._symbols.findName(iArr, i4);
        return findName == null ? a(iArr, i4, i2) : findName;
    }

    protected String _parseAposName() throws IOException {
        int i;
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if (readUnsignedByte == 39) {
            return "";
        }
        int[] iArr = this._quadBuffer;
        int[] iArr2 = _icLatin1;
        int[] iArr3 = iArr;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (readUnsignedByte != 39) {
            if (!(readUnsignedByte == 34 || iArr2[readUnsignedByte] == 0)) {
                if (readUnsignedByte != 92) {
                    _throwUnquotedSpace(readUnsignedByte, "name");
                } else {
                    readUnsignedByte = _decodeEscaped();
                }
                if (readUnsignedByte > 127) {
                    if (i2 >= 4) {
                        if (i3 >= iArr3.length) {
                            iArr3 = a(iArr3, iArr3.length);
                            this._quadBuffer = iArr3;
                        }
                        i3++;
                        iArr3[i3] = i4;
                        i2 = 0;
                        i4 = 0;
                    }
                    if (readUnsignedByte < 2048) {
                        i4 = (i4 << 8) | (readUnsignedByte >> 6) | 192;
                        i2++;
                    } else {
                        int i5 = (i4 << 8) | (readUnsignedByte >> 12) | 224;
                        int i6 = i2 + 1;
                        if (i6 >= 4) {
                            if (i3 >= iArr3.length) {
                                int[] a2 = a(iArr3, iArr3.length);
                                this._quadBuffer = a2;
                                iArr3 = a2;
                            }
                            i3++;
                            iArr3[i3] = i5;
                            i6 = 0;
                            i5 = 0;
                        }
                        i4 = (i5 << 8) | ((readUnsignedByte >> 6) & 63) | 128;
                        i2 = i6 + 1;
                    }
                    readUnsignedByte = (readUnsignedByte & 63) | 128;
                }
            }
            if (i2 < 4) {
                i2++;
                i4 = readUnsignedByte | (i4 << 8);
            } else {
                if (i3 >= iArr3.length) {
                    iArr3 = a(iArr3, iArr3.length);
                    this._quadBuffer = iArr3;
                }
                i3++;
                iArr3[i3] = i4;
                i4 = readUnsignedByte;
                i2 = 1;
            }
            readUnsignedByte = this._inputData.readUnsignedByte();
        }
        if (i2 > 0) {
            if (i3 >= iArr3.length) {
                int[] a3 = a(iArr3, iArr3.length);
                this._quadBuffer = a3;
                iArr3 = a3;
            }
            i = i3 + 1;
            iArr3[i3] = c(i4, i2);
        } else {
            i = i3;
        }
        String findName = this._symbols.findName(iArr3, i);
        return findName == null ? a(iArr3, i, i2) : findName;
    }

    private final String b(int i, int i2) throws JsonParseException {
        int c = c(i, i2);
        String findName = this._symbols.findName(c);
        if (findName != null) {
            return findName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = c;
        return a(iArr, 1, i2);
    }

    private final String c(int i, int i2, int i3) throws JsonParseException {
        int c = c(i2, i3);
        String findName = this._symbols.findName(i, c);
        if (findName != null) {
            return findName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        iArr[1] = c;
        return a(iArr, 2, i3);
    }

    private final String b(int i, int i2, int i3, int i4) throws JsonParseException {
        int c = c(i3, i4);
        String findName = this._symbols.findName(i, i2, c);
        if (findName != null) {
            return findName;
        }
        int[] iArr = this._quadBuffer;
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = c(c, i4);
        return a(iArr, 3, i4);
    }

    private final String a(int[] iArr, int i, int i2, int i3) throws JsonParseException {
        if (i >= iArr.length) {
            iArr = a(iArr, iArr.length);
            this._quadBuffer = iArr;
        }
        int i4 = i + 1;
        iArr[i] = c(i2, i3);
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
                    g(i13);
                }
                int i14 = (i13 & 63) | (i4 << 6);
                if (i5 > 1) {
                    int i15 = iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3);
                    i10++;
                    if ((i15 & 192) != 128) {
                        g(i15);
                    }
                    i7 = (i15 & 63) | (i14 << 6);
                    i6 = 2;
                    if (i5 > 2) {
                        int i16 = iArr[i10 >> 2] >> ((3 - (i10 & 3)) << 3);
                        i10++;
                        if ((i16 & 192) != 128) {
                            g(i16 & 255);
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

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected void _finishString() throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = a;
        int length = emptyAndGetCurrentSegment.length;
        int i = 0;
        while (true) {
            int readUnsignedByte = this._inputData.readUnsignedByte();
            if (iArr[readUnsignedByte] == 0) {
                int i2 = i + 1;
                emptyAndGetCurrentSegment[i] = (char) readUnsignedByte;
                if (i2 >= length) {
                    a(emptyAndGetCurrentSegment, i2, this._inputData.readUnsignedByte());
                    return;
                }
                i = i2;
            } else if (readUnsignedByte == 34) {
                this._textBuffer.setCurrentLength(i);
                return;
            } else {
                a(emptyAndGetCurrentSegment, i, readUnsignedByte);
                return;
            }
        }
    }

    private String d() throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = a;
        int length = emptyAndGetCurrentSegment.length;
        int i = 0;
        while (true) {
            int readUnsignedByte = this._inputData.readUnsignedByte();
            if (iArr[readUnsignedByte] == 0) {
                int i2 = i + 1;
                emptyAndGetCurrentSegment[i] = (char) readUnsignedByte;
                if (i2 >= length) {
                    a(emptyAndGetCurrentSegment, i2, this._inputData.readUnsignedByte());
                    return this._textBuffer.contentsAsString();
                }
                i = i2;
            } else if (readUnsignedByte == 34) {
                return this._textBuffer.setCurrentAndReturn(i);
            } else {
                a(emptyAndGetCurrentSegment, i, readUnsignedByte);
                return this._textBuffer.contentsAsString();
            }
        }
    }

    private final void a(char[] cArr, int i, int i2) throws IOException {
        int[] iArr = a;
        int length = cArr.length;
        while (true) {
            if (iArr[i2] == 0) {
                if (i >= length) {
                    cArr = this._textBuffer.finishCurrentSegment();
                    length = cArr.length;
                    i = 0;
                }
                i++;
                cArr[i] = (char) i2;
                i2 = this._inputData.readUnsignedByte();
            } else if (i2 == 34) {
                this._textBuffer.setCurrentLength(i);
                return;
            } else {
                switch (iArr[i2]) {
                    case 1:
                        i2 = _decodeEscaped();
                        break;
                    case 2:
                        i2 = d(i2);
                        break;
                    case 3:
                        i2 = e(i2);
                        break;
                    case 4:
                        int f = f(i2);
                        int i3 = i + 1;
                        cArr[i] = (char) (55296 | (f >> 10));
                        if (i3 >= cArr.length) {
                            cArr = this._textBuffer.finishCurrentSegment();
                            length = cArr.length;
                            i = 0;
                        } else {
                            i = i3;
                        }
                        i2 = (f & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) | 56320;
                        break;
                    default:
                        if (i2 >= 32) {
                            _reportInvalidChar(i2);
                            break;
                        } else {
                            _throwUnquotedSpace(i2, "string value");
                            break;
                        }
                }
                if (i >= cArr.length) {
                    cArr = this._textBuffer.finishCurrentSegment();
                    length = cArr.length;
                    i = 0;
                }
                i++;
                cArr[i] = (char) i2;
                i2 = this._inputData.readUnsignedByte();
            }
        }
    }

    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int[] iArr = a;
        while (true) {
            int readUnsignedByte = this._inputData.readUnsignedByte();
            if (iArr[readUnsignedByte] != 0) {
                if (readUnsignedByte != 34) {
                    switch (iArr[readUnsignedByte]) {
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
                            n();
                            continue;
                        default:
                            if (readUnsignedByte >= 32) {
                                _reportInvalidChar(readUnsignedByte);
                                break;
                            } else {
                                _throwUnquotedSpace(readUnsignedByte, "string value");
                                continue;
                            }
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002b, code lost:
        if (r2._parsingContext.inArray() == false) goto L_0x0086;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.core.JsonToken _handleUnexpectedValue(int r3) throws java.io.IOException {
        /*
            r2 = this;
            r0 = 39
            if (r3 == r0) goto L_0x0079
            r0 = 73
            r1 = 1
            if (r3 == r0) goto L_0x005d
            r0 = 78
            if (r3 == r0) goto L_0x0041
            r0 = 93
            if (r3 == r0) goto L_0x0025
            r0 = 125(0x7d, float:1.75E-43)
            if (r3 == r0) goto L_0x003b
            switch(r3) {
                case 43: goto L_0x0019;
                case 44: goto L_0x002e;
                default: goto L_0x0018;
            }
        L_0x0018:
            goto L_0x0086
        L_0x0019:
            java.io.DataInput r3 = r2._inputData
            int r3 = r3.readUnsignedByte()
            r0 = 0
            com.fasterxml.jackson.core.JsonToken r3 = r2._handleInvalidNumberStart(r3, r0)
            return r3
        L_0x0025:
            com.fasterxml.jackson.core.json.JsonReadContext r0 = r2._parsingContext
            boolean r0 = r0.inArray()
            if (r0 != 0) goto L_0x002e
            goto L_0x0086
        L_0x002e:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x003b
            r2._nextByte = r3
            com.fasterxml.jackson.core.JsonToken r3 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL
            return r3
        L_0x003b:
            java.lang.String r0 = "expected a value"
            r2._reportUnexpectedChar(r3, r0)
            goto L_0x0079
        L_0x0041:
            java.lang.String r0 = "NaN"
            r2._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0057
            java.lang.String r3 = "NaN"
            r0 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            com.fasterxml.jackson.core.JsonToken r3 = r2.resetAsNaN(r3, r0)
            return r3
        L_0x0057:
            java.lang.String r0 = "Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r2._reportError(r0)
            goto L_0x0086
        L_0x005d:
            java.lang.String r0 = "Infinity"
            r2._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0073
            java.lang.String r3 = "Infinity"
            r0 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            com.fasterxml.jackson.core.JsonToken r3 = r2.resetAsNaN(r3, r0)
            return r3
        L_0x0073:
            java.lang.String r0 = "Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r2._reportError(r0)
            goto L_0x0086
        L_0x0079:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES
            boolean r0 = r2.isEnabled(r0)
            if (r0 == 0) goto L_0x0086
            com.fasterxml.jackson.core.JsonToken r3 = r2._handleApos()
            return r3
        L_0x0086:
            boolean r0 = java.lang.Character.isJavaIdentifierStart(r3)
            if (r0 == 0) goto L_0x00a3
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = ""
            r0.append(r1)
            char r1 = (char) r3
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "('true', 'false' or 'null')"
            r2._reportInvalidToken(r3, r0, r1)
        L_0x00a3:
            java.lang.String r0 = "expected a valid value (number, String, array, object, 'true', 'false' or 'null')"
            r2._reportUnexpectedChar(r3, r0)
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8DataInputJsonParser._handleUnexpectedValue(int):com.fasterxml.jackson.core.JsonToken");
    }

    protected JsonToken _handleApos() throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = a;
        int i = 0;
        while (true) {
            int length = emptyAndGetCurrentSegment.length;
            if (i >= emptyAndGetCurrentSegment.length) {
                emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                length = emptyAndGetCurrentSegment.length;
                i = 0;
            }
            while (true) {
                int readUnsignedByte = this._inputData.readUnsignedByte();
                if (readUnsignedByte == 39) {
                    this._textBuffer.setCurrentLength(i);
                    return JsonToken.VALUE_STRING;
                } else if (iArr[readUnsignedByte] != 0) {
                    switch (iArr[readUnsignedByte]) {
                        case 1:
                            readUnsignedByte = _decodeEscaped();
                            break;
                        case 2:
                            readUnsignedByte = d(readUnsignedByte);
                            break;
                        case 3:
                            readUnsignedByte = e(readUnsignedByte);
                            break;
                        case 4:
                            int f = f(readUnsignedByte);
                            i++;
                            emptyAndGetCurrentSegment[i] = (char) (55296 | (f >> 10));
                            if (i >= emptyAndGetCurrentSegment.length) {
                                emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                                i = 0;
                            }
                            readUnsignedByte = 56320 | (f & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES);
                            break;
                        default:
                            if (readUnsignedByte < 32) {
                                _throwUnquotedSpace(readUnsignedByte, "string value");
                            }
                            _reportInvalidChar(readUnsignedByte);
                            break;
                    }
                    if (i >= emptyAndGetCurrentSegment.length) {
                        emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                        i = 0;
                    }
                    i++;
                    emptyAndGetCurrentSegment[i] = (char) readUnsignedByte;
                } else {
                    int i2 = i + 1;
                    emptyAndGetCurrentSegment[i] = (char) readUnsignedByte;
                    if (i2 >= length) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                }
            }
        }
    }

    protected JsonToken _handleInvalidNumberStart(int i, boolean z) throws IOException {
        String str;
        while (i == 73) {
            i = this._inputData.readUnsignedByte();
            if (i != 78) {
                if (i != 110) {
                    break;
                }
                str = z ? "-Infinity" : "+Infinity";
            } else {
                str = z ? "-INF" : "+INF";
            }
            _matchToken(str, 3);
            if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                return resetAsNaN(str, z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
            }
            _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
        }
        reportUnexpectedNumberChar(i, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    protected final void _matchToken(String str, int i) throws IOException {
        int length = str.length();
        do {
            int readUnsignedByte = this._inputData.readUnsignedByte();
            if (readUnsignedByte != str.charAt(i)) {
                _reportInvalidToken(readUnsignedByte, str.substring(0, i));
            }
            i++;
        } while (i < length);
        int readUnsignedByte2 = this._inputData.readUnsignedByte();
        if (!(readUnsignedByte2 < 48 || readUnsignedByte2 == 93 || readUnsignedByte2 == 125)) {
            a(str, i, readUnsignedByte2);
        }
        this._nextByte = readUnsignedByte2;
    }

    private final void a(String str, int i, int i2) throws IOException {
        char _decodeCharForError = (char) _decodeCharForError(i2);
        if (Character.isJavaIdentifierPart(_decodeCharForError)) {
            _reportInvalidToken(_decodeCharForError, str.substring(0, i));
        }
    }

    private final int e() throws IOException {
        int i = this._nextByte;
        if (i < 0) {
            i = this._inputData.readUnsignedByte();
        } else {
            this._nextByte = -1;
        }
        while (i <= 32) {
            if (i == 13 || i == 10) {
                this._currInputRow++;
            }
            i = this._inputData.readUnsignedByte();
        }
        return (i == 47 || i == 35) ? c(i) : i;
    }

    private final int f() throws IOException {
        int i = this._nextByte;
        if (i < 0) {
            try {
                i = this._inputData.readUnsignedByte();
            } catch (EOFException unused) {
                return _eofAsNextChar();
            }
        } else {
            this._nextByte = -1;
        }
        while (i <= 32) {
            if (i == 13 || i == 10) {
                this._currInputRow++;
            }
            try {
                i = this._inputData.readUnsignedByte();
            } catch (EOFException unused2) {
                return _eofAsNextChar();
            }
        }
        return (i == 47 || i == 35) ? c(i) : i;
    }

    private final int c(int i) throws IOException {
        while (true) {
            if (i > 32) {
                if (i == 47) {
                    h();
                } else if (i != 35 || !j()) {
                    return i;
                }
            } else if (i == 13 || i == 10) {
                this._currInputRow++;
            }
            i = this._inputData.readUnsignedByte();
        }
    }

    private final int g() throws IOException {
        int i = this._nextByte;
        if (i < 0) {
            i = this._inputData.readUnsignedByte();
        } else {
            this._nextByte = -1;
        }
        if (i == 58) {
            int readUnsignedByte = this._inputData.readUnsignedByte();
            if (readUnsignedByte > 32) {
                return (readUnsignedByte == 47 || readUnsignedByte == 35) ? a(readUnsignedByte, true) : readUnsignedByte;
            }
            if ((readUnsignedByte == 32 || readUnsignedByte == 9) && (readUnsignedByte = this._inputData.readUnsignedByte()) > 32) {
                return (readUnsignedByte == 47 || readUnsignedByte == 35) ? a(readUnsignedByte, true) : readUnsignedByte;
            }
            return a(readUnsignedByte, true);
        }
        if (i == 32 || i == 9) {
            i = this._inputData.readUnsignedByte();
        }
        if (i != 58) {
            return a(i, false);
        }
        int readUnsignedByte2 = this._inputData.readUnsignedByte();
        if (readUnsignedByte2 > 32) {
            return (readUnsignedByte2 == 47 || readUnsignedByte2 == 35) ? a(readUnsignedByte2, true) : readUnsignedByte2;
        }
        if ((readUnsignedByte2 == 32 || readUnsignedByte2 == 9) && (readUnsignedByte2 = this._inputData.readUnsignedByte()) > 32) {
            return (readUnsignedByte2 == 47 || readUnsignedByte2 == 35) ? a(readUnsignedByte2, true) : readUnsignedByte2;
        }
        return a(readUnsignedByte2, true);
    }

    private final int a(int i, boolean z) throws IOException {
        while (true) {
            if (i > 32) {
                if (i == 47) {
                    h();
                } else if (i != 35 || !j()) {
                    if (z) {
                        return i;
                    }
                    if (i != 58) {
                        _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
                    }
                    z = true;
                }
            } else if (i == 13 || i == 10) {
                this._currInputRow++;
            }
            i = this._inputData.readUnsignedByte();
        }
    }

    private final void h() throws IOException {
        if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if (readUnsignedByte == 47) {
            k();
        } else if (readUnsignedByte == 42) {
            i();
        } else {
            _reportUnexpectedChar(readUnsignedByte, "was expecting either '*' or '/' for a comment");
        }
    }

    private final void i() throws IOException {
        int[] inputCodeComment = CharTypes.getInputCodeComment();
        int readUnsignedByte = this._inputData.readUnsignedByte();
        while (true) {
            int i = inputCodeComment[readUnsignedByte];
            if (i != 0) {
                if (i == 10 || i == 13) {
                    this._currInputRow++;
                } else if (i != 42) {
                    switch (i) {
                        case 2:
                            l();
                            break;
                        case 3:
                            m();
                            break;
                        case 4:
                            n();
                            break;
                        default:
                            _reportInvalidChar(readUnsignedByte);
                            break;
                    }
                } else {
                    readUnsignedByte = this._inputData.readUnsignedByte();
                    if (readUnsignedByte == 47) {
                        return;
                    }
                }
            }
            readUnsignedByte = this._inputData.readUnsignedByte();
        }
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
            int readUnsignedByte = this._inputData.readUnsignedByte();
            int i = inputCodeComment[readUnsignedByte];
            if (i != 0) {
                if (i != 10 && i != 13) {
                    if (i != 42) {
                        switch (i) {
                            case 2:
                                l();
                                continue;
                            case 3:
                                m();
                                continue;
                            case 4:
                                n();
                                continue;
                            default:
                                if (i < 0) {
                                    _reportInvalidChar(readUnsignedByte);
                                    break;
                                } else {
                                    continue;
                                }
                        }
                    }
                }
            }
        }
        this._currInputRow++;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected char _decodeEscaped() throws IOException {
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if (readUnsignedByte == 34 || readUnsignedByte == 47 || readUnsignedByte == 92) {
            return (char) readUnsignedByte;
        }
        if (readUnsignedByte == 98) {
            return '\b';
        }
        if (readUnsignedByte == 102) {
            return '\f';
        }
        if (readUnsignedByte == 110) {
            return '\n';
        }
        if (readUnsignedByte == 114) {
            return '\r';
        }
        switch (readUnsignedByte) {
            case 116:
                return '\t';
            case 117:
                int i = 0;
                for (int i2 = 0; i2 < 4; i2++) {
                    int readUnsignedByte2 = this._inputData.readUnsignedByte();
                    int charToHex = CharTypes.charToHex(readUnsignedByte2);
                    if (charToHex < 0) {
                        _reportUnexpectedChar(readUnsignedByte2, "expected a hex-digit for character escape sequence");
                    }
                    i = (i << 4) | charToHex;
                }
                return (char) i;
            default:
                return _handleUnrecognizedCharacterEscape((char) _decodeCharForError(readUnsignedByte));
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
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if ((readUnsignedByte & 192) != 128) {
            g(readUnsignedByte & 255);
        }
        int i3 = (i2 << 6) | (readUnsignedByte & 63);
        if (c <= 1) {
            return i3;
        }
        int readUnsignedByte2 = this._inputData.readUnsignedByte();
        if ((readUnsignedByte2 & 192) != 128) {
            g(readUnsignedByte2 & 255);
        }
        int i4 = (i3 << 6) | (readUnsignedByte2 & 63);
        if (c <= 2) {
            return i4;
        }
        int readUnsignedByte3 = this._inputData.readUnsignedByte();
        if ((readUnsignedByte3 & 192) != 128) {
            g(readUnsignedByte3 & 255);
        }
        return (i4 << 6) | (readUnsignedByte3 & 63);
    }

    private final int d(int i) throws IOException {
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if ((readUnsignedByte & 192) != 128) {
            g(readUnsignedByte & 255);
        }
        return ((i & 31) << 6) | (readUnsignedByte & 63);
    }

    private final int e(int i) throws IOException {
        int i2 = i & 15;
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if ((readUnsignedByte & 192) != 128) {
            g(readUnsignedByte & 255);
        }
        int i3 = (i2 << 6) | (readUnsignedByte & 63);
        int readUnsignedByte2 = this._inputData.readUnsignedByte();
        if ((readUnsignedByte2 & 192) != 128) {
            g(readUnsignedByte2 & 255);
        }
        return (i3 << 6) | (readUnsignedByte2 & 63);
    }

    private final int f(int i) throws IOException {
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if ((readUnsignedByte & 192) != 128) {
            g(readUnsignedByte & 255);
        }
        int i2 = ((i & 7) << 6) | (readUnsignedByte & 63);
        int readUnsignedByte2 = this._inputData.readUnsignedByte();
        if ((readUnsignedByte2 & 192) != 128) {
            g(readUnsignedByte2 & 255);
        }
        int i3 = (i2 << 6) | (readUnsignedByte2 & 63);
        int readUnsignedByte3 = this._inputData.readUnsignedByte();
        if ((readUnsignedByte3 & 192) != 128) {
            g(readUnsignedByte3 & 255);
        }
        return ((i3 << 6) | (readUnsignedByte3 & 63)) - 65536;
    }

    private final void l() throws IOException {
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if ((readUnsignedByte & 192) != 128) {
            g(readUnsignedByte & 255);
        }
    }

    private final void m() throws IOException {
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if ((readUnsignedByte & 192) != 128) {
            g(readUnsignedByte & 255);
        }
        int readUnsignedByte2 = this._inputData.readUnsignedByte();
        if ((readUnsignedByte2 & 192) != 128) {
            g(readUnsignedByte2 & 255);
        }
    }

    private final void n() throws IOException {
        int readUnsignedByte = this._inputData.readUnsignedByte();
        if ((readUnsignedByte & 192) != 128) {
            g(readUnsignedByte & 255);
        }
        int readUnsignedByte2 = this._inputData.readUnsignedByte();
        if ((readUnsignedByte2 & 192) != 128) {
            g(readUnsignedByte2 & 255);
        }
        int readUnsignedByte3 = this._inputData.readUnsignedByte();
        if ((readUnsignedByte3 & 192) != 128) {
            g(readUnsignedByte3 & 255);
        }
    }

    protected void _reportInvalidToken(int i, String str) throws IOException {
        _reportInvalidToken(i, str, "'null', 'true', 'false' or NaN");
    }

    protected void _reportInvalidToken(int i, String str, String str2) throws IOException {
        StringBuilder sb = new StringBuilder(str);
        while (true) {
            char _decodeCharForError = (char) _decodeCharForError(i);
            if (!Character.isJavaIdentifierPart(_decodeCharForError)) {
                _reportError("Unrecognized token '" + sb.toString() + "': was expecting " + str2);
                return;
            }
            sb.append(_decodeCharForError);
            i = this._inputData.readUnsignedByte();
        }
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

    private void g(int i) throws JsonParseException {
        _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(i));
    }

    private static int[] a(int[] iArr, int i) {
        if (iArr == null) {
            return new int[i];
        }
        return Arrays.copyOf(iArr, iArr.length + i);
    }

    protected final byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        int readUnsignedByte;
        ByteArrayBuilder _getByteArrayBuilder = _getByteArrayBuilder();
        while (true) {
            int readUnsignedByte2 = this._inputData.readUnsignedByte();
            if (readUnsignedByte2 > 32) {
                int decodeBase64Char = base64Variant.decodeBase64Char(readUnsignedByte2);
                if (decodeBase64Char < 0) {
                    if (readUnsignedByte2 == 34) {
                        return _getByteArrayBuilder.toByteArray();
                    }
                    decodeBase64Char = _decodeBase64Escape(base64Variant, readUnsignedByte2, 0);
                    if (decodeBase64Char < 0) {
                        continue;
                    }
                }
                int readUnsignedByte3 = this._inputData.readUnsignedByte();
                int decodeBase64Char2 = base64Variant.decodeBase64Char(readUnsignedByte3);
                if (decodeBase64Char2 < 0) {
                    decodeBase64Char2 = _decodeBase64Escape(base64Variant, readUnsignedByte3, 1);
                }
                int i = (decodeBase64Char << 6) | decodeBase64Char2;
                int readUnsignedByte4 = this._inputData.readUnsignedByte();
                int decodeBase64Char3 = base64Variant.decodeBase64Char(readUnsignedByte4);
                if (decodeBase64Char3 < 0) {
                    if (decodeBase64Char3 != -2) {
                        if (readUnsignedByte4 == 34) {
                            _getByteArrayBuilder.append(i >> 4);
                            if (base64Variant.usesPadding()) {
                                _handleBase64MissingPadding(base64Variant);
                            }
                            return _getByteArrayBuilder.toByteArray();
                        }
                        decodeBase64Char3 = _decodeBase64Escape(base64Variant, readUnsignedByte4, 2);
                    }
                    if (decodeBase64Char3 == -2) {
                        readUnsignedByte = this._inputData.readUnsignedByte();
                        if (base64Variant.usesPaddingChar(readUnsignedByte) || (readUnsignedByte == 92 && _decodeBase64Escape(base64Variant, readUnsignedByte, 3) == -2)) {
                            _getByteArrayBuilder.append(i >> 4);
                        }
                    }
                }
                int i2 = (i << 6) | decodeBase64Char3;
                int readUnsignedByte5 = this._inputData.readUnsignedByte();
                int decodeBase64Char4 = base64Variant.decodeBase64Char(readUnsignedByte5);
                if (decodeBase64Char4 < 0) {
                    if (decodeBase64Char4 != -2) {
                        if (readUnsignedByte5 == 34) {
                            _getByteArrayBuilder.appendTwoBytes(i2 >> 2);
                            if (base64Variant.usesPadding()) {
                                _handleBase64MissingPadding(base64Variant);
                            }
                            return _getByteArrayBuilder.toByteArray();
                        }
                        decodeBase64Char4 = _decodeBase64Escape(base64Variant, readUnsignedByte5, 3);
                    }
                    if (decodeBase64Char4 == -2) {
                        _getByteArrayBuilder.appendTwoBytes(i2 >> 2);
                    }
                }
                _getByteArrayBuilder.appendThreeBytes((i2 << 6) | decodeBase64Char4);
            }
        }
        throw reportInvalidBase64Char(base64Variant, readUnsignedByte, 3, "expected padding character '" + base64Variant.getPaddingChar() + LrcRow.SINGLE_QUOTE);
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
    public JsonLocation getTokenLocation() {
        return new JsonLocation(_getSourceReference(), -1L, -1L, this._tokenInputRow, -1);
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase, com.fasterxml.jackson.core.JsonParser
    public JsonLocation getCurrentLocation() {
        return new JsonLocation(_getSourceReference(), -1L, -1L, this._currInputRow, -1);
    }

    private void h(int i) throws JsonParseException {
        if (i == 93) {
            if (!this._parsingContext.inArray()) {
                _reportMismatchedEndMarker(i, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (i == 125) {
            if (!this._parsingContext.inObject()) {
                _reportMismatchedEndMarker(i, ']');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_OBJECT;
        }
    }
}
