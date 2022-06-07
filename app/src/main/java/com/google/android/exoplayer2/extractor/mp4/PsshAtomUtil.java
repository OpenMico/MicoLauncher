package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.nio.ByteBuffer;
import java.util.UUID;

/* loaded from: classes2.dex */
public final class PsshAtomUtil {
    private PsshAtomUtil() {
    }

    public static byte[] buildPsshAtom(UUID uuid, @Nullable byte[] bArr) {
        return buildPsshAtom(uuid, null, bArr);
    }

    public static byte[] buildPsshAtom(UUID uuid, @Nullable UUID[] uuidArr, @Nullable byte[] bArr) {
        int length = (bArr != null ? bArr.length : 0) + 32;
        if (uuidArr != null) {
            length += (uuidArr.length * 16) + 4;
        }
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.putInt(length);
        allocate.putInt(1886614376);
        allocate.putInt(uuidArr != null ? 16777216 : 0);
        allocate.putLong(uuid.getMostSignificantBits());
        allocate.putLong(uuid.getLeastSignificantBits());
        if (uuidArr != null) {
            allocate.putInt(uuidArr.length);
            for (UUID uuid2 : uuidArr) {
                allocate.putLong(uuid2.getMostSignificantBits());
                allocate.putLong(uuid2.getLeastSignificantBits());
            }
        }
        if (!(bArr == null || bArr.length == 0)) {
            allocate.putInt(bArr.length);
            allocate.put(bArr);
        }
        return allocate.array();
    }

    public static boolean isPsshAtom(byte[] bArr) {
        return a(bArr) != null;
    }

    @Nullable
    public static UUID parseUuid(byte[] bArr) {
        a a2 = a(bArr);
        if (a2 == null) {
            return null;
        }
        return a2.a;
    }

    public static int parseVersion(byte[] bArr) {
        a a2 = a(bArr);
        if (a2 == null) {
            return -1;
        }
        return a2.b;
    }

    @Nullable
    public static byte[] parseSchemeSpecificData(byte[] bArr, UUID uuid) {
        a a2 = a(bArr);
        if (a2 == null) {
            return null;
        }
        if (uuid.equals(a2.a)) {
            return a2.c;
        }
        String valueOf = String.valueOf(uuid);
        String valueOf2 = String.valueOf(a2.a);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 33 + String.valueOf(valueOf2).length());
        sb.append("UUID mismatch. Expected: ");
        sb.append(valueOf);
        sb.append(", got: ");
        sb.append(valueOf2);
        sb.append(".");
        Log.w("PsshAtomUtil", sb.toString());
        return null;
    }

    @Nullable
    private static a a(byte[] bArr) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr);
        if (parsableByteArray.limit() < 32) {
            return null;
        }
        parsableByteArray.setPosition(0);
        if (parsableByteArray.readInt() != parsableByteArray.bytesLeft() + 4 || parsableByteArray.readInt() != 1886614376) {
            return null;
        }
        int a2 = a.a(parsableByteArray.readInt());
        if (a2 > 1) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Unsupported pssh version: ");
            sb.append(a2);
            Log.w("PsshAtomUtil", sb.toString());
            return null;
        }
        UUID uuid = new UUID(parsableByteArray.readLong(), parsableByteArray.readLong());
        if (a2 == 1) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedIntToInt() * 16);
        }
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt != parsableByteArray.bytesLeft()) {
            return null;
        }
        byte[] bArr2 = new byte[readUnsignedIntToInt];
        parsableByteArray.readBytes(bArr2, 0, readUnsignedIntToInt);
        return new a(uuid, a2, bArr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a {
        private final UUID a;
        private final int b;
        private final byte[] c;

        public a(UUID uuid, int i, byte[] bArr) {
            this.a = uuid;
            this.b = i;
            this.c = bArr;
        }
    }
}
