package com.alibaba.fastjson.support.moneta;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import javax.money.Monetary;
import org.javamoney.moneta.Money;

/* loaded from: classes.dex */
public class MonetaCodec implements ObjectDeserializer, ObjectSerializer {
    public static final MonetaCodec instance = new MonetaCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 0;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        Money money = (Money) obj;
        if (money == null) {
            jSONSerializer.writeNull();
            return;
        }
        SerializeWriter serializeWriter = jSONSerializer.out;
        serializeWriter.writeFieldValue('{', "numberStripped", money.getNumberStripped());
        serializeWriter.writeFieldValue(StringUtil.COMMA, "currency", money.getCurrency().getCurrencyCode());
        serializeWriter.write(125);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        String str;
        JSONObject parseObject = defaultJSONParser.parseObject();
        Object obj2 = parseObject.get("currency");
        if (obj2 instanceof JSONObject) {
            str = ((JSONObject) obj2).getString("currencyCode");
        } else {
            str = obj2 instanceof String ? (String) obj2 : null;
        }
        Object obj3 = parseObject.get("numberStripped");
        if (obj3 instanceof BigDecimal) {
            return (T) Money.of((BigDecimal) obj3, Monetary.getCurrency(str, new String[0]));
        }
        throw new UnsupportedOperationException();
    }
}
