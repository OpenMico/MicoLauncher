package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import okio.Utf8;

/* loaded from: classes.dex */
public final class Ac3Util {
    public static final int AC3_MAX_RATE_BYTES_PER_SECOND = 80000;
    public static final String E_AC3_JOC_CODEC_STRING = "ec+3";
    public static final int E_AC3_MAX_RATE_BYTES_PER_SECOND = 768000;
    public static final int TRUEHD_MAX_RATE_BYTES_PER_SECOND = 3062500;
    public static final int TRUEHD_RECHUNK_SAMPLE_COUNT = 16;
    public static final int TRUEHD_SYNCFRAME_PREFIX_LENGTH = 10;
    private static final int[] a = {1, 2, 3, 6};
    private static final int[] b = {OpusUtil.SAMPLE_RATE, 44100, 32000};
    private static final int[] c = {24000, 22050, 16000};
    private static final int[] d = {2, 1, 2, 3, 3, 4, 4, 5};
    private static final int[] e = {32, 40, 48, 56, 64, 80, 96, 112, 128, Opcodes.IF_ICMPNE, 192, 224, 256, 320, 384, 448, 512, 576, 640};
    private static final int[] f = {69, 87, 104, 121, 139, 174, 208, 243, 278, 348, 417, 487, 557, 696, 835, 975, 1114, 1253, 1393};

    /* loaded from: classes.dex */
    public static final class SyncFrameInfo {
        public static final int STREAM_TYPE_TYPE0 = 0;
        public static final int STREAM_TYPE_TYPE1 = 1;
        public static final int STREAM_TYPE_TYPE2 = 2;
        public static final int STREAM_TYPE_UNDEFINED = -1;
        public final int channelCount;
        public final int frameSize;
        @Nullable
        public final String mimeType;
        public final int sampleCount;
        public final int sampleRate;
        public final int streamType;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes.dex */
        public @interface StreamType {
        }

        private SyncFrameInfo(@Nullable String str, int i, int i2, int i3, int i4, int i5) {
            this.mimeType = str;
            this.streamType = i;
            this.channelCount = i2;
            this.sampleRate = i3;
            this.frameSize = i4;
            this.sampleCount = i5;
        }
    }

