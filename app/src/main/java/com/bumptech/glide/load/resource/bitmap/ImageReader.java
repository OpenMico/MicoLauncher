package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.data.InputStreamRewinder;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.util.ByteBufferUtil;
import com.bumptech.glide.util.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public interface ImageReader {
    @Nullable
    Bitmap decodeBitmap(BitmapFactory.Options options) throws IOException;

    int getImageOrientation() throws IOException;

    ImageHeaderParser.ImageType getImageType() throws IOException;

    void stopGrowingBuffers();

    /* loaded from: classes.dex */
    public static final class ByteArrayReader implements ImageReader {
        private final byte[] a;
        private final List<ImageHeaderParser> b;
        private final ArrayPool c;

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public void stopGrowingBuffers() {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        @Nullable
        public Bitmap decodeBitmap(BitmapFactory.Options options) {
            byte[] bArr = this.a;
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public ImageHeaderParser.ImageType getImageType() throws IOException {
            return ImageHeaderParserUtils.getType(this.b, ByteBuffer.wrap(this.a));
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public int getImageOrientation() throws IOException {
            return ImageHeaderParserUtils.getOrientation(this.b, ByteBuffer.wrap(this.a), this.c);
        }
    }

    /* loaded from: classes.dex */
    public static final class FileReader implements ImageReader {
        private final File a;
        private final List<ImageHeaderParser> b;
        private final ArrayPool c;

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public void stopGrowingBuffers() {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        @Nullable
        public Bitmap decodeBitmap(BitmapFactory.Options options) throws FileNotFoundException {
            Throwable th;
            RecyclableBufferedInputStream recyclableBufferedInputStream = null;
            try {
                RecyclableBufferedInputStream recyclableBufferedInputStream2 = new RecyclableBufferedInputStream(new FileInputStream(this.a), this.c);
                try {
                    Bitmap decodeStream = BitmapFactory.decodeStream(recyclableBufferedInputStream2, null, options);
                    try {
                        recyclableBufferedInputStream2.close();
                    } catch (IOException unused) {
                    }
                    return decodeStream;
                } catch (Throwable th2) {
                    th = th2;
                    recyclableBufferedInputStream = recyclableBufferedInputStream2;
                    if (recyclableBufferedInputStream != null) {
                        try {
                            recyclableBufferedInputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public ImageHeaderParser.ImageType getImageType() throws IOException {
            Throwable th;
            RecyclableBufferedInputStream recyclableBufferedInputStream;
            RecyclableBufferedInputStream recyclableBufferedInputStream2 = null;
            try {
                recyclableBufferedInputStream = new RecyclableBufferedInputStream(new FileInputStream(this.a), this.c);
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                ImageHeaderParser.ImageType type = ImageHeaderParserUtils.getType(this.b, recyclableBufferedInputStream, this.c);
                try {
                    recyclableBufferedInputStream.close();
                } catch (IOException unused) {
                }
                return type;
            } catch (Throwable th3) {
                th = th3;
                recyclableBufferedInputStream2 = recyclableBufferedInputStream;
                if (recyclableBufferedInputStream2 != null) {
                    try {
                        recyclableBufferedInputStream2.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public int getImageOrientation() throws IOException {
            Throwable th;
            RecyclableBufferedInputStream recyclableBufferedInputStream;
            RecyclableBufferedInputStream recyclableBufferedInputStream2 = null;
            try {
                recyclableBufferedInputStream = new RecyclableBufferedInputStream(new FileInputStream(this.a), this.c);
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                int orientation = ImageHeaderParserUtils.getOrientation(this.b, recyclableBufferedInputStream, this.c);
                try {
                    recyclableBufferedInputStream.close();
                } catch (IOException unused) {
                }
                return orientation;
            } catch (Throwable th3) {
                th = th3;
                recyclableBufferedInputStream2 = recyclableBufferedInputStream;
                if (recyclableBufferedInputStream2 != null) {
                    try {
                        recyclableBufferedInputStream2.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class ByteBufferReader implements ImageReader {
        private final ByteBuffer a;
        private final List<ImageHeaderParser> b;
        private final ArrayPool c;

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public void stopGrowingBuffers() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ByteBufferReader(ByteBuffer byteBuffer, List<ImageHeaderParser> list, ArrayPool arrayPool) {
            this.a = byteBuffer;
            this.b = list;
            this.c = arrayPool;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        @Nullable
        public Bitmap decodeBitmap(BitmapFactory.Options options) {
            return BitmapFactory.decodeStream(a(), null, options);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public ImageHeaderParser.ImageType getImageType() throws IOException {
            return ImageHeaderParserUtils.getType(this.b, ByteBufferUtil.rewind(this.a));
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public int getImageOrientation() throws IOException {
            return ImageHeaderParserUtils.getOrientation(this.b, ByteBufferUtil.rewind(this.a), this.c);
        }

        private InputStream a() {
            return ByteBufferUtil.toStream(ByteBufferUtil.rewind(this.a));
        }
    }

    /* loaded from: classes.dex */
    public static final class InputStreamImageReader implements ImageReader {
        private final InputStreamRewinder a;
        private final ArrayPool b;
        private final List<ImageHeaderParser> c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public InputStreamImageReader(InputStream inputStream, List<ImageHeaderParser> list, ArrayPool arrayPool) {
            this.b = (ArrayPool) Preconditions.checkNotNull(arrayPool);
            this.c = (List) Preconditions.checkNotNull(list);
            this.a = new InputStreamRewinder(inputStream, arrayPool);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        @Nullable
        public Bitmap decodeBitmap(BitmapFactory.Options options) throws IOException {
            return BitmapFactory.decodeStream(this.a.rewindAndGet(), null, options);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public ImageHeaderParser.ImageType getImageType() throws IOException {
            return ImageHeaderParserUtils.getType(this.c, this.a.rewindAndGet(), this.b);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public int getImageOrientation() throws IOException {
            return ImageHeaderParserUtils.getOrientation(this.c, this.a.rewindAndGet(), this.b);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public void stopGrowingBuffers() {
            this.a.fixMarkLimits();
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static final class ParcelFileDescriptorImageReader implements ImageReader {
        private final ArrayPool a;
        private final List<ImageHeaderParser> b;
        private final ParcelFileDescriptorRewinder c;

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public void stopGrowingBuffers() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ParcelFileDescriptorImageReader(ParcelFileDescriptor parcelFileDescriptor, List<ImageHeaderParser> list, ArrayPool arrayPool) {
            this.a = (ArrayPool) Preconditions.checkNotNull(arrayPool);
            this.b = (List) Preconditions.checkNotNull(list);
            this.c = new ParcelFileDescriptorRewinder(parcelFileDescriptor);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        @Nullable
        public Bitmap decodeBitmap(BitmapFactory.Options options) throws IOException {
            return BitmapFactory.decodeFileDescriptor(this.c.rewindAndGet().getFileDescriptor(), null, options);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public ImageHeaderParser.ImageType getImageType() throws IOException {
            return ImageHeaderParserUtils.getType(this.b, this.c, this.a);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.ImageReader
        public int getImageOrientation() throws IOException {
            return ImageHeaderParserUtils.getOrientation(this.b, this.c, this.a);
        }
    }
}
