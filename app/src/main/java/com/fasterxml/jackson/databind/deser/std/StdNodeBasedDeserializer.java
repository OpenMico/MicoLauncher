package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class StdNodeBasedDeserializer<T> extends StdDeserializer<T> implements ResolvableDeserializer {
    private static final long serialVersionUID = 1;
    protected JsonDeserializer<Object> _treeDeserializer;

    public abstract T convert(JsonNode jsonNode, DeserializationContext deserializationContext) throws IOException;

    protected StdNodeBasedDeserializer(JavaType javaType) {
        super(javaType);
    }

    protected StdNodeBasedDeserializer(Class<T> cls) {
        super((Class<?>) cls);
    }

    protected StdNodeBasedDeserializer(StdNodeBasedDeserializer<?> stdNodeBasedDeserializer) {
        super(stdNodeBasedDeserializer);
        this._treeDeserializer = stdNodeBasedDeserializer._treeDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.deser.ResolvableDeserializer
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        this._treeDeserializer = deserializationContext.findRootValueDeserializer(deserializationContext.constructType(JsonNode.class));
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return convert((JsonNode) this._treeDeserializer.deserialize(jsonParser, deserializationContext), deserializationContext);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return convert((JsonNode) this._treeDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer), deserializationContext);
    }
}
