package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/* loaded from: classes.dex */
public final class NullNode extends ValueNode {
    public static final NullNode instance = new NullNode();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String asText() {
        return "null";
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String asText(String str) {
        return str;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object obj) {
        return obj == this;
    }

    protected NullNode() {
    }

    public static NullNode getInstance() {
        return instance;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNodeType getNodeType() {
        return JsonNodeType.NULL;
    }

    @Override // com.fasterxml.jackson.databind.node.ValueNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.VALUE_NULL;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        serializerProvider.defaultSerializeNull(jsonGenerator);
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode
    public int hashCode() {
        return JsonNodeType.NULL.ordinal();
    }
}
