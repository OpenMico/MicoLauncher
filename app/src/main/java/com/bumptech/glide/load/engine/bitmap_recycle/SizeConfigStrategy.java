package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.Util;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

@RequiresApi(19)
/* loaded from: classes.dex */
public class SizeConfigStrategy implements e {
    private static final Bitmap.Config[] a;
    private static final Bitmap.Config[] b;
    private static final Bitmap.Config[] c;
    private static final Bitmap.Config[] d;
    private static final Bitmap.Config[] e;
    private final b f = new b();
    private final d<a, Bitmap> g = new d<>();
    private final Map<Bitmap.Config, NavigableMap<Integer, Integer>> h = new HashMap();

    static {
        Bitmap.Config[] configArr = {Bitmap.Config.ARGB_8888, null};
        if (Build.VERSION.SDK_INT >= 26) {
            configArr = (Bitmap.Config[]) Arrays.copyOf(configArr, configArr.length + 1);
            configArr[configArr.length - 1] = Bitmap.Config.RGBA_F16;
        }
        a = configArr;
        b = a;
        c = new Bitmap.Config[]{Bitmap.Config.RGB_565};
        d = new Bitmap.Config[]{Bitmap.Config.ARGB_4444};
        e = new Bitmap.Config[]{Bitmap.Config.ALPHA_8};
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public void put(Bitmap bitmap) {
        a a2 = this.f.a(Util.getBitmapByteSize(bitmap), bitmap.getConfig());
        this.g.a(a2, bitmap);
        NavigableMap<Integer, Integer> a3 = a(bitmap.getConfig());
        Integer num = (Integer) a3.get(Integer.valueOf(a2.a));
        Integer valueOf = Integer.valueOf(a2.a);
        int i = 1;
        if (num != null) {
            i = 1 + num.intValue();
        }
        a3.put(valueOf, Integer.valueOf(i));
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    @Nullable
    public Bitmap get(int i, int i2, Bitmap.Config config) {
        a b2 = b(Util.getBitmapByteSize(i, i2, config), config);
        Bitmap a2 = this.g.a((d<a, Bitmap>) b2);
        if (a2 != null) {
            a(Integer.valueOf(b2.a), a2);
            a2.reconfigure(i, i2, config);
        }
        return a2;
    }

    private a b(int i, Bitmap.Config config) {
        a a2 = this.f.a(i, config);
        Bitmap.Config[] b2 = b(config);
        for (Bitmap.Config config2 : b2) {
            Integer ceilingKey = a(config2).ceilingKey(Integer.valueOf(i));
            if (ceilingKey != null && ceilingKey.intValue() <= i * 8) {
                if (ceilingKey.intValue() == i) {
                    if (config2 == null) {
                        if (config == null) {
                            return a2;
                        }
                    } else if (config2.equals(config)) {
                        return a2;
                    }
                }
                this.f.a(a2);
                return this.f.a(ceilingKey.intValue(), config2);
            }
        }
        return a2;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    @Nullable
    public Bitmap removeLast() {
        Bitmap a2 = this.g.a();
        if (a2 != null) {
            a(Integer.valueOf(Util.getBitmapByteSize(a2)), a2);
        }
        return a2;
    }

    private void a(Integer num, Bitmap bitmap) {
        NavigableMap<Integer, Integer> a2 = a(bitmap.getConfig());
        Integer num2 = (Integer) a2.get(num);
        if (num2 == null) {
            throw new NullPointerException("Tried to decrement empty size, size: " + num + ", removed: " + logBitmap(bitmap) + ", this: " + this);
        } else if (num2.intValue() == 1) {
            a2.remove(num);
        } else {
            a2.put(num, Integer.valueOf(num2.intValue() - 1));
        }
    }

    private NavigableMap<Integer, Integer> a(Bitmap.Config config) {
        NavigableMap<Integer, Integer> navigableMap = this.h.get(config);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.h.put(config, treeMap);
        return treeMap;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public String logBitmap(Bitmap bitmap) {
        return a(Util.getBitmapByteSize(bitmap), bitmap.getConfig());
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public String logBitmap(int i, int i2, Bitmap.Config config) {
        return a(Util.getBitmapByteSize(i, i2, config), config);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.e
    public int getSize(Bitmap bitmap) {
        return Util.getBitmapByteSize(bitmap);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SizeConfigStrategy{groupedMap=");
        sb.append(this.g);
        sb.append(", sortedSizes=(");
        for (Map.Entry<Bitmap.Config, NavigableMap<Integer, Integer>> entry : this.h.entrySet()) {
            sb.append(entry.getKey());
            sb.append('[');
            sb.append(entry.getValue());
            sb.append("], ");
        }
        if (!this.h.isEmpty()) {
            sb.replace(sb.length() - 2, sb.length(), "");
        }
        sb.append(")}");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class b extends c<a> {
        b() {
        }

        public a a(int i, Bitmap.Config config) {
            a c = c();
            c.a(i, config);
            return c;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public a b() {
            return new a(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static final class a implements f {
        int a;
        private final b b;
        private Bitmap.Config c;

        public a(b bVar) {
            this.b = bVar;
        }

        public void a(int i, Bitmap.Config config) {
            this.a = i;
            this.c = config;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.f
        public void a() {
            this.b.a(this);
        }

        public String toString() {
            return SizeConfigStrategy.a(this.a, this.c);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.a == aVar.a && Util.bothNullOrEqual(this.c, aVar.c);
        }

        public int hashCode() {
            int i = this.a * 31;
            Bitmap.Config config = this.c;
            return i + (config != null ? config.hashCode() : 0);
        }
    }

    static String a(int i, Bitmap.Config config) {
        return "[" + i + "](" + config + ")";
    }

    private static Bitmap.Config[] b(Bitmap.Config config) {
        if (Build.VERSION.SDK_INT >= 26 && Bitmap.Config.RGBA_F16.equals(config)) {
            return b;
        }
        switch (AnonymousClass1.a[config.ordinal()]) {
            case 1:
                return a;
            case 2:
                return c;
            case 3:
                return d;
            case 4:
                return e;
            default:
                return new Bitmap.Config[]{config};
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.bumptech.glide.load.engine.bitmap_recycle.SizeConfigStrategy$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Bitmap.Config.values().length];

        static {
            try {
                a[Bitmap.Config.ARGB_8888.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[Bitmap.Config.ALPHA_8.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
