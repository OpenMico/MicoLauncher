package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.ByteBufferUtil;
import java.io.IOException;
import java.io.InputStream;

@RequiresApi(api = 28)
/* loaded from: classes.dex */
public final class InputStreamBitmapImageDecoderResourceDecoder implements ResourceDecoder<InputStream, Bitmap> {
    private final BitmapImageDecoderResourceDecoder a = new BitmapImageDecoderResourceDecoder();

    public boolean handles(@NonNull InputStream inputStream, @NonNull Options options) throws IOException {
        return true;
    }

    public Resource<Bitmap> decode(@NonNull InputStream inputStream, int i, int i2, @NonNull Options options) throws IOException {
        return this.a.decode(ImageDecoder.createSource(ByteBufferUtil.fromStream(inputStream)), i, i2, options);
    }
}
