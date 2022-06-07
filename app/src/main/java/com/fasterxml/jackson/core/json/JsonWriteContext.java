package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;

/* loaded from: classes.dex */
public class JsonWriteContext extends JsonStreamContext {
    public static final int STATUS_EXPECT_NAME = 5;
    public static final int STATUS_EXPECT_VALUE = 4;
    public static final int STATUS_OK_AFTER_COLON = 2;
    public static final int STATUS_OK_AFTER_COMMA = 1;
    public static final int STATUS_OK_AFTER_SPACE = 3;
    public static final int STATUS_OK_AS_IS = 0;
    protected JsonWriteContext _child;
    protected String _currentName;
    protected Object _currentValue;
    protected DupDetector _dups;
    protected boolean _gotName;
    protected final JsonWriteContext _parent;

    protected JsonWriteContext(int i, JsonWriteContext jsonWriteContext, DupDetector dupDetector) {
        this._type = i;
        this._parent = jsonWriteContext;
        this._dups = dupDetector;
        this._index = -1;
    }

    protected JsonWriteContext reset(int i) {
        this._type = i;
        this._index = -1;
        this._currentName = null;
        this._gotName = false;
        this._currentValue = null;
        DupDetector dupDetector = this._dups;
        if (dupDetector != null) {
            dupDetector.reset();
        }
        return this;
    }

    public JsonWriteContext withDupDetector(DupDetector dupDetector) {
        this._dups = dupDetector;
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public Object getCurrentValue() {
        return this._currentValue;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public void setCurrentValue(Object obj) {
        this._currentValue = obj;
    }

    @Deprecated
    public static JsonWriteContext createRootContext() {
        return createRootContext(null);
    }

    public static JsonWriteContext createRootContext(DupDetector dupDetector) {
        return new JsonWriteContext(0, null, dupDetector);
    }

    public JsonWriteContext createChildArrayContext() {
        JsonWriteContext jsonWriteContext = this._child;
        if (jsonWriteContext != null) {
            return jsonWriteContext.reset(1);
        }
        DupDetector dupDetector = this._dups;
        JsonWriteContext jsonWriteContext2 = new JsonWriteContext(1, this, dupDetector == null ? null : dupDetector.child());
        this._child = jsonWriteContext2;
        return jsonWriteContext2;
    }

    public JsonWriteContext createChildObjectContext() {
        JsonWriteContext jsonWriteContext = this._child;
        if (jsonWriteContext != null) {
            return jsonWriteContext.reset(2);
        }
        DupDetector dupDetector = this._dups;
        JsonWriteContext jsonWriteContext2 = new JsonWriteContext(2, this, dupDetector == null ? null : dupDetector.child());
        this._child = jsonWriteContext2;
        return jsonWriteContext2;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public final JsonWriteContext getParent() {
        return this._parent;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public final String getCurrentName() {
        return this._currentName;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public boolean hasCurrentName() {
        return this._currentName != null;
    }

    public JsonWriteContext clearAndGetParent() {
        this._currentValue = null;
        return this._parent;
    }

    public DupDetector getDupDetector() {
        return this._dups;
    }

    public int writeFieldName(String str) throws JsonProcessingException {
        if (this._type != 2 || this._gotName) {
            return 4;
        }
        this._gotName = true;
        this._currentName = str;
        DupDetector dupDetector = this._dups;
        if (dupDetector != null) {
            a(dupDetector, str);
        }
        return this._index < 0 ? 0 : 1;
    }

    private final void a(DupDetector dupDetector, String str) throws JsonProcessingException {
        if (dupDetector.isDup(str)) {
            Object source = dupDetector.getSource();
            throw new JsonGenerationException("Duplicate field '" + str + LrcRow.SINGLE_QUOTE, source instanceof JsonGenerator ? (JsonGenerator) source : null);
        }
    }

    public int writeValue() {
        if (this._type == 2) {
            if (!this._gotName) {
                return 5;
            }
            this._gotName = false;
            this._index++;
            return 2;
        } else if (this._type == 1) {
            int i = this._index;
            this._index++;
            return i < 0 ? 0 : 1;
        } else {
            this._index++;
            return this._index == 0 ? 0 : 3;
        }
    }
}
