package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public class AnnotationIntrospectorPair extends AnnotationIntrospector implements Serializable {
    private static final long serialVersionUID = 1;
    protected final AnnotationIntrospector _primary;
    protected final AnnotationIntrospector _secondary;

    public AnnotationIntrospectorPair(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
        this._primary = annotationIntrospector;
        this._secondary = annotationIntrospector2;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return this._primary.version();
    }

    public static AnnotationIntrospector create(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
        return annotationIntrospector == null ? annotationIntrospector2 : annotationIntrospector2 == null ? annotationIntrospector : new AnnotationIntrospectorPair(annotationIntrospector, annotationIntrospector2);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Collection<AnnotationIntrospector> allIntrospectors() {
        return allIntrospectors(new ArrayList());
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> collection) {
        this._primary.allIntrospectors(collection);
        this._secondary.allIntrospectors(collection);
        return collection;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean isAnnotationBundle(Annotation annotation) {
        return this._primary.isAnnotationBundle(annotation) || this._secondary.isAnnotationBundle(annotation);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public PropertyName findRootName(AnnotatedClass annotatedClass) {
        PropertyName findRootName;
        PropertyName findRootName2 = this._primary.findRootName(annotatedClass);
        if (findRootName2 == null) {
            return this._secondary.findRootName(annotatedClass);
        }
        return (!findRootName2.hasSimpleName() && (findRootName = this._secondary.findRootName(annotatedClass)) != null) ? findRootName : findRootName2;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated annotated) {
        JsonIgnoreProperties.Value findPropertyIgnorals = this._secondary.findPropertyIgnorals(annotated);
        JsonIgnoreProperties.Value findPropertyIgnorals2 = this._primary.findPropertyIgnorals(annotated);
        return findPropertyIgnorals == null ? findPropertyIgnorals2 : findPropertyIgnorals.withOverrides(findPropertyIgnorals2);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean isIgnorableType(AnnotatedClass annotatedClass) {
        Boolean isIgnorableType = this._primary.isIgnorableType(annotatedClass);
        return isIgnorableType == null ? this._secondary.isIgnorableType(annotatedClass) : isIgnorableType;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findFilterId(Annotated annotated) {
        Object findFilterId = this._primary.findFilterId(annotated);
        return findFilterId == null ? this._secondary.findFilterId(annotated) : findFilterId;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findNamingStrategy(AnnotatedClass annotatedClass) {
        Object findNamingStrategy = this._primary.findNamingStrategy(annotatedClass);
        return findNamingStrategy == null ? this._secondary.findNamingStrategy(annotatedClass) : findNamingStrategy;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findClassDescription(AnnotatedClass annotatedClass) {
        String findClassDescription = this._primary.findClassDescription(annotatedClass);
        return (findClassDescription == null || findClassDescription.isEmpty()) ? this._secondary.findClassDescription(annotatedClass) : findClassDescription;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public String[] findPropertiesToIgnore(Annotated annotated) {
        String[] findPropertiesToIgnore = this._primary.findPropertiesToIgnore(annotated);
        return findPropertiesToIgnore == null ? this._secondary.findPropertiesToIgnore(annotated) : findPropertiesToIgnore;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public String[] findPropertiesToIgnore(Annotated annotated, boolean z) {
        String[] findPropertiesToIgnore = this._primary.findPropertiesToIgnore(annotated, z);
        return findPropertiesToIgnore == null ? this._secondary.findPropertiesToIgnore(annotated, z) : findPropertiesToIgnore;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public Boolean findIgnoreUnknownProperties(AnnotatedClass annotatedClass) {
        Boolean findIgnoreUnknownProperties = this._primary.findIgnoreUnknownProperties(annotatedClass);
        return findIgnoreUnknownProperties == null ? this._secondary.findIgnoreUnknownProperties(annotatedClass) : findIgnoreUnknownProperties;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass annotatedClass, VisibilityChecker<?> visibilityChecker) {
        return this._primary.findAutoDetectVisibility(annotatedClass, this._secondary.findAutoDetectVisibility(annotatedClass, visibilityChecker));
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType) {
        TypeResolverBuilder<?> findTypeResolver = this._primary.findTypeResolver(mapperConfig, annotatedClass, javaType);
        return findTypeResolver == null ? this._secondary.findTypeResolver(mapperConfig, annotatedClass, javaType) : findTypeResolver;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        TypeResolverBuilder<?> findPropertyTypeResolver = this._primary.findPropertyTypeResolver(mapperConfig, annotatedMember, javaType);
        return findPropertyTypeResolver == null ? this._secondary.findPropertyTypeResolver(mapperConfig, annotatedMember, javaType) : findPropertyTypeResolver;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        TypeResolverBuilder<?> findPropertyContentTypeResolver = this._primary.findPropertyContentTypeResolver(mapperConfig, annotatedMember, javaType);
        return findPropertyContentTypeResolver == null ? this._secondary.findPropertyContentTypeResolver(mapperConfig, annotatedMember, javaType) : findPropertyContentTypeResolver;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public List<NamedType> findSubtypes(Annotated annotated) {
        List<NamedType> findSubtypes = this._primary.findSubtypes(annotated);
        List<NamedType> findSubtypes2 = this._secondary.findSubtypes(annotated);
        if (findSubtypes == null || findSubtypes.isEmpty()) {
            return findSubtypes2;
        }
        if (findSubtypes2 == null || findSubtypes2.isEmpty()) {
            return findSubtypes;
        }
        ArrayList arrayList = new ArrayList(findSubtypes.size() + findSubtypes2.size());
        arrayList.addAll(findSubtypes);
        arrayList.addAll(findSubtypes2);
        return arrayList;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findTypeName(AnnotatedClass annotatedClass) {
        String findTypeName = this._primary.findTypeName(annotatedClass);
        return (findTypeName == null || findTypeName.length() == 0) ? this._secondary.findTypeName(annotatedClass) : findTypeName;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember annotatedMember) {
        AnnotationIntrospector.ReferenceProperty findReferenceType = this._primary.findReferenceType(annotatedMember);
        return findReferenceType == null ? this._secondary.findReferenceType(annotatedMember) : findReferenceType;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public NameTransformer findUnwrappingNameTransformer(AnnotatedMember annotatedMember) {
        NameTransformer findUnwrappingNameTransformer = this._primary.findUnwrappingNameTransformer(annotatedMember);
        return findUnwrappingNameTransformer == null ? this._secondary.findUnwrappingNameTransformer(annotatedMember) : findUnwrappingNameTransformer;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JacksonInject.Value findInjectableValue(AnnotatedMember annotatedMember) {
        JacksonInject.Value findInjectableValue = this._primary.findInjectableValue(annotatedMember);
        return findInjectableValue == null ? this._secondary.findInjectableValue(annotatedMember) : findInjectableValue;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
        return this._primary.hasIgnoreMarker(annotatedMember) || this._secondary.hasIgnoreMarker(annotatedMember);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean hasRequiredMarker(AnnotatedMember annotatedMember) {
        Boolean hasRequiredMarker = this._primary.hasRequiredMarker(annotatedMember);
        return hasRequiredMarker == null ? this._secondary.hasRequiredMarker(annotatedMember) : hasRequiredMarker;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public Object findInjectableValueId(AnnotatedMember annotatedMember) {
        Object findInjectableValueId = this._primary.findInjectableValueId(annotatedMember);
        return findInjectableValueId == null ? this._secondary.findInjectableValueId(annotatedMember) : findInjectableValueId;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findSerializer(Annotated annotated) {
        Object findSerializer = this._primary.findSerializer(annotated);
        return _isExplicitClassOrOb(findSerializer, JsonSerializer.None.class) ? findSerializer : _explicitClassOrOb(this._secondary.findSerializer(annotated), JsonSerializer.None.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findKeySerializer(Annotated annotated) {
        Object findKeySerializer = this._primary.findKeySerializer(annotated);
        return _isExplicitClassOrOb(findKeySerializer, JsonSerializer.None.class) ? findKeySerializer : _explicitClassOrOb(this._secondary.findKeySerializer(annotated), JsonSerializer.None.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findContentSerializer(Annotated annotated) {
        Object findContentSerializer = this._primary.findContentSerializer(annotated);
        return _isExplicitClassOrOb(findContentSerializer, JsonSerializer.None.class) ? findContentSerializer : _explicitClassOrOb(this._secondary.findContentSerializer(annotated), JsonSerializer.None.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findNullSerializer(Annotated annotated) {
        Object findNullSerializer = this._primary.findNullSerializer(annotated);
        return _isExplicitClassOrOb(findNullSerializer, JsonSerializer.None.class) ? findNullSerializer : _explicitClassOrOb(this._secondary.findNullSerializer(annotated), JsonSerializer.None.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public JsonInclude.Include findSerializationInclusion(Annotated annotated, JsonInclude.Include include) {
        return this._primary.findSerializationInclusion(annotated, this._secondary.findSerializationInclusion(annotated, include));
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public JsonInclude.Include findSerializationInclusionForContent(Annotated annotated, JsonInclude.Include include) {
        return this._primary.findSerializationInclusionForContent(annotated, this._secondary.findSerializationInclusionForContent(annotated, include));
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonInclude.Value findPropertyInclusion(Annotated annotated) {
        JsonInclude.Value findPropertyInclusion = this._secondary.findPropertyInclusion(annotated);
        JsonInclude.Value findPropertyInclusion2 = this._primary.findPropertyInclusion(annotated);
        return findPropertyInclusion == null ? findPropertyInclusion2 : findPropertyInclusion.withOverrides(findPropertyInclusion2);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonSerialize.Typing findSerializationTyping(Annotated annotated) {
        JsonSerialize.Typing findSerializationTyping = this._primary.findSerializationTyping(annotated);
        return findSerializationTyping == null ? this._secondary.findSerializationTyping(annotated) : findSerializationTyping;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findSerializationConverter(Annotated annotated) {
        Object findSerializationConverter = this._primary.findSerializationConverter(annotated);
        return findSerializationConverter == null ? this._secondary.findSerializationConverter(annotated) : findSerializationConverter;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findSerializationContentConverter(AnnotatedMember annotatedMember) {
        Object findSerializationContentConverter = this._primary.findSerializationContentConverter(annotatedMember);
        return findSerializationContentConverter == null ? this._secondary.findSerializationContentConverter(annotatedMember) : findSerializationContentConverter;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?>[] findViews(Annotated annotated) {
        Class<?>[] findViews = this._primary.findViews(annotated);
        return findViews == null ? this._secondary.findViews(annotated) : findViews;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean isTypeId(AnnotatedMember annotatedMember) {
        Boolean isTypeId = this._primary.isTypeId(annotatedMember);
        return isTypeId == null ? this._secondary.isTypeId(annotatedMember) : isTypeId;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public ObjectIdInfo findObjectIdInfo(Annotated annotated) {
        ObjectIdInfo findObjectIdInfo = this._primary.findObjectIdInfo(annotated);
        return findObjectIdInfo == null ? this._secondary.findObjectIdInfo(annotated) : findObjectIdInfo;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public ObjectIdInfo findObjectReferenceInfo(Annotated annotated, ObjectIdInfo objectIdInfo) {
        return this._primary.findObjectReferenceInfo(annotated, this._secondary.findObjectReferenceInfo(annotated, objectIdInfo));
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonFormat.Value findFormat(Annotated annotated) {
        JsonFormat.Value findFormat = this._primary.findFormat(annotated);
        JsonFormat.Value findFormat2 = this._secondary.findFormat(annotated);
        return findFormat2 == null ? findFormat : findFormat2.withOverrides(findFormat);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public PropertyName findWrapperName(Annotated annotated) {
        PropertyName findWrapperName;
        PropertyName findWrapperName2 = this._primary.findWrapperName(annotated);
        if (findWrapperName2 == null) {
            return this._secondary.findWrapperName(annotated);
        }
        return (findWrapperName2 != PropertyName.USE_DEFAULT || (findWrapperName = this._secondary.findWrapperName(annotated)) == null) ? findWrapperName2 : findWrapperName;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findPropertyDefaultValue(Annotated annotated) {
        String findPropertyDefaultValue = this._primary.findPropertyDefaultValue(annotated);
        return (findPropertyDefaultValue == null || findPropertyDefaultValue.isEmpty()) ? this._secondary.findPropertyDefaultValue(annotated) : findPropertyDefaultValue;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findPropertyDescription(Annotated annotated) {
        String findPropertyDescription = this._primary.findPropertyDescription(annotated);
        return findPropertyDescription == null ? this._secondary.findPropertyDescription(annotated) : findPropertyDescription;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Integer findPropertyIndex(Annotated annotated) {
        Integer findPropertyIndex = this._primary.findPropertyIndex(annotated);
        return findPropertyIndex == null ? this._secondary.findPropertyIndex(annotated) : findPropertyIndex;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findImplicitPropertyName(AnnotatedMember annotatedMember) {
        String findImplicitPropertyName = this._primary.findImplicitPropertyName(annotatedMember);
        return findImplicitPropertyName == null ? this._secondary.findImplicitPropertyName(annotatedMember) : findImplicitPropertyName;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public List<PropertyName> findPropertyAliases(Annotated annotated) {
        List<PropertyName> findPropertyAliases = this._primary.findPropertyAliases(annotated);
        return findPropertyAliases == null ? this._secondary.findPropertyAliases(annotated) : findPropertyAliases;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonProperty.Access findPropertyAccess(Annotated annotated) {
        JsonProperty.Access findPropertyAccess = this._primary.findPropertyAccess(annotated);
        if (findPropertyAccess != null && findPropertyAccess != JsonProperty.Access.AUTO) {
            return findPropertyAccess;
        }
        JsonProperty.Access findPropertyAccess2 = this._secondary.findPropertyAccess(annotated);
        return findPropertyAccess2 != null ? findPropertyAccess2 : JsonProperty.Access.AUTO;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public AnnotatedMethod resolveSetterConflict(MapperConfig<?> mapperConfig, AnnotatedMethod annotatedMethod, AnnotatedMethod annotatedMethod2) {
        AnnotatedMethod resolveSetterConflict = this._primary.resolveSetterConflict(mapperConfig, annotatedMethod, annotatedMethod2);
        return resolveSetterConflict == null ? this._secondary.resolveSetterConflict(mapperConfig, annotatedMethod, annotatedMethod2) : resolveSetterConflict;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JavaType refineSerializationType(MapperConfig<?> mapperConfig, Annotated annotated, JavaType javaType) throws JsonMappingException {
        return this._primary.refineSerializationType(mapperConfig, annotated, this._secondary.refineSerializationType(mapperConfig, annotated, javaType));
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public Class<?> findSerializationType(Annotated annotated) {
        Class<?> findSerializationType = this._primary.findSerializationType(annotated);
        return findSerializationType == null ? this._secondary.findSerializationType(annotated) : findSerializationType;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public Class<?> findSerializationKeyType(Annotated annotated, JavaType javaType) {
        Class<?> findSerializationKeyType = this._primary.findSerializationKeyType(annotated, javaType);
        return findSerializationKeyType == null ? this._secondary.findSerializationKeyType(annotated, javaType) : findSerializationKeyType;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public Class<?> findSerializationContentType(Annotated annotated, JavaType javaType) {
        Class<?> findSerializationContentType = this._primary.findSerializationContentType(annotated, javaType);
        return findSerializationContentType == null ? this._secondary.findSerializationContentType(annotated, javaType) : findSerializationContentType;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String[] findSerializationPropertyOrder(AnnotatedClass annotatedClass) {
        String[] findSerializationPropertyOrder = this._primary.findSerializationPropertyOrder(annotatedClass);
        return findSerializationPropertyOrder == null ? this._secondary.findSerializationPropertyOrder(annotatedClass) : findSerializationPropertyOrder;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean findSerializationSortAlphabetically(Annotated annotated) {
        Boolean findSerializationSortAlphabetically = this._primary.findSerializationSortAlphabetically(annotated);
        return findSerializationSortAlphabetically == null ? this._secondary.findSerializationSortAlphabetically(annotated) : findSerializationSortAlphabetically;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public void findAndAddVirtualProperties(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, List<BeanPropertyWriter> list) {
        this._primary.findAndAddVirtualProperties(mapperConfig, annotatedClass, list);
        this._secondary.findAndAddVirtualProperties(mapperConfig, annotatedClass, list);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public PropertyName findNameForSerialization(Annotated annotated) {
        PropertyName findNameForSerialization;
        PropertyName findNameForSerialization2 = this._primary.findNameForSerialization(annotated);
        if (findNameForSerialization2 == null) {
            return this._secondary.findNameForSerialization(annotated);
        }
        return (findNameForSerialization2 != PropertyName.USE_DEFAULT || (findNameForSerialization = this._secondary.findNameForSerialization(annotated)) == null) ? findNameForSerialization2 : findNameForSerialization;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean hasAsValue(Annotated annotated) {
        Boolean hasAsValue = this._primary.hasAsValue(annotated);
        return hasAsValue == null ? this._secondary.hasAsValue(annotated) : hasAsValue;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean hasAnyGetter(Annotated annotated) {
        Boolean hasAnyGetter = this._primary.hasAnyGetter(annotated);
        return hasAnyGetter == null ? this._secondary.hasAnyGetter(annotated) : hasAnyGetter;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String[] findEnumValues(Class<?> cls, Enum<?>[] enumArr, String[] strArr) {
        return this._primary.findEnumValues(cls, enumArr, this._secondary.findEnumValues(cls, enumArr, strArr));
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Enum<?> findDefaultEnumValue(Class<Enum<?>> cls) {
        Enum<?> findDefaultEnumValue = this._primary.findDefaultEnumValue(cls);
        return findDefaultEnumValue == null ? this._secondary.findDefaultEnumValue(cls) : findDefaultEnumValue;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public String findEnumValue(Enum<?> r2) {
        String findEnumValue = this._primary.findEnumValue(r2);
        return findEnumValue == null ? this._secondary.findEnumValue(r2) : findEnumValue;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public boolean hasAsValueAnnotation(AnnotatedMethod annotatedMethod) {
        return this._primary.hasAsValueAnnotation(annotatedMethod) || this._secondary.hasAsValueAnnotation(annotatedMethod);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public boolean hasAnyGetterAnnotation(AnnotatedMethod annotatedMethod) {
        return this._primary.hasAnyGetterAnnotation(annotatedMethod) || this._secondary.hasAnyGetterAnnotation(annotatedMethod);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findDeserializer(Annotated annotated) {
        Object findDeserializer = this._primary.findDeserializer(annotated);
        return _isExplicitClassOrOb(findDeserializer, JsonDeserializer.None.class) ? findDeserializer : _explicitClassOrOb(this._secondary.findDeserializer(annotated), JsonDeserializer.None.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findKeyDeserializer(Annotated annotated) {
        Object findKeyDeserializer = this._primary.findKeyDeserializer(annotated);
        return _isExplicitClassOrOb(findKeyDeserializer, KeyDeserializer.None.class) ? findKeyDeserializer : _explicitClassOrOb(this._secondary.findKeyDeserializer(annotated), KeyDeserializer.None.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findContentDeserializer(Annotated annotated) {
        Object findContentDeserializer = this._primary.findContentDeserializer(annotated);
        return _isExplicitClassOrOb(findContentDeserializer, JsonDeserializer.None.class) ? findContentDeserializer : _explicitClassOrOb(this._secondary.findContentDeserializer(annotated), JsonDeserializer.None.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findDeserializationConverter(Annotated annotated) {
        Object findDeserializationConverter = this._primary.findDeserializationConverter(annotated);
        return findDeserializationConverter == null ? this._secondary.findDeserializationConverter(annotated) : findDeserializationConverter;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findDeserializationContentConverter(AnnotatedMember annotatedMember) {
        Object findDeserializationContentConverter = this._primary.findDeserializationContentConverter(annotatedMember);
        return findDeserializationContentConverter == null ? this._secondary.findDeserializationContentConverter(annotatedMember) : findDeserializationContentConverter;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JavaType refineDeserializationType(MapperConfig<?> mapperConfig, Annotated annotated, JavaType javaType) throws JsonMappingException {
        return this._primary.refineDeserializationType(mapperConfig, annotated, this._secondary.refineDeserializationType(mapperConfig, annotated, javaType));
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public Class<?> findDeserializationType(Annotated annotated, JavaType javaType) {
        Class<?> findDeserializationType = this._primary.findDeserializationType(annotated, javaType);
        return findDeserializationType != null ? findDeserializationType : this._secondary.findDeserializationType(annotated, javaType);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public Class<?> findDeserializationKeyType(Annotated annotated, JavaType javaType) {
        Class<?> findDeserializationKeyType = this._primary.findDeserializationKeyType(annotated, javaType);
        return findDeserializationKeyType == null ? this._secondary.findDeserializationKeyType(annotated, javaType) : findDeserializationKeyType;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public Class<?> findDeserializationContentType(Annotated annotated, JavaType javaType) {
        Class<?> findDeserializationContentType = this._primary.findDeserializationContentType(annotated, javaType);
        return findDeserializationContentType == null ? this._secondary.findDeserializationContentType(annotated, javaType) : findDeserializationContentType;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findValueInstantiator(AnnotatedClass annotatedClass) {
        Object findValueInstantiator = this._primary.findValueInstantiator(annotatedClass);
        return findValueInstantiator == null ? this._secondary.findValueInstantiator(annotatedClass) : findValueInstantiator;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?> findPOJOBuilder(AnnotatedClass annotatedClass) {
        Class<?> findPOJOBuilder = this._primary.findPOJOBuilder(annotatedClass);
        return findPOJOBuilder == null ? this._secondary.findPOJOBuilder(annotatedClass) : findPOJOBuilder;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass annotatedClass) {
        JsonPOJOBuilder.Value findPOJOBuilderConfig = this._primary.findPOJOBuilderConfig(annotatedClass);
        return findPOJOBuilderConfig == null ? this._secondary.findPOJOBuilderConfig(annotatedClass) : findPOJOBuilderConfig;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public PropertyName findNameForDeserialization(Annotated annotated) {
        PropertyName findNameForDeserialization;
        PropertyName findNameForDeserialization2 = this._primary.findNameForDeserialization(annotated);
        if (findNameForDeserialization2 == null) {
            return this._secondary.findNameForDeserialization(annotated);
        }
        return (findNameForDeserialization2 != PropertyName.USE_DEFAULT || (findNameForDeserialization = this._secondary.findNameForDeserialization(annotated)) == null) ? findNameForDeserialization2 : findNameForDeserialization;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean hasAnySetter(Annotated annotated) {
        Boolean hasAnySetter = this._primary.hasAnySetter(annotated);
        return hasAnySetter == null ? this._secondary.hasAnySetter(annotated) : hasAnySetter;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonSetter.Value findSetterInfo(Annotated annotated) {
        JsonSetter.Value findSetterInfo = this._secondary.findSetterInfo(annotated);
        JsonSetter.Value findSetterInfo2 = this._primary.findSetterInfo(annotated);
        return findSetterInfo == null ? findSetterInfo2 : findSetterInfo.withOverrides(findSetterInfo2);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean findMergeInfo(Annotated annotated) {
        Boolean findMergeInfo = this._primary.findMergeInfo(annotated);
        return findMergeInfo == null ? this._secondary.findMergeInfo(annotated) : findMergeInfo;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public boolean hasCreatorAnnotation(Annotated annotated) {
        return this._primary.hasCreatorAnnotation(annotated) || this._secondary.hasCreatorAnnotation(annotated);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public JsonCreator.Mode findCreatorBinding(Annotated annotated) {
        JsonCreator.Mode findCreatorBinding = this._primary.findCreatorBinding(annotated);
        return findCreatorBinding != null ? findCreatorBinding : this._secondary.findCreatorBinding(annotated);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonCreator.Mode findCreatorAnnotation(MapperConfig<?> mapperConfig, Annotated annotated) {
        JsonCreator.Mode findCreatorAnnotation = this._primary.findCreatorAnnotation(mapperConfig, annotated);
        return findCreatorAnnotation == null ? this._secondary.findCreatorAnnotation(mapperConfig, annotated) : findCreatorAnnotation;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    @Deprecated
    public boolean hasAnySetterAnnotation(AnnotatedMethod annotatedMethod) {
        return this._primary.hasAnySetterAnnotation(annotatedMethod) || this._secondary.hasAnySetterAnnotation(annotatedMethod);
    }

    protected boolean _isExplicitClassOrOb(Object obj, Class<?> cls) {
        if (obj == null || obj == cls) {
            return false;
        }
        if (obj instanceof Class) {
            return !ClassUtil.isBogusClass((Class) obj);
        }
        return true;
    }

    protected Object _explicitClassOrOb(Object obj, Class<?> cls) {
        if (obj == null || obj == cls) {
            return null;
        }
        if (!(obj instanceof Class) || !ClassUtil.isBogusClass((Class) obj)) {
            return obj;
        }
        return null;
    }
}
