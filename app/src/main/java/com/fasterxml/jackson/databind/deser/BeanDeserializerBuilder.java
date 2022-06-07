package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.Annotations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BeanDeserializerBuilder {
    protected SettableAnyProperty _anySetter;
    protected HashMap<String, SettableBeanProperty> _backRefProperties;
    protected final BeanDescription _beanDesc;
    protected AnnotatedMethod _buildMethod;
    protected JsonPOJOBuilder.Value _builderConfig;
    protected final DeserializationConfig _config;
    protected final DeserializationContext _context;
    protected HashSet<String> _ignorableProps;
    protected boolean _ignoreAllUnknown;
    protected List<ValueInjector> _injectables;
    protected ObjectIdReader _objectIdReader;
    protected final Map<String, SettableBeanProperty> _properties = new LinkedHashMap();
    protected ValueInstantiator _valueInstantiator;

    public BeanDeserializerBuilder(BeanDescription beanDescription, DeserializationContext deserializationContext) {
        this._beanDesc = beanDescription;
        this._context = deserializationContext;
        this._config = deserializationContext.getConfig();
    }

    protected BeanDeserializerBuilder(BeanDeserializerBuilder beanDeserializerBuilder) {
        this._beanDesc = beanDeserializerBuilder._beanDesc;
        this._context = beanDeserializerBuilder._context;
        this._config = beanDeserializerBuilder._config;
        this._properties.putAll(beanDeserializerBuilder._properties);
        this._injectables = a(beanDeserializerBuilder._injectables);
        this._backRefProperties = a(beanDeserializerBuilder._backRefProperties);
        this._ignorableProps = beanDeserializerBuilder._ignorableProps;
        this._valueInstantiator = beanDeserializerBuilder._valueInstantiator;
        this._objectIdReader = beanDeserializerBuilder._objectIdReader;
        this._anySetter = beanDeserializerBuilder._anySetter;
        this._ignoreAllUnknown = beanDeserializerBuilder._ignoreAllUnknown;
        this._buildMethod = beanDeserializerBuilder._buildMethod;
        this._builderConfig = beanDeserializerBuilder._builderConfig;
    }

    private static HashMap<String, SettableBeanProperty> a(HashMap<String, SettableBeanProperty> hashMap) {
        if (hashMap == null) {
            return null;
        }
        return new HashMap<>(hashMap);
    }

    private static <T> List<T> a(List<T> list) {
        if (list == null) {
            return null;
        }
        return new ArrayList(list);
    }

    public void addOrReplaceProperty(SettableBeanProperty settableBeanProperty, boolean z) {
        this._properties.put(settableBeanProperty.getName(), settableBeanProperty);
    }

    public void addProperty(SettableBeanProperty settableBeanProperty) {
        SettableBeanProperty put = this._properties.put(settableBeanProperty.getName(), settableBeanProperty);
        if (put != null && put != settableBeanProperty) {
            throw new IllegalArgumentException("Duplicate property '" + settableBeanProperty.getName() + "' for " + this._beanDesc.getType());
        }
    }

    public void addBackReferenceProperty(String str, SettableBeanProperty settableBeanProperty) {
        if (this._backRefProperties == null) {
            this._backRefProperties = new HashMap<>(4);
        }
        settableBeanProperty.fixAccess(this._config);
        this._backRefProperties.put(str, settableBeanProperty);
    }

    public void addInjectable(PropertyName propertyName, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember, Object obj) {
        if (this._injectables == null) {
            this._injectables = new ArrayList();
        }
        boolean canOverrideAccessModifiers = this._config.canOverrideAccessModifiers();
        boolean z = canOverrideAccessModifiers && this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS);
        if (canOverrideAccessModifiers) {
            annotatedMember.fixAccess(z);
        }
        this._injectables.add(new ValueInjector(propertyName, javaType, annotatedMember, obj));
    }

    public void addIgnorable(String str) {
        if (this._ignorableProps == null) {
            this._ignorableProps = new HashSet<>();
        }
        this._ignorableProps.add(str);
    }

    public void addCreatorProperty(SettableBeanProperty settableBeanProperty) {
        addProperty(settableBeanProperty);
    }

    public void setAnySetter(SettableAnyProperty settableAnyProperty) {
        if (this._anySetter == null || settableAnyProperty == null) {
            this._anySetter = settableAnyProperty;
            return;
        }
        throw new IllegalStateException("_anySetter already set to non-null");
    }

    public void setIgnoreUnknownProperties(boolean z) {
        this._ignoreAllUnknown = z;
    }

    public void setValueInstantiator(ValueInstantiator valueInstantiator) {
        this._valueInstantiator = valueInstantiator;
    }

    public void setObjectIdReader(ObjectIdReader objectIdReader) {
        this._objectIdReader = objectIdReader;
    }

    public void setPOJOBuilder(AnnotatedMethod annotatedMethod, JsonPOJOBuilder.Value value) {
        this._buildMethod = annotatedMethod;
        this._builderConfig = value;
    }

    public Iterator<SettableBeanProperty> getProperties() {
        return this._properties.values().iterator();
    }

    public SettableBeanProperty findProperty(PropertyName propertyName) {
        return this._properties.get(propertyName.getSimpleName());
    }

    public boolean hasProperty(PropertyName propertyName) {
        return findProperty(propertyName) != null;
    }

    public SettableBeanProperty removeProperty(PropertyName propertyName) {
        return this._properties.remove(propertyName.getSimpleName());
    }

    public SettableAnyProperty getAnySetter() {
        return this._anySetter;
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    public List<ValueInjector> getInjectables() {
        return this._injectables;
    }

    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    public AnnotatedMethod getBuildMethod() {
        return this._buildMethod;
    }

    public JsonPOJOBuilder.Value getBuilderConfig() {
        return this._builderConfig;
    }

    public boolean hasIgnorable(String str) {
        HashSet<String> hashSet = this._ignorableProps;
        return hashSet != null && hashSet.contains(str);
    }

    public JsonDeserializer<?> build() {
        boolean z;
        Collection<SettableBeanProperty> values = this._properties.values();
        _fixAccess(values);
        BeanPropertyMap construct = BeanPropertyMap.construct(values, this._config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES), _collectAliases(values));
        construct.assignIndexes();
        boolean z2 = !this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
        if (!z2) {
            for (SettableBeanProperty settableBeanProperty : values) {
                if (settableBeanProperty.hasViews()) {
                    z = true;
                    break;
                }
            }
        }
        z = z2;
        ObjectIdReader objectIdReader = this._objectIdReader;
        return new BeanDeserializer(this, this._beanDesc, objectIdReader != null ? construct.withProperty(new ObjectIdValueProperty(objectIdReader, PropertyMetadata.STD_REQUIRED)) : construct, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, z);
    }

    public AbstractDeserializer buildAbstract() {
        return new AbstractDeserializer(this, this._beanDesc, this._backRefProperties, this._properties);
    }

    public JsonDeserializer<?> buildBuilderBased(JavaType javaType, String str) throws JsonMappingException {
        boolean z;
        AnnotatedMethod annotatedMethod = this._buildMethod;
        if (annotatedMethod != null) {
            Class<?> rawReturnType = annotatedMethod.getRawReturnType();
            Class<?> rawClass = javaType.getRawClass();
            if (rawReturnType != rawClass && !rawReturnType.isAssignableFrom(rawClass) && !rawClass.isAssignableFrom(rawReturnType)) {
                this._context.reportBadDefinition(this._beanDesc.getType(), String.format("Build method '%s' has wrong return type (%s), not compatible with POJO type (%s)", this._buildMethod.getFullName(), rawReturnType.getName(), javaType.getRawClass().getName()));
            }
        } else if (!str.isEmpty()) {
            this._context.reportBadDefinition(this._beanDesc.getType(), String.format("Builder class %s does not have build method (name: '%s')", this._beanDesc.getBeanClass().getName(), str));
        }
        Collection<SettableBeanProperty> values = this._properties.values();
        _fixAccess(values);
        BeanPropertyMap construct = BeanPropertyMap.construct(values, this._config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES), _collectAliases(values));
        construct.assignIndexes();
        boolean z2 = !this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
        if (!z2) {
            for (SettableBeanProperty settableBeanProperty : values) {
                if (settableBeanProperty.hasViews()) {
                    z = true;
                    break;
                }
            }
        }
        z = z2;
        ObjectIdReader objectIdReader = this._objectIdReader;
        return new BuilderBasedDeserializer(this, this._beanDesc, javaType, objectIdReader != null ? construct.withProperty(new ObjectIdValueProperty(objectIdReader, PropertyMetadata.STD_REQUIRED)) : construct, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, z);
    }

    protected void _fixAccess(Collection<SettableBeanProperty> collection) {
        for (SettableBeanProperty settableBeanProperty : collection) {
            settableBeanProperty.fixAccess(this._config);
        }
        SettableAnyProperty settableAnyProperty = this._anySetter;
        if (settableAnyProperty != null) {
            settableAnyProperty.fixAccess(this._config);
        }
        AnnotatedMethod annotatedMethod = this._buildMethod;
        if (annotatedMethod != null) {
            annotatedMethod.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
        }
    }

    protected Map<String, List<PropertyName>> _collectAliases(Collection<SettableBeanProperty> collection) {
        AnnotationIntrospector annotationIntrospector = this._config.getAnnotationIntrospector();
        HashMap hashMap = null;
        if (annotationIntrospector != null) {
            for (SettableBeanProperty settableBeanProperty : collection) {
                List<PropertyName> findPropertyAliases = annotationIntrospector.findPropertyAliases(settableBeanProperty.getMember());
                if (findPropertyAliases != null && !findPropertyAliases.isEmpty()) {
                    if (hashMap == null) {
                        hashMap = new HashMap();
                    }
                    hashMap.put(settableBeanProperty.getName(), findPropertyAliases);
                }
            }
        }
        return hashMap == null ? Collections.emptyMap() : hashMap;
    }
}
