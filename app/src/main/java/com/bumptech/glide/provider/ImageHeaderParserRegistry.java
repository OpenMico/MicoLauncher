package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.ImageHeaderParser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ImageHeaderParserRegistry {
    private final List<ImageHeaderParser> a = new ArrayList();

    @NonNull
    public synchronized List<ImageHeaderParser> getParsers() {
        return this.a;
    }

    public synchronized void add(@NonNull ImageHeaderParser imageHeaderParser) {
        this.a.add(imageHeaderParser);
    }
}
