package com.bumptech.glide.load.resource.bitmap;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.view.InputDeviceCompat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final class DefaultImageHeaderParser implements ImageHeaderParser {
    static final byte[] a = "Exif\u0000\u0000".getBytes(Charset.forName("UTF-8"));
    private static final int[] b = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};

    private static int a(int i, int i2) {
        return i + 2 + (i2 * 12);
    }

    private static boolean a(int i) {
        return (i & 65496) == 65496 || i == 19789 || i == 18761;
    }

    @Override // com.bumptech.glide.load.ImageHeaderParser
    @NonNull
    public ImageHeaderParser.ImageType getType(@NonNull InputStream inputStream) throws IOException {
        return a(new c((InputStream) Preconditions.checkNotNull(inputStream)));
    }

    @Override // com.bumptech.glide.load.ImageHeaderParser
    @NonNull
    public ImageHeaderParser.ImageType getType(@NonNull ByteBuffer byteBuffer) throws IOException {
        return a(new a((ByteBuffer) Preconditions.checkNotNull(byteBuffer)));
    }

    @Override // com.bumptech.glide.load.ImageHeaderParser
    public int getOrientation(@NonNull InputStream inputStream, @NonNull ArrayPool arrayPool) throws IOException {
        return a(new c((InputStream) Preconditions.checkNotNull(inputStream)), (ArrayPool) Preconditions.checkNotNull(arrayPool));
    }

    @Override // com.bumptech.glide.load.ImageHeaderParser
    public int getOrientation(@NonNull ByteBuffer byteBuffer, @NonNull ArrayPool arrayPool) throws IOException {
        return a(new a((ByteBuffer) Preconditions.checkNotNull(byteBuffer)), (ArrayPool) Preconditions.checkNotNull(arrayPool));
    }

    @NonNull
    private ImageHeaderParser.ImageType a(Reader reader) throws IOException {
        try {
            int b2 = reader.b();
            if (b2 == 65496) {
                return ImageHeaderParser.ImageType.JPEG;
            }
            int a2 = (b2 << 8) | reader.a();
            if (a2 == 4671814) {
                return ImageHeaderParser.ImageType.GIF;
            }
            int a3 = (a2 << 8) | reader.a();
            if (a3 == -1991225785) {
                reader.a(21L);
                try {
                    return reader.a() >= 3 ? ImageHeaderParser.ImageType.PNG_A : ImageHeaderParser.ImageType.PNG;
                } catch (Reader.EndOfFileException unused) {
                    return ImageHeaderParser.ImageType.PNG;
                }
            } else if (a3 != 1380533830) {
                return a(reader, a3) ? ImageHeaderParser.ImageType.AVIF : ImageHeaderParser.ImageType.UNKNOWN;
            } else {
                reader.a(4L);
                if (((reader.b() << 16) | reader.b()) != 1464156752) {
                    return ImageHeaderParser.ImageType.UNKNOWN;
                }
                int b3 = (reader.b() << 16) | reader.b();
                if ((b3 & InputDeviceCompat.SOURCE_ANY) != 1448097792) {
                    return ImageHeaderParser.ImageType.UNKNOWN;
                }
                int i = b3 & 255;
                if (i == 88) {
                    reader.a(4L);
                    short a4 = reader.a();
                    if ((a4 & 2) != 0) {
                        return ImageHeaderParser.ImageType.ANIMATED_WEBP;
                    }
                    if ((a4 & 16) != 0) {
                        return ImageHeaderParser.ImageType.WEBP_A;
                    }
                    return ImageHeaderParser.ImageType.WEBP;
                } else if (i != 76) {
                    return ImageHeaderParser.ImageType.WEBP;
                } else {
                    reader.a(4L);
                    return (reader.a() & 8) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
                }
            }
        } catch (Reader.EndOfFileException unused2) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
    }

    private boolean a(Reader reader, int i) throws IOException {
        if (((reader.b() << 16) | reader.b()) != 1718909296) {
            return false;
        }
        int b2 = (reader.b() << 16) | reader.b();
        if (b2 == 1635150182 || b2 == 1635150195) {
            return true;
        }
        reader.a(4L);
        int i2 = i - 16;
        if (i2 % 4 != 0) {
            return false;
        }
        int i3 = 0;
        for (int i4 = i2; i3 < 5 && i4 > 0; i4 -= 4) {
            int b3 = (reader.b() << 16) | reader.b();
            if (b3 == 1635150182 || b3 == 1635150195) {
                return true;
            }
            i3++;
        }
        return false;
    }

    private int a(Reader reader, ArrayPool arrayPool) throws IOException {
        try {
            int b2 = reader.b();
            if (!a(b2)) {
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Parser doesn't handle magic number: " + b2);
                }
                return -1;
            }
            int b3 = b(reader);
            if (b3 == -1) {
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Failed to parse exif segment length, or exif segment not found");
                }
                return -1;
            }
            byte[] bArr = (byte[]) arrayPool.get(b3, byte[].class);
            int a2 = a(reader, bArr, b3);
            arrayPool.put(bArr);
            return a2;
        } catch (Reader.EndOfFileException unused) {
            return -1;
        }
    }

    private int a(Reader reader, byte[] bArr, int i) throws IOException {
        int a2 = reader.a(bArr, i);
        if (a2 != i) {
            if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                Log.d("DfltImageHeaderParser", "Unable to read exif segment data, length: " + i + ", actually read: " + a2);
            }
            return -1;
        } else if (a(bArr, i)) {
            return a(new b(bArr, i));
        } else {
            if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                Log.d("DfltImageHeaderParser", "Missing jpeg exif preamble");
            }
            return -1;
        }
    }

    private boolean a(byte[] bArr, int i) {
        boolean z = bArr != null && i > a.length;
        if (!z) {
            return z;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = a;
            if (i2 >= bArr2.length) {
                return z;
            }
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private int b(Reader reader) throws IOException {
        short a2;
        int b2;
        long j;
        long a3;
        do {
            short a4 = reader.a();
            if (a4 != 255) {
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Unknown segmentId=" + ((int) a4));
                }
                return -1;
            }
            a2 = reader.a();
            if (a2 == 218) {
                return -1;
            }
            if (a2 == 217) {
                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            b2 = reader.b() - 2;
            if (a2 == 225) {
                return b2;
            }
            j = b2;
            a3 = reader.a(j);
        } while (a3 == j);
        if (Log.isLoggable("DfltImageHeaderParser", 3)) {
            Log.d("DfltImageHeaderParser", "Unable to skip enough data, type: " + ((int) a2) + ", wanted to skip: " + b2 + ", but actually skipped: " + a3);
        }
        return -1;
    }

    private static int a(b bVar) {
        ByteOrder byteOrder;
        short b2 = bVar.b(6);
        if (b2 == 18761) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else if (b2 != 19789) {
            if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                Log.d("DfltImageHeaderParser", "Unknown endianness = " + ((int) b2));
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else {
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        bVar.a(byteOrder);
        int a2 = bVar.a(10) + 6;
        short b3 = bVar.b(a2);
        for (int i = 0; i < b3; i++) {
            int a3 = a(a2, i);
            short b4 = bVar.b(a3);
            if (b4 == 274) {
                short b5 = bVar.b(a3 + 2);
                if (b5 >= 1 && b5 <= 12) {
                    int a4 = bVar.a(a3 + 4);
                    if (a4 >= 0) {
                        if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                            Log.d("DfltImageHeaderParser", "Got tagIndex=" + i + " tagType=" + ((int) b4) + " formatCode=" + ((int) b5) + " componentCount=" + a4);
                        }
                        int i2 = a4 + b[b5];
                        if (i2 <= 4) {
                            int i3 = a3 + 8;
                            if (i3 < 0 || i3 > bVar.a()) {
                                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                                    Log.d("DfltImageHeaderParser", "Illegal tagValueOffset=" + i3 + " tagType=" + ((int) b4));
                                }
                            } else if (i2 >= 0 && i2 + i3 <= bVar.a()) {
                                return bVar.b(i3);
                            } else {
                                if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                                    Log.d("DfltImageHeaderParser", "Illegal number of bytes for TI tag data tagType=" + ((int) b4));
                                }
                            }
                        } else if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                            Log.d("DfltImageHeaderParser", "Got byte count > 4, not orientation, continuing, formatCode=" + ((int) b5));
                        }
                    } else if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                        Log.d("DfltImageHeaderParser", "Negative tiff component count");
                    }
                } else if (Log.isLoggable("DfltImageHeaderParser", 3)) {
                    Log.d("DfltImageHeaderParser", "Got invalid format code = " + ((int) b5));
                }
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b {
        private final ByteBuffer a;

        b(byte[] bArr, int i) {
            this.a = (ByteBuffer) ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).limit(i);
        }

        void a(ByteOrder byteOrder) {
            this.a.order(byteOrder);
        }

        int a() {
            return this.a.remaining();
        }

        int a(int i) {
            if (a(i, 4)) {
                return this.a.getInt(i);
            }
            return -1;
        }

        short b(int i) {
            if (a(i, 2)) {
                return this.a.getShort(i);
            }
            return (short) -1;
        }

        private boolean a(int i, int i2) {
            return this.a.remaining() - i >= i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface Reader {
        int a(byte[] bArr, int i) throws IOException;

        long a(long j) throws IOException;

        short a() throws IOException;

        int b() throws IOException;

        /* loaded from: classes.dex */
        public static final class EndOfFileException extends IOException {
            private static final long serialVersionUID = 1;

            EndOfFileException() {
                super("Unexpectedly reached end of a file");
            }
        }
    }

    /* loaded from: classes.dex */
    private static final class a implements Reader {
        private final ByteBuffer a;

        a(ByteBuffer byteBuffer) {
            this.a = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser.Reader
        public short a() throws Reader.EndOfFileException {
            if (this.a.remaining() >= 1) {
                return (short) (this.a.get() & 255);
            }
            throw new Reader.EndOfFileException();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser.Reader
        public int b() throws Reader.EndOfFileException {
            return (a() << 8) | a();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser.Reader
        public int a(byte[] bArr, int i) {
            int min = Math.min(i, this.a.remaining());
            if (min == 0) {
                return -1;
            }
            this.a.get(bArr, 0, min);
            return min;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser.Reader
        public long a(long j) {
            int min = (int) Math.min(this.a.remaining(), j);
            ByteBuffer byteBuffer = this.a;
            byteBuffer.position(byteBuffer.position() + min);
            return min;
        }
    }

    /* loaded from: classes.dex */
    private static final class c implements Reader {
        private final InputStream a;

        c(InputStream inputStream) {
            this.a = inputStream;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser.Reader
        public short a() throws IOException {
            int read = this.a.read();
            if (read != -1) {
                return (short) read;
            }
            throw new Reader.EndOfFileException();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser.Reader
        public int b() throws IOException {
            return (a() << 8) | a();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser.Reader
        public int a(byte[] bArr, int i) throws IOException {
            int i2 = 0;
            int i3 = 0;
            while (i2 < i && (i3 = this.a.read(bArr, i2, i - i2)) != -1) {
                i2 += i3;
            }
            if (i2 != 0 || i3 != -1) {
                return i2;
            }
            throw new Reader.EndOfFileException();
        }

        @Override // com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser.Reader
        public long a(long j) throws IOException {
            if (j < 0) {
                return 0L;
            }
            long j2 = j;
            while (j2 > 0) {
                long skip = this.a.skip(j2);
                if (skip > 0) {
                    j2 -= skip;
                } else if (this.a.read() == -1) {
                    break;
                } else {
                    j2--;
                }
            }
            return j - j2;
        }
    }
}
