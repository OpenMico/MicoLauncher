package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes.dex */
public class SimpleDateFormatSerializer implements ObjectSerializer {
    private final String pattern;

    public SimpleDateFormatSerializer(String str) {
        this.pattern = str;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        if (obj == null) {
            jSONSerializer.out.writeNull();
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.pattern, jSONSerializer.locale);
        simpleDateFormat.setTimeZone(jSONSerializer.timeZone);
        jSONSerializer.write(simpleDateFormat.format((Date) obj));
    }
}
