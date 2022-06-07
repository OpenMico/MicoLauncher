package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class DecimalNode extends NumericNode {
    public static final DecimalNode ZERO = new DecimalNode(BigDecimal.ZERO);
    private static final BigDecimal a = BigDecimal.valueOf(-2147483648L);
    private static final BigDecimal b = BigDecimal.valueOf(2147483647L);
    private static final BigDecimal c = BigDecimal.valueOf(Long.MIN_VALUE);
    private static final BigDecimal d = BigDecimal.valueOf(Long.MAX_VALUE);
    protected final BigDecimal _value;

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isBigDecimal() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isFloatingPointNumber() {
        return true;
    }

    public DecimalNode(BigDecimal bigDecimal) {
        this._value = bigDecimal;
    }

    public static DecimalNode valueOf(BigDecimal bigDecimal) {
        return new DecimalNode(bigDecimal);
    }

    @Override // com.fasterxml.jackson.databind.node.ValueNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.BIG_DECIMAL;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public boolean canConvertToInt() {
        return this._value.compareTo(a) >= 0 && this._value.compareTo(b) <= 0;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public boolean canConvertToLong() {
        return this._value.compareTo(c) >= 0 && this._value.compareTo(d) <= 0;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public Number numberValue() {
        return this._value;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public short shortValue() {
        return this._value.shortValue();
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public int intValue() {
        return this._value.intValue();
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public long longValue() {
        return this._value.longValue();
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public BigInteger bigIntegerValue() {
        return this._value.toBigInteger();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public float floatValue() {
        return this._value.floatValue();
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public double doubleValue() {
        return this._value.doubleValue();
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public BigDecimal decimalValue() {
        return this._value;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public String asText() {
        return this._value.toString();
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj != null && (obj instanceof DecimalNode) && ((DecimalNode) obj)._value.compareTo(this._value) == 0;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode
    public int hashCode() {
        return Double.valueOf(doubleValue()).hashCode();
    }
}
