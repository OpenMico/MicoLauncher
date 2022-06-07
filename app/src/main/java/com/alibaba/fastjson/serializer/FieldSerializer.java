package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/* loaded from: classes.dex */
public class FieldSerializer implements Comparable<FieldSerializer> {
    protected boolean browserCompatible;
    protected boolean disableCircularReferenceDetect;
    private final String double_quoted_fieldPrefix;
    protected int features;
    protected BeanContext fieldContext;
    public final FieldInfo fieldInfo;
    private String format;
    protected boolean persistenceXToMany;
    private RuntimeSerializerInfo runtimeInfo;
    protected boolean serializeUsing = false;
    private String single_quoted_fieldPrefix;
    private String un_quoted_fieldPrefix;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected final boolean writeNull;

    public FieldSerializer(Class<?> cls, FieldInfo fieldInfo) {
        boolean z;
        JSONType jSONType;
        boolean z2 = false;
        this.writeEnumUsingToString = false;
        this.writeEnumUsingName = false;
        this.disableCircularReferenceDetect = false;
        this.persistenceXToMany = false;
        this.fieldInfo = fieldInfo;
        this.fieldContext = new BeanContext(cls, fieldInfo);
        if (!(cls == null || (jSONType = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class)) == null)) {
            SerializerFeature[] serialzeFeatures = jSONType.serialzeFeatures();
            for (SerializerFeature serializerFeature : serialzeFeatures) {
                if (serializerFeature == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (serializerFeature == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                } else if (serializerFeature == SerializerFeature.DisableCircularReferenceDetect) {
                    this.disableCircularReferenceDetect = true;
                } else if (serializerFeature == SerializerFeature.BrowserCompatible) {
                    this.features |= SerializerFeature.BrowserCompatible.mask;
                    this.browserCompatible = true;
                } else if (serializerFeature == SerializerFeature.WriteMapNullValue) {
                    this.features |= SerializerFeature.WriteMapNullValue.mask;
                }
            }
        }
        fieldInfo.setAccessible();
        this.double_quoted_fieldPrefix = '\"' + fieldInfo.name + "\":";
        JSONField annotation = fieldInfo.getAnnotation();
        if (annotation != null) {
            SerializerFeature[] serialzeFeatures2 = annotation.serialzeFeatures();
            int length = serialzeFeatures2.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = false;
                    break;
                } else if ((serialzeFeatures2[i].getMask() & SerializerFeature.WRITE_MAP_NULL_FEATURES) != 0) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            this.format = annotation.format();
            if (this.format.trim().length() == 0) {
                this.format = null;
            }
            SerializerFeature[] serialzeFeatures3 = annotation.serialzeFeatures();
            for (SerializerFeature serializerFeature2 : serialzeFeatures3) {
                if (serializerFeature2 == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (serializerFeature2 == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                } else if (serializerFeature2 == SerializerFeature.DisableCircularReferenceDetect) {
                    this.disableCircularReferenceDetect = true;
                } else if (serializerFeature2 == SerializerFeature.BrowserCompatible) {
                    this.browserCompatible = true;
                }
            }
            this.features = SerializerFeature.of(annotation.serialzeFeatures()) | this.features;
        } else {
            z = false;
        }
        this.writeNull = z;
        this.persistenceXToMany = (TypeUtils.isAnnotationPresentOneToMany(fieldInfo.method) || TypeUtils.isAnnotationPresentManyToMany(fieldInfo.method)) ? true : z2;
    }

