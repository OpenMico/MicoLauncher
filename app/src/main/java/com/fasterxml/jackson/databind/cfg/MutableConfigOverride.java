package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;

/* loaded from: classes.dex */
public class MutableConfigOverride extends ConfigOverride implements Serializable {
    private static final long serialVersionUID = 1;

    public MutableConfigOverride() {
    }

    protected MutableConfigOverride(MutableConfigOverride mutableConfigOverride) {
        super(mutableConfigOverride);
    }

    public MutableConfigOverride copy() {
        return new MutableConfigOverride(this);
    }

    public MutableConfigOverride setFormat(JsonFormat.Value value) {
        this._format = value;
        return this;
    }

    public MutableConfigOverride setInclude(JsonInclude.Value value) {
        this._include = value;
        return this;
    }

    public MutableConfigOverride setIncludeAsProperty(JsonInclude.Value value) {
        this._includeAsProperty = value;
        return this;
    }

    public MutableConfigOverride setIgnorals(JsonIgnoreProperties.Value value) {
        this._ignorals = value;
        return this;
    }

    public MutableConfigOverride setIsIgnoredType(Boolean bool) {
        this._isIgnoredType = bool;
        return this;
    }

    public MutableConfigOverride setSetterInfo(JsonSetter.Value value) {
        this._setterInfo = value;
        return this;
    }

    public MutableConfigOverride setVisibility(JsonAutoDetect.Value value) {
        this._visibility = value;
        return this;
    }

    public MutableConfigOverride setMergeable(Boolean bool) {
        this._mergeable = bool;
        return this;
    }
}
