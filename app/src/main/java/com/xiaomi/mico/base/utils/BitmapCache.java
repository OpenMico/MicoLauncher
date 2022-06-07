package com.xiaomi.mico.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class BitmapCache {
    public static final int MAX_HEIGHT = 1280;
    private HashMap<String, Reference<a>> a = new HashMap<>();
    private static final BitmapCache b = new BitmapCache();
    public static final int MAX_WIDTH = 720;
    public static final DecodeOptions DEFAULT_DECODE_OPTIONS = new DecodeOptions(MAX_WIDTH, 1280);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a {
        Bitmap a;
        public int b;
        public int c;

        a() {
        }
    }

    public static BitmapCache instance() {
        return b;
    }

    /* loaded from: classes3.dex */
    public static class DecodeOptions {
        int a;
        int b;

        public DecodeOptions(int i, int i2) {
            this.b = i2;
            this.a = i;
        }
    }

    public Bitmap getBitmap(String str, DecodeOptions decodeOptions) {
        Reference<a> reference;
        a aVar;
        int i;
        int i2;
        Bitmap bitmap = null;
        if (str == null) {
            return null;
        }
        if (decodeOptions == null) {
            decodeOptions = DEFAULT_DECODE_OPTIONS;
        }
        synchronized (this) {
            reference = this.a.get(str);
        }
        if (reference != null) {
            aVar = reference.get();
            if (aVar != null && a(aVar, decodeOptions)) {
                return aVar.a;
            }
        } else {
            aVar = null;
        }
        if (aVar != null) {
            i2 = aVar.b;
            i = aVar.c;
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            int i3 = options.outWidth;
            i = options.outHeight;
            i2 = i3;
        }
        try {
            bitmap = CommonUtils.decodeFile2(str, decodeOptions.a, decodeOptions.b);
            if (aVar == null) {
                aVar = new a();
            }
            aVar.a = bitmap;
            aVar.b = i2;
            aVar.c = i;
            synchronized (this) {
                this.a.put(str, new SoftReference(aVar));
            }
        } catch (IOException e) {
            Log.e("BitmapCache", "failed to decode bitmap file" + str, e);
        }
        return bitmap;
    }

    private boolean a(a aVar, DecodeOptions decodeOptions) {
        Bitmap bitmap = aVar.a;
        if (bitmap == null) {
            return false;
        }
        return (bitmap.getWidth() >= decodeOptions.a && bitmap.getHeight() >= decodeOptions.b) || aVar.b <= decodeOptions.a || aVar.c <= decodeOptions.b;
    }

    public Bitmap peekBitmap(String str) {
        Reference<a> reference;
        a aVar;
        if (str == null) {
            return null;
        }
        synchronized (this) {
            reference = this.a.get(str);
        }
        if (reference == null || (aVar = reference.get()) == null) {
            return null;
        }
        return aVar.a;
    }
}
