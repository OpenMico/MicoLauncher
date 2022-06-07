package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class JsonGeneratorImpl extends GeneratorBase {
    protected static final int[] sOutputEscapes = CharTypes.get7BitOutputEscapes();
    protected boolean _cfgUnqNames;
    protected CharacterEscapes _characterEscapes;
    protected final IOContext _ioContext;
    protected int _maximumNonEscapedChar;
    protected int[] _outputEscapes = sOutputEscapes;
    protected SerializableString _rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;

    public JsonGeneratorImpl(IOContext iOContext, int i, ObjectCodec objectCodec) {
        super(i, objectCodec);
        this._ioContext = iOContext;
        if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(i)) {
            this._maximumNonEscapedChar = 127;
        }
        this._cfgUnqNames = !JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(i);
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return VersionUtil.versionFor(getClass());
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        super.enable(feature);
        if (feature == JsonGenerator.Feature.QUOTE_FIELD_NAMES) {
            this._cfgUnqNames = false;
        }
        return this;
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        super.disable(feature);
        if (feature == JsonGenerator.Feature.QUOTE_FIELD_NAMES) {
            this._cfgUnqNames = true;
        }
        return this;
    }

    @Override // com.fasterxml.jackson.core.base.GeneratorBase
    public void _checkStdFeatureChanges(int i, int i2) {
        super._checkStdFeatureChanges(i, i2);
        this._cfgUnqNames = !JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(i);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator setHighestNonEscapedChar(int i) {
        if (i < 0) {
            i = 0;
        }
        this._maximumNonEscapedChar = i;
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public int getHighestEscapedChar() {
        return this._maximumNonEscapedChar;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
        this._characterEscapes = characterEscapes;
        if (characterEscapes == null) {
            this._outputEscapes = sOutputEscapes;
        } else {
            this._outputEscapes = characterEscapes.getEscapeCodesForAscii();
        }
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
        this._rootValueSeparator = serializableString;
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeStringField(String str, String str2) throws IOException {
        writeFieldName(str);
        writeString(str2);
    }

    protected void _verifyPrettyValueWrite(String str, int i) throws IOException {
        if (i != 5) {
            switch (i) {
                case 0:
                    if (this._writeContext.inArray()) {
                        this._cfgPrettyPrinter.beforeArrayValues(this);
                        return;
                    } else if (this._writeContext.inObject()) {
                        this._cfgPrettyPrinter.beforeObjectEntries(this);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    this._cfgPrettyPrinter.writeArrayValueSeparator(this);
                    return;
                case 2:
                    this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
                    return;
                case 3:
                    this._cfgPrettyPrinter.writeRootValueSeparator(this);
                    return;
                default:
                    _throwInternal();
                    return;
            }
        } else {
            _reportCantWriteValueExpectName(str);
        }
    }

    protected void _reportCantWriteValueExpectName(String str) throws IOException {
        _reportError(String.format("Can not %s, expecting field name (context: %s)", str, this._writeContext.typeDesc()));
    }
}
