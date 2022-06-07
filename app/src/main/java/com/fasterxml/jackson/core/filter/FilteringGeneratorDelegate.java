package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class FilteringGeneratorDelegate extends JsonGeneratorDelegate {
    protected boolean _allowMultipleMatches;
    protected TokenFilterContext _filterContext;
    @Deprecated
    protected boolean _includeImmediateParent;
    protected boolean _includePath;
    protected TokenFilter _itemFilter;
    protected int _matchCount;
    protected TokenFilter rootFilter;

    public FilteringGeneratorDelegate(JsonGenerator jsonGenerator, TokenFilter tokenFilter, boolean z, boolean z2) {
        super(jsonGenerator, false);
        this.rootFilter = tokenFilter;
        this._itemFilter = tokenFilter;
        this._filterContext = TokenFilterContext.createRootContext(tokenFilter);
        this._includePath = z;
        this._allowMultipleMatches = z2;
    }

    public TokenFilter getFilter() {
        return this.rootFilter;
    }

    public JsonStreamContext getFilterContext() {
        return this._filterContext;
    }

    public int getMatchCount() {
        return this._matchCount;
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public JsonStreamContext getOutputContext() {
        return this._filterContext;
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeStartArray() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
        } else if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray();
        } else {
            this._itemFilter = this._filterContext.checkValue(this._itemFilter);
            TokenFilter tokenFilter2 = this._itemFilter;
            if (tokenFilter2 == null) {
                this._filterContext = this._filterContext.createChildArrayContext(null, false);
                return;
            }
            if (tokenFilter2 != TokenFilter.INCLUDE_ALL) {
                this._itemFilter = this._itemFilter.filterStartArray();
            }
            if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
                _checkParentPath();
                this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
                this.delegate.writeStartArray();
                return;
            }
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeStartArray(int i) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
        } else if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(i);
        } else {
            this._itemFilter = this._filterContext.checkValue(this._itemFilter);
            TokenFilter tokenFilter2 = this._itemFilter;
            if (tokenFilter2 == null) {
                this._filterContext = this._filterContext.createChildArrayContext(null, false);
                return;
            }
            if (tokenFilter2 != TokenFilter.INCLUDE_ALL) {
                this._itemFilter = this._itemFilter.filterStartArray();
            }
            if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
                _checkParentPath();
                this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
                this.delegate.writeStartArray(i);
                return;
            }
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeEndArray() throws IOException {
        this._filterContext = this._filterContext.closeArray(this.delegate);
        TokenFilterContext tokenFilterContext = this._filterContext;
        if (tokenFilterContext != null) {
            this._itemFilter = tokenFilterContext.getFilter();
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeStartObject() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, false);
        } else if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
            this.delegate.writeStartObject();
        } else {
            TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
            if (checkValue != null) {
                if (checkValue != TokenFilter.INCLUDE_ALL) {
                    checkValue = checkValue.filterStartObject();
                }
                if (checkValue == TokenFilter.INCLUDE_ALL) {
                    _checkParentPath();
                    this._filterContext = this._filterContext.createChildObjectContext(checkValue, true);
                    this.delegate.writeStartObject();
                    return;
                }
                this._filterContext = this._filterContext.createChildObjectContext(checkValue, false);
            }
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeStartObject(Object obj) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, false);
        } else if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
            this.delegate.writeStartObject(obj);
        } else {
            TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
            if (checkValue != null) {
                if (checkValue != TokenFilter.INCLUDE_ALL) {
                    checkValue = checkValue.filterStartObject();
                }
                if (checkValue == TokenFilter.INCLUDE_ALL) {
                    _checkParentPath();
                    this._filterContext = this._filterContext.createChildObjectContext(checkValue, true);
                    this.delegate.writeStartObject(obj);
                    return;
                }
                this._filterContext = this._filterContext.createChildObjectContext(checkValue, false);
            }
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeEndObject() throws IOException {
        this._filterContext = this._filterContext.closeObject(this.delegate);
        TokenFilterContext tokenFilterContext = this._filterContext;
        if (tokenFilterContext != null) {
            this._itemFilter = tokenFilterContext.getFilter();
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(String str) throws IOException {
        TokenFilter fieldName = this._filterContext.setFieldName(str);
        if (fieldName == null) {
            this._itemFilter = null;
        } else if (fieldName == TokenFilter.INCLUDE_ALL) {
            this._itemFilter = fieldName;
            this.delegate.writeFieldName(str);
        } else {
            TokenFilter includeProperty = fieldName.includeProperty(str);
            this._itemFilter = includeProperty;
            if (includeProperty == TokenFilter.INCLUDE_ALL) {
                _checkPropertyParentPath();
            }
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(SerializableString serializableString) throws IOException {
        TokenFilter fieldName = this._filterContext.setFieldName(serializableString.getValue());
        if (fieldName == null) {
            this._itemFilter = null;
        } else if (fieldName == TokenFilter.INCLUDE_ALL) {
            this._itemFilter = fieldName;
            this.delegate.writeFieldName(serializableString);
        } else {
            TokenFilter includeProperty = fieldName.includeProperty(serializableString.getValue());
            this._itemFilter = includeProperty;
            if (includeProperty == TokenFilter.INCLUDE_ALL) {
                _checkPropertyParentPath();
            }
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeString(String str) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeString(str)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeString(str);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeString(char[] cArr, int i, int i2) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                String str = new String(cArr, i, i2);
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeString(str)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeString(cArr, i, i2);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeString(SerializableString serializableString) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeString(serializableString.getValue())) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeString(serializableString);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRawUTF8String(bArr, i, i2);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeUTF8String(byte[] bArr, int i, int i2) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeUTF8String(bArr, i, i2);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String str) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRaw(str);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String str, int i, int i2) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRaw(str);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(SerializableString serializableString) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRaw(serializableString);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char[] cArr, int i, int i2) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRaw(cArr, i, i2);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char c) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRaw(c);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(String str) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRaw(str);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(String str, int i, int i2) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRaw(str, i, i2);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(char[] cArr, int i, int i2) throws IOException {
        if (_checkRawValueWrite()) {
            this.delegate.writeRaw(cArr, i, i2);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
        if (_checkBinaryWrite()) {
            this.delegate.writeBinary(base64Variant, bArr, i, i2);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
        if (_checkBinaryWrite()) {
            return this.delegate.writeBinary(base64Variant, inputStream, i);
        }
        return -1;
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(short s) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeNumber((int) s)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNumber(s);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(int i) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeNumber(i)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNumber(i);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(long j) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeNumber(j)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNumber(j);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigInteger bigInteger) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeNumber(bigInteger)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNumber(bigInteger);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(double d) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeNumber(d)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNumber(d);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(float f) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeNumber(f)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNumber(f);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeNumber(bigDecimal)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNumber(bigDecimal);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(String str) throws IOException, UnsupportedOperationException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeRawValue()) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNumber(str);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeBoolean(boolean z) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeBoolean(z)) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeBoolean(z);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeNull() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter != null) {
            if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                TokenFilter checkValue = this._filterContext.checkValue(this._itemFilter);
                if (checkValue != null) {
                    if (checkValue == TokenFilter.INCLUDE_ALL || checkValue.includeNull()) {
                        _checkParentPath();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.delegate.writeNull();
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeOmittedField(String str) throws IOException {
        if (this._itemFilter != null) {
            this.delegate.writeOmittedField(str);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeObjectId(Object obj) throws IOException {
        if (this._itemFilter != null) {
            this.delegate.writeObjectId(obj);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeObjectRef(Object obj) throws IOException {
        if (this._itemFilter != null) {
            this.delegate.writeObjectRef(obj);
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonGeneratorDelegate, com.fasterxml.jackson.core.JsonGenerator
    public void writeTypeId(Object obj) throws IOException {
        if (this._itemFilter != null) {
            this.delegate.writeTypeId(obj);
        }
    }

    protected void _checkParentPath() throws IOException {
        this._matchCount++;
        if (this._includePath) {
            this._filterContext.writePath(this.delegate);
        }
        if (!this._allowMultipleMatches) {
            this._filterContext.skipParentChecks();
        }
    }

    protected void _checkPropertyParentPath() throws IOException {
        this._matchCount++;
        if (this._includePath) {
            this._filterContext.writePath(this.delegate);
        } else if (this._includeImmediateParent) {
            this._filterContext.writeImmediatePath(this.delegate);
        }
        if (!this._allowMultipleMatches) {
            this._filterContext.skipParentChecks();
        }
    }

    protected boolean _checkBinaryWrite() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return false;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            return true;
        }
        if (!this._itemFilter.includeBinary()) {
            return false;
        }
        _checkParentPath();
        return true;
    }

    protected boolean _checkRawValueWrite() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return false;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            return true;
        }
        if (!this._itemFilter.includeRawValue()) {
            return false;
        }
        _checkParentPath();
        return true;
    }
}
