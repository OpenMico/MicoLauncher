package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public abstract class FieldDeserializer {
    protected BeanContext beanContext;
    protected final Class<?> clazz;
    public final FieldInfo fieldInfo;

    public int getFastMatchToken() {
        return 0;
    }

    public abstract void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map);

    public FieldDeserializer(Class<?> cls, FieldInfo fieldInfo) {
        this.clazz = cls;
        this.fieldInfo = fieldInfo;
    }

    public void setValue(Object obj, boolean z) {
        setValue(obj, Boolean.valueOf(z));
    }

    public void setValue(Object obj, int i) {
        setValue(obj, Integer.valueOf(i));
    }

    public void setValue(Object obj, long j) {
        setValue(obj, Long.valueOf(j));
    }

    public void setValue(Object obj, String str) {
        setValue(obj, (Object) str);
    }

    public void setValue(Object obj, Object obj2) {
        if (obj2 != null || !this.fieldInfo.fieldClass.isPrimitive()) {
            if (this.fieldInfo.fieldClass == String.class && this.fieldInfo.format != null && this.fieldInfo.format.equals("trim")) {
                obj2 = ((String) obj2).trim();
            }
            try {
                Method method = this.fieldInfo.method;
                if (method == null) {
                    Field field = this.fieldInfo.field;
                    if (this.fieldInfo.getOnly) {
                        if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                            AtomicInteger atomicInteger = (AtomicInteger) field.get(obj);
                            if (atomicInteger != null) {
                                atomicInteger.set(((AtomicInteger) obj2).get());
                            }
                        } else if (this.fieldInfo.fieldClass == AtomicLong.class) {
                            AtomicLong atomicLong = (AtomicLong) field.get(obj);
                            if (atomicLong != null) {
                                atomicLong.set(((AtomicLong) obj2).get());
                            }
                        } else if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                            AtomicBoolean atomicBoolean = (AtomicBoolean) field.get(obj);
                            if (atomicBoolean != null) {
                                atomicBoolean.set(((AtomicBoolean) obj2).get());
                            }
                        } else if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                            Map map = (Map) field.get(obj);
                            if (!(map == null || map == Collections.emptyMap() || map.getClass().getName().startsWith("java.util.Collections$Unmodifiable"))) {
                                map.putAll((Map) obj2);
                            }
                        } else {
                            Collection collection = (Collection) field.get(obj);
                            if (!(collection == null || obj2 == null || collection == Collections.emptySet() || collection == Collections.emptyList() || collection.getClass().getName().startsWith("java.util.Collections$Unmodifiable"))) {
                                collection.clear();
                                collection.addAll((Collection) obj2);
                            }
                        }
                    } else if (field != null) {
                        field.set(obj, obj2);
                    }
                } else if (!this.fieldInfo.getOnly) {
                    method.invoke(obj, obj2);
                } else if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                    AtomicInteger atomicInteger2 = (AtomicInteger) method.invoke(obj, new Object[0]);
                    if (atomicInteger2 != null) {
                        atomicInteger2.set(((AtomicInteger) obj2).get());
                    }
                } else if (this.fieldInfo.fieldClass == AtomicLong.class) {
                    AtomicLong atomicLong2 = (AtomicLong) method.invoke(obj, new Object[0]);
                    if (atomicLong2 != null) {
                        atomicLong2.set(((AtomicLong) obj2).get());
                    }
                } else if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                    AtomicBoolean atomicBoolean2 = (AtomicBoolean) method.invoke(obj, new Object[0]);
                    if (atomicBoolean2 != null) {
                        atomicBoolean2.set(((AtomicBoolean) obj2).get());
                    }
                } else if (Map.class.isAssignableFrom(method.getReturnType())) {
                    Map map2 = (Map) method.invoke(obj, new Object[0]);
                    if (!(map2 == null || map2 == Collections.emptyMap() || map2.getClass().getName().startsWith("java.util.Collections$Unmodifiable"))) {
                        map2.putAll((Map) obj2);
                    }
                } else {
                    Collection collection2 = (Collection) method.invoke(obj, new Object[0]);
                    if (!(collection2 == null || obj2 == null || collection2 == Collections.emptySet() || collection2 == Collections.emptyList() || collection2.getClass().getName().startsWith("java.util.Collections$Unmodifiable"))) {
                        collection2.clear();
                        collection2.addAll((Collection) obj2);
                    }
                }
            } catch (Exception e) {
                throw new JSONException("set property error, " + this.clazz.getName() + "#" + this.fieldInfo.name, e);
            }
        }
    }

    public void setWrappedValue(String str, Object obj) {
        throw new JSONException("TODO");
    }
}
