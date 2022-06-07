package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

@JacksonStdImpl
/* loaded from: classes.dex */
public class BeanPropertyWriter extends PropertyWriter implements Serializable {
    public static final Object MARKER_FOR_EMPTY = JsonInclude.Include.NON_EMPTY;
    private static final long serialVersionUID = 1;
    protected transient Method _accessorMethod;
    protected final JavaType _cfgSerializationType;
    protected final transient Annotations _contextAnnotations;
    protected final JavaType _declaredType;
    protected transient PropertySerializerMap _dynamicSerializers;
    protected transient Field _field;
    protected final Class<?>[] _includeInViews;
    protected transient HashMap<Object, Object> _internalSettings;
    protected final AnnotatedMember _member;
    protected final SerializedString _name;
    protected JavaType _nonTrivialBaseType;
    protected JsonSerializer<Object> _nullSerializer;
    protected JsonSerializer<Object> _serializer;
    protected final boolean _suppressNulls;
    protected final Object _suppressableValue;
    protected TypeSerializer _typeSerializer;
    protected final PropertyName _wrapperName;

    public boolean isUnwrapping() {
        return false;
    }

    public BeanPropertyWriter(BeanPropertyDefinition beanPropertyDefinition, AnnotatedMember annotatedMember, Annotations annotations, JavaType javaType, JsonSerializer<?> jsonSerializer, TypeSerializer typeSerializer, JavaType javaType2, boolean z, Object obj, Class<?>[] clsArr) {
        super(beanPropertyDefinition);
        this._member = annotatedMember;
        this._contextAnnotations = annotations;
        this._name = new SerializedString(beanPropertyDefinition.getName());
        this._wrapperName = beanPropertyDefinition.getWrapperName();
        this._declaredType = javaType;
        this._serializer = jsonSerializer;
        this._dynamicSerializers = jsonSerializer == null ? PropertySerializerMap.emptyForProperties() : null;
        this._typeSerializer = typeSerializer;
        this._cfgSerializationType = javaType2;
        if (annotatedMember instanceof AnnotatedField) {
            this._accessorMethod = null;
            this._field = (Field) annotatedMember.getMember();
        } else if (annotatedMember instanceof AnnotatedMethod) {
            this._accessorMethod = (Method) annotatedMember.getMember();
            this._field = null;
        } else {
            this._accessorMethod = null;
            this._field = null;
        }
        this._suppressNulls = z;
        this._suppressableValue = obj;
        this._nullSerializer = null;
        this._includeInViews = clsArr;
    }

    @Deprecated
    public BeanPropertyWriter(BeanPropertyDefinition beanPropertyDefinition, AnnotatedMember annotatedMember, Annotations annotations, JavaType javaType, JsonSerializer<?> jsonSerializer, TypeSerializer typeSerializer, JavaType javaType2, boolean z, Object obj) {
        this(beanPropertyDefinition, annotatedMember, annotations, javaType, jsonSerializer, typeSerializer, javaType2, z, obj, null);
    }

    public BeanPropertyWriter() {
        super(PropertyMetadata.STD_REQUIRED_OR_OPTIONAL);
        this._member = null;
        this._contextAnnotations = null;
        this._name = null;
        this._wrapperName = null;
        this._includeInViews = null;
        this._declaredType = null;
        this._serializer = null;
        this._dynamicSerializers = null;
        this._typeSerializer = null;
        this._cfgSerializationType = null;
        this._accessorMethod = null;
        this._field = null;
        this._suppressNulls = false;
        this._suppressableValue = null;
        this._nullSerializer = null;
    }

    public BeanPropertyWriter(BeanPropertyWriter beanPropertyWriter) {
        this(beanPropertyWriter, beanPropertyWriter._name);
    }

