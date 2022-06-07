package com.bumptech.glide.load.engine.cache;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.StateVerifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes.dex */
public class SafeKeyGenerator {
    private final LruCache<Key, String> a = new LruCache<>(1000);
    private final Pools.Pool<a> b = FactoryPools.threadSafe(10, new FactoryPools.Factory<a>() { // from class: com.bumptech.glide.load.engine.cache.SafeKeyGenerator.1
        /* renamed from: a */
        public a create() {
            try {
                return new a(MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    });

    public String getSafeKey(Key key) {
        String str;
        synchronized (this.a) {
            str = this.a.get(key);
        }
        if (str == null) {
            str = a(key);
        }
        synchronized (this.a) {
            this.a.put(key, str);
        }
        return str;
    }

    private String a(Key key) {
        a aVar = (a) Preconditions.checkNotNull(this.b.acquire());
        try {
            key.updateDiskCacheKey(aVar.a);
            return Util.sha256BytesToHex(aVar.a.digest());
        } finally {
            this.b.release(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a implements FactoryPools.Poolable {
        final MessageDigest a;
        private final StateVerifier b = StateVerifier.newInstance();

        a(MessageDigest messageDigest) {
            this.a = messageDigest;
        }

        @Override // com.bumptech.glide.util.pool.FactoryPools.Poolable
        @NonNull
        public StateVerifier getVerifier() {
            return this.b;
        }
    }
}
