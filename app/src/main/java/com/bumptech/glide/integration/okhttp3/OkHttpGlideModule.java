package com.bumptech.glide.integration.okhttp3;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import java.io.InputStream;

@Deprecated
/* loaded from: classes.dex */
public class OkHttpGlideModule implements GlideModule {
    @Override // com.bumptech.glide.module.a
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder glideBuilder) {
    }

    @Override // com.bumptech.glide.module.b
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
