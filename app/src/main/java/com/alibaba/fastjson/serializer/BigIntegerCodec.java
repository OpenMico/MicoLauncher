package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class BigIntegerCodec implements ObjectDeserializer, ObjectSerializer {
    private static final BigInteger LOW = BigInteger.valueOf(-9007199254740991L);
    private static final BigInteger HIGH = BigInteger.valueOf(9007199254740991L);
    public static final BigIntegerCodec instance = new BigIntegerCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        BigInteger bigInteger = (BigInteger) obj;
        String bigInteger2 = bigInteger.toString();
        if (bigInteger2.length() < 16 || !SerializerFeature.isEnabled(i, serializeWriter.features, SerializerFeature.BrowserCompatible) || (bigInteger.compareTo(LOW) >= 0 && bigInteger.compareTo(HIGH) <= 0)) {
            serializeWriter.write(bigInteger2);
        } else {
            serializeWriter.writeString(bigInteger2);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser);
    }

    public static <T> T deserialze(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 2) {
            String numberString = jSONLexer.numberString();
            jSONLexer.nextToken(16);
            return (T) new BigInteger(numberString);
        }
        Object parse = defaultJSONParser.parse();
        if (parse == null) {
            return null;
        }
        return (T) TypeUtils.castToBigInteger(parse);
    }
}
