package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.databind.util.RawValue;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class JsonNodeFactory implements JsonNodeCreator, Serializable {
    private static final JsonNodeFactory a = new JsonNodeFactory(false);
    private static final JsonNodeFactory b = new JsonNodeFactory(true);
    public static final JsonNodeFactory instance = a;
    private static final long serialVersionUID = 1;
    private final boolean _cfgBigDecimalExact;

    protected boolean _inIntRange(long j) {
        return ((long) ((int) j)) == j;
    }

    public JsonNodeFactory(boolean z) {
        this._cfgBigDecimalExact = z;
    }

    protected JsonNodeFactory() {
        this(false);
    }

    public static JsonNodeFactory withExactBigDecimals(boolean z) {
        return z ? b : a;
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public BooleanNode booleanNode(boolean z) {
        return z ? BooleanNode.getTrue() : BooleanNode.getFalse();
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public NullNode nullNode() {
        return NullNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public NumericNode numberNode(byte b2) {
        return IntNode.valueOf(b2);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode numberNode(Byte b2) {
        return b2 == null ? nullNode() : IntNode.valueOf(b2.intValue());
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public NumericNode numberNode(short s) {
        return ShortNode.valueOf(s);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode numberNode(Short sh) {
        return sh == null ? nullNode() : ShortNode.valueOf(sh.shortValue());
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public NumericNode numberNode(int i) {
        return IntNode.valueOf(i);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode numberNode(Integer num) {
        return num == null ? nullNode() : IntNode.valueOf(num.intValue());
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public NumericNode numberNode(long j) {
        return LongNode.valueOf(j);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode numberNode(Long l) {
        if (l == null) {
            return nullNode();
        }
        return LongNode.valueOf(l.longValue());
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode numberNode(BigInteger bigInteger) {
        if (bigInteger == null) {
            return nullNode();
        }
        return BigIntegerNode.valueOf(bigInteger);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public NumericNode numberNode(float f) {
        return FloatNode.valueOf(f);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode numberNode(Float f) {
        return f == null ? nullNode() : FloatNode.valueOf(f.floatValue());
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public NumericNode numberNode(double d) {
        return DoubleNode.valueOf(d);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode numberNode(Double d) {
        return d == null ? nullNode() : DoubleNode.valueOf(d.doubleValue());
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode numberNode(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return nullNode();
        }
        if (this._cfgBigDecimalExact) {
            return DecimalNode.valueOf(bigDecimal);
        }
        return bigDecimal.compareTo(BigDecimal.ZERO) == 0 ? DecimalNode.ZERO : DecimalNode.valueOf(bigDecimal.stripTrailingZeros());
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public TextNode textNode(String str) {
        return TextNode.valueOf(str);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public BinaryNode binaryNode(byte[] bArr) {
        return BinaryNode.valueOf(bArr);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public BinaryNode binaryNode(byte[] bArr, int i, int i2) {
        return BinaryNode.valueOf(bArr, i, i2);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ArrayNode arrayNode() {
        return new ArrayNode(this);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ArrayNode arrayNode(int i) {
        return new ArrayNode(this, i);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ObjectNode objectNode() {
        return new ObjectNode(this);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode pojoNode(Object obj) {
        return new POJONode(obj);
    }

    @Override // com.fasterxml.jackson.databind.node.JsonNodeCreator
    public ValueNode rawValueNode(RawValue rawValue) {
        return new POJONode(rawValue);
    }
}
