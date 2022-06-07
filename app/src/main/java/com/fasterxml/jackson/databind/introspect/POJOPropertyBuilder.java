package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.ConfigOverride;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes.dex */
public class POJOPropertyBuilder extends BeanPropertyDefinition implements Comparable<POJOPropertyBuilder> {
    private static final AnnotationIntrospector.ReferenceProperty a = AnnotationIntrospector.ReferenceProperty.managed("");
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final MapperConfig<?> _config;
    protected Linked<AnnotatedParameter> _ctorParameters;
    protected Linked<AnnotatedField> _fields;
    protected final boolean _forSerialization;
    protected Linked<AnnotatedMethod> _getters;
    protected final PropertyName _internalName;
    protected transient PropertyMetadata _metadata;
    protected final PropertyName _name;
    protected transient AnnotationIntrospector.ReferenceProperty _referenceInfo;
    protected Linked<AnnotatedMethod> _setters;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface a<T> {
        T b(AnnotatedMember annotatedMember);
    }

    public POJOPropertyBuilder(MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, boolean z, PropertyName propertyName) {
        this(mapperConfig, annotationIntrospector, z, propertyName, propertyName);
    }

    protected POJOPropertyBuilder(MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, boolean z, PropertyName propertyName, PropertyName propertyName2) {
        this._config = mapperConfig;
        this._annotationIntrospector = annotationIntrospector;
        this._internalName = propertyName;
        this._name = propertyName2;
        this._forSerialization = z;
    }

