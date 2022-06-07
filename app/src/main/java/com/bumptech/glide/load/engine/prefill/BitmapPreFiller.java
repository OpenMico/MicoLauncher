package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.prefill.PreFillType;
import com.bumptech.glide.util.Util;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class BitmapPreFiller {
    private final MemoryCache a;
    private final BitmapPool b;
    private final DecodeFormat c;
    private a d;

    public BitmapPreFiller(MemoryCache memoryCache, BitmapPool bitmapPool, DecodeFormat decodeFormat) {
        this.a = memoryCache;
        this.b = bitmapPool;
        this.c = decodeFormat;
    }

    public void preFill(PreFillType.Builder... builderArr) {
        Bitmap.Config config;
        a aVar = this.d;
        if (aVar != null) {
            aVar.a();
        }
        PreFillType[] preFillTypeArr = new PreFillType[builderArr.length];
        for (int i = 0; i < builderArr.length; i++) {
            PreFillType.Builder builder = builderArr[i];
            if (builder.a() == null) {
                if (this.c == DecodeFormat.PREFER_ARGB_8888) {
                    config = Bitmap.Config.ARGB_8888;
                } else {
                    config = Bitmap.Config.RGB_565;
                }
                builder.setConfig(config);
            }
            preFillTypeArr[i] = builder.b();
        }
        this.d = new a(this.b, this.a, a(preFillTypeArr));
        Util.postOnUiThread(this.d);
    }

    @VisibleForTesting
    b a(PreFillType... preFillTypeArr) {
        long maxSize = (this.a.getMaxSize() - this.a.getCurrentSize()) + this.b.getMaxSize();
        int i = 0;
        for (PreFillType preFillType : preFillTypeArr) {
            i += preFillType.d();
        }
        float f = ((float) maxSize) / i;
        HashMap hashMap = new HashMap();
        for (PreFillType preFillType2 : preFillTypeArr) {
            hashMap.put(preFillType2, Integer.valueOf(Math.round(preFillType2.d() * f) / a(preFillType2)));
        }
        return new b(hashMap);
    }

    private static int a(PreFillType preFillType) {
        return Util.getBitmapByteSize(preFillType.a(), preFillType.b(), preFillType.c());
    }
}
