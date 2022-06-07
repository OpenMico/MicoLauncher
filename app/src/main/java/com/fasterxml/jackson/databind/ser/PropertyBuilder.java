package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;

/* loaded from: classes.dex */
public class PropertyBuilder {
    private static final Object a = Boolean.FALSE;
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final BeanDescription _beanDesc;
    protected final SerializationConfig _config;
    protected Object _defaultBean;
    protected final JsonInclude.Value _defaultInclusion;
    protected final boolean _useRealPropertyDefaults;

    public PropertyBuilder(SerializationConfig serializationConfig, BeanDescription beanDescription) {
        this._config = serializationConfig;
        this._beanDesc = beanDescription;
        JsonInclude.Value merge = JsonInclude.Value.merge(beanDescription.findPropertyInclusion(JsonInclude.Value.empty()), serializationConfig.getDefaultPropertyInclusion(beanDescription.getBeanClass(), JsonInclude.Value.empty()));
        this._defaultInclusion = JsonInclude.Value.merge(serializationConfig.getDefaultPropertyInclusion(), merge);
        this._useRealPropertyDefaults = merge.getValueInclusion() == JsonInclude.Include.NON_DEFAULT;
        this._annotationIntrospector = this._config.getAnnotationIntrospector();
    }

    public Annotations getClassAnnotations() {
        return this._beanDesc.getClassAnnotations();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BeanPropertyWriter buildWriter(SerializerProvider serializerProvider, BeanPropertyDefinition beanPropertyDefinition, JavaType javaType, JsonSerializer<?> jsonSerializer, TypeSerializer typeSerializer, TypeSerializer typeSerializer2, AnnotatedMember annotatedMember, boolean z) throws JsonMappingException {
        JavaType javaType2;
        Object obj;
        boolean z2;
        Object defaultBean;
        boolean z3 = false;
        try {
            JavaType findSerializationType = findSerializationType(annotatedMember, z, javaType);
            if (typeSerializer2 != null) {
                if (findSerializationType == null) {
                    findSerializationType = javaType;
                }
                if (findSerializationType.getContentType() == null) {
                    serializerProvider.reportBadPropertyDefinition(this._beanDesc, beanPropertyDefinition, "serialization type " + findSerializationType + " has no content", new Object[0]);
                }
                JavaType withContentTypeHandler = findSerializationType.withContentTypeHandler(typeSerializer2);
                withContentTypeHandler.getContentType();
                javaType2 = withContentTypeHandler;
            } else {
                javaType2 = findSerializationType;
            }
            Object obj2 = null;
            JavaType javaType3 = javaType2 == null ? javaType : javaType2;
            AnnotatedMember accessor = beanPropertyDefinition.getAccessor();
            if (accessor == null) {
                return (BeanPropertyWriter) serializerProvider.reportBadPropertyDefinition(this._beanDesc, beanPropertyDefinition, "could not determine property type", new Object[0]);
            }
            JsonInclude.Value withOverrides = this._config.getDefaultInclusion(javaType3.getRawClass(), accessor.getRawType(), this._defaultInclusion).withOverrides(beanPropertyDefinition.findInclusion());
            JsonInclude.Include valueInclusion = withOverrides.getValueInclusion();
            if (valueInclusion == JsonInclude.Include.USE_DEFAULTS) {
                valueInclusion = JsonInclude.Include.ALWAYS;
            }
            boolean z4 = true;
            switch (valueInclusion) {
                case NON_DEFAULT:
                    if (!this._useRealPropertyDefaults || (defaultBean = getDefaultBean()) == null) {
                        obj2 = BeanUtil.getDefaultValue(javaType3);
                        z3 = true;
                    } else {
                        if (serializerProvider.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
                            annotatedMember.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
                        }
                        try {
                            obj2 = annotatedMember.getValue(defaultBean);
                        } catch (Exception e) {
                            _throwWrapped(e, beanPropertyDefinition.getName(), defaultBean);
                        }
                    }
                    if (obj2 != null) {
                        if (!obj2.getClass().isArray()) {
                            z2 = z3;
                            obj = obj2;
                            break;
                        } else {
                            obj = ArrayBuilders.getArrayComparator(obj2);
                            z2 = z3;
                            break;
                        }
                    } else {
                        obj = obj2;
                        z2 = true;
                        break;
                    }
                case NON_ABSENT:
                    if (!javaType3.isReferenceType()) {
                        obj = null;
                        z2 = true;
                        break;
                    } else {
                        obj = BeanPropertyWriter.MARKER_FOR_EMPTY;
                        z2 = true;
                        break;
                    }
                case NON_EMPTY:
                    obj = BeanPropertyWriter.MARKER_FOR_EMPTY;
                    z2 = true;
                    break;
                case CUSTOM:
                    Object includeFilterInstance = serializerProvider.includeFilterInstance(beanPropertyDefinition, withOverrides.getValueFilter());
                    if (includeFilterInstance != null) {
                        obj = includeFilterInstance;
                        z2 = serializerProvider.includeFilterSuppressNulls(includeFilterInstance);
                        break;
                    } else {
                        obj = includeFilterInstance;
                        z2 = true;
                        break;
                    }
                default:
                    z4 = false;
                case NON_NULL:
                    if (javaType3.isContainerType() && !this._config.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS)) {
                        obj = BeanPropertyWriter.MARKER_FOR_EMPTY;
                        z2 = z4;
                        break;
                    } else {
                        obj = null;
                        z2 = z4;
                        break;
                    }
                    break;
            }
            Class<?>[] findViews = beanPropertyDefinition.findViews();
            BeanPropertyWriter beanPropertyWriter = new BeanPropertyWriter(beanPropertyDefinition, annotatedMember, this._beanDesc.getClassAnnotations(), javaType, jsonSerializer, typeSerializer, javaType2, z2, obj, findViews == null ? this._beanDesc.findDefaultViews() : findViews);
            Object findNullSerializer = this._annotationIntrospector.findNullSerializer(annotatedMember);
            if (findNullSerializer != null) {
                beanPropertyWriter.assignNullSerializer(serializerProvider.serializerInstance(annotatedMember, findNullSerializer));
            }
            NameTransformer findUnwrappingNameTransformer = this._annotationIntrospector.findUnwrappingNameTransformer(annotatedMember);
            return findUnwrappingNameTransformer != null ? beanPropertyWriter.unwrappingWriter(findUnwrappingNameTransformer) : beanPropertyWriter;
        } catch (JsonMappingException e2) {
            if (beanPropertyDefinition == null) {
                return (BeanPropertyWriter) serializerProvider.reportBadDefinition(javaType, ClassUtil.exceptionMessage(e2));
            }
            return (BeanPropertyWriter) serializerProvider.reportBadPropertyDefinition(this._beanDesc, beanPropertyDefinition, ClassUtil.exceptionMessage(e2), new Object[0]);
        }
    }

