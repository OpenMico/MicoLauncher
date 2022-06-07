package com.bumptech.glide.load.engine.cache;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.util.LruCache;

/* loaded from: classes.dex */
public class LruResourceCache extends LruCache<Key, Resource<?>> implements MemoryCache {
    private MemoryCache.ResourceRemovedListener a;

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    @Nullable
    public /* bridge */ /* synthetic */ Resource put(@NonNull Key key, @Nullable Resource resource) {
        return (Resource) super.put((LruResourceCache) key, (Key) resource);
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    @Nullable
    public /* bridge */ /* synthetic */ Resource remove(@NonNull Key key) {
        return (Resource) super.remove((LruResourceCache) key);
    }

    public LruResourceCache(long j) {
        super(j);
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    public void setResourceRemovedListener(@NonNull MemoryCache.ResourceRemovedListener resourceRemovedListener) {
        this.a = resourceRemovedListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onItemEvicted(@NonNull Key key, @Nullable Resource<?> resource) {
        MemoryCache.ResourceRemovedListener resourceRemovedListener = this.a;
        if (resourceRemovedListener != null && resource != null) {
            resourceRemovedListener.onResourceRemoved(resource);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSize(@Nullable Resource<?> resource) {
        if (resource == null) {
            return super.getSize((LruResourceCache) null);
        }
        return resource.getSize();
    }

    @Override // com.bumptech.glide.load.engine.cache.MemoryCache
    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i) {
        if (i >= 40) {
            clearMemory();
        } else if (i >= 20 || i == 15) {
            trimToSize(getMaxSize() / 2);
        }
    }
}
