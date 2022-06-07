package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import java.io.IOException;

/* compiled from: JsonNodeDeserializer.java */
/* loaded from: classes.dex */
abstract class a<T extends JsonNode> extends StdDeserializer<T> {
    protected final Boolean _supportsUpdates;

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return true;
    }

    public a(Class<T> cls, Boolean bool) {
        super((Class<?>) cls);
        this._supportsUpdates = bool;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Boolean supportsUpdate(DeserializationConfig deserializationConfig) {
        return this._supportsUpdates;
    }

    protected void _handleDuplicateField(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory, String str, ObjectNode objectNode, JsonNode jsonNode, JsonNode jsonNode2) throws JsonProcessingException {
        if (deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)) {
            deserializationContext.reportInputMismatch(JsonNode.class, "Duplicate field '%s' for ObjectNode: not allowed when FAIL_ON_READING_DUP_TREE_KEY enabled", str);
        }
    }

    protected final ObjectNode deserializeObject(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        JsonNode jsonNode;
        ObjectNode objectNode = jsonNodeFactory.objectNode();
        String nextFieldName = jsonParser.nextFieldName();
        while (nextFieldName != null) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken == null) {
                nextToken = JsonToken.NOT_AVAILABLE;
            }
            switch (nextToken.id()) {
                case 1:
                    jsonNode = deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 2:
                case 4:
                case 5:
                case 8:
                default:
                    jsonNode = deserializeAny(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 3:
                    jsonNode = deserializeArray(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 6:
                    jsonNode = jsonNodeFactory.textNode(jsonParser.getText());
                    break;
                case 7:
                    jsonNode = _fromInt(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 9:
                    jsonNode = jsonNodeFactory.booleanNode(true);
                    break;
                case 10:
                    jsonNode = jsonNodeFactory.booleanNode(false);
                    break;
                case 11:
                    jsonNode = jsonNodeFactory.nullNode();
                    break;
                case 12:
                    jsonNode = _fromEmbedded(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
            }
            JsonNode replace = objectNode.replace(nextFieldName, jsonNode);
            if (replace != null) {
                _handleDuplicateField(jsonParser, deserializationContext, jsonNodeFactory, nextFieldName, objectNode, replace, jsonNode);
            }
            nextFieldName = jsonParser.nextFieldName();
        }
        return objectNode;
    }

    protected final ObjectNode deserializeObjectAtName(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        JsonNode jsonNode;
        ObjectNode objectNode = jsonNodeFactory.objectNode();
        String currentName = jsonParser.getCurrentName();
        while (currentName != null) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken == null) {
                nextToken = JsonToken.NOT_AVAILABLE;
            }
            switch (nextToken.id()) {
                case 1:
                    jsonNode = deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 2:
                case 4:
                case 5:
                case 8:
                default:
                    jsonNode = deserializeAny(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 3:
                    jsonNode = deserializeArray(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 6:
                    jsonNode = jsonNodeFactory.textNode(jsonParser.getText());
                    break;
                case 7:
                    jsonNode = _fromInt(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                case 9:
                    jsonNode = jsonNodeFactory.booleanNode(true);
                    break;
                case 10:
                    jsonNode = jsonNodeFactory.booleanNode(false);
                    break;
                case 11:
                    jsonNode = jsonNodeFactory.nullNode();
                    break;
                case 12:
                    jsonNode = _fromEmbedded(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
            }
            JsonNode replace = objectNode.replace(currentName, jsonNode);
            if (replace != null) {
                _handleDuplicateField(jsonParser, deserializationContext, jsonNodeFactory, currentName, objectNode, replace, jsonNode);
            }
            currentName = jsonParser.nextFieldName();
        }
        return objectNode;
    }

    protected final JsonNode updateObject(JsonParser jsonParser, DeserializationContext deserializationContext, ObjectNode objectNode) throws IOException {
        String str;
        JsonNode jsonNode;
        if (jsonParser.isExpectedStartObjectToken()) {
            str = jsonParser.nextFieldName();
        } else if (!jsonParser.hasToken(JsonToken.FIELD_NAME)) {
            return (JsonNode) deserialize(jsonParser, deserializationContext);
        } else {
            str = jsonParser.getCurrentName();
        }
        while (str != null) {
            JsonToken nextToken = jsonParser.nextToken();
            JsonNode jsonNode2 = objectNode.get(str);
            if (jsonNode2 != null) {
                if (jsonNode2 instanceof ObjectNode) {
                    JsonNode updateObject = updateObject(jsonParser, deserializationContext, (ObjectNode) jsonNode2);
                    if (updateObject != jsonNode2) {
                        objectNode.set(str, updateObject);
                    }
                } else if (jsonNode2 instanceof ArrayNode) {
                    JsonNode updateArray = updateArray(jsonParser, deserializationContext, (ArrayNode) jsonNode2);
                    if (updateArray != jsonNode2) {
                        objectNode.set(str, updateArray);
                    }
                }
                str = jsonParser.nextFieldName();
            }
            if (nextToken == null) {
                nextToken = JsonToken.NOT_AVAILABLE;
            }
            JsonNodeFactory nodeFactory = deserializationContext.getNodeFactory();
            switch (nextToken.id()) {
                case 1:
                    jsonNode = deserializeObject(jsonParser, deserializationContext, nodeFactory);
                    break;
                case 2:
                case 4:
                case 5:
                case 8:
                default:
                    jsonNode = deserializeAny(jsonParser, deserializationContext, nodeFactory);
                    break;
                case 3:
                    jsonNode = deserializeArray(jsonParser, deserializationContext, nodeFactory);
                    break;
                case 6:
                    jsonNode = nodeFactory.textNode(jsonParser.getText());
                    break;
                case 7:
                    jsonNode = _fromInt(jsonParser, deserializationContext, nodeFactory);
                    break;
                case 9:
                    jsonNode = nodeFactory.booleanNode(true);
                    break;
                case 10:
                    jsonNode = nodeFactory.booleanNode(false);
                    break;
                case 11:
                    jsonNode = nodeFactory.nullNode();
                    break;
                case 12:
                    jsonNode = _fromEmbedded(jsonParser, deserializationContext, nodeFactory);
                    break;
            }
            if (jsonNode2 != null) {
                _handleDuplicateField(jsonParser, deserializationContext, nodeFactory, str, objectNode, jsonNode2, jsonNode);
            }
            objectNode.set(str, jsonNode);
            str = jsonParser.nextFieldName();
        }
        return objectNode;
    }

    protected final ArrayNode deserializeArray(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        ArrayNode arrayNode = jsonNodeFactory.arrayNode();
        while (true) {
            switch (jsonParser.nextToken().id()) {
                case 1:
                    arrayNode.add(deserializeObject(jsonParser, deserializationContext, jsonNodeFactory));
                    break;
                case 2:
                case 5:
                case 8:
                default:
                    arrayNode.add(deserializeAny(jsonParser, deserializationContext, jsonNodeFactory));
                    break;
                case 3:
                    arrayNode.add(deserializeArray(jsonParser, deserializationContext, jsonNodeFactory));
                    break;
                case 4:
                    return arrayNode;
                case 6:
                    arrayNode.add(jsonNodeFactory.textNode(jsonParser.getText()));
                    break;
                case 7:
                    arrayNode.add(_fromInt(jsonParser, deserializationContext, jsonNodeFactory));
                    break;
                case 9:
                    arrayNode.add(jsonNodeFactory.booleanNode(true));
                    break;
                case 10:
                    arrayNode.add(jsonNodeFactory.booleanNode(false));
                    break;
                case 11:
                    arrayNode.add(jsonNodeFactory.nullNode());
                    break;
                case 12:
                    arrayNode.add(_fromEmbedded(jsonParser, deserializationContext, jsonNodeFactory));
                    break;
            }
        }
    }

    protected final JsonNode updateArray(JsonParser jsonParser, DeserializationContext deserializationContext, ArrayNode arrayNode) throws IOException {
        JsonNodeFactory nodeFactory = deserializationContext.getNodeFactory();
        while (true) {
            switch (jsonParser.nextToken().id()) {
                case 1:
                    arrayNode.add(deserializeObject(jsonParser, deserializationContext, nodeFactory));
                    break;
                case 2:
                case 5:
                case 8:
                default:
                    arrayNode.add(deserializeAny(jsonParser, deserializationContext, nodeFactory));
                    break;
                case 3:
                    arrayNode.add(deserializeArray(jsonParser, deserializationContext, nodeFactory));
                    break;
                case 4:
                    return arrayNode;
                case 6:
                    arrayNode.add(nodeFactory.textNode(jsonParser.getText()));
                    break;
                case 7:
                    arrayNode.add(_fromInt(jsonParser, deserializationContext, nodeFactory));
                    break;
                case 9:
                    arrayNode.add(nodeFactory.booleanNode(true));
                    break;
                case 10:
                    arrayNode.add(nodeFactory.booleanNode(false));
                    break;
                case 11:
                    arrayNode.add(nodeFactory.nullNode());
                    break;
                case 12:
                    arrayNode.add(_fromEmbedded(jsonParser, deserializationContext, nodeFactory));
                    break;
            }
        }
    }

    protected final JsonNode deserializeAny(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        int currentTokenId = jsonParser.getCurrentTokenId();
        if (currentTokenId == 2) {
            return jsonNodeFactory.objectNode();
        }
        switch (currentTokenId) {
            case 5:
                return deserializeObjectAtName(jsonParser, deserializationContext, jsonNodeFactory);
            case 6:
                return jsonNodeFactory.textNode(jsonParser.getText());
            case 7:
                return _fromInt(jsonParser, deserializationContext, jsonNodeFactory);
            case 8:
                return _fromFloat(jsonParser, deserializationContext, jsonNodeFactory);
            case 9:
                return jsonNodeFactory.booleanNode(true);
            case 10:
                return jsonNodeFactory.booleanNode(false);
            case 11:
                return jsonNodeFactory.nullNode();
            case 12:
                return _fromEmbedded(jsonParser, deserializationContext, jsonNodeFactory);
            default:
                return (JsonNode) deserializationContext.handleUnexpectedToken(handledType(), jsonParser);
        }
    }

    protected final JsonNode _fromInt(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        JsonParser.NumberType numberType;
        int deserializationFeatures = deserializationContext.getDeserializationFeatures();
        if ((F_MASK_INT_COERCIONS & deserializationFeatures) == 0) {
            numberType = jsonParser.getNumberType();
        } else if (DeserializationFeature.USE_BIG_INTEGER_FOR_INTS.enabledIn(deserializationFeatures)) {
            numberType = JsonParser.NumberType.BIG_INTEGER;
        } else if (DeserializationFeature.USE_LONG_FOR_INTS.enabledIn(deserializationFeatures)) {
            numberType = JsonParser.NumberType.LONG;
        } else {
            numberType = jsonParser.getNumberType();
        }
        if (numberType == JsonParser.NumberType.INT) {
            return jsonNodeFactory.numberNode(jsonParser.getIntValue());
        }
        if (numberType == JsonParser.NumberType.LONG) {
            return jsonNodeFactory.numberNode(jsonParser.getLongValue());
        }
        return jsonNodeFactory.numberNode(jsonParser.getBigIntegerValue());
    }

    protected final JsonNode _fromFloat(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        JsonParser.NumberType numberType = jsonParser.getNumberType();
        if (numberType == JsonParser.NumberType.BIG_DECIMAL) {
            return jsonNodeFactory.numberNode(jsonParser.getDecimalValue());
        }
        if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
            if (jsonParser.isNaN()) {
                return jsonNodeFactory.numberNode(jsonParser.getDoubleValue());
            }
            return jsonNodeFactory.numberNode(jsonParser.getDecimalValue());
        } else if (numberType == JsonParser.NumberType.FLOAT) {
            return jsonNodeFactory.numberNode(jsonParser.getFloatValue());
        } else {
            return jsonNodeFactory.numberNode(jsonParser.getDoubleValue());
        }
    }

    protected final JsonNode _fromEmbedded(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException {
        Object embeddedObject = jsonParser.getEmbeddedObject();
        if (embeddedObject == null) {
            return jsonNodeFactory.nullNode();
        }
        if (embeddedObject.getClass() == byte[].class) {
            return jsonNodeFactory.binaryNode((byte[]) embeddedObject);
        }
        if (embeddedObject instanceof RawValue) {
            return jsonNodeFactory.rawValueNode((RawValue) embeddedObject);
        }
        if (embeddedObject instanceof JsonNode) {
            return (JsonNode) embeddedObject;
        }
        return jsonNodeFactory.pojoNode(embeddedObject);
    }
}
