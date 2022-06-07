package com.xiaomi.ext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Jdk8BeanSerializerModifier extends BeanSerializerModifier {
    @Override // com.fasterxml.jackson.databind.ser.BeanSerializerModifier
    public List<BeanPropertyWriter> changeProperties(SerializationConfig serializationConfig, BeanDescription beanDescription, List<BeanPropertyWriter> list) {
        for (int i = 0; i < list.size(); i++) {
            BeanPropertyWriter beanPropertyWriter = list.get(i);
            if (beanPropertyWriter.getType().isTypeOrSubTypeOf(Optional.class)) {
                list.set(i, new Jdk8OptionalBeanPropertyWriter(beanPropertyWriter, Optional.empty()));
            }
        }
        return list;
    }
}