    public static Format parseAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, @Nullable DrmInitData drmInitData) {
        int i = b[(parsableByteArray.readUnsignedByte() & 192) >> 6];
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = d[(readUnsignedByte & 56) >> 3];
        if ((readUnsignedByte & 4) != 0) {
            i2++;
        }
        return new Format.Builder().setId(str).setSampleMimeType(MimeTypes.AUDIO_AC3).setChannelCount(i2).setSampleRate(i).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static Format parseEAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, @Nullable DrmInitData drmInitData) {
        parsableByteArray.skipBytes(2);
        int i = b[(parsableByteArray.readUnsignedByte() & 192) >> 6];
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = d[(readUnsignedByte & 14) >> 1];
        if ((readUnsignedByte & 1) != 0) {
            i2++;
        }
        if (((parsableByteArray.readUnsignedByte() & 30) >> 1) > 0 && (2 & parsableByteArray.readUnsignedByte()) != 0) {
            i2 += 2;
        }
        String str3 = MimeTypes.AUDIO_E_AC3;
        if (parsableByteArray.bytesLeft() > 0 && (parsableByteArray.readUnsignedByte() & 1) != 0) {
            str3 = MimeTypes.AUDIO_E_AC3_JOC;
        }
        return new Format.Builder().setId(str).setSampleMimeType(str3).setChannelCount(i2).setSampleRate(i).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static SyncFrameInfo parseAc3SyncframeInfo(ParsableBitArray parsableBitArray) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        String str;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int position = parsableBitArray.getPosition();
        parsableBitArray.skipBits(40);
        boolean z = parsableBitArray.readBits(5) > 10;
        parsableBitArray.setPosition(position);
        int i13 = -1;
        if (z) {
            parsableBitArray.skipBits(16);
            switch (parsableBitArray.readBits(2)) {
                case 0:
                    i13 = 0;
                    break;
                case 1:
                    i13 = 1;
                    break;
                case 2:
                    i13 = 2;
                    break;
            }
            parsableBitArray.skipBits(3);
            i2 = (parsableBitArray.readBits(11) + 1) * 2;
            int readBits = parsableBitArray.readBits(2);
            if (readBits == 3) {
                i3 = c[parsableBitArray.readBits(2)];
                i7 = 6;
                i8 = 3;
            } else {
                i8 = parsableBitArray.readBits(2);
                i7 = a[i8];
                i3 = b[readBits];
            }
            i = i7 * 256;
            int readBits2 = parsableBitArray.readBits(3);
            boolean readBit = parsableBitArray.readBit();
            i4 = d[readBits2] + (readBit ? 1 : 0);
            parsableBitArray.skipBits(10);
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(8);
            }
            if (readBits2 == 0) {
                parsableBitArray.skipBits(5);
                if (parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
            }
            if (i13 == 1 && parsableBitArray.readBit()) {
                parsableBitArray.skipBits(16);
            }
            if (parsableBitArray.readBit()) {
                if (readBits2 > 2) {
                    parsableBitArray.skipBits(2);
                }
                if ((readBits2 & 1) == 0 || readBits2 <= 2) {
                    i11 = 6;
                } else {
                    i11 = 6;
                    parsableBitArray.skipBits(6);
                }
                if ((readBits2 & 4) != 0) {
                    parsableBitArray.skipBits(i11);
                }
                if (readBit && parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(5);
                }
                if (i13 == 0) {
                    if (parsableBitArray.readBit()) {
                        i12 = 6;
                        parsableBitArray.skipBits(6);
                    } else {
                        i12 = 6;
                    }
                    if (readBits2 == 0 && parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(i12);
                    }
                    if (parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(i12);
                    }
                    int readBits3 = parsableBitArray.readBits(2);
                    if (readBits3 == 1) {
                        parsableBitArray.skipBits(5);
                    } else if (readBits3 == 2) {
                        parsableBitArray.skipBits(12);
                    } else if (readBits3 == 3) {
                        int readBits4 = parsableBitArray.readBits(5);
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(5);
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(4);
                                }
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(4);
                                }
                            }
                        }
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(5);
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(7);
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(8);
                                }
                            }
                        }
                        parsableBitArray.skipBits((readBits4 + 2) * 8);
                        parsableBitArray.byteAlign();
                    }
                    if (readBits2 < 2) {
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(14);
                        }
                        if (readBits2 == 0 && parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(14);
                        }
                    }
                    if (parsableBitArray.readBit()) {
                        if (i8 == 0) {
                            parsableBitArray.skipBits(5);
                        } else {
                            for (int i14 = 0; i14 < i7; i14++) {
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(5);
                                }
                            }
                        }
                    }
                }
            }
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(5);
                if (readBits2 == 2) {
                    parsableBitArray.skipBits(4);
                    i10 = 6;
                } else {
                    i10 = 6;
                }
                if (readBits2 >= i10) {
                    parsableBitArray.skipBits(2);
                }
                if (parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
                if (readBits2 == 0 && parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
                if (readBits < 3) {
                    parsableBitArray.skipBit();
                }
            }
            if (i13 == 0 && i8 != 3) {
                parsableBitArray.skipBit();
            }
            if (i13 == 2) {
                if (i8 == 3) {
                    i9 = 6;
                } else if (parsableBitArray.readBit()) {
                    i9 = 6;
                } else {
                    i9 = 6;
                }
                parsableBitArray.skipBits(i9);
            } else {
                i9 = 6;
            }
            str = MimeTypes.AUDIO_E_AC3;
            if (parsableBitArray.readBit() && parsableBitArray.readBits(i9) == 1 && parsableBitArray.readBits(8) == 1) {
                str = MimeTypes.AUDIO_E_AC3_JOC;
            }
            i5 = i13;
        } else {
            str = MimeTypes.AUDIO_AC3;
            parsableBitArray.skipBits(32);
            int readBits5 = parsableBitArray.readBits(2);
            if (readBits5 == 3) {
                str = null;
                i6 = 6;
            } else {
                i6 = 6;
            }
            int a2 = a(readBits5, parsableBitArray.readBits(i6));
            parsableBitArray.skipBits(8);
            int readBits6 = parsableBitArray.readBits(3);
            if (!((readBits6 & 1) == 0 || readBits6 == 1)) {
                parsableBitArray.skipBits(2);
            }
            if ((readBits6 & 4) != 0) {
                parsableBitArray.skipBits(2);
            }
            if (readBits6 == 2) {
                parsableBitArray.skipBits(2);
            }
            int[] iArr = b;
            i3 = readBits5 < iArr.length ? iArr[readBits5] : -1;
            i = 1536;
            i4 = d[readBits6] + (parsableBitArray.readBit() ? 1 : 0);
            i5 = -1;
            i2 = a2;
        }
        return new SyncFrameInfo(str, i5, i4, i3, i2, i);
    }

    public static int parseAc3SyncframeSize(byte[] bArr) {
        if (bArr.length < 6) {
            return -1;
        }
        if (!(((bArr[5] & 248) >> 3) > 10)) {
            return a((bArr[4] & 192) >> 6, bArr[4] & Utf8.REPLACEMENT_BYTE);
        }
        return (((bArr[3] & 255) | ((bArr[2] & 7) << 8)) + 1) * 2;
    }

    public static int parseAc3SyncframeAudioSampleCount(ByteBuffer byteBuffer) {
        int i = 3;
        if (!(((byteBuffer.get(byteBuffer.position() + 5) & 248) >> 3) > 10)) {
            return 1536;
        }
        if (((byteBuffer.get(byteBuffer.position() + 4) & 192) >> 6) != 3) {
            i = (byteBuffer.get(byteBuffer.position() + 4) & 48) >> 4;
        }
        return a[i] * 256;
    }

    public static int findTrueHdSyncframeOffset(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int limit = byteBuffer.limit() - 10;
        for (int i = position; i <= limit; i++) {
            if ((Util.getBigEndianInt(byteBuffer, i + 4) & (-2)) == -126718022) {
                return i - position;
            }
        }
        return -1;
    }

    public static int parseTrueHdSyncframeAudioSampleCount(byte[] bArr) {
        boolean z = false;
        if (bArr[4] != -8 || bArr[5] != 114 || bArr[6] != 111 || (bArr[7] & 254) != 186) {
            return 0;
        }
        if ((bArr[7] & 255) == 187) {
            z = true;
        }
        return 40 << ((bArr[z ? '\t' : '\b'] >> 4) & 7);
    }

    public static int parseTrueHdSyncframeAudioSampleCount(ByteBuffer byteBuffer, int i) {
        return 40 << ((byteBuffer.get((byteBuffer.position() + i) + ((byteBuffer.get((byteBuffer.position() + i) + 7) & 255) == 187 ? 9 : 8)) >> 4) & 7);
    }

    private static int a(int i, int i2) {
        int i3 = i2 / 2;
        if (i < 0) {
            return -1;
        }
        int[] iArr = b;
        if (i >= iArr.length || i2 < 0) {
            return -1;
        }
        int[] iArr2 = f;
        if (i3 >= iArr2.length) {
            return -1;
        }
        int i4 = iArr[i];
        if (i4 == 44100) {
            return (iArr2[i3] + (i2 % 2)) * 2;
        }
        int i5 = e[i3];
        return i4 == 32000 ? i5 * 6 : i5 * 4;
    }

    private Ac3Util() {
    }
}
