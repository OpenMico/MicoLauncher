package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CharArrayCodec implements ObjectDeserializer {
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T deserialze(DefaultJSONParser defaultJSONParser) {
        boolean z;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 4) {
            String stringVal = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            return (T) stringVal.toCharArray();
        } else if (jSONLexer.token() == 2) {
            Number integerValue = jSONLexer.integerValue();
            jSONLexer.nextToken(16);
            return (T) integerValue.toString().toCharArray();
        } else {
            Object parse = defaultJSONParser.parse();
            if (parse instanceof String) {
                return (T) ((String) parse).toCharArray();
            }
            if (parse instanceof Collection) {
                Collection<String> collection = (Collection) parse;
                Iterator it = collection.iterator();
                while (true) {
                    z = true;
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    if ((next instanceof String) && ((String) next).length() != 1) {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    char[] cArr = new char[collection.size()];
                    int i = 0;
                    for (String str : collection) {
                        i++;
                        cArr[i] = str.charAt(0);
                    }
                    return cArr;
                }
                throw new JSONException("can not cast to char[]");
            } else if (parse == null) {
                return null;
            } else {
                return (T) JSON.toJSONString(parse).toCharArray();
            }
        }
    }
}
