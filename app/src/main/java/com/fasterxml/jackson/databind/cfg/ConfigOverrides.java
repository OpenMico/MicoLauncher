package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ConfigOverrides implements Serializable {
    private static final long serialVersionUID = 1;
    protected JsonInclude.Value _defaultInclusion;
    protected Boolean _defaultMergeable;
    protected JsonSetter.Value _defaultSetterInfo;
    protected Map<Class<?>, MutableConfigOverride> _overrides;
    protected VisibilityChecker<?> _visibilityChecker;

    public ConfigOverrides() {
        this(null, JsonInclude.Value.empty(), JsonSetter.Value.empty(), VisibilityChecker.Std.defaultInstance(), null);
    }

    protected ConfigOverrides(Map<Class<?>, MutableConfigOverride> map, JsonInclude.Value value, JsonSetter.Value value2, VisibilityChecker<?> visibilityChecker, Boolean bool) {
        this._overrides = map;
        this._defaultInclusion = value;
        this._defaultSetterInfo = value2;
        this._visibilityChecker = visibilityChecker;
        this._defaultMergeable = bool;
    }

    public ConfigOverrides copy() {
        Map<Class<?>, MutableConfigOverride> map;
        if (this._overrides == null) {
            map = null;
        } else {
            Map<Class<?>, MutableConfigOverride> _newMap = _newMap();
            for (Map.Entry<Class<?>, MutableConfigOverride> entry : this._overrides.entrySet()) {
                _newMap.put(entry.getKey(), entry.getValue().copy());
            }
            map = _newMap;
        }
        return new ConfigOverrides(map, this._defaultInclusion, this._defaultSetterInfo, this._visibilityChecker, this._defaultMergeable);
    }

    public ConfigOverride findOverride(Class<?> cls) {
        Map<Class<?>, MutableConfigOverride> map = this._overrides;
        if (map == null) {
            return null;
        }
        return map.get(cls);
    }

    public MutableConfigOverride findOrCreateOverride(Class<?> cls) {
        if (this._overrides == null) {
            this._overrides = _newMap();
        }
        MutableConfigOverride mutableConfigOverride = this._overrides.get(cls);
        if (mutableConfigOverride != null) {
            return mutableConfigOverride;
        }
        MutableConfigOverride mutableConfigOverride2 = new MutableConfigOverride();
        this._overrides.put(cls, mutableConfigOverride2);
        return mutableConfigOverride2;
    }

    public JsonInclude.Value getDefaultInclusion() {
        return this._defaultInclusion;
    }

    public JsonSetter.Value getDefaultSetterInfo() {
        return this._defaultSetterInfo;
    }

    public Boolean getDefaultMergeable() {
        return this._defaultMergeable;
    }

    public VisibilityChecker<?> getDefaultVisibility() {
        return this._visibilityChecker;
    }

    public void setDefaultInclusion(JsonInclude.Value value) {
        this._defaultInclusion = value;
    }

    public void setDefaultSetterInfo(JsonSetter.Value value) {
        this._defaultSetterInfo = value;
    }

    public void setDefaultMergeable(Boolean bool) {
        this._defaultMergeable = bool;
    }

    public void setDefaultVisibility(VisibilityChecker<?> visibilityChecker) {
        this._visibilityChecker = visibilityChecker;
    }

    protected Map<Class<?>, MutableConfigOverride> _newMap() {
        return new HashMap();
    }
}
