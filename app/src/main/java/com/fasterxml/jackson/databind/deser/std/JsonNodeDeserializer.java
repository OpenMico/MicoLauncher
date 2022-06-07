package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

/* loaded from: classes.dex */
public class JsonNodeDeserializer extends a<JsonNode> {
    private static final JsonNodeDeserializer a = new JsonNodeDeserializer();

    @Override // com.fasterxml.jackson.databind.deser.std.a, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public /* bridge */ /* synthetic */ Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return super.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.a, com.fasterxml.jackson.databind.JsonDeserializer
    public /* bridge */ /* synthetic */ boolean isCachable() {
        return super.isCachable();
    }

    @Override // com.fasterxml.jackson.databind.deser.std.a, com.fasterxml.jackson.databind.JsonDeserializer
    public /* bridge */ /* synthetic */ Boolean supportsUpdate(DeserializationConfig deserializationConfig) {
        return super.supportsUpdate(deserializationConfig);
    }

    protected JsonNodeDeserializer() {
        super(JsonNode.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> cls) {
        if (cls == ObjectNode.class) {
            return b.a();
        }
        if (cls == ArrayNode.class) {
            return a.a();
        }
        return a;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
    public JsonNode getNullValue(DeserializationContext deserializationContext) {
        return NullNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public JsonNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int currentTokenId = jsonParser.getCurrentTokenId();
        if (currentTokenId == 1) {
            return deserializeObject(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
        }
        if (currentTokenId != 3) {
            return deserializeAny(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
        }
        return deserializeArray(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
    }

    /* loaded from: classes.dex */
    static final class b extends a<ObjectNode> {
        protected static final b a = new b();
        private static final long serialVersionUID = 1;

        protected b() {
            super(ObjectNode.class, true);
        }

        public static b a() {
            return a;
        }

        /* renamed from: a */
        public ObjectNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.isExpectedStartObjectToken()) {
                return deserializeObject(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
            }
            if (jsonParser.hasToken(JsonToken.FIELD_NAME)) {
                return deserializeObjectAtName(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
            }
            if (jsonParser.hasToken(JsonToken.END_OBJECT)) {
                return deserializationContext.getNodeFactory().objectNode();
            }
            return (ObjectNode) deserializationContext.handleUnexpectedToken(ObjectNode.class, jsonParser);
        }

        /* renamed from: a */
        public ObjectNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, ObjectNode objectNode) throws IOException {
            if (jsonParser.isExpectedStartObjectToken() || jsonParser.hasToken(JsonToken.FIELD_NAME)) {
                return (ObjectNode) updateObject(jsonParser, deserializationContext, objectNode);
            }
            return (ObjectNode) deserializationContext.handleUnexpectedToken(ObjectNode.class, jsonParser);
        }
    }

    /* loaded from: classes.dex */
    static final class a extends a<ArrayNode> {
        protected static final a a = new a();
        private static final long serialVersionUID = 1;

        protected a() {
            super(ArrayNode.class, true);
        }

        public static a a() {
            return a;
        }

        /* renamed from: a */
        public ArrayNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.isExpectedStartArrayToken()) {
                return deserializeArray(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
            }
            return (ArrayNode) deserializationContext.handleUnexpectedToken(ArrayNode.class, jsonParser);
        }

        /* renamed from: a */
        public ArrayNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, ArrayNode arrayNode) throws IOException {
            if (jsonParser.isExpectedStartArrayToken()) {
                return (ArrayNode) updateArray(jsonParser, deserializationContext, arrayNode);
            }
            return (ArrayNode) deserializationContext.handleUnexpectedToken(ArrayNode.class, jsonParser);
        }
    }
}