    protected JavaType findSerializationType(Annotated annotated, boolean z, JavaType javaType) throws JsonMappingException {
        JavaType refineSerializationType = this._annotationIntrospector.refineSerializationType(this._config, annotated, javaType);
        z = true;
        if (refineSerializationType != javaType) {
            Class<?> rawClass = refineSerializationType.getRawClass();
            Class<?> rawClass2 = javaType.getRawClass();
            if (!rawClass.isAssignableFrom(rawClass2) && !rawClass2.isAssignableFrom(rawClass)) {
                throw new IllegalArgumentException("Illegal concrete-type annotation for method '" + annotated.getName() + "': class " + rawClass.getName() + " not a super-type of (declared) class " + rawClass2.getName());
            }
            javaType = refineSerializationType;
            z = true;
        }
        JsonSerialize.Typing findSerializationTyping = this._annotationIntrospector.findSerializationTyping(annotated);
        if (!(findSerializationTyping == null || findSerializationTyping == JsonSerialize.Typing.DEFAULT_TYPING)) {
            if (findSerializationTyping != JsonSerialize.Typing.STATIC) {
                z = false;
            }
        }
        if (z) {
            return javaType.withStaticTyping();
        }
        return null;
    }

    protected Object getDefaultBean() {
        Object obj = this._defaultBean;
        if (obj == null) {
            obj = this._beanDesc.instantiateBean(this._config.canOverrideAccessModifiers());
            if (obj == null) {
                obj = a;
            }
            this._defaultBean = obj;
        }
        if (obj == a) {
            return null;
        }
        return this._defaultBean;
    }

    @Deprecated
    protected Object getPropertyDefaultValue(String str, AnnotatedMember annotatedMember, JavaType javaType) {
        Object defaultBean = getDefaultBean();
        if (defaultBean == null) {
            return getDefaultValue(javaType);
        }
        try {
            return annotatedMember.getValue(defaultBean);
        } catch (Exception e) {
            return _throwWrapped(e, str, defaultBean);
        }
    }

    @Deprecated
    protected Object getDefaultValue(JavaType javaType) {
        return BeanUtil.getDefaultValue(javaType);
    }

    /* JADX WARN: Code restructure failed: missing block: B:0:?, code lost:
        r3 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.lang.Object _throwWrapped(java.lang.Exception r3, java.lang.String r4, java.lang.Object r5) {
        /*
            r2 = this;
        L_0x0000:
            java.lang.Throwable r0 = r3.getCause()
            if (r0 == 0) goto L_0x000b
            java.lang.Throwable r3 = r3.getCause()
            goto L_0x0000
        L_0x000b:
            com.fasterxml.jackson.databind.util.ClassUtil.throwIfError(r3)
            com.fasterxml.jackson.databind.util.ClassUtil.throwIfRTE(r3)
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Failed to get property '"
            r0.append(r1)
            r0.append(r4)
            java.lang.String r4 = "' of default "
            r0.append(r4)
            java.lang.Class r4 = r5.getClass()
            java.lang.String r4 = r4.getName()
            r0.append(r4)
            java.lang.String r4 = " instance"
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.PropertyBuilder._throwWrapped(java.lang.Exception, java.lang.String, java.lang.Object):java.lang.Object");
    }
}
