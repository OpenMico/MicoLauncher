package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.bumptech.glide.gifdecoder.StandardGifDecoder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

/* loaded from: classes.dex */
public class ByteBufferGifDecoder implements ResourceDecoder<ByteBuffer, GifDrawable> {
    private static final a a = new a();
    private static final b b = new b();
    private final Context c;
    private final List<ImageHeaderParser> d;
    private final b e;
    private final a f;
    private final GifBitmapProvider g;

    public ByteBufferGifDecoder(Context context) {
        this(context, Glide.get(context).getRegistry().getImageHeaderParsers(), Glide.get(context).getBitmapPool(), Glide.get(context).getArrayPool());
    }

    public ByteBufferGifDecoder(Context context, List<ImageHeaderParser> list, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this(context, list, bitmapPool, arrayPool, b, a);
    }

    @VisibleForTesting
    ByteBufferGifDecoder(Context context, List<ImageHeaderParser> list, BitmapPool bitmapPool, ArrayPool arrayPool, b bVar, a aVar) {
        this.c = context.getApplicationContext();
        this.d = list;
        this.f = aVar;
        this.g = new GifBitmapProvider(bitmapPool, arrayPool);
        this.e = bVar;
    }

    public boolean handles(@NonNull ByteBuffer byteBuffer, @NonNull Options options) throws IOException {
        return !((Boolean) options.get(GifOptions.DISABLE_ANIMATION)).booleanValue() && ImageHeaderParserUtils.getType(this.d, byteBuffer) == ImageHeaderParser.ImageType.GIF;
    }

    public GifDrawableResource decode(@NonNull ByteBuffer byteBuffer, int i, int i2, @NonNull Options options) {
        GifHeaderParser a2 = this.e.a(byteBuffer);
        try {
            return a(byteBuffer, i, i2, a2, options);
        } finally {
            this.e.a(a2);
        }
    }

    @Nullable
    private GifDrawableResource a(ByteBuffer byteBuffer, int i, int i2, GifHeaderParser gifHeaderParser, Options options) {
        int i3;
        Bitmap.Config config;
        int i4;
        long logTime = LogTime.getLogTime();
        try {
            GifHeader parseHeader = gifHeaderParser.parseHeader();
            if (parseHeader.getNumFrames() > 0 && parseHeader.getStatus() == 0) {
                if (options.get(GifOptions.DECODE_FORMAT) == DecodeFormat.PREFER_RGB_565) {
                    i3 = i2;
                    config = Bitmap.Config.RGB_565;
                    i4 = i;
                } else {
                    i3 = i2;
                    config = Bitmap.Config.ARGB_8888;
                    i4 = i;
                }
                GifDecoder a2 = this.f.a(this.g, parseHeader, byteBuffer, a(parseHeader, i4, i3));
                a2.setDefaultBitmapConfig(config);
                a2.advance();
                Bitmap nextFrame = a2.getNextFrame();
                if (nextFrame == null) {
                    if (Log.isLoggable("BufferGifDecoder", 2)) {
                        Log.v("BufferGifDecoder", "Decoded GIF from stream in " + LogTime.getElapsedMillis(logTime));
                    }
                    return null;
                }
                GifDrawableResource gifDrawableResource = new GifDrawableResource(new GifDrawable(this.c, a2, UnitTransformation.get(), i, i2, nextFrame));
                if (Log.isLoggable("BufferGifDecoder", 2)) {
                    Log.v("BufferGifDecoder", "Decoded GIF from stream in " + LogTime.getElapsedMillis(logTime));
                }
                return gifDrawableResource;
            }
            if (Log.isLoggable("BufferGifDecoder", 2)) {
                Log.v("BufferGifDecoder", "Decoded GIF from stream in " + LogTime.getElapsedMillis(logTime));
            }
            return null;
        } catch (Throwable th) {
            if (Log.isLoggable("BufferGifDecoder", 2)) {
                Log.v("BufferGifDecoder", "Decoded GIF from stream in " + LogTime.getElapsedMillis(logTime));
            }
            throw th;
        }
    }

    private static int a(GifHeader gifHeader, int i, int i2) {
        int min = Math.min(gifHeader.getHeight() / i2, gifHeader.getWidth() / i);
        int max = Math.max(1, min == 0 ? 0 : Integer.highestOneBit(min));
        if (Log.isLoggable("BufferGifDecoder", 2) && max > 1) {
            Log.v("BufferGifDecoder", "Downsampling GIF, sampleSize: " + max + ", target dimens: [" + i + "x" + i2 + "], actual dimens: [" + gifHeader.getWidth() + "x" + gifHeader.getHeight() + "]");
        }
        return max;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class a {
        a() {
        }

        GifDecoder a(GifDecoder.BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer, int i) {
            return new StandardGifDecoder(bitmapProvider, gifHeader, byteBuffer, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class b {
        private final Queue<GifHeaderParser> a = Util.createQueue(0);

        b() {
        }

        synchronized GifHeaderParser a(ByteBuffer byteBuffer) {
            GifHeaderParser poll;
            poll = this.a.poll();
            if (poll == null) {
                poll = new GifHeaderParser();
            }
            return poll.setData(byteBuffer);
        }

        synchronized void a(GifHeaderParser gifHeaderParser) {
            gifHeaderParser.clear();
            this.a.offer(gifHeaderParser);
        }
    }
}
