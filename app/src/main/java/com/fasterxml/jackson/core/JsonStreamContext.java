package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.CharTypes;
import org.slf4j.Logger;

/* loaded from: classes.dex */
public abstract class JsonStreamContext {
    protected static final int TYPE_ARRAY = 1;
    protected static final int TYPE_OBJECT = 2;
    protected static final int TYPE_ROOT = 0;
    protected int _index;
    protected int _type;

    public abstract String getCurrentName();

    public Object getCurrentValue() {
        return null;
    }

    public abstract JsonStreamContext getParent();

    public void setCurrentValue(Object obj) {
    }

    public JsonStreamContext() {
    }

    public JsonStreamContext(JsonStreamContext jsonStreamContext) {
        this._type = jsonStreamContext._type;
        this._index = jsonStreamContext._index;
    }

    public JsonStreamContext(int i, int i2) {
        this._type = i;
        this._index = i2;
    }

    public final boolean inArray() {
        return this._type == 1;
    }

    public final boolean inRoot() {
        return this._type == 0;
    }

    public final boolean inObject() {
        return this._type == 2;
    }

    @Deprecated
    public final String getTypeDesc() {
        switch (this._type) {
            case 0:
                return Logger.ROOT_LOGGER_NAME;
            case 1:
                return "ARRAY";
            case 2:
                return "OBJECT";
            default:
                return "?";
        }
    }

    public String typeDesc() {
        switch (this._type) {
            case 0:
                return "root";
            case 1:
                return "Array";
            case 2:
                return "Object";
            default:
                return "?";
        }
    }

    public final int getEntryCount() {
        return this._index + 1;
    }

    public final int getCurrentIndex() {
        int i = this._index;
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public boolean hasCurrentIndex() {
        return this._index >= 0;
    }

    public boolean hasPathSegment() {
        int i = this._type;
        if (i == 2) {
            return hasCurrentName();
        }
        if (i == 1) {
            return hasCurrentIndex();
        }
        return false;
    }

    public boolean hasCurrentName() {
        return getCurrentName() != null;
    }

    public JsonPointer pathAsPointer() {
        return JsonPointer.forPath(this, false);
    }

    public JsonPointer pathAsPointer(boolean z) {
        return JsonPointer.forPath(this, z);
    }

    public JsonLocation getStartLocation(Object obj) {
        return JsonLocation.NA;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        switch (this._type) {
            case 0:
                sb.append("/");
                break;
            case 1:
                sb.append('[');
                sb.append(getCurrentIndex());
                sb.append(']');
                break;
            default:
                sb.append('{');
                String currentName = getCurrentName();
                if (currentName != null) {
                    sb.append('\"');
                    CharTypes.appendQuoted(sb, currentName);
                    sb.append('\"');
                } else {
                    sb.append('?');
                }
                sb.append('}');
                break;
        }
        return sb.toString();
    }
}
