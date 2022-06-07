package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JacksonStdImpl
/* loaded from: classes.dex */
public class MapDeserializer extends ContainerDeserializerBase<Map<Object, Object>> implements ContextualDeserializer, ResolvableDeserializer {
    private static final long serialVersionUID = 1;
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected final boolean _hasDefaultCreator;
    protected Set<String> _ignorableProperties;
    protected final KeyDeserializer _keyDeserializer;
    protected PropertyBasedCreator _propertyBasedCreator;
    protected boolean _standardStringKey;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;

    public MapDeserializer(JavaType javaType, ValueInstantiator valueInstantiator, KeyDeserializer keyDeserializer, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        super(javaType, (NullValueProvider) null, (Boolean) null);
        this._keyDeserializer = keyDeserializer;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = valueInstantiator;
        this._hasDefaultCreator = valueInstantiator.canCreateUsingDefault();
        this._delegateDeserializer = null;
        this._propertyBasedCreator = null;
        this._standardStringKey = _isStdKeyDeser(javaType, keyDeserializer);
    }

    protected MapDeserializer(MapDeserializer mapDeserializer) {
        super(mapDeserializer);
        this._keyDeserializer = mapDeserializer._keyDeserializer;
        this._valueDeserializer = mapDeserializer._valueDeserializer;
        this._valueTypeDeserializer = mapDeserializer._valueTypeDeserializer;
        this._valueInstantiator = mapDeserializer._valueInstantiator;
        this._propertyBasedCreator = mapDeserializer._propertyBasedCreator;
        this._delegateDeserializer = mapDeserializer._delegateDeserializer;
        this._hasDefaultCreator = mapDeserializer._hasDefaultCreator;
        this._ignorableProperties = mapDeserializer._ignorableProperties;
        this._standardStringKey = mapDeserializer._standardStringKey;
    }

    protected MapDeserializer(MapDeserializer mapDeserializer, KeyDeserializer keyDeserializer, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, NullValueProvider nullValueProvider, Set<String> set) {
        super(mapDeserializer, nullValueProvider, mapDeserializer._unwrapSingle);
        this._keyDeserializer = keyDeserializer;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = mapDeserializer._valueInstantiator;
        this._propertyBasedCreator = mapDeserializer._propertyBasedCreator;
        this._delegateDeserializer = mapDeserializer._delegateDeserializer;
        this._hasDefaultCreator = mapDeserializer._hasDefaultCreator;
        this._ignorableProperties = set;
        this._standardStringKey = _isStdKeyDeser(this._containerType, keyDeserializer);
    }

    protected MapDeserializer withResolved(KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer, NullValueProvider nullValueProvider, Set<String> set) {
        return (this._keyDeserializer == keyDeserializer && this._valueDeserializer == jsonDeserializer && this._valueTypeDeserializer == typeDeserializer && this._nullProvider == nullValueProvider && this._ignorableProperties == set) ? this : new MapDeserializer(this, keyDeserializer, jsonDeserializer, typeDeserializer, nullValueProvider, set);
    }

    protected final boolean _isStdKeyDeser(JavaType javaType, KeyDeserializer keyDeserializer) {
        JavaType keyType;
        if (keyDeserializer == null || (keyType = javaType.getKeyType()) == null) {
            return true;
        }
        Class<?> rawClass = keyType.getRawClass();
        return (rawClass == String.class || rawClass == Object.class) && isDefaultKeyDeserializer(keyDeserializer);
    }

    public void setIgnorableProperties(String[] strArr) {
        this._ignorableProperties = (strArr == null || strArr.length == 0) ? null : ArrayBuilders.arrayToSet(strArr);
    }

    public void setIgnorableProperties(Set<String> set) {
        if (set == null || set.size() == 0) {
            set = null;
        }
        this._ignorableProperties = set;
    }

