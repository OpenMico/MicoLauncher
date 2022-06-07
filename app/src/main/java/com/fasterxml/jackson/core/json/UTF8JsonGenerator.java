package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import io.netty.handler.codec.http.HttpConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class UTF8JsonGenerator extends JsonGeneratorImpl {
    private static final byte[] a = CharTypes.copyHexBytes();
    private static final byte[] b = {110, 117, 108, 108};
    private static final byte[] c = {116, 114, 117, 101};
    private static final byte[] d = {102, 97, 108, 115, 101};
    protected boolean _bufferRecyclable;
    protected char[] _charBuffer;
    protected final int _charBufferLength;
    protected byte[] _entityBuffer;
    protected byte[] _outputBuffer;
    protected final int _outputEnd;
    protected final int _outputMaxContiguous;
    protected final OutputStream _outputStream;
    protected int _outputTail;
    protected byte _quoteChar;

    public UTF8JsonGenerator(IOContext iOContext, int i, ObjectCodec objectCodec, OutputStream outputStream) {
        super(iOContext, i, objectCodec);
        this._quoteChar = (byte) 34;
        this._outputStream = outputStream;
        this._bufferRecyclable = true;
        this._outputBuffer = iOContext.allocWriteEncodingBuffer();
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = iOContext.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
        if (isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
            setHighestNonEscapedChar(127);
        }
    }

    public UTF8JsonGenerator(IOContext iOContext, int i, ObjectCodec objectCodec, OutputStream outputStream, byte[] bArr, int i2, boolean z) {
        super(iOContext, i, objectCodec);
        this._quoteChar = (byte) 34;
        this._outputStream = outputStream;
        this._bufferRecyclable = z;
        this._outputTail = i2;
        this._outputBuffer = bArr;
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = iOContext.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public Object getOutputTarget() {
        return this._outputStream;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public int getOutputBuffered() {
        return this._outputTail;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(String str) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            _writePPFieldName(str);
            return;
        }
        int writeFieldName = this._writeContext.writeFieldName(str);
        if (writeFieldName == 4) {
            _reportError("Can not write a field name, expecting a value");
        }
        if (writeFieldName == 1) {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            bArr[i] = HttpConstants.COMMA;
        }
        if (this._cfgUnqNames) {
            a(str, false);
            return;
        }
        int length = str.length();
        if (length > this._charBufferLength) {
            a(str, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr2[i2] = this._quoteChar;
        if (length <= this._outputMaxContiguous) {
            if (this._outputTail + length > this._outputEnd) {
                _flushBuffer();
            }
            b(str, 0, length);
        } else {
            a(str, 0, length);
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr3 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr3[i3] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(SerializableString serializableString) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            _writePPFieldName(serializableString);
            return;
        }
        int writeFieldName = this._writeContext.writeFieldName(serializableString.getValue());
        if (writeFieldName == 4) {
            _reportError("Can not write a field name, expecting a value");
        }
        if (writeFieldName == 1) {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            bArr[i] = HttpConstants.COMMA;
        }
        if (this._cfgUnqNames) {
            a(serializableString);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr2[i2] = this._quoteChar;
        int appendQuotedUTF8 = serializableString.appendQuotedUTF8(bArr2, this._outputTail);
        if (appendQuotedUTF8 < 0) {
            a(serializableString.asQuotedUTF8());
        } else {
            this._outputTail += appendQuotedUTF8;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr3 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr3[i3] = this._quoteChar;
    }

    private final void a(SerializableString serializableString) throws IOException {
        int appendQuotedUTF8 = serializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (appendQuotedUTF8 < 0) {
            a(serializableString.asQuotedUTF8());
        } else {
            this._outputTail += appendQuotedUTF8;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeStartArray() throws IOException {
        _verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = 91;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeEndArray() throws IOException {
        if (!this._writeContext.inArray()) {
            _reportError("Current context not Array but " + this._writeContext.typeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            bArr[i] = 93;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeStartObject() throws IOException {
        _verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = 123;
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public void writeStartObject(Object obj) throws IOException {
        _verifyValueWrite("start an object");
        JsonWriteContext createChildObjectContext = this._writeContext.createChildObjectContext();
        this._writeContext = createChildObjectContext;
        if (obj != null) {
            createChildObjectContext.setCurrentValue(obj);
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = 123;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeEndObject() throws IOException {
        if (!this._writeContext.inObject()) {
            _reportError("Current context not Object but " + this._writeContext.typeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            bArr[i] = 125;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }

    protected final void _writePPFieldName(String str) throws IOException {
        int writeFieldName = this._writeContext.writeFieldName(str);
        if (writeFieldName == 4) {
            _reportError("Can not write a field name, expecting a value");
        }
        if (writeFieldName == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            a(str, false);
            return;
        }
        int length = str.length();
        if (length > this._charBufferLength) {
            a(str, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = this._quoteChar;
        str.getChars(0, length, this._charBuffer, 0);
        if (length <= this._outputMaxContiguous) {
            if (this._outputTail + length > this._outputEnd) {
                _flushBuffer();
            }
            d(this._charBuffer, 0, length);
        } else {
            c(this._charBuffer, 0, length);
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr2[i2] = this._quoteChar;
    }

    protected final void _writePPFieldName(SerializableString serializableString) throws IOException {
        int writeFieldName = this._writeContext.writeFieldName(serializableString.getValue());
        if (writeFieldName == 4) {
            _reportError("Can not write a field name, expecting a value");
        }
        if (writeFieldName == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        boolean z = !this._cfgUnqNames;
        if (z) {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            bArr[i] = this._quoteChar;
        }
        a(serializableString.asQuotedUTF8());
        if (z) {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr2 = this._outputBuffer;
            int i2 = this._outputTail;
            this._outputTail = i2 + 1;
            bArr2[i2] = this._quoteChar;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(String str) throws IOException {
        _verifyValueWrite("write a string");
        if (str == null) {
            a();
            return;
        }
        int length = str.length();
        if (length > this._outputMaxContiguous) {
            a(str, true);
            return;
        }
        if (this._outputTail + length >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = this._quoteChar;
        b(str, 0, length);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr2[i2] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(Reader reader, int i) throws IOException {
        _verifyValueWrite("write a string");
        if (reader == null) {
            _reportError("null reader");
        }
        int i2 = i >= 0 ? i : Integer.MAX_VALUE;
        char[] cArr = this._charBuffer;
        if (this._outputTail + i >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr[i3] = this._quoteChar;
        while (i2 > 0) {
            int read = reader.read(cArr, 0, Math.min(i2, cArr.length));
            if (read <= 0) {
                break;
            }
            if (this._outputTail + i >= this._outputEnd) {
                _flushBuffer();
            }
            c(cArr, 0, read);
            i2 -= read;
        }
        if (this._outputTail + i >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i4 = this._outputTail;
        this._outputTail = i4 + 1;
        bArr2[i4] = this._quoteChar;
        if (i2 > 0 && i >= 0) {
            _reportError("Didn't read enough from reader");
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(char[] cArr, int i, int i2) throws IOException {
        _verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr[i3] = this._quoteChar;
        if (i2 <= this._outputMaxContiguous) {
            if (this._outputTail + i2 > this._outputEnd) {
                _flushBuffer();
            }
            d(cArr, i, i2);
        } else {
            c(cArr, i, i2);
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i4 = this._outputTail;
        this._outputTail = i4 + 1;
        bArr2[i4] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public final void writeString(SerializableString serializableString) throws IOException {
        _verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = this._quoteChar;
        int appendQuotedUTF8 = serializableString.appendQuotedUTF8(bArr, this._outputTail);
        if (appendQuotedUTF8 < 0) {
            a(serializableString.asQuotedUTF8());
        } else {
            this._outputTail += appendQuotedUTF8;
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr2[i2] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException {
        _verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr2[i3] = this._quoteChar;
        a(bArr, i, i2);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr3 = this._outputBuffer;
        int i4 = this._outputTail;
        this._outputTail = i4 + 1;
        bArr3[i4] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeUTF8String(byte[] bArr, int i, int i2) throws IOException {
        _verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr2[i3] = this._quoteChar;
        if (i2 <= this._outputMaxContiguous) {
            c(bArr, i, i2);
        } else {
            b(bArr, i, i2);
        }
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr3 = this._outputBuffer;
        int i4 = this._outputTail;
        this._outputTail = i4 + 1;
        bArr3[i4] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String str) throws IOException {
        int length = str.length();
        char[] cArr = this._charBuffer;
        if (length <= cArr.length) {
            str.getChars(0, length, cArr, 0);
            writeRaw(cArr, 0, length);
            return;
        }
        writeRaw(str, 0, length);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String str, int i, int i2) throws IOException {
        char c2;
        char[] cArr = this._charBuffer;
        int length = cArr.length;
        if (i2 <= length) {
            str.getChars(i, i + i2, cArr, 0);
            writeRaw(cArr, 0, i2);
            return;
        }
        int i3 = this._outputEnd;
        int min = Math.min(length, (i3 >> 2) + (i3 >> 4));
        int i4 = min * 3;
        while (i2 > 0) {
            int min2 = Math.min(min, i2);
            str.getChars(i, i + min2, cArr, 0);
            if (this._outputTail + i4 > this._outputEnd) {
                _flushBuffer();
            }
            if (min2 > 1 && (c2 = cArr[min2 - 1]) >= 55296 && c2 <= 56319) {
                min2--;
            }
            b(cArr, 0, min2);
            i += min2;
            i2 -= min2;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(SerializableString serializableString) throws IOException {
        byte[] asUnquotedUTF8 = serializableString.asUnquotedUTF8();
        if (asUnquotedUTF8.length > 0) {
            a(asUnquotedUTF8);
        }
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(SerializableString serializableString) throws IOException {
        _verifyValueWrite("write a raw (unencoded) value");
        byte[] asUnquotedUTF8 = serializableString.asUnquotedUTF8();
        if (asUnquotedUTF8.length > 0) {
            a(asUnquotedUTF8);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001c, code lost:
        r0 = r6 + 1;
        r6 = r5[r6];
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:
        if (r6 >= 2048) goto L_0x0042;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0024, code lost:
        r1 = r4._outputBuffer;
        r2 = r4._outputTail;
        r4._outputTail = r2 + 1;
        r1[r2] = (byte) ((r6 >> 6) | 192);
        r2 = r4._outputTail;
        r4._outputTail = r2 + 1;
        r1[r2] = (byte) ((r6 & '?') | 128);
        r6 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0042, code lost:
        r6 = a(r6, r5, r0, r7);
     */
    @Override // com.fasterxml.jackson.core.JsonGenerator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeRaw(char[] r5, int r6, int r7) throws java.io.IOException {
        /*
            r4 = this;
            int r0 = r7 + r7
            int r0 = r0 + r7
            int r1 = r4._outputTail
            int r1 = r1 + r0
            int r2 = r4._outputEnd
            if (r1 <= r2) goto L_0x0013
            if (r2 >= r0) goto L_0x0010
            r4.a(r5, r6, r7)
            return
        L_0x0010:
            r4._flushBuffer()
        L_0x0013:
            int r7 = r7 + r6
        L_0x0014:
            if (r6 >= r7) goto L_0x0056
        L_0x0016:
            char r0 = r5[r6]
            r1 = 127(0x7f, float:1.78E-43)
            if (r0 <= r1) goto L_0x0047
            int r0 = r6 + 1
            char r6 = r5[r6]
            r1 = 2048(0x800, float:2.87E-42)
            if (r6 >= r1) goto L_0x0042
            byte[] r1 = r4._outputBuffer
            int r2 = r4._outputTail
            int r3 = r2 + 1
            r4._outputTail = r3
            int r3 = r6 >> 6
            r3 = r3 | 192(0xc0, float:2.69E-43)
            byte r3 = (byte) r3
            r1[r2] = r3
            int r2 = r4._outputTail
            int r3 = r2 + 1
            r4._outputTail = r3
            r6 = r6 & 63
            r6 = r6 | 128(0x80, float:1.794E-43)
            byte r6 = (byte) r6
            r1[r2] = r6
            r6 = r0
            goto L_0x0014
        L_0x0042:
            int r6 = r4.a(r6, r5, r0, r7)
            goto L_0x0014
        L_0x0047:
            byte[] r1 = r4._outputBuffer
            int r2 = r4._outputTail
            int r3 = r2 + 1
            r4._outputTail = r3
            byte r0 = (byte) r0
            r1[r2] = r0
            int r6 = r6 + 1
            if (r6 < r7) goto L_0x0016
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8JsonGenerator.writeRaw(char[], int, int):void");
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char c2) throws IOException {
        if (this._outputTail + 3 >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        if (c2 <= 127) {
            int i = this._outputTail;
            this._outputTail = i + 1;
            bArr[i] = (byte) c2;
        } else if (c2 < 2048) {
            int i2 = this._outputTail;
            this._outputTail = i2 + 1;
            bArr[i2] = (byte) ((c2 >> 6) | 192);
            int i3 = this._outputTail;
            this._outputTail = i3 + 1;
            bArr[i3] = (byte) ((c2 & '?') | 128);
        } else {
            a(c2, (char[]) null, 0, 0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001e, code lost:
        if (r8 >= 2048) goto L_0x003b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0020, code lost:
        r4 = r6._outputTail;
        r6._outputTail = r4 + 1;
        r1[r4] = (byte) ((r8 >> 6) | 192);
        r4 = r6._outputTail;
        r6._outputTail = r4 + 1;
        r1[r4] = (byte) ((r8 & '?') | 128);
        r8 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003b, code lost:
        r8 = a(r8, r7, r2, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0013, code lost:
        if ((r6._outputTail + 3) < r6._outputEnd) goto L_0x0018;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0015, code lost:
        _flushBuffer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0018, code lost:
        r2 = r8 + 1;
        r8 = r7[r8];
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void a(char[] r7, int r8, int r9) throws java.io.IOException {
        /*
            r6 = this;
            int r0 = r6._outputEnd
            byte[] r1 = r6._outputBuffer
            int r9 = r9 + r8
        L_0x0005:
            if (r8 >= r9) goto L_0x0054
        L_0x0007:
            char r2 = r7[r8]
            r3 = 128(0x80, float:1.794E-43)
            if (r2 < r3) goto L_0x0040
            int r2 = r6._outputTail
            int r2 = r2 + 3
            int r4 = r6._outputEnd
            if (r2 < r4) goto L_0x0018
            r6._flushBuffer()
        L_0x0018:
            int r2 = r8 + 1
            char r8 = r7[r8]
            r4 = 2048(0x800, float:2.87E-42)
            if (r8 >= r4) goto L_0x003b
            int r4 = r6._outputTail
            int r5 = r4 + 1
            r6._outputTail = r5
            int r5 = r8 >> 6
            r5 = r5 | 192(0xc0, float:2.69E-43)
            byte r5 = (byte) r5
            r1[r4] = r5
            int r4 = r6._outputTail
            int r5 = r4 + 1
            r6._outputTail = r5
            r8 = r8 & 63
            r8 = r8 | r3
            byte r8 = (byte) r8
            r1[r4] = r8
            r8 = r2
            goto L_0x0005
        L_0x003b:
            int r8 = r6.a(r8, r7, r2, r9)
            goto L_0x0005
        L_0x0040:
            int r3 = r6._outputTail
            if (r3 < r0) goto L_0x0047
            r6._flushBuffer()
        L_0x0047:
            int r3 = r6._outputTail
            int r4 = r3 + 1
            r6._outputTail = r4
            byte r2 = (byte) r2
            r1[r3] = r2
            int r8 = r8 + 1
            if (r8 < r9) goto L_0x0007
        L_0x0054:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8JsonGenerator.a(char[], int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0008, code lost:
        r0 = r6 + 1;
        r6 = r5[r6];
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x000e, code lost:
        if (r6 >= 2048) goto L_0x002e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0010, code lost:
        r1 = r4._outputBuffer;
        r2 = r4._outputTail;
        r4._outputTail = r2 + 1;
        r1[r2] = (byte) ((r6 >> 6) | 192);
        r2 = r4._outputTail;
        r4._outputTail = r2 + 1;
        r1[r2] = (byte) ((r6 & '?') | 128);
        r6 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x002e, code lost:
        r6 = a(r6, r5, r0, r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(char[] r5, int r6, int r7) throws java.io.IOException {
        /*
            r4 = this;
        L_0x0000:
            if (r6 >= r7) goto L_0x0042
        L_0x0002:
            char r0 = r5[r6]
            r1 = 127(0x7f, float:1.78E-43)
            if (r0 <= r1) goto L_0x0033
            int r0 = r6 + 1
            char r6 = r5[r6]
            r1 = 2048(0x800, float:2.87E-42)
            if (r6 >= r1) goto L_0x002e
            byte[] r1 = r4._outputBuffer
            int r2 = r4._outputTail
            int r3 = r2 + 1
            r4._outputTail = r3
            int r3 = r6 >> 6
            r3 = r3 | 192(0xc0, float:2.69E-43)
            byte r3 = (byte) r3
            r1[r2] = r3
            int r2 = r4._outputTail
            int r3 = r2 + 1
            r4._outputTail = r3
            r6 = r6 & 63
            r6 = r6 | 128(0x80, float:1.794E-43)
            byte r6 = (byte) r6
            r1[r2] = r6
            r6 = r0
            goto L_0x0000
        L_0x002e:
            int r6 = r4.a(r6, r5, r0, r7)
            goto L_0x0000
        L_0x0033:
            byte[] r1 = r4._outputBuffer
            int r2 = r4._outputTail
            int r3 = r2 + 1
            r4._outputTail = r3
            byte r0 = (byte) r0
            r1[r2] = r0
            int r6 = r6 + 1
            if (r6 < r7) goto L_0x0002
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.UTF8JsonGenerator.b(char[], int, int):void");
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException, JsonGenerationException {
        _verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr2[i3] = this._quoteChar;
        _writeBinary(base64Variant, bArr, i, i2 + i);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr3 = this._outputBuffer;
        int i4 = this._outputTail;
        this._outputTail = i4 + 1;
        bArr3[i4] = this._quoteChar;
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException, JsonGenerationException {
        _verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr[i2] = this._quoteChar;
        byte[] allocBase64Buffer = this._ioContext.allocBase64Buffer();
        try {
            if (i < 0) {
                i = _writeBinary(base64Variant, inputStream, allocBase64Buffer);
            } else {
                int _writeBinary = _writeBinary(base64Variant, inputStream, allocBase64Buffer, i);
                if (_writeBinary > 0) {
                    _reportError("Too few bytes available: missing " + _writeBinary + " bytes (out of " + i + ")");
                }
            }
            this._ioContext.releaseBase64Buffer(allocBase64Buffer);
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr2 = this._outputBuffer;
            int i3 = this._outputTail;
            this._outputTail = i3 + 1;
            bArr2[i3] = this._quoteChar;
            return i;
        } catch (Throwable th) {
            this._ioContext.releaseBase64Buffer(allocBase64Buffer);
            throw th;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(short s) throws IOException {
        _verifyValueWrite("write a number");
        if (this._outputTail + 6 >= this._outputEnd) {
            _flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            a(s);
        } else {
            this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
        }
    }

    private final void a(short s) throws IOException {
        if (this._outputTail + 8 >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = this._quoteChar;
        this._outputTail = NumberOutput.outputInt(s, bArr, this._outputTail);
        byte[] bArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr2[i2] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(int i) throws IOException {
        _verifyValueWrite("write a number");
        if (this._outputTail + 11 >= this._outputEnd) {
            _flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            a(i);
        } else {
            this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
        }
    }

    private final void a(int i) throws IOException {
        if (this._outputTail + 13 >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr[i2] = this._quoteChar;
        this._outputTail = NumberOutput.outputInt(i, bArr, this._outputTail);
        byte[] bArr2 = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr2[i3] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(long j) throws IOException {
        _verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            a(j);
            return;
        }
        if (this._outputTail + 21 >= this._outputEnd) {
            _flushBuffer();
        }
        this._outputTail = NumberOutput.outputLong(j, this._outputBuffer, this._outputTail);
    }

    private final void a(long j) throws IOException {
        if (this._outputTail + 23 >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = this._quoteChar;
        this._outputTail = NumberOutput.outputLong(j, bArr, this._outputTail);
        byte[] bArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr2[i2] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigInteger bigInteger) throws IOException {
        _verifyValueWrite("write a number");
        if (bigInteger == null) {
            a();
        } else if (this._cfgNumbersAsStrings) {
            a(bigInteger.toString());
        } else {
            writeRaw(bigInteger.toString());
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(double d2) throws IOException {
        if (this._cfgNumbersAsStrings || ((Double.isNaN(d2) || Double.isInfinite(d2)) && JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
            writeString(String.valueOf(d2));
            return;
        }
        _verifyValueWrite("write a number");
        writeRaw(String.valueOf(d2));
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(float f) throws IOException {
        if (this._cfgNumbersAsStrings || ((Float.isNaN(f) || Float.isInfinite(f)) && JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
            writeString(String.valueOf(f));
            return;
        }
        _verifyValueWrite("write a number");
        writeRaw(String.valueOf(f));
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        _verifyValueWrite("write a number");
        if (bigDecimal == null) {
            a();
        } else if (this._cfgNumbersAsStrings) {
            a(_asString(bigDecimal));
        } else {
            writeRaw(_asString(bigDecimal));
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(String str) throws IOException {
        _verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            a(str);
        } else {
            writeRaw(str);
        }
    }

    private final void a(String str) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i = this._outputTail;
        this._outputTail = i + 1;
        bArr[i] = this._quoteChar;
        writeRaw(str);
        if (this._outputTail >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr2 = this._outputBuffer;
        int i2 = this._outputTail;
        this._outputTail = i2 + 1;
        bArr2[i2] = this._quoteChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeBoolean(boolean z) throws IOException {
        _verifyValueWrite("write a boolean value");
        if (this._outputTail + 5 >= this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = z ? c : d;
        int length = bArr.length;
        System.arraycopy(bArr, 0, this._outputBuffer, this._outputTail, length);
        this._outputTail += length;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNull() throws IOException {
        _verifyValueWrite("write a null");
        a();
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase
    protected final void _verifyValueWrite(String str) throws IOException {
        byte b2;
        int writeValue = this._writeContext.writeValue();
        if (this._cfgPrettyPrinter != null) {
            _verifyPrettyValueWrite(str, writeValue);
        } else if (writeValue != 5) {
            switch (writeValue) {
                case 1:
                    b2 = HttpConstants.COMMA;
                    break;
                case 2:
                    b2 = 58;
                    break;
                case 3:
                    if (this._rootValueSeparator != null) {
                        byte[] asUnquotedUTF8 = this._rootValueSeparator.asUnquotedUTF8();
                        if (asUnquotedUTF8.length > 0) {
                            a(asUnquotedUTF8);
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            bArr[i] = b2;
        } else {
            _reportCantWriteValueExpectName(str);
        }
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, java.io.Flushable
    public void flush() throws IOException {
        _flushBuffer();
        if (this._outputStream != null && isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
            this._outputStream.flush();
        }
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        if (this._outputBuffer != null && isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
            while (true) {
                JsonStreamContext outputContext = getOutputContext();
                if (!outputContext.inArray()) {
                    if (!outputContext.inObject()) {
                        break;
                    }
                    writeEndObject();
                } else {
                    writeEndArray();
                }
            }
        }
        _flushBuffer();
        this._outputTail = 0;
        if (this._outputStream != null) {
            if (this._ioContext.isResourceManaged() || isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                this._outputStream.close();
            } else if (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                this._outputStream.flush();
            }
        }
        _releaseBuffers();
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase
    protected void _releaseBuffers() {
        byte[] bArr = this._outputBuffer;
        if (bArr != null && this._bufferRecyclable) {
            this._outputBuffer = null;
            this._ioContext.releaseWriteEncodingBuffer(bArr);
        }
        char[] cArr = this._charBuffer;
        if (cArr != null) {
            this._charBuffer = null;
            this._ioContext.releaseConcatBuffer(cArr);
        }
    }

    private final void a(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this._outputTail + length > this._outputEnd) {
            _flushBuffer();
            if (length > 512) {
                this._outputStream.write(bArr, 0, length);
                return;
            }
        }
        System.arraycopy(bArr, 0, this._outputBuffer, this._outputTail, length);
        this._outputTail += length;
    }

    private final void a(byte[] bArr, int i, int i2) throws IOException {
        if (this._outputTail + i2 > this._outputEnd) {
            _flushBuffer();
            if (i2 > 512) {
                this._outputStream.write(bArr, i, i2);
                return;
            }
        }
        System.arraycopy(bArr, i, this._outputBuffer, this._outputTail, i2);
        this._outputTail += i2;
    }

    private final void a(String str, boolean z) throws IOException {
        if (z) {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr = this._outputBuffer;
            int i = this._outputTail;
            this._outputTail = i + 1;
            bArr[i] = this._quoteChar;
        }
        int length = str.length();
        int i2 = 0;
        while (length > 0) {
            int min = Math.min(this._outputMaxContiguous, length);
            if (this._outputTail + min > this._outputEnd) {
                _flushBuffer();
            }
            b(str, i2, min);
            i2 += min;
            length -= min;
        }
        if (z) {
            if (this._outputTail >= this._outputEnd) {
                _flushBuffer();
            }
            byte[] bArr2 = this._outputBuffer;
            int i3 = this._outputTail;
            this._outputTail = i3 + 1;
            bArr2[i3] = this._quoteChar;
        }
    }

    private final void c(char[] cArr, int i, int i2) throws IOException {
        do {
            int min = Math.min(this._outputMaxContiguous, i2);
            if (this._outputTail + min > this._outputEnd) {
                _flushBuffer();
            }
            d(cArr, i, min);
            i += min;
            i2 -= min;
        } while (i2 > 0);
    }

    private final void a(String str, int i, int i2) throws IOException {
        do {
            int min = Math.min(this._outputMaxContiguous, i2);
            if (this._outputTail + min > this._outputEnd) {
                _flushBuffer();
            }
            b(str, i, min);
            i += min;
            i2 -= min;
        } while (i2 > 0);
    }

    private final void d(char[] cArr, int i, int i2) throws IOException {
        int i3 = i2 + i;
        int i4 = this._outputTail;
        byte[] bArr = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        while (i < i3) {
            char c2 = cArr[i];
            if (c2 > 127 || iArr[c2] != 0) {
                break;
            }
            i4++;
            bArr[i4] = (byte) c2;
            i++;
        }
        this._outputTail = i4;
        if (i >= i3) {
            return;
        }
        if (this._characterEscapes != null) {
            g(cArr, i, i3);
        } else if (this._maximumNonEscapedChar == 0) {
            e(cArr, i, i3);
        } else {
            f(cArr, i, i3);
        }
    }

    private final void b(String str, int i, int i2) throws IOException {
        int i3 = i2 + i;
        int i4 = this._outputTail;
        byte[] bArr = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        while (i < i3) {
            char charAt = str.charAt(i);
            if (charAt > 127 || iArr[charAt] != 0) {
                break;
            }
            i4++;
            bArr[i4] = (byte) charAt;
            i++;
        }
        this._outputTail = i4;
        if (i >= i3) {
            return;
        }
        if (this._characterEscapes != null) {
            e(str, i, i3);
        } else if (this._maximumNonEscapedChar == 0) {
            c(str, i, i3);
        } else {
            d(str, i, i3);
        }
    }

    private final void e(char[] cArr, int i, int i2) throws IOException {
        if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
            _flushBuffer();
        }
        int i3 = this._outputTail;
        byte[] bArr = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        while (i < i2) {
            int i4 = i + 1;
            char c2 = cArr[i];
            if (c2 > 127) {
                if (c2 <= 2047) {
                    int i5 = i3 + 1;
                    bArr[i3] = (byte) ((c2 >> 6) | 192);
                    i3 = i5 + 1;
                    bArr[i5] = (byte) ((c2 & '?') | 128);
                } else {
                    i3 = a(c2, i3);
                }
                i = i4;
            } else if (iArr[c2] == 0) {
                i3++;
                bArr[i3] = (byte) c2;
                i = i4;
            } else {
                int i6 = iArr[c2];
                if (i6 > 0) {
                    int i7 = i3 + 1;
                    bArr[i3] = 92;
                    i3 = i7 + 1;
                    bArr[i7] = (byte) i6;
                    i = i4;
                } else {
                    i3 = b(c2, i3);
                    i = i4;
                }
            }
        }
        this._outputTail = i3;
    }

    private final void c(String str, int i, int i2) throws IOException {
        if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
            _flushBuffer();
        }
        int i3 = this._outputTail;
        byte[] bArr = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        while (i < i2) {
            int i4 = i + 1;
            char charAt = str.charAt(i);
            if (charAt > 127) {
                if (charAt <= 2047) {
                    int i5 = i3 + 1;
                    bArr[i3] = (byte) ((charAt >> 6) | 192);
                    i3 = i5 + 1;
                    bArr[i5] = (byte) ((charAt & '?') | 128);
                } else {
                    i3 = a(charAt, i3);
                }
                i = i4;
            } else if (iArr[charAt] == 0) {
                i3++;
                bArr[i3] = (byte) charAt;
                i = i4;
            } else {
                int i6 = iArr[charAt];
                if (i6 > 0) {
                    int i7 = i3 + 1;
                    bArr[i3] = 92;
                    i3 = i7 + 1;
                    bArr[i7] = (byte) i6;
                    i = i4;
                } else {
                    i3 = b(charAt, i3);
                    i = i4;
                }
            }
        }
        this._outputTail = i3;
    }

    private final void f(char[] cArr, int i, int i2) throws IOException {
        if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
            _flushBuffer();
        }
        int i3 = this._outputTail;
        byte[] bArr = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        int i4 = this._maximumNonEscapedChar;
        while (i < i2) {
            int i5 = i + 1;
            char c2 = cArr[i];
            if (c2 <= 127) {
                if (iArr[c2] == 0) {
                    i3++;
                    bArr[i3] = (byte) c2;
                    i = i5;
                } else {
                    int i6 = iArr[c2];
                    if (i6 > 0) {
                        int i7 = i3 + 1;
                        bArr[i3] = 92;
                        i3 = i7 + 1;
                        bArr[i7] = (byte) i6;
                        i = i5;
                    } else {
                        i3 = b(c2, i3);
                        i = i5;
                    }
                }
            } else if (c2 > i4) {
                i3 = b(c2, i3);
                i = i5;
            } else {
                if (c2 <= 2047) {
                    int i8 = i3 + 1;
                    bArr[i3] = (byte) ((c2 >> 6) | 192);
                    i3 = i8 + 1;
                    bArr[i8] = (byte) ((c2 & '?') | 128);
                } else {
                    i3 = a(c2, i3);
                }
                i = i5;
            }
        }
        this._outputTail = i3;
    }

    private final void d(String str, int i, int i2) throws IOException {
        if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
            _flushBuffer();
        }
        int i3 = this._outputTail;
        byte[] bArr = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        int i4 = this._maximumNonEscapedChar;
        while (i < i2) {
            int i5 = i + 1;
            char charAt = str.charAt(i);
            if (charAt <= 127) {
                if (iArr[charAt] == 0) {
                    i3++;
                    bArr[i3] = (byte) charAt;
                    i = i5;
                } else {
                    int i6 = iArr[charAt];
                    if (i6 > 0) {
                        int i7 = i3 + 1;
                        bArr[i3] = 92;
                        i3 = i7 + 1;
                        bArr[i7] = (byte) i6;
                        i = i5;
                    } else {
                        i3 = b(charAt, i3);
                        i = i5;
                    }
                }
            } else if (charAt > i4) {
                i3 = b(charAt, i3);
                i = i5;
            } else {
                if (charAt <= 2047) {
                    int i8 = i3 + 1;
                    bArr[i3] = (byte) ((charAt >> 6) | 192);
                    i3 = i8 + 1;
                    bArr[i8] = (byte) ((charAt & '?') | 128);
                } else {
                    i3 = a(charAt, i3);
                }
                i = i5;
            }
        }
        this._outputTail = i3;
    }

    private final void g(char[] cArr, int i, int i2) throws IOException {
        if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
            _flushBuffer();
        }
        int i3 = this._outputTail;
        byte[] bArr = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        int i4 = this._maximumNonEscapedChar <= 0 ? 65535 : this._maximumNonEscapedChar;
        CharacterEscapes characterEscapes = this._characterEscapes;
        while (i < i2) {
            int i5 = i + 1;
            char c2 = cArr[i];
            if (c2 <= 127) {
                if (iArr[c2] == 0) {
                    i3++;
                    bArr[i3] = (byte) c2;
                    i = i5;
                } else {
                    int i6 = iArr[c2];
                    if (i6 > 0) {
                        int i7 = i3 + 1;
                        bArr[i3] = 92;
                        i3 = i7 + 1;
                        bArr[i7] = (byte) i6;
                        i = i5;
                    } else if (i6 == -2) {
                        SerializableString escapeSequence = characterEscapes.getEscapeSequence(c2);
                        if (escapeSequence == null) {
                            _reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(c2) + ", although was supposed to have one");
                        }
                        i3 = a(bArr, i3, escapeSequence, i2 - i5);
                        i = i5;
                    } else {
                        i3 = b(c2, i3);
                        i = i5;
                    }
                }
            } else if (c2 > i4) {
                i3 = b(c2, i3);
                i = i5;
            } else {
                SerializableString escapeSequence2 = characterEscapes.getEscapeSequence(c2);
                if (escapeSequence2 != null) {
                    i3 = a(bArr, i3, escapeSequence2, i2 - i5);
                    i = i5;
                } else {
                    if (c2 <= 2047) {
                        int i8 = i3 + 1;
                        bArr[i3] = (byte) ((c2 >> 6) | 192);
                        i3 = i8 + 1;
                        bArr[i8] = (byte) ((c2 & '?') | 128);
                    } else {
                        i3 = a(c2, i3);
                    }
                    i = i5;
                }
            }
        }
        this._outputTail = i3;
    }

    private final void e(String str, int i, int i2) throws IOException {
        if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
            _flushBuffer();
        }
        int i3 = this._outputTail;
        byte[] bArr = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        int i4 = this._maximumNonEscapedChar <= 0 ? 65535 : this._maximumNonEscapedChar;
        CharacterEscapes characterEscapes = this._characterEscapes;
        while (i < i2) {
            int i5 = i + 1;
            char charAt = str.charAt(i);
            if (charAt <= 127) {
                if (iArr[charAt] == 0) {
                    i3++;
                    bArr[i3] = (byte) charAt;
                    i = i5;
                } else {
                    int i6 = iArr[charAt];
                    if (i6 > 0) {
                        int i7 = i3 + 1;
                        bArr[i3] = 92;
                        i3 = i7 + 1;
                        bArr[i7] = (byte) i6;
                        i = i5;
                    } else if (i6 == -2) {
                        SerializableString escapeSequence = characterEscapes.getEscapeSequence(charAt);
                        if (escapeSequence == null) {
                            _reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(charAt) + ", although was supposed to have one");
                        }
                        i3 = a(bArr, i3, escapeSequence, i2 - i5);
                        i = i5;
                    } else {
                        i3 = b(charAt, i3);
                        i = i5;
                    }
                }
            } else if (charAt > i4) {
                i3 = b(charAt, i3);
                i = i5;
            } else {
                SerializableString escapeSequence2 = characterEscapes.getEscapeSequence(charAt);
                if (escapeSequence2 != null) {
                    i3 = a(bArr, i3, escapeSequence2, i2 - i5);
                    i = i5;
                } else {
                    if (charAt <= 2047) {
                        int i8 = i3 + 1;
                        bArr[i3] = (byte) ((charAt >> 6) | 192);
                        i3 = i8 + 1;
                        bArr[i8] = (byte) ((charAt & '?') | 128);
                    } else {
                        i3 = a(charAt, i3);
                    }
                    i = i5;
                }
            }
        }
        this._outputTail = i3;
    }

    private final int a(byte[] bArr, int i, SerializableString serializableString, int i2) throws IOException, JsonGenerationException {
        byte[] asUnquotedUTF8 = serializableString.asUnquotedUTF8();
        int length = asUnquotedUTF8.length;
        if (length > 6) {
            return a(bArr, i, this._outputEnd, asUnquotedUTF8, i2);
        }
        System.arraycopy(asUnquotedUTF8, 0, bArr, i, length);
        return i + length;
    }

    private final int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IOException, JsonGenerationException {
        int length = bArr2.length;
        if (i + length > i2) {
            this._outputTail = i;
            _flushBuffer();
            int i4 = this._outputTail;
            if (length > bArr.length) {
                this._outputStream.write(bArr2, 0, length);
                return i4;
            }
            System.arraycopy(bArr2, 0, bArr, i4, length);
            i = i4 + length;
        }
        if ((i3 * 6) + i <= i2) {
            return i;
        }
        _flushBuffer();
        return this._outputTail;
    }

    private final void b(byte[] bArr, int i, int i2) throws IOException, JsonGenerationException {
        do {
            int min = Math.min(this._outputMaxContiguous, i2);
            c(bArr, i, min);
            i += min;
            i2 -= min;
        } while (i2 > 0);
    }

    private final void c(byte[] bArr, int i, int i2) throws IOException, JsonGenerationException {
        int[] iArr = this._outputEscapes;
        int i3 = i + i2;
        int i4 = i;
        while (i4 < i3) {
            i4++;
            byte b2 = bArr[i4];
            if (b2 >= 0 && iArr[b2] != 0) {
                d(bArr, i, i2);
                return;
            }
        }
        if (this._outputTail + i2 > this._outputEnd) {
            _flushBuffer();
        }
        System.arraycopy(bArr, i, this._outputBuffer, this._outputTail, i2);
        this._outputTail += i2;
    }

    private final void d(byte[] bArr, int i, int i2) throws IOException, JsonGenerationException {
        int i3 = this._outputTail;
        if ((i2 * 6) + i3 > this._outputEnd) {
            _flushBuffer();
            i3 = this._outputTail;
        }
        byte[] bArr2 = this._outputBuffer;
        int[] iArr = this._outputEscapes;
        int i4 = i2 + i;
        while (i < i4) {
            int i5 = i + 1;
            byte b2 = bArr[i];
            if (b2 < 0 || iArr[b2] == 0) {
                i3++;
                bArr2[i3] = b2;
                i = i5;
            } else {
                int i6 = iArr[b2];
                if (i6 > 0) {
                    int i7 = i3 + 1;
                    bArr2[i3] = 92;
                    i3 = i7 + 1;
                    bArr2[i7] = (byte) i6;
                } else {
                    i3 = b(b2, i3);
                }
                i = i5;
            }
        }
        this._outputTail = i3;
    }

    protected final void _writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException, JsonGenerationException {
        int i3 = i2 - 3;
        int i4 = this._outputEnd - 6;
        int maxLineLength = base64Variant.getMaxLineLength() >> 2;
        while (i <= i3) {
            if (this._outputTail > i4) {
                _flushBuffer();
            }
            int i5 = i + 1;
            int i6 = i5 + 1;
            int i7 = ((bArr[i] << 8) | (bArr[i5] & 255)) << 8;
            i = i6 + 1;
            this._outputTail = base64Variant.encodeBase64Chunk(i7 | (bArr[i6] & 255), this._outputBuffer, this._outputTail);
            maxLineLength--;
            if (maxLineLength <= 0) {
                byte[] bArr2 = this._outputBuffer;
                int i8 = this._outputTail;
                this._outputTail = i8 + 1;
                bArr2[i8] = 92;
                int i9 = this._outputTail;
                this._outputTail = i9 + 1;
                bArr2[i9] = 110;
                maxLineLength = base64Variant.getMaxLineLength() >> 2;
            }
        }
        int i10 = i2 - i;
        if (i10 > 0) {
            if (this._outputTail > i4) {
                _flushBuffer();
            }
            int i11 = i + 1;
            int i12 = bArr[i] << 16;
            if (i10 == 2) {
                i12 |= (bArr[i11] & 255) << 8;
            }
            this._outputTail = base64Variant.encodeBase64Partial(i12, i10, this._outputBuffer, this._outputTail);
        }
    }

    protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] bArr, int i) throws IOException, JsonGenerationException {
        int a2;
        int i2 = this._outputEnd - 6;
        int maxLineLength = base64Variant.getMaxLineLength() >> 2;
        int i3 = -3;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i <= 2) {
                break;
            }
            if (i4 > i3) {
                int a3 = a(inputStream, bArr, i4, i5, i);
                if (a3 < 3) {
                    i5 = a3;
                    i4 = 0;
                    break;
                }
                i3 = a3 - 3;
                i5 = a3;
                i4 = 0;
            }
            if (this._outputTail > i2) {
                _flushBuffer();
            }
            int i6 = i4 + 1;
            int i7 = i6 + 1;
            i4 = i7 + 1;
            i -= 3;
            this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i6] & 255) | (bArr[i4] << 8)) << 8) | (bArr[i7] & 255), this._outputBuffer, this._outputTail);
            maxLineLength--;
            if (maxLineLength <= 0) {
                byte[] bArr2 = this._outputBuffer;
                int i8 = this._outputTail;
                this._outputTail = i8 + 1;
                bArr2[i8] = 92;
                int i9 = this._outputTail;
                this._outputTail = i9 + 1;
                bArr2[i9] = 110;
                maxLineLength = base64Variant.getMaxLineLength() >> 2;
            }
        }
        if (i <= 0 || (a2 = a(inputStream, bArr, i4, i5, i)) <= 0) {
            return i;
        }
        if (this._outputTail > i2) {
            _flushBuffer();
        }
        int i10 = bArr[0] << 16;
        int i11 = 1;
        if (1 < a2) {
            i10 |= (bArr[1] & 255) << 8;
            i11 = 2;
        }
        this._outputTail = base64Variant.encodeBase64Partial(i10, i11, this._outputBuffer, this._outputTail);
        return i - i11;
    }

    protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] bArr) throws IOException, JsonGenerationException {
        int a2;
        int i = this._outputEnd - 6;
        int maxLineLength = base64Variant.getMaxLineLength() >> 2;
        int i2 = -3;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i4 > i2) {
                a2 = a(inputStream, bArr, i4, i5, bArr.length);
                if (a2 < 3) {
                    break;
                }
                i2 = a2 - 3;
                i4 = 0;
                i5 = a2;
            }
            if (this._outputTail > i) {
                _flushBuffer();
            }
            int i6 = i4 + 1;
            int i7 = i6 + 1;
            i4 = i7 + 1;
            i3 += 3;
            this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i6] & 255) | (bArr[i4] << 8)) << 8) | (bArr[i7] & 255), this._outputBuffer, this._outputTail);
            maxLineLength--;
            if (maxLineLength <= 0) {
                byte[] bArr2 = this._outputBuffer;
                int i8 = this._outputTail;
                this._outputTail = i8 + 1;
                bArr2[i8] = 92;
                int i9 = this._outputTail;
                this._outputTail = i9 + 1;
                bArr2[i9] = 110;
                maxLineLength = base64Variant.getMaxLineLength() >> 2;
            }
        }
        if (a2 <= 0) {
            return i3;
        }
        if (this._outputTail > i) {
            _flushBuffer();
        }
        int i10 = bArr[0] << 16;
        int i11 = 1;
        if (1 < a2) {
            i10 |= (bArr[1] & 255) << 8;
            i11 = 2;
        }
        int i12 = i3 + i11;
        this._outputTail = base64Variant.encodeBase64Partial(i10, i11, this._outputBuffer, this._outputTail);
        return i12;
    }

    private final int a(InputStream inputStream, byte[] bArr, int i, int i2, int i3) throws IOException {
        int i4 = 0;
        while (i < i2) {
            i4++;
            i++;
            bArr[i4] = bArr[i];
        }
        int min = Math.min(i3, bArr.length);
        do {
            int i5 = min - i4;
            if (i5 == 0) {
                break;
            }
            int read = inputStream.read(bArr, i4, i5);
            if (read < 0) {
                return i4;
            }
            i4 += read;
        } while (i4 < 3);
        return i4;
    }

    private final int a(int i, char[] cArr, int i2, int i3) throws IOException {
        if (i < 55296 || i > 57343) {
            byte[] bArr = this._outputBuffer;
            int i4 = this._outputTail;
            this._outputTail = i4 + 1;
            bArr[i4] = (byte) ((i >> 12) | 224);
            int i5 = this._outputTail;
            this._outputTail = i5 + 1;
            bArr[i5] = (byte) (((i >> 6) & 63) | 128);
            int i6 = this._outputTail;
            this._outputTail = i6 + 1;
            bArr[i6] = (byte) ((i & 63) | 128);
            return i2;
        }
        if (i2 >= i3 || cArr == null) {
            _reportError(String.format("Split surrogate on writeRaw() input (last character): first character 0x%4x", Integer.valueOf(i)));
        }
        _outputSurrogates(i, cArr[i2]);
        return i2 + 1;
    }

    protected final void _outputSurrogates(int i, int i2) throws IOException {
        int _decodeSurrogate = _decodeSurrogate(i, i2);
        if (this._outputTail + 4 > this._outputEnd) {
            _flushBuffer();
        }
        byte[] bArr = this._outputBuffer;
        int i3 = this._outputTail;
        this._outputTail = i3 + 1;
        bArr[i3] = (byte) ((_decodeSurrogate >> 18) | PsExtractor.VIDEO_STREAM_MASK);
        int i4 = this._outputTail;
        this._outputTail = i4 + 1;
        bArr[i4] = (byte) (((_decodeSurrogate >> 12) & 63) | 128);
        int i5 = this._outputTail;
        this._outputTail = i5 + 1;
        bArr[i5] = (byte) (((_decodeSurrogate >> 6) & 63) | 128);
        int i6 = this._outputTail;
        this._outputTail = i6 + 1;
        bArr[i6] = (byte) ((_decodeSurrogate & 63) | 128);
    }

    private final int a(int i, int i2) throws IOException {
        byte[] bArr = this._outputBuffer;
        if (i < 55296 || i > 57343) {
            int i3 = i2 + 1;
            bArr[i2] = (byte) ((i >> 12) | 224);
            int i4 = i3 + 1;
            bArr[i3] = (byte) (((i >> 6) & 63) | 128);
            int i5 = i4 + 1;
            bArr[i4] = (byte) ((i & 63) | 128);
            return i5;
        }
        int i6 = i2 + 1;
        bArr[i2] = 92;
        int i7 = i6 + 1;
        bArr[i6] = 117;
        int i8 = i7 + 1;
        byte[] bArr2 = a;
        bArr[i7] = bArr2[(i >> 12) & 15];
        int i9 = i8 + 1;
        bArr[i8] = bArr2[(i >> 8) & 15];
        int i10 = i9 + 1;
        bArr[i9] = bArr2[(i >> 4) & 15];
        int i11 = i10 + 1;
        bArr[i10] = bArr2[i & 15];
        return i11;
    }

    private final void a() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            _flushBuffer();
        }
        System.arraycopy(b, 0, this._outputBuffer, this._outputTail, 4);
        this._outputTail += 4;
    }

    private int b(int i, int i2) throws IOException {
        int i3;
        byte[] bArr = this._outputBuffer;
        int i4 = i2 + 1;
        bArr[i2] = 92;
        int i5 = i4 + 1;
        bArr[i4] = 117;
        if (i > 255) {
            int i6 = 255 & (i >> 8);
            int i7 = i5 + 1;
            byte[] bArr2 = a;
            bArr[i5] = bArr2[i6 >> 4];
            i3 = i7 + 1;
            bArr[i7] = bArr2[i6 & 15];
            i &= 255;
        } else {
            int i8 = i5 + 1;
            bArr[i5] = 48;
            i3 = i8 + 1;
            bArr[i8] = 48;
        }
        int i9 = i3 + 1;
        byte[] bArr3 = a;
        bArr[i3] = bArr3[i >> 4];
        int i10 = i9 + 1;
        bArr[i9] = bArr3[i & 15];
        return i10;
    }

    protected final void _flushBuffer() throws IOException {
        int i = this._outputTail;
        if (i > 0) {
            this._outputTail = 0;
            this._outputStream.write(this._outputBuffer, 0, i);
        }
    }
}
