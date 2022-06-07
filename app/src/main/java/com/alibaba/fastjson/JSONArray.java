package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

/* loaded from: classes.dex */
public class JSONArray extends JSON implements Serializable, Cloneable, List<Object>, RandomAccess {
    private static final long serialVersionUID = 1;
    protected transient Type componentType;
    private final List<Object> list;
    protected transient Object relatedArray;

    public JSONArray() {
        this.list = new ArrayList();
    }

    public JSONArray(List<Object> list) {
        if (list != null) {
            this.list = list;
            return;
        }
        throw new IllegalArgumentException("list is null.");
    }

    public JSONArray(int i) {
        this.list = new ArrayList(i);
    }

    public Object getRelatedArray() {
        return this.relatedArray;
    }

    public void setRelatedArray(Object obj) {
        this.relatedArray = obj;
    }

    public Type getComponentType() {
        return this.componentType;
    }

    public void setComponentType(Type type) {
        this.componentType = type;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.list.size();
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.list.contains(obj);
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<Object> iterator() {
        return this.list.iterator();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.list.toArray(tArr);
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(Object obj) {
        return this.list.add(obj);
    }

    public JSONArray fluentAdd(Object obj) {
        this.list.add(obj);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        return this.list.remove(obj);
    }

    public JSONArray fluentRemove(Object obj) {
        this.list.remove(obj);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.list.containsAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends Object> collection) {
        return this.list.addAll(collection);
    }

    public JSONArray fluentAddAll(Collection<? extends Object> collection) {
        this.list.addAll(collection);
        return this;
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Object> collection) {
        return this.list.addAll(i, collection);
    }

    public JSONArray fluentAddAll(int i, Collection<? extends Object> collection) {
        this.list.addAll(i, collection);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return this.list.removeAll(collection);
    }

    public JSONArray fluentRemoveAll(Collection<?> collection) {
        this.list.removeAll(collection);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return this.list.retainAll(collection);
    }

    public JSONArray fluentRetainAll(Collection<?> collection) {
        this.list.retainAll(collection);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.list.clear();
    }

    public JSONArray fluentClear() {
        this.list.clear();
        return this;
    }

    @Override // java.util.List
    public Object set(int i, Object obj) {
        if (i == -1) {
            this.list.add(obj);
            return null;
        } else if (this.list.size() > i) {
            return this.list.set(i, obj);
        } else {
            for (int size = this.list.size(); size < i; size++) {
                this.list.add(null);
            }
            this.list.add(obj);
            return null;
        }
    }

    public JSONArray fluentSet(int i, Object obj) {
        set(i, obj);
        return this;
    }

    @Override // java.util.List
    public void add(int i, Object obj) {
        this.list.add(i, obj);
    }

    public JSONArray fluentAdd(int i, Object obj) {
        this.list.add(i, obj);
        return this;
    }

    @Override // java.util.List
    public Object remove(int i) {
        return this.list.remove(i);
    }

    public JSONArray fluentRemove(int i) {
        this.list.remove(i);
        return this;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.list.indexOf(obj);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.list.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<Object> listIterator() {
        return this.list.listIterator();
    }

    @Override // java.util.List
    public ListIterator<Object> listIterator(int i) {
        return this.list.listIterator(i);
    }

    @Override // java.util.List
    public List<Object> subList(int i, int i2) {
        return this.list.subList(i, i2);
    }

    @Override // java.util.List
    public Object get(int i) {
        return this.list.get(i);
    }

    public JSONObject getJSONObject(int i) {
        Object obj = this.list.get(i);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        if (obj instanceof Map) {
            return new JSONObject((Map) obj);
        }
        return (JSONObject) toJSON(obj);
    }

    public JSONArray getJSONArray(int i) {
        Object obj = this.list.get(i);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        if (obj instanceof List) {
            return new JSONArray((List) obj);
        }
        return (JSONArray) toJSON(obj);
    }

    public <T> T getObject(int i, Class<T> cls) {
        return (T) TypeUtils.castToJavaBean(this.list.get(i), cls);
    }

    public <T> T getObject(int i, Type type) {
        Object obj = this.list.get(i);
        if (type instanceof Class) {
            return (T) TypeUtils.castToJavaBean(obj, (Class) type);
        }
        return (T) JSON.parseObject(JSON.toJSONString(obj), type, new Feature[0]);
    }

    public Boolean getBoolean(int i) {
        Object obj = get(i);
        if (obj == null) {
            return null;
        }
        return TypeUtils.castToBoolean(obj);
    }

    public boolean getBooleanValue(int i) {
        Object obj = get(i);
        if (obj == null) {
            return false;
        }
        return TypeUtils.castToBoolean(obj).booleanValue();
    }

    public Byte getByte(int i) {
        return TypeUtils.castToByte(get(i));
    }

    public byte getByteValue(int i) {
        Byte castToByte = TypeUtils.castToByte(get(i));
        if (castToByte == null) {
            return (byte) 0;
        }
        return castToByte.byteValue();
    }

    public Short getShort(int i) {
        return TypeUtils.castToShort(get(i));
    }

    public short getShortValue(int i) {
        Short castToShort = TypeUtils.castToShort(get(i));
        if (castToShort == null) {
            return (short) 0;
        }
        return castToShort.shortValue();
    }

    public Integer getInteger(int i) {
        return TypeUtils.castToInt(get(i));
    }

    public int getIntValue(int i) {
        Integer castToInt = TypeUtils.castToInt(get(i));
        if (castToInt == null) {
            return 0;
        }
        return castToInt.intValue();
    }

    public Long getLong(int i) {
        return TypeUtils.castToLong(get(i));
    }

    public long getLongValue(int i) {
        Long castToLong = TypeUtils.castToLong(get(i));
        if (castToLong == null) {
            return 0L;
        }
        return castToLong.longValue();
    }

    public Float getFloat(int i) {
        return TypeUtils.castToFloat(get(i));
    }

    public float getFloatValue(int i) {
        Float castToFloat = TypeUtils.castToFloat(get(i));
        if (castToFloat == null) {
            return 0.0f;
        }
        return castToFloat.floatValue();
    }

    public Double getDouble(int i) {
        return TypeUtils.castToDouble(get(i));
    }

    public double getDoubleValue(int i) {
        Double castToDouble = TypeUtils.castToDouble(get(i));
        if (castToDouble == null) {
            return 0.0d;
        }
        return castToDouble.doubleValue();
    }

    public BigDecimal getBigDecimal(int i) {
        return TypeUtils.castToBigDecimal(get(i));
    }

    public BigInteger getBigInteger(int i) {
        return TypeUtils.castToBigInteger(get(i));
    }

    public String getString(int i) {
        return TypeUtils.castToString(get(i));
    }

    public Date getDate(int i) {
        return TypeUtils.castToDate(get(i));
    }

    public java.sql.Date getSqlDate(int i) {
        return TypeUtils.castToSqlDate(get(i));
    }

    public Timestamp getTimestamp(int i) {
        return TypeUtils.castToTimestamp(get(i));
    }

    public <T> List<T> toJavaList(Class<T> cls) {
        ArrayList arrayList = new ArrayList(size());
        ParserConfig globalInstance = ParserConfig.getGlobalInstance();
        Iterator<Object> it = iterator();
        while (it.hasNext()) {
            arrayList.add(TypeUtils.cast(it.next(), (Class<Object>) cls, globalInstance));
        }
        return arrayList;
    }

    public Object clone() {
        return new JSONArray(new ArrayList(this.list));
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        return this.list.equals(obj);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.list.hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        JSONObject.SecureObjectInputStream.ensureFields();
        if (JSONObject.SecureObjectInputStream.fields != null && !JSONObject.SecureObjectInputStream.fields_error) {
            try {
                new JSONObject.SecureObjectInputStream(objectInputStream).defaultReadObject();
                return;
            } catch (NotActiveException unused) {
            }
        }
        objectInputStream.defaultReadObject();
        for (Object obj : this.list) {
            if (obj != null) {
                ParserConfig.global.checkAutoType(obj.getClass().getName(), null);
            }
        }
    }
}
