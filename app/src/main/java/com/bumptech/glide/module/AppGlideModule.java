package com.bumptech.glide.module;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.GlideBuilder;

/* loaded from: classes.dex */
public abstract class AppGlideModule extends LibraryGlideModule implements a {
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder glideBuilder) {
    }

    public boolean isManifestParsingEnabled() {
        return true;
    }
}
