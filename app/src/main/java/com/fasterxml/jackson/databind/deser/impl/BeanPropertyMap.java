package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class BeanPropertyMap implements Serializable, Iterable<SettableBeanProperty> {
    private static final long serialVersionUID = 2;
    private final Map<String, List<PropertyName>> _aliasDefs;
    private final Map<String, String> _aliasMapping;
    protected final boolean _caseInsensitive;
    private Object[] _hashArea;
    private int _hashMask;
    private final SettableBeanProperty[] _propsInOrder;
    private int _size;
    private int _spillCount;

    private static final int a(int i) {
        if (i <= 5) {
            return 8;
        }
        if (i <= 12) {
            return 16;
        }
        int i2 = 32;
        while (i2 < i + (i >> 2)) {
            i2 += i2;
        }
        return i2;
    }

    public BeanPropertyMap(boolean z, Collection<SettableBeanProperty> collection, Map<String, List<PropertyName>> map) {
        this._caseInsensitive = z;
        this._propsInOrder = (SettableBeanProperty[]) collection.toArray(new SettableBeanProperty[collection.size()]);
        this._aliasDefs = map;
        this._aliasMapping = a(map);
        init(collection);
    }

    private BeanPropertyMap(BeanPropertyMap beanPropertyMap, SettableBeanProperty settableBeanProperty, int i, int i2) {
        this._caseInsensitive = beanPropertyMap._caseInsensitive;
        this._hashMask = beanPropertyMap._hashMask;
        this._size = beanPropertyMap._size;
        this._spillCount = beanPropertyMap._spillCount;
        this._aliasDefs = beanPropertyMap._aliasDefs;
        this._aliasMapping = beanPropertyMap._aliasMapping;
        Object[] objArr = beanPropertyMap._hashArea;
        this._hashArea = Arrays.copyOf(objArr, objArr.length);
        SettableBeanProperty[] settableBeanPropertyArr = beanPropertyMap._propsInOrder;
        this._propsInOrder = (SettableBeanProperty[]) Arrays.copyOf(settableBeanPropertyArr, settableBeanPropertyArr.length);
        this._hashArea[i] = settableBeanProperty;
        this._propsInOrder[i2] = settableBeanProperty;
    }

    private BeanPropertyMap(BeanPropertyMap beanPropertyMap, SettableBeanProperty settableBeanProperty, String str, int i) {
        this._caseInsensitive = beanPropertyMap._caseInsensitive;
        this._hashMask = beanPropertyMap._hashMask;
        this._size = beanPropertyMap._size;
        this._spillCount = beanPropertyMap._spillCount;
        this._aliasDefs = beanPropertyMap._aliasDefs;
        this._aliasMapping = beanPropertyMap._aliasMapping;
        Object[] objArr = beanPropertyMap._hashArea;
        this._hashArea = Arrays.copyOf(objArr, objArr.length);
        SettableBeanProperty[] settableBeanPropertyArr = beanPropertyMap._propsInOrder;
        int length = settableBeanPropertyArr.length;
        this._propsInOrder = (SettableBeanProperty[]) Arrays.copyOf(settableBeanPropertyArr, length + 1);
        this._propsInOrder[length] = settableBeanProperty;
        int i2 = this._hashMask + 1;
        int i3 = i << 1;
        Object[] objArr2 = this._hashArea;
        if (objArr2[i3] != null) {
            i3 = ((i >> 1) + i2) << 1;
            if (objArr2[i3] != null) {
                int i4 = this._spillCount;
                i3 = ((i2 + (i2 >> 1)) << 1) + i4;
                this._spillCount = i4 + 2;
                if (i3 >= objArr2.length) {
                    this._hashArea = Arrays.copyOf(objArr2, objArr2.length + 4);
                }
            }
        }
        Object[] objArr3 = this._hashArea;
        objArr3[i3] = str;
        objArr3[i3 + 1] = settableBeanProperty;
    }

    @Deprecated
    public BeanPropertyMap(boolean z, Collection<SettableBeanProperty> collection) {
        this(z, collection, Collections.emptyMap());
    }

    protected BeanPropertyMap(BeanPropertyMap beanPropertyMap, boolean z) {
        this._caseInsensitive = z;
        this._aliasDefs = beanPropertyMap._aliasDefs;
        this._aliasMapping = beanPropertyMap._aliasMapping;
        SettableBeanProperty[] settableBeanPropertyArr = beanPropertyMap._propsInOrder;
        this._propsInOrder = (SettableBeanProperty[]) Arrays.copyOf(settableBeanPropertyArr, settableBeanPropertyArr.length);
        init(Arrays.asList(this._propsInOrder));
    }

    public BeanPropertyMap withCaseInsensitivity(boolean z) {
        return this._caseInsensitive == z ? this : new BeanPropertyMap(this, z);
    }

    protected void init(Collection<SettableBeanProperty> collection) {
        this._size = collection.size();
        int a = a(this._size);
        this._hashMask = a - 1;
        int i = (a >> 1) + a;
        Object[] objArr = new Object[i * 2];
        int i2 = 0;
        for (SettableBeanProperty settableBeanProperty : collection) {
            if (settableBeanProperty != null) {
                String propertyName = getPropertyName(settableBeanProperty);
                int c = c(propertyName);
                int i3 = c << 1;
                if (objArr[i3] != null) {
                    i3 = ((c >> 1) + a) << 1;
                    if (objArr[i3] != null) {
                        i3 = (i << 1) + i2;
                        i2 += 2;
                        if (i3 >= objArr.length) {
                            objArr = Arrays.copyOf(objArr, objArr.length + 4);
                        }
                    }
                }
                objArr[i3] = propertyName;
                objArr[i3 + 1] = settableBeanProperty;
            }
        }
        this._hashArea = objArr;
        this._spillCount = i2;
    }

    public static BeanPropertyMap construct(Collection<SettableBeanProperty> collection, boolean z, Map<String, List<PropertyName>> map) {
        return new BeanPropertyMap(z, collection, map);
    }

    @Deprecated
    public static BeanPropertyMap construct(Collection<SettableBeanProperty> collection, boolean z) {
        return construct(collection, z, Collections.emptyMap());
    }

    public BeanPropertyMap withProperty(SettableBeanProperty settableBeanProperty) {
        String propertyName = getPropertyName(settableBeanProperty);
        int length = this._hashArea.length;
        for (int i = 1; i < length; i += 2) {
            SettableBeanProperty settableBeanProperty2 = (SettableBeanProperty) this._hashArea[i];
            if (settableBeanProperty2 != null && settableBeanProperty2.getName().equals(propertyName)) {
                return new BeanPropertyMap(this, settableBeanProperty, i, a(settableBeanProperty2));
            }
        }
        return new BeanPropertyMap(this, settableBeanProperty, propertyName, c(propertyName));
    }

    public BeanPropertyMap assignIndexes() {
        int length = this._hashArea.length;
        int i = 0;
        for (int i2 = 1; i2 < length; i2 += 2) {
            SettableBeanProperty settableBeanProperty = (SettableBeanProperty) this._hashArea[i2];
            if (settableBeanProperty != null) {
                i++;
                settableBeanProperty.assignIndex(i);
            }
        }
        return this;
    }

    public BeanPropertyMap renameAll(NameTransformer nameTransformer) {
        if (nameTransformer == null || nameTransformer == NameTransformer.NOP) {
            return this;
        }
        int length = this._propsInOrder.length;
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            SettableBeanProperty settableBeanProperty = this._propsInOrder[i];
            if (settableBeanProperty == null) {
                arrayList.add(settableBeanProperty);
            } else {
                arrayList.add(_rename(settableBeanProperty, nameTransformer));
            }
        }
        return new BeanPropertyMap(this._caseInsensitive, arrayList, this._aliasDefs);
    }

    public BeanPropertyMap withoutProperties(Collection<String> collection) {
        if (collection.isEmpty()) {
            return this;
        }
        int length = this._propsInOrder.length;
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            SettableBeanProperty settableBeanProperty = this._propsInOrder[i];
            if (settableBeanProperty != null && !collection.contains(settableBeanProperty.getName())) {
                arrayList.add(settableBeanProperty);
            }
        }
        return new BeanPropertyMap(this._caseInsensitive, arrayList, this._aliasDefs);
    }

    @Deprecated
    public void replace(SettableBeanProperty settableBeanProperty) {
        String propertyName = getPropertyName(settableBeanProperty);
        int b = b(propertyName);
        if (b >= 0) {
            Object[] objArr = this._hashArea;
            objArr[b] = settableBeanProperty;
            this._propsInOrder[a((SettableBeanProperty) objArr[b])] = settableBeanProperty;
            return;
        }
        throw new NoSuchElementException("No entry '" + propertyName + "' found, can't replace");
    }

    public void replace(SettableBeanProperty settableBeanProperty, SettableBeanProperty settableBeanProperty2) {
        int length = this._hashArea.length;
        for (int i = 1; i <= length; i += 2) {
            Object[] objArr = this._hashArea;
            if (objArr[i] == settableBeanProperty) {
                objArr[i] = settableBeanProperty2;
                this._propsInOrder[a(settableBeanProperty)] = settableBeanProperty2;
                return;
            }
        }
        throw new NoSuchElementException("No entry '" + settableBeanProperty.getName() + "' found, can't replace");
    }

    public void remove(SettableBeanProperty settableBeanProperty) {
        ArrayList arrayList = new ArrayList(this._size);
        String propertyName = getPropertyName(settableBeanProperty);
        int length = this._hashArea.length;
        boolean z = false;
        for (int i = 1; i < length; i += 2) {
            Object[] objArr = this._hashArea;
            SettableBeanProperty settableBeanProperty2 = (SettableBeanProperty) objArr[i];
            if (settableBeanProperty2 != null) {
                if (z || !(z = propertyName.equals(objArr[i - 1]))) {
                    arrayList.add(settableBeanProperty2);
                } else {
                    this._propsInOrder[a(settableBeanProperty2)] = null;
                }
            }
        }
        if (z) {
            init(arrayList);
            return;
        }
        throw new NoSuchElementException("No entry '" + settableBeanProperty.getName() + "' found, can't remove");
    }

    public int size() {
        return this._size;
    }

    public boolean isCaseInsensitive() {
        return this._caseInsensitive;
    }

    public boolean hasAliases() {
        return !this._aliasDefs.isEmpty();
    }

    @Override // java.lang.Iterable
    public Iterator<SettableBeanProperty> iterator() {
        return a().iterator();
    }

    private List<SettableBeanProperty> a() {
        ArrayList arrayList = new ArrayList(this._size);
        int length = this._hashArea.length;
        for (int i = 1; i < length; i += 2) {
            SettableBeanProperty settableBeanProperty = (SettableBeanProperty) this._hashArea[i];
            if (settableBeanProperty != null) {
                arrayList.add(settableBeanProperty);
            }
        }
        return arrayList;
    }

    public SettableBeanProperty[] getPropertiesInInsertionOrder() {
        return this._propsInOrder;
    }

    protected final String getPropertyName(SettableBeanProperty settableBeanProperty) {
        return this._caseInsensitive ? settableBeanProperty.getName().toLowerCase() : settableBeanProperty.getName();
    }

    public SettableBeanProperty find(int i) {
        int length = this._hashArea.length;
        for (int i2 = 1; i2 < length; i2 += 2) {
            SettableBeanProperty settableBeanProperty = (SettableBeanProperty) this._hashArea[i2];
            if (settableBeanProperty != null && i == settableBeanProperty.getPropertyIndex()) {
                return settableBeanProperty;
            }
        }
        return null;
    }

    public SettableBeanProperty find(String str) {
        if (str != null) {
            if (this._caseInsensitive) {
                str = str.toLowerCase();
            }
            int hashCode = str.hashCode() & this._hashMask;
            int i = hashCode << 1;
            Object obj = this._hashArea[i];
            if (obj == str || str.equals(obj)) {
                return (SettableBeanProperty) this._hashArea[i + 1];
            }
            return a(str, hashCode, obj);
        }
        throw new IllegalArgumentException("Cannot pass null property name");
    }

    private final SettableBeanProperty a(String str, int i, Object obj) {
        if (obj == null) {
            return a(this._aliasMapping.get(str));
        }
        int i2 = this._hashMask + 1;
        int i3 = ((i >> 1) + i2) << 1;
        Object obj2 = this._hashArea[i3];
        if (str.equals(obj2)) {
            return (SettableBeanProperty) this._hashArea[i3 + 1];
        }
        if (obj2 != null) {
            int i4 = (i2 + (i2 >> 1)) << 1;
            int i5 = this._spillCount + i4;
            while (i4 < i5) {
                Object obj3 = this._hashArea[i4];
                if (obj3 == str || str.equals(obj3)) {
                    return (SettableBeanProperty) this._hashArea[i4 + 1];
                }
                i4 += 2;
            }
        }
        return a(this._aliasMapping.get(str));
    }

    private SettableBeanProperty a(String str) {
        if (str == null) {
            return null;
        }
        int c = c(str);
        int i = c << 1;
        Object obj = this._hashArea[i];
        if (str.equals(obj)) {
            return (SettableBeanProperty) this._hashArea[i + 1];
        }
        if (obj == null) {
            return null;
        }
        return b(str, c, obj);
    }

    private SettableBeanProperty b(String str, int i, Object obj) {
        int i2 = this._hashMask + 1;
        int i3 = ((i >> 1) + i2) << 1;
        Object obj2 = this._hashArea[i3];
        if (str.equals(obj2)) {
            return (SettableBeanProperty) this._hashArea[i3 + 1];
        }
        if (obj2 == null) {
            return null;
        }
        int i4 = (i2 + (i2 >> 1)) << 1;
        int i5 = this._spillCount + i4;
        while (i4 < i5) {
            Object obj3 = this._hashArea[i4];
            if (obj3 == str || str.equals(obj3)) {
                return (SettableBeanProperty) this._hashArea[i4 + 1];
            }
            i4 += 2;
        }
        return null;
    }

    public boolean findDeserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, String str) throws IOException {
        SettableBeanProperty find = find(str);
        if (find == null) {
            return false;
        }
        try {
            find.deserializeAndSet(jsonParser, deserializationContext, obj);
            return true;
        } catch (Exception e) {
            wrapAndThrow(e, obj, str, deserializationContext);
            return true;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Properties=[");
        Iterator<SettableBeanProperty> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            SettableBeanProperty next = it.next();
            i++;
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(next.getName());
            sb.append('(');
            sb.append(next.getType());
            sb.append(')');
        }
        sb.append(']');
        if (!this._aliasDefs.isEmpty()) {
            sb.append("(aliases: ");
            sb.append(this._aliasDefs);
            sb.append(")");
        }
        return sb.toString();
    }

    protected SettableBeanProperty _rename(SettableBeanProperty settableBeanProperty, NameTransformer nameTransformer) {
        JsonDeserializer<Object> unwrappingDeserializer;
        if (settableBeanProperty == null) {
            return settableBeanProperty;
        }
        SettableBeanProperty withSimpleName = settableBeanProperty.withSimpleName(nameTransformer.transform(settableBeanProperty.getName()));
        JsonDeserializer<Object> valueDeserializer = withSimpleName.getValueDeserializer();
        return (valueDeserializer == null || (unwrappingDeserializer = valueDeserializer.unwrappingDeserializer(nameTransformer)) == valueDeserializer) ? withSimpleName : withSimpleName.withValueDeserializer(unwrappingDeserializer);
    }

    protected void wrapAndThrow(Throwable th, Object obj, String str, DeserializationContext deserializationContext) throws IOException {
        while ((th instanceof InvocationTargetException) && th.getCause() != null) {
            th = th.getCause();
        }
        ClassUtil.throwIfError(th);
        boolean z = deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
        if (th instanceof IOException) {
            if (!z || !(th instanceof JsonProcessingException)) {
                throw ((IOException) th);
            }
        } else if (!z) {
            ClassUtil.throwIfRTE(th);
        }
        throw JsonMappingException.wrapWithPath(th, obj, str);
    }

    private final int b(String str) {
        int c = c(str);
        int i = c << 1;
        if (str.equals(this._hashArea[i])) {
            return i + 1;
        }
        int i2 = this._hashMask + 1;
        int i3 = ((c >> 1) + i2) << 1;
        if (str.equals(this._hashArea[i3])) {
            return i3 + 1;
        }
        int i4 = (i2 + (i2 >> 1)) << 1;
        int i5 = this._spillCount + i4;
        while (i4 < i5) {
            if (str.equals(this._hashArea[i4])) {
                return i4 + 1;
            }
            i4 += 2;
        }
        return -1;
    }

    private final int a(SettableBeanProperty settableBeanProperty) {
        int length = this._propsInOrder.length;
        for (int i = 0; i < length; i++) {
            if (this._propsInOrder[i] == settableBeanProperty) {
                return i;
            }
        }
        throw new IllegalStateException("Illegal state: property '" + settableBeanProperty.getName() + "' missing from _propsInOrder");
    }

    private final int c(String str) {
        return str.hashCode() & this._hashMask;
    }

    private Map<String, String> a(Map<String, List<PropertyName>> map) {
        if (map == null || map.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, List<PropertyName>> entry : map.entrySet()) {
            String key = entry.getKey();
            if (this._caseInsensitive) {
                key = key.toLowerCase();
            }
            for (PropertyName propertyName : entry.getValue()) {
                String simpleName = propertyName.getSimpleName();
                if (this._caseInsensitive) {
                    simpleName = simpleName.toLowerCase();
                }
                hashMap.put(simpleName, key);
            }
        }
        return hashMap;
    }
}
