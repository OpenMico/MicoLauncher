package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

/* loaded from: classes.dex */
public abstract class ConfigOverride {
    protected JsonFormat.Value _format;
    protected JsonIgnoreProperties.Value _ignorals;
    protected JsonInclude.Value _include;
    protected JsonInclude.Value _includeAsProperty;
    protected Boolean _isIgnoredType;
    protected Boolean _mergeable;
    protected JsonSetter.Value _setterInfo;
    protected JsonAutoDetect.Value _visibility;

    public ConfigOverride() {
    }

    public ConfigOverride(ConfigOverride configOverride) {
        this._format = configOverride._format;
        this._include = configOverride._include;
        this._includeAsProperty = configOverride._includeAsProperty;
        this._ignorals = configOverride._ignorals;
        this._setterInfo = configOverride._setterInfo;
        this._visibility = configOverride._visibility;
        this._isIgnoredType = configOverride._isIgnoredType;
        this._mergeable = configOverride._mergeable;
    }

    public static ConfigOverride empty() {
        return a.a;
    }

    public JsonFormat.Value getFormat() {
        return this._format;
    }

    public JsonInclude.Value getInclude() {
        return this._include;
    }

    public JsonInclude.Value getIncludeAsProperty() {
        return this._includeAsProperty;
    }

    public JsonIgnoreProperties.Value getIgnorals() {
        return this._ignorals;
    }

    public Boolean getIsIgnoredType() {
        return this._isIgnoredType;
    }

    public JsonSetter.Value getSetterInfo() {
        return this._setterInfo;
    }

    public JsonAutoDetect.Value getVisibility() {
        return this._visibility;
    }

    public Boolean getMergeable() {
        return this._mergeable;
    }

    /* loaded from: classes.dex */
    public static final class a extends ConfigOverride {
        static final a a = new a();

        private a() {
        }
    }
}
