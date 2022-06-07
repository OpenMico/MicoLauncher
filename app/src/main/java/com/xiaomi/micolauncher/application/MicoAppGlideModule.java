package com.xiaomi.micolauncher.application;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.xiaomi.micolauncher.common.GlideUtils;
import java.io.InputStream;
import okhttp3.OkHttpClient;

@GlideModule
/* loaded from: classes3.dex */
public class MicoAppGlideModule extends AppGlideModule {
    @Override // com.bumptech.glide.module.AppGlideModule, com.bumptech.glide.module.a
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder glideBuilder) {
        glideBuilder.setMemorySizeCalculator(new MemorySizeCalculator.Builder(context).setMemoryCacheScreens(2.0f).setBitmapPoolScreens(1.0f));
        glideBuilder.setDefaultRequestOptions(GlideUtils.getDefaultRequestOptions().format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig());
        glideBuilder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, 104857600));
        glideBuilder.setConnectivityMonitorFactory(new a());
    }

    @Override // com.bumptech.glide.module.LibraryGlideModule, com.bumptech.glide.module.b
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient glideConfigOkHttpClient = GlideOkHttpUtil.getGlideConfigOkHttpClient();
        if (glideConfigOkHttpClient == null) {
            super.registerComponents(context, glide, registry);
        } else {
            registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(glideConfigOkHttpClient));
        }
    }

    /* loaded from: classes3.dex */
    private static class a implements ConnectivityMonitorFactory {
        private a() {
        }

        @Override // com.bumptech.glide.manager.ConnectivityMonitorFactory
        @NonNull
        public ConnectivityMonitor build(@NonNull Context context, @NonNull ConnectivityMonitor.ConnectivityListener connectivityListener) {
            return new ConnectivityMonitor() { // from class: com.xiaomi.micolauncher.application.MicoAppGlideModule.a.1
                @Override // com.bumptech.glide.manager.LifecycleListener
                public void onDestroy() {
                }

                @Override // com.bumptech.glide.manager.LifecycleListener
                public void onStart() {
                }

                @Override // com.bumptech.glide.manager.LifecycleListener
                public void onStop() {
                }
            };
        }
    }
}
