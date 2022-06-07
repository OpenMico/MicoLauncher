package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class TimeZoneSerializer extends StdScalarSerializer<TimeZone> {
    public TimeZoneSerializer() {
        super(TimeZone.class);
    }

    public void serialize(TimeZone timeZone, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(timeZone.getID());
    }

    public void serializeWithType(TimeZone timeZone, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(timeZone, TimeZone.class, JsonToken.VALUE_STRING));
        serialize(timeZone, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }
}
