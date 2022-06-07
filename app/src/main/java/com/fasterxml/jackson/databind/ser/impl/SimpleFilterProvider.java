package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class SimpleFilterProvider extends FilterProvider implements Serializable {
    private static final long serialVersionUID = 1;
    protected boolean _cfgFailOnUnknownId;
    protected PropertyFilter _defaultFilter;
    protected final Map<String, PropertyFilter> _filtersById;

    public SimpleFilterProvider() {
        this(new HashMap());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleFilterProvider(Map<String, ?> map) {
        this._cfgFailOnUnknownId = true;
        for (Object obj : map.values()) {
            if (!(obj instanceof PropertyFilter)) {
                this._filtersById = a(map);
                return;
            }
        }
        this._filtersById = map;
    }

    private static final Map<String, PropertyFilter> a(Map<String, ?> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof PropertyFilter) {
                hashMap.put(entry.getKey(), (PropertyFilter) value);
            } else if (value instanceof BeanPropertyFilter) {
                hashMap.put(entry.getKey(), a((BeanPropertyFilter) value));
            } else {
                throw new IllegalArgumentException("Unrecognized filter type (" + value.getClass().getName() + ")");
            }
        }
        return hashMap;
    }

    private static final PropertyFilter a(BeanPropertyFilter beanPropertyFilter) {
        return SimpleBeanPropertyFilter.from(beanPropertyFilter);
    }

    @Deprecated
    public SimpleFilterProvider setDefaultFilter(BeanPropertyFilter beanPropertyFilter) {
        this._defaultFilter = SimpleBeanPropertyFilter.from(beanPropertyFilter);
        return this;
    }

    public SimpleFilterProvider setDefaultFilter(PropertyFilter propertyFilter) {
        this._defaultFilter = propertyFilter;
        return this;
    }

    public SimpleFilterProvider setDefaultFilter(SimpleBeanPropertyFilter simpleBeanPropertyFilter) {
        this._defaultFilter = simpleBeanPropertyFilter;
        return this;
    }

    public PropertyFilter getDefaultFilter() {
        return this._defaultFilter;
    }

    public SimpleFilterProvider setFailOnUnknownId(boolean z) {
        this._cfgFailOnUnknownId = z;
        return this;
    }

    public boolean willFailOnUnknownId() {
        return this._cfgFailOnUnknownId;
    }

    @Deprecated
    public SimpleFilterProvider addFilter(String str, BeanPropertyFilter beanPropertyFilter) {
        this._filtersById.put(str, a(beanPropertyFilter));
        return this;
    }

    public SimpleFilterProvider addFilter(String str, PropertyFilter propertyFilter) {
        this._filtersById.put(str, propertyFilter);
        return this;
    }

    public SimpleFilterProvider addFilter(String str, SimpleBeanPropertyFilter simpleBeanPropertyFilter) {
        this._filtersById.put(str, simpleBeanPropertyFilter);
        return this;
    }

    public PropertyFilter removeFilter(String str) {
        return this._filtersById.remove(str);
    }

    @Override // com.fasterxml.jackson.databind.ser.FilterProvider
    @Deprecated
    public BeanPropertyFilter findFilter(Object obj) {
        throw new UnsupportedOperationException("Access to deprecated filters not supported");
    }

    @Override // com.fasterxml.jackson.databind.ser.FilterProvider
    public PropertyFilter findPropertyFilter(Object obj, Object obj2) {
        PropertyFilter propertyFilter = this._filtersById.get(obj);
        if (propertyFilter != null || (propertyFilter = this._defaultFilter) != null || !this._cfgFailOnUnknownId) {
            return propertyFilter;
        }
        throw new IllegalArgumentException("No filter configured with id '" + obj + "' (type " + obj.getClass().getName() + ")");
    }
}
