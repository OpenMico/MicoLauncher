package com.bumptech.glide;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule;
import com.xiaomi.micolauncher.application.MicoAppGlideModule;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes.dex */
final class GeneratedAppGlideModuleImpl extends GeneratedAppGlideModule {
    private final MicoAppGlideModule a = new MicoAppGlideModule();

    public GeneratedAppGlideModuleImpl(Context context) {
        if (Log.isLoggable("Glide", 3)) {
            Log.d("Glide", "Discovered AppGlideModule from annotation: com.xiaomi.micolauncher.application.MicoAppGlideModule");
            Log.d("Glide", "Discovered LibraryGlideModule from annotation: com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule");
        }
    }

    @Override // com.bumptech.glide.module.AppGlideModule, com.bumptech.glide.module.a
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder glideBuilder) {
        this.a.applyOptions(context, glideBuilder);
    }

    @Override // com.bumptech.glide.module.LibraryGlideModule, com.bumptech.glide.module.b
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        new OkHttpLibraryGlideModule().registerComponents(context, glide, registry);
        this.a.registerComponents(context, glide, registry);
    }

    @Override // com.bumptech.glide.module.AppGlideModule
    public boolean isManifestParsingEnabled() {
        return this.a.isManifestParsingEnabled();
    }

    @Override // com.bumptech.glide.GeneratedAppGlideModule
    @NonNull
    public Set<Class<?>> a() {
        return Collections.emptySet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    /* renamed from: c */
    public a b() {
        return new a();
    }
}
