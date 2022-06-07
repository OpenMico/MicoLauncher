package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class SerializeFilterable {
    protected List<BeforeFilter> beforeFilters = null;
    protected List<AfterFilter> afterFilters = null;
    protected List<PropertyFilter> propertyFilters = null;
    protected List<ValueFilter> valueFilters = null;
    protected List<NameFilter> nameFilters = null;
    protected List<PropertyPreFilter> propertyPreFilters = null;
    protected List<LabelFilter> labelFilters = null;
    protected List<ContextValueFilter> contextValueFilters = null;
    protected boolean writeDirect = true;

    public List<BeforeFilter> getBeforeFilters() {
        if (this.beforeFilters == null) {
            this.beforeFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.beforeFilters;
    }

    public List<AfterFilter> getAfterFilters() {
        if (this.afterFilters == null) {
            this.afterFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.afterFilters;
    }

    public List<NameFilter> getNameFilters() {
        if (this.nameFilters == null) {
            this.nameFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.nameFilters;
    }

    public List<PropertyPreFilter> getPropertyPreFilters() {
        if (this.propertyPreFilters == null) {
            this.propertyPreFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.propertyPreFilters;
    }

    public List<LabelFilter> getLabelFilters() {
        if (this.labelFilters == null) {
            this.labelFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.labelFilters;
    }

    public List<PropertyFilter> getPropertyFilters() {
        if (this.propertyFilters == null) {
            this.propertyFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.propertyFilters;
    }

    public List<ContextValueFilter> getContextValueFilters() {
        if (this.contextValueFilters == null) {
            this.contextValueFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.contextValueFilters;
    }

    public List<ValueFilter> getValueFilters() {
        if (this.valueFilters == null) {
            this.valueFilters = new ArrayList();
            this.writeDirect = false;
        }
        return this.valueFilters;
    }

    public void addFilter(SerializeFilter serializeFilter) {
        if (serializeFilter != null) {
            if (serializeFilter instanceof PropertyPreFilter) {
                getPropertyPreFilters().add((PropertyPreFilter) serializeFilter);
            }
            if (serializeFilter instanceof NameFilter) {
                getNameFilters().add((NameFilter) serializeFilter);
            }
            if (serializeFilter instanceof ValueFilter) {
                getValueFilters().add((ValueFilter) serializeFilter);
            }
            if (serializeFilter instanceof ContextValueFilter) {
                getContextValueFilters().add((ContextValueFilter) serializeFilter);
            }
            if (serializeFilter instanceof PropertyFilter) {
                getPropertyFilters().add((PropertyFilter) serializeFilter);
            }
            if (serializeFilter instanceof BeforeFilter) {
                getBeforeFilters().add((BeforeFilter) serializeFilter);
            }
            if (serializeFilter instanceof AfterFilter) {
                getAfterFilters().add((AfterFilter) serializeFilter);
            }
            if (serializeFilter instanceof LabelFilter) {
                getLabelFilters().add((LabelFilter) serializeFilter);
            }
        }
    }

    public boolean applyName(JSONSerializer jSONSerializer, Object obj, String str) {
        if (jSONSerializer.propertyPreFilters != null) {
            for (PropertyPreFilter propertyPreFilter : jSONSerializer.propertyPreFilters) {
                if (!propertyPreFilter.apply(jSONSerializer, obj, str)) {
                    return false;
                }
            }
        }
        List<PropertyPreFilter> list = this.propertyPreFilters;
        if (list == null) {
            return true;
        }
        for (PropertyPreFilter propertyPreFilter2 : list) {
            if (!propertyPreFilter2.apply(jSONSerializer, obj, str)) {
                return false;
            }
        }
        return true;
    }

    public boolean apply(JSONSerializer jSONSerializer, Object obj, String str, Object obj2) {
        if (jSONSerializer.propertyFilters != null) {
            for (PropertyFilter propertyFilter : jSONSerializer.propertyFilters) {
                if (!propertyFilter.apply(obj, str, obj2)) {
                    return false;
                }
            }
        }
        List<PropertyFilter> list = this.propertyFilters;
        if (list == null) {
            return true;
        }
        for (PropertyFilter propertyFilter2 : list) {
            if (!propertyFilter2.apply(obj, str, obj2)) {
                return false;
            }
        }
        return true;
    }

    protected String processKey(JSONSerializer jSONSerializer, Object obj, String str, Object obj2) {
        if (jSONSerializer.nameFilters != null) {
            for (NameFilter nameFilter : jSONSerializer.nameFilters) {
                str = nameFilter.process(obj, str, obj2);
            }
        }
        List<NameFilter> list = this.nameFilters;
        if (list != null) {
            for (NameFilter nameFilter2 : list) {
                str = nameFilter2.process(obj, str, obj2);
            }
        }
        return str;
    }

    protected Object processValue(JSONSerializer jSONSerializer, BeanContext beanContext, Object obj, String str, Object obj2) {
        return processValue(jSONSerializer, beanContext, obj, str, obj2, 0);
    }

    protected Object processValue(JSONSerializer jSONSerializer, BeanContext beanContext, Object obj, String str, Object obj2, int i) {
        boolean z;
        if (obj2 != null) {
            if ((SerializerFeature.isEnabled(jSONSerializer.out.features, i, SerializerFeature.WriteNonStringValueAsString) || !(beanContext == null || (beanContext.getFeatures() & SerializerFeature.WriteNonStringValueAsString.mask) == 0)) && (((z = obj2 instanceof Number)) || (obj2 instanceof Boolean))) {
                String str2 = null;
                if (z && beanContext != null) {
                    str2 = beanContext.getFormat();
                }
                if (str2 != null) {
                    obj2 = new DecimalFormat(str2).format(obj2);
                } else {
                    obj2 = obj2.toString();
                }
            } else if (beanContext != null && beanContext.isJsonDirect()) {
                obj2 = JSON.parse((String) obj2);
            }
        }
        if (jSONSerializer.valueFilters != null) {
            for (ValueFilter valueFilter : jSONSerializer.valueFilters) {
                obj2 = valueFilter.process(obj, str, obj2);
            }
        }
        List<ValueFilter> list = this.valueFilters;
        if (list != null) {
            for (ValueFilter valueFilter2 : list) {
                obj2 = valueFilter2.process(obj, str, obj2);
            }
        }
        if (jSONSerializer.contextValueFilters != null) {
            for (ContextValueFilter contextValueFilter : jSONSerializer.contextValueFilters) {
                obj2 = contextValueFilter.process(beanContext, obj, str, obj2);
            }
        }
        List<ContextValueFilter> list2 = this.contextValueFilters;
        if (list2 != null) {
            for (ContextValueFilter contextValueFilter2 : list2) {
                obj2 = contextValueFilter2.process(beanContext, obj, str, obj2);
            }
        }
        return obj2;
    }

    protected boolean writeDirect(JSONSerializer jSONSerializer) {
        return jSONSerializer.out.writeDirect && this.writeDirect && jSONSerializer.writeDirect;
    }
}
