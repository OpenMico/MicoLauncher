package com.bumptech.glide.load.resource.drawable;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.resource.DefaultOnHeaderDecodedListener;
import com.bumptech.glide.util.ByteBufferUtil;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

@RequiresApi(28)
/* loaded from: classes.dex */
public final class AnimatedWebpDecoder {
    private final List<ImageHeaderParser> a;
    private final ArrayPool b;

    public static ResourceDecoder<InputStream, Drawable> streamDecoder(List<ImageHeaderParser> list, ArrayPool arrayPool) {
        return new c(new AnimatedWebpDecoder(list, arrayPool));
    }

    public static ResourceDecoder<ByteBuffer, Drawable> byteBufferDecoder(List<ImageHeaderParser> list, ArrayPool arrayPool) {
        return new b(new AnimatedWebpDecoder(list, arrayPool));
    }

    private AnimatedWebpDecoder(List<ImageHeaderParser> list, ArrayPool arrayPool) {
        this.a = list;
        this.b = arrayPool;
    }

    boolean a(ByteBuffer byteBuffer) throws IOException {
        return a(ImageHeaderParserUtils.getType(this.a, byteBuffer));
    }

    boolean a(InputStream inputStream) throws IOException {
        return a(ImageHeaderParserUtils.getType(this.a, inputStream, this.b));
    }

    private boolean a(ImageHeaderParser.ImageType imageType) {
        return imageType == ImageHeaderParser.ImageType.ANIMATED_WEBP;
    }

    Resource<Drawable> a(@NonNull ImageDecoder.Source source, int i, int i2, @NonNull Options options) throws IOException {
        Drawable decodeDrawable = ImageDecoder.decodeDrawable(source, new DefaultOnHeaderDecodedListener(i, i2, options));
        if (decodeDrawable instanceof AnimatedImageDrawable) {
            return new a((AnimatedImageDrawable) decodeDrawable);
        }
        throw new IOException("Received unexpected drawable type for animated webp, failing: " + decodeDrawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a implements Resource<Drawable> {
        private final AnimatedImageDrawable a;

        a(AnimatedImageDrawable animatedImageDrawable) {
            this.a = animatedImageDrawable;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        @NonNull
        public Class<Drawable> getResourceClass() {
            return Drawable.class;
        }

        @NonNull
        /* renamed from: a */
        public AnimatedImageDrawable get() {
            return this.a;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public int getSize() {
            return this.a.getIntrinsicWidth() * this.a.getIntrinsicHeight() * Util.getBytesPerPixel(Bitmap.Config.ARGB_8888) * 2;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public void recycle() {
            this.a.stop();
            this.a.clearAnimationCallbacks();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class c implements ResourceDecoder<InputStream, Drawable> {
        private final AnimatedWebpDecoder a;

        c(AnimatedWebpDecoder animatedWebpDecoder) {
            this.a = animatedWebpDecoder;
        }

        /* renamed from: a */
        public boolean handles(@NonNull InputStream inputStream, @NonNull Options options) throws IOException {
            return this.a.a(inputStream);
        }

        /* renamed from: a */
        public Resource<Drawable> decode(@NonNull InputStream inputStream, int i, int i2, @NonNull Options options) throws IOException {
            return this.a.a(ImageDecoder.createSource(ByteBufferUtil.fromStream(inputStream)), i, i2, options);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b implements ResourceDecoder<ByteBuffer, Drawable> {
        private final AnimatedWebpDecoder a;

        b(AnimatedWebpDecoder animatedWebpDecoder) {
            this.a = animatedWebpDecoder;
        }

        /* renamed from: a */
        public boolean handles(@NonNull ByteBuffer byteBuffer, @NonNull Options options) throws IOException {
            return this.a.a(byteBuffer);
        }

        /* renamed from: a */
        public Resource<Drawable> decode(@NonNull ByteBuffer byteBuffer, int i, int i2, @NonNull Options options) throws IOException {
            return this.a.a(ImageDecoder.createSource(byteBuffer), i, i2, options);
        }
    }
}
