package com.fasterxml.jackson.databind.jsonschema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.language.bm.Languages;

@Deprecated
/* loaded from: classes.dex */
public class JsonSchema {
    private final ObjectNode a;

    @JsonCreator
    public JsonSchema(ObjectNode objectNode) {
        this.a = objectNode;
    }

    @JsonValue
    public ObjectNode getSchemaNode() {
        return this.a;
    }

    public String toString() {
        return this.a.toString();
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof JsonSchema)) {
            return false;
        }
        JsonSchema jsonSchema = (JsonSchema) obj;
        ObjectNode objectNode = this.a;
        if (objectNode == null) {
            return jsonSchema.a == null;
        }
        return objectNode.equals(jsonSchema.a);
    }

    public static JsonNode getDefaultSchemaNode() {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("type", Languages.ANY);
        return objectNode;
    }
}
