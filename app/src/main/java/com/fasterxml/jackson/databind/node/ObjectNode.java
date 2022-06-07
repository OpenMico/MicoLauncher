package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.RawValue;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ObjectNode extends ContainerNode<ObjectNode> {
    protected final Map<String, JsonNode> _children;

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode get(int i) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public final boolean isObject() {
        return true;
    }

    public ObjectNode(JsonNodeFactory jsonNodeFactory) {
        super(jsonNodeFactory);
        this._children = new LinkedHashMap();
    }

    public ObjectNode(JsonNodeFactory jsonNodeFactory, Map<String, JsonNode> map) {
        super(jsonNodeFactory);
        this._children = map;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    protected JsonNode _at(JsonPointer jsonPointer) {
        return get(jsonPointer.getMatchingProperty());
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode deepCopy() {
        ObjectNode objectNode = new ObjectNode(this._nodeFactory);
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            objectNode._children.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return objectNode;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable.Base
    public boolean isEmpty(SerializerProvider serializerProvider) {
        return this._children.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNodeType getNodeType() {
        return JsonNodeType.OBJECT;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.START_OBJECT;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public int size() {
        return this._children.size();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Iterator<JsonNode> elements() {
        return this._children.values().iterator();
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode get(String str) {
        return this._children.get(str);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public Iterator<String> fieldNames() {
        return this._children.keySet().iterator();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode path(int i) {
        return MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode path(String str) {
        JsonNode jsonNode = this._children.get(str);
        return jsonNode != null ? jsonNode : MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Iterator<Map.Entry<String, JsonNode>> fields() {
        return this._children.entrySet().iterator();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode with(String str) {
        JsonNode jsonNode = this._children.get(str);
        if (jsonNode == null) {
            ObjectNode objectNode = objectNode();
            this._children.put(str, objectNode);
            return objectNode;
        } else if (jsonNode instanceof ObjectNode) {
            return (ObjectNode) jsonNode;
        } else {
            throw new UnsupportedOperationException("Property '" + str + "' has value that is not of type ObjectNode (but " + jsonNode.getClass().getName() + ")");
        }
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ArrayNode withArray(String str) {
        JsonNode jsonNode = this._children.get(str);
        if (jsonNode == null) {
            ArrayNode arrayNode = arrayNode();
            this._children.put(str, arrayNode);
            return arrayNode;
        } else if (jsonNode instanceof ArrayNode) {
            return (ArrayNode) jsonNode;
        } else {
            throw new UnsupportedOperationException("Property '" + str + "' has value that is not of type ArrayNode (but " + jsonNode.getClass().getName() + ")");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0025  */
    @Override // com.fasterxml.jackson.databind.JsonNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.util.Comparator<com.fasterxml.jackson.databind.JsonNode> r5, com.fasterxml.jackson.databind.JsonNode r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.fasterxml.jackson.databind.node.ObjectNode
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            com.fasterxml.jackson.databind.node.ObjectNode r6 = (com.fasterxml.jackson.databind.node.ObjectNode) r6
            java.util.Map<java.lang.String, com.fasterxml.jackson.databind.JsonNode> r0 = r4._children
            java.util.Map<java.lang.String, com.fasterxml.jackson.databind.JsonNode> r6 = r6._children
            int r2 = r0.size()
            int r3 = r6.size()
            if (r3 == r2) goto L_0x0017
            return r1
        L_0x0017:
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x001f:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x0044
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.Object r3 = r6.get(r3)
            com.fasterxml.jackson.databind.JsonNode r3 = (com.fasterxml.jackson.databind.JsonNode) r3
            if (r3 == 0) goto L_0x0043
            java.lang.Object r2 = r2.getValue()
            com.fasterxml.jackson.databind.JsonNode r2 = (com.fasterxml.jackson.databind.JsonNode) r2
            boolean r2 = r2.equals(r5, r3)
            if (r2 != 0) goto L_0x001f
        L_0x0043:
            return r1
        L_0x0044:
            r5 = 1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.node.ObjectNode.equals(java.util.Comparator, com.fasterxml.jackson.databind.JsonNode):boolean");
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode findValue(String str) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (str.equals(entry.getKey())) {
                return entry.getValue();
            }
            JsonNode findValue = entry.getValue().findValue(str);
            if (findValue != null) {
                return findValue;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findValues(String str, List<JsonNode> list) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (str.equals(entry.getKey())) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(entry.getValue());
            } else {
                list = entry.getValue().findValues(str, list);
            }
        }
        return list;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<String> findValuesAsText(String str, List<String> list) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (str.equals(entry.getKey())) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(entry.getValue().asText());
            } else {
                list = entry.getValue().findValuesAsText(str, list);
            }
        }
        return list;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode findParent(String str) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (str.equals(entry.getKey())) {
                return this;
            }
            JsonNode findParent = entry.getValue().findParent(str);
            if (findParent != null) {
                return (ObjectNode) findParent;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findParents(String str, List<JsonNode> list) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (str.equals(entry.getKey())) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(this);
            } else {
                list = entry.getValue().findParents(str, list);
            }
        }
        return list;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        boolean z = serializerProvider != null && !serializerProvider.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
        jsonGenerator.writeStartObject(this);
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            BaseJsonNode baseJsonNode = (BaseJsonNode) entry.getValue();
            if (!z || !baseJsonNode.isArray() || !baseJsonNode.isEmpty(serializerProvider)) {
                jsonGenerator.writeFieldName(entry.getKey());
                baseJsonNode.serialize(jsonGenerator, serializerProvider);
            }
        }
        jsonGenerator.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        boolean z = serializerProvider != null && !serializerProvider.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
        WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(this, JsonToken.START_OBJECT));
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            BaseJsonNode baseJsonNode = (BaseJsonNode) entry.getValue();
            if (!z || !baseJsonNode.isArray() || !baseJsonNode.isEmpty(serializerProvider)) {
                jsonGenerator.writeFieldName(entry.getKey());
                baseJsonNode.serialize(jsonGenerator, serializerProvider);
            }
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }

    public JsonNode set(String str, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        this._children.put(str, jsonNode);
        return this;
    }

    public JsonNode setAll(Map<String, ? extends JsonNode> map) {
        for (Map.Entry<String, ? extends JsonNode> entry : map.entrySet()) {
            JsonNode jsonNode = (JsonNode) entry.getValue();
            if (jsonNode == null) {
                jsonNode = nullNode();
            }
            this._children.put(entry.getKey(), jsonNode);
        }
        return this;
    }

    public JsonNode setAll(ObjectNode objectNode) {
        this._children.putAll(objectNode._children);
        return this;
    }

    public JsonNode replace(String str, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        return this._children.put(str, jsonNode);
    }

    public JsonNode without(String str) {
        this._children.remove(str);
        return this;
    }

    public ObjectNode without(Collection<String> collection) {
        this._children.keySet().removeAll(collection);
        return this;
    }

    @Deprecated
    public JsonNode put(String str, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        return this._children.put(str, jsonNode);
    }

    public JsonNode remove(String str) {
        return this._children.remove(str);
    }

    public ObjectNode remove(Collection<String> collection) {
        this._children.keySet().removeAll(collection);
        return this;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode
    public ObjectNode removeAll() {
        this._children.clear();
        return this;
    }

    @Deprecated
    public JsonNode putAll(Map<String, ? extends JsonNode> map) {
        return setAll(map);
    }

    @Deprecated
    public JsonNode putAll(ObjectNode objectNode) {
        return setAll(objectNode);
    }

    public ObjectNode retain(Collection<String> collection) {
        this._children.keySet().retainAll(collection);
        return this;
    }

    public ObjectNode retain(String... strArr) {
        return retain(Arrays.asList(strArr));
    }

    public ArrayNode putArray(String str) {
        ArrayNode arrayNode = arrayNode();
        _put(str, arrayNode);
        return arrayNode;
    }

    public ObjectNode putObject(String str) {
        ObjectNode objectNode = objectNode();
        _put(str, objectNode);
        return objectNode;
    }

    public ObjectNode putPOJO(String str, Object obj) {
        return _put(str, pojoNode(obj));
    }

    public ObjectNode putRawValue(String str, RawValue rawValue) {
        return _put(str, rawValueNode(rawValue));
    }

    public ObjectNode putNull(String str) {
        this._children.put(str, nullNode());
        return this;
    }

    public ObjectNode put(String str, short s) {
        return _put(str, numberNode(s));
    }

    public ObjectNode put(String str, Short sh) {
        JsonNode jsonNode;
        if (sh == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = numberNode(sh.shortValue());
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, int i) {
        return _put(str, numberNode(i));
    }

    public ObjectNode put(String str, Integer num) {
        JsonNode jsonNode;
        if (num == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = numberNode(num.intValue());
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, long j) {
        return _put(str, numberNode(j));
    }

    public ObjectNode put(String str, Long l) {
        JsonNode jsonNode;
        if (l == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = numberNode(l.longValue());
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, float f) {
        return _put(str, numberNode(f));
    }

    public ObjectNode put(String str, Float f) {
        JsonNode jsonNode;
        if (f == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = numberNode(f.floatValue());
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, double d) {
        return _put(str, numberNode(d));
    }

    public ObjectNode put(String str, Double d) {
        JsonNode jsonNode;
        if (d == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = numberNode(d.doubleValue());
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, BigDecimal bigDecimal) {
        JsonNode jsonNode;
        if (bigDecimal == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = numberNode(bigDecimal);
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, BigInteger bigInteger) {
        JsonNode jsonNode;
        if (bigInteger == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = numberNode(bigInteger);
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, String str2) {
        JsonNode jsonNode;
        if (str2 == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = textNode(str2);
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, boolean z) {
        return _put(str, booleanNode(z));
    }

    public ObjectNode put(String str, Boolean bool) {
        JsonNode jsonNode;
        if (bool == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = booleanNode(bool.booleanValue());
        }
        return _put(str, jsonNode);
    }

    public ObjectNode put(String str, byte[] bArr) {
        JsonNode jsonNode;
        if (bArr == null) {
            jsonNode = nullNode();
        } else {
            jsonNode = binaryNode(bArr);
        }
        return _put(str, jsonNode);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && (obj instanceof ObjectNode)) {
            return _childrenEqual((ObjectNode) obj);
        }
        return false;
    }

    protected boolean _childrenEqual(ObjectNode objectNode) {
        return this._children.equals(objectNode._children);
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode
    public int hashCode() {
        return this._children.hashCode();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        StringBuilder sb = new StringBuilder((size() << 4) + 32);
        sb.append("{");
        int i = 0;
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (i > 0) {
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
            i++;
            TextNode.appendQuoted(sb, entry.getKey());
            sb.append(':');
            sb.append(entry.getValue().toString());
        }
        sb.append("}");
        return sb.toString();
    }

    protected ObjectNode _put(String str, JsonNode jsonNode) {
        this._children.put(str, jsonNode);
        return this;
    }
}