    @Override // com.fasterxml.jackson.databind.deser.ResolvableDeserializer
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            JavaType delegateType = this._valueInstantiator.getDelegateType(deserializationContext.getConfig());
            if (delegateType == null) {
                deserializationContext.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'", this._containerType, this._valueInstantiator.getClass().getName()));
            }
            this._delegateDeserializer = findDeserializer(deserializationContext, delegateType, null);
        } else if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
            JavaType arrayDelegateType = this._valueInstantiator.getArrayDelegateType(deserializationContext.getConfig());
            if (arrayDelegateType == null) {
                deserializationContext.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'", this._containerType, this._valueInstantiator.getClass().getName()));
            }
            this._delegateDeserializer = findDeserializer(deserializationContext, arrayDelegateType, null);
        }
        if (this._valueInstantiator.canCreateFromObjectWith()) {
            this._propertyBasedCreator = PropertyBasedCreator.construct(deserializationContext, this._valueInstantiator, this._valueInstantiator.getFromObjectArguments(deserializationContext.getConfig()), deserializationContext.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
        }
        this._standardStringKey = _isStdKeyDeser(this._containerType, this._keyDeserializer);
    }

    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        KeyDeserializer keyDeserializer;
        JsonDeserializer<?> jsonDeserializer;
        HashSet hashSet;
        AnnotatedMember member;
        JsonIgnoreProperties.Value findPropertyIgnorals;
        KeyDeserializer keyDeserializer2 = this._keyDeserializer;
        if (keyDeserializer2 == null) {
            keyDeserializer = deserializationContext.findKeyDeserializer(this._containerType.getKeyType(), beanProperty);
        } else {
            keyDeserializer = keyDeserializer2 instanceof ContextualKeyDeserializer ? ((ContextualKeyDeserializer) keyDeserializer2).createContextual(deserializationContext, beanProperty) : keyDeserializer2;
        }
        JsonDeserializer<?> jsonDeserializer2 = this._valueDeserializer;
        if (beanProperty != null) {
            jsonDeserializer2 = findConvertingContentDeserializer(deserializationContext, beanProperty, jsonDeserializer2);
        }
        JavaType contentType = this._containerType.getContentType();
        if (jsonDeserializer2 == null) {
            jsonDeserializer = deserializationContext.findContextualValueDeserializer(contentType, beanProperty);
        } else {
            jsonDeserializer = deserializationContext.handleSecondaryContextualization(jsonDeserializer2, beanProperty, contentType);
        }
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        TypeDeserializer forProperty = typeDeserializer != null ? typeDeserializer.forProperty(beanProperty) : typeDeserializer;
        Set<String> set = this._ignorableProperties;
        AnnotationIntrospector annotationIntrospector = deserializationContext.getAnnotationIntrospector();
        if (!(!_neitherNull(annotationIntrospector, beanProperty) || (member = beanProperty.getMember()) == null || (findPropertyIgnorals = annotationIntrospector.findPropertyIgnorals(member)) == null)) {
            Set<String> findIgnoredForDeserialization = findPropertyIgnorals.findIgnoredForDeserialization();
            if (!findIgnoredForDeserialization.isEmpty()) {
                HashSet hashSet2 = set == null ? new HashSet() : new HashSet(set);
                for (String str : findIgnoredForDeserialization) {
                    hashSet2.add(str);
                }
                hashSet = hashSet2;
                return withResolved(keyDeserializer, forProperty, jsonDeserializer, findContentNullProvider(deserializationContext, beanProperty, jsonDeserializer), hashSet);
            }
        }
        hashSet = set;
        return withResolved(keyDeserializer, forProperty, jsonDeserializer, findContentNullProvider(deserializationContext, beanProperty, jsonDeserializer), hashSet);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase, com.fasterxml.jackson.databind.deser.ValueInstantiator.Gettable
    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return this._valueDeserializer == null && this._keyDeserializer == null && this._valueTypeDeserializer == null && this._ignorableProperties == null;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Map<Object, Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._propertyBasedCreator != null) {
            return _deserializeUsingCreator(jsonParser, deserializationContext);
        }
        JsonDeserializer<Object> jsonDeserializer = this._delegateDeserializer;
        if (jsonDeserializer != null) {
            return (Map) this._valueInstantiator.createUsingDelegate(deserializationContext, jsonDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (!this._hasDefaultCreator) {
            return (Map) deserializationContext.handleMissingInstantiator(getMapClass(), getValueInstantiator(), jsonParser, "no default constructor found", new Object[0]);
        }
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.START_OBJECT || currentToken == JsonToken.FIELD_NAME || currentToken == JsonToken.END_OBJECT) {
            Map<Object, Object> map = (Map) this._valueInstantiator.createUsingDefault(deserializationContext);
            if (this._standardStringKey) {
                _readAndBindStringKeyMap(jsonParser, deserializationContext, map);
                return map;
            }
            _readAndBind(jsonParser, deserializationContext, map);
            return map;
        } else if (currentToken == JsonToken.VALUE_STRING) {
            return (Map) this._valueInstantiator.createFromString(deserializationContext, jsonParser.getText());
        } else {
            return _deserializeFromEmpty(jsonParser, deserializationContext);
        }
    }

    public Map<Object, Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException {
        jsonParser.setCurrentValue(map);
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken != JsonToken.START_OBJECT && currentToken != JsonToken.FIELD_NAME) {
            return (Map) deserializationContext.handleUnexpectedToken(getMapClass(), jsonParser);
        }
        if (this._standardStringKey) {
            _readAndUpdateStringKeyMap(jsonParser, deserializationContext, map);
            return map;
        }
        _readAndUpdate(jsonParser, deserializationContext, map);
        return map;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    public final Class<?> getMapClass() {
        return this._containerType.getRawClass();
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase, com.fasterxml.jackson.databind.deser.std.StdDeserializer
    public JavaType getValueType() {
        return this._containerType;
    }

    protected final void _readAndBind(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException {
        String str;
        Object obj;
        KeyDeserializer keyDeserializer = this._keyDeserializer;
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        boolean z = jsonDeserializer.getObjectIdReader() != null;
        b bVar = z ? new b(this._containerType.getContentType().getRawClass(), map) : null;
        if (jsonParser.isExpectedStartObjectToken()) {
            str = jsonParser.nextFieldName();
        } else {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken != JsonToken.FIELD_NAME) {
                if (currentToken != JsonToken.END_OBJECT) {
                    deserializationContext.reportWrongTokenException(this, JsonToken.FIELD_NAME, (String) null, new Object[0]);
                } else {
                    return;
                }
            }
            str = jsonParser.getCurrentName();
        }
        while (str != null) {
            Object deserializeKey = keyDeserializer.deserializeKey(str, deserializationContext);
            JsonToken nextToken = jsonParser.nextToken();
            Set<String> set = this._ignorableProperties;
            if (set == null || !set.contains(str)) {
                try {
                    if (nextToken == JsonToken.VALUE_NULL) {
                        if (!this._skipNullValues) {
                            obj = this._nullProvider.getNullValue(deserializationContext);
                        }
                    } else if (typeDeserializer == null) {
                        obj = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                    } else {
                        obj = jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
                    }
                    if (z) {
                        bVar.a(deserializeKey, obj);
                    } else {
                        map.put(deserializeKey, obj);
                    }
                } catch (UnresolvedForwardReference e) {
                    a(deserializationContext, bVar, deserializeKey, e);
                } catch (Exception e2) {
                    wrapAndThrow(e2, map, str);
                }
            } else {
                jsonParser.skipChildren();
            }
            str = jsonParser.nextFieldName();
        }
    }

    protected final void _readAndBindStringKeyMap(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException {
        String str;
        Object obj;
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        boolean z = jsonDeserializer.getObjectIdReader() != null;
        b bVar = z ? new b(this._containerType.getContentType().getRawClass(), map) : null;
        if (jsonParser.isExpectedStartObjectToken()) {
            str = jsonParser.nextFieldName();
        } else {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken != JsonToken.END_OBJECT) {
                if (currentToken != JsonToken.FIELD_NAME) {
                    deserializationContext.reportWrongTokenException(this, JsonToken.FIELD_NAME, (String) null, new Object[0]);
                }
                str = jsonParser.getCurrentName();
            } else {
                return;
            }
        }
        while (str != null) {
            JsonToken nextToken = jsonParser.nextToken();
            Set<String> set = this._ignorableProperties;
            if (set == null || !set.contains(str)) {
                try {
                    if (nextToken == JsonToken.VALUE_NULL) {
                        if (!this._skipNullValues) {
                            obj = this._nullProvider.getNullValue(deserializationContext);
                        }
                    } else if (typeDeserializer == null) {
                        obj = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                    } else {
                        obj = jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
                    }
                    if (z) {
                        bVar.a(str, obj);
                    } else {
                        map.put(str, obj);
                    }
                } catch (UnresolvedForwardReference e) {
                    a(deserializationContext, bVar, str, e);
                } catch (Exception e2) {
                    wrapAndThrow(e2, map, str);
                }
            } else {
                jsonParser.skipChildren();
            }
            str = jsonParser.nextFieldName();
        }
    }

    public Map<Object, Object> _deserializeUsingCreator(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String str;
        Object obj;
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, null);
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        if (jsonParser.isExpectedStartObjectToken()) {
            str = jsonParser.nextFieldName();
        } else {
            str = jsonParser.hasToken(JsonToken.FIELD_NAME) ? jsonParser.getCurrentName() : null;
        }
        while (str != null) {
            JsonToken nextToken = jsonParser.nextToken();
            Set<String> set = this._ignorableProperties;
            if (set == null || !set.contains(str)) {
                SettableBeanProperty findCreatorProperty = propertyBasedCreator.findCreatorProperty(str);
                if (findCreatorProperty == null) {
                    Object deserializeKey = this._keyDeserializer.deserializeKey(str, deserializationContext);
                    try {
                        if (nextToken == JsonToken.VALUE_NULL) {
                            if (!this._skipNullValues) {
                                obj = this._nullProvider.getNullValue(deserializationContext);
                            }
                        } else if (typeDeserializer == null) {
                            obj = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                        } else {
                            obj = jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
                        }
                        startBuilding.bufferMapProperty(deserializeKey, obj);
                    } catch (Exception e) {
                        wrapAndThrow(e, this._containerType.getRawClass(), str);
                        return null;
                    }
                } else if (startBuilding.assignParameter(findCreatorProperty, findCreatorProperty.deserialize(jsonParser, deserializationContext))) {
                    jsonParser.nextToken();
                    try {
                        Map<Object, Object> map = (Map) propertyBasedCreator.build(deserializationContext, startBuilding);
                        _readAndBind(jsonParser, deserializationContext, map);
                        return map;
                    } catch (Exception e2) {
                        return (Map) wrapAndThrow(e2, this._containerType.getRawClass(), str);
                    }
                }
            } else {
                jsonParser.skipChildren();
            }
            str = jsonParser.nextFieldName();
        }
        try {
            return (Map) propertyBasedCreator.build(deserializationContext, startBuilding);
        } catch (Exception e3) {
            wrapAndThrow(e3, this._containerType.getRawClass(), str);
            return null;
        }
    }

    protected final void _readAndUpdate(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException {
        String str;
        Object obj;
        KeyDeserializer keyDeserializer = this._keyDeserializer;
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        if (jsonParser.isExpectedStartObjectToken()) {
            str = jsonParser.nextFieldName();
        } else {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken != JsonToken.END_OBJECT) {
                if (currentToken != JsonToken.FIELD_NAME) {
                    deserializationContext.reportWrongTokenException(this, JsonToken.FIELD_NAME, (String) null, new Object[0]);
                }
                str = jsonParser.getCurrentName();
            } else {
                return;
            }
        }
        while (str != null) {
            Object deserializeKey = keyDeserializer.deserializeKey(str, deserializationContext);
            JsonToken nextToken = jsonParser.nextToken();
            Set<String> set = this._ignorableProperties;
            if (set == null || !set.contains(str)) {
                try {
                    if (nextToken != JsonToken.VALUE_NULL) {
                        Object obj2 = map.get(deserializeKey);
                        if (obj2 != null) {
                            obj = jsonDeserializer.deserialize(jsonParser, deserializationContext, obj2);
                        } else if (typeDeserializer == null) {
                            obj = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                        } else {
                            obj = jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
                        }
                        if (obj != obj2) {
                            map.put(deserializeKey, obj);
                        }
                    } else if (!this._skipNullValues) {
                        map.put(deserializeKey, this._nullProvider.getNullValue(deserializationContext));
                    }
                } catch (Exception e) {
                    wrapAndThrow(e, map, str);
                }
            } else {
                jsonParser.skipChildren();
            }
            str = jsonParser.nextFieldName();
        }
    }

    protected final void _readAndUpdateStringKeyMap(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException {
        String str;
        Object obj;
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        if (jsonParser.isExpectedStartObjectToken()) {
            str = jsonParser.nextFieldName();
        } else {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken != JsonToken.END_OBJECT) {
                if (currentToken != JsonToken.FIELD_NAME) {
                    deserializationContext.reportWrongTokenException(this, JsonToken.FIELD_NAME, (String) null, new Object[0]);
                }
                str = jsonParser.getCurrentName();
            } else {
                return;
            }
        }
        while (str != null) {
            JsonToken nextToken = jsonParser.nextToken();
            Set<String> set = this._ignorableProperties;
            if (set == null || !set.contains(str)) {
                try {
                    if (nextToken != JsonToken.VALUE_NULL) {
                        Object obj2 = map.get(str);
                        if (obj2 != null) {
                            obj = jsonDeserializer.deserialize(jsonParser, deserializationContext, obj2);
                        } else if (typeDeserializer == null) {
                            obj = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                        } else {
                            obj = jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
                        }
                        if (obj != obj2) {
                            map.put(str, obj);
                        }
                    } else if (!this._skipNullValues) {
                        map.put(str, this._nullProvider.getNullValue(deserializationContext));
                    }
                } catch (Exception e) {
                    wrapAndThrow(e, map, str);
                }
            } else {
                jsonParser.skipChildren();
            }
            str = jsonParser.nextFieldName();
        }
    }

    private void a(DeserializationContext deserializationContext, b bVar, Object obj, UnresolvedForwardReference unresolvedForwardReference) throws JsonMappingException {
        if (bVar == null) {
            deserializationContext.reportInputMismatch(this, "Unresolved forward reference but no identity info: " + unresolvedForwardReference, new Object[0]);
        }
        unresolvedForwardReference.getRoid().appendReferring(bVar.a(unresolvedForwardReference, obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b {
        private final Class<?> a;
        private Map<Object, Object> b;
        private List<a> c = new ArrayList();

        public b(Class<?> cls, Map<Object, Object> map) {
            this.a = cls;
            this.b = map;
        }

        public void a(Object obj, Object obj2) {
            if (this.c.isEmpty()) {
                this.b.put(obj, obj2);
                return;
            }
            List<a> list = this.c;
            list.get(list.size() - 1).a.put(obj, obj2);
        }

        public ReadableObjectId.Referring a(UnresolvedForwardReference unresolvedForwardReference, Object obj) {
            a aVar = new a(this, unresolvedForwardReference, this.a, obj);
            this.c.add(aVar);
            return aVar;
        }

        public void b(Object obj, Object obj2) throws IOException {
            Iterator<a> it = this.c.iterator();
            Map<Object, Object> map = this.b;
            while (it.hasNext()) {
                a next = it.next();
                if (next.hasId(obj)) {
                    it.remove();
                    map.put(next.b, obj2);
                    map.putAll(next.a);
                    return;
                }
                map = next.a;
            }
            throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + obj + "] that wasn't previously seen as unresolved.");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends ReadableObjectId.Referring {
        public final Map<Object, Object> a = new LinkedHashMap();
        public final Object b;
        private final b c;

        a(b bVar, UnresolvedForwardReference unresolvedForwardReference, Class<?> cls, Object obj) {
            super(unresolvedForwardReference, cls);
            this.c = bVar;
            this.b = obj;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.Referring
        public void handleResolvedForwardReference(Object obj, Object obj2) throws IOException {
            this.c.b(obj, obj2);
        }
    }
}
