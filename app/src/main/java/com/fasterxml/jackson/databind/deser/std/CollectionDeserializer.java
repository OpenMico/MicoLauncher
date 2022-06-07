package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@JacksonStdImpl
/* loaded from: classes.dex */
public class CollectionDeserializer extends ContainerDeserializerBase<Collection<Object>> implements ContextualDeserializer {
    private static final long serialVersionUID = -1;
    protected final JsonDeserializer<Object> _delegateDeserializer;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;

    public CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator) {
        this(javaType, jsonDeserializer, typeDeserializer, valueInstantiator, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator, JsonDeserializer<Object> jsonDeserializer2, NullValueProvider nullValueProvider, Boolean bool) {
        super(javaType, nullValueProvider, bool);
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = valueInstantiator;
        this._delegateDeserializer = jsonDeserializer2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CollectionDeserializer(CollectionDeserializer collectionDeserializer) {
        super(collectionDeserializer);
        this._valueDeserializer = collectionDeserializer._valueDeserializer;
        this._valueTypeDeserializer = collectionDeserializer._valueTypeDeserializer;
        this._valueInstantiator = collectionDeserializer._valueInstantiator;
        this._delegateDeserializer = collectionDeserializer._delegateDeserializer;
    }

    protected CollectionDeserializer withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, TypeDeserializer typeDeserializer, NullValueProvider nullValueProvider, Boolean bool) {
        return new CollectionDeserializer(this._containerType, jsonDeserializer2, typeDeserializer, this._valueInstantiator, jsonDeserializer, nullValueProvider, bool);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return this._valueDeserializer == null && this._valueTypeDeserializer == null && this._delegateDeserializer == null;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a0  */
    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.fasterxml.jackson.databind.deser.std.CollectionDeserializer createContextual(com.fasterxml.jackson.databind.DeserializationContext r8, com.fasterxml.jackson.databind.BeanProperty r9) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            r7 = this;
            com.fasterxml.jackson.databind.deser.ValueInstantiator r0 = r7._valueInstantiator
            if (r0 == 0) goto L_0x0073
            boolean r0 = r0.canCreateUsingDelegate()
            r1 = 1
            r2 = 0
            r3 = 2
            if (r0 == 0) goto L_0x003c
            com.fasterxml.jackson.databind.deser.ValueInstantiator r0 = r7._valueInstantiator
            com.fasterxml.jackson.databind.DeserializationConfig r4 = r8.getConfig()
            com.fasterxml.jackson.databind.JavaType r0 = r0.getDelegateType(r4)
            if (r0 != 0) goto L_0x0036
            com.fasterxml.jackson.databind.JavaType r4 = r7._containerType
            java.lang.String r5 = "Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.fasterxml.jackson.databind.JavaType r6 = r7._containerType
            r3[r2] = r6
            com.fasterxml.jackson.databind.deser.ValueInstantiator r2 = r7._valueInstantiator
            java.lang.Class r2 = r2.getClass()
            java.lang.String r2 = r2.getName()
            r3[r1] = r2
            java.lang.String r1 = java.lang.String.format(r5, r3)
            r8.reportBadDefinition(r4, r1)
        L_0x0036:
            com.fasterxml.jackson.databind.JsonDeserializer r0 = r7.findDeserializer(r8, r0, r9)
            r2 = r0
            goto L_0x0075
        L_0x003c:
            com.fasterxml.jackson.databind.deser.ValueInstantiator r0 = r7._valueInstantiator
            boolean r0 = r0.canCreateUsingArrayDelegate()
            if (r0 == 0) goto L_0x0073
            com.fasterxml.jackson.databind.deser.ValueInstantiator r0 = r7._valueInstantiator
            com.fasterxml.jackson.databind.DeserializationConfig r4 = r8.getConfig()
            com.fasterxml.jackson.databind.JavaType r0 = r0.getArrayDelegateType(r4)
            if (r0 != 0) goto L_0x006d
            com.fasterxml.jackson.databind.JavaType r4 = r7._containerType
            java.lang.String r5 = "Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.fasterxml.jackson.databind.JavaType r6 = r7._containerType
            r3[r2] = r6
            com.fasterxml.jackson.databind.deser.ValueInstantiator r2 = r7._valueInstantiator
            java.lang.Class r2 = r2.getClass()
            java.lang.String r2 = r2.getName()
            r3[r1] = r2
            java.lang.String r1 = java.lang.String.format(r5, r3)
            r8.reportBadDefinition(r4, r1)
        L_0x006d:
            com.fasterxml.jackson.databind.JsonDeserializer r0 = r7.findDeserializer(r8, r0, r9)
            r2 = r0
            goto L_0x0075
        L_0x0073:
            r0 = 0
            r2 = r0
        L_0x0075:
            java.lang.Class<java.util.Collection> r0 = java.util.Collection.class
            com.fasterxml.jackson.annotation.JsonFormat$Feature r1 = com.fasterxml.jackson.annotation.JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
            java.lang.Boolean r6 = r7.findFormatFeature(r8, r9, r0, r1)
            com.fasterxml.jackson.databind.JsonDeserializer<java.lang.Object> r0 = r7._valueDeserializer
            com.fasterxml.jackson.databind.JsonDeserializer r0 = r7.findConvertingContentDeserializer(r8, r9, r0)
            com.fasterxml.jackson.databind.JavaType r1 = r7._containerType
            com.fasterxml.jackson.databind.JavaType r1 = r1.getContentType()
            if (r0 != 0) goto L_0x0091
            com.fasterxml.jackson.databind.JsonDeserializer r0 = r8.findContextualValueDeserializer(r1, r9)
            r3 = r0
            goto L_0x0096
        L_0x0091:
            com.fasterxml.jackson.databind.JsonDeserializer r0 = r8.handleSecondaryContextualization(r0, r9, r1)
            r3 = r0
        L_0x0096:
            com.fasterxml.jackson.databind.jsontype.TypeDeserializer r0 = r7._valueTypeDeserializer
            if (r0 == 0) goto L_0x00a0
            com.fasterxml.jackson.databind.jsontype.TypeDeserializer r0 = r0.forProperty(r9)
            r4 = r0
            goto L_0x00a1
        L_0x00a0:
            r4 = r0
        L_0x00a1:
            com.fasterxml.jackson.databind.deser.NullValueProvider r5 = r7.findContentNullProvider(r8, r9, r3)
            java.lang.Boolean r8 = r7._unwrapSingle
            if (r6 != r8) goto L_0x00bb
            com.fasterxml.jackson.databind.deser.NullValueProvider r8 = r7._nullProvider
            if (r5 != r8) goto L_0x00bb
            com.fasterxml.jackson.databind.JsonDeserializer<java.lang.Object> r8 = r7._delegateDeserializer
            if (r2 != r8) goto L_0x00bb
            com.fasterxml.jackson.databind.JsonDeserializer<java.lang.Object> r8 = r7._valueDeserializer
            if (r3 != r8) goto L_0x00bb
            com.fasterxml.jackson.databind.jsontype.TypeDeserializer r8 = r7._valueTypeDeserializer
            if (r4 == r8) goto L_0x00ba
            goto L_0x00bb
        L_0x00ba:
            return r7
        L_0x00bb:
            r1 = r7
            com.fasterxml.jackson.databind.deser.std.CollectionDeserializer r8 = r1.withResolved(r2, r3, r4, r5, r6)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.createContextual(com.fasterxml.jackson.databind.DeserializationContext, com.fasterxml.jackson.databind.BeanProperty):com.fasterxml.jackson.databind.deser.std.CollectionDeserializer");
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
    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonDeserializer<Object> jsonDeserializer = this._delegateDeserializer;
        if (jsonDeserializer != null) {
            return (Collection) this._valueInstantiator.createUsingDelegate(deserializationContext, jsonDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            String text = jsonParser.getText();
            if (text.length() == 0) {
                return (Collection) this._valueInstantiator.createFromString(deserializationContext, text);
            }
        }
        return deserialize(jsonParser, deserializationContext, createDefaultInstance(deserializationContext));
    }

    protected Collection<Object> createDefaultInstance(DeserializationContext deserializationContext) throws IOException {
        return (Collection) this._valueInstantiator.createUsingDefault(deserializationContext);
    }

    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException {
        Object deserializeWithType;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return handleNonArray(jsonParser, deserializationContext, collection);
        }
        jsonParser.setCurrentValue(collection);
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        if (jsonDeserializer.getObjectIdReader() != null) {
            return _deserializeWithObjectId(jsonParser, deserializationContext, collection);
        }
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        while (true) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken == JsonToken.END_ARRAY) {
                return collection;
            }
            try {
                if (nextToken == JsonToken.VALUE_NULL) {
                    if (!this._skipNullValues) {
                        deserializeWithType = this._nullProvider.getNullValue(deserializationContext);
                    }
                } else if (typeDeserializer == null) {
                    deserializeWithType = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                } else {
                    deserializeWithType = jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
                }
                collection.add(deserializeWithType);
            } catch (Exception e) {
                if (!(deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS))) {
                    ClassUtil.throwIfRTE(e);
                }
                throw JsonMappingException.wrapWithPath(e, collection, collection.size());
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    protected final Collection<Object> handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException {
        Object obj;
        if (!(this._unwrapSingle == Boolean.TRUE || (this._unwrapSingle == null && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)))) {
            return (Collection) deserializationContext.handleUnexpectedToken(this._containerType.getRawClass(), jsonParser);
        }
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        try {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
                if (this._skipNullValues) {
                    return collection;
                }
                obj = this._nullProvider.getNullValue(deserializationContext);
            } else if (typeDeserializer == null) {
                obj = jsonDeserializer.deserialize(jsonParser, deserializationContext);
            } else {
                obj = jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
            }
            collection.add(obj);
            return collection;
        } catch (Exception e) {
            throw JsonMappingException.wrapWithPath(e, Object.class, collection.size());
        }
    }

    protected Collection<Object> _deserializeWithObjectId(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException {
        Object deserializeWithType;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return handleNonArray(jsonParser, deserializationContext, collection);
        }
        jsonParser.setCurrentValue(collection);
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        CollectionReferringAccumulator collectionReferringAccumulator = new CollectionReferringAccumulator(this._containerType.getContentType().getRawClass(), collection);
        while (true) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken == JsonToken.END_ARRAY) {
                return collection;
            }
            try {
            } catch (UnresolvedForwardReference e) {
                e.getRoid().appendReferring(collectionReferringAccumulator.handleUnresolvedReference(e));
            } catch (Exception e2) {
                if (!(deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS))) {
                    ClassUtil.throwIfRTE(e2);
                }
                throw JsonMappingException.wrapWithPath(e2, collection, collection.size());
            }
            if (nextToken == JsonToken.VALUE_NULL) {
                if (!this._skipNullValues) {
                    deserializeWithType = this._nullProvider.getNullValue(deserializationContext);
                }
            } else if (typeDeserializer == null) {
                deserializeWithType = jsonDeserializer.deserialize(jsonParser, deserializationContext);
            } else {
                deserializeWithType = jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
            }
            collectionReferringAccumulator.add(deserializeWithType);
        }
    }

    /* loaded from: classes.dex */
    public static class CollectionReferringAccumulator {
        private final Class<?> a;
        private final Collection<Object> b;
        private List<a> c = new ArrayList();

        public CollectionReferringAccumulator(Class<?> cls, Collection<Object> collection) {
            this.a = cls;
            this.b = collection;
        }

        public void add(Object obj) {
            if (this.c.isEmpty()) {
                this.b.add(obj);
                return;
            }
            List<a> list = this.c;
            list.get(list.size() - 1).a.add(obj);
        }

        public ReadableObjectId.Referring handleUnresolvedReference(UnresolvedForwardReference unresolvedForwardReference) {
            a aVar = new a(this, unresolvedForwardReference, this.a);
            this.c.add(aVar);
            return aVar;
        }

        public void resolveForwardReference(Object obj, Object obj2) throws IOException {
            Iterator<a> it = this.c.iterator();
            Collection collection = this.b;
            while (it.hasNext()) {
                a next = it.next();
                if (next.hasId(obj)) {
                    it.remove();
                    collection.add(obj2);
                    collection.addAll(next.a);
                    return;
                }
                collection = next.a;
            }
            throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + obj + "] that wasn't previously seen as unresolved.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a extends ReadableObjectId.Referring {
        public final List<Object> a = new ArrayList();
        private final CollectionReferringAccumulator b;

        a(CollectionReferringAccumulator collectionReferringAccumulator, UnresolvedForwardReference unresolvedForwardReference, Class<?> cls) {
            super(unresolvedForwardReference, cls);
            this.b = collectionReferringAccumulator;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.Referring
        public void handleResolvedForwardReference(Object obj, Object obj2) throws IOException {
            this.b.resolveForwardReference(obj, obj2);
        }
    }
}
