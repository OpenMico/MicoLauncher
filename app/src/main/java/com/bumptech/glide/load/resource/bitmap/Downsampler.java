package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.PreferredColorSpace;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.bitmap.ImageReader;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/* loaded from: classes.dex */
public final class Downsampler {
    private final BitmapPool e;
    private final DisplayMetrics f;
    private final ArrayPool g;
    private final List<ImageHeaderParser> h;
    private final HardwareConfigState i = HardwareConfigState.getInstance();
    public static final Option<DecodeFormat> DECODE_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.DEFAULT);
    public static final Option<PreferredColorSpace> PREFERRED_COLOR_SPACE = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.PreferredColorSpace");
    @Deprecated
    public static final Option<DownsampleStrategy> DOWNSAMPLE_STRATEGY = DownsampleStrategy.OPTION;
    public static final Option<Boolean> FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", false);
    public static final Option<Boolean> ALLOW_HARDWARE_CONFIG = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode", false);
    private static final Set<String> a = Collections.unmodifiableSet(new HashSet(Arrays.asList("image/vnd.wap.wbmp", "image/x-ico")));
    private static final DecodeCallbacks b = new DecodeCallbacks() { // from class: com.bumptech.glide.load.resource.bitmap.Downsampler.1
        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
        public void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
        public void onObtainBounds() {
        }
    };
    private static final Set<ImageHeaderParser.ImageType> c = Collections.unmodifiableSet(EnumSet.of(ImageHeaderParser.ImageType.JPEG, ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG));
    private static final Queue<BitmapFactory.Options> d = Util.createQueue(0);

    /* loaded from: classes.dex */
    public interface DecodeCallbacks {
        void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) throws IOException;

        void onObtainBounds();
    }

    private static boolean a(int i) {
        return i == 90 || i == 270;
    }

    private static int c(double d2) {
        return (int) (d2 + 0.5d);
    }

    public boolean handles(InputStream inputStream) {
        return true;
    }

    public boolean handles(ByteBuffer byteBuffer) {
        return true;
    }

    public Downsampler(List<ImageHeaderParser> list, DisplayMetrics displayMetrics, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this.h = list;
        this.f = (DisplayMetrics) Preconditions.checkNotNull(displayMetrics);
        this.e = (BitmapPool) Preconditions.checkNotNull(bitmapPool);
        this.g = (ArrayPool) Preconditions.checkNotNull(arrayPool);
    }

    public boolean handles(ParcelFileDescriptor parcelFileDescriptor) {
        return ParcelFileDescriptorRewinder.isSupported();
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, Options options) throws IOException {
        return decode(inputStream, i, i2, options, b);
    }

    public Resource<Bitmap> decode(ByteBuffer byteBuffer, int i, int i2, Options options) throws IOException {
        return a(new ImageReader.ByteBufferReader(byteBuffer, this.h, this.g), i, i2, options, b);
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        return a(new ImageReader.InputStreamImageReader(inputStream, this.h, this.g), i, i2, options, decodeCallbacks);
    }

    @RequiresApi(21)
    public Resource<Bitmap> decode(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, Options options) throws IOException {
        return a(new ImageReader.ParcelFileDescriptorImageReader(parcelFileDescriptor, this.h, this.g), i, i2, options, b);
    }

    private Resource<Bitmap> a(ImageReader imageReader, int i, int i2, Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        byte[] bArr = (byte[]) this.g.get(65536, byte[].class);
        BitmapFactory.Options a2 = a();
        a2.inTempStorage = bArr;
        DecodeFormat decodeFormat = (DecodeFormat) options.get(DECODE_FORMAT);
        PreferredColorSpace preferredColorSpace = (PreferredColorSpace) options.get(PREFERRED_COLOR_SPACE);
        try {
            return BitmapResource.obtain(a(imageReader, a2, (DownsampleStrategy) options.get(DownsampleStrategy.OPTION), decodeFormat, preferredColorSpace, options.get(ALLOW_HARDWARE_CONFIG) != null && ((Boolean) options.get(ALLOW_HARDWARE_CONFIG)).booleanValue(), i, i2, ((Boolean) options.get(FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS)).booleanValue(), decodeCallbacks), this.e);
        } finally {
            c(a2);
            this.g.put(bArr);
        }
    }

    private Bitmap a(ImageReader imageReader, BitmapFactory.Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, PreferredColorSpace preferredColorSpace, boolean z, int i, int i2, boolean z2, DecodeCallbacks decodeCallbacks) throws IOException {
        int i3;
        int i4;
        int i5;
        Downsampler downsampler;
        int i6;
        int i7;
        long logTime = LogTime.getLogTime();
        int[] a2 = a(imageReader, options, decodeCallbacks, this.e);
        boolean z3 = false;
        int i8 = a2[0];
        int i9 = a2[1];
        String str = options.outMimeType;
        boolean z4 = (i8 == -1 || i9 == -1) ? false : z;
        int imageOrientation = imageReader.getImageOrientation();
        int exifOrientationDegrees = TransformationUtils.getExifOrientationDegrees(imageOrientation);
        boolean isExifOrientationRequired = TransformationUtils.isExifOrientationRequired(imageOrientation);
        if (i != Integer.MIN_VALUE) {
            i4 = i2;
            i3 = i;
        } else if (a(exifOrientationDegrees)) {
            i4 = i2;
            i3 = i9;
        } else {
            i4 = i2;
            i3 = i8;
        }
        if (i4 == Integer.MIN_VALUE) {
            i5 = a(exifOrientationDegrees) ? i8 : i9;
        } else {
            i5 = i4;
        }
        ImageHeaderParser.ImageType imageType = imageReader.getImageType();
        a(imageType, imageReader, decodeCallbacks, this.e, downsampleStrategy, exifOrientationDegrees, i8, i9, i3, i5, options);
        a(imageReader, decodeFormat, z4, isExifOrientationRequired, options, i3, i5);
        boolean z5 = Build.VERSION.SDK_INT >= 19;
        if (options.inSampleSize == 1 || z5) {
            downsampler = this;
            if (downsampler.a(imageType)) {
                if (i8 < 0 || i9 < 0 || !z2 || !z5) {
                    float f = a(options) ? options.inTargetDensity / options.inDensity : 1.0f;
                    int i10 = options.inSampleSize;
                    float f2 = i10;
                    i7 = Math.round(((int) Math.ceil(i8 / f2)) * f);
                    i6 = Math.round(((int) Math.ceil(i9 / f2)) * f);
                    if (Log.isLoggable("Downsampler", 2)) {
                        Log.v("Downsampler", "Calculated target [" + i7 + "x" + i6 + "] for source [" + i8 + "x" + i9 + "], sampleSize: " + i10 + ", targetDensity: " + options.inTargetDensity + ", density: " + options.inDensity + ", density multiplier: " + f);
                    }
                } else {
                    i7 = i3;
                    i6 = i5;
                }
                if (i7 > 0 && i6 > 0) {
                    a(options, downsampler.e, i7, i6);
                }
            }
        } else {
            downsampler = this;
        }
        if (preferredColorSpace != null) {
            if (Build.VERSION.SDK_INT >= 28) {
                if (preferredColorSpace == PreferredColorSpace.DISPLAY_P3 && options.outColorSpace != null && options.outColorSpace.isWideGamut()) {
                    z3 = true;
                }
                options.inPreferredColorSpace = ColorSpace.get(z3 ? ColorSpace.Named.DISPLAY_P3 : ColorSpace.Named.SRGB);
            } else if (Build.VERSION.SDK_INT >= 26) {
                options.inPreferredColorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
            }
        }
        Bitmap b2 = b(imageReader, options, decodeCallbacks, downsampler.e);
        decodeCallbacks.onDecodeComplete(downsampler.e, b2);
        if (Log.isLoggable("Downsampler", 2)) {
            a(i8, i9, str, options, b2, i, i2, logTime);
        }
        Bitmap bitmap = null;
        if (b2 != null) {
            b2.setDensity(downsampler.f.densityDpi);
            bitmap = TransformationUtils.rotateImageExif(downsampler.e, b2, imageOrientation);
            if (!b2.equals(bitmap)) {
                downsampler.e.put(b2);
            }
        }
        return bitmap;
    }

    private static void a(ImageHeaderParser.ImageType imageType, ImageReader imageReader, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool, DownsampleStrategy downsampleStrategy, int i, int i2, int i3, int i4, int i5, BitmapFactory.Options options) throws IOException {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        if (i2 > 0 && i3 > 0) {
            if (a(i)) {
                i6 = i2;
                i7 = i3;
            } else {
                i7 = i2;
                i6 = i3;
            }
            float scaleFactor = downsampleStrategy.getScaleFactor(i7, i6, i4, i5);
            if (scaleFactor > 0.0f) {
                DownsampleStrategy.SampleSizeRounding sampleSizeRounding = downsampleStrategy.getSampleSizeRounding(i7, i6, i4, i5);
                if (sampleSizeRounding != null) {
                    float f = i7;
                    float f2 = i6;
                    int c2 = i7 / c(scaleFactor * f);
                    int c3 = i6 / c(scaleFactor * f2);
                    if (sampleSizeRounding == DownsampleStrategy.SampleSizeRounding.MEMORY) {
                        i8 = Math.max(c2, c3);
                    } else {
                        i8 = Math.min(c2, c3);
                    }
                    if (Build.VERSION.SDK_INT > 23 || !a.contains(options.outMimeType)) {
                        int max = Math.max(1, Integer.highestOneBit(i8));
                        i9 = (sampleSizeRounding != DownsampleStrategy.SampleSizeRounding.MEMORY || ((float) max) >= 1.0f / scaleFactor) ? max : max << 1;
                    } else {
                        i9 = 1;
                    }
                    options.inSampleSize = i9;
                    if (imageType == ImageHeaderParser.ImageType.JPEG) {
                        float min = Math.min(i9, 8);
                        i10 = (int) Math.ceil(f / min);
                        i11 = (int) Math.ceil(f2 / min);
                        int i12 = i9 / 8;
                        if (i12 > 0) {
                            i10 /= i12;
                            i11 /= i12;
                        }
                    } else if (imageType == ImageHeaderParser.ImageType.PNG || imageType == ImageHeaderParser.ImageType.PNG_A) {
                        float f3 = i9;
                        i10 = (int) Math.floor(f / f3);
                        i11 = (int) Math.floor(f2 / f3);
                    } else if (imageType.isWebp()) {
                        if (Build.VERSION.SDK_INT >= 24) {
                            float f4 = i9;
                            i10 = Math.round(f / f4);
                            i11 = Math.round(f2 / f4);
                        } else {
                            float f5 = i9;
                            i10 = (int) Math.floor(f / f5);
                            i11 = (int) Math.floor(f2 / f5);
                        }
                    } else if (i7 % i9 == 0 && i6 % i9 == 0) {
                        i10 = i7 / i9;
                        i11 = i6 / i9;
                    } else {
                        int[] a2 = a(imageReader, options, decodeCallbacks, bitmapPool);
                        i10 = a2[0];
                        i11 = a2[1];
                    }
                    double scaleFactor2 = downsampleStrategy.getScaleFactor(i10, i11, i4, i5);
                    if (Build.VERSION.SDK_INT >= 19) {
                        options.inTargetDensity = a(scaleFactor2);
                        options.inDensity = b(scaleFactor2);
                    }
                    if (a(options)) {
                        options.inScaled = true;
                    } else {
                        options.inTargetDensity = 0;
                        options.inDensity = 0;
                    }
                    if (Log.isLoggable("Downsampler", 2)) {
                        Log.v("Downsampler", "Calculate scaling, source: [" + i2 + "x" + i3 + "], degreesToRotate: " + i + ", target: [" + i4 + "x" + i5 + "], power of two scaled: [" + i10 + "x" + i11 + "], exact scale factor: " + scaleFactor + ", power of 2 sample size: " + i9 + ", adjusted scale factor: " + scaleFactor2 + ", target density: " + options.inTargetDensity + ", density: " + options.inDensity);
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException("Cannot round with null rounding");
            }
            throw new IllegalArgumentException("Cannot scale with factor: " + scaleFactor + " from: " + downsampleStrategy + ", source: [" + i2 + "x" + i3 + "], target: [" + i4 + "x" + i5 + "]");
        } else if (Log.isLoggable("Downsampler", 3)) {
            Log.d("Downsampler", "Unable to determine dimensions for: " + imageType + " with target [" + i4 + "x" + i5 + "]");
        }
    }

    private static int a(double d2) {
        int b2 = b(d2);
        int c2 = c(b2 * d2);
        return c((d2 / (c2 / b2)) * c2);
    }

    private static int b(double d2) {
        if (d2 > 1.0d) {
            d2 = 1.0d / d2;
        }
        return (int) Math.round(d2 * 2.147483647E9d);
    }

    private boolean a(ImageHeaderParser.ImageType imageType) {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return c.contains(imageType);
    }

    private void a(ImageReader imageReader, DecodeFormat decodeFormat, boolean z, boolean z2, BitmapFactory.Options options, int i, int i2) {
        if (!this.i.a(i, i2, options, z, z2)) {
            if (decodeFormat == DecodeFormat.PREFER_ARGB_8888 || Build.VERSION.SDK_INT == 16) {
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return;
            }
            boolean z3 = false;
            try {
                z3 = imageReader.getImageType().hasAlpha();
            } catch (IOException e) {
                if (Log.isLoggable("Downsampler", 3)) {
                    Log.d("Downsampler", "Cannot determine whether the image has alpha or not from header, format " + decodeFormat, e);
                }
            }
            options.inPreferredConfig = z3 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            if (options.inPreferredConfig == Bitmap.Config.RGB_565) {
                options.inDither = true;
            }
        }
    }

    private static int[] a(ImageReader imageReader, BitmapFactory.Options options, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool) throws IOException {
        options.inJustDecodeBounds = true;
        b(imageReader, options, decodeCallbacks, bitmapPool);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static Bitmap b(ImageReader imageReader, BitmapFactory.Options options, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool) throws IOException {
        if (!options.inJustDecodeBounds) {
            decodeCallbacks.onObtainBounds();
            imageReader.stopGrowingBuffers();
        }
        try {
            int i = options.outWidth;
            int i2 = options.outHeight;
            String str = options.outMimeType;
            TransformationUtils.getBitmapDrawableLock().lock();
            try {
                Bitmap decodeBitmap = imageReader.decodeBitmap(options);
                TransformationUtils.getBitmapDrawableLock().unlock();
                return decodeBitmap;
            } catch (IllegalArgumentException e) {
                IOException a2 = a(e, i, i2, str, options);
                if (Log.isLoggable("Downsampler", 3)) {
                    Log.d("Downsampler", "Failed to decode with inBitmap, trying again without Bitmap re-use", a2);
                }
                if (options.inBitmap != null) {
                    try {
                        bitmapPool.put(options.inBitmap);
                        options.inBitmap = null;
                        Bitmap b2 = b(imageReader, options, decodeCallbacks, bitmapPool);
                        TransformationUtils.getBitmapDrawableLock().unlock();
                        return b2;
                    } catch (IOException unused) {
                        throw a2;
                    }
                } else {
                    throw a2;
                }
            }
        } catch (Throwable th) {
            TransformationUtils.getBitmapDrawableLock().unlock();
            throw th;
        }
    }

    private static boolean a(BitmapFactory.Options options) {
        return options.inTargetDensity > 0 && options.inDensity > 0 && options.inTargetDensity != options.inDensity;
    }

    private static void a(int i, int i2, String str, BitmapFactory.Options options, Bitmap bitmap, int i3, int i4, long j) {
        Log.v("Downsampler", "Decoded " + a(bitmap) + " from [" + i + "x" + i2 + "] " + str + " with inBitmap " + b(options) + " for [" + i3 + "x" + i4 + "], sample size: " + options.inSampleSize + ", density: " + options.inDensity + ", target density: " + options.inTargetDensity + ", thread: " + Thread.currentThread().getName() + ", duration: " + LogTime.getElapsedMillis(j));
    }

    private static String b(BitmapFactory.Options options) {
        return a(options.inBitmap);
    }

    @Nullable
    @TargetApi(19)
    private static String a(Bitmap bitmap) {
        String str;
        if (bitmap == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            str = " (" + bitmap.getAllocationByteCount() + ")";
        } else {
            str = "";
        }
        return "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig() + str;
    }

    private static IOException a(IllegalArgumentException illegalArgumentException, int i, int i2, String str, BitmapFactory.Options options) {
        return new IOException("Exception decoding bitmap, outWidth: " + i + ", outHeight: " + i2 + ", outMimeType: " + str + ", inBitmap: " + b(options), illegalArgumentException);
    }

    @TargetApi(26)
    private static void a(BitmapFactory.Options options, BitmapPool bitmapPool, int i, int i2) {
        Bitmap.Config config;
        if (Build.VERSION.SDK_INT < 26) {
            config = null;
        } else if (options.inPreferredConfig != Bitmap.Config.HARDWARE) {
            config = options.outConfig;
        } else {
            return;
        }
        if (config == null) {
            config = options.inPreferredConfig;
        }
        options.inBitmap = bitmapPool.getDirty(i, i2, config);
    }

    private static synchronized BitmapFactory.Options a() {
        BitmapFactory.Options poll;
        synchronized (Downsampler.class) {
            synchronized (d) {
                poll = d.poll();
            }
            if (poll == null) {
                poll = new BitmapFactory.Options();
                d(poll);
            }
        }
        return poll;
    }

    private static void c(BitmapFactory.Options options) {
        d(options);
        synchronized (d) {
            d.offer(options);
        }
    }

    private static void d(BitmapFactory.Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        if (Build.VERSION.SDK_INT >= 26) {
            options.inPreferredColorSpace = null;
            options.outColorSpace = null;
            options.outConfig = null;
        }
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        options.inBitmap = null;
        options.inMutable = true;
    }
}
