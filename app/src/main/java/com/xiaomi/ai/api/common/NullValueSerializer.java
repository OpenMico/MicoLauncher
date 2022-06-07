package com.xiaomi.ai.api.common;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class NullValueSerializer extends StdSerializer<Object> {
    /* JADX INFO: Access modifiers changed from: protected */
    public NullValueSerializer() {
        super(Object.class);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        throwForNull(jsonGenerator);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        throwForNull(jsonGenerator);
    }

    private void throwForNull(JsonGenerator jsonGenerator) throws IOException {
        String str;
        String str2;
        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder();
        JsonStreamContext outputContext = jsonGenerator.getOutputContext();
        Object currentValue = outputContext.getCurrentValue();
        while (outputContext != null) {
            if (outputContext.inRoot()) {
                str = "[" + currentValue.getClass().getName() + "]";
            } else {
                if (outputContext.inArray()) {
                    sb = new StringBuilder();
                    sb.append("/[");
                    sb.append(outputContext.getCurrentIndex());
                    str2 = "]";
                } else {
                    sb = new StringBuilder();
                    sb.append("/");
                    str2 = outputContext.getCurrentName();
                }
                sb.append(str2);
                str = sb.toString();
            }
            sb2.insert(0, str);
            if (outputContext.getCurrentValue() != null) {
                currentValue = outputContext.getCurrentValue();
            }
            outputContext = outputContext.getParent();
        }
        Utils.dumpValue(currentValue, sb2);
        throw new JsonGenerationException(String.format("Missing required value: %s", sb2.toString()));
    }
}