    protected POJOPropertyBuilder(POJOPropertyBuilder pOJOPropertyBuilder, PropertyName propertyName) {
        this._config = pOJOPropertyBuilder._config;
        this._annotationIntrospector = pOJOPropertyBuilder._annotationIntrospector;
        this._internalName = pOJOPropertyBuilder._internalName;
        this._name = propertyName;
        this._fields = pOJOPropertyBuilder._fields;
        this._ctorParameters = pOJOPropertyBuilder._ctorParameters;
        this._getters = pOJOPropertyBuilder._getters;
        this._setters = pOJOPropertyBuilder._setters;
        this._forSerialization = pOJOPropertyBuilder._forSerialization;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public POJOPropertyBuilder withName(PropertyName propertyName) {
        return new POJOPropertyBuilder(this, propertyName);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public POJOPropertyBuilder withSimpleName(String str) {
        PropertyName withSimpleName = this._name.withSimpleName(str);
        return withSimpleName == this._name ? this : new POJOPropertyBuilder(this, withSimpleName);
    }

    public int compareTo(POJOPropertyBuilder pOJOPropertyBuilder) {
        if (this._ctorParameters != null) {
            if (pOJOPropertyBuilder._ctorParameters == null) {
                return -1;
            }
        } else if (pOJOPropertyBuilder._ctorParameters != null) {
            return 1;
        }
        return getName().compareTo(pOJOPropertyBuilder.getName());
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition, com.fasterxml.jackson.databind.util.Named
    public String getName() {
        PropertyName propertyName = this._name;
        if (propertyName == null) {
            return null;
        }
        return propertyName.getSimpleName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyName getFullName() {
        return this._name;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasName(PropertyName propertyName) {
        return this._name.equals(propertyName);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public String getInternalName() {
        return this._internalName.getSimpleName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyName getWrapperName() {
        AnnotationIntrospector annotationIntrospector;
        AnnotatedMember primaryMember = getPrimaryMember();
        if (primaryMember == null || (annotationIntrospector = this._annotationIntrospector) == null) {
            return null;
        }
        return annotationIntrospector.findWrapperName(primaryMember);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isExplicitlyIncluded() {
        return e(this._fields) || e(this._getters) || e(this._setters) || f(this._ctorParameters);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isExplicitlyNamed() {
        return f(this._fields) || f(this._getters) || f(this._setters) || f(this._ctorParameters);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyMetadata getMetadata() {
        PropertyMetadata propertyMetadata;
        if (this._metadata == null) {
            Boolean _findRequired = _findRequired();
            String _findDescription = _findDescription();
            Integer _findIndex = _findIndex();
            String _findDefaultValue = _findDefaultValue();
            if (_findRequired == null && _findIndex == null && _findDefaultValue == null) {
                if (_findDescription == null) {
                    propertyMetadata = PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
                } else {
                    propertyMetadata = PropertyMetadata.STD_REQUIRED_OR_OPTIONAL.withDescription(_findDescription);
                }
                this._metadata = propertyMetadata;
            } else {
                this._metadata = PropertyMetadata.construct(_findRequired, _findDescription, _findIndex, _findDefaultValue);
            }
            if (!this._forSerialization) {
                this._metadata = _getSetterInfo(this._metadata);
            }
        }
        return this._metadata;
    }

    protected PropertyMetadata _getSetterInfo(PropertyMetadata propertyMetadata) {
        Nulls nulls;
        Boolean mergeable;
        Boolean findMergeInfo;
        AnnotatedMember primaryMember = getPrimaryMember();
        AnnotatedMember accessor = getAccessor();
        Nulls nulls2 = null;
        boolean z = true;
        if (primaryMember != null) {
            AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
            if (annotationIntrospector != null) {
                if (!(accessor == null || (findMergeInfo = annotationIntrospector.findMergeInfo(primaryMember)) == null)) {
                    if (findMergeInfo.booleanValue()) {
                        propertyMetadata = propertyMetadata.withMergeInfo(PropertyMetadata.MergeInfo.createForPropertyOverride(accessor));
                        z = false;
                    } else {
                        z = false;
                    }
                }
                JsonSetter.Value findSetterInfo = this._annotationIntrospector.findSetterInfo(primaryMember);
                if (findSetterInfo != null) {
                    nulls2 = findSetterInfo.nonDefaultValueNulls();
                    nulls = findSetterInfo.nonDefaultContentNulls();
                } else {
                    nulls = null;
                }
            } else {
                nulls = null;
            }
            if (z || nulls2 == null || nulls == null) {
                ConfigOverride configOverride = this._config.getConfigOverride(getRawPrimaryType());
                JsonSetter.Value setterInfo = configOverride.getSetterInfo();
                if (setterInfo != null) {
                    if (nulls2 == null) {
                        nulls2 = setterInfo.nonDefaultValueNulls();
                    }
                    if (nulls == null) {
                        nulls = setterInfo.nonDefaultContentNulls();
                    }
                }
                if (!(!z || accessor == null || (mergeable = configOverride.getMergeable()) == null)) {
                    if (mergeable.booleanValue()) {
                        propertyMetadata = propertyMetadata.withMergeInfo(PropertyMetadata.MergeInfo.createForTypeOverride(accessor));
                        z = false;
                    } else {
                        z = false;
                    }
                }
            }
        } else {
            nulls = null;
        }
        if (z || nulls2 == null || nulls == null) {
            JsonSetter.Value defaultSetterInfo = this._config.getDefaultSetterInfo();
            if (nulls2 == null) {
                nulls2 = defaultSetterInfo.nonDefaultValueNulls();
            }
            if (nulls == null) {
                nulls = defaultSetterInfo.nonDefaultContentNulls();
            }
            if (z) {
                if (Boolean.TRUE.equals(this._config.getDefaultMergeable()) && accessor != null) {
                    propertyMetadata = propertyMetadata.withMergeInfo(PropertyMetadata.MergeInfo.createForDefaults(accessor));
                }
            }
        }
        return (nulls2 == null && nulls == null) ? propertyMetadata : propertyMetadata.withNulls(nulls2, nulls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public JavaType getPrimaryType() {
        if (this._forSerialization) {
            AnnotatedMethod getter = getGetter();
            if (getter != null) {
                return getter.getType();
            }
            AnnotatedField field = getField();
            if (field == null) {
                return TypeFactory.unknownType();
            }
            return field.getType();
        }
        Annotated constructorParameter = getConstructorParameter();
        if (constructorParameter == null) {
            AnnotatedMethod setter = getSetter();
            if (setter != null) {
                return setter.getParameterType(0);
            }
            constructorParameter = getField();
        }
        if (constructorParameter == null && (constructorParameter = getGetter()) == null) {
            return TypeFactory.unknownType();
        }
        return constructorParameter.getType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Class<?> getRawPrimaryType() {
        return getPrimaryType().getRawClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasGetter() {
        return this._getters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasSetter() {
        return this._setters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasField() {
        return this._fields != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasConstructorParameter() {
        return this._ctorParameters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean couldDeserialize() {
        return (this._ctorParameters == null && this._setters == null && this._fields == null) ? false : true;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean couldSerialize() {
        return (this._getters == null && this._fields == null) ? false : true;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getGetter() {
        Linked<AnnotatedMethod> linked = this._getters;
        if (linked == null) {
            return null;
        }
        Linked<AnnotatedMethod> linked2 = linked.next;
        if (linked2 == null) {
            return linked.value;
        }
        for (Linked<AnnotatedMethod> linked3 = linked2; linked3 != null; linked3 = linked3.next) {
            Class<?> declaringClass = linked.value.getDeclaringClass();
            Class<?> declaringClass2 = linked3.value.getDeclaringClass();
            if (declaringClass != declaringClass2) {
                if (!declaringClass.isAssignableFrom(declaringClass2)) {
                    if (declaringClass2.isAssignableFrom(declaringClass)) {
                        continue;
                    }
                }
                linked = linked3;
            }
            int _getterPriority = _getterPriority(linked3.value);
            int _getterPriority2 = _getterPriority(linked.value);
            if (_getterPriority != _getterPriority2) {
                if (_getterPriority >= _getterPriority2) {
                }
                linked = linked3;
            } else {
                throw new IllegalArgumentException("Conflicting getter definitions for property \"" + getName() + "\": " + linked.value.getFullName() + " vs " + linked3.value.getFullName());
            }
        }
        this._getters = linked.withoutNext();
        return linked.value;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getSetter() {
        Linked<AnnotatedMethod> linked = this._setters;
        if (linked == null) {
            return null;
        }
        Linked<AnnotatedMethod> linked2 = linked.next;
        if (linked2 == null) {
            return linked.value;
        }
        for (Linked<AnnotatedMethod> linked3 = linked2; linked3 != null; linked3 = linked3.next) {
            Class<?> declaringClass = linked.value.getDeclaringClass();
            Class<?> declaringClass2 = linked3.value.getDeclaringClass();
            if (declaringClass != declaringClass2) {
                if (!declaringClass.isAssignableFrom(declaringClass2)) {
                    if (declaringClass2.isAssignableFrom(declaringClass)) {
                        continue;
                    }
                }
                linked = linked3;
            }
            AnnotatedMethod annotatedMethod = linked3.value;
            AnnotatedMethod annotatedMethod2 = linked.value;
            int _setterPriority = _setterPriority(annotatedMethod);
            int _setterPriority2 = _setterPriority(annotatedMethod2);
            if (_setterPriority != _setterPriority2) {
                if (_setterPriority >= _setterPriority2) {
                }
                linked = linked3;
            } else {
                AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
                if (annotationIntrospector != null) {
                    AnnotatedMethod resolveSetterConflict = annotationIntrospector.resolveSetterConflict(this._config, annotatedMethod2, annotatedMethod);
                    if (resolveSetterConflict != annotatedMethod2) {
                        if (resolveSetterConflict != annotatedMethod) {
                        }
                        linked = linked3;
                    } else {
                        continue;
                    }
                }
                throw new IllegalArgumentException(String.format("Conflicting setter definitions for property \"%s\": %s vs %s", getName(), linked.value.getFullName(), linked3.value.getFullName()));
            }
        }
        this._setters = linked.withoutNext();
        return linked.value;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedField getField() {
        Linked<AnnotatedField> linked = this._fields;
        if (linked == null) {
            return null;
        }
        AnnotatedField annotatedField = linked.value;
        for (Linked linked2 = this._fields.next; linked2 != null; linked2 = linked2.next) {
            AnnotatedField annotatedField2 = (AnnotatedField) linked2.value;
            Class<?> declaringClass = annotatedField.getDeclaringClass();
            Class<?> declaringClass2 = annotatedField2.getDeclaringClass();
            if (declaringClass != declaringClass2) {
                if (declaringClass.isAssignableFrom(declaringClass2)) {
                    annotatedField = annotatedField2;
                } else if (declaringClass2.isAssignableFrom(declaringClass)) {
                }
            }
            throw new IllegalArgumentException("Multiple fields representing property \"" + getName() + "\": " + annotatedField.getFullName() + " vs " + annotatedField2.getFullName());
        }
        return annotatedField;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedParameter getConstructorParameter() {
        Linked linked = this._ctorParameters;
        if (linked == null) {
            return null;
        }
        while (!(((AnnotatedParameter) linked.value).getOwner() instanceof AnnotatedConstructor)) {
            linked = linked.next;
            if (linked == null) {
                return this._ctorParameters.value;
            }
        }
        return (AnnotatedParameter) linked.value;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Iterator<AnnotatedParameter> getConstructorParameters() {
        Linked<AnnotatedParameter> linked = this._ctorParameters;
        if (linked == null) {
            return ClassUtil.emptyIterator();
        }
        return new MemberIterator(linked);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMember getPrimaryMember() {
        if (this._forSerialization) {
            return getAccessor();
        }
        AnnotatedMember mutator = getMutator();
        return mutator == null ? getAccessor() : mutator;
    }

    protected int _getterPriority(AnnotatedMethod annotatedMethod) {
        String name = annotatedMethod.getName();
        if (!name.startsWith(BluetoothConstants.GET) || name.length() <= 3) {
            return (!name.startsWith(ai.ae) || name.length() <= 2) ? 3 : 2;
        }
        return 1;
    }

    protected int _setterPriority(AnnotatedMethod annotatedMethod) {
        String name = annotatedMethod.getName();
        return (!name.startsWith(BluetoothConstants.SET) || name.length() <= 3) ? 2 : 1;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Class<?>[] findViews() {
        return (Class[]) fromMemberAnnotations(new a<Class<?>[]>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.1
            /* renamed from: a */
            public Class<?>[] b(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findViews(annotatedMember);
            }
        });
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotationIntrospector.ReferenceProperty findReferenceType() {
        AnnotationIntrospector.ReferenceProperty referenceProperty = this._referenceInfo;
        if (referenceProperty == null) {
            AnnotationIntrospector.ReferenceProperty referenceProperty2 = (AnnotationIntrospector.ReferenceProperty) fromMemberAnnotations(new a<AnnotationIntrospector.ReferenceProperty>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.3
                /* renamed from: a */
                public AnnotationIntrospector.ReferenceProperty b(AnnotatedMember annotatedMember) {
                    return POJOPropertyBuilder.this._annotationIntrospector.findReferenceType(annotatedMember);
                }
            });
            this._referenceInfo = referenceProperty2 == null ? a : referenceProperty2;
            return referenceProperty2;
        } else if (referenceProperty == a) {
            return null;
        } else {
            return referenceProperty;
        }
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isTypeId() {
        Boolean bool = (Boolean) fromMemberAnnotations(new a<Boolean>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.4
            /* renamed from: a */
            public Boolean b(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.isTypeId(annotatedMember);
            }
        });
        return bool != null && bool.booleanValue();
    }

    protected Boolean _findRequired() {
        return (Boolean) fromMemberAnnotations(new a<Boolean>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.5
            /* renamed from: a */
            public Boolean b(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.hasRequiredMarker(annotatedMember);
            }
        });
    }

    protected String _findDescription() {
        return (String) fromMemberAnnotations(new a<String>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.6
            /* renamed from: a */
            public String b(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findPropertyDescription(annotatedMember);
            }
        });
    }

    protected Integer _findIndex() {
        return (Integer) fromMemberAnnotations(new a<Integer>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.7
            /* renamed from: a */
            public Integer b(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findPropertyIndex(annotatedMember);
            }
        });
    }

    protected String _findDefaultValue() {
        return (String) fromMemberAnnotations(new a<String>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.8
            /* renamed from: a */
            public String b(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findPropertyDefaultValue(annotatedMember);
            }
        });
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public ObjectIdInfo findObjectIdInfo() {
        return (ObjectIdInfo) fromMemberAnnotations(new a<ObjectIdInfo>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.9
            /* renamed from: a */
            public ObjectIdInfo b(AnnotatedMember annotatedMember) {
                ObjectIdInfo findObjectIdInfo = POJOPropertyBuilder.this._annotationIntrospector.findObjectIdInfo(annotatedMember);
                return findObjectIdInfo != null ? POJOPropertyBuilder.this._annotationIntrospector.findObjectReferenceInfo(annotatedMember, findObjectIdInfo) : findObjectIdInfo;
            }
        });
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public JsonInclude.Value findInclusion() {
        AnnotatedMember accessor = getAccessor();
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        JsonInclude.Value findPropertyInclusion = annotationIntrospector == null ? null : annotationIntrospector.findPropertyInclusion(accessor);
        return findPropertyInclusion == null ? JsonInclude.Value.empty() : findPropertyInclusion;
    }

    public JsonProperty.Access findAccess() {
        return (JsonProperty.Access) fromMemberAnnotationsExcept(new a<JsonProperty.Access>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.10
            /* renamed from: a */
            public JsonProperty.Access b(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findPropertyAccess(annotatedMember);
            }
        }, JsonProperty.Access.AUTO);
    }

    public void addField(AnnotatedField annotatedField, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
        this._fields = new Linked<>(annotatedField, this._fields, propertyName, z, z2, z3);
    }

    public void addCtor(AnnotatedParameter annotatedParameter, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
        this._ctorParameters = new Linked<>(annotatedParameter, this._ctorParameters, propertyName, z, z2, z3);
    }

    public void addGetter(AnnotatedMethod annotatedMethod, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
        this._getters = new Linked<>(annotatedMethod, this._getters, propertyName, z, z2, z3);
    }

    public void addSetter(AnnotatedMethod annotatedMethod, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
        this._setters = new Linked<>(annotatedMethod, this._setters, propertyName, z, z2, z3);
    }

    public void addAll(POJOPropertyBuilder pOJOPropertyBuilder) {
        this._fields = a(this._fields, pOJOPropertyBuilder._fields);
        this._ctorParameters = a(this._ctorParameters, pOJOPropertyBuilder._ctorParameters);
        this._getters = a(this._getters, pOJOPropertyBuilder._getters);
        this._setters = a(this._setters, pOJOPropertyBuilder._setters);
    }

    private static <T> Linked<T> a(Linked<T> linked, Linked<T> linked2) {
        return linked == null ? linked2 : linked2 == null ? linked : linked.append(linked2);
    }

    public void removeIgnored() {
        this._fields = b(this._fields);
        this._getters = b(this._getters);
        this._setters = b(this._setters);
        this._ctorParameters = b(this._ctorParameters);
    }

    public JsonProperty.Access removeNonVisible(boolean z) {
        JsonProperty.Access findAccess = findAccess();
        if (findAccess == null) {
            findAccess = JsonProperty.Access.AUTO;
        }
        switch (findAccess) {
            case READ_ONLY:
                this._setters = null;
                this._ctorParameters = null;
                if (!this._forSerialization) {
                    this._fields = null;
                    break;
                }
                break;
            case READ_WRITE:
                break;
            case WRITE_ONLY:
                this._getters = null;
                if (this._forSerialization) {
                    this._fields = null;
                    break;
                }
                break;
            default:
                this._getters = c(this._getters);
                this._ctorParameters = c(this._ctorParameters);
                if (!z || this._getters == null) {
                    this._fields = c(this._fields);
                    this._setters = c(this._setters);
                    break;
                }
                break;
        }
        return findAccess;
    }

    public void removeConstructors() {
        this._ctorParameters = null;
    }

    public void trimByVisibility() {
        this._fields = d(this._fields);
        this._getters = d(this._getters);
        this._setters = d(this._setters);
        this._ctorParameters = d(this._ctorParameters);
    }

    public void mergeAnnotations(boolean z) {
        if (z) {
            Linked<AnnotatedMethod> linked = this._getters;
            if (linked != null) {
                this._getters = a(this._getters, a(0, linked, this._fields, this._ctorParameters, this._setters));
                return;
            }
            Linked<AnnotatedField> linked2 = this._fields;
            if (linked2 != null) {
                this._fields = a(this._fields, a(0, linked2, this._ctorParameters, this._setters));
                return;
            }
            return;
        }
        Linked<AnnotatedParameter> linked3 = this._ctorParameters;
        if (linked3 != null) {
            this._ctorParameters = a(this._ctorParameters, a(0, linked3, this._setters, this._fields, this._getters));
            return;
        }
        Linked<AnnotatedMethod> linked4 = this._setters;
        if (linked4 != null) {
            this._setters = a(this._setters, a(0, linked4, this._fields, this._getters));
            return;
        }
        Linked<AnnotatedField> linked5 = this._fields;
        if (linked5 != null) {
            this._fields = a(this._fields, a(0, linked5, this._getters));
        }
    }

    private AnnotationMap a(int i, Linked<? extends AnnotatedMember>... linkedArr) {
        AnnotationMap a2 = a(linkedArr[i]);
        do {
            i++;
            if (i >= linkedArr.length) {
                return a2;
            }
        } while (linkedArr[i] == null);
        return AnnotationMap.merge(a2, a(i, linkedArr));
    }

    private <T extends AnnotatedMember> AnnotationMap a(Linked<T> linked) {
        AnnotationMap allAnnotations = linked.value.getAllAnnotations();
        return linked.next != null ? AnnotationMap.merge(allAnnotations, a(linked.next)) : allAnnotations;
    }

    private <T extends AnnotatedMember> Linked<T> a(Linked<T> linked, AnnotationMap annotationMap) {
        AnnotatedMember annotatedMember = (AnnotatedMember) linked.value.withAnnotations(annotationMap);
        Linked<T> linked2 = linked.next;
        Linked linked3 = linked;
        if (linked2 != null) {
            linked3 = (Linked<T>) linked.withNext(a(linked.next, annotationMap));
        }
        return linked3.withValue(annotatedMember);
    }

    private <T> Linked<T> b(Linked<T> linked) {
        return linked == null ? linked : linked.withoutIgnored();
    }

    private <T> Linked<T> c(Linked<T> linked) {
        return linked == null ? linked : linked.withoutNonVisible();
    }

    private <T> Linked<T> d(Linked<T> linked) {
        return linked == null ? linked : linked.trimByVisibility();
    }

    private <T> boolean e(Linked<T> linked) {
        while (linked != null) {
            if (linked.name != null && linked.name.hasSimpleName()) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    private <T> boolean f(Linked<T> linked) {
        while (linked != null) {
            if (linked.name != null && linked.isNameExplicit) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    public boolean anyVisible() {
        return g(this._fields) || g(this._getters) || g(this._setters) || g(this._ctorParameters);
    }

    private <T> boolean g(Linked<T> linked) {
        while (linked != null) {
            if (linked.isVisible) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    public boolean anyIgnorals() {
        return h(this._fields) || h(this._getters) || h(this._setters) || h(this._ctorParameters);
    }

    private <T> boolean h(Linked<T> linked) {
        while (linked != null) {
            if (linked.isMarkedIgnored) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    public Set<PropertyName> findExplicitNames() {
        Set<PropertyName> a2 = a(this._ctorParameters, a(this._setters, a(this._getters, a(this._fields, (Set<PropertyName>) null))));
        return a2 == null ? Collections.emptySet() : a2;
    }

    public Collection<POJOPropertyBuilder> explode(Collection<PropertyName> collection) {
        HashMap hashMap = new HashMap();
        a(collection, hashMap, this._fields);
        a(collection, hashMap, this._getters);
        a(collection, hashMap, this._setters);
        a(collection, hashMap, this._ctorParameters);
        return hashMap.values();
    }

    private void a(Collection<PropertyName> collection, Map<PropertyName, POJOPropertyBuilder> map, Linked<?> linked) {
        for (Linked linked2 = linked; linked2 != null; linked2 = linked2.next) {
            PropertyName propertyName = linked2.name;
            if (linked2.isNameExplicit && propertyName != null) {
                POJOPropertyBuilder pOJOPropertyBuilder = map.get(propertyName);
                if (pOJOPropertyBuilder == null) {
                    pOJOPropertyBuilder = new POJOPropertyBuilder(this._config, this._annotationIntrospector, this._forSerialization, this._internalName, propertyName);
                    map.put(propertyName, pOJOPropertyBuilder);
                }
                if (linked == this._fields) {
                    pOJOPropertyBuilder._fields = linked2.withNext(pOJOPropertyBuilder._fields);
                } else if (linked == this._getters) {
                    pOJOPropertyBuilder._getters = linked2.withNext(pOJOPropertyBuilder._getters);
                } else if (linked == this._setters) {
                    pOJOPropertyBuilder._setters = linked2.withNext(pOJOPropertyBuilder._setters);
                } else if (linked == this._ctorParameters) {
                    pOJOPropertyBuilder._ctorParameters = linked2.withNext(pOJOPropertyBuilder._ctorParameters);
                } else {
                    throw new IllegalStateException("Internal error: mismatched accessors, property: " + this);
                }
            } else if (linked2.isVisible) {
                throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found multiple explicit names: " + collection + ", but also implicit accessor: " + linked2);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:0:?, code lost:
        r2 = r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.Set<com.fasterxml.jackson.databind.PropertyName> a(com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.Linked<? extends com.fasterxml.jackson.databind.introspect.AnnotatedMember> r2, java.util.Set<com.fasterxml.jackson.databind.PropertyName> r3) {
        /*
            r1 = this;
        L_0x0000:
            if (r2 == 0) goto L_0x001a
            boolean r0 = r2.isNameExplicit
            if (r0 == 0) goto L_0x0017
            com.fasterxml.jackson.databind.PropertyName r0 = r2.name
            if (r0 != 0) goto L_0x000b
            goto L_0x0017
        L_0x000b:
            if (r3 != 0) goto L_0x0012
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
        L_0x0012:
            com.fasterxml.jackson.databind.PropertyName r0 = r2.name
            r3.add(r0)
        L_0x0017:
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked<T> r2 = r2.next
            goto L_0x0000
        L_0x001a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.a(com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked, java.util.Set):java.util.Set");
    }

    public String toString() {
        return "[Property '" + this._name + "'; ctors: " + this._ctorParameters + ", field(s): " + this._fields + ", getter(s): " + this._getters + ", setter(s): " + this._setters + "]";
    }

    protected <T> T fromMemberAnnotations(a<T> aVar) {
        Linked<AnnotatedField> linked;
        Linked<AnnotatedMethod> linked2;
        T t = null;
        if (this._annotationIntrospector == null) {
            return null;
        }
        if (this._forSerialization) {
            Linked<AnnotatedMethod> linked3 = this._getters;
            if (linked3 != null) {
                t = aVar.b(linked3.value);
            }
        } else {
            Linked<AnnotatedParameter> linked4 = this._ctorParameters;
            if (linked4 != null) {
                t = aVar.b(linked4.value);
            }
            if (t == null && (linked2 = this._setters) != null) {
                t = aVar.b(linked2.value);
            }
        }
        return (t != null || (linked = this._fields) == null) ? t : aVar.b(linked.value);
    }

    protected <T> T fromMemberAnnotationsExcept(a<T> aVar, T t) {
        T b;
        T b2;
        T b3;
        T b4;
        T b5;
        T b6;
        T b7;
        T b8;
        if (this._annotationIntrospector == null) {
            return null;
        }
        if (this._forSerialization) {
            Linked<AnnotatedMethod> linked = this._getters;
            if (linked != null && (b8 = aVar.b(linked.value)) != null && b8 != t) {
                return b8;
            }
            Linked<AnnotatedField> linked2 = this._fields;
            if (linked2 != null && (b7 = aVar.b(linked2.value)) != null && b7 != t) {
                return b7;
            }
            Linked<AnnotatedParameter> linked3 = this._ctorParameters;
            if (linked3 != null && (b6 = aVar.b(linked3.value)) != null && b6 != t) {
                return b6;
            }
            Linked<AnnotatedMethod> linked4 = this._setters;
            if (linked4 == null || (b5 = aVar.b(linked4.value)) == null || b5 == t) {
                return null;
            }
            return b5;
        }
        Linked<AnnotatedParameter> linked5 = this._ctorParameters;
        if (linked5 != null && (b4 = aVar.b(linked5.value)) != null && b4 != t) {
            return b4;
        }
        Linked<AnnotatedMethod> linked6 = this._setters;
        if (linked6 != null && (b3 = aVar.b(linked6.value)) != null && b3 != t) {
            return b3;
        }
        Linked<AnnotatedField> linked7 = this._fields;
        if (linked7 != null && (b2 = aVar.b(linked7.value)) != null && b2 != t) {
            return b2;
        }
        Linked<AnnotatedMethod> linked8 = this._getters;
        if (linked8 == null || (b = aVar.b(linked8.value)) == null || b == t) {
            return null;
        }
        return b;
    }

    /* loaded from: classes.dex */
    protected static class MemberIterator<T extends AnnotatedMember> implements Iterator<T> {
        private Linked<T> a;

        public MemberIterator(Linked<T> linked) {
            this.a = linked;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.a != null;
        }

        @Override // java.util.Iterator
        public T next() {
            Linked<T> linked = this.a;
            if (linked != null) {
                T t = linked.value;
                this.a = this.a.next;
                return t;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static final class Linked<T> {
        public final boolean isMarkedIgnored;
        public final boolean isNameExplicit;
        public final boolean isVisible;
        public final PropertyName name;
        public final Linked<T> next;
        public final T value;

        public Linked(T t, Linked<T> linked, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
            this.value = t;
            this.next = linked;
            this.name = (propertyName == null || propertyName.isEmpty()) ? null : propertyName;
            if (z) {
                if (this.name == null) {
                    throw new IllegalArgumentException("Cannot pass true for 'explName' if name is null/empty");
                } else if (!propertyName.hasSimpleName()) {
                    z = false;
                }
            }
            this.isNameExplicit = z;
            this.isVisible = z2;
            this.isMarkedIgnored = z3;
        }

        public Linked<T> withoutNext() {
            return this.next == null ? this : new Linked<>(this.value, null, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withValue(T t) {
            return t == this.value ? this : new Linked<>(t, this.next, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withNext(Linked<T> linked) {
            return linked == this.next ? this : new Linked<>(this.value, linked, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withoutIgnored() {
            Linked<T> withoutIgnored;
            if (this.isMarkedIgnored) {
                Linked<T> linked = this.next;
                if (linked == null) {
                    return null;
                }
                return linked.withoutIgnored();
            }
            Linked<T> linked2 = this.next;
            return (linked2 == null || (withoutIgnored = linked2.withoutIgnored()) == this.next) ? this : withNext(withoutIgnored);
        }

        public Linked<T> withoutNonVisible() {
            Linked<T> linked = this.next;
            Linked<T> withoutNonVisible = linked == null ? null : linked.withoutNonVisible();
            return this.isVisible ? withNext(withoutNonVisible) : withoutNonVisible;
        }

        protected Linked<T> append(Linked<T> linked) {
            Linked<T> linked2 = this.next;
            if (linked2 == null) {
                return withNext(linked);
            }
            return withNext(linked2.append(linked));
        }

        public Linked<T> trimByVisibility() {
            Linked<T> linked = this.next;
            if (linked == null) {
                return this;
            }
            Linked<T> trimByVisibility = linked.trimByVisibility();
            if (this.name != null) {
                if (trimByVisibility.name == null) {
                    return withNext(null);
                }
                return withNext(trimByVisibility);
            } else if (trimByVisibility.name != null) {
                return trimByVisibility;
            } else {
                boolean z = this.isVisible;
                if (z == trimByVisibility.isVisible) {
                    return withNext(trimByVisibility);
                }
                return z ? withNext(null) : trimByVisibility;
            }
        }

        public String toString() {
            String format = String.format("%s[visible=%b,ignore=%b,explicitName=%b]", this.value.toString(), Boolean.valueOf(this.isVisible), Boolean.valueOf(this.isMarkedIgnored), Boolean.valueOf(this.isNameExplicit));
            if (this.next == null) {
                return format;
            }
            return format + ", " + this.next.toString();
        }
    }
}
