package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/* loaded from: classes.dex */
public class UUIDDeserializer extends FromStringDeserializer<UUID> {
    static final int[] a = new int[127];
    private static final long serialVersionUID = 1;

    static {
        Arrays.fill(a, -1);
        for (int i = 0; i < 10; i++) {
            a[i + 48] = i;
        }
        for (int i2 = 0; i2 < 6; i2++) {
            int[] iArr = a;
            int i3 = i2 + 10;
            iArr[i2 + 97] = i3;
            iArr[i2 + 65] = i3;
        }
    }

    public UUIDDeserializer() {
        super(UUID.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
    public UUID _deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        if (str.length() == 36) {
            if (!(str.charAt(8) == '-' && str.charAt(13) == '-' && str.charAt(18) == '-' && str.charAt(23) == '-')) {
                a(str, deserializationContext);
            }
            int b = b(str, 24, deserializationContext);
            return new UUID((a(str, 0, deserializationContext) << 32) + ((b(str, 9, deserializationContext) << 16) | b(str, 14, deserializationContext)), ((a(str, 28, deserializationContext) << 32) >>> 32) | ((b | (b(str, 19, deserializationContext) << 16)) << 32));
        } else if (str.length() == 24) {
            return a(Base64Variants.getDefaultVariant().decode(str), deserializationContext);
        } else {
            return a(str, deserializationContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
    public UUID _deserializeEmbedded(Object obj, DeserializationContext deserializationContext) throws IOException {
        if (obj instanceof byte[]) {
            return a((byte[]) obj, deserializationContext);
        }
        super._deserializeEmbedded(obj, deserializationContext);
        return null;
    }

    private UUID a(String str, DeserializationContext deserializationContext) throws IOException {
        return (UUID) deserializationContext.handleWeirdStringValue(handledType(), str, "UUID has to be represented by standard 36-char representation", new Object[0]);
    }

    int a(String str, int i, DeserializationContext deserializationContext) throws JsonMappingException {
        return (c(str, i, deserializationContext) << 24) + (c(str, i + 2, deserializationContext) << 16) + (c(str, i + 4, deserializationContext) << 8) + c(str, i + 6, deserializationContext);
    }

    int b(String str, int i, DeserializationContext deserializationContext) throws JsonMappingException {
        return (c(str, i, deserializationContext) << 8) + c(str, i + 2, deserializationContext);
    }

    int c(String str, int i, DeserializationContext deserializationContext) throws JsonMappingException {
        char charAt = str.charAt(i);
        int i2 = i + 1;
        char charAt2 = str.charAt(i2);
        if (charAt <= 127 && charAt2 <= 127) {
            int[] iArr = a;
            int i3 = iArr[charAt2] | (iArr[charAt] << 4);
            if (i3 >= 0) {
                return i3;
            }
        }
        if (charAt > 127 || a[charAt] < 0) {
            return a(str, i, deserializationContext, charAt);
        }
        return a(str, i2, deserializationContext, charAt2);
    }

    int a(String str, int i, DeserializationContext deserializationContext, char c) throws JsonMappingException {
        throw deserializationContext.weirdStringException(str, handledType(), String.format("Non-hex character '%c' (value 0x%s), not valid for UUID String", Character.valueOf(c), Integer.toHexString(c)));
    }

    private UUID a(byte[] bArr, DeserializationContext deserializationContext) throws JsonMappingException {
        if (bArr.length == 16) {
            return new UUID(a(bArr, 0), a(bArr, 8));
        }
        JsonParser parser = deserializationContext.getParser();
        throw InvalidFormatException.from(parser, "Can only construct UUIDs from byte[16]; got " + bArr.length + " bytes", bArr, handledType());
    }

    private static long a(byte[] bArr, int i) {
        return ((b(bArr, i + 4) << 32) >>> 32) | (b(bArr, i) << 32);
    }

    private static int b(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | (bArr[i] << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }
}
