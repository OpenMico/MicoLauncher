package com.xiaomi.micolauncher.module.main.manager;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class ImageSwatchCacheManager {
    private Map<String, Integer> a = new HashMap();

    /* loaded from: classes3.dex */
    private static class a {
        private static ImageSwatchCacheManager a = new ImageSwatchCacheManager();
    }

    public static ImageSwatchCacheManager getManager() {
        return a.a;
    }

    public void addCache(String str, Integer num) {
        this.a.put(str, num);
    }

    public int getCachedColor(String str) {
        if (this.a.containsKey(str)) {
            return this.a.get(str).intValue();
        }
        return 0;
    }

    public void clearCache() {
        this.a.clear();
    }
}