    public BeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, PropertyName propertyName) {
        super(beanPropertyWriter);
        this._name = new SerializedString(propertyName.getSimpleName());
        this._wrapperName = beanPropertyWriter._wrapperName;
        this._contextAnnotations = beanPropertyWriter._contextAnnotations;
        this._declaredType = beanPropertyWriter._declaredType;
        this._member = beanPropertyWriter._member;
        this._accessorMethod = beanPropertyWriter._accessorMethod;
        this._field = beanPropertyWriter._field;
        this._serializer = beanPropertyWriter._serializer;
        this._nullSerializer = beanPropertyWriter._nullSerializer;
        HashMap<Object, Object> hashMap = beanPropertyWriter._internalSettings;
        if (hashMap != null) {
            this._internalSettings = new HashMap<>(hashMap);
        }
        this._cfgSerializationType = beanPropertyWriter._cfgSerializationType;
        this._dynamicSerializers = beanPropertyWriter._dynamicSerializers;
        this._suppressNulls = beanPropertyWriter._suppressNulls;
        this._suppressableValue = beanPropertyWriter._suppressableValue;
        this._includeInViews = beanPropertyWriter._includeInViews;
        this._typeSerializer = beanPropertyWriter._typeSerializer;
        this._nonTrivialBaseType = beanPropertyWriter._nonTrivialBaseType;
    }

    public BeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, SerializedString serializedString) {
        super(beanPropertyWriter);
        this._name = serializedString;
        this._wrapperName = beanPropertyWriter._wrapperName;
        this._member = beanPropertyWriter._member;
        this._contextAnnotations = beanPropertyWriter._contextAnnotations;
        this._declaredType = beanPropertyWriter._declaredType;
        this._accessorMethod = beanPropertyWriter._accessorMethod;
        this._field = beanPropertyWriter._field;
        this._serializer = beanPropertyWriter._serializer;
        this._nullSerializer = beanPropertyWriter._nullSerializer;
        HashMap<Object, Object> hashMap = beanPropertyWriter._internalSettings;
        if (hashMap != null) {
            this._internalSettings = new HashMap<>(hashMap);
        }
        this._cfgSerializationType = beanPropertyWriter._cfgSerializationType;
        this._dynamicSerializers = beanPropertyWriter._dynamicSerializers;
        this._suppressNulls = beanPropertyWriter._suppressNulls;
        this._suppressableValue = beanPropertyWriter._suppressableValue;
        this._includeInViews = beanPropertyWriter._includeInViews;
        this._typeSerializer = beanPropertyWriter._typeSerializer;
        this._nonTrivialBaseType = beanPropertyWriter._nonTrivialBaseType;
    }

    public BeanPropertyWriter rename(NameTransformer nameTransformer) {
        String transform = nameTransformer.transform(this._name.getValue());
        return transform.equals(this._name.toString()) ? this : _new(PropertyName.construct(transform));
    }

    protected BeanPropertyWriter _new(PropertyName propertyName) {
        return new BeanPropertyWriter(this, propertyName);
    }

    public void assignTypeSerializer(TypeSerializer typeSerializer) {
        this._typeSerializer = typeSerializer;
    }

    public void assignSerializer(JsonSerializer<Object> jsonSerializer) {
        JsonSerializer<Object> jsonSerializer2 = this._serializer;
        if (jsonSerializer2 == null || jsonSerializer2 == jsonSerializer) {
            this._serializer = jsonSerializer;
            return;
        }
        throw new IllegalStateException(String.format("Cannot override _serializer: had a %s, trying to set to %s", ClassUtil.classNameOf(jsonSerializer2), ClassUtil.classNameOf(jsonSerializer)));
    }

    public void assignNullSerializer(JsonSerializer<Object> jsonSerializer) {
        JsonSerializer<Object> jsonSerializer2 = this._nullSerializer;
        if (jsonSerializer2 == null || jsonSerializer2 == jsonSerializer) {
            this._nullSerializer = jsonSerializer;
            return;
        }
        throw new IllegalStateException(String.format("Cannot override _nullSerializer: had a %s, trying to set to %s", ClassUtil.classNameOf(jsonSerializer2), ClassUtil.classNameOf(jsonSerializer)));
    }

    public BeanPropertyWriter unwrappingWriter(NameTransformer nameTransformer) {
        return new UnwrappingBeanPropertyWriter(this, nameTransformer);
    }

    public void setNonTrivialBaseType(JavaType javaType) {
        this._nonTrivialBaseType = javaType;
    }

    public void fixAccess(SerializationConfig serializationConfig) {
        this._member.fixAccess(serializationConfig.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
    }

    Object readResolve() {
        AnnotatedMember annotatedMember = this._member;
        if (annotatedMember instanceof AnnotatedField) {
            this._accessorMethod = null;
            this._field = (Field) annotatedMember.getMember();
        } else if (annotatedMember instanceof AnnotatedMethod) {
            this._accessorMethod = (Method) annotatedMember.getMember();
            this._field = null;
        }
        if (this._serializer == null) {
            this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
        }
        return this;
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter, com.fasterxml.jackson.databind.BeanProperty, com.fasterxml.jackson.databind.util.Named
    public String getName() {
        return this._name.getValue();
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter, com.fasterxml.jackson.databind.BeanProperty
    public PropertyName getFullName() {
        return new PropertyName(this._name.getValue());
    }

    @Override // com.fasterxml.jackson.databind.BeanProperty
    public JavaType getType() {
        return this._declaredType;
    }

    @Override // com.fasterxml.jackson.databind.BeanProperty
    public PropertyName getWrapperName() {
        return this._wrapperName;
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter, com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        AnnotatedMember annotatedMember = this._member;
        if (annotatedMember == null) {
            return null;
        }
        return (A) annotatedMember.getAnnotation(cls);
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter, com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getContextAnnotation(Class<A> cls) {
        Annotations annotations = this._contextAnnotations;
        if (annotations == null) {
            return null;
        }
        return (A) annotations.get(cls);
    }

    @Override // com.fasterxml.jackson.databind.BeanProperty
    public AnnotatedMember getMember() {
        return this._member;
    }

    protected void _depositSchemaProperty(ObjectNode objectNode, JsonNode jsonNode) {
        objectNode.set(getName(), jsonNode);
    }

    public Object getInternalSetting(Object obj) {
        HashMap<Object, Object> hashMap = this._internalSettings;
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(obj);
    }

    public Object setInternalSetting(Object obj, Object obj2) {
        if (this._internalSettings == null) {
            this._internalSettings = new HashMap<>();
        }
        return this._internalSettings.put(obj, obj2);
    }

    public Object removeInternalSetting(Object obj) {
        HashMap<Object, Object> hashMap = this._internalSettings;
        if (hashMap == null) {
            return null;
        }
        Object remove = hashMap.remove(obj);
        if (this._internalSettings.size() != 0) {
            return remove;
        }
        this._internalSettings = null;
        return remove;
    }

    public SerializableString getSerializedName() {
        return this._name;
    }

    public boolean hasSerializer() {
        return this._serializer != null;
    }

    public boolean hasNullSerializer() {
        return this._nullSerializer != null;
    }

    public TypeSerializer getTypeSerializer() {
        return this._typeSerializer;
    }

    public boolean willSuppressNulls() {
        return this._suppressNulls;
    }

    public boolean wouldConflictWithName(PropertyName propertyName) {
        PropertyName propertyName2 = this._wrapperName;
        if (propertyName2 != null) {
            return propertyName2.equals(propertyName);
        }
        return propertyName.hasSimpleName(this._name.getValue()) && !propertyName.hasNamespace();
    }

    public JsonSerializer<Object> getSerializer() {
        return this._serializer;
    }

    public JavaType getSerializationType() {
        return this._cfgSerializationType;
    }

    @Deprecated
    public Class<?> getRawSerializationType() {
        JavaType javaType = this._cfgSerializationType;
        if (javaType == null) {
            return null;
        }
        return javaType.getRawClass();
    }

    @Deprecated
    public Class<?> getPropertyType() {
        Method method = this._accessorMethod;
        if (method != null) {
            return method.getReturnType();
        }
        Field field = this._field;
        if (field != null) {
            return field.getType();
        }
        return null;
    }

    @Deprecated
    public Type getGenericPropertyType() {
        Method method = this._accessorMethod;
        if (method != null) {
            return method.getGenericReturnType();
        }
        Field field = this._field;
        if (field != null) {
            return field.getGenericType();
        }
        return null;
    }

    public Class<?>[] getViews() {
        return this._includeInViews;
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter
    public void serializeAsField(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        Method method = this._accessorMethod;
        Object invoke = method == null ? this._field.get(obj) : method.invoke(obj, null);
        if (invoke != null) {
            JsonSerializer<?> jsonSerializer = this._serializer;
            if (jsonSerializer == null) {
                Class<?> cls = invoke.getClass();
                PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
                JsonSerializer<?> serializerFor = propertySerializerMap.serializerFor(cls);
                jsonSerializer = serializerFor == null ? _findAndAddDynamic(propertySerializerMap, cls, serializerProvider) : serializerFor;
            }
            Object obj2 = this._suppressableValue;
            if (obj2 != null) {
                if (MARKER_FOR_EMPTY == obj2) {
                    if (jsonSerializer.isEmpty(serializerProvider, invoke)) {
                        return;
                    }
                } else if (obj2.equals(invoke)) {
                    return;
                }
            }
            if (invoke != obj || !_handleSelfReference(obj, jsonGenerator, serializerProvider, jsonSerializer)) {
                jsonGenerator.writeFieldName(this._name);
                TypeSerializer typeSerializer = this._typeSerializer;
                if (typeSerializer == null) {
                    jsonSerializer.serialize(invoke, jsonGenerator, serializerProvider);
                } else {
                    jsonSerializer.serializeWithType(invoke, jsonGenerator, serializerProvider, typeSerializer);
                }
            }
        } else if (this._nullSerializer != null) {
            jsonGenerator.writeFieldName(this._name);
            this._nullSerializer.serialize(null, jsonGenerator, serializerProvider);
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter
    public void serializeAsOmittedField(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        if (!jsonGenerator.canOmitFields()) {
            jsonGenerator.writeOmittedField(this._name.getValue());
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter
    public void serializeAsElement(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        Method method = this._accessorMethod;
        Object invoke = method == null ? this._field.get(obj) : method.invoke(obj, null);
        if (invoke == null) {
            JsonSerializer<Object> jsonSerializer = this._nullSerializer;
            if (jsonSerializer != null) {
                jsonSerializer.serialize(null, jsonGenerator, serializerProvider);
            } else {
                jsonGenerator.writeNull();
            }
        } else {
            JsonSerializer<?> jsonSerializer2 = this._serializer;
            if (jsonSerializer2 == null) {
                Class<?> cls = invoke.getClass();
                PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
                JsonSerializer<?> serializerFor = propertySerializerMap.serializerFor(cls);
                jsonSerializer2 = serializerFor == null ? _findAndAddDynamic(propertySerializerMap, cls, serializerProvider) : serializerFor;
            }
            Object obj2 = this._suppressableValue;
            if (obj2 != null) {
                if (MARKER_FOR_EMPTY == obj2) {
                    if (jsonSerializer2.isEmpty(serializerProvider, invoke)) {
                        serializeAsPlaceholder(obj, jsonGenerator, serializerProvider);
                        return;
                    }
                } else if (obj2.equals(invoke)) {
                    serializeAsPlaceholder(obj, jsonGenerator, serializerProvider);
                    return;
                }
            }
            if (invoke != obj || !_handleSelfReference(obj, jsonGenerator, serializerProvider, jsonSerializer2)) {
                TypeSerializer typeSerializer = this._typeSerializer;
                if (typeSerializer == null) {
                    jsonSerializer2.serialize(invoke, jsonGenerator, serializerProvider);
                } else {
                    jsonSerializer2.serializeWithType(invoke, jsonGenerator, serializerProvider, typeSerializer);
                }
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter
    public void serializeAsPlaceholder(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        JsonSerializer<Object> jsonSerializer = this._nullSerializer;
        if (jsonSerializer != null) {
            jsonSerializer.serialize(null, jsonGenerator, serializerProvider);
        } else {
            jsonGenerator.writeNull();
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter, com.fasterxml.jackson.databind.BeanProperty
    public void depositSchemaProperty(JsonObjectFormatVisitor jsonObjectFormatVisitor, SerializerProvider serializerProvider) throws JsonMappingException {
        if (jsonObjectFormatVisitor == null) {
            return;
        }
        if (isRequired()) {
            jsonObjectFormatVisitor.property(this);
        } else {
            jsonObjectFormatVisitor.optionalProperty(this);
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.PropertyWriter
    @Deprecated
    public void depositSchemaProperty(ObjectNode objectNode, SerializerProvider serializerProvider) throws JsonMappingException {
        JsonNode jsonNode;
        JavaType serializationType = getSerializationType();
        Type type = serializationType == null ? getType() : serializationType.getRawClass();
        JsonSerializer<Object> serializer = getSerializer();
        if (serializer == null) {
            serializer = serializerProvider.findValueSerializer(getType(), this);
        }
        boolean z = !isRequired();
        if (serializer instanceof SchemaAware) {
            jsonNode = ((SchemaAware) serializer).getSchema(serializerProvider, type, z);
        } else {
            jsonNode = JsonSchema.getDefaultSchemaNode();
        }
        _depositSchemaProperty(objectNode, jsonNode);
    }

    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> cls, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResult;
        JavaType javaType = this._nonTrivialBaseType;
        if (javaType != null) {
            serializerAndMapResult = propertySerializerMap.findAndAddPrimarySerializer(serializerProvider.constructSpecializedType(javaType, cls), serializerProvider, this);
        } else {
            serializerAndMapResult = propertySerializerMap.findAndAddPrimarySerializer(cls, serializerProvider, this);
        }
        if (propertySerializerMap != serializerAndMapResult.map) {
            this._dynamicSerializers = serializerAndMapResult.map;
        }
        return serializerAndMapResult.serializer;
    }

    public final Object get(Object obj) throws Exception {
        Method method = this._accessorMethod;
        return method == null ? this._field.get(obj) : method.invoke(obj, null);
    }

    protected boolean _handleSelfReference(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<?> jsonSerializer) throws JsonMappingException {
        if (!serializerProvider.isEnabled(SerializationFeature.FAIL_ON_SELF_REFERENCES) || jsonSerializer.usesObjectId() || !(jsonSerializer instanceof BeanSerializerBase)) {
            return false;
        }
        serializerProvider.reportBadDefinition(getType(), "Direct self-reference leading to cycle");
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        sb.append("property '");
        sb.append(getName());
        sb.append("' (");
        if (this._accessorMethod != null) {
            sb.append("via method ");
            sb.append(this._accessorMethod.getDeclaringClass().getName());
            sb.append("#");
            sb.append(this._accessorMethod.getName());
        } else if (this._field != null) {
            sb.append("field \"");
            sb.append(this._field.getDeclaringClass().getName());
            sb.append("#");
            sb.append(this._field.getName());
        } else {
            sb.append("virtual");
        }
        if (this._serializer == null) {
            sb.append(", no static serializer");
        } else {
            sb.append(", static serializer of type " + this._serializer.getClass().getName());
        }
        sb.append(')');
        return sb.toString();
    }
}
