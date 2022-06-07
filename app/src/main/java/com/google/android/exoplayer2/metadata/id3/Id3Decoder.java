package com.google.android.exoplayer2.metadata.id3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class Id3Decoder extends SimpleMetadataDecoder {
    public static final int ID3_HEADER_LENGTH = 10;
    public static final int ID3_TAG = 4801587;
    public static final FramePredicate NO_FRAMES_PREDICATE = $$Lambda$Id3Decoder$B3fcTuP3ulBY6FQRxpR_m4ZfWvA.INSTANCE;
    @Nullable
    private final FramePredicate a;

    /* loaded from: classes2.dex */
    public interface FramePredicate {
        boolean evaluate(int i, int i2, int i3, int i4, int i5);
    }

    private static String a(int i) {
        switch (i) {
            case 1:
                return "UTF-16";
            case 2:
                return "UTF-16BE";
            case 3:
                return "UTF-8";
            default:
                return "ISO-8859-1";
        }
    }

    private static int b(int i) {
        return (i == 0 || i == 3) ? 1 : 2;
    }

    public static /* synthetic */ boolean b(int i, int i2, int i3, int i4, int i5) {
        return false;
    }

    public Id3Decoder() {
        this(null);
    }

    public Id3Decoder(@Nullable FramePredicate framePredicate) {
        this.a = framePredicate;
    }

    @Override // com.google.android.exoplayer2.metadata.SimpleMetadataDecoder
    @Nullable
    protected Metadata decode(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer) {
        return decode(byteBuffer.array(), byteBuffer.limit());
    }

    @Nullable
    public Metadata decode(byte[] bArr, int i) {
        ArrayList arrayList = new ArrayList();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        a a2 = a(parsableByteArray);
        if (a2 == null) {
            return null;
        }
        int position = parsableByteArray.getPosition();
        int i2 = a2.a == 2 ? 6 : 10;
        int i3 = a2.c;
        if (a2.b) {
            i3 = g(parsableByteArray, a2.c);
        }
        parsableByteArray.setLimit(position + i3);
        boolean z = false;
        if (!a(parsableByteArray, a2.a, i2, false)) {
            if (a2.a != 4 || !a(parsableByteArray, 4, i2, true)) {
                int i4 = a2.a;
                StringBuilder sb = new StringBuilder(56);
                sb.append("Failed to validate ID3 tag with majorVersion=");
                sb.append(i4);
                Log.w("Id3Decoder", sb.toString());
                return null;
            }
            z = true;
        }
        while (parsableByteArray.bytesLeft() >= i2) {
            Id3Frame a3 = a(a2.a, parsableByteArray, z, i2, this.a);
            if (a3 != null) {
                arrayList.add(a3);
            }
        }
        return new Metadata(arrayList);
    }

    @Nullable
    private static a a(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() < 10) {
            Log.w("Id3Decoder", "Data too short to be an ID3 tag");
            return null;
        }
        int readUnsignedInt24 = parsableByteArray.readUnsignedInt24();
        boolean z = false;
        if (readUnsignedInt24 != 4801587) {
            String valueOf = String.valueOf(String.format("%06X", Integer.valueOf(readUnsignedInt24)));
            Log.w("Id3Decoder", valueOf.length() != 0 ? "Unexpected first three bytes of ID3 tag header: 0x".concat(valueOf) : new String("Unexpected first three bytes of ID3 tag header: 0x"));
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.skipBytes(1);
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        int readSynchSafeInt = parsableByteArray.readSynchSafeInt();
        if (readUnsignedByte == 2) {
            if ((readUnsignedByte2 & 64) != 0) {
                Log.w("Id3Decoder", "Skipped ID3 tag with majorVersion=2 and undefined compression scheme");
                return null;
            }
        } else if (readUnsignedByte == 3) {
            if ((readUnsignedByte2 & 64) != 0) {
                int readInt = parsableByteArray.readInt();
                parsableByteArray.skipBytes(readInt);
                readSynchSafeInt -= readInt + 4;
            }
        } else if (readUnsignedByte == 4) {
            if ((readUnsignedByte2 & 64) != 0) {
                int readSynchSafeInt2 = parsableByteArray.readSynchSafeInt();
                parsableByteArray.skipBytes(readSynchSafeInt2 - 4);
                readSynchSafeInt -= readSynchSafeInt2;
            }
            if ((readUnsignedByte2 & 16) != 0) {
                readSynchSafeInt -= 10;
            }
        } else {
            StringBuilder sb = new StringBuilder(57);
            sb.append("Skipped ID3 tag with unsupported majorVersion=");
            sb.append(readUnsignedByte);
            Log.w("Id3Decoder", sb.toString());
            return null;
        }
        if (readUnsignedByte < 4 && (readUnsignedByte2 & 128) != 0) {
            z = true;
        }
        return new a(readUnsignedByte, z, readSynchSafeInt);
    }

    private static boolean a(ParsableByteArray parsableByteArray, int i, int i2, boolean z) {
        int i3;
        long j;
        int i4;
        boolean z2;
        boolean z3;
        int position = parsableByteArray.getPosition();
        while (true) {
            try {
                int i5 = 1;
                if (parsableByteArray.bytesLeft() < i2) {
                    return true;
                }
                if (i >= 3) {
                    i4 = parsableByteArray.readInt();
                    j = parsableByteArray.readUnsignedInt();
                    i3 = parsableByteArray.readUnsignedShort();
                } else {
                    i4 = parsableByteArray.readUnsignedInt24();
                    j = parsableByteArray.readUnsignedInt24();
                    i3 = 0;
                }
                if (i4 == 0 && j == 0 && i3 == 0) {
                    return true;
                }
                if (i == 4 && !z) {
                    if ((8421504 & j) != 0) {
                        return false;
                    }
                    j = (((j >> 24) & 255) << 21) | (j & 255) | (((j >> 8) & 255) << 7) | (((j >> 16) & 255) << 14);
                }
                if (i == 4) {
                    z3 = (i3 & 64) != 0;
                    z2 = (i3 & 1) != 0;
                } else if (i == 3) {
                    z3 = (i3 & 32) != 0;
                    z2 = (i3 & 128) != 0;
                } else {
                    z3 = false;
                    z2 = false;
                }
                if (!z3) {
                    i5 = 0;
                }
                if (z2) {
                    i5 += 4;
                }
                if (j < i5) {
                    return false;
                }
                if (parsableByteArray.bytesLeft() < j) {
                    return false;
                }
                parsableByteArray.skipBytes((int) j);
            } finally {
                parsableByteArray.setPosition(position);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:132:0x0193, code lost:
        if (r13 == 67) goto L_0x0195;
     */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.metadata.id3.Id3Frame a(int r19, com.google.android.exoplayer2.util.ParsableByteArray r20, boolean r21, int r22, @androidx.annotation.Nullable com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate r23) {
        /*
            Method dump skipped, instructions count: 581
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.a(int, com.google.android.exoplayer2.util.ParsableByteArray, boolean, int, com.google.android.exoplayer2.metadata.id3.Id3Decoder$FramePredicate):com.google.android.exoplayer2.metadata.id3.Id3Frame");
    }

    @Nullable
    private static TextInformationFrame a(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String a2 = a(readUnsignedByte);
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        int a3 = a(bArr, 0, readUnsignedByte);
        String str = new String(bArr, 0, a3, a2);
        int b = a3 + b(readUnsignedByte);
        return new TextInformationFrame("TXXX", str, a(bArr, b, a(bArr, b, readUnsignedByte), a2));
    }

    @Nullable
    private static TextInformationFrame a(ParsableByteArray parsableByteArray, int i, String str) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String a2 = a(readUnsignedByte);
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        return new TextInformationFrame(str, null, new String(bArr, 0, a(bArr, 0, readUnsignedByte), a2));
    }

    @Nullable
    private static UrlLinkFrame b(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String a2 = a(readUnsignedByte);
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        int a3 = a(bArr, 0, readUnsignedByte);
        String str = new String(bArr, 0, a3, a2);
        int b = a3 + b(readUnsignedByte);
        return new UrlLinkFrame("WXXX", str, a(bArr, b, a(bArr, b), "ISO-8859-1"));
    }

    private static UrlLinkFrame b(ParsableByteArray parsableByteArray, int i, String str) throws UnsupportedEncodingException {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        return new UrlLinkFrame(str, null, new String(bArr, 0, a(bArr, 0), "ISO-8859-1"));
    }

    private static PrivFrame c(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        int a2 = a(bArr, 0);
        return new PrivFrame(new String(bArr, 0, a2, "ISO-8859-1"), b(bArr, a2 + 1, bArr.length));
    }

    private static GeobFrame d(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String a2 = a(readUnsignedByte);
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        int a3 = a(bArr, 0);
        String str = new String(bArr, 0, a3, "ISO-8859-1");
        int i3 = a3 + 1;
        int a4 = a(bArr, i3, readUnsignedByte);
        String a5 = a(bArr, i3, a4, a2);
        int b = a4 + b(readUnsignedByte);
        int a6 = a(bArr, b, readUnsignedByte);
        return new GeobFrame(str, a5, a(bArr, b, a6, a2), b(bArr, a6 + b(readUnsignedByte), bArr.length));
    }

    private static ApicFrame a(ParsableByteArray parsableByteArray, int i, int i2) throws UnsupportedEncodingException {
        String str;
        int i3;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String a2 = a(readUnsignedByte);
        int i4 = i - 1;
        byte[] bArr = new byte[i4];
        parsableByteArray.readBytes(bArr, 0, i4);
        if (i2 == 2) {
            String valueOf = String.valueOf(Ascii.toLowerCase(new String(bArr, 0, 3, "ISO-8859-1")));
            String concat = valueOf.length() != 0 ? "image/".concat(valueOf) : new String("image/");
            if ("image/jpg".equals(concat)) {
                str = "image/jpeg";
                i3 = 2;
            } else {
                str = concat;
                i3 = 2;
            }
        } else {
            i3 = a(bArr, 0);
            str = Ascii.toLowerCase(new String(bArr, 0, i3, "ISO-8859-1"));
            if (str.indexOf(47) == -1) {
                String valueOf2 = String.valueOf(str);
                str = valueOf2.length() != 0 ? "image/".concat(valueOf2) : new String("image/");
            }
        }
        int i5 = i3 + 2;
        int a3 = a(bArr, i5, readUnsignedByte);
        return new ApicFrame(str, new String(bArr, i5, a3 - i5, a2), bArr[i3 + 1] & 255, b(bArr, a3 + b(readUnsignedByte), bArr.length));
    }

    @Nullable
    private static CommentFrame e(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        if (i < 4) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String a2 = a(readUnsignedByte);
        byte[] bArr = new byte[3];
        parsableByteArray.readBytes(bArr, 0, 3);
        String str = new String(bArr, 0, 3);
        int i2 = i - 4;
        byte[] bArr2 = new byte[i2];
        parsableByteArray.readBytes(bArr2, 0, i2);
        int a3 = a(bArr2, 0, readUnsignedByte);
        String str2 = new String(bArr2, 0, a3, a2);
        int b = a3 + b(readUnsignedByte);
        return new CommentFrame(str, str2, a(bArr2, b, a(bArr2, b, readUnsignedByte), a2));
    }

    private static ChapterFrame a(ParsableByteArray parsableByteArray, int i, int i2, boolean z, int i3, @Nullable FramePredicate framePredicate) throws UnsupportedEncodingException {
        int position = parsableByteArray.getPosition();
        int a2 = a(parsableByteArray.getData(), position);
        String str = new String(parsableByteArray.getData(), position, a2 - position, "ISO-8859-1");
        parsableByteArray.setPosition(a2 + 1);
        int readInt = parsableByteArray.readInt();
        int readInt2 = parsableByteArray.readInt();
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        long j = readUnsignedInt == 4294967295L ? -1L : readUnsignedInt;
        long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
        long j2 = readUnsignedInt2 == 4294967295L ? -1L : readUnsignedInt2;
        ArrayList arrayList = new ArrayList();
        int i4 = position + i;
        while (parsableByteArray.getPosition() < i4) {
            Id3Frame a3 = a(i2, parsableByteArray, z, i3, framePredicate);
            if (a3 != null) {
                arrayList.add(a3);
            }
        }
        return new ChapterFrame(str, readInt, readInt2, j, j2, (Id3Frame[]) arrayList.toArray(new Id3Frame[0]));
    }

    private static ChapterTocFrame b(ParsableByteArray parsableByteArray, int i, int i2, boolean z, int i3, @Nullable FramePredicate framePredicate) throws UnsupportedEncodingException {
        int position = parsableByteArray.getPosition();
        int a2 = a(parsableByteArray.getData(), position);
        String str = new String(parsableByteArray.getData(), position, a2 - position, "ISO-8859-1");
        parsableByteArray.setPosition(a2 + 1);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        boolean z2 = (readUnsignedByte & 2) != 0;
        boolean z3 = (readUnsignedByte & 1) != 0;
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        String[] strArr = new String[readUnsignedByte2];
        for (int i4 = 0; i4 < readUnsignedByte2; i4++) {
            int position2 = parsableByteArray.getPosition();
            int a3 = a(parsableByteArray.getData(), position2);
            strArr[i4] = new String(parsableByteArray.getData(), position2, a3 - position2, "ISO-8859-1");
            parsableByteArray.setPosition(a3 + 1);
        }
        ArrayList arrayList = new ArrayList();
        int i5 = position + i;
        while (parsableByteArray.getPosition() < i5) {
            Id3Frame a4 = a(i2, parsableByteArray, z, i3, framePredicate);
            if (a4 != null) {
                arrayList.add(a4);
            }
        }
        return new ChapterTocFrame(str, z2, z3, strArr, (Id3Frame[]) arrayList.toArray(new Id3Frame[0]));
    }

    private static MlltFrame f(ParsableByteArray parsableByteArray, int i) {
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedInt24 = parsableByteArray.readUnsignedInt24();
        int readUnsignedInt242 = parsableByteArray.readUnsignedInt24();
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        ParsableBitArray parsableBitArray = new ParsableBitArray();
        parsableBitArray.reset(parsableByteArray);
        int i2 = ((i - 10) * 8) / (readUnsignedByte + readUnsignedByte2);
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int readBits = parsableBitArray.readBits(readUnsignedByte);
            int readBits2 = parsableBitArray.readBits(readUnsignedByte2);
            iArr[i3] = readBits;
            iArr2[i3] = readBits2;
        }
        return new MlltFrame(readUnsignedShort, readUnsignedInt24, readUnsignedInt242, iArr, iArr2);
    }

    private static BinaryFrame c(ParsableByteArray parsableByteArray, int i, String str) {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        return new BinaryFrame(str, bArr);
    }

    private static int g(ParsableByteArray parsableByteArray, int i) {
        byte[] data = parsableByteArray.getData();
        int position = parsableByteArray.getPosition();
        int i2 = i;
        int i3 = position;
        while (true) {
            int i4 = i3 + 1;
            if (i4 >= position + i2) {
                return i2;
            }
            if ((data[i3] & 255) == 255 && data[i4] == 0) {
                System.arraycopy(data, i3 + 2, data, i4, (i2 - (i3 - position)) - 2);
                i2--;
            }
            i3 = i4;
        }
    }

    private static String a(int i, int i2, int i3, int i4, int i5) {
        return i == 2 ? String.format(Locale.US, "%c%c%c", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)) : String.format(Locale.US, "%c%c%c%c", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
    }

    private static int a(byte[] bArr, int i, int i2) {
        int a2 = a(bArr, i);
        if (i2 == 0 || i2 == 3) {
            return a2;
        }
        while (a2 < bArr.length - 1) {
            if ((a2 - i) % 2 == 0 && bArr[a2 + 1] == 0) {
                return a2;
            }
            a2 = a(bArr, a2 + 1);
        }
        return bArr.length;
    }

    private static int a(byte[] bArr, int i) {
        while (i < bArr.length) {
            if (bArr[i] == 0) {
                return i;
            }
            i++;
        }
        return bArr.length;
    }

    private static byte[] b(byte[] bArr, int i, int i2) {
        if (i2 <= i) {
            return Util.EMPTY_BYTE_ARRAY;
        }
        return Arrays.copyOfRange(bArr, i, i2);
    }

    private static String a(byte[] bArr, int i, int i2, String str) throws UnsupportedEncodingException {
        return (i2 <= i || i2 > bArr.length) ? "" : new String(bArr, i, i2 - i, str);
    }

    /* loaded from: classes2.dex */
    public static final class a {
        private final int a;
        private final boolean b;
        private final int c;

        public a(int i, boolean z, int i2) {
            this.a = i;
            this.b = z;
            this.c = i2;
        }
    }
}