    public void writePrefix(JSONSerializer jSONSerializer) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (!serializeWriter.quoteFieldNames) {
            if (this.un_quoted_fieldPrefix == null) {
                this.un_quoted_fieldPrefix = this.fieldInfo.name + Constants.COLON_SEPARATOR;
            }
            serializeWriter.write(this.un_quoted_fieldPrefix);
        } else if (SerializerFeature.isEnabled(serializeWriter.features, this.fieldInfo.serialzeFeatures, SerializerFeature.UseSingleQuotes)) {
            if (this.single_quoted_fieldPrefix == null) {
                this.single_quoted_fieldPrefix = '\'' + this.fieldInfo.name + "':";
            }
            serializeWriter.write(this.single_quoted_fieldPrefix);
        } else {
            serializeWriter.write(this.double_quoted_fieldPrefix);
        }
    }

    public Object getPropertyValueDirect(Object obj) throws InvocationTargetException, IllegalAccessException {
        Object obj2 = this.fieldInfo.get(obj);
        if (!this.persistenceXToMany || TypeUtils.isHibernateInitialized(obj2)) {
            return obj2;
        }
        return null;
    }

    public Object getPropertyValue(Object obj) throws InvocationTargetException, IllegalAccessException {
        Object obj2 = this.fieldInfo.get(obj);
        if (this.format == null || obj2 == null || (this.fieldInfo.fieldClass != Date.class && this.fieldInfo.fieldClass != java.sql.Date.class)) {
            return obj2;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.format, JSON.defaultLocale);
        simpleDateFormat.setTimeZone(JSON.defaultTimeZone);
        return simpleDateFormat.format(obj2);
    }

    public int compareTo(FieldSerializer fieldSerializer) {
        return this.fieldInfo.compareTo(fieldSerializer.fieldInfo);
    }

    public void writeValue(JSONSerializer jSONSerializer, Object obj) throws Exception {
        ObjectSerializer objectSerializer;
        Class<?> cls;
        if (this.runtimeInfo == null) {
            if (obj == null) {
                cls = this.fieldInfo.fieldClass;
                if (cls == Byte.TYPE) {
                    cls = Byte.class;
                } else if (cls == Short.TYPE) {
                    cls = Short.class;
                } else if (cls == Integer.TYPE) {
                    cls = Integer.class;
                } else if (cls == Long.TYPE) {
                    cls = Long.class;
                } else if (cls == Float.TYPE) {
                    cls = Float.class;
                } else if (cls == Double.TYPE) {
                    cls = Double.class;
                } else if (cls == Boolean.TYPE) {
                    cls = Boolean.class;
                }
            } else {
                cls = obj.getClass();
            }
            ObjectSerializer objectSerializer2 = null;
            JSONField annotation = this.fieldInfo.getAnnotation();
            if (annotation == null || annotation.serializeUsing() == Void.class) {
                if (this.format != null) {
                    if (cls == Double.TYPE || cls == Double.class) {
                        objectSerializer2 = new DoubleSerializer(this.format);
                    } else if (cls == Float.TYPE || cls == Float.class) {
                        objectSerializer2 = new FloatCodec(this.format);
                    }
                }
                if (objectSerializer2 == null) {
                    objectSerializer2 = jSONSerializer.getObjectWriter(cls);
                }
            } else {
                objectSerializer2 = (ObjectSerializer) annotation.serializeUsing().newInstance();
                this.serializeUsing = true;
            }
            this.runtimeInfo = new RuntimeSerializerInfo(objectSerializer2, cls);
        }
        RuntimeSerializerInfo runtimeSerializerInfo = this.runtimeInfo;
        int i = (this.disableCircularReferenceDetect ? this.fieldInfo.serialzeFeatures | SerializerFeature.DisableCircularReferenceDetect.mask : this.fieldInfo.serialzeFeatures) | this.features;
        if (obj == null) {
            SerializeWriter serializeWriter = jSONSerializer.out;
            if (this.fieldInfo.fieldClass != Object.class || !serializeWriter.isEnabled(SerializerFeature.WRITE_MAP_NULL_FEATURES)) {
                Class<?> cls2 = runtimeSerializerInfo.runtimeFieldClass;
                if (Number.class.isAssignableFrom(cls2)) {
                    serializeWriter.writeNull(this.features, SerializerFeature.WriteNullNumberAsZero.mask);
                } else if (String.class == cls2) {
                    serializeWriter.writeNull(this.features, SerializerFeature.WriteNullStringAsEmpty.mask);
                } else if (Boolean.class == cls2) {
                    serializeWriter.writeNull(this.features, SerializerFeature.WriteNullBooleanAsFalse.mask);
                } else if (Collection.class.isAssignableFrom(cls2) || cls2.isArray()) {
                    serializeWriter.writeNull(this.features, SerializerFeature.WriteNullListAsEmpty.mask);
                } else {
                    ObjectSerializer objectSerializer3 = runtimeSerializerInfo.fieldSerializer;
                    if (!serializeWriter.isEnabled(SerializerFeature.WRITE_MAP_NULL_FEATURES) || !(objectSerializer3 instanceof JavaBeanSerializer)) {
                        objectSerializer3.write(jSONSerializer, null, this.fieldInfo.name, this.fieldInfo.fieldType, i);
                    } else {
                        serializeWriter.writeNull();
                    }
                }
            } else {
                serializeWriter.writeNull();
            }
        } else {
            if (this.fieldInfo.isEnum) {
                if (this.writeEnumUsingName) {
                    jSONSerializer.out.writeString(((Enum) obj).name());
                    return;
                } else if (this.writeEnumUsingToString) {
                    jSONSerializer.out.writeString(((Enum) obj).toString());
                    return;
                }
            }
            Class<?> cls3 = obj.getClass();
            if (cls3 == runtimeSerializerInfo.runtimeFieldClass || this.serializeUsing) {
                objectSerializer = runtimeSerializerInfo.fieldSerializer;
            } else {
                objectSerializer = jSONSerializer.getObjectWriter(cls3);
            }
            String str = this.format;
            if (str == null || (objectSerializer instanceof DoubleSerializer) || (objectSerializer instanceof FloatCodec)) {
                if (this.fieldInfo.unwrapped) {
                    if (objectSerializer instanceof JavaBeanSerializer) {
                        ((JavaBeanSerializer) objectSerializer).write(jSONSerializer, obj, this.fieldInfo.name, this.fieldInfo.fieldType, i, true);
                        return;
                    } else if (objectSerializer instanceof MapSerializer) {
                        ((MapSerializer) objectSerializer).write(jSONSerializer, obj, this.fieldInfo.name, this.fieldInfo.fieldType, i, true);
                        return;
                    }
                }
                if ((this.features & SerializerFeature.WriteClassName.mask) == 0 || cls3 == this.fieldInfo.fieldClass || !(objectSerializer instanceof JavaBeanSerializer)) {
                    if (this.browserCompatible && (this.fieldInfo.fieldClass == Long.TYPE || this.fieldInfo.fieldClass == Long.class)) {
                        long longValue = ((Long) obj).longValue();
                        if (longValue > 9007199254740991L || longValue < -9007199254740991L) {
                            jSONSerializer.getWriter().writeString(Long.toString(longValue));
                            return;
                        }
                    }
                    objectSerializer.write(jSONSerializer, obj, this.fieldInfo.name, this.fieldInfo.fieldType, i);
                    return;
                }
                ((JavaBeanSerializer) objectSerializer).write(jSONSerializer, obj, this.fieldInfo.name, this.fieldInfo.fieldType, i, false);
            } else if (objectSerializer instanceof ContextObjectSerializer) {
                ((ContextObjectSerializer) objectSerializer).write(jSONSerializer, obj, this.fieldContext);
            } else {
                jSONSerializer.writeWithFormat(obj, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class RuntimeSerializerInfo {
        final ObjectSerializer fieldSerializer;
        final Class<?> runtimeFieldClass;

        public RuntimeSerializerInfo(ObjectSerializer objectSerializer, Class<?> cls) {
            this.fieldSerializer = objectSerializer;
            this.runtimeFieldClass = cls;
        }
    }
}
