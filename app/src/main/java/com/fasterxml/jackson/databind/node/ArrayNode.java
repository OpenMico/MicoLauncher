package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.RawValue;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ArrayNode extends ContainerNode<ArrayNode> {
    private final List<JsonNode> a;

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode get(String str) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public boolean isArray() {
        return true;
    }

    public ArrayNode(JsonNodeFactory jsonNodeFactory) {
        super(jsonNodeFactory);
        this.a = new ArrayList();
    }

    public ArrayNode(JsonNodeFactory jsonNodeFactory, int i) {
        super(jsonNodeFactory);
        this.a = new ArrayList(i);
    }

    public ArrayNode(JsonNodeFactory jsonNodeFactory, List<JsonNode> list) {
        super(jsonNodeFactory);
        this.a = list;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    protected JsonNode _at(JsonPointer jsonPointer) {
        return get(jsonPointer.getMatchingIndex());
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ArrayNode deepCopy() {
        ArrayNode arrayNode = new ArrayNode(this._nodeFactory);
        for (JsonNode jsonNode : this.a) {
            arrayNode.a.add(jsonNode.deepCopy());
        }
        return arrayNode;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable.Base
    public boolean isEmpty(SerializerProvider serializerProvider) {
        return this.a.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNodeType getNodeType() {
        return JsonNodeType.ARRAY;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.START_ARRAY;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public int size() {
        return this.a.size();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Iterator<JsonNode> elements() {
        return this.a.iterator();
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode get(int i) {
        if (i < 0 || i >= this.a.size()) {
            return null;
        }
        return this.a.get(i);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode path(String str) {
        return MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode path(int i) {
        if (i < 0 || i >= this.a.size()) {
            return MissingNode.getInstance();
        }
        return this.a.get(i);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Comparator<JsonNode> comparator, JsonNode jsonNode) {
        if (!(jsonNode instanceof ArrayNode)) {
            return false;
        }
        ArrayNode arrayNode = (ArrayNode) jsonNode;
        int size = this.a.size();
        if (arrayNode.size() != size) {
            return false;
        }
        List<JsonNode> list = this.a;
        List<JsonNode> list2 = arrayNode.a;
        for (int i = 0; i < size; i++) {
            if (!list.get(i).equals(comparator, list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<JsonNode> list = this.a;
        int size = list.size();
        jsonGenerator.writeStartArray(size);
        for (int i = 0; i < size; i++) {
            ((BaseJsonNode) list.get(i)).serialize(jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndArray();
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(this, JsonToken.START_ARRAY));
        Iterator<JsonNode> it = this.a.iterator();
        while (it.hasNext()) {
            ((BaseJsonNode) it.next()).serialize(jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode findValue(String str) {
        for (JsonNode jsonNode : this.a) {
            JsonNode findValue = jsonNode.findValue(str);
            if (findValue != null) {
                return findValue;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findValues(String str, List<JsonNode> list) {
        for (JsonNode jsonNode : this.a) {
            list = jsonNode.findValues(str, list);
        }
        return list;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<String> findValuesAsText(String str, List<String> list) {
        for (JsonNode jsonNode : this.a) {
            list = jsonNode.findValuesAsText(str, list);
        }
        return list;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode findParent(String str) {
        for (JsonNode jsonNode : this.a) {
            JsonNode findParent = jsonNode.findParent(str);
            if (findParent != null) {
                return (ObjectNode) findParent;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findParents(String str, List<JsonNode> list) {
        for (JsonNode jsonNode : this.a) {
            list = jsonNode.findParents(str, list);
        }
        return list;
    }

    public JsonNode set(int i, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        if (i >= 0 && i < this.a.size()) {
            return this.a.set(i, jsonNode);
        }
        throw new IndexOutOfBoundsException("Illegal index " + i + ", array size " + size());
    }

    public ArrayNode add(JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        _add(jsonNode);
        return this;
    }

    public ArrayNode addAll(ArrayNode arrayNode) {
        this.a.addAll(arrayNode.a);
        return this;
    }

    public ArrayNode addAll(Collection<? extends JsonNode> collection) {
        this.a.addAll(collection);
        return this;
    }

    public ArrayNode insert(int i, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        _insert(i, jsonNode);
        return this;
    }

    public JsonNode remove(int i) {
        if (i < 0 || i >= this.a.size()) {
            return null;
        }
        return this.a.remove(i);
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode
    public ArrayNode removeAll() {
        this.a.clear();
        return this;
    }

    public ArrayNode addArray() {
        ArrayNode arrayNode = arrayNode();
        _add(arrayNode);
        return arrayNode;
    }

    public ObjectNode addObject() {
        ObjectNode objectNode = objectNode();
        _add(objectNode);
        return objectNode;
    }

    public ArrayNode addPOJO(Object obj) {
        if (obj == null) {
            addNull();
        } else {
            _add(pojoNode(obj));
        }
        return this;
    }

    public ArrayNode addRawValue(RawValue rawValue) {
        if (rawValue == null) {
            addNull();
        } else {
            _add(rawValueNode(rawValue));
        }
        return this;
    }

    public ArrayNode addNull() {
        _add(nullNode());
        return this;
    }

    public ArrayNode add(int i) {
        _add(numberNode(i));
        return this;
    }

    public ArrayNode add(Integer num) {
        if (num == null) {
            return addNull();
        }
        return _add(numberNode(num.intValue()));
    }

    public ArrayNode add(long j) {
        return _add(numberNode(j));
    }

    public ArrayNode add(Long l) {
        if (l == null) {
            return addNull();
        }
        return _add(numberNode(l.longValue()));
    }

    public ArrayNode add(float f) {
        return _add(numberNode(f));
    }

    public ArrayNode add(Float f) {
        if (f == null) {
            return addNull();
        }
        return _add(numberNode(f.floatValue()));
    }

    public ArrayNode add(double d) {
        return _add(numberNode(d));
    }

    public ArrayNode add(Double d) {
        if (d == null) {
            return addNull();
        }
        return _add(numberNode(d.doubleValue()));
    }

    public ArrayNode add(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return addNull();
        }
        return _add(numberNode(bigDecimal));
    }

    public ArrayNode add(BigInteger bigInteger) {
        if (bigInteger == null) {
            return addNull();
        }
        return _add(numberNode(bigInteger));
    }

    public ArrayNode add(String str) {
        if (str == null) {
            return addNull();
        }
        return _add(textNode(str));
    }

    public ArrayNode add(boolean z) {
        return _add(booleanNode(z));
    }

    public ArrayNode add(Boolean bool) {
        if (bool == null) {
            return addNull();
        }
        return _add(booleanNode(bool.booleanValue()));
    }

    public ArrayNode add(byte[] bArr) {
        if (bArr == null) {
            return addNull();
        }
        return _add(binaryNode(bArr));
    }

    public ArrayNode insertArray(int i) {
        ArrayNode arrayNode = arrayNode();
        _insert(i, arrayNode);
        return arrayNode;
    }

    public ObjectNode insertObject(int i) {
        ObjectNode objectNode = objectNode();
        _insert(i, objectNode);
        return objectNode;
    }

    public ArrayNode insertPOJO(int i, Object obj) {
        if (obj == null) {
            return insertNull(i);
        }
        return _insert(i, pojoNode(obj));
    }

    public ArrayNode insertNull(int i) {
        _insert(i, nullNode());
        return this;
    }

    public ArrayNode insert(int i, int i2) {
        _insert(i, numberNode(i2));
        return this;
    }

    public ArrayNode insert(int i, Integer num) {
        if (num == null) {
            insertNull(i);
        } else {
            _insert(i, numberNode(num.intValue()));
        }
        return this;
    }

    public ArrayNode insert(int i, long j) {
        return _insert(i, numberNode(j));
    }

    public ArrayNode insert(int i, Long l) {
        if (l == null) {
            return insertNull(i);
        }
        return _insert(i, numberNode(l.longValue()));
    }

    public ArrayNode insert(int i, float f) {
        return _insert(i, numberNode(f));
    }

    public ArrayNode insert(int i, Float f) {
        if (f == null) {
            return insertNull(i);
        }
        return _insert(i, numberNode(f.floatValue()));
    }

    public ArrayNode insert(int i, double d) {
        return _insert(i, numberNode(d));
    }

    public ArrayNode insert(int i, Double d) {
        if (d == null) {
            return insertNull(i);
        }
        return _insert(i, numberNode(d.doubleValue()));
    }

    public ArrayNode insert(int i, BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return insertNull(i);
        }
        return _insert(i, numberNode(bigDecimal));
    }

    public ArrayNode insert(int i, BigInteger bigInteger) {
        if (bigInteger == null) {
            return insertNull(i);
        }
        return _insert(i, numberNode(bigInteger));
    }

    public ArrayNode insert(int i, String str) {
        if (str == null) {
            return insertNull(i);
        }
        return _insert(i, textNode(str));
    }

    public ArrayNode insert(int i, boolean z) {
        return _insert(i, booleanNode(z));
    }

    public ArrayNode insert(int i, Boolean bool) {
        if (bool == null) {
            return insertNull(i);
        }
        return _insert(i, booleanNode(bool.booleanValue()));
    }

    public ArrayNode insert(int i, byte[] bArr) {
        if (bArr == null) {
            return insertNull(i);
        }
        return _insert(i, binaryNode(bArr));
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && (obj instanceof ArrayNode)) {
            return this.a.equals(((ArrayNode) obj).a);
        }
        return false;
    }

    protected boolean _childrenEqual(ArrayNode arrayNode) {
        return this.a.equals(arrayNode.a);
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode
    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        StringBuilder sb = new StringBuilder((size() << 4) + 16);
        sb.append('[');
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(StringUtil.COMMA);
            }
            sb.append(this.a.get(i).toString());
        }
        sb.append(']');
        return sb.toString();
    }

    protected ArrayNode _add(JsonNode jsonNode) {
        this.a.add(jsonNode);
        return this;
    }

    protected ArrayNode _insert(int i, JsonNode jsonNode) {
        if (i < 0) {
            this.a.add(0, jsonNode);
        } else if (i >= this.a.size()) {
            this.a.add(jsonNode);
        } else {
            this.a.add(i, jsonNode);
        }
        return this;
    }
}
