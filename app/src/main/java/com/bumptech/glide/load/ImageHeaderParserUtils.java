package com.bumptech.glide.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: classes.dex */
public final class ImageHeaderParserUtils {

    /* loaded from: classes.dex */
    public interface a {
        int a(ImageHeaderParser imageHeaderParser) throws IOException;
    }

    /* loaded from: classes.dex */
    public interface b {
        ImageHeaderParser.ImageType a(ImageHeaderParser imageHeaderParser) throws IOException;
    }

    private ImageHeaderParserUtils() {
    }

    @NonNull
    public static ImageHeaderParser.ImageType getType(@NonNull List<ImageHeaderParser> list, @Nullable final InputStream inputStream, @NonNull ArrayPool arrayPool) throws IOException {
        if (inputStream == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        if (!inputStream.markSupported()) {
            inputStream = new RecyclableBufferedInputStream(inputStream, arrayPool);
        }
        inputStream.mark(5242880);
        return a(list, new b() { // from class: com.bumptech.glide.load.ImageHeaderParserUtils.1
            @Override // com.bumptech.glide.load.ImageHeaderParserUtils.b
            public ImageHeaderParser.ImageType a(ImageHeaderParser imageHeaderParser) throws IOException {
                try {
                    return imageHeaderParser.getType(inputStream);
                } finally {
                    inputStream.reset();
                }
            }
        });
    }

    @NonNull
    public static ImageHeaderParser.ImageType getType(@NonNull List<ImageHeaderParser> list, @Nullable final ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer == null) {
            return ImageHeaderParser.ImageType.UNKNOWN;
        }
        return a(list, new b() { // from class: com.bumptech.glide.load.ImageHeaderParserUtils.2
            @Override // com.bumptech.glide.load.ImageHeaderParserUtils.b
            public ImageHeaderParser.ImageType a(ImageHeaderParser imageHeaderParser) throws IOException {
                return imageHeaderParser.getType(byteBuffer);
            }
        });
    }

    @NonNull
    @RequiresApi(21)
    public static ImageHeaderParser.ImageType getType(@NonNull List<ImageHeaderParser> list, @NonNull final ParcelFileDescriptorRewinder parcelFileDescriptorRewinder, @NonNull final ArrayPool arrayPool) throws IOException {
        return a(list, new b() { // from class: com.bumptech.glide.load.ImageHeaderParserUtils.3
            @Override // com.bumptech.glide.load.ImageHeaderParserUtils.b
            public ImageHeaderParser.ImageType a(ImageHeaderParser imageHeaderParser) throws IOException {
                Throwable th;
                RecyclableBufferedInputStream recyclableBufferedInputStream = null;
                try {
                    RecyclableBufferedInputStream recyclableBufferedInputStream2 = new RecyclableBufferedInputStream(new FileInputStream(parcelFileDescriptorRewinder.rewindAndGet().getFileDescriptor()), arrayPool);
                    try {
                        ImageHeaderParser.ImageType type = imageHeaderParser.getType(recyclableBufferedInputStream2);
                        try {
                            recyclableBufferedInputStream2.close();
                        } catch (IOException unused) {
                        }
                        parcelFileDescriptorRewinder.rewindAndGet();
                        return type;
                    } catch (Throwable th2) {
                        th = th2;
                        recyclableBufferedInputStream = recyclableBufferedInputStream2;
                        if (recyclableBufferedInputStream != null) {
                            try {
                                recyclableBufferedInputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        parcelFileDescriptorRewinder.rewindAndGet();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
        });
    }

    @NonNull
    private static ImageHeaderParser.ImageType a(@NonNull List<ImageHeaderParser> list, b bVar) throws IOException {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ImageHeaderParser.ImageType a2 = bVar.a(list.get(i));
            if (a2 != ImageHeaderParser.ImageType.UNKNOWN) {
                return a2;
            }
        }
        return ImageHeaderParser.ImageType.UNKNOWN;
    }

    public static int getOrientation(@NonNull List<ImageHeaderParser> list, @Nullable final ByteBuffer byteBuffer, @NonNull final ArrayPool arrayPool) throws IOException {
        if (byteBuffer == null) {
            return -1;
        }
        return a(list, new a() { // from class: com.bumptech.glide.load.ImageHeaderParserUtils.4
            @Override // com.bumptech.glide.load.ImageHeaderParserUtils.a
            public int a(ImageHeaderParser imageHeaderParser) throws IOException {
                return imageHeaderParser.getOrientation(byteBuffer, arrayPool);
            }
        });
    }

    public static int getOrientation(@NonNull List<ImageHeaderParser> list, @Nullable final InputStream inputStream, @NonNull final ArrayPool arrayPool) throws IOException {
        if (inputStream == null) {
            return -1;
        }
        if (!inputStream.markSupported()) {
            inputStream = new RecyclableBufferedInputStream(inputStream, arrayPool);
        }
        inputStream.mark(5242880);
        return a(list, new a() { // from class: com.bumptech.glide.load.ImageHeaderParserUtils.5
            @Override // com.bumptech.glide.load.ImageHeaderParserUtils.a
            public int a(ImageHeaderParser imageHeaderParser) throws IOException {
                try {
                    return imageHeaderParser.getOrientation(inputStream, arrayPool);
                } finally {
                    inputStream.reset();
                }
            }
        });
    }

    @RequiresApi(21)
    public static int getOrientation(@NonNull List<ImageHeaderParser> list, @NonNull final ParcelFileDescriptorRewinder parcelFileDescriptorRewinder, @NonNull final ArrayPool arrayPool) throws IOException {
        return a(list, new a() { // from class: com.bumptech.glide.load.ImageHeaderParserUtils.6
            @Override // com.bumptech.glide.load.ImageHeaderParserUtils.a
            public int a(ImageHeaderParser imageHeaderParser) throws IOException {
                Throwable th;
                RecyclableBufferedInputStream recyclableBufferedInputStream = null;
                try {
                    RecyclableBufferedInputStream recyclableBufferedInputStream2 = new RecyclableBufferedInputStream(new FileInputStream(parcelFileDescriptorRewinder.rewindAndGet().getFileDescriptor()), arrayPool);
                    try {
                        int orientation = imageHeaderParser.getOrientation(recyclableBufferedInputStream2, arrayPool);
                        try {
                            recyclableBufferedInputStream2.close();
                        } catch (IOException unused) {
                        }
                        parcelFileDescriptorRewinder.rewindAndGet();
                        return orientation;
                    } catch (Throwable th2) {
                        th = th2;
                        recyclableBufferedInputStream = recyclableBufferedInputStream2;
                        if (recyclableBufferedInputStream != null) {
                            try {
                                recyclableBufferedInputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        parcelFileDescriptorRewinder.rewindAndGet();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
        });
    }

    private static int a(@NonNull List<ImageHeaderParser> list, a aVar) throws IOException {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int a2 = aVar.a(list.get(i));
            if (a2 != -1) {
                return a2;
            }
        }
        return -1;
    }
}
