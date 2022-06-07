package com.bumptech.glide.load.engine.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.MemoryCache;

/* loaded from: classes.dex */
public class MemoryCacheAdapter implements MemoryCache {
    private MemoryCache.ResourceRemovedListener a;

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public void clearMemory() {
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public long getCurrentSize() {
        return 0L;
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public long getMaxSize() {
        return 0L;
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    @Nullable
    public Resource<?> remove(@NonNull Key key) {
        return null;
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public void setSizeMultiplier(float f) {
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public void trimMemory(int i) {
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    @Nullable
    public Resource<?> put(@NonNull Key key, @Nullable Resource<?> resource) {
        if (resource == null) {
            return null;
        }
        this.a.onResourceRemoved(resource);
        return null;
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public void setResourceRemovedListener(@NonNull MemoryCache.ResourceRemovedListener resourceRemovedListener) {
        this.a = resourceRemovedListener;
    }
}
