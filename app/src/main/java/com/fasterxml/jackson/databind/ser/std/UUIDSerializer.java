package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.UUID;

/* loaded from: classes.dex */
public class UUIDSerializer extends StdScalarSerializer<UUID> {
    static final char[] a = "0123456789abcdef".toCharArray();

    public UUIDSerializer() {
        super(UUID.class);
    }

    public boolean isEmpty(SerializerProvider serializerProvider, UUID uuid) {
        return uuid.getLeastSignificantBits() == 0 && uuid.getMostSignificantBits() == 0;
    }

    public void serialize(UUID uuid, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (!jsonGenerator.canWriteBinaryNatively() || (jsonGenerator instanceof TokenBuffer)) {
            char[] cArr = new char[36];
            long mostSignificantBits = uuid.getMostSignificantBits();
            a((int) (mostSignificantBits >> 32), cArr, 0);
            cArr[8] = '-';
            int i = (int) mostSignificantBits;
            b(i >>> 16, cArr, 9);
            cArr[13] = '-';
            b(i, cArr, 14);
            cArr[18] = '-';
            long leastSignificantBits = uuid.getLeastSignificantBits();
            b((int) (leastSignificantBits >>> 48), cArr, 19);
            cArr[23] = '-';
            b((int) (leastSignificantBits >>> 32), cArr, 24);
            a((int) leastSignificantBits, cArr, 28);
            jsonGenerator.writeString(cArr, 0, 36);
            return;
        }
        jsonGenerator.writeBinary(a(uuid));
    }

    private static void a(int i, char[] cArr, int i2) {
        b(i >> 16, cArr, i2);
        b(i, cArr, i2 + 4);
    }

    private static void b(int i, char[] cArr, int i2) {
        char[] cArr2 = a;
        cArr[i2] = cArr2[(i >> 12) & 15];
        int i3 = i2 + 1;
        cArr[i3] = cArr2[(i >> 8) & 15];
        int i4 = i3 + 1;
        cArr[i4] = cArr2[(i >> 4) & 15];
        cArr[i4 + 1] = cArr2[i & 15];
    }

    private static final byte[] a(UUID uuid) {
        byte[] bArr = new byte[16];
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        a((int) (mostSignificantBits >> 32), bArr, 0);
        a((int) mostSignificantBits, bArr, 4);
        a((int) (leastSignificantBits >> 32), bArr, 8);
        a((int) leastSignificantBits, bArr, 12);
        return bArr;
    }

    private static final void a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >> 24);
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >> 16);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i >> 8);
        bArr[i4 + 1] = (byte) i;
    }
}
