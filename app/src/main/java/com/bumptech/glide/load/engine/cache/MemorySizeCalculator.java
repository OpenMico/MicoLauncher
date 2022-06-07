package com.bumptech.glide.load.engine.cache;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.util.Preconditions;
import com.xiaomi.idm.api.IDMServer;

/* loaded from: classes.dex */
public final class MemorySizeCalculator {
    private final int a;
    private final int b;
    private final Context c;
    private final int d;

    /* loaded from: classes.dex */
    interface b {
        int a();

        int b();
    }

    MemorySizeCalculator(Builder builder) {
        int i;
        this.c = builder.b;
        if (a(builder.c)) {
            i = builder.i / 2;
        } else {
            i = builder.i;
        }
        this.d = i;
        int a2 = a(builder.c, builder.g, builder.h);
        float a3 = builder.d.a() * builder.d.b() * 4;
        int round = Math.round(builder.f * a3);
        int round2 = Math.round(a3 * builder.e);
        int i2 = a2 - this.d;
        int i3 = round2 + round;
        if (i3 <= i2) {
            this.b = round2;
            this.a = round;
        } else {
            float f = i2 / (builder.f + builder.e);
            this.b = Math.round(builder.e * f);
            this.a = Math.round(f * builder.f);
        }
        if (Log.isLoggable("MemorySizeCalculator", 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calculation complete, Calculated memory cache size: ");
            sb.append(a(this.b));
            sb.append(", pool size: ");
            sb.append(a(this.a));
            sb.append(", byte array size: ");
            sb.append(a(this.d));
            sb.append(", memory class limited? ");
            sb.append(i3 > a2);
            sb.append(", max size: ");
            sb.append(a(a2));
            sb.append(", memoryClass: ");
            sb.append(builder.c.getMemoryClass());
            sb.append(", isLowMemoryDevice: ");
            sb.append(a(builder.c));
            Log.d("MemorySizeCalculator", sb.toString());
        }
    }

    public int getMemoryCacheSize() {
        return this.b;
    }

    public int getBitmapPoolSize() {
        return this.a;
    }

    public int getArrayPoolSizeInBytes() {
        return this.d;
    }

    private static int a(ActivityManager activityManager, float f, float f2) {
        boolean a2 = a(activityManager);
        float memoryClass = activityManager.getMemoryClass() * 1024 * 1024;
        if (a2) {
            f = f2;
        }
        return Math.round(memoryClass * f);
    }

    private String a(int i) {
        return Formatter.formatFileSize(this.c, i);
    }

    @TargetApi(19)
    static boolean a(ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice();
        }
        return true;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        static final int a;
        final Context b;
        ActivityManager c;
        b d;
        float f;
        float e = 2.0f;
        float g = 0.4f;
        float h = 0.33f;
        int i = 4194304;

        static {
            a = Build.VERSION.SDK_INT < 26 ? 4 : 1;
        }

        public Builder(Context context) {
            this.f = a;
            this.b = context;
            this.c = (ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
            this.d = new a(context.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= 26 && MemorySizeCalculator.a(this.c)) {
                this.f = 0.0f;
            }
        }

        public Builder setMemoryCacheScreens(float f) {
            Preconditions.checkArgument(f >= 0.0f, "Memory cache screens must be greater than or equal to 0");
            this.e = f;
            return this;
        }

        public Builder setBitmapPoolScreens(float f) {
            Preconditions.checkArgument(f >= 0.0f, "Bitmap pool screens must be greater than or equal to 0");
            this.f = f;
            return this;
        }

        public Builder setMaxSizeMultiplier(float f) {
            Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Size multiplier must be between 0 and 1");
            this.g = f;
            return this;
        }

        public Builder setLowMemoryMaxSizeMultiplier(float f) {
            Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Low memory max size multiplier must be between 0 and 1");
            this.h = f;
            return this;
        }

        public Builder setArrayPoolSize(int i) {
            this.i = i;
            return this;
        }

        public MemorySizeCalculator build() {
            return new MemorySizeCalculator(this);
        }
    }

    /* loaded from: classes.dex */
    private static final class a implements b {
        private final DisplayMetrics a;

        a(DisplayMetrics displayMetrics) {
            this.a = displayMetrics;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.b
        public int a() {
            return this.a.widthPixels;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.b
        public int b() {
            return this.a.heightPixels;
        }
    }
}
